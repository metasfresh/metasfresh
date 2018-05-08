/** Generated Model - DO NOT CHANGE */
package de.metas.marketing.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for MKTG_Consent
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_MKTG_Consent extends org.compiere.model.PO implements I_MKTG_Consent, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1766758501L;

    /** Standard Constructor */
    public X_MKTG_Consent (Properties ctx, int MKTG_Consent_ID, String trxName)
    {
      super (ctx, MKTG_Consent_ID, trxName);
      /** if (MKTG_Consent_ID == 0)
        {
			setMKTG_Consent_ID (0);
        } */
    }

    /** Load Constructor */
    public X_MKTG_Consent (Properties ctx, ResultSet rs, String trxName)
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
	public org.compiere.model.I_AD_User getAD_User() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_User_ID, org.compiere.model.I_AD_User.class);
	}

	@Override
	public void setAD_User(org.compiere.model.I_AD_User AD_User)
	{
		set_ValueFromPO(COLUMNNAME_AD_User_ID, org.compiere.model.I_AD_User.class, AD_User);
	}

	/** Set Ansprechpartner.
		@param AD_User_ID 
		User within the system - Internal or Business Partner Contact
	  */
	@Override
	public void setAD_User_ID (int AD_User_ID)
	{
		if (AD_User_ID < 0) 
			set_Value (COLUMNNAME_AD_User_ID, null);
		else 
			set_Value (COLUMNNAME_AD_User_ID, Integer.valueOf(AD_User_ID));
	}

	/** Get Ansprechpartner.
		@return User within the system - Internal or Business Partner Contact
	  */
	@Override
	public int getAD_User_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_User_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set Einverständnis erklärt am.
		@param ConsentDeclaredOn Einverständnis erklärt am	  */
	@Override
	public void setConsentDeclaredOn (java.sql.Timestamp ConsentDeclaredOn)
	{
		set_Value (COLUMNNAME_ConsentDeclaredOn, ConsentDeclaredOn);
	}

	/** Get Einverständnis erklärt am.
		@return Einverständnis erklärt am	  */
	@Override
	public java.sql.Timestamp getConsentDeclaredOn () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_ConsentDeclaredOn);
	}

	/** Set Einverständnis widerrufen am.
		@param ConsentRevokedOn Einverständnis widerrufen am	  */
	@Override
	public void setConsentRevokedOn (java.sql.Timestamp ConsentRevokedOn)
	{
		set_Value (COLUMNNAME_ConsentRevokedOn, ConsentRevokedOn);
	}

	/** Get Einverständnis widerrufen am.
		@return Einverständnis widerrufen am	  */
	@Override
	public java.sql.Timestamp getConsentRevokedOn () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_ConsentRevokedOn);
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

	/** 
	 * MarketingPlatformGatewayId AD_Reference_ID=540858
	 * Reference name: MarketingPlatformGatewayId
	 */
	public static final int MARKETINGPLATFORMGATEWAYID_AD_Reference_ID=540858;
	/** CleverReach = CleverReach */
	public static final String MARKETINGPLATFORMGATEWAYID_CleverReach = "CleverReach";
	/** Set Marketing Platform GatewayId.
		@param MarketingPlatformGatewayId Marketing Platform GatewayId	  */
	@Override
	public void setMarketingPlatformGatewayId (java.lang.String MarketingPlatformGatewayId)
	{

		set_Value (COLUMNNAME_MarketingPlatformGatewayId, MarketingPlatformGatewayId);
	}

	/** Get Marketing Platform GatewayId.
		@return Marketing Platform GatewayId	  */
	@Override
	public java.lang.String getMarketingPlatformGatewayId () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_MarketingPlatformGatewayId);
	}

	/** Set MKTG_Consent.
		@param MKTG_Consent_ID MKTG_Consent	  */
	@Override
	public void setMKTG_Consent_ID (int MKTG_Consent_ID)
	{
		if (MKTG_Consent_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_MKTG_Consent_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_MKTG_Consent_ID, Integer.valueOf(MKTG_Consent_ID));
	}

	/** Get MKTG_Consent.
		@return MKTG_Consent	  */
	@Override
	public int getMKTG_Consent_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_MKTG_Consent_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public de.metas.marketing.model.I_MKTG_ContactPerson getMKTG_ContactPerson() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_MKTG_ContactPerson_ID, de.metas.marketing.model.I_MKTG_ContactPerson.class);
	}

	@Override
	public void setMKTG_ContactPerson(de.metas.marketing.model.I_MKTG_ContactPerson MKTG_ContactPerson)
	{
		set_ValueFromPO(COLUMNNAME_MKTG_ContactPerson_ID, de.metas.marketing.model.I_MKTG_ContactPerson.class, MKTG_ContactPerson);
	}

	/** Set MKTG_ContactPerson.
		@param MKTG_ContactPerson_ID MKTG_ContactPerson	  */
	@Override
	public void setMKTG_ContactPerson_ID (int MKTG_ContactPerson_ID)
	{
		if (MKTG_ContactPerson_ID < 1) 
			set_Value (COLUMNNAME_MKTG_ContactPerson_ID, null);
		else 
			set_Value (COLUMNNAME_MKTG_ContactPerson_ID, Integer.valueOf(MKTG_ContactPerson_ID));
	}

	/** Get MKTG_ContactPerson.
		@return MKTG_ContactPerson	  */
	@Override
	public int getMKTG_ContactPerson_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_MKTG_ContactPerson_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}