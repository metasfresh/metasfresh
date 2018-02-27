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
package de.metas.dpd.model;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.model.I_Persistent;
import org.compiere.model.MTable;
import org.compiere.model.PO;
import org.compiere.model.POInfo;

/** Generated Model for DPD_Country
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_DPD_Country extends PO implements I_DPD_Country, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20100128L;

    /** Standard Constructor */
    public X_DPD_Country (Properties ctx, int DPD_Country_ID, String trxName)
    {
      super (ctx, DPD_Country_ID, trxName);
      /** if (DPD_Country_ID == 0)
        {
			setCountryCode (null);
			setCountryCodeLong (null);
			setDPD_Country_ID (0);
			setDPD_FileInfo_ID (0);
			setFlagPostCodeNo (false);
			setNumCountryCode (0);
        } */
    }

    /** Load Constructor */
    public X_DPD_Country (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 7 - System - Client - Org 
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
      StringBuffer sb = new StringBuffer ("X_DPD_Country[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set ISO Laendercode.
		@param CountryCode 
		Zweibuchstabiger ISO Laendercode gemaess ISO 3166-1 - http://www.chemie.fu-berlin.de/diverse/doc/ISO_3166.html
	  */
	public void setCountryCode (String CountryCode)
	{
		set_ValueNoCheck (COLUMNNAME_CountryCode, CountryCode);
	}

	/** Get ISO Laendercode.
		@return Zweibuchstabiger ISO Laendercode gemaess ISO 3166-1 - http://www.chemie.fu-berlin.de/diverse/doc/ISO_3166.html
	  */
	public String getCountryCode () 
	{
		return (String)get_Value(COLUMNNAME_CountryCode);
	}

	/** Set CountryCodeLong.
		@param CountryCodeLong 
		ISO-Alpha3CountryCode
	  */
	public void setCountryCodeLong (String CountryCodeLong)
	{
		set_ValueNoCheck (COLUMNNAME_CountryCodeLong, CountryCodeLong);
	}

	/** Get CountryCodeLong.
		@return ISO-Alpha3CountryCode
	  */
	public String getCountryCodeLong () 
	{
		return (String)get_Value(COLUMNNAME_CountryCodeLong);
	}

	/** Set DPD_Country.
		@param DPD_Country_ID DPD_Country	  */
	public void setDPD_Country_ID (int DPD_Country_ID)
	{
		if (DPD_Country_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_DPD_Country_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_DPD_Country_ID, Integer.valueOf(DPD_Country_ID));
	}

	/** Get DPD_Country.
		@return DPD_Country	  */
	public int getDPD_Country_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DPD_Country_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public de.metas.dpd.model.I_DPD_FileInfo getDPD_FileInfo() throws RuntimeException
    {
		return (de.metas.dpd.model.I_DPD_FileInfo)MTable.get(getCtx(), de.metas.dpd.model.I_DPD_FileInfo.Table_Name)
			.getPO(getDPD_FileInfo_ID(), get_TrxName());	}

	/** Set DPD_FileInfo.
		@param DPD_FileInfo_ID DPD_FileInfo	  */
	public void setDPD_FileInfo_ID (int DPD_FileInfo_ID)
	{
		if (DPD_FileInfo_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_DPD_FileInfo_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_DPD_FileInfo_ID, Integer.valueOf(DPD_FileInfo_ID));
	}

	/** Get DPD_FileInfo.
		@return DPD_FileInfo	  */
	public int getDPD_FileInfo_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DPD_FileInfo_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set FlagPostCodeNo.
		@param FlagPostCodeNo 
		Flags if country has *no* post code system
	  */
	public void setFlagPostCodeNo (boolean FlagPostCodeNo)
	{
		set_ValueNoCheck (COLUMNNAME_FlagPostCodeNo, Boolean.valueOf(FlagPostCodeNo));
	}

	/** Get FlagPostCodeNo.
		@return Flags if country has *no* post code system
	  */
	public boolean isFlagPostCodeNo () 
	{
		Object oo = get_Value(COLUMNNAME_FlagPostCodeNo);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Languages.
		@param Languages Languages	  */
	public void setLanguages (String Languages)
	{
		set_ValueNoCheck (COLUMNNAME_Languages, Languages);
	}

	/** Get Languages.
		@return Languages	  */
	public String getLanguages () 
	{
		return (String)get_Value(COLUMNNAME_Languages);
	}

	/** Set NumCountryCode.
		@param NumCountryCode 
		ISO-3166-NumCountryCode
	  */
	public void setNumCountryCode (int NumCountryCode)
	{
		set_ValueNoCheck (COLUMNNAME_NumCountryCode, Integer.valueOf(NumCountryCode));
	}

	/** Get NumCountryCode.
		@return ISO-3166-NumCountryCode
	  */
	public int getNumCountryCode () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_NumCountryCode);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}
