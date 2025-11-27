package de.metas.handlingunits.picking.job.repository;

import de.metas.bpartner.BPartnerId;
import de.metas.gs1.GS1ProductCodes;
import de.metas.handlingunits.HUPIItemProduct;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.HuPackingInstructionsId;
import de.metas.handlingunits.picking.config.mobileui.MobileUIPickingUserProfileService;
import de.metas.handlingunits.picking.config.mobileui.PickingJobOptions;
import de.metas.handlingunits.picking.job.model.ScheduledPackageable;
import de.metas.handlingunits.picking.job.model.ScheduledPackageableList;
import de.metas.handlingunits.picking.job.model.ScheduledPackageableLocks;
import de.metas.handlingunits.picking.job.service.PickingJobLockService;
import de.metas.handlingunits.picking.job.service.PickingJobSlotService;
import de.metas.handlingunits.picking.job.service.external.bpartner.PickingJobBPartnerService;
import de.metas.handlingunits.picking.job.service.external.hu.PickingJobHUService;
import de.metas.handlingunits.picking.job.service.external.product.PickingJobProductService;
import de.metas.handlingunits.picking.job.service.external.product.ProductInfo;
import de.metas.handlingunits.picking.job.service.external.salesorder.PickingJobSalesOrderService;
import de.metas.handlingunits.picking.job.service.external.warehouse.PickingJobWarehouseService;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import de.metas.i18n.ITranslatableString;
import de.metas.order.OrderAndLineId;
import de.metas.order.OrderId;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.picking.api.PickingSlotId;
import de.metas.picking.api.PickingSlotIdAndCaption;
import de.metas.picking.api.ShipmentScheduleAndJobScheduleIdSet;
import de.metas.product.ProductCategoryId;
import de.metas.product.ProductId;
import de.metas.util.Services;
import de.metas.util.collections.CollectionUtils;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.warehouse.LocatorId;
import org.compiere.util.TimeUtil;

import javax.annotation.Nullable;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.Optional;
import java.util.Set;

/**
 * A bunch of services needed to support the loading of a given PickingJob.
 *
 * @implNote Please don't instantiate it in Repositories.
 */
@Builder
public class DefaultPickingJobLoaderSupportingServices implements PickingJobLoaderSupportingServices
{
	@NonNull private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	@NonNull private final PickingJobSalesOrderService orderService;
	@NonNull private final PickingJobWarehouseService warehouseService;
	@NonNull private final PickingJobBPartnerService bpartnerService;
	@NonNull private final PickingJobProductService productService;
	@NonNull private final PickingJobSlotService pickingSlotService;
	@NonNull private final PickingJobLockService pickingJobLockService;
	@NonNull private final PickingJobHUService huService;
	@NonNull private final MobileUIPickingUserProfileService profileService;

	private final HashMap<OrderId, String> salesOrderDocumentNosCache = new HashMap<>();
	private final HashMap<BPartnerId, String> bpartnerNamesCache = new HashMap<>();
	private final HashMap<PickingSlotId, PickingSlotIdAndCaption> pickingSlotIdAndCaptionsCache = new HashMap<>();
	private final HashMap<ProductId, ProductInfo> productInfoCache = new HashMap<>();
	private final HashMap<LocatorId, String> locatorNamesCache = new HashMap<>();

	@Override
	public PickingJobOptions getPickingJobOptions(@Nullable final BPartnerId customerId) {return profileService.getPickingJobOptions(customerId);}

	@Override
	public void warmUpCachesFrom(@NonNull final ScheduledPackageableList items)
	{
		items.forEach(this::loadCacheFrom);
	}

	private void loadCacheFrom(@NonNull final ScheduledPackageable packageable)
	{
		if (packageable.getSalesOrderId() != null)
		{
			salesOrderDocumentNosCache.put(packageable.getSalesOrderId(), packageable.getSalesOrderDocumentNo());
		}
	}

	@Override
	public void warmUpSalesOrderDocumentNosCache(@NonNull final Collection<OrderId> orderIds)
	{
		CollectionUtils.getAllOrLoad(salesOrderDocumentNosCache, orderIds, orderService::getDocumentNosByIds);
	}

	@Override
	public String getSalesOrderDocumentNo(@NonNull final OrderId salesOrderId)
	{
		return salesOrderDocumentNosCache.computeIfAbsent(salesOrderId, orderService::getDocumentNoById);
	}

	@Override
	public void warmUpBPartnerNamesCache(@NonNull final Set<BPartnerId> bpartnerIds)
	{
		CollectionUtils.getAllOrLoad(bpartnerNamesCache, bpartnerIds, bpartnerService::getBPartnerNames);
	}

	@Override
	public String getBPartnerName(@NonNull final BPartnerId bpartnerId)
	{
		return bpartnerNamesCache.computeIfAbsent(bpartnerId, bpartnerService::getBPartnerName);
	}

	@Override
	public ZonedDateTime toZonedDateTime(@NonNull final java.sql.Timestamp timestamp, @NonNull final OrgId orgId)
	{
		final ZoneId zoneId = orgDAO.getTimeZone(orgId);
		return TimeUtil.asZonedDateTime(timestamp, zoneId);
	}

	@Override
	public PickingSlotIdAndCaption getPickingSlotIdAndCaption(@NonNull final PickingSlotId pickingSlotId)
	{
		return pickingSlotIdAndCaptionsCache.computeIfAbsent(pickingSlotId, pickingSlotService::getPickingSlotIdAndCaption);
	}

	@Override
	public String getProductNo(@NonNull final ProductId productId)
	{
		return getProductInfo(productId).getProductNo();
	}

	@Override
	public Optional<GS1ProductCodes> getGS1ProductCodes(@NonNull final ProductId productId, @Nullable final BPartnerId customerId)
	{
		return getProductInfo(productId).getGs1ProductCodes().getEffectiveCodes(customerId);
	}

	@Override
	public ProductCategoryId getProductCategoryId(@NonNull final ProductId productId)
	{
		return getProductInfo(productId).getProductCategoryId();
	}

	@Override
	public ITranslatableString getProductName(@NonNull final ProductId productId)
	{
		return getProductInfo(productId).getName();
	}

	private ProductInfo getProductInfo(@NonNull final ProductId productId)
	{
		return productInfoCache.computeIfAbsent(productId, productService::getById);
	}

	@Override
	public HUPIItemProduct getPackingInfo(@NonNull final HUPIItemProductId huPIItemProductId)
	{
		return huService.getPackingInfo(huPIItemProductId);
	}

	@Override
	public String getPICaption(@NonNull final HuPackingInstructionsId piId)
	{
		return huService.getPI(piId).getName();
	}

	@Override
	public String getLocatorName(@NonNull final LocatorId locatorId)
	{
		return locatorNamesCache.computeIfAbsent(locatorId, warehouseService::getLocatorNameById);
	}

	@Override
	public HUQRCode getQRCodeByHUId(final HuId huId)
	{
		return huService.getQRCodeByHuId(huId);
	}

	@Override
	public ScheduledPackageableLocks getLocks(final ShipmentScheduleAndJobScheduleIdSet scheduleIds)
	{
		return pickingJobLockService.getLocks(scheduleIds);
	}

	@Override
	public int getSalesOrderLineSeqNo(@NonNull final OrderAndLineId orderAndLineId)
	{
		return orderService.getSalesOrderLineSeqNo(orderAndLineId);
	}

	//
	//
	//
}
