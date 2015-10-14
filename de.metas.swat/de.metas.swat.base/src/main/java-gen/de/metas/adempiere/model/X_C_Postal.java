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
package de.metas.adempiere.model;

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

/** Generated Model for C_Postal
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a#216 - $Id$ */
@SuppressWarnings("javadoc")
public class X_C_Postal extends org.compiere.model.PO implements I_C_Postal, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -1944348541L;

    /** Standard Constructor */
    public X_C_Postal (Properties ctx, int C_Postal_ID, String trxName)
    {
      super (ctx, C_Postal_ID, trxName);
      /** if (C_Postal_ID == 0)
        {
			setC_Country_ID (0);
			setC_Postal_ID (0);
			setIsManual (false);
// N
			setNonStdAddress (false);
// N
        } */
    }

    /** Load Constructor */
    public X_C_Postal (Properties ctx, ResultSet rs, String trxName)
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
    public String toString()
    {
      StringBuffer sb = new StringBuffer ("X_C_Postal[")
        .append(get_ID()).append("]");
      return sb.toString();
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
		Ort
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
		@return Ort
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

	/** Set Postal codes.
		@param C_Postal_ID Postal codes	  */
	@Override
	public void setC_Postal_ID (int C_Postal_ID)
	{
		if (C_Postal_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Postal_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Postal_ID, Integer.valueOf(C_Postal_ID));
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

	/** Set Bezirk.
		@param District Bezirk	  */
	@Override
	public void setDistrict (java.lang.String District)
	{
		set_Value (COLUMNNAME_District, District);
	}

	/** Get Bezirk.
		@return Bezirk	  */
	@Override
	public java.lang.String getDistrict () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_District);
	}

	/** Set Manuell.
		@param IsManual 
		Dies ist ein manueller Vorgang
	  */
	@Override
	public void setIsManual (boolean IsManual)
	{
		set_Value (COLUMNNAME_IsManual, Boolean.valueOf(IsManual));
	}

	/** Get Manuell.
		@return Dies ist ein manueller Vorgang
	  */
	@Override
	public boolean isManual () 
	{
		Object oo = get_Value(COLUMNNAME_IsManual);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set DPD Validated.
		@param IsValidDPD 
		Record was validated on DPD database
	  */
	@Override
	public void setIsValidDPD (boolean IsValidDPD)
	{
		set_Value (COLUMNNAME_IsValidDPD, Boolean.valueOf(IsValidDPD));
	}

	/** Get DPD Validated.
		@return Record was validated on DPD database
	  */
	@Override
	public boolean isValidDPD () 
	{
		Object oo = get_Value(COLUMNNAME_IsValidDPD);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Bereinigung notwendig.
		@param NonStdAddress Bereinigung notwendig	  */
	@Override
	public void setNonStdAddress (boolean NonStdAddress)
	{
		set_Value (COLUMNNAME_NonStdAddress, Boolean.valueOf(NonStdAddress));
	}

	/** Get Bereinigung notwendig.
		@return Bereinigung notwendig	  */
	@Override
	public boolean isNonStdAddress () 
	{
		Object oo = get_Value(COLUMNNAME_NonStdAddress);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
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

	/** Set Gemeinde.
		@param Township Gemeinde	  */
	@Override
	public void setTownship (java.lang.String Township)
	{
		set_Value (COLUMNNAME_Township, Township);
	}

	/** Get Gemeinde.
		@return Gemeinde	  */
	@Override
	public java.lang.String getTownship () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Township);
	}
}
