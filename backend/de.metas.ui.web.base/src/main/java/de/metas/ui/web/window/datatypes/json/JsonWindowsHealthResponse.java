package de.metas.ui.web.window.datatypes.json;

import de.metas.common.rest_api.v2.JsonErrorItem;
import de.metas.ui.web.window.datatypes.WindowId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;
import java.util.List;

@Value
@Builder
@Jacksonized
public class JsonWindowsHealthResponse
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
		@NonNull WindowId windowId;
		@Nullable String windowName;
		@Nullable JsonErrorItem error;
	}
}
