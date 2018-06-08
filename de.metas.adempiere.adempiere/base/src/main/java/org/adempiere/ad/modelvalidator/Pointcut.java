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

import java.lang.reflect.Method;
import java.util.Set;

import org.adempiere.ad.service.IDeveloperModeBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.adempiere.util.lang.EqualsBuilder;

import com.google.common.collect.ComparisonChain;
import com.google.common.collect.ImmutableSet;
import com.google.common.primitives.Ints;

import lombok.Getter;
import lombok.Setter;

/* package */final class Pointcut implements IPointcut, Comparable<Pointcut>
{
	private final String pointcutId;
	private final PointcutType type;
	private final Method method;
	private final Set<Integer> timings;
	private final boolean afterCommit;
	private String tableName;
	private Class<?> modelClass;
	private Class<?> methodTimingParameterType;
	private Set<String> modelColumnNames = ImmutableSet.of();
	private Set<String> changedColumns = ImmutableSet.of();
	private Set<String> ignoredColumns = ImmutableSet.of();
	private volatile Set<String> columnsToCheckForChanges = null;
	private boolean onlyIfUIAction = false;
	@Getter
	@Setter
	private boolean skipIfCopying = false;

	public Pointcut(final PointcutType type, final Method method, final int[] timings, final boolean afterCommit)
	{
		this.pointcutId = String.format("%s#%s",
				method.getDeclaringClass().getName(),
				method.getName());

		this.type = type;
		this.method = method;
		this.timings = ImmutableSet.copyOf(Ints.asList(timings));
		this.afterCommit = afterCommit;

		//
		// Validate timings
		if (Services.get(IDeveloperModeBL.class).isEnabled())
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
				catch (Exception e)
				{
					throw new AdempiereException("Invalid timing parameter type " + timing + " for method " + getMethod(), e);
				}
			}
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
				+ ", onColumnChanged=" + changedColumns
				+ ", ignoredColumns=" + ignoredColumns
				+ ", methodTimingParameterType=" + methodTimingParameterType
				+ ", onlyIfUIAction=" + onlyIfUIAction
				+ "]";
	}

	@Override
	public String getPointcutId()
	{
		return pointcutId;
	}

	@Override
	public Method getMethod()
	{
		return method;
	}

	@Override
	public String getTableName()
	{
		return tableName;
	}

	protected void setTableName(final String tableName)
	{
		this.tableName = tableName;
	}

	@Override
	public Class<?> getModelClass()
	{
		return modelClass;
	}

	protected void setModelClass(final Class<?> modelClass)
	{
		this.modelClass = modelClass;
		this.modelColumnNames = InterfaceWrapperHelper.getModelColumnNames(modelClass);
		this.columnsToCheckForChanges = null;
	}

	@Override
	public boolean isMethodRequiresTiming()
	{
		return methodTimingParameterType != null;
	}

	/**
	 * Sets timing parameter type.
	 *
	 * @param methodTimingParameterType timing parameter type or <code>null</code> if no timing parameter is required.
	 */
	protected void setMethodTimingParameterType(final Class<?> methodTimingParameterType)
	{
		if (methodTimingParameterType == null)
		{
			this.methodTimingParameterType = null;
		}
		// NOTE: by putting int.class into brackets i try to avoid
		// Fail to execute PMD. Following file is ignored: D:\Jenkins\workspace\de.metas.adempiere.adempiere.base\base\src\org\adempiere\ad\modelvalidator\AnnotatedModelValidator.java
		// net.sourceforge.pmd.ast.ParseException: Encountered " "." ". "" at line 273, column 60.
		// Was expecting:
		// ")" ...
		else if ((int.class).isAssignableFrom(methodTimingParameterType))
		{
			this.methodTimingParameterType = methodTimingParameterType;
		}
		else if (type == PointcutType.ModelChange && ModelChangeType.class == methodTimingParameterType)
		{
			this.methodTimingParameterType = methodTimingParameterType;
		}
		else if (type == PointcutType.DocValidate && DocTimingType.class == methodTimingParameterType)
		{
			this.methodTimingParameterType = methodTimingParameterType;
		}
		else
		{
			throw new AdempiereException("Invalid timing parameter type " + methodTimingParameterType + " for method " + getMethod());
		}
	}

	@Override
	public Object convertToMethodTimingParameterType(final int timing)
	{
		if (methodTimingParameterType == null)
		{
			// shall not happen
			throw new AdempiereException("Method does not required timing parameter: " + getMethod());
		}
		else if ((int.class).isAssignableFrom(methodTimingParameterType))
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

	/*
	 * (non-Javadoc)
	 *
	 * @see de.metas.adempiere.modelvalidator.IPointcut#getType()
	 */
	@Override
	public PointcutType getType()
	{
		return type;
	}

	@Override
	public Set<Integer> getTimings()
	{
		return timings;
	}

	@Override
	public boolean isAfterCommit()
	{
		return afterCommit;
	}

	public void setChangedColumns(final String[] changedColumns)
	{
		if (changedColumns == null || changedColumns.length == 0)
		{
			this.changedColumns = ImmutableSet.of();
		}
		else
		{
			this.changedColumns = ImmutableSet.copyOf(changedColumns);
		}
		this.columnsToCheckForChanges = null;
	}

	public final void setIgnoredColumns(final String[] ignoredColumns)
	{
		if (ignoredColumns == null || ignoredColumns.length == 0)
		{
			this.ignoredColumns = ImmutableSet.of();
		}
		else
		{
			this.ignoredColumns = ImmutableSet.copyOf(ignoredColumns);
		}
		this.columnsToCheckForChanges = null;
	}

	@Override
	public boolean isOnlyIfUIAction()
	{
		return onlyIfUIAction;
	}

	public void setOnlyIfUIAction(final boolean onlyIfUIAction)
	{
		this.onlyIfUIAction = onlyIfUIAction;
	}

	@Override
	public Set<String> getColumnsToCheckForChanges()
	{
		if (columnsToCheckForChanges == null)
		{
			synchronized (this)
			{
				if (columnsToCheckForChanges == null)
				{
					columnsToCheckForChanges = buildColumnsToCheckForChanges();
				}
			}
		}
		return columnsToCheckForChanges;
	}

	/**
	 * Build the list of columns that needs to be checked for changes.
	 *
	 * @return
	 */
	private final Set<String> buildColumnsToCheckForChanges()
	{
		//
		// Case: specific columns to be checked for changes were specified
		if (!changedColumns.isEmpty())
		{
			// Case: no columns to be ignored from the list of columns to be checked
			if (ignoredColumns.isEmpty())
			{
				return changedColumns;
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
