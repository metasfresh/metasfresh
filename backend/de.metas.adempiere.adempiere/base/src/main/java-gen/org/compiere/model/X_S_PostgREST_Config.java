/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for S_PostgREST_Config
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("javadoc")
public class X_S_PostgREST_Config extends org.compiere.model.PO implements I_S_PostgREST_Config, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1149708152L;

    /** Standard Constructor */
    public X_S_PostgREST_Config (Properties ctx, int S_PostgREST_Config_ID, String trxName)
    {
      super (ctx, S_PostgREST_Config_ID, trxName);
    }

    /** Load Constructor */
    public X_S_PostgREST_Config (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }


	/** Load Meta Data */
	@Override
	protected org.compiere.model.POInfo initPO(Properties ctx)
	{
		return org.compiere.model.POInfo.getPOInfo(Table_Name);
	}

	@Override
	public void setBase_url (java.lang.String Base_url)
	{
		set_Value (COLUMNNAME_Base_url, Base_url);
	}

	@Override
	public java.lang.String getBase_url() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Base_url);
	}

	@Override
	public void setConnection_timeout (int Connection_timeout)
	{
		set_Value (COLUMNNAME_Connection_timeout, Integer.valueOf(Connection_timeout));
	}

	@Override
	public int getConnection_timeout() 
	{
		return get_ValueAsInt(COLUMNNAME_Connection_timeout);
	}

	@Override
	public void setPostgREST_Config_ID (int PostgREST_Config_ID)
	{
		if (PostgREST_Config_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_PostgREST_Config_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_PostgREST_Config_ID, Integer.valueOf(PostgREST_Config_ID));
	}

	@Override
	public int getPostgREST_Config_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_PostgREST_Config_ID);
	}

	@Override
	public void setRead_timeout (int Read_timeout)
	{
		set_Value (COLUMNNAME_Read_timeout, Integer.valueOf(Read_timeout));
	}

	@Override
	public int getRead_timeout() 
	{
		return get_ValueAsInt(COLUMNNAME_Read_timeout);
	}
}