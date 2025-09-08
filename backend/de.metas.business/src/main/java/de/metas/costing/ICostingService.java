package de.metas.costing;

import de.metas.i18n.ExplainedOptional;
import lombok.NonNull;

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
	CostElement getCostElementById(@NonNull CostElementId costElementId);

	CostDetailCreateResultsList createCostDetail(CostDetailCreateRequest request);

	ExplainedOptional<CostDetailCreateResultsList> createCostDetailOrEmpty(@NonNull CostDetailCreateRequest request);

	CostDetailCreateResultsList createReversalCostDetails(CostDetailReverseRequest request);

	ExplainedOptional<CostDetailCreateResultsList> createReversalCostDetailsOrEmpty(CostDetailReverseRequest request);

	MoveCostsResult moveCosts(MoveCostsRequest request);

	void voidAndDeleteForDocument(CostingDocumentRef documentRef);

	Optional<CostPrice> getCurrentCostPrice(
			CostSegment costSegment,
			CostingMethod costingMethod);

	CostsRevaluationResult revaluateCosts(@NonNull CostsRevaluationRequest request);
}
