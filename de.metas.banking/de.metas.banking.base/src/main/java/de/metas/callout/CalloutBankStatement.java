/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                        *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Properties;

import org.adempiere.currency.ICurrencyConversionContext;
import org.adempiere.currency.ICurrencyRate;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.DBException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ICurrencyConversionBL;
import org.adempiere.util.Services;
import org.adempiere.util.time.SystemTime;
import org.compiere.model.CalloutEngine;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.model.I_C_Payment;
import org.compiere.model.MCurrency;
import org.compiere.util.DB;
import org.compiere.util.Env;

import de.metas.banking.interfaces.I_C_BankStatementLine_Ref;
import de.metas.banking.model.IBankStatementLineOrRef;
import de.metas.banking.model.I_C_BankStatementLine;
import de.metas.banking.payment.IBankStatmentPaymentBL;

public class CalloutBankStatement extends CalloutEngine
{
	/**
	 * Called on C_BankStatementLine: "ChargeAmt", "InterestAmt", "StmtAmt", "DiscountAmt", "WriteOffAmt", "OverUnderAmt"
	 */
	public String amount(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		if (isCalloutActive())
			return "";

		final I_C_BankStatementLine bsl = InterfaceWrapperHelper.create(mTab, I_C_BankStatementLine.class);

		// Get Stmt & Trx & Discount & WriteOff & OverUnder
		final BigDecimal stmt = bsl.getStmtAmt();
		final BigDecimal trx = bsl.getTrxAmt();
		final BigDecimal discount = bsl.getDiscountAmt();
		final BigDecimal writeOff = bsl.getWriteOffAmt();
		final BigDecimal overUnder = bsl.isOverUnderPayment() ? bsl.getOverUnderAmt() : Env.ZERO;

		BigDecimal bd = stmt.subtract(trx).subtract(discount).subtract(writeOff).subtract(overUnder);

		// Charge - calculate Interest
		if (mField.getColumnName().equals(I_C_BankStatementLine.COLUMNNAME_ChargeAmt))
		{
			BigDecimal charge = bsl.getChargeAmt();
			if (charge == null)
				charge = Env.ZERO;
			bd = bd.subtract(charge);
			bsl.setInterestAmt(bd);
		}
		// Calculate Charge
		else if (mField.getColumnName().equals(I_C_BankStatementLine.COLUMNNAME_InterestAmt))
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
		return "";
	} // amount

	/**
	 * Called on C_BankStatementLine: C_Payment_ID
	 */
	public String payment(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		final I_C_BankStatementLine line = InterfaceWrapperHelper.create(mTab, I_C_BankStatementLine.class);
		setPaymentDetails(line);
		amount(ctx, WindowNo, mTab, mField, value);// Recalculate Amounts
		return "";
	} // payment

	/**
	 * C_BankStatementLine_Ref: "C_Payment_ID"
	 */
	public String paymentRef(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		final I_C_BankStatementLine_Ref ref = InterfaceWrapperHelper.create(mTab, I_C_BankStatementLine_Ref.class);
		setPaymentDetails(ref);
		return "";
	}

	public String invoice(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		final Integer C_Invoice_ID = (Integer)value;
		if (isCalloutActive() // assuming it is resetting value
				|| C_Invoice_ID == null || C_Invoice_ID.intValue() <= 0)
			return "";

		Timestamp dateTrx;

		final I_C_BankStatementLine line;
		final I_C_BankStatementLine_Ref ref;
		final IBankStatementLineOrRef lineOrRef;
		if (I_C_BankStatementLine_Ref.Table_Name.equals(mTab.getTableName()))
		{
			line = null;
			ref = InterfaceWrapperHelper.create(mTab, I_C_BankStatementLine_Ref.class);
			lineOrRef = ref;
			dateTrx = ref.getC_BankStatementLine().getStatementLineDate();
		}
		else if (I_C_BankStatementLine.Table_Name.equals(mTab.getTableName()))
		{
			line = InterfaceWrapperHelper.create(mTab, I_C_BankStatementLine.class);
			ref = null;
			lineOrRef = line;
			dateTrx = line.getStatementLineDate();
		}
		else
		{
			throw new IllegalStateException("" + mTab + " is not supported");
		}

		if (dateTrx == null)
		{
			dateTrx = SystemTime.asTimestamp();
		}
		log.fine("DateTrx=" + dateTrx);

		//
		lineOrRef.setDiscountAmt(Env.ZERO);
		lineOrRef.setWriteOffAmt(Env.ZERO);
		lineOrRef.setIsOverUnderPayment(false);
		lineOrRef.setOverUnderAmt(Env.ZERO);
		if (ref != null)
		{
			ref.setTrxAmt(Env.ZERO);
		}
		else if (line != null)
		{
			line.setTrxAmt(Env.ZERO);
			line.setStmtAmt(Env.ZERO);
		}

		int C_InvoicePaySchedule_ID = getC_InvoicePaySchedule_ID(ctx, C_Invoice_ID);
		final InvoiceInfoVO invoiceInfo = fetchInvoiceInfo(C_Invoice_ID, C_InvoicePaySchedule_ID, dateTrx);
		if (invoiceInfo != null)
		{
			final BigDecimal openAmtActual = invoiceInfo.openAmt.subtract(invoiceInfo.discountAmt);
			lineOrRef.setC_BPartner_ID(invoiceInfo.bpartnerId);
			lineOrRef.setC_Currency_ID(invoiceInfo.currencyId);
			if (ref != null)
			{
				ref.setTrxAmt(openAmtActual);
			}
			else if (line != null)
			{
				line.setTrxAmt(openAmtActual);
				line.setStmtAmt(openAmtActual);
			}
			lineOrRef.setDiscountAmt(invoiceInfo.discountAmt);
			lineOrRef.setC_Invoice_ID(C_Invoice_ID);
		}

		return "";
	} // invoice

	/**
	 * Called by C_BankStatementLine_Ref: "Amount", "C_Currency_ID", "DiscountAmt", "IsOverUnderPayment", "OverUnderAmt", "WriteOffAmt"
	 * 
	 */
	public String paymentAmounts(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value, Object oldValue)
	{
		if (isCalloutActive()) // assuming it is resetting value
			return "";

		final int C_Invoice_ID = Env.getContextAsInt(ctx, WindowNo, "C_Invoice_ID");
		String amtColumn = (I_C_BankStatementLine.Table_Name.equals(mTab.getTableName())) ? I_C_BankStatementLine.COLUMNNAME_TrxAmt : I_C_BankStatementLine_Ref.COLUMNNAME_TrxAmt;

		final I_C_BankStatementLine line;
		final I_C_BankStatementLine_Ref ref;
		final IBankStatementLineOrRef lineOrRef;

		BigDecimal PayAmt;
		Timestamp dateTrx = null;
		Timestamp ConvDate = null;

		if (I_C_BankStatementLine_Ref.Table_Name.equals(mTab.getTableName()))
		{
			line = null;
			ref = InterfaceWrapperHelper.create(mTab, I_C_BankStatementLine_Ref.class);
			lineOrRef = ref;
			PayAmt = ref.getTrxAmt();
			dateTrx = ref.getC_BankStatementLine().getStatementLineDate();
		}
		else if (I_C_BankStatementLine.Table_Name.equals(mTab.getTableName()))
		{
			line = InterfaceWrapperHelper.create(mTab, I_C_BankStatementLine.class);
			ref = null;
			lineOrRef = line;
			PayAmt = line.getTrxAmt();
			dateTrx = line.getStatementLineDate();
		}
		else
		{
			throw new IllegalStateException("" + mTab + " is not supported");
		}

		if (dateTrx == null)
			dateTrx = SystemTime.asTimestamp();
		if (ConvDate == null)
			ConvDate = dateTrx;

		// Changed Column
		final String colName = mField.getColumnName();
		if (I_C_BankStatementLine.COLUMNNAME_IsOverUnderPayment.equals(colName) && !lineOrRef.isOverUnderPayment())
		{
			lineOrRef.setOverUnderAmt(Env.ZERO);
		}

		int C_InvoicePaySchedule_ID = getC_InvoicePaySchedule_ID(ctx, C_Invoice_ID);
		final InvoiceInfoVO invoiceInfo = fetchInvoiceInfo(C_Invoice_ID, C_InvoicePaySchedule_ID, dateTrx);
		log.fine("" + invoiceInfo);

		// Get Info from Tab
		BigDecimal InvoiceOpenAmt = invoiceInfo != null ? invoiceInfo.openAmt : Env.ZERO;
		BigDecimal DiscountAmt = lineOrRef.getDiscountAmt();
		BigDecimal WriteOffAmt = lineOrRef.getWriteOffAmt();
		BigDecimal OverUnderAmt = lineOrRef.getOverUnderAmt();
		log.fine("Pay=" + PayAmt + ", Discount=" + DiscountAmt + ", WriteOff=" + WriteOffAmt + ", OverUnderAmt=" + OverUnderAmt);
		final int C_Currency_ID = lineOrRef.getC_Currency_ID();
		final MCurrency currency = MCurrency.get(ctx, C_Currency_ID);
		int C_ConversionType_ID = 0;
		Integer ii = (Integer)mTab.getValue("C_ConversionType_ID"); // TODO: this column is missing?
		if (ii != null)
			C_ConversionType_ID = ii.intValue();
		// Get Currency Rate
		BigDecimal CurrencyRate = Env.ONE;
		if ((invoiceInfo != null && C_Currency_ID > 0 && invoiceInfo.currencyId > 0 && C_Currency_ID != invoiceInfo.currencyId)
				|| colName.equals(I_C_BankStatementLine.COLUMNNAME_C_Currency_ID)
				|| colName.equals("C_ConversionType_ID")) // TODO: this column is missing
		{
			log.fine("InvInfo=" + invoiceInfo + ", PayCurrency=" + C_Currency_ID + ", Date=" + ConvDate + ", Type=" + C_ConversionType_ID);
			if (invoiceInfo != null)
			{
				final ICurrencyConversionBL currencyConversionBL = Services.get(ICurrencyConversionBL.class);
				
				CurrencyRate =  currencyConversionBL.getRate(invoiceInfo.currencyId,
						C_Currency_ID, ConvDate, C_ConversionType_ID,
						lineOrRef.getAD_Client_ID(), lineOrRef.getAD_Org_ID());
			}
			if (CurrencyRate == null || CurrencyRate.signum() == 0)
			{
				throw new AdempiereException("@NoCurrencyConversion@");
			}
			//
			InvoiceOpenAmt = InvoiceOpenAmt.multiply(CurrencyRate).setScale(currency.getStdPrecision(), BigDecimal.ROUND_HALF_UP);
			log.fine("Rate=" + CurrencyRate + ", InvoiceOpenAmt=" + InvoiceOpenAmt);
		}

		// Currency Changed - convert all
		if (colName.equals(I_C_BankStatementLine.COLUMNNAME_C_Currency_ID)
				|| colName.equals("C_ConversionType_ID")) // TODO: missing column
		{
			PayAmt = PayAmt.multiply(CurrencyRate).setScale(currency.getStdPrecision(), BigDecimal.ROUND_HALF_UP);
			mTab.setValue(amtColumn, PayAmt);
			DiscountAmt = DiscountAmt.multiply(CurrencyRate).setScale(currency.getStdPrecision(), BigDecimal.ROUND_HALF_UP);
			lineOrRef.setDiscountAmt(DiscountAmt);
			WriteOffAmt = WriteOffAmt.multiply(CurrencyRate).setScale(currency.getStdPrecision(), BigDecimal.ROUND_HALF_UP);
			lineOrRef.setWriteOffAmt(WriteOffAmt);
			OverUnderAmt = OverUnderAmt.multiply(CurrencyRate).setScale(currency.getStdPrecision(), BigDecimal.ROUND_HALF_UP);
			lineOrRef.setOverUnderAmt(OverUnderAmt);
		}
		// No Invoice - Set Discount, Witeoff, Under/Over to 0
		else if (C_Invoice_ID <= 0)
		{
			lineOrRef.setDiscountAmt(Env.ZERO);
			lineOrRef.setWriteOffAmt(Env.ZERO);
			lineOrRef.setIsOverUnderPayment(false);
			lineOrRef.setOverUnderAmt(Env.ZERO);
		}
		// PayAmt - calculate write off
		else if (colName.equals(amtColumn) && lineOrRef.isOverUnderPayment())
		{
			OverUnderAmt = InvoiceOpenAmt.subtract(PayAmt).subtract(DiscountAmt).subtract(WriteOffAmt);
			lineOrRef.setOverUnderAmt(OverUnderAmt);
		}
		else if (colName.equals(amtColumn))
		{
			WriteOffAmt = InvoiceOpenAmt.subtract(PayAmt).subtract(DiscountAmt).subtract(OverUnderAmt);
			lineOrRef.setWriteOffAmt(WriteOffAmt);
		}
		else if (colName.equals(I_C_BankStatementLine.COLUMNNAME_IsOverUnderPayment))
		{
			if (lineOrRef.isOverUnderPayment())
			{
				OverUnderAmt = InvoiceOpenAmt.subtract(PayAmt).subtract(DiscountAmt);
				lineOrRef.setWriteOffAmt(Env.ZERO);
				lineOrRef.setOverUnderAmt(OverUnderAmt);
			}
			else
			{
				WriteOffAmt = InvoiceOpenAmt.subtract(PayAmt).subtract(DiscountAmt);
				lineOrRef.setWriteOffAmt(WriteOffAmt);
				lineOrRef.setOverUnderAmt(Env.ZERO);
			}
		}
		else
		// calculate PayAmt
		{
			PayAmt = InvoiceOpenAmt.subtract(DiscountAmt).subtract(WriteOffAmt).subtract(OverUnderAmt);
			mTab.setValue(amtColumn, PayAmt);
		}
		return "";
	} // amounts

	public String bankAccountTo(Properties ctx, int WindowNo, GridTab mTab,
			GridField mField, Object value, Object oldValue)
	{
		final I_C_BankStatementLine bsl = InterfaceWrapperHelper.create(mTab, I_C_BankStatementLine.class);
		if (bsl.getC_BP_BankAccountTo_ID() <= 0)
			return "";

		bsl.setTrxAmt(bsl.getStmtAmt());
		bsl.setChargeAmt(BigDecimal.ZERO);
		bsl.setWriteOffAmt(BigDecimal.ZERO);
		bsl.setDiscountAmt(BigDecimal.ZERO);
		bsl.setOverUnderAmt(BigDecimal.ZERO);
		bsl.setIsOverUnderPayment(false);

		return NO_ERROR;
	}

	/**
	 * C_BankStatementLine: C_BP_BankAccountTo_ID
	 */
	public String onC_BP_BankAccountTo_ID(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value, Object oldValue)
	{
		I_C_BankStatementLine bsl = InterfaceWrapperHelper.create(mTab, I_C_BankStatementLine.class);
		if (bsl.getC_BP_BankAccountTo_ID() <= 0)
		{
			bsl.setLink_BankStatementLine_ID(0);
			return "";
		}
		return "";
	}

	/**
	 * C_BankStatementLine: Link_BankStatement_ID
	 */
	public String onLink_BankStatement_ID(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value, Object oldValue)
	{
		final I_C_BankStatementLine bsl = InterfaceWrapperHelper.create(mTab, I_C_BankStatementLine.class);
		if (bsl.getLink_BankStatementLine_ID() <= 0)
		{
			return NO_ERROR;
		}

		final org.compiere.model.I_C_BankStatementLine bslFrom = bsl.getLink_BankStatementLine();

		final BigDecimal trxAmtFrom = bslFrom.getTrxAmt();
		final int trxAmtFromCurrencyId = bslFrom.getC_Currency_ID();

		final int trxAmtCurrencyId = bsl.getC_Currency_ID();

		final ICurrencyConversionBL currencyConversionBL = Services.get(ICurrencyConversionBL.class);
		final ICurrencyConversionContext currencyConversionCtx = currencyConversionBL.createCurrencyConversionContext(
				bsl.getValutaDate(),
				ICurrencyConversionBL.DEFAULT_ConversionType_ID,
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

	private static int getC_InvoicePaySchedule_ID(Properties ctx, int C_Invoice_ID)
	{
		// TODO: refactor
		int C_InvoicePaySchedule_ID = 0;
		if (Env.getContextAsInt(ctx, Env.WINDOW_INFO, Env.TAB_INFO, "C_Invoice_ID") == C_Invoice_ID
				&& Env.getContextAsInt(ctx, Env.WINDOW_INFO, Env.TAB_INFO, "C_InvoicePaySchedule_ID") != 0)
		{
			C_InvoicePaySchedule_ID = Env.getContextAsInt(ctx, Env.WINDOW_INFO, Env.TAB_INFO, "C_InvoicePaySchedule_ID");
		}
		return C_InvoicePaySchedule_ID;
	}

	private InvoiceInfoVO fetchInvoiceInfo(int C_Invoice_ID, int C_InvoicePaySchedule_ID, Timestamp dateTrx)
	{
		if (C_Invoice_ID <= 0)
			return null;

		final String sql = "SELECT C_BPartner_ID,C_Currency_ID," // 1..2
				+ " invoiceOpen(C_Invoice_ID, ?)," // 3 #1
				+ " invoiceDiscount(C_Invoice_ID,?,?), IsSOTrx " // 4..5 #2/3
				+ "FROM C_Invoice WHERE C_Invoice_ID=?"; // #4
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, null);
			pstmt.setInt(1, C_InvoicePaySchedule_ID);
			pstmt.setTimestamp(2, dateTrx);
			pstmt.setInt(3, C_InvoicePaySchedule_ID);
			pstmt.setInt(4, C_Invoice_ID);
			rs = pstmt.executeQuery();
			if (rs.next())
			{
				final InvoiceInfoVO invoiceInfo = new InvoiceInfoVO();
				invoiceInfo.invoiceId = C_Invoice_ID;
				invoiceInfo.bpartnerId = rs.getInt(1);
				invoiceInfo.currencyId = rs.getInt(2); // Set Invoice Currency
				invoiceInfo.openAmt = rs.getBigDecimal(3); // Set Invoice
				if (invoiceInfo.openAmt == null)
					invoiceInfo.openAmt = Env.ZERO;
				invoiceInfo.discountAmt = rs.getBigDecimal(4); // Set Discount
				if (invoiceInfo.discountAmt == null)
					invoiceInfo.discountAmt = Env.ZERO;
				return invoiceInfo;
			}
		}
		catch (SQLException e)
		{
			throw new DBException(e, sql);
		}
		finally
		{
			DB.close(rs, pstmt);
		}

		return null;
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

	private static class InvoiceInfoVO
	{
		public int invoiceId = -1;
		public int currencyId = -1;
		public int bpartnerId = -1;
		public BigDecimal openAmt = Env.ZERO;
		public BigDecimal discountAmt = Env.ZERO;

		@Override
		public String toString()
		{
			return "InvoiceInfoVO [invoiceId=" + invoiceId + ", currencyId=" + currencyId + ", bpartnerId=" + bpartnerId + ", openAmt=" + openAmt + ", discountAmt=" + discountAmt + "]";
		}

	}
} // CalloutBankStatement
