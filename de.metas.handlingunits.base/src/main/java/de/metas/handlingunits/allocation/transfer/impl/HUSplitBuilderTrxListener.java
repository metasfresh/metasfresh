package de.metas.handlingunits.allocation.transfer.impl;

import de.metas.handlingunits.HUContextDateTrxProvider;

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

import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHUTransaction;
import de.metas.handlingunits.IHUTrxListener;
import de.metas.handlingunits.impl.HUTrxListenerAdapter;

/**
 * This listener is added to a {@link IHUContext} before a split or merge or sth similar is executred. 
 * Its job is to invoke the other listeners' {@link IHUTrxListener#onSplitTransaction(IHUContext, IHUTransaction, IHUTransaction)} method if its own {@link #onUnloadLoadTransaction(IHUContext, IHUTransaction, IHUTransaction)} method is invoked.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
/* package */final class HUSplitBuilderTrxListener extends HUTrxListenerAdapter
{
	public static final transient HUSplitBuilderTrxListener instance = new HUSplitBuilderTrxListener();

	private HUSplitBuilderTrxListener()
	{
		super();
	}

	/**
	 * Invokes {@link IHUTrxListener#onSplitTransaction(IHUContext, IHUTransaction, IHUTransaction)} on the trxListeners that are registered with the given {@code huContext}.
	 */
	@Override
	public void onUnloadLoadTransaction(final IHUContext huContext, final IHUTransaction unloadTrx, final IHUTransaction loadTrx)
	{
		huContext.getTrxListeners().onSplitTransaction(huContext, unloadTrx, loadTrx);
	}

	/**
	 * Does nothing, because we triggered this event.
	 */
	@Override
	public void onSplitTransaction(final IHUContext huContext, final IHUTransaction unloadTrx, final IHUTransaction loadTrx)
	{
		// nothing, because we triggered this event; let the others do their stuff...
	}

}
