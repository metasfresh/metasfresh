package de.metas.project.workorder.conflicts;

import de.metas.calendar.util.CalendarDateRange;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

public class ResourceAllocationsTestUtils
{
	static final Instant refInstant = LocalDate.parse("2022-06-01").atStartOfDay(ZoneId.of("Europe/Berlin")).toInstant();

	static Instant instant(int day) {return refInstant.plus(day - 1, ChronoUnit.DAYS);}

	static CalendarDateRange allDay(int startDay, int endDay)
	{
		return CalendarDateRange.builder()
				.startDate(instant(startDay))
				.endDate(instant(endDay))
				.allDay(true)
				.build();
	}
}
