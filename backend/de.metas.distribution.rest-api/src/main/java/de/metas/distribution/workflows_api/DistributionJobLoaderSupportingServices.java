package de.metas.distribution.workflows_api;

import de.metas.handlingunits.ddorder.IHUDDOrderBL;
import de.metas.handlingunits.ddorder.picking.DDOrderPickFromService;
import de.metas.handlingunits.ddorder.picking.DDOrderPickSchedule;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.warehouse.LocatorBarcode;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.compiere.model.I_M_Locator;
import org.eevolution.api.DDOrderId;
import org.eevolution.model.I_DD_Order;
import org.eevolution.model.I_DD_OrderLine;

import java.time.ZoneId;
import java.util.List;

public class DistributionJobLoaderSupportingServices
{
	private final IHUDDOrderBL ddOrderBL;
	private final DDOrderPickFromService ddOrderPickFromService;
	private final IWarehouseBL warehouseBL;
	private final IProductBL productBL;
	private final IOrgDAO orgDAO;

	@Builder
	private DistributionJobLoaderSupportingServices(
			@NonNull final IHUDDOrderBL ddOrderBL,
			@NonNull final DDOrderPickFromService ddOrderPickFromService,
			@NonNull final IWarehouseBL warehouseBL,
			@NonNull final IProductBL productBL,
			@NonNull final IOrgDAO orgDAO)
	{
		this.ddOrderBL = ddOrderBL;
		this.ddOrderPickFromService = ddOrderPickFromService;
		this.warehouseBL = warehouseBL;
		this.productBL = productBL;
		this.orgDAO = orgDAO;
	}

	public ProductInfo getProductInfo(@NonNull final ProductId productId)
	{
		return ProductInfo.builder()
				.productId(productId)
				.caption(productBL.getProductNameTrl(productId))
				.build();
	}

	public WarehouseInfo getWarehouseInfoByRepoId(final int warehouseRepoId)
	{
		final WarehouseId warehouseId = WarehouseId.ofRepoId(warehouseRepoId);
		return WarehouseInfo.builder()
				.warehouseId(warehouseId)
				.caption(warehouseBL.getWarehouseName(warehouseId))
				.build();
	}

	public LocatorInfo getLocatorInfoByRepoId(final int locatorRepoId)
	{
		final I_M_Locator locator = warehouseBL.getLocatorByRepoId(locatorRepoId);
		final LocatorId locatorId = LocatorId.ofRepoId(locator.getM_Warehouse_ID(), locator.getM_Locator_ID());
		return LocatorInfo.builder()
				.locatorId(locatorId)
				.barcode(LocatorBarcode.ofLocatorId(locatorId))
				.caption(locator.getValue())
				.build();
	}

	public I_DD_Order getDDOrderById(final DDOrderId ddOrderId)
	{
		return ddOrderBL.getById(ddOrderId);
	}

	public List<I_DD_OrderLine> getLines(final I_DD_Order ddOrder)
	{
		return ddOrderBL.retrieveLines(ddOrder);
	}

	public List<DDOrderPickSchedule> getSchedules(final DDOrderId ddOrderId)
	{
		return ddOrderPickFromService.getSchedules(ddOrderId);
	}

	public ZoneId getTimeZone(final OrgId orgId)
	{
		return orgDAO.getTimeZone(orgId);
	}
}
