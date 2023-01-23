package com.alammar.apache.camel.errorHandling.processor;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.net.SocketTimeoutException;

@Slf4j
public class PaymentProcessor implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {
        log.info("Processing Payment....");

        // here we will simulate connecting to banking processor and throw SocketTimeoutExceptions, NullPinterExceptions Randomly..
        if (Math.random() > 0.5) {
            if (Math.random() > 0.5) {
                throw new SocketTimeoutException("Failed to connect to payment processor !"); // re-tryable error
            } else {
                throw new NullPointerException("UnExcepted Error Occurs!"); // unexpected error..will be dropped to DLQ!
            }
        } else {
            log.info("Processing Payment Completed!");
        }
    }
}
