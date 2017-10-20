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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Properties;

import org.adempiere.ad.callout.api.ICalloutField;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.DBException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.adempiere.util.time.SystemTime;
import org.compiere.model.CalloutEngine;
import org.compiere.model.I_C_Currency;
import org.compiere.model.I_C_Payment;
import org.compiere.util.DB;
import org.compiere.util.Env;

import com.google.common.base.MoreObjects;

import de.metas.banking.interfaces.I_C_BankStatementLine_Ref;
import de.metas.banking.model.IBankStatementLineOrRef;
import de.metas.banking.model.I_C_BankStatementLine;
import de.metas.banking.payment.IBankStatmentPaymentBL;
import de.metas.currency.ConversionType;
import de.metas.currency.ICurrencyBL;
import de.metas.currency.ICurrencyConversionContext;
import de.metas.currency.ICurrencyDAO;
import de.metas.currency.ICurrencyRate;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.Value;

public class CalloutBankStatement extends CalloutEngine
{
	final private ICurrencyBL currencyConversionBL = Services.get(ICurrencyBL.class);

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

		setBankStatementLineOrRefFieldsForInvoice(lineOrRef, invoiceId);

		return NO_ERROR;
	}

	private void setBankStatementLineOrRefFieldsForInvoice(@NonNull final IBankStatementLineOrRef lineOrRef, @NonNull final Integer invoiceId)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(lineOrRef);
		final Timestamp dateTrx;
		if (lineOrRef instanceof org.compiere.model.I_C_BankStatementLine)
		{
			final I_C_BankStatementLine line = (I_C_BankStatementLine)lineOrRef;
			dateTrx = MoreObjects.firstNonNull(line.getStatementLineDate(), SystemTime.asTimestamp());
		}
		else
		{
			final I_C_BankStatementLine_Ref ref = (I_C_BankStatementLine_Ref)lineOrRef;
			dateTrx = MoreObjects.firstNonNull(ref.getC_BankStatementLine().getStatementLineDate(), SystemTime.asTimestamp());
		}

		log.debug("DateTrx=" + dateTrx);

		setBankStatementLineOrRefAmountsToZero(lineOrRef);
		setBankStatementLineOrRefTrxAndStmtAmountsToZero(lineOrRef);

		int invoicePayScheduleId = getC_InvoicePaySchedule_ID(ctx, invoiceId);
		final InvoiceInfoVO invoiceInfo = fetchInvoiceInfo(invoiceId, invoicePayScheduleId, dateTrx);
		setBankStatementLineOrRefAmountsForInvoice(lineOrRef, invoiceInfo);
	}

	private void setBankStatementLineOrRefAmountsToZero(@NonNull final IBankStatementLineOrRef lineOrRef)
	{
		lineOrRef.setDiscountAmt(BigDecimal.ZERO);
		lineOrRef.setWriteOffAmt(BigDecimal.ZERO);
		lineOrRef.setIsOverUnderPayment(false);
		lineOrRef.setOverUnderAmt(BigDecimal.ZERO);
	}

	private void setBankStatementLineOrRefTrxAndStmtAmountsToZero(@NonNull final IBankStatementLineOrRef lineOrRef)
	{
		lineOrRef.setTrxAmt(BigDecimal.ZERO);

		if (lineOrRef instanceof org.compiere.model.I_C_BankStatementLine)
		{
			final I_C_BankStatementLine line = (I_C_BankStatementLine)lineOrRef;
			line.setStmtAmt(BigDecimal.ZERO);
		}
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

	@Builder
	@Value
	private static class InvoiceInfoVO
	{
		private final int currencyId;
		private final int bpartnerId;
		private final int invoiceId;
		private final BigDecimal openAmt;
		private final BigDecimal discountAmt;

		@Override
		public String toString()
		{
			return "InvoiceInfoVO [invoiceId=" + invoiceId + ", currencyId=" + currencyId + ", bpartnerId=" + bpartnerId + ", openAmt=" + openAmt + ", discountAmt=" + discountAmt + "]";
		}
	}

	private static InvoiceInfoVO fetchInvoiceInfo(int invoiceId, int invoicePayScheduleId, Timestamp dateTrx)
	{
		if (invoiceId <= 0)
		{
			return null;
		}

		final PreparedStatementParamsForInvoice params = PreparedStatementParamsForInvoice.builder()
				.dateTrx(dateTrx)
				.invoicePayScheduleId(invoicePayScheduleId)
				.invoiceId(invoiceId)
				.build();

		try (PreparedStatement pstmt = createPreparedStatementForInvoice(params);
				ResultSet rs = pstmt.executeQuery())
		{
			if (rs.next())
			{
				return InvoiceInfoVO.builder()
						.invoiceId(invoiceId)
						.bpartnerId(rs.getInt(1))
						.currencyId(rs.getInt(2))
						.openAmt(MoreObjects.firstNonNull(rs.getBigDecimal(3), BigDecimal.ZERO))
						.discountAmt(MoreObjects.firstNonNull(rs.getBigDecimal(4), BigDecimal.ZERO))
						.build();
			}
		}
		catch (SQLException e)
		{
			throw new DBException(e);
		}

		return null;

	}

	private void setBankStatementLineOrRefAmountsForInvoice(@NonNull final IBankStatementLineOrRef lineOrRef, final InvoiceInfoVO invoiceInfo)
	{
		if (invoiceInfo == null)
		{
			return;
		}

		final BigDecimal openAmount = invoiceInfo.getOpenAmt();
		final BigDecimal discount = invoiceInfo.getDiscountAmt();
		final BigDecimal openAmtActual = openAmount.subtract(discount);
		lineOrRef.setC_BPartner_ID(invoiceInfo.getBpartnerId());
		lineOrRef.setC_Currency_ID(invoiceInfo.getCurrencyId());
		lineOrRef.setTrxAmt(openAmtActual);
		lineOrRef.setDiscountAmt(invoiceInfo.getDiscountAmt());
		lineOrRef.setC_Invoice_ID(invoiceInfo.getInvoiceId());
		if (lineOrRef instanceof org.compiere.model.I_C_BankStatementLine)
		{
			final I_C_BankStatementLine line = (I_C_BankStatementLine)lineOrRef;
			line.setStmtAmt(openAmtActual);
		}
	}

	@Builder
	@Value
	private static class PreparedStatementParamsForInvoice
	{
		private final int invoicePayScheduleId;
		private final int invoiceId;
		private final Timestamp dateTrx;
	}

	private static PreparedStatement createPreparedStatementForInvoice(PreparedStatementParamsForInvoice params) throws SQLException
	{
		final StringBuilder sql = new StringBuilder()
				.append("SELECT C_BPartner_ID,C_Currency_ID,") // 1..2
				.append(" invoiceOpen(C_Invoice_ID, ?),") // 3 #1
				.append(" invoiceDiscount(C_Invoice_ID,?,?), IsSOTrx ") // 4..5 #2/3
				.append("FROM C_Invoice WHERE C_Invoice_ID=?"); // #4

		final PreparedStatement pstmt = DB.prepareStatement(sql.toString(), ITrx.TRXNAME_None);
		pstmt.setInt(1, params.getInvoicePayScheduleId());
		pstmt.setTimestamp(2, params.getDateTrx());
		pstmt.setInt(3, params.getInvoicePayScheduleId());
		pstmt.setInt(4, params.getInvoiceId());
		return pstmt;
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
			setBankStatementLineOrRefAmountsToZero(lineOrRef);
		}
		else
		{
			final PaymentAmounts paymentAmounts = computeBankStatementLineOrRefAmountsForPayment(lineOrRef, colName, invoiceId);
			setBankStatementLineOrRefAmountsForPayment(lineOrRef, paymentAmounts);
		}
	}
	
	@Data
	@Builder
	private static class PaymentAmounts
	{
		private BigDecimal invoiceOpenAmt;
		private BigDecimal discountAmt;
		private BigDecimal writeOffAmt;
		private BigDecimal overUnderAmt;
		private BigDecimal payAmt;
	}
	
	private PaymentAmounts computeBankStatementLineOrRefAmountsForPayment(@NonNull final IBankStatementLineOrRef lineOrRef, @NonNull final String colName, final int invoiceId)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(lineOrRef);
		final Timestamp dateTrx = getTrxDate(lineOrRef);

		final int invoicePayScheduleId = getC_InvoicePaySchedule_ID(ctx, invoiceId);
		final InvoiceInfoVO invoiceInfo = fetchInvoiceInfo(invoiceId, invoicePayScheduleId, dateTrx);

		BigDecimal invoiceOpenAmt = invoiceInfo != null ? invoiceInfo.getOpenAmt() : BigDecimal.ZERO;
		BigDecimal discountAmt = lineOrRef.getDiscountAmt();
		BigDecimal writeOffAmt = lineOrRef.getWriteOffAmt();
		BigDecimal overUnderAmt = lineOrRef.getOverUnderAmt();
		BigDecimal payAmt = lineOrRef.getTrxAmt();

		final PaymentAmounts paymentAmounts = PaymentAmounts.builder()
				.payAmt(payAmt)
				.invoiceOpenAmt(invoiceOpenAmt)
				.discountAmt(discountAmt)
				.writeOffAmt(writeOffAmt)
				.overUnderAmt(overUnderAmt)
				.build();
		
		computeInvoiceOpenAmtIfNeeded(lineOrRef, invoiceInfo, paymentAmounts);

		if (colName.equals(I_C_BankStatementLine.COLUMNNAME_C_Currency_ID))
		{
			columnCurrencyChanged(lineOrRef, invoiceInfo, paymentAmounts);
		}
		else if (colName.equals(I_C_BankStatementLine.COLUMNNAME_TrxAmt))
		{
			columnTrxAmtChanged(lineOrRef, paymentAmounts);
		}
		else if (colName.equals(I_C_BankStatementLine.COLUMNNAME_IsOverUnderPayment))
		{
			columnIsOverUnderPaymentChanged(lineOrRef, paymentAmounts);
		}
		else
		{
			invoiceOpenAmt = paymentAmounts.getInvoiceOpenAmt();
			discountAmt = lineOrRef.getDiscountAmt();
			writeOffAmt = lineOrRef.getWriteOffAmt();
			overUnderAmt = lineOrRef.getOverUnderAmt();
			
			payAmt = invoiceOpenAmt.subtract(discountAmt).subtract(writeOffAmt).subtract(overUnderAmt);
			paymentAmounts.setPayAmt(payAmt);
		}

		return paymentAmounts;
	}
	
	private Timestamp getTrxDate(@NonNull final IBankStatementLineOrRef lineOrRef)
	{
		if (lineOrRef instanceof org.compiere.model.I_C_BankStatementLine)
		{
			final I_C_BankStatementLine line = (I_C_BankStatementLine)lineOrRef;
			return MoreObjects.firstNonNull(line.getStatementLineDate(), SystemTime.asTimestamp());
		}
		else
		{
			final I_C_BankStatementLine_Ref ref = (I_C_BankStatementLine_Ref)lineOrRef;
			return MoreObjects.firstNonNull(ref.getC_BankStatementLine().getStatementLineDate(), SystemTime.asTimestamp());
		}
	}
	
	private void setBankStatementLineOrRefAmountsForPayment(@NonNull final IBankStatementLineOrRef lineOrRef, @NonNull final PaymentAmounts paymentAmounts)
	{
		lineOrRef.setTrxAmt(paymentAmounts.getPayAmt());
		lineOrRef.setOverUnderAmt(paymentAmounts.getOverUnderAmt());
		lineOrRef.setWriteOffAmt(paymentAmounts.getWriteOffAmt());
		lineOrRef.setDiscountAmt(paymentAmounts.getDiscountAmt());
	}


	private void computeInvoiceOpenAmtIfNeeded(@NonNull final IBankStatementLineOrRef lineOrRef, final InvoiceInfoVO invoiceInfo, @NonNull final PaymentAmounts paymentAmounts)
	{
		final int currencyId = lineOrRef.getC_Currency_ID();

		if (invoiceInfo != null && currencyId > 0
				&& invoiceInfo.getCurrencyId() > 0
				&& currencyId != invoiceInfo.getCurrencyId())
		{
			final Properties ctx = InterfaceWrapperHelper.getCtx(lineOrRef);
			final I_C_Currency currency = Services.get(ICurrencyDAO.class).retrieveCurrency(ctx, currencyId);
			final BigDecimal currencyRate = computeCurrencyRate(lineOrRef, invoiceInfo);
			BigDecimal invoiceOpenAmt = paymentAmounts.getInvoiceOpenAmt();
			invoiceOpenAmt = invoiceOpenAmt.multiply(currencyRate).setScale(currency.getStdPrecision(), BigDecimal.ROUND_HALF_UP);
			paymentAmounts.setInvoiceOpenAmt(invoiceOpenAmt);
		}
	}
	
	private BigDecimal computeCurrencyRate(final IBankStatementLineOrRef lineOrRef, final InvoiceInfoVO invoiceInfo)
	{
		final int currencyId = lineOrRef.getC_Currency_ID();
		final Timestamp convDate = getTrxDate(lineOrRef);

		BigDecimal currencyRate = BigDecimal.ONE;
		if (invoiceInfo != null && currencyId > 0
				&& invoiceInfo.getCurrencyId() > 0
				&& currencyId != invoiceInfo.getCurrencyId())
		{
			currencyRate = currencyConversionBL.getRate(invoiceInfo.currencyId,
					currencyId, convDate, 0,
					lineOrRef.getAD_Client_ID(), lineOrRef.getAD_Org_ID());
			if (currencyRate == null || currencyRate.signum() == 0)
			{
				throw new AdempiereException("@NoCurrencyConversion@");
			}
		}
		return currencyRate;
	}

	private void columnCurrencyChanged(@NonNull final IBankStatementLineOrRef lineOrRef, final InvoiceInfoVO invoiceInfo, @NonNull final PaymentAmounts paymentAmounts)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(lineOrRef);

		BigDecimal invoiceOpenAmt = paymentAmounts.getInvoiceOpenAmt();
		BigDecimal payAmt = paymentAmounts.getPayAmt();
		BigDecimal discountAmt = paymentAmounts.getDiscountAmt();
		BigDecimal writeOffAmt = paymentAmounts.getWriteOffAmt();
		BigDecimal overUnderAmt = paymentAmounts.getOverUnderAmt();

		final BigDecimal currencyRate = computeCurrencyRate(lineOrRef, invoiceInfo);

		final I_C_Currency currency = Services.get(ICurrencyDAO.class).retrieveCurrency(ctx, lineOrRef.getC_Currency_ID());

		invoiceOpenAmt = invoiceOpenAmt.multiply(currencyRate).setScale(currency.getStdPrecision(), BigDecimal.ROUND_HALF_UP);
		payAmt = payAmt.multiply(currencyRate).setScale(currency.getStdPrecision(), BigDecimal.ROUND_HALF_UP);
		discountAmt = discountAmt.multiply(currencyRate).setScale(currency.getStdPrecision(), BigDecimal.ROUND_HALF_UP);
		writeOffAmt = writeOffAmt.multiply(currencyRate).setScale(currency.getStdPrecision(), BigDecimal.ROUND_HALF_UP);
		overUnderAmt = overUnderAmt.multiply(currencyRate).setScale(currency.getStdPrecision(), BigDecimal.ROUND_HALF_UP);

		paymentAmounts.setPayAmt(payAmt);
		paymentAmounts.setDiscountAmt(discountAmt);
		paymentAmounts.setWriteOffAmt(writeOffAmt);
		paymentAmounts.setOverUnderAmt(overUnderAmt);
		paymentAmounts.setInvoiceOpenAmt(invoiceOpenAmt);
	}

	private void columnTrxAmtChanged(final IBankStatementLineOrRef lineOrRef, @NonNull final PaymentAmounts paymentAmounts)
	{
		BigDecimal invoiceOpenAmt = paymentAmounts.getInvoiceOpenAmt();
		BigDecimal payAmt = paymentAmounts.getPayAmt();
		BigDecimal discountAmt = paymentAmounts.getDiscountAmt();
		BigDecimal writeOffAmt = paymentAmounts.getWriteOffAmt();
		BigDecimal overUnderAmt = paymentAmounts.getOverUnderAmt();

		if (lineOrRef.isOverUnderPayment())
		{
			overUnderAmt = invoiceOpenAmt.subtract(payAmt).subtract(discountAmt).subtract(writeOffAmt);
		}
		else
		{
			overUnderAmt = BigDecimal.ZERO;
			writeOffAmt = invoiceOpenAmt.subtract(payAmt).subtract(discountAmt).subtract(overUnderAmt);
		}

		paymentAmounts.setOverUnderAmt(overUnderAmt);
		paymentAmounts.setWriteOffAmt(writeOffAmt);

	}

	private void columnIsOverUnderPaymentChanged(final IBankStatementLineOrRef lineOrRef, @NonNull final PaymentAmounts paymentAmounts)
	{
		BigDecimal invoiceOpenAmt = paymentAmounts.getInvoiceOpenAmt();
		BigDecimal payAmt = paymentAmounts.getPayAmt();
		BigDecimal discountAmt = paymentAmounts.getDiscountAmt();
		BigDecimal writeOffAmt = paymentAmounts.getWriteOffAmt();
		BigDecimal overUnderAmt = paymentAmounts.getOverUnderAmt();

		if (lineOrRef.isOverUnderPayment())
		{
			overUnderAmt = invoiceOpenAmt.subtract(payAmt).subtract(discountAmt);
			writeOffAmt = BigDecimal.ZERO;
		}
		else
		{
			writeOffAmt = invoiceOpenAmt.subtract(payAmt).subtract(discountAmt);
			overUnderAmt = BigDecimal.ZERO;
		}

		paymentAmounts.setWriteOffAmt(writeOffAmt);
		paymentAmounts.setOverUnderAmt(overUnderAmt);

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
