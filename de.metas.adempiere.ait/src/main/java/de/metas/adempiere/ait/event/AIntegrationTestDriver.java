package de.metas.adempiere.ait.event;

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


import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.slf4j.Logger;
import de.metas.logging.LogManager;
import org.compiere.util.Trx;
import org.compiere.util.Util;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;

import de.metas.adempiere.ait.helper.IHelper;
import de.metas.adempiere.ait.test.IntegrationTestSuite;
import de.metas.adempiere.ait.test.annotation.ITEventListener;

/**
 * Abstract base class for all integration test drivers.
 * 
 * An integration test driver is supposed to perform actions on the system and fire {@link TestEvent}s. It is called by
 * the default junit test runner.
 * 
 * Note: Why is there no interface for test drivers to implement? Because
 * <ul>
 * <li>the listeners are configured by {@link IntegrationTestSuite} <strong>before</strong> we have instances. Therefore
 * they need to be put in static fields (see {@link #driver2eventSupport}).</li>
 * <li>We can't declare static fields in an interface, so we can't force implementers of the interface to be "correct"
 * test drivers.</li>
 * </ul>
 * 
 * 
 * @author ts
 * @see IntegrationTestSuite
 * @see ITEventListener
 */
public abstract class AIntegrationTestDriver
{
	public final Logger logger = LogManager.getLogger(getClass());

	private IHelper _helper = null;

	public static Map<Class<? extends AIntegrationTestDriver>, TestEventSupport> driver2eventSupport =
			new HashMap<Class<? extends AIntegrationTestDriver>, TestEventSupport>();

	@Before
	public void setupAdempiere()
	{
		// make sure helper is loaded
		Assert.assertNotNull("Helper could not be loaded", getHelper());

		fireTestEvent(EventType.TESTDRIVER_STARTED, null);
	}

	public abstract IHelper newHelper();

	@After
	public void cleanUp() throws Exception
	{
		if (_helper != null)
		{
			_helper.finish();
			_helper = null;
		}
	}

	public Properties getCtx()
	{
		return getHelper().getCtx();
	}

	public String getTrxName()
	{
		return getHelper().getTrxName();
	}

	public Trx getTrx()
	{
		return getHelper().getTrx();
	}

	public IHelper getHelper()
	{
		if (_helper == null)
		{
			_helper = newHelper();
			_helper.init();			
		}
		return _helper;
	}

	/**
	 * Method to be called from {@link IntegrationTestSuite}. Sets the given event support for the given test driver
	 * class.
	 * 
	 * @param clazz
	 * @param testEventSupport
	 */
	public static void setTestEventSupport(final Class<? extends AIntegrationTestDriver> clazz, final TestEventSupport testEventSupport)
	{
		Util.assume(testEventSupport != null, "Param 'testEventSupport' is not null");
		if (driver2eventSupport.containsKey(clazz))
		{
			throw new AdempiereException("Another testEventSupport has already been registered for class " + clazz);
		}
		driver2eventSupport.put(clazz, testEventSupport);
	}

	/**
	 * Method makes sure that all test event listeners were called at least once.
	 * 
	 * Call this method from your test drivers static after class method.
	 */
	public static void assertAllListenersCalled(final Class<? extends AIntegrationTestDriver> clazz)
	{
		final TestEventSupport testEventSupport = driver2eventSupport.get(clazz);

		final StringBuilder uncalledListeners = new StringBuilder();

		for (final TestEventSupport.ListenerMethod listenerMethod : testEventSupport.getUncalledMethods())
		{
			if (uncalledListeners.length() > 0)
			{
				uncalledListeners.append(" ,");
			}
			uncalledListeners.append("[Class=" + listenerMethod.eventListener.getClass());
			uncalledListeners.append("; Method=" + listenerMethod.eventListenerMethodToCall.getName() + "]");
		}

		if (uncalledListeners.length() > 0)
		{
			Assert.fail("The following test event listeners were not called: " + uncalledListeners.toString());
		}
	}

	public static TestEventSupport getTES(final Class<? extends AIntegrationTestDriver> clazz)
	{
		return driver2eventSupport.get(clazz);
	}

	public void fireTestEvent(final IITEventType eventType, final Object obj)
	{
		final TestEventSupport tes = getTES(this.getClass());
		if (tes != null)
		{
			// note: if a test event is called with the actual driver, we call the underlying PropertyChangeSupport with
			// Null. Otherwise the event would be ignored.
			final Object objParam = this.equals(obj) ? null : obj;

			tes.fireTestEvent(eventType, this, objParam);
		}
		else
		{
			logger.info("No TestEventSupport was registered for " + this);
		}
	}
	
	@Before
	public void setup()
	{
		// we are not setting the trxName, because as of now there are too many pieces of 
		// ait-code that use local transactions, and we don't have the time to get on top of this
//		if (getHelper().getTrxName() == null)
//		{
//			getHelper().setTrxName("Trx_" + getClass().getSimpleName());
//		}
	}
	
	@After
	public void tearDown()
	{
		getHelper().commitTrx(false);
	}
}
