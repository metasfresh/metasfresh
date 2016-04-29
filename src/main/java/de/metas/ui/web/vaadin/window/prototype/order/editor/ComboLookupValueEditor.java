package de.metas.ui.web.vaadin.window.prototype.order.editor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.util.AbstractContainer;
import com.vaadin.data.util.filter.UnsupportedFilterException;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.AbstractSelect.ItemCaptionMode;
import com.vaadin.ui.ComboBox;

import de.metas.logging.LogManager;
import de.metas.ui.web.vaadin.window.prototype.order.PropertyDescriptor;
import de.metas.ui.web.vaadin.window.prototype.order.PropertyName;
import de.metas.ui.web.vaadin.window.prototype.order.WindowConstants;

/*
 * #%L
 * metasfresh-webui
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
public class ComboLookupValueEditor extends FieldEditor<Object>
{
	@SuppressWarnings("unused")
	private static final Logger logger = LogManager.getLogger(ComboLookupValueEditor.class);

	private final PropertyName valuesPropertyName;
	private final ImmutableSet<PropertyName> watchedPropertyNames;
	private final LookupValueContainer lookupValuesContainer;

	public ComboLookupValueEditor(final PropertyDescriptor propertyDescriptor)
	{
		super(propertyDescriptor);

		final ComboBox valueField = getValueField();
		lookupValuesContainer = new LookupValueContainer();
		valueField.setContainerDataSource(lookupValuesContainer);
		valueField.setItemCaptionMode(ItemCaptionMode.PROPERTY);
		valueField.setItemCaptionPropertyId(lookupValuesContainer.getCaptionPropertyId());
		valueField.setItemIconPropertyId(lookupValuesContainer.getIconPropertyId());

		final ImmutableSet.Builder<PropertyName> watchedPropertyNames = ImmutableSet.builder();

		valuesPropertyName = WindowConstants.lookupValuesName(getPropertyName());
		watchedPropertyNames.add(valuesPropertyName);

		this.watchedPropertyNames = watchedPropertyNames.build();
	}

	@Override
	protected AbstractField<Object> createValueField()
	{
		final ComboBox valueField = new ComboBox()
		{
			@Override
			protected void setInternalValue(final Object newValue)
			{
				lookupValuesContainer.setTemporaryValue(newValue);
				super.setInternalValue(newValue);
			}
		};

		return valueField;
	}

	@Override
	public Set<PropertyName> getWatchedPropertyNames()
	{
		return watchedPropertyNames;
	}

	@Override
	protected ComboBox getValueField()
	{
		return (ComboBox)super.getValueField();
	}

	@Override
	protected Object convertToView(final Object valueObj)
	{
		return valueObj;
	}

	@Override
	public void setValue(final PropertyName propertyName, final Object value)
	{
		if (valuesPropertyName != null && Objects.equal(valuesPropertyName, propertyName))
		{
			final LookupValue currentLookupValue = getValue();
			lookupValuesContainer.setValues(value, currentLookupValue);
		}
		else
		{
			lookupValuesContainer.setTemporaryValue(value);
			super.setValue(propertyName, value);
		}
	}

	@Override
	public LookupValue getValue()
	{
		return (LookupValue)super.getValue();
	}

	public static class LookupValueContainer extends AbstractContainer implements Container.Filterable, Container.Indexed
	{
		private static final String PROPERTY_ID_Key = LookupValueItem.PROPERTY_ID_Key;
		public static final String PROPERTY_ID_Caption = LookupValueItem.PROPERTY_ID_Name;
		private static final Set<String> PROPERTY_IDs = ImmutableSet.of(PROPERTY_ID_Key, PROPERTY_ID_Caption);

		private static final Map<Object, Class<?>> PROPERTY_Types = ImmutableMap.<Object, Class<?>> builder()
				.put(PROPERTY_ID_Key, Object.class)
				.put(PROPERTY_ID_Caption, String.class)
				.build();

		private LookupValueItem temporaryLookupValueItem;
		private int temporaryLookupValueItemIndex = -1;
		private Supplier<List<LookupValue>> _lookupValuesSupplier = null;
		private Map<LookupValue, LookupValueItem> _filteredItemsMap = null;
		private List<LookupValue> _filteredItemIds = null;

		private final Set<Filter> filters = new HashSet<>();

		private final LookupValue normalizeItemId(final Object itemIdObj)
		{
			return (LookupValue)itemIdObj;
		}

		public Object getCaptionPropertyId()
		{
			return PROPERTY_ID_Caption;
		}

		public Object getIconPropertyId()
		{
			// TODO Auto-generated method stub
			return null;
		}

		private final List<LookupValue> getFilteredItemIds()
		{
			if (_filteredItemIds == null)
			{
				reapplyFilters();
			}
			return _filteredItemIds;
		}

		private final Map<LookupValue, LookupValueItem> getFilteredItems()
		{
			if (_filteredItemsMap == null)
			{
				reapplyFilters();
			}
			return _filteredItemsMap;
		}

		@Override
		public LookupValueItem getItem(final Object itemIdObj)
		{
			if (isTemporaryItemId(itemIdObj))
			{
				return temporaryLookupValueItem;
			}

			final Object itemId = normalizeItemId(itemIdObj);
			final LookupValueItem item = getFilteredItems().get(itemId);
			return item;
		}

		@Override
		public Collection<?> getContainerPropertyIds()
		{
			return PROPERTY_IDs;
		}

		@Override
		public Collection<?> getItemIds()
		{
			return getFilteredItemIds();
		}

		@Override
		@SuppressWarnings("rawtypes")
		public Property getContainerProperty(final Object itemId, final Object propertyId)
		{
			final LookupValueItem item = getItem(itemId);
			return item == null ? null : item.getItemProperty(propertyId);
		}

		@Override
		public Class<?> getType(final Object propertyId)
		{
			return PROPERTY_Types.get(propertyId);
		}

		@Override
		public int size()
		{
			// FIXME: it's important to get the actual size.
			// if (_filteredItemIds == null)
			// {
			// return temporaryLookupValueItem != null ? 1 : 0;
			// }

			return getFilteredItemIds().size();
		}

		@Override
		public boolean containsId(final Object itemIdObj)
		{
			if (isTemporaryItemId(itemIdObj))
			{
				return true;
			}

			final Object itemIdNormalized = normalizeItemId(itemIdObj);
			return getFilteredItemIds().contains(itemIdNormalized);
		}

		public void setValues(final Object lookupValuesObj, final LookupValue temporaryLookupValueToPreserve)
		{
			//
			if (temporaryLookupValueToPreserve == null)
			{
				temporaryLookupValueItem = null;
			}
			else
			{
				temporaryLookupValueItem = LookupValueItem.of(temporaryLookupValueToPreserve);
			}

			//
			if (lookupValuesObj == null)
			{
				_lookupValuesSupplier = Suppliers.ofInstance(ImmutableList.of());
				reapplyFilters();
			}
			else if (lookupValuesObj instanceof List)
			{
				@SuppressWarnings("unchecked")
				final List<LookupValue> lookupValues = (List<LookupValue>)lookupValuesObj;
				_lookupValuesSupplier = Suppliers.ofInstance(ImmutableList.copyOf(lookupValues));
				reapplyFilters();
			}
			else if (lookupValuesObj instanceof Supplier)
			{
				@SuppressWarnings("unchecked")
				final Supplier<List<LookupValue>> lookupValuesSupplier = (Supplier<List<LookupValue>>)lookupValuesObj;
				_lookupValuesSupplier = Suppliers.memoize(lookupValuesSupplier);
				invalidateFilteredItems();
			}
			else
			{
				throw new IllegalArgumentException("Invalid lookupValuesObj type: " + lookupValuesObj + " (" + lookupValuesObj.getClass() + ")");
			}
		}

		public void setTemporaryValue(final Object temporaryLookupValue)
		{
			if (temporaryLookupValue == null)
			{
				temporaryLookupValueItem = null;
				temporaryLookupValueItemIndex = -1;
			}
			else
			{
				temporaryLookupValueItem = LookupValueItem.of((LookupValue)temporaryLookupValue);
				temporaryLookupValueItemIndex = 0;
			}

			invalidateFilteredItems();
		}

		private boolean isTemporaryItemId(final Object itemIdObj)
		{
			if (temporaryLookupValueItem == null)
			{
				return false;
			}

			final LookupValue itemId = normalizeItemId(itemIdObj);
			return temporaryLookupValueItem.getLookupValue().equals(itemId);
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
		public final boolean addContainerProperty(final Object propertyId, final Class<?> type, final Object defaultValue) throws UnsupportedOperationException
		{
			throw new UnsupportedOperationException();
		}

		@Override
		public final boolean removeContainerProperty(final Object propertyId) throws UnsupportedOperationException
		{
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean removeAllItems() throws UnsupportedOperationException
		{
			throw new UnsupportedOperationException();
		}

		@Override
		public void addContainerFilter(final Filter filter) throws UnsupportedFilterException
		{
			Preconditions.checkNotNull(filter, "filter is null");

			if (!filters.add(filter))
			{
				return;
			}

			reapplyFilters();
		}

		@Override
		public void removeContainerFilter(final Filter filter)
		{
			if (!filters.remove(filter))
			{
				return;
			}

			reapplyFilters();
		}

		@Override
		public void removeAllContainerFilters()
		{
			if (filters.isEmpty())
			{
				return;
			}

			filters.clear();
			reapplyFilters();
		}

		@Override
		public Collection<Filter> getContainerFilters()
		{
			return ImmutableSet.copyOf(filters);
		}

		private void invalidateFilteredItems()
		{
			_filteredItemIds = null;
			_filteredItemsMap = null;
		}

		private void reapplyFilters()
		{
			final List<LookupValue> filteredItemIds = new ArrayList<>();
			final Map<LookupValue, LookupValueItem> filteredItems = new HashMap<>();

			// First, consider the temporary value we need to keep
			if (temporaryLookupValueItem != null)
			{
				// NOTE: keep in sync with temporaryLookupValueItemIndex
				assert temporaryLookupValueItemIndex == 0;

				final LookupValue temporaryLookupValue = temporaryLookupValueItem.getLookupValue();
				if (passesFilters(filters, temporaryLookupValue, temporaryLookupValueItem))
				{
					filteredItemIds.add(temporaryLookupValue);
					filteredItems.put(temporaryLookupValue, temporaryLookupValueItem);
				}
			}

			// Check and filter all other values we have
			final List<LookupValue> lookupValues = _lookupValuesSupplier != null ? _lookupValuesSupplier.get() : null;
			if (lookupValues != null && !lookupValues.isEmpty())
			{
				for (final LookupValue lookupValue : lookupValues)
				{
					if (lookupValue == null)
					{
						continue;
					}

					final LookupValue itemId = lookupValue;
					if (filteredItemIds.contains(itemId))
					{
						continue;
					}

					final LookupValueItem item = LookupValueItem.of(lookupValue);
					if (passesFilters(filters, itemId, item))
					{
						filteredItemIds.add(itemId);
						filteredItems.put(itemId, item);
					}
				}
			}

			//
			// Check if our content has really changed
			final ImmutableList<LookupValue> filteredItemIdsNew = ImmutableList.copyOf(filteredItemIds);
			if (Objects.equal(_filteredItemIds, filteredItemIdsNew))
			{
				return;
			}

			// Set the new filtered list and fire event
			_filteredItemIds = filteredItemIdsNew;
			_filteredItemsMap = ImmutableMap.copyOf(filteredItems);
			fireItemSetChange();
		}

		private boolean passesFilters(final Set<Filter> filters, final LookupValue itemId, final LookupValueItem item)
		{
			if (filters == null || filters.isEmpty())
			{
				return true;
			}

			for (final Filter filter : filters)
			{
				if (!filter.passesFilter(itemId, item))
				{
					return false;
				}
			}

			return true;
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
		public int indexOfId(final Object itemIdObj)
		{
			if (temporaryLookupValueItemIndex >= 0 && isTemporaryItemId(itemIdObj))
			{
				return temporaryLookupValueItemIndex;
			}

			final LookupValue itemId = normalizeItemId(itemIdObj);
			return getFilteredItemIds().indexOf(itemId);
		}

		@Override
		public Object getIdByIndex(final int index)
		{
			if (temporaryLookupValueItemIndex >= 0 && temporaryLookupValueItemIndex == index)
			{
				return temporaryLookupValueItem.getLookupValue();
			}
			return getFilteredItemIds().get(index);
		}

		@Override
		public List<LookupValue> getItemIds(final int startIndex, final int numberOfItems)
		{
			if (numberOfItems <= 0)
			{
				return ImmutableList.of();
			}

			if (temporaryLookupValueItem != null
					&& startIndex == temporaryLookupValueItemIndex
					&& numberOfItems == 1)
			{
				return ImmutableList.of(temporaryLookupValueItem.getLookupValue());
			}

			int toIndex = startIndex + numberOfItems;
			final List<LookupValue> filteredItemIds = getFilteredItemIds();
			if (toIndex > filteredItemIds.size())
			{
				toIndex = filteredItemIds.size();
			}
			return filteredItemIds.subList(startIndex, toIndex);
		}
	}

}
