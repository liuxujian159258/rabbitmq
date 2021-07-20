package rabbitmq.seven;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import util.RabbitMqUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ReceiveTopic02 {
    public static final String TOPIC_NAME = "topic_logs";

    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = RabbitMqUtils.getChannel();
        channel.exchangeDeclare(TOPIC_NAME, BuiltinExchangeType.TOPIC);
        // 队列名称
        String queueName="Q2";
        channel.queueDeclare(queueName, false, false, false, null);
        channel.queueBind(queueName, TOPIC_NAME, "*.*.rabbit");
        channel.queueBind(queueName, TOPIC_NAME, "lazy.#");
        DeliverCallback deliverCallback = (consumerTag, message) -> {
            byte[] body = message.getBody();
            System.out.println("ReceiveTopic02接收到的消息：" + new String(body));
        };
        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {});
    }
}
