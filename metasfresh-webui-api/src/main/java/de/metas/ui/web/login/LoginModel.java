package de.metas.ui.web.login;

import org.compiere.util.KeyNamePairList;
import org.compiere.util.ValueNamePair;
import org.compiere.util.ValueNamePairList;

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

public interface LoginModel
{
	/**
	 * @param username
	 * @param password
	 * @return available roles
	 */
	LoginAuthResponse authenticate(LoginAuthRequest request);

	void loginComplete(LoginCompleteRequest request);

	KeyNamePairList getAD_Clients(int adRoleId);

	KeyNamePairList getAD_Orgs(int adClientId);

	KeyNamePairList getM_Warehouses(int adOrgId);

	/**
	 * Sets the language
	 * @param language
	 * @return language which was actually set
	 */
	ValueNamePair setLanguage(ValueNamePair language);

	ValueNamePair getLanguage();

	ValueNamePairList getAvailableLanguages();

	boolean isShowWarehouseOnLogin();

}