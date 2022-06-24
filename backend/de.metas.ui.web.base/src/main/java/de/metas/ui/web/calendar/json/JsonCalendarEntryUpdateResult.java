package de.metas.ui.web.calendar.json;

import com.google.common.collect.ImmutableList;
import de.metas.calendar.CalendarEntry;
import de.metas.calendar.CalendarEntryUpdateResult;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.time.ZoneId;
import java.util.List;

@Value
@Builder
@Jacksonized
public class JsonCalendarEntryUpdateResult
{
	@NonNull JsonCalendarEntry changedEntry;

	@Builder.Default
	@NonNull List<JsonCalendarEntry> otherChangedEntries = ImmutableList.of();

	public static JsonCalendarEntryUpdateResult of(
			@NonNull final CalendarEntryUpdateResult result,
			@NonNull final ZoneId timeZone,
			@NonNull final String adLanguage)
	{
		return builder()
				.changedEntry(JsonCalendarEntry.of(result.getChangedEntry(), timeZone, adLanguage))
				.otherChangedEntries(result.getOtherChangedEntries()
						.stream()
						.map(entry -> JsonCalendarEntry.of(entry, timeZone, adLanguage))
						.collect(ImmutableList.toImmutableList()))
				.build();
	}

	public static JsonCalendarEntryUpdateResult ofChangedEntry(
			@NonNull final CalendarEntry changedEntry,
			@NonNull final ZoneId timeZone,
			@NonNull final String adLanguage)
	{
		return builder()
				.changedEntry(JsonCalendarEntry.of(changedEntry, timeZone, adLanguage))
				.otherChangedEntries(ImmutableList.of())
				.build();
	}

}
