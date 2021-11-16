/*
 * #%L
 * de.metas.externalsystem
 * %%
 * Copyright (C) 2021 metas GmbH
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

/** Generated Model for ExternalSystem_Config_GRSSignum
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_ExternalSystem_Config_GRSSignum extends org.compiere.model.PO implements I_ExternalSystem_Config_GRSSignum, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -591859223L;

    /** Standard Constructor */
    public X_ExternalSystem_Config_GRSSignum (final Properties ctx, final int ExternalSystem_Config_GRSSignum_ID, @Nullable final String trxName)
    {
      super (ctx, ExternalSystem_Config_GRSSignum_ID, trxName);
    }

    /** Load Constructor */
    public X_ExternalSystem_Config_GRSSignum (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setBaseURL (final java.lang.String BaseURL)
	{
		set_Value (COLUMNNAME_BaseURL, BaseURL);
	}

	@Override
	public java.lang.String getBaseURL() 
	{
		return get_ValueAsString(COLUMNNAME_BaseURL);
	}

	@Override
	public void setCamelHttpResourceAuthKey (final @Nullable java.lang.String CamelHttpResourceAuthKey)
	{
		set_Value (COLUMNNAME_CamelHttpResourceAuthKey, CamelHttpResourceAuthKey);
	}

	@Override
	public java.lang.String getCamelHttpResourceAuthKey() 
	{
		return get_ValueAsString(COLUMNNAME_CamelHttpResourceAuthKey);
	}

	@Override
	public void setExternalSystem_Config_GRSSignum_ID (final int ExternalSystem_Config_GRSSignum_ID)
	{
		if (ExternalSystem_Config_GRSSignum_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_ExternalSystem_Config_GRSSignum_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_ExternalSystem_Config_GRSSignum_ID, ExternalSystem_Config_GRSSignum_ID);
	}

	@Override
	public int getExternalSystem_Config_GRSSignum_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_ExternalSystem_Config_GRSSignum_ID);
	}

	@Override
	public de.metas.externalsystem.model.I_ExternalSystem_Config getExternalSystem_Config()
	{
		return get_ValueAsPO(COLUMNNAME_ExternalSystem_Config_ID, de.metas.externalsystem.model.I_ExternalSystem_Config.class);
	}

	@Override
	public void setExternalSystem_Config(final de.metas.externalsystem.model.I_ExternalSystem_Config ExternalSystem_Config)
	{
		set_ValueFromPO(COLUMNNAME_ExternalSystem_Config_ID, de.metas.externalsystem.model.I_ExternalSystem_Config.class, ExternalSystem_Config);
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
	public void setExternalSystemValue (final java.lang.String ExternalSystemValue)
	{
		set_Value (COLUMNNAME_ExternalSystemValue, ExternalSystemValue);
	}

	@Override
	public java.lang.String getExternalSystemValue() 
	{
		return get_ValueAsString(COLUMNNAME_ExternalSystemValue);
	}
}