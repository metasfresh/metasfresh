/** Generated Model - DO NOT CHANGE */
package de.metas.vertical.pharma.vendor.gateway.mvs3.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for MSV3_Vendor_Config
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_MSV3_Vendor_Config extends org.compiere.model.PO implements I_MSV3_Vendor_Config, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1652367961L;

    /** Standard Constructor */
    public X_MSV3_Vendor_Config (Properties ctx, int MSV3_Vendor_Config_ID, String trxName)
    {
      super (ctx, MSV3_Vendor_Config_ID, trxName);
      /** if (MSV3_Vendor_Config_ID == 0)
        {
			setC_BPartner_ID (0);
			setMSV3_BaseUrl (null);
			setMSV3_Vendor_Config_ID (0);
			setPassword (null);
			setUserID (null);
        } */
    }

    /** Load Constructor */
    public X_MSV3_Vendor_Config (Properties ctx, ResultSet rs, String trxName)
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

	/** Set MSV3 Base URL.
		@param MSV3_BaseUrl 
		Beispiel: https://msv3-server:443/msv3/v2.0
	  */
	@Override
	public void setMSV3_BaseUrl (java.lang.String MSV3_BaseUrl)
	{
		set_Value (COLUMNNAME_MSV3_BaseUrl, MSV3_BaseUrl);
	}

	/** Get MSV3 Base URL.
		@return Beispiel: https://msv3-server:443/msv3/v2.0
	  */
	@Override
	public java.lang.String getMSV3_BaseUrl () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_MSV3_BaseUrl);
	}

	/** Set MSV3_Vendor_Config.
		@param MSV3_Vendor_Config_ID MSV3_Vendor_Config	  */
	@Override
	public void setMSV3_Vendor_Config_ID (int MSV3_Vendor_Config_ID)
	{
		if (MSV3_Vendor_Config_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_MSV3_Vendor_Config_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_MSV3_Vendor_Config_ID, Integer.valueOf(MSV3_Vendor_Config_ID));
	}

	/** Get MSV3_Vendor_Config.
		@return MSV3_Vendor_Config	  */
	@Override
	public int getMSV3_Vendor_Config_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_MSV3_Vendor_Config_ID);
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