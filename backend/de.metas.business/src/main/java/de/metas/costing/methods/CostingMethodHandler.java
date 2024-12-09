package de.metas.costing.methods;

<<<<<<< HEAD
import java.util.Optional;
import java.util.Set;

import com.google.common.collect.ImmutableSet;

import de.metas.costing.CostAmount;
import de.metas.costing.CostDetailCreateRequest;
import de.metas.costing.CostDetailCreateResult;
import de.metas.costing.CostDetailVoidRequest;
import de.metas.costing.CostSegment;
import de.metas.costing.CostingMethod;
import de.metas.costing.MoveCostsRequest;
import de.metas.costing.MoveCostsResult;
import de.metas.order.OrderLineId;
import lombok.NonNull;

=======
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

>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
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
<<<<<<< HEAD
 * 
=======
 *
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
<<<<<<< HEAD
 * 
=======
 *
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public interface CostingMethodHandler
{
<<<<<<< HEAD
	String ANY = "*";
	Set<String> ANY_TABLE = ImmutableSet.of(ANY);

=======
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	CostingMethod getCostingMethod();

	Set<String> getHandledTableNames();

<<<<<<< HEAD
	Optional<CostDetailCreateResult> createOrUpdateCost(CostDetailCreateRequest request);
=======
	CostDetailCreateResultsList createOrUpdateCost(CostDetailCreateRequest request);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	MoveCostsResult createMovementCosts(@NonNull MoveCostsRequest request);

	void voidCosts(CostDetailVoidRequest request);

<<<<<<< HEAD
	Optional<CostAmount> calculateSeedCosts(CostSegment costSegment, final OrderLineId orderLineId);

}
=======
	CostDetailAdjustment recalculateCostDetailAmountAndUpdateCurrentCost(CostDetail costDetail, CurrentCost currentCost);
}

>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
