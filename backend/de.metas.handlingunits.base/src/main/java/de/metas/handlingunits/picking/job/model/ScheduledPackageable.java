package de.metas.handlingunits.picking.job.model;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.ShipmentAllocationBestBeforePolicy;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.picking.job_schedule.model.PickingJobSchedule;
import de.metas.picking.api.PickingJobScheduleId;
import de.metas.picking.api.ShipmentScheduleAndJobScheduleId;
import de.metas.inout.ShipmentScheduleId;
import de.metas.order.OrderAndLineId;
import de.metas.order.OrderId;
import de.metas.organization.InstantAndOrgId;
import de.metas.organization.OrgId;
import de.metas.picking.api.Packageable;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.shipping.ShipperId;
import de.metas.uom.UomId;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.WarehouseTypeId;
import org.compiere.model.I_C_UOM;
import org.eevolution.api.PPOrderId;

import javax.annotation.Nullable;
import java.util.Optional;

@EqualsAndHashCode
@ToString
public class ScheduledPackageable
{
	@NonNull Packageable packageable;
	@Nullable PickingJobSchedule schedule;

	private ScheduledPackageable(@NonNull final Packageable packageable, @Nullable final PickingJobSchedule schedule)
	{
		if (schedule != null && !ShipmentScheduleId.equals(packageable.getShipmentScheduleId(), schedule.getShipmentScheduleId()))
		{
			throw new AdempiereException("M_ShipmentSchedule_ID not matching: " + packageable + ", " + schedule);
		}

		this.packageable = packageable;
		this.schedule = schedule;
	}

	public static ScheduledPackageable ofPackageable(@NonNull final Packageable packageable) {return new ScheduledPackageable(packageable, null);}

	public static ScheduledPackageable of(@NonNull final Packageable packageable, @NonNull PickingJobSchedule schedule) {return new ScheduledPackageable(packageable, schedule);}

	public @NonNull OrgId getOrgId() {return packageable.getOrgId();}

	public @NonNull ShipmentScheduleAndJobScheduleId getId() {return ShipmentScheduleAndJobScheduleId.of(getShipmentScheduleId(), getPickingJobScheduleId());}

	public @NonNull ShipmentScheduleId getShipmentScheduleId() {return packageable.getShipmentScheduleId();}

	public @Nullable PickingJobScheduleId getPickingJobScheduleId() {return schedule != null ? schedule.getId() : null;}

	public @NonNull BPartnerId getCustomerId() {return packageable.getCustomerId();}

	public @NonNull String getCustomerName() {return packageable.getCustomerName();}

	public @NonNull BPartnerLocationId getCustomerLocationId() {return packageable.getCustomerLocationId();}

	public BPartnerLocationId getHandoverLocationId() {return packageable.getHandoverLocationId();}

	public String getCustomerAddress() {return packageable.getCustomerAddress();}

	public @NonNull InstantAndOrgId getPreparationDate() {return packageable.getPreparationDate();}

	public @NonNull InstantAndOrgId getDeliveryDate() {return packageable.getDeliveryDate();}

	public @Nullable OrderId getSalesOrderId() {return packageable.getSalesOrderId();}

	public @Nullable String getSalesOrderDocumentNo() {return packageable.getSalesOrderDocumentNo();}

	public @Nullable OrderAndLineId getSalesOrderAndLineIdOrNull() {return packageable.getSalesOrderAndLineIdOrNull();}

	public @Nullable WarehouseTypeId getWarehouseTypeId() {return packageable.getWarehouseTypeId();}

	public boolean isPartiallyPickedOrDelivered()
	{
		return packageable.getQtyPickedPlanned().signum() != 0
				|| packageable.getQtyPickedNotDelivered().signum() != 0
				|| packageable.getQtyPickedAndDelivered().signum() != 0;
	}

	public @NonNull ProductId getProductId() {return packageable.getProductId();}

	public @NonNull String getProductName() {return packageable.getProductName();}

	public @NonNull I_C_UOM getUOM() {return getQtyToDeliver().getUOM();}

	public @NonNull Quantity getQtyToDeliver()
	{
		return schedule != null
				? schedule.getQtyToPick()
				: packageable.getQtyToDeliver();
	}

	public @NonNull HUPIItemProductId getPackToHUPIItemProductId() {return packageable.getPackToHUPIItemProductId();}

	public @NonNull Quantity getQtyToPick()
	{
		return schedule != null
				? schedule.getQtyToPick()
				: packageable.getQtyToPick();
	}

	public @Nullable UomId getCatchWeightUomId() {return packageable.getCatchWeightUomId();}

	public @Nullable PPOrderId getPickFromOrderId() {return packageable.getPickFromOrderId();}

	public @Nullable ShipperId getShipperId() {return packageable.getShipperId();}

	public @NonNull AttributeSetInstanceId getAsiId() {return packageable.getAsiId();}

	public Optional<ShipmentAllocationBestBeforePolicy> getBestBeforePolicy() {return packageable.getBestBeforePolicy();}

	public @NonNull WarehouseId getWarehouseId() {return packageable.getWarehouseId();}
}
