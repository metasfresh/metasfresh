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
	 * The Skonto-only rule comes first, so a customer without a payment bonus is matched by the Skonto rule only.
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

	/**
	 * How a paid amount settles an invoice. Pure and partner-agnostic: whether a deduction is <i>granted</i> to a partner is the caller's decision.
	 */
	public enum SettlementType
	{
		/** The customer deducted the payment bonus themselves: open - bonus, or open - Skonto - bonus. */
		SELF_DEDUCT,
		/** The customer paid the full amount: open, or open - Skonto (full payment still counts as pay-full when it takes the Skonto). */
		PAY_FULL,
		/** Anything else (partial or unrecognised amount, or a paid amount in another currency). */
		OTHER
	}

	/**
	 * Classifies {@code paidAmt}: {@link SettlementType#PAY_FULL} if it is {@code openAmt} or {@code openAmt - discountAmt};
	 * else {@link SettlementType#SELF_DEDUCT} if {@link #match} finds a deduction (open - bonus, open - Skonto - bonus);
	 * else {@link SettlementType#OTHER}. The pay-full rules come first, so an amount that is both (e.g. Skonto equals bonus) is PAY_FULL.
	 * Currency handling is the same as in {@link #match}: a {@code paidAmt} in another currency is OTHER, a deduction in another currency throws.
	 *
	 * @param bonusGrossAmt the gross payment bonus, or {@code null} if there is none
	 * @param bonusAllowed  {@code false} if no payment bonus may be deducted (e.g. a purchase document); then open - bonus is OTHER
	 */
	public static SettlementType classify(
			@NonNull final Amount openAmt,
			@NonNull final Amount discountAmt,
			@Nullable final Amount bonusGrossAmt,
			@NonNull final Amount paidAmt,
			final boolean bonusAllowed)
	{
		if (openAmt.isEqualByComparingTo(paidAmt) || openAmt.subtract(discountAmt).isEqualByComparingTo(paidAmt))
		{
			return SettlementType.PAY_FULL;
		}

		return match(openAmt, discountAmt, bonusGrossAmt, paidAmt, bonusAllowed).isPresent()
				? SettlementType.SELF_DEDUCT
				: SettlementType.OTHER;
	}

	@Value
	public static class MatchedDeductions
	{
		@NonNull Amount discountAmt;
		@NonNull Amount bonusAmt;
	}
}
