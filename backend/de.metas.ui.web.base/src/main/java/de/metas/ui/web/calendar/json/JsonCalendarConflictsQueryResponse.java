package de.metas.ui.web.calendar.json;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Value
@Builder
@Jacksonized
public class JsonCalendarConflictsQueryResponse
{
	@NonNull List<JsonCalendarConflict> conflicts;
}
