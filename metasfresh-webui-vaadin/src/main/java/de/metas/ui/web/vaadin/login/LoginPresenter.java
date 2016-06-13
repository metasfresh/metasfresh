package de.metas.ui.web.vaadin.login;

import java.util.Locale;
import java.util.ResourceBundle;

import org.compiere.util.KeyNamePair;
import org.compiere.util.KeyNamePairList;
import org.compiere.util.ValueNamePair;

import com.vaadin.ui.Component;
import com.vaadin.ui.UI;

import de.metas.ui.web.login.LoginModel;
import de.metas.ui.web.vaadin.session.UserSession;
import de.metas.ui.web.window.shared.login.LoginAuthRequest;
import de.metas.ui.web.window.shared.login.LoginAuthResponse;
import de.metas.ui.web.window.shared.login.LoginCompleteRequest;

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
	private final LoginModel loginModel = new RestProxyLoginModel();

	private static final String RESOURCE = "org.compiere.apps.ALoginRes";
	private ResourceBundle resourceBundle = ResourceBundle.getBundle(RESOURCE);

	private final boolean warehouseVisible;

	public LoginPresenter()
	{
		super();

		loginView.setListener(this);

		loginView.setResourceBundle(resourceBundle);
		loginView.setLanguages(loginModel.getAvailableLanguages().getValues(), loginModel.getLanguage());
		
		this.warehouseVisible = loginModel.isShowWarehouseOnLogin();
		loginView.setWarehouseVisible(warehouseVisible);
	}

	public Component getComponent()
	{
		return loginView.getComponent();
	}

	@Override
	public void viewAuthenticate(final String username, final String password)
	{
		final LoginAuthResponse response = loginModel.authenticate(LoginAuthRequest.of(username, password));

		loginView.setRoles(response.getRoles());
		loginView.showRolePanel();
	}

	@Override
	public void viewLoginComplete(final KeyNamePair role, final KeyNamePair client, final KeyNamePair org, final KeyNamePair warehouse)
	{
		final LoginCompleteRequest request = LoginCompleteRequest.of(role, client, org, warehouse);
		loginModel.loginComplete(request);
		
		UserSession.getCurrent().setLoggedIn(true);
		
		//UIEventBus.post(new UserLoggedInEvent());
		UI.getCurrent().getNavigator().navigateTo("/");
	}

	@Override
	public void viewLanguageChanged(final ValueNamePair language)
	{
		final ValueNamePair languageActual = loginModel.setLanguage(language);

		final String adLanguage = languageActual.getValue();
		final Locale locale = getLocaleForAD_Language(adLanguage);

		resourceBundle = ResourceBundle.getBundle(RESOURCE, locale);
		loginView.setResourceBundle(resourceBundle);
	}

	private static final Locale getLocaleForAD_Language(final String adLanguage)
	{
		final String languageTag = adLanguage.replace("_", "-");
		final Locale locale = Locale.forLanguageTag(languageTag);
		if (locale == null)
		{
			throw new IllegalArgumentException("No locale found for " + adLanguage);
		}
		return locale;
	}

	@Override
	public void viewRoleChanged(final KeyNamePair role)
	{
		final KeyNamePairList clients = loginModel.getAD_Clients(role == null ? -1 : role.getKey());
		loginView.setClients(clients.getValues());
	}

	@Override
	public void viewClientChanged(final KeyNamePair client)
	{
		final KeyNamePairList orgs = loginModel.getAD_Orgs(client == null ? -1 : client.getKey());
		loginView.setOrgs(orgs.getValues());
	}

	@Override
	public void viewOrgChanged(final KeyNamePair org)
	{
		final KeyNamePairList warehouses;
		if (warehouseVisible)
		{
			warehouses = loginModel.getM_Warehouses(org == null ? -1 : org.getKey());
		}
		else
		{
			warehouses = KeyNamePairList.of();
		}

		loginView.setWarehouses(warehouses.getValues());
	}
}
