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

/** Generated Model for AD_Language
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_AD_Language extends PO implements I_AD_Language, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20090915L;

    /** Standard Constructor */
    public X_AD_Language (Properties ctx, int AD_Language_ID, String trxName)
    {
      super (ctx, AD_Language_ID, trxName);
      /** if (AD_Language_ID == 0)
        {
			setAD_Language (null);
			setAD_Language_ID (0);
// @SQL=SELECT NVL(MAX(AD_Language_ID),0)+1 AS DefaultValue FROM AD_Language
			setIsBaseLanguage (false);
// N
			setIsDecimalPoint (false);
			setIsSystemLanguage (false);
			setName (null);
        } */
    }

    /** Load Constructor */
    public X_AD_Language (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 4 - System 
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
      StringBuffer sb = new StringBuffer ("X_AD_Language[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Language.
		@param AD_Language 
		Language for this entity
	  */
	public void setAD_Language (String AD_Language)
	{
		set_ValueNoCheck (COLUMNNAME_AD_Language, AD_Language);
	}

	/** Get Language.
		@return Language for this entity
	  */
	public String getAD_Language () 
	{
		return (String)get_Value(COLUMNNAME_AD_Language);
	}

	/** Set Language ID.
		@param AD_Language_ID Language ID	  */
	public void setAD_Language_ID (int AD_Language_ID)
	{
		if (AD_Language_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Language_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Language_ID, Integer.valueOf(AD_Language_ID));
	}

	/** Get Language ID.
		@return Language ID	  */
	public int getAD_Language_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Language_ID);
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

	/** Set Date Pattern.
		@param DatePattern 
		Java Date Pattern
	  */
	public void setDatePattern (String DatePattern)
	{
		set_Value (COLUMNNAME_DatePattern, DatePattern);
	}

	/** Get Date Pattern.
		@return Java Date Pattern
	  */
	public String getDatePattern () 
	{
		return (String)get_Value(COLUMNNAME_DatePattern);
	}

	/** Set Base Language.
		@param IsBaseLanguage 
		The system information is maintained in this language
	  */
	public void setIsBaseLanguage (boolean IsBaseLanguage)
	{
		set_ValueNoCheck (COLUMNNAME_IsBaseLanguage, Boolean.valueOf(IsBaseLanguage));
	}

	/** Get Base Language.
		@return The system information is maintained in this language
	  */
	public boolean isBaseLanguage () 
	{
		Object oo = get_Value(COLUMNNAME_IsBaseLanguage);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Decimal Point.
		@param IsDecimalPoint 
		The number notation has a decimal point (no decimal comma)
	  */
	public void setIsDecimalPoint (boolean IsDecimalPoint)
	{
		set_Value (COLUMNNAME_IsDecimalPoint, Boolean.valueOf(IsDecimalPoint));
	}

	/** Get Decimal Point.
		@return The number notation has a decimal point (no decimal comma)
	  */
	public boolean isDecimalPoint () 
	{
		Object oo = get_Value(COLUMNNAME_IsDecimalPoint);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set System Language.
		@param IsSystemLanguage 
		The screens, etc. are maintained in this Language
	  */
	public void setIsSystemLanguage (boolean IsSystemLanguage)
	{
		set_Value (COLUMNNAME_IsSystemLanguage, Boolean.valueOf(IsSystemLanguage));
	}

	/** Get System Language.
		@return The screens, etc. are maintained in this Language
	  */
	public boolean isSystemLanguage () 
	{
		Object oo = get_Value(COLUMNNAME_IsSystemLanguage);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set ISO Language Code.
		@param LanguageISO 
		Lower-case two-letter ISO-3166 code - http://www.ics.uci.edu/pub/ietf/http/related/iso639.txt 
	  */
	public void setLanguageISO (String LanguageISO)
	{
		set_Value (COLUMNNAME_LanguageISO, LanguageISO);
	}

	/** Get ISO Language Code.
		@return Lower-case two-letter ISO-3166 code - http://www.ics.uci.edu/pub/ietf/http/related/iso639.txt 
	  */
	public String getLanguageISO () 
	{
		return (String)get_Value(COLUMNNAME_LanguageISO);
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

	/** Set Process Now.
		@param Processing Process Now	  */
	public void setProcessing (boolean Processing)
	{
		set_Value (COLUMNNAME_Processing, Boolean.valueOf(Processing));
	}

	/** Get Process Now.
		@return Process Now	  */
	public boolean isProcessing () 
	{
		Object oo = get_Value(COLUMNNAME_Processing);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Time Pattern.
		@param TimePattern 
		Java Time Pattern
	  */
	public void setTimePattern (String TimePattern)
	{
		set_Value (COLUMNNAME_TimePattern, TimePattern);
	}

	/** Get Time Pattern.
		@return Java Time Pattern
	  */
	public String getTimePattern () 
	{
		return (String)get_Value(COLUMNNAME_TimePattern);
	}
}