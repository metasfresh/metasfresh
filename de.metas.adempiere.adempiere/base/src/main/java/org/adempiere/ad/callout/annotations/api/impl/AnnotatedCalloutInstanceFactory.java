package org.adempiere.ad.callout.annotations.api.impl;

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

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.ad.callout.api.ICalloutField;
import org.adempiere.ad.callout.exceptions.CalloutInitException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.slf4j.Logger;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ListMultimap;

import de.metas.logging.LogManager;

/**
 * Inspects a given annotated callout object and creates {@link AnnotatedCalloutInstance}s.
 *
 * @author tsa
 *
 */
public final class AnnotatedCalloutInstanceFactory
{
	// services
	private final transient Logger logger = LogManager.getLogger(getClass());

	private Object annotatedCalloutObject;
	private String tableName;
	private Callout.RecursionAvoidanceLevel recursionAvoidanceLevel;
	private final Set<String> columnNames = new HashSet<String>();

	private final transient ListMultimap<CalloutMethodPointcutKey, CalloutMethodPointcut> mapPointcuts = ArrayListMultimap.create();

	public List<AnnotatedCalloutInstance> create()
	{
		loadAnnotatedClassDefinition();
		loadPointcuts();

		if (mapPointcuts.isEmpty())
		{
			return Collections.emptyList();
		}

		final List<AnnotatedCalloutInstance> calloutInstances = new ArrayList<>();

		if (Callout.RecursionAvoidanceLevel.CalloutClass == recursionAvoidanceLevel)
		{
			final String id = annotatedCalloutObject.getClass().getName();
			final AnnotatedCalloutInstance calloutInstance = createCalloutInstance(id, columnNames, mapPointcuts);
			calloutInstances.add(calloutInstance);
		}
		else if (Callout.RecursionAvoidanceLevel.CalloutField == recursionAvoidanceLevel)
		{
			for (final CalloutMethodPointcutKey key : mapPointcuts.keySet())
			{
				final String columnName = key.getColumnName();
				final List<CalloutMethodPointcut> pointcuts = mapPointcuts.get(key);
				if (pointcuts.isEmpty())
				{
					continue;
				}

				final String id = annotatedCalloutObject.getClass().getName() + "#ColumnName=" + columnName;
				final Set<String> columnNames = ImmutableSet.of(columnName);
				final ListMultimap<CalloutMethodPointcutKey, CalloutMethodPointcut> instancePointcuts = ImmutableListMultimap.<CalloutMethodPointcutKey, CalloutMethodPointcut> builder()
						.putAll(key, pointcuts)
						.build();
				final AnnotatedCalloutInstance calloutInstance = createCalloutInstance(id, columnNames, instancePointcuts);
				calloutInstances.add(calloutInstance);
			}
		}
		else if (Callout.RecursionAvoidanceLevel.CalloutMethod == recursionAvoidanceLevel)
		{
			for (final Map.Entry<CalloutMethodPointcutKey, CalloutMethodPointcut> e : mapPointcuts.entries())
			{
				final CalloutMethodPointcutKey key = e.getKey();
				final String columnName = key.getColumnName();
				final CalloutMethodPointcut pointcut = e.getValue();

				final String id = annotatedCalloutObject.getClass().getName() + "#Method=" + pointcut.getMethod().getName();
				final ImmutableSet<String> columnNames = ImmutableSet.of(columnName);
				final ListMultimap<CalloutMethodPointcutKey, CalloutMethodPointcut> instancePointcuts = ImmutableListMultimap.of(key, pointcut);
				final AnnotatedCalloutInstance calloutInstance = createCalloutInstance(id, columnNames, instancePointcuts);
				calloutInstances.add(calloutInstance);
			}
		}
		else
		{
			throw new CalloutInitException("Unknown recursion RecursionAvoidanceLevel: " + recursionAvoidanceLevel);
		}

		return calloutInstances;
	}

	private final AnnotatedCalloutInstance createCalloutInstance(final String id,
			final Set<String> columnNames,
			final ListMultimap<CalloutMethodPointcutKey, CalloutMethodPointcut> mapPointcuts)
	{
		return new AnnotatedCalloutInstance(id, tableName, columnNames, annotatedCalloutObject, mapPointcuts);
	}

	public AnnotatedCalloutInstanceFactory setAnnotatedCalloutObject(final Object annotatedCalloutObject)
	{
		this.annotatedCalloutObject = annotatedCalloutObject;
		return this;
	}

	private final Object getAnnotatedCalloutObject()
	{
		Check.assumeNotNull(annotatedCalloutObject, "annotatedCalloutObj not null");
		return annotatedCalloutObject;
	}

	private final Class<?> getAnnotatedClass()
	{
		return getAnnotatedCalloutObject().getClass();
	}

	private void loadAnnotatedClassDefinition()
	{
		final Class<?> annotatedClass = getAnnotatedClass();
		final Callout annCallout = annotatedClass.getAnnotation(Callout.class);
		if (annCallout == null)
		{
			throw new CalloutInitException("Each callout class shall be marked with " + Callout.class + " annotation");
		}

		tableName = InterfaceWrapperHelper.getTableNameOrNull(annCallout.value());
		if (tableName == null)
		{
			throw new CalloutInitException("Cannot find tableName for " + annCallout.value() + " on " + annotatedClass);
		}

		//
		// Validate Callout classname and:
		// * throw exception (if development mode)
		// * log warning if production mode
		if (!tableName.equals(annotatedClass.getSimpleName()))
		{
			new CalloutInitException("According to metas best practices,"
					+ "Callouts shall have the same name as the table."
					+ " Please rename class " + annotatedClass + " to " + tableName)
							.throwIfDeveloperModeOrLogWarningElse(logger);
		}

		recursionAvoidanceLevel = annCallout.recursionAvoidanceLevel();
		if (recursionAvoidanceLevel == null)
		{
			throw new CalloutInitException("Invalid " + Callout.RecursionAvoidanceLevel.class + " for " + annotatedClass);
		}
	}

	private void loadPointcuts()
	{
		final Class<?> annotatedClass = getAnnotatedClass();
		for (final Method method : annotatedClass.getDeclaredMethods())
		{
			final CalloutMethod annCalloutMethod = method.getAnnotation(CalloutMethod.class);
			if (annCalloutMethod != null)
			{
				loadPointcut(annCalloutMethod, method);
			}
		}
	}

	private void loadPointcut(final CalloutMethod annModelChange, final Method method)
	{
		final CalloutMethodPointcut pointcut = createPointcut(annModelChange, method);
		addPointcutToMap(pointcut);
	}

	private CalloutMethodPointcut createPointcut(final CalloutMethod annModelChange, final Method method)
	{
		if (method.getReturnType() != void.class)
		{
			throw new CalloutInitException("Return type should be void for " + method);
		}

		final Class<?>[] parameterTypes = method.getParameterTypes();
		if (parameterTypes == null || parameterTypes.length == 0)
		{
			throw new CalloutInitException("Invalid method " + method + ": It has no arguments");
		}

		if (parameterTypes.length < 1 || parameterTypes.length > 2)
		{
			throw new CalloutInitException("Invalid method " + method + ": It shall have following paramters: model and optionally ICalloutField");
		}

		//
		// Validate parameter 1: Model Class for binding
		final Class<?> modelClass = parameterTypes[0];
		{
			//
			// Validate Model Class's TableName
			final String tableName = InterfaceWrapperHelper.getTableNameOrNull(modelClass);
			if (Check.isEmpty(tableName))
			{
				throw new CalloutInitException("Cannot find tablename for " + modelClass);
			}
			if (tableName != null && !tableName.contains(tableName))
			{
				throw new CalloutInitException("Table " + tableName + " is not in the list of allowed tables names specified by " + Callout.class + " annotation: " + tableName);
			}
		}

		//
		// Validate parameter 2: ICalloutField
		final boolean paramCalloutFieldRequired;
		if (parameterTypes.length >= 2)
		{
			if (!ICalloutField.class.isAssignableFrom(parameterTypes[1]))
			{
				throw new CalloutInitException("Invalid method " + method + ": second parameter shall be " + ICalloutField.class + " or not specified at all");
			}
			paramCalloutFieldRequired = true;
		}
		else
		{
			paramCalloutFieldRequired = false;
		}

		final CalloutMethodPointcut pointcut = new CalloutMethodPointcut(modelClass,
				method,
				annModelChange.columnNames(),
				paramCalloutFieldRequired,
				annModelChange.skipIfCopying());
		return pointcut;
	}

	private void addPointcutToMap(final CalloutMethodPointcut pointcut)
	{
		//
		// Add pointcut to map
		for (final String columnName : pointcut.getColumnNames())
		{
			final CalloutMethodPointcutKey key = CalloutMethodPointcutKey.of(columnName);
			mapPointcuts.put(key, pointcut);
			columnNames.add(columnName);
		}

		logger.debug("Loaded {}", pointcut);
	}
}
