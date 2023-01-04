package de.metas.calendar.conflicts;

import com.google.common.collect.ImmutableList;

public interface CalendarConflictEventsListener
{
	void onCalendarConflictChangesEvents(final ImmutableList<CalendarConflictChangesEvent> events);
}
