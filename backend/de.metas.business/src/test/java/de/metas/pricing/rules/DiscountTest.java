package de.metas.pricing.rules;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class DiscountTest
{
	private static final LocalDate VALID_FROM = LocalDate.of(2026, 9, 7); // Monday
	private static final LocalDate VALID_TO = LocalDate.of(2026, 9, 11); // Friday, same week

	@Test
	public void dateBeforeValidFrom_notApplicable()
	{
		assertThat(Discount.isDateWithinValidity(VALID_FROM.minusDays(1), VALID_FROM, VALID_TO)).isFalse();
	}

	@Test
	public void dateEqualsValidFrom_applicable()
	{
		assertThat(Discount.isDateWithinValidity(VALID_FROM, VALID_FROM, VALID_TO)).isTrue();
	}

	@Test
	public void dateBetweenValidFromAndValidTo_applicable()
	{
		assertThat(Discount.isDateWithinValidity(VALID_FROM.plusDays(2), VALID_FROM, VALID_TO)).isTrue();
	}

	@Test
	public void dateEqualsValidTo_applicableInclusive()
	{
		assertThat(Discount.isDateWithinValidity(VALID_TO, VALID_FROM, VALID_TO)).isTrue();
	}

	@Test
	public void dateAfterValidTo_notApplicable()
	{
		assertThat(Discount.isDateWithinValidity(VALID_TO.plusDays(3), VALID_FROM, VALID_TO)).isFalse();
	}

	@Test
	public void validToNull_applicableFarInFuture()
	{
		assertThat(Discount.isDateWithinValidity(VALID_FROM.plusYears(5), VALID_FROM, null)).isTrue();
	}
}
