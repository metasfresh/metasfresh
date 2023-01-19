// Generated Model - DO NOT CHANGE
package de.metas.externalsystem.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for ExternalSystem_Config_SAP
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_ExternalSystem_Config_SAP extends org.compiere.model.PO implements I_ExternalSystem_Config_SAP, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1815587035L;

    /** Standard Constructor */
    public X_ExternalSystem_Config_SAP (final Properties ctx, final int ExternalSystem_Config_SAP_ID, @Nullable final String trxName)
    {
      super (ctx, ExternalSystem_Config_SAP_ID, trxName);
    }

    /** Load Constructor */
    public X_ExternalSystem_Config_SAP (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setExternalSystem_Config_SAP_ID (final int ExternalSystem_Config_SAP_ID)
	{
		if (ExternalSystem_Config_SAP_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_ExternalSystem_Config_SAP_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_ExternalSystem_Config_SAP_ID, ExternalSystem_Config_SAP_ID);
	}

	@Override
	public int getExternalSystem_Config_SAP_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_ExternalSystem_Config_SAP_ID);
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
	public void setIsCheckDescriptionForMaterialType (final boolean IsCheckDescriptionForMaterialType)
	{
		set_Value (COLUMNNAME_IsCheckDescriptionForMaterialType, IsCheckDescriptionForMaterialType);
	}

	@Override
	public boolean isCheckDescriptionForMaterialType() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsCheckDescriptionForMaterialType);
	}
}