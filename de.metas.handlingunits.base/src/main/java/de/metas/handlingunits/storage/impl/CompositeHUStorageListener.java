package de.metas.handlingunits.storage.impl;

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


import java.util.LinkedHashSet;
import java.util.Set;

import org.adempiere.util.concurrent.CloseableReentrantLock;

import de.metas.handlingunits.storage.HUStorageChangeEvent;
import de.metas.handlingunits.storage.IHUStorageListener;

public class CompositeHUStorageListener implements IHUStorageListener
{
	private final Set<IHUStorageListener> listeners = new LinkedHashSet<IHUStorageListener>();
	private final CloseableReentrantLock listenersLock = new CloseableReentrantLock();

	public CompositeHUStorageListener()
	{
		super();
	}

	public void addHUStorageListener(final IHUStorageListener listener)
	{
		listeners.add(listener);
	}

	@Override
	public void onQtyChanged(final HUStorageChangeEvent event)
	{
		// Take a copy of current listeners
		final Set<IHUStorageListener> listenersCopy;
		try (final CloseableReentrantLock lock = listenersLock.open())
		{
			listenersCopy = new LinkedHashSet<>(listeners);
		}

		for (final IHUStorageListener listener : listenersCopy)
		{
			listener.onQtyChanged(new HUStorageChangeEvent(event.getHUStorage(), event.getM_Product(), event.getC_UOM(), event.getQtyOld(), event.getQtyNew()));
		}
	}

	@Override
	public String toString()
	{
		final StringBuilder builder = new StringBuilder();
		builder.append("CompositeHUStorageListener [listeners=");
		builder.append(listeners);
		builder.append("]");
		return builder.toString();
	}
}
