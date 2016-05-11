package de.metas.ui.web.vaadin;

import com.google.common.eventbus.Subscribe;
import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.annotations.Push;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Widgetset;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Component;
import com.vaadin.ui.UI;

import de.metas.ui.web.vaadin.event.UIEventBus;
import de.metas.ui.web.vaadin.login.LoginPresenter;
import de.metas.ui.web.vaadin.login.MainView;
import de.metas.ui.web.vaadin.login.event.UserLoggedInEvent;
import de.metas.ui.web.vaadin.session.UserSession;

/*
 * #%L
 * de.metas.endcustomer.mf15.webui
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

@SpringUI
@PreserveOnRefresh
@Widgetset("de.metas.ui.web.vaadin.widgetset.metasfreshWidgetSet")
@Theme(de.metas.ui.web.vaadin.theme.Theme.NAME)
@SuppressWarnings("serial")
@Push
public class RootUI extends UI
{
	private final UIEventBus eventBus = new UIEventBus();

	@Override
	protected void init(final VaadinRequest request)
	{
		UIEventBus.register(this);
		updateContent();
		
	}
	
	private final void updateContent()
	{
		final Component view;
		final UserSession userSession = UserSession.getCurrent();
		if (!userSession.isLoggedIn())
		{
			view = new LoginPresenter().getComponent();
		}
		else
		{
			view = new MainView();
		}
		setContent(view);
	}
	
	public static RootUI getCurrentRootUI()
	{
		return (RootUI)getCurrent();
	}

	public static UIEventBus getCurrentEventBus()
	{
		return getCurrentRootUI().eventBus;
	}
	
	@Subscribe
	public void onUserLoginRequestEvent(final UserLoggedInEvent event)
	{
		updateContent();
	}
}
