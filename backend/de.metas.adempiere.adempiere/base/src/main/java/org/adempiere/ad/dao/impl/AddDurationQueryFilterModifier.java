package org.adempiere.ad.dao.impl;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.ad.dao.IQueryFilterModifier;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.TimeUtil;

import javax.annotation.Nullable;
import java.time.Duration;
import java.time.Instant;
import java.util.List;

/**
 * Adds a constant duration to the main (date/time) column, e.g. {@code Created + 24 h > SUMUP_LastSync_Timestamp}:
 * <pre>
 * new CompareQueryFilter&lt;&gt;(Created, Operator.GREATER, ModelColumnNameValue.forColumnName(SUMUP_LastSync_Timestamp),
 *         new AddDurationQueryFilterModifier(Duration.ofHours(24)))
 * </pre>
 * The second operand (a value or another column) is left unchanged. For an offset read from a column, see
 * {@link AddHoursQueryFilterModifier}.
 */
@EqualsAndHashCode
@ToString
public final class AddDurationQueryFilterModifier implements IQueryFilterModifier
{
	@NonNull private final Duration duration;

	/**
	 * @param duration whole seconds (may be negative): the SQL interval is in seconds, so a sub-second part would make the SQL
	 *                 and the in-memory results differ
	 */
	public AddDurationQueryFilterModifier(@NonNull final Duration duration)
	{
		if (duration.getNano() != 0)
		{
			throw new AdempiereException("Duration must be whole seconds: " + duration);
		}
		this.duration = duration;
	}

	@Override
	public @NonNull String getColumnSql(@NonNull final String columnName)
	{
		return "(" + columnName + " + INTERVAL '" + duration.getSeconds() + " seconds')";
	}

	@Override
	public String getValueSql(final Object value, final List<Object> params)
	{
		return NullQueryFilterModifier.instance.getValueSql(value, params);
	}

	@Nullable
	@Override
	public Object convertValue(@Nullable final String columnName, @Nullable final Object value, @Nullable final Object model)
	{
		// the second operand, with which the column is compared, stays as it is
		if (columnName == null || value == null)
		{
			return value;
		}

		final Instant instant = TimeUtil.asInstant(value);
		return instant != null ? TimeUtil.asTimestamp(instant.plus(duration)) : null;
	}
}
