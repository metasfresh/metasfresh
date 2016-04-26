package de.metas.ui.web.vaadin.data.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.common.collect.ImmutableList;
import com.vaadin.data.Container;
import com.vaadin.data.util.BeanContainer;
import com.vaadin.data.util.BeanItem;

/*
 * #%L
 * test_vaadin
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

/**
 *
 * @author https://vaadin.com/forum#!/thread/944116
 * @author metas-dev <dev@metas-fresh.com>
 *
 * @param <BT>
 */
public class HierarchicalBeanContainer<BT extends TreeItem<BT>>
		extends BeanContainer<Integer, BT>
		implements Container.Hierarchical
{
	private static final long serialVersionUID = 3602488403920324451L;
	
	public static final String PROPERTYID_Icon = "icon";
	public static final Object PROPERTYID_Caption = "caption";
	
	private final List<Integer> rootItemIds = new ArrayList<Integer>();

	public HierarchicalBeanContainer(final Class<BT> type)
	{
		super(type);
		super.setBeanIdResolver(new BeanIdResolver<Integer, BT>()
		{
			private static final long serialVersionUID = 14343948239048L;

			@Override
			public Integer getIdForBean(final BT bean)
			{
				return bean.getId();
			}
		});
		
	}
	
	public HierarchicalBeanContainer(final Class<BT> type, final BT root)
	{
		this(type);
		addRoot(root);
	}


	@Override
	public BeanItem<BT> addBean(final BT bean)
	{
		final BeanItem<BT> item = super.addBean(bean);
		for (final BT child : bean.getChildren())
		{
			addBean(child);
		}
		return item;
	}

	@Override
	public Collection<Integer> getChildren(final Object itemId)
	{
		final BT bean = getBeanById(itemId);
		if (bean == null)
		{
			return ImmutableList.of();
		}
		final Collection<Integer> childrenIds = bean.getChildrenIds();
		return retainFilteredIds(childrenIds);
	}

	@Override
	public Integer getParent(final Object itemId)
	{
		final BT bean = getBeanById(itemId);
		if(bean == null)
		{
			return null;
		}
		
		final int parentId = bean.getParentId();
		if (!isVisibleItemId(parentId))
		{
			return null;
		}
		return parentId;
	}

	private final BT getBeanById(final Object itemId)
	{
		final BeanItem<BT> beanItem = getItem(itemId);
		if(beanItem == null)
		{
			return null;
		}
		final BT bean = beanItem.getBean();
		if(bean == null)
		{
			return null;
		}
		
		if (!isVisibleItemId(bean.getId()))
		{
			return null;
		}
		
		return bean;
	}

	@Override
	public Collection<Integer> rootItemIds()
	{
		return retainFilteredIds(rootItemIds);
	}

	public void addRoot(final BT bean)
	{
		final int id = bean.getId();

		if (rootItemIds.contains(id))
		{
			return;
		}
		
		rootItemIds.add(id);
		addBean(bean);
	}

	public void addRoots(final Collection<BT> beans)
	{
		if (beans == null || beans.isEmpty())
		{
			return;
		}
		
		for (final BT bean : beans)
		{
			addRoot(bean);
		}
	}

	public void removeRootById(final int id)
	{
		rootItemIds.remove(id);
	}

	@Override
	public boolean setParent(final Object itemId, final Object newParentId) throws UnsupportedOperationException
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean areChildrenAllowed(final Object itemId)
	{
		return hasChildren(itemId);
	}

	@Override
	public boolean setChildrenAllowed(final Object itemId, final boolean areChildrenAllowed) throws UnsupportedOperationException
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isRoot(final Object itemId)
	{
		final BT bean = getBeanById(itemId);
		if(bean == null)
		{
			return false;
		}
		final Collection<Integer> rootIds = rootItemIds();
		return rootIds.contains(bean.getId());
	}

	@Override
	public boolean hasChildren(final Object itemId)
	{
		return !getChildren(itemId).isEmpty();
	}
	
	private final Collection<Integer> retainFilteredIds(final Collection<Integer> ids)
	{
		if (ids.isEmpty())
		{
			return ids;
		}
		
		final List<Integer> filteredItemIds = getFilteredItemIds();
		if(filteredItemIds == null)
		{
			return ids;
		}
		
		final List<Integer> idsFiltered = new ArrayList<>(ids);
		if (idsFiltered.retainAll(filteredItemIds))
		{
			return idsFiltered;
		}
		
		return ids;
	}
	
	private boolean isVisibleItemId(final Integer id)
	{
		final List<Integer> filteredItemIds = getFilteredItemIds();
		if(filteredItemIds == null)
		{
			return true;
		}

		return filteredItemIds.contains(id);
	}
	
	
}