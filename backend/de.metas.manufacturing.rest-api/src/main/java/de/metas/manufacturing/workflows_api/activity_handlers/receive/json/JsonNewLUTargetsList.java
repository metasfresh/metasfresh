package de.metas.manufacturing.workflows_api.activity_handlers.receive.json;

import com.google.common.collect.ImmutableList;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;
import java.util.List;

@Value
public class JsonNewLUTargetsList
{
	@NonNull List<JsonNewLUTarget> values;
	@Nullable String emptyReason;
	@Nullable List<String> debugMessages;

	public static JsonNewLUTargetsList ofList(@NonNull List<JsonNewLUTarget> values, @Nullable final List<String> debugMessages)
	{
		Check.assumeNotEmpty(values, "values");
		return builder().values(values).debugMessages(debugMessages).build();
	}

	/**
	 * Empty, but deliberately WITHOUT an {@code emptyReason}: the reason is the operator-facing
	 * "no receiving Gebinde" guidance, and the frontend shows it whenever it is present. A structure
	 * excluded by configuration must therefore stay silent, so the remaining structure's targets are
	 * offered without an accompanying error.
	 */
	public static JsonNewLUTargetsList emptyWithoutReason()
	{
		return builder().build();
	}

	public static JsonNewLUTargetsList emptyBecause(@NonNull String emptyReason)
	{
		return emptyBecause(emptyReason, null);
	}

	public static JsonNewLUTargetsList emptyBecause(@NonNull String emptyReason, @Nullable final List<String> debugMessages)
	{
		Check.assumeNotEmpty(emptyReason, "emptyReason");
		return builder().emptyReason(emptyReason).debugMessages(debugMessages).build();
	}

	@Builder
	@Jacksonized
	private JsonNewLUTargetsList(
			@Nullable final List<JsonNewLUTarget> values,
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
