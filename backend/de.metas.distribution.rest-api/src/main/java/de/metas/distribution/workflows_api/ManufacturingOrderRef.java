package de.metas.distribution.workflows_api;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.eevolution.api.PPOrderId;

@Value
@Builder
public class ManufacturingOrderRef
{
	@NonNull PPOrderId id;
	@NonNull String documentNo;

}
