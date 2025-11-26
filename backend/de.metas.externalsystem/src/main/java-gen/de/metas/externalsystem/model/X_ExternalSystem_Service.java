// Generated Model - DO NOT CHANGE
package de.metas.externalsystem.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for ExternalSystem_Service
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_ExternalSystem_Service extends org.compiere.model.PO implements I_ExternalSystem_Service, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 473874422L;

    /** Standard Constructor */
    public X_ExternalSystem_Service (final Properties ctx, final int ExternalSystem_Service_ID, @Nullable final String trxName)
    {
      super (ctx, ExternalSystem_Service_ID, trxName);
    }

    /** Load Constructor */
    public X_ExternalSystem_Service (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setDisableCommand (final @Nullable java.lang.String DisableCommand)
	{
		set_Value (COLUMNNAME_DisableCommand, DisableCommand);
	}

	@Override
	public java.lang.String getDisableCommand() 
	{
		return get_ValueAsString(COLUMNNAME_DisableCommand);
	}

	@Override
	public void setEnableCommand (final @Nullable java.lang.String EnableCommand)
	{
		set_Value (COLUMNNAME_EnableCommand, EnableCommand);
	}

	@Override
	public java.lang.String getEnableCommand() 
	{
		return get_ValueAsString(COLUMNNAME_EnableCommand);
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
	public void setExternalSystem_Service_ID (final int ExternalSystem_Service_ID)
	{
		if (ExternalSystem_Service_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_ExternalSystem_Service_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_ExternalSystem_Service_ID, ExternalSystem_Service_ID);
	}

	@Override
	public int getExternalSystem_Service_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_ExternalSystem_Service_ID);
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
	public void setValue (final java.lang.String Value)
	{
		set_Value (COLUMNNAME_Value, Value);
	}

	@Override
	public java.lang.String getValue() 
	{
		return get_ValueAsString(COLUMNNAME_Value);
	}
}