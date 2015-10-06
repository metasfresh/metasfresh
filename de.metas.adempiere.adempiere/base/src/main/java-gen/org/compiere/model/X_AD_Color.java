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

/** Generated Model for AD_Color
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_AD_Color extends PO implements I_AD_Color, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20090915L;

    /** Standard Constructor */
    public X_AD_Color (Properties ctx, int AD_Color_ID, String trxName)
    {
      super (ctx, AD_Color_ID, trxName);
      /** if (AD_Color_ID == 0)
        {
			setAD_Color_ID (0);
			setAlpha (0);
			setBlue (0);
			setColorType (null);
			setGreen (0);
			setImageAlpha (Env.ZERO);
			setIsDefault (false);
			setName (null);
			setRed (0);
        } */
    }

    /** Load Constructor */
    public X_AD_Color (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_AD_Color[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set System Color.
		@param AD_Color_ID 
		Color for backgrounds or indicators
	  */
	public void setAD_Color_ID (int AD_Color_ID)
	{
		if (AD_Color_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Color_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Color_ID, Integer.valueOf(AD_Color_ID));
	}

	/** Get System Color.
		@return Color for backgrounds or indicators
	  */
	public int getAD_Color_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Color_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_AD_Image getAD_Image() throws RuntimeException
    {
		return (I_AD_Image)MTable.get(getCtx(), I_AD_Image.Table_Name)
			.getPO(getAD_Image_ID(), get_TrxName());	}

	/** Set Image.
		@param AD_Image_ID 
		Image or Icon
	  */
	public void setAD_Image_ID (int AD_Image_ID)
	{
		if (AD_Image_ID < 1) 
			set_Value (COLUMNNAME_AD_Image_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Image_ID, Integer.valueOf(AD_Image_ID));
	}

	/** Get Image.
		@return Image or Icon
	  */
	public int getAD_Image_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Image_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Alpha.
		@param Alpha 
		Color Alpha value 0-255
	  */
	public void setAlpha (int Alpha)
	{
		set_Value (COLUMNNAME_Alpha, Integer.valueOf(Alpha));
	}

	/** Get Alpha.
		@return Color Alpha value 0-255
	  */
	public int getAlpha () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Alpha);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set 2nd Alpha.
		@param Alpha_1 
		Alpha value for second color
	  */
	public void setAlpha_1 (int Alpha_1)
	{
		set_Value (COLUMNNAME_Alpha_1, Integer.valueOf(Alpha_1));
	}

	/** Get 2nd Alpha.
		@return Alpha value for second color
	  */
	public int getAlpha_1 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Alpha_1);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Blue.
		@param Blue 
		Color RGB blue value
	  */
	public void setBlue (int Blue)
	{
		set_Value (COLUMNNAME_Blue, Integer.valueOf(Blue));
	}

	/** Get Blue.
		@return Color RGB blue value
	  */
	public int getBlue () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Blue);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set 2nd Blue.
		@param Blue_1 
		RGB value for second color
	  */
	public void setBlue_1 (int Blue_1)
	{
		set_Value (COLUMNNAME_Blue_1, Integer.valueOf(Blue_1));
	}

	/** Get 2nd Blue.
		@return RGB value for second color
	  */
	public int getBlue_1 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Blue_1);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** ColorType AD_Reference_ID=243 */
	public static final int COLORTYPE_AD_Reference_ID=243;
	/** Normal (Flat) = F */
	public static final String COLORTYPE_NormalFlat = "F";
	/** Gradient = G */
	public static final String COLORTYPE_Gradient = "G";
	/** Line = L */
	public static final String COLORTYPE_Line = "L";
	/** Texture (Picture) = T */
	public static final String COLORTYPE_TexturePicture = "T";
	/** Set Color Type.
		@param ColorType 
		Color presentation for this color
	  */
	public void setColorType (String ColorType)
	{

		set_Value (COLUMNNAME_ColorType, ColorType);
	}

	/** Get Color Type.
		@return Color presentation for this color
	  */
	public String getColorType () 
	{
		return (String)get_Value(COLUMNNAME_ColorType);
	}

	/** Set Green.
		@param Green 
		RGB value 
	  */
	public void setGreen (int Green)
	{
		set_Value (COLUMNNAME_Green, Integer.valueOf(Green));
	}

	/** Get Green.
		@return RGB value 
	  */
	public int getGreen () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Green);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set 2nd Green.
		@param Green_1 
		RGB value for second color
	  */
	public void setGreen_1 (int Green_1)
	{
		set_Value (COLUMNNAME_Green_1, Integer.valueOf(Green_1));
	}

	/** Get 2nd Green.
		@return RGB value for second color
	  */
	public int getGreen_1 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Green_1);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Image Alpha .
		@param ImageAlpha 
		Image Texture Composite Alpha
	  */
	public void setImageAlpha (BigDecimal ImageAlpha)
	{
		set_Value (COLUMNNAME_ImageAlpha, ImageAlpha);
	}

	/** Get Image Alpha .
		@return Image Texture Composite Alpha
	  */
	public BigDecimal getImageAlpha () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ImageAlpha);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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

	/** Set Line Distance.
		@param LineDistance 
		Distance between lines
	  */
	public void setLineDistance (int LineDistance)
	{
		set_Value (COLUMNNAME_LineDistance, Integer.valueOf(LineDistance));
	}

	/** Get Line Distance.
		@return Distance between lines
	  */
	public int getLineDistance () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_LineDistance);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Line Width.
		@param LineWidth 
		Width of the lines
	  */
	public void setLineWidth (int LineWidth)
	{
		set_Value (COLUMNNAME_LineWidth, Integer.valueOf(LineWidth));
	}

	/** Get Line Width.
		@return Width of the lines
	  */
	public int getLineWidth () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_LineWidth);
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

	/** Set Red.
		@param Red 
		RGB value
	  */
	public void setRed (int Red)
	{
		set_Value (COLUMNNAME_Red, Integer.valueOf(Red));
	}

	/** Get Red.
		@return RGB value
	  */
	public int getRed () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Red);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set 2nd Red.
		@param Red_1 
		RGB value for second color
	  */
	public void setRed_1 (int Red_1)
	{
		set_Value (COLUMNNAME_Red_1, Integer.valueOf(Red_1));
	}

	/** Get 2nd Red.
		@return RGB value for second color
	  */
	public int getRed_1 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Red_1);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Repeat Distance.
		@param RepeatDistance 
		Distance in points to repeat gradient color - or zero
	  */
	public void setRepeatDistance (int RepeatDistance)
	{
		set_Value (COLUMNNAME_RepeatDistance, Integer.valueOf(RepeatDistance));
	}

	/** Get Repeat Distance.
		@return Distance in points to repeat gradient color - or zero
	  */
	public int getRepeatDistance () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_RepeatDistance);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** StartPoint AD_Reference_ID=248 */
	public static final int STARTPOINT_AD_Reference_ID=248;
	/** North = 1 */
	public static final String STARTPOINT_North = "1";
	/** North East = 2 */
	public static final String STARTPOINT_NorthEast = "2";
	/** East = 3 */
	public static final String STARTPOINT_East = "3";
	/** South East = 4 */
	public static final String STARTPOINT_SouthEast = "4";
	/** South = 5 */
	public static final String STARTPOINT_South = "5";
	/** South West = 6 */
	public static final String STARTPOINT_SouthWest = "6";
	/** West = 7 */
	public static final String STARTPOINT_West = "7";
	/** North West = 8 */
	public static final String STARTPOINT_NorthWest = "8";
	/** Set Start Point.
		@param StartPoint 
		Start point of the gradient colors
	  */
	public void setStartPoint (String StartPoint)
	{

		set_Value (COLUMNNAME_StartPoint, StartPoint);
	}

	/** Get Start Point.
		@return Start point of the gradient colors
	  */
	public String getStartPoint () 
	{
		return (String)get_Value(COLUMNNAME_StartPoint);
	}
}