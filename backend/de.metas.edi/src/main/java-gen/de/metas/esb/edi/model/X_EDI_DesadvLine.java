// Generated Model - DO NOT CHANGE
package de.metas.esb.edi.model;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for EDI_DesadvLine
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_EDI_DesadvLine extends org.compiere.model.PO implements I_EDI_DesadvLine, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1729503778L;

    /** Standard Constructor */
    public X_EDI_DesadvLine (final Properties ctx, final int EDI_DesadvLine_ID, @Nullable final String trxName)
    {
      super (ctx, EDI_DesadvLine_ID, trxName);
    }

    /** Load Constructor */
    public X_EDI_DesadvLine (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
    {
      super (ctx, rs, trxName);
    }


	/** Load Meta Data */
	@Override
	protected org.compiere.model.POInfo initPO(final Properties ctx)
	{
		return org.compiere.model.POInfo.getPOInfo(Table_Name);
	}

	@Override
	public void setBPartner_QtyItemCapacity (final @Nullable BigDecimal BPartner_QtyItemCapacity)
	{
		set_ValueNoCheck (COLUMNNAME_BPartner_QtyItemCapacity, BPartner_QtyItemCapacity);
	}

	@Override
	public BigDecimal getBPartner_QtyItemCapacity()
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_BPartner_QtyItemCapacity);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setC_UOM_BPartner_ID (final int C_UOM_BPartner_ID)
	{
		if (C_UOM_BPartner_ID < 1)
			set_Value (COLUMNNAME_C_UOM_BPartner_ID, null);
		else
			set_Value (COLUMNNAME_C_UOM_BPartner_ID, C_UOM_BPartner_ID);
	}

	@Override
	public int getC_UOM_BPartner_ID()
	{
		return get_ValueAsInt(COLUMNNAME_C_UOM_BPartner_ID);
	}

	@Override
	public void setC_UOM_ID (final int C_UOM_ID)
	{
		if (C_UOM_ID < 1) 
			set_Value (COLUMNNAME_C_UOM_ID, null);
		else 
			set_Value (COLUMNNAME_C_UOM_ID, C_UOM_ID);
	}

	@Override
	public int getC_UOM_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_UOM_ID);
	}

	@Override
	public void setC_UOM_Invoice_ID (final int C_UOM_Invoice_ID)
	{
		if (C_UOM_Invoice_ID < 1) 
			set_Value (COLUMNNAME_C_UOM_Invoice_ID, null);
		else 
			set_Value (COLUMNNAME_C_UOM_Invoice_ID, C_UOM_Invoice_ID);
	}

	@Override
	public int getC_UOM_Invoice_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_UOM_Invoice_ID);
	}

	@Override
	public void setEAN_CU (final @Nullable String EAN_CU)
	{
		set_Value (COLUMNNAME_EAN_CU, EAN_CU);
	}

	@Override
	public String getEAN_CU()
	{
		return get_ValueAsString(COLUMNNAME_EAN_CU);
	}

	@Override
	public void setEAN_TU (final @Nullable String EAN_TU)
	{
		set_Value (COLUMNNAME_EAN_TU, EAN_TU);
	}

	@Override
	public String getEAN_TU()
	{
		return get_ValueAsString(COLUMNNAME_EAN_TU);
	}

	@Override
	public void setEanCom_Invoice_UOM (final @Nullable String EanCom_Invoice_UOM)
	{
		throw new IllegalArgumentException ("EanCom_Invoice_UOM is virtual column");	}

	@Override
	public String getEanCom_Invoice_UOM()
	{
		return get_ValueAsString(COLUMNNAME_EanCom_Invoice_UOM);
	}

	@Override
	public I_EDI_Desadv getEDI_Desadv()
	{
		return get_ValueAsPO(COLUMNNAME_EDI_Desadv_ID, I_EDI_Desadv.class);
	}

	@Override
	public void setEDI_Desadv(final I_EDI_Desadv EDI_Desadv)
	{
		set_ValueFromPO(COLUMNNAME_EDI_Desadv_ID, I_EDI_Desadv.class, EDI_Desadv);
	}

	@Override
	public void setEDI_Desadv_ID (final int EDI_Desadv_ID)
	{
		if (EDI_Desadv_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_EDI_Desadv_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_EDI_Desadv_ID, EDI_Desadv_ID);
	}

	@Override
	public int getEDI_Desadv_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_EDI_Desadv_ID);
	}

	@Override
	public void setEDI_DesadvLine_ID (final int EDI_DesadvLine_ID)
	{
		if (EDI_DesadvLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_EDI_DesadvLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_EDI_DesadvLine_ID, EDI_DesadvLine_ID);
	}

	@Override
	public int getEDI_DesadvLine_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_EDI_DesadvLine_ID);
	}

	@Override
	public void setExternalSeqNo (final int ExternalSeqNo)
	{
		set_Value (COLUMNNAME_ExternalSeqNo, ExternalSeqNo);
	}

	@Override
	public int getExternalSeqNo()
	{
		return get_ValueAsInt(COLUMNNAME_ExternalSeqNo);
	}

	@Override
	public void setGTIN (final @Nullable String GTIN)
	{
		set_Value (COLUMNNAME_GTIN, GTIN);
	}

	@Override
	public String getGTIN()
	{
		return get_ValueAsString(COLUMNNAME_GTIN);
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
	public void setInvoicableQtyBasedOn (final String InvoicableQtyBasedOn)
	{
		set_Value (COLUMNNAME_InvoicableQtyBasedOn, InvoicableQtyBasedOn);
	}

	@Override
	public String getInvoicableQtyBasedOn()
	{
		return get_ValueAsString(COLUMNNAME_InvoicableQtyBasedOn);
	}

	@Override
	public void setIsSubsequentDeliveryPlanned (final boolean IsSubsequentDeliveryPlanned)
	{
		set_Value (COLUMNNAME_IsSubsequentDeliveryPlanned, IsSubsequentDeliveryPlanned);
	}

	@Override
	public boolean isSubsequentDeliveryPlanned() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsSubsequentDeliveryPlanned);
	}

	@Override
	public void setLine (final int Line)
	{
		set_Value (COLUMNNAME_Line, Line);
	}

	@Override
	public int getLine() 
	{
		return get_ValueAsInt(COLUMNNAME_Line);
	}

	@Override
	public void setM_Product_ID (final int M_Product_ID)
	{
		if (M_Product_ID < 1) 
			set_Value (COLUMNNAME_M_Product_ID, null);
		else 
			set_Value (COLUMNNAME_M_Product_ID, M_Product_ID);
	}

	@Override
	public int getM_Product_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Product_ID);
	}

	@Override
	public void setOrderLine (final int OrderLine)
	{
		set_Value (COLUMNNAME_OrderLine, OrderLine);
	}

	@Override
	public int getOrderLine() 
	{
		return get_ValueAsInt(COLUMNNAME_OrderLine);
	}

	@Override
	public void setOrderPOReference (final @Nullable String OrderPOReference)
	{
		set_Value (COLUMNNAME_OrderPOReference, OrderPOReference);
	}

	@Override
	public String getOrderPOReference()
	{
		return get_ValueAsString(COLUMNNAME_OrderPOReference);
	}

	@Override
	public void setPriceActual (final @Nullable BigDecimal PriceActual)
	{
		set_Value (COLUMNNAME_PriceActual, PriceActual);
	}

	@Override
	public BigDecimal getPriceActual() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_PriceActual);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setProductDescription (final @Nullable String ProductDescription)
	{
		set_Value (COLUMNNAME_ProductDescription, ProductDescription);
	}

	@Override
	public String getProductDescription()
	{
		return get_ValueAsString(COLUMNNAME_ProductDescription);
	}

	@Override
	public void setProductNo (final @Nullable String ProductNo)
	{
		set_Value (COLUMNNAME_ProductNo, ProductNo);
	}

	@Override
	public String getProductNo()
	{
		return get_ValueAsString(COLUMNNAME_ProductNo);
	}

	@Override
	public void setQtyDeliveredInInvoiceUOM (final @Nullable BigDecimal QtyDeliveredInInvoiceUOM)
	{
		set_Value (COLUMNNAME_QtyDeliveredInInvoiceUOM, QtyDeliveredInInvoiceUOM);
	}

	@Override
	public BigDecimal getQtyDeliveredInInvoiceUOM() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyDeliveredInInvoiceUOM);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyDeliveredInStockingUOM (final @Nullable BigDecimal QtyDeliveredInStockingUOM)
	{
		set_Value (COLUMNNAME_QtyDeliveredInStockingUOM, QtyDeliveredInStockingUOM);
	}

	@Override
	public BigDecimal getQtyDeliveredInStockingUOM() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyDeliveredInStockingUOM);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyDeliveredInUOM (final @Nullable BigDecimal QtyDeliveredInUOM)
	{
		set_Value (COLUMNNAME_QtyDeliveredInUOM, QtyDeliveredInUOM);
	}

	@Override
	public BigDecimal getQtyDeliveredInUOM() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyDeliveredInUOM);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyEntered (final @Nullable BigDecimal QtyEntered)
	{
		set_Value (COLUMNNAME_QtyEntered, QtyEntered);
	}

	@Override
	public BigDecimal getQtyEntered() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyEntered);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyEnteredInBPartnerUOM (final @Nullable BigDecimal QtyEnteredInBPartnerUOM)
	{
		set_Value (COLUMNNAME_QtyEnteredInBPartnerUOM, QtyEnteredInBPartnerUOM);
	}

	@Override
	public BigDecimal getQtyEnteredInBPartnerUOM()
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyEnteredInBPartnerUOM);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyItemCapacity (final @Nullable BigDecimal QtyItemCapacity)
	{
		set_Value (COLUMNNAME_QtyItemCapacity, QtyItemCapacity);
	}

	@Override
	public BigDecimal getQtyItemCapacity() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyItemCapacity);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyOrdered (final BigDecimal QtyOrdered)
	{
		set_Value (COLUMNNAME_QtyOrdered, QtyOrdered);
	}

	@Override
	public BigDecimal getQtyOrdered() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyOrdered);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setUPC_CU (final @Nullable String UPC_CU)
	{
		set_Value (COLUMNNAME_UPC_CU, UPC_CU);
	}

	@Override
	public String getUPC_CU()
	{
		return get_ValueAsString(COLUMNNAME_UPC_CU);
	}

	@Override
	public void setUPC_TU (final @Nullable String UPC_TU)
	{
		set_Value (COLUMNNAME_UPC_TU, UPC_TU);
	}

	@Override
	public String getUPC_TU()
	{
		return get_ValueAsString(COLUMNNAME_UPC_TU);
	}
}