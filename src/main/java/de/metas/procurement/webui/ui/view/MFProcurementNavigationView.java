package de.metas.procurement.webui.ui.view;

import java.util.Objects;

import com.vaadin.addon.touchkit.ui.NavigationView;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import de.metas.procurement.webui.Application;

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
 * A {@link NavigationView} extension which paints the "Back" button on bottom, right under the toolbar.
 *
 * @author metas-dev <dev@metas-fresh.com>
 *
 */
@SuppressWarnings("serial")
public class MFProcurementNavigationView extends NavigationView
{
	private final VerticalLayout bottomPanel = new VerticalLayout();
	private final Label backButtonPlaceholder = new Label();
	private final Component backButton;
	private Component toolbar = null;
	private boolean displayBackButtonOnBottom = true;

	public MFProcurementNavigationView()
	{
		super();
		Application.autowire(this);
		
		backButton = getLeftComponent();
		bottomPanel.setStyleName("bottom");

		backButtonPlaceholder.setVisible(false);
		backButtonPlaceholder.setEnabled(false);
	}

	@Override
	public void attach()
	{
		super.attach();
		updateLayout();
	}

	public final void setDisplayBackButtonOnBottom(final boolean displayBackButtonOnBottom)
	{
		if (this.displayBackButtonOnBottom == displayBackButtonOnBottom)
		{
			return;
		}

		this.displayBackButtonOnBottom = displayBackButtonOnBottom;
		updateLayout();
	}

	public final boolean isDisplayBackButtonOnBottom()
	{
		return displayBackButtonOnBottom;
	}

	private final void updateLayout()
	{
		if (displayBackButtonOnBottom)
		{
			if (super.getToolbar() != bottomPanel)
			{
				super.setToolbar(bottomPanel);
			}

			bottomPanel.removeAllComponents();

			if (toolbar != null)
			{
				bottomPanel.addComponent(toolbar);
			}

			setLeftComponent(backButtonPlaceholder); // NOTE: we cannot set it to null because Vaadin touchkit will fail miserably so we go with a not visible label as a placeholder
			bottomPanel.addComponent(backButton);
		}
		else
		{
			if (super.getToolbar() != toolbar)
			{
				super.setToolbar(toolbar);
			}

			if (super.getLeftComponent() != backButton)
			{
				setLeftComponent(backButton);
			}
		}
	}

	@Override
	public final void setToolbar(final Component toolbar)
	{
		if (Objects.equals(this.toolbar, toolbar))
		{
			return;
		}

		this.toolbar = toolbar;
		updateLayout();
	}

	@Override
	public final Component getToolbar()
	{
		return toolbar;
	}
}
