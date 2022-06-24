package de.metas.calendar;

import com.google.common.collect.ImmutableList;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class CalendarEntryUpdateResult
{
	@NonNull CalendarEntry changedEntry;

	@Builder.Default
	@NonNull ImmutableList<CalendarEntry> otherChangedEntries = ImmutableList.of();

	public static CalendarEntryUpdateResult ofChangedEntry(@NonNull final CalendarEntry changedEntry)
	{
		return builder().changedEntry(changedEntry).build();
	}
}
