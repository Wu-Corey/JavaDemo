package com.example.javademo.m4_juc.wait_notify;

import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;

/**
 * 2022/2/26
 * 异步模式之生产者/消费者模型
 */
public class TestProducerConsumer {

    public static void main(String[] args) {

        MQ mq = new MQ(10);

        for (int i = 0;i < 100;i++){
            int finalI = i;
            new Thread(()->{
                Message message = new Message(finalI, "消息"+finalI);
                mq.produce(message);
            },"producer" + i).start();
        }

        for (int i = 0;i < 100;i++){
            new Thread(mq::consume,"consumer" + i).start();
        }


    }
}

@Slf4j
class MQ{

    private LinkedList<Message> queue;

    private Integer capacity;

    public MQ(Integer capacity) {
        this.queue = new LinkedList<>();
        this.capacity = capacity;
    }

    public Message consume(){
        synchronized (queue){
            while (queue.isEmpty()){
                try {
                    log.info("队列中没有消息 consumer进入wait...");
                    queue.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Message message = queue.removeFirst();
            queue.notifyAll();
            log.info("消费消息：{}",message);
            return message;
        }
    }

    public void produce(Message message){

        synchronized (queue){
            while (queue.size() == capacity){
                try {
                    log.info("队列满了 producer进入wait...");
                    queue.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            log.info("生产消息加入队列：{}",message);
            queue.addLast(message);
            queue.notifyAll();
        }
    }


}

final class Message{

    private Integer id;
    private Object value;

    public Message(Integer id, Object value) {
        this.id = id;
        this.value = value;
    }

    public Integer getId() {
        return id;
    }

    public Object getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", value=" + value +
                '}';
    }
}
