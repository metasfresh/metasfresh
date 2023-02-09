package de.metas.calendar.continuous_query;

import de.metas.calendar.CalendarEntry;
import lombok.NonNull;
import lombok.Value;

@Value(staticConstructor = "of")
public class EntryChangedEvent implements Event
{
	@NonNull CalendarEntry entry;
}
