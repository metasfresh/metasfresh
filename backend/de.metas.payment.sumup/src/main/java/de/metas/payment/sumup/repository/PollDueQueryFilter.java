package de.metas.payment.sumup.repository;

import com.google.common.collect.ImmutableList;
import de.metas.payment.sumup.SumUpPollSchedule;
import de.metas.payment.sumup.repository.model.I_SUMUP_Transaction;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.ISqlQueryFilter;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Properties;

/**
 * Only the transactions due for the SumUp poller's cycle at the given time, by {@link SumUpPollSchedule} (from
 * {@code Created} and {@code SUMUP_LastSync_Timestamp}).
 * <p>
 * Evaluated in SQL and, for the in-memory database of unit tests, in Java via {@link SumUpPollSchedule#isDue}.
 */
@EqualsAndHashCode
@ToString
final class PollDueQueryFilter implements IQueryFilter<I_SUMUP_Transaction>, ISqlQueryFilter
{
	@NonNull private final Instant now;

	PollDueQueryFilter(@NonNull final Instant now)
	{
		this.now = now;
	}

	@Override
	public boolean accept(final I_SUMUP_Transaction record)
	{
		final Timestamp lastLookupAt = record.getSUMUP_LastSync_Timestamp();
		return SumUpPollSchedule.isDue(
				record.getCreated().toInstant(),
				lastLookupAt != null ? lastLookupAt.toInstant() : null,
				now);
	}

	@Override
	public String getSql()
	{
		final String created = I_SUMUP_Transaction.COLUMNNAME_Created;
		final String lastLookupAt = I_SUMUP_Transaction.COLUMNNAME_SUMUP_LastSync_Timestamp;
		return "(" + lastLookupAt + " IS NULL"
				+ " OR " + created + " > ?" // fast phase: every cycle
				+ " OR (" + created + " > ? AND " + lastLookupAt + " <= ?)" // medium phase
				+ " OR " + lastLookupAt + " <= ?" // slow phase (also true in the earlier phases)
				+ ")";
	}

	@Override
	public List<Object> getSqlParams(final Properties ctx)
	{
		return ImmutableList.of(
				Timestamp.from(now.minus(SumUpPollSchedule.FAST_PHASE)),
				Timestamp.from(now.minus(SumUpPollSchedule.MEDIUM_PHASE)),
				Timestamp.from(now.minus(SumUpPollSchedule.MEDIUM_INTERVAL)),
				Timestamp.from(now.minus(SumUpPollSchedule.SLOW_INTERVAL)));
	}
}
