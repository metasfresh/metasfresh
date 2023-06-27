// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for GPLR_Report_Line
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_GPLR_Report_Line extends org.compiere.model.PO implements I_GPLR_Report_Line, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 1588563245L;

    /** Standard Constructor */
    public X_GPLR_Report_Line (final Properties ctx, final int GPLR_Report_Line_ID, @Nullable final String trxName)
    {
      super (ctx, GPLR_Report_Line_ID, trxName);
    }

    /** Load Constructor */
    public X_GPLR_Report_Line (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setAmount_FC (final @Nullable BigDecimal Amount_FC)
	{
		set_Value (COLUMNNAME_Amount_FC, Amount_FC);
	}

	@Override
	public BigDecimal getAmount_FC() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_Amount_FC);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setAmount_LC (final @Nullable BigDecimal Amount_LC)
	{
		set_Value (COLUMNNAME_Amount_LC, Amount_LC);
	}

	@Override
	public BigDecimal getAmount_LC() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_Amount_LC);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setBatch (final @Nullable java.lang.String Batch)
	{
		set_Value (COLUMNNAME_Batch, Batch);
	}

	@Override
	public java.lang.String getBatch() 
	{
		return get_ValueAsString(COLUMNNAME_Batch);
	}

	@Override
	public void setC_UOM_ID (final int C_UOM_ID)
	{
		if (C_UOM_ID < 1) 
			set_Value (COLUMNNAME_C_UOM_ID, null);
		else 
			set_Value (COLUMNNAME_C_UOM_ID, C_UOM_ID);
	}

	@Override
	public int getC_UOM_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_UOM_ID);
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
	public void setDocumentNo (final @Nullable java.lang.String DocumentNo)
	{
		set_Value (COLUMNNAME_DocumentNo, DocumentNo);
	}

	@Override
	public java.lang.String getDocumentNo() 
	{
		return get_ValueAsString(COLUMNNAME_DocumentNo);
	}

	@Override
	public void setForeignCurrencyCode (final @Nullable java.lang.String ForeignCurrencyCode)
	{
		set_Value (COLUMNNAME_ForeignCurrencyCode, ForeignCurrencyCode);
	}

	@Override
	public java.lang.String getForeignCurrencyCode() 
	{
		return get_ValueAsString(COLUMNNAME_ForeignCurrencyCode);
	}

	@Override
	public org.compiere.model.I_GPLR_Report getGPLR_Report()
	{
		return get_ValueAsPO(COLUMNNAME_GPLR_Report_ID, org.compiere.model.I_GPLR_Report.class);
	}

	@Override
	public void setGPLR_Report(final org.compiere.model.I_GPLR_Report GPLR_Report)
	{
		set_ValueFromPO(COLUMNNAME_GPLR_Report_ID, org.compiere.model.I_GPLR_Report.class, GPLR_Report);
	}

	@Override
	public void setGPLR_Report_ID (final int GPLR_Report_ID)
	{
		if (GPLR_Report_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_GPLR_Report_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_GPLR_Report_ID, GPLR_Report_ID);
	}

	@Override
	public int getGPLR_Report_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_GPLR_Report_ID);
	}

	@Override
	public void setGPLR_Report_Line_ID (final int GPLR_Report_Line_ID)
	{
		if (GPLR_Report_Line_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_GPLR_Report_Line_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_GPLR_Report_Line_ID, GPLR_Report_Line_ID);
	}

	@Override
	public int getGPLR_Report_Line_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_GPLR_Report_Line_ID);
	}

	@Override
	public void setLine (final java.lang.String Line)
	{
		set_Value (COLUMNNAME_Line, Line);
	}

	@Override
	public java.lang.String getLine() 
	{
		return get_ValueAsString(COLUMNNAME_Line);
	}

	@Override
	public void setLocalCurrencyCode (final @Nullable java.lang.String LocalCurrencyCode)
	{
		set_Value (COLUMNNAME_LocalCurrencyCode, LocalCurrencyCode);
	}

	@Override
	public java.lang.String getLocalCurrencyCode() 
	{
		return get_ValueAsString(COLUMNNAME_LocalCurrencyCode);
	}

	@Override
	public void setPrice_FC (final @Nullable BigDecimal Price_FC)
	{
		set_Value (COLUMNNAME_Price_FC, Price_FC);
	}

	@Override
	public BigDecimal getPrice_FC() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_Price_FC);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQty (final @Nullable BigDecimal Qty)
	{
		set_Value (COLUMNNAME_Qty, Qty);
	}

	@Override
	public BigDecimal getQty() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_Qty);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setSeqNo (final int SeqNo)
	{
		set_Value (COLUMNNAME_SeqNo, SeqNo);
	}

	@Override
	public int getSeqNo() 
	{
		return get_ValueAsInt(COLUMNNAME_SeqNo);
	}
}