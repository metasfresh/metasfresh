package de.metas.frontend_testing;

import de.metas.rest_api.v2.order.JsonSalesOrderCreateRequest;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;
import java.util.Map;

@Value
@Builder
@Jacksonized
public class JsonCreateMasterdataRequest
{
	@Nullable Map<String, JsonSalesOrderCreateRequest> salesOrders;
	@Nullable Map<String, JsonCreateHURequest> handlingUnits;
}
