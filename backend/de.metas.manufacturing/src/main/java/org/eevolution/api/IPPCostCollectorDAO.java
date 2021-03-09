package org.eevolution.api;

import java.math.BigDecimal;
import java.time.Duration;

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
import java.util.Set;

import org.eevolution.model.I_PP_Cost_Collector;

import de.metas.util.ISingletonService;

public interface IPPCostCollectorDAO extends ISingletonService
{
	BigDecimal getQtyUsageVariance(PPOrderBOMLineId orderBOMLineId);

	List<I_PP_Cost_Collector> getByParent(I_PP_Cost_Collector parentCostCollector);

	I_PP_Cost_Collector getById(PPCostCollectorId costCollectorId);

	<T extends I_PP_Cost_Collector> T getById(PPCostCollectorId costCollectorId, Class<T> modelClass);

	List<I_PP_Cost_Collector> getByIds(Set<PPCostCollectorId> costCollectorIds);

	<T extends I_PP_Cost_Collector> List<T> getByIds(Set<PPCostCollectorId> costCollectorIds, Class<T> modelClass);

	List<I_PP_Cost_Collector> getByOrderId(PPOrderId ppOrderId);

	/**
	 * Retrieve the cost collectors of the given <code>order</code> that are active and are either completed or closed.
	 */
	List<I_PP_Cost_Collector> getCompletedOrClosedByOrderId(PPOrderId order);

	/**
	 * Retrieve the cost collectors for the given ppOrder. The cost collectors must have:
	 * <ul>
	 * <li>ppOrder's id
	 * <li>status completed
	 * <li>type Material receipt
	 * </ul>
	 */
	List<I_PP_Cost_Collector> getReceiptsByOrderId(PPOrderId ppOrderId);

	Duration getTotalSetupTimeReal(PPOrderRoutingActivity activity, CostCollectorType costCollectorType);

	Duration getDurationReal(PPOrderRoutingActivity activity, CostCollectorType costCollectorType);

	void save(I_PP_Cost_Collector cc);
}
