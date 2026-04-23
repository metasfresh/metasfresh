package de.metas.frontend_testing.masterdata;

import com.fasterxml.jackson.annotation.JsonInclude;
import de.metas.frontend_testing.masterdata.bpartner.JsonCreateBPartnerResponse;
import de.metas.frontend_testing.masterdata.dd_order.JsonDDOrderResponse;
import de.metas.frontend_testing.masterdata.hu.JsonCreateHUResponse;
import de.metas.frontend_testing.masterdata.hu.JsonPackingInstructionsResponse;
import de.metas.frontend_testing.masterdata.huQRCodes.JsonGenerateHUQRCodeResponse;
import de.metas.frontend_testing.masterdata.inventory.JsonInventoryResponse;
import de.metas.frontend_testing.masterdata.mobile_configuration.JsonMobileConfigResponse;
import de.metas.frontend_testing.masterdata.picking_slot.JsonPickingSlotCreateResponse;
import de.metas.frontend_testing.masterdata.pp_order.JsonPPOrderResponse;
import de.metas.frontend_testing.masterdata.product.JsonCreateProductResponse;
import de.metas.frontend_testing.masterdata.product_planning.JsonCreateProductPlanningResponse;
import de.metas.frontend_testing.masterdata.resource.JsonCreateResourceResponse;
import de.metas.frontend_testing.masterdata.purchase_order.JsonPurchaseOrderCreateResponse;
import de.metas.frontend_testing.masterdata.receipt.JsonReceiptCreateResponse;
import de.metas.frontend_testing.masterdata.sales_order.JsonSalesOrderCreateResponse;
import de.metas.frontend_testing.masterdata.shipment.JsonShipmentCreateResponse;
import de.metas.frontend_testing.masterdata.invoice.JsonInvoiceCreateResponse;
import de.metas.frontend_testing.masterdata.shipper.JsonCreateShipperResponse;
import de.metas.frontend_testing.masterdata.user.JsonLoginUserResponse;
import de.metas.frontend_testing.masterdata.warehouse.JsonWarehouseResponse;
import de.metas.frontend_testing.masterdata.workplace.JsonWorkplaceResponse;
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
	@Nullable Map<String, Object> context;

	@Nullable @JsonInclude(JsonInclude.Include.NON_EMPTY) Map<String, String> previousSysconfigs;

	@Nullable @JsonInclude(JsonInclude.Include.NON_EMPTY) JsonMobileConfigResponse mobileConfig;
	@NonNull Map<String, JsonLoginUserResponse> login;
	@NonNull Map<String, JsonCreateBPartnerResponse> bpartners;
	@NonNull Map<String, JsonCreateProductResponse> products;
	@Nullable Map<String, JsonCreateResourceResponse> resources;
	@NonNull Map<String, JsonCreateProductPlanningResponse> productPlannings;
	@NonNull Map<String, JsonPickingSlotCreateResponse> pickingSlots;
	@NonNull Map<String, JsonWarehouseResponse> warehouses;
	@NonNull Map<String, JsonWorkplaceResponse> workplaces;
	@NonNull Map<String, JsonPackingInstructionsResponse> packingInstructions;
	@Nullable Map<String, JsonCreateShipperResponse> shippers;
	@NonNull Map<String, JsonCreateHUResponse> handlingUnits;
	@Nullable Map<String, JsonGenerateHUQRCodeResponse> generatedHUQRCodes;
	@NonNull Map<String, JsonSalesOrderCreateResponse> salesOrders;
	@NonNull Map<String, JsonPurchaseOrderCreateResponse> purchaseOrders;
	@Nullable Map<String, JsonShipmentCreateResponse> shipments;
	@Nullable Map<String, JsonReceiptCreateResponse> receipts;
	@Nullable Map<String, JsonInvoiceCreateResponse> invoices;
	@NonNull Map<String, JsonDDOrderResponse> distributionOrders;
	@NonNull Map<String, JsonPPOrderResponse> manufacturingOrders;
	@Nullable Map<String, JsonInventoryResponse> inventories;
}
