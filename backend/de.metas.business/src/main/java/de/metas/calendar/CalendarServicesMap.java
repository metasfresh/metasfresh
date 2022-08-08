package de.metas.calendar;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.exceptions.AdempiereException;

import java.util.List;
import java.util.stream.Stream;

@ToString
public class CalendarServicesMap
{
	private final ImmutableMap<CalendarServiceId, CalendarService> byId;

	private CalendarServicesMap(@NonNull final List<CalendarService> calendarServices)
	{
		this.byId = Maps.uniqueIndex(calendarServices, CalendarService::getCalendarServiceId);
	}

	public static CalendarServicesMap of(@NonNull final List<CalendarService> calendarServices)
	{
		return new CalendarServicesMap(calendarServices);
	}

	public CalendarService getById(@NonNull final CalendarServiceId calendarServiceId)
	{
		final CalendarService calendarService = byId.get(calendarServiceId);
		if (calendarService == null)
		{
			throw new AdempiereException("No calendar service found for " + calendarServiceId);
		}
		return calendarService;
	}

	public Stream<CalendarService> stream()
	{
		return byId.values().stream();
	}

}
