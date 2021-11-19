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
@Builder
@Jacksonized
public class JsonAggregateToNewLUList
{
	@NonNull List<JsonAggregateToNewLU> values;
	@Nullable String emptyReason;

	public static JsonAggregateToNewLUList ofList(@NonNull List<JsonAggregateToNewLU> values)
	{
		Check.assumeNotEmpty(values, "values");
		return builder().values(ImmutableList.copyOf(values)).build();
	}

	public static JsonAggregateToNewLUList emptyBecause(@NonNull String emptyReason)
	{
		Check.assumeNotEmpty(emptyReason, "emptyReason");
		return builder().values(ImmutableList.of()).emptyReason(emptyReason).build();
	}
}
