package de.metas.costing;

import de.metas.money.CurrencyId;
import org.adempiere.exceptions.AdempiereException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/*
 * #%L
 * de.metas.business
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

public class CostAmountTest
{
	private final CurrencyId currencyId1 = CurrencyId.ofRepoId(123);
	private final CurrencyId currencyId2 = CurrencyId.ofRepoId(4);
	private final CurrencyId currencyId3 = CurrencyId.ofRepoId(5);

	private CostAmount amt(final String amountStr)
	{
		return amt(amountStr, currencyId1);
	}

	private CostAmount amt(final String amountStr, final CurrencyId currencyId)
	{
		return CostAmount.of(new BigDecimal(amountStr), currencyId);
	}

	private CostAmount amt(final String amountStr, final CurrencyId currencyId, final String sourceAmtStr, final CurrencyId sourceCurrencyId)
	{
		return CostAmount.of(
				new BigDecimal(amountStr),
				currencyId,
				sourceAmtStr != null ? new BigDecimal(sourceAmtStr) : null,
				sourceCurrencyId);
	}

	@Test
	void testEquals()
	{
		assertThat(amt("10.000000")).isEqualTo(amt("10"));
		assertThat(amt("10.00001000000")).isEqualTo(amt("10.00001"));
	}

	@Test
	void compareToEquals()
	{
		assertThat(amt("10.000000").compareToEquals(amt("10"))).isTrue();
		assertThat(amt("1").compareToEquals(amt("2"))).isFalse();
		assertThatThrownBy(() -> amt("1", currencyId1).compareToEquals(amt("1", currencyId2)))
				.isInstanceOf(AdempiereException.class)
				.hasMessageStartingWith("Amount has invalid currency");
	}

	@Nested
	class getCommonCurrencyIdOfAll
	{
		void assertFails(String messageStartingWith, CostAmount... amts)
		{
			assertThatThrownBy(() -> CostAmount.getCommonCurrencyIdOfAll(amts))
					.isInstanceOf(AdempiereException.class)
					.hasMessageStartingWith(messageStartingWith);
		}

		void assertSuccess(CurrencyId expectedCurrencyId, CostAmount... amts)
		{
			assertThat(CostAmount.getCommonCurrencyIdOfAll(amts)).isEqualTo(expectedCurrencyId);
		}

		@Test
		void nullParam()
		{
			assertFails("No Amount provided",
					(CostAmount[])null);
		}

		@Test
		void singleNullParam()
		{
			assertFails("At least one non null Amount instance was expected",
					(CostAmount)null);
		}

		@Test
		void multipleNullParams()
		{
			assertFails(
					"At least one non null Amount instance was expected",
					null, null, null);
		}

		@Test
		void singleAmount() {assertSuccess(currencyId1, amt("123", currencyId1));}

		@Test
		void singleAmountAndNullFirst() {assertSuccess(currencyId1, null, amt("123", currencyId1));}

		@Test
		void singleAmountAndNulls() {assertSuccess(currencyId1, null, amt("123", currencyId1), null);}

		@Test
		void multipleAmountSameCurrency() {assertSuccess(currencyId1, amt("123", currencyId1), amt("126", currencyId1));}

		@Test
		void multipleAmountSameCurrencyAndSomeNulls() {assertSuccess(currencyId1, amt("123", currencyId1), null, amt("126", currencyId1), null);}

		@Test
		void multipleAmountDifferentCurrency()
		{
			assertFails("All given Amount(s) shall have the same currency",
					amt("123", currencyId1), amt("126", currencyId2));
		}

		@Test
		void multipleAmountDifferentCurrencyAndSomeNulls()
		{
			assertFails("All given Amount(s) shall have the same currency",
					amt("123", currencyId1), null, amt("126", currencyId2), null);
		}
	}

	@Nested
	class assertCurrencyMatching
	{
		void assertNotMatching(final CostAmount... amts)
		{
			assertThatThrownBy(() -> CostAmount.assertCurrencyMatching(amts))
					.isInstanceOf(AdempiereException.class)
					.hasMessageStartingWith("Amount has invalid currency");
		}

		@Test
		void nullParam() {CostAmount.assertCurrencyMatching((CostAmount[])null);}

		@Test
		void nullsParams() {CostAmount.assertCurrencyMatching(null, null, null);}

		@Test
		void singleAmount() {CostAmount.assertCurrencyMatching(amt("123", currencyId1));}

		@Test
		void singleAmountAndNulls() {CostAmount.assertCurrencyMatching(null, amt("123", currencyId1), null);}

		@Test
		void multipleAmountSameCurrency() {CostAmount.assertCurrencyMatching(amt("123", currencyId1), amt("126", currencyId1));}

		@Test
		void multipleAmountSameCurrencyAndSomeNulls() {CostAmount.assertCurrencyMatching(amt("123", currencyId1), null, amt("126", currencyId1), null);}

		@Test
		void multipleAmountDifferentCurrency() {assertNotMatching(amt("123", currencyId1), amt("126", currencyId2));}

		@Test
		void multipleAmountDifferentCurrencyAndSomeNulls() {assertNotMatching(amt("123", currencyId1), null, amt("126", currencyId2), null);}
	}

	@Nested
	class add
	{
		@Test
		void sameSourceCurrency()
		{
			assertThat(amt("1", currencyId1, "10", currencyId2)
					.add(amt("2", currencyId1, "20", currencyId2))
			).isEqualTo(amt("3", currencyId1, "30", currencyId2));
		}

		@Test
		void differentSourceCurrency()
		{
			assertThat(amt("1", currencyId1, "10", currencyId2)
					.add(amt("2", currencyId1, "20", currencyId3))
			).isEqualTo(amt("3", currencyId1, null, null));
		}

	}
}
