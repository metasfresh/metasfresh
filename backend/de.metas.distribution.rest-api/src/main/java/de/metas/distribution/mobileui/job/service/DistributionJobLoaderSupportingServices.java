package de.metas.distribution.mobileui.job.service;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import de.metas.distribution.ddorder.DDOrderId;
import de.metas.distribution.ddorder.DDOrderLineId;
import de.metas.distribution.ddorder.DDOrderQuery;
import de.metas.distribution.ddorder.DDOrderService;
import de.metas.distribution.ddorder.movement.schedule.DDOrderMoveSchedule;
import de.metas.distribution.ddorder.movement.schedule.DDOrderMoveScheduleService;
import de.metas.distribution.mobileui.config.MobileUIDistributionConfig;
import de.metas.distribution.mobileui.config.MobileUIDistributionConfigRepository;
import de.metas.distribution.mobileui.external_services.hu.DistributionHUService;
import de.metas.distribution.mobileui.external_services.hu.HUInfo;
import de.metas.distribution.mobileui.external_services.product.DistributionProductService;
import de.metas.distribution.mobileui.external_services.product.ProductInfo;
import de.metas.distribution.mobileui.external_services.sourcedoc.DistributionSourceDocService;
import de.metas.distribution.mobileui.external_services.sourcedoc.ManufacturingOrderRef;
import de.metas.distribution.mobileui.external_services.sourcedoc.PlantInfo;
import de.metas.distribution.mobileui.external_services.sourcedoc.SalesOrderRef;
import de.metas.distribution.mobileui.external_services.warehouse.DistributionWarehouseService;
import de.metas.distribution.mobileui.external_services.warehouse.LocatorInfo;
import de.metas.distribution.mobileui.external_services.warehouse.WarehouseInfo;
import de.metas.handlingunits.HuId;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStrings;
import de.metas.order.OrderId;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.product.ResourceId;
import de.metas.util.Services;
import de.metas.util.collections.CollectionUtils;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.warehouse.LocatorId;
import org.eevolution.api.PPOrderBOMLineId;
import org.eevolution.api.PPOrderId;
import org.eevolution.model.I_DD_Order;
import org.eevolution.model.I_DD_OrderLine;
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
	@NonNull private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	@NonNull private final MobileUIDistributionConfigRepository configRepository;
	@NonNull private final DDOrderService ddOrderService;
	@NonNull private final DDOrderMoveScheduleService ddOrderMoveScheduleService;
	@NonNull private final DistributionProductService productService;
	@NonNull private final DistributionWarehouseService warehouseService;
	@NonNull private final DistributionHUService huService;
	@NonNull private final DistributionSourceDocService sourceDocService;

	public MobileUIDistributionConfig getConfig() {return configRepository.getConfig();}

	public ProductInfo getProductInfo(@NonNull final ProductId productId)
	{
		return productService.getProductInfo(productId);
	}

	public WarehouseInfo getWarehouseInfoByRepoId(final int warehouseRepoId)
	{
		return warehouseService.getWarehouseInfoByRepoId(warehouseRepoId);
	}

	public LocatorInfo getLocatorInfoByRepoId(final int locatorRepoId)
	{
		return warehouseService.getLocatorInfoByRepoId(locatorRepoId);
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

	public boolean hasInTransitSchedules(@NonNull final LocatorId inTransitLocatorId)
	{
		return ddOrderMoveScheduleService.hasInTransitSchedules(inTransitLocatorId);
	}

	public ZoneId getTimeZone(final OrgId orgId)
	{
		return orgDAO.getTimeZone(orgId);
	}

	public HUInfo getHUInfo(final HuId huId) {return huService.getHUInfoById(huId);}

	@Nullable
	public SalesOrderRef getSalesOderRef(@NonNull final I_DD_Order ddOrder)
	{
		final OrderId salesOrderId = OrderId.ofRepoIdOrNull(ddOrder.getC_Order_ID());
		return salesOrderId != null ? sourceDocService.getSalesOderRef(salesOrderId) : null;
	}

	@Nullable
	public ManufacturingOrderRef getManufacturingOrderRef(@NonNull final I_DD_Order ddOrder)
	{
		final PPOrderId ppOrderId = PPOrderId.ofRepoIdOrNull(ddOrder.getForward_PP_Order_ID());
		return ppOrderId != null ? sourceDocService.getManufacturingOrderRef(ppOrderId) : null;
	}

	@NonNull
	public ITranslatableString getPickingInstruction(@NonNull final I_DD_Order ddOrder)
	{
		final PPOrderBOMLineId orderBOMLineId = PPOrderBOMLineId.ofRepoIdOrNull(ddOrder.getForward_PP_Order_BOMLine_ID());
		return orderBOMLineId != null ? sourceDocService.getPickingInstruction(orderBOMLineId) : TranslatableStrings.empty();
	}

	@NonNull
	public PlantInfo getPlantInfo(@NonNull final ResourceId plantId)
	{
		return sourceDocService.getPlantInfo(plantId);
	}
}
