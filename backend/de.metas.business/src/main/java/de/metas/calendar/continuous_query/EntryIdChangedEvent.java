package de.metas.calendar.continuous_query;

import de.metas.calendar.CalendarEntryId;
import lombok.NonNull;
import lombok.Value;

@Value(staticConstructor = "of")
public class EntryIdChangedEvent implements Event
{
	@NonNull CalendarEntryId entryId;
}
