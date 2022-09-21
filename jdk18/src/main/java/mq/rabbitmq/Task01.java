package mq.rabbitmq;

import com.rabbitmq.client.Channel;
import lombok.SneakyThrows;

import java.util.Scanner;

/**
 * @Description: 通过控制台输入多次发送消息
 * @ClassName mq.rabbitmq.Task01
 * @Author zhengyongxian
 * @Date 2022/8/24 18:17
 */
public class Task01 {

    @SneakyThrows
    public static void main(String[] args) {
        Channel channel = RabbitMQUtils.getChannel();
        // 队列声明
        channel.queueDeclare(RabbitMQUtils.QUEUE_NAME_HELLO, false, false, false, null);
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()){
            String message = scanner.next();
            channel.basicPublish("", RabbitMQUtils.QUEUE_NAME_HELLO, null, message.getBytes());
            System.out.println("消息发送完成："+message);
        }

    }
}
