package de.metas.money;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Before;
import org.junit.Test;

import de.metas.lang.Percent;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2018 metas GmbH
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

public class MoneyTest
{
	private final AtomicInteger nextCurrencyId = new AtomicInteger(1);

	private Currency EUR;
	private Currency CHF;

	@Before
	public void init()
	{
		EUR = createCurrency("EUR");
		CHF = createCurrency("CHF");
	}

	private Currency createCurrency(final String threeLetterCode)
	{
		return Currency
				.builder()
				.threeLetterCode(threeLetterCode)
				.precision(2)
				.id(CurrencyId.ofRepoId(nextCurrencyId.getAndIncrement()))
				.build();
	}

	@Test
	public void equals()
	{
		assertThat(Money.of(new BigDecimal("4.00"), EUR))
				.isEqualTo(Money.of(new BigDecimal("4"), EUR));
	}

	@Test
	public void percentage()
	{
		final Money result = Money.of(new BigDecimal("200.00"), EUR).percentage(Percent.of(80));
		assertThat(result.getCurrency()).isEqualTo(EUR);
		assertThat(result.getValue()).isEqualByComparingTo("160");
	}

	@Test
	public void percentage_zero()
	{
		final Money result = Money.of(new BigDecimal("200.00"), EUR).percentage(Percent.of(0));

		assertThat(result.getCurrency()).isEqualTo(EUR);
		assertThat(result.getValue()).isEqualByComparingTo("0");
		assertThat(result.isZero()).isTrue();
	}

	@Test
	public void test_getCommonCurrencyOfAll()
	{
		assertGetCommonCurrencyOfAllReturns(EUR, Money.of(1, EUR));
		assertGetCommonCurrencyOfAllReturns(EUR, Money.of(1, EUR), Money.of(2, EUR));
		assertGetCommonCurrencyOfAllReturns(EUR, Money.of(1, EUR), null, Money.of(2, EUR), null);

		assertGetCommonCurrencyOfAllFails();
		assertGetCommonCurrencyOfAllFails(null, null, null);
		assertGetCommonCurrencyOfAllFails(Money.of(1, EUR), Money.of(1, CHF));
	}

	public void assertGetCommonCurrencyOfAllReturns(final Currency expectedCurrency, final Money... moneys)
	{
		final Currency actualCurrency = Money.getCommonCurrencyOfAll(moneys);
		assertThat(actualCurrency).isEqualTo(expectedCurrency);
	}

	public void assertGetCommonCurrencyOfAllFails(final Money... moneys)
	{
		assertThatThrownBy(() -> Money.getCommonCurrencyOfAll(moneys))
				.isNotNull();
	}

	@Test
	public void testMinMax()
	{
		final Money money_1EUR = Money.of(1, EUR);
		final Money money_2EUR = Money.of(2, EUR);
		final Money money_2CHF = Money.of(2, CHF);

		assertThat(money_1EUR.min(money_2EUR)).isSameAs(money_1EUR);
		assertThat(money_2EUR.min(money_1EUR)).isSameAs(money_1EUR);
		assertThat(money_1EUR.max(money_2EUR)).isSameAs(money_2EUR);
		assertThat(money_2EUR.max(money_1EUR)).isSameAs(money_2EUR);

		assertThatThrownBy(() -> money_1EUR.min(money_2CHF)).isNotNull();
		assertThatThrownBy(() -> money_1EUR.max(money_2CHF)).isNotNull();
	}

	@Test
	public void testSubtactPercentage()
	{
		final Money money_100EUR = Money.of(100, EUR);
		assertThat(money_100EUR.subtract(Percent.of(0))).isSameAs(money_100EUR);
		assertThat(money_100EUR.subtract(Percent.of(30))).isEqualTo(Money.of(70, EUR));

		final Money money_0EUR = Money.of(0, EUR);
		assertThat(money_0EUR.subtract(Percent.of(55))).isSameAs(money_0EUR);
	}
}
