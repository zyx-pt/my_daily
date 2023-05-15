package mq.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;


/**
 * @Description: RabbitMQ工具类
 * @ClassName mq.rabbitmq.RabbitMQUtils
 * @Author yxzheng
 * @Date 2022/8/24 11:39
 */
public class RabbitMQUtils {
    public static final String QUEUE_NAME_HELLO = "hello";

    public static Channel getChannel() throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("112.124.36.159");
        connectionFactory.setUsername("admin");
        connectionFactory.setPassword("admin");
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        return channel;
    }
}
