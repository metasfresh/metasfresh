/**
 *
 */
package de.metas.handlingunits.client.terminal.pporder.api;

/*
 * #%L
 * de.metas.handlingunits.client
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

import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_M_Warehouse;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.I_PP_Order_BOMLine;

import de.metas.handlingunits.IHUQueryBuilder;
import de.metas.util.ISingletonService;

public interface IHUIssueFiltering extends ISingletonService
{
	List<I_M_Warehouse> retrieveWarehouse();

	/**
	 * Gets manufacturing orders based on {@link I_M_Warehouse}s
	 *
	 * @param warehouse
	 * @return
	 */
	List<I_PP_Order> getManufacturingOrders(WarehouseId warehouseId);

	IHUQueryBuilder getHUsForIssueQuery(I_PP_Order ppOrder, List<I_PP_Order_BOMLine> orderBOMLines, WarehouseId warehouseId);

	List<I_PP_Order_BOMLine> getOrderBOMLines(I_PP_Order order);

}
