package de.metas.handlingunits.order.api;

/*
 * #%L
 * de.metas.handlingunits.base
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
import java.util.Properties;

import org.adempiere.util.ISingletonService;

import de.metas.handlingunits.model.I_C_Order;
import de.metas.interfaces.I_C_OrderLine;

/**
 * Order and Handling Units integration BL
 *
 * @author tsa
 *
 */
public interface IHUOrderBL extends ISingletonService
{
	/**
	 * Updates the HU relevant fields in order line. The column name is optional, can be used to explicitly state a column has been changed (for using the method in invoice).
	 *
	 * @param ol
	 * @param columnName
	 */
	void updateOrderLine(I_C_OrderLine ol, String columnName);

	/**
	 * Update the product quantity (Qty CU) if the M_HU_PI_Item_Produc's qty (Qty TU) was changed
	 *
	 * @param order
	 * @return true if the qty CU was modified, false otherwise
	 */
	boolean updateQtyCU(I_C_Order order);

	/**
	 * Update the M_HU_PI_Item_Product quantity(Qty TU) if the M_Product's qty (Qty CU) was changed
	 *
	 * @param order
	 * @return true if the qty TU was modified, false otherwise
	 */
	boolean updateQtyTU(I_C_Order order);

	/**
	 * Update quantities when the M_HU_PI_Item_Product, Menge TU or Menge CU changes
	 *
	 * @param order
	 * @param columnname
	 * @param modifying
	 */

	void updateQtys(I_C_Order order, String columnname);

	/**
	 * Check if the M_Product of an order in contained in any Transportation Unit
	 *
	 * @param order
	 * @return
	 */
	boolean hasTUs(I_C_Order order);

	/**
	 * Check if the M_Product of an order in contained in any Transportation Unit of the specified partner (or of no partner), that is valid in the specified date
	 *
	 * @param ctx
	 * @param bpartnerId
	 * @param productId
	 * @param date
	 * @return
	 */
	boolean hasTUs(Properties ctx, int bpartnerId, int productId, Date date);
}
