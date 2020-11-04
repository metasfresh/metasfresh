/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.handlingunits.pporder.api;

import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.model.I_PP_Order_Qty;
import de.metas.material.planning.pporder.PPOrderId;
import de.metas.util.ISingletonService;

public interface IHUPPOrderQtyDAO extends ISingletonService
{
	I_PP_Order_Qty retrieveById(int ppOrderQtyId);

	I_PP_Order_Qty save(CreateIssueCandidateRequest request);

	I_PP_Order_Qty save(CreateReceiptCandidateRequest request);

	List<I_PP_Order_Qty> saveAll(Collection<CreateReceiptCandidateRequest> requests);

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

	List<I_PP_Order_Qty> retrieveOrderQtys(PPOrderId ppOrderId);

	I_PP_Order_Qty retrieveOrderQtyForCostCollector(PPOrderId ppOrderId, final int costCollectorId);

	default Stream<I_PP_Order_Qty> streamOrderQtys(PPOrderId ppOrderId)
	{
		return retrieveOrderQtys(ppOrderId).stream();
	}
}
