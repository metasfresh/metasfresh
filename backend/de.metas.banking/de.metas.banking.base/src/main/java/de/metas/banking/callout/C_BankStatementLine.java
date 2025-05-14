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

import de.metas.banking.model.BankStatementLineAmounts;
import de.metas.banking.service.IBankStatementBL;
import de.metas.invoice.InvoiceId;
import lombok.NonNull;
import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.compiere.model.I_C_BankStatementLine;

import java.math.BigDecimal;

@Callout(I_C_BankStatementLine.class)
public class C_BankStatementLine
{
	private final IBankStatementBL bankStatementBL;

	private final BankStatementLineAmountsCallout bankStatementLineAmountsCallout = new BankStatementLineAmountsCallout();
	private final CashJournalLineAmountsCallout cashJournalLineAmountsCallout = new CashJournalLineAmountsCallout();

	public C_BankStatementLine(
			@NonNull final IBankStatementBL bankStatementBL)
	{
		this.bankStatementBL = bankStatementBL;
	}

	private AmountsCallout getAmountsCallout(@NonNull final I_C_BankStatementLine bsl)
	{
		return bankStatementBL.isCashJournal(bsl)
				? cashJournalLineAmountsCallout
				: bankStatementLineAmountsCallout;
	}

	@CalloutMethod(columnNames = I_C_BankStatementLine.COLUMNNAME_StmtAmt)
	public void onStmtAmtChanged(final @NonNull I_C_BankStatementLine bsl)
	{
		getAmountsCallout(bsl).onStmtAmtChanged(bsl);
	}

	@CalloutMethod(columnNames = I_C_BankStatementLine.COLUMNNAME_TrxAmt)
	public void onTrxAmtChanged(final @NonNull I_C_BankStatementLine bsl)
	{
		getAmountsCallout(bsl).onTrxAmtChanged(bsl);
	}

	@CalloutMethod(columnNames = I_C_BankStatementLine.COLUMNNAME_BankFeeAmt)
	public void onBankFeeAmtChanged(final @NonNull I_C_BankStatementLine bsl)
	{
		getAmountsCallout(bsl).onBankFeeAmtChanged(bsl);
	}

	@CalloutMethod(columnNames = I_C_BankStatementLine.COLUMNNAME_ChargeAmt)
	public void onChargeAmtChanged(final @NonNull I_C_BankStatementLine bsl)
	{
		getAmountsCallout(bsl).onChargeAmtChanged(bsl);
	}

	@CalloutMethod(columnNames = I_C_BankStatementLine.COLUMNNAME_InterestAmt)
	public void onInterestAmtChanged(final @NonNull I_C_BankStatementLine bsl)
	{
		getAmountsCallout(bsl).onInterestAmtChanged(bsl);
	}

	@CalloutMethod(columnNames = I_C_BankStatementLine.COLUMNNAME_C_Invoice_ID)
	public void onC_Invoice_ID_Changed(@NonNull final I_C_BankStatementLine bsl)
	{
		final InvoiceId invoiceId = InvoiceId.ofRepoIdOrNull(bsl.getC_Invoice_ID());
		if (invoiceId == null)
		{
			return;
		}

		bankStatementBL.updateLineFromInvoice(bsl, invoiceId);
	}

	private static void updateTrxAmt(final I_C_BankStatementLine bsl)
	{
		bsl.setTrxAmt(BankStatementLineAmounts.of(bsl)
				.addDifferenceToTrxAmt()
				.getTrxAmt());
	}

	private interface AmountsCallout
	{
		void onStmtAmtChanged(final I_C_BankStatementLine bsl);

		void onTrxAmtChanged(final I_C_BankStatementLine bsl);

		void onBankFeeAmtChanged(final I_C_BankStatementLine bsl);

		void onChargeAmtChanged(final I_C_BankStatementLine bsl);

		void onInterestAmtChanged(final I_C_BankStatementLine bsl);
	}

	public static class BankStatementLineAmountsCallout implements AmountsCallout
	{
		@Override
		public void onStmtAmtChanged(final I_C_BankStatementLine bsl)
		{
			updateTrxAmt(bsl);
		}

		@Override
		public void onTrxAmtChanged(final I_C_BankStatementLine bsl)
		{
			bsl.setBankFeeAmt(BankStatementLineAmounts.of(bsl)
					.addDifferenceToBankFeeAmt()
					.getBankFeeAmt());
		}

		@Override
		public void onBankFeeAmtChanged(final I_C_BankStatementLine bsl)
		{
			updateTrxAmt(bsl);
		}

		@Override
		public void onChargeAmtChanged(final I_C_BankStatementLine bsl)
		{
			updateTrxAmt(bsl);
		}

		@Override
		public void onInterestAmtChanged(final I_C_BankStatementLine bsl)
		{
			updateTrxAmt(bsl);
		}
	}

	public static class CashJournalLineAmountsCallout implements AmountsCallout
	{

		@Override
		public void onStmtAmtChanged(final I_C_BankStatementLine bsl)
		{
			updateTrxAmt(bsl);
		}

		@Override
		public void onTrxAmtChanged(final I_C_BankStatementLine bsl)
		{
			// i.e. set the TrxAmt back.
			// user shall not be allowed to change it
			// instead, StmtAmt can be changed
			updateTrxAmt(bsl);
		}

		@Override
		public void onBankFeeAmtChanged(final I_C_BankStatementLine bsl)
		{
			bsl.setBankFeeAmt(BigDecimal.ZERO);
			updateTrxAmt(bsl);
		}

		@Override
		public void onChargeAmtChanged(final I_C_BankStatementLine bsl)
		{
			bsl.setChargeAmt(BigDecimal.ZERO);
			updateTrxAmt(bsl);
		}

		@Override
		public void onInterestAmtChanged(final I_C_BankStatementLine bsl)
		{
			bsl.setInterestAmt(BigDecimal.ZERO);
			updateTrxAmt(bsl);
		}

	}
}
