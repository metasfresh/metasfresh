package de.metas.handlingunits.picking.job.repository;

import com.google.common.collect.ImmutableSetMultimap;
import com.google.common.collect.SetMultimap;
import de.metas.bpartner.BPartnerId;
import de.metas.handlingunits.HUPIItemProduct;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.HuPackingInstructionsItemId;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStrings;
import de.metas.inout.ShipmentScheduleId;
import de.metas.lock.spi.ExistingLockInfo;
import de.metas.order.OrderId;
import de.metas.organization.OrgId;
import de.metas.picking.api.PackageableList;
import de.metas.picking.api.PickingSlotId;
import de.metas.picking.api.PickingSlotIdAndCaption;
import de.metas.product.ProductId;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.warehouse.LocatorId;

import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

public class MockedPickingJobLoaderSupportingServices implements PickingJobLoaderSupportingServices
{
	public static final ZoneId ZONE_ID = ZoneId.of("Europe/London");
	private final HashMap<HuId, HUQRCode> qrCodes = new HashMap<>();

	@Override
	public void warmUpCachesFrom(@NonNull final PackageableList items)
	{
		// do nothing
	}

	@Override
	public void warmUpSalesOrderDocumentNosCache(@NonNull final Collection<OrderId> orderIds)
	{
		// do nothing
	}

	@Override
	public String getSalesOrderDocumentNo(@NonNull final OrderId salesOrderId)
	{
		return "docno-" + salesOrderId.getRepoId();
	}

	@Override
	public void warmUpBPartnerNamesCache(@NonNull final Set<BPartnerId> bpartnerIds)
	{
		// do nothing
	}

	@Override
	public String getBPartnerName(@NonNull final BPartnerId bpartnerId)
	{
		return "bpname-" + bpartnerId.getRepoId();
	}

	@Override
	public ZonedDateTime toZonedDateTime(@NonNull final Timestamp timestamp, @NonNull final OrgId orgId)
	{
		return timestamp.toInstant().atZone(ZONE_ID);
	}

	@Override
	public PickingSlotIdAndCaption getPickingSlotIdAndCaption(@NonNull final PickingSlotId pickingSlotId)
	{
		return PickingSlotIdAndCaption.of(pickingSlotId, "caption-" + pickingSlotId.getRepoId());
	}

	@Override
	public String getProductNo(@NonNull final ProductId productId)
	{
		return "productNo-" + productId.getRepoId();
	}

	@Override
	public ITranslatableString getProductName(@NonNull final ProductId productId)
	{
		return TranslatableStrings.anyLanguage("productName-" + productId.getRepoId());
	}

	@Override
	public HUPIItemProduct getPackingInfo(@NonNull final HUPIItemProductId huPIItemProductId)
	{
		return HUPIItemProduct.builder()
				.id(huPIItemProductId)
				.name(TranslatableStrings.anyLanguage("infinite-" + huPIItemProductId.getRepoId()))
				.piItemId(HuPackingInstructionsItemId.ofRepoId(123))
				.build();
	}

	@Override
	public String getLocatorName(@NonNull final LocatorId locatorId)
	{
		return "locatorName-" + locatorId.getRepoId();
	}

	@Override
	public HUQRCode getQRCodeByHUId(final HuId huId)
	{
		final HUQRCode qrCode = qrCodes.get(huId);
		if (qrCode == null)
		{
			throw new AdempiereException("QR code not mocked for " + huId + ". Currently registered mocked QR codes are: " + qrCodes);
		}
		return qrCode;
	}

	@Override
	public SetMultimap<ShipmentScheduleId, ExistingLockInfo> getLocks(final Collection<ShipmentScheduleId> shipmentScheduleIds) {return ImmutableSetMultimap.of();}

	public void mockQRCode(@NonNull final HuId huId, @NonNull final HUQRCode qrCode)
	{
		qrCodes.put(huId, qrCode);
	}
}
