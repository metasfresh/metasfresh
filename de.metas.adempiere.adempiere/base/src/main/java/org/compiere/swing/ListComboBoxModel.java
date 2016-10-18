package org.compiere.swing;

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


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;

import org.adempiere.util.Check;

/**
 * Wraps a given given {@link List} (copy) and behaves like a {@link ComboBoxModel}.
 * 
 * @author tsa
 *
 * @param <E> element type
 */
public class ListComboBoxModel<E> extends AbstractListModel<E> implements ComboBoxModel<E>
{
	private static final long serialVersionUID = -6815830373015828943L;
	
	public static final <E> ListComboBoxModel<E> ofNullable(final Collection<E> list)
	{
		if(list == null)
		{
			return new ListComboBoxModel<>();
		}
		else
		{
			return new ListComboBoxModel<>(list);
		}
	}
	
	private final List<E> list;
	private E selected = null;

	public ListComboBoxModel()
	{
		super();
		this.list = new ArrayList<>();
	}

	public ListComboBoxModel(final Collection<E> list)
	{
		super();
		this.list = new ArrayList<>(list);
	}
	
	public ListComboBoxModel(final E[] array)
	{
		super();
		this.list = new ArrayList<>(Arrays.asList(array));
	}


	@Override
	public int getSize()
	{
		return list.size();
	}

	@Override
	public E getElementAt(int index)
	{
		return list.get(index);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setSelectedItem(final Object item)
	{
		if (Check.equals(this.selected, item))
		{
			return;
		}

		selected = (E)item;
		fireContentsChanged(this, -1, -1);
	}

	private void selectFirstItemIfOnlyOneItemAndNotSelected()
	{
		if (selected != null)
		{
			return;
		}
		if (list.size() != 1)
		{
			return;
		}
		setSelectedItem(list.get(0));
	}

	@Override
	public E getSelectedItem()
	{
		return selected;
	}

	public boolean add(final E item)
	{
		final boolean added = list.add(item);
		fireIntervalAdded(this, list.size() - 1, list.size() - 1);
		selectFirstItemIfOnlyOneItemAndNotSelected();

		return added;
	}

	public boolean addAll(Collection<? extends E> c)
	{
		if(c == null || c.isEmpty())
		{
			return false;
		}
		
		final int sizeBeforeAdd = list.size();
		final boolean added = list.addAll(c);
		final int sizeAfterAdd = list.size();

		// Fire interval added if any
		final int index0 = sizeBeforeAdd;
		final int index1 = sizeAfterAdd - 1;
		if (index0 <= index1)
		{
			fireIntervalAdded(this, index0, index1);
		}

		selectFirstItemIfOnlyOneItemAndNotSelected();

		return added;
	}
	
	/**
	 * @param item
     * @return <tt>true</tt> if the element was added
	 */
	public boolean addIfAbsent(final E item)
	{
		if (list.contains(item))
		{
			return false;
		}
		return add(item);
	}
	
	public int size()
	{
		return list.size();
	}
	
	public E get(final int index)
	{
		return list.get(index);
	}
	
	public void clear()
	{
		if (list.isEmpty())
		{
			return;
		}
		
		final int sizeBeforeClear = list.size();
		list.clear();
		fireIntervalRemoved(this, 0, sizeBeforeClear - 1);
	}
	
	public void set(final Collection<E> items)
	{
		clear();
		addAll(items);
	}
	
	public void set(final E[] items)
	{
		final List<E> itemsAsList;
		if (items == null || items.length == 0)
		{
			itemsAsList = Collections.emptyList();
		}
		else
		{
			itemsAsList = Arrays.asList(items);
		}
		
		set(itemsAsList);
	}
}
