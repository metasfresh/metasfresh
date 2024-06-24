// Generated Model - DO NOT CHANGE
package org.compiere.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for DatevAcctExport
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_DatevAcctExport extends org.compiere.model.PO implements I_DatevAcctExport, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -878844184L;

    /** Standard Constructor */
    public X_DatevAcctExport (final Properties ctx, final int DatevAcctExport_ID, @Nullable final String trxName)
    {
      super (ctx, DatevAcctExport_ID, trxName);
    }

    /** Load Constructor */
    public X_DatevAcctExport (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public org.compiere.model.I_C_Period getC_Period()
	{
		return get_ValueAsPO(COLUMNNAME_C_Period_ID, org.compiere.model.I_C_Period.class);
	}

	@Override
	public void setC_Period(final org.compiere.model.I_C_Period C_Period)
	{
		set_ValueFromPO(COLUMNNAME_C_Period_ID, org.compiere.model.I_C_Period.class, C_Period);
	}

	@Override
	public void setC_Period_ID (final int C_Period_ID)
	{
		if (C_Period_ID < 1) 
			set_Value (COLUMNNAME_C_Period_ID, null);
		else 
			set_Value (COLUMNNAME_C_Period_ID, C_Period_ID);
	}

	@Override
	public int getC_Period_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Period_ID);
	}

	@Override
	public void setDatevAcctExport_ID (final int DatevAcctExport_ID)
	{
		if (DatevAcctExport_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_DatevAcctExport_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_DatevAcctExport_ID, DatevAcctExport_ID);
	}

	@Override
	public int getDatevAcctExport_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_DatevAcctExport_ID);
	}

	@Override
	public void setExportBy_ID (final int ExportBy_ID)
	{
		if (ExportBy_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_ExportBy_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_ExportBy_ID, ExportBy_ID);
	}

	@Override
	public int getExportBy_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_ExportBy_ID);
	}

	@Override
	public void setExportDate (final @Nullable java.sql.Timestamp ExportDate)
	{
		set_ValueNoCheck (COLUMNNAME_ExportDate, ExportDate);
	}

	@Override
	public java.sql.Timestamp getExportDate() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_ExportDate);
	}

	/** 
	 * ExportType AD_Reference_ID=541172
	 * Reference name: DatevExportType
	 */
	public static final int EXPORTTYPE_AD_Reference_ID=541172;
	/** Payment = Payment */
	public static final String EXPORTTYPE_Payment = "Payment";
	/** Commission Invoice = CommissionInvoice */
	public static final String EXPORTTYPE_CommissionInvoice = "CommissionInvoice";
	/** Sales Invoice = SalesInvoice */
	public static final String EXPORTTYPE_SalesInvoice = "SalesInvoice";
	/** Credit Memo = CreditMemo */
	public static final String EXPORTTYPE_CreditMemo = "CreditMemo";
	@Override
	public void setExportType (final java.lang.String ExportType)
	{
		set_Value (COLUMNNAME_ExportType, ExportType);
	}

	@Override
	public java.lang.String getExportType() 
	{
		return get_ValueAsString(COLUMNNAME_ExportType);
	}

	@Override
	public void setProcessed (final boolean Processed)
	{
		set_Value (COLUMNNAME_Processed, Processed);
	}

	@Override
	public boolean isProcessed() 
	{
		return get_ValueAsBoolean(COLUMNNAME_Processed);
	}
}