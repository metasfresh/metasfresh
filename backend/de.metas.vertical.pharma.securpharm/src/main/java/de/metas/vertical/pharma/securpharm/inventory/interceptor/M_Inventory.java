/*
 *
 * * #%L
 * * %%
 * * Copyright (C) <current year> metas GmbH
 * * %%
 * * This program is free software: you can redistribute it and/or modify
 * * it under the terms of the GNU General Public License as
 * * published by the Free Software Foundation, either version 2 of the
 * * License, or (at your option) any later version.
 * *
 * * This program is distributed in the hope that it will be useful,
 * * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * * GNU General Public License for more details.
 * *
 * * You should have received a copy of the GNU General Public
 * * License along with this program. If not, see
 * * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * * #L%
 *
 */

package de.metas.vertical.pharma.securpharm.inventory.interceptor;

import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import de.metas.handlingunits.model.I_M_Inventory;
import de.metas.inventory.InventoryId;
import de.metas.vertical.pharma.securpharm.actions.SecurPharmAction;
import de.metas.vertical.pharma.securpharm.actions.SecurPharmaActionRequest;
import de.metas.vertical.pharma.securpharm.service.SecurPharmService;
import lombok.NonNull;

@Interceptor(I_M_Inventory.class)
@Component
public class M_Inventory
{
	private final SecurPharmService securPharmService;

	public M_Inventory(@NonNull final SecurPharmService securPharmService)
	{
		this.securPharmService = securPharmService;
	}

	@DocValidate(timings = ModelValidator.TIMING_AFTER_COMPLETE)
	public void beforeComplete(final I_M_Inventory inventory)
	{
		if (securPharmService.hasConfig())
		{
			final InventoryId inventoryId = InventoryId.ofRepoId(inventory.getM_Inventory_ID());
			securPharmService.scheduleAction(SecurPharmaActionRequest.builder()
					.action(SecurPharmAction.DECOMMISSION)
					.inventoryId(inventoryId)
					.build());
		}
	}

	@DocValidate(timings = ModelValidator.TIMING_BEFORE_REVERSECORRECT)
	public void beforeReverse(final I_M_Inventory inventory)
	{
		if (securPharmService.hasConfig())
		{
			final InventoryId inventoryId = InventoryId.ofRepoId(inventory.getM_Inventory_ID());
			securPharmService.scheduleAction(SecurPharmaActionRequest.builder()
					.action(SecurPharmAction.UNDO_DECOMMISSION)
					.inventoryId(inventoryId)
					.build());
		}
	}

}
