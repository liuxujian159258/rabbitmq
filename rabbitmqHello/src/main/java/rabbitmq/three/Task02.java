package rabbitmq.three;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;
import util.RabbitMqUtils;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

public class Task02 {
    public static final String TASK_QUEUE_NAME = "ack_queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = RabbitMqUtils.getChannel();
        // 开启发布确认
        channel.confirmSelect();
        // 持久化
        boolean durable = true;
        AMQP.Queue.DeclareOk declareOk = channel.queueDeclare(TASK_QUEUE_NAME, durable, false, false, null);
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String message = scanner.next();
            // 设置生产者发送消息为持久化消息（保存到磁盘上）
            channel.basicPublish("", TASK_QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
            System.out.println("生产者消息发送完毕:" + message);
        }
    }
}
