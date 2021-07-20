package util;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class RabbitMqUtils {
    public static Channel getChannel() throws IOException, TimeoutException {
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
        return channel;
    }
}
