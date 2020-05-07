package de.metas.tourplanning.api.impl;

import java.time.ZonedDateTime;

import de.metas.bpartner.BPartnerLocationId;
import de.metas.inoutcandidate.api.IShipmentScheduleEffectiveBL;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.tourplanning.api.IDeliveryDayAllocable;
import de.metas.tourplanning.api.IShipmentScheduleDeliveryDayBL;
import de.metas.util.Check;
import de.metas.util.Services;

/**
 * Wraps a {@link I_M_ShipmentSchedule} and behave like {@link IDeliveryDayAllocable}
 * 
 * @author tsa
 *
 */
/* package */class ShipmentScheduleDeliveryDayAllocable implements IDeliveryDayAllocable
{
	//
	// Services
	private final IShipmentScheduleEffectiveBL shipmentScheduleEffectiveBL = Services.get(IShipmentScheduleEffectiveBL.class);
	private final IShipmentScheduleDeliveryDayBL shipmentScheduleDeliveryDayBL = Services.get(IShipmentScheduleDeliveryDayBL.class);

	private final I_M_ShipmentSchedule sched;

	public ShipmentScheduleDeliveryDayAllocable(final I_M_ShipmentSchedule sched)
	{
		Check.assumeNotNull(sched, "sched not null");
		this.sched = sched;
	}

	public I_M_ShipmentSchedule getM_ShipmentSchedule()
	{
		return sched;
	}

	@Override
	public String getTableName()
	{
		return I_M_ShipmentSchedule.Table_Name;
	}

	@Override
	public int getRecord_ID()
	{
		return sched.getM_ShipmentSchedule_ID();
	}

	@Override
	public ZonedDateTime getDeliveryDate()
	{
		return shipmentScheduleDeliveryDayBL.getDeliveryDateCurrent(sched);
	}

	@Override
	public BPartnerLocationId getBPartnerLocationId()
	{
		return shipmentScheduleEffectiveBL.getBPartnerLocationId(sched);
	}

	@Override
	public int getM_Product_ID()
	{
		return sched.getM_Product_ID();
	}

	@Override
	public boolean isToBeFetched()
	{
		// Shipment Schedules are about sending materials to customer
		return false;
	}

	@Override
	public int getAD_Org_ID()
	{
		return sched.getAD_Org_ID();
	}

}
