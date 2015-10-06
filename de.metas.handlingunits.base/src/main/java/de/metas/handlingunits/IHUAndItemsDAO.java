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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.util.List;

import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item;

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

	I_M_HU_Item retrieveParentItem(I_M_HU hu);

	void setParentItem(I_M_HU hu, I_M_HU_Item parentItem);

	List<I_M_HU> retrieveIncludedHUs(final I_M_HU_Item item);

	List<I_M_HU_Item> retrieveItems(final I_M_HU hu);

	I_M_HU_Item retrieveItem(I_M_HU hu, I_M_HU_PI_Item piItem);

	/**
	 * Creates and saves {@link I_M_HU_Item}
	 *
	 * @param hu
	 * @param piItem
	 * @return created HU item
	 */
	I_M_HU_Item createHUItem(I_M_HU hu, I_M_HU_PI_Item piItem);
}
