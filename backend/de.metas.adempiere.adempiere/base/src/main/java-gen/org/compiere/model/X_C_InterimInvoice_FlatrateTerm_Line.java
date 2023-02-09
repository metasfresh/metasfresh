// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for C_InterimInvoice_FlatrateTerm_Line
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_InterimInvoice_FlatrateTerm_Line extends org.compiere.model.PO implements I_C_InterimInvoice_FlatrateTerm_Line, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1098088481L;

    /** Standard Constructor */
    public X_C_InterimInvoice_FlatrateTerm_Line (final Properties ctx, final int C_InterimInvoice_FlatrateTerm_Line_ID, @Nullable final String trxName)
    {
      super (ctx, C_InterimInvoice_FlatrateTerm_Line_ID, trxName);
    }

    /** Load Constructor */
    public X_C_InterimInvoice_FlatrateTerm_Line (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public org.compiere.model.I_C_InterimInvoice_FlatrateTerm getC_InterimInvoice_FlatrateTerm()
	{
		return get_ValueAsPO(COLUMNNAME_C_InterimInvoice_FlatrateTerm_ID, org.compiere.model.I_C_InterimInvoice_FlatrateTerm.class);
	}

	@Override
	public void setC_InterimInvoice_FlatrateTerm(final org.compiere.model.I_C_InterimInvoice_FlatrateTerm C_InterimInvoice_FlatrateTerm)
	{
		set_ValueFromPO(COLUMNNAME_C_InterimInvoice_FlatrateTerm_ID, org.compiere.model.I_C_InterimInvoice_FlatrateTerm.class, C_InterimInvoice_FlatrateTerm);
	}

	@Override
	public void setC_InterimInvoice_FlatrateTerm_ID (final int C_InterimInvoice_FlatrateTerm_ID)
	{
		if (C_InterimInvoice_FlatrateTerm_ID < 1) 
			set_Value (COLUMNNAME_C_InterimInvoice_FlatrateTerm_ID, null);
		else 
			set_Value (COLUMNNAME_C_InterimInvoice_FlatrateTerm_ID, C_InterimInvoice_FlatrateTerm_ID);
	}

	@Override
	public int getC_InterimInvoice_FlatrateTerm_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_InterimInvoice_FlatrateTerm_ID);
	}

	@Override
	public void setC_InterimInvoice_FlatrateTerm_Line_ID (final int C_InterimInvoice_FlatrateTerm_Line_ID)
	{
		if (C_InterimInvoice_FlatrateTerm_Line_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_InterimInvoice_FlatrateTerm_Line_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_InterimInvoice_FlatrateTerm_Line_ID, C_InterimInvoice_FlatrateTerm_Line_ID);
	}

	@Override
	public int getC_InterimInvoice_FlatrateTerm_Line_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_InterimInvoice_FlatrateTerm_Line_ID);
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
	public org.compiere.model.I_M_InOut getM_InOut()
	{
		return get_ValueAsPO(COLUMNNAME_M_InOut_ID, org.compiere.model.I_M_InOut.class);
	}

	@Override
	public void setM_InOut(final org.compiere.model.I_M_InOut M_InOut)
	{
		set_ValueFromPO(COLUMNNAME_M_InOut_ID, org.compiere.model.I_M_InOut.class, M_InOut);
	}

	@Override
	public void setM_InOut_ID (final int M_InOut_ID)
	{
		if (M_InOut_ID < 1) 
			set_Value (COLUMNNAME_M_InOut_ID, null);
		else 
			set_Value (COLUMNNAME_M_InOut_ID, M_InOut_ID);
	}

	@Override
	public int getM_InOut_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_InOut_ID);
	}

	@Override
	public org.compiere.model.I_M_InOutLine getM_InOutLine()
	{
		return get_ValueAsPO(COLUMNNAME_M_InOutLine_ID, org.compiere.model.I_M_InOutLine.class);
	}

	@Override
	public void setM_InOutLine(final org.compiere.model.I_M_InOutLine M_InOutLine)
	{
		set_ValueFromPO(COLUMNNAME_M_InOutLine_ID, org.compiere.model.I_M_InOutLine.class, M_InOutLine);
	}

	@Override
	public void setM_InOutLine_ID (final int M_InOutLine_ID)
	{
		if (M_InOutLine_ID < 1) 
			set_Value (COLUMNNAME_M_InOutLine_ID, null);
		else 
			set_Value (COLUMNNAME_M_InOutLine_ID, M_InOutLine_ID);
	}

	@Override
	public int getM_InOutLine_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_InOutLine_ID);
	}
}