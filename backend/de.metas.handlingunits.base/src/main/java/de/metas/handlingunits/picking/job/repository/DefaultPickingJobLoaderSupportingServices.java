package de.metas.handlingunits.picking.job.repository;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.picking.job.service.PickingJobSlotService;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import de.metas.handlingunits.qrcodes.service.HUQRCodesService;
import de.metas.i18n.ITranslatableString;
import de.metas.order.IOrderBL;
import de.metas.order.OrderId;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.picking.api.Packageable;
import de.metas.picking.api.PickingSlotId;
import de.metas.picking.api.PickingSlotIdAndCaption;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.compiere.util.TimeUtil;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashMap;

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
	private final HUQRCodesService huQRCodeService;

	private final HashMap<OrderId, String> salesOrderDocumentNosCache = new HashMap<>();
	private final HashMap<BPartnerId, String> bpartnerNamesCache = new HashMap<>();
	private final HashMap<PickingSlotId, PickingSlotIdAndCaption> pickingSlotIdAndCaptionsCache = new HashMap<>();
	private final HashMap<ProductId, ITranslatableString> productNamesCache = new HashMap<>();
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
	public void warmUpCachesFrom(@NonNull final ImmutableList<Packageable> items)
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
	public String getSalesOrderDocumentNo(@NonNull final OrderId salesOrderId)
	{
		return salesOrderDocumentNosCache.computeIfAbsent(salesOrderId, orderBL::getDocumentNoById);
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
	public ITranslatableString getProductName(@NonNull final ProductId productId)
	{
		return productNamesCache.computeIfAbsent(productId, productBL::getProductNameTrl);
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
}
