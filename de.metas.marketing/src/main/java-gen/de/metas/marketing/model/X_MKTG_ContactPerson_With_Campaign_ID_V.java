/** Generated Model - DO NOT CHANGE */
package de.metas.marketing.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for MKTG_ContactPerson_With_Campaign_ID_V
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_MKTG_ContactPerson_With_Campaign_ID_V extends org.compiere.model.PO implements I_MKTG_ContactPerson_With_Campaign_ID_V, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -636558285L;

    /** Standard Constructor */
    public X_MKTG_ContactPerson_With_Campaign_ID_V (Properties ctx, int MKTG_ContactPerson_With_Campaign_ID_V_ID, String trxName)
    {
      super (ctx, MKTG_ContactPerson_With_Campaign_ID_V_ID, trxName);
      /** if (MKTG_ContactPerson_With_Campaign_ID_V_ID == 0)
        {
			setMKTG_ContactPerson_With_Campaign_ID_ID (0);
			setName (null);
        } */
    }

    /** Load Constructor */
    public X_MKTG_ContactPerson_With_Campaign_ID_V (Properties ctx, ResultSet rs, String trxName)
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
			set_ValueNoCheck (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_ID, Integer.valueOf(C_BPartner_ID));
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

	/** Set eMail.
		@param EMail 
		EMail-Adresse
	  */
	@Override
	public void setEMail (java.lang.String EMail)
	{
		set_Value (COLUMNNAME_EMail, EMail);
	}

	/** Get eMail.
		@return EMail-Adresse
	  */
	@Override
	public java.lang.String getEMail () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_EMail);
	}

	@Override
	public de.metas.marketing.model.I_MKTG_Campaign getMKTG_Campaign() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_MKTG_Campaign_ID, de.metas.marketing.model.I_MKTG_Campaign.class);
	}

	@Override
	public void setMKTG_Campaign(de.metas.marketing.model.I_MKTG_Campaign MKTG_Campaign)
	{
		set_ValueFromPO(COLUMNNAME_MKTG_Campaign_ID, de.metas.marketing.model.I_MKTG_Campaign.class, MKTG_Campaign);
	}

	/** Set MKTG_Campaign.
		@param MKTG_Campaign_ID MKTG_Campaign	  */
	@Override
	public void setMKTG_Campaign_ID (int MKTG_Campaign_ID)
	{
		if (MKTG_Campaign_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_MKTG_Campaign_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_MKTG_Campaign_ID, Integer.valueOf(MKTG_Campaign_ID));
	}

	/** Get MKTG_Campaign.
		@return MKTG_Campaign	  */
	@Override
	public int getMKTG_Campaign_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_MKTG_Campaign_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set MKTG_ContactPerson_With_Campaign_ID.
		@param MKTG_ContactPerson_With_Campaign_ID_ID MKTG_ContactPerson_With_Campaign_ID	  */
	@Override
	public void setMKTG_ContactPerson_With_Campaign_ID_ID (int MKTG_ContactPerson_With_Campaign_ID_ID)
	{
		if (MKTG_ContactPerson_With_Campaign_ID_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_MKTG_ContactPerson_With_Campaign_ID_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_MKTG_ContactPerson_With_Campaign_ID_ID, Integer.valueOf(MKTG_ContactPerson_With_Campaign_ID_ID));
	}

	/** Get MKTG_ContactPerson_With_Campaign_ID.
		@return MKTG_ContactPerson_With_Campaign_ID	  */
	@Override
	public int getMKTG_ContactPerson_With_Campaign_ID_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_MKTG_ContactPerson_With_Campaign_ID_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Name.
		@param Name 
		Alphanumeric identifier of the entity
	  */
	@Override
	public void setName (java.lang.String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	/** Get Name.
		@return Alphanumeric identifier of the entity
	  */
	@Override
	public java.lang.String getName () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Name);
	}
}