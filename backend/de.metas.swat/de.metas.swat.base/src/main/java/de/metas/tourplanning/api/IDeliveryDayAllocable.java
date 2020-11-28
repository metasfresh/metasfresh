package de.metas.tourplanning.api;

import java.time.ZonedDateTime;

import de.metas.bpartner.BPartnerLocationId;
import de.metas.tourplanning.model.I_M_DeliveryDay;

/**
 * A document line which is allocable to a {@link I_M_DeliveryDay}.
 * 
 * Implementations of this interface will handle specific cases (e.g. shipment schedules, receipt schedules etc).
 * 
 * @author tsa
 *
 */
public interface IDeliveryDayAllocable
{
	String getTableName();

	int getRecord_ID();

	int getAD_Org_ID();

	ZonedDateTime getDeliveryDate();

	BPartnerLocationId getBPartnerLocationId();

	int getM_Product_ID();

	/**
	 * 
	 * @return <ul>
	 *         <li>true if materials needs to be fetched from vendor (i.e. document has IsSOTrx=false)
	 *         <li>false if materials needs to be delivered to customer (i.e. document has IsSOTrx=true)
	 *         </ul>
	 */
	boolean isToBeFetched();
}
