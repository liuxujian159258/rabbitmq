package rabbitmq.two;

import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import util.RabbitMqUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Worker01 {
    public static final String QUEUE_NAME = "hello";

    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = RabbitMqUtils.getChannel();
        // 声明接收消息
        DeliverCallback deliverCallback = (consumerTag, message) -> {
            byte[] body = message.getBody();
            System.out.println("接收到的消息：" + new String(body));
        };
        // 取消消息的回调
        CancelCallback cancelCallback = consumerTag -> {
            System.out.println("消息消费被中断");
        };
        System.out.println("C2等待接收消息》》》》");
        channel.basicConsume(QUEUE_NAME, true, deliverCallback, cancelCallback);
    }
}
