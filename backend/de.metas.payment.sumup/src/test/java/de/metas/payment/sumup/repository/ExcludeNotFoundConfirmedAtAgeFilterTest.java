package de.metas.payment.sumup.repository;

import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * The SQL side of {@link ExcludeNotFoundConfirmedAtAgeFilter}: the in-memory database of the other tests runs only its Java
 * side. The SQL itself was checked against PostgreSQL on rows matching
 * {@code SumUpTransactionRepositoryTest.query_excludeNotFoundConfirmedAtAge_*}.
 */
class ExcludeNotFoundConfirmedAtAgeFilterTest
{
	@Test
	void sql_andParams()
	{
		final ExcludeNotFoundConfirmedAtAgeFilter filter = new ExcludeNotFoundConfirmedAtAgeFilter(Duration.ofHours(24));

		// kept: SumUp reported it, or the last lookup was not SumUp's "not found", or none, or it came before the 24 h mark
		assertThat(filter.getSql()).isEqualTo("(JsonResponse IS NOT NULL"
				+ " OR SUMUP_LastSync_Status IS DISTINCT FROM ?"
				+ " OR SUMUP_LastSync_Timestamp IS NULL"
				+ " OR SUMUP_LastSync_Timestamp < Created + INTERVAL '86400 seconds')");
		assertThat(filter.getSqlParams(null)).containsExactly("OK");
	}
}
