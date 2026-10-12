package de.metas.invoice.paymentbonus;

import de.metas.currency.Amount;
import de.metas.currency.CurrencyCode;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class PaymentBonusAmountMatcherTest
{
	private static Amount eur(final String value)
	{
		return Amount.of(value, CurrencyCode.EUR);
	}

	@Test
	void exactSkonto_skontoOnly()
	{
		// open 100, Skonto 3, bonus 10; paid 97
		final Optional<PaymentBonusAmountMatcher.MatchedDeductions> result = PaymentBonusAmountMatcher.match(eur("100"), eur("3"), eur("10"), eur("97"), true);

		assertThat(result).isPresent();
		assertThat(result.get().getDiscountAmt()).isEqualByComparingTo(eur("3"));
		assertThat(result.get().getBonusAmt()).isEqualByComparingTo(eur("0"));
	}

	@Test
	void openMinusBonus_bonusOnly()
	{
		final Optional<PaymentBonusAmountMatcher.MatchedDeductions> result = PaymentBonusAmountMatcher.match(eur("100"), eur("3"), eur("10"), eur("90"), true);

		assertThat(result).isPresent();
		assertThat(result.get().getDiscountAmt()).isEqualByComparingTo(eur("0"));
		assertThat(result.get().getBonusAmt()).isEqualByComparingTo(eur("10"));
	}

	@Test
	void openMinusSkontoMinusBonus_both()
	{
		final Optional<PaymentBonusAmountMatcher.MatchedDeductions> result = PaymentBonusAmountMatcher.match(eur("100"), eur("3"), eur("10"), eur("87"), true);

		assertThat(result).isPresent();
		assertThat(result.get().getDiscountAmt()).isEqualByComparingTo(eur("3"));
		assertThat(result.get().getBonusAmt()).isEqualByComparingTo(eur("10"));
	}

	@Test
	void skontoEqualsBonus_skontoWins()
	{
		// open - 5 matches both "Skonto only" and "bonus only"
		final Optional<PaymentBonusAmountMatcher.MatchedDeductions> result = PaymentBonusAmountMatcher.match(eur("100"), eur("5"), eur("5"), eur("95"), true);

		assertThat(result).isPresent();
		assertThat(result.get().getDiscountAmt()).isEqualByComparingTo(eur("5"));
		assertThat(result.get().getBonusAmt()).isEqualByComparingTo(eur("0"));
	}

	@Test
	void noBonus_onlySkontoRule()
	{
		assertThat(PaymentBonusAmountMatcher.match(eur("100"), eur("3"), null, eur("90"), true))
				.as("bonusGrossAmt null, paid 90 (open - 10): no match")
				.isEmpty();
		assertThat(PaymentBonusAmountMatcher.match(eur("100"), eur("3"), null, eur("97"), true))
				.as("bonusGrossAmt null, paid 97 (open - Skonto): Skonto rule still matches")
				.isPresent();

		assertThat(PaymentBonusAmountMatcher.match(eur("100"), eur("3"), eur("10"), eur("90"), false))
				.as("bonusAllowed false, paid 90 (open - bonus): no match")
				.isEmpty();
		assertThat(PaymentBonusAmountMatcher.match(eur("100"), eur("3"), eur("10"), eur("87"), false))
				.as("bonusAllowed false, paid 87 (open - Skonto - bonus): no match")
				.isEmpty();
		final Optional<PaymentBonusAmountMatcher.MatchedDeductions> skonto = PaymentBonusAmountMatcher.match(eur("100"), eur("3"), eur("10"), eur("97"), false);
		assertThat(skonto)
				.as("bonusAllowed false, paid 97 (open - Skonto): Skonto rule matches")
				.isPresent();
		assertThat(skonto.get().getBonusAmt())
				.as("bonusAllowed false, paid 97: no bonus is returned")
				.isEqualByComparingTo(eur("0"));
	}

	@Test
	void shortByOther_noMatch()
	{
		assertThat(PaymentBonusAmountMatcher.match(eur("100"), eur("3"), eur("10"), eur("80"), true)).isEmpty();
	}
}
