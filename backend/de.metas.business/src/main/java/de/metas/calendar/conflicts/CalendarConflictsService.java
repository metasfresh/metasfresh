package de.metas.calendar.conflicts;

import de.metas.calendar.CalendarConflictsQuery;
import lombok.NonNull;

public interface CalendarConflictsService
{
	CalendarEntryConflicts query(@NonNull final CalendarConflictsQuery query);

	void checkAllConflicts();
}
