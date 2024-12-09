<<<<<<< HEAD
/** Generated Model - DO NOT CHANGE */
package de.metas.esb.edi.model;

=======
// Generated Model - DO NOT CHANGE
package de.metas.esb.edi.model;

import javax.annotation.Nullable;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for EDI_cctop_invoic_500_v
 *  @author metasfresh (generated) 
 */
<<<<<<< HEAD
@SuppressWarnings("javadoc")
public class X_EDI_cctop_invoic_500_v extends org.compiere.model.PO implements I_EDI_cctop_invoic_500_v, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 228915322L;

    /** Standard Constructor */
    public X_EDI_cctop_invoic_500_v (Properties ctx, int EDI_cctop_invoic_500_v_ID, String trxName)
=======
@SuppressWarnings("unused")
public class X_EDI_cctop_invoic_500_v extends org.compiere.model.PO implements I_EDI_cctop_invoic_500_v, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 1125461297L;

    /** Standard Constructor */
    public X_EDI_cctop_invoic_500_v (final Properties ctx, final int EDI_cctop_invoic_500_v_ID, @Nullable final String trxName)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
    {
      super (ctx, EDI_cctop_invoic_500_v_ID, trxName);
    }

    /** Load Constructor */
<<<<<<< HEAD
    public X_EDI_cctop_invoic_500_v (Properties ctx, ResultSet rs, String trxName)
=======
    public X_EDI_cctop_invoic_500_v (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
    {
      super (ctx, rs, trxName);
    }


	/** Load Meta Data */
	@Override
<<<<<<< HEAD
	protected org.compiere.model.POInfo initPO(Properties ctx)
=======
	protected org.compiere.model.POInfo initPO(final Properties ctx)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		return org.compiere.model.POInfo.getPOInfo(Table_Name);
	}

	@Override
<<<<<<< HEAD
=======
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
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	public org.compiere.model.I_C_Invoice getC_Invoice()
	{
		return get_ValueAsPO(COLUMNNAME_C_Invoice_ID, org.compiere.model.I_C_Invoice.class);
	}

	@Override
<<<<<<< HEAD
	public void setC_Invoice(org.compiere.model.I_C_Invoice C_Invoice)
=======
	public void setC_Invoice(final org.compiere.model.I_C_Invoice C_Invoice)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		set_ValueFromPO(COLUMNNAME_C_Invoice_ID, org.compiere.model.I_C_Invoice.class, C_Invoice);
	}

	@Override
<<<<<<< HEAD
	public void setC_Invoice_ID (int C_Invoice_ID)
=======
	public void setC_Invoice_ID (final int C_Invoice_ID)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		if (C_Invoice_ID < 1) 
			set_Value (COLUMNNAME_C_Invoice_ID, null);
		else 
<<<<<<< HEAD
			set_Value (COLUMNNAME_C_Invoice_ID, Integer.valueOf(C_Invoice_ID));
=======
			set_Value (COLUMNNAME_C_Invoice_ID, C_Invoice_ID);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	}

	@Override
	public int getC_Invoice_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Invoice_ID);
	}

<<<<<<< HEAD
=======
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

>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	/** 
	 * EanCom_Ordered_UOM AD_Reference_ID=114
	 * Reference name: C_UOM_Default_First
	 */
	public static final int EANCOM_ORDERED_UOM_AD_Reference_ID=114;
	@Override
<<<<<<< HEAD
	public void setEanCom_Ordered_UOM (java.lang.String EanCom_Ordered_UOM)
	{

=======
	public void setEanCom_Ordered_UOM (final @Nullable java.lang.String EanCom_Ordered_UOM)
	{
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		set_ValueNoCheck (COLUMNNAME_EanCom_Ordered_UOM, EanCom_Ordered_UOM);
	}

	@Override
	public java.lang.String getEanCom_Ordered_UOM() 
	{
<<<<<<< HEAD
		return (java.lang.String)get_Value(COLUMNNAME_EanCom_Ordered_UOM);
	}

	@Override
	public void setEanCom_Price_UOM (java.lang.String EanCom_Price_UOM)
=======
		return get_ValueAsString(COLUMNNAME_EanCom_Ordered_UOM);
	}

	@Override
	public void setEanCom_Price_UOM (final @Nullable java.lang.String EanCom_Price_UOM)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		set_ValueNoCheck (COLUMNNAME_EanCom_Price_UOM, EanCom_Price_UOM);
	}

	@Override
	public java.lang.String getEanCom_Price_UOM() 
	{
<<<<<<< HEAD
		return (java.lang.String)get_Value(COLUMNNAME_EanCom_Price_UOM);
	}

	@Override
	public void setEanCom_UOM (java.lang.String EanCom_UOM)
=======
		return get_ValueAsString(COLUMNNAME_EanCom_Price_UOM);
	}

	@Override
	public void setEanCom_UOM (final @Nullable java.lang.String EanCom_UOM)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		set_Value (COLUMNNAME_EanCom_UOM, EanCom_UOM);
	}

	@Override
	public java.lang.String getEanCom_UOM() 
	{
<<<<<<< HEAD
		return (java.lang.String)get_Value(COLUMNNAME_EanCom_UOM);
	}

	@Override
	public void setEAN_CU (java.lang.String EAN_CU)
=======
		return get_ValueAsString(COLUMNNAME_EanCom_UOM);
	}

	@Override
	public void setEAN_CU (final @Nullable java.lang.String EAN_CU)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		set_ValueNoCheck (COLUMNNAME_EAN_CU, EAN_CU);
	}

	@Override
	public java.lang.String getEAN_CU() 
	{
<<<<<<< HEAD
		return (java.lang.String)get_Value(COLUMNNAME_EAN_CU);
	}

	@Override
	public void setEAN_TU (java.lang.String EAN_TU)
=======
		return get_ValueAsString(COLUMNNAME_EAN_CU);
	}

	@Override
	public void setEAN_TU (final @Nullable java.lang.String EAN_TU)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		set_ValueNoCheck (COLUMNNAME_EAN_TU, EAN_TU);
	}

	@Override
	public java.lang.String getEAN_TU() 
	{
<<<<<<< HEAD
		return (java.lang.String)get_Value(COLUMNNAME_EAN_TU);
	}

	@Override
	public void setEDI_cctop_invoic_500_v_ID (int EDI_cctop_invoic_500_v_ID)
=======
		return get_ValueAsString(COLUMNNAME_EAN_TU);
	}

	@Override
	public void setEDI_cctop_invoic_500_v_ID (final int EDI_cctop_invoic_500_v_ID)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		if (EDI_cctop_invoic_500_v_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_EDI_cctop_invoic_500_v_ID, null);
		else 
<<<<<<< HEAD
			set_ValueNoCheck (COLUMNNAME_EDI_cctop_invoic_500_v_ID, Integer.valueOf(EDI_cctop_invoic_500_v_ID));
=======
			set_ValueNoCheck (COLUMNNAME_EDI_cctop_invoic_500_v_ID, EDI_cctop_invoic_500_v_ID);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
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
<<<<<<< HEAD
	public void setEDI_cctop_invoic_v(de.metas.esb.edi.model.I_EDI_cctop_invoic_v EDI_cctop_invoic_v)
=======
	public void setEDI_cctop_invoic_v(final de.metas.esb.edi.model.I_EDI_cctop_invoic_v EDI_cctop_invoic_v)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		set_ValueFromPO(COLUMNNAME_EDI_cctop_invoic_v_ID, de.metas.esb.edi.model.I_EDI_cctop_invoic_v.class, EDI_cctop_invoic_v);
	}

	@Override
<<<<<<< HEAD
	public void setEDI_cctop_invoic_v_ID (int EDI_cctop_invoic_v_ID)
=======
	public void setEDI_cctop_invoic_v_ID (final int EDI_cctop_invoic_v_ID)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		if (EDI_cctop_invoic_v_ID < 1) 
			set_Value (COLUMNNAME_EDI_cctop_invoic_v_ID, null);
		else 
<<<<<<< HEAD
			set_Value (COLUMNNAME_EDI_cctop_invoic_v_ID, Integer.valueOf(EDI_cctop_invoic_v_ID));
=======
			set_Value (COLUMNNAME_EDI_cctop_invoic_v_ID, EDI_cctop_invoic_v_ID);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	}

	@Override
	public int getEDI_cctop_invoic_v_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_EDI_cctop_invoic_v_ID);
	}

	@Override
<<<<<<< HEAD
	public void setGTIN (java.lang.String GTIN)
=======
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
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		set_ValueNoCheck (COLUMNNAME_GTIN, GTIN);
	}

	@Override
	public java.lang.String getGTIN() 
	{
<<<<<<< HEAD
		return (java.lang.String)get_Value(COLUMNNAME_GTIN);
	}

	@Override
	public void setISO_Code (java.lang.String ISO_Code)
=======
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
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		set_Value (COLUMNNAME_ISO_Code, ISO_Code);
	}

	@Override
	public java.lang.String getISO_Code() 
	{
<<<<<<< HEAD
		return (java.lang.String)get_Value(COLUMNNAME_ISO_Code);
	}

	@Override
	public void setLeergut (java.lang.String Leergut)
=======
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
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		set_Value (COLUMNNAME_Leergut, Leergut);
	}

	@Override
	public java.lang.String getLeergut() 
	{
<<<<<<< HEAD
		return (java.lang.String)get_Value(COLUMNNAME_Leergut);
	}

	@Override
	public void setLine (int Line)
	{
		set_Value (COLUMNNAME_Line, Integer.valueOf(Line));
=======
		return get_ValueAsString(COLUMNNAME_Leergut);
	}

	@Override
	public void setLine (final int Line)
	{
		set_Value (COLUMNNAME_Line, Line);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	}

	@Override
	public int getLine() 
	{
		return get_ValueAsInt(COLUMNNAME_Line);
	}

	@Override
<<<<<<< HEAD
	public void setLineNetAmt (java.math.BigDecimal LineNetAmt)
=======
	public void setLineNetAmt (final @Nullable BigDecimal LineNetAmt)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		set_Value (COLUMNNAME_LineNetAmt, LineNetAmt);
	}

	@Override
<<<<<<< HEAD
	public java.math.BigDecimal getLineNetAmt() 
	{
		BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_LineNetAmt);
=======
	public BigDecimal getLineNetAmt() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_LineNetAmt);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
<<<<<<< HEAD
	public void setName (java.lang.String Name)
=======
	public void setName (final @Nullable java.lang.String Name)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	@Override
	public java.lang.String getName() 
	{
<<<<<<< HEAD
		return (java.lang.String)get_Value(COLUMNNAME_Name);
	}

	@Override
	public void setName2 (java.lang.String Name2)
=======
		return get_ValueAsString(COLUMNNAME_Name);
	}

	@Override
	public void setName2 (final @Nullable java.lang.String Name2)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		set_Value (COLUMNNAME_Name2, Name2);
	}

	@Override
	public java.lang.String getName2() 
	{
<<<<<<< HEAD
		return (java.lang.String)get_Value(COLUMNNAME_Name2);
	}

	@Override
	public void setOrderLine (int OrderLine)
	{
		set_Value (COLUMNNAME_OrderLine, Integer.valueOf(OrderLine));
=======
		return get_ValueAsString(COLUMNNAME_Name2);
	}

	@Override
	public void setOrderLine (final int OrderLine)
	{
		set_Value (COLUMNNAME_OrderLine, OrderLine);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	}

	@Override
	public int getOrderLine() 
	{
		return get_ValueAsInt(COLUMNNAME_OrderLine);
	}

	@Override
<<<<<<< HEAD
	public void setOrderPOReference (java.lang.String OrderPOReference)
=======
	public void setOrderPOReference (final @Nullable java.lang.String OrderPOReference)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		set_ValueNoCheck (COLUMNNAME_OrderPOReference, OrderPOReference);
	}

	@Override
	public java.lang.String getOrderPOReference() 
	{
<<<<<<< HEAD
		return (java.lang.String)get_Value(COLUMNNAME_OrderPOReference);
	}

	@Override
	public void setPriceActual (java.math.BigDecimal PriceActual)
=======
		return get_ValueAsString(COLUMNNAME_OrderPOReference);
	}

	@Override
	public void setPriceActual (final @Nullable BigDecimal PriceActual)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		set_Value (COLUMNNAME_PriceActual, PriceActual);
	}

	@Override
<<<<<<< HEAD
	public java.math.BigDecimal getPriceActual() 
	{
		BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_PriceActual);
=======
	public BigDecimal getPriceActual() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_PriceActual);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
<<<<<<< HEAD
	public void setPriceList (java.math.BigDecimal PriceList)
=======
	public void setPriceList (final @Nullable BigDecimal PriceList)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		set_Value (COLUMNNAME_PriceList, PriceList);
	}

	@Override
<<<<<<< HEAD
	public java.math.BigDecimal getPriceList() 
	{
		BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_PriceList);
=======
	public BigDecimal getPriceList() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_PriceList);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
<<<<<<< HEAD
	public void setProductDescription (java.lang.String ProductDescription)
=======
	public void setProductDescription (final @Nullable java.lang.String ProductDescription)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		set_Value (COLUMNNAME_ProductDescription, ProductDescription);
	}

	@Override
	public java.lang.String getProductDescription() 
	{
<<<<<<< HEAD
		return (java.lang.String)get_Value(COLUMNNAME_ProductDescription);
	}

	@Override
	public void setQtyInvoiced (java.math.BigDecimal QtyInvoiced)
=======
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
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		set_Value (COLUMNNAME_QtyInvoiced, QtyInvoiced);
	}

	@Override
<<<<<<< HEAD
	public java.math.BigDecimal getQtyInvoiced() 
	{
		BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyInvoiced);
=======
	public BigDecimal getQtyInvoiced() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyInvoiced);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
<<<<<<< HEAD
	public void setQtyInvoicedInOrderedUOM (java.math.BigDecimal QtyInvoicedInOrderedUOM)
=======
	public void setQtyInvoicedInOrderedUOM (final @Nullable BigDecimal QtyInvoicedInOrderedUOM)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		set_ValueNoCheck (COLUMNNAME_QtyInvoicedInOrderedUOM, QtyInvoicedInOrderedUOM);
	}

	@Override
<<<<<<< HEAD
	public java.math.BigDecimal getQtyInvoicedInOrderedUOM() 
	{
		BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyInvoicedInOrderedUOM);
=======
	public BigDecimal getQtyInvoicedInOrderedUOM() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyInvoicedInOrderedUOM);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
<<<<<<< HEAD
	public void setRate (java.math.BigDecimal Rate)
=======
	public void setRate (final @Nullable BigDecimal Rate)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		set_Value (COLUMNNAME_Rate, Rate);
	}

	@Override
<<<<<<< HEAD
	public java.math.BigDecimal getRate() 
	{
		BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_Rate);
=======
	public BigDecimal getRate() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_Rate);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
<<<<<<< HEAD
	public void setTaxAmtInfo (java.math.BigDecimal TaxAmtInfo)
=======
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
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		set_Value (COLUMNNAME_TaxAmtInfo, TaxAmtInfo);
	}

	@Override
<<<<<<< HEAD
	public java.math.BigDecimal getTaxAmtInfo() 
	{
		BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_TaxAmtInfo);
=======
	public BigDecimal getTaxAmtInfo() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_TaxAmtInfo);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
<<<<<<< HEAD
	public void settaxfree (boolean taxfree)
	{
		set_Value (COLUMNNAME_taxfree, Boolean.valueOf(taxfree));
=======
	public void settaxfree (final boolean taxfree)
	{
		set_Value (COLUMNNAME_taxfree, taxfree);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	}

	@Override
	public boolean istaxfree() 
	{
		return get_ValueAsBoolean(COLUMNNAME_taxfree);
	}

	@Override
<<<<<<< HEAD
	public void setUPC_CU (java.lang.String UPC_CU)
=======
	public void setUPC_CU (final @Nullable java.lang.String UPC_CU)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		set_ValueNoCheck (COLUMNNAME_UPC_CU, UPC_CU);
	}

	@Override
	public java.lang.String getUPC_CU() 
	{
<<<<<<< HEAD
		return (java.lang.String)get_Value(COLUMNNAME_UPC_CU);
	}

	@Override
	public void setUPC_TU (java.lang.String UPC_TU)
=======
		return get_ValueAsString(COLUMNNAME_UPC_CU);
	}

	@Override
	public void setUPC_TU (final @Nullable java.lang.String UPC_TU)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		set_ValueNoCheck (COLUMNNAME_UPC_TU, UPC_TU);
	}

	@Override
	public java.lang.String getUPC_TU() 
	{
<<<<<<< HEAD
		return (java.lang.String)get_Value(COLUMNNAME_UPC_TU);
	}

	@Override
	public void setValue (java.lang.String Value)
=======
		return get_ValueAsString(COLUMNNAME_UPC_TU);
	}

	@Override
	public void setValue (final @Nullable java.lang.String Value)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		set_Value (COLUMNNAME_Value, Value);
	}

	@Override
	public java.lang.String getValue() 
	{
<<<<<<< HEAD
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
=======
		return get_ValueAsString(COLUMNNAME_Value);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	}
}