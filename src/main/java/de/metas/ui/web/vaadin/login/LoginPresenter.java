package de.metas.ui.web.vaadin.login;

import java.util.List;

import org.compiere.util.KeyNamePair;
import org.compiere.util.Language;

import com.google.common.collect.ImmutableList;
import com.vaadin.ui.Component;

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

public class LoginPresenter implements LoginViewListener
{
	private final LoginView loginView = new LoginViewImpl();
	private final LoginModel loginModel = new LoginModel();

	public LoginPresenter()
	{
		super();

		loginView.setListener(this);

		loginView.setResourceBundle(loginModel.getResourceBundle());
		loginView.setLanguages(loginModel.getAvailableLanguages(), loginModel.getLanguage());
		loginView.setWarehouseVisible(loginModel.isShowWarehouseOnLogin());
	}

	public Component getComponent()
	{
		return loginView.getComponent();
	}

	@Override
	public void viewAuthenticate(final String username, final String password)
	{
		loginModel.authenticate(username, password);
		
		loginView.setRoles(loginModel.getRoles());
		loginView.showRolePanel();
	}

	@Override
	public void viewLoginComplete(final KeyNamePair role, final KeyNamePair client, final KeyNamePair org, final KeyNamePair warehouse)
	{
		loginModel.loginComplete(role, client, org, warehouse);
		UIEventBus.post(new UserLoggedInEvent());
	}

	@Override
	public void viewLanguageChanged(final Language language)
	{
		loginModel.setLanguage(language);
		loginView.setResourceBundle(loginModel.getResourceBundle());
	}

	@Override
	public void viewRoleChanged(final KeyNamePair role)
	{
		final List<KeyNamePair> clients = loginModel.getAD_Clients(role);
		loginView.setClients(clients);
	}

	@Override
	public void viewClientChanged(final KeyNamePair client)
	{
		final List<KeyNamePair> orgs = loginModel.getAD_Orgs(client);
		loginView.setOrgs(orgs);
	}

	@Override
	public void viewOrgChanged(final KeyNamePair org)
	{
		final List<KeyNamePair> warehouses;
		if (loginModel.isShowWarehouseOnLogin())
		{
			warehouses = loginModel.getM_Warehouses(org);
		}
		else
		{
			warehouses = ImmutableList.of();
		}

		loginView.setWarehouses(warehouses);
	}
}
