/*
 * #%L
 * de.metas.util
 * %%
 * Copyright (C) 2024 metas GmbH
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

package org.adempiere.util.reflect;

import de.metas.util.Check;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * To be used when it comes to class loading. Can be extended/overridden for testing.
 *
 * @author metas-dev <dev@metasfresh.com>
 */
public class ClassInstanceProvider implements IClassInstanceProvider
{
	public static final ClassInstanceProvider instance = new ClassInstanceProvider();

	private ClassInstanceProvider()
	{
	}

	@Override
	public Class<?> provideClass(final String className) throws ClassNotFoundException
	{
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		if (classLoader == null)
		{
			classLoader = ClassInstanceProvider.class.getClassLoader();
		}
		return classLoader.loadClass(className);
	}

	@Override
	public <T> T provideInstance(final Class<T> interfaceClazz, final Class<?> instanceClazz) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException
	{
		Check.errorUnless(interfaceClazz.isAssignableFrom(instanceClazz), "Class {} doesn't implement {}", instanceClazz, interfaceClazz);

		final Constructor<?> constructor = instanceClazz.getDeclaredConstructor();
		constructor.setAccessible(true);
		//noinspection unchecked
		return (T)constructor.newInstance();
	}
}
