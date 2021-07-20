package rabbitmq.three;

import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import util.RabbitMqUtils;
import util.SleepUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Worker03 {
    public static final String TASK_QUEUE_NAME = "ack_queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = RabbitMqUtils.getChannel();
        System.out.println("C1等待接收消息处理时间较短");
        // 声明接收消息
        DeliverCallback deliverCallback = (consumerTag, message) -> {
            SleepUtils.sleep(1);
            byte[] body = message.getBody();
            System.out.println("接收到的消息：" + new String(body));
            // 手动应答
            // 消息的标记 tag
            // 是否批量应答
            channel.basicAck(message.getEnvelope().getDeliveryTag(), false);
        };
        // 取消消息的回调
        CancelCallback cancelCallback = consumerTag -> {
            System.out.println("消息消费被中断");
        };
        // 设置不公平分发
        int prefetchCount = 2;
        channel.basicQos(prefetchCount);
        boolean autoAck = false;
        channel.basicConsume(TASK_QUEUE_NAME, autoAck, deliverCallback, cancelCallback);

    }

}
