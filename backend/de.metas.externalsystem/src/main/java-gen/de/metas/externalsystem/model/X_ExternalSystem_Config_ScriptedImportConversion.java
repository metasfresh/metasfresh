// Generated Model - DO NOT CHANGE
package de.metas.externalsystem.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for ExternalSystem_Config_ScriptedImportConversion
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_ExternalSystem_Config_ScriptedImportConversion extends org.compiere.model.PO implements I_ExternalSystem_Config_ScriptedImportConversion, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1432582525L;

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
	public void setDescription (final @Nullable java.lang.String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	@Override
	public java.lang.String getDescription() 
	{
		return get_ValueAsString(COLUMNNAME_Description);
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
	public void setExternalSystem_Endpoint_ID (final int ExternalSystem_Endpoint_ID)
	{
		if (ExternalSystem_Endpoint_ID < 1) 
			set_Value (COLUMNNAME_ExternalSystem_Endpoint_ID, null);
		else 
			set_Value (COLUMNNAME_ExternalSystem_Endpoint_ID, ExternalSystem_Endpoint_ID);
	}

	@Override
	public int getExternalSystem_Endpoint_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_ExternalSystem_Endpoint_ID);
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
	public void setScriptIdentifier (final java.lang.String ScriptIdentifier)
	{
		set_Value (COLUMNNAME_ScriptIdentifier, ScriptIdentifier);
	}

	@Override
	public java.lang.String getScriptIdentifier() 
	{
		return get_ValueAsString(COLUMNNAME_ScriptIdentifier);
	}
}