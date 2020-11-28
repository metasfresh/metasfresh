/** Generated Model - DO NOT CHANGE */
package de.metas.printing.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for AD_Print_Clients
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("javadoc")
public class X_AD_Print_Clients extends org.compiere.model.PO implements I_AD_Print_Clients, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -978034459L;

    /** Standard Constructor */
    public X_AD_Print_Clients (Properties ctx, int AD_Print_Clients_ID, String trxName)
    {
      super (ctx, AD_Print_Clients_ID, trxName);
    }

    /** Load Constructor */
    public X_AD_Print_Clients (Properties ctx, ResultSet rs, String trxName)
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
	public void setAD_Print_Clients_ID (int AD_Print_Clients_ID)
	{
		if (AD_Print_Clients_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Print_Clients_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Print_Clients_ID, Integer.valueOf(AD_Print_Clients_ID));
	}

	@Override
	public int getAD_Print_Clients_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Print_Clients_ID);
	}

	@Override
	public org.compiere.model.I_AD_Session getAD_Session()
	{
		return get_ValueAsPO(COLUMNNAME_AD_Session_ID, org.compiere.model.I_AD_Session.class);
	}

	@Override
	public void setAD_Session(org.compiere.model.I_AD_Session AD_Session)
	{
		set_ValueFromPO(COLUMNNAME_AD_Session_ID, org.compiere.model.I_AD_Session.class, AD_Session);
	}

	@Override
	public void setAD_Session_ID (int AD_Session_ID)
	{
		if (AD_Session_ID < 1) 
			set_Value (COLUMNNAME_AD_Session_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Session_ID, Integer.valueOf(AD_Session_ID));
	}

	@Override
	public int getAD_Session_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Session_ID);
	}

	@Override
	public void setDateLastPoll (java.sql.Timestamp DateLastPoll)
	{
		set_Value (COLUMNNAME_DateLastPoll, DateLastPoll);
	}

	@Override
	public java.sql.Timestamp getDateLastPoll() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_DateLastPoll);
	}

	@Override
	public void setHostKey (java.lang.String HostKey)
	{
		set_Value (COLUMNNAME_HostKey, HostKey);
	}

	@Override
	public java.lang.String getHostKey() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_HostKey);
	}
}