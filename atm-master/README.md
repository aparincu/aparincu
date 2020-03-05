
# ATM Simulation

 March 2020

##### Implements Feature Set

1. Device has a supply of bank notes

2. Device can be asked how many of each type of note it currently has

3. Device can be initialised, but only once.

4. Notes can be added

5. Notes can be withdrawn

6. $20 and $50 notes are supported

7. If a requested withdrawal cannot be met a message will be provided

8. Dispensing money reduces the amount of money remaining

9. If a requested withdrawal cannot be met the amount of money remaining is unchanged

10. Data is persisted (in a H2 in memory database which can be swapped for permanent persistence for other environments
by configuration.)

##Technologies

- Java 1.8
- JPA with hibernate provider
- Spring MVC, Data, Boot
- Maven
- H2


## URLS

### Local

http://localhost:8080

## Usage

### User Interface

The application starts at the Initialise Automatic Teller Machine screen. After this is used to load money, 
you are taken to the Automatic Teller Ready screen, where you can use the drop down buttons to perform 
various tasks. The link in the top left hand corner will take you back to the first screen. 

### API Usage

	   ##SimpeATM:
	   	   
	   Choose 0 for Initialise;
	   GET http://localhost:8080/api/simpleATM/0/{amount}
	   Choose 1 for Withdraw;
	   GET http://localhost:8080/api/simpleATM/1/{amount}
	   Choose 2 for Deposit;
	   GET http://localhost:8080/api/simpleATM/2/{amount}
	   Choose 3 for Check Balance;
	   GET http://localhost:8080/api/simpleATM/3/
	   Choose 4 for EXIT;
	   GET http://localhost:8080/api/simpleATM/4/	


	   ##API for simulation with TWENTY and FIFTY notes:
	   
	   GET  http://localhost:8080/api/help/money   
	   Returns an example of the JSON that needs to be sent to initialise the ATM
  
	   Content-Type: application/json
	   POST  http://localhost:8080/api/initialise  
	   See capture.jpg
	   Body   Initialises the ATM with specified amount unless it is already initialised
	   
	   
	   After using the UI to initialise the ATM you can then withdraw money with
       GET http://localhost:8080/api/chain/{amount} 
	   
	   GET http://localhost:8080/api/withdraw/{amount}  
	   Returns a Cash object containing a list of Money objects
	    e.g. {"money":[{"type":"FIFTY","number":1},{"type":"TWENTY","number":3}]}
	   
	   PUT http://localhost:8080/api/load/{type}/{amount}  
	   Type can be either 20 or 50, amount is the number of notes to load. 
	   Responds with confirmation of the number and type of notes loaded e.g 20/20 would respond with
	    {"money":[{"type":"FIFTY","number":0},{"type":"TWENTY","number":20}]}
	   
	   GET http://localhost:8080/api/check/{type}  
	   Type can be either 20 or 50. Response is the number avialable.



## Notes

1. Although the user interface is basic, you should get good re-sizing results as Bootstrap gives you good mobile-first
 features out of the box.
2. Global Error handling has not been implemented, needs more tests, there are also some hard-coded strings that need
 to be moved, either to property files, loaded by the model or from more interactive UI. There is duplicate code in 
 the 2 controllers that needs to be refactored.
3. The cash dispensing algorithm needs to be replaced with a better more succinct one.
4. Dealing with currency - because we are dealing with whole numbers using BigDecimal is probably overkill in this
 instance. I'm thinking the Java Money and Currency API is worth a look if extending functionality.
5. I also added a RestController API controller serving up JSON data - using ResponseBody 
and Jackson. If I had have thought of it earlier I would have integrated the 2 controllers more closely.
6. To build this project you need Maven. To run this project you need Java 8.

### Test Driven Development

Yes, I did. You'll notice that the tests (and the scenarios that inform them) match the feature set above quite closely.

I used Spock because it combines JUnit and Mocking capabilities, is 100% JUnit compatible, you can use Groovy, and
it has a nice behaviour driven development style.



 











