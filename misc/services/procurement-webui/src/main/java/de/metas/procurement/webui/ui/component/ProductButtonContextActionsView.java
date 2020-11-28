package de.metas.procurement.webui.ui.component;

import com.vaadin.addon.touchkit.ui.NavigationButton;
import com.vaadin.addon.touchkit.ui.VerticalComponentGroup;
import com.vaadin.ui.Component;

import de.metas.procurement.webui.ui.component.GenericProductButton.Action;
import de.metas.procurement.webui.ui.view.MFProcurementNavigationView;

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

/**
 * The options/context menu displayed when user right clicks (or long presses) on a Product button.
 * 
 * @author metas-dev <dev@metas-fresh.com>
 *
 */
@SuppressWarnings("serial")
public class ProductButtonContextActionsView extends MFProcurementNavigationView
{
	private Component previousNavigationView;
	private final GenericProductButton<?> productButton;

	public ProductButtonContextActionsView(final GenericProductButton<?> productButton)
	{
		super();
		this.productButton = productButton;
	}

	@Override
	public void attach()
	{
		super.attach();

		this.previousNavigationView = getNavigationManager().getCurrentComponent(); // i.e. our current component

		setCaption(productButton.getCaption());

		final VerticalComponentGroup content = new VerticalComponentGroup();
		for (final Action action : productButton.getActions())
		{
			final ActionButton actionButton = new ActionButton(action);
			actionButton.setTargetView(previousNavigationView);
			content.addComponent(actionButton);
		}

		setContent(content);
	}

	private class ActionButton extends NavigationButton
	{
		private final Action action;

		public ActionButton(final Action action)
		{
			super();
			addStyleName(GenericProductButton.STYLE); // TODO: change CSS to have a common style name for Navigation buttons. Atm we are reusing the style of a product button
			this.action = action;

			setCaption(action.getCaption());
			setDescription(action.getDescription());

			setTargetView(null);
			addClickListener(new NavigationButtonClickListener()
			{
				@Override
				public void buttonClick(final NavigationButtonClickEvent event)
				{
					onClick();
				}
			});
		}

		private void onClick()
		{
			action.execute(productButton);
		}
	}
}
