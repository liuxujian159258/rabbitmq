package rabbitmq.six;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import util.RabbitMqUtils;
import util.SleepUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ReceiveLogsDirect02 {

    public static final String EXCHANGE_NAME = "direct_logs";

    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = RabbitMqUtils.getChannel();
        // 声明交换机
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
        // 声明一个队列
        channel.queueDeclare("disk", false, false, false, null);
        channel.queueBind("disk", EXCHANGE_NAME, "error");
        // 成功接收消息的回调函数
        DeliverCallback deliverCallback = (consumerTag, message) -> {
            byte[] body = message.getBody();
            System.out.println("ReceiveLogsDirect02接收到的消息：" + new String(body));
        };
        channel.basicConsume("disk", true, deliverCallback, consumerTag -> {});
    }

}
