/** Generated Model - DO NOT CHANGE */
package de.metas.esb.edi.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for EDI_cctop_invoic_500_v
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("javadoc")
public class X_EDI_cctop_invoic_500_v extends org.compiere.model.PO implements I_EDI_cctop_invoic_500_v, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 228915322L;

    /** Standard Constructor */
    public X_EDI_cctop_invoic_500_v (Properties ctx, int EDI_cctop_invoic_500_v_ID, String trxName)
    {
      super (ctx, EDI_cctop_invoic_500_v_ID, trxName);
    }

    /** Load Constructor */
    public X_EDI_cctop_invoic_500_v (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }


	/** Load Meta Data */
	@Override
	protected org.compiere.model.POInfo initPO(Properties ctx)
	{
		return org.compiere.model.POInfo.getPOInfo(Table_Name);
	}

	@Override
	public org.compiere.model.I_C_Invoice getC_Invoice()
	{
		return get_ValueAsPO(COLUMNNAME_C_Invoice_ID, org.compiere.model.I_C_Invoice.class);
	}

	@Override
	public void setC_Invoice(org.compiere.model.I_C_Invoice C_Invoice)
	{
		set_ValueFromPO(COLUMNNAME_C_Invoice_ID, org.compiere.model.I_C_Invoice.class, C_Invoice);
	}

	@Override
	public void setC_Invoice_ID (int C_Invoice_ID)
	{
		if (C_Invoice_ID < 1) 
			set_Value (COLUMNNAME_C_Invoice_ID, null);
		else 
			set_Value (COLUMNNAME_C_Invoice_ID, Integer.valueOf(C_Invoice_ID));
	}

	@Override
	public int getC_Invoice_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Invoice_ID);
	}

	/** 
	 * EanCom_Ordered_UOM AD_Reference_ID=114
	 * Reference name: C_UOM_Default_First
	 */
	public static final int EANCOM_ORDERED_UOM_AD_Reference_ID=114;
	@Override
	public void setEanCom_Ordered_UOM (java.lang.String EanCom_Ordered_UOM)
	{

		set_ValueNoCheck (COLUMNNAME_EanCom_Ordered_UOM, EanCom_Ordered_UOM);
	}

	@Override
	public java.lang.String getEanCom_Ordered_UOM() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_EanCom_Ordered_UOM);
	}

	@Override
	public void setEanCom_Price_UOM (java.lang.String EanCom_Price_UOM)
	{
		set_ValueNoCheck (COLUMNNAME_EanCom_Price_UOM, EanCom_Price_UOM);
	}

	@Override
	public java.lang.String getEanCom_Price_UOM() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_EanCom_Price_UOM);
	}

	@Override
	public void setEanCom_UOM (java.lang.String EanCom_UOM)
	{
		set_Value (COLUMNNAME_EanCom_UOM, EanCom_UOM);
	}

	@Override
	public java.lang.String getEanCom_UOM() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_EanCom_UOM);
	}

	@Override
	public void setEAN_CU (java.lang.String EAN_CU)
	{
		set_ValueNoCheck (COLUMNNAME_EAN_CU, EAN_CU);
	}

	@Override
	public java.lang.String getEAN_CU() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_EAN_CU);
	}

	@Override
	public void setEAN_TU (java.lang.String EAN_TU)
	{
		set_ValueNoCheck (COLUMNNAME_EAN_TU, EAN_TU);
	}

	@Override
	public java.lang.String getEAN_TU() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_EAN_TU);
	}

	@Override
	public void setEDI_cctop_invoic_500_v_ID (int EDI_cctop_invoic_500_v_ID)
	{
		if (EDI_cctop_invoic_500_v_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_EDI_cctop_invoic_500_v_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_EDI_cctop_invoic_500_v_ID, Integer.valueOf(EDI_cctop_invoic_500_v_ID));
	}

	@Override
	public int getEDI_cctop_invoic_500_v_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_EDI_cctop_invoic_500_v_ID);
	}

	@Override
	public de.metas.esb.edi.model.I_EDI_cctop_invoic_v getEDI_cctop_invoic_v()
	{
		return get_ValueAsPO(COLUMNNAME_EDI_cctop_invoic_v_ID, de.metas.esb.edi.model.I_EDI_cctop_invoic_v.class);
	}

	@Override
	public void setEDI_cctop_invoic_v(de.metas.esb.edi.model.I_EDI_cctop_invoic_v EDI_cctop_invoic_v)
	{
		set_ValueFromPO(COLUMNNAME_EDI_cctop_invoic_v_ID, de.metas.esb.edi.model.I_EDI_cctop_invoic_v.class, EDI_cctop_invoic_v);
	}

	@Override
	public void setEDI_cctop_invoic_v_ID (int EDI_cctop_invoic_v_ID)
	{
		if (EDI_cctop_invoic_v_ID < 1) 
			set_Value (COLUMNNAME_EDI_cctop_invoic_v_ID, null);
		else 
			set_Value (COLUMNNAME_EDI_cctop_invoic_v_ID, Integer.valueOf(EDI_cctop_invoic_v_ID));
	}

	@Override
	public int getEDI_cctop_invoic_v_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_EDI_cctop_invoic_v_ID);
	}

	@Override
	public void setGTIN (java.lang.String GTIN)
	{
		set_ValueNoCheck (COLUMNNAME_GTIN, GTIN);
	}

	@Override
	public java.lang.String getGTIN() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_GTIN);
	}

	@Override
	public void setISO_Code (java.lang.String ISO_Code)
	{
		set_Value (COLUMNNAME_ISO_Code, ISO_Code);
	}

	@Override
	public java.lang.String getISO_Code() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ISO_Code);
	}

	@Override
	public void setLeergut (java.lang.String Leergut)
	{
		set_Value (COLUMNNAME_Leergut, Leergut);
	}

	@Override
	public java.lang.String getLeergut() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Leergut);
	}

	@Override
	public void setLine (int Line)
	{
		set_Value (COLUMNNAME_Line, Integer.valueOf(Line));
	}

	@Override
	public int getLine() 
	{
		return get_ValueAsInt(COLUMNNAME_Line);
	}

	@Override
	public void setLineNetAmt (java.math.BigDecimal LineNetAmt)
	{
		set_Value (COLUMNNAME_LineNetAmt, LineNetAmt);
	}

	@Override
	public java.math.BigDecimal getLineNetAmt() 
	{
		BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_LineNetAmt);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setName (java.lang.String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	@Override
	public java.lang.String getName() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Name);
	}

	@Override
	public void setName2 (java.lang.String Name2)
	{
		set_Value (COLUMNNAME_Name2, Name2);
	}

	@Override
	public java.lang.String getName2() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Name2);
	}

	@Override
	public void setOrderLine (int OrderLine)
	{
		set_Value (COLUMNNAME_OrderLine, Integer.valueOf(OrderLine));
	}

	@Override
	public int getOrderLine() 
	{
		return get_ValueAsInt(COLUMNNAME_OrderLine);
	}

	@Override
	public void setOrderPOReference (java.lang.String OrderPOReference)
	{
		set_ValueNoCheck (COLUMNNAME_OrderPOReference, OrderPOReference);
	}

	@Override
	public java.lang.String getOrderPOReference() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_OrderPOReference);
	}

	@Override
	public void setPriceActual (java.math.BigDecimal PriceActual)
	{
		set_Value (COLUMNNAME_PriceActual, PriceActual);
	}

	@Override
	public java.math.BigDecimal getPriceActual() 
	{
		BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_PriceActual);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setPriceList (java.math.BigDecimal PriceList)
	{
		set_Value (COLUMNNAME_PriceList, PriceList);
	}

	@Override
	public java.math.BigDecimal getPriceList() 
	{
		BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_PriceList);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setProductDescription (java.lang.String ProductDescription)
	{
		set_Value (COLUMNNAME_ProductDescription, ProductDescription);
	}

	@Override
	public java.lang.String getProductDescription() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ProductDescription);
	}

	@Override
	public void setQtyInvoiced (java.math.BigDecimal QtyInvoiced)
	{
		set_Value (COLUMNNAME_QtyInvoiced, QtyInvoiced);
	}

	@Override
	public java.math.BigDecimal getQtyInvoiced() 
	{
		BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyInvoiced);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyInvoicedInOrderedUOM (java.math.BigDecimal QtyInvoicedInOrderedUOM)
	{
		set_ValueNoCheck (COLUMNNAME_QtyInvoicedInOrderedUOM, QtyInvoicedInOrderedUOM);
	}

	@Override
	public java.math.BigDecimal getQtyInvoicedInOrderedUOM() 
	{
		BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyInvoicedInOrderedUOM);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setRate (java.math.BigDecimal Rate)
	{
		set_Value (COLUMNNAME_Rate, Rate);
	}

	@Override
	public java.math.BigDecimal getRate() 
	{
		BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_Rate);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setTaxAmtInfo (java.math.BigDecimal TaxAmtInfo)
	{
		set_Value (COLUMNNAME_TaxAmtInfo, TaxAmtInfo);
	}

	@Override
	public java.math.BigDecimal getTaxAmtInfo() 
	{
		BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_TaxAmtInfo);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void settaxfree (boolean taxfree)
	{
		set_Value (COLUMNNAME_taxfree, Boolean.valueOf(taxfree));
	}

	@Override
	public boolean istaxfree() 
	{
		return get_ValueAsBoolean(COLUMNNAME_taxfree);
	}

	@Override
	public void setUPC_CU (java.lang.String UPC_CU)
	{
		set_ValueNoCheck (COLUMNNAME_UPC_CU, UPC_CU);
	}

	@Override
	public java.lang.String getUPC_CU() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_UPC_CU);
	}

	@Override
	public void setUPC_TU (java.lang.String UPC_TU)
	{
		set_ValueNoCheck (COLUMNNAME_UPC_TU, UPC_TU);
	}

	@Override
	public java.lang.String getUPC_TU() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_UPC_TU);
	}

	@Override
	public void setValue (java.lang.String Value)
	{
		set_Value (COLUMNNAME_Value, Value);
	}

	@Override
	public java.lang.String getValue() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Value);
	}

	@Override
	public void setVendorProductNo (java.lang.String VendorProductNo)
	{
		set_Value (COLUMNNAME_VendorProductNo, VendorProductNo);
	}

	@Override
	public java.lang.String getVendorProductNo() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_VendorProductNo);
	}
}