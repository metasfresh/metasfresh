/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2007 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software, you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY, without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program, if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.util.KeyNamePair;

/** Generated Model for C_Country
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_C_Country extends PO implements I_C_Country, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20090915L;

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

    /** AccessLevel
      * @return 6 - System - Client 
      */
    protected int get_AccessLevel()
    {
      return accessLevel.intValue();
    }

    /** Load Meta Data */
    protected POInfo initPO (Properties ctx)
    {
      POInfo poi = POInfo.getPOInfo (ctx, Table_ID, get_TrxName());
      return poi;
    }

    public String toString()
    {
      StringBuffer sb = new StringBuffer ("X_C_Country[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** AD_Language AD_Reference_ID=106 */
	public static final int AD_LANGUAGE_AD_Reference_ID=106;
	/** Set Language.
		@param AD_Language 
		Language for this entity
	  */
	public void setAD_Language (String AD_Language)
	{

		set_Value (COLUMNNAME_AD_Language, AD_Language);
	}

	/** Get Language.
		@return Language for this entity
	  */
	public String getAD_Language () 
	{
		return (String)get_Value(COLUMNNAME_AD_Language);
	}

	/** Set AllowCitiesOutOfList.
		@param AllowCitiesOutOfList 
		A flag which tells if a country accept or not new cities
	  */
	public void setAllowCitiesOutOfList (boolean AllowCitiesOutOfList)
	{
		set_Value (COLUMNNAME_AllowCitiesOutOfList, Boolean.valueOf(AllowCitiesOutOfList));
	}

	/** Get AllowCitiesOutOfList.
		@return A flag which tells if a country accept or not new cities
	  */
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

	/** Set CaptureSequence.
		@param CaptureSequence CaptureSequence	  */
	public void setCaptureSequence (String CaptureSequence)
	{
		set_Value (COLUMNNAME_CaptureSequence, CaptureSequence);
	}

	/** Get CaptureSequence.
		@return CaptureSequence	  */
	public String getCaptureSequence () 
	{
		return (String)get_Value(COLUMNNAME_CaptureSequence);
	}

	/** Set Country.
		@param C_Country_ID 
		Country 
	  */
	public void setC_Country_ID (int C_Country_ID)
	{
		if (C_Country_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Country_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Country_ID, Integer.valueOf(C_Country_ID));
	}

	/** Get Country.
		@return Country 
	  */
	public int getC_Country_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Country_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_Currency getC_Currency() throws RuntimeException
    {
		return (I_C_Currency)MTable.get(getCtx(), I_C_Currency.Table_Name)
			.getPO(getC_Currency_ID(), get_TrxName());	}

	/** Set Currency.
		@param C_Currency_ID 
		The Currency for this record
	  */
	public void setC_Currency_ID (int C_Currency_ID)
	{
		if (C_Currency_ID < 1) 
			set_Value (COLUMNNAME_C_Currency_ID, null);
		else 
			set_Value (COLUMNNAME_C_Currency_ID, Integer.valueOf(C_Currency_ID));
	}

	/** Get Currency.
		@return The Currency for this record
	  */
	public int getC_Currency_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Currency_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set ISO Country Code.
		@param CountryCode 
		Upper-case two-letter alphanumeric ISO Country code according to ISO 3166-1 - http://www.chemie.fu-berlin.de/diverse/doc/ISO_3166.html
	  */
	public void setCountryCode (String CountryCode)
	{
		set_Value (COLUMNNAME_CountryCode, CountryCode);
	}

	/** Get ISO Country Code.
		@return Upper-case two-letter alphanumeric ISO Country code according to ISO 3166-1 - http://www.chemie.fu-berlin.de/diverse/doc/ISO_3166.html
	  */
	public String getCountryCode () 
	{
		return (String)get_Value(COLUMNNAME_CountryCode);
	}

	/** Set Description.
		@param Description 
		Optional short description of the record
	  */
	public void setDescription (String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	/** Get Description.
		@return Optional short description of the record
	  */
	public String getDescription () 
	{
		return (String)get_Value(COLUMNNAME_Description);
	}

	/** Set Address Print Format.
		@param DisplaySequence 
		Format for printing this Address
	  */
	public void setDisplaySequence (String DisplaySequence)
	{
		set_Value (COLUMNNAME_DisplaySequence, DisplaySequence);
	}

	/** Get Address Print Format.
		@return Format for printing this Address
	  */
	public String getDisplaySequence () 
	{
		return (String)get_Value(COLUMNNAME_DisplaySequence);
	}

	/** Set Local Address Format.
		@param DisplaySequenceLocal 
		Format for printing this Address locally
	  */
	public void setDisplaySequenceLocal (String DisplaySequenceLocal)
	{
		set_Value (COLUMNNAME_DisplaySequenceLocal, DisplaySequenceLocal);
	}

	/** Get Local Address Format.
		@return Format for printing this Address locally
	  */
	public String getDisplaySequenceLocal () 
	{
		return (String)get_Value(COLUMNNAME_DisplaySequenceLocal);
	}

	/** Set Bank Account No Format.
		@param ExpressionBankAccountNo 
		Format of the Bank Account
	  */
	public void setExpressionBankAccountNo (String ExpressionBankAccountNo)
	{
		set_Value (COLUMNNAME_ExpressionBankAccountNo, ExpressionBankAccountNo);
	}

	/** Get Bank Account No Format.
		@return Format of the Bank Account
	  */
	public String getExpressionBankAccountNo () 
	{
		return (String)get_Value(COLUMNNAME_ExpressionBankAccountNo);
	}

	/** Set Bank Routing No Format.
		@param ExpressionBankRoutingNo 
		Format of the Bank Routing Number
	  */
	public void setExpressionBankRoutingNo (String ExpressionBankRoutingNo)
	{
		set_Value (COLUMNNAME_ExpressionBankRoutingNo, ExpressionBankRoutingNo);
	}

	/** Get Bank Routing No Format.
		@return Format of the Bank Routing Number
	  */
	public String getExpressionBankRoutingNo () 
	{
		return (String)get_Value(COLUMNNAME_ExpressionBankRoutingNo);
	}

	/** Set Phone Format.
		@param ExpressionPhone 
		Format of the phone; Can contain fixed format elements, Variables: "_lLoOaAcCa09"
	  */
	public void setExpressionPhone (String ExpressionPhone)
	{
		set_Value (COLUMNNAME_ExpressionPhone, ExpressionPhone);
	}

	/** Get Phone Format.
		@return Format of the phone; Can contain fixed format elements, Variables: "_lLoOaAcCa09"
	  */
	public String getExpressionPhone () 
	{
		return (String)get_Value(COLUMNNAME_ExpressionPhone);
	}

	/** Set Postal Code Format.
		@param ExpressionPostal 
		Format of the postal code; Can contain fixed format elements, Variables: "_lLoOaAcCa09"
	  */
	public void setExpressionPostal (String ExpressionPostal)
	{
		set_Value (COLUMNNAME_ExpressionPostal, ExpressionPostal);
	}

	/** Get Postal Code Format.
		@return Format of the postal code; Can contain fixed format elements, Variables: "_lLoOaAcCa09"
	  */
	public String getExpressionPostal () 
	{
		return (String)get_Value(COLUMNNAME_ExpressionPostal);
	}

	/** Set Additional Postal Format.
		@param ExpressionPostal_Add 
		Format of the value; Can contain fixed format elements, Variables: "_lLoOaAcCa09"
	  */
	public void setExpressionPostal_Add (String ExpressionPostal_Add)
	{
		set_Value (COLUMNNAME_ExpressionPostal_Add, ExpressionPostal_Add);
	}

	/** Get Additional Postal Format.
		@return Format of the value; Can contain fixed format elements, Variables: "_lLoOaAcCa09"
	  */
	public String getExpressionPostal_Add () 
	{
		return (String)get_Value(COLUMNNAME_ExpressionPostal_Add);
	}

	/** Set Additional Postal code.
		@param HasPostal_Add 
		Has Additional Postal Code
	  */
	public void setHasPostal_Add (boolean HasPostal_Add)
	{
		set_Value (COLUMNNAME_HasPostal_Add, Boolean.valueOf(HasPostal_Add));
	}

	/** Get Additional Postal code.
		@return Has Additional Postal Code
	  */
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

	/** Set Country has Region.
		@param HasRegion 
		Country contains Regions
	  */
	public void setHasRegion (boolean HasRegion)
	{
		set_Value (COLUMNNAME_HasRegion, Boolean.valueOf(HasRegion));
	}

	/** Get Country has Region.
		@return Country contains Regions
	  */
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
	public void setIsAddressLinesLocalReverse (boolean IsAddressLinesLocalReverse)
	{
		set_Value (COLUMNNAME_IsAddressLinesLocalReverse, Boolean.valueOf(IsAddressLinesLocalReverse));
	}

	/** Get Reverse Local Address Lines.
		@return Print Local Address in reverse Order
	  */
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
	public void setIsAddressLinesReverse (boolean IsAddressLinesReverse)
	{
		set_Value (COLUMNNAME_IsAddressLinesReverse, Boolean.valueOf(IsAddressLinesReverse));
	}

	/** Get Reverse Address Lines.
		@return Print Address in reverse Order
	  */
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

	/** Set IsPostcodeLookup.
		@param IsPostcodeLookup 
		Does this country have a post code web service
	  */
	public void setIsPostcodeLookup (boolean IsPostcodeLookup)
	{
		set_Value (COLUMNNAME_IsPostcodeLookup, Boolean.valueOf(IsPostcodeLookup));
	}

	/** Get IsPostcodeLookup.
		@return Does this country have a post code web service
	  */
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

	/** Set LookupClassName.
		@param LookupClassName 
		The class name of the postcode lookup plugin
	  */
	public void setLookupClassName (String LookupClassName)
	{
		set_Value (COLUMNNAME_LookupClassName, LookupClassName);
	}

	/** Get LookupClassName.
		@return The class name of the postcode lookup plugin
	  */
	public String getLookupClassName () 
	{
		return (String)get_Value(COLUMNNAME_LookupClassName);
	}

	/** Set LookupClientID.
		@param LookupClientID 
		The ClientID or Login submitted to the Lookup URL
	  */
	public void setLookupClientID (String LookupClientID)
	{
		set_Value (COLUMNNAME_LookupClientID, LookupClientID);
	}

	/** Get LookupClientID.
		@return The ClientID or Login submitted to the Lookup URL
	  */
	public String getLookupClientID () 
	{
		return (String)get_Value(COLUMNNAME_LookupClientID);
	}

	/** Set LookupPassword.
		@param LookupPassword 
		The password submitted to the Lookup URL
	  */
	public void setLookupPassword (String LookupPassword)
	{
		set_Value (COLUMNNAME_LookupPassword, LookupPassword);
	}

	/** Get LookupPassword.
		@return The password submitted to the Lookup URL
	  */
	public String getLookupPassword () 
	{
		return (String)get_Value(COLUMNNAME_LookupPassword);
	}

	/** Set LookupUrl.
		@param LookupUrl 
		The URL of the web service that the plugin connects to in order to retrieve postcode data
	  */
	public void setLookupUrl (String LookupUrl)
	{
		set_Value (COLUMNNAME_LookupUrl, LookupUrl);
	}

	/** Get LookupUrl.
		@return The URL of the web service that the plugin connects to in order to retrieve postcode data
	  */
	public String getLookupUrl () 
	{
		return (String)get_Value(COLUMNNAME_LookupUrl);
	}

	/** Set Media Size.
		@param MediaSize 
		Java Media Size
	  */
	public void setMediaSize (String MediaSize)
	{
		set_Value (COLUMNNAME_MediaSize, MediaSize);
	}

	/** Get Media Size.
		@return Java Media Size
	  */
	public String getMediaSize () 
	{
		return (String)get_Value(COLUMNNAME_MediaSize);
	}

	/** Set Name.
		@param Name 
		Alphanumeric identifier of the entity
	  */
	public void setName (String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	/** Get Name.
		@return Alphanumeric identifier of the entity
	  */
	public String getName () 
	{
		return (String)get_Value(COLUMNNAME_Name);
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), getName());
    }

	/** Set Region.
		@param RegionName 
		Name of the Region
	  */
	public void setRegionName (String RegionName)
	{
		set_Value (COLUMNNAME_RegionName, RegionName);
	}

	/** Get Region.
		@return Name of the Region
	  */
	public String getRegionName () 
	{
		return (String)get_Value(COLUMNNAME_RegionName);
	}
}