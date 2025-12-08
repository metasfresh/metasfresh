package org.adempiere.ad.modelvalidator;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import com.google.common.collect.ComparisonChain;
import com.google.common.collect.ImmutableSet;
import com.google.common.primitives.Ints;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.ad.service.IDeveloperModeBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.EqualsBuilder;

import java.lang.reflect.Method;

@Getter
/* package */final class Pointcut implements Comparable<Pointcut>
{
	private final String pointcutId;
	private final PointcutType type;
	private final Method method;
	private final ImmutableSet<Integer> timings;
	private final boolean afterCommit;
	private final String tableName;
	private final Class<?> modelClass;
	private final Class<?> methodTimingParameterType;
	private final ImmutableSet<String> columnNamesToCheckForChanges;

	private final boolean onlyIfUIAction;
	private final boolean skipIfCopying;

	@Builder
	private Pointcut(
			@NonNull final PointcutType type,
			@NonNull final Method method,
			@NonNull final int[] timings,
			final boolean afterCommit,
			final boolean onlyIfUIAction,
			final boolean skipIfCopying,
			//
			final Class<?> modelClass,
			final String[] columnNamesToCheckForChanges,
			final String[] ignoreColumnNames)
	{
		pointcutId = String.format("%s#%s",
				method.getDeclaringClass().getName(),
				method.getName());

		this.type = type;

		this.method = method;
		if (method.getReturnType() != void.class)
		{
			throw new AdempiereException("Return type should be void for " + method);
		}

		this.timings = ImmutableSet.copyOf(Ints.asList(timings));
		if (this.timings.isEmpty())
		{
			throw new AdempiereException("Invalid method " + method + ": no timings were specified");
		}
		if (Services.get(IDeveloperModeBL.class).isEnabled())
		{
			assertValidTimings(type, timings, method);
		}

		this.afterCommit = afterCommit;
		this.onlyIfUIAction = onlyIfUIAction;
		this.skipIfCopying = skipIfCopying;

		this.modelClass = extractModelClass(method, modelClass);
		this.tableName = extractModelTableName(this.modelClass);
		this.columnNamesToCheckForChanges = extractColumnNamesToCheckForChanges(
				this.modelClass,
				columnNamesToCheckForChanges,
				ignoreColumnNames);

		this.methodTimingParameterType = extractMethodTimingParameterType(method);
	}

	private static Class<?> extractModelClass(final Method method, final Class<?> providedModelClass)
	{
		final Class<?>[] parameterTypes = method.getParameterTypes();
		if (parameterTypes == null || parameterTypes.length == 0)
		{
			throw new AdempiereException("Invalid method " + method + ": It has no arguments");
		}

		Class<?> modelClass = parameterTypes[0];

		// Case: our parameter comes from Java generics
		// e.g. method comes from an abstract class which have annotated methods with Java Generics parameters.
		// In this case it's quite hard to get the actual type so we assume the modelClass.
		if (Object.class.equals(modelClass))
		{
			if (providedModelClass == null)
			{
				throw new AdempiereException("Cannot extact the model class from " + method + ". The provided model class is null.");
			}
			modelClass = providedModelClass;
		}

		return modelClass;
	}

	private static String extractModelTableName(final Class<?> modelClass)
	{
		//
		// Get table name
		final String tableName = InterfaceWrapperHelper.getTableNameOrNull(modelClass);
		if (Check.isEmpty(tableName, true))
		{
			throw new AdempiereException("Cannot find tablename for " + modelClass);
		}

		return tableName;
	}

	private static void assertValidTimings(final PointcutType type, final int[] timings, final Method method)
	{
		for (final int timing : timings)
		{
			try
			{
				if (PointcutType.ModelChange == type)
				{
					ModelChangeType.valueOf(timing); // shall throw exception if timing not found
				}
				else if (PointcutType.DocValidate == type)
				{
					DocTimingType.valueOf(timing); // shall throw exception if timing not found
				}
				else
				{
					throw new IllegalArgumentException("Unknown type: " + type); // shall not happen
				}
			}
			catch (final Exception e)
			{
				throw new AdempiereException("Invalid timing parameter type " + timing + " for method " + method, e);
			}
		}
	}

	private Class<?> extractMethodTimingParameterType(final Method method)
	{
		final Class<?> methodTimingParameterType;
		final Class<?>[] parameterTypes = method.getParameterTypes();
		if (parameterTypes.length == 1)
		{
			methodTimingParameterType = null; // no timing parameter required
		}
		else if (parameterTypes.length == 2)
		{
			methodTimingParameterType = parameterTypes[1];
		}
		else
		{
			throw new AdempiereException("Invalid method " + method + ": definition not supported");
		}

		if (methodTimingParameterType == null)
		{
			return null; // OK
		}
		// NOTE: by putting int.class into brackets i try to avoid
		// Fail to execute PMD. Following file is ignored: D:\Jenkins\workspace\de.metas.adempiere.adempiere.base\base\src\org\adempiere\ad\modelvalidator\AnnotatedModelValidator.java
		// net.sourceforge.pmd.ast.ParseException: Encountered " "." ". "" at line 273, column 60.
		// Was expecting:
		// ")" ...
		else if (int.class.isAssignableFrom(methodTimingParameterType))
		{
			return methodTimingParameterType; // OK
		}
		else if (type == PointcutType.ModelChange && ModelChangeType.class == methodTimingParameterType)
		{
			return methodTimingParameterType; // OK
		}
		else if (type == PointcutType.DocValidate && DocTimingType.class == methodTimingParameterType)
		{
			return methodTimingParameterType; // OK
		}
		else
		{
			throw new AdempiereException("Invalid timing parameter type " + methodTimingParameterType + " for method " + method);
		}
	}

	/**
	 * @return a list of columns that needs to be checked for changes.
	 */
	private static final ImmutableSet<String> extractColumnNamesToCheckForChanges(
			final Class<?> modelClass,
			final String[] changedColumnsArr,
			final String[] ignoredColumnsArr)
	{
		final ImmutableSet<String> changedColumns = changedColumnsArr != null ? ImmutableSet.copyOf(changedColumnsArr) : ImmutableSet.of();
		final ImmutableSet<String> ignoredColumns = ignoredColumnsArr != null ? ImmutableSet.copyOf(ignoredColumnsArr) : ImmutableSet.of();

		//
		// Case: specific columns to be checked for changes were specified
		if (!changedColumns.isEmpty())
		{
			// Case: no columns to be ignored from the list of columns to be checked
			if (ignoredColumns.isEmpty())
			{
				return ImmutableSet.copyOf(changedColumns);
			}
			// Case: columns to be excluded from the list of columns to be checked were set
			// => return a copy of columns to be checked, but remove the columns to exclude from it
			else
			{
				final ImmutableSet.Builder<String> columnsToCheckForChanges = ImmutableSet.builder();
				for (final String columnName : changedColumns)
				{
					if (ignoredColumns.contains(columnName))
					{
						continue;
					}
					columnsToCheckForChanges.add(columnName);
				}
				return columnsToCheckForChanges.build();
			}
		}
		//
		// Case: No particular columns to be checked for changes were specified
		// => return a list of all columns of this model but without those that chall be ignored
		else if (!ignoredColumns.isEmpty())
		{
			final ImmutableSet<String> modelColumnNames = ImmutableSet.copyOf(InterfaceWrapperHelper.getModelPhysicalColumnNames(modelClass));

			final ImmutableSet.Builder<String> columnsToCheckForChanges = ImmutableSet.builder();
			for (final String columnName : modelColumnNames)
			{
				if (ignoredColumns.contains(columnName))
				{
					continue;
				}
				columnsToCheckForChanges.add(columnName);
			}
			return columnsToCheckForChanges.build();
		}
		//
		// Case: no columns to be checked and no columns to be ignored were specified
		// => return an empty set, there is nothing to be checked.
		else
		{
			return ImmutableSet.of();
		}
	}

	@Override
	public String toString()
	{
		return "Pointcut [type=" + type
				+ ", tableName=" + tableName
				+ ", modelClass=" + modelClass
				+ ", method=" + method
				+ ", timings=" + timings
				+ ", afterCommit=" + afterCommit
				+ ", columnNamesToCheckForChanges=" + columnNamesToCheckForChanges
				+ ", methodTimingParameterType=" + methodTimingParameterType
				+ ", onlyIfUIAction=" + onlyIfUIAction
				+ "]";
	}

	public boolean isMethodRequiresTiming()
	{
		return methodTimingParameterType != null;
	}

	public Object convertToMethodTimingParameterType(final int timing)
	{
		if (methodTimingParameterType == null)
		{
			// shall not happen
			throw new AdempiereException("Method does not required timing parameter: " + getMethod());
		}
		else if (int.class.isAssignableFrom(methodTimingParameterType))
		{
			return timing;
		}
		else if (ModelChangeType.class.isAssignableFrom(methodTimingParameterType))
		{
			return ModelChangeType.valueOf(timing);
		}
		else if (DocTimingType.class.isAssignableFrom(methodTimingParameterType))
		{
			return DocTimingType.valueOf(timing);
		}
		else
		{
			// shall not happen because we already validated the parameter type when we set it
			throw new AdempiereException("Not supported timing parameter type '" + methodTimingParameterType + "' for method " + getMethod());
		}
	}

	/**
	 * Returns <code>true</code> if the other instance is also a PointCut and if {@link #compareTo(Pointcut)} returns 0.<br>
	 * Just added this method because the javadoc of {@link java.util.SortedSet} states that {@link #equals(Object)} and {@link #compareTo(Pointcut)} need to be consistent.
	 *
	 * @task https://metasfresh.atlassian.net/browse/FRESH-318
	 */
	@Override
	public boolean equals(final Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		final Pointcut other = EqualsBuilder.getOther(this, obj);
		if (other == null)
		{
			return false;
		}

		// we use PointCut in a sorted set, so equals has to be consistent with compareTo()
		if (compareTo(other) == 0)
		{
			return true;
		}

		return false;
	};

	/**
	 * Compares this instance with the given {@link Pointcut} by comparing their.
	 * <ul>
	 * <li>TableName</li>
	 * <li>Method's name</li>
	 * <li>Method's declaring class name</li>
	 * </ul>
	 *
	 * @task https://metasfresh.atlassian.net/browse/FRESH-318
	 */
	@Override
	public int compareTo(final Pointcut o)
	{
		return ComparisonChain.start()

				.compare(getTableName(), o.getTableName())

				.compare(getMethod() == null ? null : getMethod().getDeclaringClass().getName(),
						o.getMethod() == null ? null : o.getMethod().getDeclaringClass().getName())

				.compare(getMethod() == null ? null : getMethod().getName(),
						o.getMethod() == null ? null : o.getMethod().getName())

				.compare(getMethod() == null ? null : getMethod().getName(),
						o.getMethod() == null ? null : o.getMethod().getName())

				.result();
	}
}
