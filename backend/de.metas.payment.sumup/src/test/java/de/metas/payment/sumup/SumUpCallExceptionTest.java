package de.metas.payment.sumup;

import org.adempiere.exceptions.DBException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SumUpCallExceptionTest
{
	@Test
	void failureAfterAccepted_isIndeterminateAndNamesTheClientTransactionId()
	{
		final SumUpCallException ex = SumUpCallException.ofFailureAfterAccepted(
				new DBException("insert failed"),
				SumUpClientTransactionId.ofString("ctid-123"));

		assertThat(ex.isDefiniteRejection()).isFalse();
		assertThat(ex.getMessage()).contains("ctid-123").contains("insert failed");
		assertThat(ex.getParameter("SumUpClientTransactionId")).isEqualTo("ctid-123");
	}

	@Test
	void failureAfterAccepted_withoutClientTransactionId_isIndeterminate()
	{
		final SumUpCallException ex = SumUpCallException.ofFailureAfterAccepted(new RuntimeException("boom"), null);

		assertThat(ex.isDefiniteRejection()).isFalse();
		assertThat(ex.getMessage()).contains("boom");
	}
}
