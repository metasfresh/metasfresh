// Generated Model - DO NOT CHANGE
package org.compiere.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_Invoice_Verification_SetLine
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_Invoice_Verification_SetLine extends org.compiere.model.PO implements I_C_Invoice_Verification_SetLine, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1088808834L;

    /** Standard Constructor */
    public X_C_Invoice_Verification_SetLine (final Properties ctx, final int C_Invoice_Verification_SetLine_ID, @Nullable final String trxName)
    {
      super (ctx, C_Invoice_Verification_SetLine_ID, trxName);
    }

    /** Load Constructor */
    public X_C_Invoice_Verification_SetLine (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setC_DocType_ID (final int C_DocType_ID)
	{
		if (C_DocType_ID < 0) 
			set_ValueNoCheck (COLUMNNAME_C_DocType_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_DocType_ID, C_DocType_ID);
	}

	@Override
	public int getC_DocType_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_DocType_ID);
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
			set_Value (COLUMNNAME_C_InvoiceLine_ID, null);
		else 
			set_Value (COLUMNNAME_C_InvoiceLine_ID, C_InvoiceLine_ID);
	}

	@Override
	public int getC_InvoiceLine_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_InvoiceLine_ID);
	}

	@Override
	public void setC_InvoiceLine_Tax_ID (final int C_InvoiceLine_Tax_ID)
	{
		if (C_InvoiceLine_Tax_ID < 1) 
			set_Value (COLUMNNAME_C_InvoiceLine_Tax_ID, null);
		else 
			set_Value (COLUMNNAME_C_InvoiceLine_Tax_ID, C_InvoiceLine_Tax_ID);
	}

	@Override
	public int getC_InvoiceLine_Tax_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_InvoiceLine_Tax_ID);
	}

	@Override
	public org.compiere.model.I_C_Invoice_Verification_Set getC_Invoice_Verification_Set()
	{
		return get_ValueAsPO(COLUMNNAME_C_Invoice_Verification_Set_ID, org.compiere.model.I_C_Invoice_Verification_Set.class);
	}

	@Override
	public void setC_Invoice_Verification_Set(final org.compiere.model.I_C_Invoice_Verification_Set C_Invoice_Verification_Set)
	{
		set_ValueFromPO(COLUMNNAME_C_Invoice_Verification_Set_ID, org.compiere.model.I_C_Invoice_Verification_Set.class, C_Invoice_Verification_Set);
	}

	@Override
	public void setC_Invoice_Verification_Set_ID (final int C_Invoice_Verification_Set_ID)
	{
		if (C_Invoice_Verification_Set_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Invoice_Verification_Set_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Invoice_Verification_Set_ID, C_Invoice_Verification_Set_ID);
	}

	@Override
	public int getC_Invoice_Verification_Set_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Invoice_Verification_Set_ID);
	}

	@Override
	public void setC_Invoice_Verification_SetLine_ID (final int C_Invoice_Verification_SetLine_ID)
	{
		if (C_Invoice_Verification_SetLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Invoice_Verification_SetLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Invoice_Verification_SetLine_ID, C_Invoice_Verification_SetLine_ID);
	}

	@Override
	public int getC_Invoice_Verification_SetLine_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Invoice_Verification_SetLine_ID);
	}

	@Override
	public void setRelevantDate (final @Nullable java.sql.Timestamp RelevantDate)
	{
		set_ValueNoCheck (COLUMNNAME_RelevantDate, RelevantDate);
	}

	@Override
	public java.sql.Timestamp getRelevantDate() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_RelevantDate);
	}
}