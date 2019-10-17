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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
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
import org.adempiere.util.lang.impl.TableRecordReference;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableSet;

import de.metas.process.PInstanceId;
import lombok.NonNull;

/**
 * Pool of records to be processed (e.g. lock, unlock etc).
 * 
 * @author tsa
 *
 */
/* package */final class LockRecords
{
	private Set<TableRecordReference> _records = null;
	//
	private int _selection_AD_Table_ID = -1;
	private PInstanceId _selection_pinstanceId;

	private IQueryFilter<?> _selection_filters = null;

	@Override
	public String toString()
	{
		if (_selection_filters != null)
		{
			return MoreObjects.toStringHelper(this)
					.add("type", "queryFilter")
					.add("queryFilter", _selection_filters)
					.toString();
		}
		else if (_selection_pinstanceId != null)
		{
			return MoreObjects.toStringHelper(this)
					.add("type", "selectionId")
					.add("selectionId", _selection_pinstanceId)
					.add("adTableId", _selection_AD_Table_ID)
					.toString();
		}
		else if (_records != null)
		{
			return MoreObjects.toStringHelper(this)
					.add("type", "records")
					.add("recordsCount", _records.size())
					.toString();
		}
		else
		{
			return MoreObjects.toStringHelper(this)
					.add("type", "unknown")
					.toString();
		}
	}

	public void setRecordByModel(final Object model)
	{
		final TableRecordReference record = TableRecordReference.of(model);
		setRecords(Collections.singleton(record));
	}

	public void setRecordsByModels(final Collection<?> models)
	{
		final Collection<TableRecordReference> records = convertModelsToRecords(models);
		setRecords(records);
	}

	private static Collection<TableRecordReference> convertModelsToRecords(final Collection<?> models)
	{
		final Set<TableRecordReference> records = new HashSet<>(models.size());
		for (final Object model : models)
		{
			final TableRecordReference record = TableRecordReference.of(model);
			records.add(record);
		}

		return records;
	}

	public void setRecordByTableRecordId(final int tableId, final int recordId)
	{
		final TableRecordReference record = TableRecordReference.of(tableId, recordId);
		setRecords(ImmutableSet.of(record));
	}

	public void setRecordByTableRecordId(final String tableName, final int recordId)
	{
		final TableRecordReference record = TableRecordReference.of(tableName, recordId);
		setRecords(Collections.singleton(record));
	}

	private void setRecords(final Collection<TableRecordReference> records)
	{
		_records = new HashSet<>(records);
		// Reset selection
		_selection_AD_Table_ID = -1;
		_selection_pinstanceId = null;
	}

	public void addRecords(final Collection<TableRecordReference> records)
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
		_selection_pinstanceId = null;
	}

	public void setRecordsBySelection(final Class<?> modelClass, @NonNull final PInstanceId pinstanceId)
	{
		_selection_AD_Table_ID = InterfaceWrapperHelper.getTableId(modelClass);
		_selection_pinstanceId = pinstanceId;

		_records = null;
	}

	public <T> void setSetRecordsByFilter(final Class<T> modelClass, final IQueryFilter<T> filters)
	{
		_selection_AD_Table_ID = InterfaceWrapperHelper.getTableId(modelClass);
		_selection_filters = filters;
	}

	public IQueryFilter<?> getSelection_Filters()
	{
		return _selection_filters;
	}

	public int getSelection_AD_Table_ID()
	{
		return _selection_AD_Table_ID;
	}

	public PInstanceId getSelection_PInstanceId()
	{
		return _selection_pinstanceId;
	}

	public Iterator<TableRecordReference> getRecordsIterator()
	{
		return _records == null ? null : _records.iterator();
	}

	public void addRecordByModel(final Object model)
	{
		final TableRecordReference record = TableRecordReference.of(model);
		addRecords(Collections.singleton(record));
	}

	public void addRecordByModels(final Collection<?> models)
	{
		final Collection<TableRecordReference> records = convertModelsToRecords(models);
		addRecords(records);
	}
}
