package com.leaf.rocketproducer;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author leaves_ck
 * @create 2020-08-17-8:11 下午
 */
public class AsyncProducer {

    public static void main(String[] args) throws Exception{

        DefaultMQProducer producer = new DefaultMQProducer("async-produce-group");

        producer.setNamesrvAddr("localhost:9876");

        producer.start();

        producer.setRetryTimesWhenSendAsyncFailed(0);

        int messageCount = 100;

        final CountDownLatch countDownLatch = new CountDownLatch(messageCount);

        for (int i = 0; i< 100; i++){

            try {
                final int index = i;

                Message msg = new Message("Jodie_topic_1023", "TagA", "OrderId188",
                        "Hello world".getBytes(RemotingHelper.DEFAULT_CHARSET));
                producer.send(msg, new SendCallback() {
                    @Override
                    public void onSuccess(SendResult sendResult) {
                        countDownLatch.countDown();
                        System.out.printf("%-10d OK %s %n", index, sendResult.getMsgId());
                    }

                    @Override
                    public void onException(Throwable throwable) {
                        countDownLatch.countDown();
                        System.out.printf("%-10d Exception %s %n", index, throwable);
                        throwable.printStackTrace();
                    }
                });
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        countDownLatch.await(5, TimeUnit.SECONDS);
        producer.shutdown();
    }
}
