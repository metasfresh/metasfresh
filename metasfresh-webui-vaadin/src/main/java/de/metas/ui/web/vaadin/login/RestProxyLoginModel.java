package de.metas.ui.web.vaadin.login;

import org.compiere.util.KeyNamePairList;
import org.compiere.util.ValueNamePair;
import org.compiere.util.ValueNamePairList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import de.metas.ui.web.login.LoginController;
import de.metas.ui.web.login.LoginModel;
import de.metas.ui.web.vaadin.VaadinClientApplication;
import de.metas.ui.web.window.shared.login.LoginAuthRequest;
import de.metas.ui.web.window.shared.login.LoginAuthResponse;
import de.metas.ui.web.window.shared.login.LoginCompleteRequest;

/*
 * #%L
 * metasfresh-webui-api
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

public class RestProxyLoginModel implements LoginModel
{
	private final String restEndpointUrl = "http://localhost:8080" + LoginController.ENDPOINT;

	@Autowired
	private RestTemplate restTemplate;

	public RestProxyLoginModel()
	{
		super();
		VaadinClientApplication.autowire(this);
	}

	private final String buildURL(final String path)
	{
		return restEndpointUrl + path;
	}

	@Override
	public LoginAuthResponse authenticate(final LoginAuthRequest request)
	{
		return restTemplate.postForObject(buildURL("/auth"), request, LoginAuthResponse.class);
	}

	@Override
	public void loginComplete(final LoginCompleteRequest request)
	{
		restTemplate.postForObject(buildURL("/loginComplete"), request, Void.class);
	}

	@Override
	public KeyNamePairList getAD_Clients(final int adRoleId)
	{
		return restTemplate.getForObject(buildURL("/clients/" + adRoleId), KeyNamePairList.class);
	}

	@Override
	public KeyNamePairList getAD_Orgs(final int adClientId)
	{
		return restTemplate.getForObject(buildURL("/orgs/" + adClientId), KeyNamePairList.class);
	}

	@Override
	public KeyNamePairList getM_Warehouses(final int adOrgId)
	{
		return restTemplate.getForObject(buildURL("/warehouses/" + adOrgId), KeyNamePairList.class);
	}

	@Override
	public ValueNamePair setLanguage(final ValueNamePair language)
	{
		return restTemplate.postForObject(buildURL("/language"), language, ValueNamePair.class);
	}

	@Override
	public ValueNamePair getLanguage()
	{
		return restTemplate.getForObject(buildURL("/language"), ValueNamePair.class);
	}

	@Override
	public ValueNamePairList getAvailableLanguages()
	{
		return restTemplate.getForObject(buildURL("/getAvailableLanguages"), ValueNamePairList.class);
	}

	@Override
	public boolean isShowWarehouseOnLogin()
	{
		return restTemplate.getForObject(buildURL("/showWarehouseOnLogin"), Boolean.class);
	}
}
