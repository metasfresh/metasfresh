/** Generated Model - DO NOT CHANGE */
package de.metas.esb.edi.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for EDI_DesadvLine
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("javadoc")
public class X_EDI_DesadvLine extends org.compiere.model.PO implements I_EDI_DesadvLine, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -182511967L;

    /** Standard Constructor */
    public X_EDI_DesadvLine (Properties ctx, int EDI_DesadvLine_ID, String trxName)
    {
      super (ctx, EDI_DesadvLine_ID, trxName);
    }

    /** Load Constructor */
    public X_EDI_DesadvLine (Properties ctx, ResultSet rs, String trxName)
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
	public void setC_UOM_ID (int C_UOM_ID)
	{
		if (C_UOM_ID < 1) 
			set_Value (COLUMNNAME_C_UOM_ID, null);
		else 
			set_Value (COLUMNNAME_C_UOM_ID, Integer.valueOf(C_UOM_ID));
	}

	@Override
	public int getC_UOM_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_UOM_ID);
	}

	@Override
	public void setC_UOM_Invoice_ID (int C_UOM_Invoice_ID)
	{
		if (C_UOM_Invoice_ID < 1) 
			set_Value (COLUMNNAME_C_UOM_Invoice_ID, null);
		else 
			set_Value (COLUMNNAME_C_UOM_Invoice_ID, Integer.valueOf(C_UOM_Invoice_ID));
	}

	@Override
	public int getC_UOM_Invoice_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_UOM_Invoice_ID);
	}

	@Override
	public void setEanCom_Invoice_UOM (java.lang.String EanCom_Invoice_UOM)
	{
		throw new IllegalArgumentException ("EanCom_Invoice_UOM is virtual column");	}

	@Override
	public java.lang.String getEanCom_Invoice_UOM() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_EanCom_Invoice_UOM);
	}

	@Override
	public void setEAN_CU (java.lang.String EAN_CU)
	{
		set_Value (COLUMNNAME_EAN_CU, EAN_CU);
	}

	@Override
	public java.lang.String getEAN_CU() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_EAN_CU);
	}

	@Override
	public void setEAN_TU (java.lang.String EAN_TU)
	{
		set_Value (COLUMNNAME_EAN_TU, EAN_TU);
	}

	@Override
	public java.lang.String getEAN_TU() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_EAN_TU);
	}

	@Override
	public de.metas.esb.edi.model.I_EDI_Desadv getEDI_Desadv()
	{
		return get_ValueAsPO(COLUMNNAME_EDI_Desadv_ID, de.metas.esb.edi.model.I_EDI_Desadv.class);
	}

	@Override
	public void setEDI_Desadv(de.metas.esb.edi.model.I_EDI_Desadv EDI_Desadv)
	{
		set_ValueFromPO(COLUMNNAME_EDI_Desadv_ID, de.metas.esb.edi.model.I_EDI_Desadv.class, EDI_Desadv);
	}

	@Override
	public void setEDI_Desadv_ID (int EDI_Desadv_ID)
	{
		if (EDI_Desadv_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_EDI_Desadv_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_EDI_Desadv_ID, Integer.valueOf(EDI_Desadv_ID));
	}

	@Override
	public int getEDI_Desadv_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_EDI_Desadv_ID);
	}

	@Override
	public void setEDI_DesadvLine_ID (int EDI_DesadvLine_ID)
	{
		if (EDI_DesadvLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_EDI_DesadvLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_EDI_DesadvLine_ID, Integer.valueOf(EDI_DesadvLine_ID));
	}

	@Override
	public int getEDI_DesadvLine_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_EDI_DesadvLine_ID);
	}

	@Override
	public void setGTIN (java.lang.String GTIN)
	{
		set_Value (COLUMNNAME_GTIN, GTIN);
	}

	@Override
	public java.lang.String getGTIN() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_GTIN);
	}

	/** 
	 * InvoicableQtyBasedOn AD_Reference_ID=541023
	 * Reference name: InvoicableQtyBasedOn
	 */
	public static final int INVOICABLEQTYBASEDON_AD_Reference_ID=541023;
	/** Nominal = Nominal */
	public static final String INVOICABLEQTYBASEDON_Nominal = "Nominal";
	/** CatchWeight = CatchWeight */
	public static final String INVOICABLEQTYBASEDON_CatchWeight = "CatchWeight";
	@Override
	public void setInvoicableQtyBasedOn (java.lang.String InvoicableQtyBasedOn)
	{

		set_Value (COLUMNNAME_InvoicableQtyBasedOn, InvoicableQtyBasedOn);
	}

	@Override
	public java.lang.String getInvoicableQtyBasedOn() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_InvoicableQtyBasedOn);
	}

	@Override
	public void setIsSubsequentDeliveryPlanned (boolean IsSubsequentDeliveryPlanned)
	{
		set_Value (COLUMNNAME_IsSubsequentDeliveryPlanned, Boolean.valueOf(IsSubsequentDeliveryPlanned));
	}

	@Override
	public boolean isSubsequentDeliveryPlanned() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsSubsequentDeliveryPlanned);
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
	public void setM_Product_ID (int M_Product_ID)
	{
		if (M_Product_ID < 1) 
			set_Value (COLUMNNAME_M_Product_ID, null);
		else 
			set_Value (COLUMNNAME_M_Product_ID, Integer.valueOf(M_Product_ID));
	}

	@Override
	public int getM_Product_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Product_ID);
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
		set_Value (COLUMNNAME_OrderPOReference, OrderPOReference);
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
	public void setProductNo (java.lang.String ProductNo)
	{
		set_Value (COLUMNNAME_ProductNo, ProductNo);
	}

	@Override
	public java.lang.String getProductNo() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ProductNo);
	}

	@Override
	public void setQtyDeliveredInInvoiceUOM (java.math.BigDecimal QtyDeliveredInInvoiceUOM)
	{
		set_Value (COLUMNNAME_QtyDeliveredInInvoiceUOM, QtyDeliveredInInvoiceUOM);
	}

	@Override
	public java.math.BigDecimal getQtyDeliveredInInvoiceUOM() 
	{
		BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyDeliveredInInvoiceUOM);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyDeliveredInStockingUOM (java.math.BigDecimal QtyDeliveredInStockingUOM)
	{
		set_Value (COLUMNNAME_QtyDeliveredInStockingUOM, QtyDeliveredInStockingUOM);
	}

	@Override
	public java.math.BigDecimal getQtyDeliveredInStockingUOM() 
	{
		BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyDeliveredInStockingUOM);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyDeliveredInUOM (java.math.BigDecimal QtyDeliveredInUOM)
	{
		set_Value (COLUMNNAME_QtyDeliveredInUOM, QtyDeliveredInUOM);
	}

	@Override
	public java.math.BigDecimal getQtyDeliveredInUOM() 
	{
		BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyDeliveredInUOM);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyEntered (java.math.BigDecimal QtyEntered)
	{
		set_Value (COLUMNNAME_QtyEntered, QtyEntered);
	}

	@Override
	public java.math.BigDecimal getQtyEntered() 
	{
		BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyEntered);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyItemCapacity (java.math.BigDecimal QtyItemCapacity)
	{
		set_Value (COLUMNNAME_QtyItemCapacity, QtyItemCapacity);
	}

	@Override
	public java.math.BigDecimal getQtyItemCapacity() 
	{
		BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyItemCapacity);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyOrdered (java.math.BigDecimal QtyOrdered)
	{
		set_Value (COLUMNNAME_QtyOrdered, QtyOrdered);
	}

	@Override
	public java.math.BigDecimal getQtyOrdered() 
	{
		BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyOrdered);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setUPC_CU (java.lang.String UPC_CU)
	{
		set_Value (COLUMNNAME_UPC_CU, UPC_CU);
	}

	@Override
	public java.lang.String getUPC_CU() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_UPC_CU);
	}

	@Override
	public void setUPC_TU (java.lang.String UPC_TU)
	{
		set_Value (COLUMNNAME_UPC_TU, UPC_TU);
	}

	@Override
	public java.lang.String getUPC_TU() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_UPC_TU);
	}
}