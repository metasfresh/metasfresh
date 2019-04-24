/*
 *
 *  * #%L
 *  * %%
 *  * Copyright (C) <current year> metas GmbH
 *  * %%
 *  * This program is free software: you can redistribute it and/or modify
 *  * it under the terms of the GNU General Public License as
 *  * published by the Free Software Foundation, either version 2 of the
 *  * License, or (at your option) any later version.
 *  *
 *  * This program is distributed in the hope that it will be useful,
 *  * but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  * GNU General Public License for more details.
 *  *
 *  * You should have received a copy of the GNU General Public
 *  * License along with this program. If not, see
 *  * <http://www.gnu.org/licenses/gpl-2.0.html>.
 *  * #L%
 *
 */

package de.metas.vertical.pharma.securpharm.interceptor;

import de.metas.handlingunits.inventory.InventoryId;
import de.metas.handlingunits.model.I_M_Inventory;
import de.metas.vertical.pharma.securpharm.model.DecommissionAction;
import de.metas.vertical.pharma.securpharm.model.SecurPharmActionResult;
import de.metas.vertical.pharma.securpharm.model.SecurPharmProductDataResult;
import de.metas.vertical.pharma.securpharm.repository.SecurPharmResultRepository;
import de.metas.vertical.pharma.securpharm.service.SecurPharmService;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Interceptor(I_M_Inventory.class)
@Component("de.metas.vertical.pharma.securpharm.interceptor.M_Inventory")
public class M_Inventory
{

	private final SecurPharmService securPharmService;

	private final SecurPharmResultRepository resultRepository;

	public M_Inventory(@NonNull final SecurPharmService securPharmService, @NonNull final SecurPharmResultRepository resultRepository)
	{
		this.securPharmService = securPharmService;
		this.resultRepository = resultRepository;
	}

	@DocValidate(timings = ModelValidator.TIMING_BEFORE_COMPLETE)
	public void beforeComplete(final I_M_Inventory inventory) throws Exception
	{
		if (securPharmService.hasConfig())
		{
			final InventoryId inventoryId = InventoryId.ofRepoId(inventory.getM_Inventory_ID());
			final SecurPharmProductDataResult productDataResult = resultRepository.getProductResultByInventoryId(inventoryId);
			if (productDataResult != null && !productDataResult.isError())
			{
				securPharmService.decommision(productDataResult, DecommissionAction.DESTROY, inventoryId);
			}
		}

	}

	@DocValidate(timings = ModelValidator.TIMING_BEFORE_REVERSECORRECT)
	public void beforeReverse(final I_M_Inventory inventory) throws Exception
	{
		if (securPharmService.hasConfig())
		{
			final InventoryId inventoryId = InventoryId.ofRepoId(inventory.getM_Inventory_ID());
			final SecurPharmActionResult actionResult = resultRepository.getActionResultByInventoryId(inventoryId, DecommissionAction.DESTROY);
			if (actionResult != null && !actionResult.isError())
			{
				securPharmService.undoDecommision(actionResult, DecommissionAction.UNDO_DISPENSE, inventoryId);
			}
		}

	}
}
