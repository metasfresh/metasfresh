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

import org.adempiere.util.lang.IMutable;

import de.metas.handlingunits.impl.HUIterator;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.storage.IHUItemStorage;

/**
 * Downstream {@link HUIterator} Listener.
 *
 * @author tsa
 *
 * @see HUIteratorListenerAdapter
 * @see HUIteratorListenerDelegateAdapter
 */
public interface IHUIteratorListener
{
	enum Result
	{
		/**
		 * Continue navigation
		 */
		CONTINUE,
		/**
		 * Stop navigation right on this node
		 */
		STOP,
		/**
		 * Skip downstream navigation and continue with siblings and other nodes.
		 *
		 * This result can be returned only from before* methods.
		 */
		SKIP_DOWNSTREAM,
	}

	/**
	 * Called by API to set the {@link IHUIterator} on which this listener operates.
	 *
	 * @param iterator
	 */
	void setHUIterator(final IHUIterator iterator);

	Result beforeHU(IMutable<I_M_HU> hu);

	/**
	 * Method is called after a the given <code>hu</code> has been visited by the iterator. May not return {@link Result#SKIP_DOWNSTREAM}.
	 */
	Result afterHU(I_M_HU hu);

	Result beforeHUItem(IMutable<I_M_HU_Item> item);

	Result afterHUItem(I_M_HU_Item item);

	Result beforeHUItemStorage(IMutable<IHUItemStorage> itemStorage);

	Result afterHUItemStorage(IHUItemStorage itemStorage);
}
