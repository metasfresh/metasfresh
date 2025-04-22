package de.metas.distribution.workflows_api;

import de.metas.distribution.config.MobileUIDistributionConfig;
import de.metas.distribution.config.MobileUIDistributionConfigRepository;
import de.metas.distribution.ddorder.DDOrderId;
import de.metas.distribution.ddorder.DDOrderService;
import de.metas.distribution.ddorder.movement.schedule.DDOrderMoveSchedule;
import de.metas.distribution.ddorder.movement.schedule.DDOrderMoveScheduleService;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import de.metas.handlingunits.qrcodes.service.HUQRCodesService;
import de.metas.order.IOrderBL;
import de.metas.order.OrderId;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.product.ResourceId;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.adempiere.warehouse.qrcode.LocatorQRCode;
import org.compiere.model.I_M_Locator;
import org.eevolution.api.IPPOrderBL;
import org.eevolution.api.PPOrderId;
import org.eevolution.model.I_DD_Order;
import org.eevolution.model.I_DD_OrderLine;
import org.eevolution.model.I_PP_Order;

import javax.annotation.Nullable;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

public class DistributionJobLoaderSupportingServices
{
	private final MobileUIDistributionConfigRepository configRepository;
	private final DDOrderService ddOrderService;
	private final DDOrderMoveScheduleService ddOrderMoveScheduleService;
	private final HUQRCodesService huQRCodeService;
	private final IWarehouseBL warehouseBL;
	private final IProductBL productBL;
	private final IOrgDAO orgDAO;
	private final IOrderBL orderBL;
	private final IPPOrderBL ppOrderBL;

	@Builder
	private DistributionJobLoaderSupportingServices(
			@NonNull final MobileUIDistributionConfigRepository configRepository,
			@NonNull final DDOrderService ddOrderService,
			@NonNull final DDOrderMoveScheduleService ddOrderMoveScheduleService,
			@NonNull final HUQRCodesService huQRCodeService,
			@NonNull final IWarehouseBL warehouseBL,
			@NonNull final IProductBL productBL,
			@NonNull final IOrgDAO orgDAO,
			@NonNull final IOrderBL orderBL,
			@NonNull final IPPOrderBL ppOrderBL)
	{
		this.configRepository = configRepository;
		this.ddOrderService = ddOrderService;
		this.ddOrderMoveScheduleService = ddOrderMoveScheduleService;
		this.huQRCodeService = huQRCodeService;
		this.warehouseBL = warehouseBL;
		this.productBL = productBL;
		this.orgDAO = orgDAO;
		this.orderBL = orderBL;
		this.ppOrderBL = ppOrderBL;
	}

	public MobileUIDistributionConfig getConfig() {return configRepository.getConfig();}

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
				.qrCode(LocatorQRCode.ofLocator(locator))
				.caption(locator.getValue())
				.build();
	}

	public I_DD_Order getDDOrderById(final DDOrderId ddOrderId)
	{
		return ddOrderService.getById(ddOrderId);
	}

	public List<I_DD_OrderLine> getLines(final I_DD_Order ddOrder)
	{
		return ddOrderService.retrieveLines(ddOrder);
	}

	public List<DDOrderMoveSchedule> getSchedules(final DDOrderId ddOrderId)
	{
		return ddOrderMoveScheduleService.getSchedules(ddOrderId);
	}

	public ZoneId getTimeZone(final OrgId orgId)
	{
		return orgDAO.getTimeZone(orgId);
	}

	public HUQRCode getQRCodeByHuId(final HuId huId) {return huQRCodeService.getQRCodeByHuId(huId);}

	@Nullable
	public String getSalesOderDocNo(@NonNull final I_DD_Order ddOrder)
	{
		return Optional.ofNullable(OrderId.ofRepoIdOrNull(ddOrder.getC_Order_ID()))
				.map(orderBL::getDocumentNoById)
				.orElse(null);
	}

	@Nullable
	public String getPPOrderDocNo(@NonNull final I_DD_Order ddOrder)
	{
		return Optional.ofNullable(PPOrderId.ofRepoIdOrNull(ddOrder.getForward_PP_Order_ID()))
				.map(ppOrderBL::getById)
				.map(I_PP_Order::getDocumentNo)
				.orElse(null);
	}

	@NonNull
	public ResourceInfo getPlantInfo(@NonNull final ResourceId resourceId)
	{
		return ResourceInfo.builder()
				.resourceId(resourceId)
				.caption(ppOrderBL.getResourceName(resourceId))
				.build();
	}
}
