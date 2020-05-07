/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for M_InOutLine_To_C_Customs_Invoice_Line
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_M_InOutLine_To_C_Customs_Invoice_Line extends org.compiere.model.PO implements I_M_InOutLine_To_C_Customs_Invoice_Line, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 509559177L;

    /** Standard Constructor */
    public X_M_InOutLine_To_C_Customs_Invoice_Line (Properties ctx, int M_InOutLine_To_C_Customs_Invoice_Line_ID, String trxName)
    {
      super (ctx, M_InOutLine_To_C_Customs_Invoice_Line_ID, trxName);
      /** if (M_InOutLine_To_C_Customs_Invoice_Line_ID == 0)
        {
			setM_InOutLine_To_C_Customs_Invoice_Line_ID (0);
        } */
    }

    /** Load Constructor */
    public X_M_InOutLine_To_C_Customs_Invoice_Line (Properties ctx, ResultSet rs, String trxName)
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

	/** Set Währung.
		@param C_Currency_ID 
		Die Währung für diesen Eintrag
	  */
	@Override
	public void setC_Currency_ID (int C_Currency_ID)
	{
		if (C_Currency_ID < 1) 
			set_Value (COLUMNNAME_C_Currency_ID, null);
		else 
			set_Value (COLUMNNAME_C_Currency_ID, Integer.valueOf(C_Currency_ID));
	}

	/** Get Währung.
		@return Die Währung für diesen Eintrag
	  */
	@Override
	public int getC_Currency_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Currency_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_Customs_Invoice getC_Customs_Invoice()
	{
		return get_ValueAsPO(COLUMNNAME_C_Customs_Invoice_ID, org.compiere.model.I_C_Customs_Invoice.class);
	}

	@Override
	public void setC_Customs_Invoice(org.compiere.model.I_C_Customs_Invoice C_Customs_Invoice)
	{
		set_ValueFromPO(COLUMNNAME_C_Customs_Invoice_ID, org.compiere.model.I_C_Customs_Invoice.class, C_Customs_Invoice);
	}

	/** Set Zollrechnung.
		@param C_Customs_Invoice_ID Zollrechnung	  */
	@Override
	public void setC_Customs_Invoice_ID (int C_Customs_Invoice_ID)
	{
		if (C_Customs_Invoice_ID < 1) 
			set_Value (COLUMNNAME_C_Customs_Invoice_ID, null);
		else 
			set_Value (COLUMNNAME_C_Customs_Invoice_ID, Integer.valueOf(C_Customs_Invoice_ID));
	}

	/** Get Zollrechnung.
		@return Zollrechnung	  */
	@Override
	public int getC_Customs_Invoice_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Customs_Invoice_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_Customs_Invoice_Line getC_Customs_Invoice_Line()
	{
		return get_ValueAsPO(COLUMNNAME_C_Customs_Invoice_Line_ID, org.compiere.model.I_C_Customs_Invoice_Line.class);
	}

	@Override
	public void setC_Customs_Invoice_Line(org.compiere.model.I_C_Customs_Invoice_Line C_Customs_Invoice_Line)
	{
		set_ValueFromPO(COLUMNNAME_C_Customs_Invoice_Line_ID, org.compiere.model.I_C_Customs_Invoice_Line.class, C_Customs_Invoice_Line);
	}

	/** Set Customs Invoice Line.
		@param C_Customs_Invoice_Line_ID Customs Invoice Line	  */
	@Override
	public void setC_Customs_Invoice_Line_ID (int C_Customs_Invoice_Line_ID)
	{
		if (C_Customs_Invoice_Line_ID < 1) 
			set_Value (COLUMNNAME_C_Customs_Invoice_Line_ID, null);
		else 
			set_Value (COLUMNNAME_C_Customs_Invoice_Line_ID, Integer.valueOf(C_Customs_Invoice_Line_ID));
	}

	/** Get Customs Invoice Line.
		@return Customs Invoice Line	  */
	@Override
	public int getC_Customs_Invoice_Line_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Customs_Invoice_Line_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Maßeinheit.
		@param C_UOM_ID 
		Maßeinheit
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
		@return Maßeinheit
	  */
	@Override
	public int getC_UOM_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_UOM_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_M_InOut getM_InOut()
	{
		return get_ValueAsPO(COLUMNNAME_M_InOut_ID, org.compiere.model.I_M_InOut.class);
	}

	@Override
	public void setM_InOut(org.compiere.model.I_M_InOut M_InOut)
	{
		set_ValueFromPO(COLUMNNAME_M_InOut_ID, org.compiere.model.I_M_InOut.class, M_InOut);
	}

	/** Set Lieferung/Wareneingang.
		@param M_InOut_ID 
		Material Shipment Document
	  */
	@Override
	public void setM_InOut_ID (int M_InOut_ID)
	{
		if (M_InOut_ID < 1) 
			set_Value (COLUMNNAME_M_InOut_ID, null);
		else 
			set_Value (COLUMNNAME_M_InOut_ID, Integer.valueOf(M_InOut_ID));
	}

	/** Get Lieferung/Wareneingang.
		@return Material Shipment Document
	  */
	@Override
	public int getM_InOut_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_InOut_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_M_InOutLine getM_InOutLine()
	{
		return get_ValueAsPO(COLUMNNAME_M_InOutLine_ID, org.compiere.model.I_M_InOutLine.class);
	}

	@Override
	public void setM_InOutLine(org.compiere.model.I_M_InOutLine M_InOutLine)
	{
		set_ValueFromPO(COLUMNNAME_M_InOutLine_ID, org.compiere.model.I_M_InOutLine.class, M_InOutLine);
	}

	/** Set Versand-/Wareneingangsposition.
		@param M_InOutLine_ID 
		Position auf Versand- oder Wareneingangsbeleg
	  */
	@Override
	public void setM_InOutLine_ID (int M_InOutLine_ID)
	{
		if (M_InOutLine_ID < 1) 
			set_Value (COLUMNNAME_M_InOutLine_ID, null);
		else 
			set_Value (COLUMNNAME_M_InOutLine_ID, Integer.valueOf(M_InOutLine_ID));
	}

	/** Get Versand-/Wareneingangsposition.
		@return Position auf Versand- oder Wareneingangsbeleg
	  */
	@Override
	public int getM_InOutLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_InOutLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set M_InOutLine_To_C_Customs_Invoice_Line.
		@param M_InOutLine_To_C_Customs_Invoice_Line_ID M_InOutLine_To_C_Customs_Invoice_Line	  */
	@Override
	public void setM_InOutLine_To_C_Customs_Invoice_Line_ID (int M_InOutLine_To_C_Customs_Invoice_Line_ID)
	{
		if (M_InOutLine_To_C_Customs_Invoice_Line_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_InOutLine_To_C_Customs_Invoice_Line_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_InOutLine_To_C_Customs_Invoice_Line_ID, Integer.valueOf(M_InOutLine_To_C_Customs_Invoice_Line_ID));
	}

	/** Get M_InOutLine_To_C_Customs_Invoice_Line.
		@return M_InOutLine_To_C_Customs_Invoice_Line	  */
	@Override
	public int getM_InOutLine_To_C_Customs_Invoice_Line_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_InOutLine_To_C_Customs_Invoice_Line_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set Bewegungs-Menge.
		@param MovementQty 
		Menge eines bewegten Produktes.
	  */
	@Override
	public void setMovementQty (java.math.BigDecimal MovementQty)
	{
		set_Value (COLUMNNAME_MovementQty, MovementQty);
	}

	/** Get Bewegungs-Menge.
		@return Menge eines bewegten Produktes.
	  */
	@Override
	public java.math.BigDecimal getMovementQty () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_MovementQty);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Einzelpreis.
		@param PriceActual 
		Effektiver Preis
	  */
	@Override
	public void setPriceActual (java.math.BigDecimal PriceActual)
	{
		set_Value (COLUMNNAME_PriceActual, PriceActual);
	}

	/** Get Einzelpreis.
		@return Effektiver Preis
	  */
	@Override
	public java.math.BigDecimal getPriceActual () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PriceActual);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}
}