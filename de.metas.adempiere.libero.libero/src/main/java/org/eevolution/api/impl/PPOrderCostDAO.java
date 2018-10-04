package org.eevolution.api.impl;

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

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.eevolution.api.IPPOrderCostDAO;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.I_PP_Order_Cost;

import de.metas.util.Services;

public class PPOrderCostDAO implements IPPOrderCostDAO
{
	/**
	 * Retrieves all {@link I_PP_Order_Cost}s (including those which are not active) for given {@link I_PP_Order}.
	 * 
	 * @param order
	 * @return
	 */
	public List<I_PP_Order_Cost> retrieveAllOrderCosts(final I_PP_Order order)
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_PP_Order_Cost.class, order)
				.addEqualsFilter(I_PP_Order_Cost.COLUMNNAME_PP_Order_ID, order.getPP_Order_ID())
				.create()
				// .setOnlyActiveRecords(true) // NOTE: we need to retrieve ALL costs
				.list();
	}

	@Override
	public void deleteOrderCosts(final I_PP_Order ppOrder)
	{
		for (final I_PP_Order_Cost orderCost : retrieveAllOrderCosts(ppOrder))
		{
			InterfaceWrapperHelper.delete(orderCost);
		}
	}

}
