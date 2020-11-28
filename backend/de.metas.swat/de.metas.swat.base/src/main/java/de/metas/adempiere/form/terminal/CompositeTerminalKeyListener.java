package de.metas.adempiere.form.terminal;

/*
 * #%L
 * de.metas.swat.base
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
import java.util.Iterator;
import java.util.List;

import org.adempiere.util.lang.IReference;
import org.adempiere.util.lang.ImmutableReference;

import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.util.Check;
import de.metas.util.collections.IdentityHashSet;

public class CompositeTerminalKeyListener implements ITerminalKeyListener
{
	// private final ITerminalContext terminalContext;
	private final List<IReference<ITerminalKeyListener>> listeners = new ArrayList<IReference<ITerminalKeyListener>>();

	/**
	 * A set containing all currently active Key Returns (see {@link #keyReturned(ITerminalKey)}).
	 */
	private final IdentityHashSet<ITerminalKey> activeKeyReturns = new IdentityHashSet<ITerminalKey>();

	public CompositeTerminalKeyListener(final ITerminalContext tc)
	{
		super();
		Check.assumeNotNull(tc, "tc not null");
		// this.terminalContext = WeakWrapper.asWeak(tc, ITerminalContext.class);
	}

	public void clear()
	{
		listeners.clear();
	}

	public void addTerminalKeyListener(final ITerminalKeyListener listenerToAdd)
	{
		Check.assumeNotNull(listenerToAdd, "listenerToAdd not null");

		//
		// Check: don't add it twice
		final Iterator<IReference<ITerminalKeyListener>> it = listeners.iterator();
		while (it.hasNext())
		{
			final IReference<ITerminalKeyListener> listenerRef = it.next();
			final ITerminalKeyListener listener = listenerRef.getValue();
			if (listener == null)
			{
				// reference expired
				it.remove();
				continue;
			}

			if (listener.equals(listenerToAdd))
			{
				return;
			}
		}

		final IReference<ITerminalKeyListener> listenerToAddRef = createReference(listenerToAdd);
		listeners.add(listenerToAddRef);
	}

	private final IReference<ITerminalKeyListener> createReference(final ITerminalKeyListener listener)
	{
		final IReference<ITerminalKeyListener> listenerRef = new ImmutableReference<>(listener);

		// listenerRef = new org.adempiere.util.lang.WeakReference<T>(listener);
		// // make sure this listener will be garbage collected ONLY when terminal context is trashed
		// terminalContext.enqueueToNoGCList(listenerToAdd);

		return listenerRef;
	}

	@Override
	public void keyReturning(final ITerminalKey key)
	{
		// If the key was already added, exit right away to avoid recursion
		if (!activeKeyReturns.add(key))
		{
			return;
		}

		try
		{
			final Iterator<IReference<ITerminalKeyListener>> it = listeners.iterator();
			while (it.hasNext())
			{
				final IReference<ITerminalKeyListener> listenerRef = it.next();
				final ITerminalKeyListener listener = listenerRef.getValue();
				if (listener == null)
				{
					// reference expired
					it.remove();
					continue;
				}

				listener.keyReturning(key);
			}
		}
		finally
		{
			// Remove our key from active Key Returns set
			activeKeyReturns.remove(key);
		}
	}

	@Override
	public void keyReturned(final ITerminalKey key)
	{
		// If the key was already added, exit right away to avoid recursion
		if (!activeKeyReturns.add(key))
		{
			return;
		}

		try
		{
			final Iterator<IReference<ITerminalKeyListener>> it = listeners.iterator();
			while (it.hasNext())
			{
				final IReference<ITerminalKeyListener> listenerRef = it.next();
				final ITerminalKeyListener listener = listenerRef.getValue();
				if (listener == null)
				{
					// reference expired
					it.remove();
					continue;
				}

				listener.keyReturned(key);
			}
		}
		finally
		{
			// Remove our key from active Key Returns set
			activeKeyReturns.remove(key);
		}
	}
}
