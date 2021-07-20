package rabbitmq.two;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import util.RabbitMqUtils;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

public class Task01 {
    public static final String QUEUE_NAME = "hello";

    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = RabbitMqUtils.getChannel();
        AMQP.Queue.DeclareOk declareOk = channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String message = scanner.next();
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
            System.out.println("消息发送完毕:" + message);
        }
    }
}
