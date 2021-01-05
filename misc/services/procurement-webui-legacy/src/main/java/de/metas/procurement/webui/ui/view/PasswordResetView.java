package de.metas.procurement.webui.ui.view;

import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.gwt.thirdparty.guava.common.base.Throwables;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Page;
import com.vaadin.ui.CustomComponent;

import de.metas.procurement.webui.Application;
import de.metas.procurement.webui.MFProcurementUI;
import de.metas.procurement.webui.event.UIEventBus;
import de.metas.procurement.webui.event.UserLoggedInEvent;
import de.metas.procurement.webui.model.User;
import de.metas.procurement.webui.service.ILoginService;
import de.metas.procurement.webui.ui.component.MFNavigator;

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
public class PasswordResetView extends CustomComponent implements View
{
	public static final String NAME = "password-reset";
	private static final String STYLE = "PasswordResetView";

	private final transient Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired(required = true)
	ILoginService loginService;

	public PasswordResetView()
	{
		super();
		Application.autowire(this);

		addStyleName(STYLE);
	}

	@Override
	public void enter(final ViewChangeEvent event)
	{
		try
		{
			final String parameters = event.getParameters();
			final String passwordResetKey = parameters;
			final User user = loginService.resetPassword(passwordResetKey);

			final String afterLoginMessage = "New password: " + user.getPassword();
			UIEventBus.post(UserLoggedInEvent.of(user, afterLoginMessage));
		}
		catch (Exception e)
		{
			// TODO: shall we display a notification to the user?
			logger.warn("Error while reseting password", e);
			MFProcurementUI.getCurrentNavigator().navigateToDefaultView();
		}
	}

	public static final URI buildPasswordResetURI(final String passwordResetKey)
	{
		try
		{
			final URI uri = Page.getCurrent().getLocation();
			final String query = null;
			final String fragment = MFNavigator.createURIFragment(NAME, passwordResetKey);
			final URI resetURI = new URI(uri.getScheme(), uri.getAuthority(), uri.getPath(), query, fragment);
			System.out.println(resetURI);
			return resetURI;
		}
		catch (Exception e)
		{
			throw Throwables.propagate(e);
		}
	}

}
