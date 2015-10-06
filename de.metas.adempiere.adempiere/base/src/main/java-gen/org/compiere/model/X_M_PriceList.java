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

/** Generated Model for M_PriceList
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_M_PriceList extends PO implements I_M_PriceList, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20090915L;

    /** Standard Constructor */
    public X_M_PriceList (Properties ctx, int M_PriceList_ID, String trxName)
    {
      super (ctx, M_PriceList_ID, trxName);
      /** if (M_PriceList_ID == 0)
        {
			setC_Currency_ID (0);
			setEnforcePriceLimit (false);
			setIsDefault (false);
			setIsSOPriceList (false);
			setIsTaxIncluded (false);
			setM_PriceList_ID (0);
			setName (null);
			setPricePrecision (0);
// 2
        } */
    }

    /** Load Constructor */
    public X_M_PriceList (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_M_PriceList[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public I_M_PriceList getBasePriceList() throws RuntimeException
    {
		return (I_M_PriceList)MTable.get(getCtx(), I_M_PriceList.Table_Name)
			.getPO(getBasePriceList_ID(), get_TrxName());	}

	/** Set Base Pricelist.
		@param BasePriceList_ID 
		Pricelist to be used, if product not found on this pricelist
	  */
	public void setBasePriceList_ID (int BasePriceList_ID)
	{
		if (BasePriceList_ID < 1) 
			set_Value (COLUMNNAME_BasePriceList_ID, null);
		else 
			set_Value (COLUMNNAME_BasePriceList_ID, Integer.valueOf(BasePriceList_ID));
	}

	/** Get Base Pricelist.
		@return Pricelist to be used, if product not found on this pricelist
	  */
	public int getBasePriceList_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_BasePriceList_ID);
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

	/** Set Enforce price limit.
		@param EnforcePriceLimit 
		Do not allow prices below the limit price
	  */
	public void setEnforcePriceLimit (boolean EnforcePriceLimit)
	{
		set_Value (COLUMNNAME_EnforcePriceLimit, Boolean.valueOf(EnforcePriceLimit));
	}

	/** Get Enforce price limit.
		@return Do not allow prices below the limit price
	  */
	public boolean isEnforcePriceLimit () 
	{
		Object oo = get_Value(COLUMNNAME_EnforcePriceLimit);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
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

	/** Set Mandatory.
		@param IsMandatory 
		Data entry is required in this column
	  */
	public void setIsMandatory (boolean IsMandatory)
	{
		set_Value (COLUMNNAME_IsMandatory, Boolean.valueOf(IsMandatory));
	}

	/** Get Mandatory.
		@return Data entry is required in this column
	  */
	public boolean isMandatory () 
	{
		Object oo = get_Value(COLUMNNAME_IsMandatory);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set isPresentForProduct.
		@param isPresentForProduct isPresentForProduct	  */
	public void setIsPresentForProduct (boolean IsPresentForProduct)
	{
		set_Value (COLUMNNAME_IsPresentForProduct, Boolean.valueOf(IsPresentForProduct));
	}

	/** Get isPresentForProduct.
		@return isPresentForProduct	  */
	public boolean isPresentForProduct () 
	{
		Object oo = get_Value(COLUMNNAME_IsPresentForProduct);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Sales Price list.
		@param IsSOPriceList 
		This is a Sales Price List
	  */
	public void setIsSOPriceList (boolean IsSOPriceList)
	{
		set_Value (COLUMNNAME_IsSOPriceList, Boolean.valueOf(IsSOPriceList));
	}

	/** Get Sales Price list.
		@return This is a Sales Price List
	  */
	public boolean isSOPriceList () 
	{
		Object oo = get_Value(COLUMNNAME_IsSOPriceList);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Price includes Tax.
		@param IsTaxIncluded 
		Tax is included in the price 
	  */
	public void setIsTaxIncluded (boolean IsTaxIncluded)
	{
		set_Value (COLUMNNAME_IsTaxIncluded, Boolean.valueOf(IsTaxIncluded));
	}

	/** Get Price includes Tax.
		@return Tax is included in the price 
	  */
	public boolean isTaxIncluded () 
	{
		Object oo = get_Value(COLUMNNAME_IsTaxIncluded);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Price List.
		@param M_PriceList_ID 
		Unique identifier of a Price List
	  */
	public void setM_PriceList_ID (int M_PriceList_ID)
	{
		if (M_PriceList_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_PriceList_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_PriceList_ID, Integer.valueOf(M_PriceList_ID));
	}

	/** Get Price List.
		@return Unique identifier of a Price List
	  */
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

	/** Set Price Precision.
		@param PricePrecision 
		Precision (number of decimals) for the Price
	  */
	public void setPricePrecision (int PricePrecision)
	{
		set_Value (COLUMNNAME_PricePrecision, Integer.valueOf(PricePrecision));
	}

	/** Get Price Precision.
		@return Precision (number of decimals) for the Price
	  */
	public int getPricePrecision () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PricePrecision);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}