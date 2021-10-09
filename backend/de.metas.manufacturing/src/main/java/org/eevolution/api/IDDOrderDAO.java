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

import de.metas.util.ISingletonService;
import org.adempiere.ad.dao.IQueryBuilder;
import org.compiere.model.I_M_Forecast;
import org.eevolution.model.I_DD_Order;
import org.eevolution.model.I_DD_OrderLine;
import org.eevolution.model.I_DD_OrderLine_Alternative;
import org.eevolution.model.I_DD_OrderLine_Or_Alternative;

import java.util.List;

public interface IDDOrderDAO extends ISingletonService
{

	I_DD_Order getById(int ddOrderId);

	/**
	 * @return active {@link I_DD_OrderLine}s
	 */
	List<I_DD_OrderLine> retrieveLines(I_DD_Order order);

	List<I_DD_OrderLine_Alternative> retrieveAllAlternatives(I_DD_OrderLine ddOrderLine);

	/**
	 * Retrieve Forward DD_OrderLines of this DD_Order.
	 *
	 * i.e.
	 * <ul>
	 * <li>those DD_OrderLines who triggered this DD_Order to be created
	 * <li>demanding DD Order Lines
	 * </ul>
	 *
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
	 * @return backward {@link I_DD_OrderLine}s
	 */
	IQueryBuilder<I_DD_OrderLine> retrieveBackwardDDOrderLinesQuery(I_DD_Order ddOrder);

	IQueryBuilder<I_DD_OrderLine> retrieveBackwardDDOrderLinesQuery(I_M_Forecast forecast);

	void save(I_DD_Order ddOrder);

	void save(I_DD_OrderLine ddOrderline);

	void save(I_DD_OrderLine_Alternative ddOrderLineAlternative);

	void save(I_DD_OrderLine_Or_Alternative ddOrderLineOrAlternative);

	I_DD_OrderLine getLineById(final DDOrderLineId ddOrderLineID);
}
