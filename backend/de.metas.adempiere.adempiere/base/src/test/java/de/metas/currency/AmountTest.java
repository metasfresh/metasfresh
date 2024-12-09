package de.metas.currency;

<<<<<<< HEAD
import com.google.common.collect.ImmutableMap;
=======
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import de.metas.JsonObjectMapperHolder;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import org.adempiere.exceptions.AdempiereException;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
<<<<<<< HEAD

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.*;
=======
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2019 metas GmbH
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

public class AmountTest
{
	private static Amount euro(final int amt)
	{
		return euro(String.valueOf(amt));
	}

	private static Amount euro(final String amt)
	{
		return amt(amt, "EUR");
	}

	private static Amount usd(final int amt)
	{
		return amt(String.valueOf(amt), "USD");
	}

	private static Amount amt(final String amt, final String currencyCode)
	{
		return Amount.of(new BigDecimal(amt), CurrencyCode.ofThreeLetterCode(currencyCode));
	}

	private static void assertThrowsAmountShallHaveSameCurrencyException(final ThrowingCallable callable)
	{
		assertThatThrownBy(callable)
				.isInstanceOf(AdempiereException.class)
				.hasMessageStartingWith("Amounts shall have the same currency");
	}

	@Nested
	class testEquals
	{
		@Test
		public void amountsWithDifferentPrecisionsAreEqual()
		{
			assertThat(euro("11.00000000000000000")).isEqualTo(euro("11"));
		}
	}

	@Nested
	class assertSameCurrency
	{
		@Test
		void emptyParams()
		{
			Amount.assertSameCurrency();
		}

		@Test
		void oneParams()
		{
			Amount.assertSameCurrency(euro(1));
		}

		@Test
		void trueCases()
		{
			Amount.assertSameCurrency(euro(1), euro(2));
			Amount.assertSameCurrency(euro(1), euro(2), euro(3));
		}

		@Test
		void falseCases()
		{
			assertThrowsAmountShallHaveSameCurrencyException(() -> Amount.assertSameCurrency(euro(1), usd(2)));
			assertThrowsAmountShallHaveSameCurrencyException(() -> Amount.assertSameCurrency(euro(1), euro(2), euro(3), usd(4)));
		}
	}

	@Test
	public void test_compareTo()
	{
		assertThat(euro(5))
				.isLessThan(euro(6))
				.isGreaterThan(euro(4));
		//noinspection ResultOfMethodCallIgnored
		assertThrowsAmountShallHaveSameCurrencyException(() -> euro(5).compareTo(usd(5)));
	}

	@Nested
	class abs
	{
		@Test
		void negativeAmount()
		{
			assertThat(euro(-10).abs()).isEqualTo(euro(10));
		}

		@Test
		void zeroAmount()
		{
			final Amount amt = euro(0);
			assertThat(amt.abs()).isSameAs(amt);
		}

		@Test
		void positiveAmount()
		{
			final Amount amt = euro(10);
			assertThat(amt.abs()).isSameAs(amt);
		}
	}

	@Nested
	class toMoney
	{
		@Test
		void test()
		{
			final Amount amt = Amount.of("1.23456789", CurrencyCode.EUR);
			final ImmutableMap<CurrencyCode, CurrencyId> currencyIdByCode = ImmutableMap.of(CurrencyCode.EUR, CurrencyId.ofRepoId(555));
			final Money money = amt.toMoney(currencyIdByCode::get);
			assertThat(money).isEqualTo(Money.of("1.23456789", CurrencyId.ofRepoId(555)));
		}
	}
<<<<<<< HEAD
=======

	@ParameterizedTest
	@ValueSource(strings = { "123.45 EUR", "-54.321 CHF", "0 RON", "0.00005 HUF" })
	void testSerializeDeserialize(final String expectedJson) throws JsonProcessingException
	{
		System.out.println("expected json: " + expectedJson);

		final ObjectMapper jsonObjectMapper = JsonObjectMapperHolder.newJsonObjectMapper();
		final Amount expectedAmount = Amount.fromJson(expectedJson);
		System.out.println("expected amount: " + expectedAmount);

		final String actualJson = jsonObjectMapper.writeValueAsString(expectedAmount);
		System.out.println("actual json: " + actualJson);
		assertThat(actualJson).isEqualTo("\"" + expectedJson + "\"");

		final Amount actualAmount = jsonObjectMapper.readValue(actualJson, Amount.class);
		System.out.println("actual amount: " + actualAmount);
		assertThat(actualAmount).usingRecursiveComparison().isEqualTo(expectedAmount);
		assertThat(actualAmount).isEqualTo(expectedAmount);
	}
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
}
