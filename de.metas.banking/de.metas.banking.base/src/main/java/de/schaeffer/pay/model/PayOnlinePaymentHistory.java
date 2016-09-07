package de.schaeffer.pay.model;

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


import java.sql.ResultSet;
import java.util.Properties;

import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.MPayment;

import de.metas.banking.model.I_C_Payment;

/**
 * Log of online payment transactions (cc, direct debit).
 * 
 * @author Karsten Thiemann, kt@schaeffer-ag.de
 *
 */
public class PayOnlinePaymentHistory extends X_Pay_OnlinePaymentHistory {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3670774494568875260L;

	public PayOnlinePaymentHistory(Properties ctx, int onlinePaymentHistoryID, String trxName) {
		super(ctx, onlinePaymentHistoryID, trxName);
	}

	public PayOnlinePaymentHistory(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	/**
	 * Sets actual values (paymentId, TenderType, TrxType, 
	 * XX_CCPaymentState, Amount) from the given payment.
	 * @param paymentPO MPayment
	 */
	public void setValuesFromPayment(MPayment paymentPO) {
		
		final I_C_Payment payment = InterfaceWrapperHelper.create(paymentPO, I_C_Payment.class); 
		
		setC_Payment_ID(payment.getC_Payment_ID());
		setTenderType(payment.getTenderType());
		setTrxType(payment.getTrxType());
		setCCPaymentState(payment.getCCPaymentState());
		setOrig_TrxID(payment.getOrig_TrxID());
		setAmount(payment.getPayAmt());
		if (payment.getTenderType().equals(MPayment.TENDERTYPE_CreditCard)) {
			String ccno = payment.getCreditCardNumber();

			if (ccno.length() > 4) {
				ccno = ccno.replace(ccno.substring(0,ccno.length()-4),"*****");
			}
			setCreditCardValue(paymentPO.getCreditCardName() + " " + ccno + " " + paymentPO.getCreditCardExpMM() + "\\" + paymentPO.getCreditCardExpYY() );
		} else {
			setCreditCardValue("");
		}
	}
	
	/**
	 * Sets actual values (paymentId, TenderType, TrxType, Amount) from the given payment,
	 * sets the XX_CCPaymentState to error and sets the error message.
	 * @param p_mp
	 */
	public void setValuesFromPayment(MPayment payment, String errorMessage) {
		log.debug("errorMessage: " + errorMessage);
		setValuesFromPayment(payment);
		setCCPaymentState(I_C_Payment.CCPAYMENTSTATE_Error);
		setErrorMsg(errorMessage);
	}

}
