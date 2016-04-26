package de.metas.ui.web.vaadin.window.data;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.compiere.util.NamePair;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.util.AbstractContainer;
import com.vaadin.data.util.converter.Converter;
import com.vaadin.data.util.filter.UnsupportedFilterException;

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
public class LookupFieldEditorContainer extends AbstractContainer
		implements FieldEditorContainer
		, NamePairProvider
		, Container.Filterable
		, Container.Indexed
{
	public static final LookupFieldEditorContainer of(final ILookupDataSource dataSource)
	{
		return new LookupFieldEditorContainer(dataSource);
	}

	private static final String PROPERTY_ID_Key = NamePairItem.PROPERTY_ID_Key;
	public static final String PROPERTY_ID_Caption = NamePairItem.PROPERTY_ID_Name;
	private static final Set<String> PROPERTY_IDs = ImmutableSet.of(PROPERTY_ID_Key, PROPERTY_ID_Caption);

	private static final Map<Object, Class<?>> PROPERTY_Types = ImmutableMap.<Object, Class<?>> builder()
			.put(PROPERTY_ID_Key, Object.class)
			.put(PROPERTY_ID_Caption, String.class)
			.build();

	private final ILookupDataSource dataSource;
	private NamePairConverter _converter;

	private Map<NamePair, NamePairItem> items = ImmutableMap.of();
	private List<NamePair> filteredItemIds = ImmutableList.of();

	private final Set<Filter> filters = new HashSet<>();

	private LookupFieldEditorContainer(final ILookupDataSource dataSource)
	{
		this.dataSource = dataSource;
		reload();
	}

	@Override
	public ILookupDataSource getDataSource()
	{
		return dataSource;
	}

	private final Object normalizeItemId(final Object itemId)
	{
		return itemId;
	}

	@Override
	public NamePairItem getItem(final Object itemId)
	{
		final NamePairItem item = items.get(normalizeItemId(itemId));
		return item;
	}

	@Override
	public NamePair getNamePair(final Object key)
	{
		final NamePairItem item = getItem(key);
		if (item != null)
		{
			return item.getNamePair();
		}
		
		return dataSource.getNamePair(key);
	}

	@Override
	public boolean isNumericKey()
	{
		return dataSource.isNumericKey();
	}

	@Override
	public Collection<?> getContainerPropertyIds()
	{
		return PROPERTY_IDs;
	}

	@Override
	public Collection<?> getItemIds()
	{
		return filteredItemIds;
	}

	@Override
	@SuppressWarnings("rawtypes")
	public Property getContainerProperty(final Object itemId, final Object propertyId)
	{
		final NamePairItem item = getItem(itemId);
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
		return items.size();
	}

	@Override
	public boolean containsId(final Object itemId)
	{
		return items.containsKey(normalizeItemId(itemId));
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

	public void reload()
	{
		//
		// Retrieve data from data source
		final Set<Filter> queryFilters;
		final Set<Filter> afterQueryFilters;
		final Collection<NamePair> data;
		if (isHighVolume())
		{
			if (filters.isEmpty())
			{
				queryFilters = null;
				afterQueryFilters = null;
				data = ImmutableList.of();
			}
			else
			{
				queryFilters = filters;
				afterQueryFilters = null;
				data = dataSource.retrieveValues(queryFilters);
			}
		}
		else
		{
			queryFilters = null; // no query filters => retrieve all and filter after, in memory
			afterQueryFilters = filters;
			data = dataSource.retrieveValues(queryFilters);
		}

		//
		// Load data
		final Map<NamePair, NamePairItem> itemsNew;
		final List<NamePair> filteredItemIdsNew;
		if (!data.isEmpty())
		{
			final ImmutableMap.Builder<NamePair, NamePairItem> items = ImmutableMap.builder();
			final ImmutableList.Builder<NamePair> filteredItemIds = ImmutableList.builder();
			for (final NamePair itemId : data)
			{
				final NamePairItem item = NamePairItem.of(itemId);
				items.put(itemId, item);

				if (passesFilters(afterQueryFilters, itemId, item))
				{
					filteredItemIds.add(itemId);
				}
			}

			itemsNew = items.build();
			filteredItemIdsNew = filteredItemIds.build();
		}
		else
		{
			itemsNew = ImmutableMap.of();
			filteredItemIdsNew = ImmutableList.of();
		}

		//
		// Set the new result
		if (Objects.equal(items, itemsNew)
				&& Objects.equal(filteredItemIds, filteredItemIdsNew))
		{
			// do nothing because nothing changed
			return;
		}
		items = itemsNew;
		filteredItemIds = filteredItemIdsNew;
		fireItemSetChange();
	}

	@Override
	public String getCaptionPropertyId()
	{
		return PROPERTY_ID_Caption;
	}

	@Override
	public String getIconPropertyId()
	{
		return null;
	}

	@Override
	public void addContainerFilter(final Filter filter) throws UnsupportedFilterException
	{
		Preconditions.checkNotNull(filter, "filter is null");

		if (isHighVolume())
		{
			filters.clear();
		}
		if (!filters.add(filter))
		{
			return;
		}

		reapplyFilters();

	}

	@Override
	public void removeContainerFilter(final Filter filter)
	{
		if (isHighVolume())
		{
			return;
		}

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

	private final boolean isHighVolume()
	{
		return dataSource.isHighVolume();
	}

	private void reapplyFilters()
	{
		if (isHighVolume())
		{
			reload();
		}
		else
		{
			final ImmutableList.Builder<NamePair> filteredItemIdsBuilder = ImmutableList.builder();
			for (final Map.Entry<NamePair, NamePairItem> e : items.entrySet())
			{
				final NamePair itemId = e.getKey();
				final NamePairItem item = e.getValue();

				if (passesFilters(filters, itemId, item))
				{
					filteredItemIdsBuilder.add(itemId);
				}
			}

			final ImmutableList<NamePair> filteredItemIdsNew = filteredItemIdsBuilder.build();
			if (Objects.equal(filteredItemIds, filteredItemIdsNew))
			{
				return;
			}

			filteredItemIds = filteredItemIdsNew;
			fireItemSetChange();
		}
	}

	private boolean passesFilters(final Set<Filter> filters, final NamePair itemId, final NamePairItem item)
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
	public int indexOfId(final Object itemId)
	{
		return filteredItemIds.indexOf(normalizeItemId(itemId));
	}

	@Override
	public Object getIdByIndex(final int index)
	{
		return filteredItemIds.get(index);
	}

	@Override
	public List<NamePair> getItemIds(final int startIndex, final int numberOfItems)
	{
		int toIndex = startIndex + numberOfItems;

		if (toIndex > filteredItemIds.size())
		{
			toIndex = filteredItemIds.size();
		}
		return filteredItemIds.subList(startIndex, toIndex);
	}

	@Override
	public Converter<Object, Object> getConverter()
	{
		if (_converter == null)
		{
			_converter = NamePairConverter.of(this);
		}
		return _converter;
	}
}
