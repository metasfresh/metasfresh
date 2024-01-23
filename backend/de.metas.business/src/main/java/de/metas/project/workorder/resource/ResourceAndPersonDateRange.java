package de.metas.project.workorder.resource;

import de.metas.calendar.util.CalendarDateRange;
import de.metas.util.Check;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.time.Duration;
import java.time.Instant;

@EqualsAndHashCode
@ToString
public class ResourceAndPersonDateRange
{
	@Nullable private final CalendarDateRange resourceDateRange;
	@Nullable private final CalendarDateRange personDateRange;

	@NonNull private final CalendarDateRange enclosingDateRange;

	@Builder(toBuilder = true)
	private ResourceAndPersonDateRange(
			@Nullable final CalendarDateRange resourceDateRange,
			@Nullable final CalendarDateRange personDateRange)
	{
		if (resourceDateRange == null && personDateRange == null)
		{
			throw new AdempiereException("At least one of date ranges shall be not null");
		}
		this.resourceDateRange = resourceDateRange;
		this.personDateRange = personDateRange;

		this.enclosingDateRange = Check.assumeNotNull(
				CalendarDateRange.span(this.resourceDateRange, this.personDateRange),
				"At least one of date ranges shall be not null");
	}

	public CalendarDateRange toCalendarDateRange() {return enclosingDateRange;}

	@NonNull
	public Instant getStartDate() {return enclosingDateRange.getStartDate();}

	@NonNull
	public Instant getEndDate() {return enclosingDateRange.getEndDate();}

	public boolean isAllDay() {return enclosingDateRange.isAllDay();}

	public ResourceAndPersonDateRange plus(@NonNull final Duration offset)
	{
		if (offset.isZero())
		{
			return this;
		}

		return toBuilder()
				.resourceDateRange(resourceDateRange != null ? resourceDateRange.plus(offset) : null)
				.personDateRange(personDateRange != null ? personDateRange.plus(offset) : null)
				.build();
	}

	public boolean isOverlappingWith(@Nullable final Instant startDate, @Nullable final Instant endDate)
	{
		return enclosingDateRange.isOverlappingWith(startDate, endDate);
	}
}
