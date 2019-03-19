package org.adempiere.ad.modelvalidator;

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


import org.compiere.model.I_AD_Client;
import org.compiere.model.ModelValidator;
import org.compiere.util.Ini;

/**
 * Model Interceptor interface.
 * 
 * This is the replacement of {@link ModelValidator} which will become deprecated.
 * 
 * @author tsa
 * 
 */
public interface IModelInterceptor
{
	public void initialize(IModelValidationEngine engine, I_AD_Client client);

	/**
	 * Get Client to be monitored.
	 * 
	 * If the interceptor was not already initialized or it was registered for all clients this method will return <code>-1</code>.
	 * 
	 * @return AD_Client_ID or <code>-1</code>
	 */
	public int getAD_Client_ID();

	/**
	 * Called when user logs in.
	 * 
	 * NOTE:
	 * <ul>
	 * <li>called before preferences are set
	 * <li>called only if we run in Client mode (see {@link Ini#isSwingClient()})
	 * </ul>
	 * 
	 * @param AD_Org_ID org
	 * @param AD_Role_ID role
	 * @param AD_User_ID user
	 */
	public void onUserLogin(int AD_Org_ID, int AD_Role_ID, int AD_User_ID);

	/**
	 * Model Change of a monitored Table. Called after PO.beforeSave/PO.beforeDelete when you called addModelChange for the table
	 * 
	 * @param model persistent object
	 * @param changeType
	 * @exception Exception if the recipient wishes the change to be not accept.
	 */
	public void onModelChange(Object model, ModelChangeType changeType) throws Exception;

	/**
	 * Validate Document. Called as first step of DocAction.prepareIt or at the end of DocAction.completeIt when you called addDocValidate for the table. Note that totals, etc. may not be correct
	 * before the prepare stage.
	 * 
	 * @param model persistent object
	 * @param timing
	 */
	public void onDocValidate(Object model, DocTimingType timing) throws Exception;

}
