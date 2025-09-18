// Generated Model - DO NOT CHANGE
package de.metas.externalsystem.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for ExternalSystem_Config
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_ExternalSystem_Config extends org.compiere.model.PO implements I_ExternalSystem_Config, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 1603772571L;

    /** Standard Constructor */
    public X_ExternalSystem_Config (final Properties ctx, final int ExternalSystem_Config_ID, @Nullable final String trxName)
    {
      super (ctx, ExternalSystem_Config_ID, trxName);
    }

    /** Load Constructor */
    public X_ExternalSystem_Config (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setAuditFileFolder (final java.lang.String AuditFileFolder)
	{
		set_Value (COLUMNNAME_AuditFileFolder, AuditFileFolder);
	}

	@Override
	public java.lang.String getAuditFileFolder() 
	{
		return get_ValueAsString(COLUMNNAME_AuditFileFolder);
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
	public de.metas.externalsystem.model.I_ExternalSystem getExternalSystem()
	{
		return get_ValueAsPO(COLUMNNAME_ExternalSystem_ID, de.metas.externalsystem.model.I_ExternalSystem.class);
	}

	@Override
	public void setExternalSystem(final de.metas.externalsystem.model.I_ExternalSystem ExternalSystem)
	{
		set_ValueFromPO(COLUMNNAME_ExternalSystem_ID, de.metas.externalsystem.model.I_ExternalSystem.class, ExternalSystem);
	}

	@Override
	public void setExternalSystem_ID (final int ExternalSystem_ID)
	{
		if (ExternalSystem_ID < 1) 
			set_Value (COLUMNNAME_ExternalSystem_ID, null);
		else 
			set_Value (COLUMNNAME_ExternalSystem_ID, ExternalSystem_ID);
	}

	@Override
	public int getExternalSystem_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_ExternalSystem_ID);
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
	public void setWriteAudit (final boolean WriteAudit)
	{
		set_Value (COLUMNNAME_WriteAudit, WriteAudit);
	}

	@Override
	public boolean isWriteAudit() 
	{
		return get_ValueAsBoolean(COLUMNNAME_WriteAudit);
	}
}