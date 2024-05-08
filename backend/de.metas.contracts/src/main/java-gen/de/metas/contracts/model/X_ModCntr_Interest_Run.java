// Generated Model - DO NOT CHANGE
package de.metas.contracts.model;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for ModCntr_Interest_Run
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_ModCntr_Interest_Run extends org.compiere.model.PO implements I_ModCntr_Interest_Run, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -945197375L;

    /** Standard Constructor */
    public X_ModCntr_Interest_Run (final Properties ctx, final int ModCntr_Interest_Run_ID, @Nullable final String trxName)
    {
      super (ctx, ModCntr_Interest_Run_ID, trxName);
    }

    /** Load Constructor */
    public X_ModCntr_Interest_Run (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setBillingDate (final java.sql.Timestamp BillingDate)
	{
		set_Value (COLUMNNAME_BillingDate, BillingDate);
	}

	@Override
	public java.sql.Timestamp getBillingDate() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_BillingDate);
	}

	@Override
	public void setDateStart (final java.sql.Timestamp DateStart)
	{
		set_Value (COLUMNNAME_DateStart, DateStart);
	}

	@Override
	public java.sql.Timestamp getDateStart() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_DateStart);
	}

	@Override
	public void setInterimDate (final java.sql.Timestamp InterimDate)
	{
		set_Value (COLUMNNAME_InterimDate, InterimDate);
	}

	@Override
	public java.sql.Timestamp getInterimDate() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_InterimDate);
	}

	@Override
	public void setModCntr_Interest_Run_ID (final int ModCntr_Interest_Run_ID)
	{
		if (ModCntr_Interest_Run_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_ModCntr_Interest_Run_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_ModCntr_Interest_Run_ID, ModCntr_Interest_Run_ID);
	}

	@Override
	public int getModCntr_Interest_Run_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_ModCntr_Interest_Run_ID);
	}

	@Override
	public de.metas.contracts.model.I_ModCntr_InvoicingGroup getModCntr_InvoicingGroup()
	{
		return get_ValueAsPO(COLUMNNAME_ModCntr_InvoicingGroup_ID, de.metas.contracts.model.I_ModCntr_InvoicingGroup.class);
	}

	@Override
	public void setModCntr_InvoicingGroup(final de.metas.contracts.model.I_ModCntr_InvoicingGroup ModCntr_InvoicingGroup)
	{
		set_ValueFromPO(COLUMNNAME_ModCntr_InvoicingGroup_ID, de.metas.contracts.model.I_ModCntr_InvoicingGroup.class, ModCntr_InvoicingGroup);
	}

	@Override
	public void setModCntr_InvoicingGroup_ID (final int ModCntr_InvoicingGroup_ID)
	{
		if (ModCntr_InvoicingGroup_ID < 1) 
			set_Value (COLUMNNAME_ModCntr_InvoicingGroup_ID, null);
		else 
			set_Value (COLUMNNAME_ModCntr_InvoicingGroup_ID, ModCntr_InvoicingGroup_ID);
	}

	@Override
	public int getModCntr_InvoicingGroup_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_ModCntr_InvoicingGroup_ID);
	}

	@Override
	public void setTotalInterest (final BigDecimal TotalInterest)
	{
		set_Value (COLUMNNAME_TotalInterest, TotalInterest);
	}

	@Override
	public BigDecimal getTotalInterest() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_TotalInterest);
		return bd != null ? bd : BigDecimal.ZERO;
	}
}