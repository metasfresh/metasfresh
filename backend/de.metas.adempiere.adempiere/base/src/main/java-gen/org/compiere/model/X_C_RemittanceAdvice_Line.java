/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_RemittanceAdvice_Line
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_RemittanceAdvice_Line extends org.compiere.model.PO implements I_C_RemittanceAdvice_Line, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 1398935408L;

    /** Standard Constructor */
    public X_C_RemittanceAdvice_Line (final Properties ctx, final int C_RemittanceAdvice_Line_ID, final String trxName)
    {
      super (ctx, C_RemittanceAdvice_Line_ID, trxName);
    }

    /** Load Constructor */
    public X_C_RemittanceAdvice_Line (final Properties ctx, final ResultSet rs, final String trxName)
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
	public void setAdditionalNotes (final java.lang.String AdditionalNotes)
	{
		set_Value (COLUMNNAME_AdditionalNotes, AdditionalNotes);
	}

	@Override
	public java.lang.String getAdditionalNotes() 
	{
		return get_ValueAsString(COLUMNNAME_AdditionalNotes);
	}

	@Override
	public void setBill_BPartner_ID (final int Bill_BPartner_ID)
	{
		if (Bill_BPartner_ID < 1) 
			set_Value (COLUMNNAME_Bill_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_Bill_BPartner_ID, Bill_BPartner_ID);
	}

	@Override
	public int getBill_BPartner_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Bill_BPartner_ID);
	}

	@Override
	public void setC_BPartner_ID (final int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_ID, C_BPartner_ID);
	}

	@Override
	public int getC_BPartner_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_ID);
	}

	@Override
	public void setC_Invoice_Currency_ID (final int C_Invoice_Currency_ID)
	{
		if (C_Invoice_Currency_ID < 1) 
			set_Value (COLUMNNAME_C_Invoice_Currency_ID, null);
		else 
			set_Value (COLUMNNAME_C_Invoice_Currency_ID, C_Invoice_Currency_ID);
	}

	@Override
	public int getC_Invoice_Currency_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Invoice_Currency_ID);
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
	public org.compiere.model.I_C_RemittanceAdvice getC_RemittanceAdvice()
	{
		return get_ValueAsPO(COLUMNNAME_C_RemittanceAdvice_ID, org.compiere.model.I_C_RemittanceAdvice.class);
	}

	@Override
	public void setC_RemittanceAdvice(final org.compiere.model.I_C_RemittanceAdvice C_RemittanceAdvice)
	{
		set_ValueFromPO(COLUMNNAME_C_RemittanceAdvice_ID, org.compiere.model.I_C_RemittanceAdvice.class, C_RemittanceAdvice);
	}

	@Override
	public void setC_RemittanceAdvice_ID (final int C_RemittanceAdvice_ID)
	{
		if (C_RemittanceAdvice_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_RemittanceAdvice_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_RemittanceAdvice_ID, C_RemittanceAdvice_ID);
	}

	@Override
	public int getC_RemittanceAdvice_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_RemittanceAdvice_ID);
	}

	@Override
	public void setC_RemittanceAdvice_Line_ID (final int C_RemittanceAdvice_Line_ID)
	{
		if (C_RemittanceAdvice_Line_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_RemittanceAdvice_Line_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_RemittanceAdvice_Line_ID, C_RemittanceAdvice_Line_ID);
	}

	@Override
	public int getC_RemittanceAdvice_Line_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_RemittanceAdvice_Line_ID);
	}

	@Override
	public void setExternalInvoiceDocBaseType (final java.lang.String ExternalInvoiceDocBaseType)
	{
		set_Value (COLUMNNAME_ExternalInvoiceDocBaseType, ExternalInvoiceDocBaseType);
	}

	@Override
	public java.lang.String getExternalInvoiceDocBaseType() 
	{
		return get_ValueAsString(COLUMNNAME_ExternalInvoiceDocBaseType);
	}

	@Override
	public void setInvoiceAmt (final BigDecimal InvoiceAmt)
	{
		set_Value (COLUMNNAME_InvoiceAmt, InvoiceAmt);
	}

	@Override
	public BigDecimal getInvoiceAmt() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_InvoiceAmt);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setInvoiceAmtInREMADVCurrency (final BigDecimal InvoiceAmtInREMADVCurrency)
	{
		set_Value (COLUMNNAME_InvoiceAmtInREMADVCurrency, InvoiceAmtInREMADVCurrency);
	}

	@Override
	public BigDecimal getInvoiceAmtInREMADVCurrency() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_InvoiceAmtInREMADVCurrency);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setInvoiceDate (final java.sql.Timestamp InvoiceDate)
	{
		set_Value (COLUMNNAME_InvoiceDate, InvoiceDate);
	}

	@Override
	public java.sql.Timestamp getInvoiceDate() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_InvoiceDate);
	}

	@Override
	public void setInvoiceGrossAmount (final BigDecimal InvoiceGrossAmount)
	{
		set_Value (COLUMNNAME_InvoiceGrossAmount, InvoiceGrossAmount);
	}

	@Override
	public BigDecimal getInvoiceGrossAmount() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_InvoiceGrossAmount);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setInvoiceIdentifier (final java.lang.String InvoiceIdentifier)
	{
		set_Value (COLUMNNAME_InvoiceIdentifier, InvoiceIdentifier);
	}

	@Override
	public java.lang.String getInvoiceIdentifier() 
	{
		return get_ValueAsString(COLUMNNAME_InvoiceIdentifier);
	}

	@Override
	public void setIsAmountValid (final boolean IsAmountValid)
	{
		set_Value (COLUMNNAME_IsAmountValid, IsAmountValid);
	}

	@Override
	public boolean isAmountValid() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsAmountValid);
	}

	@Override
	public void setIsBPartnerValid (final boolean IsBPartnerValid)
	{
		set_Value (COLUMNNAME_IsBPartnerValid, IsBPartnerValid);
	}

	@Override
	public boolean isBPartnerValid() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsBPartnerValid);
	}

	@Override
	public void setIsInvoiceDateValid (final boolean IsInvoiceDateValid)
	{
		set_Value (COLUMNNAME_IsInvoiceDateValid, IsInvoiceDateValid);
	}

	@Override
	public boolean isInvoiceDateValid() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsInvoiceDateValid);
	}

	@Override
	public void setIsInvoiceDocTypeValid (final boolean IsInvoiceDocTypeValid)
	{
		set_Value (COLUMNNAME_IsInvoiceDocTypeValid, IsInvoiceDocTypeValid);
	}

	@Override
	public boolean isInvoiceDocTypeValid() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsInvoiceDocTypeValid);
	}

	@Override
	public void setIsInvoiceResolved (final boolean IsInvoiceResolved)
	{
		set_Value (COLUMNNAME_IsInvoiceResolved, IsInvoiceResolved);
	}

	@Override
	public boolean isInvoiceResolved() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsInvoiceResolved);
	}

	@Override
	public void setIsLineAcknowledged (final boolean IsLineAcknowledged)
	{
		set_Value (COLUMNNAME_IsLineAcknowledged, IsLineAcknowledged);
	}

	@Override
	public boolean isLineAcknowledged() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsLineAcknowledged);
	}

	@Override
	public void setIsServiceColumnsResolved (final boolean IsServiceColumnsResolved)
	{
		set_Value (COLUMNNAME_IsServiceColumnsResolved, IsServiceColumnsResolved);
	}

	@Override
	public boolean isServiceColumnsResolved() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsServiceColumnsResolved);
	}

	@Override
	public void setIsServiceFeeVatRateValid (final boolean IsServiceFeeVatRateValid)
	{
		set_Value (COLUMNNAME_IsServiceFeeVatRateValid, IsServiceFeeVatRateValid);
	}

	@Override
	public boolean isServiceFeeVatRateValid() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsServiceFeeVatRateValid);
	}

	@Override
	public void setOverUnderAmt (final BigDecimal OverUnderAmt)
	{
		set_Value (COLUMNNAME_OverUnderAmt, OverUnderAmt);
	}

	@Override
	public BigDecimal getOverUnderAmt() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_OverUnderAmt);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setPaymentDiscountAmt (final BigDecimal PaymentDiscountAmt)
	{
		set_Value (COLUMNNAME_PaymentDiscountAmt, PaymentDiscountAmt);
	}

	@Override
	public BigDecimal getPaymentDiscountAmt() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_PaymentDiscountAmt);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setProcessed (final boolean Processed)
	{
		set_Value (COLUMNNAME_Processed, Processed);
	}

	@Override
	public boolean isProcessed() 
	{
		return get_ValueAsBoolean(COLUMNNAME_Processed);
	}

	@Override
	public void setRemittanceAmt (final BigDecimal RemittanceAmt)
	{
		set_Value (COLUMNNAME_RemittanceAmt, RemittanceAmt);
	}

	@Override
	public BigDecimal getRemittanceAmt() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_RemittanceAmt);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setService_BPartner_ID (final int Service_BPartner_ID)
	{
		if (Service_BPartner_ID < 1) 
			set_Value (COLUMNNAME_Service_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_Service_BPartner_ID, Service_BPartner_ID);
	}

	@Override
	public int getService_BPartner_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Service_BPartner_ID);
	}

	@Override
	public org.compiere.model.I_C_Invoice getService_Fee_Invoice()
	{
		return get_ValueAsPO(COLUMNNAME_Service_Fee_Invoice_ID, org.compiere.model.I_C_Invoice.class);
	}

	@Override
	public void setService_Fee_Invoice(final org.compiere.model.I_C_Invoice Service_Fee_Invoice)
	{
		set_ValueFromPO(COLUMNNAME_Service_Fee_Invoice_ID, org.compiere.model.I_C_Invoice.class, Service_Fee_Invoice);
	}

	@Override
	public void setService_Fee_Invoice_ID (final int Service_Fee_Invoice_ID)
	{
		if (Service_Fee_Invoice_ID < 1) 
			set_Value (COLUMNNAME_Service_Fee_Invoice_ID, null);
		else 
			set_Value (COLUMNNAME_Service_Fee_Invoice_ID, Service_Fee_Invoice_ID);
	}

	@Override
	public int getService_Fee_Invoice_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Service_Fee_Invoice_ID);
	}

	@Override
	public void setService_Product_ID (final int Service_Product_ID)
	{
		if (Service_Product_ID < 1) 
			set_Value (COLUMNNAME_Service_Product_ID, null);
		else 
			set_Value (COLUMNNAME_Service_Product_ID, Service_Product_ID);
	}

	@Override
	public int getService_Product_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Service_Product_ID);
	}

	@Override
	public void setService_Tax_ID (final int Service_Tax_ID)
	{
		if (Service_Tax_ID < 1) 
			set_Value (COLUMNNAME_Service_Tax_ID, null);
		else 
			set_Value (COLUMNNAME_Service_Tax_ID, Service_Tax_ID);
	}

	@Override
	public int getService_Tax_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Service_Tax_ID);
	}

	@Override
	public void setServiceFeeAmount (final BigDecimal ServiceFeeAmount)
	{
		set_Value (COLUMNNAME_ServiceFeeAmount, ServiceFeeAmount);
	}

	@Override
	public BigDecimal getServiceFeeAmount() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_ServiceFeeAmount);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setServiceFeeVatRate (final BigDecimal ServiceFeeVatRate)
	{
		set_Value (COLUMNNAME_ServiceFeeVatRate, ServiceFeeVatRate);
	}

	@Override
	public BigDecimal getServiceFeeVatRate() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_ServiceFeeVatRate);
		return bd != null ? bd : BigDecimal.ZERO;
	}
}