// Generated Model - DO NOT CHANGE
package de.metas.esb.edi.model;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for EDI_cctop_invoic_500_v
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_EDI_cctop_invoic_500_v extends org.compiere.model.PO implements I_EDI_cctop_invoic_500_v, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 1125461297L;

    /** Standard Constructor */
    public X_EDI_cctop_invoic_500_v (final Properties ctx, final int EDI_cctop_invoic_500_v_ID, @Nullable final String trxName)
    {
      super (ctx, EDI_cctop_invoic_500_v_ID, trxName);
    }

    /** Load Constructor */
    public X_EDI_cctop_invoic_500_v (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setBuyer_EAN_CU (final @Nullable java.lang.String Buyer_EAN_CU)
	{
		set_ValueNoCheck (COLUMNNAME_Buyer_EAN_CU, Buyer_EAN_CU);
	}

	@Override
	public java.lang.String getBuyer_EAN_CU() 
	{
		return get_ValueAsString(COLUMNNAME_Buyer_EAN_CU);
	}

	@Override
	public void setBuyer_GTIN_CU (final @Nullable java.lang.String Buyer_GTIN_CU)
	{
		set_ValueNoCheck (COLUMNNAME_Buyer_GTIN_CU, Buyer_GTIN_CU);
	}

	@Override
	public java.lang.String getBuyer_GTIN_CU() 
	{
		return get_ValueAsString(COLUMNNAME_Buyer_GTIN_CU);
	}

	@Override
	public void setBuyer_GTIN_TU (final @Nullable java.lang.String Buyer_GTIN_TU)
	{
		set_ValueNoCheck (COLUMNNAME_Buyer_GTIN_TU, Buyer_GTIN_TU);
	}

	@Override
	public java.lang.String getBuyer_GTIN_TU() 
	{
		return get_ValueAsString(COLUMNNAME_Buyer_GTIN_TU);
	}

	@Override
	public org.compiere.model.I_C_Invoice getC_Invoice()
	{
		return get_ValueAsPO(COLUMNNAME_C_Invoice_ID, org.compiere.model.I_C_Invoice.class);
	}

	@Override
	public void setC_Invoice(final org.compiere.model.I_C_Invoice C_Invoice)
	{
		set_ValueFromPO(COLUMNNAME_C_Invoice_ID, org.compiere.model.I_C_Invoice.class, C_Invoice);
	}

	@Override
	public void setC_Invoice_ID (final int C_Invoice_ID)
	{
		if (C_Invoice_ID < 1) 
			set_Value (COLUMNNAME_C_Invoice_ID, null);
		else 
			set_Value (COLUMNNAME_C_Invoice_ID, C_Invoice_ID);
	}

	@Override
	public int getC_Invoice_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Invoice_ID);
	}

	@Override
	public void setC_UOM_BPartner_ID (final int C_UOM_BPartner_ID)
	{
		if (C_UOM_BPartner_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_UOM_BPartner_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_UOM_BPartner_ID, C_UOM_BPartner_ID);
	}

	@Override
	public int getC_UOM_BPartner_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_UOM_BPartner_ID);
	}

	@Override
	public void setCustomerProductNo (final @Nullable java.lang.String CustomerProductNo)
	{
		set_ValueNoCheck (COLUMNNAME_CustomerProductNo, CustomerProductNo);
	}

	@Override
	public java.lang.String getCustomerProductNo() 
	{
		return get_ValueAsString(COLUMNNAME_CustomerProductNo);
	}

	@Override
	public void setDiscount (final @Nullable BigDecimal Discount)
	{
		set_ValueNoCheck (COLUMNNAME_Discount, Discount);
	}

	@Override
	public BigDecimal getDiscount() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_Discount);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	/** 
	 * EanCom_Ordered_UOM AD_Reference_ID=114
	 * Reference name: C_UOM_Default_First
	 */
	public static final int EANCOM_ORDERED_UOM_AD_Reference_ID=114;
	@Override
	public void setEanCom_Ordered_UOM (final @Nullable java.lang.String EanCom_Ordered_UOM)
	{
		set_ValueNoCheck (COLUMNNAME_EanCom_Ordered_UOM, EanCom_Ordered_UOM);
	}

	@Override
	public java.lang.String getEanCom_Ordered_UOM() 
	{
		return get_ValueAsString(COLUMNNAME_EanCom_Ordered_UOM);
	}

	@Override
	public void setEanCom_Price_UOM (final @Nullable java.lang.String EanCom_Price_UOM)
	{
		set_ValueNoCheck (COLUMNNAME_EanCom_Price_UOM, EanCom_Price_UOM);
	}

	@Override
	public java.lang.String getEanCom_Price_UOM() 
	{
		return get_ValueAsString(COLUMNNAME_EanCom_Price_UOM);
	}

	@Override
	public void setEanCom_UOM (final @Nullable java.lang.String EanCom_UOM)
	{
		set_Value (COLUMNNAME_EanCom_UOM, EanCom_UOM);
	}

	@Override
	public java.lang.String getEanCom_UOM() 
	{
		return get_ValueAsString(COLUMNNAME_EanCom_UOM);
	}

	@Override
	public void setEAN_CU (final @Nullable java.lang.String EAN_CU)
	{
		set_ValueNoCheck (COLUMNNAME_EAN_CU, EAN_CU);
	}

	@Override
	public java.lang.String getEAN_CU() 
	{
		return get_ValueAsString(COLUMNNAME_EAN_CU);
	}

	@Override
	public void setEAN_TU (final @Nullable java.lang.String EAN_TU)
	{
		set_ValueNoCheck (COLUMNNAME_EAN_TU, EAN_TU);
	}

	@Override
	public java.lang.String getEAN_TU() 
	{
		return get_ValueAsString(COLUMNNAME_EAN_TU);
	}

	@Override
	public void setEDI_cctop_invoic_500_v_ID (final int EDI_cctop_invoic_500_v_ID)
	{
		if (EDI_cctop_invoic_500_v_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_EDI_cctop_invoic_500_v_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_EDI_cctop_invoic_500_v_ID, EDI_cctop_invoic_500_v_ID);
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
	public void setEDI_cctop_invoic_v(final de.metas.esb.edi.model.I_EDI_cctop_invoic_v EDI_cctop_invoic_v)
	{
		set_ValueFromPO(COLUMNNAME_EDI_cctop_invoic_v_ID, de.metas.esb.edi.model.I_EDI_cctop_invoic_v.class, EDI_cctop_invoic_v);
	}

	@Override
	public void setEDI_cctop_invoic_v_ID (final int EDI_cctop_invoic_v_ID)
	{
		if (EDI_cctop_invoic_v_ID < 1) 
			set_Value (COLUMNNAME_EDI_cctop_invoic_v_ID, null);
		else 
			set_Value (COLUMNNAME_EDI_cctop_invoic_v_ID, EDI_cctop_invoic_v_ID);
	}

	@Override
	public int getEDI_cctop_invoic_v_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_EDI_cctop_invoic_v_ID);
	}

	@Override
	public void setExternalSeqNo (final int ExternalSeqNo)
	{
		set_ValueNoCheck (COLUMNNAME_ExternalSeqNo, ExternalSeqNo);
	}

	@Override
	public int getExternalSeqNo() 
	{
		return get_ValueAsInt(COLUMNNAME_ExternalSeqNo);
	}

	@Override
	public void setGTIN (final @Nullable java.lang.String GTIN)
	{
		set_ValueNoCheck (COLUMNNAME_GTIN, GTIN);
	}

	@Override
	public java.lang.String getGTIN() 
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
	public void setInvoicableQtyBasedOn (final @Nullable java.lang.String InvoicableQtyBasedOn)
	{
		set_ValueNoCheck (COLUMNNAME_InvoicableQtyBasedOn, InvoicableQtyBasedOn);
	}

	@Override
	public java.lang.String getInvoicableQtyBasedOn() 
	{
		return get_ValueAsString(COLUMNNAME_InvoicableQtyBasedOn);
	}

	@Override
	public void setISO_Code (final @Nullable java.lang.String ISO_Code)
	{
		set_Value (COLUMNNAME_ISO_Code, ISO_Code);
	}

	@Override
	public java.lang.String getISO_Code() 
	{
		return get_ValueAsString(COLUMNNAME_ISO_Code);
	}

	@Override
	public void setIsTaxExempt (final boolean IsTaxExempt)
	{
		set_ValueNoCheck (COLUMNNAME_IsTaxExempt, IsTaxExempt);
	}

	@Override
	public boolean isTaxExempt() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsTaxExempt);
	}

	@Override
	public void setLeergut (final @Nullable java.lang.String Leergut)
	{
		set_Value (COLUMNNAME_Leergut, Leergut);
	}

	@Override
	public java.lang.String getLeergut() 
	{
		return get_ValueAsString(COLUMNNAME_Leergut);
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
	public void setLineNetAmt (final @Nullable BigDecimal LineNetAmt)
	{
		set_Value (COLUMNNAME_LineNetAmt, LineNetAmt);
	}

	@Override
	public BigDecimal getLineNetAmt() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_LineNetAmt);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setName (final @Nullable java.lang.String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	@Override
	public java.lang.String getName() 
	{
		return get_ValueAsString(COLUMNNAME_Name);
	}

	@Override
	public void setName2 (final @Nullable java.lang.String Name2)
	{
		set_Value (COLUMNNAME_Name2, Name2);
	}

	@Override
	public java.lang.String getName2() 
	{
		return get_ValueAsString(COLUMNNAME_Name2);
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
	public void setOrderPOReference (final @Nullable java.lang.String OrderPOReference)
	{
		set_ValueNoCheck (COLUMNNAME_OrderPOReference, OrderPOReference);
	}

	@Override
	public java.lang.String getOrderPOReference() 
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
	public void setPriceList (final @Nullable BigDecimal PriceList)
	{
		set_Value (COLUMNNAME_PriceList, PriceList);
	}

	@Override
	public BigDecimal getPriceList() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_PriceList);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setProductDescription (final @Nullable java.lang.String ProductDescription)
	{
		set_Value (COLUMNNAME_ProductDescription, ProductDescription);
	}

	@Override
	public java.lang.String getProductDescription() 
	{
		return get_ValueAsString(COLUMNNAME_ProductDescription);
	}

	@Override
	public void setQtyEnteredInBPartnerUOM (final @Nullable BigDecimal QtyEnteredInBPartnerUOM)
	{
		set_ValueNoCheck (COLUMNNAME_QtyEnteredInBPartnerUOM, QtyEnteredInBPartnerUOM);
	}

	@Override
	public BigDecimal getQtyEnteredInBPartnerUOM() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyEnteredInBPartnerUOM);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyInvoiced (final @Nullable BigDecimal QtyInvoiced)
	{
		set_Value (COLUMNNAME_QtyInvoiced, QtyInvoiced);
	}

	@Override
	public BigDecimal getQtyInvoiced() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyInvoiced);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyInvoicedInOrderedUOM (final @Nullable BigDecimal QtyInvoicedInOrderedUOM)
	{
		set_ValueNoCheck (COLUMNNAME_QtyInvoicedInOrderedUOM, QtyInvoicedInOrderedUOM);
	}

	@Override
	public BigDecimal getQtyInvoicedInOrderedUOM() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyInvoicedInOrderedUOM);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setRate (final @Nullable BigDecimal Rate)
	{
		set_Value (COLUMNNAME_Rate, Rate);
	}

	@Override
	public BigDecimal getRate() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_Rate);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setSupplier_GTIN_CU (final @Nullable java.lang.String Supplier_GTIN_CU)
	{
		set_ValueNoCheck (COLUMNNAME_Supplier_GTIN_CU, Supplier_GTIN_CU);
	}

	@Override
	public java.lang.String getSupplier_GTIN_CU() 
	{
		return get_ValueAsString(COLUMNNAME_Supplier_GTIN_CU);
	}

	@Override
	public void setTaxAmtInfo (final @Nullable BigDecimal TaxAmtInfo)
	{
		set_Value (COLUMNNAME_TaxAmtInfo, TaxAmtInfo);
	}

	@Override
	public BigDecimal getTaxAmtInfo() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_TaxAmtInfo);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void settaxfree (final boolean taxfree)
	{
		set_Value (COLUMNNAME_taxfree, taxfree);
	}

	@Override
	public boolean istaxfree() 
	{
		return get_ValueAsBoolean(COLUMNNAME_taxfree);
	}

	@Override
	public void setUPC_CU (final @Nullable java.lang.String UPC_CU)
	{
		set_ValueNoCheck (COLUMNNAME_UPC_CU, UPC_CU);
	}

	@Override
	public java.lang.String getUPC_CU() 
	{
		return get_ValueAsString(COLUMNNAME_UPC_CU);
	}

	@Override
	public void setUPC_TU (final @Nullable java.lang.String UPC_TU)
	{
		set_ValueNoCheck (COLUMNNAME_UPC_TU, UPC_TU);
	}

	@Override
	public java.lang.String getUPC_TU() 
	{
		return get_ValueAsString(COLUMNNAME_UPC_TU);
	}

	@Override
	public void setValue (final @Nullable java.lang.String Value)
	{
		set_Value (COLUMNNAME_Value, Value);
	}

	@Override
	public java.lang.String getValue() 
	{
		return get_ValueAsString(COLUMNNAME_Value);
	}
}