package de.metas.ui.web.vaadin.window.editor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.util.AbstractContainer;

import de.metas.ui.web.vaadin.window.PropertyDescriptor;
import de.metas.ui.web.vaadin.window.PropertyLayoutInfo;
import de.metas.ui.web.vaadin.window.PropertyName;
import de.metas.ui.web.vaadin.window.WindowConstants;
import de.metas.ui.web.vaadin.window.shared.datatype.GridRowId;
import de.metas.ui.web.vaadin.window.shared.datatype.PropertyValuesDTO;
import de.metas.ui.web.vaadin.window.shared.datatype.PropertyValuesListDTO;

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
		implements Container.ItemSetChangeNotifier, Container.Ordered, Container.Indexed
{
	private final PropertyDescriptor descriptor;
	private final Collection<PropertyName> visiblePropertyNames;

	private final LinkedHashMap<GridRowId, GridRowItem> rows = new LinkedHashMap<>();
	private final List<GridRowId> visibleRowIds = new ArrayList<>();

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
		public void valueChange(final Property.ValueChangeEvent event)
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
		visiblePropertyNames = propertyNames.build();
	}

	public void setEditorListener(final EditorListener listener)
	{
		editorListener = listener != null ? listener : NullEditorListener.instance;
	}

	@Override
	public void addItemSetChangeListener(final Container.ItemSetChangeListener listener)
	{
		super.addItemSetChangeListener(listener);
	}

	@Override
	@Deprecated
	public void addListener(final Container.ItemSetChangeListener listener)
	{
		super.addListener(listener);
	}

	@Override
	public void removeItemSetChangeListener(final Container.ItemSetChangeListener listener)
	{
		super.removeItemSetChangeListener(listener);
	}

	@Override
	@Deprecated
	public void removeListener(final Container.ItemSetChangeListener listener)
	{
		super.removeListener(listener);
	}

	@Override
	public GridRowItem getItem(final Object itemId)
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
	public Property getContainerProperty(final Object itemId, final Object propertyId)
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
	public Class<?> getType(final Object propertyId)
	{
		return descriptor.getChildPropertyDescriptorsAsMap().get(propertyId).getValueType();
	}

	@Override
	public int size()
	{
		return rows.size();
	}

	@Override
	public boolean containsId(final Object itemId)
	{
		final GridRowId rowId = GridRowId.of(itemId);
		return rows.containsKey(rowId);
	}

	private GridRowItem createGridRowItem(final Object itemId, final PropertyValuesDTO rowValues)
	{
		final GridRowId rowId = GridRowId.of(itemId);
		final GridRowItem rowItem = new GridRowItem(rowId, descriptor.getChildPropertyDescriptorsAsMap());
		rowItem.setValues(rowValues);
		rowItem.addValueChangeListener(cellValueChangedListenerDelegate);

		return rowItem;
	}

	@Override
	public GridRowItem addItem(final Object itemId)
	{
		return addItem(itemId, PropertyValuesDTO.of());
	}

	public GridRowItem addItem(final Object itemId, final PropertyValuesDTO rowValues)
	{
		final GridRowItem rowItem = addItemNoFire(itemId, rowValues);

		// TODO: fire a more specific event
		fireItemSetChange();

		return rowItem;
	}

	private GridRowItem addItemNoFire(final Object itemId, final PropertyValuesDTO rowValues)
	{
		final GridRowItem rowItem = createGridRowItem(itemId, rowValues);
		final GridRowId rowId = rowItem.getRowId();

		rows.put(rowId, rowItem);
		visibleRowIds.add(rowId);

		return rowItem;
	}

	@Override
	public GridRowItem addItem()
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean removeItem(final Object itemId) throws UnsupportedOperationException
	{
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
		// return false;
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
		if (!removeAllItemsNoFire())
		{
			return false;
		}

		// TODO: fire a more specific event
		fireItemSetChange();

		return true;
	}

	private boolean removeAllItemsNoFire()
	{
		if (rows.isEmpty() && visibleRowIds.isEmpty())
		{
			return false;
		}

		rows.clear();
		visibleRowIds.clear();

		return true;
	}

	public void setContent(final PropertyValuesListDTO rowValuesList)
	{
		removeAllItemsNoFire();

		for (final PropertyValuesDTO rowValues : rowValuesList.getList())
		{
			final Object rowId = rowValues.get(WindowConstants.PROPERTYNAME_GridRowId);
			addItemNoFire(rowId, rowValues);
		}

		fireItemSetChange();
	}

	private List<GridRowId> getVisibleItemIds()
	{
		return visibleRowIds;
	}

	@Override
	public int indexOfId(final Object itemId)
	{
		return getVisibleItemIds().indexOf(itemId);
	}

	@Override
	public GridRowId getIdByIndex(final int index)
	{
		return getVisibleItemIds().get(index);
	}

	@Override
	public Object nextItemId(final Object itemId)
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
	public Object prevItemId(final Object itemId)
	{
		final int index = indexOfId(itemId);
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
	public boolean isFirstId(final Object itemId)
	{
		if (itemId == null)
		{
			return false;
		}
		return itemId.equals(firstItemId());
	}

	@Override
	public boolean isLastId(final Object itemId)
	{
		if (itemId == null)
		{
			return false;
		}
		return itemId.equals(lastItemId());
	}

	@Override
	public Object addItemAfter(final Object previousItemId) throws UnsupportedOperationException
	{
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public Item addItemAfter(final Object previousItemId, final Object newItemId) throws UnsupportedOperationException
	{
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public List<?> getItemIds(final int startIndex, final int numberOfItems)
	{
		final List<GridRowId> rowIds = getVisibleItemIds();
		final int toIndex = startIndex + numberOfItems;
		final List<GridRowId> sublist = rowIds.subList(startIndex, toIndex);
		return ImmutableList.copyOf(sublist);
	}

	@Override
	public Object addItemAt(final int index) throws UnsupportedOperationException
	{
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public Item addItemAt(final int index, final Object newItemId) throws UnsupportedOperationException
	{
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}
}