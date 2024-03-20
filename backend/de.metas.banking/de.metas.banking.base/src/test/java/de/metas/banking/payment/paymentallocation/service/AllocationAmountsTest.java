package de.metas.banking.payment.paymentallocation.service;

import de.metas.banking.payment.paymentallocation.service.AllocationAmounts.AllocationAmountsBuilder;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import org.adempiere.exceptions.AdempiereException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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

public class AllocationAmountsTest
{
	private CurrencyId euroCurrencyId;
	private AllocationAmounts allAmountFieldsSet;

	@BeforeEach
	public void beforeEach()
	{
		euroCurrencyId = CurrencyId.ofRepoId(1);

		allAmountFieldsSet = AllocationAmounts.builder()
				.payAmt(euro(999001))
				.discountAmt(euro(999002))
				.writeOffAmt(euro(999003))
				.invoiceProcessingFee(euro(999004))
				.build();
	}

	private Money euro(final int amount)
	{
		return Money.of(amount, euroCurrencyId);
	}

	@Nested
	public class builder
	{
		@Test
		public void noAmounts()
		{
			assertThatThrownBy(() -> AllocationAmounts.builder().build())
					.isInstanceOf(AdempiereException.class)
					.hasMessageStartingWith("Provide at least one amount.");
		}

		@Test
		public void payAmt()
		{
			AllocationAmounts.builder().payAmt(euro(1)).build();
		}

		@Test
		public void discountAmt()
		{
			AllocationAmounts.builder().discountAmt(euro(1)).build();
		}

		@Test
		public void writeOffAmt()
		{
			AllocationAmounts.builder().writeOffAmt(euro(1)).build();
		}

		@Test
		public void invoiceProcessingFee()
		{
			AllocationAmounts.builder().invoiceProcessingFee(euro(1)).build();
		}

		@Test
		public void differentCurrencies()
		{
			final AllocationAmountsBuilder builder = AllocationAmounts.builder()
					.discountAmt(Money.of(1, CurrencyId.EUR))
					.invoiceProcessingFee(Money.of(2, CurrencyId.USD));

			assertThatThrownBy(builder::build)
					.isInstanceOf(AdempiereException.class)
					.hasMessageStartingWith("All given Money(s) shall have the same currency");
		}
	}

	@Test
	public void add()
	{
		final AllocationAmounts amounts = allAmountFieldsSet.toBuilder()
				.payAmt(euro(1))
				.discountAmt(euro(2))
				.writeOffAmt(euro(3))
				.invoiceProcessingFee(euro(4))
				.build();

		assertThat(amounts.add(amounts))
				.usingRecursiveComparison()
				.isEqualTo(AllocationAmounts.builder()
						.payAmt(euro(2))
						.discountAmt(euro(4))
						.writeOffAmt(euro(6))
						.invoiceProcessingFee(euro(8))
						.build());
	}

	@Test
	public void subtract()
	{
		final AllocationAmounts amounts = allAmountFieldsSet.toBuilder()
				.payAmt(euro(1))
				.discountAmt(euro(2))
				.writeOffAmt(euro(3))
				.invoiceProcessingFee(euro(4))
				.build();

		assertThat(amounts.subtract(amounts))
				.usingRecursiveComparison()
				.isEqualTo(AllocationAmounts.zero(euroCurrencyId));
	}

	@Test
	public void negate()
	{
		final AllocationAmounts amounts = allAmountFieldsSet.toBuilder()
				.payAmt(euro(1))
				.discountAmt(euro(2))
				.writeOffAmt(euro(3))
				.invoiceProcessingFee(euro(4))
				.build();

		assertThat(amounts.negate())
				.usingRecursiveComparison()
				.isEqualTo(AllocationAmounts.builder()
						.payAmt(euro(-1))
						.discountAmt(euro(-2))
						.writeOffAmt(euro(-3))
						.invoiceProcessingFee(euro(-4))
						.build());
	}

	@Test
	public void getTotalAmt()
	{
		final AllocationAmounts amounts = allAmountFieldsSet.toBuilder()
				.payAmt(euro(1))
				.discountAmt(euro(2))
				.writeOffAmt(euro(3))
				.invoiceProcessingFee(euro(4))
				.build();

		assertThat(amounts.getTotalAmt()).isEqualTo(euro(1 + 2 + 3 + 4));
	}

	@Test
	public void toZero()
	{
		assertThat(allAmountFieldsSet.isZero()).isFalse();
		assertThat(allAmountFieldsSet.toZero().isZero()).isTrue();
	}

	@Test
	public void withZeroPayAmt()
	{
		final AllocationAmounts amounts = AllocationAmounts.ofPayAmt(euro(123));
		assertThat(amounts.getPayAmt()).isEqualTo(euro(123));

		final AllocationAmounts zero = amounts.withZeroPayAmt();
		assertThat(zero.getPayAmt()).isEqualTo(euro(0));
	}

	@Nested
	public class isZero
	{
		private AllocationAmounts zeroAmounts;

		@BeforeEach
		public void beforeEach()
		{
			zeroAmounts = AllocationAmounts.zero(euroCurrencyId);
		}

		@Test
		public void zero()
		{
			assertThat(zeroAmounts.isZero()).isTrue();
		}

		@Test
		public void withPayAmtSet()
		{
			final AllocationAmounts amounts = zeroAmounts.withPayAmt(euro(1));
			assertThat(amounts.isZero()).isFalse();
		}

		@Test
		public void withDiscountAmtSet()
		{
			final AllocationAmounts amounts = zeroAmounts.withDiscountAmt(euro(1));
			assertThat(amounts.isZero()).isFalse();
		}

		@Test
		public void withWriteOffAmtSet()
		{
			final AllocationAmounts amounts = zeroAmounts.withWriteOffAmt(euro(1));
			assertThat(amounts.isZero()).isFalse();
		}

		@Test
		public void withInvoiceProcessingFee()
		{
			final AllocationAmounts amounts = zeroAmounts.withInvoiceProcessingFee(euro(1));
			assertThat(amounts.isZero()).isFalse();
		}

		@Test
		public void fromAmountsFullySetToZero()
		{
			AllocationAmounts amounts = allAmountFieldsSet;
			assertThat(amounts.isZero()).isFalse();

			amounts = amounts.withPayAmt(euro(0));
			assertThat(amounts.isZero()).isFalse();

			amounts = amounts.withDiscountAmt(euro(0));
			assertThat(amounts.isZero()).isFalse();

			amounts = amounts.withWriteOffAmt(euro(0));
			assertThat(amounts.isZero()).isFalse();

			amounts = amounts.withInvoiceProcessingFee(euro(0));
			// assertThat(amounts.isZero()).isTrue(); // checked below

			//
			// All amounts are set to zero:
			assertThat(amounts.isZero()).isTrue();
			assertThat(amounts.getTotalAmt()).isEqualTo(Money.zero(euroCurrencyId));
		}
	}
}
