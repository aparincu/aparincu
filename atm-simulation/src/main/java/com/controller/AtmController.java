package com.controller;

import com.exception.InsufficientBalanceException;
import com.exception.InsufficientNoteException;
import com.exception.InvalidAmountException;
import com.service.AtmService;
import com.service.ResponseWrapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
public class AtmController {

    @Autowired
    AtmService atmService;

   
    @PostMapping("/withdraw")
    public ResponseEntity<ResponseWrapper> deposit(
            @RequestParam("amount") int amount)
            throws InsufficientNoteException, InvalidAmountException, InsufficientBalanceException {
        return atmService.calculateBank(amount);
    }


    @GetMapping("/checkBalance")
    public ResponseEntity<ResponseWrapper> checkBalance() {
    	System.out.println("intra check");
        return atmService.checkBalance();
    }

}
