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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.Date;
import java.util.Properties;
import java.util.function.Consumer;

import org.adempiere.util.ISingletonService;
import org.compiere.model.I_M_Product;

import de.metas.handlingunits.model.I_C_Order;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
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
	 * Updates the HU relevant fields in order line. This method updates the order line based on the following columns:
	 * <ul>
	 * <li>M_Product_ID</li>: if changed or if inconsistent with the given <code>ol</code>'s M_HU_PI_Item_Product's M_Product_ID
	 * <li>C_BPartner_ID</li>
	 * <li>DateOrdered</li>
	 * <li>M_HU_PI_Item_Product_ID</li>
	 * </ul>
	 *
	 * Notes:
	 * <ul>
	 * <li>It does nothing if <code>M_Product_ID</code> is not set or if <code>ManualQtyItemCapacity=Y</code></li>
	 * <li>It might also update the prices</li>
	 * <li>It also set/resets <code>M_HU_PI_Item_Product_ID</code> from <code>C_BPartner_ID</code>, <code>M_Product_ID</code> and <code>DateOrdered</code>,
	 * if either BPartner or Product were changed, or - for new ols, as of FRESH-351 - if <code>M_Product_ID</code> is inconsistent with
	 * the given <code>ol</code>'s <code>M_HU_PI_Item_Product<code>'s <code>M_Product_ID</code>.</li>
	 * </ul>
	 *
	 * @param ol
	 * @param columnName optional, may be <code>null</code>. Can be used to explicitly state a column has been changed (for using the method in invoice).
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

	/**
	 * Find best matching {@link I_M_HU_PI_Item_Product} for given <code>product</code> and <code>order</code>.
	 * 
	 * If an {@link I_M_HU_PI_Item_Product} was found, the consumer fill be called.
	 * 
	 * @param order
	 * @param product
	 * @param pipConsumer {@link I_M_HU_PI_Item_Product} consumer
	 */
	void findM_HU_PI_Item_Product(org.compiere.model.I_C_Order order, I_M_Product product, Consumer<I_M_HU_PI_Item_Product> pipConsumer);
}
