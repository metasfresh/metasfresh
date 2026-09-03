package de.metas.frontend_testing.masterdata.shipper;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class JsonCreateShipperResponse
{
	int shipperId;
	String name;
}
