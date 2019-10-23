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

/** Generated Model for C_POSKey
 *  @author Adempiere (generated) 
 *  @version Release 3.6.0LTS - $Id$ */
public class X_C_POSKey extends PO implements I_C_POSKey, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20100614L;

    /** Standard Constructor */
    public X_C_POSKey (Properties ctx, int C_POSKey_ID, String trxName)
    {
      super (ctx, C_POSKey_ID, trxName);
      /** if (C_POSKey_ID == 0)
        {
			setC_POSKey_ID (0);
			setC_POSKeyLayout_ID (0);
			setName (null);
			setSeqNo (0);
// @SQL=SELECT NVL(MAX(SeqNo),0)+10 AS DefaultValue FROM C_POSKey WHERE C_POSKeyLayout_ID=@C_POSKeyLayout_ID@
        } */
    }

    /** Load Constructor */
    public X_C_POSKey (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 2 - Client 
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
      StringBuffer sb = new StringBuffer ("X_C_POSKey[")
        .append(get_ID()).append("]");
      return sb.toString();
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

	public I_AD_PrintColor getAD_PrintColor() throws RuntimeException
    {
		return (I_AD_PrintColor)MTable.get(getCtx(), I_AD_PrintColor.Table_Name)
			.getPO(getAD_PrintColor_ID(), get_TrxName());	}

	/** Set Print Color.
		@param AD_PrintColor_ID 
		Color used for printing and display
	  */
	public void setAD_PrintColor_ID (int AD_PrintColor_ID)
	{
		if (AD_PrintColor_ID < 1) 
			set_Value (COLUMNNAME_AD_PrintColor_ID, null);
		else 
			set_Value (COLUMNNAME_AD_PrintColor_ID, Integer.valueOf(AD_PrintColor_ID));
	}

	/** Get Print Color.
		@return Color used for printing and display
	  */
	public int getAD_PrintColor_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_PrintColor_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_AD_PrintFont getAD_PrintFont() throws RuntimeException
    {
		return (I_AD_PrintFont)MTable.get(getCtx(), I_AD_PrintFont.Table_Name)
			.getPO(getAD_PrintFont_ID(), get_TrxName());	}

	/** Set Print Font.
		@param AD_PrintFont_ID 
		Maintain Print Font
	  */
	public void setAD_PrintFont_ID (int AD_PrintFont_ID)
	{
		if (AD_PrintFont_ID < 1) 
			set_Value (COLUMNNAME_AD_PrintFont_ID, null);
		else 
			set_Value (COLUMNNAME_AD_PrintFont_ID, Integer.valueOf(AD_PrintFont_ID));
	}

	/** Get Print Font.
		@return Maintain Print Font
	  */
	public int getAD_PrintFont_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_PrintFont_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set POS Key.
		@param C_POSKey_ID 
		POS Function Key
	  */
	public void setC_POSKey_ID (int C_POSKey_ID)
	{
		if (C_POSKey_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_POSKey_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_POSKey_ID, Integer.valueOf(C_POSKey_ID));
	}

	/** Get POS Key.
		@return POS Function Key
	  */
	public int getC_POSKey_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_POSKey_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_POSKeyLayout getC_POSKeyLayout() throws RuntimeException
    {
		return (I_C_POSKeyLayout)MTable.get(getCtx(), I_C_POSKeyLayout.Table_Name)
			.getPO(getC_POSKeyLayout_ID(), get_TrxName());	}

	/** Set POS Key Layout.
		@param C_POSKeyLayout_ID 
		POS Function Key Layout
	  */
	public void setC_POSKeyLayout_ID (int C_POSKeyLayout_ID)
	{
		if (C_POSKeyLayout_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_POSKeyLayout_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_POSKeyLayout_ID, Integer.valueOf(C_POSKeyLayout_ID));
	}

	/** Get POS Key Layout.
		@return POS Function Key Layout
	  */
	public int getC_POSKeyLayout_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_POSKeyLayout_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	public I_M_Product getM_Product() throws RuntimeException
    {
		return (I_M_Product)MTable.get(getCtx(), I_M_Product.Table_Name)
			.getPO(getM_Product_ID(), get_TrxName());	}

	/** Set Product.
		@param M_Product_ID 
		Product, Service, Item
	  */
	public void setM_Product_ID (int M_Product_ID)
	{
		if (M_Product_ID < 1) 
			set_Value (COLUMNNAME_M_Product_ID, null);
		else 
			set_Value (COLUMNNAME_M_Product_ID, Integer.valueOf(M_Product_ID));
	}

	/** Get Product.
		@return Product, Service, Item
	  */
	public int getM_Product_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Product_ID);
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

	/** Set Quantity.
		@param Qty 
		Quantity
	  */
	public void setQty (BigDecimal Qty)
	{
		set_Value (COLUMNNAME_Qty, Qty);
	}

	/** Get Quantity.
		@return Quantity
	  */
	public BigDecimal getQty () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Qty);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Sequence.
		@param SeqNo 
		Method of ordering records; lowest number comes first
	  */
	public void setSeqNo (int SeqNo)
	{
		set_Value (COLUMNNAME_SeqNo, Integer.valueOf(SeqNo));
	}

	/** Get Sequence.
		@return Method of ordering records; lowest number comes first
	  */
	public int getSeqNo () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_SeqNo);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Column span.
		@param SpanX 
		Number of columns spanned
	  */
	public void setSpanX (int SpanX)
	{
		set_Value (COLUMNNAME_SpanX, Integer.valueOf(SpanX));
	}

	/** Get Column span.
		@return Number of columns spanned
	  */
	public int getSpanX () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_SpanX);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Row Span.
		@param SpanY 
		Number of rows spanned
	  */
	public void setSpanY (int SpanY)
	{
		set_Value (COLUMNNAME_SpanY, Integer.valueOf(SpanY));
	}

	/** Get Row Span.
		@return Number of rows spanned
	  */
	public int getSpanY () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_SpanY);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_POSKeyLayout getSubKeyLayout() throws RuntimeException
    {
		return (I_C_POSKeyLayout)MTable.get(getCtx(), I_C_POSKeyLayout.Table_Name)
			.getPO(getSubKeyLayout_ID(), get_TrxName());	}

	/** Set Key Layout.
		@param SubKeyLayout_ID 
		Key Layout to be displayed when this key is pressed
	  */
	public void setSubKeyLayout_ID (int SubKeyLayout_ID)
	{
		if (SubKeyLayout_ID < 1) 
			set_Value (COLUMNNAME_SubKeyLayout_ID, null);
		else 
			set_Value (COLUMNNAME_SubKeyLayout_ID, Integer.valueOf(SubKeyLayout_ID));
	}

	/** Get Key Layout.
		@return Key Layout to be displayed when this key is pressed
	  */
	public int getSubKeyLayout_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_SubKeyLayout_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Text.
		@param Text Text	  */
	public void setText (String Text)
	{
		set_Value (COLUMNNAME_Text, Text);
	}

	/** Get Text.
		@return Text	  */
	public String getText () 
	{
		return (String)get_Value(COLUMNNAME_Text);
	}

	@Override
	public I_AD_Reference getAD_Reference() throws RuntimeException
	{
		return (I_AD_Reference)MTable.get(getCtx(), I_AD_Reference.Table_Name)
		.getPO(getAD_Reference_ID(), get_TrxName());	
	}

	@Override
	public int getAD_Reference_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Reference_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public void setAD_Reference_ID(int AD_Reference_ID)
	{
		if (AD_Reference_ID < 1) 
			set_Value (COLUMNNAME_AD_Reference_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Reference_ID, Integer.valueOf(AD_Reference_ID));
	}
}