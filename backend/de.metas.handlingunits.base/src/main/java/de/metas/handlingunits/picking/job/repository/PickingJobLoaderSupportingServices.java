package de.metas.handlingunits.picking.job.repository;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerId;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import de.metas.i18n.ITranslatableString;
import de.metas.order.OrderId;
import de.metas.organization.OrgId;
import de.metas.picking.api.Packageable;
import de.metas.picking.api.PickingSlotId;
import de.metas.picking.api.PickingSlotIdAndCaption;
import de.metas.product.ProductId;
import lombok.NonNull;
import org.adempiere.warehouse.LocatorId;

import java.time.ZonedDateTime;

/**
 * Services Facade used to load Picking Jobs.
 */
public interface PickingJobLoaderSupportingServices
{
	void warmUpCachesFrom(@NonNull ImmutableList<Packageable> items);

	String getSalesOrderDocumentNo(@NonNull OrderId salesOrderId);

	String getBPartnerName(@NonNull BPartnerId bpartnerId);

	ZonedDateTime toZonedDateTime(@NonNull java.sql.Timestamp timestamp, @NonNull OrgId orgId);

	PickingSlotIdAndCaption getPickingSlotIdAndCaption(@NonNull PickingSlotId pickingSlotId);

	ITranslatableString getProductName(@NonNull ProductId productId);

	String getLocatorName(@NonNull LocatorId locatorId);

	HUQRCode getQRCodeByHUId(HuId huId);
}
