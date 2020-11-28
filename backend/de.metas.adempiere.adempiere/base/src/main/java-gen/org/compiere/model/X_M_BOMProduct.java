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

/** Generated Model for M_BOMProduct
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_M_BOMProduct extends PO implements I_M_BOMProduct, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20090915L;

    /** Standard Constructor */
    public X_M_BOMProduct (Properties ctx, int M_BOMProduct_ID, String trxName)
    {
      super (ctx, M_BOMProduct_ID, trxName);
      /** if (M_BOMProduct_ID == 0)
        {
			setBOMProductType (null);
// S
			setBOMQty (Env.ZERO);
// 1
			setIsPhantom (false);
			setLeadTimeOffset (0);
			setLine (0);
// @SQL=SELECT NVL(MAX(Line),0)+10 AS DefaultValue FROM M_BOMProduct WHERE M_BOM_ID=@M_BOM_ID@
			setM_BOM_ID (0);
			setM_BOMProduct_ID (0);
        } */
    }

    /** Load Constructor */
    public X_M_BOMProduct (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 3 - Client - Org 
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
      StringBuffer sb = new StringBuffer ("X_M_BOMProduct[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** BOMProductType AD_Reference_ID=349 */
	public static final int BOMPRODUCTTYPE_AD_Reference_ID=349;
	/** Standard Product = S */
	public static final String BOMPRODUCTTYPE_StandardProduct = "S";
	/** Optional Product = O */
	public static final String BOMPRODUCTTYPE_OptionalProduct = "O";
	/** Alternative = A */
	public static final String BOMPRODUCTTYPE_Alternative = "A";
	/** Alternative (Default) = D */
	public static final String BOMPRODUCTTYPE_AlternativeDefault = "D";
	/** Outside Processing = X */
	public static final String BOMPRODUCTTYPE_OutsideProcessing = "X";
	/** Set Component Type.
		@param BOMProductType 
		BOM Product Type
	  */
	public void setBOMProductType (String BOMProductType)
	{

		set_Value (COLUMNNAME_BOMProductType, BOMProductType);
	}

	/** Get Component Type.
		@return BOM Product Type
	  */
	public String getBOMProductType () 
	{
		return (String)get_Value(COLUMNNAME_BOMProductType);
	}

	/** Set BOM Quantity.
		@param BOMQty 
		Bill of Materials Quantity
	  */
	public void setBOMQty (BigDecimal BOMQty)
	{
		set_Value (COLUMNNAME_BOMQty, BOMQty);
	}

	/** Get BOM Quantity.
		@return Bill of Materials Quantity
	  */
	public BigDecimal getBOMQty () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_BOMQty);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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

	/** Set Comment/Help.
		@param Help 
		Comment or Hint
	  */
	public void setHelp (String Help)
	{
		set_Value (COLUMNNAME_Help, Help);
	}

	/** Get Comment/Help.
		@return Comment or Hint
	  */
	public String getHelp () 
	{
		return (String)get_Value(COLUMNNAME_Help);
	}

	/** Set Phantom.
		@param IsPhantom 
		Phantom Component
	  */
	public void setIsPhantom (boolean IsPhantom)
	{
		set_Value (COLUMNNAME_IsPhantom, Boolean.valueOf(IsPhantom));
	}

	/** Get Phantom.
		@return Phantom Component
	  */
	public boolean isPhantom () 
	{
		Object oo = get_Value(COLUMNNAME_IsPhantom);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Lead Time Offset.
		@param LeadTimeOffset 
		Optional Lead Time offest before starting production
	  */
	public void setLeadTimeOffset (int LeadTimeOffset)
	{
		set_Value (COLUMNNAME_LeadTimeOffset, Integer.valueOf(LeadTimeOffset));
	}

	/** Get Lead Time Offset.
		@return Optional Lead Time offest before starting production
	  */
	public int getLeadTimeOffset () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_LeadTimeOffset);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Line No.
		@param Line 
		Unique line for this document
	  */
	public void setLine (int Line)
	{
		set_Value (COLUMNNAME_Line, Integer.valueOf(Line));
	}

	/** Get Line No.
		@return Unique line for this document
	  */
	public int getLine () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Line);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), String.valueOf(getLine()));
    }

	public I_M_AttributeSetInstance getM_AttributeSetInstance() throws RuntimeException
    {
		return (I_M_AttributeSetInstance)MTable.get(getCtx(), I_M_AttributeSetInstance.Table_Name)
			.getPO(getM_AttributeSetInstance_ID(), get_TrxName());	}

	/** Set Attribute Set Instance.
		@param M_AttributeSetInstance_ID 
		Product Attribute Set Instance
	  */
	public void setM_AttributeSetInstance_ID (int M_AttributeSetInstance_ID)
	{
		if (M_AttributeSetInstance_ID < 0) 
			set_Value (COLUMNNAME_M_AttributeSetInstance_ID, null);
		else 
			set_Value (COLUMNNAME_M_AttributeSetInstance_ID, Integer.valueOf(M_AttributeSetInstance_ID));
	}

	/** Get Attribute Set Instance.
		@return Product Attribute Set Instance
	  */
	public int getM_AttributeSetInstance_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_AttributeSetInstance_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_M_BOMAlternative getM_BOMAlternative() throws RuntimeException
    {
		return (I_M_BOMAlternative)MTable.get(getCtx(), I_M_BOMAlternative.Table_Name)
			.getPO(getM_BOMAlternative_ID(), get_TrxName());	}

	/** Set Alternative Group.
		@param M_BOMAlternative_ID 
		Product BOM Alternative Group
	  */
	public void setM_BOMAlternative_ID (int M_BOMAlternative_ID)
	{
		if (M_BOMAlternative_ID < 1) 
			set_Value (COLUMNNAME_M_BOMAlternative_ID, null);
		else 
			set_Value (COLUMNNAME_M_BOMAlternative_ID, Integer.valueOf(M_BOMAlternative_ID));
	}

	/** Get Alternative Group.
		@return Product BOM Alternative Group
	  */
	public int getM_BOMAlternative_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_BOMAlternative_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_M_BOM getM_BOM() throws RuntimeException
    {
		return (I_M_BOM)MTable.get(getCtx(), I_M_BOM.Table_Name)
			.getPO(getM_BOM_ID(), get_TrxName());	}

	/** Set BOM.
		@param M_BOM_ID 
		Bill of Material
	  */
	public void setM_BOM_ID (int M_BOM_ID)
	{
		if (M_BOM_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_BOM_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_BOM_ID, Integer.valueOf(M_BOM_ID));
	}

	/** Get BOM.
		@return Bill of Material
	  */
	public int getM_BOM_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_BOM_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set BOM Component.
		@param M_BOMProduct_ID 
		Bill of Material Component (Product)
	  */
	public void setM_BOMProduct_ID (int M_BOMProduct_ID)
	{
		if (M_BOMProduct_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_BOMProduct_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_BOMProduct_ID, Integer.valueOf(M_BOMProduct_ID));
	}

	/** Get BOM Component.
		@return Bill of Material Component (Product)
	  */
	public int getM_BOMProduct_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_BOMProduct_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_M_ChangeNotice getM_ChangeNotice() throws RuntimeException
    {
		return (I_M_ChangeNotice)MTable.get(getCtx(), I_M_ChangeNotice.Table_Name)
			.getPO(getM_ChangeNotice_ID(), get_TrxName());	}

	/** Set Change Notice.
		@param M_ChangeNotice_ID 
		Bill of Materials (Engineering) Change Notice (Version)
	  */
	public void setM_ChangeNotice_ID (int M_ChangeNotice_ID)
	{
		if (M_ChangeNotice_ID < 1) 
			set_Value (COLUMNNAME_M_ChangeNotice_ID, null);
		else 
			set_Value (COLUMNNAME_M_ChangeNotice_ID, Integer.valueOf(M_ChangeNotice_ID));
	}

	/** Get Change Notice.
		@return Bill of Materials (Engineering) Change Notice (Version)
	  */
	public int getM_ChangeNotice_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_ChangeNotice_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_M_Product getM_ProductBOM() throws RuntimeException
    {
		return (I_M_Product)MTable.get(getCtx(), I_M_Product.Table_Name)
			.getPO(getM_ProductBOM_ID(), get_TrxName());	}

	/** Set BOM Product.
		@param M_ProductBOM_ID 
		Bill of Material Component Product
	  */
	public void setM_ProductBOM_ID (int M_ProductBOM_ID)
	{
		if (M_ProductBOM_ID < 1) 
			set_Value (COLUMNNAME_M_ProductBOM_ID, null);
		else 
			set_Value (COLUMNNAME_M_ProductBOM_ID, Integer.valueOf(M_ProductBOM_ID));
	}

	/** Get BOM Product.
		@return Bill of Material Component Product
	  */
	public int getM_ProductBOM_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_ProductBOM_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_M_ProductOperation getM_ProductOperation() throws RuntimeException
    {
		return (I_M_ProductOperation)MTable.get(getCtx(), I_M_ProductOperation.Table_Name)
			.getPO(getM_ProductOperation_ID(), get_TrxName());	}

	/** Set Product Operation.
		@param M_ProductOperation_ID 
		Product Manufacturing Operation
	  */
	public void setM_ProductOperation_ID (int M_ProductOperation_ID)
	{
		if (M_ProductOperation_ID < 1) 
			set_Value (COLUMNNAME_M_ProductOperation_ID, null);
		else 
			set_Value (COLUMNNAME_M_ProductOperation_ID, Integer.valueOf(M_ProductOperation_ID));
	}

	/** Get Product Operation.
		@return Product Manufacturing Operation
	  */
	public int getM_ProductOperation_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_ProductOperation_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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
}