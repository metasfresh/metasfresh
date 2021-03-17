// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for AD_Session
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_AD_Session extends org.compiere.model.PO implements I_AD_Session, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 1015108895L;

    /** Standard Constructor */
    public X_AD_Session (final Properties ctx, final int AD_Session_ID, @Nullable final String trxName)
    {
      super (ctx, AD_Session_ID, trxName);
    }

    /** Load Constructor */
    public X_AD_Session (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public org.compiere.model.I_AD_Role getAD_Role()
	{
		return get_ValueAsPO(COLUMNNAME_AD_Role_ID, org.compiere.model.I_AD_Role.class);
	}

	@Override
	public void setAD_Role(final org.compiere.model.I_AD_Role AD_Role)
	{
		set_ValueFromPO(COLUMNNAME_AD_Role_ID, org.compiere.model.I_AD_Role.class, AD_Role);
	}

	@Override
	public void setAD_Role_ID (final int AD_Role_ID)
	{
		if (AD_Role_ID < 0) 
			set_Value (COLUMNNAME_AD_Role_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Role_ID, AD_Role_ID);
	}

	@Override
	public int getAD_Role_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Role_ID);
	}

	@Override
	public void setAD_Session_ID (final int AD_Session_ID)
	{
		if (AD_Session_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Session_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Session_ID, AD_Session_ID);
	}

	@Override
	public int getAD_Session_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Session_ID);
	}

	@Override
	public void setClient_Info (final @Nullable java.lang.String Client_Info)
	{
		set_Value (COLUMNNAME_Client_Info, Client_Info);
	}

	@Override
	public java.lang.String getClient_Info() 
	{
		return get_ValueAsString(COLUMNNAME_Client_Info);
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
	public void setHostKey (final @Nullable java.lang.String HostKey)
	{
		set_Value (COLUMNNAME_HostKey, HostKey);
	}

	@Override
	public java.lang.String getHostKey() 
	{
		return get_ValueAsString(COLUMNNAME_HostKey);
	}

	@Override
	public void setIsLoginIncorrect (final boolean IsLoginIncorrect)
	{
		set_Value (COLUMNNAME_IsLoginIncorrect, IsLoginIncorrect);
	}

	@Override
	public boolean isLoginIncorrect() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsLoginIncorrect);
	}

	@Override
	public void setLoginDate (final @Nullable java.sql.Timestamp LoginDate)
	{
		set_Value (COLUMNNAME_LoginDate, LoginDate);
	}

	@Override
	public java.sql.Timestamp getLoginDate() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_LoginDate);
	}

	@Override
	public void setLoginUsername (final @Nullable java.lang.String LoginUsername)
	{
		set_Value (COLUMNNAME_LoginUsername, LoginUsername);
	}

	@Override
	public java.lang.String getLoginUsername() 
	{
		return get_ValueAsString(COLUMNNAME_LoginUsername);
	}

	@Override
	public void setProcessed (final boolean Processed)
	{
		set_ValueNoCheck (COLUMNNAME_Processed, Processed);
	}

	@Override
	public boolean isProcessed() 
	{
		return get_ValueAsBoolean(COLUMNNAME_Processed);
	}

	@Override
	public void setRemote_Addr (final @Nullable java.lang.String Remote_Addr)
	{
		set_ValueNoCheck (COLUMNNAME_Remote_Addr, Remote_Addr);
	}

	@Override
	public java.lang.String getRemote_Addr() 
	{
		return get_ValueAsString(COLUMNNAME_Remote_Addr);
	}

	@Override
	public void setRemote_Host (final @Nullable java.lang.String Remote_Host)
	{
		set_ValueNoCheck (COLUMNNAME_Remote_Host, Remote_Host);
	}

	@Override
	public java.lang.String getRemote_Host() 
	{
		return get_ValueAsString(COLUMNNAME_Remote_Host);
	}

	@Override
	public void setWebSession (final @Nullable java.lang.String WebSession)
	{
		set_ValueNoCheck (COLUMNNAME_WebSession, WebSession);
	}

	@Override
	public java.lang.String getWebSession() 
	{
		return get_ValueAsString(COLUMNNAME_WebSession);
	}
}