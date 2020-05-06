package de.metas.procurement.webui.ui.component;

import com.vaadin.addon.touchkit.ui.NavigationButton;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.util.BeanItem;

/*
 * #%L
 * de.metas.procurement.webui
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
public class BeanItemNavigationButton<BT> extends NavigationButton
{
	private BeanItem<BT> item;

	private final Property.ValueChangeListener propertyListener = new Property.ValueChangeListener()
	{

		@Override
		public void valueChange(final ValueChangeEvent event)
		{
			updateUI();
		}
	};

	public BeanItemNavigationButton()
	{
		super();
	}

	public final void setItem(final BeanItem<BT> item)
	{
		if (this.item == item)
		{
			return;
		}

		if (this.item != null)
		{
			for (final Object propertyId : this.item.getItemPropertyIds())
			{
				final Property<?> property = this.item.getItemProperty(propertyId);
				if (property instanceof Property.ValueChangeNotifier)
				{
					((Property.ValueChangeNotifier)property).removeValueChangeListener(propertyListener);
				}
			}
		}

		this.item = item;

		if (this.item != null)
		{
			for (final Object propertyId : this.item.getItemPropertyIds())
			{
				final Property<?> property = this.item.getItemProperty(propertyId);
				if (property instanceof Property.ValueChangeNotifier)
				{
					((Property.ValueChangeNotifier)property).addValueChangeListener(propertyListener);
				}
			}
		}

		onItemChanged(item);
		updateUI();
	}
	
	protected void onItemChanged(BeanItem<BT> item)
	{
		
	}
	
	protected BT getBean()
	{
		return item == null ? null : item.getBean();
	}

	private final void updateUI()
	{
		final BT bean = item == null ? null : item.getBean();
		updateUI(bean);
	}
	
	protected void updateUI(final BT bean)
	{
		
	}

	@Override
	public void detach()
	{
		super.detach();

//		// unbind the item when this component is removed from layout.
//		setItem(null);
	}

}
