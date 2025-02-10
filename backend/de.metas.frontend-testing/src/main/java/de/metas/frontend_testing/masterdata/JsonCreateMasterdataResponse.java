package de.metas.frontend_testing.masterdata;

import de.metas.frontend_testing.masterdata.bpartner.JsonCreateBPartnerResponse;
import de.metas.frontend_testing.masterdata.hu.JsonCreateHUResponse;
import de.metas.frontend_testing.masterdata.hu.JsonPackingInstructionsResponse;
import de.metas.frontend_testing.masterdata.product.JsonCreateProductResponse;
import de.metas.frontend_testing.masterdata.sales_order.JsonSalesOrderCreateResponse;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;
import java.util.Map;

@Value
@Builder
@Jacksonized
public class JsonCreateMasterdataResponse
{
	@Nullable Map<String, JsonCreateBPartnerResponse> bpartners;
	@Nullable Map<String, JsonCreateProductResponse> products;
	@NonNull Map<String, JsonPackingInstructionsResponse> packingInstructions;
	@NonNull Map<String, JsonCreateHUResponse> handlingUnits;
	@NonNull Map<String, JsonSalesOrderCreateResponse> salesOrders;
}
