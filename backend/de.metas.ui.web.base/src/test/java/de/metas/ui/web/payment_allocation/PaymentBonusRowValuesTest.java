package de.metas.ui.web.payment_allocation;

import de.metas.bpartner.BPartnerId;
import de.metas.currency.Amount;
import de.metas.currency.CurrencyCode;
import de.metas.currency.CurrencyPrecision;
import de.metas.i18n.ITranslatableString;
import org.compiere.util.Env;
import lombok.NonNull;
import de.metas.util.Services;
import de.metas.i18n.impl.PlainMsgBL;
import de.metas.i18n.MessageFormatter;
import de.metas.i18n.IMsgBL;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.TranslatableStrings;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.paymentbonus.PaymentBonusDeduction;
import de.metas.invoice.paymentbonus.PaymentBonusDeductionLine;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.tax.api.Tax;
import de.metas.tax.api.TaxCategoryId;
import de.metas.tax.api.TaxId;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.util.TimeUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class PaymentBonusRowValuesTest
{
	private static final CurrencyId EUR_ID = CurrencyId.ofRepoId(102);
	private static final CurrencyCode EUR = CurrencyCode.EUR;

	@BeforeEach
	void init()
	{
		AdempiereTestHelper.get().init();
	}

	@Test
	void prefill_bonusNotAboveTheOpenAmount_isPrefilled()
	{
		final PaymentBonusDeduction deduction = deduction("7", "2.60"); // 2.78 with VAT

		final PaymentBonusRowValues values = PaymentBonusRowValues.prefill(deduction, EUR, Amount.of("100", EUR), null, null);

		assertThat(values.getPaymentBonusAmt()).isEqualByComparingTo(Amount.of("2.78", EUR));
		assertThat(values.getPaymentBonusDeduction()).isSameAs(deduction);
		assertThat(values.getPaymentBonusNote()).isNull();
	}

	/** E.g. an invoice that is paid but for a small rest: the customer cannot deduct more than is open, so nothing is pre-filled, and the note says why. */
	@Test
	void prefill_bonusAboveTheOpenAmount_isNotPrefilled()
	{
		final PaymentBonusDeduction deduction = deduction("7", "2.60"); // 2.78 with VAT

		final PaymentBonusRowValues values = PaymentBonusRowValues.prefill(deduction, EUR, Amount.of("2.00", EUR), null, null);

		assertThat(values.getPaymentBonusAmt()).isEqualByComparingTo(Amount.zero(EUR));
		assertThat(values.getPaymentBonusDeduction()).isSameAs(deduction); // a smaller amount can still be entered
		assertThat(values.getPaymentBonusNote()).isNotNull();
	}

	/** The open amount is converted to the payment's currency; the bonus is booked in the invoice's currency and cannot be compared or deducted from it. */
	@Test
	void prefill_openAmountInAnotherCurrency_noBonus()
	{
		final PaymentBonusRowValues values = PaymentBonusRowValues.prefill(deduction("7", "2.60"), EUR, Amount.of("100", CurrencyCode.CHF), null, null);

		assertThat(values.getPaymentBonusAmt()).isNull();
		assertThat(values.getPaymentBonusDeduction()).isNull();
		assertThat(values.getPaymentBonusNote()).isNotNull();
	}

	/** What the customer pays is the open amount minus the discount and the service fee; the bonus cannot be more than that. */
	@Test
	void prefill_bonusAboveTheOpenAmountMinusDiscountAndServiceFee_isNotPrefilled()
	{
		final PaymentBonusDeduction deduction = deduction("7", "2.60"); // 2.78 with VAT

		final PaymentBonusRowValues values = PaymentBonusRowValues.prefill(deduction, EUR, Amount.of("5.00", EUR), Amount.of("1.50", EUR), Amount.of("1.00", EUR)); // 2.50 left

		assertThat(values.getPaymentBonusAmt()).isEqualByComparingTo(Amount.zero(EUR));
		assertThat(values.getPaymentBonusNote()).isNotNull();
	}

	@Test
	void prefill_noBonus()
	{
		final PaymentBonusRowValues values = PaymentBonusRowValues.prefill(null, EUR, Amount.of("100", EUR), null, null);

		assertThat(values.getPaymentBonusAmt()).isNull();
		assertThat(values.getPaymentBonusDeduction()).isNull();
		assertThat(values.getPaymentBonusNote()).isNull();
	}

	/** The invoice is still shown and can be allocated without the bonus; the note tells the user why there is none. */
	@Test
	void notComputed_showsTheError()
	{
		final PaymentBonusRowValues values = PaymentBonusRowValues.notComputed(new AdempiereException("no price for the bonus product"));

		assertThat(values.getPaymentBonusAmt()).isNull();
		assertThat(values.getPaymentBonusDeduction()).isNull();
		assertThat(values.getPaymentBonusNote()).isNotNull();
		assertThat(values.getPaymentBonusNote().getDefaultValue()).contains("no price for the bonus product");
	}

	@Test
	void entered_reachableAmount_isKept()
	{
		final PaymentBonusDeduction deduction = deduction("7", "2.60");

		final PaymentBonusRowValues values = PaymentBonusRowValues.entered(deduction, Amount.of("2.00", EUR), Amount.of("100", EUR), null);

		assertThat(values.getPaymentBonusAmt()).isEqualByComparingTo(Amount.of("2.00", EUR));
		assertThat(values.getPaymentBonusNote()).isNull();
	}

	/** Because of the rounding of the VAT, not every gross amount can be booked; the row shows the amount that will be booked. */
	@Test
	void entered_unreachableAmount_showsTheAdjustedAmount()
	{
		final PaymentBonusDeduction deduction = deduction("19", "10.00"); // 0.02 net -> 0.00 VAT, 0.03 net -> 0.01 VAT: 0.03 gross cannot be reached

		final PaymentBonusRowValues values = PaymentBonusRowValues.entered(deduction, Amount.of("0.03", EUR), Amount.of("100", EUR), null);

		assertThat(values.getPaymentBonusAmt()).isNotEqualByComparingTo(Amount.of("0.03", EUR));
		assertThat(values.getPaymentBonusAmt()).isEqualByComparingTo(deduction.withGrossAmount(Money.of(new BigDecimal("0.03"), EUR_ID)).getGrossAmount().toAmount(currencyId -> EUR));
		assertThat(values.getPaymentBonusNote()).isNotNull();
	}

	/** Without a computed bonus, the note says why (e.g. it could not be computed); editing the amount keeps it. */
	@Test
	void entered_withoutDeduction_keepsTheNote()
	{
		final ITranslatableString note = TranslatableStrings.anyLanguage("could not be computed");

		final PaymentBonusRowValues values = PaymentBonusRowValues.entered(null, Amount.of("2.00", EUR), Amount.of("100", EUR), note);

		assertThat(values.getPaymentBonusAmt()).isEqualByComparingTo(Amount.of("2.00", EUR));
		assertThat(values.getPaymentBonusNote()).isSameAs(note);
	}

	/** Entering an amount above what the customer pays: the row tells why it cannot be booked, like the pre-fill does. */
	@Test
	void entered_aboveWhatTheCustomerPays_keepsTheReason()
	{
		final PaymentBonusDeduction deduction = deduction("7", "2.60");

		final PaymentBonusRowValues values = PaymentBonusRowValues.entered(deduction, Amount.of("3.00", EUR), Amount.of("2.00", EUR), TranslatableStrings.anyLanguage("not pre-filled"));

		assertThat(values.getPaymentBonusAmt()).isEqualByComparingTo(Amount.of("3.00", EUR));
		assertThat(values.getPaymentBonusNote()).isNotNull();
		assertThat(values.getPaymentBonusNote().getDefaultValue()).contains("PaymentBonusNotPrefilledAboveOpenAmt");
	}

	/**
	 * The amounts in the notes are formatted for the user's language (e.g. with a decimal comma in German), like in every message whose parameters are amounts.
	 * The test formats the message like {@code Msg} does, with {@link MessageFormatter}.
	 */
	@Test
	void notes_formatTheAmountsForTheLanguage()
	{
		Env.setContext(Env.getCtx(), Env.CTXNAME_AD_Language, "de_DE");
		Services.registerService(IMsgBL.class, new PlainMsgBL()
		{
			@Override
			public ITranslatableString getTranslatableMsgText(@NonNull final AdMessageKey adMessage, final Object... msgParameters)
			{
				return TranslatableStrings.constant(MessageFormatter.format("{0} / {1}", msgParameters));
			}
		});

		final PaymentBonusRowValues prefilled = PaymentBonusRowValues.prefill(deduction("7", "2.60"), EUR, Amount.of("2", EUR), null, null);
		assertThat(prefilled.getPaymentBonusNote().getDefaultValue()).contains("2,78").contains("2,00").doesNotContain("2.78");

		final PaymentBonusRowValues adjusted = PaymentBonusRowValues.entered(deduction("19", "10.00"), Amount.of("0.03", EUR), Amount.of("100", EUR), null);
		assertThat(adjusted.getPaymentBonusNote().getDefaultValue()).contains("0,03").doesNotContain("0.03");
	}

	@Test
	void entered_zero_noNote()
	{
		final PaymentBonusRowValues values = PaymentBonusRowValues.entered(deduction("7", "2.60"), Amount.zero(EUR), Amount.of("100", EUR), null);

		assertThat(values.getPaymentBonusAmt()).isEqualByComparingTo(Amount.zero(EUR));
		assertThat(values.getPaymentBonusNote()).isNull();
	}

	private static PaymentBonusDeduction deduction(final String taxRate, final String netAmt)
	{
		return PaymentBonusDeduction.builder()
				.orgId(OrgId.ofRepoId(1))
				.invoiceId(InvoiceId.ofRepoId(10))
				.customerId(BPartnerId.ofRepoId(20))
				.currencyId(EUR_ID)
				.precision(CurrencyPrecision.TWO)
				.line(PaymentBonusDeductionLine.builder()
						.bonusProductId(ProductId.ofRepoId(30))
						.tax(Tax.builder()
								.taxId(TaxId.ofRepoId(1))
								.name(taxRate + " %")
								.orgId(OrgId.ANY)
								.validFrom(TimeUtil.asTimestamp(LocalDate.parse("2020-01-01")))
								.taxCategoryId(TaxCategoryId.ofRepoId(1))
								.rate(new BigDecimal(taxRate))
								.isTaxExempt(false)
								.requiresTaxCertificate(false)
								.seqNo(10)
								.build())
						.netAmt(Money.of(new BigDecimal(netAmt), EUR_ID))
						.build())
				.build();
	}
}
