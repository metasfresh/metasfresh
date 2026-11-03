// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for InvoiceProcessingServiceCompany
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_InvoiceProcessingServiceCompany extends org.compiere.model.PO implements I_InvoiceProcessingServiceCompany, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 84175160L;

    /** Standard Constructor */
    public X_InvoiceProcessingServiceCompany (final Properties ctx, final int InvoiceProcessingServiceCompany_ID, @Nullable final String trxName)
    {
      super (ctx, InvoiceProcessingServiceCompany_ID, trxName);
    }

    /** Load Constructor */
    public X_InvoiceProcessingServiceCompany (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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

	@Override
	public void setServiceCompany_BPartner_ID (final int ServiceCompany_BPartner_ID)
	{
		if (ServiceCompany_BPartner_ID < 1) 
			set_Value (COLUMNNAME_ServiceCompany_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_ServiceCompany_BPartner_ID, ServiceCompany_BPartner_ID);
	}

	@Override
	public int getServiceCompany_BPartner_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_ServiceCompany_BPartner_ID);
	}

	@Override
	public void setServiceCompany_BPartner_Name (final @Nullable java.lang.String ServiceCompany_BPartner_Name)
	{
		throw new IllegalArgumentException ("ServiceCompany_BPartner_Name is virtual column");	}

	@Override
	public java.lang.String getServiceCompany_BPartner_Name() 
	{
		return get_ValueAsString(COLUMNNAME_ServiceCompany_BPartner_Name);
	}

	@Override
	public void setServiceFee_Product_ID (final int ServiceFee_Product_ID)
	{
		if (ServiceFee_Product_ID < 1) 
			set_Value (COLUMNNAME_ServiceFee_Product_ID, null);
		else 
			set_Value (COLUMNNAME_ServiceFee_Product_ID, ServiceFee_Product_ID);
	}

	@Override
	public int getServiceFee_Product_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_ServiceFee_Product_ID);
	}

	@Override
	public void setServiceInvoice_DocType_ID (final int ServiceInvoice_DocType_ID)
	{
		if (ServiceInvoice_DocType_ID < 1) 
			set_Value (COLUMNNAME_ServiceInvoice_DocType_ID, null);
		else 
			set_Value (COLUMNNAME_ServiceInvoice_DocType_ID, ServiceInvoice_DocType_ID);
	}

	@Override
	public int getServiceInvoice_DocType_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_ServiceInvoice_DocType_ID);
	}

	@Override
	public void setValidFrom (final @Nullable java.sql.Timestamp ValidFrom)
	{
		set_Value (COLUMNNAME_ValidFrom, ValidFrom);
	}

	@Override
	public java.sql.Timestamp getValidFrom() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_ValidFrom);
	}
}