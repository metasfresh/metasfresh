package de.metas.lang;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.junit.Test;

/*
 * #%L
 * de.metas.util
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

public class PercentTest
{
	@Test
	public void test_subtract()
	{
		test_subtract(100, 0, 100);
		test_subtract(100, 45, 55);
		test_subtract(100, 100, 0);
	}

	private void test_subtract(final int percentInt1, final int percentInt2, final int expectedResult)
	{
		final Percent percent1 = Percent.of(percentInt1);
		final Percent percent2 = Percent.of(percentInt2);
		assertThat(percent1.subtract(percent2)).isEqualTo(Percent.of(expectedResult));
	}

	@Test
	public void test_multiply()
	{
		test_multiply(100, 0, 0);
		test_multiply(100, 45, 45);
		test_multiply(100, 100, 100);
	}

	private void test_multiply(final int base, final int percentInt, final int expectedResult)
	{
		final Percent percent = Percent.of(percentInt);
		final int precision = 2;
		assertThat(percent.multiply(BigDecimal.valueOf(base), precision)).isEqualByComparingTo(BigDecimal.valueOf(expectedResult));
	}

	@Test
	public void test_subtractFromBase()
	{
		test_subtractFromBase(100, 0, 100);
		test_subtractFromBase(100, 45, 55);
		test_subtractFromBase(100, 100, 0);
	}

	private void test_subtractFromBase(final int base, final int percentInt, final int expectedResult)
	{
		final Percent percent = Percent.of(percentInt);
		final int precision = 2;
		assertThat(percent.subtractFromBase(BigDecimal.valueOf(base), precision)).isEqualByComparingTo(BigDecimal.valueOf(expectedResult));
	}

	@Test
	public void test_hashCodeAndEquals()
	{
		test_hashCodeAndEquals("0.0000000010000000000000", "0.000000001");
		test_hashCodeAndEquals("1.00", "1");
		test_hashCodeAndEquals("10.00", "10");
	}

	public void test_hashCodeAndEquals(final String stringPercent1, final String stringPercent2)
	{
		final Percent percent1 = Percent.of(new BigDecimal(stringPercent1));
		final Percent percent2 = Percent.of(new BigDecimal(stringPercent2));

		assertThat(percent1.hashCode()).isEqualTo(percent2.hashCode());
		assertThat(percent1).isEqualTo(percent2);
	}

}
