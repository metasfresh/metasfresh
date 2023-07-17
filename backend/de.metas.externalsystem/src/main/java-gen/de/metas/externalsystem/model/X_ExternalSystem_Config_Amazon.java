/*
 * #%L
 * de.metas.externalsystem
 * %%
 * Copyright (C) 2022 metas GmbH
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

// Generated Model - DO NOT CHANGE
package de.metas.externalsystem.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for ExternalSystem_Config_Amazon
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_ExternalSystem_Config_Amazon extends org.compiere.model.PO implements I_ExternalSystem_Config_Amazon, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1618278417L;

    /** Standard Constructor */
    public X_ExternalSystem_Config_Amazon (final Properties ctx, final int ExternalSystem_Config_Amazon_ID, @Nullable final String trxName)
    {
      super (ctx, ExternalSystem_Config_Amazon_ID, trxName);
    }

    /** Load Constructor */
    public X_ExternalSystem_Config_Amazon (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
    {
      super (ctx, rs, trxName);
    }


	/** Load Meta Data */
	@Override
	protected org.compiere.model.POInfo initPO(final Properties ctx)
	{
		return org.compiere.model.POInfo.getPOInfo(Table_Name);
	}

	@Override
	public void setAccessKeyId (final java.lang.String AccessKeyId)
	{
		set_Value (COLUMNNAME_AccessKeyId, AccessKeyId);
	}

	@Override
	public java.lang.String getAccessKeyId() 
	{
		return get_ValueAsString(COLUMNNAME_AccessKeyId);
	}

	@Override
	public void setBasePath (final java.lang.String BasePath)
	{
		set_Value (COLUMNNAME_BasePath, BasePath);
	}

	@Override
	public java.lang.String getBasePath() 
	{
		return get_ValueAsString(COLUMNNAME_BasePath);
	}

	@Override
	public void setClientID (final java.lang.String ClientID)
	{
		set_Value (COLUMNNAME_ClientID, ClientID);
	}

	@Override
	public java.lang.String getClientID() 
	{
		return get_ValueAsString(COLUMNNAME_ClientID);
	}

	@Override
	public void setClientSecret (final java.lang.String ClientSecret)
	{
		set_Value (COLUMNNAME_ClientSecret, ClientSecret);
	}

	@Override
	public java.lang.String getClientSecret() 
	{
		return get_ValueAsString(COLUMNNAME_ClientSecret);
	}

	@Override
	public void setExternalSystem_Config_Amazon_ID (final int ExternalSystem_Config_Amazon_ID)
	{
		if (ExternalSystem_Config_Amazon_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_ExternalSystem_Config_Amazon_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_ExternalSystem_Config_Amazon_ID, ExternalSystem_Config_Amazon_ID);
	}

	@Override
	public int getExternalSystem_Config_Amazon_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_ExternalSystem_Config_Amazon_ID);
	}

	@Override
	public void setExternalSystem_Config_ID (final int ExternalSystem_Config_ID)
	{
		if (ExternalSystem_Config_ID < 1) 
			set_Value (COLUMNNAME_ExternalSystem_Config_ID, null);
		else 
			set_Value (COLUMNNAME_ExternalSystem_Config_ID, ExternalSystem_Config_ID);
	}

	@Override
	public int getExternalSystem_Config_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_ExternalSystem_Config_ID);
	}

	@Override
	public void setExternalSystemValue (final java.lang.String ExternalSystemValue)
	{
		set_Value (COLUMNNAME_ExternalSystemValue, ExternalSystemValue);
	}

	@Override
	public java.lang.String getExternalSystemValue() 
	{
		return get_ValueAsString(COLUMNNAME_ExternalSystemValue);
	}

	@Override
	public void setIsDebugProtocol (final boolean IsDebugProtocol)
	{
		set_Value (COLUMNNAME_IsDebugProtocol, IsDebugProtocol);
	}

	@Override
	public boolean isDebugProtocol() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsDebugProtocol);
	}

	@Override
	public void setLWAEndpoint (final java.lang.String LWAEndpoint)
	{
		set_Value (COLUMNNAME_LWAEndpoint, LWAEndpoint);
	}

	@Override
	public java.lang.String getLWAEndpoint() 
	{
		return get_ValueAsString(COLUMNNAME_LWAEndpoint);
	}

	@Override
	public void setName (final java.lang.String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	@Override
	public java.lang.String getName() 
	{
		return get_ValueAsString(COLUMNNAME_Name);
	}

	@Override
	public void setRefreshToken (final @Nullable java.lang.String RefreshToken)
	{
		set_Value (COLUMNNAME_RefreshToken, RefreshToken);
	}

	@Override
	public java.lang.String getRefreshToken() 
	{
		return get_ValueAsString(COLUMNNAME_RefreshToken);
	}

	@Override
	public void setRegionName (final @Nullable java.lang.String RegionName)
	{
		set_Value (COLUMNNAME_RegionName, RegionName);
	}

	@Override
	public java.lang.String getRegionName() 
	{
		return get_ValueAsString(COLUMNNAME_RegionName);
	}

	@Override
	public void setRoleArn (final java.lang.String RoleArn)
	{
		set_Value (COLUMNNAME_RoleArn, RoleArn);
	}

	@Override
	public java.lang.String getRoleArn() 
	{
		return get_ValueAsString(COLUMNNAME_RoleArn);
	}

	@Override
	public void setSecretKey (final @Nullable java.lang.String SecretKey)
	{
		set_Value (COLUMNNAME_SecretKey, SecretKey);
	}

	@Override
	public java.lang.String getSecretKey() 
	{
		return get_ValueAsString(COLUMNNAME_SecretKey);
	}
}