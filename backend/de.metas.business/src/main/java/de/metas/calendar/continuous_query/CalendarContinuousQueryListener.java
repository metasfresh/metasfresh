package de.metas.calendar.continuous_query;

import lombok.NonNull;

import java.util.List;

/**
 * Listen on {@link de.metas.calendar.continuous_query.CalendarContinuousQuery} and gets notified when there are new events
 */
public interface CalendarContinuousQueryListener
{
	void onEvents(@NonNull final List<Event> events);
}
