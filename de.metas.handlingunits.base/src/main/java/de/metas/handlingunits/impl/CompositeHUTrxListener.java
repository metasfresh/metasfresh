package de.metas.handlingunits.impl;

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


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.adempiere.util.Check;
import org.adempiere.util.lang.IReference;

import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHUTransaction;
import de.metas.handlingunits.IHUTrxListener;
import de.metas.handlingunits.allocation.IAllocationResult;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.model.I_M_HU_Trx_Hdr;
import de.metas.handlingunits.model.I_M_HU_Trx_Line;

public class CompositeHUTrxListener implements IHUTrxListener
{
	private final List<IHUTrxListener> listeners = new ArrayList<IHUTrxListener>();

	public CompositeHUTrxListener()
	{
		super();
	}

	/**
	 * Add given <code>listener</code>.
	 *
	 * If listener was already added then it won't be added again.
	 *
	 * @param listener
	 */
	public void addListener(final IHUTrxListener listener)
	{
		Check.assumeNotNull(listener, "listener not null");
		if (hasListener(listener))
		{
			return;
		}

		listeners.add(listener);
	}

	public void addListeners(final Collection<IHUTrxListener> listeners)
	{
		Check.assumeNotNull(listeners, "listeners not null");
		if (listeners.isEmpty())
		{
			return;
		}

		for (final IHUTrxListener listener : listeners)
		{
			addListener(listener);
		}
	}

	public void removeListener(final IHUTrxListener listener)
	{
		if (listener == null)
		{
			return;
		}
		listeners.remove(listener);
	}

	/**
	 *
	 * @param listener
	 * @return true if given listener was already registered
	 */
	public boolean hasListener(final IHUTrxListener listener)
	{
		return listeners.contains(listener);
	}

	public List<IHUTrxListener> asList()
	{
		return new ArrayList<>(listeners);
	}

	public CompositeHUTrxListener copy()
	{
		final CompositeHUTrxListener copy = new CompositeHUTrxListener();
		copy.listeners.addAll(listeners);
		return copy;
	}

	@Override
	public void trxLineProcessed(final IHUContext huContext, final I_M_HU_Trx_Line trxLine)
	{
		for (final IHUTrxListener listener : listeners)
		{
			listener.trxLineProcessed(huContext, trxLine);
		}
	}

	@Override
	public void huParentChanged(final I_M_HU hu, final I_M_HU_Item parentHUItemOld)
	{
		for (final IHUTrxListener listener : listeners)
		{
			listener.huParentChanged(hu, parentHUItemOld);
		}
	}

	@Override
	public void afterTrxProcessed(final IReference<I_M_HU_Trx_Hdr> trxHdrRef, final List<I_M_HU_Trx_Line> trxLines)
	{
		for (final IHUTrxListener listener : listeners)
		{
			listener.afterTrxProcessed(trxHdrRef, trxLines);
		}
	}

	@Override
	public void afterLoad(final IHUContext huContext, final List<IAllocationResult> loadResults)
	{
		for (final IHUTrxListener listener : listeners)
		{
			listener.afterLoad(huContext, loadResults);
		}
	}

	@Override
	public void onUnloadLoadTransaction(final IHUContext huContext, final IHUTransaction unloadTrx, final IHUTransaction loadTrx)
	{
		for (final IHUTrxListener listener : listeners)
		{
			listener.onUnloadLoadTransaction(huContext, unloadTrx, loadTrx);
		}
	}

	@Override
	public void onSplitTransaction(final IHUContext huContext, final IHUTransaction unloadTrx, final IHUTransaction loadTrx)
	{
		for (final IHUTrxListener listener : listeners)
		{
			listener.onSplitTransaction(huContext, unloadTrx, loadTrx);
		}
	}

	@Override
	public String toString()
	{
		return "CompositeHUTrxListener [listeners=" + listeners + "]";
	}
}
