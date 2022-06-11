// Generated Model - DO NOT CHANGE
package de.metas.externalsystem.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for ExternalSystem_Config_LeichMehl
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_ExternalSystem_Config_LeichMehl extends org.compiere.model.PO implements I_ExternalSystem_Config_LeichMehl, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1328676085L;

    /** Standard Constructor */
    public X_ExternalSystem_Config_LeichMehl (final Properties ctx, final int ExternalSystem_Config_LeichMehl_ID, @Nullable final String trxName)
    {
      super (ctx, ExternalSystem_Config_LeichMehl_ID, trxName);
    }

    /** Load Constructor */
    public X_ExternalSystem_Config_LeichMehl (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setExternalSystem_Config_LeichMehl_ID (final int ExternalSystem_Config_LeichMehl_ID)
	{
		if (ExternalSystem_Config_LeichMehl_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_ExternalSystem_Config_LeichMehl_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_ExternalSystem_Config_LeichMehl_ID, ExternalSystem_Config_LeichMehl_ID);
	}

	@Override
	public int getExternalSystem_Config_LeichMehl_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_ExternalSystem_Config_LeichMehl_ID);
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
	public void setFTP_Directory (final java.lang.String FTP_Directory)
	{
		set_Value (COLUMNNAME_FTP_Directory, FTP_Directory);
	}

	@Override
	public java.lang.String getFTP_Directory() 
	{
		return get_ValueAsString(COLUMNNAME_FTP_Directory);
	}

	@Override
	public void setFTP_Hostname (final java.lang.String FTP_Hostname)
	{
		set_Value (COLUMNNAME_FTP_Hostname, FTP_Hostname);
	}

	@Override
	public java.lang.String getFTP_Hostname() 
	{
		return get_ValueAsString(COLUMNNAME_FTP_Hostname);
	}

	@Override
	public void setFTP_Password (final java.lang.String FTP_Password)
	{
		set_Value (COLUMNNAME_FTP_Password, FTP_Password);
	}

	@Override
	public java.lang.String getFTP_Password() 
	{
		return get_ValueAsString(COLUMNNAME_FTP_Password);
	}

	@Override
	public void setFTP_Port (final int FTP_Port)
	{
		set_Value (COLUMNNAME_FTP_Port, FTP_Port);
	}

	@Override
	public int getFTP_Port() 
	{
		return get_ValueAsInt(COLUMNNAME_FTP_Port);
	}

	@Override
	public void setFTP_Username (final java.lang.String FTP_Username)
	{
		set_Value (COLUMNNAME_FTP_Username, FTP_Username);
	}

	@Override
	public java.lang.String getFTP_Username() 
	{
		return get_ValueAsString(COLUMNNAME_FTP_Username);
	}
}