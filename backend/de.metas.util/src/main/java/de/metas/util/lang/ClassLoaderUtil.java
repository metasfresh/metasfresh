/*
 * #%L
 * de.metas.util
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.util.lang;

import de.metas.util.Check;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

import javax.annotation.Nullable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

@UtilityClass
public class ClassLoaderUtil
{
	public Class<?> validateJavaClassname(
			@NonNull final String classname,
			@Nullable final Class<?> parentClass)
	{
		if (Check.isBlank(classname))
		{
			throw new RuntimeException("Given classname is blank");
		}
		ClassLoader cl = Thread.currentThread().getContextClassLoader();
		if (cl == null)
		{
			cl = ClassLoaderUtil.class.getClassLoader();
		}

		final Class<?> clazz;
		try
		{
			clazz = cl.loadClass(classname);
		}
		catch (final ClassNotFoundException e)
		{
			throw new RuntimeException("Classname not found: " + classname, e);
		}

		if (parentClass != null)
		{
			if (!parentClass.isAssignableFrom(clazz))
			{
				throw new RuntimeException("Class " + clazz + " is not assignable from " + parentClass);
			}
		}

		return clazz;
	}

	public <T> T newInstanceFromNoArgConstructor(@NonNull final Class<T> clazz)
	{
		final Constructor<T> declaredConstructor;

		try
		{
			declaredConstructor = clazz.getDeclaredConstructor();
		}
		catch (final NoSuchMethodException e)
		{
			throw new RuntimeException("Class " + clazz + " does not have a no-arg-constructor" + e);
		}

		try
		{
			return declaredConstructor.newInstance();
		}
		catch (final InstantiationException | IllegalAccessException | InvocationTargetException e)
		{
			throw new RuntimeException("The constructor " + declaredConstructor + " of class " + clazz + " could not be executed", e);
		}
	}
}
