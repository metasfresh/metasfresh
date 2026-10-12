package de.metas.invoice.paymentbonus;

import de.metas.bpartner.BPartnerId;
import de.metas.currency.CurrencyPrecision;
import de.metas.invoice.InvoiceId;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.tax.api.Tax;
import de.metas.tax.api.TaxCategoryId;
import de.metas.tax.api.TaxId;
import org.compiere.util.TimeUtil;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class PaymentBonusDeductionTest
{
	private static final CurrencyId EUR = CurrencyId.ofRepoId(102);

	private static Tax tax(final int id, final String rate)
	{
		return Tax.builder()
				.taxId(TaxId.ofRepoId(id))
				.name(rate + " %")
				.orgId(OrgId.ANY)
				.validFrom(TimeUtil.asTimestamp(LocalDate.parse("2020-01-01")))
				.taxCategoryId(TaxCategoryId.ofRepoId(id))
				.rate(new BigDecimal(rate))
				.isTaxExempt(false)
				.requiresTaxCertificate(false)
				.seqNo(10)
				.build();
	}

	private static final Tax TAX_7 = tax(1, "7");
	private static final Tax TAX_19 = tax(2, "19");

	private static PaymentBonusDeductionLine line(final int bonusProductId, final Tax tax, final String netAmt)
	{
		return PaymentBonusDeductionLine.builder()
				.bonusProductId(ProductId.ofRepoId(bonusProductId))
				.tax(tax)
				.netAmt(Money.of(new BigDecimal(netAmt), EUR))
				.build();
	}

	private static PaymentBonusDeduction deduction(final PaymentBonusDeductionLine... lines)
	{
		final PaymentBonusDeduction.PaymentBonusDeductionBuilder builder = PaymentBonusDeduction.builder()
				.orgId(OrgId.ofRepoId(1))
				.invoiceId(InvoiceId.ofRepoId(10))
				.customerId(BPartnerId.ofRepoId(20))
				.currencyId(EUR)
				.precision(CurrencyPrecision.TWO);
		for (final PaymentBonusDeductionLine line : lines)
		{
			builder.line(line);
		}
		return builder.build();
	}

	/** The VAT is computed per tax on the sum of its lines, like the credit memo's own taxes. */
	@Test
	void grossAmount_vatPerTaxOnTheSumOfItsLines()
	{
		// 7 %: 0.07 + 0.07 = 0.14 -> 0.0098 VAT -> 0.01 (per line it would be 0.00 + 0.00)
		// 19 %: 10.00 -> 1.90 VAT
		final PaymentBonusDeduction deduction = deduction(line(1, TAX_7, "0.07"), line(2, TAX_7, "0.07"), line(3, TAX_19, "10.00"));

		assertThat(deduction.getNetAmount().toBigDecimal()).isEqualByComparingTo("10.14");
		assertThat(deduction.getGrossAmount().toBigDecimal()).isEqualByComparingTo("12.05"); // 10.14 + 0.01 + 1.90
	}

	@Test
	void withGrossAmount_sameAmount_unchanged()
	{
		final PaymentBonusDeduction deduction = deduction(line(1, TAX_7, "2.60"));

		assertThat(deduction.withGrossAmount(Money.of(new BigDecimal("2.78"), EUR))).isSameAs(deduction);
	}

	/** The customer deducted less than computed: the net amounts are scaled down, the VAT stays on top. */
	@Test
	void withGrossAmount_scalesTheLines()
	{
		final PaymentBonusDeduction deduction = deduction(line(1, TAX_7, "10.40"), line(2, TAX_7, "1.60")); // gross 12.84

		final PaymentBonusDeduction scaled = deduction.withGrossAmount(Money.of(new BigDecimal("6.42"), EUR));

		assertThat(scaled.getGrossAmount().toBigDecimal()).isEqualByComparingTo("6.42");
		assertThat(scaled.getLines()).extracting(l -> l.getNetAmt().toBigDecimal().setScale(2))
				.containsExactly(new BigDecimal("5.20"), new BigDecimal("0.80"));
	}

	/** Rounding of the scaled lines and of their VAT is corrected on the biggest line. */
	@Test
	void withGrossAmount_roundingIsCorrectedOnTheBiggestLine()
	{
		final PaymentBonusDeduction deduction = deduction(line(1, TAX_19, "3.00"), line(2, TAX_7, "1.00")); // gross 3.57 + 1.07 = 4.64

		final PaymentBonusDeduction scaled = deduction.withGrossAmount(Money.of(new BigDecimal("3.33"), EUR));

		assertThat(scaled.getGrossAmount().toBigDecimal()).isEqualByComparingTo("3.33");
	}

	/** Not every gross amount can be reached with VAT on top: 19 % of 0.01 more net can add 0.02 gross. Then the closest one wins. */
	@Test
	void withGrossAmount_unreachableAmount_closestOne()
	{
		final PaymentBonusDeduction deduction = deduction(line(1, TAX_19, "10.00")); // gross 11.90

		final PaymentBonusDeduction scaled = deduction.withGrossAmount(Money.of(new BigDecimal("1.20"), EUR));

		// 1.01 net -> 0.19 VAT -> 1.20 gross is reachable; but e.g. 0.03 net -> 0.01 -> 0.04, 0.02 net -> 0.00 -> 0.02: 0.03 gross is not
		assertThat(scaled.getGrossAmount().toBigDecimal()).isEqualByComparingTo("1.20");

		final PaymentBonusDeduction tiny = deduction.withGrossAmount(Money.of(new BigDecimal("0.03"), EUR));
		assertThat(tiny.getGrossAmount().subtract(Money.of(new BigDecimal("0.03"), EUR)).abs().toBigDecimal()).isLessThanOrEqualTo(new BigDecimal("0.01"));
	}

	private static Money eur(final String amount)
	{
		return Money.of(new BigDecimal(amount), EUR);
	}

	private static BigDecimal grossOf(final Optional<PaymentBonusDeduction> deduction)
	{
		return deduction.map(PaymentBonusDeduction::getGrossAmount).map(Money::toBigDecimal).orElse(BigDecimal.ZERO);
	}

	/** A limit at or above the deduction's own gross amount gives the deduction itself, never more. */
	@Test
	void withLargestReachableGrossAmountNotAbove_limitAboveTheDeduction_deductionItself()
	{
		final PaymentBonusDeduction deduction = deduction(line(1, TAX_19, "10.00")); // gross 11.90

		assertThat(deduction.withLargestReachableGrossAmountNotAbove(eur("20.00"))).containsSame(deduction);
		assertThat(deduction.withLargestReachableGrossAmountNotAbove(eur("11.90"))).containsSame(deduction);
	}

	/** A reachable limit is reached exactly, e.g. a 2.78 bonus capped to the 0.22 or 1.50 that a payment leaves open. */
	@Test
	void withLargestReachableGrossAmountNotAbove_reachableLimit_limit()
	{
		final PaymentBonusDeduction deduction = deduction(line(1, TAX_7, "2.60")); // gross 2.78

		assertThat(grossOf(deduction.withLargestReachableGrossAmountNotAbove(eur("0.22")))).isEqualByComparingTo("0.22");
		assertThat(grossOf(deduction.withLargestReachableGrossAmountNotAbove(eur("1.50")))).isEqualByComparingTo("1.50");
	}

	/** {@link PaymentBonusDeduction#withGrossAmount(Money)} overshoots 0.03 to 0.04 (19 % VAT); the largest reachable amount not above 0.03 is 0.02. */
	@Test
	void withLargestReachableGrossAmountNotAbove_overshootingLimit_nextLowerReachable()
	{
		final PaymentBonusDeduction deduction = deduction(line(1, TAX_19, "10.00")); // gross 11.90
		assertThat(deduction.withGrossAmount(eur("0.03")).getGrossAmount().toBigDecimal()).isEqualByComparingTo("0.04"); // precondition: the overshoot

		assertThat(grossOf(deduction.withLargestReachableGrossAmountNotAbove(eur("0.03")))).isEqualByComparingTo("0.02");
	}

	/**
	 * With lines of two taxes, the gross amount that a scaling reached is not always reached again when scaling the original deduction to it:
	 * here 6.72 is reached by asking for 6.73, but asking for 6.72 gives 6.73. So the scaled deduction itself is returned, never only its gross amount.
	 */
	@Test
	void withLargestReachableGrossAmountNotAbove_twoTaxes_returnsTheScaledDeductionThatStaysBelowTheLimit()
	{
		final PaymentBonusDeduction deduction = deduction(line(1, TAX_7, "10.40"), line(2, TAX_19, "1.60")); // gross 11.13 + 1.90 = 13.03
		assertThat(deduction.withGrossAmount(eur("6.72")).getGrossAmount().toBigDecimal()).isEqualByComparingTo("6.73"); // precondition: scaling again to 6.72 would overshoot

		final PaymentBonusDeduction capped = deduction.withLargestReachableGrossAmountNotAbove(eur("6.72")).orElseThrow(AssertionError::new);

		assertThat(capped.getGrossAmount().toBigDecimal()).isEqualByComparingTo("6.72");
		assertThat(capped.getLines()).hasSize(2);
	}

	@Test
	void withLargestReachableGrossAmountNotAbove_limitNotPositive_empty()
	{
		final PaymentBonusDeduction deduction = deduction(line(1, TAX_7, "2.60"));

		assertThat(deduction.withLargestReachableGrossAmountNotAbove(eur("0"))).isEmpty();
		assertThat(deduction.withLargestReachableGrossAmountNotAbove(eur("-1.00"))).isEmpty();
	}

	/** Unlike {@link PaymentBonusDeduction#withLargestReachableGrossAmountNotAbove(Money)}, an amount above the deduction's own gross amount scales the deduction up: the customer deducted more than computed. */
	@Test
	void withLargestReachableGrossAmountUpTo_amountAboveTheDeduction_scaledUp()
	{
		final PaymentBonusDeduction deduction = deduction(line(1, TAX_7, "2.60")); // gross 2.78

		assertThat(grossOf(deduction.withLargestReachableGrossAmountUpTo(eur("3.00")))).isEqualByComparingTo("3.00");
		assertThat(deduction.withLargestReachableGrossAmountUpTo(eur("2.78"))).containsSame(deduction);
		assertThat(grossOf(deduction.withLargestReachableGrossAmountUpTo(eur("1.50")))).isEqualByComparingTo("1.50");
	}

	/** {@link PaymentBonusDeduction#withGrossAmount(Money)} overshoots 0.03 to 0.04 (19 % VAT); so the cent-down gives 0.02, also above the deduction: 11.93 overshoots to 11.94 and gives 11.92. */
	@Test
	void withLargestReachableGrossAmountUpTo_overshootingAmount_nextLowerReachable()
	{
		final PaymentBonusDeduction deduction = deduction(line(1, TAX_19, "10.00")); // gross 11.90
		assertThat(deduction.withGrossAmount(eur("11.93")).getGrossAmount().toBigDecimal()).isEqualByComparingTo("11.94"); // precondition: the overshoot

		assertThat(grossOf(deduction.withLargestReachableGrossAmountUpTo(eur("0.03")))).isEqualByComparingTo("0.02");
		assertThat(grossOf(deduction.withLargestReachableGrossAmountUpTo(eur("11.93")))).isEqualByComparingTo("11.92");
	}

	/** With lines of two taxes, an amount above the deduction is reached by scaling all lines up, keeping both taxes. */
	@Test
	void withLargestReachableGrossAmountUpTo_twoTaxes_scaledUp()
	{
		final PaymentBonusDeduction deduction = deduction(line(1, TAX_7, "10.40"), line(2, TAX_19, "1.60")); // gross 11.13 + 1.90 = 13.03

		final PaymentBonusDeduction scaled = deduction.withLargestReachableGrossAmountUpTo(eur("20.00")).orElseThrow(AssertionError::new);

		assertThat(scaled.getGrossAmount().toBigDecimal()).isLessThanOrEqualTo(new BigDecimal("20.00")).isGreaterThanOrEqualTo(new BigDecimal("19.99"));
		assertThat(scaled.getLines()).hasSize(2);
		assertThat(scaled.getNetAmount().toBigDecimal()).isGreaterThan(deduction.getNetAmount().toBigDecimal());
	}

	@Test
	void withLargestReachableGrossAmountUpTo_amountNotPositive_empty()
	{
		final PaymentBonusDeduction deduction = deduction(line(1, TAX_7, "2.60"));

		assertThat(deduction.withLargestReachableGrossAmountUpTo(eur("0"))).isEmpty();
		assertThat(deduction.withLargestReachableGrossAmountUpTo(eur("-1.00"))).isEmpty();
	}
}
