package de.metas.ui.web.vaadin.window.editor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableSet;
import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.util.AbstractContainer;

import de.metas.ui.web.vaadin.window.GridRowId;
import de.metas.ui.web.vaadin.window.PropertyDescriptor;
import de.metas.ui.web.vaadin.window.PropertyLayoutInfo;
import de.metas.ui.web.vaadin.window.PropertyName;
import de.metas.ui.web.vaadin.window.WindowConstants;
import de.metas.ui.web.vaadin.window.model.PropertyValuesDTO;

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
final class GridEditorDataContainer extends AbstractContainer
		implements Container.ItemSetChangeNotifier, Container.Ordered
{
	private final PropertyDescriptor descriptor;
	private final Collection<PropertyName> visiblePropertyNames;

	private final Map<GridRowId, GridRowItem> rows = new LinkedHashMap<>();
	private final List<GridRowId> rowIds = new ArrayList<>();

	private EditorListener editorListener = NullEditorListener.instance;
	private final Property.ValueChangeListener cellValueChangedListenerDelegate = new Property.ValueChangeListener()
	{
		@Override
		public String toString()
		{
			return MoreObjects.toStringHelper(this)
					.add("gridPropertyName", descriptor.getPropertyName())
					.add("objectId", System.identityHashCode(this))
					.toString();
		};

		@Override
		public void valueChange(Property.ValueChangeEvent event)
		{
			if (editorListener == null)
			{
				return;
			}

			final PropertyName gridPropertyName = descriptor.getPropertyName();
			final GridCellProperty cell = (GridCellProperty)event.getProperty();
			final GridRowId rowId = cell.getRowId();
			final PropertyName propertyName = cell.getPropertyName();
			final Object value = cell.getValue();
			editorListener.gridValueChanged(gridPropertyName, rowId, propertyName, value);
		}
	};

	public GridEditorDataContainer(final PropertyDescriptor descriptor)
	{
		super();
		this.descriptor = descriptor;

		final ImmutableSet.Builder<PropertyName> propertyNames = ImmutableSet.builder();
		for (final PropertyDescriptor childPropertyDescriptor : descriptor.getChildPropertyDescriptors())
		{
			final PropertyLayoutInfo layoutInfo = childPropertyDescriptor.getLayoutInfo();
			final boolean visible = layoutInfo == null || layoutInfo.isDisplayed();
			if (!visible)
			{
				continue;
			}
			propertyNames.add(childPropertyDescriptor.getPropertyName());
		}
		this.visiblePropertyNames = propertyNames.build();
	}

	public void setEditorListener(final EditorListener listener)
	{
		this.editorListener = listener != null ? listener : NullEditorListener.instance;
	}

	@Override
	public void addItemSetChangeListener(Container.ItemSetChangeListener listener)
	{
		super.addItemSetChangeListener(listener);
	}

	@Override
	@Deprecated
	public void addListener(Container.ItemSetChangeListener listener)
	{
		super.addListener(listener);
	}

	@Override
	public void removeItemSetChangeListener(Container.ItemSetChangeListener listener)
	{
		super.removeItemSetChangeListener(listener);
	}

	@Override
	@Deprecated
	public void removeListener(Container.ItemSetChangeListener listener)
	{
		super.removeListener(listener);
	}

	@Override
	public GridRowItem getItem(Object itemId)
	{
		final GridRowId rowId = GridRowId.of(itemId);
		return rows.get(rowId);
	}

	@Override
	public Collection<PropertyName> getContainerPropertyIds()
	{
		return visiblePropertyNames;
	}

	public String getHeader(final Object propertyId)
	{
		final PropertyDescriptor childPropertyDescriptor = descriptor.getChildPropertyDescriptorsAsMap().get(propertyId);
		if (childPropertyDescriptor != null)
		{
			return childPropertyDescriptor.getCaption();
		}

		return "";
	}

	@Override
	public Collection<GridRowId> getItemIds()
	{
		return rows.keySet();
	}

	@Override
	@SuppressWarnings("rawtypes")
	public Property getContainerProperty(Object itemId, Object propertyId)
	{
		final GridRowId rowId = GridRowId.of(itemId);
		final GridRowItem row = rows.get(rowId);
		if (row == null)
		{
			return null;
		}
		return row.getItemProperty(propertyId);
	}

	@Override
	public Class<?> getType(Object propertyId)
	{
		return descriptor.getChildPropertyDescriptorsAsMap().get(propertyId).getValueType();
	}

	@Override
	public int size()
	{
		return rows.size();
	}

	@Override
	public boolean containsId(Object itemId)
	{
		final GridRowId rowId = GridRowId.of(itemId);
		return rows.containsKey(rowId);
	}

	@Override
	public GridRowItem addItem(Object itemId) throws UnsupportedOperationException
	{
		final GridRowId rowId = GridRowId.of(itemId);
		final GridRowItem rowItem = new GridRowItem(rowId, this.descriptor.getChildPropertyDescriptorsAsMap());
		rowItem.addValueChangeListener(cellValueChangedListenerDelegate);
		rows.put(rowId, rowItem);
		rowIds.add(rowId);
		return rowItem;
	}

	@Override
	public GridRowItem addItem()
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean removeItem(Object itemId) throws UnsupportedOperationException
	{
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
		// return false;
	}

	@Override
	public boolean addContainerProperty(Object propertyId, Class<?> type, Object defaultValue) throws UnsupportedOperationException
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean removeContainerProperty(Object propertyId) throws UnsupportedOperationException
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean removeAllItems() throws UnsupportedOperationException
	{
		rows.clear();
		return true;
	}

	public void setContent(final List<PropertyValuesDTO> rowValuesList)
	{
		rows.clear();

		for (final PropertyValuesDTO rowValues : rowValuesList)
		{
			final Object rowId = rowValues.get(WindowConstants.PROPERTYNAME_GridRowId);
			final GridRowItem rowItem = addItem(rowId);
			rowItem.setValues(rowValues); // FIXME: this is firing events to model!!!
		}

		fireItemSetChange();
	}

	private List<GridRowId> getVisibleItemIds()
	{
		return rowIds;
	}

	public int indexOfId(Object itemId)
	{
		return getVisibleItemIds().indexOf(itemId);
	}

	public GridRowId getIdByIndex(int index)
	{
		return getVisibleItemIds().get(index);
	}

	@Override
	public Object nextItemId(Object itemId)
	{
		final int index = indexOfId(itemId);
		if (index >= 0 && index < size() - 1)
		{
			return getIdByIndex(index + 1);
		}
		else
		{
			// out of bounds
			return null;
		}
	}

	@Override
	public Object prevItemId(Object itemId)
	{
		int index = indexOfId(itemId);
		if (index > 0)
		{
			return getIdByIndex(index - 1);
		}
		else
		{
			// out of bounds
			return null;
		}
	}

	@Override
	public Object firstItemId()
	{
		if (size() > 0)
		{
			return getIdByIndex(0);
		}
		else
		{
			return null;
		}
	}

	@Override
	public Object lastItemId()
	{
		if (size() > 0)
		{
			return getIdByIndex(size() - 1);
		}
		else
		{
			return null;
		}
	}

	@Override
	public boolean isFirstId(Object itemId)
	{
		if (itemId == null)
		{
			return false;
		}
		return itemId.equals(firstItemId());
	}

	@Override
	public boolean isLastId(Object itemId)
	{
		if (itemId == null)
		{
			return false;
		}
		return itemId.equals(lastItemId());
	}

	@Override
	public Object addItemAfter(Object previousItemId) throws UnsupportedOperationException
	{
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public Item addItemAfter(Object previousItemId, Object newItemId) throws UnsupportedOperationException
	{
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}
}