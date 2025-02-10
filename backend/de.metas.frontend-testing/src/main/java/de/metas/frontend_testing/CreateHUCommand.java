package de.metas.frontend_testing;

import de.metas.common.util.time.SystemTime;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.inventory.CreateVirtualInventoryWithQtyReq;
import de.metas.handlingunits.inventory.InventoryService;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import de.metas.handlingunits.qrcodes.service.HUQRCodesService;
import de.metas.organization.OrgId;
import de.metas.product.IProductBL;
import de.metas.product.IProductDAO;
import de.metas.product.IProductDAO.ProductQuery;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.service.ClientId;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.compiere.model.I_C_UOM;

@Builder
public class CreateHUCommand
{
	@NonNull private final IProductDAO productDAO = Services.get(IProductDAO.class);
	@NonNull private final IProductBL productBL = Services.get(IProductBL.class);
	@NonNull private final IWarehouseDAO warehouseDAO = Services.get(IWarehouseDAO.class);
	@NonNull private final InventoryService inventoryService;
	@NonNull private final HUQRCodesService huQRCodesService;

	@NonNull private final JsonCreateHURequest request;

	public JsonCreateHUResponse execute()
	{
		final WarehouseId warehouseId = warehouseDAO.getWarehouseIdByValue(request.getWarehouseCode());
		final ProductId productId = getProductIdByCode(request.getProductCode());

		final I_C_UOM uom = productBL.getStockUOM(productId);

		final HuId huId = inventoryService.createInventoryForMissingQty(
				CreateVirtualInventoryWithQtyReq.builder()
						.clientId(ClientId.METASFRESH)
						.orgId(OrgId.MAIN)
						.warehouseId(warehouseId)
						.productId(productId)
						.qty(Quantity.of(request.getQty(), uom))
						.movementDate(SystemTime.asZonedDateTime())
						.attributeSetInstanceId(AttributeSetInstanceId.NONE)
						.build()
		);
		final HUQRCode huQRCode = huQRCodesService.getQRCodeByHuId(huId);

		return JsonCreateHUResponse.builder()
				.huId(String.valueOf(huId.getRepoId()))
				.qrCode(huQRCode.toGlobalQRCodeString())
				.build();
	}

	private @NonNull ProductId getProductIdByCode(final String productCode)
	{
		final ProductId productId = productDAO.retrieveProductIdBy(ProductQuery.builder()
				.orgId(OrgId.MAIN)
				.includeAnyOrg(true)
				.value(productCode)
				.build());
		if (productId == null)
		{
			throw new AdempiereException("@NotFound@ M_Product_ID@ (@Value=" + productCode + ")");
		}
		return productId;
	}
}
