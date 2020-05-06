/*
 * #%L
 * de.metas.banking.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.banking.callout;

import de.metas.banking.BankStatementLineId;
import de.metas.banking.service.IBankStatementBL;
import de.metas.currency.ConversionTypeMethod;
import de.metas.currency.CurrencyConversionContext;
import de.metas.currency.CurrencyRate;
import de.metas.currency.ICurrencyBL;
import de.metas.invoice.InvoiceId;
import de.metas.money.CurrencyId;
import de.metas.organization.OrgId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.service.ClientId;
import org.compiere.model.I_C_BankStatementLine;
import org.compiere.util.TimeUtil;

import java.math.BigDecimal;

@Callout(I_C_BankStatementLine.class)
public class C_BankStatementLine
{
	public static final C_BankStatementLine instance = new C_BankStatementLine();

	private C_BankStatementLine()
	{
	}

	@CalloutMethod(columnNames = I_C_BankStatementLine.COLUMNNAME_StmtAmt)
	public void onStmtAmtChanged(final @NonNull I_C_BankStatementLine bsl)
	{
		final BigDecimal trxAmt = bsl.getStmtAmt();
		bsl.setTrxAmt(trxAmt);
	}

	@CalloutMethod(columnNames = I_C_BankStatementLine.COLUMNNAME_ChargeAmt)
	public void onChargeAmtChanged(final @NonNull I_C_BankStatementLine bsl)
	{
		final BigDecimal stmtAmt = bsl.getStmtAmt();
		final BigDecimal trxAmt = bsl.getTrxAmt();
		final BigDecimal chargeAmt = bsl.getChargeAmt();
		final BigDecimal interestAmt = stmtAmt.subtract(trxAmt).subtract(chargeAmt);
		bsl.setInterestAmt(interestAmt);
	}

	@CalloutMethod(columnNames = I_C_BankStatementLine.COLUMNNAME_InterestAmt)
	public void onInterestAmtChanged(final @NonNull I_C_BankStatementLine bsl)
	{
		final BigDecimal stmtAmt = bsl.getStmtAmt();
		final BigDecimal trxAmt = bsl.getTrxAmt();
		final BigDecimal interestAmt = bsl.getInterestAmt();
		final BigDecimal chargeAmt = stmtAmt.subtract(trxAmt).subtract(interestAmt);
		bsl.setChargeAmt(chargeAmt);
	}

	@CalloutMethod(columnNames = I_C_BankStatementLine.COLUMNNAME_Link_BankStatementLine_ID)
	public void onLink_BankStatement_IDChangedResetAmounts(final @NonNull I_C_BankStatementLine bsl)
	{
		final BankStatementLineId linkedBankStatementLineId = BankStatementLineId.ofRepoIdOrNull(bsl.getLink_BankStatementLine_ID());
		if (linkedBankStatementLineId == null)
		{
			bsl.setCurrencyRate(null);
			return;
		}

		final IBankStatementBL bankStatementBL = Services.get(IBankStatementBL.class);
		final ICurrencyBL currencyConversionBL = Services.get(ICurrencyBL.class);

		final I_C_BankStatementLine bslFrom = bankStatementBL.getLineById(linkedBankStatementLineId);

		final BigDecimal trxAmtFrom = bslFrom.getTrxAmt();
		final CurrencyId trxAmtFromCurrencyId = CurrencyId.ofRepoId(bslFrom.getC_Currency_ID());

		final CurrencyId trxAmtCurrencyId = CurrencyId.ofRepoId(bsl.getC_Currency_ID());

		final CurrencyConversionContext currencyConversionCtx = currencyConversionBL.createCurrencyConversionContext(
				TimeUtil.asLocalDate(bsl.getValutaDate()),
				ConversionTypeMethod.Spot,
				ClientId.ofRepoId(bsl.getAD_Client_ID()),
				OrgId.ofRepoId(bsl.getAD_Org_ID()));

		final CurrencyRate currencyRate = currencyConversionBL.getCurrencyRate(currencyConversionCtx, trxAmtFromCurrencyId, trxAmtCurrencyId);
		final BigDecimal trxAmt = currencyRate
				.convertAmount(trxAmtFrom)
				.negate();

		// bsl.setStmtAmt(trxAmt); // never touch the statement amount after the line was created
		bsl.setTrxAmt(trxAmt);
		bsl.setCurrencyRate(currencyRate.getConversionRate());
		bsl.setChargeAmt(BigDecimal.ZERO);
	}

	@CalloutMethod(columnNames = I_C_BankStatementLine.COLUMNNAME_C_BP_BankAccountTo_ID)
	public void onC_BP_BankAccountTo_IDChanged(final @NonNull I_C_BankStatementLine bsl)
	{
		if (bsl.getC_BP_BankAccountTo_ID() <= 0)
		{
			bsl.setLink_BankStatementLine_ID(0);
		}
	}

	@CalloutMethod(columnNames = I_C_BankStatementLine.COLUMNNAME_C_Invoice_ID)
	public void onC_Invoice_ID_Changed(@NonNull final I_C_BankStatementLine bsl)
	{
		final InvoiceId invoiceId = InvoiceId.ofRepoIdOrNull(bsl.getC_Invoice_ID());
		if (invoiceId == null)
		{
			return;
		}

		final IBankStatementBL bankStatementBL = Services.get(IBankStatementBL.class);
		bankStatementBL.updateLineFromInvoice(bsl, invoiceId);
	}
}
