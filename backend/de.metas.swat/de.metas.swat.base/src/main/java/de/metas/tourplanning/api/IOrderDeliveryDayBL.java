package de.metas.tourplanning.api;

import org.compiere.model.I_C_Order;

import de.metas.tourplanning.model.I_M_DeliveryDay;
import de.metas.util.ISingletonService;

/**
 * Handles the relation between {@link I_C_Order} and Tour Planning module.
 * 
 * @author tsa
 *
 */
public interface IOrderDeliveryDayBL extends ISingletonService
{
	/**
	 * Set Preparation Date from matching {@link I_M_DeliveryDay} if possible.
	 * 
	 * Preparation Date won't be set if the value of fields on which depends are not set or if the order is already processed.
	 * 
	 * @param order
	 * @param fallbackToDatePromised if the computed preparationDate is already in the past, then let this parameter decide if the PreparationDate remains empty or is filled with the given
	 *            <code>order</code>'s <code>DatePromised</code> value (task 08931).
	 * @return true if preparation date was set
	 */
	boolean setPreparationDateAndTour(I_C_Order order, boolean fallbackToDatePromised);

}
