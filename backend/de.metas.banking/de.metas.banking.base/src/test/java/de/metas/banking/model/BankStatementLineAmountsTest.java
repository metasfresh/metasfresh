package de.metas.banking.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

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

public class BankStatementLineAmountsTest
{
	@Nested
	public class hasDifferences
	{
		@Test
		public void stmt100_trx100()
		{
			final BankStatementLineAmounts amounts = BankStatementLineAmounts.builder()
					.stmtAmt(new BigDecimal("100"))
					.trxAmt(new BigDecimal("100"))
					.build();
			assertThat(amounts.hasDifferences()).isFalse();
		}

		@Test
		public void stmt100_trx90()
		{
			final BankStatementLineAmounts amounts = BankStatementLineAmounts.builder()
					.stmtAmt(new BigDecimal("100"))
					.trxAmt(new BigDecimal("90"))
					.build();
			assertThat(amounts.hasDifferences()).isTrue();
		}

		@Test
		public void stmt100_trx90_fee10()
		{
			final BankStatementLineAmounts amounts = BankStatementLineAmounts.builder()
					.stmtAmt(new BigDecimal("100"))
					.trxAmt(new BigDecimal("110"))
					.bankFeeAmt(new BigDecimal("10"))
					.build();
			assertThat(amounts.hasDifferences()).isFalse();
		}

		@Test
		public void stmt100_trx90_fee1()
		{
			final BankStatementLineAmounts amounts = BankStatementLineAmounts.builder()
					.stmtAmt(new BigDecimal("100"))
					.trxAmt(new BigDecimal("90"))
					.bankFeeAmt(new BigDecimal("1"))
					.build();
			assertThat(amounts.hasDifferences()).isTrue();
		}
	}

	@Nested
	public class addDifferenceToTrxAmt
	{
		@Test
		public void noDifference()
		{
			final BankStatementLineAmounts amounts = BankStatementLineAmounts.builder()
					.stmtAmt(new BigDecimal("100"))
					.trxAmt(new BigDecimal("100"))
					.build();

			assertThat(amounts.addDifferenceToTrxAmt()).isSameAs(amounts);
		}

		@Test
		public void stmt100_trx90()
		{
			final BankStatementLineAmounts amounts = BankStatementLineAmounts.builder()
					.stmtAmt(new BigDecimal("100"))
					.trxAmt(new BigDecimal("90"))
					.build();

			assertThat(amounts.addDifferenceToTrxAmt())
					.isEqualTo(amounts.withTrxAmt(new BigDecimal("100")));
		}

		@Test
		public void stmt100_trx90_fee10_charge9_interest2()
		{
			final BankStatementLineAmounts amounts = BankStatementLineAmounts.builder()
					.stmtAmt(new BigDecimal("100"))
					.trxAmt(new BigDecimal("9999999999"))
					.bankFeeAmt(new BigDecimal("10"))
					.chargeAmt(new BigDecimal("9"))
					.interestAmt(new BigDecimal("2"))
					.build();

			assertThat(amounts.addDifferenceToTrxAmt())
					.isEqualTo(amounts.withTrxAmt(new BigDecimal("99")));
		}

	}

	@Nested
	public class addDifferencesToBankFee
	{
		@Test
		public void noDifference()
		{
			final BankStatementLineAmounts amounts = BankStatementLineAmounts.builder()
					.stmtAmt(new BigDecimal("100"))
					.trxAmt(new BigDecimal("100"))
					.build();

			assertThat(amounts.addDifferenceToBankFeeAmt()).isSameAs(amounts);
		}

		@Test
		public void stmt90_trx100()
		{
			final BankStatementLineAmounts amounts = BankStatementLineAmounts.builder()
					.stmtAmt(new BigDecimal("90"))
					.trxAmt(new BigDecimal("100"))
					.build();

			assertThat(amounts.addDifferenceToBankFeeAmt())
					.isEqualTo(amounts.withBankFeeAmt(new BigDecimal("10")));
		}
	}
}
