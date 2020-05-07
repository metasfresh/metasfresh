package org.adempiere.ad.persistence;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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


import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;

import javax.annotation.Nullable;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.collections.IdentityHashSet;

/**
 * Used to fetch {@link IModelClassInfo}s from model classes.
 *
 * @author tsa
 *
 */
public final class ModelClassIntrospector implements IModelClassIntrospector
{
	public static final IModelClassIntrospector instance = new ModelClassIntrospector();

	public static final IModelClassIntrospector getInstance()
	{
		return instance;
	}

	private final Map<Class<?>, IModelClassInfo> class2info = new IdentityHashMap<>(500);
	private final Set<Class<?>> classesWhichAreNotModels = new IdentityHashSet<>(100);
	private final ReentrantLock lock = new ReentrantLock();

	private ModelClassIntrospector()
	{
		super();
	}

	@Override
	public IModelClassInfo getModelClassInfo(@Nullable final Class<?> clazz)
	{
		if (clazz == null)
		{
			return null;
		}

		lock.lock();
		try
		{
			//
			// Check if we already have cached the model
			IModelClassInfo modelClassInfo = class2info.get(clazz);
			if (modelClassInfo != null)
			{
				return modelClassInfo;
			}

			//
			// Check if our class is on black list
			if (classesWhichAreNotModels.contains(clazz))
			{
				return null;
			}

			//
			// Introspect the class and create it's model info
			modelClassInfo = createModelClassInfo(clazz);
			if (modelClassInfo == null)
			{
				// If class could not be introspected then it's not a model, so we are adding it to black list
				classesWhichAreNotModels.add(clazz);
			}
			else
			{
				// add model info to cache
				class2info.put(clazz, modelClassInfo);
			}

			return modelClassInfo;
		}
		finally
		{
			lock.unlock();
		}
	}

	private final IModelClassInfo createModelClassInfo(final Class<?> clazz)
	{
		final String tableName = getTableNameOrNull(clazz);
		if (tableName != null || clazz.isInterface())
		{
			final IModelClassInfo modelClassInfo = new ModelClassInfo(this, clazz, tableName);
			return modelClassInfo;
		}

		return null;
	}

	public Map<Method, IModelMethodInfo> createModelMethodInfos(final Class<?> clazz)
	{
		final Map<Method, IModelMethodInfo> modelMethodsInfo = new HashMap<>();
		for (final Method method : clazz.getMethods())
		{
			final IModelMethodInfo modelMethodInfo = createModelMethodInfo(method);
			modelMethodsInfo.put(method, modelMethodInfo);
		}

		return modelMethodsInfo;
	}

	/**
	 * Creates {@link IModelMethodInfo} for given <code>method</code>
	 *
	 * @param method
	 * @return method info; never return null
	 */
	public IModelMethodInfo createModelMethodInfo(final Method method)
	{
		String methodName = method.getName();
		final Class<?>[] parameters = method.getParameterTypes();
		final int parametersCount = parameters == null ? 0 : parameters.length;

		if (methodName.startsWith("set") && parametersCount == 1)
		{
			final String propertyName = methodName.substring(3); // method name without "set" prefix
			final Class<?> paramType = parameters[0];
			if (InterfaceWrapperHelper.isModelInterface(paramType))
			{
				final ModelSetterMethodInfo methodInfo = new ModelSetterMethodInfo(method, paramType, propertyName + "_ID");
				return methodInfo;
			}
			else
			{
				final ValueSetterMethodInfo methodInfo = new ValueSetterMethodInfo(method, propertyName);
				return methodInfo;
			}
		}
		else if (methodName.startsWith("get") && (parametersCount == 0))
		{
			String propertyName = methodName.substring(3);

			if (InterfaceWrapperHelper.isModelInterface(method.getReturnType()))
			{
				final String columnName = propertyName + "_ID";
				final ModelGetterMethodInfo methodInfo = new ModelGetterMethodInfo(method, columnName);
				return methodInfo;
			}
			else
			{
				final ValueGetterMethodInfo methodInfo = new ValueGetterMethodInfo(method, propertyName);
				return methodInfo;
			}
		}
		else if (methodName.startsWith("is") && (parametersCount == 0))
		{
			final String propertyName = methodName.substring(2);
			final BooleanGetterMethodInfo methodInfo = new BooleanGetterMethodInfo(method, propertyName);
			return methodInfo;
		}
		else if (methodName.equals("equals") && parametersCount == 1)
		{
			final EqualsMethodInfo methodInfo = new EqualsMethodInfo(method);
			return methodInfo;
		}
		else
		{
			final InvokeParentMethodInfo methodInfo = new InvokeParentMethodInfo(method);
			return methodInfo;
		}
	}

	private static final String getTableNameOrNull(final Class<?> clazz)
	{
		try
		{
			final Field field = clazz.getField("Table_Name");
			if (!field.isAccessible())
			{
				field.setAccessible(true);
			}
			return (String)field.get(null);
		}
		catch (final Exception e)
		{
			return null;
		}
	}

}
