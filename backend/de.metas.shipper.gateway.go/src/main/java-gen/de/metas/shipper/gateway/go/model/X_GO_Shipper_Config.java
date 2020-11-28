/** Generated Model - DO NOT CHANGE */
package de.metas.shipper.gateway.go.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for GO_Shipper_Config
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_GO_Shipper_Config extends org.compiere.model.PO implements I_GO_Shipper_Config, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 2090041594L;

    /** Standard Constructor */
    public X_GO_Shipper_Config (Properties ctx, int GO_Shipper_Config_ID, String trxName)
    {
      super (ctx, GO_Shipper_Config_ID, trxName);
      /** if (GO_Shipper_Config_ID == 0)
        {
			setGO_AuthPassword (null);
			setGO_AuthUsername (null);
			setGO_RequestSenderId (null);
			setGO_RequestUsername (null);
			setGO_Shipper_Config_ID (0);
			setGO_URL (null);
			setM_Shipper_ID (0);
        } */
    }

    /** Load Constructor */
    public X_GO_Shipper_Config (Properties ctx, ResultSet rs, String trxName)
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

	/** Set Auth Password.
		@param GO_AuthPassword Auth Password	  */
	@Override
	public void setGO_AuthPassword (java.lang.String GO_AuthPassword)
	{
		set_Value (COLUMNNAME_GO_AuthPassword, GO_AuthPassword);
	}

	/** Get Auth Password.
		@return Auth Password	  */
	@Override
	public java.lang.String getGO_AuthPassword () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_GO_AuthPassword);
	}

	/** Set Auth Username.
		@param GO_AuthUsername Auth Username	  */
	@Override
	public void setGO_AuthUsername (java.lang.String GO_AuthUsername)
	{
		set_Value (COLUMNNAME_GO_AuthUsername, GO_AuthUsername);
	}

	/** Get Auth Username.
		@return Auth Username	  */
	@Override
	public java.lang.String getGO_AuthUsername () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_GO_AuthUsername);
	}

	/** Set Request Sender ID.
		@param GO_RequestSenderId Request Sender ID	  */
	@Override
	public void setGO_RequestSenderId (java.lang.String GO_RequestSenderId)
	{
		set_Value (COLUMNNAME_GO_RequestSenderId, GO_RequestSenderId);
	}

	/** Get Request Sender ID.
		@return Request Sender ID	  */
	@Override
	public java.lang.String getGO_RequestSenderId () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_GO_RequestSenderId);
	}

	/** Set Request Username.
		@param GO_RequestUsername Request Username	  */
	@Override
	public void setGO_RequestUsername (java.lang.String GO_RequestUsername)
	{
		set_Value (COLUMNNAME_GO_RequestUsername, GO_RequestUsername);
	}

	/** Get Request Username.
		@return Request Username	  */
	@Override
	public java.lang.String getGO_RequestUsername () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_GO_RequestUsername);
	}

	/** Set GO Shipper Configuration.
		@param GO_Shipper_Config_ID GO Shipper Configuration	  */
	@Override
	public void setGO_Shipper_Config_ID (int GO_Shipper_Config_ID)
	{
		if (GO_Shipper_Config_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_GO_Shipper_Config_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_GO_Shipper_Config_ID, Integer.valueOf(GO_Shipper_Config_ID));
	}

	/** Get GO Shipper Configuration.
		@return GO Shipper Configuration	  */
	@Override
	public int getGO_Shipper_Config_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_GO_Shipper_Config_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set GO URL.
		@param GO_URL GO URL	  */
	@Override
	public void setGO_URL (java.lang.String GO_URL)
	{
		set_Value (COLUMNNAME_GO_URL, GO_URL);
	}

	/** Get GO URL.
		@return GO URL	  */
	@Override
	public java.lang.String getGO_URL () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_GO_URL);
	}

	@Override
	public org.compiere.model.I_M_Shipper getM_Shipper() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_Shipper_ID, org.compiere.model.I_M_Shipper.class);
	}

	@Override
	public void setM_Shipper(org.compiere.model.I_M_Shipper M_Shipper)
	{
		set_ValueFromPO(COLUMNNAME_M_Shipper_ID, org.compiere.model.I_M_Shipper.class, M_Shipper);
	}

	/** Set Lieferweg.
		@param M_Shipper_ID 
		Methode oder Art der Warenlieferung
	  */
	@Override
	public void setM_Shipper_ID (int M_Shipper_ID)
	{
		if (M_Shipper_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Shipper_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Shipper_ID, Integer.valueOf(M_Shipper_ID));
	}

	/** Get Lieferweg.
		@return Methode oder Art der Warenlieferung
	  */
	@Override
	public int getM_Shipper_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Shipper_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}