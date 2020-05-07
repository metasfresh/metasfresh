package org.adempiere.util.reflect;

import java.util.HashMap;
import java.util.Map;

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
/**
 * This implementation can be used for testing. It delegates to {@link ClassInstanceProvider} to do the actual work, but has additional methods to emulate exceptions.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class TestingClassInstanceProvider implements IClassInstanceProvider
{
	public static final TestingClassInstanceProvider instance = new TestingClassInstanceProvider();

	private IClassInstanceProvider internalImpl = ClassInstanceProvider.instance;

	private TestingClassInstanceProvider()
	{
	}

	/**
	 * See {@link #throwExceptionForClassName(String, RuntimeException)}.
	 */
	private final Map<String, ReflectiveOperationException> className2Exception = new HashMap<String, ReflectiveOperationException>();

	/**
	 * Allows unit tests to set exceptions that shall be thrown if certain classes are loaded using {@link #getInstance(Class, String)} and {@link #getInstanceOrNull(Class, String)}.
	 * 
	 * @param className
	 * @param exception
	 */
	public void throwExceptionForClassName(final String className, final ReflectiveOperationException exception)
	{
		className2Exception.put(className, exception);
	}

	/**
	 * See {@link #throwExceptionForClassName(String, RuntimeException)}.
	 */
	public void clearExceptionsForClassNames()
	{
		className2Exception.clear();
	}

	@Override
	public Class<?> provideClass(String className) throws ReflectiveOperationException
	{
		// for unit testing: allow us to test with class loading failures.
		if (className2Exception.containsKey(className))
		{
			throw className2Exception.get(className);
		}
		return internalImpl.provideClass(className);
	}

	@Override
	public <T> T provideInstance(Class<T> interfaceClazz, Class<?> instanceClazz) throws ReflectiveOperationException
	{
		// for unit testing: allow us to test with class loading failures.
		if (className2Exception.containsKey(instanceClazz.getName()))
		{
			throw className2Exception.get(instanceClazz.getName());
		}
		return internalImpl.provideInstance(interfaceClazz, instanceClazz);
	}
}
