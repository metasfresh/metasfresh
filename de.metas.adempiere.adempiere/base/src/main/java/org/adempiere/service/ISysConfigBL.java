package org.adempiere.service;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.List;
import java.util.Map;

import org.adempiere.util.ISingletonService;

public interface ISysConfigBL extends ISingletonService
{
	/**
	 * Get system configuration property of type string
	 *
	 * @param Name
	 * @param defaultValue
	 * @return String
	 */
	public String getValue(String Name, String defaultValue);

	/**
	 * Get system configuration property of type string
	 *
	 * @param Name
	 * @return String
	 */
	public String getValue(String Name);

	/**
	 * Get system configuration property of type int
	 *
	 * @param Name
	 * @param defaultValue
	 * @return int
	 */
	public int getIntValue(String Name, int defaultValue);

	/**
	 * Get system configuration property of type double
	 *
	 * @param Name
	 * @param defaultValue
	 * @return double
	 */
	public double getDoubleValue(String Name, double defaultValue);

	/**
	 * Get system configuration property of type boolean
	 *
	 * @param Name
	 * @param defaultValue
	 * @return boolean
	 */
	public boolean getBooleanValue(String Name, boolean defaultValue);

	/**
	 * Get client configuration property of type string.<br>
	 * If there is more than one matching record, the value of the first <code>AD_SysConfig</code> record, according to <code>ORDER BY AD_Client_ID DESC, AD_Org_ID DESC</code> will be returned.
	 *
	 * @param Name
	 * @param defaultValue
	 * @param AD_Client_ID the system will retrieve the value from an <code>AD_SysConfig</code> record that has the given client-ID or <code>0</code>, prefering a records with a not-<code>0</code> ID.
	 * @return String
	 */
	public String getValue(String Name, String defaultValue, int AD_Client_ID);

	/**
	 * Get system configuration property of type string.<br>
	 * If there is more than one matching record, the value of the first <code>AD_SysConfig</code> record, according to <code>ORDER BY AD_Client_ID DESC, AD_Org_ID DESC</code> will be returned.
	 *
	 * @param Name
	 * @param Client ID
	 * @return String
	 */
	public String getValue(String Name, int AD_Client_ID);

	/**
	 * Get system configuration property of type int
	 *
	 * @param Name
	 * @param defaultValue
	 * @param Client ID
	 * @return int
	 */
	public int getIntValue(String Name, int defaultValue, int AD_Client_ID);

	/**
	 * Get system configuration property of type double
	 *
	 * @param Name
	 * @param defaultValue
	 * @param Client ID
	 * @return double
	 */
	public double getDoubleValue(String Name, double defaultValue, int AD_Client_ID);

	/**
	 * Get system configuration property of type boolean. Valid SysConfig can be <code>Y</code>, <code>N</code> (case is ignored) and whatever {@link Boolean#valueOf(String)} can deal with.
	 *
	 * @param Name
	 * @param defaultValue
	 * @param Client ID
	 * @return boolean
	 */
	public boolean getBooleanValue(String Name, boolean defaultValue, int AD_Client_ID);

	/**
	 * Get client configuration property of type string
	 *
	 * @param Name
	 * @param defaultValue
	 * @param Client ID
	 * @param Organization ID
	 * @return String
	 */
	public String getValue(String Name, String defaultValue, int AD_Client_ID, int AD_Org_ID);

	/**
	 * Get system configuration property of type string
	 *
	 * @param Name
	 * @param Client ID
	 * @param Organization ID
	 * @return String
	 */
	public String getValue(String Name, int AD_Client_ID, int AD_Org_ID);

	/**
	 * Get system configuration property of type int
	 *
	 * @param Name
	 * @param defaultValue
	 * @param Client ID
	 * @param Organization ID
	 * @return int
	 */
	public int getIntValue(String Name, int defaultValue, int AD_Client_ID, int AD_Org_ID);

	/**
	 * Get system configuration property of type double
	 *
	 * @param Name
	 * @param defaultValue
	 * @param Client ID
	 * @param Organization ID
	 * @return double
	 */
	public double getDoubleValue(String Name, double defaultValue, int AD_Client_ID, int AD_Org_ID);

	/**
	 * Get system configuration property of type boolean
	 *
	 * @param Name
	 * @param defaultValue
	 * @param Client ID
	 * @param Organization ID
	 * @return boolean
	 */
	public boolean getBooleanValue(String Name, boolean defaultValue, int AD_Client_ID, int AD_Org_ID);

	public void setValue(String name, int value, int AD_Org_ID);

	public void setValue(String name, double value, int AD_Org_ID);

	public void setValue(String name, boolean value, int AD_Org_ID);

	public void setValue(String name, String value, int AD_Org_ID);

	List<String> getNamesForPrefix(String prefix, int adClientId, int adOrgId);

	/**
	 * Returns a mapping (name -> value) that includes all AD_SysConfig records whose <code>Name</code> has the given <code>prefix</code>.
	 *
	 * @param prefix
	 * @param adClientId
	 * @param adOrgId
	 * @return
	 */
	Map<String, String> getValuesForPrefix(String prefix, int adClientId, int adOrgId);

	/**
	 * This method is similar {@link #getValuesForPrefix(String, int, int)}, but has the additional <code>removePrefix</code> parameter.
	 *
	 * @param prefix
	 * @param removePrefix if <code>false</code>, then the result is the same as the result of {@link #getValuesForPrefix(String, int, int)}. If <code>true</code>, then the given prefix is removed
	 *            from the AD_SysConfig <code>Name</code> values before adding them to the result map.
	 * @param adClientId
	 * @param adOrgId
	 * @return immutable map of name/value
	 */
	Map<String, String> getValuesForPrefix(String prefix, boolean removePrefix, int adClientId, int adOrgId);

}
