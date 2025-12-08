package de.metas.rest_api.v2.workstation;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;

@Value
@Builder
@Jacksonized
public class JsonWorkstationSettings
{
	@Nullable JsonWorkstation assignedWorkstation;
}
