package de.metas.currency;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

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
	@Nested
	public class ofInt
	{
		@Test
		public void cached()
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
		public void notCached()
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

		@Test
		public void negativeValue()
		{
			assertThatThrownBy(() -> CurrencyPrecision.ofInt(-1))
					.isInstanceOf(RuntimeException.class);
		}
	}

	@Nested
	public class roundIfNeeded
	{
		@Test
		public void rounding_not_needed()
		{
			final BigDecimal amt1_1 = new BigDecimal("1.1");
			assertThat(CurrencyPrecision.ofInt(2).roundIfNeeded(amt1_1)).isSameAs(amt1_1);
		}

		@Test
		public void rounding_is_needed()
		{
			final BigDecimal amt1_456 = new BigDecimal("1.456");
			assertThat(CurrencyPrecision.ofInt(2).roundIfNeeded(amt1_456)).isEqualTo(new BigDecimal("1.46"));
		}
	}

	@Test
	public void test_min()
	{
		final CurrencyPrecision precision_1 = CurrencyPrecision.ofInt(1);
		final CurrencyPrecision precision_2 = CurrencyPrecision.ofInt(2);

		assertThat(precision_1.min(precision_2)).isSameAs(precision_1);
		assertThat(precision_2.min(precision_1)).isSameAs(precision_1);
	}
}
