package de.metas.pos;

import de.metas.common.util.CoalesceUtil;
import de.metas.i18n.BooleanWithReason;
import de.metas.money.Money;
import de.metas.payment.PaymentId;
import de.metas.pos.payment_gateway.POSPaymentProcessResponse;
import de.metas.util.Check;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.Instant;

@Data
public class POSPayment
{
	@NonNull private final POSPaymentExternalId externalId;
	@Nullable private POSPaymentId localId;

	@NonNull private final POSPaymentMethod paymentMethod;
	@NonNull private final Money amount;

	@NonNull private final Money cashTenderedAmount;
	@NonNull private final Money cashGiveBackAmount;

	@NonNull private final POSPaymentProcessingStatus paymentProcessingStatus;
	@Nullable private final POSPaymentCardProcessingDetails cardProcessingDetails;
	@Nullable private final PaymentId paymentReceiptId;

	/** SumUp's client transaction id of the current card checkout; null until known. */
	@Nullable private final String sumUpClientTransactionId;
	/** SumUp's client transaction id of a checkout the cashier released as not charged; its late results are never booked. */
	@Nullable private final String releasedSumUpClientTransactionId;
	@Nullable private final Instant cardCheckoutStartedAt;
	@Nullable private final Instant cardLookupFailingSince;

	@Builder(toBuilder = true)
	private POSPayment(
			@NonNull final POSPaymentExternalId externalId,
			@Nullable final POSPaymentId localId,
			@NonNull final POSPaymentMethod paymentMethod,
			@NonNull final Money amount,
			@Nullable final Money cashTenderedAmount,
			@NonNull final POSPaymentProcessingStatus paymentProcessingStatus,
			@Nullable final POSPaymentCardProcessingDetails cardProcessingDetails,
			@Nullable final PaymentId paymentReceiptId,
			@Nullable final String sumUpClientTransactionId,
			@Nullable final String releasedSumUpClientTransactionId,
			@Nullable final Instant cardCheckoutStartedAt,
			@Nullable final Instant cardLookupFailingSince)
	{
		this.externalId = externalId;
		this.localId = localId;
		this.paymentMethod = paymentMethod;
		this.amount = amount;
		this.paymentProcessingStatus = paymentProcessingStatus;
		this.cardProcessingDetails = cardProcessingDetails;
		this.paymentReceiptId = paymentReceiptId;
		this.sumUpClientTransactionId = sumUpClientTransactionId;
		this.releasedSumUpClientTransactionId = releasedSumUpClientTransactionId;
		this.cardCheckoutStartedAt = cardCheckoutStartedAt;
		this.cardLookupFailingSince = cardLookupFailingSince;

		final Money zero = amount.toZero();
		if (paymentMethod.isCash())
		{
			this.cashTenderedAmount = CoalesceUtil.coalesceNotNull(cashTenderedAmount, zero);
			this.cashGiveBackAmount = this.cashTenderedAmount.subtract(this.amount);
		}
		else
		{
			this.cashTenderedAmount = zero;
			this.cashGiveBackAmount = zero;
		}
		Money.assertSameCurrency(this.amount, this.cashTenderedAmount, this.cashGiveBackAmount);
	}

	public POSPaymentId getLocalIdNotNull() {return Check.assumeNotNull(this.getLocalId(), "Expected POSPayment to be saved: {}", this);}

	public boolean isNew() {return paymentProcessingStatus.isNew();}

	public boolean isPending() {return paymentProcessingStatus.isPending();}

	public boolean isSuccessful() {return paymentProcessingStatus.isSuccessful();}

	public boolean isDeleted() {return paymentProcessingStatus.isDeleted();}

	public boolean isUnknown() {return paymentProcessingStatus.isUnknown();}

	public boolean isAllowDeleteFromDB() {return paymentProcessingStatus.checkAllowDeleteFromDB().isTrue();}

	public boolean isAllowDelete()
	{
		return checkAllowDelete().isTrue();
	}

	private BooleanWithReason checkAllowDelete()
	{
		if (paymentReceiptId != null)
		{
			return BooleanWithReason.falseBecause("Deleting a POS payment with receipt is not allowed");
		}

		return paymentProcessingStatus.checkAllowDelete(paymentMethod);
	}

	void assertAllowDelete()
	{
		checkAllowDelete().assertTrue();
	}

	public void assertAllowDeleteFromDB()
	{
		paymentProcessingStatus.checkAllowDeleteFromDB()
				.and(this::checkAllowDelete)
				.assertTrue();
	}

	public void assertAllowCheckout()
	{
		if (paymentMethod.isCash() && paymentProcessingStatus.isPending())
			return; // FIXME workaround!

		paymentProcessingStatus.assertAllowCheckout();
	}

	public boolean isAllowRefund()
	{
		return paymentMethod.isCard() && paymentProcessingStatus.isAllowRefund();
	}

	public void assertAllowRefund()
	{
		if (!isAllowRefund())
		{
			throw new AdempiereException("Payments with " + paymentMethod + "/" + paymentProcessingStatus + " cannot be refunded");
		}
	}

	public BooleanWithReason checkAllowVoid()
	{
		if (paymentProcessingStatus.isUnknown())
		{
			return BooleanWithReason.falseBecause(POSPaymentProcessingStatus.MSG_CardPaymentResultUnknown);
		}

		return paymentProcessingStatus.isPendingOrSuccessful()
				? BooleanWithReason.falseBecause("Pending or Successful payments cannot be voided")
				: BooleanWithReason.TRUE;
	}

	public void assertNoPaymentReceipt()
	{
		if (paymentReceiptId != null)
		{
			throw new AdempiereException("Expected POS Payment to not have a payment receipt")
					.setParameter("posPayment", this);
		}
	}

	public POSPayment changingStatusToPending()
	{
		if (paymentProcessingStatus.isPending())
		{
			return this;
		}
		if (!paymentProcessingStatus.isNew())
		{
			throw new AdempiereException("Cannot change status from " + paymentProcessingStatus + " to Pending");
		}

		return toBuilder().paymentProcessingStatus(POSPaymentProcessingStatus.PENDING).build();
	}

	public POSPayment changingStatusToSuccessful()
	{
		if (paymentProcessingStatus == POSPaymentProcessingStatus.SUCCESSFUL)
		{
			return this;
		}

		return toBuilder().paymentProcessingStatus(POSPaymentProcessingStatus.SUCCESSFUL).build();
	}

	public POSPayment changingStatusToDeleted()
	{
		if (isDeleted())
		{
			return this;
		}

		assertAllowDelete();
		return toBuilder().paymentProcessingStatus(POSPaymentProcessingStatus.DELETED).build();
	}

	/**
	 * Applies a status reported by the payment processor. These rules are checked in this order:
	 * <ol>
	 * <li>a result of the released checkout ({@code releasedSumUpClientTransactionId}) leaves the payment unchanged;</li>
	 * <li>a result of an earlier checkout leaves the payment unchanged (see {@link #isOfAnEarlierCheckout}):
	 * <ul>
	 * <li>the result and the payment's current checkout both name a SumUp transaction, and they differ; or</li>
	 * <li>the payment was released at least once, its current checkout names no SumUp transaction, the result names one, and
	 * the payment does not await a result (neither PENDING nor UNKNOWN);</li>
	 * </ul>
	 * </li>
	 * <li>SUCCESSFUL on a DELETED payment leaves it unchanged;</li>
	 * <li>PENDING on an UNKNOWN payment keeps it UNKNOWN;</li>
	 * <li>any other result is applied as is (e.g. SUCCESSFUL on a FAILED / CANCELLED payment that was never released, or on one
	 * whose current checkout's own result it is).</li>
	 * </ol>
	 * A result that leaves the payment unchanged by rules 1 to 3 is flagged as a late approval not booked (to be reviewed by
	 * the back office) if it is SUCCESSFUL. So a released payment that does not await a result is never turned SUCCESSFUL by a result naming a SumUp transaction.
	 * Limit: while a payment awaits a result and its current checkout names no SumUp transaction yet, a result of an earlier
	 * checkout other than the last released one cannot be told apart from the current checkout's own, and is applied.
	 */
	public POSPaymentRemoteUpdateResult changingStatusFromRemote(@NonNull final POSPaymentProcessResponse response)
	{
		paymentMethod.assertCard();

		final POSPaymentProcessingStatus remoteStatus = response.getStatus();

		if (releasedSumUpClientTransactionId != null && releasedSumUpClientTransactionId.equals(response.getClientTransactionId()))
		{
			return POSPaymentRemoteUpdateResult.of(this, remoteStatus.isSuccessful());
		}
		if (isOfAnEarlierCheckout(response))
		{
			return POSPaymentRemoteUpdateResult.of(this, remoteStatus.isSuccessful());
		}
		if (isDeleted() && remoteStatus.isSuccessful())
		{
			return POSPaymentRemoteUpdateResult.of(this, true);
		}
		if (isUnknown() && remoteStatus.isPending())
		{
			return POSPaymentRemoteUpdateResult.of(this, false);
		}

		// NOTE: when changing status from remote we cannot validate if the status transition is OK
		// we have to accept what we have on remote.

		final POSPayment paymentChanged = toBuilder()
				.paymentProcessingStatus(remoteStatus)
				.cardProcessingDetails(POSPaymentCardProcessingDetails.builder()
						.config(response.getConfig())
						.transactionId(response.getTransactionId())
						.summary(response.getSummary())
						.cardReader(response.getCardReader())
						.build())
				.build();
		return POSPaymentRemoteUpdateResult.of(paymentChanged, false);
	}

	/**
	 * @return whether the result is of an earlier checkout of this payment, not of its current one:
	 * <ul>
	 * <li>both the result and the payment's current checkout name a SumUp transaction, and they differ; or</li>
	 * <li>the payment was released at least once, its current checkout names no SumUp transaction, the result names one,
	 * and the payment does not await a result. While it awaits one (PENDING or UNKNOWN), the result may be the current
	 * checkout's own: SumUp's status event of a new checkout may arrive before its answer is stored. Otherwise (e.g. FAILED
	 * after a release of a checkout that got no answer) only an earlier checkout can still report.</li>
	 * </ul>
	 * A payment that was never released is not covered by the second case: its results are applied as before.
	 */
	private boolean isOfAnEarlierCheckout(@NonNull final POSPaymentProcessResponse response)
	{
		final String remoteClientTransactionId = response.getClientTransactionId();
		if (remoteClientTransactionId == null)
		{
			return false;
		}
		if (sumUpClientTransactionId != null)
		{
			return !sumUpClientTransactionId.equals(remoteClientTransactionId);
		}
		return releasedSumUpClientTransactionId != null && !isAwaitingAResult();
	}

	private boolean isAwaitingAResult()
	{
		return isPending() || isUnknown();
	}

	/**
	 * Step 1 of a card checkout: the payment is PENDING from now on, for a new attempt that names no SumUp transaction yet.
	 *
	 * @throws AdempiereException if the payment cannot be checked out (e.g. its card result is unknown)
	 */
	public POSPayment changingStatusToCardCheckoutStarted(@NonNull final Instant startedAt)
	{
		paymentMethod.assertCard();
		assertAllowCheckout();

		return toBuilder()
				.paymentProcessingStatus(POSPaymentProcessingStatus.PENDING)
				.cardCheckoutStartedAt(startedAt)
				.sumUpClientTransactionId(null)
				.build();
	}

	/**
	 * Step 3 of a card checkout: applies SumUp's answer to the checkout sent in step 2, only while the payment is still
	 * PENDING (otherwise a status event already applied a later result, which is kept):
	 * <ul>
	 * <li>accepted → its status (PENDING as a rule), naming SumUp's client transaction id of this attempt;</li>
	 * <li>definite rejection → FAILED;</li>
	 * <li>indeterminate (the charge may or may not have happened) → UNKNOWN, naming SumUp's client transaction id if
	 * SumUp accepted the checkout before the failure, otherwise none.</li>
	 * </ul>
	 */
	public POSPayment changingStatusFromSendResult(@NonNull final POSPaymentProcessResponse response)
	{
		paymentMethod.assertCard();
		if (!isPending())
		{
			return this;
		}

		final POSPaymentProcessingStatus newStatus = response.isIndeterminate()
				? POSPaymentProcessingStatus.UNKNOWN
				: response.getStatus();

		return toBuilder()
				.paymentProcessingStatus(newStatus)
				.sumUpClientTransactionId(response.getClientTransactionId())
				.cardProcessingDetails(POSPaymentCardProcessingDetails.builder()
						.config(response.getConfig())
						.transactionId(response.getTransactionId())
						.summary(response.getSummary())
						.cardReader(response.getCardReader())
						.build())
				.build();
	}

	/**
	 * The orphan rule: a card checkout that stopped before SumUp's answer was stored (PENDING, naming no SumUp transaction)
	 * becomes UNKNOWN; any other payment is returned unchanged. The caller decides whether the checkout is old enough and
	 * has no SumUp transaction of its own.
	 */
	public POSPayment changingStatusToUnknownIfOrphan()
	{
		if (!paymentMethod.isCard() || !isPending() || sumUpClientTransactionId != null)
		{
			return this;
		}

		return toBuilder()
				.paymentProcessingStatus(POSPaymentProcessingStatus.UNKNOWN)
				.build();
	}

	/**
	 * The cashier released this card payment whose result is unknown, confirming that the card was not charged: it is FAILED
	 * (so it can be deleted or retried), and its current checkout's SumUp transaction, if it names one, is remembered as
	 * released, so that a late result of that checkout is never booked.
	 *
	 * @throws AdempiereException if it is not a card payment whose result is unknown
	 */
	public POSPayment changingStatusToReleasedAsNotCharged()
	{
		paymentMethod.assertCard();
		if (!isUnknown())
		{
			throw new AdempiereException("Only a card payment whose result is unknown can be released")
					.setParameter("posPayment", this);
		}

		return toBuilder()
				.paymentProcessingStatus(POSPaymentProcessingStatus.FAILED)
				// a checkout that named no SumUp transaction (timeout, network error, stopped) keeps an earlier released one guarded
				.releasedSumUpClientTransactionId(sumUpClientTransactionId != null ? sumUpClientTransactionId : releasedSumUpClientTransactionId)
				.build();
	}

	/**
	 * A SumUp lookup made for this card payment failed: from now on (unless already) it is failing a SumUp lookup.
	 */
	public POSPayment withCardLookupFailed(@NonNull final Instant now)
	{
		return cardLookupFailingSince != null
				? this
				: toBuilder().cardLookupFailingSince(now).build();
	}

	/**
	 * A SumUp lookup made for this card payment succeeded (whether SumUp knew the transaction or not): an ongoing outage ended.
	 */
	public POSPayment withCardLookupSucceeded()
	{
		return cardLookupFailingSince == null
				? this
				: toBuilder().cardLookupFailingSince(null).build();
	}

	public POSPayment withPaymentReceipt(@Nullable final PaymentId paymentReceiptId)
	{
		if (PaymentId.equals(this.paymentReceiptId, paymentReceiptId))
		{
			return this;
		}

		if (paymentReceiptId != null && !paymentProcessingStatus.isSuccessful())
		{
			throw new AdempiereException("Cannot set a payment receipt if status is not successful");
		}

		if (this.paymentReceiptId != null && paymentReceiptId != null)
		{
			throw new AdempiereException("Changing the payment receipt is not allowed");
		}

		return toBuilder().paymentReceiptId(paymentReceiptId).build();
	}

	public POSPayment withCashTenderedAmount(@NonNull final BigDecimal cashTenderedAmountBD)
	{
		paymentMethod.assertCash();
		Check.assume(cashTenderedAmountBD.signum() > 0, "Cash Tendered Amount must be positive");

		final Money cashTenderedAmountNew = Money.of(cashTenderedAmountBD, this.cashTenderedAmount.getCurrencyId());
		if (Money.equals(this.cashTenderedAmount, cashTenderedAmountNew))
		{
			return this;
		}

		return toBuilder().cashTenderedAmount(cashTenderedAmountNew).build();
	}

	public POSPayment withCardPayAmount(@NonNull final BigDecimal cardPayAmountBD)
	{
		paymentMethod.assertCard();
		Check.assume(cardPayAmountBD.signum() > 0, "Card Pay Amount must be positive");

		final Money amountNew = Money.of(cardPayAmountBD, this.amount.getCurrencyId());
		if (Money.equals(this.amount, amountNew))
		{
			return this;
		}

		return toBuilder().amount(amountNew).build();
	}
}
