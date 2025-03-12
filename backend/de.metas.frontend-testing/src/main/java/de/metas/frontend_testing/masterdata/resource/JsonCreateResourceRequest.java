package de.metas.frontend_testing.masterdata.resource;

import de.metas.material.planning.ResourceTypeId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;

@Value
@Builder
@Jacksonized
public class JsonCreateResourceRequest
{
	@NonNull String type;
	@Nullable ResourceTypeId resourceTypeId;
}
