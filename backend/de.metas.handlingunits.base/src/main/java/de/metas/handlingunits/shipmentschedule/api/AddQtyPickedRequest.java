package de.metas.handlingunits.shipmentschedule.api;

import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.picking.api.ShipmentScheduleAndJobScheduleId;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.quantity.StockQtyAndUOMQty;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Value
@Builder
public
class AddQtyPickedRequest
{
	@NonNull ShipmentScheduleAndJobScheduleId scheduleId;
	@Nullable I_M_ShipmentSchedule cachedShipmentSchedule;
	@NonNull StockQtyAndUOMQty qtyPicked;
	@NonNull I_M_HU tuOrVHU;
	@NonNull IHUContext huContext;
	/**
	 * true if the HU was picked on the fly for the shipment process
	 */
	boolean anonymousHuPickedOnTheFly;

	public static class AddQtyPickedRequestBuilder
	{
		public AddQtyPickedRequestBuilder shipmentSchedule(@NonNull final I_M_ShipmentSchedule shipmentSchedule)
		{
			final ShipmentScheduleId shipmentScheduleId = ShipmentScheduleId.ofRepoId(shipmentSchedule.getM_ShipmentSchedule_ID());
			scheduleId(ShipmentScheduleAndJobScheduleId.ofShipmentScheduleId(shipmentScheduleId));
			cachedShipmentSchedule(shipmentSchedule);
			return this;
		}
	}
}
