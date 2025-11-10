package de.metas.frontend_testing.masterdata.inventory;

import com.google.common.collect.ImmutableSet;
import de.metas.common.util.time.SystemTime;
import de.metas.frontend_testing.masterdata.Identifier;
import de.metas.frontend_testing.masterdata.MasterdataContext;
import de.metas.handlingunits.inventory.Inventory;
import de.metas.handlingunits.inventory.InventoryHeaderCreateRequest;
import de.metas.handlingunits.inventory.InventoryService;
import de.metas.handlingunits.inventory.draftlinescreator.DraftInventoryLinesCreateRequest;
import de.metas.inventory.HUAggregationType;
import de.metas.logging.LogManager;
import de.metas.organization.OrgId;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.slf4j.Logger;

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

		final Inventory inventory = inventoryService.createInventoryHeader(
				InventoryHeaderCreateRequest.builder()
						.orgId(orgId)
						.warehouseId(context.getId(request.getWarehouse(), WarehouseId.class))
						.docTypeId(inventoryService.getDocTypeIdByAggregationType(HUAggregationType.MULTI_HU, orgId))
						.movementDate(request.getDate() != null ? request.getDate() : SystemTime.asZonedDateTime())
						.build()
		);

		final ImmutableSet<ProductId> productIds = context.getIds(request.getProducts(), ProductId.class);
		if (productIds.isEmpty())
		{
			throw new AdempiereException("Inventory shall specify at least one product: " + identifier + "=" + request);
		}

		inventoryService.createDraftLines(
				DraftInventoryLinesCreateRequest.builder()
						.inventory(inventory)
						.onlyProductIds(productIds)
						.build()
		);

		context.putIdentifier(identifier, inventory.getId());

		return JsonInventoryResponse.builder()
				.id(inventory.getId())
				.documentNo(inventory.getDocumentNo())
				.build();
	}
}
