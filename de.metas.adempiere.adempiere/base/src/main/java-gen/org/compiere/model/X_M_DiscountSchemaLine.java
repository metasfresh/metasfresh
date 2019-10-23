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

/** Generated Model for M_DiscountSchemaLine
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_M_DiscountSchemaLine extends org.compiere.model.PO implements I_M_DiscountSchemaLine, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -1468437129L;

    /** Standard Constructor */
    public X_M_DiscountSchemaLine (Properties ctx, int M_DiscountSchemaLine_ID, String trxName)
    {
      super (ctx, M_DiscountSchemaLine_ID, trxName);
      /** if (M_DiscountSchemaLine_ID == 0)
        {
			setC_ConversionType_ID (0);
			setConversionDate (new Timestamp( System.currentTimeMillis() ));
// @#Date@
			setLimit_AddAmt (Env.ZERO);
			setLimit_Base (null);
// X
			setLimit_Discount (Env.ZERO);
			setLimit_MaxAmt (Env.ZERO);
			setLimit_MinAmt (Env.ZERO);
			setLimit_Rounding (null);
// C
			setList_AddAmt (Env.ZERO);
			setList_Base (null);
// L
			setList_Discount (Env.ZERO);
			setList_MaxAmt (Env.ZERO);
			setList_MinAmt (Env.ZERO);
			setList_Rounding (null);
// C
			setM_DiscountSchema_ID (0);
			setM_DiscountSchemaLine_ID (0);
			setSeqNo (0);
// @SQL=SELECT COALESCE(MAX(SeqNo),0)+10 AS DefaultValue FROM M_DiscountSchemaLine WHERE M_DiscountSchema_ID=@M_DiscountSchema_ID@
			setStd_AddAmt (Env.ZERO);
			setStd_Base (null);
// S
			setStd_Discount (Env.ZERO);
			setStd_MaxAmt (Env.ZERO);
			setStd_MinAmt (Env.ZERO);
			setStd_Rounding (null);
// C
        } */
    }

    /** Load Constructor */
    public X_M_DiscountSchemaLine (Properties ctx, ResultSet rs, String trxName)
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
      StringBuilder sb = new StringBuilder ("X_M_DiscountSchemaLine[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	@Override
	public org.compiere.model.I_C_BPartner getC_BPartner() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_BPartner_ID, org.compiere.model.I_C_BPartner.class);
	}

	@Override
	public void setC_BPartner(org.compiere.model.I_C_BPartner C_BPartner)
	{
		set_ValueFromPO(COLUMNNAME_C_BPartner_ID, org.compiere.model.I_C_BPartner.class, C_BPartner);
	}

	/** Set Geschäftspartner.
		@param C_BPartner_ID 
		Identifies a Business Partner
	  */
	@Override
	public void setC_BPartner_ID (int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_ID, Integer.valueOf(C_BPartner_ID));
	}

	/** Get Geschäftspartner.
		@return Identifies a Business Partner
	  */
	@Override
	public int getC_BPartner_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_ConversionType getC_ConversionType() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_ConversionType_ID, org.compiere.model.I_C_ConversionType.class);
	}

	@Override
	public void setC_ConversionType(org.compiere.model.I_C_ConversionType C_ConversionType)
	{
		set_ValueFromPO(COLUMNNAME_C_ConversionType_ID, org.compiere.model.I_C_ConversionType.class, C_ConversionType);
	}

	/** Set Kursart.
		@param C_ConversionType_ID 
		Currency Conversion Rate Type
	  */
	@Override
	public void setC_ConversionType_ID (int C_ConversionType_ID)
	{
		if (C_ConversionType_ID < 1) 
			set_Value (COLUMNNAME_C_ConversionType_ID, null);
		else 
			set_Value (COLUMNNAME_C_ConversionType_ID, Integer.valueOf(C_ConversionType_ID));
	}

	/** Get Kursart.
		@return Currency Conversion Rate Type
	  */
	@Override
	public int getC_ConversionType_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_ConversionType_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Klassifizierung.
		@param Classification 
		Classification for grouping
	  */
	@Override
	public void setClassification (java.lang.String Classification)
	{
		set_Value (COLUMNNAME_Classification, Classification);
	}

	/** Get Klassifizierung.
		@return Classification for grouping
	  */
	@Override
	public java.lang.String getClassification () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Classification);
	}

	/** Set Konvertierungsdatum.
		@param ConversionDate 
		Date for selecting conversion rate
	  */
	@Override
	public void setConversionDate (java.sql.Timestamp ConversionDate)
	{
		set_Value (COLUMNNAME_ConversionDate, ConversionDate);
	}

	/** Get Konvertierungsdatum.
		@return Date for selecting conversion rate
	  */
	@Override
	public java.sql.Timestamp getConversionDate () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_ConversionDate);
	}

	@Override
	public org.compiere.model.I_C_TaxCategory getC_TaxCategory() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_TaxCategory_ID, org.compiere.model.I_C_TaxCategory.class);
	}

	@Override
	public void setC_TaxCategory(org.compiere.model.I_C_TaxCategory C_TaxCategory)
	{
		set_ValueFromPO(COLUMNNAME_C_TaxCategory_ID, org.compiere.model.I_C_TaxCategory.class, C_TaxCategory);
	}

	/** Set Steuerkategorie.
		@param C_TaxCategory_ID 
		Steuerkategorie
	  */
	@Override
	public void setC_TaxCategory_ID (int C_TaxCategory_ID)
	{
		if (C_TaxCategory_ID < 1) 
			set_Value (COLUMNNAME_C_TaxCategory_ID, null);
		else 
			set_Value (COLUMNNAME_C_TaxCategory_ID, Integer.valueOf(C_TaxCategory_ID));
	}

	/** Get Steuerkategorie.
		@return Steuerkategorie
	  */
	@Override
	public int getC_TaxCategory_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_TaxCategory_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_TaxCategory getC_TaxCategory_Target() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_TaxCategory_Target_ID, org.compiere.model.I_C_TaxCategory.class);
	}

	@Override
	public void setC_TaxCategory_Target(org.compiere.model.I_C_TaxCategory C_TaxCategory_Target)
	{
		set_ValueFromPO(COLUMNNAME_C_TaxCategory_Target_ID, org.compiere.model.I_C_TaxCategory.class, C_TaxCategory_Target);
	}

	/** Set Ziel-Steuerkategorie.
		@param C_TaxCategory_Target_ID Ziel-Steuerkategorie	  */
	@Override
	public void setC_TaxCategory_Target_ID (int C_TaxCategory_Target_ID)
	{
		if (C_TaxCategory_Target_ID < 1) 
			set_Value (COLUMNNAME_C_TaxCategory_Target_ID, null);
		else 
			set_Value (COLUMNNAME_C_TaxCategory_Target_ID, Integer.valueOf(C_TaxCategory_Target_ID));
	}

	/** Get Ziel-Steuerkategorie.
		@return Ziel-Steuerkategorie	  */
	@Override
	public int getC_TaxCategory_Target_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_TaxCategory_Target_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Group1.
		@param Group1 Group1	  */
	@Override
	public void setGroup1 (java.lang.String Group1)
	{
		set_Value (COLUMNNAME_Group1, Group1);
	}

	/** Get Group1.
		@return Group1	  */
	@Override
	public java.lang.String getGroup1 () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Group1);
	}

	/** Set Group2.
		@param Group2 Group2	  */
	@Override
	public void setGroup2 (java.lang.String Group2)
	{
		set_Value (COLUMNNAME_Group2, Group2);
	}

	/** Get Group2.
		@return Group2	  */
	@Override
	public java.lang.String getGroup2 () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Group2);
	}

	/** Set Aufschlag auf Mindestpreis.
		@param Limit_AddAmt 
		Amount added to the converted/copied price before multiplying
	  */
	@Override
	public void setLimit_AddAmt (java.math.BigDecimal Limit_AddAmt)
	{
		set_Value (COLUMNNAME_Limit_AddAmt, Limit_AddAmt);
	}

	/** Get Aufschlag auf Mindestpreis.
		@return Amount added to the converted/copied price before multiplying
	  */
	@Override
	public java.math.BigDecimal getLimit_AddAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Limit_AddAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** 
	 * Limit_Base AD_Reference_ID=194
	 * Reference name: M_DiscountPriceList Base
	 */
	public static final int LIMIT_BASE_AD_Reference_ID=194;
	/** Listenpreis = L */
	public static final String LIMIT_BASE_Listenpreis = "L";
	/** Standardpreis = S */
	public static final String LIMIT_BASE_Standardpreis = "S";
	/** Mindestpreis = X */
	public static final String LIMIT_BASE_Mindestpreis = "X";
	/** Festpreis = F */
	public static final String LIMIT_BASE_Festpreis = "F";
	/** Set Basis Mindestpreis.
		@param Limit_Base 
		Base price for calculation of the new price
	  */
	@Override
	public void setLimit_Base (java.lang.String Limit_Base)
	{

		set_Value (COLUMNNAME_Limit_Base, Limit_Base);
	}

	/** Get Basis Mindestpreis.
		@return Base price for calculation of the new price
	  */
	@Override
	public java.lang.String getLimit_Base () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Limit_Base);
	}

	/** Set Abschlag % auf Mindestpreis.
		@param Limit_Discount 
		Discount in percent to be subtracted from base, if negative it will be added to base price
	  */
	@Override
	public void setLimit_Discount (java.math.BigDecimal Limit_Discount)
	{
		set_Value (COLUMNNAME_Limit_Discount, Limit_Discount);
	}

	/** Get Abschlag % auf Mindestpreis.
		@return Discount in percent to be subtracted from base, if negative it will be added to base price
	  */
	@Override
	public java.math.BigDecimal getLimit_Discount () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Limit_Discount);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Fixed Limit Price.
		@param Limit_Fixed 
		Fixed Limit Price (not calculated)
	  */
	@Override
	public void setLimit_Fixed (java.math.BigDecimal Limit_Fixed)
	{
		set_Value (COLUMNNAME_Limit_Fixed, Limit_Fixed);
	}

	/** Get Fixed Limit Price.
		@return Fixed Limit Price (not calculated)
	  */
	@Override
	public java.math.BigDecimal getLimit_Fixed () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Limit_Fixed);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Limit price max Margin.
		@param Limit_MaxAmt 
		Maximum difference to original limit price; ignored if zero
	  */
	@Override
	public void setLimit_MaxAmt (java.math.BigDecimal Limit_MaxAmt)
	{
		set_Value (COLUMNNAME_Limit_MaxAmt, Limit_MaxAmt);
	}

	/** Get Limit price max Margin.
		@return Maximum difference to original limit price; ignored if zero
	  */
	@Override
	public java.math.BigDecimal getLimit_MaxAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Limit_MaxAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Limit price min Margin.
		@param Limit_MinAmt 
		Minimum difference to original limit price; ignored if zero
	  */
	@Override
	public void setLimit_MinAmt (java.math.BigDecimal Limit_MinAmt)
	{
		set_Value (COLUMNNAME_Limit_MinAmt, Limit_MinAmt);
	}

	/** Get Limit price min Margin.
		@return Minimum difference to original limit price; ignored if zero
	  */
	@Override
	public java.math.BigDecimal getLimit_MinAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Limit_MinAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** 
	 * Limit_Rounding AD_Reference_ID=155
	 * Reference name: M_DiscountPriceList RoundingRule
	 */
	public static final int LIMIT_ROUNDING_AD_Reference_ID=155;
	/** Ganze Zahl .00 = 0 */
	public static final String LIMIT_ROUNDING_GanzeZahl00 = "0";
	/** No Rounding = N */
	public static final String LIMIT_ROUNDING_NoRounding = "N";
	/** Quarter .25 .50 .75 = Q */
	public static final String LIMIT_ROUNDING_Quarter255075 = "Q";
	/** Dime .10, .20, .30, ... = D */
	public static final String LIMIT_ROUNDING_Dime102030 = "D";
	/** Nickel .05, .10, .15, ... = 5 */
	public static final String LIMIT_ROUNDING_Nickel051015 = "5";
	/** Ten 10.00, 20.00, .. = T */
	public static final String LIMIT_ROUNDING_Ten10002000 = "T";
	/** Currency Precision = C */
	public static final String LIMIT_ROUNDING_CurrencyPrecision = "C";
	/** Ending in 9/5 = 9 */
	public static final String LIMIT_ROUNDING_EndingIn95 = "9";
	/** Set Rundung Mindestpreis.
		@param Limit_Rounding 
		Rounding of the final result
	  */
	@Override
	public void setLimit_Rounding (java.lang.String Limit_Rounding)
	{

		set_Value (COLUMNNAME_Limit_Rounding, Limit_Rounding);
	}

	/** Get Rundung Mindestpreis.
		@return Rounding of the final result
	  */
	@Override
	public java.lang.String getLimit_Rounding () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Limit_Rounding);
	}

	/** Set Aufschlag auf Listenpreis.
		@param List_AddAmt 
		List Price Surcharge Amount
	  */
	@Override
	public void setList_AddAmt (java.math.BigDecimal List_AddAmt)
	{
		set_Value (COLUMNNAME_List_AddAmt, List_AddAmt);
	}

	/** Get Aufschlag auf Listenpreis.
		@return List Price Surcharge Amount
	  */
	@Override
	public java.math.BigDecimal getList_AddAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_List_AddAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** 
	 * List_Base AD_Reference_ID=194
	 * Reference name: M_DiscountPriceList Base
	 */
	public static final int LIST_BASE_AD_Reference_ID=194;
	/** Listenpreis = L */
	public static final String LIST_BASE_Listenpreis = "L";
	/** Standardpreis = S */
	public static final String LIST_BASE_Standardpreis = "S";
	/** Mindestpreis = X */
	public static final String LIST_BASE_Mindestpreis = "X";
	/** Festpreis = F */
	public static final String LIST_BASE_Festpreis = "F";
	/** Set Basis Listenpreis.
		@param List_Base 
		Price used as the basis for price list calculations
	  */
	@Override
	public void setList_Base (java.lang.String List_Base)
	{

		set_Value (COLUMNNAME_List_Base, List_Base);
	}

	/** Get Basis Listenpreis.
		@return Price used as the basis for price list calculations
	  */
	@Override
	public java.lang.String getList_Base () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_List_Base);
	}

	/** Set Abschlag % auf Listenpreis.
		@param List_Discount 
		Discount from list price as a percentage
	  */
	@Override
	public void setList_Discount (java.math.BigDecimal List_Discount)
	{
		set_Value (COLUMNNAME_List_Discount, List_Discount);
	}

	/** Get Abschlag % auf Listenpreis.
		@return Discount from list price as a percentage
	  */
	@Override
	public java.math.BigDecimal getList_Discount () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_List_Discount);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Fixed List Price.
		@param List_Fixed 
		Fixes List Price (not calculated)
	  */
	@Override
	public void setList_Fixed (java.math.BigDecimal List_Fixed)
	{
		set_Value (COLUMNNAME_List_Fixed, List_Fixed);
	}

	/** Get Fixed List Price.
		@return Fixes List Price (not calculated)
	  */
	@Override
	public java.math.BigDecimal getList_Fixed () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_List_Fixed);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set List price max Margin.
		@param List_MaxAmt 
		Maximum margin for a product
	  */
	@Override
	public void setList_MaxAmt (java.math.BigDecimal List_MaxAmt)
	{
		set_Value (COLUMNNAME_List_MaxAmt, List_MaxAmt);
	}

	/** Get List price max Margin.
		@return Maximum margin for a product
	  */
	@Override
	public java.math.BigDecimal getList_MaxAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_List_MaxAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set List price min Margin.
		@param List_MinAmt 
		Minimum margin for a product
	  */
	@Override
	public void setList_MinAmt (java.math.BigDecimal List_MinAmt)
	{
		set_Value (COLUMNNAME_List_MinAmt, List_MinAmt);
	}

	/** Get List price min Margin.
		@return Minimum margin for a product
	  */
	@Override
	public java.math.BigDecimal getList_MinAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_List_MinAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** 
	 * List_Rounding AD_Reference_ID=155
	 * Reference name: M_DiscountPriceList RoundingRule
	 */
	public static final int LIST_ROUNDING_AD_Reference_ID=155;
	/** Ganze Zahl .00 = 0 */
	public static final String LIST_ROUNDING_GanzeZahl00 = "0";
	/** No Rounding = N */
	public static final String LIST_ROUNDING_NoRounding = "N";
	/** Quarter .25 .50 .75 = Q */
	public static final String LIST_ROUNDING_Quarter255075 = "Q";
	/** Dime .10, .20, .30, ... = D */
	public static final String LIST_ROUNDING_Dime102030 = "D";
	/** Nickel .05, .10, .15, ... = 5 */
	public static final String LIST_ROUNDING_Nickel051015 = "5";
	/** Ten 10.00, 20.00, .. = T */
	public static final String LIST_ROUNDING_Ten10002000 = "T";
	/** Currency Precision = C */
	public static final String LIST_ROUNDING_CurrencyPrecision = "C";
	/** Ending in 9/5 = 9 */
	public static final String LIST_ROUNDING_EndingIn95 = "9";
	/** Set Rundung Listenpreis.
		@param List_Rounding 
		Rounding rule for final list price
	  */
	@Override
	public void setList_Rounding (java.lang.String List_Rounding)
	{

		set_Value (COLUMNNAME_List_Rounding, List_Rounding);
	}

	/** Get Rundung Listenpreis.
		@return Rounding rule for final list price
	  */
	@Override
	public java.lang.String getList_Rounding () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_List_Rounding);
	}

	@Override
	public org.compiere.model.I_M_DiscountSchema getM_DiscountSchema() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_DiscountSchema_ID, org.compiere.model.I_M_DiscountSchema.class);
	}

	@Override
	public void setM_DiscountSchema(org.compiere.model.I_M_DiscountSchema M_DiscountSchema)
	{
		set_ValueFromPO(COLUMNNAME_M_DiscountSchema_ID, org.compiere.model.I_M_DiscountSchema.class, M_DiscountSchema);
	}

	/** Set Rabatt-Schema.
		@param M_DiscountSchema_ID 
		Schema to calculate the trade discount percentage
	  */
	@Override
	public void setM_DiscountSchema_ID (int M_DiscountSchema_ID)
	{
		if (M_DiscountSchema_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_DiscountSchema_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_DiscountSchema_ID, Integer.valueOf(M_DiscountSchema_ID));
	}

	/** Get Rabatt-Schema.
		@return Schema to calculate the trade discount percentage
	  */
	@Override
	public int getM_DiscountSchema_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_DiscountSchema_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Discount Pricelist.
		@param M_DiscountSchemaLine_ID 
		Line of the pricelist trade discount schema
	  */
	@Override
	public void setM_DiscountSchemaLine_ID (int M_DiscountSchemaLine_ID)
	{
		if (M_DiscountSchemaLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_DiscountSchemaLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_DiscountSchemaLine_ID, Integer.valueOf(M_DiscountSchemaLine_ID));
	}

	/** Get Discount Pricelist.
		@return Line of the pricelist trade discount schema
	  */
	@Override
	public int getM_DiscountSchemaLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_DiscountSchemaLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_M_Product_Category getM_Product_Category() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_Product_Category_ID, org.compiere.model.I_M_Product_Category.class);
	}

	@Override
	public void setM_Product_Category(org.compiere.model.I_M_Product_Category M_Product_Category)
	{
		set_ValueFromPO(COLUMNNAME_M_Product_Category_ID, org.compiere.model.I_M_Product_Category.class, M_Product_Category);
	}

	/** Set Produkt-Kategorie.
		@param M_Product_Category_ID 
		Category of a Product
	  */
	@Override
	public void setM_Product_Category_ID (int M_Product_Category_ID)
	{
		if (M_Product_Category_ID < 1) 
			set_Value (COLUMNNAME_M_Product_Category_ID, null);
		else 
			set_Value (COLUMNNAME_M_Product_Category_ID, Integer.valueOf(M_Product_Category_ID));
	}

	/** Get Produkt-Kategorie.
		@return Category of a Product
	  */
	@Override
	public int getM_Product_Category_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Product_Category_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_M_Product getM_Product() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_Product_ID, org.compiere.model.I_M_Product.class);
	}

	@Override
	public void setM_Product(org.compiere.model.I_M_Product M_Product)
	{
		set_ValueFromPO(COLUMNNAME_M_Product_ID, org.compiere.model.I_M_Product.class, M_Product);
	}

	/** Set Produkt.
		@param M_Product_ID 
		Produkt, Leistung, Artikel
	  */
	@Override
	public void setM_Product_ID (int M_Product_ID)
	{
		if (M_Product_ID < 1) 
			set_Value (COLUMNNAME_M_Product_ID, null);
		else 
			set_Value (COLUMNNAME_M_Product_ID, Integer.valueOf(M_Product_ID));
	}

	/** Get Produkt.
		@return Produkt, Leistung, Artikel
	  */
	@Override
	public int getM_Product_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Product_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Reihenfolge.
		@param SeqNo 
		Method of ordering records; lowest number comes first
	  */
	@Override
	public void setSeqNo (int SeqNo)
	{
		set_Value (COLUMNNAME_SeqNo, Integer.valueOf(SeqNo));
	}

	/** Get Reihenfolge.
		@return Method of ordering records; lowest number comes first
	  */
	@Override
	public int getSeqNo () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_SeqNo);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Aufschlag auf Standardpreis.
		@param Std_AddAmt 
		Amount added to a price as a surcharge
	  */
	@Override
	public void setStd_AddAmt (java.math.BigDecimal Std_AddAmt)
	{
		set_Value (COLUMNNAME_Std_AddAmt, Std_AddAmt);
	}

	/** Get Aufschlag auf Standardpreis.
		@return Amount added to a price as a surcharge
	  */
	@Override
	public java.math.BigDecimal getStd_AddAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Std_AddAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** 
	 * Std_Base AD_Reference_ID=194
	 * Reference name: M_DiscountPriceList Base
	 */
	public static final int STD_BASE_AD_Reference_ID=194;
	/** Listenpreis = L */
	public static final String STD_BASE_Listenpreis = "L";
	/** Standardpreis = S */
	public static final String STD_BASE_Standardpreis = "S";
	/** Mindestpreis = X */
	public static final String STD_BASE_Mindestpreis = "X";
	/** Festpreis = F */
	public static final String STD_BASE_Festpreis = "F";
	/** Set Basis Standardpreis.
		@param Std_Base 
		Base price for calculating new standard price
	  */
	@Override
	public void setStd_Base (java.lang.String Std_Base)
	{

		set_Value (COLUMNNAME_Std_Base, Std_Base);
	}

	/** Get Basis Standardpreis.
		@return Base price for calculating new standard price
	  */
	@Override
	public java.lang.String getStd_Base () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Std_Base);
	}

	/** Set Abschlag % auf Standardpreis.
		@param Std_Discount 
		Discount percentage to subtract from base price
	  */
	@Override
	public void setStd_Discount (java.math.BigDecimal Std_Discount)
	{
		set_Value (COLUMNNAME_Std_Discount, Std_Discount);
	}

	/** Get Abschlag % auf Standardpreis.
		@return Discount percentage to subtract from base price
	  */
	@Override
	public java.math.BigDecimal getStd_Discount () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Std_Discount);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Fixed Standard Price.
		@param Std_Fixed 
		Fixed Standard Price (not calculated)
	  */
	@Override
	public void setStd_Fixed (java.math.BigDecimal Std_Fixed)
	{
		set_Value (COLUMNNAME_Std_Fixed, Std_Fixed);
	}

	/** Get Fixed Standard Price.
		@return Fixed Standard Price (not calculated)
	  */
	@Override
	public java.math.BigDecimal getStd_Fixed () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Std_Fixed);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Standard max Margin.
		@param Std_MaxAmt 
		Maximum margin allowed for a product
	  */
	@Override
	public void setStd_MaxAmt (java.math.BigDecimal Std_MaxAmt)
	{
		set_Value (COLUMNNAME_Std_MaxAmt, Std_MaxAmt);
	}

	/** Get Standard max Margin.
		@return Maximum margin allowed for a product
	  */
	@Override
	public java.math.BigDecimal getStd_MaxAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Std_MaxAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Standard price min Margin.
		@param Std_MinAmt 
		Minimum margin allowed for a product
	  */
	@Override
	public void setStd_MinAmt (java.math.BigDecimal Std_MinAmt)
	{
		set_Value (COLUMNNAME_Std_MinAmt, Std_MinAmt);
	}

	/** Get Standard price min Margin.
		@return Minimum margin allowed for a product
	  */
	@Override
	public java.math.BigDecimal getStd_MinAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Std_MinAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** 
	 * Std_Rounding AD_Reference_ID=155
	 * Reference name: M_DiscountPriceList RoundingRule
	 */
	public static final int STD_ROUNDING_AD_Reference_ID=155;
	/** Ganze Zahl .00 = 0 */
	public static final String STD_ROUNDING_GanzeZahl00 = "0";
	/** No Rounding = N */
	public static final String STD_ROUNDING_NoRounding = "N";
	/** Quarter .25 .50 .75 = Q */
	public static final String STD_ROUNDING_Quarter255075 = "Q";
	/** Dime .10, .20, .30, ... = D */
	public static final String STD_ROUNDING_Dime102030 = "D";
	/** Nickel .05, .10, .15, ... = 5 */
	public static final String STD_ROUNDING_Nickel051015 = "5";
	/** Ten 10.00, 20.00, .. = T */
	public static final String STD_ROUNDING_Ten10002000 = "T";
	/** Currency Precision = C */
	public static final String STD_ROUNDING_CurrencyPrecision = "C";
	/** Ending in 9/5 = 9 */
	public static final String STD_ROUNDING_EndingIn95 = "9";
	/** Set Rundung Standardpreis.
		@param Std_Rounding 
		Rounding rule for calculated price
	  */
	@Override
	public void setStd_Rounding (java.lang.String Std_Rounding)
	{

		set_Value (COLUMNNAME_Std_Rounding, Std_Rounding);
	}

	/** Get Rundung Standardpreis.
		@return Rounding rule for calculated price
	  */
	@Override
	public java.lang.String getStd_Rounding () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Std_Rounding);
	}
}