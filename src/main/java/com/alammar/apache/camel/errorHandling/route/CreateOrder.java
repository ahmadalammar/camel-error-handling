package com.alammar.apache.camel.errorHandling.route;

import com.alammar.apache.camel.errorHandling.processor.PaymentProcessor;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import java.net.SocketTimeoutException;

@Slf4j
@Component
public class CreateOrder extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        // unexpected errors will be dropped here in DLQ...
        // you can drop the message to SQS queue or DB...but for simplicity for now i will just log it!
        errorHandler(deadLetterChannel("log:dead?level=ERROR"));

        // Retry SocketTimeoutException 3 times before u drop in DLQ !
        onException(SocketTimeoutException.class)
                .maximumRedeliveries(3)
                .useExponentialBackOff()
                .backOffMultiplier(2)
                .log("Message Exhausted after 3 retries... proceed to drop message in DLQ....")
                .log("log:dead?level=ERROR");

        from("direct:createOrder")
                .log("Create Order Received!")
                .process(new PaymentProcessor()::process)
                .log("Order Created Successfully!");
    }
}
