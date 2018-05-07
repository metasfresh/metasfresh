/** Generated Model - DO NOT CHANGE */
package de.metas.marketing.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for MKTG_ContactPerson_Attribute
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_MKTG_ContactPerson_Attribute extends org.compiere.model.PO implements I_MKTG_ContactPerson_Attribute, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -1051350101L;

    /** Standard Constructor */
    public X_MKTG_ContactPerson_Attribute (Properties ctx, int MKTG_ContactPerson_Attribute_ID, String trxName)
    {
      super (ctx, MKTG_ContactPerson_Attribute_ID, trxName);
      /** if (MKTG_ContactPerson_Attribute_ID == 0)
        {
			setM_Attribute_ID (0);
			setMKTG_ContactPerson_Attribute_ID (0);
			setMKTG_ContactPerson_ID (0);
        } */
    }

    /** Load Constructor */
    public X_MKTG_ContactPerson_Attribute (Properties ctx, ResultSet rs, String trxName)
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
	public org.compiere.model.I_M_Attribute getM_Attribute() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_Attribute_ID, org.compiere.model.I_M_Attribute.class);
	}

	@Override
	public void setM_Attribute(org.compiere.model.I_M_Attribute M_Attribute)
	{
		set_ValueFromPO(COLUMNNAME_M_Attribute_ID, org.compiere.model.I_M_Attribute.class, M_Attribute);
	}

	/** Set Merkmal.
		@param M_Attribute_ID 
		Produkt-Merkmal
	  */
	@Override
	public void setM_Attribute_ID (int M_Attribute_ID)
	{
		if (M_Attribute_ID < 1) 
			set_Value (COLUMNNAME_M_Attribute_ID, null);
		else 
			set_Value (COLUMNNAME_M_Attribute_ID, Integer.valueOf(M_Attribute_ID));
	}

	/** Get Merkmal.
		@return Produkt-Merkmal
	  */
	@Override
	public int getM_Attribute_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Attribute_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set MKTG_ContactPerson_Attribute.
		@param MKTG_ContactPerson_Attribute_ID MKTG_ContactPerson_Attribute	  */
	@Override
	public void setMKTG_ContactPerson_Attribute_ID (int MKTG_ContactPerson_Attribute_ID)
	{
		if (MKTG_ContactPerson_Attribute_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_MKTG_ContactPerson_Attribute_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_MKTG_ContactPerson_Attribute_ID, Integer.valueOf(MKTG_ContactPerson_Attribute_ID));
	}

	/** Get MKTG_ContactPerson_Attribute.
		@return MKTG_ContactPerson_Attribute	  */
	@Override
	public int getMKTG_ContactPerson_Attribute_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_MKTG_ContactPerson_Attribute_ID);
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
			set_ValueNoCheck (COLUMNNAME_MKTG_ContactPerson_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_MKTG_ContactPerson_ID, Integer.valueOf(MKTG_ContactPerson_ID));
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