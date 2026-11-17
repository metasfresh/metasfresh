package de.metas.payment.sumup.repository;

import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * The SQL side of {@link PollDueQueryFilter}: the in-memory database of the other tests runs only its Java side. The SQL
 * itself was checked against PostgreSQL on rows matching {@code SumUpTransactionRepositoryTest.query_pollDueAt_*}.
 */
class PollDueQueryFilterTest
{
	@Test
	void sql_andParams_matchThePollSchedule()
	{
		final PollDueQueryFilter filter = new PollDueQueryFilter(Instant.parse("2026-11-16T12:00:00Z"));

		assertThat(filter.getSql()).isEqualTo("(SUMUP_LastSync_Timestamp IS NULL"
				+ " OR Created > ?"
				+ " OR (Created > ? AND SUMUP_LastSync_Timestamp <= ?)"
				+ " OR SUMUP_LastSync_Timestamp <= ?)");
		assertThat(filter.getSqlParams(null)).containsExactly(
				Timestamp.from(Instant.parse("2026-11-16T11:55:00Z")), // age under 5 min
				Timestamp.from(Instant.parse("2026-11-16T11:00:00Z")), // age under 1 h ...
				Timestamp.from(Instant.parse("2026-11-16T11:59:00Z")), // ... and last lookup at least 1 min ago
				Timestamp.from(Instant.parse("2026-11-16T11:45:00Z"))); // last lookup at least 15 min ago
	}
}
