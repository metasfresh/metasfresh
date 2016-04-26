package de.metas.ui.web.vaadin.window.components;

import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

import de.metas.ui.web.vaadin.window.model.TabModel;

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
public class AWindowTab extends VerticalLayout
{
	private final TabModel tabModel;

	// UI
	private final ATabToolbar toolbar;
	private final AWindowTabFormLayout formLayout;

	public AWindowTab(final TabModel tabModel)
	{
		super();
		this.tabModel = tabModel;
		setSizeFull();
		setCaption(tabModel.getName());

		toolbar = new ATabToolbar(tabModel.getToolbarModel());
		addComponent(toolbar);

		{
			formLayout = new AWindowTabFormLayout(tabModel.getWindowContext(), tabModel.getDescriptor());
			formLayout.setRow(tabModel.getCurrentRow());
			
			final Panel formLayoutContainer = new Panel();
			formLayoutContainer.setHeight("400px");
			formLayoutContainer.setContent(formLayout);
			addComponent(formLayoutContainer);
		}

		// final AWindowTabGridLayout gridLayout = new AWindowTabGridLayout(tabModel.getWindowContext(), tabModel.getRowsContainer());
		// addComponent(gridLayout);
	}

	public void refresh()
	{
		tabModel.query();
	}
}
