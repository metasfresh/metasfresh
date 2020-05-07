/** Generated Model - DO NOT CHANGE */
package org.eevolution.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for PP_Cost_Collector_CostDetailAdjust
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_PP_Cost_Collector_CostDetailAdjust extends org.compiere.model.PO implements I_PP_Cost_Collector_CostDetailAdjust, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -2085209042L;

    /** Standard Constructor */
    public X_PP_Cost_Collector_CostDetailAdjust (Properties ctx, int PP_Cost_Collector_CostDetailAdjust_ID, String trxName)
    {
      super (ctx, PP_Cost_Collector_CostDetailAdjust_ID, trxName);
      /** if (PP_Cost_Collector_CostDetailAdjust_ID == 0)
        {
			setAmt (BigDecimal.ZERO);
			setM_CostDetail_ID (0);
			setPP_Cost_Collector_CostDetailAdjust_ID (0);
			setPrev_CumulatedAmt (BigDecimal.ZERO);
			setPrev_CumulatedQty (BigDecimal.ZERO);
			setPrev_CurrentCostPrice (BigDecimal.ZERO);
			setPrev_CurrentCostPriceLL (BigDecimal.ZERO);
			setPrev_CurrentQty (BigDecimal.ZERO);
			setQty (BigDecimal.ZERO);
        } */
    }

    /** Load Constructor */
    public X_PP_Cost_Collector_CostDetailAdjust (Properties ctx, ResultSet rs, String trxName)
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

	/** Set Betrag.
		@param Amt 
		Betrag
	  */
	@Override
	public void setAmt (java.math.BigDecimal Amt)
	{
		set_ValueNoCheck (COLUMNNAME_Amt, Amt);
	}

	/** Get Betrag.
		@return Betrag
	  */
	@Override
	public java.math.BigDecimal getAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Amt);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	@Override
	public org.compiere.model.I_M_CostDetail getM_CostDetail() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_CostDetail_ID, org.compiere.model.I_M_CostDetail.class);
	}

	@Override
	public void setM_CostDetail(org.compiere.model.I_M_CostDetail M_CostDetail)
	{
		set_ValueFromPO(COLUMNNAME_M_CostDetail_ID, org.compiere.model.I_M_CostDetail.class, M_CostDetail);
	}

	/** Set Kosten-Detail.
		@param M_CostDetail_ID 
		Cost Detail Information
	  */
	@Override
	public void setM_CostDetail_ID (int M_CostDetail_ID)
	{
		if (M_CostDetail_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_CostDetail_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_CostDetail_ID, Integer.valueOf(M_CostDetail_ID));
	}

	/** Get Kosten-Detail.
		@return Cost Detail Information
	  */
	@Override
	public int getM_CostDetail_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_CostDetail_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set PP_Cost_Collector_CostDetailAdjust.
		@param PP_Cost_Collector_CostDetailAdjust_ID PP_Cost_Collector_CostDetailAdjust	  */
	@Override
	public void setPP_Cost_Collector_CostDetailAdjust_ID (int PP_Cost_Collector_CostDetailAdjust_ID)
	{
		if (PP_Cost_Collector_CostDetailAdjust_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_PP_Cost_Collector_CostDetailAdjust_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_PP_Cost_Collector_CostDetailAdjust_ID, Integer.valueOf(PP_Cost_Collector_CostDetailAdjust_ID));
	}

	/** Get PP_Cost_Collector_CostDetailAdjust.
		@return PP_Cost_Collector_CostDetailAdjust	  */
	@Override
	public int getPP_Cost_Collector_CostDetailAdjust_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PP_Cost_Collector_CostDetailAdjust_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Previous Cumulated Amount.
		@param Prev_CumulatedAmt Previous Cumulated Amount	  */
	@Override
	public void setPrev_CumulatedAmt (java.math.BigDecimal Prev_CumulatedAmt)
	{
		set_Value (COLUMNNAME_Prev_CumulatedAmt, Prev_CumulatedAmt);
	}

	/** Get Previous Cumulated Amount.
		@return Previous Cumulated Amount	  */
	@Override
	public java.math.BigDecimal getPrev_CumulatedAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Prev_CumulatedAmt);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Previous Cumulated Quantity.
		@param Prev_CumulatedQty Previous Cumulated Quantity	  */
	@Override
	public void setPrev_CumulatedQty (java.math.BigDecimal Prev_CumulatedQty)
	{
		set_Value (COLUMNNAME_Prev_CumulatedQty, Prev_CumulatedQty);
	}

	/** Get Previous Cumulated Quantity.
		@return Previous Cumulated Quantity	  */
	@Override
	public java.math.BigDecimal getPrev_CumulatedQty () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Prev_CumulatedQty);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Previous Current Cost Price.
		@param Prev_CurrentCostPrice Previous Current Cost Price	  */
	@Override
	public void setPrev_CurrentCostPrice (java.math.BigDecimal Prev_CurrentCostPrice)
	{
		set_Value (COLUMNNAME_Prev_CurrentCostPrice, Prev_CurrentCostPrice);
	}

	/** Get Previous Current Cost Price.
		@return Previous Current Cost Price	  */
	@Override
	public java.math.BigDecimal getPrev_CurrentCostPrice () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Prev_CurrentCostPrice);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Previous Current Cost Price LL.
		@param Prev_CurrentCostPriceLL Previous Current Cost Price LL	  */
	@Override
	public void setPrev_CurrentCostPriceLL (java.math.BigDecimal Prev_CurrentCostPriceLL)
	{
		set_Value (COLUMNNAME_Prev_CurrentCostPriceLL, Prev_CurrentCostPriceLL);
	}

	/** Get Previous Current Cost Price LL.
		@return Previous Current Cost Price LL	  */
	@Override
	public java.math.BigDecimal getPrev_CurrentCostPriceLL () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Prev_CurrentCostPriceLL);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Previous Current Qty.
		@param Prev_CurrentQty Previous Current Qty	  */
	@Override
	public void setPrev_CurrentQty (java.math.BigDecimal Prev_CurrentQty)
	{
		set_Value (COLUMNNAME_Prev_CurrentQty, Prev_CurrentQty);
	}

	/** Get Previous Current Qty.
		@return Previous Current Qty	  */
	@Override
	public java.math.BigDecimal getPrev_CurrentQty () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Prev_CurrentQty);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Menge.
		@param Qty 
		Menge
	  */
	@Override
	public void setQty (java.math.BigDecimal Qty)
	{
		set_ValueNoCheck (COLUMNNAME_Qty, Qty);
	}

	/** Get Menge.
		@return Menge
	  */
	@Override
	public java.math.BigDecimal getQty () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Qty);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}
}