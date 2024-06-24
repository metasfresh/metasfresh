package de.metas.money;

import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

class CurrencyIdTest
{
	@Value(staticConstructor = "of")
	private static class CurrencyAware
	{
		@NonNull CurrencyId currencyId;

		// setting it here just to make sure 2 instances with same currency are not equal
		@NonNull UUID unique = UUID.randomUUID();
	}

	private static CurrencyAware ca(final int currencyRepoId) {return CurrencyAware.of(CurrencyId.ofRepoId(currencyRepoId));}

	@Nested
	class getCommonCurrencyIdOfAll
	{
		void assertFails(String messageStartingWith, CurrencyAware... amts)
		{
			assertThatThrownBy(() -> CurrencyId.getCommonCurrencyIdOfAll(CurrencyAware::getCurrencyId, "CurrencyAware", amts))
					.isInstanceOf(AdempiereException.class)
					.hasMessageStartingWith(messageStartingWith);
		}

		@SuppressWarnings("SameParameterValue")
		void assertSuccess(int expectedCurrencyRepoId, CurrencyAware... amts)
		{
			assertThat(CurrencyId.getCommonCurrencyIdOfAll(CurrencyAware::getCurrencyId, "CurrencyAware", amts))
					.isEqualTo(CurrencyId.ofRepoId(expectedCurrencyRepoId));
		}

		@Test
		void nullParam()
		{
			assertFails("No CurrencyAware provided",
					(CurrencyAware[])null);
		}

		@Test
		void singleNullParam()
		{
			assertFails("At least one non null CurrencyAware instance was expected",
					(CurrencyAware)null);
		}

		@Test
		void multipleNullParams()
		{
			assertFails(
					"At least one non null CurrencyAware instance was expected",
					null, null, null);
		}

		@Test
		void singleAmount() {assertSuccess(1, ca(1));}

		@Test
		void singleAmountAndNullFirst() {assertSuccess(1, null, ca(1));}

		@Test
		void singleAmountAndNulls() {assertSuccess(1, null, ca(1), null);}

		@Test
		void multipleAmountSameCurrency() {assertSuccess(1, ca(1), ca(1));}

		@Test
		void multipleAmountSameCurrencyAndSomeNulls() {assertSuccess(1, ca(1), null, ca(1), null);}

		@Test
		void multipleAmountDifferentCurrency()
		{
			assertFails("All given CurrencyAware(s) shall have the same currency",
					ca(1), ca(2));
		}

		@Test
		void multipleAmountDifferentCurrencyAndSomeNulls()
		{
			assertFails("All given CurrencyAware(s) shall have the same currency",
					ca(1), null, ca(2), null);
		}
	}

	@Nested
	class assertCurrencyMatching
	{
		void assertNotMatching(final CurrencyAware... amts)
		{
			assertThatThrownBy(() -> CurrencyId.assertCurrencyMatching(CurrencyAware::getCurrencyId, "CurrencyAware", amts))
					.isInstanceOf(AdempiereException.class)
					.hasMessageStartingWith("CurrencyAware has invalid currency");
		}

		void assertMatching(final CurrencyAware... amts)
		{
			CurrencyId.assertCurrencyMatching(CurrencyAware::getCurrencyId, "CurrencyAware", amts);
		}

		@Test
		void nullParam() {assertMatching((CurrencyAware[])null);}

		@Test
		void nullsParams() {assertMatching(null, null, null);}

		@Test
		void singleAmount() {assertMatching(ca(1));}

		@Test
		void singleAmountAndNulls() {assertMatching(null, ca(1), null);}

		@Test
		void multipleAmountSameCurrency() {assertMatching(ca(1), ca(1));}

		@Test
		void multipleAmountSameCurrencyAndSomeNulls() {assertMatching(ca(1), null, ca(1), null);}

		@Test
		void multipleAmountDifferentCurrency() {assertNotMatching(ca(1), ca(2));}

		@Test
		void multipleAmountDifferentCurrencyAndSomeNulls() {assertNotMatching(null, ca(1), null, ca(2), null);}
	}

}