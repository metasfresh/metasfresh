package de.metas.banking.callout;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_BankStatementLine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.metas.banking.model.BankStatementLineAmounts;
import lombok.Builder;

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

public class C_BankStatementLine_Test
{
	private C_BankStatementLine callout;

	@BeforeEach
	public void beforeEach()
	{
		AdempiereTestHelper.get().init();
		callout = C_BankStatementLine.instance;
	}

	@Builder(builderMethodName = "bankStatementLine", builderClassName = "$BankStatementLineBuilder")
	private I_C_BankStatementLine createBankStatementLine(
			final String stmtAmt,
			final String trxAmt,
			final String bankFeeAmt,
			final String chargeAmt,
			final String interestAmt)
	{
		final I_C_BankStatementLine bsl = newInstance(I_C_BankStatementLine.class);

		bsl.setStmtAmt(toBigDecimal(stmtAmt));
		bsl.setTrxAmt(toBigDecimal(trxAmt));
		bsl.setBankFeeAmt(toBigDecimal(bankFeeAmt));
		bsl.setChargeAmt(toBigDecimal(chargeAmt));
		bsl.setInterestAmt(toBigDecimal(interestAmt));

		return bsl;
	}

	private static BigDecimal toBigDecimal(final String value)
	{
		return value != null ? new BigDecimal(value) : null;
	}

	@Test
	public void onStmtAmtChanged()
	{
		final I_C_BankStatementLine bsl = bankStatementLine()
				.stmtAmt("100")
				.bankFeeAmt("10")
				.build();

		callout.onStmtAmtChanged(bsl);

		assertThat(BankStatementLineAmounts.of(bsl))
				.isEqualTo(BankStatementLineAmounts.builder()
						.stmtAmt(new BigDecimal("100"))
						.trxAmt(new BigDecimal("110"))
						.bankFeeAmt(new BigDecimal("10"))
						.build());
	}

	@Test
	public void onTrxAmtChanged()
	{
		final I_C_BankStatementLine bsl = bankStatementLine()
				.stmtAmt("100")
				.trxAmt("90")
				.build();

		callout.onTrxAmtChanged(bsl);

		assertThat(BankStatementLineAmounts.of(bsl))
				.isEqualTo(BankStatementLineAmounts.builder()
						.stmtAmt(new BigDecimal("100"))
						.trxAmt(new BigDecimal("90"))
						.bankFeeAmt(new BigDecimal("-10"))
						.build());
	}

	@Test
	public void onBankFeeAmtChanged()
	{
		final I_C_BankStatementLine bsl = bankStatementLine()
				.stmtAmt("100")
				.trxAmt("100")
				.bankFeeAmt("10")
				.build();

		callout.onBankFeeAmtChanged(bsl);

		assertThat(BankStatementLineAmounts.of(bsl))
				.isEqualTo(BankStatementLineAmounts.builder()
						.stmtAmt(new BigDecimal("100"))
						.trxAmt(new BigDecimal("110"))
						.bankFeeAmt(new BigDecimal("10"))
						.build());
	}

	@Test
	public void onChargeAmtChanged()
	{
		final I_C_BankStatementLine bsl = bankStatementLine()
				.stmtAmt("100")
				.trxAmt("100")
				.bankFeeAmt("10")
				.chargeAmt("9")
				.build();

		callout.onChargeAmtChanged(bsl);

		assertThat(BankStatementLineAmounts.of(bsl))
				.isEqualTo(BankStatementLineAmounts.builder()
						.stmtAmt(new BigDecimal("100"))
						.trxAmt(new BigDecimal("101"))
						.bankFeeAmt(new BigDecimal("10"))
						.chargeAmt(new BigDecimal("9"))
						.build());
	}

	@Test
	public void onInterestAmtChanged()
	{
		final I_C_BankStatementLine bsl = bankStatementLine()
				.stmtAmt("100")
				.trxAmt("100")
				.bankFeeAmt("10")
				.chargeAmt("9")
				.interestAmt("2")
				.build();

		callout.onInterestAmtChanged(bsl);

		assertThat(BankStatementLineAmounts.of(bsl))
				.isEqualTo(BankStatementLineAmounts.builder()
						.stmtAmt(new BigDecimal("100"))
						.trxAmt(new BigDecimal("99"))
						.bankFeeAmt(new BigDecimal("10"))
						.chargeAmt(new BigDecimal("9"))
						.interestAmt(new BigDecimal("2"))
						.build());
	}
}
