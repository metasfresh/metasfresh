package de.metas.manufacturing.workflows_api.rest_api.json;

import de.metas.manufacturing.workflows_api.activity_handlers.json.JsonAggregateToExistingLU;
import lombok.Data;
import lombok.NonNull;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;

@Data
@Jacksonized
public class JsonManufacturingOrderEventResult
{
	//
	// Activity Identifier
	@NonNull final String wfProcessId;
	@NonNull final String wfActivityId;

	//
	// Receive result
	@Nullable JsonAggregateToExistingLU existingLU;
}
