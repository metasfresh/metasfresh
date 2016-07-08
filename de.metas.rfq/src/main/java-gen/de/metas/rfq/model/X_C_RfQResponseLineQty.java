/** Generated Model - DO NOT CHANGE */
package de.metas.rfq.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.util.Env;

/** Generated Model for C_RfQResponseLineQty
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_RfQResponseLineQty extends org.compiere.model.PO implements I_C_RfQResponseLineQty, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 497842527L;

    /** Standard Constructor */
    public X_C_RfQResponseLineQty (Properties ctx, int C_RfQResponseLineQty_ID, String trxName)
    {
      super (ctx, C_RfQResponseLineQty_ID, trxName);
      /** if (C_RfQResponseLineQty_ID == 0)
        {
			setC_RfQLine_ID (0);
// @C_RfQLine_ID@
			setC_RfQResponseLine_ID (0);
			setC_RfQResponseLineQty_ID (0);
			setPrice (Env.ZERO);
			setQtyPromised (Env.ZERO);
// 0
        } */
    }

    /** Load Constructor */
    public X_C_RfQResponseLineQty (Properties ctx, ResultSet rs, String trxName)
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

	@Override
	public de.metas.rfq.model.I_C_RfQLineQty getC_RfQLineQty() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_RfQLineQty_ID, de.metas.rfq.model.I_C_RfQLineQty.class);
	}

	@Override
	public void setC_RfQLineQty(de.metas.rfq.model.I_C_RfQLineQty C_RfQLineQty)
	{
		set_ValueFromPO(COLUMNNAME_C_RfQLineQty_ID, de.metas.rfq.model.I_C_RfQLineQty.class, C_RfQLineQty);
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
	public de.metas.rfq.model.I_C_RfQResponseLine getC_RfQResponseLine() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_RfQResponseLine_ID, de.metas.rfq.model.I_C_RfQResponseLine.class);
	}

	@Override
	public void setC_RfQResponseLine(de.metas.rfq.model.I_C_RfQResponseLine C_RfQResponseLine)
	{
		set_ValueFromPO(COLUMNNAME_C_RfQResponseLine_ID, de.metas.rfq.model.I_C_RfQResponseLine.class, C_RfQResponseLine);
	}

	/** Set RfQ Response Line.
		@param C_RfQResponseLine_ID 
		Request for Quotation Response Line
	  */
	@Override
	public void setC_RfQResponseLine_ID (int C_RfQResponseLine_ID)
	{
		if (C_RfQResponseLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_RfQResponseLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_RfQResponseLine_ID, Integer.valueOf(C_RfQResponseLine_ID));
	}

	/** Get RfQ Response Line.
		@return Request for Quotation Response Line
	  */
	@Override
	public int getC_RfQResponseLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_RfQResponseLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set RfQ Response Line Qty.
		@param C_RfQResponseLineQty_ID 
		Request for Quotation Response Line Quantity
	  */
	@Override
	public void setC_RfQResponseLineQty_ID (int C_RfQResponseLineQty_ID)
	{
		if (C_RfQResponseLineQty_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_RfQResponseLineQty_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_RfQResponseLineQty_ID, Integer.valueOf(C_RfQResponseLineQty_ID));
	}

	/** Get RfQ Response Line Qty.
		@return Request for Quotation Response Line Quantity
	  */
	@Override
	public int getC_RfQResponseLineQty_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_RfQResponseLineQty_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Zugesagter Termin.
		@param DatePromised 
		Zugesagter Termin für diesen Auftrag
	  */
	@Override
	public void setDatePromised (java.sql.Timestamp DatePromised)
	{
		set_Value (COLUMNNAME_DatePromised, DatePromised);
	}

	/** Get Zugesagter Termin.
		@return Zugesagter Termin für diesen Auftrag
	  */
	@Override
	public java.sql.Timestamp getDatePromised () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_DatePromised);
	}

	/** Set Rabatt %.
		@param Discount 
		Discount in percent
	  */
	@Override
	public void setDiscount (java.math.BigDecimal Discount)
	{
		set_Value (COLUMNNAME_Discount, Discount);
	}

	/** Get Rabatt %.
		@return Discount in percent
	  */
	@Override
	public java.math.BigDecimal getDiscount () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Discount);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Preis.
		@param Price 
		Price
	  */
	@Override
	public void setPrice (java.math.BigDecimal Price)
	{
		set_Value (COLUMNNAME_Price, Price);
	}

	/** Get Preis.
		@return Price
	  */
	@Override
	public java.math.BigDecimal getPrice () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Price);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Zusagbar.
		@param QtyPromised Zusagbar	  */
	@Override
	public void setQtyPromised (java.math.BigDecimal QtyPromised)
	{
		set_Value (COLUMNNAME_QtyPromised, QtyPromised);
	}

	/** Get Zusagbar.
		@return Zusagbar	  */
	@Override
	public java.math.BigDecimal getQtyPromised () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyPromised);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Ranking.
		@param Ranking 
		Relative Rank Number
	  */
	@Override
	public void setRanking (int Ranking)
	{
		set_Value (COLUMNNAME_Ranking, Integer.valueOf(Ranking));
	}

	/** Get Ranking.
		@return Relative Rank Number
	  */
	@Override
	public int getRanking () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Ranking);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}