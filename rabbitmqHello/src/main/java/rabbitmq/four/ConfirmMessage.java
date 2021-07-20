package rabbitmq.four;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmCallback;
import util.RabbitMqUtils;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

public class ConfirmMessage {
    public final static int MESSAGE_COUNT = 1000;
    public static void main(String[] args) throws InterruptedException, TimeoutException, IOException {
        // ConfirmMessage.publicMessageIndividually();
        // ConfirmMessage.publicMessageBatch();
        publicMessageAsync();

    }
    // 单个确认
    public static void publicMessageIndividually() throws IOException, TimeoutException, InterruptedException {
        Channel channel = RabbitMqUtils.getChannel();
        //队列声明
        String queueName = UUID.randomUUID().toString();
        channel.queueDeclare(queueName, false, false,false,null);
        // 开启发布确认
        channel.confirmSelect();
        // 开始时间
        long begin = System.currentTimeMillis();
        // 批量发送消息
        for (int i = 0; i < MESSAGE_COUNT; i++) {
            String message = i + "";
            channel.basicPublish("", queueName,null,message.getBytes());
            // 单个消息马上进行发布确认
            boolean b = channel.waitForConfirms();
            if (false) {
                System.out.println("消息发送成功");
            }
        }
        // 结束时间
        long end = System.currentTimeMillis();
        System.out.println("单独发布确认发布时间：" + (end-begin));
    }
    // 批量发布
    public static void publicMessageBatch() throws IOException, TimeoutException, InterruptedException {
        Channel channel = RabbitMqUtils.getChannel();
        //队列声明
        String queueName = UUID.randomUUID().toString();
        channel.queueDeclare(queueName, false, false,false,null);
        // 开启发布确认
        channel.confirmSelect();
        // 开始时间
        long begin = System.currentTimeMillis();
        // 批量确认消息大小
        int batchSize = 100;
        // 批量发送消息
        for (int i = 0; i < MESSAGE_COUNT; i++) {
            String message = i + "";
            channel.basicPublish("", queueName,null,message.getBytes());
            // 单个消息马上进行发布确认
            boolean b = channel.waitForConfirms();
            // 满一百条，确认一次
            if (i%batchSize == 0) {
                channel.confirmSelect();
            }
        }
        // 结束时间
        long end = System.currentTimeMillis();
        System.out.println("单独发布确认发布时间：" + (end-begin));
    }
    // 异步确认
    public static void publicMessageAsync() throws IOException, TimeoutException, InterruptedException {
        Channel channel = RabbitMqUtils.getChannel();
        //队列声明
        String queueName = UUID.randomUUID().toString();
        channel.queueDeclare(queueName, false, false,false,null);
        // 开启发布确认
        channel.confirmSelect();
        // 开始时间
        long begin = System.currentTimeMillis();
        // 批量确认消息大小
        int batchSize = 100;
        // 消息确认成功，回调函数
        // deliveryTag,消息标识，multiple是否为批量确认
        ConfirmCallback ackCallback = (deliveryTag,multiple) -> {

        };
        ConfirmCallback nackCallback = (deliveryTag,multiple) -> {
            System.out.println("未确认的消息：" + deliveryTag);
        };
        // 消息监听器
        channel.addConfirmListener(ackCallback, nackCallback);
        // 批量发送消息
        for (int i = 0; i < MESSAGE_COUNT; i++) {
            String message = i + "";
            channel.basicPublish("", queueName,null,message.getBytes());
        }
        // 结束时间
        long end = System.currentTimeMillis();
        System.out.println("单独发布确认发布时间：" + (end-begin));
    }
}
