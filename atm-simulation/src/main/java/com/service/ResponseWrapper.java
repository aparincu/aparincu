package com.service;

import com.model.Bank;
import com.util.BankMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Service
public class ResponseWrapper {

	private List<Bank> banks;
	private String responseCode;
	private String responseDesc;
	private String responseStatus;

	public ResponseWrapper() {
		this.banks = new ArrayList<>();
	}

	public void setResponseBody(int[] banks, int[] bankValues) {
		
		
		for (int i = 0; i < banks.length; i++) {
			if (banks[i] > 0) {
				Bank bank = new Bank(BankMapper.getBankType(bankValues[i]), bankValues[i]);
				bank.setAmount(banks[i]);
				this.banks.add(bank);
			}
		}
		 
		  
		 
		
		//with stream
		
		/*
		 * List<Integer> listBanks =
		 * IntStream.of(banks).boxed().collect(Collectors.toCollection(ArrayList::new));
		 * 
		 * Stream<Integer> listBanksFilter = listBanks.stream().filter(i ->
		 * (listBanks.get(i)>0)); listBanksFilter.forEach(i -> { Bank bank = new
		 * Bank(BankMapper.getBankType(bankValues[i]), bankValues[i]);
		 * bank.setAmount(banks[i]); this.banks.add(bank); });
		 */
		 
		 
		 
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	public void setResponseDesc(String responseDesc) {
		this.responseDesc = responseDesc;
	}

	public void setResponseStatus(String responseStatus) {
		this.responseStatus = responseStatus;
	}

	public List<Bank> getResponseBody() {
		return banks;
	}

	public String getResponseCode() {
		return responseCode;
	}

	public String getResponseDesc() {
		return responseDesc;
	}

	public String getResponseStatus() {
		return responseStatus;
	}

	@Override
	public String toString() {
		return "ResponseWrapper:{" + " resultCode=" + getResponseCode() + " resultStatus=" + getResponseStatus()
				+ " resultDesc=" + getResponseDesc() + " resultBody=" + getResponseBody() + " }";
	}
}
