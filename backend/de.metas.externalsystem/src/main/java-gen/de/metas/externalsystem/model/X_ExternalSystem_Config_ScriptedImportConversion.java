/*
 * #%L
 * de.metas.externalsystem
 * %%
 * Copyright (C) 2025 metas GmbH
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

/** Generated Model for ExternalSystem_Config_ScriptedImportConversion
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_ExternalSystem_Config_ScriptedImportConversion extends org.compiere.model.PO implements I_ExternalSystem_Config_ScriptedImportConversion, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 1888501400L;

    /** Standard Constructor */
    public X_ExternalSystem_Config_ScriptedImportConversion (final Properties ctx, final int ExternalSystem_Config_ScriptedImportConversion_ID, @Nullable final String trxName)
    {
      super (ctx, ExternalSystem_Config_ScriptedImportConversion_ID, trxName);
    }

    /** Load Constructor */
    public X_ExternalSystem_Config_ScriptedImportConversion (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setAD_User_Import_ID (final int AD_User_Import_ID)
	{
		if (AD_User_Import_ID < 1) 
			set_Value (COLUMNNAME_AD_User_Import_ID, null);
		else 
			set_Value (COLUMNNAME_AD_User_Import_ID, AD_User_Import_ID);
	}

	@Override
	public int getAD_User_Import_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_User_Import_ID);
	}

	@Override
	public void setDescription (final @Nullable String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	@Override
	public String getDescription() 
	{
		return get_ValueAsString(COLUMNNAME_Description);
	}

	@Override
	public void setEndpointName (final String EndpointName)
	{
		set_Value (COLUMNNAME_EndpointName, EndpointName);
	}

	@Override
	public String getEndpointName() 
	{
		return get_ValueAsString(COLUMNNAME_EndpointName);
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
	public void setExternalSystem_Config_ScriptedImportConversion_ID (final int ExternalSystem_Config_ScriptedImportConversion_ID)
	{
		if (ExternalSystem_Config_ScriptedImportConversion_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_ExternalSystem_Config_ScriptedImportConversion_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_ExternalSystem_Config_ScriptedImportConversion_ID, ExternalSystem_Config_ScriptedImportConversion_ID);
	}

	@Override
	public int getExternalSystem_Config_ScriptedImportConversion_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_ExternalSystem_Config_ScriptedImportConversion_ID);
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
	public void setScriptIdentifier (final String ScriptIdentifier)
	{
		set_Value (COLUMNNAME_ScriptIdentifier, ScriptIdentifier);
	}

	@Override
	public String getScriptIdentifier() 
	{
		return get_ValueAsString(COLUMNNAME_ScriptIdentifier);
	}
}