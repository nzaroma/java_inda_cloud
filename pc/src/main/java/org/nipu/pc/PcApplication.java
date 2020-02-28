package org.nipu.pc;

import org.nipu.po.order.ProductOrder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.messaging.Message;

import java.util.List;


@SpringBootApplication
@EnableEurekaClient
@EnableBinding(Sink.class)
public class PcApplication {

    public static void main(String[] args) {
        SpringApplication.run(PcApplication.class, args);
    }

    @StreamListener(target = Sink.INPUT)
    public void handleMessage(List<ProductOrder> message) {
        System.out.println(message);
    }

    @StreamListener(target = Sink.INPUT)
    public void handleMessage2(Object message) {
        System.out.println(message);
    }
}
