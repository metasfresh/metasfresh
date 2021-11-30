package de.metas.handlingunits.picking.job.repository;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerId;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStrings;
import de.metas.order.OrderId;
import de.metas.organization.OrgId;
import de.metas.picking.api.Packageable;
import de.metas.picking.api.PickingSlotId;
import de.metas.picking.api.PickingSlotIdAndCaption;
import de.metas.product.ProductId;
import lombok.NonNull;
import org.adempiere.warehouse.LocatorId;

import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class MockedPickingJobLoaderSupportingServices implements PickingJobLoaderSupportingServices
{
	public static final ZoneId ZONE_ID = ZoneId.of("Europe/London");

	@Override
	public void warmUpCachesFrom(@NonNull final ImmutableList<Packageable> items)
	{
		// do nothing
	}

	@Override
	public String getSalesOrderDocumentNo(@NonNull final OrderId salesOrderId)
	{
		return "docno-" + salesOrderId.getRepoId();
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
	public ITranslatableString getProductName(@NonNull final ProductId productId)
	{
		return TranslatableStrings.anyLanguage("productName-" + productId.getRepoId());
	}

	@Override
	public String getLocatorName(@NonNull final LocatorId locatorId)
	{
		return "locatorName-" + locatorId.getRepoId();
	}
}
