package de.metas.frontend_testing.masterdata;

import de.metas.frontend_testing.masterdata.adprocess.JsonSetAdProcessFlagsRequest;
import de.metas.frontend_testing.masterdata.bpartner.JsonCreateBPartnerRequest;
import de.metas.frontend_testing.masterdata.orgseller.JsonOrgSellerRequest;
import de.metas.frontend_testing.masterdata.compensation_group.JsonCompensationGroupSchemaRequest;
import de.metas.frontend_testing.masterdata.custom_qrcode_format.JsonCustomQRCodeFormatRequest;
import de.metas.frontend_testing.masterdata.dd_order.JsonDDOrderRequest;
import de.metas.frontend_testing.masterdata.hu.JsonCreateHURequest;
import de.metas.frontend_testing.masterdata.hu.JsonPackingInstructionsRequest;
import de.metas.frontend_testing.masterdata.huQRCodes.JsonGenerateHUQRCodeRequest;
import de.metas.frontend_testing.masterdata.inventory.JsonInventoryRequest;
import de.metas.frontend_testing.masterdata.mobile_configuration.JsonMobileConfigRequest;
import de.metas.frontend_testing.masterdata.picking_slot.JsonPickingSlotCreateRequest;
import de.metas.frontend_testing.masterdata.pp_order.JsonPPOrderRequest;
import de.metas.frontend_testing.masterdata.product.JsonCreateProductRequest;
import de.metas.frontend_testing.masterdata.uom.JsonUOMRequest;
import de.metas.frontend_testing.masterdata.product_planning.JsonCreateProductPlanningRequest;
import de.metas.frontend_testing.masterdata.resource.JsonCreateResourceRequest;
import de.metas.frontend_testing.masterdata.purchase_order.JsonPurchaseOrderCreateRequest;
import de.metas.frontend_testing.masterdata.receipt.JsonReceiptCreateRequest;
import de.metas.frontend_testing.masterdata.sales_order.JsonSalesOrderCreateRequest;
import de.metas.frontend_testing.masterdata.shipment.JsonShipmentCreateRequest;
import de.metas.frontend_testing.masterdata.invoice.JsonInvoiceCreateRequest;
import de.metas.frontend_testing.masterdata.shipper.JsonCreateShipperRequest;
import de.metas.frontend_testing.masterdata.user.JsonLoginUserRequest;
import de.metas.frontend_testing.masterdata.warehouse.JsonWarehouseRequest;
import de.metas.frontend_testing.masterdata.workplace.JsonWorkplaceRequest;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

@Value
@Builder
@Jacksonized
public class JsonCreateMasterdataRequest
{
	@Nullable Map<String, Object> context;

	@Nullable Map<String, String> sysconfigs;

	/**
	 * Sets flag columns on {@code AD_Process} records matched by a {@code JasperReport} substring.
	 * Used to enable {@code IsPdfA3Output=Y} on the sales-invoice report process so that the mock
	 * report service returns a valid PDF/A-3 and ZUGFeRD assembly can embed the CII XML into it.
	 * Applied in execution order before bpartner/product creation.
	 */
	@Nullable List<JsonSetAdProcessFlagsRequest> adProcessFlags;

	/**
	 * Configures an org's seller identity for ZUGFeRD / EN16931: sets
	 * {@code AD_OrgInfo.Org_BPartner_ID} + {@code OrgBP_Location_ID} to the specified
	 * BR-DE-conformant BPartner. Must appear after {@code bpartners} in execution order.
	 */
	@Nullable JsonOrgSellerRequest orgSeller;

	@Nullable JsonMobileConfigRequest mobileConfig;
	@Nullable Map<String, JsonLoginUserRequest> login;
	@Nullable Map<String, JsonCreateBPartnerRequest> bpartners;
	@Nullable Map<String, JsonWorkplaceRequest> workplaces;
	@Nullable Map<String, JsonWarehouseRequest> warehouses;
	@Nullable Map<String, JsonUOMRequest> uoms;
	@Nullable Map<String, JsonCompensationGroupSchemaRequest> compensationGroupSchemas;
	@Nullable Map<String, JsonCreateProductRequest> products;

	/**
	 * Updates {@code M_Product.ProductLifeCycleStatus} (BBS-Status) on already-created products:
	 * product identifier → status code ({@code O}/{@code A}/{@code G}/{@code N}). Applied late
	 * (after order creation) so a product can be flipped to a blocking status only once its
	 * order/picking-job setup exists — mirroring the real-life temporal block. See
	 * {@link de.metas.frontend_testing.masterdata.product.SetProductLifeCycleStatusCommand}.
	 */
	@Nullable Map<String, String> productLifeCycleStatuses;

	@Nullable Map<String, JsonCreateResourceRequest> resources;
	@Nullable Map<String, JsonCreateProductPlanningRequest> productPlannings;
	@Nullable Map<String, JsonPickingSlotCreateRequest> pickingSlots;
	@Nullable Map<String, JsonPackingInstructionsRequest> packingInstructions;
	@Nullable Map<String, JsonCreateShipperRequest> shippers;
	@Nullable Map<String, JsonCreateHURequest> handlingUnits;
	@Nullable Map<String, JsonGenerateHUQRCodeRequest> generatedHUQRCodes;
	@Nullable Map<String, JsonSalesOrderCreateRequest> salesOrders;
	@Nullable Map<String, JsonPurchaseOrderCreateRequest> purchaseOrders;
	@Nullable Map<String, JsonShipmentCreateRequest> shipments;
	@Nullable Map<String, JsonReceiptCreateRequest> receipts;
	@Nullable Map<String, JsonInvoiceCreateRequest> invoices;
	@Nullable Map<String, JsonPPOrderRequest> manufacturingOrders;
	@Nullable Map<String, JsonDDOrderRequest> distributionOrders;
	@Nullable List<JsonCustomQRCodeFormatRequest> customQRCodeFormats;
	@Nullable Map<String, JsonInventoryRequest> inventories;
}
