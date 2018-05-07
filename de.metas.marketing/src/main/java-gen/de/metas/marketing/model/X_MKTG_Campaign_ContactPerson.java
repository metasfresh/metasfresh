/** Generated Model - DO NOT CHANGE */
package de.metas.marketing.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for MKTG_Campaign_ContactPerson
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_MKTG_Campaign_ContactPerson extends org.compiere.model.PO implements I_MKTG_Campaign_ContactPerson, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1962662593L;

    /** Standard Constructor */
    public X_MKTG_Campaign_ContactPerson (Properties ctx, int MKTG_Campaign_ContactPerson_ID, String trxName)
    {
      super (ctx, MKTG_Campaign_ContactPerson_ID, trxName);
      /** if (MKTG_Campaign_ContactPerson_ID == 0)
        {
			setMKTG_Campaign_ContactPerson_ID (0);
			setMKTG_Campaign_ID (0);
			setMKTG_ContactPerson_ID (0);
        } */
    }

    /** Load Constructor */
    public X_MKTG_Campaign_ContactPerson (Properties ctx, ResultSet rs, String trxName)
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

	/** Set MKTG_Campaign_ContactPerson.
		@param MKTG_Campaign_ContactPerson_ID MKTG_Campaign_ContactPerson	  */
	@Override
	public void setMKTG_Campaign_ContactPerson_ID (int MKTG_Campaign_ContactPerson_ID)
	{
		if (MKTG_Campaign_ContactPerson_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_MKTG_Campaign_ContactPerson_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_MKTG_Campaign_ContactPerson_ID, Integer.valueOf(MKTG_Campaign_ContactPerson_ID));
	}

	/** Get MKTG_Campaign_ContactPerson.
		@return MKTG_Campaign_ContactPerson	  */
	@Override
	public int getMKTG_Campaign_ContactPerson_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_MKTG_Campaign_ContactPerson_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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
			set_Value (COLUMNNAME_MKTG_Campaign_ID, null);
		else 
			set_Value (COLUMNNAME_MKTG_Campaign_ID, Integer.valueOf(MKTG_Campaign_ID));
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