package  de.metas.adempiere.ait.event;

/*
 * #%L
 * de.metas.adempiere.ait
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
import java.beans.PropertyChangeSupport;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

import junit.framework.AssertionFailedError;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.Util;

public final class TestEventSupport
{
	public static class ListenerMethod
	{
		public final Object eventListener;
		public final Method eventListenerMethodToCall;

		private ListenerMethod(
				final Object eventListener,
				final Method eventListenerMethodToCall)
		{
			this.eventListenerMethodToCall = eventListenerMethodToCall;
			this.eventListener = eventListener;
		}
	}

	public TestEventSupport(final Class<? extends AIntegrationTestDriver> sourceTestClass)
	{
		propertyChangeSupport = new PropertyChangeSupport(sourceTestClass);
	}

	final private PropertyChangeSupport propertyChangeSupport;

	final private Set<ListenerMethod> uncalledMethods = new HashSet<TestEventSupport.ListenerMethod>();

	public void addTestEventMethod(
			final EventType type,
			final Object listener,
			final Method testMethod)
	{
		final ListenerMethod listenerMethod = new ListenerMethod(listener, testMethod);
		propertyChangeSupport.addPropertyChangeListener(type.toString(), new PclWrapper(listenerMethod));

		uncalledMethods.add(listenerMethod);

		System.out.println("Registered event " + type + " handled by " + testMethod);
	}

	public void fireTestEvent(
			final IITEventType eventType,
			final AIntegrationTestDriver itd,
			final Object obj)
	{
		propertyChangeSupport.firePropertyChange(eventType.toString(), itd, obj);
	}

	public Set<ListenerMethod> getUncalledMethods()
	{
		return uncalledMethods;
	}

	private class PclWrapper implements PropertyChangeListener
	{
		private final ListenerMethod listenerMethod;

		private PclWrapper(final ListenerMethod listenerMethod)
		{
			this.listenerMethod = listenerMethod;
		}

		@Override
		public void propertyChange(final PropertyChangeEvent pce)
		{
			final Object itd = pce.getOldValue();

			Util.assume(
					itd instanceof AIntegrationTestDriver,
					"pce.getOldValue()='" + itd + "' is instance of IIntegrationTestDriver");

			final TestEvent testEvent = new TestEvent(
					(AIntegrationTestDriver)pce.getOldValue(),
					EventType.valueOf(pce.getPropertyName()),
					pce.getNewValue());

			try
			{
				listenerMethod.eventListenerMethodToCall.invoke(listenerMethod.eventListener, testEvent);
				uncalledMethods.remove(listenerMethod);
			}
			catch (InvocationTargetException ite)
			{
				final Throwable cause = ite.getCause();
				if (cause instanceof java.lang.AssertionError)
				{
					throw (java.lang.AssertionError)cause;
				}
				else if (cause instanceof AssertionFailedError)
				{
					throw (AssertionFailedError)cause;
				}
				else if (cause instanceof AdempiereException)
				{
					// a test failure within a TrxRunner instance is wrapped in an AdempiereException.
					final AdempiereException adempiereException = (AdempiereException)cause;
					if (adempiereException.getCause() instanceof AssertionError)
					{
						throw (AssertionError)adempiereException.getCause();
					}
					throw adempiereException;
				}
				else
				{
					throw new RuntimeException(cause);
				}
			}
			catch (IllegalArgumentException e)
			{
				throw new RuntimeException(e);
			}
			catch (IllegalAccessException e)
			{
				throw new RuntimeException(e);
			}
		}

	}
}
