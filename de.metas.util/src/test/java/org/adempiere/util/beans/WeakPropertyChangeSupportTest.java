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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeListenerProxy;

import org.adempiere.util.beans.WeakPropertyChangeSupportTest.MockedPropertyChangeListener;
import org.junit.Assert;
import org.junit.Test;

public class WeakPropertyChangeSupportTest
{
	private final Object sourceBean = new Object();

	public static class MockedPropertyChangeListener implements PropertyChangeListener
	{
		private boolean executed = false;

		@Override
		public void propertyChange(PropertyChangeEvent evt)
		{
			this.executed = true;
		}

		public void resetExecuted()
		{
			this.executed = false;
		}

		public boolean isExecuted()
		{
			return executed;
		}

		public void assertExecuted()
		{
			Assert.assertTrue("Listener " + this + " should have been executed", executed);
		}

		public void assertNotExecuted()
		{
			Assert.assertFalse("Listener " + this + " shouldn't have been executed", executed);
		}
	}

	private void assertEmpty(final WeakPropertyChangeSupport pcs)
	{
		Assert.assertNotNull("pcs not null", pcs);

		final PropertyChangeListener[] listeners = pcs.getPropertyChangeListeners();
		Assert.assertTrue("No listeners shall be registered but they are: " + listeners, listeners == null || listeners.length == 0);
	}

	@Test
	public void test_add_remove()
	{
		for (final boolean weakDefault : new Boolean[] { false, true })
		{
			for (final boolean addWeak : new Boolean[] { false, true })
			{
				final WeakPropertyChangeSupport pcs = new WeakPropertyChangeSupport(sourceBean, weakDefault);
				final MockedPropertyChangeListener listener = new MockedPropertyChangeListener();

				final TestAddRemoveParameters parameters = new TestAddRemoveParameters(pcs, listener, addWeak);
				testAddRemoveListener(parameters);
			}
		}
	}

	private void testAddRemoveListener(final TestAddRemoveParameters parameters)
	{
		final WeakPropertyChangeSupport pcs = parameters.getPcs();
		final MockedPropertyChangeListener listener = parameters.getListener();
		listener.resetExecuted(); // make sure is reset before we start the test
		listener.assertNotExecuted();

		//
		// Add listener
		pcs.addPropertyChangeListener(parameters.getListener(), parameters.isAddWeak());

		//
		// guard: Make sure listener was correctly added
		pcs.firePropertyChange("DummyProperty1", false, true);
		listener.assertExecuted();

		//
		// Remove listener
		pcs.removePropertyChangeListener(parameters.getListener());

		//
		// Make sure listener was correctly removed
		listener.resetExecuted();
		pcs.firePropertyChange("DummyProperty2", false, true);
		listener.assertNotExecuted(); // this is the actual test
		assertEmpty(pcs);
	}

	@Test
	public void testAddPropertyChangeListenerProxy()
	{
		final WeakPropertyChangeSupport pcs = new WeakPropertyChangeSupport(sourceBean, true);

		final MockedPropertyChangeListener listener = new MockedPropertyChangeListener();
		listener.assertNotExecuted();

		//
		// Add listener
		final String propertyName = "DummyProperty1";
		pcs.addPropertyChangeListener(propertyName, listener);

		//
		// guard: Make sure listener was correctly added
		listener.resetExecuted();
		pcs.firePropertyChange(propertyName, false, true);
		listener.assertExecuted();
		//
		listener.resetExecuted();
		pcs.firePropertyChange(propertyName + "_OTHER", false, true);
		listener.assertNotExecuted();

		// Take a snapshot of currently registered listeners
		final PropertyChangeListener[] pcsListeners = pcs.getPropertyChangeListeners();
		Assert.assertEquals("Invalid listeners length", 1, pcsListeners.length);
		Assert.assertTrue("Listener shall be a proxy: " + pcsListeners[0], pcsListeners[0] instanceof PropertyChangeListenerProxy);

		//
		// Remove all listeners
		pcs.clear();
		assertEmpty(pcs);

		//
		// guard: make sure our listener is not invoked
		listener.resetExecuted();
		pcs.firePropertyChange(propertyName, false, true);
		listener.assertNotExecuted();

		//
		// Re-add all listeners
		for (final PropertyChangeListener l : pcsListeners)
		{
			pcs.addPropertyChangeListener(l);
		}

		//
		// guard: Make sure listener was correctly re-added
		listener.resetExecuted();
		pcs.firePropertyChange(propertyName, false, true);
		listener.assertExecuted();
		//
		listener.resetExecuted();
		pcs.firePropertyChange(propertyName + "_OTHER", false, true);
		listener.assertNotExecuted();

		//
		// clear all
		pcs.clear();
		assertEmpty(pcs);
	}
};

class TestAddRemoveParameters
{
	private WeakPropertyChangeSupport pcs;
	private MockedPropertyChangeListener listener;
	private final boolean addWeak;

	public TestAddRemoveParameters(WeakPropertyChangeSupport pcs, MockedPropertyChangeListener listener, boolean addWeak)
	{
		this.pcs = pcs;
		this.listener = listener;
		this.addWeak = addWeak;
	}

	public WeakPropertyChangeSupport getPcs()
	{
		return pcs;
	}

	public MockedPropertyChangeListener getListener()
	{
		return listener;
	}

	public boolean isAddWeak()
	{
		return addWeak;
	}

}
