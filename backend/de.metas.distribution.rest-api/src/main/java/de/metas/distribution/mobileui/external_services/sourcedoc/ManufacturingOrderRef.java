package de.metas.distribution.mobileui.external_services.sourcedoc;

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
