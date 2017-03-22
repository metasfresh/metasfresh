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

import java.util.Collection;
import java.util.Date;
import java.util.Properties;

import de.metas.handlingunits.impl.HUIterator;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.storage.IHUItemStorage;
import de.metas.handlingunits.storage.IHUStorageFactory;

/**
 * Downstream HU Iterator. Iterates a HU hierarchy starting a the given root.
 * <p>
 * Use the {@link HUIterator} constructor to get yours.
 *
 * NOTE:
 * <ul>
 * <li>duplicate nodes are avoided
 * </ul>
 *
 * @author tsa
 *
 */
public interface IHUIterator
{
	/**
	 * Marks level of the starting HUs.
	 */
	int DEPTH_STARTING_HU = 0;

	/**
	 * Marks level of the starting HU's Item.
	 */
	int DEPTH_STARTING_HU_Item = 1;

	/**
	 * Marks the first level of iteration (direct children of the starting HU).
	 */
	int DEPTH_FIRSTHUCHILD_HU = 2;

	/**
	 * Marks the level of HU_Items of direct children
	 */
	int DEPTH_FIRSTHUCHILD_HU_Item = 3;

	/**
	 * Get/Create {@link IHUContext}
	 *
	 * @return
	 */
	IHUContext getHUContext();

	IHUIterator setHUContext(IHUContext huContext);

	Properties getCtx();

	IHUIterator setCtx(Properties ctx);

	Date getDate();

	IHUIterator setDate(final Date date);

	void setStorageFactory(final IHUStorageFactory storageFactory);

	IHUStorageFactory getStorageFactoryToUse();

	IHUIterator setListener(final IHUIteratorListener listener);

	/**
	 * Gets current depth.
	 *
	 * @return current depth; also please check DEPTH_* constants.
	 */
	int getDepth();

	/**
	 * Sets the maximum depth the iterator is allowed to reach.
	 *
	 * NOTE: The depth of the first child is {@link #DEPTH_FIRSTHUCHILD_HU}.
	 */
	void setDepthMax(final int depthMax);

	/**
	 * @return The maximum depth this iterator is allowed to reach.
	 */
	int getDepthMax();

	IHUItemStorage getCurrentHUItemStorage();

	I_M_HU_Item getCurrentHUItem();

	I_M_HU getCurrentHU();

	IHUIterator iterate(final Collection<I_M_HU> hus);

	IHUIterator iterate(final I_M_HU hu);
}
