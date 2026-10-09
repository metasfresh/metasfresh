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
}
