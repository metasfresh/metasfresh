package de.metas.banking.payment.paymentallocation.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import de.metas.money.CurrencyId;
import de.metas.money.Money;

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
				.build();
	}

	private Money euro(final int amount)
	{
		return Money.of(amount, euroCurrencyId);
	}

	@Test
	public void add()
	{
		final AllocationAmounts amounts = allAmountFieldsSet.toBuilder()
				.payAmt(euro(1))
				.discountAmt(euro(2))
				.writeOffAmt(euro(3))
				.build();

		assertThat(amounts.add(amounts))
				.isEqualToComparingFieldByField(AllocationAmounts.builder()
						.payAmt(euro(2))
						.discountAmt(euro(4))
						.writeOffAmt(euro(6))
						.build());
	}

	@Test
	public void remove()
	{
		final AllocationAmounts amounts = allAmountFieldsSet.toBuilder()
				.payAmt(euro(1))
				.discountAmt(euro(2))
				.writeOffAmt(euro(3))
				.build();

		assertThat(amounts.subtract(amounts))
				.isEqualToComparingFieldByField(AllocationAmounts.zero(euroCurrencyId));
	}

	@Test
	public void negate()
	{
		final AllocationAmounts amounts = allAmountFieldsSet.toBuilder()
				.payAmt(euro(1))
				.discountAmt(euro(2))
				.writeOffAmt(euro(3))
				.build();

		assertThat(amounts.negate())
				.isEqualToComparingFieldByField(AllocationAmounts.builder()
						.payAmt(euro(-1))
						.discountAmt(euro(-2))
						.writeOffAmt(euro(-3))
						.build());
	}

	@Test
	public void getTotalAmt()
	{
		final AllocationAmounts amounts = allAmountFieldsSet.toBuilder()
				.payAmt(euro(1))
				.discountAmt(euro(2))
				.writeOffAmt(euro(3))
				.build();

		assertThat(amounts.getTotalAmt()).isEqualTo(euro(1 + 2 + 3));
	}

	@Test
	public void toZero()
	{
		assertThat(allAmountFieldsSet.isZero()).isFalse();
		assertThat(allAmountFieldsSet.toZero().isZero()).isTrue();
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
		public void fromAmountsFullySetToZero()
		{
			AllocationAmounts amounts = allAmountFieldsSet;
			assertThat(amounts.isZero()).isFalse();

			amounts = amounts.withPayAmt(euro(0));
			assertThat(amounts.isZero()).isFalse();

			amounts = amounts.withDiscountAmt(euro(0));
			assertThat(amounts.isZero()).isFalse();

			amounts = amounts.withWriteOffAmt(euro(0));

			assertThat(amounts.isZero()).isTrue();
		}
	}
}
