package com.atm.service;

import java.math.BigDecimal;
import java.util.List;

import com.atm.domain.Note;

public interface ATMChainService {
	List<Note> withDraw(BigDecimal amount);
}
