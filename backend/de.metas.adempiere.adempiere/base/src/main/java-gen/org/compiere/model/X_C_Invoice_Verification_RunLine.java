// Generated Model - DO NOT CHANGE
package org.compiere.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_Invoice_Verification_RunLine
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_Invoice_Verification_RunLine extends org.compiere.model.PO implements I_C_Invoice_Verification_RunLine, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1365449705L;

    /** Standard Constructor */
    public X_C_Invoice_Verification_RunLine (final Properties ctx, final int C_Invoice_Verification_RunLine_ID, @Nullable final String trxName)
    {
      super (ctx, C_Invoice_Verification_RunLine_ID, trxName);
    }

    /** Load Constructor */
    public X_C_Invoice_Verification_RunLine (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
		throw new IllegalArgumentException ("C_Invoice_ID is virtual column");	}

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
		throw new IllegalArgumentException ("C_InvoiceLine_ID is virtual column");	}

	@Override
	public int getC_InvoiceLine_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_InvoiceLine_ID);
	}

	@Override
	public void setC_InvoiceLine_Tax_ID (final int C_InvoiceLine_Tax_ID)
	{
		throw new IllegalArgumentException ("C_InvoiceLine_Tax_ID is virtual column");	}

	@Override
	public int getC_InvoiceLine_Tax_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_InvoiceLine_Tax_ID);
	}

	@Override
	public org.compiere.model.I_C_Invoice_Verification_Run getC_Invoice_Verification_Run()
	{
		return get_ValueAsPO(COLUMNNAME_C_Invoice_Verification_Run_ID, org.compiere.model.I_C_Invoice_Verification_Run.class);
	}

	@Override
	public void setC_Invoice_Verification_Run(final org.compiere.model.I_C_Invoice_Verification_Run C_Invoice_Verification_Run)
	{
		set_ValueFromPO(COLUMNNAME_C_Invoice_Verification_Run_ID, org.compiere.model.I_C_Invoice_Verification_Run.class, C_Invoice_Verification_Run);
	}

	@Override
	public void setC_Invoice_Verification_Run_ID (final int C_Invoice_Verification_Run_ID)
	{
		if (C_Invoice_Verification_Run_ID < 1) 
			set_Value (COLUMNNAME_C_Invoice_Verification_Run_ID, null);
		else 
			set_Value (COLUMNNAME_C_Invoice_Verification_Run_ID, C_Invoice_Verification_Run_ID);
	}

	@Override
	public int getC_Invoice_Verification_Run_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Invoice_Verification_Run_ID);
	}

	@Override
	public void setC_Invoice_Verification_RunLine_ID (final int C_Invoice_Verification_RunLine_ID)
	{
		if (C_Invoice_Verification_RunLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Invoice_Verification_RunLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Invoice_Verification_RunLine_ID, C_Invoice_Verification_RunLine_ID);
	}

	@Override
	public int getC_Invoice_Verification_RunLine_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Invoice_Verification_RunLine_ID);
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
			set_Value (COLUMNNAME_C_Invoice_Verification_Set_ID, null);
		else 
			set_Value (COLUMNNAME_C_Invoice_Verification_Set_ID, C_Invoice_Verification_Set_ID);
	}

	@Override
	public int getC_Invoice_Verification_Set_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Invoice_Verification_Set_ID);
	}

	@Override
	public org.compiere.model.I_C_Invoice_Verification_SetLine getC_Invoice_Verification_SetLine()
	{
		return get_ValueAsPO(COLUMNNAME_C_Invoice_Verification_SetLine_ID, org.compiere.model.I_C_Invoice_Verification_SetLine.class);
	}

	@Override
	public void setC_Invoice_Verification_SetLine(final org.compiere.model.I_C_Invoice_Verification_SetLine C_Invoice_Verification_SetLine)
	{
		set_ValueFromPO(COLUMNNAME_C_Invoice_Verification_SetLine_ID, org.compiere.model.I_C_Invoice_Verification_SetLine.class, C_Invoice_Verification_SetLine);
	}

	@Override
	public void setC_Invoice_Verification_SetLine_ID (final int C_Invoice_Verification_SetLine_ID)
	{
		if (C_Invoice_Verification_SetLine_ID < 1) 
			set_Value (COLUMNNAME_C_Invoice_Verification_SetLine_ID, null);
		else 
			set_Value (COLUMNNAME_C_Invoice_Verification_SetLine_ID, C_Invoice_Verification_SetLine_ID);
	}

	@Override
	public int getC_Invoice_Verification_SetLine_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Invoice_Verification_SetLine_ID);
	}

	@Override
	public void setIsTaxBoilerPlateMatch (final boolean IsTaxBoilerPlateMatch)
	{
		set_Value (COLUMNNAME_IsTaxBoilerPlateMatch, IsTaxBoilerPlateMatch);
	}

	@Override
	public boolean isTaxBoilerPlateMatch() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsTaxBoilerPlateMatch);
	}

	@Override
	public void setIsTaxIdMatch (final boolean IsTaxIdMatch)
	{
		set_Value (COLUMNNAME_IsTaxIdMatch, IsTaxIdMatch);
	}

	@Override
	public boolean isTaxIdMatch() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsTaxIdMatch);
	}

	@Override
	public void setIsTaxRateMatch (final boolean IsTaxRateMatch)
	{
		set_Value (COLUMNNAME_IsTaxRateMatch, IsTaxRateMatch);
	}

	@Override
	public boolean isTaxRateMatch() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsTaxRateMatch);
	}

	@Override
	public void setRun_Tax_ID (final int Run_Tax_ID)
	{
		if (Run_Tax_ID < 1) 
			set_Value (COLUMNNAME_Run_Tax_ID, null);
		else 
			set_Value (COLUMNNAME_Run_Tax_ID, Run_Tax_ID);
	}

	@Override
	public int getRun_Tax_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Run_Tax_ID);
	}

	@Override
	public void setRun_Tax_Lookup_Log (final @Nullable java.lang.String Run_Tax_Lookup_Log)
	{
		set_Value (COLUMNNAME_Run_Tax_Lookup_Log, Run_Tax_Lookup_Log);
	}

	@Override
	public java.lang.String getRun_Tax_Lookup_Log() 
	{
		return get_ValueAsString(COLUMNNAME_Run_Tax_Lookup_Log);
	}
}