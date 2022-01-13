// Generated Model - DO NOT CHANGE
package org.compiere.model;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for InvoiceProcessingServiceCompany_BPartnerAssignment
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("javadoc")
public class X_InvoiceProcessingServiceCompany_BPartnerAssignment extends org.compiere.model.PO implements I_InvoiceProcessingServiceCompany_BPartnerAssignment, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 664748012L;

    /** Standard Constructor */
    public X_InvoiceProcessingServiceCompany_BPartnerAssignment (final Properties ctx, final int InvoiceProcessingServiceCompany_BPartnerAssignment_ID, @Nullable final String trxName)
    {
      super (ctx, InvoiceProcessingServiceCompany_BPartnerAssignment_ID, trxName);
    }

    /** Load Constructor */
    public X_InvoiceProcessingServiceCompany_BPartnerAssignment (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setC_DocType_ID (final int C_DocType_ID)
	{
		if (C_DocType_ID < 0) 
			set_Value (COLUMNNAME_C_DocType_ID, null);
		else 
			set_Value (COLUMNNAME_C_DocType_ID, C_DocType_ID);
	}

	@Override
	public int getC_DocType_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_DocType_ID);
	}

	@Override
	public void setDescription (final @Nullable java.lang.String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	@Override
	public java.lang.String getDescription() 
	{
		return get_ValueAsString(COLUMNNAME_Description);
	}

	@Override
	public void setFeePercentageOfGrandTotal (final BigDecimal FeePercentageOfGrandTotal)
	{
		set_Value (COLUMNNAME_FeePercentageOfGrandTotal, FeePercentageOfGrandTotal);
	}

	@Override
	public BigDecimal getFeePercentageOfGrandTotal() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_FeePercentageOfGrandTotal);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setInvoiceProcessingServiceCompany_BPartnerAssignment_ID (final int InvoiceProcessingServiceCompany_BPartnerAssignment_ID)
	{
		if (InvoiceProcessingServiceCompany_BPartnerAssignment_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_InvoiceProcessingServiceCompany_BPartnerAssignment_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_InvoiceProcessingServiceCompany_BPartnerAssignment_ID, InvoiceProcessingServiceCompany_BPartnerAssignment_ID);
	}

	@Override
	public int getInvoiceProcessingServiceCompany_BPartnerAssignment_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_InvoiceProcessingServiceCompany_BPartnerAssignment_ID);
	}

	@Override
	public org.compiere.model.I_InvoiceProcessingServiceCompany getInvoiceProcessingServiceCompany()
	{
		return get_ValueAsPO(COLUMNNAME_InvoiceProcessingServiceCompany_ID, org.compiere.model.I_InvoiceProcessingServiceCompany.class);
	}

	@Override
	public void setInvoiceProcessingServiceCompany(final org.compiere.model.I_InvoiceProcessingServiceCompany InvoiceProcessingServiceCompany)
	{
		set_ValueFromPO(COLUMNNAME_InvoiceProcessingServiceCompany_ID, org.compiere.model.I_InvoiceProcessingServiceCompany.class, InvoiceProcessingServiceCompany);
	}

	@Override
	public void setInvoiceProcessingServiceCompany_ID (final int InvoiceProcessingServiceCompany_ID)
	{
		if (InvoiceProcessingServiceCompany_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_InvoiceProcessingServiceCompany_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_InvoiceProcessingServiceCompany_ID, InvoiceProcessingServiceCompany_ID);
	}

	@Override
	public int getInvoiceProcessingServiceCompany_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_InvoiceProcessingServiceCompany_ID);
	}
}