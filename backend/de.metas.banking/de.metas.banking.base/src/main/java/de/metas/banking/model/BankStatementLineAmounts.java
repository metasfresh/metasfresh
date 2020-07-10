package de.metas.banking.model;

import java.math.BigDecimal;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_BankStatementLine;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

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

@Value
public class BankStatementLineAmounts
{
	public static BankStatementLineAmounts of(@NonNull final I_C_BankStatementLine bsl)
	{
		return BankStatementLineAmounts.builder()
				.stmtAmt(bsl.getStmtAmt())
				.trxAmt(bsl.getTrxAmt())
				.bankFeeAmt(bsl.getBankFeeAmt())
				.chargeAmt(bsl.getChargeAmt())
				.interestAmt(bsl.getInterestAmt())
				.build();
	}

	BigDecimal stmtAmt;

	BigDecimal trxAmt;
	BigDecimal bankFeeAmt;
	BigDecimal chargeAmt;
	BigDecimal interestAmt;

	BigDecimal differenceAmt;

	@Builder(toBuilder = true)
	private BankStatementLineAmounts(
			@NonNull final BigDecimal stmtAmt,
			final BigDecimal trxAmt,
			final BigDecimal bankFeeAmt,
			final BigDecimal chargeAmt,
			final BigDecimal interestAmt)
	{
		this.stmtAmt = stmtAmt;

		this.trxAmt = trxAmt != null ? trxAmt : BigDecimal.ZERO;
		this.bankFeeAmt = bankFeeAmt != null ? bankFeeAmt : BigDecimal.ZERO;
		this.chargeAmt = chargeAmt != null ? chargeAmt : BigDecimal.ZERO;
		this.interestAmt = interestAmt != null ? interestAmt : BigDecimal.ZERO;

		//
		final BigDecimal stmtAmtComputed = this.trxAmt
				.subtract(this.bankFeeAmt)
				.add(this.chargeAmt)
				.add(this.interestAmt);
		this.differenceAmt = this.stmtAmt.subtract(stmtAmtComputed);
	}

	public boolean hasDifferences()
	{
		return differenceAmt.signum() != 0;
	}

	public BankStatementLineAmounts assertNoDifferences()
	{
		if (differenceAmt.signum() != 0)
		{
			throw new AdempiereException("Amounts are not completelly balanced: " + this);
		}

		return this;
	}

	public BankStatementLineAmounts addDifferenceToTrxAmt()
	{
		if (differenceAmt.signum() == 0)
		{
			return this;
		}

		return toBuilder()
				.trxAmt(this.trxAmt.add(differenceAmt))
				.build();
	}

	public BankStatementLineAmounts withTrxAmt(@NonNull final BigDecimal trxAmt)
	{
		return !this.trxAmt.equals(trxAmt)
				? toBuilder().trxAmt(trxAmt).build()
				: this;
	}

	public BankStatementLineAmounts addDifferenceToBankFeeAmt()
	{
		if (differenceAmt.signum() == 0)
		{
			return this;
		}

		return toBuilder()
				.bankFeeAmt(this.bankFeeAmt.subtract(differenceAmt))
				.build();
	}

	public BankStatementLineAmounts withBankFeeAmt(@NonNull final BigDecimal bankFeeAmt)
	{
		return !this.bankFeeAmt.equals(bankFeeAmt)
				? toBuilder().bankFeeAmt(bankFeeAmt).build()
				: this;
	}
}
