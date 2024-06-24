package de.metas.costing;

import lombok.NonNull;
import org.adempiere.service.ClientId;

import java.util.List;
import java.util.Optional;
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

public interface ICostElementRepository
{
	Optional<CostElement> getByIdIfExists(@NonNull CostElementId costElementId);

	@NonNull
	CostElement getById(@NonNull CostElementId costElementId);

	@NonNull
	CostElement getOrCreateMaterialCostElement(ClientId adClientId, CostingMethod costingMethod);

	List<CostElement> getCostElementsWithCostingMethods(ClientId adClientId);

	List<CostElement> getMaterialCostingMethods(ClientId adClientId);

	List<CostElement> getByCostingMethod(CostingMethod costingMethod);

	Set<CostElementId> getActiveCostElementIds();

	Set<CostElementId> getIdsByCostingMethod(CostingMethod costingMethod);

	List<CostElement> getMaterialCostingElementsForCostingMethod(@NonNull CostingMethod costingMethod);
}
