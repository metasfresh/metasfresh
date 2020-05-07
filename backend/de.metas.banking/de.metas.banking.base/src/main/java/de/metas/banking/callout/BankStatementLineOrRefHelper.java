/**
 * 
 */
package de.metas.banking.callout;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Optional;
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.DBException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.compiere.model.I_C_Payment;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;

import com.google.common.base.MoreObjects;

import de.metas.banking.interfaces.I_C_BankStatementLine_Ref;
import de.metas.banking.model.IBankStatementLineOrRef;
import de.metas.banking.model.I_C_BankStatementLine;
import de.metas.banking.payment.IBankStatmentPaymentBL;
import de.metas.currency.Currency;
import de.metas.currency.CurrencyPrecision;
import de.metas.currency.ICurrencyBL;
import de.metas.currency.ICurrencyDAO;
import de.metas.money.CurrencyConversionTypeId;
import de.metas.money.CurrencyId;
import de.metas.organization.OrgId;
import de.metas.util.Services;
import de.metas.util.time.SystemTime;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.banking.base
 * %%
 * Copyright (C) 2017 metas GmbH
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

/**
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class BankStatementLineOrRefHelper
{
	final static private ICurrencyBL currencyConversionBL = Services.get(ICurrencyBL.class);

	public static void setBankStatementLineOrRefFieldsWhenInvoiceChanged(@NonNull final IBankStatementLineOrRef lineOrRef)
	{
		setBankStatementLineOrRefAmountsToZero(lineOrRef);
		setBankStatementLineOrRefTrxAndStmtAmountsToZero(lineOrRef);

		final InvoiceInfoVO invoiceInfo = fetchInvoiceCurrencyBpartnerAndAmounts(lineOrRef);
		setBankStatementLineOrRefCurrencyBPartneAndInvoiceWhenInvoiceChanged(lineOrRef, invoiceInfo);
		setBankStatementLineOrRefAmountsWhenInvoiceChanged(lineOrRef, invoiceInfo);
	}

	public static void setBankStatementLineOrRefAmountsToZero(@NonNull final IBankStatementLineOrRef lineOrRef)
	{
		lineOrRef.setDiscountAmt(BigDecimal.ZERO);
		lineOrRef.setWriteOffAmt(BigDecimal.ZERO);
		lineOrRef.setOverUnderAmt(BigDecimal.ZERO);
		lineOrRef.setIsOverUnderPayment(false);
	}

	private static void setBankStatementLineOrRefTrxAndStmtAmountsToZero(@NonNull final IBankStatementLineOrRef lineOrRef)
	{
		lineOrRef.setTrxAmt(BigDecimal.ZERO);

		if (lineOrRef instanceof org.compiere.model.I_C_BankStatementLine)
		{
			final I_C_BankStatementLine line = (I_C_BankStatementLine)lineOrRef;
			line.setStmtAmt(BigDecimal.ZERO);
		}
	}

	private static InvoiceInfoVO fetchInvoiceCurrencyBpartnerAndAmounts(@NonNull final IBankStatementLineOrRef lineOrRef)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(lineOrRef);
		final Timestamp dateTrx = getTrxDate(lineOrRef);
		final int invoiceId = lineOrRef.getC_Invoice_ID();
		final int invoicePayScheduleId = getC_InvoicePaySchedule_ID(ctx, invoiceId);
		return fetchInvoiceInfo(invoiceId, invoicePayScheduleId, dateTrx);
	}

	private static void setBankStatementLineOrRefCurrencyBPartneAndInvoiceWhenInvoiceChanged(@NonNull final IBankStatementLineOrRef lineOrRef, final InvoiceInfoVO invoiceInfo)
	{
		if (invoiceInfo == null)
		{
			return;
		}

		lineOrRef.setC_BPartner_ID(invoiceInfo.getBpartnerId());
		lineOrRef.setC_Currency_ID(CurrencyId.toRepoId(invoiceInfo.getCurrencyId()));
		lineOrRef.setC_Invoice_ID(invoiceInfo.getInvoiceId());
	}

	private static void setBankStatementLineOrRefAmountsWhenInvoiceChanged(@NonNull final IBankStatementLineOrRef lineOrRef, final InvoiceInfoVO invoiceInfo)
	{
		if (invoiceInfo == null)
		{
			return;
		}

		final BigDecimal openAmount = invoiceInfo.getOpenAmt();
		final BigDecimal discount = invoiceInfo.getDiscountAmt();
		final BigDecimal openAmtActual = openAmount.subtract(discount);
		lineOrRef.setTrxAmt(openAmtActual);
		lineOrRef.setDiscountAmt(invoiceInfo.getDiscountAmt());
		if (lineOrRef instanceof org.compiere.model.I_C_BankStatementLine)
		{
			final I_C_BankStatementLine line = (I_C_BankStatementLine)lineOrRef;
			line.setStmtAmt(openAmtActual);
		}
	}

	private static int getC_InvoicePaySchedule_ID(Properties ctx, int C_Invoice_ID)
	{
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
		private final CurrencyId currencyId;
		private final int bpartnerId;
		private final int invoiceId;
		private final BigDecimal openAmt;
		private final BigDecimal discountAmt;
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
						.currencyId(CurrencyId.ofRepoId(rs.getInt(2)))
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
				.append("SELECT C_BPartner_ID,C_Currency_ID,")
				.append(" invoiceOpen(C_Invoice_ID, ?),")
				.append(" invoiceDiscount(C_Invoice_ID,?,?), IsSOTrx ")
				.append("FROM C_Invoice WHERE C_Invoice_ID=?");

		final PreparedStatement pstmt = DB.prepareStatement(sql.toString(), ITrx.TRXNAME_None);
		pstmt.setInt(1, params.getInvoicePayScheduleId());
		pstmt.setTimestamp(2, params.getDateTrx());
		pstmt.setInt(3, params.getInvoicePayScheduleId());
		pstmt.setInt(4, params.getInvoiceId());
		return pstmt;
	}

	public static void setBankStatementLineOrRefAmountsWhenSomeAmountChanged(@NonNull final IBankStatementLineOrRef lineOrRef, @NonNull final String colName)
	{
		final Amounts paymentAmounts = computeBankStatementLineOrRefAmounts(lineOrRef, colName);

		lineOrRef.setTrxAmt(paymentAmounts.getPayAmt());
		lineOrRef.setOverUnderAmt(paymentAmounts.getOverUnderAmt());
		lineOrRef.setWriteOffAmt(paymentAmounts.getWriteOffAmt());
		lineOrRef.setDiscountAmt(paymentAmounts.getDiscountAmt());
	}

	@Data
	@Builder
	private static class Amounts
	{
		private BigDecimal invoiceOpenAmt;
		private BigDecimal discountAmt;
		private BigDecimal writeOffAmt;
		private BigDecimal overUnderAmt;
		private BigDecimal payAmt;
	}

	private static Amounts computeBankStatementLineOrRefAmounts(@NonNull final IBankStatementLineOrRef lineOrRef, @NonNull final String colName)
	{
		final InvoiceInfoVO invoiceInfo = fetchInvoiceCurrencyBpartnerAndAmounts(lineOrRef);

		final BigDecimal invoiceOpenAmt = invoiceInfo != null ? invoiceInfo.getOpenAmt() : BigDecimal.ZERO;
		final BigDecimal discountAmt = lineOrRef.getDiscountAmt();
		final BigDecimal writeOffAmt = lineOrRef.getWriteOffAmt();
		final BigDecimal overUnderAmt = lineOrRef.getOverUnderAmt();
		final BigDecimal payAmt = lineOrRef.getTrxAmt();

		final Amounts paymentAmounts = Amounts.builder()
				.payAmt(payAmt)
				.invoiceOpenAmt(invoiceOpenAmt)
				.discountAmt(discountAmt)
				.writeOffAmt(writeOffAmt)
				.overUnderAmt(overUnderAmt)
				.build();

		computeInvoiceOpenAmtIfNeeded(lineOrRef, invoiceInfo, paymentAmounts);
		if (colName.equals(I_C_BankStatementLine.COLUMNNAME_C_Currency_ID))
		{
			computeAmountsWhenCurrencyChanged(lineOrRef, invoiceInfo, paymentAmounts);
		}
		else if (colName.equals(I_C_BankStatementLine.COLUMNNAME_TrxAmt))
		{
			computeOverUnderAndWriteOffAmountsWhenTrxAmtChanged(lineOrRef, paymentAmounts);
		}
		else if (colName.equals(I_C_BankStatementLine.COLUMNNAME_IsOverUnderPayment))
		{
			computeOverUnderAndWriteOffAmountsWhenIsOverUnderPaymentChanged(lineOrRef, paymentAmounts);
		}
		else
		{
			computeTrxAmtAmount(lineOrRef, paymentAmounts);
		}
		return paymentAmounts;
	}

	private static Timestamp getTrxDate(@NonNull final IBankStatementLineOrRef lineOrRef)
	{
		if (lineOrRef instanceof org.compiere.model.I_C_BankStatementLine)
		{
			final I_C_BankStatementLine line = (I_C_BankStatementLine)lineOrRef;
			return MoreObjects.firstNonNull(line.getStatementLineDate(), SystemTime.asTimestamp());
		}
		else
		{
			final I_C_BankStatementLine_Ref ref = (I_C_BankStatementLine_Ref)lineOrRef;
			return getStatementLineDateOrCurrentDate(ref);
		}
	}

	private static Timestamp getStatementLineDateOrCurrentDate(@NonNull final I_C_BankStatementLine_Ref ref)
	{
		final Optional<org.compiere.model.I_C_BankStatementLine> line = Optional.ofNullable(ref.getC_BankStatementLine());
		if (line.isPresent())
		{
			return MoreObjects.firstNonNull(line.get().getStatementLineDate(), SystemTime.asTimestamp());
		}
		return SystemTime.asTimestamp();
	}

	private static void computeInvoiceOpenAmtIfNeeded(@NonNull final IBankStatementLineOrRef lineOrRef, final InvoiceInfoVO invoiceInfo, @NonNull final Amounts paymentAmounts)
	{
		final CurrencyId currencyId = CurrencyId.ofRepoIdOrNull(lineOrRef.getC_Currency_ID());

		if (invoiceInfo != null
				&& currencyId != null
				&& invoiceInfo.getCurrencyId() != null
				&& !CurrencyId.equals(currencyId, invoiceInfo.getCurrencyId()))
		{
			final Currency currency = Services.get(ICurrencyDAO.class).getById(currencyId);
			final CurrencyPrecision precision = currency.getPrecision();
			
			final BigDecimal currencyRate = computeCurrencyRate(lineOrRef, invoiceInfo);
			BigDecimal invoiceOpenAmt = paymentAmounts.getInvoiceOpenAmt();
			invoiceOpenAmt = precision.round(invoiceOpenAmt.multiply(currencyRate));
			paymentAmounts.setInvoiceOpenAmt(invoiceOpenAmt);
		}
	}

	private static void computeAmountsWhenCurrencyChanged(@NonNull final IBankStatementLineOrRef lineOrRef, final InvoiceInfoVO invoiceInfo, @NonNull final Amounts paymentAmounts)
	{
		BigDecimal invoiceOpenAmt = paymentAmounts.getInvoiceOpenAmt();
		BigDecimal payAmt = paymentAmounts.getPayAmt();
		BigDecimal discountAmt = paymentAmounts.getDiscountAmt();
		BigDecimal writeOffAmt = paymentAmounts.getWriteOffAmt();
		BigDecimal overUnderAmt = paymentAmounts.getOverUnderAmt();

		final BigDecimal currencyRate = computeCurrencyRate(lineOrRef, invoiceInfo);
		final CurrencyId currencyId = CurrencyId.ofRepoId(lineOrRef.getC_Currency_ID());
		final Currency currency = Services.get(ICurrencyDAO.class).getById(currencyId);
		final CurrencyPrecision precision = currency.getPrecision();

		invoiceOpenAmt = precision.round(invoiceOpenAmt.multiply(currencyRate));
		payAmt = precision.round(payAmt.multiply(currencyRate));
		discountAmt = precision.round(discountAmt.multiply(currencyRate));
		writeOffAmt = precision.round(writeOffAmt.multiply(currencyRate));
		overUnderAmt = precision.round(overUnderAmt.multiply(currencyRate));

		paymentAmounts.setPayAmt(payAmt);
		paymentAmounts.setDiscountAmt(discountAmt);
		paymentAmounts.setWriteOffAmt(writeOffAmt);
		paymentAmounts.setOverUnderAmt(overUnderAmt);
		paymentAmounts.setInvoiceOpenAmt(invoiceOpenAmt);
	}

	private static void computeOverUnderAndWriteOffAmountsWhenTrxAmtChanged(final IBankStatementLineOrRef lineOrRef, @NonNull final Amounts paymentAmounts)
	{
		final BigDecimal invoiceOpenAmt = paymentAmounts.getInvoiceOpenAmt();
		final BigDecimal payAmt = paymentAmounts.getPayAmt();
		final BigDecimal discountAmt = paymentAmounts.getDiscountAmt();
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

	private static void computeOverUnderAndWriteOffAmountsWhenIsOverUnderPaymentChanged(final IBankStatementLineOrRef lineOrRef, @NonNull final Amounts paymentAmounts)
	{
		final BigDecimal invoiceOpenAmt = paymentAmounts.getInvoiceOpenAmt();
		final BigDecimal payAmt = paymentAmounts.getPayAmt();
		final BigDecimal discountAmt = paymentAmounts.getDiscountAmt();
		BigDecimal writeOffAmt = paymentAmounts.getWriteOffAmt();
		BigDecimal overUnderAmt = paymentAmounts.getOverUnderAmt();

		if (lineOrRef.isOverUnderPayment())
		{
			overUnderAmt = invoiceOpenAmt.subtract(payAmt).subtract(discountAmt);
			writeOffAmt = BigDecimal.ZERO;
		}
		else
		{
			overUnderAmt = BigDecimal.ZERO;
			writeOffAmt = invoiceOpenAmt.subtract(payAmt).subtract(discountAmt);
		}

		paymentAmounts.setWriteOffAmt(writeOffAmt);
		paymentAmounts.setOverUnderAmt(overUnderAmt);
	}

	private static void computeTrxAmtAmount(@NonNull final IBankStatementLineOrRef lineOrRef, @NonNull final Amounts paymentAmounts)
	{
		final BigDecimal invoiceOpenAmt = paymentAmounts.getInvoiceOpenAmt();
		final BigDecimal discountAmt = lineOrRef.getDiscountAmt();
		final BigDecimal writeOffAmt = lineOrRef.getWriteOffAmt();
		final BigDecimal overUnderAmt = lineOrRef.getOverUnderAmt();
		final BigDecimal payAmt = invoiceOpenAmt.subtract(discountAmt).subtract(writeOffAmt).subtract(overUnderAmt);
		paymentAmounts.setPayAmt(payAmt);
	}

	private static BigDecimal computeCurrencyRate(final IBankStatementLineOrRef lineOrRef, final InvoiceInfoVO invoiceInfo)
	{
		final CurrencyId currencyId = CurrencyId.ofRepoIdOrNull(lineOrRef.getC_Currency_ID());
		final LocalDate convDate = TimeUtil.asLocalDate(getTrxDate(lineOrRef));

		BigDecimal currencyRate = BigDecimal.ONE;
		if (invoiceInfo != null
				&& currencyId != null
				&& invoiceInfo.getCurrencyId() != null
				&& !CurrencyId.equals(currencyId, invoiceInfo.getCurrencyId()))
		{
			currencyRate = currencyConversionBL.getRate(
					invoiceInfo.getCurrencyId(),
					currencyId,
					convDate,
					(CurrencyConversionTypeId)null, // conversionTypeId
					ClientId.ofRepoId(lineOrRef.getAD_Client_ID()),
					OrgId.ofRepoId(lineOrRef.getAD_Org_ID()));
			if (currencyRate == null || currencyRate.signum() == 0)
			{
				throw new AdempiereException("@NoCurrencyConversion@");
			}
		}
		return currencyRate;
	}

	public static void setPaymentDetails(IBankStatementLineOrRef line)
	{
		I_C_Payment payment = null;
		if (line.getC_Payment_ID() > 0)
		{
			payment = line.getC_Payment();
		}
		Services.get(IBankStatmentPaymentBL.class).setC_Payment(line, payment);
	}
}
