/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_City
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_City extends org.compiere.model.PO implements I_C_City, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1130282910L;

    /** Standard Constructor */
    public X_C_City (Properties ctx, int C_City_ID, String trxName)
    {
      super (ctx, C_City_ID, trxName);
      /** if (C_City_ID == 0)
        {
			setC_City_ID (0);
			setC_Country_ID (0);
			setName (null);
        } */
    }

    /** Load Constructor */
    public X_C_City (Properties ctx, ResultSet rs, String trxName)
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

	/** Set Area Code.
		@param AreaCode 
		Phone Area Code
	  */
	@Override
	public void setAreaCode (java.lang.String AreaCode)
	{
		set_Value (COLUMNNAME_AreaCode, AreaCode);
	}

	/** Get Area Code.
		@return Phone Area Code
	  */
	@Override
	public java.lang.String getAreaCode () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_AreaCode);
	}

	/** Set Ort.
		@param C_City_ID 
		City
	  */
	@Override
	public void setC_City_ID (int C_City_ID)
	{
		if (C_City_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_City_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_City_ID, Integer.valueOf(C_City_ID));
	}

	/** Get Ort.
		@return City
	  */
	@Override
	public int getC_City_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_City_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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
		Country 
	  */
	@Override
	public void setC_Country_ID (int C_Country_ID)
	{
		if (C_Country_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Country_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Country_ID, Integer.valueOf(C_Country_ID));
	}

	/** Get Land.
		@return Country 
	  */
	@Override
	public int getC_Country_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Country_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_Region getC_Region() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_Region_ID, org.compiere.model.I_C_Region.class);
	}

	@Override
	public void setC_Region(org.compiere.model.I_C_Region C_Region)
	{
		set_ValueFromPO(COLUMNNAME_C_Region_ID, org.compiere.model.I_C_Region.class, C_Region);
	}

	/** Set Region.
		@param C_Region_ID 
		Identifies a geographical Region
	  */
	@Override
	public void setC_Region_ID (int C_Region_ID)
	{
		if (C_Region_ID < 1) 
			set_Value (COLUMNNAME_C_Region_ID, null);
		else 
			set_Value (COLUMNNAME_C_Region_ID, Integer.valueOf(C_Region_ID));
	}

	/** Get Region.
		@return Identifies a geographical Region
	  */
	@Override
	public int getC_Region_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Region_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Coordinates.
		@param Coordinates 
		Location coordinate
	  */
	@Override
	public void setCoordinates (java.lang.String Coordinates)
	{
		set_Value (COLUMNNAME_Coordinates, Coordinates);
	}

	/** Get Coordinates.
		@return Location coordinate
	  */
	@Override
	public java.lang.String getCoordinates () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Coordinates);
	}

	/** Set Locode.
		@param Locode 
		Location code - UN/LOCODE 
	  */
	@Override
	public void setLocode (java.lang.String Locode)
	{
		set_Value (COLUMNNAME_Locode, Locode);
	}

	/** Get Locode.
		@return Location code - UN/LOCODE 
	  */
	@Override
	public java.lang.String getLocode () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Locode);
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

	/** Set PLZ.
		@param Postal 
		Postal code
	  */
	@Override
	public void setPostal (java.lang.String Postal)
	{
		set_Value (COLUMNNAME_Postal, Postal);
	}

	/** Get PLZ.
		@return Postal code
	  */
	@Override
	public java.lang.String getPostal () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Postal);
	}
}