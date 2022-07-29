// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for C_PartialPayment_Overview_Line
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_PartialPayment_Overview_Line extends org.compiere.model.PO implements I_C_PartialPayment_Overview_Line, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 186757629L;

    /** Standard Constructor */
    public X_C_PartialPayment_Overview_Line (final Properties ctx, final int C_PartialPayment_Overview_Line_ID, @Nullable final String trxName)
    {
      super (ctx, C_PartialPayment_Overview_Line_ID, trxName);
    }

    /** Load Constructor */
    public X_C_PartialPayment_Overview_Line (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public org.compiere.model.I_C_Calendar getC_Calendar()
	{
		return get_ValueAsPO(COLUMNNAME_C_Calendar_ID, org.compiere.model.I_C_Calendar.class);
	}

	@Override
	public void setC_Calendar(final org.compiere.model.I_C_Calendar C_Calendar)
	{
		set_ValueFromPO(COLUMNNAME_C_Calendar_ID, org.compiere.model.I_C_Calendar.class, C_Calendar);
	}

	@Override
	public void setC_Calendar_ID (final int C_Calendar_ID)
	{
		if (C_Calendar_ID < 1) 
			set_Value (COLUMNNAME_C_Calendar_ID, null);
		else 
			set_Value (COLUMNNAME_C_Calendar_ID, C_Calendar_ID);
	}

	@Override
	public int getC_Calendar_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Calendar_ID);
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
	public org.compiere.model.I_C_PartialPayment_Overview getC_PartialPayment_Overview()
	{
		return get_ValueAsPO(COLUMNNAME_C_PartialPayment_Overview_ID, org.compiere.model.I_C_PartialPayment_Overview.class);
	}

	@Override
	public void setC_PartialPayment_Overview(final org.compiere.model.I_C_PartialPayment_Overview C_PartialPayment_Overview)
	{
		set_ValueFromPO(COLUMNNAME_C_PartialPayment_Overview_ID, org.compiere.model.I_C_PartialPayment_Overview.class, C_PartialPayment_Overview);
	}

	@Override
	public void setC_PartialPayment_Overview_ID (final int C_PartialPayment_Overview_ID)
	{
		if (C_PartialPayment_Overview_ID < 1) 
			set_Value (COLUMNNAME_C_PartialPayment_Overview_ID, null);
		else 
			set_Value (COLUMNNAME_C_PartialPayment_Overview_ID, C_PartialPayment_Overview_ID);
	}

	@Override
	public int getC_PartialPayment_Overview_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_PartialPayment_Overview_ID);
	}

	@Override
	public void setC_PartialPayment_Overview_Line_ID (final int C_PartialPayment_Overview_Line_ID)
	{
		if (C_PartialPayment_Overview_Line_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_PartialPayment_Overview_Line_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_PartialPayment_Overview_Line_ID, C_PartialPayment_Overview_Line_ID);
	}

	@Override
	public int getC_PartialPayment_Overview_Line_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_PartialPayment_Overview_Line_ID);
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