/** Generated Model - DO NOT CHANGE */
package de.metas.inbound.mail.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_InboundMailConfig
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_InboundMailConfig extends org.compiere.model.PO implements I_C_InboundMailConfig, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -357538494L;

    /** Standard Constructor */
    public X_C_InboundMailConfig (Properties ctx, int C_InboundMailConfig_ID, String trxName)
    {
      super (ctx, C_InboundMailConfig_ID, trxName);
      /** if (C_InboundMailConfig_ID == 0)
        {
			setC_InboundMailConfig_ID (0);
			setFolder (null); // INBOX
			setHost (null);
			setIsDebugProtocol (false); // N
			setPort (0);
			setProtocol (null);
        } */
    }

    /** Load Constructor */
    public X_C_InboundMailConfig (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }


    /** Load Meta Data */
    @Override
    protected org.compiere.model.POInfo initPO (Properties ctx)
    {
      org.compiere.model.POInfo poi = org.compiere.model.POInfo.getPOInfo (ctx, Table_Name, get_TrxName());
      return poi;
    }

	/** Set Inbound EMail Config.
		@param C_InboundMailConfig_ID Inbound EMail Config	  */
	@Override
	public void setC_InboundMailConfig_ID (int C_InboundMailConfig_ID)
	{
		if (C_InboundMailConfig_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_InboundMailConfig_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_InboundMailConfig_ID, Integer.valueOf(C_InboundMailConfig_ID));
	}

	/** Get Inbound EMail Config.
		@return Inbound EMail Config	  */
	@Override
	public int getC_InboundMailConfig_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_InboundMailConfig_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Beschreibung.
		@param Description Beschreibung	  */
	@Override
	public void setDescription (java.lang.String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	/** Get Beschreibung.
		@return Beschreibung	  */
	@Override
	public java.lang.String getDescription () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Description);
	}

	/** Set Folder.
		@param Folder 
		A folder on a local or remote system to store data into
	  */
	@Override
	public void setFolder (java.lang.String Folder)
	{
		set_Value (COLUMNNAME_Folder, Folder);
	}

	/** Get Folder.
		@return A folder on a local or remote system to store data into
	  */
	@Override
	public java.lang.String getFolder () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Folder);
	}

	/** Set Host.
		@param Host Host	  */
	@Override
	public void setHost (java.lang.String Host)
	{
		set_Value (COLUMNNAME_Host, Host);
	}

	/** Get Host.
		@return Host	  */
	@Override
	public java.lang.String getHost () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Host);
	}

	/** Set Debug protocol.
		@param IsDebugProtocol Debug protocol	  */
	@Override
	public void setIsDebugProtocol (boolean IsDebugProtocol)
	{
		set_Value (COLUMNNAME_IsDebugProtocol, Boolean.valueOf(IsDebugProtocol));
	}

	/** Get Debug protocol.
		@return Debug protocol	  */
	@Override
	public boolean isDebugProtocol () 
	{
		Object oo = get_Value(COLUMNNAME_IsDebugProtocol);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Kennwort.
		@param Password Kennwort	  */
	@Override
	public void setPassword (java.lang.String Password)
	{
		set_Value (COLUMNNAME_Password, Password);
	}

	/** Get Kennwort.
		@return Kennwort	  */
	@Override
	public java.lang.String getPassword () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Password);
	}

	/** Set Port.
		@param Port Port	  */
	@Override
	public void setPort (int Port)
	{
		set_Value (COLUMNNAME_Port, Integer.valueOf(Port));
	}

	/** Get Port.
		@return Port	  */
	@Override
	public int getPort () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Port);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** 
	 * Protocol AD_Reference_ID=540906
	 * Reference name: C_InboundMailConfig_Protocol
	 */
	public static final int PROTOCOL_AD_Reference_ID=540906;
	/** IMAP = imap */
	public static final String PROTOCOL_IMAP = "imap";
	/** IMAPS = imaps */
	public static final String PROTOCOL_IMAPS = "imaps";
	/** Set Protocol.
		@param Protocol 
		Protocol
	  */
	@Override
	public void setProtocol (java.lang.String Protocol)
	{

		set_Value (COLUMNNAME_Protocol, Protocol);
	}

	/** Get Protocol.
		@return Protocol
	  */
	@Override
	public java.lang.String getProtocol () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Protocol);
	}

	@Override
	public org.compiere.model.I_R_RequestType getR_RequestType() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_R_RequestType_ID, org.compiere.model.I_R_RequestType.class);
	}

	@Override
	public void setR_RequestType(org.compiere.model.I_R_RequestType R_RequestType)
	{
		set_ValueFromPO(COLUMNNAME_R_RequestType_ID, org.compiere.model.I_R_RequestType.class, R_RequestType);
	}

	/** Set Request Type.
		@param R_RequestType_ID 
		Type of request (e.g. Inquiry, Complaint, ..)
	  */
	@Override
	public void setR_RequestType_ID (int R_RequestType_ID)
	{
		if (R_RequestType_ID < 1) 
			set_Value (COLUMNNAME_R_RequestType_ID, null);
		else 
			set_Value (COLUMNNAME_R_RequestType_ID, Integer.valueOf(R_RequestType_ID));
	}

	/** Get Request Type.
		@return Type of request (e.g. Inquiry, Complaint, ..)
	  */
	@Override
	public int getR_RequestType_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_R_RequestType_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Nutzer-ID/Login.
		@param UserName Nutzer-ID/Login	  */
	@Override
	public void setUserName (java.lang.String UserName)
	{
		set_Value (COLUMNNAME_UserName, UserName);
	}

	/** Get Nutzer-ID/Login.
		@return Nutzer-ID/Login	  */
	@Override
	public java.lang.String getUserName () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_UserName);
	}
}