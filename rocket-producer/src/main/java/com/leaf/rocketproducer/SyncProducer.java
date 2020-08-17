package com.leaf.rocketproducer;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.io.UnsupportedEncodingException;

/**
 * @author leaves_ck
 * @create 2020-08-17-6:13 下午
 */
public class SyncProducer {
    public static void main(String[] args) throws MQClientException, UnsupportedEncodingException, RemotingException, InterruptedException, MQBrokerException {

        //初始化一个消息提供者，并制定消息组名
        DefaultMQProducer producer = new DefaultMQProducer("test-group-name");
        //指定namesrv的地址
        producer.setNamesrvAddr("localhost:9876");
        //装载消息提供者
        producer.start();

        for (int i = 0; i < 100; i++){
            //创建一个消息实体，指定Topic、tag 以及 消息内容
            Message msg = new Message("TopicTest", "TagA",
                    ("Hello RocketMQ"+i).getBytes(RemotingHelper.DEFAULT_CHARSET));

            SendResult sendResult = producer.send(msg);
            System.out.printf("%s%n", sendResult);
        }
        producer.shutdown();
    }
}
