package de.metas.shipper.gateway.nshift.config;

import de.metas.shipping.ShipperId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class NShiftConfig
{
	@NonNull ShipperId shipperId;
	// TODO
}
