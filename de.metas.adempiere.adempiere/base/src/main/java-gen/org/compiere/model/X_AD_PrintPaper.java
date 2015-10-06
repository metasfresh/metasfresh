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

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;

/** Generated Model for AD_PrintPaper
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_AD_PrintPaper extends PO implements I_AD_PrintPaper, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20090915L;

    /** Standard Constructor */
    public X_AD_PrintPaper (Properties ctx, int AD_PrintPaper_ID, String trxName)
    {
      super (ctx, AD_PrintPaper_ID, trxName);
      /** if (AD_PrintPaper_ID == 0)
        {
			setAD_PrintPaper_ID (0);
			setCode (null);
// iso-a4
			setIsDefault (false);
			setIsLandscape (true);
// Y
			setMarginBottom (0);
// 36
			setMarginLeft (0);
// 36
			setMarginRight (0);
// 36
			setMarginTop (0);
// 36
			setName (null);
        } */
    }

    /** Load Constructor */
    public X_AD_PrintPaper (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_AD_PrintPaper[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Print Paper.
		@param AD_PrintPaper_ID 
		Printer paper definition
	  */
	public void setAD_PrintPaper_ID (int AD_PrintPaper_ID)
	{
		if (AD_PrintPaper_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_PrintPaper_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_PrintPaper_ID, Integer.valueOf(AD_PrintPaper_ID));
	}

	/** Get Print Paper.
		@return Printer paper definition
	  */
	public int getAD_PrintPaper_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_PrintPaper_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Validation code.
		@param Code 
		Validation Code
	  */
	public void setCode (String Code)
	{
		set_Value (COLUMNNAME_Code, Code);
	}

	/** Get Validation code.
		@return Validation Code
	  */
	public String getCode () 
	{
		return (String)get_Value(COLUMNNAME_Code);
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

	/** DimensionUnits AD_Reference_ID=375 */
	public static final int DIMENSIONUNITS_AD_Reference_ID=375;
	/** MM = M */
	public static final String DIMENSIONUNITS_MM = "M";
	/** Inch = I */
	public static final String DIMENSIONUNITS_Inch = "I";
	/** Set Dimension Units.
		@param DimensionUnits 
		Units of Dimension
	  */
	public void setDimensionUnits (String DimensionUnits)
	{

		set_Value (COLUMNNAME_DimensionUnits, DimensionUnits);
	}

	/** Get Dimension Units.
		@return Units of Dimension
	  */
	public String getDimensionUnits () 
	{
		return (String)get_Value(COLUMNNAME_DimensionUnits);
	}

	/** Set Default.
		@param IsDefault 
		Default value
	  */
	public void setIsDefault (boolean IsDefault)
	{
		set_Value (COLUMNNAME_IsDefault, Boolean.valueOf(IsDefault));
	}

	/** Get Default.
		@return Default value
	  */
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

	/** Set Landscape.
		@param IsLandscape 
		Landscape orientation
	  */
	public void setIsLandscape (boolean IsLandscape)
	{
		set_Value (COLUMNNAME_IsLandscape, Boolean.valueOf(IsLandscape));
	}

	/** Get Landscape.
		@return Landscape orientation
	  */
	public boolean isLandscape () 
	{
		Object oo = get_Value(COLUMNNAME_IsLandscape);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Bottom Margin.
		@param MarginBottom 
		Bottom Space in 1/72 inch
	  */
	public void setMarginBottom (int MarginBottom)
	{
		set_Value (COLUMNNAME_MarginBottom, Integer.valueOf(MarginBottom));
	}

	/** Get Bottom Margin.
		@return Bottom Space in 1/72 inch
	  */
	public int getMarginBottom () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_MarginBottom);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Left Margin.
		@param MarginLeft 
		Left Space in 1/72 inch
	  */
	public void setMarginLeft (int MarginLeft)
	{
		set_Value (COLUMNNAME_MarginLeft, Integer.valueOf(MarginLeft));
	}

	/** Get Left Margin.
		@return Left Space in 1/72 inch
	  */
	public int getMarginLeft () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_MarginLeft);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Right Margin.
		@param MarginRight 
		Right Space in 1/72 inch
	  */
	public void setMarginRight (int MarginRight)
	{
		set_Value (COLUMNNAME_MarginRight, Integer.valueOf(MarginRight));
	}

	/** Get Right Margin.
		@return Right Space in 1/72 inch
	  */
	public int getMarginRight () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_MarginRight);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Top Margin.
		@param MarginTop 
		Top Space in 1/72 inch
	  */
	public void setMarginTop (int MarginTop)
	{
		set_Value (COLUMNNAME_MarginTop, Integer.valueOf(MarginTop));
	}

	/** Get Top Margin.
		@return Top Space in 1/72 inch
	  */
	public int getMarginTop () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_MarginTop);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set Size X.
		@param SizeX 
		X (horizontal) dimension size
	  */
	public void setSizeX (BigDecimal SizeX)
	{
		set_Value (COLUMNNAME_SizeX, SizeX);
	}

	/** Get Size X.
		@return X (horizontal) dimension size
	  */
	public BigDecimal getSizeX () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_SizeX);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Size Y.
		@param SizeY 
		Y (vertical) dimension size
	  */
	public void setSizeY (BigDecimal SizeY)
	{
		set_Value (COLUMNNAME_SizeY, SizeY);
	}

	/** Get Size Y.
		@return Y (vertical) dimension size
	  */
	public BigDecimal getSizeY () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_SizeY);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}
}