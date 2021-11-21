package de.metas.manufacturing.workflows_api.activity_handlers.json;

import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;

@Value
public class JsonAggregateToNewLU
{
	@NonNull String caption;
	@Nullable String tuCaption;

	int luPIItemId;
	int tuPIItemProductId;

	@Builder
	@Jacksonized
	private JsonAggregateToNewLU(
			@NonNull final String caption,
			@Nullable final String tuCaption,
			final int luPIItemId,
			final int tuPIItemProductId)
	{
		Check.assumeGreaterThanZero(luPIItemId, "luPIItemId");
		Check.assumeGreaterThanZero(tuPIItemProductId, "tuPIItemProductId");

		this.caption = caption;
		this.tuCaption = tuCaption;
		this.luPIItemId = luPIItemId;
		this.tuPIItemProductId = tuPIItemProductId;
	}
}
