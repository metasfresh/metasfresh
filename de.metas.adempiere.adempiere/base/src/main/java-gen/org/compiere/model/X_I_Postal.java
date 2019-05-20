/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for I_Postal
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_I_Postal extends org.compiere.model.PO implements I_I_Postal, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1373131476L;

    /** Standard Constructor */
    public X_I_Postal (Properties ctx, int I_Postal_ID, String trxName)
    {
      super (ctx, I_Postal_ID, trxName);
      /** if (I_Postal_ID == 0)
        {
			setI_IsImported (null); // N
			setI_Postal_ID (0);
			setProcessed (false); // N
        } */
    }

    /** Load Constructor */
    public X_I_Postal (Properties ctx, ResultSet rs, String trxName)
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

	/** Set Ort.
		@param City 
		Name des Ortes
	  */
	@Override
	public void setCity (java.lang.String City)
	{
		set_Value (COLUMNNAME_City, City);
	}

	/** Get Ort.
		@return Name des Ortes
	  */
	@Override
	public java.lang.String getCity () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_City);
	}

	/** Set ISO Ländercode.
		@param CountryCode 
		Zweibuchstabiger ISO Ländercode gemäß ISO 3166-1 - http://www.chemie.fu-berlin.de/diverse/doc/ISO_3166.html
	  */
	@Override
	public void setCountryCode (java.lang.String CountryCode)
	{
		set_Value (COLUMNNAME_CountryCode, CountryCode);
	}

	/** Get ISO Ländercode.
		@return Zweibuchstabiger ISO Ländercode gemäß ISO 3166-1 - http://www.chemie.fu-berlin.de/diverse/doc/ISO_3166.html
	  */
	@Override
	public java.lang.String getCountryCode () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_CountryCode);
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
		Identifiziert eine geographische Region
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
		@return Identifiziert eine geographische Region
	  */
	@Override
	public int getC_Region_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Region_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Import-Fehlermeldung.
		@param I_ErrorMsg 
		Meldungen, die durch den Importprozess generiert wurden
	  */
	@Override
	public void setI_ErrorMsg (java.lang.String I_ErrorMsg)
	{
		set_Value (COLUMNNAME_I_ErrorMsg, I_ErrorMsg);
	}

	/** Get Import-Fehlermeldung.
		@return Meldungen, die durch den Importprozess generiert wurden
	  */
	@Override
	public java.lang.String getI_ErrorMsg () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_I_ErrorMsg);
	}

	/** 
	 * I_IsImported AD_Reference_ID=540745
	 * Reference name: I_IsImported
	 */
	public static final int I_ISIMPORTED_AD_Reference_ID=540745;
	/** NotImported = N */
	public static final String I_ISIMPORTED_NotImported = "N";
	/** Imported = Y */
	public static final String I_ISIMPORTED_Imported = "Y";
	/** ImportFailed = E */
	public static final String I_ISIMPORTED_ImportFailed = "E";
	/** Set Importiert.
		@param I_IsImported 
		Ist dieser Import verarbeitet worden?
	  */
	@Override
	public void setI_IsImported (java.lang.String I_IsImported)
	{

		set_Value (COLUMNNAME_I_IsImported, I_IsImported);
	}

	/** Get Importiert.
		@return Ist dieser Import verarbeitet worden?
	  */
	@Override
	public java.lang.String getI_IsImported () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_I_IsImported);
	}

	/** Set Import Postal Data.
		@param I_Postal_ID Import Postal Data	  */
	@Override
	public void setI_Postal_ID (int I_Postal_ID)
	{
		if (I_Postal_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_I_Postal_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_I_Postal_ID, Integer.valueOf(I_Postal_ID));
	}

	/** Get Import Postal Data.
		@return Import Postal Data	  */
	@Override
	public int getI_Postal_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_I_Postal_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set PLZ.
		@param Postal 
		Postleitzahl
	  */
	@Override
	public void setPostal (java.lang.String Postal)
	{
		set_Value (COLUMNNAME_Postal, Postal);
	}

	/** Get PLZ.
		@return Postleitzahl
	  */
	@Override
	public java.lang.String getPostal () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Postal);
	}

	/** Set Verarbeitet.
		@param Processed 
		Checkbox sagt aus, ob der Beleg verarbeitet wurde. 
	  */
	@Override
	public void setProcessed (boolean Processed)
	{
		set_Value (COLUMNNAME_Processed, Boolean.valueOf(Processed));
	}

	/** Get Verarbeitet.
		@return Checkbox sagt aus, ob der Beleg verarbeitet wurde. 
	  */
	@Override
	public boolean isProcessed () 
	{
		Object oo = get_Value(COLUMNNAME_Processed);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Region.
		@param RegionName 
		Name der Region
	  */
	@Override
	public void setRegionName (java.lang.String RegionName)
	{
		set_Value (COLUMNNAME_RegionName, RegionName);
	}

	/** Get Region.
		@return Name der Region
	  */
	@Override
	public java.lang.String getRegionName () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_RegionName);
	}

	/** Set Gültig ab.
		@param ValidFrom 
		Gültig ab inklusiv (erster Tag)
	  */
	@Override
	public void setValidFrom (java.sql.Timestamp ValidFrom)
	{
		set_Value (COLUMNNAME_ValidFrom, ValidFrom);
	}

	/** Get Gültig ab.
		@return Gültig ab inklusiv (erster Tag)
	  */
	@Override
	public java.sql.Timestamp getValidFrom () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_ValidFrom);
	}

	/** Set Gültig bis.
		@param ValidTo 
		Gültig bis inklusiv (letzter Tag)
	  */
	@Override
	public void setValidTo (java.sql.Timestamp ValidTo)
	{
		set_Value (COLUMNNAME_ValidTo, ValidTo);
	}

	/** Get Gültig bis.
		@return Gültig bis inklusiv (letzter Tag)
	  */
	@Override
	public java.sql.Timestamp getValidTo () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_ValidTo);
	}
}