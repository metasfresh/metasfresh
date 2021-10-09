/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for M_Shipper
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_M_Shipper extends org.compiere.model.PO implements I_M_Shipper, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -1137521397L;

    /** Standard Constructor */
    public X_M_Shipper (Properties ctx, int M_Shipper_ID, String trxName)
    {
      super (ctx, M_Shipper_ID, trxName);
      /** if (M_Shipper_ID == 0)
        {
			setIsDefault (false); // N
			setM_Shipper_ID (0);
			setName (null);
        } */
    }

    /** Load Constructor */
    public X_M_Shipper (Properties ctx, ResultSet rs, String trxName)
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

	/** Set Geschäftspartner.
		@param C_BPartner_ID 
		Identifies a Business Partner
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
		@return Identifies a Business Partner
	  */
	@Override
	public int getC_BPartner_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_ID);
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

	/** Set Standard.
		@param IsDefault 
		Default value
	  */
	@Override
	public void setIsDefault (boolean IsDefault)
	{
		set_Value (COLUMNNAME_IsDefault, Boolean.valueOf(IsDefault));
	}

	/** Get Standard.
		@return Default value
	  */
	@Override
	public boolean isDefault () 
	{
		Object oo = get_Value(COLUMNNAME_IsDefault);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Lieferweg.
		@param M_Shipper_ID 
		Method or manner of product delivery
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
		@return Method or manner of product delivery
	  */
	@Override
	public int getM_Shipper_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Shipper_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Name.
		@param Name Name	  */
	@Override
	public void setName (java.lang.String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	/** Get Name.
		@return Name	  */
	@Override
	public java.lang.String getName () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Name);
	}

	/** 
	 * ShipperGateway AD_Reference_ID=540808
	 * Reference name: ShipperGateway
	 */
	public static final int SHIPPERGATEWAY_AD_Reference_ID=540808;
	/** GO = go */
	public static final String SHIPPERGATEWAY_GO = "go";
	/** Der Kurier = derKurier */
	public static final String SHIPPERGATEWAY_DerKurier = "derKurier";
	/** DHL = dhl */
	public static final String SHIPPERGATEWAY_DHL = "dhl";
	/** DPD = dpd */
	public static final String SHIPPERGATEWAY_DPD = "dpd";
	/** Set Shipper Gateway.
		@param ShipperGateway Shipper Gateway	  */
	@Override
	public void setShipperGateway (java.lang.String ShipperGateway)
	{

		set_Value (COLUMNNAME_ShipperGateway, ShipperGateway);
	}

	/** Get Shipper Gateway.
		@return Shipper Gateway	  */
	@Override
	public java.lang.String getShipperGateway () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ShipperGateway);
	}

	/** Set Nachverfolgungs-URL.
		@param TrackingURL 
		URL of the shipper to track shipments
	  */
	@Override
	public void setTrackingURL (java.lang.String TrackingURL)
	{
		set_Value (COLUMNNAME_TrackingURL, TrackingURL);
	}

	/** Get Nachverfolgungs-URL.
		@return URL of the shipper to track shipments
	  */
	@Override
	public java.lang.String getTrackingURL () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_TrackingURL);
	}

	/** Set Suchschlüssel.
		@param Value 
		Suchschlüssel für den Eintrag im erforderlichen Format - muss eindeutig sein
	  */
	@Override
	public void setValue (java.lang.String Value)
	{
		set_Value (COLUMNNAME_Value, Value);
	}

	/** Get Suchschlüssel.
		@return Suchschlüssel für den Eintrag im erforderlichen Format - muss eindeutig sein
	  */
	@Override
	public java.lang.String getValue () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Value);
	}

	@Override
	public void setInternalName (java.lang.String InternalName)
	{
		set_Value (COLUMNNAME_InternalName, InternalName);
	}

	@Override
	public java.lang.String getInternalName()
	{
		return (java.lang.String)get_Value(COLUMNNAME_InternalName);
	}

}
