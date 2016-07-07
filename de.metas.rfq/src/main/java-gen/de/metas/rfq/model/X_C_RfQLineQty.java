/** Generated Model - DO NOT CHANGE */
package de.metas.rfq.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.util.Env;

/** Generated Model for C_RfQLineQty
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_RfQLineQty extends org.compiere.model.PO implements I_C_RfQLineQty, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1599547650L;

    /** Standard Constructor */
    public X_C_RfQLineQty (Properties ctx, int C_RfQLineQty_ID, String trxName)
    {
      super (ctx, C_RfQLineQty_ID, trxName);
      /** if (C_RfQLineQty_ID == 0)
        {
			setBenchmarkPrice (Env.ZERO);
			setC_RfQLine_ID (0);
			setC_RfQLineQty_ID (0);
			setC_UOM_ID (0);
			setIsOfferQty (false);
			setIsPurchaseQty (false);
			setIsRfQQty (true);
// Y
			setQty (Env.ZERO);
// 1
        } */
    }

    /** Load Constructor */
    public X_C_RfQLineQty (Properties ctx, ResultSet rs, String trxName)
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

	/** Set Benchmark Price.
		@param BenchmarkPrice 
		Price to compare responses to
	  */
	@Override
	public void setBenchmarkPrice (java.math.BigDecimal BenchmarkPrice)
	{
		set_Value (COLUMNNAME_BenchmarkPrice, BenchmarkPrice);
	}

	/** Get Benchmark Price.
		@return Price to compare responses to
	  */
	@Override
	public java.math.BigDecimal getBenchmarkPrice () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_BenchmarkPrice);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Best Response Amount.
		@param BestResponseAmt 
		Best Response Amount
	  */
	@Override
	public void setBestResponseAmt (java.math.BigDecimal BestResponseAmt)
	{
		set_Value (COLUMNNAME_BestResponseAmt, BestResponseAmt);
	}

	/** Get Best Response Amount.
		@return Best Response Amount
	  */
	@Override
	public java.math.BigDecimal getBestResponseAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_BestResponseAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	@Override
	public de.metas.rfq.model.I_C_RfQLine getC_RfQLine() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_RfQLine_ID, de.metas.rfq.model.I_C_RfQLine.class);
	}

	@Override
	public void setC_RfQLine(de.metas.rfq.model.I_C_RfQLine C_RfQLine)
	{
		set_ValueFromPO(COLUMNNAME_C_RfQLine_ID, de.metas.rfq.model.I_C_RfQLine.class, C_RfQLine);
	}

	/** Set RfQ Line.
		@param C_RfQLine_ID 
		Request for Quotation Line
	  */
	@Override
	public void setC_RfQLine_ID (int C_RfQLine_ID)
	{
		if (C_RfQLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_RfQLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_RfQLine_ID, Integer.valueOf(C_RfQLine_ID));
	}

	/** Get RfQ Line.
		@return Request for Quotation Line
	  */
	@Override
	public int getC_RfQLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_RfQLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set RfQ Line Quantity.
		@param C_RfQLineQty_ID 
		Request for Quotation Line Quantity
	  */
	@Override
	public void setC_RfQLineQty_ID (int C_RfQLineQty_ID)
	{
		if (C_RfQLineQty_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_RfQLineQty_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_RfQLineQty_ID, Integer.valueOf(C_RfQLineQty_ID));
	}

	/** Get RfQ Line Quantity.
		@return Request for Quotation Line Quantity
	  */
	@Override
	public int getC_RfQLineQty_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_RfQLineQty_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_UOM getC_UOM() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_UOM_ID, org.compiere.model.I_C_UOM.class);
	}

	@Override
	public void setC_UOM(org.compiere.model.I_C_UOM C_UOM)
	{
		set_ValueFromPO(COLUMNNAME_C_UOM_ID, org.compiere.model.I_C_UOM.class, C_UOM);
	}

	/** Set Maßeinheit.
		@param C_UOM_ID 
		Unit of Measure
	  */
	@Override
	public void setC_UOM_ID (int C_UOM_ID)
	{
		if (C_UOM_ID < 1) 
			set_Value (COLUMNNAME_C_UOM_ID, null);
		else 
			set_Value (COLUMNNAME_C_UOM_ID, Integer.valueOf(C_UOM_ID));
	}

	/** Get Maßeinheit.
		@return Unit of Measure
	  */
	@Override
	public int getC_UOM_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_UOM_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Offer Quantity.
		@param IsOfferQty 
		This quantity is used in the Offer to the Customer
	  */
	@Override
	public void setIsOfferQty (boolean IsOfferQty)
	{
		set_Value (COLUMNNAME_IsOfferQty, Boolean.valueOf(IsOfferQty));
	}

	/** Get Offer Quantity.
		@return This quantity is used in the Offer to the Customer
	  */
	@Override
	public boolean isOfferQty () 
	{
		Object oo = get_Value(COLUMNNAME_IsOfferQty);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Purchase Quantity.
		@param IsPurchaseQty 
		This quantity is used in the Purchase Order to the Supplier
	  */
	@Override
	public void setIsPurchaseQty (boolean IsPurchaseQty)
	{
		set_Value (COLUMNNAME_IsPurchaseQty, Boolean.valueOf(IsPurchaseQty));
	}

	/** Get Purchase Quantity.
		@return This quantity is used in the Purchase Order to the Supplier
	  */
	@Override
	public boolean isPurchaseQty () 
	{
		Object oo = get_Value(COLUMNNAME_IsPurchaseQty);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set RfQ Quantity.
		@param IsRfQQty 
		The quantity is used when generating RfQ Responses
	  */
	@Override
	public void setIsRfQQty (boolean IsRfQQty)
	{
		set_Value (COLUMNNAME_IsRfQQty, Boolean.valueOf(IsRfQQty));
	}

	/** Get RfQ Quantity.
		@return The quantity is used when generating RfQ Responses
	  */
	@Override
	public boolean isRfQQty () 
	{
		Object oo = get_Value(COLUMNNAME_IsRfQQty);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Margin %.
		@param Margin 
		Margin for a product as a percentage
	  */
	@Override
	public void setMargin (java.math.BigDecimal Margin)
	{
		set_Value (COLUMNNAME_Margin, Margin);
	}

	/** Get Margin %.
		@return Margin for a product as a percentage
	  */
	@Override
	public java.math.BigDecimal getMargin () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Margin);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Offer Amount.
		@param OfferAmt 
		Amount of the Offer
	  */
	@Override
	public void setOfferAmt (java.math.BigDecimal OfferAmt)
	{
		set_Value (COLUMNNAME_OfferAmt, OfferAmt);
	}

	/** Get Offer Amount.
		@return Amount of the Offer
	  */
	@Override
	public java.math.BigDecimal getOfferAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_OfferAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Menge.
		@param Qty 
		Quantity
	  */
	@Override
	public void setQty (java.math.BigDecimal Qty)
	{
		set_Value (COLUMNNAME_Qty, Qty);
	}

	/** Get Menge.
		@return Quantity
	  */
	@Override
	public java.math.BigDecimal getQty () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Qty);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}
}