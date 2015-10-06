package de.schaeffer.telecash.model;

/*
 * #%L
 * de.metas.banking.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.math.BigDecimal;

import org.compiere.model.I_C_BPartner;
import org.compiere.model.MPayment;

/**
 * Simple java class (pojo) to hold the credit card transaction data.
 * 
 * @author Karsten Thiemann, kt@schaeffer-ag.de
 */
public class CreditCardTransactionData {

	/** transaction type purchase */
	public static final String TRANSACTION_CC_PURCHASE = "CCPurchase";

	/** transaction type authorization */
	public static final String TRANSACTION_CC_AUTHORIZATION = "CCAuthorization";

	/** transaction type capture (of an authorization) */
	public static final String TRANSACTION_CC_CAPTURE = "CCCapture";

	/** transaction type credit*/
	public static final String TRANSACTION_CC_CREDIT = "CCCredit";

	/** transaction type reversal */
	public static final String TRANSACTION_REVERSAL = "Reversal";

	/** transaction type refund (all or part) */
	public static final String TRANSACTION_REFUND = "Refund";

	/** transaction type reconcilation */
	public static final String TRANSACTION_RECONCILIATION = "Reconciliation";

	/** transaction amount */
	private BigDecimal amount;

	/** ISO currency code */
	private String currency;

	/** credit card expiry yyyy/mm */
	private String ccExpiry;

	/** credit card validation number */
	private String ccValidationNumber;

	/** credit card number */
	private String ccNumber;

	/** bank code */
	private String bankCode;

	/** customer id */
	private String customerId;

	/** transaction id */
	private String transactionId;

	/** transaction type */
	private String transactionType;
	
	/** authentication token */
	private String authToken;
	
	/** id to reverse transaction (telecash: tcphReversalSequenceNo/tcphResultSequenceNo, 
	 * payment: Orig_TrxID, onlinepaymenthistory: XX_ResultSequenceNo */
	private String reversalSequenceNo;


	/**
	 * Constructor.
	 * 
	 * @param payment
	 * @param transactionId
	 * @param transactionType
	 * @throws Exception
	 */
	public CreditCardTransactionData(MPayment payment, String transactionId, String transactionType)
			throws Exception {
		this.amount = payment.getPayAmt();
		this.currency = payment.getC_Currency().getISO_Code();
		String expMonth = payment.getCreditCardExpMM()<10?("0"+payment.getCreditCardExpMM()):(""+payment.getCreditCardExpMM());
		int expYear = payment.getCreditCardExpYY()<1000?(payment.getCreditCardExpYY()+2000):payment.getCreditCardExpYY();
		this.ccExpiry = expYear + expMonth; 
		this.ccValidationNumber = payment.getCreditCardVV();
		this.ccNumber = payment.getCreditCardNumber();
		if (this.ccNumber!=null) {
			this.ccNumber = this.ccNumber.replaceAll("\\D", "");
		}
		this.bankCode = payment.getRoutingNo();
		this.customerId = payment.getC_BPartner_ID()+"";
		I_C_BPartner bp = payment.getC_BPartner();
		if(bp!=null) {
			this.customerId = bp.getValue();
		}
		
		this.transactionId = transactionId;
		this.transactionType = transactionType;
		this.authToken = payment.getR_AuthCode();
		this.reversalSequenceNo = payment.getOrig_TrxID();
	}
	
	/**
	 * Constructor. Sets all values to null or "".
	 * 
	 * @param payment
	 * @param transactionId
	 * @param transactionType
	 * @throws Exception
	 */
	public CreditCardTransactionData(String transactionId, String transactionType)
			throws Exception {
		this.transactionId = transactionId;
		this.transactionType = transactionType;
		this.amount = null;
		this.currency = "";
		this.ccExpiry = "";
		this.ccValidationNumber = "";
		this.ccNumber = "";
		this.bankCode = "";
		this.customerId = "";
		this.authToken = "";
		this.reversalSequenceNo = "";
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getCcExpiry() {
		return ccExpiry;
	}

	public void setCcExpiry(String ccExpiry) {
		this.ccExpiry = ccExpiry;
	}

	public String getCcValidationNumber() {
		return ccValidationNumber;
	}

	public void setCcValidationNumber(String ccValidationNumber) {
		this.ccValidationNumber = ccValidationNumber;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public String getCcNumber() {
		return ccNumber;
	}

	public void setCcNumber(String ccNumber) {
		this.ccNumber = ccNumber;
	}
	
	public String getAuthToken() {
		return authToken;
	}

	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}

	public String getReversalSequenceNo() {
		return reversalSequenceNo;
	}

	public void setReversalSequenceNo(String reversalSequenceNo) {
		this.reversalSequenceNo = reversalSequenceNo;
	}
}
