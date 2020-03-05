package com.atm.service;


import com.atm.domain.Cash;
import com.atm.domain.Note;
import com.atm.enums.Denomination;
import com.atm.repository.NoteRepository;
import com.atm.service.ATMService;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


/**
 * Created by pshields on 10/11/2016.
 *
 * This is an integration test as we are loading some of the Spring context to
 * get access to services, repositories and an in memory database. In a full
 * application we would have a separate integration properties file which would
 * be used to load the in memory database and run these tests every night.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class ATMServiceTest {

	@Autowired
	NoteRepository noteRepository;

	@Autowired
	ATMService atmService;

	@Test
	public void withDrawTest() {
		  List<Note> notes = this.makeCashBox(1,1); 
		  Cash cashBox = new Cash(notes);
		  Boolean success = atmService.initialiseMachine(cashBox); 
		  BigDecimal withdrawalRequest = new BigDecimal(300); 
		  List<Note> result =atmService.withDraw(withdrawalRequest);
		  assertEquals(atmService.notesAvailable(Denomination.FIFTY),1);
		  assertEquals(atmService.notesAvailable(Denomination.TWENTY),1);
		  assertEquals(success,false);
		  assertEquals(result.get(0).getNumber(),0);
	}

	@Test
	public void initialiseMachineTest() {
		  List<Note> notes = this.makeCashBox(10,12); 
		  Cash cashBox = new Cash(notes);
		  Boolean success = atmService.initialiseMachine(cashBox); 
		  Integer nr1 = atmService.notesAvailable(Denomination.FIFTY); 
		  Integer nr2 =atmService.notesAvailable(Denomination.TWENTY); 		
		  assertEquals(nr1,nr2);
		  assertEquals(success,false);
	}

	@Test
	public void availableFunds() {
		List<Note> notes = makeCashBox(1, 1);
		Cash cashBox = new Cash(notes);
		atmService.initialiseMachine(cashBox);
		BigDecimal availableFunds = atmService.availableFunds();
		availableFunds.equals(BigDecimal.valueOf(70.0));
	}

	public List<Note> makeCashBox(int numberOfFifties, int numberOfTwenties) {
		List<Note> money = new ArrayList<Note>(2);
		Note twenties = new Note(Denomination.TWENTY, numberOfTwenties);
		Note fifties = new Note(Denomination.FIFTY, numberOfFifties);
		money.add(twenties);
		money.add(fifties);
		return money;
	}

}