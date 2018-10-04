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


import java.beans.IndexedPropertyChangeEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

import de.metas.util.Check;

/**
 * {@link PropertyChangeSupport} like implementations which is capable to memorize events for a while, when activated by {@link #blockEvents()} and then release all together by
 * {@link #releaseEvents()}.
 * 
 * By default, this implementation is not blocking any events.
 * 
 * @author tsa
 *
 */
public final class DelayedPropertyChangeSupport
{
	private final Object source;
	private final PropertyChangeSupport delegate;

	/** Delayed events queue */
	private transient List<PropertyChangeEvent> delayedEvents;

	public DelayedPropertyChangeSupport(final Object sourceBean)
	{
		super();
		Check.assumeNotNull(sourceBean, "sourceBean not null");
		this.source = sourceBean;
		this.delegate = new PropertyChangeSupport(sourceBean);
	}

	/**
	 * Start blocking future events (if not already), until {@link #releaseEvents()} it's called.
	 */
	public void blockEvents()
	{
		if (delayedEvents == null)
		{
			delayedEvents = new ArrayList<>();
		}
	}

	/**
	 * Release(i.e. fire) all queued events.
	 * If events were not blocked or the queue is empty, this method will do nothing.
	 */
	public void releaseEvents()
	{
		final List<PropertyChangeEvent> eventsToFire = delayedEvents == null ? null : new ArrayList<>(delayedEvents);
		delayedEvents = null;

		if (eventsToFire != null && !eventsToFire.isEmpty())
		{
			for (final PropertyChangeEvent event : eventsToFire)
			{
				firePropertyChange(event);
			}
		}
	}

	public void addPropertyChangeListener(final PropertyChangeListener listener)
	{
		delegate.addPropertyChangeListener(listener);
	}

	public void removePropertyChangeListener(final PropertyChangeListener listener)
	{
		delegate.removePropertyChangeListener(listener);
	}

	public PropertyChangeListener[] getPropertyChangeListeners()
	{
		return delegate.getPropertyChangeListeners();
	}

	public void addPropertyChangeListener(final String propertyName, final PropertyChangeListener listener)
	{
		delegate.addPropertyChangeListener(propertyName, listener);
	}

	public void removePropertyChangeListener(final String propertyName, final PropertyChangeListener listener)
	{
		delegate.removePropertyChangeListener(propertyName, listener);
	}

	public PropertyChangeListener[] getPropertyChangeListeners(final String propertyName)
	{
		return delegate.getPropertyChangeListeners(propertyName);
	}

	public boolean hasListeners(final String propertyName)
	{
		return delegate.hasListeners(propertyName);
	}

	public final void firePropertyChange(final PropertyChangeEvent event)
	{
		if (delayedEvents != null)
		{
			delayedEvents.add(event);
		}
		else
		{
			delegate.firePropertyChange(event);
		}
	}

	public void firePropertyChange(final String propertyName, final Object oldValue, final Object newValue)
	{
		final PropertyChangeEvent event = new PropertyChangeEvent(source, propertyName, oldValue, newValue);
		firePropertyChange(event);
	}

	public void firePropertyChange(final String propertyName, final int oldValue, final int newValue)
	{
		final PropertyChangeEvent event = new PropertyChangeEvent(source, propertyName, oldValue, newValue);
		firePropertyChange(event);
	}

	public void firePropertyChange(final String propertyName, final boolean oldValue, final boolean newValue)
	{
		final PropertyChangeEvent event = new PropertyChangeEvent(source, propertyName, oldValue, newValue);
		firePropertyChange(event);
	}

	public void fireIndexedPropertyChange(final String propertyName, final int index, final Object oldValue, final Object newValue)
	{
		final IndexedPropertyChangeEvent event = new IndexedPropertyChangeEvent(source, propertyName, oldValue, newValue, index);
		firePropertyChange(event);
	}

	public void fireIndexedPropertyChange(final String propertyName, final int index, final int oldValue, final int newValue)
	{
		final IndexedPropertyChangeEvent event = new IndexedPropertyChangeEvent(source, propertyName, oldValue, newValue, index);
		firePropertyChange(event);
	}

	public void fireIndexedPropertyChange(final String propertyName, final int index, final boolean oldValue, final boolean newValue)
	{
		final IndexedPropertyChangeEvent event = new IndexedPropertyChangeEvent(source, propertyName, oldValue, newValue, index);
		firePropertyChange(event);
	}
}
