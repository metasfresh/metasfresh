package org.adempiere.ad.model.util;

import java.util.HashSet;

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


import java.util.Set;

import org.adempiere.ad.persistence.IModelInternalAccessor;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;

import com.google.common.collect.ImmutableSet;

public class ModelCopyHelper implements IModelCopyHelper
{
	// From/To models
	private Object _fromModel;
	private IModelInternalAccessor _fromModelAccessor;
	private Object _toModel;
	private IModelInternalAccessor _toModelAccessor;

	// Parameters
	private boolean _skipCalculatedColumns = false;

	/** List of column names which we are always skipping from copying */
	private static final Set<String> DEFAULT_ColumnNamesToSkipAlways = ImmutableSet.<String> builder()
			.add("Created")
			.add("CreatedBy")
			.add("Updated")
			.add("UpdatedBy")
			.build();
	
	private final Set<String> targetColumnNamesToSkip = new HashSet<>();

	@Override
	public void copy()
	{
		final IModelInternalAccessor from = getFrom();
		final IModelInternalAccessor to = getTo();

		for (final String columnName : to.getColumnNames())
		{
			// Skip this column if it does not exist in our "from" model
			if (!from.hasColumnName(columnName))
			{
				continue;
			}
			
			// Skip columns which were advised to be skipped
			if(targetColumnNamesToSkip.contains(columnName))
			{
				continue;
			}

			// Skip virtual columns
			if (to.isVirtualColumn(columnName))
			{
				continue;
			}

			// Skip copying key columns
			if (to.isKeyColumnName(columnName))
			{
				continue;
			}

			if (DEFAULT_ColumnNamesToSkipAlways.contains(columnName))
			{
				continue;
			}

			// Skip calculated columns if asked
			if (isSkipCalculatedColumns() && to.isCalculated(columnName))
			{
				continue;
			}

			//
			// Copy the value
			final Object value = from.getValue(columnName, Object.class);
			final boolean valueSet = to.setValue(columnName, value);
			if (!valueSet)
			{
				throw new AdempiereException("Could not copy value for " + columnName
						+ "\n Value: " + value
						+ "\n From: " + from
						+ "\n To: " + to);
			}
		}
	}

	@Override
	public IModelCopyHelper setFrom(final Object fromModel)
	{
		this._fromModel = fromModel;
		this._fromModelAccessor = null;
		return this;
	}

	private final IModelInternalAccessor getFrom()
	{
		if (_fromModelAccessor == null)
		{
			Check.assumeNotNull(_fromModel, "_fromModel not null");
			_fromModelAccessor = InterfaceWrapperHelper.getModelInternalAccessor(_fromModel);
		}
		return _fromModelAccessor;
	}

	@Override
	public IModelCopyHelper setTo(final Object toModel)
	{
		this._toModel = toModel;
		this._toModelAccessor = null;
		return this;
	}

	private final IModelInternalAccessor getTo()
	{
		if (_toModelAccessor == null)
		{
			Check.assumeNotNull(_toModel, "_toModel not null");
			_toModelAccessor = InterfaceWrapperHelper.getModelInternalAccessor(_toModel);
		}
		return _toModelAccessor;
	}

	public final boolean isSkipCalculatedColumns()
	{
		return _skipCalculatedColumns;
	}

	@Override
	public IModelCopyHelper setSkipCalculatedColumns(boolean skipCalculatedColumns)
	{
		this._skipCalculatedColumns = skipCalculatedColumns;
		return this;
	}

	@Override
	public IModelCopyHelper addTargetColumnNameToSkip(final String columnName)
	{
		targetColumnNamesToSkip.add(columnName);
		return this;
	}
}
