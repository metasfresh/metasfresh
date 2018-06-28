package de.metas.lang;

import static java.math.BigDecimal.ONE;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.math.RoundingMode;

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
	public void subtract()
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
	public void multiply()
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
	public void subtractFromBase()
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
	public void of_two_params()
	{
		assertThat(Percent.of(new BigDecimal("1"), new BigDecimal("4")).getValueAsBigDecimal()).isEqualByComparingTo("25");
		assertThat(Percent.of(new BigDecimal("4"), new BigDecimal("1")).getValueAsBigDecimal()).isEqualByComparingTo("400");

		assertThat(Percent.of(new BigDecimal("4"), new BigDecimal("0")).isZero()).isTrue();
		assertThat(Percent.of(new BigDecimal("0"), new BigDecimal("4")).isZero()).isTrue();

		assertThat(Percent.of(new BigDecimal("3"), new BigDecimal("10")).getValueAsBigDecimal()).isEqualByComparingTo("30");
		assertThat(Percent.of(new BigDecimal("1"), new BigDecimal("3")).getValueAsBigDecimal()).isEqualByComparingTo("33.33");
	}

	@Test
	public void roundToHalf()
	{
		assertThat(Percent.of(new BigDecimal("10.01")).roundToHalf(RoundingMode.HALF_UP).getValueAsBigDecimal()).isEqualByComparingTo("10.0");
		assertThat(Percent.of(new BigDecimal("10.32")).roundToHalf(RoundingMode.HALF_UP).getValueAsBigDecimal()).isEqualByComparingTo("10.5");
	}

	@Test
	public void test_hashCodeAndEquals()
	{
		assertThatHashCodeAndEqualsAreCorrect("0.0000000010000000000000", "0.000000001");
		assertThatHashCodeAndEqualsAreCorrect("1.00", "1");
		assertThatHashCodeAndEqualsAreCorrect("10.00", "10");
	}

	public void assertThatHashCodeAndEqualsAreCorrect(final String stringPercent1, final String stringPercent2)
	{
		final Percent percent1 = Percent.of(new BigDecimal(stringPercent1));
		final Percent percent2 = Percent.of(new BigDecimal(stringPercent2));

		assertThat(percent1.hashCode()).isEqualTo(percent2.hashCode());
		assertThat(percent1).isEqualTo(percent2);
	}

	@Test
	public void ofDelta()
	{
		assertThat(Percent.ofDelta(ONE, new BigDecimal("1.2")).getValueAsBigDecimal()).isEqualByComparingTo("20");
		assertThat(Percent.ofDelta(ONE, new BigDecimal("0.75")).getValueAsBigDecimal()).isEqualByComparingTo("-25");
		assertThat(Percent.ofDelta(new BigDecimal("0.75"), new BigDecimal("0.750")).getValueAsBigDecimal()).isEqualByComparingTo("0");
	}
}
