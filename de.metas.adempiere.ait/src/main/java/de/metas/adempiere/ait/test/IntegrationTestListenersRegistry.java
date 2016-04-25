package de.metas.adempiere.ait.test;

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


import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.compiere.util.Util;
import org.junit.runners.Suite.SuiteClasses;
import org.junit.runners.model.InitializationError;

import de.metas.adempiere.ait.event.AIntegrationTestDriver;
import de.metas.adempiere.ait.event.EventType;
import de.metas.adempiere.ait.event.TestEventSupport;
import de.metas.adempiere.ait.test.annotation.ITEventListener;

/**
 * Integration Test Listeners Registry
 * 
 * @author tsa
 * 
 */
public class IntegrationTestListenersRegistry
{
	public static final String DEFAULT_TestSuiteClassname = "test.integration.AllTests";

	private static final IntegrationTestListenersRegistry instance = new IntegrationTestListenersRegistry();

	public static IntegrationTestListenersRegistry get()
	{
		return instance;
	}

	private List<Class<?>> registeredAnnotatedClasses = new ArrayList<Class<?>>();
	private final Map<Class<?>, Object> listenerInstances = new HashMap<Class<?>, Object>();
	private final List<Class<?>> listenerClasses = new ArrayList<Class<?>>();

	private IntegrationTestListenersRegistry()
	{
		registerDefaults();
	}

	public void registerDefaults()
	{
		try
		{
			registerFromAnnotatedClass(DEFAULT_TestSuiteClassname);
		}
		catch (InitializationError e)
		{
			throw new RuntimeException(e);
		}
	}

	public boolean registerFromAnnotatedClass(String classname) throws InitializationError
	{
		ClassLoader cl = getClass().getClassLoader();
		if (cl == null)
			cl = Thread.currentThread().getContextClassLoader();

		final Class<?> clazz;
		try
		{
			clazz = cl.loadClass(classname);
		}
		catch (ClassNotFoundException e)
		{
			return false;
		}

		return registerFromAnnotatedClass(clazz);
	}

	public boolean registerFromAnnotatedClass(Class<?> annotatedClass) throws InitializationError
	{
		Util.assume(annotatedClass != null, "anntatedClass is null");
		if (registeredAnnotatedClasses.contains(annotatedClass))
		{
			return false;
		}

		final IntegrationTestListeners annListeners = annotatedClass.getAnnotation(IntegrationTestListeners.class);
		if (annListeners != null)
		{
			Class<?>[] listenerClasses = annListeners.value();
			if (listenerClasses == null || listenerClasses.length == 0)
			{
				throw new InitializationError("No listeners specified on " + annListeners + " for " + annotatedClass);
			}

			for (Class<?> listenerClass : listenerClasses)
				registerListenerClass(listenerClass);
		}

		registeredAnnotatedClasses.add(annotatedClass);

		final SuiteClasses annSuiteClasses = annotatedClass.getAnnotation(SuiteClasses.class);
		if (annSuiteClasses != null)
		{
			Class<?>[] suiteClasses = annSuiteClasses.value();
			if (suiteClasses != null)
			{
				for (Class<?> suiteClass : suiteClasses)
				{
					registerFromAnnotatedClass(suiteClass);
				}
			}
		}

		return true;
	}

	public void registerListenerClass(Class<?> listenerClass)
	{
		if (listenerClasses.contains(listenerClass))
			return;

		try
		{
			installListener(listenerClass);
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
		listenerClasses.add(listenerClass);
		System.out.println("Registered listener class: " + listenerClass);
	}

	private void installListener(Class<?> listenerClass) throws Exception
	{
		for (final Method method : listenerClass.getMethods())
		{
			final ITEventListener annotation = method.getAnnotation(ITEventListener.class);
			if (annotation == null)
			{
				continue;
			}

			final Class<? extends AIntegrationTestDriver> driverClass = annotation.driver();
			if (driverClass == null)
			{
				throw new InitializationError("No driver found for annotation " + annotation + " on method " + method);
			}

			final EventType[] types = annotation.eventTypes();

			TestEventSupport testEventSupport = AIntegrationTestDriver.getTES(driverClass);
			if (testEventSupport == null)
			{
				testEventSupport = new TestEventSupport(driverClass);
				AIntegrationTestDriver.setTestEventSupport(driverClass, testEventSupport);
			}
			
			Object listener = listenerInstances.get(listenerClass);
			if (listener == null)
			{
				listener = listenerClass.newInstance();
				listenerInstances.put(listenerClass, listener);
			}

			if (types.length == 0)
			{
				throw new RuntimeException("Types is empty for annotation " + annotation + " on method " + method);
			}
			else
			{
				for (final EventType type : types)
				{
					testEventSupport.addTestEventMethod(type, listener, method);
				}
			}
		}

	}
}
