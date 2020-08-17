package com.leaf.rocketproducer;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

/**
 * @author leaves_ck
 * @create 2020-08-17-9:26 下午
 */
public class OneWayProducer {

    public static void main(String[] args) throws Exception{
        DefaultMQProducer producer = new DefaultMQProducer("on-way-producer-group");

        producer.setNamesrvAddr("localhost:9876");

        producer.start();

        for (int i = 0; i < 100; i++){

            Message msg = new Message("TopicTest","TagA",
                    ("Hello RocketMq"+i).getBytes(RemotingHelper.DEFAULT_CHARSET));

            producer.sendOneway(msg);
        }

        Thread.sleep(5000);

        producer.shutdown();

    }
}
