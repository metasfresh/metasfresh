// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for C_BP_BankAccount_InvoiceAutoAllocateRule
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_BP_BankAccount_InvoiceAutoAllocateRule extends org.compiere.model.PO implements I_C_BP_BankAccount_InvoiceAutoAllocateRule, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -956367533L;

    /** Standard Constructor */
    public X_C_BP_BankAccount_InvoiceAutoAllocateRule (final Properties ctx, final int C_BP_BankAccount_InvoiceAutoAllocateRule_ID, @Nullable final String trxName)
    {
      super (ctx, C_BP_BankAccount_InvoiceAutoAllocateRule_ID, trxName);
    }

    /** Load Constructor */
    public X_C_BP_BankAccount_InvoiceAutoAllocateRule (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setC_BP_BankAccount_ID (final int C_BP_BankAccount_ID)
	{
		if (C_BP_BankAccount_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_BP_BankAccount_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BP_BankAccount_ID, C_BP_BankAccount_ID);
	}

	@Override
	public int getC_BP_BankAccount_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BP_BankAccount_ID);
	}

	@Override
	public void setC_BP_BankAccount_InvoiceAutoAllocateRule_ID (final int C_BP_BankAccount_InvoiceAutoAllocateRule_ID)
	{
		if (C_BP_BankAccount_InvoiceAutoAllocateRule_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_BP_BankAccount_InvoiceAutoAllocateRule_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BP_BankAccount_InvoiceAutoAllocateRule_ID, C_BP_BankAccount_InvoiceAutoAllocateRule_ID);
	}

	@Override
	public int getC_BP_BankAccount_InvoiceAutoAllocateRule_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BP_BankAccount_InvoiceAutoAllocateRule_ID);
	}

	@Override
	public void setC_DocTypeInvoice_ID (final int C_DocTypeInvoice_ID)
	{
		if (C_DocTypeInvoice_ID < 1) 
			set_Value (COLUMNNAME_C_DocTypeInvoice_ID, null);
		else 
			set_Value (COLUMNNAME_C_DocTypeInvoice_ID, C_DocTypeInvoice_ID);
	}

	@Override
	public int getC_DocTypeInvoice_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_DocTypeInvoice_ID);
	}
}