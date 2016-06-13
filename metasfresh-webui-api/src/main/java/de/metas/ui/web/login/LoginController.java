package de.metas.ui.web.login;

import org.compiere.util.KeyNamePairList;
import org.compiere.util.ValueNamePair;
import org.compiere.util.ValueNamePairList;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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

@RestController
@RequestMapping(value = LoginController.ENDPOINT)
public class LoginController
{
	public static final String ENDPOINT = "/rest/api/login";

	private volatile LoginModel _loginModel = null; // lazy
	
	private LoginModel getLoginModel()
	{
		if (_loginModel == null)
		{
			synchronized (this)
			{
				if (_loginModel == null)
				{
					_loginModel = new LoginModelImpl();
				}
			}
		}
		return _loginModel;
	}

	@RequestMapping(value = "/auth", method = RequestMethod.POST)
	public LoginAuthResponse authenticate(@RequestBody final LoginAuthRequest request)
	{
		return getLoginModel().authenticate(request);
	}
	
	@RequestMapping(value = "/loginComplete", method = RequestMethod.POST)
	public void loginComplete(@RequestBody final LoginCompleteRequest request)
	{
		getLoginModel().loginComplete(request);
	}

	@RequestMapping(value = "/getAvailableLanguages", method = RequestMethod.GET)
	public ValueNamePairList getAvailableLanguages()
	{
		return getLoginModel().getAvailableLanguages();
	}

	@RequestMapping(value = "/language", method = RequestMethod.POST)
	public ValueNamePair setLanguage(@RequestBody final ValueNamePair language)
	{
		return getLoginModel().setLanguage(language);
	}

	@RequestMapping(value = "/language", method = RequestMethod.GET)
	public ValueNamePair getLanguage()
	{
		return getLoginModel().getLanguage();
	}

	@RequestMapping(value = "/clients/{AD_Role_ID}", method = RequestMethod.GET)
	public KeyNamePairList getAD_Clients(@PathVariable("AD_Role_ID") final int adRoleId)
	{
		return getLoginModel().getAD_Clients(adRoleId);
	}

	@RequestMapping(value = "/orgs/{AD_Client_ID}", method = RequestMethod.GET)
	public KeyNamePairList getAD_Orgs(@PathVariable("AD_Client_ID") final int adClientId)
	{
		return getLoginModel().getAD_Orgs(adClientId);
	}

	@RequestMapping(value = "/warehouses/{AD_Org_ID}", method = RequestMethod.GET)
	public KeyNamePairList getM_Warehouses(@PathVariable("AD_Org_ID") final int adOrgId)
	{
		return getLoginModel().getM_Warehouses(adOrgId);
	}

	@RequestMapping(value = "/showWarehouseOnLogin", method = RequestMethod.GET)
	public boolean isShowWarehouseOnLogin()
	{
		return getLoginModel().isShowWarehouseOnLogin();
	}

}
