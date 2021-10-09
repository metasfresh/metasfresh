package de.metas.costing;

import de.metas.order.OrderLineId;
import de.metas.uom.UomId;

import java.util.Optional;

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

public interface ICostingService
{
	AggregatedCostAmount createCostDetail(CostDetailCreateRequest request);

	AggregatedCostAmount createReversalCostDetails(CostDetailReverseRequest request);

	MoveCostsResult moveCosts(MoveCostsRequest request);

	void voidAndDeleteForDocument(CostingDocumentRef documentRef);

	/**
	 * @return seed cost or null
	 */
	Optional<CostAmount> calculateSeedCosts(
			CostSegment costSegment,
			CostingMethod costingMethod,
			final OrderLineId orderLineId);

	Optional<CostPrice> getCurrentCostPrice(
			CostSegment costSegment,
			CostingMethod costingMethod);
}
