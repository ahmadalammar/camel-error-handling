# camel-error-handling
## Introduction:
When we write java code we always tend to focus on business logic without much considering about error handling or exceptions that could occurs suddenly in production.

but what happen is that this errors or exceptions could potentially break our system logic and might potentially lose some messages or transactions in the middle of processing!

we could solve this by adding some try-catch blocks, but this will make our code a bit ugly right ? and sometimes when we have multiple catches blocks it will make our code even hard to read…

so how can we handle exceptions or errors properly in our code and maintaining clean code at the same time ?

Answer is: implementing error handling and DLQ using apache camel.

![DeadLetterChannelSolution](https://user-images.githubusercontent.com/17546520/214173609-e0f26073-98e2-455e-835e-d754cebfbb1e.gif)

## Use Case Diagram:
Lets take the following example Orders System where user first initiate asynchronous call to initiate his order, then order-service will process the payment (by calling banking processor) and if payment success it will start the shipment and trigger a callback to user about the transaction status..

we will focus in this example on how order-service will handle the unexpected errors or exceptions coming from payment-gateway…
![error-handling](https://user-images.githubusercontent.com/17546520/214173423-31fac3fe-1e55-4d3f-bd68-72632a88e409.png)

in the above diagram we have order-service that will try to process payment for the order by calling the banking processor… but sometimes due to high load on banking processor we could receive different kind of errors..

some of these error is connection timeout (its most likely retryable error) and we can retry after few seconds to process payment using exponential retry..but if we retry after certain amount of times (in our example max retry is 3) and it still showing same error then we gave up retrying the message and we will drop the message in DLQ so we can manually retry them later when banking system is up again…

Moreover, if order-service hit some unexpected errors (for example NullPointerException), then this message will be also dropped in DLQ as its not expected to receive this kind of errors and order-service dropped the message in DLQ bcoz it does not know what to do with this kind of errors…

Full article can be found here:
https://medium.com/@ahmadalammar/how-to-properly-handles-errors-and-exceptions-using-apache-camel-1a86b8e47c9f
