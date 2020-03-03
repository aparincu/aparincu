# ATM Simulator
This is a Spring Boot project simulating ATM machine's dispensing logic

### About API
The ATM Simulator provides two services.
* Withdraw service - To withdraw money from the ATM Simulator
* Check balance service - To check current amount of each type of notes the ATM Simulator currently has

### Technologies
* [Maven](https://maven.apache.org/) 
* [Spring Boot](https://projects.spring.io/spring-boot/) 
* [H2](http://www.h2database.com/html/main.html) - A Java in-memory database providing JDBC API
* [Mockito](http://site.mockito.org/) - Write beautiful unitests with a clean & readable with simple API



* Then prepare project dependencies with
```
$ mvn clean install
```

* Run project using command
```
$ mvn spring-boot:run
```

The project should start on port 8080




You can request dispensing money via [Postman](https://www.getpostman.com) or by opening the following URL in a browser
```
http://localhost:8080/atm-simulator/withdraw?amount=<AMOUNT>
```
while \<AMOUNT\> indicates money you want to dispense from ATM Simulator
<br/>The response should look like
```
{
    "responseCode": "0",
    "responseDesc": "SUCCESS",
    "responseStatus": "SUCCESS",
    "responseBody": [
        {
            "type": "ONE_HUNDRED",
            "value": 100,
            "amount": 1
        },
        {
            "type": "FIFTY",
            "value": 50,
            "amount": 1
        }
    ]
}
```
You can check money via Postman
```
http://localhost:8080/atm-simulator/checkBalance
```

#### Possible API response

* Check balance success
```
{
    "responseCode": "0",
    "responseDesc": "SUCCESS",
    "responseStatus": "SUCCESS",
    "responseBody": [
        {
            "type": "THOUSAND",
            "value": 1000,
            "amount": 20
        },
        {
            "type": "FIVE_HUNDRED",
            "value": 500,
            "amount": 20
        },
        {
            "type": "ONE_HUNDRED",
            "value": 100,
            "amount": 19
        },
        {
            "type": "FIFTY",
            "value": 50,
            "amount": 19
        },
        {
            "type": "TWENTY",
            "value": 20,
            "amount": 18
        }
    ]
}
```

* Withdrow success
```
{
    "responseCode": "0",
    "responseDesc": "SUCCESS",
    "responseStatus": "SUCCESS",
    "responseBody": [
        {
            "type": "ONE_HUNDRED",
            "value": 100,
            "amount": 1
        },
        {
            "type": "FIFTY",
            "value": 50,
            "amount": 1
        }
    ]
}
```

* Insufficient balance - Total balance of ATM Simulator is less then needed amount
```
{
    "responseCode": "1",
    "responseDesc": "Remaining balance less than dispensed amount",
    "responseStatus": "FAIL",
    "responseBody": []
}
```

* Invalid amount - Needed amount is less than minimum value of the note in ATM Simulator(which, in this case, is 20)
```
{
    "responseCode": "1",
    "responseDesc": "Amount less than min amount",
    "responseStatus": "FAIL",
    "responseBody": []
}
```

* Insufficient note - ATM Simulator doesn't have enough notes needed by requested amount or the simulator cannot find appropriate combination of notes to fulfill requested amount
```
{
    "responseCode": "1",
    "responseDesc": "Insufficient note number. Try dispensing a different amount.",
    "responseStatus": "FAIL",
    "responseBody": []
}

