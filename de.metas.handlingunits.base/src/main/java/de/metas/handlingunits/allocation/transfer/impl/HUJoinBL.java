package de.metas.handlingunits.allocation.transfer.impl;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2015 metas GmbH
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

import java.util.List;

import org.adempiere.util.Services;

import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.allocation.transfer.IHUJoinBL;
import de.metas.handlingunits.exceptions.NoCompatibleHUItemParentFoundException;
import de.metas.handlingunits.hutransaction.IHUTrxBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.X_M_HU_PI_Item;

public class HUJoinBL implements IHUJoinBL
{
	@Override
	public void assignTradingUnitToLoadingUnit(final IHUContext huContext,
			final I_M_HU loadingUnit,
			final I_M_HU tradingUnit) throws NoCompatibleHUItemParentFoundException
	{
		//
		// Services
		final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
		final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);

		final IHUTrxBL huTrxBL = Services.get(IHUTrxBL.class);

		// why is that?
		// Check.errorIf(handlingUnitsBL.isAggregateHU(tradingUnit), "Param 'tradingUnit' can't be an aggregate HU; tradingUnit={}", tradingUnit);

		//
		// iterate existing items of 'lodingUnit' and see if one fits to our 'tradingUnit'
		final List<I_M_HU_Item> luItems = handlingUnitsDAO.retrieveItems(loadingUnit);
		for (final I_M_HU_Item luItem : luItems)
		{
			if (!X_M_HU_PI_Item.ITEMTYPE_HandlingUnit.equals(handlingUnitsBL.getItemType(luItem)))
			{

				continue; // Item type needs to be handling unit
			}

			if (handlingUnitsBL.isVirtual(tradingUnit))
			{
				// We assign virtual PI to any loading unit.
				huTrxBL.setParentHU(huContext, luItem, tradingUnit);
				return; // we are done
			}

			if (handlingUnitsBL.getPIItem(luItem).getIncluded_HU_PI_ID() != handlingUnitsBL.getPIVersion(tradingUnit).getM_HU_PI_ID())
			{
				continue; // Item not supported by this handling unit
			}

			// Assign HU to the luItem which we just found
			huTrxBL.setParentHU(huContext, luItem, tradingUnit);
			return; // we are done
		}

		// we did not find a compatible item for 'tradingUnit' to attach.
		// try if we can create one on the fly
		final I_M_HU_PI_Item luPI = handlingUnitsDAO.retrieveParentPIItemForChildHUOrNull(loadingUnit, handlingUnitsBL.getPI(tradingUnit), huContext);
		if (luPI != null)
		{
			final I_M_HU_Item newLUItem = handlingUnitsDAO.createHUItem(loadingUnit, luPI);

			// Assign HU to the newLUItem which we just created
			huTrxBL.setParentHU(huContext, newLUItem, tradingUnit);
			return; // we are done
		}

		// We did not succeed; thorw an exception.
		// This does not need to be translated, as it will be handled later (internal error message)
		throw new NoCompatibleHUItemParentFoundException("TU could not be attached to the specified LU because no LU-PI Item supports it"
				+ "\nLoading Unit (LU): " + loadingUnit
				+ "\nTrading Unit (TU): " + tradingUnit);

	}
}
