package helpers;

import org.apache.activemq.ActiveMQConnectionFactory;
import javax.jms.MessageProducer;
import javax.jms.Session;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static helpers.PartitionLinkedList.partition;
import static helpers.StrandSortHelperMethods.merge;
import static helpers.StrandSortHelperMethods.orderList;
import javax.jms.*;

public class ActiveMQHelper {
    private volatile LinkedList<Integer> temporaryList = new LinkedList<Integer>();
    private static final String URL = "tcp://localhost:61616?jms.prefetchPolicy.all=1";

    private Session session;
    private Destination destination;
    private Connection connection;

    private int produced = 0;
    private int consumed = 0;

    public void starter(LinkedList<Integer> produceList, int amountOfThreads) throws InterruptedException, JMSException {
        List<LinkedList<Integer>> partitions = partition(produceList, produceList.size() / amountOfThreads);

        ArrayList<Thread> produceTasks = new ArrayList<>(amountOfThreads);
        ArrayList<Thread> consumeTasks = new ArrayList<>(amountOfThreads);

        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(URL);
        connection = connectionFactory.createConnection();
        connection.start();
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        destination = session.createTemporaryQueue();

        for (int i = 0; i < amountOfThreads; i++) {
            LinkedList<Integer> listPart = partitions.get(i);

            produceTasks.add(new Thread(() -> {
                try {
                    produce(listPart);
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }));
            produceTasks.get(i).start();
        }

        for (int i = 0; i < amountOfThreads; i++) {
            produceTasks.get(i).join();
        }

        for (int i = 0; i < amountOfThreads; i++) {
            consumeTasks.add(new Thread(() -> {
                try {
                    consume();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }));
            consumeTasks.get(i).start();
        }

        for (int i = 0; i < amountOfThreads; i++) {
            consumeTasks.get(i).join();
        }


        System.out.println("produced: " + produced);
        System.out.println("consumed: " + consumed);

        connection.close();
    }

    private synchronized void produce(LinkedList<Integer> produceList) throws JMSException {
        MessageProducer producer = session.createProducer(destination);
        String listString = produceList.stream().map(Object::toString).collect(Collectors.joining(","));

        String produceListString = produceList.stream().map(Object::toString).collect(Collectors.joining(","));
        TextMessage textMessage = session.createTextMessage(produceListString);

        producer.send(textMessage);
        System.out.println("P");
        produced++;
    }

    public volatile LinkedList<Integer> resultList = new LinkedList<>();

    private synchronized void consume() throws JMSException {
        MessageConsumer consumer = session.createConsumer(destination);
        consumer.setMessageListener(tempMessage -> {
            try {
                TextMessage textMessage = (TextMessage) tempMessage;
                String messageText = textMessage.getText();

                LinkedList<Integer> messageList = new LinkedList<>();
                for (String message : messageText.split(",")) {
                    messageList.add(Integer.parseInt(message));
                }

                temporaryList = orderList(messageList, temporaryList);
                resultList = merge(temporaryList, resultList);
                temporaryList.clear();

                System.out.println("C");
                consumed++;
            } catch (JMSException e) {
                e.printStackTrace();
            }
        });
    }
}
