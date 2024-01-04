/*
 * #%L
 * de-metas-common-util
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.common.util;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.*;

class NumberUtilsTest
{
	@Test
	void asBigDecimalList()
	{
		assertThat(NumberUtils.asBigDecimalList(",", ",")).isEmpty();
		assertThat(NumberUtils.asBigDecimalList("1,2", ",")).containsExactly(new BigDecimal("1"), new BigDecimal("2"));
		assertThat(NumberUtils.asBigDecimalList("1, 2", ",")).containsExactly(new BigDecimal("1"), new BigDecimal("2"));
		assertThat(NumberUtils.asBigDecimalList("1, 2, ", ",")).containsExactly(new BigDecimal("1"), new BigDecimal("2"));
	}

	@Nested
	class asInt
	{
		@Test
		void from_int()
		{
			assertThat(NumberUtils.asInt(123)).isEqualTo(123);
		}

		@Test
		void from_BigDecimal()
		{
			assertThat(NumberUtils.asInt(new BigDecimal("123"))).isEqualTo(123);
		}

		@Test
		void from_String()
		{
			assertThat(NumberUtils.asInt("123")).isEqualTo(123);
		}

		@Test
		void from_InvalidString()
		{
			assertThatThrownBy(() -> NumberUtils.asInt("123aaa"))
					.isInstanceOf(RuntimeException.class)
					.hasMessageStartingWith("Cannot convert `123aaa` (class java.lang.String) to int");
		}

	}

	@Nested
	class roundToBigDecimal
	{
		@Test
		void givenIntegerNumberRoundToFractionalNumber_returnTheSameNumber()
		{
			assertThat(NumberUtils.roundToBigDecimal(BigDecimal.TEN, new BigDecimal("0.658"))).isEqualTo(BigDecimal.TEN);
		}

		@Test
		void given102RoundTo5_return100()
		{
			assertThat(NumberUtils.roundToBigDecimal(new BigDecimal("102"), new BigDecimal("5"))).isEqualTo(new BigDecimal("100"));
		}

		@Test
		void given103RoundTo5_return105()
		{
			assertThat(NumberUtils.roundToBigDecimal(new BigDecimal("103"), new BigDecimal("5"))).isEqualTo(new BigDecimal("105"));
		}

		@Test
		void given10Dot3RoundTo0Dot5_return10Dot5()
		{
			assertThat(NumberUtils.roundToBigDecimal(new BigDecimal("10.3"), new BigDecimal("0.5"))).isEqualTo(new BigDecimal("10.5"));
		}
	}
}