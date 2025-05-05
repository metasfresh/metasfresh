// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for GPLR_Report
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_GPLR_Report extends org.compiere.model.PO implements I_GPLR_Report, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1651633625L;

    /** Standard Constructor */
    public X_GPLR_Report (final Properties ctx, final int GPLR_Report_ID, @Nullable final String trxName)
    {
      super (ctx, GPLR_Report_ID, trxName);
    }

    /** Load Constructor */
    public X_GPLR_Report (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setCreatedByName (final java.lang.String CreatedByName)
	{
		set_ValueNoCheck (COLUMNNAME_CreatedByName, CreatedByName);
	}

	@Override
	public java.lang.String getCreatedByName() 
	{
		return get_ValueAsString(COLUMNNAME_CreatedByName);
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
	public void setDateDoc (final @Nullable java.sql.Timestamp DateDoc)
	{
		set_Value (COLUMNNAME_DateDoc, DateDoc);
	}

	@Override
	public java.sql.Timestamp getDateDoc() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_DateDoc);
	}

	@Override
	public void setDepartmentName (final @Nullable java.lang.String DepartmentName)
	{
		set_Value (COLUMNNAME_DepartmentName, DepartmentName);
	}

	@Override
	public java.lang.String getDepartmentName() 
	{
		return get_ValueAsString(COLUMNNAME_DepartmentName);
	}

	@Override
	public void setDocCreateDate (final @Nullable java.sql.Timestamp DocCreateDate)
	{
		set_Value (COLUMNNAME_DocCreateDate, DocCreateDate);
	}

	@Override
	public java.sql.Timestamp getDocCreateDate() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_DocCreateDate);
	}

	@Override
	public void setDocTypeName (final java.lang.String DocTypeName)
	{
		set_Value (COLUMNNAME_DocTypeName, DocTypeName);
	}

	@Override
	public java.lang.String getDocTypeName() 
	{
		return get_ValueAsString(COLUMNNAME_DocTypeName);
	}

	@Override
	public void setDueDate (final @Nullable java.sql.Timestamp DueDate)
	{
		set_Value (COLUMNNAME_DueDate, DueDate);
	}

	@Override
	public java.sql.Timestamp getDueDate() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_DueDate);
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
	public void setInvoiceDocumentNo (final java.lang.String InvoiceDocumentNo)
	{
		set_Value (COLUMNNAME_InvoiceDocumentNo, InvoiceDocumentNo);
	}

	@Override
	public java.lang.String getInvoiceDocumentNo() 
	{
		return get_ValueAsString(COLUMNNAME_InvoiceDocumentNo);
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
	public org.compiere.model.I_C_Invoice getPurchase_Invoice()
	{
		return get_ValueAsPO(COLUMNNAME_Purchase_Invoice_ID, org.compiere.model.I_C_Invoice.class);
	}

	@Override
	public void setPurchase_Invoice(final org.compiere.model.I_C_Invoice Purchase_Invoice)
	{
		set_ValueFromPO(COLUMNNAME_Purchase_Invoice_ID, org.compiere.model.I_C_Invoice.class, Purchase_Invoice);
	}

	@Override
	public void setPurchase_Invoice_ID (final int Purchase_Invoice_ID)
	{
		if (Purchase_Invoice_ID < 1) 
			set_Value (COLUMNNAME_Purchase_Invoice_ID, null);
		else 
			set_Value (COLUMNNAME_Purchase_Invoice_ID, Purchase_Invoice_ID);
	}

	@Override
	public int getPurchase_Invoice_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Purchase_Invoice_ID);
	}

	@Override
	public org.compiere.model.I_C_Invoice getSales_Invoice()
	{
		return get_ValueAsPO(COLUMNNAME_Sales_Invoice_ID, org.compiere.model.I_C_Invoice.class);
	}

	@Override
	public void setSales_Invoice(final org.compiere.model.I_C_Invoice Sales_Invoice)
	{
		set_ValueFromPO(COLUMNNAME_Sales_Invoice_ID, org.compiere.model.I_C_Invoice.class, Sales_Invoice);
	}

	@Override
	public void setSales_Invoice_ID (final int Sales_Invoice_ID)
	{
		if (Sales_Invoice_ID < 1) 
			set_Value (COLUMNNAME_Sales_Invoice_ID, null);
		else 
			set_Value (COLUMNNAME_Sales_Invoice_ID, Sales_Invoice_ID);
	}

	@Override
	public int getSales_Invoice_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Sales_Invoice_ID);
	}

	@Override
	public void setSAP_ProductHierarchy (final @Nullable java.lang.String SAP_ProductHierarchy)
	{
		set_Value (COLUMNNAME_SAP_ProductHierarchy, SAP_ProductHierarchy);
	}

	@Override
	public java.lang.String getSAP_ProductHierarchy() 
	{
		return get_ValueAsString(COLUMNNAME_SAP_ProductHierarchy);
	}

	@Override
	public void setSectionCodeAndName (final @Nullable java.lang.String SectionCodeAndName)
	{
		set_Value (COLUMNNAME_SectionCodeAndName, SectionCodeAndName);
	}

	@Override
	public java.lang.String getSectionCodeAndName() 
	{
		return get_ValueAsString(COLUMNNAME_SectionCodeAndName);
	}
}