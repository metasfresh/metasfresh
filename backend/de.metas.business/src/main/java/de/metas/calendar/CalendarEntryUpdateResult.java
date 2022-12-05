package de.metas.calendar;

import com.google.common.collect.ImmutableList;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.util.lang.OldAndNewValues;

@Value
@Builder
public class CalendarEntryUpdateResult
{
	@NonNull OldAndNewValues<CalendarEntry> changedEntry;

	@Builder.Default
	@NonNull ImmutableList<OldAndNewValues<CalendarEntry>> otherChangedEntries = ImmutableList.of();

	public static CalendarEntryUpdateResult ofChangedEntry(@NonNull final OldAndNewValues<CalendarEntry> changedEntry)
	{
		return builder().changedEntry(changedEntry).build();
	}
}
