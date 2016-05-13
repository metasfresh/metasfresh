package de.metas.ui.web.vaadin.window.prototype.order;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;

import de.metas.ui.web.vaadin.components.navigator.MFViewDisplay;
import de.metas.ui.web.vaadin.window.prototype.order.propertyDescriptor.gridWindowVO.VOPropertyDescriptorProvider;

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
public class WindowNavigationView extends CustomComponent implements View
{
	private final int windowId;

	public WindowNavigationView(final int windowId)
	{
		super();
		this.windowId = windowId;
	}

	@Override
	public void enter(final ViewChangeEvent event)
	{
		final WindowPresenter windowPresenter = new WindowPresenter();

		final MFViewDisplay viewDisplay = MFViewDisplay.getMFViewDisplayOrNull(event);
		if (viewDisplay != null)
		{
			windowPresenter.addPropertyValueChangedListener(WindowConstants.PROPERTYNAME_WindowTitle, (propertyName, value) -> {
				viewDisplay.setTitle(value == null ? "" : value.toString());
			});
		}

		final PropertyDescriptor rootPropertyDescriptor = new VOPropertyDescriptorProvider().provideForWindow(windowId);
		windowPresenter.setRootPropertyDescriptor(rootPropertyDescriptor);

		final Component viewComp = windowPresenter.getViewComponent();
		setCompositionRoot(viewComp);
	}

}
