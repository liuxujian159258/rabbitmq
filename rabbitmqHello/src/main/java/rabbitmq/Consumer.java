package rabbitmq;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Consumer {
    public static final String QUEUE_NAME = "hello";

    public static void main(String[] args) throws IOException, TimeoutException {
        // 创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        // 工厂IP连接RabbitMQ的队列
        factory.setHost("localhost");
        factory.setUsername("guest");
        factory.setPassword("guest");
        // 创建连接
        Connection connection = factory.newConnection();
        // 获取信道
        Channel channel = connection.createChannel();
        // 消费者消费消息
        // 1.消费那个队列
        // 2.消费成功之后是否要自动应答
        // 3.未成功消费的回调
        // 4.消费者取消消费的回调
        // 声明接收消息
        DeliverCallback deliverCallback = (consumerTag, message) -> {
            byte[] body = message.getBody();
            System.out.println(new String(body));
        };
        // 取消消息的回调
        CancelCallback cancelCallback = consumerTag -> {
            System.out.println("消息消费被中断");
        };
        channel.basicConsume(QUEUE_NAME, true, deliverCallback, cancelCallback);
    }
}
