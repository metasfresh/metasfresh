package de.metas.money;

import static org.assertj.core.api.Assertions.assertThat;

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

public class MoneyServiceTest
{
	private static final AtomicInteger NEXT_CURRENCY_ID = new AtomicInteger(1);

	private static final Currency EUR = createCurrency("EUR");

	private static final Money SEVENTY_EURO = Money.of(70, EUR.getId());

	private static final Money ZERO_EURO = Money.of(0, EUR.getId());

	private static final Money ONEHUNDRET_EURO = Money.of(100, EUR.getId());

	private static final Money TWOHUNDRED_EURO = Money.of(new BigDecimal("200.00"), EUR.getId());

	private MoneyService moneyService;

	@Before
	public void init()
	{
		moneyService = new MoneyService(new CurrencyRepository());
	}

	private static Currency createCurrency(final String threeLetterCode)
	{
		return Currency
				.builder()
				.threeLetterCode(threeLetterCode)
				.precision(2)
				.id(CurrencyId.ofRepoId(NEXT_CURRENCY_ID.getAndIncrement()))
				.build();
	}

	@Test
	public void percentage()
	{
		final Money result = moneyService.percentage(Percent.of(80), TWOHUNDRED_EURO);

		assertThat(result.getCurrencyId()).isEqualTo(EUR.getId());
		assertThat(result.getValue()).isEqualByComparingTo("160");
	}

	@Test
	public void percentage_zero()
	{
		final Money result = moneyService.percentage(Percent.of(0), TWOHUNDRED_EURO);

		assertThat(result.getCurrencyId()).isEqualTo(EUR);
		assertThat(result).isEqualTo(ZERO_EURO);
		assertThat(result.isZero()).isTrue();
	}

	@Test
	public void testSubtactPercentage()
	{
		assertThat(moneyService.subtractPercent(Percent.of(0), ONEHUNDRET_EURO)).isSameAs(ONEHUNDRET_EURO);

		assertThat(moneyService.subtractPercent(Percent.of(30), ONEHUNDRET_EURO)).isSameAs(SEVENTY_EURO);

		assertThat(moneyService.subtractPercent(Percent.of(55), ZERO_EURO)).isSameAs(ZERO_EURO);

	}
}
