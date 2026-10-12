package de.metas.invoice.paymentbonus;

import de.metas.currency.Amount;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.Optional;

/**
 * Decides which deductions explain a paid amount that is lower than the open amount of an invoice: Skonto, the payment bonus, or both.
 */
public final class PaymentBonusAmountMatcher
{
	private PaymentBonusAmountMatcher() {}

	/**
	 * Tries, in this order: open - Skonto, open - bonus, open - Skonto - bonus.
	 * The Skonto-only rule comes first, so a customer without a payment bonus is matched exactly as before.
	 *
	 * All amounts must be in one currency. A deduction in another currency than {@code openAmt} throws when it is subtracted (in {@link Amount#subtract(Amount)}):
	 * {@code discountAmt} always, {@code bonusGrossAmt} only if {@code bonusAllowed} is {@code true}, it is non-null, and the Skonto-only rule did not match.
	 * A {@code paidAmt} in another currency matches no rule, so the result is empty.
	 *
	 * @param bonusGrossAmt the gross payment bonus, or {@code null} if there is none
	 * @param bonusAllowed  {@code false} if no payment bonus may be deducted (e.g. a purchase document); then only the Skonto rule applies
	 * @return the matched deductions, or empty if the paid amount matches none of the rules
	 */
	public static Optional<MatchedDeductions> match(
			@NonNull final Amount openAmt,
			@NonNull final Amount discountAmt,
			@Nullable final Amount bonusGrossAmt,
			@NonNull final Amount paidAmt,
			final boolean bonusAllowed)
	{
		final Amount zero = Amount.zero(openAmt.getCurrencyCode());

		if (openAmt.subtract(discountAmt).isEqualByComparingTo(paidAmt))
		{
			return Optional.of(new MatchedDeductions(discountAmt, zero));
		}

		if (!bonusAllowed || bonusGrossAmt == null)
		{
			return Optional.empty();
		}

		if (openAmt.subtract(bonusGrossAmt).isEqualByComparingTo(paidAmt))
		{
			return Optional.of(new MatchedDeductions(zero, bonusGrossAmt));
		}

		if (openAmt.subtract(discountAmt).subtract(bonusGrossAmt).isEqualByComparingTo(paidAmt))
		{
			return Optional.of(new MatchedDeductions(discountAmt, bonusGrossAmt));
		}

		return Optional.empty();
	}

	@Value
	public static class MatchedDeductions
	{
		@NonNull Amount discountAmt;
		@NonNull Amount bonusAmt;
	}
}
