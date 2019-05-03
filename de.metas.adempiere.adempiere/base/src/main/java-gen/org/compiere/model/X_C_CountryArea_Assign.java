/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_CountryArea_Assign
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_CountryArea_Assign extends org.compiere.model.PO implements I_C_CountryArea_Assign, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1569835791L;

    /** Standard Constructor */
    public X_C_CountryArea_Assign (Properties ctx, int C_CountryArea_Assign_ID, String trxName)
    {
      super (ctx, C_CountryArea_Assign_ID, trxName);
      /** if (C_CountryArea_Assign_ID == 0)
        {
			setC_Country_ID (0);
			setC_CountryArea_Assign_ID (0);
			setC_CountryArea_ID (0);
			setValidFrom (new Timestamp( System.currentTimeMillis() ));
        } */
    }

    /** Load Constructor */
    public X_C_CountryArea_Assign (Properties ctx, ResultSet rs, String trxName)
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
	public org.compiere.model.I_C_Country getC_Country() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_Country_ID, org.compiere.model.I_C_Country.class);
	}

	@Override
	public void setC_Country(org.compiere.model.I_C_Country C_Country)
	{
		set_ValueFromPO(COLUMNNAME_C_Country_ID, org.compiere.model.I_C_Country.class, C_Country);
	}

	/** Set Land.
		@param C_Country_ID 
		Land
	  */
	@Override
	public void setC_Country_ID (int C_Country_ID)
	{
		if (C_Country_ID < 1) 
			set_Value (COLUMNNAME_C_Country_ID, null);
		else 
			set_Value (COLUMNNAME_C_Country_ID, Integer.valueOf(C_Country_ID));
	}

	/** Get Land.
		@return Land
	  */
	@Override
	public int getC_Country_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Country_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Country area assign.
		@param C_CountryArea_Assign_ID Country area assign	  */
	@Override
	public void setC_CountryArea_Assign_ID (int C_CountryArea_Assign_ID)
	{
		if (C_CountryArea_Assign_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_CountryArea_Assign_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_CountryArea_Assign_ID, Integer.valueOf(C_CountryArea_Assign_ID));
	}

	/** Get Country area assign.
		@return Country area assign	  */
	@Override
	public int getC_CountryArea_Assign_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_CountryArea_Assign_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_CountryArea getC_CountryArea() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_CountryArea_ID, org.compiere.model.I_C_CountryArea.class);
	}

	@Override
	public void setC_CountryArea(org.compiere.model.I_C_CountryArea C_CountryArea)
	{
		set_ValueFromPO(COLUMNNAME_C_CountryArea_ID, org.compiere.model.I_C_CountryArea.class, C_CountryArea);
	}

	/** Set Country Area.
		@param C_CountryArea_ID Country Area	  */
	@Override
	public void setC_CountryArea_ID (int C_CountryArea_ID)
	{
		if (C_CountryArea_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_CountryArea_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_CountryArea_ID, Integer.valueOf(C_CountryArea_ID));
	}

	/** Get Country Area.
		@return Country Area	  */
	@Override
	public int getC_CountryArea_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_CountryArea_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set GÃ¼ltig ab.
		@param ValidFrom 
		GÃ¼ltig ab inklusiv (erster Tag)
	  */
	@Override
	public void setValidFrom (java.sql.Timestamp ValidFrom)
	{
		set_Value (COLUMNNAME_ValidFrom, ValidFrom);
	}

	/** Get GÃ¼ltig ab.
		@return GÃ¼ltig ab inklusiv (erster Tag)
	  */
	@Override
	public java.sql.Timestamp getValidFrom () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_ValidFrom);
	}

	/** Set GÃ¼ltig bis.
		@param ValidTo 
		GÃ¼ltig bis inklusiv (letzter Tag)
	  */
	@Override
	public void setValidTo (java.sql.Timestamp ValidTo)
	{
		set_Value (COLUMNNAME_ValidTo, ValidTo);
	}

	/** Get GÃ¼ltig bis.
		@return GÃ¼ltig bis inklusiv (letzter Tag)
	  */
	@Override
	public java.sql.Timestamp getValidTo () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_ValidTo);
	}
}