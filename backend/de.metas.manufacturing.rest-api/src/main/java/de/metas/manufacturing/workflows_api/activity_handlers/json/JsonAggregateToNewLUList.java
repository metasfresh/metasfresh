package de.metas.manufacturing.workflows_api.activity_handlers.json;

import com.google.common.collect.ImmutableList;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;
import java.util.List;

@Value
public class JsonAggregateToNewLUList
{
	@NonNull List<JsonAggregateToNewLU> values;
	@Nullable String emptyReason;
	@Nullable List<String> debugMessages;

	public static JsonAggregateToNewLUList ofList(@NonNull List<JsonAggregateToNewLU> values, @Nullable final List<String> debugMessages)
	{
		Check.assumeNotEmpty(values, "values");
		return builder().values(values).debugMessages(debugMessages).build();
	}

	public static JsonAggregateToNewLUList emptyBecause(@NonNull String emptyReason)
	{
		return emptyBecause(emptyReason, null);
	}

	public static JsonAggregateToNewLUList emptyBecause(@NonNull String emptyReason, @Nullable final List<String> debugMessages)
	{
		Check.assumeNotEmpty(emptyReason, "emptyReason");
		return builder().emptyReason(emptyReason).debugMessages(debugMessages).build();
	}

	@Builder
	@Jacksonized
	private JsonAggregateToNewLUList(
			@Nullable final List<JsonAggregateToNewLU> values,
			@Nullable final String emptyReason,
			@Nullable final List<String> debugMessages)
	{
		this.values = values != null ? ImmutableList.copyOf(values) : ImmutableList.of();
		this.emptyReason = emptyReason;
		this.debugMessages = debugMessages != null && !debugMessages.isEmpty()
				? ImmutableList.copyOf(debugMessages)
				: null;
	}
}
