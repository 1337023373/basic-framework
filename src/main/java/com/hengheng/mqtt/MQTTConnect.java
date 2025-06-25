package com.hengheng.mqtt;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.CountDownLatch;

/**
 * @Author lkj
 * @Date 2025/6/25 9:44
 * @Version 1.0
 */
@Slf4j
@Component
public class MQTTConnect {
    private MqttClient mqttClient;
    private String HOST = "tcp://10.10.10.242:1883"; //mqtt服务器的地址和端口号
    private final String clientId = "DC" + (int) (Math.random() * 100000000);

    /**
     * 客户端connect连接mqtt服务器
     *
     * @param userName     用户名
     * @param password     密码
     * @param mqttCallback 回调函数
     **/
    public void setMqttClient(String userName, String password, MqttCallback mqttCallback) throws MqttException {
        //    mqtt连接参数设置
        MqttConnectOptions options = mqttConnectOptions(userName, password);
        // 创建 mqttClient 实例
        mqttClient = new MqttClient(HOST, clientId, new MemoryPersistence());

        // 设置回调处理器（连接成功、消息到达、连接丢失等）
        mqttClient.setCallback(mqttCallback);

        // 建立连接
        mqttClient.connect(options);
    }

    private MqttConnectOptions mqttConnectOptions(String userName, String password) throws MqttException {
        mqttClient = new MqttClient(HOST, clientId, new MemoryPersistence());
        MqttConnectOptions options = new MqttConnectOptions();
        options.setUserName(userName);
        options.setPassword(password.toCharArray());
        options.setConnectionTimeout(10);
        options.setAutomaticReconnect(true);//默认：false
        options.setCleanSession(false);//默认：true
        return options;
    }

    /**
     * @param
     * @return
     * @description 关闭mqtt连接
     * @author lkj
     * @date 2025/6/25
     */
    public void closer(){
        if (mqttClient != null) {
            try {
                if (mqttClient.isConnected()) {
                    mqttClient.disconnect(); // 断开连接
                }
                mqttClient.close(); // 释放资源
                log.info("MQTT 客户端已成功关闭");
            } catch (MqttException e) {
                log.error("关闭 MQTT 客户端失败: {}", e.getMessage(), e);
            }
        }
    }

    /**
     * 向某个主题发布消息 默认qos：1
     *
     * @param topic:发布的主题
     * @param msg：发布的消息
     */
    public void pub(String topic, MqttMessage msg) {
        try {
            if (!mqttClient.isConnected()) {
                log.warn("MQTT 客户端未连接，跳过发布：{}", topic);
                return;
            }

            MqttTopic mqttTopic = mqttClient.getTopic(topic);
            MqttDeliveryToken token = mqttTopic.publish(msg);
            token.waitForCompletion(); // 可改为带超时或去掉
            log.info("MQTT 消息已发布: topic={}, payload={}", topic, new String(msg.getPayload()));
        } catch (MqttException e) {
            log.error("MQTT 发布失败，topic={}, 错误={}", topic, e.getMessage(), e);
        }
    }


    /**
     * 向某个主题发布消息
     *
     * @param topic: 发布的主题
     * @param msg:   发布的消息
     * @param qos:   消息质量    Qos：0、1、2
     */
    public void pub(String topic, String msg, int qos) throws MqttException {
        if (!mqttClient.isConnected()) {
            throw new MqttException(MqttException.REASON_CODE_CLIENT_NOT_CONNECTED);
        }
        MqttMessage mqttMessage = new MqttMessage(msg.getBytes());
        mqttMessage.setQos(qos);
        mqttMessage.setRetained(false);
        mqttClient.getTopic(topic).publish(mqttMessage).waitForCompletion();
    }

    /**
     * 订阅某一个主题 ，此方法默认的的Qos等级为：1
     *
     * @param topic 主题
     */
    public void sub(String topic) throws MqttException {
        mqttClient.subscribe(topic);
    }

    /**
     * 订阅某一个主题，可携带Qos
     *
     * @param topic 所要订阅的主题
     * @param qos   消息质量：0、1、2
     */
    public void sub(String topic, int qos) throws MqttException {
        mqttClient.subscribe(topic, qos);
    }

    /**
     * main函数自己测试用
     */
    //public static void main(String[] args) throws Exception {
    //    CountDownLatch latch = new CountDownLatch(1);
    //
    //    MQTTConnect mqttConnect = new MQTTConnect();
    //
    //    // 设置回调
    //    mqttConnect.setMqttClient("admin", "public", new MqttCallback() {
    //        @Override
    //        public void connectionLost(Throwable cause) {
    //            System.out.println("连接断开: " + cause.getMessage());
    //        }
    //
    //        @Override
    //        public void messageArrived(String topic, MqttMessage message) {
    //            System.out.println("➡️ 收到消息 | topic: " + topic + ", payload: " + new String(message.getPayload()));
    //            latch.countDown(); // 收到消息后退出主线程
    //        }
    //
    //        @Override
    //        public void deliveryComplete(IMqttDeliveryToken token) {
    //            System.out.println("✅ 消息发送完成");
    //        }
    //    });
    //
    //    String topic = "com/iot/init";
    //    String payload = "📨 Hello MQTT - " + UUID.randomUUID();
    //
    //    mqttConnect.sub(topic); // 订阅
    //    Thread.sleep(500);      // 等待订阅建立（必要）
    //
    //    mqttConnect.pub(topic, payload, 1); // 发布消息，QoS 1
    //    System.out.println("🚀 消息已发布: " + payload);
    //    // 添加钩子确保关闭连接
    //    Runtime.getRuntime().addShutdownHook(new Thread(() -> {
    //        try {
    //            mqttConnect.closer();
    //            System.out.println("🛑 MQTT 客户端已关闭");
    //        } catch (Exception e) {
    //            e.printStackTrace();
    //        }
    //    }));
    //
    //    // 等待接收到消息或超时退出
    //    if (!latch.await(10, java.util.concurrent.TimeUnit.SECONDS)) {
    //        System.out.println("⏱️ 超时未收到消息");
    //    }
    //
    //    mqttConnect.closer();
    //}
    public static void main(String[] args) throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        MQTTConnect mqttConnect = new MQTTConnect();

        mqttConnect.setMqttClient("admin", "public", new MqttCallback() {
            @Override
            public void connectionLost(Throwable cause) {
                System.err.println("🚫 连接断开: " + cause.getMessage());
            }

            @Override
            public void messageArrived(String topic, MqttMessage message) {
                System.out.println("📨 收到消息 | topic: " + topic + " | payload: " + new String(message.getPayload()));
                latch.countDown(); // 收到消息就结束主线程
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {
                System.out.println("📤 消息已送达");
            }
        });

        String topic = "com/iot/init";
        mqttConnect.sub(topic);

        Thread.sleep(1000); // 等待订阅完成

        String payload = "👋 Hello from client " + UUID.randomUUID();
        mqttConnect.pub(topic, payload,1);

        // 添加关闭钩子
        Runtime.getRuntime().addShutdownHook(new Thread(mqttConnect::closer));

        // 保持连接运行（长连接核心）
        latch.await(); // 等待直到接收到一条消息再退出
    }

}
