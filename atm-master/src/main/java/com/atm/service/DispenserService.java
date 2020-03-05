package com.atm.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atm.domain.Note;

import java.math.BigDecimal;
import java.util.List;

public interface DispenserService {


	void setNextChain(DispenserService nextChain);

	List<Note> dispense(int amount);
}
