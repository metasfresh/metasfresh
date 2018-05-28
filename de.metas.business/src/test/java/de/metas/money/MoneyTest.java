package de.metas.money;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

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
	private final static Currency CURRENCY = Currency
			.builder()
			.precision(2)
			.id(CurrencyId.ofRepoId(20))
			.build();

	@Test
	public void equals()
	{
		assertThat(Money.of(new BigDecimal("4.00"), CURRENCY))
				.isEqualTo(Money.of(new BigDecimal("4"), CURRENCY));
	}

	@Test
	public void percentage()
	{
		final Money result = Money.of(new BigDecimal("200.00"), CURRENCY).percentage(Percent.of(80));
		assertThat(result.getCurrency()).isEqualTo(CURRENCY);
		assertThat(result.getValue()).isEqualByComparingTo("160");
	}

	@Test
	public void percentage_zero()
	{
		final Money result = Money.of(new BigDecimal("200.00"), CURRENCY).percentage(Percent.of(0));

		assertThat(result.getCurrency()).isEqualTo(CURRENCY);
		assertThat(result.getValue()).isEqualByComparingTo("0");
		assertThat(result.isZero()).isTrue();
	}
}
