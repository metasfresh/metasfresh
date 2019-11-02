/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for M_CustomsTariff
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_M_CustomsTariff extends org.compiere.model.PO implements I_M_CustomsTariff, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 543378193L;

    /** Standard Constructor */
    public X_M_CustomsTariff (Properties ctx, int M_CustomsTariff_ID, String trxName)
    {
      super (ctx, M_CustomsTariff_ID, trxName);
      /** if (M_CustomsTariff_ID == 0)
        {
			setM_CustomsTariff_ID (0);
			setName (null);
			setValue (null);
        } */
    }

    /** Load Constructor */
    public X_M_CustomsTariff (Properties ctx, ResultSet rs, String trxName)
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

	/** Set Customs Tariff.
		@param M_CustomsTariff_ID Customs Tariff	  */
	@Override
	public void setM_CustomsTariff_ID (int M_CustomsTariff_ID)
	{
		if (M_CustomsTariff_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_CustomsTariff_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_CustomsTariff_ID, Integer.valueOf(M_CustomsTariff_ID));
	}

	/** Get Customs Tariff.
		@return Customs Tariff	  */
	@Override
	public int getM_CustomsTariff_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_CustomsTariff_ID);
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
}