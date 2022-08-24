package mq.rabbitmq;

import com.rabbitmq.client.Channel;
import lombok.SneakyThrows;

/**
 * @Description: RabbitMQ生成者
 * @ClassName mq.rabbitmq.Producer
 * @Author zhengyongxian
 * @Date 2022/8/12 17:36
 */
public class RabbitMQProducer {
    public static final String QUEUE_NAME = "hello";

    @SneakyThrows
    public static void main(String[] args) {
        Channel channel = RabbitMQUtils.getChannel();
        /** 生成队列
         * 1.队列名称
         * 2.队列里面的消息是否持久化，默认情况下，消息存储在内存中
         * 3.该队列是否只供一个消费者进行消费，true可以只能一个消费者消费，false 多个消费者消费
         * 4.是否自动删除，最后一个消费者端开连接以后，该队列是否自动删除，true表示自动删除
         * 5.其他参数
         */
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        String message = "Hello, World";
        /**
         *发送一个消息
         * 1.发送到哪个交换机
         * 2.路由的key值是哪个，本次是队列的名称
         * 3.其他参数信息
         * 4.发送消息的消息体
         */
        channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
        System.out.println("消息推送完毕！");

    }
}
