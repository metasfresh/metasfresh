package de.metas.calendar.continuous_query;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;

/**
 * Listen on {@link de.metas.calendar.continuous_query.CalendarContinuousQuery} and gets notified when there are new events
 */
public interface CalendarContinuousQueryListener
{
	void onCalendarContinuousQueryEvents(@NonNull final ImmutableList<Event> events);
}
