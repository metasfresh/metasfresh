package de.metas.handlingunits.picking.job.repository;

import com.google.common.collect.SetMultimap;
import de.metas.bpartner.BPartnerId;
import de.metas.handlingunits.HUPIItemProduct;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import de.metas.i18n.ITranslatableString;
import de.metas.inout.ShipmentScheduleId;
import de.metas.lock.spi.ExistingLockInfo;
import de.metas.order.OrderId;
import de.metas.organization.OrgId;
import de.metas.picking.api.PackageableList;
import de.metas.picking.api.PickingSlotId;
import de.metas.picking.api.PickingSlotIdAndCaption;
import de.metas.product.ProductId;
import lombok.NonNull;
import org.adempiere.warehouse.LocatorId;

import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.Set;

/**
 * Services Facade used to load Picking Jobs.
 */
public interface PickingJobLoaderSupportingServices
{
	void warmUpCachesFrom(@NonNull PackageableList items);

	void warmUpSalesOrderDocumentNosCache(@NonNull Collection<OrderId> orderIds);

	String getSalesOrderDocumentNo(@NonNull OrderId salesOrderId);

	void warmUpBPartnerNamesCache(@NonNull Set<BPartnerId> bpartnerIds);

	String getBPartnerName(@NonNull BPartnerId bpartnerId);

	ZonedDateTime toZonedDateTime(@NonNull java.sql.Timestamp timestamp, @NonNull OrgId orgId);

	PickingSlotIdAndCaption getPickingSlotIdAndCaption(@NonNull PickingSlotId pickingSlotId);

	String getProductNo(@NonNull ProductId productId);

	ITranslatableString getProductName(@NonNull ProductId productId);

	HUPIItemProduct getPackingInfo(@NonNull HUPIItemProductId huPIItemProductId);

	String getLocatorName(@NonNull LocatorId locatorId);

	HUQRCode getQRCodeByHUId(HuId huId);

	SetMultimap<ShipmentScheduleId, ExistingLockInfo> getLocks(Collection<ShipmentScheduleId> shipmentScheduleIds);
}
