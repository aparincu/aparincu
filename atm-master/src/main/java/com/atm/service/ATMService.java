package com.atm.service;

import com.atm.domain.Cash;
import com.atm.domain.Note;
import com.atm.enums.Denomination;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ATMService {

	int notesAvailable(Denomination p);

	Boolean initialiseMachine(Cash p);

	List<Note> withDraw(BigDecimal p);

	BigDecimal availableFunds();

	List<Note> loadMoney(Denomination denomination, BigDecimal amount);

	 Boolean checkCases(BigDecimal amount);

	Boolean checkAmount(List<Note> money);
	
	String simpleATM(int operation, int amount);

	String simpleATM(int operation);
	
	
}
