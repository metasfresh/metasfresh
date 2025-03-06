package de.metas.frontend_testing.masterdata;

import com.fasterxml.jackson.annotation.JsonInclude;
import de.metas.frontend_testing.masterdata.bpartner.JsonCreateBPartnerResponse;
import de.metas.frontend_testing.masterdata.dd_order.JsonDDOrderResponse;
import de.metas.frontend_testing.masterdata.hu.JsonCreateHUResponse;
import de.metas.frontend_testing.masterdata.hu.JsonPackingInstructionsResponse;
import de.metas.frontend_testing.masterdata.mobile_configuration.JsonMobileConfigResponse;
import de.metas.frontend_testing.masterdata.picking_slot.JsonPickingSlotCreateResponse;
import de.metas.frontend_testing.masterdata.pp_order.JsonPPOrderResponse;
import de.metas.frontend_testing.masterdata.product.JsonCreateProductResponse;
import de.metas.frontend_testing.masterdata.sales_order.JsonSalesOrderCreateResponse;
import de.metas.frontend_testing.masterdata.user.JsonLoginUserResponse;
import de.metas.frontend_testing.masterdata.warehouse.JsonWarehouseResponse;
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
	@Nullable @JsonInclude(JsonInclude.Include.NON_EMPTY) JsonMobileConfigResponse mobileConfig;
	@NonNull Map<String, JsonLoginUserResponse> login;
	@NonNull Map<String, JsonCreateBPartnerResponse> bpartners;
	@NonNull Map<String, JsonCreateProductResponse> products;
	@NonNull Map<String, JsonPickingSlotCreateResponse> pickingSlots;
	@NonNull Map<String, JsonWarehouseResponse> warehouses;
	@NonNull Map<String, JsonPackingInstructionsResponse> packingInstructions;
	@NonNull Map<String, JsonCreateHUResponse> handlingUnits;
	@NonNull Map<String, JsonSalesOrderCreateResponse> salesOrders;
	@NonNull Map<String, JsonDDOrderResponse> distributionOrders;
	@NonNull Map<String, JsonPPOrderResponse> manufacturingOrders;
}
