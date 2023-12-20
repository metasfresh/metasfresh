package de.metas.ui.web.window.datatypes.json;

import de.metas.common.rest_api.v2.JsonErrorItem;
import de.metas.ui.web.process.ProcessId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;
import java.util.List;

@Value
@Builder
@Jacksonized
public class JsonProcessHealthResponse
{
	int countTotal;
	int countErrors;
	String took;

	List<Entry> errors;

	@Value
	@Builder
	@Jacksonized
	public static class Entry
	{
		@NonNull ProcessId processId;
		@Nullable String value;
		@Nullable String name;
		@Nullable String classname;
		@Nullable JsonErrorItem error;
	}
}
