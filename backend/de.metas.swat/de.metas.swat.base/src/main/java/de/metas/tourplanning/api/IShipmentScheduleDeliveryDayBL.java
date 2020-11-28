package de.metas.tourplanning.api;

import java.time.ZonedDateTime;

import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.tourplanning.model.I_M_DeliveryDay;
import de.metas.tourplanning.model.I_M_DeliveryDay_Alloc;
import de.metas.util.ISingletonService;

/**
 * Handles the relation between {@link de.metas.tourplanning.model.I_M_ShipmentSchedule} and {@link I_M_DeliveryDay}.
 * 
 * @author tsa
 *
 */
public interface IShipmentScheduleDeliveryDayBL extends ISingletonService
{
	/**
	 * Gets current delivery date.
	 * 
	 * If is not set directly in shipment schedule, it will try to fetch it from underlying documents (i.e. Order Line / Order).
	 * 
	 * @param sched
	 * @return delivery date; never null
	 */
	ZonedDateTime getDeliveryDateCurrent(I_M_ShipmentSchedule sched);

	/**
	 * <ul>
	 * <li>create/update shipment schedule to {@link I_M_DeliveryDay} allocation (i.e. {@link I_M_DeliveryDay_Alloc})
	 * 
	 * @param sched
	 */
	void updateDeliveryDayInfo(I_M_ShipmentSchedule sched);

	/**
	 * 
	 * @param sched
	 * @return shipment schedule wrapped as {@link IDeliveryDayAllocable}; never return null
	 */
	IDeliveryDayAllocable asDeliveryDayAllocable(I_M_ShipmentSchedule sched);

	/**
	 * Gets underlying {@link I_M_ShipmentSchedule}.
	 * 
	 * @param deliveryDayAllocable
	 * @param shipment schedule's interface class to be used when returning
	 * @return underlying {@link I_M_ShipmentSchedule} or null if <code>deliveryDayAllocable</code> or not applicable.
	 */
	<T extends I_M_ShipmentSchedule> T getShipmentScheduleOrNull(IDeliveryDayAllocable deliveryDayAllocable, final Class<T> modelClass);
}
