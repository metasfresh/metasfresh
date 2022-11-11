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

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for ExternalSystem_Config_Metasfresh
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_ExternalSystem_Config_Metasfresh extends org.compiere.model.PO implements I_ExternalSystem_Config_Metasfresh, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 237265603L;

    /** Standard Constructor */
    public X_ExternalSystem_Config_Metasfresh (final Properties ctx, final int ExternalSystem_Config_Metasfresh_ID, @Nullable final String trxName)
    {
      super (ctx, ExternalSystem_Config_Metasfresh_ID, trxName);
    }

    /** Load Constructor */
    public X_ExternalSystem_Config_Metasfresh (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setCamelHttpResourceAuthKey (final @Nullable String CamelHttpResourceAuthKey)
	{
		set_Value (COLUMNNAME_CamelHttpResourceAuthKey, CamelHttpResourceAuthKey);
	}

	@Override
	public String getCamelHttpResourceAuthKey() 
	{
		return get_ValueAsString(COLUMNNAME_CamelHttpResourceAuthKey);
	}

	@Override
	public I_ExternalSystem_Config getExternalSystem_Config()
	{
		return get_ValueAsPO(COLUMNNAME_ExternalSystem_Config_ID, I_ExternalSystem_Config.class);
	}

	@Override
	public void setExternalSystem_Config(final I_ExternalSystem_Config ExternalSystem_Config)
	{
		set_ValueFromPO(COLUMNNAME_ExternalSystem_Config_ID, I_ExternalSystem_Config.class, ExternalSystem_Config);
	}

	@Override
	public void setExternalSystem_Config_ID (final int ExternalSystem_Config_ID)
	{
		if (ExternalSystem_Config_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_ExternalSystem_Config_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_ExternalSystem_Config_ID, ExternalSystem_Config_ID);
	}

	@Override
	public int getExternalSystem_Config_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_ExternalSystem_Config_ID);
	}

	@Override
	public void setExternalSystem_Config_Metasfresh_ID (final int ExternalSystem_Config_Metasfresh_ID)
	{
		if (ExternalSystem_Config_Metasfresh_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_ExternalSystem_Config_Metasfresh_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_ExternalSystem_Config_Metasfresh_ID, ExternalSystem_Config_Metasfresh_ID);
	}

	@Override
	public int getExternalSystem_Config_Metasfresh_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_ExternalSystem_Config_Metasfresh_ID);
	}

	@Override
	public void setExternalSystemValue (final String ExternalSystemValue)
	{
		set_Value (COLUMNNAME_ExternalSystemValue, ExternalSystemValue);
	}

	@Override
	public String getExternalSystemValue() 
	{
		return get_ValueAsString(COLUMNNAME_ExternalSystemValue);
	}

	@Override
	public void setHttpResponseAuthKey (final @Nullable String HttpResponseAuthKey)
	{
		set_Value (COLUMNNAME_HttpResponseAuthKey, HttpResponseAuthKey);
	}

	@Override
	public String getHttpResponseAuthKey() 
	{
		return get_ValueAsString(COLUMNNAME_HttpResponseAuthKey);
	}

	@Override
	public void setHttpResponseUrl (final @Nullable String HttpResponseUrl)
	{
		set_Value (COLUMNNAME_HttpResponseUrl, HttpResponseUrl);
	}

	@Override
	public String getHttpResponseUrl() 
	{
		return get_ValueAsString(COLUMNNAME_HttpResponseUrl);
	}
}