/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_Customs_Invoice_Line
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_Customs_Invoice_Line extends org.compiere.model.PO implements I_C_Customs_Invoice_Line, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1938936946L;

    /** Standard Constructor */
    public X_C_Customs_Invoice_Line (Properties ctx, int C_Customs_Invoice_Line_ID, String trxName)
    {
      super (ctx, C_Customs_Invoice_Line_ID, trxName);
      /** if (C_Customs_Invoice_Line_ID == 0)
        {
			setC_Customs_Invoice_Line_ID (0);
			setC_UOM_ID (0);
			setLineNetAmt (BigDecimal.ZERO);
			setLineNo (0); // @SQL=SELECT COALESCE(MAX(LineNo),0)+10 AS DefaultValue FROM C_Customs_Invoice_Line WHERE C_Customs_Invoice_ID=@C_Customs_Invoice_ID@
			setM_Product_ID (0);
        } */
    }

    /** Load Constructor */
    public X_C_Customs_Invoice_Line (Properties ctx, ResultSet rs, String trxName)
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
	public org.compiere.model.I_C_Customs_Invoice getC_Customs_Invoice() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_Customs_Invoice_ID, org.compiere.model.I_C_Customs_Invoice.class);
	}

	@Override
	public void setC_Customs_Invoice(org.compiere.model.I_C_Customs_Invoice C_Customs_Invoice)
	{
		set_ValueFromPO(COLUMNNAME_C_Customs_Invoice_ID, org.compiere.model.I_C_Customs_Invoice.class, C_Customs_Invoice);
	}

	/** Set Customs Invoice.
		@param C_Customs_Invoice_ID Customs Invoice	  */
	@Override
	public void setC_Customs_Invoice_ID (int C_Customs_Invoice_ID)
	{
		if (C_Customs_Invoice_ID < 1) 
			set_Value (COLUMNNAME_C_Customs_Invoice_ID, null);
		else 
			set_Value (COLUMNNAME_C_Customs_Invoice_ID, Integer.valueOf(C_Customs_Invoice_ID));
	}

	/** Get Customs Invoice.
		@return Customs Invoice	  */
	@Override
	public int getC_Customs_Invoice_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Customs_Invoice_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Customs Invoice Line.
		@param C_Customs_Invoice_Line_ID Customs Invoice Line	  */
	@Override
	public void setC_Customs_Invoice_Line_ID (int C_Customs_Invoice_Line_ID)
	{
		if (C_Customs_Invoice_Line_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Customs_Invoice_Line_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Customs_Invoice_Line_ID, Integer.valueOf(C_Customs_Invoice_Line_ID));
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

	/** Set Berechnete Menge.
		@param InvoicedQty 
		The quantity invoiced
	  */
	@Override
	public void setInvoicedQty (java.math.BigDecimal InvoicedQty)
	{
		set_Value (COLUMNNAME_InvoicedQty, InvoicedQty);
	}

	/** Get Berechnete Menge.
		@return The quantity invoiced
	  */
	@Override
	public java.math.BigDecimal getInvoicedQty () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_InvoicedQty);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Zeilennetto.
		@param LineNetAmt 
		Nettowert Zeile (Menge * Einzelpreis) ohne Fracht und Gebühren
	  */
	@Override
	public void setLineNetAmt (java.math.BigDecimal LineNetAmt)
	{
		set_Value (COLUMNNAME_LineNetAmt, LineNetAmt);
	}

	/** Get Zeilennetto.
		@return Nettowert Zeile (Menge * Einzelpreis) ohne Fracht und Gebühren
	  */
	@Override
	public java.math.BigDecimal getLineNetAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_LineNetAmt);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Position.
		@param LineNo 
		Zeile Nr.
	  */
	@Override
	public void setLineNo (int LineNo)
	{
		set_Value (COLUMNNAME_LineNo, Integer.valueOf(LineNo));
	}

	/** Get Position.
		@return Zeile Nr.
	  */
	@Override
	public int getLineNo () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_LineNo);
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
}