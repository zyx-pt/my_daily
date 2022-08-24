package mq.rabbitmq;

import com.rabbitmq.client.*;
import lombok.SneakyThrows;

/**
 * @Description: RabbitMQ消费者
 * @ClassName mq.rabbitmq.RabbitMQConsumer
 * @Author zhengyongxian
 * @Date 2022/8/12 17:47
 */
public class RabbitMQConsumer {
    public static final String QUEUE_NAME = "hello";

    @SneakyThrows
    public static void main(String[] args) {
        Channel channel = RabbitMQUtils.getChannel();
        // 声明接收消息
        DeliverCallback deliverCallback = (comsumerTag, message) ->{
            System.out.println("消费者获取到："+new String(message.getBody()));
        };
        // 取消消息时的回调
        CancelCallback cancelCallback = consumerTag -> {
            System.out.println("消息消费被中断");
        };
        /**
         *消费者消费消息
         * 1.消费哪个队列
         * 2.消费成功后是否自动应答
         * 3.消费者未能成功消费的回调
         * 4.消费者取消消费的回调
         */
        channel.basicConsume(QUEUE_NAME, true, deliverCallback, cancelCallback);
    }

}
