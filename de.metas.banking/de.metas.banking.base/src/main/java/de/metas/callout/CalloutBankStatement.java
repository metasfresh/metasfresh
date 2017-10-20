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
import de.metas.banking.service.IBankStatementBL;
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
	final private transient IBankStatementBL bankStatementBL = Services.get(IBankStatementBL.class);

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

		setBankStatementLineOrRefAmountsZero(lineOrRef);

		int invoicePayScheduleId = getC_InvoicePaySchedule_ID(ctx, invoiceId);
		final InvoiceInfoVO invoiceInfo = fetchInvoiceInfo(invoiceId, invoicePayScheduleId, dateTrx);
		setBankStatementLineOrRefAmounts(lineOrRef, invoiceInfo);
	}

	private void setBankStatementLineOrRefAmountsZero(@NonNull final IBankStatementLineOrRef lineOrRef)
	{
		lineOrRef.setDiscountAmt(BigDecimal.ZERO);
		lineOrRef.setWriteOffAmt(BigDecimal.ZERO);
		lineOrRef.setIsOverUnderPayment(false);
		lineOrRef.setOverUnderAmt(BigDecimal.ZERO);
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

	private void setBankStatementLineOrRefAmounts(@NonNull final IBankStatementLineOrRef lineOrRef, final InvoiceInfoVO invoiceInfo)
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

	private void setBankStatementLineOrRefFieldsForPaymentAmounts(@NonNull final IBankStatementLineOrRef lineOrRef, final int invoiceId)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(lineOrRef);
		final Timestamp dateTrx = getTrxDate(lineOrRef);

		BigDecimal payAmt = lineOrRef.getTrxAmt();

		final int invoicePayScheduleId = getC_InvoicePaySchedule_ID(ctx, invoiceId);
		final InvoiceInfoVO invoiceInfo = fetchInvoiceInfo(invoiceId, invoicePayScheduleId, dateTrx);

		BigDecimal invoiceOpenAmt = invoiceInfo != null ? invoiceInfo.getOpenAmt() : BigDecimal.ZERO;
		BigDecimal discountAmt = lineOrRef.getDiscountAmt();
		BigDecimal writeOffAmt = lineOrRef.getWriteOffAmt();
		BigDecimal overUnderAmt = lineOrRef.getOverUnderAmt();

		log.debug("Pay=" + payAmt + ", Discount=" + discountAmt + ", WriteOff=" + writeOffAmt + ", OverUnderAmt=" + overUnderAmt);

		final PaymentAmounts paymentAmounts = PaymentAmounts.builder()
				.payAmt(payAmt)
				.invoiceOpenAmt(invoiceOpenAmt)
				.discountAmt(discountAmt)
				.writeOffAmt(writeOffAmt)
				.overUnderAmt(overUnderAmt)
				.build();

		currencyChanged(lineOrRef, invoiceInfo, paymentAmounts);
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

	private void currencyChanged(@NonNull final IBankStatementLineOrRef lineOrRef, final InvoiceInfoVO invoiceInfo, @NonNull final PaymentAmounts paymentAmounts)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(lineOrRef);

		final Timestamp convDate = getTrxDate(lineOrRef);
		final int currencyId = lineOrRef.getC_Currency_ID();
		final I_C_Currency currency = Services.get(ICurrencyDAO.class).retrieveCurrency(ctx, currencyId);

		// Get Currency Rate
		BigDecimal currencyRate = BigDecimal.ONE;
		if (invoiceInfo != null && currencyId > 0 && invoiceInfo.currencyId > 0 && currencyId != invoiceInfo.currencyId)
		{
			log.debug("InvInfo=" + invoiceInfo + ", PayCurrency=" + currencyId + ", Date=" + convDate);
			final ICurrencyBL currencyConversionBL = Services.get(ICurrencyBL.class);

			currencyRate = currencyConversionBL.getRate(invoiceInfo.currencyId,
					currencyId, convDate, 0,
					lineOrRef.getAD_Client_ID(), lineOrRef.getAD_Org_ID());
			if (currencyRate == null || currencyRate.signum() == 0)
			{
				throw new AdempiereException("@NoCurrencyConversion@");
			}
			//
			BigDecimal invoiceOpenAmt = paymentAmounts.getInvoiceOpenAmt();
			invoiceOpenAmt = invoiceOpenAmt.multiply(currencyRate).setScale(currency.getStdPrecision(), BigDecimal.ROUND_HALF_UP);
			log.debug("Rate=" + currencyRate + ", InvoiceOpenAmt=" + invoiceOpenAmt);
		}

		BigDecimal payAmt = paymentAmounts.getPayAmt();
		payAmt = payAmt.multiply(currencyRate).setScale(currency.getStdPrecision(), BigDecimal.ROUND_HALF_UP);
		paymentAmounts.setPayAmt(payAmt);

		BigDecimal discountAmt = paymentAmounts.getDiscountAmt();
		discountAmt = discountAmt.multiply(currencyRate).setScale(currency.getStdPrecision(), BigDecimal.ROUND_HALF_UP);
		paymentAmounts.setDiscountAmt(discountAmt);

		BigDecimal writeOffAmt = paymentAmounts.getWriteOffAmt();
		writeOffAmt = writeOffAmt.multiply(currencyRate).setScale(currency.getStdPrecision(), BigDecimal.ROUND_HALF_UP);
		paymentAmounts.setWriteOffAmt(writeOffAmt);

		BigDecimal overUnderAmt = paymentAmounts.getOverUnderAmt();
		overUnderAmt = overUnderAmt.multiply(currencyRate).setScale(currency.getStdPrecision(), BigDecimal.ROUND_HALF_UP);
		paymentAmounts.setOverUnderAmt(overUnderAmt);
	}

	private void trxAmtChanged(final IBankStatementLineOrRef lineOrRef, @NonNull final PaymentAmounts paymentAmounts)
	{
		BigDecimal InvoiceOpenAmt = paymentAmounts.getInvoiceOpenAmt();
		BigDecimal PayAmt = paymentAmounts.getPayAmt();
		BigDecimal DiscountAmt = paymentAmounts.getDiscountAmt();
		BigDecimal WriteOffAmt = paymentAmounts.getWriteOffAmt();
		BigDecimal OverUnderAmt = paymentAmounts.getOverUnderAmt();

		if (lineOrRef.isOverUnderPayment())
		{
			OverUnderAmt = InvoiceOpenAmt.subtract(PayAmt).subtract(DiscountAmt).subtract(WriteOffAmt);
		}
		else
		{
			OverUnderAmt = BigDecimal.ZERO;
			WriteOffAmt = InvoiceOpenAmt.subtract(PayAmt).subtract(DiscountAmt).subtract(OverUnderAmt);
		}

	}

	
	/**
	 * Called by C_BankStatementLine_Ref: "Amount", "C_Currency_ID", "DiscountAmt", "IsOverUnderPayment", "OverUnderAmt", "WriteOffAmt"
	 * 
	 */
	public String paymentAmounts(final ICalloutField calloutField)
	{
		if (isCalloutActive()) // assuming it is resetting value
			return "";

		final int invoiceId = Env.getContextAsInt(calloutField.getCtx(), calloutField.getWindowNo(), I_C_BankStatementLine.COLUMNNAME_C_Invoice_ID);
		String amtColumn = (I_C_BankStatementLine.Table_Name.equals(calloutField.getTableName())) ? I_C_BankStatementLine.COLUMNNAME_TrxAmt : I_C_BankStatementLine_Ref.COLUMNNAME_TrxAmt;

		final I_C_BankStatementLine line;
		final I_C_BankStatementLine_Ref ref;
		final IBankStatementLineOrRef lineOrRef;

		BigDecimal PayAmt;
		Timestamp dateTrx = null;
		Timestamp ConvDate = null;

		if (I_C_BankStatementLine_Ref.Table_Name.equals(calloutField.getTableName()))
		{
			line = null;
			ref = calloutField.getModel(I_C_BankStatementLine_Ref.class);
			lineOrRef = ref;
			PayAmt = ref.getTrxAmt();
			dateTrx = ref.getC_BankStatementLine().getStatementLineDate();
		}
		else if (I_C_BankStatementLine.Table_Name.equals(calloutField.getTableName()))
		{
			line = calloutField.getModel(I_C_BankStatementLine.class);
			ref = null;
			lineOrRef = line;
			PayAmt = line.getTrxAmt();
			dateTrx = line.getStatementLineDate();
		}
		else
		{
			throw new IllegalStateException("" + calloutField.getTableName() + " is not supported");
		}

		if (dateTrx == null)
			dateTrx = SystemTime.asTimestamp();
		if (ConvDate == null)
			ConvDate = dateTrx;

		// Changed Column
		final String colName = calloutField.getColumnName();
		if (I_C_BankStatementLine.COLUMNNAME_IsOverUnderPayment.equals(colName) && !lineOrRef.isOverUnderPayment())
		{
			lineOrRef.setOverUnderAmt(BigDecimal.ZERO);
		}

		int invoicePayScheduleId = getC_InvoicePaySchedule_ID(calloutField.getCtx(), invoiceId);
		final InvoiceInfoVO invoiceInfo = fetchInvoiceInfo(invoiceId, invoicePayScheduleId, dateTrx);
		log.debug("" + invoiceInfo);

		// Get Info from Tab
		BigDecimal InvoiceOpenAmt = invoiceInfo != null ? invoiceInfo.getOpenAmt() : BigDecimal.ZERO;
		BigDecimal DiscountAmt = lineOrRef.getDiscountAmt();
		BigDecimal WriteOffAmt = lineOrRef.getWriteOffAmt();
		BigDecimal OverUnderAmt = lineOrRef.getOverUnderAmt();
		log.debug("Pay=" + PayAmt + ", Discount=" + DiscountAmt + ", WriteOff=" + WriteOffAmt + ", OverUnderAmt=" + OverUnderAmt);

		final int C_Currency_ID = lineOrRef.getC_Currency_ID();
		final I_C_Currency currency = Services.get(ICurrencyDAO.class).retrieveCurrency(calloutField.getCtx(), C_Currency_ID);

		// Get Currency Rate
		BigDecimal CurrencyRate = BigDecimal.ONE;
		if ((invoiceInfo != null && C_Currency_ID > 0 && invoiceInfo.currencyId > 0 && C_Currency_ID != invoiceInfo.currencyId)
				|| colName.equals(I_C_BankStatementLine.COLUMNNAME_C_Currency_ID))
		{
			log.debug("InvInfo=" + invoiceInfo + ", PayCurrency=" + C_Currency_ID + ", Date=" + ConvDate);
			if (invoiceInfo != null)
			{
				final ICurrencyBL currencyConversionBL = Services.get(ICurrencyBL.class);

				CurrencyRate = currencyConversionBL.getRate(invoiceInfo.currencyId,
						C_Currency_ID, ConvDate, 0,
						lineOrRef.getAD_Client_ID(), lineOrRef.getAD_Org_ID());
			}
			if (CurrencyRate == null || CurrencyRate.signum() == 0)
			{
				throw new AdempiereException("@NoCurrencyConversion@");
			}
			//
			InvoiceOpenAmt = InvoiceOpenAmt.multiply(CurrencyRate).setScale(currency.getStdPrecision(), BigDecimal.ROUND_HALF_UP);
			log.debug("Rate=" + CurrencyRate + ", InvoiceOpenAmt=" + InvoiceOpenAmt);
		}

		// Currency Changed - convert all
		if (colName.equals(I_C_BankStatementLine.COLUMNNAME_C_Currency_ID))
		{
			PayAmt = PayAmt.multiply(CurrencyRate).setScale(currency.getStdPrecision(), BigDecimal.ROUND_HALF_UP);
			lineOrRef.setTrxAmt(PayAmt);

			DiscountAmt = DiscountAmt.multiply(CurrencyRate).setScale(currency.getStdPrecision(), BigDecimal.ROUND_HALF_UP);
			lineOrRef.setDiscountAmt(DiscountAmt);
			WriteOffAmt = WriteOffAmt.multiply(CurrencyRate).setScale(currency.getStdPrecision(), BigDecimal.ROUND_HALF_UP);
			lineOrRef.setWriteOffAmt(WriteOffAmt);
			OverUnderAmt = OverUnderAmt.multiply(CurrencyRate).setScale(currency.getStdPrecision(), BigDecimal.ROUND_HALF_UP);
			lineOrRef.setOverUnderAmt(OverUnderAmt);
		}
		// No Invoice - Set Discount, Writeoff, Under/Over to 0
		else if (invoiceId <= 0)
		{
			lineOrRef.setDiscountAmt(BigDecimal.ZERO);
			lineOrRef.setWriteOffAmt(BigDecimal.ZERO);
			lineOrRef.setIsOverUnderPayment(false);
			lineOrRef.setOverUnderAmt(BigDecimal.ZERO);
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
				lineOrRef.setWriteOffAmt(BigDecimal.ZERO);
				lineOrRef.setOverUnderAmt(OverUnderAmt);
			}
			else
			{
				WriteOffAmt = InvoiceOpenAmt.subtract(PayAmt).subtract(DiscountAmt);
				lineOrRef.setWriteOffAmt(WriteOffAmt);
				lineOrRef.setOverUnderAmt(BigDecimal.ZERO);
			}
		}
		else
		// calculate PayAmt
		{
			PayAmt = InvoiceOpenAmt.subtract(DiscountAmt).subtract(WriteOffAmt).subtract(OverUnderAmt);
			lineOrRef.setTrxAmt(PayAmt);
		}
		return NO_ERROR;
	} // amounts

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
