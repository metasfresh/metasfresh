package de.metas.pos;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * The SQL side of {@link NoStoredSumUpTransactionFilter} (its Java side runs in {@code POSOrdersRepositoryTest}): a
 * correlated NOT EXISTS, which PostgreSQL runs as an anti join on the unique index of SUMUP_ClientTransactionId. A
 * {@code NOT IN (subquery)} instead reads the whole SUMUP_Transaction table (checked with EXPLAIN).
 */
class NoStoredSumUpTransactionFilterTest
{
	@Test
	void sql_isACorrelatedNotExistsOnTheClientTransactionId()
	{
		assertThat(NoStoredSumUpTransactionFilter.INSTANCE.getSql()).isEqualTo(
				"NOT EXISTS (SELECT 1 FROM SUMUP_Transaction t"
						+ " WHERE t.SUMUP_ClientTransactionId = C_POS_Payment.SUMUP_ClientTransactionID)");
		assertThat(NoStoredSumUpTransactionFilter.INSTANCE.getSqlParams(null)).isEmpty();
	}
}
