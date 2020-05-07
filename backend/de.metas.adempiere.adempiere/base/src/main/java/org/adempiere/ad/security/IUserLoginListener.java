package org.adempiere.ad.security;

import org.adempiere.ad.session.MFSession;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2015 metas GmbH
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


/**
 * If you want to be notified when user logs in/out, let your model validator implement this interface.
 * 
 * @author tsa
 * 
 */
public interface IUserLoginListener
{
	/**
	 * Called after user login.
	 * 
	 * @param AD_Org_ID
	 * @param AD_Role_ID
	 * @param AD_User_ID
	 */
	// NOTE: method signature shall be the same as org.adempiere.ad.modelvalidator.IModelInterceptor.onUserLogin(int, int, int)
	void onUserLogin(int AD_Org_ID, int AD_Role_ID, int AD_User_ID);

	/**
	 * Called before Logout
	 * 
	 * @param session
	 */
	void beforeLogout(final MFSession session);

	/**
	 * Called after Logout (note, at this moment session is closed)
	 * 
	 * @param session
	 */
	void afterLogout(final MFSession session);
}
