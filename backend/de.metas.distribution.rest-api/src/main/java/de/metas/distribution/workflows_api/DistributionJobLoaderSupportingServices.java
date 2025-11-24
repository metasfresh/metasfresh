package de.metas.distribution.workflows_api;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import de.metas.distribution.config.MobileUIDistributionConfig;
import de.metas.distribution.config.MobileUIDistributionConfigRepository;
import de.metas.distribution.ddorder.DDOrderId;
import de.metas.distribution.ddorder.DDOrderLineId;
import de.metas.distribution.ddorder.DDOrderQuery;
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
import de.metas.util.Services;
import de.metas.util.collections.CollectionUtils;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
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
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class DistributionJobLoaderSupportingServices
{
	@NonNull private final IWarehouseBL warehouseBL = Services.get(IWarehouseBL.class);
	@NonNull private final IProductBL productBL = Services.get(IProductBL.class);
	@NonNull private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	@NonNull private final IOrderBL orderBL = Services.get(IOrderBL.class);
	@NonNull private final IPPOrderBL ppOrderBL = Services.get(IPPOrderBL.class);
	@NonNull private final MobileUIDistributionConfigRepository configRepository;
	@NonNull private final DDOrderService ddOrderService;
	@NonNull private final DDOrderMoveScheduleService ddOrderMoveScheduleService;
	@NonNull private final HUQRCodesService huQRCodeService;

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

	public Stream<I_DD_Order> stream(@NonNull final DDOrderQuery query)
	{
		return ddOrderService.streamDDOrders(query);
	}

	public I_DD_Order getDDOrderById(final DDOrderId ddOrderId)
	{
		return ddOrderService.getById(ddOrderId);
	}

	public Map<DDOrderId, List<I_DD_OrderLine>> getLinesByDDOrderIds(final Set<DDOrderId> ddOrderIds)
	{
		return ddOrderService.streamLinesByDDOrderIds(ddOrderIds)
				.collect(Collectors.groupingBy(ddOrderLine -> DDOrderId.ofRepoId(ddOrderLine.getDD_Order_ID()), Collectors.toList()));
	}

	public Map<DDOrderLineId, List<DDOrderMoveSchedule>> getSchedulesByDDOrderLineIds(final Set<DDOrderLineId> ddOrderLineIds)
	{
		if (ddOrderLineIds.isEmpty()) {return ImmutableMap.of();}

		final Map<DDOrderLineId, List<DDOrderMoveSchedule>> map = ddOrderMoveScheduleService.getByDDOrderLineIds(ddOrderLineIds)
				.stream()
				.collect(Collectors.groupingBy(DDOrderMoveSchedule::getDdOrderLineId, Collectors.toList()));
		return CollectionUtils.fillMissingKeys(map, ddOrderLineIds, ImmutableList.of());
	}

	public ZoneId getTimeZone(final OrgId orgId)
	{
		return orgDAO.getTimeZone(orgId);
	}

	public HUQRCode getQRCodeByHuId(final HuId huId) {return huQRCodeService.getQRCodeByHuId(huId);}

	@Nullable
	public SalesOrderRef getSalesOderRef(@NonNull final I_DD_Order ddOrder)
	{
		final OrderId salesOrderId = OrderId.ofRepoIdOrNull(ddOrder.getC_Order_ID());
		if (salesOrderId == null) {return null;}

		return SalesOrderRef.builder()
				.id(salesOrderId)
				.documentNo(orderBL.getDocumentNoById(salesOrderId))
				.build();
	}

	@Nullable
	public ManufacturingOrderRef getManufacturingOrderRef(@NonNull final I_DD_Order ddOrder)
	{
		final PPOrderId ppOrderId = PPOrderId.ofRepoIdOrNull(ddOrder.getForward_PP_Order_ID());
		if (ppOrderId == null) {return null;}

		final I_PP_Order ppOrder = ppOrderBL.getById(ppOrderId);

		return ManufacturingOrderRef.builder()
				.id(ppOrderId)
				.documentNo(ppOrder.getDocumentNo())
				.build();
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
