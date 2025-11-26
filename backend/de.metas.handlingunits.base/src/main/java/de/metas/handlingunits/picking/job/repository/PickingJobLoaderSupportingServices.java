package de.metas.handlingunits.picking.job.repository;

import de.metas.bpartner.BPartnerId;
import de.metas.gs1.GS1ProductCodes;
import de.metas.handlingunits.HUPIItemProduct;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.HuPackingInstructionsId;
import de.metas.handlingunits.picking.config.mobileui.PickingJobOptions;
import de.metas.handlingunits.picking.job.model.ScheduledPackageableList;
import de.metas.handlingunits.picking.job.model.ScheduledPackageableLocks;
import de.metas.picking.api.ShipmentScheduleAndJobScheduleIdSet;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import de.metas.i18n.ITranslatableString;
import de.metas.order.OrderAndLineId;
import de.metas.order.OrderId;
import de.metas.organization.OrgId;
import de.metas.picking.api.PickingSlotId;
import de.metas.picking.api.PickingSlotIdAndCaption;
import de.metas.product.ProductCategoryId;
import de.metas.product.ProductId;
import lombok.NonNull;
import org.adempiere.warehouse.LocatorId;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;

/**
 * Services Facade used to load Picking Jobs.
 */
public interface PickingJobLoaderSupportingServices
{
	PickingJobOptions getPickingJobOptions(@Nullable final BPartnerId customerId);

	void warmUpCachesFrom(@NonNull ScheduledPackageableList items);

	void warmUpSalesOrderDocumentNosCache(@NonNull Collection<OrderId> orderIds);

	String getSalesOrderDocumentNo(@NonNull OrderId salesOrderId);

	void warmUpBPartnerNamesCache(@NonNull Set<BPartnerId> bpartnerIds);

	String getBPartnerName(@NonNull BPartnerId bpartnerId);

	ZonedDateTime toZonedDateTime(@NonNull java.sql.Timestamp timestamp, @NonNull OrgId orgId);

	PickingSlotIdAndCaption getPickingSlotIdAndCaption(@NonNull PickingSlotId pickingSlotId);

	String getProductNo(@NonNull ProductId productId);

	Optional<GS1ProductCodes> getGS1ProductCodes(@NonNull ProductId productId, @Nullable BPartnerId customerId);

	ProductCategoryId getProductCategoryId(@NonNull ProductId productId);

	int getSalesOrderLineSeqNo(@NonNull OrderAndLineId orderAndLineId);

	ITranslatableString getProductName(@NonNull ProductId productId);

	HUPIItemProduct getPackingInfo(@NonNull HUPIItemProductId huPIItemProductId);

	String getPICaption(@NonNull HuPackingInstructionsId piId);

	String getLocatorName(@NonNull LocatorId locatorId);

	HUQRCode getQRCodeByHUId(HuId huId);

	ScheduledPackageableLocks getLocks(ShipmentScheduleAndJobScheduleIdSet scheduleIds);
}
