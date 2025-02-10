package de.metas.frontend_testing;

import de.metas.rest_api.v2.order.JsonSalesOrder;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.Map;

@Value
@Builder
@Jacksonized
public class JsonCreateMasterdataResponse
{
	@NonNull Map<String, JsonSalesOrder> salesOrders;
	@NonNull Map<String, JsonCreateHUResponse> handlingUnits;
}
