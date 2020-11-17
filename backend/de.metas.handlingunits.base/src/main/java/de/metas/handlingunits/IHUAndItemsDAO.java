package de.metas.handlingunits;

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

import javax.annotation.Nullable;

import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.X_M_HU_Item;

public interface IHUAndItemsDAO
{
	void saveHU(I_M_HU hu);

	void delete(I_M_HU hu);

	/**
	 * Gets parent {@link I_M_HU}
	 *
	 * @param hu
	 * @return parent HU or null
	 */
	I_M_HU retrieveParent(final I_M_HU hu);

	/** @return parent M_HU_ID or -1 */
	int retrieveParentId(@Nullable I_M_HU hu);

	I_M_HU_Item retrieveParentItem(I_M_HU hu);

	void setParentItem(I_M_HU hu, I_M_HU_Item parentItem);

	List<I_M_HU> retrieveIncludedHUs(final I_M_HU_Item item);

	List<I_M_HU_Item> retrieveItems(final I_M_HU hu);

	/**
	 * From the {@link I_M_HU_Item}s that reference the given {@code hu}, retrieve the one that also references the given {@code piItem},<br>
	 * <b>or</b> (gh #460) if there is no such item and the given {@code piItem} has {@code ItemType='HU'}, then retrieve the {@link I_M_HU_Item} with {@link X_M_HU_Item#ITEMTYPE_HUAggregate}.
	 *
	 * @return the found item type or {@code null}.
	 */
	I_M_HU_Item retrieveItem(I_M_HU hu, I_M_HU_PI_Item piItem);

	/**
	 * Creates and saves {@link I_M_HU_Item}
	 *
	 * @return created HU item
	 *
	 * @see IHandlingUnitsDAO#createHUItem(I_M_HU, I_M_HU_PI_Item)
	 */
	I_M_HU_Item createHUItem(I_M_HU hu, I_M_HU_PI_Item piItem);

	/**

	 * @see IHandlingUnitsDAO#createAggregateHUItem(I_M_HU)
	 */
	I_M_HU_Item createAggregateHUItem(I_M_HU hu);

	I_M_HU_Item createChildHUItem(I_M_HU hu);

	/**
	 * Retrieve the Aggregated Item from an HU in case it has one
	 */
	I_M_HU_Item retrieveAggregatedItemOrNull(I_M_HU hu);
}
