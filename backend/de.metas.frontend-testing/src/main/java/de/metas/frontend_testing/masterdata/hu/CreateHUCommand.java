package de.metas.frontend_testing.masterdata.hu;

import de.metas.common.util.time.SystemTime;
import de.metas.frontend_testing.masterdata.Identifier;
import de.metas.frontend_testing.masterdata.MasterdataContext;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.inventory.CreateVirtualInventoryWithQtyReq;
import de.metas.handlingunits.inventory.InventoryService;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import de.metas.handlingunits.qrcodes.service.HUQRCodesService;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.service.ClientId;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.compiere.model.I_C_UOM;

import javax.annotation.Nullable;

public class CreateHUCommand
{
	@NonNull private final ITrxManager trxManager = Services.get(ITrxManager.class);
	@NonNull private final IProductBL productBL = Services.get(IProductBL.class);
	@NonNull private final IWarehouseDAO warehouseDAO = Services.get(IWarehouseDAO.class);
	@NonNull private final InventoryService inventoryService;
	@NonNull private final HUQRCodesService huQRCodesService;

	@NonNull private final MasterdataContext context;
	@NonNull private final JsonCreateHURequest request;
	@NonNull private final Identifier identifier;

	@Builder
	private CreateHUCommand(
			@NonNull final InventoryService inventoryService,
			@NonNull final HUQRCodesService huQRCodesService,
			@NonNull final MasterdataContext context,
			@NonNull final JsonCreateHURequest request,
			@Nullable final String identifier)
	{
		this.inventoryService = inventoryService;
		this.huQRCodesService = huQRCodesService;
		this.context = context;
		this.request = request;

		this.identifier = Identifier.ofNullableStringOrUnique(identifier, "HU");
	}

	public JsonCreateHUResponse execute()
	{
		return trxManager.callInThreadInheritedTrx(this::execute0);
	}

	private JsonCreateHUResponse execute0()
	{
		final WarehouseId warehouseId = warehouseDAO.getWarehouseIdByValue(request.getWarehouse());
		final ProductId productId = context.getIdentifier(request.getProduct(), ProductId.class);
		final I_C_UOM uom = productBL.getStockUOM(productId);

		final HuId huId = inventoryService.createInventoryForMissingQty(
				CreateVirtualInventoryWithQtyReq.builder()
						.clientId(ClientId.METASFRESH)
						.orgId(MasterdataContext.ORG_ID)
						.warehouseId(warehouseId)
						.productId(productId)
						.qty(Quantity.of(request.getQty(), uom))
						.movementDate(SystemTime.asZonedDateTime())
						.attributeSetInstanceId(AttributeSetInstanceId.NONE)
						.build()
		);
		context.putIdentifier(identifier, huId);
		final HUQRCode huQRCode = huQRCodesService.getQRCodeByHuId(huId);

		return JsonCreateHUResponse.builder()
				.huId(String.valueOf(huId.getRepoId()))
				.qrCode(huQRCode.toGlobalQRCodeString())
				.build();
	}
}
