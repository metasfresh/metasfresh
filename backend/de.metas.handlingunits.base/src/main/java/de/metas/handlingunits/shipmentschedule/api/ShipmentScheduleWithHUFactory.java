package de.metas.handlingunits.shipmentschedule.api;

import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.model.I_M_ShipmentSchedule_QtyPicked;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.inoutcandidate.split.ShipmentScheduleSplit;
import de.metas.quantity.Quantity;
import de.metas.quantity.StockQtyAndUOMQty;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

import javax.annotation.Nullable;

@Builder
public class ShipmentScheduleWithHUFactory
{
	@NonNull private final ShipmentScheduleWithHUSupportingServices supportingServices;
	@Getter @NonNull private final IHUContext huContext;

	public ShipmentScheduleWithHU ofQtyPickedRecord(@NonNull final I_M_ShipmentSchedule_QtyPicked shipmentScheduleQtyPicked, @Nullable final ShipmentScheduleSplit split)
	{
		return ofQtyPickedRecord(
				shipmentScheduleQtyPicked,
				M_ShipmentSchedule_QuantityTypeToUse.TYPE_QTY_TO_DELIVER,/* just because that's how it was and we don't have great test coverage */
				split);
	}

	public ShipmentScheduleWithHU ofQtyPickedRecord(@NonNull final I_M_ShipmentSchedule_QtyPicked shipmentScheduleQtyPicked)
	{
		return ofQtyPickedRecord(
				shipmentScheduleQtyPicked,
				M_ShipmentSchedule_QuantityTypeToUse.TYPE_QTY_TO_DELIVER/* just because that's how it was and we don't have great test coverage */);
	}

	public ShipmentScheduleWithHU ofQtyPickedRecord(
			@NonNull final I_M_ShipmentSchedule_QtyPicked shipmentScheduleQtyPicked,
			@NonNull final M_ShipmentSchedule_QuantityTypeToUse qtyTypeToUse)
	{
		return ofQtyPickedRecord(shipmentScheduleQtyPicked,qtyTypeToUse, null );
	}

	public ShipmentScheduleWithHU ofQtyPickedRecord(
			@NonNull final I_M_ShipmentSchedule_QtyPicked shipmentScheduleQtyPicked,
			@NonNull final M_ShipmentSchedule_QuantityTypeToUse qtyTypeToUse,
			@Nullable final ShipmentScheduleSplit split)
	{
		return new ShipmentScheduleWithHU(
				supportingServices,
				huContext,
				shipmentScheduleQtyPicked,
				qtyTypeToUse,
				split);
	}

	/**
	 * Create an "empty" instance with no HUs inside. Used if a shipment without HU allocation has to be created.
	 */
	public ShipmentScheduleWithHU ofShipmentScheduleWithoutHu(
			@NonNull final I_M_ShipmentSchedule shipmentSchedule,
			@NonNull final StockQtyAndUOMQty stockQtyAndCatchQty,
			@NonNull final M_ShipmentSchedule_QuantityTypeToUse qtyTypeToUse)
	{
		return new ShipmentScheduleWithHU(supportingServices, huContext, shipmentSchedule, stockQtyAndCatchQty, qtyTypeToUse);
	}

	public ShipmentScheduleWithHU ofSplit(
			@NonNull final I_M_ShipmentSchedule shipmentSchedule,
			@NonNull final ShipmentScheduleSplit split,
			@NonNull final Quantity qtyToAllocate)
	{
		return new ShipmentScheduleWithHU(supportingServices, huContext, shipmentSchedule, split, qtyToAllocate);
	}

}
