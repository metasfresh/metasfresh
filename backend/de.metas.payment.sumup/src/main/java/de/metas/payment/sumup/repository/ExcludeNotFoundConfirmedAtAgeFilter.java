package de.metas.payment.sumup.repository;

import com.google.common.collect.ImmutableList;
import de.metas.payment.sumup.SumUpTransaction.LastSyncStatus;
import de.metas.payment.sumup.repository.model.I_SUMUP_Transaction;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.ISqlQueryFilter;

import java.sql.Timestamp;
import java.time.Duration;
import java.util.List;
import java.util.Properties;

/**
 * Leaves out the SumUp transactions for which SumUp confirmed that it has no transaction by a lookup made at least
 * {@code age} after metasfresh created them: SumUp never reported the transaction (no stored answer, {@code JsonResponse}),
 * and its last lookup succeeded ({@code SUMUP_LastSync_Status} OK, i.e. SumUp's "not found") at or after
 * {@code Created + age}. Every other transaction is kept, in particular one whose last lookup failed, one never looked up,
 * one SumUp knows (even if still PENDING), and one whose last "not found" came earlier than that.
 * <p>
 * Evaluated in SQL and, for the in-memory database of unit tests, in Java, with the same meaning.
 * <p>
 * A custom filter because the standard filters cannot express {@code SUMUP_LastSync_Timestamp < Created + age}:
 * {@link org.adempiere.ad.dao.impl.CompareQueryFilter} can compare a column with another column, but the only date
 * arithmetic modifier, {@link org.adempiere.ad.dao.impl.AddHoursQueryFilterModifier}, adds hours read from a column, not a
 * constant duration.
 */
@EqualsAndHashCode
@ToString
final class ExcludeNotFoundConfirmedAtAgeFilter implements IQueryFilter<I_SUMUP_Transaction>, ISqlQueryFilter
{
	@NonNull private final Duration age;

	ExcludeNotFoundConfirmedAtAgeFilter(@NonNull final Duration age)
	{
		this.age = age;
	}

	@Override
	public boolean accept(final I_SUMUP_Transaction record)
	{
		return !isNotFoundConfirmedAtAge(record);
	}

	private boolean isNotFoundConfirmedAtAge(@NonNull final I_SUMUP_Transaction record)
	{
		if (record.getJsonResponse() != null)
		{
			return false; // SumUp reported the transaction
		}
		if (!LastSyncStatus.OK.getCode().equals(record.getSUMUP_LastSync_Status()))
		{
			return false; // never looked up, or the last lookup failed
		}
		final Timestamp lastSyncTimestamp = record.getSUMUP_LastSync_Timestamp();
		return lastSyncTimestamp != null
				&& !lastSyncTimestamp.toInstant().isBefore(record.getCreated().toInstant().plus(age));
	}

	@Override
	public String getSql()
	{
		return "(" + I_SUMUP_Transaction.COLUMNNAME_JsonResponse + " IS NOT NULL"
				+ " OR " + I_SUMUP_Transaction.COLUMNNAME_SUMUP_LastSync_Status + " IS DISTINCT FROM ?"
				+ " OR " + I_SUMUP_Transaction.COLUMNNAME_SUMUP_LastSync_Timestamp + " IS NULL"
				+ " OR " + I_SUMUP_Transaction.COLUMNNAME_SUMUP_LastSync_Timestamp + " < " + I_SUMUP_Transaction.COLUMNNAME_Created + " + INTERVAL '" + age.getSeconds() + " seconds'"
				+ ")";
	}

	@Override
	public List<Object> getSqlParams(final Properties ctx)
	{
		return ImmutableList.of(LastSyncStatus.OK.getCode());
	}
}
