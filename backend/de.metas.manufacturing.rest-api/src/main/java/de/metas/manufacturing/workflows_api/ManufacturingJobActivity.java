package de.metas.manufacturing.workflows_api;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.eevolution.api.PPOrderRoutingActivityCode;
import org.eevolution.api.PPOrderRoutingActivityId;

@Value
@Builder
public class ManufacturingJobActivity
{
	@NonNull PPOrderRoutingActivityId ppOrderRoutingActivityId;
	@NonNull PPOrderRoutingActivityCode code;
}
