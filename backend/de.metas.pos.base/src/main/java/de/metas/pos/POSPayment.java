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
import java.time.Duration;
import java.time.Instant;
import java.util.Objects;

@Data
public class POSPayment
{
	/**
	 * How far SumUp's clock may run behind the till's: a SumUp transaction created more than this before the payment's current
	 * checkout started belongs to an earlier checkout.
	 * <p>
	 * Enough to catch earlier checkouts: a checkout is released no earlier than the release wait ({@link POSCardReleaseWait},
	 * at least 150 s) after it started, and SumUp creates its transaction up to about 60 s (the SumUp client's connect timeout)
	 * after that start. So the transaction of the checkout released last was created at least about 90 s before the next
	 * checkout started: about 30 s beyond this allowance. That checkout is caught by its released id anyway, whenever it names
	 * one; every checkout before it is at least two release waits older.
	 * <p>
	 * If SumUp's clock runs more than this behind, a result of the current checkout that arrives before the checkout's SumUp id
	 * is stored on the payment (step 3 of the checkout) looks like an earlier checkout's: it is not applied then, and, while the
	 * checkout awaits its SumUp id, not flagged as a late approval either. Once step 3 stores the id, the checkout's own SumUp
	 * transaction, as metasfresh stores it, is applied if it is final (so the current checkout's result is not lost), and an
	 * approval of another checkout that arrived meanwhile is flagged then (see {@link #isLateApprovalOfAnEarlierCheckout}). A
	 * PENDING card payment naming a SumUp transaction that is final is also reconciled by the background sweep.
	 */
	static final Duration SUMUP_CLOCK_SKEW_ALLOWANCE = Duration.ofSeconds(60);

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

	/**
	 * @return whether this is a card payment whose SumUp result is unknown (it can be checked and, after the wait, released)
	 */
	public boolean isUnknownCardPayment() {return getPaymentMethod().isCard() && isUnknown();}

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
	 * <li>a result of an earlier checkout leaves the payment unchanged (see {@link #isOfAnEarlierCheckout}); it is one if it
	 * does not name the SumUp transaction of the payment's current checkout and
	 * <ul>
	 * <li>names another one while the current checkout names one; or</li>
	 * <li>its SumUp transaction was created more than {@link #SUMUP_CLOCK_SKEW_ALLOWANCE} before the current checkout started; or</li>
	 * <li>the payment was released at least once, its current checkout names no SumUp transaction, the result names one, and
	 * the payment does not await a result (neither PENDING nor UNKNOWN);</li>
	 * </ul>
	 * </li>
	 * <li>SUCCESSFUL on a DELETED payment leaves it unchanged;</li>
	 * <li>PENDING on an UNKNOWN payment keeps it UNKNOWN;</li>
	 * <li>any other result is applied as is (e.g. the current checkout's own result, or SUCCESSFUL on a FAILED / CANCELLED
	 * payment that was never released and whose checkout start is not known). If the payment names no SumUp client
	 * transaction id yet and the applied result is not PENDING, the result's client transaction id is stored as the payment's,
	 * so later results of that transaction count as the current checkout's own; a PENDING result leaves the id unset (the
	 * checkout's step 3 stores it).</li>
	 * </ol>
	 * A SUCCESSFUL result that leaves the payment unchanged by rules 1 to 3 is flagged as a late approval not booked (to be
	 * reviewed by the back office), except one left unchanged by rule 2 only for its creation time while the current checkout
	 * awaits its SumUp id: it may be that checkout's own approval, so step 3 decides (see {@link #isLateApprovalOfAnEarlierCheckout}).
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
			// while the current checkout awaits its SumUp id, only the creation time can make it an earlier checkout's: it may be the
			// current checkout's own approval (SumUp's clock behind); which it is shows when step 3 stores the id, which flags it then
			return POSPaymentRemoteUpdateResult.of(this, remoteStatus.isSuccessful() && !isAwaitingItsSumUpId());
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
				// a non-PENDING result is the current checkout's own: if no SumUp id is stored yet, store the result's
				.sumUpClientTransactionId(sumUpClientTransactionId != null || remoteStatus.isPending() ? sumUpClientTransactionId : response.getClientTransactionId())
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
	 * @return whether the result is of an earlier checkout of this payment, not of its current one. A result naming the SumUp
	 * transaction of the current checkout is always the current checkout's own. Otherwise it is an earlier checkout's if:
	 * <ul>
	 * <li>it names another SumUp transaction while the current checkout names one; or</li>
	 * <li>its SumUp transaction was created more than {@link #SUMUP_CLOCK_SKEW_ALLOWANCE} before the current checkout started
	 * (a payment checked out before the checkout start was recorded has none, and is not covered); or</li>
	 * <li>the payment was released at least once, its current checkout names no SumUp transaction, the result names one,
	 * and the payment does not await a result: then no result of the current checkout can arrive.</li>
	 * </ul>
	 * Otherwise, while the current checkout names no SumUp transaction yet, the result is taken as its own: SumUp's status event
	 * of a new checkout may arrive before its answer is stored.
	 */
	private boolean isOfAnEarlierCheckout(@NonNull final POSPaymentProcessResponse response)
	{
		final String remoteClientTransactionId = response.getClientTransactionId();
		if (sumUpClientTransactionId != null && sumUpClientTransactionId.equals(remoteClientTransactionId))
		{
			return false;
		}
		if (sumUpClientTransactionId != null && remoteClientTransactionId != null)
		{
			return true;
		}
		if (isCreatedBeforeTheCurrentCheckoutStarted(response))
		{
			return true;
		}
		return remoteClientTransactionId != null && releasedSumUpClientTransactionId != null && !isAwaitingAResult();
	}

	/**
	 * For step 3 of a card checkout, once the checkout's SumUp id is stored: whether the given result, which may have arrived
	 * while the checkout awaited that id (and was then neither applied nor flagged, see {@link #changingStatusFromRemote}), is a
	 * SumUp approval of an earlier checkout of this payment, to be flagged as a late approval not booked. It is one if it is
	 * SUCCESSFUL, names neither the current checkout's SumUp transaction nor the released one (whose results are flagged when they
	 * arrive), and its SumUp transaction was created more than {@link #SUMUP_CLOCK_SKEW_ALLOWANCE} before the current checkout started.
	 */
	public boolean isLateApprovalOfAnEarlierCheckout(@NonNull final POSPaymentProcessResponse response)
	{
		final String remoteClientTransactionId = response.getClientTransactionId();
		return response.getStatus().isSuccessful()
				&& !Objects.equals(sumUpClientTransactionId, remoteClientTransactionId)
				&& !Objects.equals(releasedSumUpClientTransactionId, remoteClientTransactionId)
				&& isCreatedBeforeTheCurrentCheckoutStarted(response);
	}

	private boolean isCreatedBeforeTheCurrentCheckoutStarted(@NonNull final POSPaymentProcessResponse response)
	{
		final Instant transactionCreated = response.getTransactionCreated();
		return transactionCreated != null
				&& cardCheckoutStartedAt != null
				&& transactionCreated.isBefore(cardCheckoutStartedAt.minus(SUMUP_CLOCK_SKEW_ALLOWANCE));
	}

	private boolean isAwaitingAResult()
	{
		return isPending() || isUnknown();
	}

	/**
	 * @return whether the current card checkout awaits its step 3, which stores the checkout's SumUp id (PENDING, naming none yet)
	 */
	private boolean isAwaitingItsSumUpId()
	{
		return isPending() && sumUpClientTransactionId == null;
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
