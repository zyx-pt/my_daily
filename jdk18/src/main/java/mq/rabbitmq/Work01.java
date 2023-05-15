package mq.rabbitmq;

import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import lombok.SneakyThrows;

/**
 * @Description: 支持启动多个线程进行消费，测试【轮询消费】
 * @ClassName mq.rabbitmq.RabbitMQConsumer
 * @Author yxzheng
 * @Date 2022/8/12 17:47
 */
public class Work01 {

    @SneakyThrows
    public static void main(String[] args) {
        Channel channel = RabbitMQUtils.getChannel();
        // 声明接收消息
        DeliverCallback deliverCallback = (comsumerTag, message) ->{
            System.out.println("接收到消息："+new String(message.getBody()));
        };
        // 取消消息时的回调
        CancelCallback cancelCallback = consumerTag -> {
            System.out.println("消息消费被取消");
        };
        System.out.println("消费者C2等待接受消息");
        channel.basicConsume(RabbitMQUtils.QUEUE_NAME_HELLO, true, deliverCallback, cancelCallback);
    }

}
