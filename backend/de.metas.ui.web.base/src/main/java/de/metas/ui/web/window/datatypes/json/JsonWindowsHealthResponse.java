package de.metas.ui.web.window.datatypes.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.common.collect.ImmutableList;
import de.metas.common.rest_api.v2.JsonErrorItem;
import de.metas.ui.web.window.datatypes.WindowId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;
import java.util.List;
import java.util.stream.Collectors;

@Value
public class JsonWindowsHealthResponse
{
	String took;
	int countTotal;
	int countErrors;
	int countSkipped;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	List<Entry> errors;
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	String errorWindowIds;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	List<Entry> skipped;

	@Builder
	@Jacksonized
	private JsonWindowsHealthResponse(
			@Nullable final String took,
			final int countTotal,
			@Nullable final List<Entry> errors,
			@Nullable final List<Entry> skipped)
	{
		this.took = took;
		this.errors = errors != null ? errors : ImmutableList.of();
		this.skipped = skipped != null ? skipped : ImmutableList.of();
		this.countTotal = countTotal;
		this.countErrors = this.errors.size();
		this.countSkipped = this.skipped.size();

		this.errorWindowIds = this.errors.stream()
				.map(entry -> entry.getWindowId().toJson())
				.collect(Collectors.joining(","));
	}

	@Value
	@Builder
	@Jacksonized
	public static class Entry
	{
		@NonNull WindowId windowId;
		@Nullable String windowName;

		@JsonInclude(JsonInclude.Include.NON_EMPTY)
		@Nullable String errorMessage;

		@JsonInclude(JsonInclude.Include.NON_EMPTY)
		@Nullable JsonErrorItem error;
	}
}