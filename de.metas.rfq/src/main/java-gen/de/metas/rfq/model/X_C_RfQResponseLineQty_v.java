/** Generated Model - DO NOT CHANGE */
package de.metas.rfq.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.util.Env;

/** Generated Model for C_RfQResponseLineQty_v
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_RfQResponseLineQty_v extends org.compiere.model.PO implements I_C_RfQResponseLineQty_v, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -536319299L;

    /** Standard Constructor */
    public X_C_RfQResponseLineQty_v (Properties ctx, int C_RfQResponseLineQty_v_ID, String trxName)
    {
      super (ctx, C_RfQResponseLineQty_v_ID, trxName);
      /** if (C_RfQResponseLineQty_v_ID == 0)
        {
			setC_RfQLineQty_ID (0);
			setC_RfQResponseLine_ID (0);
			setC_RfQResponseLineQty_ID (0);
			setC_UOM_ID (0);
			setPrice (0);
			setQty (Env.ZERO);
        } */
    }

    /** Load Constructor */
    public X_C_RfQResponseLineQty_v (Properties ctx, ResultSet rs, String trxName)
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

	/** 
	 * AD_Language AD_Reference_ID=106
	 * Reference name: AD_Language
	 */
	public static final int AD_LANGUAGE_AD_Reference_ID=106;
	/** Set Sprache.
		@param AD_Language 
		Language for this entity
	  */
	@Override
	public void setAD_Language (java.lang.String AD_Language)
	{

		set_ValueNoCheck (COLUMNNAME_AD_Language, AD_Language);
	}

	/** Get Sprache.
		@return Language for this entity
	  */
	@Override
	public java.lang.String getAD_Language () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_AD_Language);
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
			set_ValueNoCheck (COLUMNNAME_C_UOM_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_UOM_ID, Integer.valueOf(C_UOM_ID));
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

	/** Set Rabatt %.
		@param Discount 
		Discount in percent
	  */
	@Override
	public void setDiscount (java.math.BigDecimal Discount)
	{
		set_ValueNoCheck (COLUMNNAME_Discount, Discount);
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

	@Override
	public org.compiere.model.I_C_ValidCombination getPr() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_Price, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setPr(org.compiere.model.I_C_ValidCombination Pr)
	{
		set_ValueFromPO(COLUMNNAME_Price, org.compiere.model.I_C_ValidCombination.class, Pr);
	}

	/** Set Preis.
		@param Price 
		Price
	  */
	@Override
	public void setPrice (int Price)
	{
		set_ValueNoCheck (COLUMNNAME_Price, Integer.valueOf(Price));
	}

	/** Get Preis.
		@return Price
	  */
	@Override
	public int getPrice () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Price);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Menge.
		@param Qty 
		Quantity
	  */
	@Override
	public void setQty (java.math.BigDecimal Qty)
	{
		set_ValueNoCheck (COLUMNNAME_Qty, Qty);
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

	/** Set Symbol.
		@param UOMSymbol 
		Symbol for a Unit of Measure
	  */
	@Override
	public void setUOMSymbol (java.lang.String UOMSymbol)
	{
		set_ValueNoCheck (COLUMNNAME_UOMSymbol, UOMSymbol);
	}

	/** Get Symbol.
		@return Symbol for a Unit of Measure
	  */
	@Override
	public java.lang.String getUOMSymbol () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_UOMSymbol);
	}
}