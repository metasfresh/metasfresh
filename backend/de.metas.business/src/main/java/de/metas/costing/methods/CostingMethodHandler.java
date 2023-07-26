package de.metas.costing.methods;

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
	String ANY = "*";
	Set<String> ANY_TABLE = ImmutableSet.of(ANY);

	CostingMethod getCostingMethod();

	Set<String> getHandledTableNames();

	Optional<CostDetailCreateResult> createOrUpdateCost(CostDetailCreateRequest request);

	MoveCostsResult createMovementCosts(@NonNull MoveCostsRequest request);

	void voidCosts(CostDetailVoidRequest request);

	Optional<CostAmount> calculateSeedCosts(CostSegment costSegment, final OrderLineId orderLineId);

}
