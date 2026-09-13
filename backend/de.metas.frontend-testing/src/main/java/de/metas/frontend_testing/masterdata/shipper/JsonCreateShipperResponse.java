package de.metas.frontend_testing.masterdata.shipper;

import de.metas.shipping.ShipperId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class JsonCreateShipperResponse
{
	@NonNull ShipperId shipperId;
	@NonNull String name;
}
