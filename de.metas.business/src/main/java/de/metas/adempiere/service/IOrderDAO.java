package de.metas.adempiere.service;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.adempiere.util.ISingletonService;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_M_InOut;
import org.compiere.model.X_C_Order;

import de.metas.interfaces.I_C_OrderLine;

public interface IOrderDAO extends ISingletonService
{
	/**
	 * @param ctx
	 * @param bpartnerId
	 * @return true if given C_BPartner has any orders which are completed/closed.
	 */
	boolean hasCompletedOrders(Properties ctx, int bpartnerId);

	/**
	 * @param order
	 * @return order lines for given order
	 */
	List<I_C_OrderLine> retrieveOrderLines(I_C_Order order);

	/**
	 * Similar to {@link #retrieveOrderLines(I_C_Order)}, but allows to specify which {@link org.compiere.model.I_C_OrderLine} sub-type the result shall be in.
	 * 
	 * @param order
	 * @param clazz
	 * @return order lines for given order
	 */
	<T extends org.compiere.model.I_C_OrderLine> List<T> retrieveOrderLines(I_C_Order order, Class<T> clazz);

	/** @return all C_OrderLine_IDs for given order, including the inactive ones */
	List<Integer> retrieveAllOrderLineIds(I_C_Order order);

	/**
	 * @param order
	 * @param line
	 * @param clazz
	 */
	<T extends org.compiere.model.I_C_OrderLine> T retrieveOrderLine(I_C_Order order, int lineNo, Class<T> clazz);

	/**
	 * @param order
	 * @return {@link I_M_InOut}s for given order
	 */
	List<I_M_InOut> retrieveInOuts(I_C_Order order);

	/**
	 * @param order
	 * @return {@link I_M_InOut}s which have at least one matching between C_OrderLine and M_InOutLine for given order
	 */
	List<I_M_InOut> retrieveInOutsForMatchingOrderLines(I_C_Order order);

	/**
	 * @param order
	 * @return true if for the given order InOuts have been created
	 */
	boolean hasInOuts(I_C_Order order);

	/**
	 * Retrieves completed purchase orders with {@link X_C_Order#DELIVERYVIARULE_Pickup} for
	 * <ul>
	 * <li>a specific bPartner-location
	 * <li>and with DatePromised between <code>deliveryDateTime</code> and <code>DeliveryDateTimeMax</code> (inclusively)
	 * </ul>
	 * 
	 * @param bpLoc
	 * @param deliveryDateTime
	 * @param deliveryDateTimeMax
	 * @return purchase orders matching the given parameters
	 */
	List<I_C_Order> retrievePurchaseOrdersForPickup(I_C_BPartner_Location bpLoc, Date deliveryDateTime, Date deliveryDateTimeMax);
}
