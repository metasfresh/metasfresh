package de.metas.handlingunits.impl;

import de.metas.handlingunits.IHUAndItemsDAO;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.X_M_HU_Item;
import de.metas.handlingunits.model.X_M_HU_PI_Item;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2016 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public abstract class AbstractHUAndItemsDAO implements IHUAndItemsDAO
{

	@Override
	public final I_M_HU_Item retrieveItem(final I_M_HU hu, final I_M_HU_PI_Item piItem)
	{
		I_M_HU_Item huAggregateItem = null;

		final int piItemId = piItem.getM_HU_PI_Item_ID();
		for (final I_M_HU_Item item : retrieveItems(hu))
		{
			if (item.getM_HU_PI_Item_ID() == piItemId)
			{
				return item;
			}

			// since we iterate all items anyway, also look out for the aggregate/bag item.
			if (X_M_HU_PI_Item.ITEMTYPE_HandlingUnit.equals(piItem.getItemType()) && X_M_HU_Item.ITEMTYPE_HUAggregate.equals(item.getItemType()))
			{
				huAggregateItem = item;
			}
		}
		return huAggregateItem;
	}

}
