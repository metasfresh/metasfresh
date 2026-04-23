package de.metas.frontend_testing.masterdata.shipment;

import de.metas.frontend_testing.masterdata.Identifier;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class JsonShipmentCreateRequest
{
	@NonNull Identifier salesOrder;
}
