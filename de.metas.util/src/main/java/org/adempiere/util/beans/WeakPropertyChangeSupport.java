package org.adempiere.util.beans;

/*
 * #%L
 * de.metas.util
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


import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeListenerProxy;
import java.beans.PropertyChangeSupport;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.List;

import org.adempiere.util.Check;
import org.adempiere.util.lang.ObjectUtils;

/**
 * An {@link PropertyChangeSupport} which makes weak reference to source bean and which is able to register the listeners weakly.
 *
 * @author tsa
 *
 */
public class WeakPropertyChangeSupport extends PropertyChangeSupport
{
	/**
	 *
	 */
	private static final long serialVersionUID = 309861519819203221L;

	/**
	 * i.e. use weak default option
	 */
	public static final Boolean WEAK_AUTO = null;

	protected static enum WeakListenerCreationScope
	{
		ForAdding,
		ForRemoving,
	};

	private final boolean _weakDefault;

	public WeakPropertyChangeSupport(final Object sourceBean)
	{
		this(sourceBean, false); // weakDefault=false
	}

	public WeakPropertyChangeSupport(final Object sourceBean, final boolean weakDefault)
	{
		this(new WeakReference<>(sourceBean), weakDefault);
	}

	private WeakPropertyChangeSupport(final WeakReference<Object> sourceBeanRef, final boolean weakDefault)
	{
		super(sourceBeanRef);
		this._weakDefault = weakDefault;
	}

	public final boolean isWeakDefault()
	{
		return _weakDefault;
	}

	public void clear()
	{
		final PropertyChangeListener[] listeners = getPropertyChangeListeners();
		if (listeners == null || listeners.length == 0)
		{
			return;
		}

		for (final PropertyChangeListener listener : listeners)
		{
			super.removePropertyChangeListener(listener);
		}

		//
		// Make sure everything was removed
		final PropertyChangeListener[] listeners2 = getPropertyChangeListeners();
		if (listeners2 != null && listeners2.length > 0)
		{
			throw new IllegalStateException("Could not remove all listeners from " + this + "."
					+ "\nRemaining ones are: " + listeners2);
		}
	}

	private final PropertyChangeListener createWeakPropertyChangeListener(final PropertyChangeListener listener,
			final Boolean weak,
			final WeakListenerCreationScope scope)
	{
		//
		// Gets actual Weak option to use
		final boolean weakActual;
		if (weak == null || weak == WEAK_AUTO)
		{
			weakActual = isWeakDefault();
		}
		else
		{
			weakActual = weak;
		}

		final PropertyChangeListener listenerToWrap;
		if (listener instanceof WeakPropertyChangeListener)
		{
			final WeakPropertyChangeListener weakListener = (WeakPropertyChangeListener)listener;
			if (weakListener.isWeak() == weakActual)
			{
				return weakListener;
			}
			else
			{
				listenerToWrap = weakListener.getDelegate();
				Check.assumeNotNull(listenerToWrap, "Listener already expired: {}", weakListener);
			}
		}
		else if (listener instanceof PropertyChangeListenerProxy)
		{
			final PropertyChangeListenerProxy listenerProxy = (PropertyChangeListenerProxy)listener;
			final String propertyName = listenerProxy.getPropertyName();
			final PropertyChangeListener listenerActual = listenerProxy.getListener();
			final PropertyChangeListener listenerActualWrapped = createWeakPropertyChangeListener(listenerActual, weakActual, scope);
			return new PropertyChangeListenerProxy(propertyName, listenerActualWrapped);
		}
		else
		{
			listenerToWrap = listener;
		}

		final WeakPropertyChangeListener weakListener = new WeakPropertyChangeListener(listenerToWrap, weakActual);
		notifyWeakPropertyChangeListenerCreated(listenerToWrap, weak, scope);
		return weakListener;
	}

	/**
	 * Called when a {@link WeakPropertyChangeSupport} instance is created.
	 * 
	 * @param listenerToWrap
	 * @param weak
	 * @param scope
	 */
	protected void notifyWeakPropertyChangeListenerCreated(PropertyChangeListener listener, Boolean weak, WeakListenerCreationScope scope)
	{
		// nothing at this level
	}

	@Override
	public final void addPropertyChangeListener(final PropertyChangeListener listener)
	{
		addPropertyChangeListener(listener, WEAK_AUTO);
	}

	public void addPropertyChangeListener(final PropertyChangeListener listener, final Boolean weak)
	{
		final PropertyChangeListener weakListener = createWeakPropertyChangeListener(listener, weak, WeakListenerCreationScope.ForAdding);
		super.addPropertyChangeListener(weakListener);
	}

	public static final PropertyChangeListener asWeak(final PropertyChangeListener listener)
	{
		// Check.assumeNotNull(listener, "listener not null");
		// final boolean weak = true;
		// return createWeakPropertyChangeListener(listener, weak);
		return listener; // TODO: delete it
	}

	@Override
	public final void addPropertyChangeListener(final String propertyName, final PropertyChangeListener listener)
	{
		addPropertyChangeListener(propertyName, listener, WEAK_AUTO);
	}

	public void addPropertyChangeListener(final String propertyName, final PropertyChangeListener listener, final Boolean weak)
	{
		final PropertyChangeListener weakListener = createWeakPropertyChangeListener(listener, weak, WeakListenerCreationScope.ForAdding);
		super.addPropertyChangeListener(propertyName, weakListener);
	}

	@Override
	public void removePropertyChangeListener(final PropertyChangeListener listener)
	{
		super.removePropertyChangeListener(listener);

		final PropertyChangeListener weakListener = createWeakPropertyChangeListener(listener, true, WeakListenerCreationScope.ForRemoving);
		super.removePropertyChangeListener(weakListener);
	}

	@Override
	public void removePropertyChangeListener(final String propertyName, final PropertyChangeListener listener)
	{
		super.removePropertyChangeListener(propertyName, listener);

		final PropertyChangeListener weakListener = createWeakPropertyChangeListener(listener, true, WeakListenerCreationScope.ForRemoving);
		super.removePropertyChangeListener(propertyName, weakListener);
	}

	@Override
	public void firePropertyChange(PropertyChangeEvent event)
	{
		super.firePropertyChange(event);

		boolean DEBUG = false;

		if (DEBUG)
		{
			final List<String> skipPropertyNames = Arrays.asList(
					"de.metas.handlingunits.client.terminal.editor.model.IHUKey#ChildrenChanged"
					);
			if (skipPropertyNames.contains(event.getPropertyName()))
			{
				return;
			}

			if (!hasListeners(event.getPropertyName()))
			{
				// TODO remove before integrating into base line!!
				System.out.println("Dead event: " + event);
				final Object sourceObj = event.getSource();
				if (sourceObj instanceof Reference<?>)
				{
					final Object sourceUnboxed = ((Reference<?>)sourceObj).get();
					System.out.println("\t Unboxed source: " + ObjectUtils.toString(sourceUnboxed));
				}
			}
		}
	}

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}
}
