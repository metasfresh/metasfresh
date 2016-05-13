package de.metas.ui.web.vaadin.login;

import org.compiere.util.KeyNamePair;
import org.compiere.util.Language;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.CustomComponent;

import de.metas.ui.web.vaadin.Application;
import de.metas.ui.web.vaadin.event.UIEventBus;
import de.metas.ui.web.vaadin.login.event.UserLoggedInEvent;

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
public class LoginNavigationView extends CustomComponent implements View
{
	public LoginNavigationView()
	{
		super();
		setSizeFull();
	}

	@Override
	public void enter(final ViewChangeEvent event)
	{
		if (Application.isTesting())
		{
			final LoginModel loginModel = new LoginModel();
			loginModel.authenticate("SuperUser", "System");
			loginModel.setLanguage(Language.getLanguage("de_DE"));
			loginModel.loginComplete(
					new KeyNamePair(1000000, "Admin") // role
					, new KeyNamePair(1000000, "?") // client
					, new KeyNamePair(1000000, "?") // org
					, (KeyNamePair)null // warehouse
			);
			
			UIEventBus.post(new UserLoggedInEvent());
			
			return;
		}

		setCompositionRoot(new LoginPresenter().getComponent());
	}

}
