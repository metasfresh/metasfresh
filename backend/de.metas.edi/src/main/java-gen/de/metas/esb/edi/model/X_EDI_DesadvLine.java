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

<<<<<<< HEAD
	private static final long serialVersionUID = -1729503778L;
=======
	private static final long serialVersionUID = 137682731L;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

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
<<<<<<< HEAD
	public BigDecimal getBPartner_QtyItemCapacity()
=======
	public BigDecimal getBPartner_QtyItemCapacity() 
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_BPartner_QtyItemCapacity);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setC_UOM_BPartner_ID (final int C_UOM_BPartner_ID)
	{
<<<<<<< HEAD
		if (C_UOM_BPartner_ID < 1)
			set_Value (COLUMNNAME_C_UOM_BPartner_ID, null);
		else
=======
		if (C_UOM_BPartner_ID < 1) 
			set_Value (COLUMNNAME_C_UOM_BPartner_ID, null);
		else 
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
			set_Value (COLUMNNAME_C_UOM_BPartner_ID, C_UOM_BPartner_ID);
	}

	@Override
<<<<<<< HEAD
	public int getC_UOM_BPartner_ID()
=======
	public int getC_UOM_BPartner_ID() 
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
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
<<<<<<< HEAD
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
=======
	public void setEanCom_Invoice_UOM (final @Nullable java.lang.String EanCom_Invoice_UOM)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		throw new IllegalArgumentException ("EanCom_Invoice_UOM is virtual column");	}

	@Override
<<<<<<< HEAD
	public String getEanCom_Invoice_UOM()
=======
	public java.lang.String getEanCom_Invoice_UOM() 
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		return get_ValueAsString(COLUMNNAME_EanCom_Invoice_UOM);
	}

	@Override
<<<<<<< HEAD
	public I_EDI_Desadv getEDI_Desadv()
	{
		return get_ValueAsPO(COLUMNNAME_EDI_Desadv_ID, I_EDI_Desadv.class);
	}

	@Override
	public void setEDI_Desadv(final I_EDI_Desadv EDI_Desadv)
	{
		set_ValueFromPO(COLUMNNAME_EDI_Desadv_ID, I_EDI_Desadv.class, EDI_Desadv);
=======
	public void setEAN_CU (final @Nullable java.lang.String EAN_CU)
	{
		set_Value (COLUMNNAME_EAN_CU, EAN_CU);
	}

	@Override
	public java.lang.String getEAN_CU() 
	{
		return get_ValueAsString(COLUMNNAME_EAN_CU);
	}

	@Override
	public void setEAN_TU (final @Nullable java.lang.String EAN_TU)
	{
		set_Value (COLUMNNAME_EAN_TU, EAN_TU);
	}

	@Override
	public java.lang.String getEAN_TU() 
	{
		return get_ValueAsString(COLUMNNAME_EAN_TU);
	}

	@Override
	public de.metas.esb.edi.model.I_EDI_Desadv getEDI_Desadv()
	{
		return get_ValueAsPO(COLUMNNAME_EDI_Desadv_ID, de.metas.esb.edi.model.I_EDI_Desadv.class);
	}

	@Override
	public void setEDI_Desadv(final de.metas.esb.edi.model.I_EDI_Desadv EDI_Desadv)
	{
		set_ValueFromPO(COLUMNNAME_EDI_Desadv_ID, de.metas.esb.edi.model.I_EDI_Desadv.class, EDI_Desadv);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
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
<<<<<<< HEAD
	public int getExternalSeqNo()
=======
	public int getExternalSeqNo() 
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		return get_ValueAsInt(COLUMNNAME_ExternalSeqNo);
	}

	@Override
<<<<<<< HEAD
	public void setGTIN (final @Nullable String GTIN)
	{
		set_Value (COLUMNNAME_GTIN, GTIN);
	}

	@Override
	public String getGTIN()
	{
		return get_ValueAsString(COLUMNNAME_GTIN);
=======
	public void setGTIN_CU (final @Nullable java.lang.String GTIN_CU)
	{
		set_Value (COLUMNNAME_GTIN_CU, GTIN_CU);
	}

	@Override
	public java.lang.String getGTIN_CU() 
	{
		return get_ValueAsString(COLUMNNAME_GTIN_CU);
	}

	@Override
	public void setGTIN_TU (final @Nullable java.lang.String GTIN_TU)
	{
		set_Value (COLUMNNAME_GTIN_TU, GTIN_TU);
	}

	@Override
	public java.lang.String getGTIN_TU() 
	{
		return get_ValueAsString(COLUMNNAME_GTIN_TU);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
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
<<<<<<< HEAD
	public void setInvoicableQtyBasedOn (final String InvoicableQtyBasedOn)
=======
	public void setInvoicableQtyBasedOn (final java.lang.String InvoicableQtyBasedOn)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		set_Value (COLUMNNAME_InvoicableQtyBasedOn, InvoicableQtyBasedOn);
	}

	@Override
<<<<<<< HEAD
	public String getInvoicableQtyBasedOn()
=======
	public java.lang.String getInvoicableQtyBasedOn() 
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		return get_ValueAsString(COLUMNNAME_InvoicableQtyBasedOn);
	}

	@Override
<<<<<<< HEAD
=======
	public void setIsDeliveryClosed (final boolean IsDeliveryClosed)
	{
		throw new IllegalArgumentException ("IsDeliveryClosed is virtual column");	}

	@Override
	public boolean isDeliveryClosed() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsDeliveryClosed);
	}

	@Override
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
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
<<<<<<< HEAD
	public void setOrderPOReference (final @Nullable String OrderPOReference)
=======
	public void setOrderPOReference (final @Nullable java.lang.String OrderPOReference)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		set_Value (COLUMNNAME_OrderPOReference, OrderPOReference);
	}

	@Override
<<<<<<< HEAD
	public String getOrderPOReference()
=======
	public java.lang.String getOrderPOReference() 
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
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
<<<<<<< HEAD
	public void setProductDescription (final @Nullable String ProductDescription)
=======
	public void setProductDescription (final @Nullable java.lang.String ProductDescription)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		set_Value (COLUMNNAME_ProductDescription, ProductDescription);
	}

	@Override
<<<<<<< HEAD
	public String getProductDescription()
=======
	public java.lang.String getProductDescription() 
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		return get_ValueAsString(COLUMNNAME_ProductDescription);
	}

	@Override
<<<<<<< HEAD
	public void setProductNo (final @Nullable String ProductNo)
=======
	public void setProductNo (final @Nullable java.lang.String ProductNo)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		set_Value (COLUMNNAME_ProductNo, ProductNo);
	}

	@Override
<<<<<<< HEAD
	public String getProductNo()
=======
	public java.lang.String getProductNo() 
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
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
<<<<<<< HEAD
	public BigDecimal getQtyEnteredInBPartnerUOM()
=======
	public BigDecimal getQtyEnteredInBPartnerUOM() 
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
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
<<<<<<< HEAD
	public void setUPC_CU (final @Nullable String UPC_CU)
=======
	public void setQtyOrdered_Override (final @Nullable BigDecimal QtyOrdered_Override)
	{
		set_Value (COLUMNNAME_QtyOrdered_Override, QtyOrdered_Override);
	}

	@Override
	public BigDecimal getQtyOrdered_Override() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyOrdered_Override);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setUPC_CU (final @Nullable java.lang.String UPC_CU)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		set_Value (COLUMNNAME_UPC_CU, UPC_CU);
	}

	@Override
<<<<<<< HEAD
	public String getUPC_CU()
=======
	public java.lang.String getUPC_CU() 
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		return get_ValueAsString(COLUMNNAME_UPC_CU);
	}

	@Override
<<<<<<< HEAD
	public void setUPC_TU (final @Nullable String UPC_TU)
=======
	public void setUPC_TU (final @Nullable java.lang.String UPC_TU)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		set_Value (COLUMNNAME_UPC_TU, UPC_TU);
	}

	@Override
<<<<<<< HEAD
	public String getUPC_TU()
=======
	public java.lang.String getUPC_TU() 
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		return get_ValueAsString(COLUMNNAME_UPC_TU);
	}
}