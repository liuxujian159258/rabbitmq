package rabbitmq.five;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import util.RabbitMqUtils;
import util.SleepUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ReceiveLogs02 {

    // 交换机的名称
    public static final String EXCHANGE_NAME = "logs";

    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = RabbitMqUtils.getChannel();
        // 声明一个交换机
        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
        // 声明一个临时队列，名称随机，用完即删除
        String queue = channel.queueDeclare().getQueue();
        // 绑定交换机与队列
        channel.queueBind(queue,EXCHANGE_NAME,"");
        System.out.println("02等待接收消息，***********");
        // 成功接收消息的回调函数
        DeliverCallback deliverCallback = (consumerTag, message) -> {
            SleepUtils.sleep(1);
            byte[] body = message.getBody();
            System.out.println("接收到的消息：" + new String(body));
        };
        channel.basicConsume(queue, true, deliverCallback, consumerTag -> {}) ;
    }
}
