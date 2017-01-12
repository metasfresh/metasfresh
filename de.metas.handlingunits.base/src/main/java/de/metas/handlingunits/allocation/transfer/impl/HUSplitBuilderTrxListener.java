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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHUTransaction;
import de.metas.handlingunits.impl.HUTrxListenerAdapter;

/* package */final class HUSplitBuilderTrxListener extends HUTrxListenerAdapter
{
	public static final transient HUSplitBuilderTrxListener instance = new HUSplitBuilderTrxListener();

	private HUSplitBuilderTrxListener()
	{
		super();
	}

	@Override
	public void onUnloadLoadTransaction(final IHUContext huContext, final IHUTransaction unloadTrx, final IHUTransaction loadTrx)
	{
		huContext.getTrxListeners().onSplitTransaction(huContext, unloadTrx, loadTrx);
	}

	@Override
	public void onSplitTransaction(final IHUContext huContext, final IHUTransaction unloadTrx, final IHUTransaction loadTrx)
	{
		// nothing, because we triggered this event; let the others do their stuff...
	}

}
