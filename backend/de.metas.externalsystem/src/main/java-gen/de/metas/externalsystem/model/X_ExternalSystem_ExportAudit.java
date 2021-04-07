// Generated Model - DO NOT CHANGE
package de.metas.externalsystem.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for ExternalSystem_ExportAudit
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_ExternalSystem_ExportAudit extends org.compiere.model.PO implements I_ExternalSystem_ExportAudit, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -2053147346L;

    /** Standard Constructor */
    public X_ExternalSystem_ExportAudit (final Properties ctx, final int ExternalSystem_ExportAudit_ID, @Nullable final String trxName)
    {
      super (ctx, ExternalSystem_ExportAudit_ID, trxName);
    }

    /** Load Constructor */
    public X_ExternalSystem_ExportAudit (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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

	@Override
	public void setAD_Table_ID (final int AD_Table_ID)
	{
		if (AD_Table_ID < 1) 
			set_Value (COLUMNNAME_AD_Table_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Table_ID, AD_Table_ID);
	}

	@Override
	public int getAD_Table_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Table_ID);
	}

	@Override
	public void setExportParameters (final @Nullable String ExportParameters)
	{
		set_Value (COLUMNNAME_ExportParameters, ExportParameters);
	}

	@Override
	public String getExportParameters()
	{
		return get_ValueAsString(COLUMNNAME_ExportParameters);
	}

	@Override
	public org.compiere.model.I_AD_Role getExportRole()
	{
		return get_ValueAsPO(COLUMNNAME_ExportRole_ID, org.compiere.model.I_AD_Role.class);
	}

	@Override
	public void setExportRole(final org.compiere.model.I_AD_Role ExportRole)
	{
		set_ValueFromPO(COLUMNNAME_ExportRole_ID, org.compiere.model.I_AD_Role.class, ExportRole);
	}

	@Override
	public void setExportRole_ID (final int ExportRole_ID)
	{
		if (ExportRole_ID < 1) 
			set_Value (COLUMNNAME_ExportRole_ID, null);
		else 
			set_Value (COLUMNNAME_ExportRole_ID, ExportRole_ID);
	}

	@Override
	public int getExportRole_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_ExportRole_ID);
	}

	@Override
	public void setExportTime (final java.sql.Timestamp ExportTime)
	{
		set_Value (COLUMNNAME_ExportTime, ExportTime);
	}

	@Override
	public java.sql.Timestamp getExportTime() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_ExportTime);
	}

	@Override
	public void setExportUser_ID (final int ExportUser_ID)
	{
		if (ExportUser_ID < 1) 
			set_Value (COLUMNNAME_ExportUser_ID, null);
		else 
			set_Value (COLUMNNAME_ExportUser_ID, ExportUser_ID);
	}

	@Override
	public int getExportUser_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_ExportUser_ID);
	}

	@Override
	public void setExternalSystem_ExportAudit_ID (final int ExternalSystem_ExportAudit_ID)
	{
		if (ExternalSystem_ExportAudit_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_ExternalSystem_ExportAudit_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_ExternalSystem_ExportAudit_ID, ExternalSystem_ExportAudit_ID);
	}

	@Override
	public int getExternalSystem_ExportAudit_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_ExternalSystem_ExportAudit_ID);
	}

	/** 
	 * ExternalSystemType AD_Reference_ID=541255
	 * Reference name: Type
	 */
	public static final int EXTERNALSYSTEMTYPE_AD_Reference_ID=541255;
	/** Alberta = A */
	public static final String EXTERNALSYSTEMTYPE_Alberta = "A";
	/** Shopware6 = S6 */
	public static final String EXTERNALSYSTEMTYPE_Shopware6 = "S6";
	@Override
	public void setExternalSystemType (final @Nullable String ExternalSystemType)
	{
		set_Value (COLUMNNAME_ExternalSystemType, ExternalSystemType);
	}

	@Override
	public String getExternalSystemType()
	{
		return get_ValueAsString(COLUMNNAME_ExternalSystemType);
	}

	@Override
	public void setRecord_ID (final int Record_ID)
	{
		if (Record_ID < 0) 
			set_Value (COLUMNNAME_Record_ID, null);
		else 
			set_Value (COLUMNNAME_Record_ID, Record_ID);
	}

	@Override
	public int getRecord_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Record_ID);
	}
}