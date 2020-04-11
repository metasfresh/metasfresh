package de.metas.procurement.webui.ui.view;

import com.vaadin.addon.touchkit.ui.NavigationManager;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;

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
public class MainView extends NavigationManager implements View
{
	private DailyReportingView dailyReportingView;
	
	public MainView()
	{
		super();
	}

	@Override
	public void enter(ViewChangeEvent event)
	{
		if (dailyReportingView == null)
		{
			dailyReportingView = new DailyReportingView();
			setCurrentComponent(dailyReportingView);
		}
	}
}
