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
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

import org.adempiere.util.Check;
import org.adempiere.util.StringUtils;

/* package */final class WeakPropertyChangeListener implements PropertyChangeListener
{
	private final Reference<PropertyChangeListener> delegateRef;
	private final PropertyChangeListener delegate;

	public WeakPropertyChangeListener(final PropertyChangeListener listener, final boolean weak)
	{
		super();
		Check.assumeNotNull(listener, "listener not null");
		if (weak)
		{
			delegateRef = new WeakReference<PropertyChangeListener>(listener);
			delegate = null;
		}
		else
		{
			delegateRef = null;
			delegate = listener;
		}
	}

	@Override
	public int hashCode()
	{
		final PropertyChangeListener delegate = getDelegate();

		final int prime = 31;
		int result = 1;
		result = prime * result + (delegate == null ? 0 : delegate.hashCode());
		return result;
	}

	/**
	 * This equal method basically compares <code>this</code> instance's delegate to the given <code>obj</code>'s deleget (ofc only if that given object is also a WeakpropertyChangeListener).
	 * <p>
	 * NOTE: please pay attention to this method because based on this, the Listener will be removed or not
	 *
	 * @see org.adempiere.util.beans.WeakPropertyChangeSupport#removePropertyChangeListener(PropertyChangeListener)
	 * @see org.adempiere.util.beans.WeakPropertyChangeSupport#removePropertyChangeListener(String, PropertyChangeListener)
	 */
	@Override
	public boolean equals(final Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (obj == null)
		{
			return false;
		}
		if (getClass() != obj.getClass())
		{
			return false;
		}
		final WeakPropertyChangeListener other = (WeakPropertyChangeListener)obj;

		final PropertyChangeListener thisDelegate = getDelegate();
		final PropertyChangeListener otherDelegate = other.getDelegate();

		if (thisDelegate == null)
		{
			if (otherDelegate != null)
			{
				return false;
			}
		}
		else if (!thisDelegate.equals(otherDelegate))
		{
			return false;
		}

		return true;
	}

	public boolean isWeak()
	{
		return delegate == null;
	}

	public PropertyChangeListener getDelegate()
	{
		if (delegate != null)
		{
			return delegate;
		}

		final PropertyChangeListener delegate = delegateRef.get();
		return delegate;
	}

	@Override
	public void propertyChange(final PropertyChangeEvent evt)
	{
		final boolean DEBUG = false;

		final PropertyChangeListener delegate = getDelegate();
		if (delegate == null)
		{
			// delegate reference expired
			if (DEBUG)
			{
				// TODO remove before integrating into base line!!
				System.out.println(StringUtils.formatMessage("delegate of {0} is expired", this));
			}
			return;
		}

		final Object source = evt.getSource();

		final PropertyChangeEvent evtNew;
		if (source instanceof Reference)
		{
			final Reference<?> sourceRef = (Reference<?>)source;
			final Object sourceObj = sourceRef.get();
			if (sourceObj == null)
			{
				// reference expired
				if (DEBUG)
				{
				// TODO remove before integrating into base line!!
				System.out.println(StringUtils.formatMessage("sourceObj of {0} is expired", this));
				}
				return;
			}

			evtNew = new PropertyChangeEvent(sourceObj, evt.getPropertyName(), evt.getOldValue(), evt.getNewValue());
			evtNew.setPropagationId(evt.getPropagationId());
		}
		else
		{
			evtNew = evt;
		}

		delegate.propertyChange(evtNew);
	}

	@Override
	public String toString()
	{
		return "WeakPropertyChangeListener [delegate=" + delegate + "]";
	}

}
