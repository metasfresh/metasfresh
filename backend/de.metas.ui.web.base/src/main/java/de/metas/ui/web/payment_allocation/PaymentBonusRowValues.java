package de.metas.ui.web.payment_allocation;

import de.metas.currency.Amount;
import de.metas.currency.CurrencyCode;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.IMsgBL;
import de.metas.i18n.ITranslatableString;
import de.metas.invoice.paymentbonus.PaymentBonusDeduction;
import de.metas.money.Money;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;

/**
 * The payment bonus columns of an invoice row of the payment allocation view: the amount that the customer deducts, the computed deduction that it is booked with,
 * and a note that tells the user why the amount is not the computed one.
 */
@Value
@Builder(toBuilder = true)
public class PaymentBonusRowValues
{
	private static final AdMessageKey MSG_NOT_PREFILLED_ABOVE_OPEN_AMT = AdMessageKey.of("de.metas.ui.web.payment_allocation.PaymentBonusNotPrefilledAboveOpenAmt");
	private static final AdMessageKey MSG_NOT_COMPUTED = AdMessageKey.of("de.metas.ui.web.payment_allocation.PaymentBonusNotComputed");
	private static final AdMessageKey MSG_OTHER_CURRENCY = AdMessageKey.of("de.metas.ui.web.payment_allocation.PaymentBonusOtherCurrency");
	private static final AdMessageKey MSG_ADJUSTED = AdMessageKey.of("de.metas.ui.web.payment_allocation.PaymentBonusAdjusted");

	/** {@code null} if the customer has no bonus to deduct */
	@Nullable Amount paymentBonusAmt;
	@Nullable PaymentBonusDeduction paymentBonusDeduction;
	@Nullable ITranslatableString paymentBonusNote;

	/**
	 * @param deductionCurrencyCode the currency of the deduction, i.e. of the invoice
	 * @param openAmt               the invoice's open amount, in the currency that the view allocates in
	 * @return the computed bonus, pre-filled unless it is bigger than the invoice's open amount: the customer cannot deduct more than is open.
	 */
	public static PaymentBonusRowValues prefill(
			@Nullable final PaymentBonusDeduction deduction,
			@NonNull final CurrencyCode deductionCurrencyCode,
			@NonNull final Amount openAmt)
	{
		if (deduction == null)
		{
			return builder().build();
		}
		if (!deductionCurrencyCode.equals(openAmt.getCurrencyCode()))
		{
			// the credit memo is in the invoice's currency; the allocation in another currency cannot deduct it
			return builder()
					.paymentBonusNote(msg(MSG_OTHER_CURRENCY, deductionCurrencyCode.toThreeLetterCode(), openAmt.getCurrencyCode().toThreeLetterCode()))
					.build();
		}

		final Amount grossAmt = toAmount(deduction.getGrossAmount(), openAmt.getCurrencyCode());
		if (grossAmt.compareTo(openAmt) > 0)
		{
			return builder()
					.paymentBonusAmt(Amount.zero(openAmt.getCurrencyCode()))
					.paymentBonusDeduction(deduction) // a smaller amount can still be entered
					.paymentBonusNote(msg(MSG_NOT_PREFILLED_ABOVE_OPEN_AMT, format(grossAmt), format(openAmt)))
					.build();
		}

		return builder()
				.paymentBonusAmt(grossAmt)
				.paymentBonusDeduction(deduction)
				.build();
	}

	/**
	 * @return no bonus, with a note that shows why it could not be computed (e.g. no price for the bonus product); the invoice can still be allocated without it
	 */
	public static PaymentBonusRowValues notComputed(@NonNull final Exception exception)
	{
		return builder()
				.paymentBonusNote(msg(MSG_NOT_COMPUTED, AdempiereException.extractMessage(exception)))
				.build();
	}

	/**
	 * @return the amount that the user entered, or, if it cannot be booked exactly because of the rounding of the VAT, the closest amount that can, with a note
	 */
	public static PaymentBonusRowValues entered(@Nullable final PaymentBonusDeduction deduction, @NonNull final Amount enteredAmt)
	{
		final PaymentBonusRowValues entered = builder()
				.paymentBonusAmt(enteredAmt)
				.paymentBonusDeduction(deduction)
				.build();
		if (deduction == null || enteredAmt.signum() <= 0)
		{
			return entered; // nothing to adjust; a non-zero amount without a bonus, or a negative one, is rejected when allocating
		}

		final Money bookedGrossAmt = deduction.withGrossAmount(Money.of(enteredAmt.toBigDecimal(), deduction.getCurrencyId())).getGrossAmount();
		final Amount bookedAmt = toAmount(bookedGrossAmt, enteredAmt.getCurrencyCode());
		if (bookedAmt.compareTo(enteredAmt) == 0)
		{
			return entered;
		}

		return entered.toBuilder()
				.paymentBonusAmt(bookedAmt)
				.paymentBonusNote(msg(MSG_ADJUSTED, format(enteredAmt), format(bookedAmt)))
				.build();
	}

	/** the row's amounts are in the currency of the invoice, like the bonus */
	private static Amount toAmount(@NonNull final Money money, @NonNull final CurrencyCode currencyCode)
	{
		return Amount.of(money.toBigDecimal(), currencyCode);
	}

	private static String format(@NonNull final Amount amount)
	{
		return amount.toBigDecimal().toPlainString() + " " + amount.getCurrencyCode().toThreeLetterCode();
	}

	private static ITranslatableString msg(@NonNull final AdMessageKey key, @NonNull final Object... params)
	{
		return Services.get(IMsgBL.class).getTranslatableMsgText(key, params);
	}
}
