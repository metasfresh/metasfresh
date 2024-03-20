package de.metas.resource;

import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.threeten.extra.YearWeek;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Value(staticConstructor = "ofStartAndEndDate")
public class ResourceAvailabilityRange
{
	@NonNull LocalDateTime startDate;
	@NonNull LocalDateTime endDate;

	private ResourceAvailabilityRange(@NonNull final LocalDateTime startDate, @NonNull final LocalDateTime endDate)
	{
		if (!startDate.isBefore(endDate))
		{
			throw new AdempiereException("Start date `" + startDate + "` shall be before end date `" + endDate + "`");
		}

		this.startDate = startDate;
		this.endDate = endDate;
	}

	public Duration getDuration() {return Duration.between(startDate, endDate);}

	public Map<YearWeek, Duration> getDurationByWeek()
	{
		final HashMap<YearWeek, Duration> result = new HashMap<>();

		LocalDateTime date = startDate;

		while (date.isBefore(endDate))
		{
			final YearWeek week = YearWeek.from(date);
			final LocalDateTime weekEndDate = week.plusWeeks(1).atDay(DayOfWeek.MONDAY).atStartOfDay();
			final LocalDateTime nextDate = weekEndDate.isBefore(endDate) ? weekEndDate : endDate;
			final Duration duration = Duration.between(date, nextDate);

			result.merge(week, duration, Duration::plus);

			date = nextDate;
		}

		return result;
	}
}
