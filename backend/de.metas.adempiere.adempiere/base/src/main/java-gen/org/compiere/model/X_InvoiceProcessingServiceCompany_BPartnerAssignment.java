/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for InvoiceProcessingServiceCompany_BPartnerAssignment
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("javadoc")
public class X_InvoiceProcessingServiceCompany_BPartnerAssignment extends org.compiere.model.PO implements I_InvoiceProcessingServiceCompany_BPartnerAssignment, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1900044376L;

    /** Standard Constructor */
    public X_InvoiceProcessingServiceCompany_BPartnerAssignment (Properties ctx, int InvoiceProcessingServiceCompany_BPartnerAssignment_ID, String trxName)
    {
      super (ctx, InvoiceProcessingServiceCompany_BPartnerAssignment_ID, trxName);
    }

    /** Load Constructor */
    public X_InvoiceProcessingServiceCompany_BPartnerAssignment (Properties ctx, ResultSet rs, String trxName)
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
	public void setC_BPartner_ID (int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_ID, Integer.valueOf(C_BPartner_ID));
	}

	@Override
	public int getC_BPartner_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_ID);
	}

	@Override
	public void setDescription (java.lang.String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	@Override
	public java.lang.String getDescription() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Description);
	}

	@Override
	public void setFeePercentageOfGrandTotal (java.math.BigDecimal FeePercentageOfGrandTotal)
	{
		set_Value (COLUMNNAME_FeePercentageOfGrandTotal, FeePercentageOfGrandTotal);
	}

	@Override
	public java.math.BigDecimal getFeePercentageOfGrandTotal() 
	{
		BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_FeePercentageOfGrandTotal);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setInvoiceProcessingServiceCompany_BPartnerAssignment_ID (int InvoiceProcessingServiceCompany_BPartnerAssignment_ID)
	{
		if (InvoiceProcessingServiceCompany_BPartnerAssignment_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_InvoiceProcessingServiceCompany_BPartnerAssignment_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_InvoiceProcessingServiceCompany_BPartnerAssignment_ID, Integer.valueOf(InvoiceProcessingServiceCompany_BPartnerAssignment_ID));
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
	public void setInvoiceProcessingServiceCompany(org.compiere.model.I_InvoiceProcessingServiceCompany InvoiceProcessingServiceCompany)
	{
		set_ValueFromPO(COLUMNNAME_InvoiceProcessingServiceCompany_ID, org.compiere.model.I_InvoiceProcessingServiceCompany.class, InvoiceProcessingServiceCompany);
	}

	@Override
	public void setInvoiceProcessingServiceCompany_ID (int InvoiceProcessingServiceCompany_ID)
	{
		if (InvoiceProcessingServiceCompany_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_InvoiceProcessingServiceCompany_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_InvoiceProcessingServiceCompany_ID, Integer.valueOf(InvoiceProcessingServiceCompany_ID));
	}

	@Override
	public int getInvoiceProcessingServiceCompany_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_InvoiceProcessingServiceCompany_ID);
	}
}
