package de.metas.ui.web.vaadin;

import com.google.common.eventbus.Subscribe;
import com.google.gwt.thirdparty.guava.common.base.Throwables;
import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.annotations.Push;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Widgetset;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.UI;

import de.metas.ui.web.vaadin.components.navigator.MFNavigator;
import de.metas.ui.web.vaadin.components.navigator.MFNavigator.CachedViewProvider;
import de.metas.ui.web.vaadin.event.UIEventBus;
import de.metas.ui.web.vaadin.login.LoginNavigationView;
import de.metas.ui.web.vaadin.login.event.UserLoggedInEvent;
import de.metas.ui.web.vaadin.session.UserSession;
import de.metas.ui.web.vaadin.window.prototype.order.WindowViewProvider;

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

		MFNavigator.createAndBind(this)
				.setLoginView(LoginNavigationView.class, () -> UserSession.getCurrent().isLoggedIn())
				.addViewProvider(CachedViewProvider.of(MFNavigator.VIEWNAME_DEFAULT, MainNavigationView.class))
				.addViewProvider(new WindowViewProvider());
		//
		;

		setErrorHandler(event -> {
			final String errorMessage = Throwables.getRootCause(event.getThrowable()).getLocalizedMessage();
			Notification.show("Error", errorMessage, Type.ERROR_MESSAGE);
		});
	}

	public static RootUI getCurrentRootUI()
	{
		return (RootUI)getCurrent();
	}

	public static UIEventBus getCurrentEventBus()
	{
		return getCurrentRootUI().eventBus;
	}

	@Override
	public MFNavigator getNavigator()
	{
		return (MFNavigator)super.getNavigator();
	}

	@Subscribe
	public void onUserLoginRequestEvent(final UserLoggedInEvent event)
	{
		getNavigator().navigateToDefaultView();
	}
}
