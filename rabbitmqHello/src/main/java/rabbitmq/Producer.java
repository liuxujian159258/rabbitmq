package rabbitmq;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Producer {
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
        // 创建队列
        // 1.队列名称
        // 2.是否持久化
        // 3.是否多个消费者使用
        // 4.最后一个消费者断开连接后是否删除
        // 5.其他参数
        AMQP.Queue.DeclareOk declareOk = channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        // 发送消息
        String message = "hello world";
        // 发送一个消费
        // 1.发送到能够交换机
        // 2.路由的key是那个，本次是队列的名称
        // 3.其他参数信息
        // 4。发送的信息
        channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
        System.out.println("消息发送完毕");
    }
}
