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

	private static final long serialVersionUID = -1849993644L;

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
	public org.compiere.model.I_C_AcctSchema getC_AcctSchema()
	{
		return get_ValueAsPO(COLUMNNAME_C_AcctSchema_ID, org.compiere.model.I_C_AcctSchema.class);
	}

	@Override
	public void setC_AcctSchema(final org.compiere.model.I_C_AcctSchema C_AcctSchema)
	{
		set_ValueFromPO(COLUMNNAME_C_AcctSchema_ID, org.compiere.model.I_C_AcctSchema.class, C_AcctSchema);
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
	public org.compiere.model.I_C_ElementValue getC_ElementValue()
	{
		return get_ValueAsPO(COLUMNNAME_C_ElementValue_ID, org.compiere.model.I_C_ElementValue.class);
	}

	@Override
	public void setC_ElementValue(final org.compiere.model.I_C_ElementValue C_ElementValue)
	{
		set_ValueFromPO(COLUMNNAME_C_ElementValue_ID, org.compiere.model.I_C_ElementValue.class, C_ElementValue);
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
	public org.compiere.model.I_C_InvoiceLine getC_InvoiceLine()
	{
		return get_ValueAsPO(COLUMNNAME_C_InvoiceLine_ID, org.compiere.model.I_C_InvoiceLine.class);
	}

	@Override
	public void setC_InvoiceLine(final org.compiere.model.I_C_InvoiceLine C_InvoiceLine)
	{
		set_ValueFromPO(COLUMNNAME_C_InvoiceLine_ID, org.compiere.model.I_C_InvoiceLine.class, C_InvoiceLine);
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
}