package de.metas.ui.web.vaadin.i18n;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

import org.adempiere.util.Check;
import org.compiere.util.Util;

import com.vaadin.ui.Component;
import com.vaadin.ui.HasComponents.ComponentAttachDetachNotifier;
import com.vaadin.ui.HasComponents.ComponentAttachEvent;
import com.vaadin.ui.HasComponents.ComponentAttachListener;

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

public class ResourceBundleTranslator
{
	private ResourceBundle res;
	private final List<TranslatableItem> items = new ArrayList<>();

	public ResourceBundleTranslator(final ResourceBundle res)
	{
		super();
		Check.assumeNotNull(res, "res not null");
		this.res = res;
	}

	public void addComponent(final Component component, final String caption)
	{
		final ComponentCaptionItem item = new ComponentCaptionItem(component, caption);
		items.add(item);
		translate(item);
	}

	public void addComponent(final Component component)
	{
		final String caption = component.getCaption();
		addComponent(component, caption);
	}

	public void addComponents(final Component... components)
	{
		if (components == null || components.length == 0)
		{
			return;
		}
		for (final Component component : components)
		{
			addComponent(component);
		}
	}
	
	public void addOnComponentAttach(final ComponentAttachDetachNotifier notifier)
	{
		notifier.addComponentAttachListener(new ComponentAttachListener()
		{
			private static final long serialVersionUID = 1L;

			@Override
			public void componentAttachedToContainer(ComponentAttachEvent event)
			{
				final Component comp = event.getAttachedComponent();
				addComponent(comp);
			}
		});
	}

	public void translate()
	{
		for (final Iterator<TranslatableItem> it = items.iterator(); it.hasNext();)
		{
			final TranslatableItem item = it.next();
			if (item.isExpired())
			{
				it.remove();
				continue;
			}

			translate(item);
		}
	}
	
	private final void translate(final TranslatableItem item)
	{
		final String name = item.getName();
		if(Check.isEmpty(name, true))
		{
			return;
		}
		if (!res.containsKey(name))
		{
			return;
		}
		
		final String trl = Util.cleanAmp(res.getString(name));
		item.setTranslation(trl);
	}

	public void setResourceBundle(final ResourceBundle res)
	{
		Check.assumeNotNull(res, "res not null");
		if (this.res == res)
		{
			return;
		}

		this.res = res;
		translate();
	}

	public static interface TranslatableItem
	{
		String getName();

		void setTranslation(final String trl);

		boolean isExpired();
	}

	private static final class ComponentCaptionItem implements TranslatableItem
	{
		private final Reference<Component> componentRef;
		private final String caption;

		public ComponentCaptionItem(final Component component, final String caption)
		{
			super();

			Check.assumeNotNull(component, "component not null");
			this.componentRef = new WeakReference<>(component);
			this.caption = caption;
		}

		@Override
		public String getName()
		{
			return caption;
		}

		@Override
		public void setTranslation(final String trl)
		{
			final Component component = componentRef.get();
			if (component == null)
			{
				return;
			}
			component.setCaption(trl);
		}

		@Override
		public boolean isExpired()
		{
			return componentRef.get() == null;
		}
	}
}
