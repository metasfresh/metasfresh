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

/** Generated Model for M_Forecast
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_M_Forecast extends org.compiere.model.PO implements I_M_Forecast, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 29113697L;

    /** Standard Constructor */
    public X_M_Forecast (Properties ctx, int M_Forecast_ID, String trxName)
    {
      super (ctx, M_Forecast_ID, trxName);
      /** if (M_Forecast_ID == 0)
        {
			setC_Calendar_ID (0);
			setC_Year_ID (0);
			setIsDefault (false);
			setM_Forecast_ID (0);
			setName (null);
        } */
    }

    /** Load Constructor */
    public X_M_Forecast (Properties ctx, ResultSet rs, String trxName)
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
      StringBuilder sb = new StringBuilder ("X_M_Forecast[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	@Override
	public org.compiere.model.I_C_Calendar getC_Calendar() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_Calendar_ID, org.compiere.model.I_C_Calendar.class);
	}

	@Override
	public void setC_Calendar(org.compiere.model.I_C_Calendar C_Calendar)
	{
		set_ValueFromPO(COLUMNNAME_C_Calendar_ID, org.compiere.model.I_C_Calendar.class, C_Calendar);
	}

	/** Set Kalender.
		@param C_Calendar_ID 
		Accounting Calendar Name
	  */
	@Override
	public void setC_Calendar_ID (int C_Calendar_ID)
	{
		if (C_Calendar_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Calendar_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Calendar_ID, Integer.valueOf(C_Calendar_ID));
	}

	/** Get Kalender.
		@return Accounting Calendar Name
	  */
	@Override
	public int getC_Calendar_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Calendar_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_Year getC_Year() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_Year_ID, org.compiere.model.I_C_Year.class);
	}

	@Override
	public void setC_Year(org.compiere.model.I_C_Year C_Year)
	{
		set_ValueFromPO(COLUMNNAME_C_Year_ID, org.compiere.model.I_C_Year.class, C_Year);
	}

	/** Set Jahr.
		@param C_Year_ID 
		Calendar Year
	  */
	@Override
	public void setC_Year_ID (int C_Year_ID)
	{
		if (C_Year_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Year_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Year_ID, Integer.valueOf(C_Year_ID));
	}

	/** Get Jahr.
		@return Calendar Year
	  */
	@Override
	public int getC_Year_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Year_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set Kommentar/Hilfe.
		@param Help 
		Comment or Hint
	  */
	@Override
	public void setHelp (java.lang.String Help)
	{
		set_Value (COLUMNNAME_Help, Help);
	}

	/** Get Kommentar/Hilfe.
		@return Comment or Hint
	  */
	@Override
	public java.lang.String getHelp () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Help);
	}

	/** Set Standard.
		@param IsDefault 
		Default value
	  */
	@Override
	public void setIsDefault (boolean IsDefault)
	{
		set_Value (COLUMNNAME_IsDefault, Boolean.valueOf(IsDefault));
	}

	/** Get Standard.
		@return Default value
	  */
	@Override
	public boolean isDefault () 
	{
		Object oo = get_Value(COLUMNNAME_IsDefault);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Prognose.
		@param M_Forecast_ID 
		Material Forecast
	  */
	@Override
	public void setM_Forecast_ID (int M_Forecast_ID)
	{
		if (M_Forecast_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Forecast_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Forecast_ID, Integer.valueOf(M_Forecast_ID));
	}

	/** Get Prognose.
		@return Material Forecast
	  */
	@Override
	public int getM_Forecast_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Forecast_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_M_PriceList getM_PriceList() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_PriceList_ID, org.compiere.model.I_M_PriceList.class);
	}

	@Override
	public void setM_PriceList(org.compiere.model.I_M_PriceList M_PriceList)
	{
		set_ValueFromPO(COLUMNNAME_M_PriceList_ID, org.compiere.model.I_M_PriceList.class, M_PriceList);
	}

	/** Set Preisliste.
		@param M_PriceList_ID 
		Unique identifier of a Price List
	  */
	@Override
	public void setM_PriceList_ID (int M_PriceList_ID)
	{
		if (M_PriceList_ID < 1) 
			set_Value (COLUMNNAME_M_PriceList_ID, null);
		else 
			set_Value (COLUMNNAME_M_PriceList_ID, Integer.valueOf(M_PriceList_ID));
	}

	/** Get Preisliste.
		@return Unique identifier of a Price List
	  */
	@Override
	public int getM_PriceList_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_PriceList_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set Verarbeiten.
		@param Processing Verarbeiten	  */
	@Override
	public void setProcessing (boolean Processing)
	{
		set_Value (COLUMNNAME_Processing, Boolean.valueOf(Processing));
	}

	/** Get Verarbeiten.
		@return Verarbeiten	  */
	@Override
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
}