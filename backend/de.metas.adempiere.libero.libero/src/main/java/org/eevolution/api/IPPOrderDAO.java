package org.eevolution.api;

/*
 * #%L
 * de.metas.adempiere.libero.libero
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


import java.util.List;
import java.util.Properties;

import org.adempiere.util.ISingletonService;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_Warehouse;
import org.eevolution.model.I_PP_Order;

public interface IPPOrderDAO extends ISingletonService
{
	/**
	 * Retrieve all manufacturing orders which are linked to given order line AND they have the product from order line.
	 * 
	 * @param line
	 * @return
	 */
	List<I_PP_Order> retrieveAllForOrderLine(I_C_OrderLine line);

	/**
	 * Retrieve Make-to-Order generated manufacturing order which is linked to given order line AND have the product from order line.
	 * 
	 * @param line
	 * @return make to order MO or null
	 */
	I_PP_Order retrieveMakeToOrderForOrderLine(I_C_OrderLine line);

	/**
	 * Retrieve Make-to-Order generated manufacturing orders which are linked to inout's sales order line.
	 * 
	 * @param inout
	 * @return
	 */
	List<I_PP_Order> retrieveMakeToOrderForInOut(I_M_InOut inout);

	/**
	 * Gets released manufacturing orders based on {@link I_M_Warehouse}s.
	 * 
	 * @param warehouseId
	 * @return manufacturing orders
	 */
	List<I_PP_Order> retrieveReleasedManufacturingOrdersForWarehouse(Properties ctx, int warehouseId);
	
	/**
	 * @param orderLineId
	 * @return PP_Order_ID or -1 if not found.
	 */
	int retrievePPOrderIdByOrderLineId(final int orderLineId);
}
