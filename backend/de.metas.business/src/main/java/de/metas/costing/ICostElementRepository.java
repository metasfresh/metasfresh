package de.metas.costing;

<<<<<<< HEAD
=======
import lombok.NonNull;
import org.adempiere.service.ClientId;

>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
import java.util.List;
import java.util.Optional;
import java.util.Set;

<<<<<<< HEAD
import org.adempiere.service.ClientId;

import lombok.NonNull;

=======
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

public interface ICostElementRepository
{
	Optional<CostElement> getByIdIfExists(@NonNull CostElementId costElementId);

	@NonNull
	CostElement getById(@NonNull CostElementId costElementId);

	@NonNull
	CostElement getOrCreateMaterialCostElement(ClientId adClientId, CostingMethod costingMethod);

<<<<<<< HEAD
	List<CostElement> getCostElementsWithCostingMethods(ClientId adClientId);

	List<CostElement> getMaterialCostingMethods(ClientId adClientId);

	List<CostElement> getByCostingMethod(CostingMethod costingMethod);

=======
	List<CostElement> getByTypes(@NonNull ClientId clientId, @NonNull CostElementType... types);

	List<CostElement> getByClientId(@NonNull ClientId clientId);

	List<CostElement> getByCostingMethod(CostingMethod costingMethod);

	List<CostElement> getMaterialCostingElementsForCostingMethod(@NonNull CostingMethod costingMethod);

>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	Set<CostElementId> getActiveCostElementIds();

	Set<CostElementId> getIdsByCostingMethod(CostingMethod costingMethod);

<<<<<<< HEAD
	List<CostElement> getMaterialCostingElementsForCostingMethod(@NonNull CostingMethod costingMethod);
=======
	Set<CostElementId> getIdsByClientId(@NonNull ClientId clientId);


>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
}
