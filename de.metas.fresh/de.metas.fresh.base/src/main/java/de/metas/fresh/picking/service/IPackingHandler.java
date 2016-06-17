package de.metas.fresh.picking.service;

import de.metas.adempiere.form.IPackingItem;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;

/**
 * To be used with {@link IPackingService} methods to guide their job along the road.
 * 
 * @author tsa
 *
 */
public interface IPackingHandler
{
	/**
	 * @param shipmentSchedule
	 * @return true if we are allowed to pack given <code>shipmentSchedule</code>
	 */
	boolean isPackingAllowedForShipmentSchedule(I_M_ShipmentSchedule shipmentSchedule);

	/**
	 * Called when item was packed
	 * 
	 * @param itemPacked item that was packed
	 */
	void itemPacked(final IPackingItem itemPacked);
}
