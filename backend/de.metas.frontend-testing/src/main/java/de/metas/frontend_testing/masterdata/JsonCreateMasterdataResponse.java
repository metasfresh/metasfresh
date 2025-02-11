package de.metas.frontend_testing.masterdata;

import de.metas.frontend_testing.masterdata.bpartner.JsonCreateBPartnerResponse;
import de.metas.frontend_testing.masterdata.hu.JsonCreateHUResponse;
import de.metas.frontend_testing.masterdata.hu.JsonPackingInstructionsResponse;
import de.metas.frontend_testing.masterdata.product.JsonCreateProductResponse;
import de.metas.frontend_testing.masterdata.sales_order.JsonSalesOrderCreateResponse;
import de.metas.frontend_testing.masterdata.warehouse.JsonWarehouseResponse;
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
	@NonNull Map<String, JsonCreateBPartnerResponse> bpartners;
	@NonNull Map<String, JsonCreateProductResponse> products;
	@NonNull Map<String, JsonWarehouseResponse> warehouses;
	@NonNull Map<String, JsonPackingInstructionsResponse> packingInstructions;
	@NonNull Map<String, JsonCreateHUResponse> handlingUnits;
	@NonNull Map<String, JsonSalesOrderCreateResponse> salesOrders;
}
