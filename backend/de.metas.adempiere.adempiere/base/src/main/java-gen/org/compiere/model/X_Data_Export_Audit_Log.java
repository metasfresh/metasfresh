// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for Data_Export_Audit_Log
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_Data_Export_Audit_Log extends org.compiere.model.PO implements I_Data_Export_Audit_Log, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 809458022L;

    /** Standard Constructor */
    public X_Data_Export_Audit_Log (final Properties ctx, final int Data_Export_Audit_Log_ID, @Nullable final String trxName)
    {
      super (ctx, Data_Export_Audit_Log_ID, trxName);
    }

    /** Load Constructor */
    public X_Data_Export_Audit_Log (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public org.compiere.model.I_AD_PInstance getAD_PInstance()
	{
		return get_ValueAsPO(COLUMNNAME_AD_PInstance_ID, org.compiere.model.I_AD_PInstance.class);
	}

	@Override
	public void setAD_PInstance(final org.compiere.model.I_AD_PInstance AD_PInstance)
	{
		set_ValueFromPO(COLUMNNAME_AD_PInstance_ID, org.compiere.model.I_AD_PInstance.class, AD_PInstance);
	}

	@Override
	public void setAD_PInstance_ID (final int AD_PInstance_ID)
	{
		if (AD_PInstance_ID < 1) 
			set_Value (COLUMNNAME_AD_PInstance_ID, null);
		else 
			set_Value (COLUMNNAME_AD_PInstance_ID, AD_PInstance_ID);
	}

	@Override
	public int getAD_PInstance_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_PInstance_ID);
	}

	/** 
	 * Data_Export_Action AD_Reference_ID=541389
	 * Reference name: Data_Export_Actions
	 */
	public static final int DATA_EXPORT_ACTION_AD_Reference_ID=541389;
	/** Exported-Standalone = Exported-Standalone */
	public static final String DATA_EXPORT_ACTION_Exported_Standalone = "Exported-Standalone";
	/** Exported-AlongWithParent = Exported-AlongWithParent */
	public static final String DATA_EXPORT_ACTION_Exported_AlongWithParent = "Exported-AlongWithParent";
	/** AssignedToParent = AssignedToParent */
	public static final String DATA_EXPORT_ACTION_AssignedToParent = "AssignedToParent";
	/** Deleted = Deleted */
	public static final String DATA_EXPORT_ACTION_Deleted = "Deleted";
	@Override
	public void setData_Export_Action (final java.lang.String Data_Export_Action)
	{
		set_Value (COLUMNNAME_Data_Export_Action, Data_Export_Action);
	}

	@Override
	public java.lang.String getData_Export_Action() 
	{
		return get_ValueAsString(COLUMNNAME_Data_Export_Action);
	}

	@Override
	public org.compiere.model.I_Data_Export_Audit getData_Export_Audit()
	{
		return get_ValueAsPO(COLUMNNAME_Data_Export_Audit_ID, org.compiere.model.I_Data_Export_Audit.class);
	}

	@Override
	public void setData_Export_Audit(final org.compiere.model.I_Data_Export_Audit Data_Export_Audit)
	{
		set_ValueFromPO(COLUMNNAME_Data_Export_Audit_ID, org.compiere.model.I_Data_Export_Audit.class, Data_Export_Audit);
	}

	@Override
	public void setData_Export_Audit_ID (final int Data_Export_Audit_ID)
	{
		if (Data_Export_Audit_ID < 1) 
			set_Value (COLUMNNAME_Data_Export_Audit_ID, null);
		else 
			set_Value (COLUMNNAME_Data_Export_Audit_ID, Data_Export_Audit_ID);
	}

	@Override
	public int getData_Export_Audit_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Data_Export_Audit_ID);
	}

	@Override
	public void setData_Export_Audit_Log_ID (final int Data_Export_Audit_Log_ID)
	{
		if (Data_Export_Audit_Log_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_Data_Export_Audit_Log_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Data_Export_Audit_Log_ID, Data_Export_Audit_Log_ID);
	}

	@Override
	public int getData_Export_Audit_Log_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Data_Export_Audit_Log_ID);
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
}