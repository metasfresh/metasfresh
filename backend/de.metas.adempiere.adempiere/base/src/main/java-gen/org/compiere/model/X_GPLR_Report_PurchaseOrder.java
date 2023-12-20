// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for GPLR_Report_PurchaseOrder
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_GPLR_Report_PurchaseOrder extends org.compiere.model.PO implements I_GPLR_Report_PurchaseOrder, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -729263735L;

    /** Standard Constructor */
    public X_GPLR_Report_PurchaseOrder (final Properties ctx, final int GPLR_Report_PurchaseOrder_ID, @Nullable final String trxName)
    {
      super (ctx, GPLR_Report_PurchaseOrder_ID, trxName);
    }

    /** Load Constructor */
    public X_GPLR_Report_PurchaseOrder (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setBPartnerName (final java.lang.String BPartnerName)
	{
		set_Value (COLUMNNAME_BPartnerName, BPartnerName);
	}

	@Override
	public java.lang.String getBPartnerName() 
	{
		return get_ValueAsString(COLUMNNAME_BPartnerName);
	}

	@Override
	public void setBPartnerValue (final java.lang.String BPartnerValue)
	{
		set_Value (COLUMNNAME_BPartnerValue, BPartnerValue);
	}

	@Override
	public java.lang.String getBPartnerValue() 
	{
		return get_ValueAsString(COLUMNNAME_BPartnerValue);
	}

	@Override
	public void setBPartner_VatId (final @Nullable java.lang.String BPartner_VatId)
	{
		set_Value (COLUMNNAME_BPartner_VatId, BPartner_VatId);
	}

	@Override
	public java.lang.String getBPartner_VatId() 
	{
		return get_ValueAsString(COLUMNNAME_BPartner_VatId);
	}

	@Override
	public void setCurrencyRate (final @Nullable BigDecimal CurrencyRate)
	{
		set_Value (COLUMNNAME_CurrencyRate, CurrencyRate);
	}

	@Override
	public BigDecimal getCurrencyRate() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_CurrencyRate);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setDocumentNo (final java.lang.String DocumentNo)
	{
		set_Value (COLUMNNAME_DocumentNo, DocumentNo);
	}

	@Override
	public java.lang.String getDocumentNo() 
	{
		return get_ValueAsString(COLUMNNAME_DocumentNo);
	}

	@Override
	public void setFEC_DocumentNo (final @Nullable java.lang.String FEC_DocumentNo)
	{
		set_Value (COLUMNNAME_FEC_DocumentNo, FEC_DocumentNo);
	}

	@Override
	public java.lang.String getFEC_DocumentNo() 
	{
		return get_ValueAsString(COLUMNNAME_FEC_DocumentNo);
	}

	@Override
	public void setForeignCurrencyCode (final java.lang.String ForeignCurrencyCode)
	{
		set_Value (COLUMNNAME_ForeignCurrencyCode, ForeignCurrencyCode);
	}

	@Override
	public java.lang.String getForeignCurrencyCode() 
	{
		return get_ValueAsString(COLUMNNAME_ForeignCurrencyCode);
	}

	@Override
	public org.compiere.model.I_GPLR_Report getGPLR_Report()
	{
		return get_ValueAsPO(COLUMNNAME_GPLR_Report_ID, org.compiere.model.I_GPLR_Report.class);
	}

	@Override
	public void setGPLR_Report(final org.compiere.model.I_GPLR_Report GPLR_Report)
	{
		set_ValueFromPO(COLUMNNAME_GPLR_Report_ID, org.compiere.model.I_GPLR_Report.class, GPLR_Report);
	}

	@Override
	public void setGPLR_Report_ID (final int GPLR_Report_ID)
	{
		if (GPLR_Report_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_GPLR_Report_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_GPLR_Report_ID, GPLR_Report_ID);
	}

	@Override
	public int getGPLR_Report_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_GPLR_Report_ID);
	}

	@Override
	public void setGPLR_Report_PurchaseOrder_ID (final int GPLR_Report_PurchaseOrder_ID)
	{
		if (GPLR_Report_PurchaseOrder_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_GPLR_Report_PurchaseOrder_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_GPLR_Report_PurchaseOrder_ID, GPLR_Report_PurchaseOrder_ID);
	}

	@Override
	public int getGPLR_Report_PurchaseOrder_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_GPLR_Report_PurchaseOrder_ID);
	}

	@Override
	public void setIncoterm_Code (final @Nullable java.lang.String Incoterm_Code)
	{
		set_Value (COLUMNNAME_Incoterm_Code, Incoterm_Code);
	}

	@Override
	public java.lang.String getIncoterm_Code() 
	{
		return get_ValueAsString(COLUMNNAME_Incoterm_Code);
	}

	@Override
	public void setIncotermLocation (final @Nullable java.lang.String IncotermLocation)
	{
		set_Value (COLUMNNAME_IncotermLocation, IncotermLocation);
	}

	@Override
	public java.lang.String getIncotermLocation() 
	{
		return get_ValueAsString(COLUMNNAME_IncotermLocation);
	}

	@Override
	public void setPaymentTermInfo (final java.lang.String PaymentTermInfo)
	{
		set_Value (COLUMNNAME_PaymentTermInfo, PaymentTermInfo);
	}

	@Override
	public java.lang.String getPaymentTermInfo() 
	{
		return get_ValueAsString(COLUMNNAME_PaymentTermInfo);
	}

	@Override
	public void setVendorReferenceNo (final @Nullable java.lang.String VendorReferenceNo)
	{
		set_Value (COLUMNNAME_VendorReferenceNo, VendorReferenceNo);
	}

	@Override
	public java.lang.String getVendorReferenceNo() 
	{
		return get_ValueAsString(COLUMNNAME_VendorReferenceNo);
	}
}