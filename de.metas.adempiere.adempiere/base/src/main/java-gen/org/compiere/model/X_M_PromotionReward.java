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

/** Generated Model for M_PromotionReward
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_M_PromotionReward extends PO implements I_M_PromotionReward, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20110208L;

    /** Standard Constructor */
    public X_M_PromotionReward (Properties ctx, int M_PromotionReward_ID, String trxName)
    {
      super (ctx, M_PromotionReward_ID, trxName);
      /** if (M_PromotionReward_ID == 0)
        {
			setIsForAllDistribution (false);
// N
			setM_PromotionReward_ID (0);
			setM_Promotion_ID (0);
			setRewardMode (null);
// CH
			setRewardType (null);
			setSeqNo (0);
// @SQL=SELECT COALESCE(MAX(SeqNo),0)+10 AS DefaultValue FROM M_PromotionReward WHERE M_Promotion_ID=@M_Promotion_ID@
        } */
    }

    /** Load Constructor */
    public X_M_PromotionReward (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_M_PromotionReward[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Betrag.
		@param Amount 
		Amount in a defined currency
	  */
	public void setAmount (BigDecimal Amount)
	{
		set_Value (COLUMNNAME_Amount, Amount);
	}

	/** Get Amount.
		@return Amount in a defined currency
	  */
	public BigDecimal getAmount () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Amount);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public I_C_Charge getC_Charge() throws RuntimeException
    {
		return (I_C_Charge)MTable.get(getCtx(), I_C_Charge.Table_Name)
			.getPO(getC_Charge_ID(), get_TrxName());	}

	/** Set Kosten.
		@param C_Charge_ID 
		Additional document charges
	  */
	public void setC_Charge_ID (int C_Charge_ID)
	{
		if (C_Charge_ID < 1) 
			set_Value (COLUMNNAME_C_Charge_ID, null);
		else 
			set_Value (COLUMNNAME_C_Charge_ID, Integer.valueOf(C_Charge_ID));
	}

	/** Get Charge.
		@return Additional document charges
	  */
	public int getC_Charge_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Charge_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set For all distribution.
		@param IsForAllDistribution 
		This reward is for all distribution
	  */
	public void setIsForAllDistribution (boolean IsForAllDistribution)
	{
		set_Value (COLUMNNAME_IsForAllDistribution, Boolean.valueOf(IsForAllDistribution));
	}

	/** Get For all distribution.
		@return This reward is for all distribution
	  */
	public boolean isForAllDistribution () 
	{
		Object oo = get_Value(COLUMNNAME_IsForAllDistribution);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Same distribution for source and target.
		@param IsSameDistribution 
		Use the same distribution for source and target
	  */
	public void setIsSameDistribution (boolean IsSameDistribution)
	{
		set_Value (COLUMNNAME_IsSameDistribution, Boolean.valueOf(IsSameDistribution));
	}

	/** Get Same distribution for source and target.
		@return Use the same distribution for source and target
	  */
	public boolean isSameDistribution () 
	{
		Object oo = get_Value(COLUMNNAME_IsSameDistribution);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	public I_M_PromotionDistribution getM_PromotionDistribution() throws RuntimeException
    {
		return (I_M_PromotionDistribution)MTable.get(getCtx(), I_M_PromotionDistribution.Table_Name)
			.getPO(getM_PromotionDistribution_ID(), get_TrxName());	}

	/** Set Promotion Distribution.
		@param M_PromotionDistribution_ID Promotion Distribution	  */
	public void setM_PromotionDistribution_ID (int M_PromotionDistribution_ID)
	{
		if (M_PromotionDistribution_ID < 1) 
			set_Value (COLUMNNAME_M_PromotionDistribution_ID, null);
		else 
			set_Value (COLUMNNAME_M_PromotionDistribution_ID, Integer.valueOf(M_PromotionDistribution_ID));
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

	/** Set Promotion Reward.
		@param M_PromotionReward_ID Promotion Reward	  */
	public void setM_PromotionReward_ID (int M_PromotionReward_ID)
	{
		if (M_PromotionReward_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_PromotionReward_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_PromotionReward_ID, Integer.valueOf(M_PromotionReward_ID));
	}

	/** Get Promotion Reward.
		@return Promotion Reward	  */
	public int getM_PromotionReward_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_PromotionReward_ID);
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

	public I_M_PromotionDistribution getM_TargetDistribution() throws RuntimeException
    {
		return (I_M_PromotionDistribution)MTable.get(getCtx(), I_M_PromotionDistribution.Table_Name)
			.getPO(getM_TargetDistribution_ID(), get_TrxName());	}

	/** Set Target distribution.
		@param M_TargetDistribution_ID 
		Get product from target distribution to apply the promotion reward
	  */
	public void setM_TargetDistribution_ID (int M_TargetDistribution_ID)
	{
		if (M_TargetDistribution_ID < 1) 
			set_Value (COLUMNNAME_M_TargetDistribution_ID, null);
		else 
			set_Value (COLUMNNAME_M_TargetDistribution_ID, Integer.valueOf(M_TargetDistribution_ID));
	}

	/** Get Target distribution.
		@return Get product from target distribution to apply the promotion reward
	  */
	public int getM_TargetDistribution_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_TargetDistribution_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Menge.
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

	/** RewardMode AD_Reference_ID=53389 */
	public static final int REWARDMODE_AD_Reference_ID=53389;
	/** Charge = CH */
	public static final String REWARDMODE_Charge = "CH";
	/** Split Quantity = SQ */
	public static final String REWARDMODE_SplitQuantity = "SQ";
	/** Set Reward Mode.
		@param RewardMode Reward Mode	  */
	public void setRewardMode (String RewardMode)
	{

		set_Value (COLUMNNAME_RewardMode, RewardMode);
	}

	/** Get Reward Mode.
		@return Reward Mode	  */
	public String getRewardMode () 
	{
		return (String)get_Value(COLUMNNAME_RewardMode);
	}

	/** RewardType AD_Reference_ID=53298 */
	public static final int REWARDTYPE_AD_Reference_ID=53298;
	/** Percentage = P */
	public static final String REWARDTYPE_Percentage = "P";
	/** Flat Discount = F */
	public static final String REWARDTYPE_FlatDiscount = "F";
	/** Absolute Amount = A */
	public static final String REWARDTYPE_AbsoluteAmount = "A";
	/** Set Reward Type.
		@param RewardType 
		Type of reward which consists of percentage discount, flat discount or absolute amount
	  */
	public void setRewardType (String RewardType)
	{

		set_Value (COLUMNNAME_RewardType, RewardType);
	}

	/** Get Reward Type.
		@return Type of reward which consists of percentage discount, flat discount or absolute amount
	  */
	public String getRewardType () 
	{
		return (String)get_Value(COLUMNNAME_RewardType);
	}

	/** Set Reihenfolge.
		@param SeqNo 
		Method of ordering records; lowest number comes first
	  */
	public void setSeqNo (int SeqNo)
	{
		set_Value (COLUMNNAME_SeqNo, Integer.valueOf(SeqNo));
	}

	/** Get Reihenfolge.
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