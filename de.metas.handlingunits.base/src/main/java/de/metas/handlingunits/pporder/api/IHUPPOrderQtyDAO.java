package de.metas.handlingunits.pporder.api;

import java.util.List;
import java.util.stream.Stream;

import org.adempiere.util.ISingletonService;

import de.metas.handlingunits.model.I_PP_Order_Qty;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2017 metas GmbH
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

public interface IHUPPOrderQtyDAO extends ISingletonService
{
	I_PP_Order_Qty retrieveById(int ppOrderQtyId);
	
	void save(final I_PP_Order_Qty ppOrderQty);

	void delete(I_PP_Order_Qty ppOrderQty);

	default void saveAll(final List<I_PP_Order_Qty> ppOrderQtys)
	{
		if (ppOrderQtys.isEmpty())
		{
			return;
		}
		ppOrderQtys.forEach(this::save);
	}

	List<I_PP_Order_Qty> retrieveOrderQtys(int ppOrderId);
	
	I_PP_Order_Qty retrieveOrderQtyForCostCollector(int ppOrderId, final int costCollectorId);

	default Stream<I_PP_Order_Qty> streamOrderQtys(final int ppOrderId)
	{
		return retrieveOrderQtys(ppOrderId).stream();
	}

	boolean isHuIdIssued(int huId);
}
