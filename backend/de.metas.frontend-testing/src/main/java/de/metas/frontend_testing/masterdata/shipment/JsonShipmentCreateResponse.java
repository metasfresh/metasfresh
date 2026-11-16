package de.metas.frontend_testing.masterdata.shipment;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class JsonShipmentCreateResponse
{
	@NonNull String id;
	@NonNull String documentNo;
}
