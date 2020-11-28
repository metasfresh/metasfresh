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

/** Generated Model for M_PromotionDistribution
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_M_PromotionDistribution extends PO implements I_M_PromotionDistribution, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20090915L;

    /** Standard Constructor */
    public X_M_PromotionDistribution (Properties ctx, int M_PromotionDistribution_ID, String trxName)
    {
      super (ctx, M_PromotionDistribution_ID, trxName);
      /** if (M_PromotionDistribution_ID == 0)
        {
			setDistributionType (null);
			setM_PromotionDistribution_ID (0);
			setM_Promotion_ID (0);
			setM_PromotionLine_ID (0);
			setOperation (null);
			setQty (Env.ZERO);
// 0
			setSeqNo (0);
// @SQL=SELECT COALESCE(MAX(SeqNo),0)+10 AS DefaultValue FROM M_PromotionDistribution WHERE M_Promotion_ID=@M_Promotion_ID@
        } */
    }

    /** Load Constructor */
    public X_M_PromotionDistribution (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_M_PromotionDistribution[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** DistributionSorting AD_Reference_ID=53296 */
	public static final int DISTRIBUTIONSORTING_AD_Reference_ID=53296;
	/** Ascending = A */
	public static final String DISTRIBUTIONSORTING_Ascending = "A";
	/** Descending = D */
	public static final String DISTRIBUTIONSORTING_Descending = "D";
	/** Set Distribution Sorting.
		@param DistributionSorting 
		Quantity distribution sorting by unit price
	  */
	public void setDistributionSorting (String DistributionSorting)
	{

		set_Value (COLUMNNAME_DistributionSorting, DistributionSorting);
	}

	/** Get Distribution Sorting.
		@return Quantity distribution sorting by unit price
	  */
	public String getDistributionSorting () 
	{
		return (String)get_Value(COLUMNNAME_DistributionSorting);
	}

	/** DistributionType AD_Reference_ID=53295 */
	public static final int DISTRIBUTIONTYPE_AD_Reference_ID=53295;
	/** Min = I */
	public static final String DISTRIBUTIONTYPE_Min = "I";
	/** Max = X */
	public static final String DISTRIBUTIONTYPE_Max = "X";
	/** Minus = N */
	public static final String DISTRIBUTIONTYPE_Minus = "N";
	/** Set Distribution Type.
		@param DistributionType 
		Type of quantity distribution calculation using comparison qty and order qty as operand
	  */
	public void setDistributionType (String DistributionType)
	{

		set_Value (COLUMNNAME_DistributionType, DistributionType);
	}

	/** Get Distribution Type.
		@return Type of quantity distribution calculation using comparison qty and order qty as operand
	  */
	public String getDistributionType () 
	{
		return (String)get_Value(COLUMNNAME_DistributionType);
	}

	/** Set Promotion Distribution.
		@param M_PromotionDistribution_ID Promotion Distribution	  */
	public void setM_PromotionDistribution_ID (int M_PromotionDistribution_ID)
	{
		if (M_PromotionDistribution_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_PromotionDistribution_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_PromotionDistribution_ID, Integer.valueOf(M_PromotionDistribution_ID));
	}

	/** Get Promotion Distribution.
		@return Promotion Distribution	  */
	public int getM_PromotionDistribution_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_PromotionDistribution_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_M_Promotion getM_Promotion() throws RuntimeException
    {
		return (I_M_Promotion)MTable.get(getCtx(), I_M_Promotion.Table_Name)
			.getPO(getM_Promotion_ID(), get_TrxName());	}

	/** Set Promotion.
		@param M_Promotion_ID Promotion	  */
	public void setM_Promotion_ID (int M_Promotion_ID)
	{
		if (M_Promotion_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Promotion_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Promotion_ID, Integer.valueOf(M_Promotion_ID));
	}

	/** Get Promotion.
		@return Promotion	  */
	public int getM_Promotion_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Promotion_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_M_PromotionLine getM_PromotionLine() throws RuntimeException
    {
		return (I_M_PromotionLine)MTable.get(getCtx(), I_M_PromotionLine.Table_Name)
			.getPO(getM_PromotionLine_ID(), get_TrxName());	}

	/** Set Promotion Line.
		@param M_PromotionLine_ID Promotion Line	  */
	public void setM_PromotionLine_ID (int M_PromotionLine_ID)
	{
		if (M_PromotionLine_ID < 1) 
			set_Value (COLUMNNAME_M_PromotionLine_ID, null);
		else 
			set_Value (COLUMNNAME_M_PromotionLine_ID, Integer.valueOf(M_PromotionLine_ID));
	}

	/** Get Promotion Line.
		@return Promotion Line	  */
	public int getM_PromotionLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_PromotionLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Operation AD_Reference_ID=53294 */
	public static final int OPERATION_AD_Reference_ID=53294;
	/** >= = >= */
	public static final String OPERATION_GtEq = ">=";
	/** <= = <= */
	public static final String OPERATION_LeEq = "<=";
	/** Set Operation.
		@param Operation 
		Compare Operation
	  */
	public void setOperation (String Operation)
	{

		set_Value (COLUMNNAME_Operation, Operation);
	}

	/** Get Operation.
		@return Compare Operation
	  */
	public String getOperation () 
	{
		return (String)get_Value(COLUMNNAME_Operation);
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
}