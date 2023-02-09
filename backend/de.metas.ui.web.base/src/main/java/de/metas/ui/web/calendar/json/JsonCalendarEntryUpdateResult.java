package de.metas.ui.web.calendar.json;

import com.google.common.collect.ImmutableList;
import de.metas.calendar.CalendarEntry;
import de.metas.calendar.CalendarEntryUpdateResult;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.adempiere.util.lang.OldAndNewValues;

import java.time.ZoneId;
import java.util.List;
import java.util.Objects;

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
		final CalendarEntry calendarEntry = Check.assumeNotNull(
				result.getChangedEntry().getNewValue(),
				"changed entry new value is not null: {}", result);

		return builder()
				.changedEntry(JsonCalendarEntry.of(calendarEntry, timeZone, adLanguage))
				.otherChangedEntries(result.getOtherChangedEntries()
						.stream()
						.map(OldAndNewValues::getNewValue)
						.filter(Objects::nonNull) // shall not happen
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
