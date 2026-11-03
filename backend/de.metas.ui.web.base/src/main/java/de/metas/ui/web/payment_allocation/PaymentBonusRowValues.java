package de.metas.ui.web.payment_allocation;

import com.google.common.annotations.VisibleForTesting;
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
	/** short, so that it fits the column; the amounts are formatted for the user's language */
	@VisibleForTesting
	public static final AdMessageKey MSG_ABOVE_WHAT_THE_CUSTOMER_PAYS = AdMessageKey.of("de.metas.ui.web.payment_allocation.PaymentBonusNotPrefilledAboveOpenAmt");
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
	 * @param discountAmt           the discount that the customer deducts too, in the same currency
	 * @param serviceFeeAmt         the fee that a service company deducts, in the same currency
	 * @return the computed bonus, pre-filled unless it is bigger than the open amount minus the discount and the fee: the customer cannot deduct more than it pays.
	 */
	public static PaymentBonusRowValues prefill(
			@Nullable final PaymentBonusDeduction deduction,
			@NonNull final CurrencyCode deductionCurrencyCode,
			@NonNull final Amount openAmt,
			@Nullable final Amount discountAmt,
			@Nullable final Amount serviceFeeAmt)
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

		// what the customer pays: the open amount minus the discount and the service company's fee
		Amount maxBonusAmt = openAmt;
		if (discountAmt != null)
		{
			maxBonusAmt = maxBonusAmt.subtract(discountAmt);
		}
		if (serviceFeeAmt != null)
		{
			maxBonusAmt = maxBonusAmt.subtract(serviceFeeAmt);
		}

		final Amount grossAmt = toAmount(deduction.getGrossAmount(), openAmt.getCurrencyCode());
		if (grossAmt.compareTo(maxBonusAmt) > 0)
		{
			return builder()
					.paymentBonusAmt(Amount.zero(openAmt.getCurrencyCode()))
					.paymentBonusDeduction(deduction) // a smaller amount can still be entered
					.paymentBonusNote(aboveWhatTheCustomerPays(grossAmt, maxBonusAmt))
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
	 * @param enteredAmt  the amount that the user entered; checked again with this amount (not with the booked one) when another amount of the row changes, so that the note stays
	 * @param maxBonusAmt what the customer pays for the invoice (open amount minus discount and fees)
	 * @return the amount that the user entered, or, if it cannot be booked exactly because of the rounding of the VAT, the closest amount that can, with a note.
	 *         An amount above what the customer pays gets the note why it cannot be booked.
	 */
	public static PaymentBonusRowValues entered(
			@Nullable final PaymentBonusDeduction deduction,
			@NonNull final Amount enteredAmt,
			@NonNull final Amount maxBonusAmt,
			@Nullable final ITranslatableString currentNote)
	{
		final PaymentBonusRowValues entered = builder()
				.paymentBonusAmt(enteredAmt)
				.paymentBonusDeduction(deduction)
				.build();
		if (deduction == null)
		{
			// nothing to adjust; a non-zero amount is rejected when allocating, and the note still says why there is no bonus
			return entered.toBuilder().paymentBonusNote(currentNote).build();
		}
		if (enteredAmt.signum() <= 0)
		{
			// nothing to adjust; a negative amount is rejected when allocating. No bonus: the note why (e.g. not pre-filled) stays
			return entered.toBuilder().paymentBonusNote(currentNote).build();
		}
		final Money bookedGrossAmt = deduction.withGrossAmount(Money.of(enteredAmt.toBigDecimal(), deduction.getCurrencyId())).getGrossAmount();
		final Amount bookedAmt = toAmount(bookedGrossAmt, enteredAmt.getCurrencyCode());
		if (bookedAmt.compareTo(maxBonusAmt) > 0)
		{
			// rejected when allocating, which checks the booked amount too; the note says why right away
			return entered.toBuilder()
					.paymentBonusAmt(bookedAmt)
					.paymentBonusNote(aboveWhatTheCustomerPays(bookedAmt, maxBonusAmt))
					.build();
		}
		if (bookedAmt.compareTo(enteredAmt) == 0)
		{
			return entered;
		}

		return entered.toBuilder()
				.paymentBonusAmt(bookedAmt)
				.paymentBonusNote(msg(MSG_ADJUSTED, enteredAmt, bookedAmt))
				.build();
	}

	/**
	 * @return the short reason why a bonus cannot be booked: it is above what the customer pays; e.g. "Bonus 3,57 EUR > zahlbar 2,00 EUR"
	 */
	public static ITranslatableString aboveWhatTheCustomerPays(@NonNull final Amount bonusAmt, @NonNull final Amount maxBonusAmt)
	{
		return msg(MSG_ABOVE_WHAT_THE_CUSTOMER_PAYS, bonusAmt, maxBonusAmt);
	}

	/** the row's amounts are in the currency of the invoice, like the bonus */
	private static Amount toAmount(@NonNull final Money money, @NonNull final CurrencyCode currencyCode)
	{
		return Amount.of(money.toBigDecimal(), currencyCode);
	}

	private static ITranslatableString msg(@NonNull final AdMessageKey key, @NonNull final Object... params)
	{
		return Services.get(IMsgBL.class).getTranslatableMsgText(key, params);
	}
}
