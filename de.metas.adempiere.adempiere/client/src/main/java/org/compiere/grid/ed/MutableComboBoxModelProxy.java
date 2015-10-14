package org.compiere.grid.ed;

/*
 * #%L
 * de.metas.adempiere.adempiere.client
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


import javax.swing.MutableComboBoxModel;
import javax.swing.event.EventListenerList;
import javax.swing.event.ListDataListener;

/**
 * A {@link MutableComboBoxModel} implementation on which all calls are delegated to an underlying {@link MutableComboBoxModel} (see {@link #setDelegate(MutableComboBoxModel)}).
 * 
 * @author tsa
 * 
 */
public class MutableComboBoxModelProxy implements MutableComboBoxModel
{
	private MutableComboBoxModel delegate = null;
	private final EventListenerList listenerList = new EventListenerList();

	private static final MutableComboBoxModel COMBOBOXMODEL_NULL = new MutableComboBoxModel()
	{
		@Override
		public void addListDataListener(ListDataListener l)
		{
		}

		@Override
		public void removeListDataListener(ListDataListener l)
		{
		}

		@Override
		public int getSize()
		{
			return 0;
		}

		@Override
		public Object getElementAt(int index)
		{
			throw new IndexOutOfBoundsException();
		}

		@Override
		public void setSelectedItem(Object anItem)
		{
		}

		@Override
		public Object getSelectedItem()
		{
			return null;
		}

		@Override
		public void removeElementAt(int index)
		{
		}

		@Override
		public void removeElement(Object obj)
		{
		}

		@Override
		public void insertElementAt(Object obj, int index)
		{
		}

		@Override
		public void addElement(Object obj)
		{
		}
	};

	public MutableComboBoxModelProxy()
	{
		super();
	}

	public MutableComboBoxModelProxy(final MutableComboBoxModel delegate)
	{
		super();
		setDelegate(delegate);
	}

	private final MutableComboBoxModel getDelegateToUse()
	{
		if (delegate == null)
		{
			return COMBOBOXMODEL_NULL;
		}
		return delegate;
	}

	public void setDelegate(final MutableComboBoxModel delegateNew)
	{
		if (this.delegate == delegateNew)
		{
			// nothing changed
			return;
		}

		// Unregister listeners on old lookup
		final MutableComboBoxModel delegateOld = this.delegate;
		if (delegateOld != null)
		{
			for (ListDataListener l : listenerList.getListeners(ListDataListener.class))
			{
				delegateOld.removeListDataListener(l);
			}
		}

		//
		// Setup new Lookup
		this.delegate = delegateNew;

		// Register listeners on new lookup
		if (this.delegate != null)
		{
			for (ListDataListener l : listenerList.getListeners(ListDataListener.class))
			{
				this.delegate.addListDataListener(l);
			}
		}
	}

	@Override
	public void addListDataListener(final ListDataListener l)
	{
		listenerList.add(ListDataListener.class, l);

		if (delegate != null)
		{
			delegate.addListDataListener(l);
		}
	}

	@Override
	public void removeListDataListener(final ListDataListener l)
	{
		listenerList.remove(ListDataListener.class, l);

		if (delegate != null)
		{
			delegate.removeListDataListener(l);
		}
	}

	@Override
	public int getSize()
	{
		return getDelegateToUse().getSize();
	}

	@Override
	public Object getElementAt(int index)
	{
		return getDelegateToUse().getElementAt(index);
	}

	@Override
	public void setSelectedItem(Object anItem)
	{
		getDelegateToUse().setSelectedItem(anItem);
	}

	@Override
	public Object getSelectedItem()
	{
		return getDelegateToUse().getSelectedItem();
	}

	@Override
	public void addElement(Object obj)
	{
		getDelegateToUse().addElement(obj);
	}

	@Override
	public void removeElement(Object obj)
	{
		getDelegateToUse().removeElement(obj);
	}

	@Override
	public void insertElementAt(Object obj, int index)
	{
		getDelegateToUse().insertElementAt(obj, index);
	}

	@Override
	public void removeElementAt(int index)
	{
		getDelegateToUse().removeElementAt(index);
	}
}
