package de.metas.picking.service;

/*
 * #%L
 * de.metas.fresh.base
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

import java.util.Properties;

import de.metas.picking.service.PackingItemsMap;

/**
 * Packing Context.
 * 
 * It's just a "workfile" on which {@link IPackingService} methods will work.
 * 
 * @author tsa
 * 
 */
public interface IPackingContext
{
	Properties getCtx();

	PackingItemsMap getPackingItemsMap();

	/**
	 * Set the map used to keep track of that is packed where while the packing takes place.
	 * 
	 * @param packingItems
	 */
	void setPackingItemsMap(PackingItemsMap packingItems);

	int getPackingItemsMapKey();

	/**
	 * Sets the key for {@link PackingItemsMap} under which those items are stored that are "packed".
	 * Apparently, using a {@code M_PickingSlot_ID} is a good idea, while using {@link PackingItemsMap#KEY_UnpackedItems} is probably a bad idea.
	 * 
	 * @param packingItemsMapKey
	 */
	void setPackingItemsMapKey(int packingItemsMapKey);

}
