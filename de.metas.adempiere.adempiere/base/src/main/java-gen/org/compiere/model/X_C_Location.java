/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_Location
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_Location extends org.compiere.model.PO implements I_C_Location, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 454342047L;

    /** Standard Constructor */
    public X_C_Location (Properties ctx, int C_Location_ID, String trxName)
    {
      super (ctx, C_Location_ID, trxName);
      /** if (C_Location_ID == 0)
        {
			setC_Country_ID (0);
			setC_Location_ID (0);
			setIsPostalValidated (false); // N
        } */
    }

    /** Load Constructor */
    public X_C_Location (Properties ctx, ResultSet rs, String trxName)
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

	/** Set Straße und Nr..
		@param Address1 
		Adresszeile 1 für diesen Standort
	  */
	@Override
	public void setAddress1 (java.lang.String Address1)
	{
		set_Value (COLUMNNAME_Address1, Address1);
	}

	/** Get Straße und Nr..
		@return Adresszeile 1 für diesen Standort
	  */
	@Override
	public java.lang.String getAddress1 () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Address1);
	}

	/** Set Adresszusatz.
		@param Address2 
		Adresszeile 2 für diesen Standort
	  */
	@Override
	public void setAddress2 (java.lang.String Address2)
	{
		set_Value (COLUMNNAME_Address2, Address2);
	}

	/** Get Adresszusatz.
		@return Adresszeile 2 für diesen Standort
	  */
	@Override
	public java.lang.String getAddress2 () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Address2);
	}

	/** Set Adresszeile 3.
		@param Address3 
		Address Line 3 for the location
	  */
	@Override
	public void setAddress3 (java.lang.String Address3)
	{
		set_Value (COLUMNNAME_Address3, Address3);
	}

	/** Get Adresszeile 3.
		@return Address Line 3 for the location
	  */
	@Override
	public java.lang.String getAddress3 () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Address3);
	}

	/** Set Adresszusatz.
		@param Address4 
		Adresszeile 4 für diesen Standort
	  */
	@Override
	public void setAddress4 (java.lang.String Address4)
	{
		set_Value (COLUMNNAME_Address4, Address4);
	}

	/** Get Adresszusatz.
		@return Adresszeile 4 für diesen Standort
	  */
	@Override
	public java.lang.String getAddress4 () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Address4);
	}

	@Override
	public org.compiere.model.I_C_City getC_City() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_City_ID, org.compiere.model.I_C_City.class);
	}

	@Override
	public void setC_City(org.compiere.model.I_C_City C_City)
	{
		set_ValueFromPO(COLUMNNAME_C_City_ID, org.compiere.model.I_C_City.class, C_City);
	}

	/** Set Ort.
		@param C_City_ID 
		City
	  */
	@Override
	public void setC_City_ID (int C_City_ID)
	{
		if (C_City_ID < 1) 
			set_Value (COLUMNNAME_C_City_ID, null);
		else 
			set_Value (COLUMNNAME_C_City_ID, Integer.valueOf(C_City_ID));
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
			set_Value (COLUMNNAME_C_Country_ID, null);
		else 
			set_Value (COLUMNNAME_C_Country_ID, Integer.valueOf(C_Country_ID));
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

	/** Set Anschrift.
		@param C_Location_ID 
		Location or Address
	  */
	@Override
	public void setC_Location_ID (int C_Location_ID)
	{
		if (C_Location_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Location_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Location_ID, Integer.valueOf(C_Location_ID));
	}

	/** Get Anschrift.
		@return Location or Address
	  */
	@Override
	public int getC_Location_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Location_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public de.metas.adempiere.model.I_C_Postal getC_Postal() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_Postal_ID, de.metas.adempiere.model.I_C_Postal.class);
	}

	@Override
	public void setC_Postal(de.metas.adempiere.model.I_C_Postal C_Postal)
	{
		set_ValueFromPO(COLUMNNAME_C_Postal_ID, de.metas.adempiere.model.I_C_Postal.class, C_Postal);
	}

	/** Set Postal codes.
		@param C_Postal_ID Postal codes	  */
	@Override
	public void setC_Postal_ID (int C_Postal_ID)
	{
		if (C_Postal_ID < 1) 
			set_Value (COLUMNNAME_C_Postal_ID, null);
		else 
			set_Value (COLUMNNAME_C_Postal_ID, Integer.valueOf(C_Postal_ID));
	}

	/** Get Postal codes.
		@return Postal codes	  */
	@Override
	public int getC_Postal_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Postal_ID);
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

	/** Set C/O.
		@param CareOf 
		In care of
	  */
	@Override
	public void setCareOf (java.lang.String CareOf)
	{
		set_Value (COLUMNNAME_CareOf, CareOf);
	}

	/** Get C/O.
		@return In care of
	  */
	@Override
	public java.lang.String getCareOf () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_CareOf);
	}

	/** Set Ort.
		@param City 
		Identifies a City
	  */
	@Override
	public void setCity (java.lang.String City)
	{
		set_Value (COLUMNNAME_City, City);
	}

	/** Get Ort.
		@return Identifies a City
	  */
	@Override
	public java.lang.String getCity () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_City);
	}

	/** Set PLZ verifiziert.
		@param IsPostalValidated 
		Sagt aus, ob Postleitzahl der Adresse verifiziert wurde.
	  */
	@Override
	public void setIsPostalValidated (boolean IsPostalValidated)
	{
		set_Value (COLUMNNAME_IsPostalValidated, Boolean.valueOf(IsPostalValidated));
	}

	/** Get PLZ verifiziert.
		@return Sagt aus, ob Postleitzahl der Adresse verifiziert wurde.
	  */
	@Override
	public boolean isPostalValidated () 
	{
		Object oo = get_Value(COLUMNNAME_IsPostalValidated);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Postfach.
		@param POBox Postfach	  */
	@Override
	public void setPOBox (java.lang.String POBox)
	{
		set_Value (COLUMNNAME_POBox, POBox);
	}

	/** Get Postfach.
		@return Postfach	  */
	@Override
	public java.lang.String getPOBox () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_POBox);
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

	/** Set -.
		@param Postal_Add 
		Additional ZIP or Postal code
	  */
	@Override
	public void setPostal_Add (java.lang.String Postal_Add)
	{
		set_Value (COLUMNNAME_Postal_Add, Postal_Add);
	}

	/** Get -.
		@return Additional ZIP or Postal code
	  */
	@Override
	public java.lang.String getPostal_Add () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Postal_Add);
	}

	/** Set Region.
		@param RegionName 
		Name of the Region
	  */
	@Override
	public void setRegionName (java.lang.String RegionName)
	{
		set_Value (COLUMNNAME_RegionName, RegionName);
	}

	/** Get Region.
		@return Name of the Region
	  */
	@Override
	public java.lang.String getRegionName () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_RegionName);
	}
}