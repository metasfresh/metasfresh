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
import de.metas.handlingunits.IHUTrxBL;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.allocation.transfer.IHUJoinBL;
import de.metas.handlingunits.exceptions.NoCompatibleHUItemParentFoundException;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.X_M_HU_PI_Item;

public class HUJoinBL implements IHUJoinBL
{
	@Override
	public void assignTradingUnitToLoadingUnit(final IHUContext huContext, final I_M_HU loadingUnit, final I_M_HU tradingUnit) throws NoCompatibleHUItemParentFoundException
	{
		//
		// Services
		final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
		final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);

		final IHUTrxBL huTrxBL = Services.get(IHUTrxBL.class);

		boolean availableLUPIFound = false;

		final List<I_M_HU_Item> luItems = handlingUnitsDAO.retrieveItems(loadingUnit);
		for (final I_M_HU_Item luItem : luItems)
		{
			if (handlingUnitsBL.isVirtual(tradingUnit))
			{
				//
				// We assign virtual PI to any loading unit.
				huTrxBL.setParentHU(huContext, luItem, tradingUnit);
				availableLUPIFound = true;
				break;
			}
			if (!X_M_HU_PI_Item.ITEMTYPE_HandlingUnit.equals(luItem.getItemType()))
			{
				//
				// Item type needs to be handling unit
				continue;
			}

			if (luItem.getM_HU_PI_Item().getIncluded_HU_PI_ID() != tradingUnit.getM_HU_PI_Version().getM_HU_PI_ID())
			{
				//
				// Item not supported by this handling unit
				continue;
			}

			//
			// Assign HU to the luItem which we just found
			huTrxBL.setParentHU(huContext, luItem, tradingUnit);
			availableLUPIFound = true;
		}

		if (!availableLUPIFound)
		{
			final I_M_HU_PI_Item luPI = handlingUnitsDAO.retrieveParentPIItemForChildHUOrNull(loadingUnit, tradingUnit.getM_HU_PI_Version().getM_HU_PI(), huContext);
			if (luPI != null)
			{
				final I_M_HU_Item newLUItem = handlingUnitsDAO.createHUItem(loadingUnit, luPI);

				//
				// Assign HU to the newLUItem which we just created
				huTrxBL.setParentHU(huContext, newLUItem, tradingUnit);
				availableLUPIFound = true;
			}
		}

		if (!availableLUPIFound)
		{
			//
			// This does not need to be translated, as it will be handled later (internal error message)
			throw new NoCompatibleHUItemParentFoundException("TU could not be attached to the specified LU because no LU-PI Item supports it"
					+ "\nLoading Unit (LU): " + loadingUnit
					+ "\nTrading Unit (TU): " + tradingUnit);
		}
	}
}
