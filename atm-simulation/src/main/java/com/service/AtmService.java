package com.service;

import com.util.Constant;
import com.repository.DatabaseManager;
import com.util.BankValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;



@Service
public class AtmService {

    private final Logger logger = LoggerFactory.getLogger(AtmService.class);

    @Autowired
    DatabaseManager databaseManager;

    public ResponseEntity<ResponseWrapper> calculateBank(int amount){
        String responseCode;
        String responseDesc;
        String responseStatus;
        ResponseWrapper responseWrapper = new ResponseWrapper();
        try {
            
        	//array(20,20,20,20,20)
        	int[] bankAmounts = databaseManager.getBankAmount();
        	
        	//array(1000,500,100,50,20)
            int[] bankValues = databaseManager.getBankValues();

            //nu se poate scoate mai putin de 20
            BankValidator.validateAmount(amount);
            //verifica daca suma pe care dorim sa o scoatem este mai mica decat totalul din cont
            BankValidator.validateRemainingBalance(amount, bankAmounts, bankValues);

            //cauta combinatii
            //1 0 0 0 0
            //0 2 0 0 0
            //........
            List<int[]> bankList = findBanks(amount, new int[bankAmounts.length], bankAmounts, bankValues, 0);
          
            for(int i= 0;i<bankList.size();i++) {
            	int[] arr = bankList.get(i);
            	for(int j = 0;j<arr.length;j++) {
            		System.out.print("arr = " + arr[j] + " ");
            	}
            	System.out.println(" ");
            }
           // String str = simpleVersion(amount, bankAmounts, bankValues);
            
            //Valideaza in functie de nr de bamcnote disponibile
            int[] selectedBankList = BankValidator.validateRemainingNote(bankAmounts, bankList);
            
            //Face combinatia de bancnote
            //19 20 20 20 20
            int[] updatedBankList = subtractBankAmt(bankAmounts, selectedBankList);
            
            for(int i=0 ;i<updatedBankList.length;i++) {
            	System.out.print("updatedBankList = " +updatedBankList[i] +" ");
            }
            
            //salveaza
            databaseManager.updateBalanceAmt(updatedBankList, bankValues);
            
            responseWrapper.setResponseBody(selectedBankList, bankValues);
            responseCode = Constant.SUCCESS_CODE;
            responseDesc = Constant.SUCCESS;
            responseStatus = Constant.SUCCESS;
        } catch (Exception e){
            logger.error("Got Exception while processing : {}", e.getMessage());
            responseCode = Constant.FAIL_CODE;
            responseDesc = e.getMessage();
            responseStatus = Constant.FAIL;
        }

        responseWrapper.setResponseCode(responseCode);
        responseWrapper.setResponseDesc(responseDesc);
        responseWrapper.setResponseStatus(responseStatus);

        ResponseEntity<ResponseWrapper> response = new ResponseEntity<>(responseWrapper, HttpStatus.OK);
        logger.info("Response {}", response);
        return response;
    }

    public ResponseEntity<ResponseWrapper> checkBalance() {
    	int[] bankAmounts = databaseManager.getBankAmount();
        int[] bankValues = databaseManager.getBankValues();
        ResponseWrapper responseWrapper = new ResponseWrapper();
     
        responseWrapper.setResponseBody(bankAmounts, bankValues);

        responseWrapper.setResponseCode(Constant.SUCCESS_CODE);

        responseWrapper.setResponseDesc(Constant.SUCCESS);

        responseWrapper.setResponseStatus(Constant.SUCCESS);

        ResponseEntity<ResponseWrapper> response = new ResponseEntity<>(responseWrapper, HttpStatus.OK);
        logger.info("Response {}", response);
        return response;
    }

    public List<int[]> findBanks(int amount, int[] currentBankAmt, int[] balanceBankAmt, int[] bankValues, int position){
		
		List<int[]> bankCombination = new ArrayList<>();

		int totalAmt = calCurrentTotalAmt(currentBankAmt, bankValues);
		if (totalAmt < amount) {
			for (int i = position; i < currentBankAmt.length; i++) {
				if (balanceBankAmt[i] > currentBankAmt[i]) {
					int newCurrentBankAmt[] = currentBankAmt.clone();
					newCurrentBankAmt[i]++;
					List<int[]> resultList = findBanks(amount, newCurrentBankAmt, balanceBankAmt, bankValues, i);
					if (resultList != null) {
						bankCombination.addAll(resultList);
					}
				}
			}
		} else if (totalAmt == amount) {
			bankCombination.add(currentBankAmt);
		}
		return bankCombination;
		  	
    }
    
    
	/*
	 * public String simpleVersion(int amount, int[] currentBankAmt,int[]
	 * bankValues) { for(int i = 0;i<currentBankAmt.length;i++) { if(amount %
	 * bankValues[i] ==0) { int k = amount/bankValues[i]; currentBankAmt[i] =
	 * currentBankAmt[i]-k;
	 * System.out.println("currentBankAmt[i] = "+currentBankAmt[i]);
	 * databaseManager.updateBalanceAmt(currentBankAmt[i]); } break; }
	 * 
	 * String s = "Withdrow = "+ amount; return s; }
	 */

    public int[] subtractBankAmt(int[] balanceBanks, int[] banks){
        for (int i = 0; i < banks.length; i++){
            balanceBanks[i] -= banks[i];
        }
        return balanceBanks;
    }

    public int calCurrentTotalAmt(int[] bankAmounts, int[] bankValues){
        int totalAmt = 0;
        for (int i = 0; i< bankAmounts.length; i++){
            totalAmt += bankAmounts[i]*bankValues[i];
        }
        return totalAmt;
    }
}
