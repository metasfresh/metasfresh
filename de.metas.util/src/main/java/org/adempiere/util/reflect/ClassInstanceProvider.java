package org.adempiere.util.reflect;

import org.adempiere.util.Check;

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
 * To be used when it comes to class loading. Can be extended/overridden for testing.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
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
		final Class<?> clazz = classLoader.loadClass(className);
		return clazz;
	}

	@Override
	public <T> T provideInstance(final Class<T> interfaceClazz, final Class<?> instanceClazz) throws InstantiationException, IllegalAccessException
	{
		Check.errorUnless(interfaceClazz.isAssignableFrom(instanceClazz), "Class {} doesn't implement {}", instanceClazz, interfaceClazz);

		return instanceClazz
				.asSubclass(interfaceClazz)
				.newInstance();
	}
}
