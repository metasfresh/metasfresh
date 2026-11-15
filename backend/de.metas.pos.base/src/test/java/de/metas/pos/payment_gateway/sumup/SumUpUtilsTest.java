package de.metas.pos.payment_gateway.sumup;

import de.metas.payment.sumup.SumUpTransactionStatus;
import de.metas.pos.POSPaymentProcessingStatus;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SumUpUtilsTest
{
	@Test
	void refunded_mapsToDeleted_likeSuccessfulWithRefund()
	{
		assertThat(SumUpUtils.toResponseStatus(SumUpTransactionStatus.REFUNDED, true)).isEqualTo(POSPaymentProcessingStatus.DELETED);
		assertThat(SumUpUtils.toResponseStatus(SumUpTransactionStatus.SUCCESSFUL, true)).isEqualTo(POSPaymentProcessingStatus.DELETED);
	}

	@Test
	void otherStatuses_unchanged()
	{
		assertThat(SumUpUtils.toResponseStatus(SumUpTransactionStatus.SUCCESSFUL, false)).isEqualTo(POSPaymentProcessingStatus.SUCCESSFUL);
		assertThat(SumUpUtils.toResponseStatus(SumUpTransactionStatus.CANCELLED, false)).isEqualTo(POSPaymentProcessingStatus.CANCELLED);
		assertThat(SumUpUtils.toResponseStatus(SumUpTransactionStatus.FAILED, false)).isEqualTo(POSPaymentProcessingStatus.FAILED);
		assertThat(SumUpUtils.toResponseStatus(SumUpTransactionStatus.PENDING, false)).isEqualTo(POSPaymentProcessingStatus.PENDING);
	}
}
