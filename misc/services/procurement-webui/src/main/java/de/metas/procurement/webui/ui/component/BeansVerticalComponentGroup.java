package de.metas.procurement.webui.ui.component;

import com.google.gwt.thirdparty.guava.common.base.Objects;
import com.vaadin.addon.touchkit.ui.VerticalComponentGroup;
import com.vaadin.data.Container;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Component;

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
public abstract class BeansVerticalComponentGroup<BT> extends VerticalComponentGroup
{
	private BeanItemContainer<BT> container;

	private final Container.ItemSetChangeListener listener = new Container.ItemSetChangeListener()
	{

		@Override
		public void containerItemSetChange(final Container.ItemSetChangeEvent event)
		{
			updateAll();
		}
	};

	public BeansVerticalComponentGroup()
	{
		super();
	}

	public final void setContainerDataSource(final BeanItemContainer<BT> containerNew)
	{
		if (Objects.equal(this.container, containerNew))
		{
			return;
		}

		if (this.container != null)
		{
			this.container.removeItemSetChangeListener(listener);
		}

		this.container = containerNew;

		if (this.container != null)
		{
			this.container.addItemSetChangeListener(listener);
		}
		updateAll();
	}
	
	public final BeanItemContainer<BT> getContainerDataSource()
	{
		return container;
	}

	private void updateAll()
	{
		removeAllComponents();

		if (container != null)
		{
			for (final Object itemId : container.getItemIds())
			{
				final BeanItem<BT> item = container.getItem(itemId);
				final Component itemComp = createItemComponent(item);
				addComponent(itemComp);
			}
		}
	}

	protected abstract Component createItemComponent(final BeanItem<BT> item);
}