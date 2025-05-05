package de.metas.costing.methods;

import de.metas.costing.CostDetail;
import de.metas.costing.CostDetailAdjustment;
import de.metas.costing.CostDetailCreateRequest;
import de.metas.costing.CostDetailCreateResultsList;
import de.metas.costing.CostDetailVoidRequest;
import de.metas.costing.CostingMethod;
import de.metas.costing.CurrentCost;
import de.metas.costing.MoveCostsRequest;
import de.metas.costing.MoveCostsResult;
import lombok.NonNull;

import java.util.Set;

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

public interface CostingMethodHandler
{
	CostingMethod getCostingMethod();

	Set<String> getHandledTableNames();

	CostDetailCreateResultsList createOrUpdateCost(CostDetailCreateRequest request);

	MoveCostsResult createMovementCosts(@NonNull MoveCostsRequest request);

	void voidCosts(CostDetailVoidRequest request);

	CostDetailAdjustment recalculateCostDetailAmountAndUpdateCurrentCost(CostDetail costDetail, CurrentCost currentCost);
}

