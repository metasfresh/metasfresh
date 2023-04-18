// Generated Model - DO NOT CHANGE
package org.compiere.model;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_Invoice_Review
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_Invoice_Review extends org.compiere.model.PO implements I_C_Invoice_Review, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -713992703L;

    /** Standard Constructor */
    public X_C_Invoice_Review (final Properties ctx, final int C_Invoice_Review_ID, @Nullable final String trxName)
    {
      super (ctx, C_Invoice_Review_ID, trxName);
    }

    /** Load Constructor */
    public X_C_Invoice_Review (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setBPartnerName (final @Nullable java.lang.String BPartnerName)
	{
		throw new IllegalArgumentException ("BPartnerName is virtual column");	}

	@Override
	public java.lang.String getBPartnerName() 
	{
		return get_ValueAsString(COLUMNNAME_BPartnerName);
	}

	@Override
	public void setC_Currency_ID (final int C_Currency_ID)
	{
		throw new IllegalArgumentException ("C_Currency_ID is virtual column");	}

	@Override
	public int getC_Currency_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Currency_ID);
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
	public void setC_Invoice_Review_ID (final int C_Invoice_Review_ID)
	{
		if (C_Invoice_Review_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Invoice_Review_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Invoice_Review_ID, C_Invoice_Review_ID);
	}

	@Override
	public int getC_Invoice_Review_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Invoice_Review_ID);
	}

	@Override
	public void setDateInvoiced (final @Nullable java.sql.Timestamp DateInvoiced)
	{
		throw new IllegalArgumentException ("DateInvoiced is virtual column");	}

	@Override
	public java.sql.Timestamp getDateInvoiced() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_DateInvoiced);
	}

	@Override
	public void setDocumentNo (final @Nullable java.lang.String DocumentNo)
	{
		throw new IllegalArgumentException ("DocumentNo is virtual column");	}

	@Override
	public java.lang.String getDocumentNo() 
	{
		return get_ValueAsString(COLUMNNAME_DocumentNo);
	}

	@Override
	public void setExternalId (final @Nullable java.lang.String ExternalId)
	{
		throw new IllegalArgumentException ("ExternalId is virtual column");	}

	@Override
	public java.lang.String getExternalId() 
	{
		return get_ValueAsString(COLUMNNAME_ExternalId);
	}

	@Override
	public void setGrandTotal (final @Nullable BigDecimal GrandTotal)
	{
		throw new IllegalArgumentException ("GrandTotal is virtual column");	}

	@Override
	public BigDecimal getGrandTotal() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_GrandTotal);
		return bd != null ? bd : BigDecimal.ZERO;
	}
}