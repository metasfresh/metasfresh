package de.metas.banking.callout;

import java.math.BigDecimal;

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

import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.ad.callout.api.ICalloutField;
import org.adempiere.service.ClientId;
import org.compiere.model.I_C_BankStatementLine;
import org.compiere.util.TimeUtil;

import com.google.common.base.MoreObjects;

import de.metas.currency.ConversionTypeMethod;
import de.metas.currency.CurrencyConversionContext;
import de.metas.currency.CurrencyRate;
import de.metas.currency.ICurrencyBL;
import de.metas.money.CurrencyId;
import de.metas.organization.OrgId;
import de.metas.util.Services;
import lombok.NonNull;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/**
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@Callout(I_C_BankStatementLine.class)
public class C_BankStatementLine
{
	public static final C_BankStatementLine instance = new C_BankStatementLine();

	private C_BankStatementLine()
	{
		super();
	}

	@CalloutMethod(columnNames = {
			I_C_BankStatementLine.COLUMNNAME_InterestAmt,
			I_C_BankStatementLine.COLUMNNAME_StmtAmt,
			I_C_BankStatementLine.COLUMNNAME_ChargeAmt
	})
	public void onAmountsChanged(final I_C_BankStatementLine bsl, final ICalloutField field)
	{
		final String columnName = field.getColumnName();

		if (columnName.equals(I_C_BankStatementLine.COLUMNNAME_ChargeAmt))
		{
			final BigDecimal interesetAmt = computeInterestAmt(bsl);
			bsl.setInterestAmt(interesetAmt);
		}
		else if (columnName.equals(I_C_BankStatementLine.COLUMNNAME_InterestAmt))
		{
			final BigDecimal chargeAmt = computeChargeAmt(bsl);
			bsl.setChargeAmt(chargeAmt);
		}
		else
		{
			final BigDecimal trxAmt = computeTrxAmt(bsl);
			bsl.setTrxAmt(trxAmt);
		}
	}

	private BigDecimal computeInterestAmt(@NonNull final I_C_BankStatementLine bsl)
	{
		final BigDecimal bd = computeStmtAmount(bsl);
		final BigDecimal charge = MoreObjects.firstNonNull(bsl.getChargeAmt(), BigDecimal.ZERO);
		return bd.subtract(charge);
	}

	private BigDecimal computeChargeAmt(@NonNull final I_C_BankStatementLine bsl)
	{
		final BigDecimal bd = computeStmtAmount(bsl);
		final BigDecimal interest = bsl.getInterestAmt();
		return bd.subtract(interest);
	}

	private BigDecimal computeTrxAmt(@NonNull final I_C_BankStatementLine bsl)
	{
		final BigDecimal bd = computeStmtAmount(bsl);
		final BigDecimal trxAmt = bsl.getTrxAmt();
		return trxAmt.add(bd);
	}

	private BigDecimal computeStmtAmount(@NonNull final I_C_BankStatementLine bsl)
	{
		final BigDecimal stmt = bsl.getStmtAmt();
		final BigDecimal trx = bsl.getTrxAmt();
		final BigDecimal discount = bsl.getDiscountAmt();
		final BigDecimal writeOff = bsl.getWriteOffAmt();
		final BigDecimal overUnder = bsl.isOverUnderPayment() ? bsl.getOverUnderAmt() : BigDecimal.ZERO;

		return stmt.subtract(trx).subtract(discount).subtract(writeOff).subtract(overUnder);
	}

	@CalloutMethod(columnNames = {
			I_C_BankStatementLine.COLUMNNAME_C_Payment_ID
	})
	public void onPaymentIdChanged(final I_C_BankStatementLine line, final ICalloutField field)
	{
		BankStatementLineOrRefHelper.setPaymentDetails(line);
		onAmountsChanged(line, field);
	}

	@CalloutMethod(columnNames = {
			I_C_BankStatementLine.COLUMNNAME_C_Invoice_ID
	})
	public void onInvoiceIdChanged(final I_C_BankStatementLine line, final ICalloutField field)
	{
		if (line.getC_Invoice_ID() <= 0)
		{
			return;
		}
		BankStatementLineOrRefHelper.setBankStatementLineOrRefFieldsWhenInvoiceChanged(line);
	}

	@CalloutMethod(columnNames = {
			I_C_BankStatementLine.COLUMNNAME_Link_BankStatementLine_ID
	})
	public void onLink_BankStatement_IDChangedResetAmounts(final I_C_BankStatementLine bsl, final ICalloutField calloutField)
	{
		if (bsl.getLink_BankStatementLine_ID() <= 0)
		{
			bsl.setCurrencyRate(null);
			return;
		}

		final org.compiere.model.I_C_BankStatementLine bslFrom = bsl.getLink_BankStatementLine();

		final BigDecimal trxAmtFrom = bslFrom.getTrxAmt();
		final CurrencyId trxAmtFromCurrencyId = CurrencyId.ofRepoId(bslFrom.getC_Currency_ID());

		final CurrencyId trxAmtCurrencyId = CurrencyId.ofRepoId(bsl.getC_Currency_ID());

		final ICurrencyBL currencyConversionBL = Services.get(ICurrencyBL.class);
		final CurrencyConversionContext currencyConversionCtx = currencyConversionBL.createCurrencyConversionContext(
				TimeUtil.asLocalDate(bsl.getValutaDate()),
				ConversionTypeMethod.Spot,
				ClientId.ofRepoId(bsl.getAD_Client_ID()),
				OrgId.ofRepoId(bsl.getAD_Org_ID()));

		final CurrencyRate currencyRate = currencyConversionBL.getCurrencyRate(currencyConversionCtx, trxAmtFromCurrencyId, trxAmtCurrencyId);
		final BigDecimal trxAmt = currencyRate
				.convertAmount(trxAmtFrom)
				.negate();

		bsl.setStmtAmt(trxAmt);
		bsl.setTrxAmt(trxAmt);
		bsl.setCurrencyRate(currencyRate.getConversionRate());
		bsl.setChargeAmt(BigDecimal.ZERO);
		BankStatementLineOrRefHelper.setBankStatementLineOrRefAmountsToZero(bsl);
	}

	@CalloutMethod(columnNames = {
			I_C_BankStatementLine.COLUMNNAME_C_BP_BankAccountTo_ID
	})
	public void onC_BP_BankAccountTo_IDChanged(final I_C_BankStatementLine bsl)
	{
		if (bsl.getC_BP_BankAccountTo_ID() <= 0)
		{
			bsl.setLink_BankStatementLine_ID(0);
		}
	}
}
