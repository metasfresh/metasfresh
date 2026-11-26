// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for C_Invoice_Acct
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_Invoice_Acct extends org.compiere.model.PO implements I_C_Invoice_Acct, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1357374851L;

    /** Standard Constructor */
    public X_C_Invoice_Acct (final Properties ctx, final int C_Invoice_Acct_ID, @Nullable final String trxName)
    {
      super (ctx, C_Invoice_Acct_ID, trxName);
    }

    /** Load Constructor */
    public X_C_Invoice_Acct (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
    {
      super (ctx, rs, trxName);
    }


	/** Load Meta Data */
	@Override
	protected org.compiere.model.POInfo initPO(final Properties ctx)
	{
		return org.compiere.model.POInfo.getPOInfo(Table_Name);
	}

	/** 
	 * AccountName AD_Reference_ID=542108
	 * Reference name: Invoice Posting Account Concept
	 */
	public static final int ACCOUNTNAME_AD_Reference_ID=542108;
	/** P_Revenue_Acct = P_Revenue_Acct */
	public static final String ACCOUNTNAME_P_Revenue_Acct = "P_Revenue_Acct";
	/** P_Expense_Acct = P_Expense_Acct */
	public static final String ACCOUNTNAME_P_Expense_Acct = "P_Expense_Acct";
	/** P_TradeDiscountGrant_Acct = P_TradeDiscountGrant_Acct */
	public static final String ACCOUNTNAME_P_TradeDiscountGrant_Acct = "P_TradeDiscountGrant_Acct";
	/** P_TradeDiscountRec_Acct = P_TradeDiscountRec_Acct */
	public static final String ACCOUNTNAME_P_TradeDiscountRec_Acct = "P_TradeDiscountRec_Acct";
	/** P_InventoryClearing_Acct = P_InventoryClearing_Acct */
	public static final String ACCOUNTNAME_P_InventoryClearing_Acct = "P_InventoryClearing_Acct";
	/** P_InvoicePriceVariance_Acct = P_InvoicePriceVariance_Acct */
	public static final String ACCOUNTNAME_P_InvoicePriceVariance_Acct = "P_InvoicePriceVariance_Acct";
	@Override
	public void setAccountName (final @Nullable java.lang.String AccountName)
	{
		set_Value (COLUMNNAME_AccountName, AccountName);
	}

	@Override
	public java.lang.String getAccountName() 
	{
		return get_ValueAsString(COLUMNNAME_AccountName);
	}

	@Override
	public void setC_AcctSchema_ID (final int C_AcctSchema_ID)
	{
		if (C_AcctSchema_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_AcctSchema_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_AcctSchema_ID, C_AcctSchema_ID);
	}

	@Override
	public int getC_AcctSchema_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_AcctSchema_ID);
	}

	@Override
	public void setC_ElementValue_ID (final int C_ElementValue_ID)
	{
		if (C_ElementValue_ID < 1) 
			set_Value (COLUMNNAME_C_ElementValue_ID, null);
		else 
			set_Value (COLUMNNAME_C_ElementValue_ID, C_ElementValue_ID);
	}

	@Override
	public int getC_ElementValue_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_ElementValue_ID);
	}

	@Override
	public void setC_Invoice_Acct_ID (final int C_Invoice_Acct_ID)
	{
		if (C_Invoice_Acct_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Invoice_Acct_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Invoice_Acct_ID, C_Invoice_Acct_ID);
	}

	@Override
	public int getC_Invoice_Acct_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Invoice_Acct_ID);
	}

	@Override
	public void setC_Invoice_ID (final int C_Invoice_ID)
	{
		if (C_Invoice_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Invoice_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Invoice_ID, C_Invoice_ID);
	}

	@Override
	public int getC_Invoice_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Invoice_ID);
	}

	@Override
	public void setC_InvoiceLine_ID (final int C_InvoiceLine_ID)
	{
		if (C_InvoiceLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_InvoiceLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_InvoiceLine_ID, C_InvoiceLine_ID);
	}

	@Override
	public int getC_InvoiceLine_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_InvoiceLine_ID);
	}

	@Override
	public void setInvoicePostingStatus (final @Nullable java.lang.String InvoicePostingStatus)
	{
		throw new IllegalArgumentException ("InvoicePostingStatus is virtual column");	}

	@Override
	public java.lang.String getInvoicePostingStatus() 
	{
		return get_ValueAsString(COLUMNNAME_InvoicePostingStatus);
	}
}