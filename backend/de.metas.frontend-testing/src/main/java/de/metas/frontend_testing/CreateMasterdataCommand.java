package de.metas.frontend_testing;

import com.google.common.collect.ImmutableMap;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.handlingunits.inventory.InventoryService;
import de.metas.handlingunits.qrcodes.service.HUQRCodesService;
import de.metas.picking.api.IPickingSlotBL;
import de.metas.product.IProductBL;
import de.metas.product.IProductDAO;
import de.metas.rest_api.v2.order.JsonSalesOrder;
import de.metas.rest_api.v2.order.JsonSalesOrderCreateRequest;
import de.metas.rest_api.v2.order.sales.SalesOrderRestController;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.warehouse.api.IWarehouseDAO;

import java.util.Map;

@Builder
public class CreateMasterdataCommand
{
	@NonNull private final IProductDAO productDAO = Services.get(IProductDAO.class);
	@NonNull private final IProductBL productBL = Services.get(IProductBL.class);
	@NonNull private final IWarehouseDAO warehouseDAO = Services.get(IWarehouseDAO.class);
	@NonNull private final IBPartnerDAO partnerDAO = Services.get(IBPartnerDAO.class);
	@NonNull private final IPickingSlotBL pickingSlotBL = Services.get(IPickingSlotBL.class);
	@NonNull private final SalesOrderRestController salesOrderRestController;
	@NonNull private final InventoryService inventoryService;
	@NonNull private final HUQRCodesService huQRCodesService;

	@NonNull private final JsonCreateMasterdataRequest request;

	public JsonCreateMasterdataResponse execute()
	{
		return JsonCreateMasterdataResponse.builder()
				.salesOrders(createSalesOrders())
				.handlingUnits(createHUs())
				.build();
	}

	private @NonNull ImmutableMap<String, JsonSalesOrder> createSalesOrders()
	{
		final ImmutableMap.Builder<String, JsonSalesOrder> salesOrders = ImmutableMap.builder();
		final Map<String, JsonSalesOrderCreateRequest> salesOrderRequests = request.getSalesOrders();
		if (salesOrderRequests != null && !salesOrderRequests.isEmpty())
		{
			for (final String requestKey : salesOrderRequests.keySet())
			{
				final JsonSalesOrder salesOrder = salesOrderRestController.createOrder(salesOrderRequests.get(requestKey));

				salesOrders.put(requestKey, salesOrder);
			}
		}

		return salesOrders.build();
	}

	private @NonNull ImmutableMap<String, JsonCreateHUResponse> createHUs()
	{
		final ImmutableMap.Builder<String, JsonCreateHUResponse> huResponses = ImmutableMap.builder();
		final Map<String, JsonCreateHURequest> huRequests = request.getHandlingUnits();
		if (huRequests != null && !huRequests.isEmpty())
		{
			for (final String requestKey : huRequests.keySet())
			{
				final JsonCreateHUResponse huResponse = createHU(huRequests.get(requestKey));
				huResponses.put(requestKey, huResponse);
			}
		}

		return huResponses.build();
	}

	private JsonCreateHUResponse createHU(final JsonCreateHURequest request)
	{
		return CreateHUCommand.builder()
				.inventoryService(inventoryService)
				.huQRCodesService(huQRCodesService)
				.request(request)
				.build()
				.execute();
	}
}
