package de.metas.ui.web.vaadin.window.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.util.AbstractContainer;

import de.metas.ui.web.vaadin.window.descriptor.DataRowDescriptor;

/*
 * #%L
 * de.metas.ui.web.vaadin
 * %%
 * Copyright (C) 2016 metas GmbH
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

@SuppressWarnings("serial")
public class DataRowContainer extends AbstractContainer
		implements Container.Indexed
{
	private final DataRowDescriptor descriptor;

	private List<DataRowItem> rowsList = new ArrayList<>();
	private Map<Object, DataRowItem> rowId2row = new HashMap<>();

	public DataRowContainer(final DataRowDescriptor descriptor)
	{
		super();
		this.descriptor = descriptor;
	}
	
	public DataRowDescriptor getDescriptor()
	{
		return descriptor;
	}

	@Override
	public DataRowItem getItem(final Object rowId)
	{
		if(rowId == null)
		{
			return null;
		}
		return rowId2row.get(rowId);
	}

	@Override
	public Collection<?> getContainerPropertyIds()
	{
		return descriptor.getColumnNames();
	}

	@Override
	public Collection<?> getItemIds()
	{
		return rowId2row.keySet();
	}

	@Override
	public DataFieldProperty getContainerProperty(final Object itemId, final Object propertyId)
	{
		final DataRowItem row = getItem(itemId);
		return row == null ? null : row.getItemProperty(propertyId);
	}

	@Override
	public Class<?> getType(final Object propertyId)
	{
		return descriptor.getValueClassByColumnName(propertyId);
	}

	@Override
	public int size()
	{
		return rowsList.size();
	}
	
	public boolean isEmpty()
	{
		return rowsList.isEmpty();
	}

	@Override
	public boolean containsId(final Object itemId)
	{
		return rowId2row.containsKey(itemId);
	}

	@Override
	public Item addItem(final Object itemId) throws UnsupportedOperationException
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public Object addItem() throws UnsupportedOperationException
	{
		throw new UnsupportedOperationException();
	}
	
	@Override
	public Object addItemAt(final int index) throws UnsupportedOperationException
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public Item addItemAt(final int index, final Object newItemId) throws UnsupportedOperationException
	{
		throw new UnsupportedOperationException();
	}
	
	@Override
	public Object addItemAfter(final Object previousItemId) throws UnsupportedOperationException
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public Item addItemAfter(final Object previousItemId, final Object newItemId) throws UnsupportedOperationException
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean removeItem(final Object itemId) throws UnsupportedOperationException
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean addContainerProperty(final Object propertyId, final Class<?> type, final Object defaultValue) throws UnsupportedOperationException
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean removeContainerProperty(final Object propertyId) throws UnsupportedOperationException
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean removeAllItems() throws UnsupportedOperationException
	{
		throw new UnsupportedOperationException();
	}

	public void setRows(final List<DataRowItem> rowsList)
	{
		final List<DataRowItem> rowsListNew = new ArrayList<>(rowsList);
		final Map<Object, DataRowItem> rowId2rowNew = new HashMap<>(rowsList.size());
		for (final DataRowItem row : rowsList)
		{
			final Object key = row.getKey();
			rowId2rowNew.put(key, row);
		}

		this.rowsList = rowsListNew;
		rowId2row = rowId2rowNew;
		fireItemSetChange();
	}

	@Override
	public Object nextItemId(final Object rowId)
	{
		final int index = indexOfId(rowId);
		if (index >= 0 && index < size() - 1)
		{
			return getIdByIndex(index + 1);
		}
		else
		{
			return null; // out of bounds
		}
	}

	@Override
	public Object prevItemId(final Object itemId)
	{
		final int index = indexOfId(itemId);
		if (index > 0)
		{
			return getIdByIndex(index - 1);
		}
		else
		{
			return null; // out of bounds
		}
	}

	@Override
	public Object firstItemId()
	{
		if (!isEmpty())
		{
			return getIdByIndex(0);
		}
		else
		{
			return null;
		}
	}
	
	public DataRowItem firstItem()
	{
		return isEmpty() ? null : getItemByIndex(0);
	}

	@Override
	public Object lastItemId()
	{
		final int size = size();
		if (size > 0)
		{
			return getIdByIndex(size - 1);
		}
		else
		{
			return null;
		}
	}

	@Override
	public boolean isFirstId(final Object rowId)
	{
		if (rowId == null)
		{
			return false;
		}
		return rowId.equals(firstItemId());
	}

	@Override
	public boolean isLastId(final Object rowId)
	{
		if (rowId == null)
		{
			return false;
		}
		return rowId.equals(lastItemId());
	}

	@Override
	public int indexOfId(final Object rowId)
	{
		final DataRowItem row = rowId2row.get(rowId);
		return rowsList.indexOf(row);
	}
	
	public DataRowItem getItemByIndex(final int index)
	{
		return rowsList.get(index);
	}

	@Override
	public Object getIdByIndex(final int index)
	{
		final DataRowItem row = getItemByIndex(index);
		return row.getKey();
	}

	@Override
	public List<?> getItemIds(final int startIndex, final int numberOfItems)
	{
		int toIndex = startIndex + numberOfItems;

		final int size = rowsList.size();
		if (toIndex > size)
		{
			toIndex = size;
		}
		final List<DataRowItem> rows = rowsList.subList(startIndex, toIndex);
		final List<Object> rowKeys = new ArrayList<>(rows.size());
		for (final DataRowItem row : rows)
		{
			rowKeys.add(row.getKey());
		}
		return rowKeys;
	}
}
