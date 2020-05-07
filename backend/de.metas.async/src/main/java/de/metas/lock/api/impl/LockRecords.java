package de.metas.lock.api.impl;

/*
 * #%L
 * de.metas.async
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


import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.lang.ITableRecordReference;
import org.adempiere.util.lang.ObjectUtils;
import org.adempiere.util.lang.impl.TableRecordReference;

/**
 * Pool of records to be processed (e.g. lock, unlock etc).
 * 
 * @author tsa
 *
 */
/* package */final class LockRecords
{
	private Set<ITableRecordReference> _records = null;
	//
	private int _selection_AD_Table_ID = -1;
	private int _selection_AD_PInstance_ID = -1;

	private IQueryFilter<?> _selection_filters = null;
	
	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}

	public void setRecordByModel(final Object model)
	{
		final ITableRecordReference record = TableRecordReference.of(model);
		setRecords(Collections.singleton(record));
	}

	public void setRecordsByModels(final Collection<?> models)
	{
		final Collection<ITableRecordReference> records = convertModelsToRecords(models);
		setRecords(records);
	}

	private final Collection<ITableRecordReference> convertModelsToRecords(final Collection<?> models)
	{
		final Set<ITableRecordReference> records = new HashSet<>(models.size());
		for (final Object model : models)
		{
			final ITableRecordReference record = TableRecordReference.of(model);
			records.add(record);
		}

		return records;
	}

	public void setRecordByTableRecordId(final int tableId, final int recordId)
	{
		final ITableRecordReference record = new TableRecordReference(tableId, recordId);
		setRecords(Collections.singleton(record));
	}
	
	public void setRecordByTableRecordId(final String tableName, final int recordId)
	{
		final ITableRecordReference record = new TableRecordReference(tableName, recordId);
		setRecords(Collections.singleton(record));
	}


	private final void setRecords(final Collection<ITableRecordReference> records)
	{
		_records = new HashSet<>(records);
		// Reset selection
		_selection_AD_Table_ID = -1;
		_selection_AD_PInstance_ID = -1;
	}

	public final void addRecords(final Collection<ITableRecordReference> records)
	{
		if (_records == null)
		{
			_records = new HashSet<>(records);
		}
		else
		{
			_records.addAll(records);
		}

		_selection_AD_Table_ID = -1;
		_selection_AD_PInstance_ID = -1;
	}

	public void setRecordsBySelection(final Class<?> modelClass, final int adPIstanceId)
	{
		Check.assume(adPIstanceId > 0, "adPIstanceId > 0");

		_selection_AD_Table_ID = InterfaceWrapperHelper.getTableId(modelClass);
		_selection_AD_PInstance_ID = adPIstanceId;

		_records = null;
	}
	

	public <T> void setSetRecordsByFilter(final Class<T> modelClass, final IQueryFilter<T> filters)
	{
		_selection_AD_Table_ID = InterfaceWrapperHelper.getTableId(modelClass);
		_selection_filters = filters;
	}

	public final IQueryFilter<?> getSelection_Filters()
	{
		return _selection_filters;
	}
	
	public final int getSelection_AD_Table_ID()
	{
		return _selection_AD_Table_ID;
	}

	public final int getSelection_AD_PInstance_ID()
	{
		return _selection_AD_PInstance_ID;
	}

	public final Iterator<ITableRecordReference> getRecordsIterator()
	{
		return _records == null ? null : _records.iterator();
	}

	public void addRecordByModel(final Object model)
	{
		final ITableRecordReference record = TableRecordReference.of(model);
		addRecords(Collections.singleton(record));
	}

	public void addRecordByModels(final Collection<?> models)
	{
		final Collection<ITableRecordReference> records = convertModelsToRecords(models);
		addRecords(records);
	}
}
