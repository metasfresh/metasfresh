// Generated Model - DO NOT CHANGE
package org.compiere.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for S_PostgREST_Config
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_S_PostgREST_Config extends PO implements I_S_PostgREST_Config, I_Persistent 
{

	private static final long serialVersionUID = -27386281L;

    /** Standard Constructor */
    public X_S_PostgREST_Config (final Properties ctx, final int S_PostgREST_Config_ID, @Nullable final String trxName)
    {
      super (ctx, S_PostgREST_Config_ID, trxName);
    }

    /** Load Constructor */
    public X_S_PostgREST_Config (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
    {
      super (ctx, rs, trxName);
    }


	/** Load Meta Data */
	@Override
	protected POInfo initPO(final Properties ctx)
	{
		return POInfo.getPOInfo(Table_Name);
	}

	@Override
	public void setBase_url (final String Base_url)
	{
		set_Value (COLUMNNAME_Base_url, Base_url);
	}

	@Override
	public String getBase_url() 
	{
		return get_ValueAsString(COLUMNNAME_Base_url);
	}

	@Override
	public void setConnection_timeout (final int Connection_timeout)
	{
		set_Value (COLUMNNAME_Connection_timeout, Connection_timeout);
	}

	@Override
	public int getConnection_timeout() 
	{
		return get_ValueAsInt(COLUMNNAME_Connection_timeout);
	}

	@Override
	public void setPostgREST_ResultDirectory (final String PostgREST_ResultDirectory)
	{
		set_Value (COLUMNNAME_PostgREST_ResultDirectory, PostgREST_ResultDirectory);
	}

	@Override
	public String getPostgREST_ResultDirectory() 
	{
		return get_ValueAsString(COLUMNNAME_PostgREST_ResultDirectory);
	}

	@Override
	public void setRead_timeout (final int Read_timeout)
	{
		set_Value (COLUMNNAME_Read_timeout, Read_timeout);
	}

	@Override
	public int getRead_timeout() 
	{
		return get_ValueAsInt(COLUMNNAME_Read_timeout);
	}

	@Override
	public void setS_PostgREST_Config_ID (final int S_PostgREST_Config_ID)
	{
		if (S_PostgREST_Config_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_S_PostgREST_Config_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_S_PostgREST_Config_ID, S_PostgREST_Config_ID);
	}

	@Override
	public int getS_PostgREST_Config_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_S_PostgREST_Config_ID);
	}
}