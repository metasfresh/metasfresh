/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for M_Shipment_Declaration_Line
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_M_Shipment_Declaration_Line extends org.compiere.model.PO implements I_M_Shipment_Declaration_Line, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -1679308079L;

    /** Standard Constructor */
    public X_M_Shipment_Declaration_Line (Properties ctx, int M_Shipment_Declaration_Line_ID, String trxName)
    {
      super (ctx, M_Shipment_Declaration_Line_ID, trxName);
      /** if (M_Shipment_Declaration_Line_ID == 0)
        {
			setC_UOM_ID (0);
			setLineNo (0); // 0
			setM_InOutLine_ID (0);
			setM_Product_ID (0);
			setM_Shipment_Declaration_ID (0);
			setM_Shipment_Declaration_Line_ID (0);
			setQty (BigDecimal.ZERO);
        } */
    }

    /** Load Constructor */
    public X_M_Shipment_Declaration_Line (Properties ctx, ResultSet rs, String trxName)
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
	public org.compiere.model.I_M_InOutLine getM_InOutLine() throws RuntimeException
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

	@Override
	public org.compiere.model.I_M_Shipment_Declaration getM_Shipment_Declaration() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_Shipment_Declaration_ID, org.compiere.model.I_M_Shipment_Declaration.class);
	}

	@Override
	public void setM_Shipment_Declaration(org.compiere.model.I_M_Shipment_Declaration M_Shipment_Declaration)
	{
		set_ValueFromPO(COLUMNNAME_M_Shipment_Declaration_ID, org.compiere.model.I_M_Shipment_Declaration.class, M_Shipment_Declaration);
	}

	/** Set Abgabemeldung.
		@param M_Shipment_Declaration_ID Abgabemeldung	  */
	@Override
	public void setM_Shipment_Declaration_ID (int M_Shipment_Declaration_ID)
	{
		if (M_Shipment_Declaration_ID < 1) 
			set_Value (COLUMNNAME_M_Shipment_Declaration_ID, null);
		else 
			set_Value (COLUMNNAME_M_Shipment_Declaration_ID, Integer.valueOf(M_Shipment_Declaration_ID));
	}

	/** Get Abgabemeldung.
		@return Abgabemeldung	  */
	@Override
	public int getM_Shipment_Declaration_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Shipment_Declaration_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set M_Shipment_Declaration_Line.
		@param M_Shipment_Declaration_Line_ID M_Shipment_Declaration_Line	  */
	@Override
	public void setM_Shipment_Declaration_Line_ID (int M_Shipment_Declaration_Line_ID)
	{
		if (M_Shipment_Declaration_Line_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Shipment_Declaration_Line_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Shipment_Declaration_Line_ID, Integer.valueOf(M_Shipment_Declaration_Line_ID));
	}

	/** Get M_Shipment_Declaration_Line.
		@return M_Shipment_Declaration_Line	  */
	@Override
	public int getM_Shipment_Declaration_Line_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Shipment_Declaration_Line_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Pck. Gr..
		@param PackageSize Pck. Gr.	  */
	@Override
	public void setPackageSize (java.lang.String PackageSize)
	{
		set_Value (COLUMNNAME_PackageSize, PackageSize);
	}

	/** Get Pck. Gr..
		@return Pck. Gr.	  */
	@Override
	public java.lang.String getPackageSize () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_PackageSize);
	}

	/** Set Menge.
		@param Qty 
		Menge
	  */
	@Override
	public void setQty (java.math.BigDecimal Qty)
	{
		set_Value (COLUMNNAME_Qty, Qty);
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