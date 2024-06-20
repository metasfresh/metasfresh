package de.metas.handlingunits.picking.job.repository;

import com.google.common.collect.SetMultimap;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.handlingunits.HUPIItemProduct;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHUPIItemProductBL;
import de.metas.handlingunits.model.I_M_ShipmentSchedule;
import de.metas.handlingunits.picking.job.service.PickingJobSlotService;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import de.metas.handlingunits.qrcodes.service.HUQRCodesService;
import de.metas.i18n.ITranslatableString;
import de.metas.inout.ShipmentScheduleId;
import de.metas.lock.api.ILockManager;
import de.metas.lock.spi.ExistingLockInfo;
import de.metas.order.IOrderBL;
import de.metas.order.OrderId;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.picking.api.Packageable;
import de.metas.picking.api.PackageableList;
import de.metas.picking.api.PickingSlotId;
import de.metas.picking.api.PickingSlotIdAndCaption;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.util.Services;
import de.metas.util.collections.CollectionUtils;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.compiere.model.I_M_Product;
import org.compiere.util.TimeUtil;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

/**
 * A bunch of services needed to support the loading of a given PickingJob.
 *
 * @implNote Please don't instantiate it in Repositories.
 */
public class DefaultPickingJobLoaderSupportingServices implements PickingJobLoaderSupportingServices
{
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	private final IOrderBL orderBL = Services.get(IOrderBL.class);
	private final IBPartnerBL bpartnerBL;
	private final PickingJobSlotService pickingSlotService;
	private final IProductBL productBL = Services.get(IProductBL.class);
	private final IWarehouseBL warehouseBL = Services.get(IWarehouseBL.class);
	private final ILockManager lockManager = Services.get(ILockManager.class);
	private final IHUPIItemProductBL huPIItemProductBL = Services.get(IHUPIItemProductBL.class);
	private final HUQRCodesService huQRCodeService;

	private final HashMap<OrderId, String> salesOrderDocumentNosCache = new HashMap<>();
	private final HashMap<BPartnerId, String> bpartnerNamesCache = new HashMap<>();
	private final HashMap<PickingSlotId, PickingSlotIdAndCaption> pickingSlotIdAndCaptionsCache = new HashMap<>();
	private final HashMap<ProductId, ProductInfo> productInfoCache = new HashMap<>();
	private final HashMap<LocatorId, String> locatorNamesCache = new HashMap<>();

	public DefaultPickingJobLoaderSupportingServices(
			@NonNull final IBPartnerBL bpartnerBL,
			@NonNull final PickingJobSlotService pickingSlotService,
			@NonNull final HUQRCodesService huQRCodeService)
	{
		this.bpartnerBL = bpartnerBL;
		this.pickingSlotService = pickingSlotService;
		this.huQRCodeService = huQRCodeService;
	}

	@Override
	public void warmUpCachesFrom(@NonNull final PackageableList items)
	{
		items.forEach(this::loadCacheFrom);
	}

	private void loadCacheFrom(@NonNull final Packageable packageable)
	{
		if (packageable.getSalesOrderId() != null)
		{
			salesOrderDocumentNosCache.put(packageable.getSalesOrderId(), packageable.getSalesOrderDocumentNo());
		}
	}

	@Override
	public void warmUpSalesOrderDocumentNosCache(@NonNull Collection<OrderId> orderIds)
	{
		CollectionUtils.getAllOrLoad(salesOrderDocumentNosCache, orderIds, orderBL::getDocumentNosByIds);
	}

	@Override
	public String getSalesOrderDocumentNo(@NonNull final OrderId salesOrderId)
	{
		return salesOrderDocumentNosCache.computeIfAbsent(salesOrderId, orderBL::getDocumentNoById);
	}

	@Override
	public void warmUpBPartnerNamesCache(@NonNull Set<BPartnerId> bpartnerIds)
	{
		CollectionUtils.getAllOrLoad(bpartnerNamesCache, bpartnerIds, bpartnerBL::getBPartnerNames);
	}

	@Override
	public String getBPartnerName(@NonNull final BPartnerId bpartnerId)
	{
		return bpartnerNamesCache.computeIfAbsent(bpartnerId, bpartnerBL::getBPartnerName);
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
	public ITranslatableString getProductName(@NonNull final ProductId productId)
	{
		return getProductInfo(productId).getName();
	}

	private ProductInfo getProductInfo(@NonNull final ProductId productId)
	{
		return productInfoCache.computeIfAbsent(productId, this::retrieveProductInfo);
	}

	private ProductInfo retrieveProductInfo(@NonNull final ProductId productId)
	{
		final I_M_Product product = productBL.getById(productId);
		return ProductInfo.builder()
				.productId(productId)
				.productNo(product.getValue())
				.name(InterfaceWrapperHelper.getModelTranslationMap(product).getColumnTrl(I_M_Product.COLUMNNAME_Name, product.getName()))
				.build();

	}

	@Override
	public HUPIItemProduct getPackingInfo(@NonNull final HUPIItemProductId huPIItemProductId)
	{
		return huPIItemProductBL.getById(huPIItemProductId);
	}

	@Override
	public String getLocatorName(@NonNull final LocatorId locatorId)
	{
		return locatorNamesCache.computeIfAbsent(locatorId, warehouseBL::getLocatorNameById);
	}

	@Override
	public HUQRCode getQRCodeByHUId(final HuId huId)
	{
		return huQRCodeService.getQRCodeByHuId(huId);
	}

	@Override
	public SetMultimap<ShipmentScheduleId, ExistingLockInfo> getLocks(final Collection<ShipmentScheduleId> shipmentScheduleIds)
	{
		return CollectionUtils.mapKeys(
				lockManager.getLockInfosByRecordIds(ShipmentScheduleId.toTableRecordReferenceSet(shipmentScheduleIds)),
				recordRef -> recordRef.getIdAssumingTableName(I_M_ShipmentSchedule.Table_Name, ShipmentScheduleId::ofRepoId)
		);
	}

	//
	//
	//

	@Value
	@Builder
	private static class ProductInfo
	{
		@NonNull ProductId productId;
		@NonNull String productNo;
		@NonNull ITranslatableString name;
	}
}
