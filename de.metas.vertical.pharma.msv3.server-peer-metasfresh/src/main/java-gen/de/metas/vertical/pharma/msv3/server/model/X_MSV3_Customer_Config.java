/** Generated Model - DO NOT CHANGE */
package de.metas.vertical.pharma.msv3.server.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for MSV3_Customer_Config
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_MSV3_Customer_Config extends org.compiere.model.PO implements I_MSV3_Customer_Config, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -1331481119L;

    /** Standard Constructor */
    public X_MSV3_Customer_Config (Properties ctx, int MSV3_Customer_Config_ID, String trxName)
    {
      super (ctx, MSV3_Customer_Config_ID, trxName);
      /** if (MSV3_Customer_Config_ID == 0)
        {
			setC_BPartner_ID (0);
			setC_BPartner_Location_ID (0);
			setMSV3_Customer_Config_ID (0);
			setPassword (null);
			setUserID (null);
        } */
    }

    /** Load Constructor */
    public X_MSV3_Customer_Config (Properties ctx, ResultSet rs, String trxName)
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

	@Override
	public org.compiere.model.I_C_BPartner getC_BPartner() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_BPartner_ID, org.compiere.model.I_C_BPartner.class);
	}

	@Override
	public void setC_BPartner(org.compiere.model.I_C_BPartner C_BPartner)
	{
		set_ValueFromPO(COLUMNNAME_C_BPartner_ID, org.compiere.model.I_C_BPartner.class, C_BPartner);
	}

	/** Set Geschäftspartner.
		@param C_BPartner_ID 
		Bezeichnet einen Geschäftspartner
	  */
	@Override
	public void setC_BPartner_ID (int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_ID, Integer.valueOf(C_BPartner_ID));
	}

	/** Get Geschäftspartner.
		@return Bezeichnet einen Geschäftspartner
	  */
	@Override
	public int getC_BPartner_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_BPartner_Location getC_BPartner_Location() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_BPartner_Location_ID, org.compiere.model.I_C_BPartner_Location.class);
	}

	@Override
	public void setC_BPartner_Location(org.compiere.model.I_C_BPartner_Location C_BPartner_Location)
	{
		set_ValueFromPO(COLUMNNAME_C_BPartner_Location_ID, org.compiere.model.I_C_BPartner_Location.class, C_BPartner_Location);
	}

	/** Set Standort.
		@param C_BPartner_Location_ID 
		Identifiziert die (Liefer-) Adresse des Geschäftspartners
	  */
	@Override
	public void setC_BPartner_Location_ID (int C_BPartner_Location_ID)
	{
		if (C_BPartner_Location_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_Location_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_Location_ID, Integer.valueOf(C_BPartner_Location_ID));
	}

	/** Get Standort.
		@return Identifiziert die (Liefer-) Adresse des Geschäftspartners
	  */
	@Override
	public int getC_BPartner_Location_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_Location_ID);
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

	/** Set MSV3 Customer Config.
		@param MSV3_Customer_Config_ID MSV3 Customer Config	  */
	@Override
	public void setMSV3_Customer_Config_ID (int MSV3_Customer_Config_ID)
	{
		if (MSV3_Customer_Config_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_MSV3_Customer_Config_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_MSV3_Customer_Config_ID, Integer.valueOf(MSV3_Customer_Config_ID));
	}

	/** Get MSV3 Customer Config.
		@return MSV3 Customer Config	  */
	@Override
	public int getMSV3_Customer_Config_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_MSV3_Customer_Config_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set Nutzerkennung.
		@param UserID Nutzerkennung	  */
	@Override
	public void setUserID (java.lang.String UserID)
	{
		set_Value (COLUMNNAME_UserID, UserID);
	}

	/** Get Nutzerkennung.
		@return Nutzerkennung	  */
	@Override
	public java.lang.String getUserID () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_UserID);
	}
}