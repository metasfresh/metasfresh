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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.List;

import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.util.ISingletonService;
import org.compiere.model.I_M_Forecast;
import org.compiere.model.I_M_MovementLine;
import org.eevolution.model.I_DD_Order;
import org.eevolution.model.I_DD_OrderLine;
import org.eevolution.model.I_DD_OrderLine_Alternative;
import org.eevolution.model.I_PP_Order;

public interface IDDOrderDAO extends ISingletonService
{

	/**
	 *
	 * @param order
	 * @return active {@link I_DD_OrderLine}s
	 */
	List<I_DD_OrderLine> retrieveLines(I_DD_Order order);

	/**
	 * Gets a filter which is matching only those DD Orders that have a line which has given <code>targetWarehouseId</code> as target/destination warehouse.
	 *
	 * @param targetWarehouseId
	 * @return filter
	 */
	IQueryFilter<I_DD_Order> getDDOrdersForTargetWarehouseQueryFilter(int targetWarehouseId);

	List<I_DD_OrderLine_Alternative> retrieveAllAlternatives(I_DD_OrderLine ddOrderLine);

	/**
	 * Gets all movement lines which are linked to given <code>ddOrderLine</code>.
	 *
	 * This method will NOT retrieve the movement lines which are linked to an {@link I_DD_OrderLine_Alternative} of given DD Order Line.
	 *
	 * @param ddOrderLine
	 * @param movementLineClass
	 * @return movement lines linked to given DD Order Line
	 */
	<T extends I_M_MovementLine> List<T> retriveMovementLines(I_DD_OrderLine ddOrderLine, Class<T> movementLineClass);

	/**
	 * Retrieve Forward DD_OrderLines of this DD_Order.
	 *
	 * i.e.
	 * <ul>
	 * <li>those DD_OrderLines who triggered this DD_Order to be created
	 * <li>demanding DD Order Lines
	 * </ul>
	 *
	 * @param ddOrder
	 * @return forward {@link I_DD_OrderLine}s
	 */
	IQueryBuilder<I_DD_OrderLine> retrieveForwardDDOrderLinesQuery(I_DD_Order ddOrder);

	/**
	 * Retrieve Backward DD_OrderLines of this DD_Order.
	 *
	 * i.e.
	 * <ul>
	 * <li>those DD_OrderLines which were created to balance the supplies for this DD_Order
	 * <li>supply DD_OrderLInes
	 * </ul>
	 *
	 * @param ddOrder
	 * @return backward {@link I_DD_OrderLine}s
	 */
	IQueryBuilder<I_DD_OrderLine> retrieveBackwardDDOrderLinesQuery(I_DD_Order ddOrder);

	IQueryBuilder<I_DD_OrderLine> retrieveBackwardDDOrderLinesQuery(I_M_Forecast forecast);

	IQueryBuilder<I_DD_OrderLine> retrieveForwardDDOrderLinesQuery(I_PP_Order ppOrder);

}
