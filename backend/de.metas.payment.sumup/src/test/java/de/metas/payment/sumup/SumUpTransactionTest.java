package de.metas.payment.sumup;

import de.metas.currency.Amount;
import de.metas.currency.CurrencyCode;
import de.metas.organization.ClientAndOrgId;
import org.junit.jupiter.api.Test;

import javax.annotation.Nullable;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

class SumUpTransactionTest
{
	private static SumUpTransaction trx(final String status, @Nullable final String amountRefunded)
	{
		return SumUpTransaction.builder()
				.configId(SumUpConfigId.ofRepoId(1))
				.externalId(SumUpTransactionExternalId.ofString("ext"))
				.clientTransactionId(SumUpClientTransactionId.ofString("ctid"))
				.merchantCode(SumUpMerchantCode.ofString("MC"))
				.timestamp(Instant.parse("2026-01-01T10:00:00Z"))
				.status(SumUpTransactionStatus.ofString(status))
				.amount(Amount.of("16.07", CurrencyCode.EUR))
				.amountRefunded(amountRefunded != null ? Amount.of(amountRefunded, CurrencyCode.EUR) : null)
				.clientAndOrgId(ClientAndOrgId.MAIN)
				.build();
	}

	@Test
	void statusREFUNDED_isTheKnownConstant()
	{
		assertThat(SumUpTransactionStatus.ofString("REFUNDED")).isSameAs(SumUpTransactionStatus.REFUNDED);
	}

	@Test
	void statusREFUNDED_isRefundedAndCharged_evenWithoutRefundEvent()
	{
		final SumUpTransaction trx = trx("REFUNDED", null);
		assertThat(trx.isRefunded()).isTrue();
		assertThat(trx.isCharged()).isTrue();
		assertThat(trx.isSuccessfulAndNotRefunded()).isFalse();
	}

	@Test
	void successfulWithRefundEvent_isRefundedAndCharged()
	{
		final SumUpTransaction trx = trx("SUCCESSFUL", "16.07");
		assertThat(trx.isRefunded()).isTrue();
		assertThat(trx.isCharged()).isTrue();
		assertThat(trx.isSuccessfulAndNotRefunded()).isFalse();
	}

	@Test
	void successfulWithoutRefund_isChargedAndNotRefunded()
	{
		final SumUpTransaction trx = trx("SUCCESSFUL", null);
		assertThat(trx.isRefunded()).isFalse();
		assertThat(trx.isCharged()).isTrue();
		assertThat(trx.isSuccessfulAndNotRefunded()).isTrue();
	}

	@Test
	void pendingFailedCancelled_areNotCharged()
	{
		assertThat(trx("PENDING", null).isCharged()).isFalse();
		assertThat(trx("FAILED", null).isCharged()).isFalse();
		assertThat(trx("CANCELLED", null).isCharged()).isFalse();
		assertThat(trx("PENDING", null).isRefunded()).isFalse();
	}
}
