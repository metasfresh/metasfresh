package de.metas.currency;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.junit.Test;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

public class CurrencyPrecisionTest
{
	@Test
	public void test_ofInt_cached()
	{
		for (int i = 0; i <= 12; i++)
		{
			final CurrencyPrecision precision = CurrencyPrecision.ofInt(i);
			assertThat(precision.toInt()).isEqualTo(i);

			// make sure is cached
			final CurrencyPrecision precision2 = CurrencyPrecision.ofInt(i);
			assertThat(precision2).isSameAs(precision);
		}
	}

	@Test
	public void test_ofInt_notCached()
	{
		for (int i = 13; i <= 20; i++)
		{
			final CurrencyPrecision precision = CurrencyPrecision.ofInt(i);
			assertThat(precision.toInt()).isEqualTo(i);

			// make sure is NOT cached
			final CurrencyPrecision precision2 = CurrencyPrecision.ofInt(i);
			assertThat(precision2).isNotSameAs(precision);
		}
	}

	@Test(expected = RuntimeException.class)
	public void test_ofInt_negativeValue()
	{
		CurrencyPrecision.ofInt(-1);
	}

	@Test
	public void test_roundIfNeeded()
	{
		final BigDecimal amt1_1 = new BigDecimal("1.1");
		assertThat(CurrencyPrecision.ofInt(2).roundIfNeeded(amt1_1)).isSameAs(amt1_1);

		final BigDecimal amt1_456 = new BigDecimal("1.456");
		assertThat(CurrencyPrecision.ofInt(2).roundIfNeeded(amt1_456)).isEqualTo(new BigDecimal("1.46"));
	}
}
