package de.metas.frontend_testing.masterdata.inventory;

import de.metas.common.util.time.SystemTime;
import de.metas.frontend_testing.masterdata.Identifier;
import de.metas.frontend_testing.masterdata.MasterdataContext;
import de.metas.handlingunits.inventory.Inventory;
import de.metas.handlingunits.inventory.InventoryHeaderCreateRequest;
import de.metas.handlingunits.inventory.InventoryLineCreateRequest;
import de.metas.handlingunits.inventory.InventoryService;
import de.metas.inventory.HUAggregationType;
import de.metas.logging.LogManager;
import de.metas.organization.OrgId;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.compiere.model.I_C_UOM;
import org.slf4j.Logger;

import java.math.BigDecimal;

@Builder
public class InventoryCreateCommand
{
	@NonNull private static final Logger logger = LogManager.getLogger(InventoryCreateCommand.class);
	@NonNull private final ITrxManager trxManager = Services.get(ITrxManager.class);
	@NonNull private final IWarehouseBL warehouseBL = Services.get(IWarehouseBL.class);
	@NonNull private final IProductBL productBL = Services.get(IProductBL.class);
	@NonNull private final InventoryService inventoryService;

	@NonNull private final MasterdataContext context;
	@NonNull private final JsonInventoryRequest request;
	@NonNull private final Identifier identifier;

	public JsonInventoryResponse execute()
	{
		return trxManager.callInThreadInheritedTrx(this::execute0);
	}

	private JsonInventoryResponse execute0()
	{
		final OrgId orgId = MasterdataContext.ORG_ID;

		final Inventory inventory = inventoryService.createInventoryHeader(InventoryHeaderCreateRequest.builder()
				.orgId(orgId)
				.warehouseId(context.getId(request.getWarehouse(), WarehouseId.class))
				.docTypeId(inventoryService.getDocTypeIdByAggregationType(HUAggregationType.MULTI_HU, orgId))
				.movementDate(request.getDate() != null ? request.getDate() : SystemTime.asZonedDateTime())
				.build());

		final LocatorId locatorId = warehouseBL.getOrCreateDefaultLocatorId(inventory.getWarehouseId());

		request.getLines().forEach(line -> {
			final ProductId productId = context.getId(line.getProduct(), ProductId.class);
			final I_C_UOM uom = productBL.getStockUOM(productId);
			inventoryService.createInventoryLine(InventoryLineCreateRequest.builder()
					.inventoryId(inventory.getId())
					.locatorId(locatorId)
					.aggregationType(HUAggregationType.MULTI_HU)
					.productId(productId)
					//.attributeSetId(attributeSetInstanceId)
					.qtyBooked(Quantity.of(BigDecimal.ZERO, uom))
					.qtyCount(Quantity.of(BigDecimal.ZERO, uom))
					// .inventoryLineHUList(productHUInv.toInventoryLineHUs(conversionBL, UomId.ofRepoId(stockingUOM.getC_UOM_ID())))
					.build());
		});
		
		
		throw new UnsupportedOperationException(); // TODO
	}

}
