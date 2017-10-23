/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved. *
 * This program is free software; you can redistribute it and/or modify it *
 * under the terms version 2 of the GNU General Public License as published *
 * by the Free Software Foundation. This program is distributed in the hope *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. *
 * See the GNU General Public License for more details. *
 * You should have received a copy of the GNU General Public License along *
 * with this program; if not, write to the Free Software Foundation, Inc., *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA. *
 * For the text or an alternative of this public license, you may reach us *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA *
 * or via info@compiere.org or http://www.compiere.org/license.html *
 *****************************************************************************/
package de.metas.callout;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.math.BigDecimal;

import org.adempiere.ad.callout.api.ICalloutField;
import org.adempiere.util.Services;
import org.compiere.model.CalloutEngine;
import org.compiere.model.I_C_Payment;
import org.compiere.util.Env;

import de.metas.banking.interfaces.I_C_BankStatementLine_Ref;
import de.metas.banking.model.IBankStatementLineOrRef;
import de.metas.banking.model.I_C_BankStatementLine;
import de.metas.banking.payment.IBankStatmentPaymentBL;
import de.metas.currency.ConversionType;
import de.metas.currency.ICurrencyBL;
import de.metas.currency.ICurrencyConversionContext;
import de.metas.currency.ICurrencyRate;
import lombok.NonNull;

public class CalloutBankStatement extends CalloutEngine
{
	/**
	 * Called on C_BankStatementLine: C_Payment_ID
	 */
	public String payment(final ICalloutField calloutField)
	{
		final I_C_BankStatementLine line = calloutField.getModel(I_C_BankStatementLine.class);
		setPaymentDetails(line);
		amount(calloutField);// Recalculate Amounts
		return NO_ERROR;
	} // payment

	/**
	 * Called on C_BankStatementLine: "ChargeAmt", "InterestAmt", "StmtAmt", "DiscountAmt", "WriteOffAmt", "OverUnderAmt"
	 */
	public String amount(final ICalloutField calloutField)
	{
		if (isCalloutActive())
			return "";

		final I_C_BankStatementLine bsl = calloutField.getModel(I_C_BankStatementLine.class);

		// Get Stmt & Trx & Discount & WriteOff & OverUnder
		final BigDecimal stmt = bsl.getStmtAmt();
		final BigDecimal trx = bsl.getTrxAmt();
		final BigDecimal discount = bsl.getDiscountAmt();
		final BigDecimal writeOff = bsl.getWriteOffAmt();
		final BigDecimal overUnder = bsl.isOverUnderPayment() ? bsl.getOverUnderAmt() : BigDecimal.ZERO;

		BigDecimal bd = stmt.subtract(trx).subtract(discount).subtract(writeOff).subtract(overUnder);

		final String columnName = calloutField.getColumnName();
		// Charge - calculate Interest
		if (columnName.equals(I_C_BankStatementLine.COLUMNNAME_ChargeAmt))
		{
			BigDecimal charge = bsl.getChargeAmt();
			if (charge == null)
				charge = BigDecimal.ZERO;
			bd = bd.subtract(charge);
			bsl.setInterestAmt(bd);
		}
		// Calculate Charge
		else if (columnName.equals(I_C_BankStatementLine.COLUMNNAME_InterestAmt))
		{
			BigDecimal interest = bsl.getInterestAmt();
			bd = bd.subtract(interest);
			bsl.setChargeAmt(bd);
		}
		// Put it back to trxAmt
		else
		{
			bsl.setTrxAmt(trx.add(bd));
		}
		return NO_ERROR;
	}

	/**
	 * C_BankStatementLine_Ref: "C_Payment_ID"
	 */
	public String paymentRef(final ICalloutField calloutField)
	{
		final I_C_BankStatementLine_Ref ref = calloutField.getModel(I_C_BankStatementLine_Ref.class);
		setPaymentDetails(ref);
		return NO_ERROR;
	}

	private void setPaymentDetails(IBankStatementLineOrRef line)
	{
		I_C_Payment payment = null;
		if (line.getC_Payment_ID() > 0)
		{
			payment = line.getC_Payment();
		}

		Services.get(IBankStatmentPaymentBL.class).setC_Payment(line, payment);
	}

	public String invoice(final ICalloutField calloutField)
	{
		final Integer invoiceId = (Integer)calloutField.getValue();
		if (isCalloutActive() // assuming it is resetting value
				|| invoiceId == null || invoiceId.intValue() <= 0)
			return NO_ERROR;

		final IBankStatementLineOrRef lineOrRef;
		if (I_C_BankStatementLine_Ref.Table_Name.equals(calloutField.getTableName()))
		{
			lineOrRef = calloutField.getModel(I_C_BankStatementLine_Ref.class);
		}
		else if (I_C_BankStatementLine.Table_Name.equals(calloutField.getTableName()))
		{
			lineOrRef = calloutField.getModel(I_C_BankStatementLine.class);
		}
		else
		{
			throw new IllegalStateException("" + calloutField.getTableName() + " is not supported");
		}

		BankStatementHelper.setBankStatementLineOrRefFieldsForInvoice(lineOrRef, invoiceId);

		return NO_ERROR;
	}

	
	
	/**
	 * Called by C_BankStatementLine_Ref: "Amount", "C_Currency_ID", "DiscountAmt", "IsOverUnderPayment", "OverUnderAmt", "WriteOffAmt"
	 * 
	 */
	public String paymentAmounts(final ICalloutField calloutField)
	{
		if (isCalloutActive()) // assuming it is resetting value
		{
			return "";
		}

		final IBankStatementLineOrRef lineOrRef;
		if (I_C_BankStatementLine_Ref.Table_Name.equals(calloutField.getTableName()))
		{
			lineOrRef = calloutField.getModel(I_C_BankStatementLine_Ref.class);
		}
		else if (I_C_BankStatementLine.Table_Name.equals(calloutField.getTableName()))
		{
			lineOrRef = calloutField.getModel(I_C_BankStatementLine.class);
		}
		else
		{
			throw new IllegalStateException("" + calloutField.getTableName() + " is not supported");
		}

		final int invoiceId = Env.getContextAsInt(calloutField.getCtx(), calloutField.getWindowNo(), I_C_BankStatementLine.COLUMNNAME_C_Invoice_ID);
		final String colName = calloutField.getColumnName();
		
		setBankStatementLineOrRefFieldsForPaymentAmounts(lineOrRef, colName, invoiceId);
		
		return NO_ERROR;
	} // amounts
	
	
	private void setBankStatementLineOrRefFieldsForPaymentAmounts(@NonNull final IBankStatementLineOrRef lineOrRef, @NonNull final String colName, final int invoiceId)
	{
		if (invoiceId <= 0)
		{
			BankStatementHelper.setBankStatementLineOrRefAmountsToZero(lineOrRef);
		}
		else
		{
			BankStatementHelper.setBankStatementLineOrRefAmountsForPayment(lineOrRef, colName, invoiceId);
		}
	}
	
	

	/**
	 * C_BankStatementLine: C_BP_BankAccountTo_ID
	 */
	public String onC_BP_BankAccountTo_ID(final ICalloutField calloutField)
	{
		final I_C_BankStatementLine bsl = calloutField.getModel(I_C_BankStatementLine.class);

		// If user unselected the Bank account To => reset the linked Bank Statement Line
		if (bsl.getC_BP_BankAccountTo_ID() <= 0)
		{
			bsl.setLink_BankStatementLine_ID(0);
			return NO_ERROR;
		}

		return NO_ERROR;
	}

	/**
	 * C_BankStatementLine: Link_BankStatement_ID
	 */
	public String onLink_BankStatement_ID(final ICalloutField calloutField)
	{
		final I_C_BankStatementLine bsl = calloutField.getModel(I_C_BankStatementLine.class);
		if (bsl.getLink_BankStatementLine_ID() <= 0)
		{
			bsl.setCurrencyRate(null); // reset
			return NO_ERROR;
		}

		final org.compiere.model.I_C_BankStatementLine bslFrom = bsl.getLink_BankStatementLine();

		final BigDecimal trxAmtFrom = bslFrom.getTrxAmt();
		final int trxAmtFromCurrencyId = bslFrom.getC_Currency_ID();

		final int trxAmtCurrencyId = bsl.getC_Currency_ID();

		final ICurrencyBL currencyConversionBL = Services.get(ICurrencyBL.class);
		final ICurrencyConversionContext currencyConversionCtx = currencyConversionBL.createCurrencyConversionContext(
				bsl.getValutaDate(),
				ConversionType.Spot,
				bsl.getAD_Client_ID(),
				bsl.getAD_Org_ID());

		final ICurrencyRate currencyRate = currencyConversionBL.getCurrencyRate(currencyConversionCtx, trxAmtFromCurrencyId, trxAmtCurrencyId);
		final BigDecimal trxAmt = currencyRate
				.convertAmount(trxAmtFrom)
				.negate();

		bsl.setStmtAmt(trxAmt);
		bsl.setTrxAmt(trxAmt);
		bsl.setCurrencyRate(currencyRate.getConversionRate());
		bsl.setDiscountAmt(BigDecimal.ZERO);
		bsl.setChargeAmt(BigDecimal.ZERO);
		bsl.setWriteOffAmt(BigDecimal.ZERO);
		bsl.setOverUnderAmt(BigDecimal.ZERO);
		bsl.setIsOverUnderPayment(false);

		return NO_ERROR;
	}

} // CalloutBankStatement
