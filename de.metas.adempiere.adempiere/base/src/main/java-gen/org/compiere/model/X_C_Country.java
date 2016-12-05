/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_Country
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_Country extends org.compiere.model.PO implements I_C_Country, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -1166864290L;

    /** Standard Constructor */
    public X_C_Country (Properties ctx, int C_Country_ID, String trxName)
    {
      super (ctx, C_Country_ID, trxName);
      /** if (C_Country_ID == 0)
        {
			setC_Country_ID (0);
			setCountryCode (null);
			setDisplaySequence (null);
// @C@, @R@ @P@
			setHasPostal_Add (false);
			setHasRegion (false);
			setIsAddressLinesLocalReverse (false);
			setIsAddressLinesReverse (false);
			setName (null);
        } */
    }

    /** Load Constructor */
    public X_C_Country (Properties ctx, ResultSet rs, String trxName)
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

	/** Set Account Type Length.
		@param AccountTypeLength Account Type Length	  */
	@Override
	public void setAccountTypeLength (java.lang.String AccountTypeLength)
	{
		set_Value (COLUMNNAME_AccountTypeLength, AccountTypeLength);
	}

	/** Get Account Type Length.
		@return Account Type Length	  */
	@Override
	public java.lang.String getAccountTypeLength () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_AccountTypeLength);
	}

	/** 
	 * AD_Language AD_Reference_ID=106
	 * Reference name: AD_Language
	 */
	public static final int AD_LANGUAGE_AD_Reference_ID=106;
	/** Set Sprache.
		@param AD_Language 
		Language for this entity
	  */
	@Override
	public void setAD_Language (java.lang.String AD_Language)
	{

		set_Value (COLUMNNAME_AD_Language, AD_Language);
	}

	/** Get Sprache.
		@return Language for this entity
	  */
	@Override
	public java.lang.String getAD_Language () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_AD_Language);
	}

	/** Set Allow Cities out of List.
		@param AllowCitiesOutOfList 
		A flag to allow cities, currently not in the list, to be entered
	  */
	@Override
	public void setAllowCitiesOutOfList (boolean AllowCitiesOutOfList)
	{
		set_Value (COLUMNNAME_AllowCitiesOutOfList, Boolean.valueOf(AllowCitiesOutOfList));
	}

	/** Get Allow Cities out of List.
		@return A flag to allow cities, currently not in the list, to be entered
	  */
	@Override
	public boolean isAllowCitiesOutOfList () 
	{
		Object oo = get_Value(COLUMNNAME_AllowCitiesOutOfList);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
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
	public org.compiere.model.I_C_Currency getC_Currency() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_Currency_ID, org.compiere.model.I_C_Currency.class);
	}

	@Override
	public void setC_Currency(org.compiere.model.I_C_Currency C_Currency)
	{
		set_ValueFromPO(COLUMNNAME_C_Currency_ID, org.compiere.model.I_C_Currency.class, C_Currency);
	}

	/** Set Währung.
		@param C_Currency_ID 
		The Currency for this record
	  */
	@Override
	public void setC_Currency_ID (int C_Currency_ID)
	{
		if (C_Currency_ID < 1) 
			set_Value (COLUMNNAME_C_Currency_ID, null);
		else 
			set_Value (COLUMNNAME_C_Currency_ID, Integer.valueOf(C_Currency_ID));
	}

	/** Get Währung.
		@return The Currency for this record
	  */
	@Override
	public int getC_Currency_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Currency_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Capture Sequence.
		@param CaptureSequence Capture Sequence	  */
	@Override
	public void setCaptureSequence (java.lang.String CaptureSequence)
	{
		set_Value (COLUMNNAME_CaptureSequence, CaptureSequence);
	}

	/** Get Capture Sequence.
		@return Capture Sequence	  */
	@Override
	public java.lang.String getCaptureSequence () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_CaptureSequence);
	}

	/** Set ISO Ländercode.
		@param CountryCode 
		Upper-case two-letter alphanumeric ISO Country code according to ISO 3166-1 - http://www.chemie.fu-berlin.de/diverse/doc/ISO_3166.html
	  */
	@Override
	public void setCountryCode (java.lang.String CountryCode)
	{
		set_Value (COLUMNNAME_CountryCode, CountryCode);
	}

	/** Get ISO Ländercode.
		@return Upper-case two-letter alphanumeric ISO Country code according to ISO 3166-1 - http://www.chemie.fu-berlin.de/diverse/doc/ISO_3166.html
	  */
	@Override
	public java.lang.String getCountryCode () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_CountryCode);
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

	/** Set Adress-Druckformat.
		@param DisplaySequence 
		Format for printing this Address
	  */
	@Override
	public void setDisplaySequence (java.lang.String DisplaySequence)
	{
		set_Value (COLUMNNAME_DisplaySequence, DisplaySequence);
	}

	/** Get Adress-Druckformat.
		@return Format for printing this Address
	  */
	@Override
	public java.lang.String getDisplaySequence () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DisplaySequence);
	}

	/** Set Local Address Format.
		@param DisplaySequenceLocal 
		Format for printing this Address locally
	  */
	@Override
	public void setDisplaySequenceLocal (java.lang.String DisplaySequenceLocal)
	{
		set_Value (COLUMNNAME_DisplaySequenceLocal, DisplaySequenceLocal);
	}

	/** Get Local Address Format.
		@return Format for printing this Address locally
	  */
	@Override
	public java.lang.String getDisplaySequenceLocal () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DisplaySequenceLocal);
	}

	/** Set Bank Account No Format.
		@param ExpressionBankAccountNo 
		Format of the Bank Account
	  */
	@Override
	public void setExpressionBankAccountNo (java.lang.String ExpressionBankAccountNo)
	{
		set_Value (COLUMNNAME_ExpressionBankAccountNo, ExpressionBankAccountNo);
	}

	/** Get Bank Account No Format.
		@return Format of the Bank Account
	  */
	@Override
	public java.lang.String getExpressionBankAccountNo () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ExpressionBankAccountNo);
	}

	/** Set Bank Routing No Format.
		@param ExpressionBankRoutingNo 
		Format of the Bank Routing Number
	  */
	@Override
	public void setExpressionBankRoutingNo (java.lang.String ExpressionBankRoutingNo)
	{
		set_Value (COLUMNNAME_ExpressionBankRoutingNo, ExpressionBankRoutingNo);
	}

	/** Get Bank Routing No Format.
		@return Format of the Bank Routing Number
	  */
	@Override
	public java.lang.String getExpressionBankRoutingNo () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ExpressionBankRoutingNo);
	}

	/** Set Phone Format.
		@param ExpressionPhone 
		Format of the phone; Can contain fixed format elements, Variables: "_lLoOaAcCa09"
	  */
	@Override
	public void setExpressionPhone (java.lang.String ExpressionPhone)
	{
		set_Value (COLUMNNAME_ExpressionPhone, ExpressionPhone);
	}

	/** Get Phone Format.
		@return Format of the phone; Can contain fixed format elements, Variables: "_lLoOaAcCa09"
	  */
	@Override
	public java.lang.String getExpressionPhone () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ExpressionPhone);
	}

	/** Set Postal Code Format.
		@param ExpressionPostal 
		Format of the postal code; Can contain fixed format elements, Variables: "_lLoOaAcCa09"
	  */
	@Override
	public void setExpressionPostal (java.lang.String ExpressionPostal)
	{
		set_Value (COLUMNNAME_ExpressionPostal, ExpressionPostal);
	}

	/** Get Postal Code Format.
		@return Format of the postal code; Can contain fixed format elements, Variables: "_lLoOaAcCa09"
	  */
	@Override
	public java.lang.String getExpressionPostal () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ExpressionPostal);
	}

	/** Set Additional Postal Format.
		@param ExpressionPostal_Add 
		Format of the value; Can contain fixed format elements, Variables: "_lLoOaAcCa09"
	  */
	@Override
	public void setExpressionPostal_Add (java.lang.String ExpressionPostal_Add)
	{
		set_Value (COLUMNNAME_ExpressionPostal_Add, ExpressionPostal_Add);
	}

	/** Get Additional Postal Format.
		@return Format of the value; Can contain fixed format elements, Variables: "_lLoOaAcCa09"
	  */
	@Override
	public java.lang.String getExpressionPostal_Add () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ExpressionPostal_Add);
	}

	/** Set Additional Postal code.
		@param HasPostal_Add 
		Has Additional Postal Code
	  */
	@Override
	public void setHasPostal_Add (boolean HasPostal_Add)
	{
		set_Value (COLUMNNAME_HasPostal_Add, Boolean.valueOf(HasPostal_Add));
	}

	/** Get Additional Postal code.
		@return Has Additional Postal Code
	  */
	@Override
	public boolean isHasPostal_Add () 
	{
		Object oo = get_Value(COLUMNNAME_HasPostal_Add);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Land hat Regionen.
		@param HasRegion 
		Country contains Regions
	  */
	@Override
	public void setHasRegion (boolean HasRegion)
	{
		set_Value (COLUMNNAME_HasRegion, Boolean.valueOf(HasRegion));
	}

	/** Get Land hat Regionen.
		@return Country contains Regions
	  */
	@Override
	public boolean isHasRegion () 
	{
		Object oo = get_Value(COLUMNNAME_HasRegion);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Reverse Local Address Lines.
		@param IsAddressLinesLocalReverse 
		Print Local Address in reverse Order
	  */
	@Override
	public void setIsAddressLinesLocalReverse (boolean IsAddressLinesLocalReverse)
	{
		set_Value (COLUMNNAME_IsAddressLinesLocalReverse, Boolean.valueOf(IsAddressLinesLocalReverse));
	}

	/** Get Reverse Local Address Lines.
		@return Print Local Address in reverse Order
	  */
	@Override
	public boolean isAddressLinesLocalReverse () 
	{
		Object oo = get_Value(COLUMNNAME_IsAddressLinesLocalReverse);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Reverse Address Lines.
		@param IsAddressLinesReverse 
		Print Address in reverse Order
	  */
	@Override
	public void setIsAddressLinesReverse (boolean IsAddressLinesReverse)
	{
		set_Value (COLUMNNAME_IsAddressLinesReverse, Boolean.valueOf(IsAddressLinesReverse));
	}

	/** Get Reverse Address Lines.
		@return Print Address in reverse Order
	  */
	@Override
	public boolean isAddressLinesReverse () 
	{
		Object oo = get_Value(COLUMNNAME_IsAddressLinesReverse);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Use Postcode Lookup.
		@param IsPostcodeLookup 
		Does this country have a post code web service
	  */
	@Override
	public void setIsPostcodeLookup (boolean IsPostcodeLookup)
	{
		set_Value (COLUMNNAME_IsPostcodeLookup, Boolean.valueOf(IsPostcodeLookup));
	}

	/** Get Use Postcode Lookup.
		@return Does this country have a post code web service
	  */
	@Override
	public boolean isPostcodeLookup () 
	{
		Object oo = get_Value(COLUMNNAME_IsPostcodeLookup);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Lookup ClassName.
		@param LookupClassName 
		The class name of the postcode lookup plugin
	  */
	@Override
	public void setLookupClassName (java.lang.String LookupClassName)
	{
		set_Value (COLUMNNAME_LookupClassName, LookupClassName);
	}

	/** Get Lookup ClassName.
		@return The class name of the postcode lookup plugin
	  */
	@Override
	public java.lang.String getLookupClassName () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_LookupClassName);
	}

	/** Set Lookup Client ID.
		@param LookupClientID 
		The ClientID or Login submitted to the Lookup URL
	  */
	@Override
	public void setLookupClientID (java.lang.String LookupClientID)
	{
		set_Value (COLUMNNAME_LookupClientID, LookupClientID);
	}

	/** Get Lookup Client ID.
		@return The ClientID or Login submitted to the Lookup URL
	  */
	@Override
	public java.lang.String getLookupClientID () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_LookupClientID);
	}

	/** Set Lookup Password.
		@param LookupPassword 
		The password submitted to the Lookup URL
	  */
	@Override
	public void setLookupPassword (java.lang.String LookupPassword)
	{
		set_Value (COLUMNNAME_LookupPassword, LookupPassword);
	}

	/** Get Lookup Password.
		@return The password submitted to the Lookup URL
	  */
	@Override
	public java.lang.String getLookupPassword () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_LookupPassword);
	}

	/** Set Lookup URL.
		@param LookupUrl 
		The URL of the web service that the plugin connects to in order to retrieve postcode data
	  */
	@Override
	public void setLookupUrl (java.lang.String LookupUrl)
	{
		set_Value (COLUMNNAME_LookupUrl, LookupUrl);
	}

	/** Get Lookup URL.
		@return The URL of the web service that the plugin connects to in order to retrieve postcode data
	  */
	@Override
	public java.lang.String getLookupUrl () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_LookupUrl);
	}

	/** Set Media Size.
		@param MediaSize 
		Java Media Size
	  */
	@Override
	public void setMediaSize (java.lang.String MediaSize)
	{
		set_Value (COLUMNNAME_MediaSize, MediaSize);
	}

	/** Get Media Size.
		@return Java Media Size
	  */
	@Override
	public java.lang.String getMediaSize () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_MediaSize);
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