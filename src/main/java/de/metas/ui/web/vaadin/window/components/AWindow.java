package de.metas.ui.web.vaadin.window.components;

import java.util.List;

import com.google.common.collect.ImmutableList;
import com.vaadin.ui.TabSheet;

import de.metas.ui.web.vaadin.window.WindowContext;
import de.metas.ui.web.vaadin.window.descriptor.DataWindowDescriptor;
import de.metas.ui.web.vaadin.window.model.TabModel;
import de.metas.ui.web.vaadin.window.model.WindowModel;

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
public class AWindow extends TabSheet
{
	//
	// UI
	private final TabSheet tabSheet;
	private final List<AWindowTab> tabs;

	public AWindow(final int adWindowId)
	{
		super();

		final WindowContext windowContext = WindowContext.builder()
				.setWindowNo(10) // FIXME: HARDCODED
				.build();

		final DataWindowDescriptor windowDescriptor = DataWindowDescriptor.builder()
				.initFrom(windowContext.getWindowNo(), adWindowId)
				.build();

		final WindowModel windowModel = new WindowModel(windowContext, windowDescriptor);

		//
		// UI
		tabSheet = this;
		final ImmutableList.Builder<AWindowTab> tabs = ImmutableList.builder();
		for (final TabModel tabModel : windowModel.getTabModels())
		{
			final AWindowTab tab = new AWindowTab(tabModel);
			tabs.add(tab);

			tabSheet.addTab(tab, tab.getCaption());
		}
		this.tabs = tabs.build();

		//
		// Query
		this.tabs.get(0).refresh();
	}
}
