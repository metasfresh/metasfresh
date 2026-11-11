package de.metas.pos;

import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.pos.payment_gateway.POSPaymentProcessResponse;
import de.metas.pos.payment_gateway.POSPaymentProcessorType;
import de.metas.i18n.BooleanWithReason;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.test.AdempiereTestHelper;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class POSPaymentTest
{
	private static final CurrencyId EUR = CurrencyId.ofRepoId(318);
	private static final String RELEASED_TRX = "released-trx";
	private static final String OTHER_TRX = "other-trx";

	private static POSPayment cardPayment(final POSPaymentProcessingStatus status)
	{
		return cardPaymentBuilder(status).build();
	}

	private static POSPayment.POSPaymentBuilder cardPaymentBuilder(final POSPaymentProcessingStatus status)
	{
		return POSPayment.builder()
				.externalId(POSPaymentExternalId.ofString(UUID.randomUUID().toString()))
				.localId(POSPaymentId.ofRepoId(1))
				.paymentMethod(POSPaymentMethod.CARD)
				.amount(Money.of(new BigDecimal("10.00"), EUR))
				.paymentProcessingStatus(status);
	}

	private static POSPaymentProcessResponse response(final POSPaymentProcessingStatus status, final String clientTransactionId)
	{
		return POSPaymentProcessResponse.builder()
				.status(status)
				.config(POSTerminalPaymentProcessorConfig.builder().type(POSPaymentProcessorType.SumUp).build())
				.transactionId("ext-1")
				.clientTransactionId(clientTransactionId)
				.build();
	}

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		POSTestMessages.createCardPaymentResultUnknown();
	}

	@Test
	void unknown_notCheckoutableNotDeletableNotVoidable()
	{
		final POSPayment payment = cardPayment(POSPaymentProcessingStatus.UNKNOWN);

		assertThat(payment.getPaymentProcessingStatus().isUnknown()).isTrue();
		assertThat(payment.getPaymentProcessingStatus().isAllowCheckout()).isFalse();
		assertThat(payment.isAllowDelete()).isFalse();

		// each refusal tells the cashier why: the result of the card payment is unknown
		final BooleanWithReason canVoid = payment.checkAllowVoid();
		assertThat(canVoid.isFalse()).isTrue();
		assertThat(canVoid.getReasonAsString()).contains(POSPaymentProcessingStatus.MSG_CardPaymentResultUnknown.toAD_Message());
		assertThat(POSPaymentProcessingStatus.UNKNOWN.checkAllowDelete(POSPaymentMethod.CARD).getReasonAsString())
				.contains(POSPaymentProcessingStatus.MSG_CardPaymentResultUnknown.toAD_Message());
		assertRefusedAsResultUnknown(payment::assertAllowDelete);
		assertThatThrownBy(payment::assertAllowCheckout)
				.extracting(ex -> ((AdempiereException)ex).getErrorCode())
				.isEqualTo(POSPaymentProcessingStatus.MSG_CardPaymentResultUnknown.toAD_Message());
		assertRefusedAsResultUnknown(payment::changingStatusToDeleted);
	}

	private static void assertRefusedAsResultUnknown(final ThrowingCallable callable)
	{
		assertThatThrownBy(callable)
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining(POSPaymentProcessingStatus.MSG_CardPaymentResultUnknown.toAD_Message());
	}

	@Test
	void remote_onUnknown_pendingKeepsUnknown_finalStatusesApplied()
	{
		final POSPayment unknown = cardPayment(POSPaymentProcessingStatus.UNKNOWN);

		final POSPaymentRemoteUpdateResult pending = unknown.changingStatusFromRemote(response(POSPaymentProcessingStatus.PENDING, "trx-1"));
		assertThat(pending.getPayment().getPaymentProcessingStatus()).isEqualTo(POSPaymentProcessingStatus.UNKNOWN);
		assertThat(pending.isLateApprovalNotBooked()).isFalse();

		for (final POSPaymentProcessingStatus status : new POSPaymentProcessingStatus[] { POSPaymentProcessingStatus.SUCCESSFUL, POSPaymentProcessingStatus.FAILED, POSPaymentProcessingStatus.CANCELLED })
		{
			final POSPaymentRemoteUpdateResult result = unknown.changingStatusFromRemote(response(status, "trx-1"));
			assertThat(result.getPayment().getPaymentProcessingStatus()).isEqualTo(status);
			assertThat(result.isLateApprovalNotBooked()).isFalse();
		}
	}

	@Test
	void remoteSuccessful_onDeleted_unchangedAndFlagged()
	{
		final POSPayment deleted = cardPayment(POSPaymentProcessingStatus.DELETED);

		final POSPaymentRemoteUpdateResult result = deleted.changingStatusFromRemote(response(POSPaymentProcessingStatus.SUCCESSFUL, "trx-1"));

		assertThat(result.getPayment()).isEqualTo(deleted);
		assertThat(result.isLateApprovalNotBooked()).isTrue();

		// every other remote status on a DELETED payment is handled as today
		final POSPaymentRemoteUpdateResult failed = deleted.changingStatusFromRemote(response(POSPaymentProcessingStatus.FAILED, "trx-1"));
		assertThat(failed.getPayment().getPaymentProcessingStatus()).isEqualTo(POSPaymentProcessingStatus.FAILED);
		assertThat(failed.isLateApprovalNotBooked()).isFalse();
	}

	@Test
	void remote_ofReleasedCheckout_unchanged_successfulFlagged()
	{
		final POSPayment released = cardPaymentBuilder(POSPaymentProcessingStatus.FAILED).releasedSumUpClientTransactionId(RELEASED_TRX).build();

		for (final POSPaymentProcessingStatus status : POSPaymentProcessingStatus.values())
		{
			final POSPaymentRemoteUpdateResult result = released.changingStatusFromRemote(response(status, RELEASED_TRX));
			assertThat(result.getPayment()).as("status " + status).isEqualTo(released);
			assertThat(result.isLateApprovalNotBooked()).as("flag for " + status).isEqualTo(status == POSPaymentProcessingStatus.SUCCESSFUL);
		}

		// the retry's own event, while the retry awaits its result (PENDING, naming OTHER_TRX): applied, since that payment is not released
		final POSPayment retrying = cardPaymentBuilder(POSPaymentProcessingStatus.PENDING)
				.sumUpClientTransactionId(OTHER_TRX)
				.releasedSumUpClientTransactionId(RELEASED_TRX)
				.build();
		final POSPaymentRemoteUpdateResult retry = retrying.changingStatusFromRemote(response(POSPaymentProcessingStatus.SUCCESSFUL, OTHER_TRX));
		assertThat(retry.getPayment().getPaymentProcessingStatus()).isEqualTo(POSPaymentProcessingStatus.SUCCESSFUL);
		assertThat(retry.isLateApprovalNotBooked()).isFalse();

		// another checkout's SUCCESSFUL on the released FAILED payment naming no current checkout: not applied, flagged, since a released payment is never turned successful
		final POSPaymentRemoteUpdateResult otherOnReleased = released.changingStatusFromRemote(response(POSPaymentProcessingStatus.SUCCESSFUL, OTHER_TRX));
		assertThat(otherOnReleased.getPayment()).isEqualTo(released);
		assertThat(otherOnReleased.isLateApprovalNotBooked()).isTrue();

		// a payment that was never released (null) does not match an event without client transaction id
		final POSPayment notReleased = cardPayment(POSPaymentProcessingStatus.FAILED);
		final POSPaymentRemoteUpdateResult noId = notReleased.changingStatusFromRemote(response(POSPaymentProcessingStatus.SUCCESSFUL, null));
		assertThat(noId.getPayment().getPaymentProcessingStatus()).isEqualTo(POSPaymentProcessingStatus.SUCCESSFUL);
		assertThat(noId.isLateApprovalNotBooked()).isFalse();
	}

	@Test
	void remoteSuccessful_onPlainFailed_appliedAsToday()
	{
		final POSPaymentRemoteUpdateResult result = cardPayment(POSPaymentProcessingStatus.FAILED)
				.changingStatusFromRemote(response(POSPaymentProcessingStatus.SUCCESSFUL, "trx-1"));

		assertThat(result.getPayment().getPaymentProcessingStatus()).isEqualTo(POSPaymentProcessingStatus.SUCCESSFUL);
		assertThat(result.isLateApprovalNotBooked()).isFalse();
	}

	@Test
	void remoteSuccessful_onPlainCancelled_appliedAsToday()
	{
		final POSPaymentRemoteUpdateResult result = cardPayment(POSPaymentProcessingStatus.CANCELLED)
				.changingStatusFromRemote(response(POSPaymentProcessingStatus.SUCCESSFUL, "trx-1"));

		assertThat(result.getPayment().getPaymentProcessingStatus()).isEqualTo(POSPaymentProcessingStatus.SUCCESSFUL);
		assertThat(result.isLateApprovalNotBooked()).isFalse();
	}

	@Test
	void remote_onPending_appliedAsToday()
	{
		final POSPayment pending = cardPayment(POSPaymentProcessingStatus.PENDING);

		for (final POSPaymentProcessingStatus status : new POSPaymentProcessingStatus[] { POSPaymentProcessingStatus.SUCCESSFUL, POSPaymentProcessingStatus.FAILED, POSPaymentProcessingStatus.CANCELLED, POSPaymentProcessingStatus.PENDING })
		{
			final POSPaymentRemoteUpdateResult result = pending.changingStatusFromRemote(response(status, "trx-1"));
			assertThat(result.getPayment().getPaymentProcessingStatus()).isEqualTo(status);
			assertThat(result.getPayment().getCardProcessingDetails().getTransactionId()).isEqualTo("ext-1");
			assertThat(result.isLateApprovalNotBooked()).isFalse();
		}
	}

	private static final POSTerminalPaymentProcessorConfig SUMUP_CONFIG = POSTerminalPaymentProcessorConfig.builder().type(POSPaymentProcessorType.SumUp).build();

	@Test
	void sendIndeterminate_withClientId_toUnknownLinked()
	{
		// cause (i): SumUp accepted the checkout, metasfresh could not save its transaction; SumUp's id is known
		final POSPayment payment = cardPaymentBuilder(POSPaymentProcessingStatus.PENDING).build()
				.changingStatusFromSendResult(POSPaymentProcessResponse.errorIndeterminate(SUMUP_CONFIG, "saving failed", null, "accepted-trx"));

		assertThat(payment.getPaymentProcessingStatus()).isEqualTo(POSPaymentProcessingStatus.UNKNOWN);
		assertThat(payment.getSumUpClientTransactionId()).isEqualTo("accepted-trx");
	}

	@Test
	void sendIndeterminate_withoutClientId_toUnknown()
	{
		// cause (ii): timeout, network error or SumUp 5xx; no SumUp id is known
		final POSPayment payment = cardPaymentBuilder(POSPaymentProcessingStatus.PENDING).build()
				.changingStatusFromSendResult(POSPaymentProcessResponse.errorIndeterminate(SUMUP_CONFIG, "timeout", null, null));

		assertThat(payment.getPaymentProcessingStatus()).isEqualTo(POSPaymentProcessingStatus.UNKNOWN);
		assertThat(payment.getSumUpClientTransactionId()).isNull();
	}

	@Test
	void sendDefiniteRejection_toFailed()
	{
		final POSPayment payment = cardPaymentBuilder(POSPaymentProcessingStatus.PENDING).build()
				.changingStatusFromSendResult(POSPaymentProcessResponse.error(SUMUP_CONFIG, "HTTP 4xx", null));

		assertThat(payment.getPaymentProcessingStatus()).isEqualTo(POSPaymentProcessingStatus.FAILED);
		assertThat(payment.getSumUpClientTransactionId()).isNull();
	}

	@Test
	void sendAccepted_toPendingNamingTheAttempt()
	{
		final POSPayment payment = cardPaymentBuilder(POSPaymentProcessingStatus.PENDING).build()
				.changingStatusFromSendResult(response(POSPaymentProcessingStatus.PENDING, "accepted-trx"));

		assertThat(payment.getPaymentProcessingStatus()).isEqualTo(POSPaymentProcessingStatus.PENDING);
		assertThat(payment.getSumUpClientTransactionId()).isEqualTo("accepted-trx");
	}

	@Test
	void sendResult_onPaymentNoLongerPending_unchanged()
	{
		// a status event (e.g. SumUp's approval) was applied before the checkout's answer: the later result is kept
		final POSPayment successful = cardPaymentBuilder(POSPaymentProcessingStatus.SUCCESSFUL).sumUpClientTransactionId("accepted-trx").build();

		assertThat(successful.changingStatusFromSendResult(POSPaymentProcessResponse.errorIndeterminate(SUMUP_CONFIG, "timeout", null, null)))
				.isSameAs(successful);
		assertThat(successful.changingStatusFromSendResult(response(POSPaymentProcessingStatus.PENDING, "accepted-trx")))
				.isSameAs(successful);
	}

	@Test
	void releasedAsNotCharged_failedAndRemembersTheReleasedCheckout()
	{
		final POSPayment released = cardPaymentBuilder(POSPaymentProcessingStatus.UNKNOWN)
				.sumUpClientTransactionId(RELEASED_TRX)
				.build()
				.changingStatusToReleasedAsNotCharged();

		assertThat(released.getPaymentProcessingStatus()).isEqualTo(POSPaymentProcessingStatus.FAILED);
		assertThat(released.getReleasedSumUpClientTransactionId()).isEqualTo(RELEASED_TRX);
	}

	@Test
	void releasedAsNotCharged_checkoutNamingNoSumUpTransaction_keepsTheEarlierReleasedCheckoutGuarded()
	{
		// released once (RELEASED_TRX), retried, and the retry's checkout stopped before SumUp's answer was stored
		final POSPayment released = cardPaymentBuilder(POSPaymentProcessingStatus.UNKNOWN)
				.releasedSumUpClientTransactionId(RELEASED_TRX)
				.build()
				.changingStatusToReleasedAsNotCharged();

		assertThat(released.getPaymentProcessingStatus()).isEqualTo(POSPaymentProcessingStatus.FAILED);
		assertThat(released.getReleasedSumUpClientTransactionId()).isEqualTo(RELEASED_TRX);
		// a late approval of the first checkout is still not booked
		assertThat(released.changingStatusFromRemote(response(POSPaymentProcessingStatus.SUCCESSFUL, RELEASED_TRX)).getPayment().getPaymentProcessingStatus())
				.isEqualTo(POSPaymentProcessingStatus.FAILED);
	}

	@Test
	void releasedAsNotCharged_onlyForAnUnknownResult()
	{
		assertThatThrownBy(() -> cardPayment(POSPaymentProcessingStatus.PENDING).changingStatusToReleasedAsNotCharged())
				.isInstanceOf(AdempiereException.class);
	}

	@Test
	void remote_ofAnEarlierCheckout_unchanged_successfulFlagged()
	{
		// checkout RELEASED_TRX was released, the retry OTHER_TRX was released too: only the latter is remembered as released
		final POSPayment payment = cardPaymentBuilder(POSPaymentProcessingStatus.FAILED)
				.sumUpClientTransactionId(OTHER_TRX)
				.releasedSumUpClientTransactionId(OTHER_TRX)
				.build();

		for (final POSPaymentProcessingStatus status : POSPaymentProcessingStatus.values())
		{
			final POSPaymentRemoteUpdateResult result = payment.changingStatusFromRemote(response(status, RELEASED_TRX));
			assertThat(result.getPayment()).as("status " + status).isEqualTo(payment);
			assertThat(result.isLateApprovalNotBooked()).as("flag for " + status).isEqualTo(status == POSPaymentProcessingStatus.SUCCESSFUL);
		}
	}

	@Test
	void remote_ofTheCurrentCheckout_applied()
	{
		final POSPayment pending = cardPaymentBuilder(POSPaymentProcessingStatus.PENDING).sumUpClientTransactionId(OTHER_TRX).build();

		final POSPaymentRemoteUpdateResult result = pending.changingStatusFromRemote(response(POSPaymentProcessingStatus.SUCCESSFUL, OTHER_TRX));

		assertThat(result.getPayment().getPaymentProcessingStatus()).isEqualTo(POSPaymentProcessingStatus.SUCCESSFUL);
		assertThat(result.getPayment().getCardProcessingDetails().getTransactionId()).isEqualTo("ext-1");
		assertThat(result.isLateApprovalNotBooked()).isFalse();
	}

	@Test
	void remote_releasedPaymentNamingNoCurrentCheckout_resultOfAnEarlierCheckout_appliedOnlyWhileAwaitingAResult()
	{
		// released twice (the last one named OTHER_TRX); the third checkout named no SumUp transaction
		for (final POSPaymentProcessingStatus status : POSPaymentProcessingStatus.values())
		{
			final POSPayment payment = cardPaymentBuilder(status).releasedSumUpClientTransactionId(OTHER_TRX).build();
			final boolean awaitingAResult = status == POSPaymentProcessingStatus.PENDING || status == POSPaymentProcessingStatus.UNKNOWN;

			final POSPaymentRemoteUpdateResult result = payment.changingStatusFromRemote(response(POSPaymentProcessingStatus.SUCCESSFUL, RELEASED_TRX));

			if (awaitingAResult)
			{
				// a new checkout's own result may arrive before its SumUp id is stored on the payment
				assertThat(result.getPayment().getPaymentProcessingStatus()).as("status " + status).isEqualTo(POSPaymentProcessingStatus.SUCCESSFUL);
				assertThat(result.isLateApprovalNotBooked()).as("flag for " + status).isFalse();
			}
			else
			{
				// not awaiting a result: the result is an earlier checkout's
				assertThat(result.getPayment()).as("status " + status).isEqualTo(payment);
				assertThat(result.isLateApprovalNotBooked()).as("flag for " + status).isTrue();
			}
		}
	}

	@Test
	void remote_ofATransactionCreatedLongBeforeTheCurrentCheckoutStarted_unchanged_successfulFlagged()
	{
		// the current checkout started at 10:10 and names no SumUp transaction yet; the result's transaction was created at 10:00
		final Instant startedAt = Instant.parse("2026-11-10T10:10:00Z");
		final POSPayment awaiting = cardPaymentBuilder(POSPaymentProcessingStatus.UNKNOWN).cardCheckoutStartedAt(startedAt).build();

		for (final POSPaymentProcessingStatus status : POSPaymentProcessingStatus.values())
		{
			final POSPaymentRemoteUpdateResult result = awaiting.changingStatusFromRemote(responseCreatedAt(status, RELEASED_TRX, Instant.parse("2026-11-10T10:00:00Z")));
			assertThat(result.getPayment()).as("status " + status).isEqualTo(awaiting);
			assertThat(result.isLateApprovalNotBooked()).as("flag for " + status).isEqualTo(status == POSPaymentProcessingStatus.SUCCESSFUL);
		}
	}

	@Test
	void remote_ofATransactionCreatedAfterTheCurrentCheckoutStarted_applied()
	{
		// the current checkout's own result, arriving before its SumUp id is stored on the payment
		final Instant startedAt = Instant.parse("2026-11-10T10:10:00Z");
		final POSPayment pending = cardPaymentBuilder(POSPaymentProcessingStatus.PENDING).cardCheckoutStartedAt(startedAt).build();

		final POSPaymentRemoteUpdateResult result = pending.changingStatusFromRemote(responseCreatedAt(POSPaymentProcessingStatus.SUCCESSFUL, OTHER_TRX, startedAt.plusSeconds(1)));

		assertThat(result.getPayment().getPaymentProcessingStatus()).isEqualTo(POSPaymentProcessingStatus.SUCCESSFUL);
		assertThat(result.isLateApprovalNotBooked()).isFalse();
	}

	@Test
	void remote_ofATransactionCreatedWithinTheClockSkewAllowanceBeforeTheStart_applied()
	{
		// SumUp's clock may run behind the till's
		final Instant startedAt = Instant.parse("2026-11-10T10:10:00Z");
		final POSPayment pending = cardPaymentBuilder(POSPaymentProcessingStatus.PENDING).cardCheckoutStartedAt(startedAt).build();

		final POSPaymentRemoteUpdateResult result = pending.changingStatusFromRemote(responseCreatedAt(POSPaymentProcessingStatus.SUCCESSFUL, OTHER_TRX, startedAt.minusSeconds(60)));

		assertThat(result.getPayment().getPaymentProcessingStatus()).isEqualTo(POSPaymentProcessingStatus.SUCCESSFUL);
		assertThat(result.isLateApprovalNotBooked()).isFalse();
	}

	@Test
	void remote_currentCheckoutsOwnResultBeforeItsIdIsStored_namesTheCheckout_laterResultsApplied()
	{
		// a retry after a release: PENDING, its SumUp id not stored yet; SumUp's approval of the retry arrives first
		final Instant startedAt = Instant.parse("2026-11-10T10:10:00Z");
		final POSPayment retry = cardPaymentBuilder(POSPaymentProcessingStatus.PENDING)
				.releasedSumUpClientTransactionId(RELEASED_TRX)
				.cardCheckoutStartedAt(startedAt)
				.build();

		final POSPaymentRemoteUpdateResult approved = retry.changingStatusFromRemote(responseCreatedAt(POSPaymentProcessingStatus.SUCCESSFUL, OTHER_TRX, startedAt.plusSeconds(1)));
		assertThat(approved.getPayment().getPaymentProcessingStatus()).isEqualTo(POSPaymentProcessingStatus.SUCCESSFUL);
		assertThat(approved.getPayment().getSumUpClientTransactionId()).isEqualTo(OTHER_TRX);

		// the same approval again (e.g. the back office's update of the SumUp transaction re-sends its status) is the payment's own booked charge
		final POSPaymentRemoteUpdateResult approvedAgain = approved.getPayment().changingStatusFromRemote(responseCreatedAt(POSPaymentProcessingStatus.SUCCESSFUL, OTHER_TRX, startedAt.plusSeconds(1)));
		assertThat(approvedAgain.getPayment().getPaymentProcessingStatus()).isEqualTo(POSPaymentProcessingStatus.SUCCESSFUL);
		assertThat(approvedAgain.isLateApprovalNotBooked()).isFalse();

		// SumUp refunds the retry's charge
		final POSPaymentRemoteUpdateResult refunded = approvedAgain.getPayment().changingStatusFromRemote(responseCreatedAt(POSPaymentProcessingStatus.DELETED, OTHER_TRX, startedAt.plusSeconds(1)));
		assertThat(refunded.getPayment().getPaymentProcessingStatus()).isEqualTo(POSPaymentProcessingStatus.DELETED);
		assertThat(refunded.isLateApprovalNotBooked()).isFalse();
	}

	@Test
	void remoteSuccessful_createdLongBeforeTheStart_whileTheCurrentCheckoutAwaitsItsSumUpId_unchangedAndNotFlaggedYet()
	{
		// SumUp's clock runs more than the allowance behind: the current checkout's own approval may look older than the
		// checkout; whose approval it is shows once step 3 stores the checkout's SumUp id
		final Instant startedAt = Instant.parse("2026-11-10T10:10:00Z");
		final POSPayment awaitingItsId = cardPaymentBuilder(POSPaymentProcessingStatus.PENDING).cardCheckoutStartedAt(startedAt).build();

		final POSPaymentRemoteUpdateResult result = awaitingItsId.changingStatusFromRemote(responseCreatedAt(POSPaymentProcessingStatus.SUCCESSFUL, OTHER_TRX, startedAt.minusSeconds(61)));

		assertThat(result.getPayment()).isEqualTo(awaitingItsId);
		assertThat(result.isLateApprovalNotBooked()).isFalse();
	}

	@Test
	void isLateApprovalOfAnEarlierCheckout_onlyAnotherCheckoutsApprovalCreatedLongBeforeTheStart()
	{
		final Instant startedAt = Instant.parse("2026-11-10T10:10:00Z");
		final Instant longBefore = startedAt.minusSeconds(61);
		final POSPayment payment = cardPaymentBuilder(POSPaymentProcessingStatus.PENDING)
				.sumUpClientTransactionId("current-trx")
				.releasedSumUpClientTransactionId(RELEASED_TRX)
				.cardCheckoutStartedAt(startedAt)
				.build();

		assertThat(payment.isLateApprovalOfAnEarlierCheckout(responseCreatedAt(POSPaymentProcessingStatus.SUCCESSFUL, OTHER_TRX, longBefore))).isTrue();
		// the current checkout's own approval, whatever SumUp's clock says
		assertThat(payment.isLateApprovalOfAnEarlierCheckout(responseCreatedAt(POSPaymentProcessingStatus.SUCCESSFUL, "current-trx", longBefore))).isFalse();
		// the released checkout's approval was flagged when it arrived
		assertThat(payment.isLateApprovalOfAnEarlierCheckout(responseCreatedAt(POSPaymentProcessingStatus.SUCCESSFUL, RELEASED_TRX, longBefore))).isFalse();
		// no approval
		assertThat(payment.isLateApprovalOfAnEarlierCheckout(responseCreatedAt(POSPaymentProcessingStatus.FAILED, OTHER_TRX, longBefore))).isFalse();
		// within the clock skew allowance
		assertThat(payment.isLateApprovalOfAnEarlierCheckout(responseCreatedAt(POSPaymentProcessingStatus.SUCCESSFUL, OTHER_TRX, startedAt.minusSeconds(60)))).isFalse();
	}

	private static POSPaymentProcessResponse responseCreatedAt(final POSPaymentProcessingStatus status, final String clientTransactionId, final Instant transactionCreated)
	{
		return POSPaymentProcessResponse.builder()
				.status(status)
				.config(POSTerminalPaymentProcessorConfig.builder().type(POSPaymentProcessorType.SumUp).build())
				.transactionId("ext-1")
				.clientTransactionId(clientTransactionId)
				.transactionCreated(transactionCreated)
				.build();
	}
}
