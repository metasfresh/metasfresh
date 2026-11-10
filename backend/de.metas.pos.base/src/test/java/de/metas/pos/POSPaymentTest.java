package de.metas.pos;

import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.pos.payment_gateway.POSPaymentProcessResponse;
import de.metas.pos.payment_gateway.POSPaymentProcessorType;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
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

	@Test
	void unknown_notCheckoutableNotDeletableNotVoidable()
	{
		final POSPayment payment = cardPayment(POSPaymentProcessingStatus.UNKNOWN);

		assertThat(payment.getPaymentProcessingStatus().isUnknown()).isTrue();
		assertThat(payment.getPaymentProcessingStatus().isAllowCheckout()).isFalse();
		assertThat(payment.isAllowDelete()).isFalse();
		assertThat(payment.checkAllowVoid().isFalse()).isTrue();
		assertThatThrownBy(payment::assertAllowDelete).isInstanceOf(RuntimeException.class);
		assertThatThrownBy(payment::assertAllowCheckout).isInstanceOf(RuntimeException.class);
		assertThatThrownBy(() -> payment.changingStatusToDeleted()).isInstanceOf(RuntimeException.class);
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

		// an event of another checkout (e.g. the retry) is applied as today
		final POSPaymentRemoteUpdateResult retry = released.changingStatusFromRemote(response(POSPaymentProcessingStatus.SUCCESSFUL, OTHER_TRX));
		assertThat(retry.getPayment().getPaymentProcessingStatus()).isEqualTo(POSPaymentProcessingStatus.SUCCESSFUL);
		assertThat(retry.isLateApprovalNotBooked()).isFalse();

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
}
