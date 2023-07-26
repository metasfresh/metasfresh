package de.metas.costing;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;

import org.compiere.model.I_M_Cost;
import org.compiere.model.I_M_Product;

import javax.annotation.Nullable;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2018 metas GmbH
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

public interface ICurrentCostsRepository
{
	List<CurrentCost> getByIds(Set<CurrentCostId> ids);

	@Nullable
	CurrentCost getOrNull(CostSegmentAndElement costSegmentAndElement);

	CurrentCost getOrCreate(CostSegmentAndElement costSegmentAndElement);

	CurrentCost create(CostSegmentAndElement costSegmentAndElement);

	void createIfMissing(CostSegmentAndElement costSegmentAndElement);

	void createDefaultProductCosts(I_M_Product product);

	void save(CurrentCost currentCost);

	void deleteForProduct(I_M_Product product);

	Optional<AggregatedCostPrice> getAggregatedCostPriceByCostSegmentAndCostingMethod(CostSegment costSegment, CostingMethod costingMethod);

	List<CurrentCost> getByCostSegmentAndCostingMethod(CostSegment costSegment, CostingMethod costingMethod);

	List<CurrentCost> getByCostSegmentAndCostElements(CostSegment costSegment, Set<CostElementId> costElementIds);

	void updateCostRecord(CostSegmentAndElement costSegmentAndElement, Consumer<I_M_Cost> updater);
}
