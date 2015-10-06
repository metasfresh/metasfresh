package org.adempiere.service;

/*
 * #%L
 * ADempiere ERP - Base
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


import java.util.List;
import java.util.Properties;

import org.adempiere.util.ISingletonService;
import org.compiere.model.I_AD_SysConfig;

public interface ISysConfigDAO extends ISingletonService
{

	I_AD_SysConfig retrieveSysConfig(Properties ctx, String name, int AD_Client_ID, int AD_Org_ID, String trxName);

	/**
	 * Note: If there is more than one matching record, the value of the first <code>AD_SysConfig</code> record, according to <code>ORDER BY AD_Client_ID DESC, AD_Org_ID DESC</code> will be returned.
	 * 
	 * @param Name
	 * @param defaultValue
	 * @param AD_Client_ID
	 * @param AD_Org_ID
	 * @return
	 */
	String retrieveSysConfigValue(String Name, String defaultValue, int AD_Client_ID, int AD_Org_ID);

	List<String> retrieveNamesForPrefix(String prefix, int adClientId, int adOrgId);
}