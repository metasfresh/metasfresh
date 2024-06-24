package de.metas.money;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.metas.JsonObjectMapperHolder;
import lombok.NonNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.*;

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

	private CurrencyId EUR;
	private CurrencyId CHF;

	@BeforeEach
	public void init()
	{
		EUR = CurrencyId.ofRepoId(10);
		CHF = CurrencyId.ofRepoId(20);
	}

	@Test
	public void equals()
	{
		assertThat(Money.of(new BigDecimal("4.00"), EUR))
				.isEqualTo(Money.of(new BigDecimal("4"), EUR));
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

	private void assertGetCommonCurrencyOfAllReturns(
			@NonNull final CurrencyId expectedCurrencyId,
			final Money... moneys)
	{
		final CurrencyId actualCurrency = Money.getCommonCurrencyIdOfAll(moneys);
		assertThat(actualCurrency).isEqualTo(expectedCurrencyId);
	}

	private void assertGetCommonCurrencyOfAllFails(final Money... moneys)
	{
		assertThatThrownBy(() -> Money.getCommonCurrencyIdOfAll(moneys))
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
	public void testJsonSerialization() throws Exception
	{
		testJsonSerialization(Money.of(new BigDecimal("13.14"), CurrencyId.ofRepoId(55)));
	}

	private void testJsonSerialization(final Money money) throws Exception
	{
		final ObjectMapper objectMapper = JsonObjectMapperHolder.newJsonObjectMapper();
		final String json = objectMapper.writeValueAsString(money);
		System.out.println("Serialized " + money + " to " + json);

		final Money moneyDeserialized = objectMapper.readValue(json, Money.class);
		assertThat(moneyDeserialized).isEqualTo(money);
	}

	@Test
	public void test_isLessThan()
	{
		final Money money_1EUR = Money.of(1, EUR);
		final Money money_2EUR = Money.of(2, EUR);
		final Money money_2CHF = Money.of(2, CHF);

		assertThat(money_1EUR.isLessThan(money_2EUR)).isTrue();
		assertThat(money_2EUR.isLessThan(money_2EUR)).isFalse();
		assertThat(money_2EUR.isLessThan(money_1EUR)).isFalse();
		assertThatThrownBy(() -> money_1EUR.isLessThan(money_2CHF)).isNotNull();
	}

	@Test
	public void test_isLessThanOrEqualTo()
	{
		final Money money_1EUR = Money.of(1, EUR);
		final Money money_2EUR = Money.of(2, EUR);
		final Money money_2CHF = Money.of(2, CHF);

		assertThat(money_1EUR.isLessThanOrEqualTo(money_2EUR)).isTrue();
		assertThat(money_2EUR.isLessThanOrEqualTo(money_2EUR)).isTrue();
		assertThat(money_2EUR.isLessThanOrEqualTo(money_1EUR)).isFalse();
		assertThatThrownBy(() -> money_1EUR.isLessThanOrEqualTo(money_2CHF)).isNotNull();
	}

	@Nested
	public class abs
	{
		@Test
		void zero()
		{
			final Money zero = Money.zero(EUR);
			assertThat(zero.abs()).isSameAs(zero);
		}

		@Test
		void positive()
		{
			final Money amt = Money.of(123, EUR);
			assertThat(amt.abs()).isSameAs(amt);
		}

		@Test
		void negative()
		{
			final Money amt = Money.of(-123, EUR);
			assertThat(amt.abs()).isEqualTo(Money.of(123, EUR));
		}

	}
}
