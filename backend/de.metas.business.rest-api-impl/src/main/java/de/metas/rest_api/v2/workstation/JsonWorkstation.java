package de.metas.rest_api.v2.workstation;

import de.metas.product.ResourceId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class JsonWorkstation
{
	@NonNull ResourceId id;
	@NonNull String name;
	boolean isUserAssigned;
}
