// Generated Model - DO NOT CHANGE
package de.metas.datev.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for DATEV_ExportLine
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_DATEV_ExportLine extends org.compiere.model.PO implements I_DATEV_ExportLine, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -118335786L;

    /** Standard Constructor */
    public X_DATEV_ExportLine (final Properties ctx, final int DATEV_ExportLine_ID, @Nullable final String trxName)
    {
      super (ctx, DATEV_ExportLine_ID, trxName);
    }

    /** Load Constructor */
    public X_DATEV_ExportLine (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setActivityName (final @Nullable java.lang.String ActivityName)
	{
		set_ValueNoCheck (COLUMNNAME_ActivityName, ActivityName);
	}

	@Override
	public java.lang.String getActivityName() 
	{
		return get_ValueAsString(COLUMNNAME_ActivityName);
	}

	@Override
	public void setAmt (final BigDecimal Amt)
	{
		set_Value (COLUMNNAME_Amt, Amt);
	}

	@Override
	public BigDecimal getAmt() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_Amt);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setBPName (final @Nullable java.lang.String BPName)
	{
		set_Value (COLUMNNAME_BPName, BPName);
	}

	@Override
	public java.lang.String getBPName() 
	{
		return get_ValueAsString(COLUMNNAME_BPName);
	}

	@Override
	public void setBPValue (final @Nullable java.lang.String BPValue)
	{
		set_Value (COLUMNNAME_BPValue, BPValue);
	}

	@Override
	public java.lang.String getBPValue() 
	{
		return get_ValueAsString(COLUMNNAME_BPValue);
	}

	@Override
	public void setC_Activity_ID (final int C_Activity_ID)
	{
		if (C_Activity_ID < 1) 
			set_Value (COLUMNNAME_C_Activity_ID, null);
		else 
			set_Value (COLUMNNAME_C_Activity_ID, C_Activity_ID);
	}

	@Override
	public int getC_Activity_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Activity_ID);
	}

	@Override
	public void setC_BPartner_ID (final int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_ID, C_BPartner_ID);
	}

	@Override
	public int getC_BPartner_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_ID);
	}

	@Override
	public void setC_DocType_Name (final @Nullable java.lang.String C_DocType_Name)
	{
		set_Value (COLUMNNAME_C_DocType_Name, C_DocType_Name);
	}

	@Override
	public java.lang.String getC_DocType_Name() 
	{
		return get_ValueAsString(COLUMNNAME_C_DocType_Name);
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
	public void setCR_Account (final java.lang.String CR_Account)
	{
		set_Value (COLUMNNAME_CR_Account, CR_Account);
	}

	@Override
	public java.lang.String getCR_Account() 
	{
		return get_ValueAsString(COLUMNNAME_CR_Account);
	}

	@Override
	public void setC_Tax_Rate (final @Nullable BigDecimal C_Tax_Rate)
	{
		set_Value (COLUMNNAME_C_Tax_Rate, C_Tax_Rate);
	}

	@Override
	public BigDecimal getC_Tax_Rate() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_C_Tax_Rate);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setCurrency (final @Nullable java.lang.String Currency)
	{
		set_Value (COLUMNNAME_Currency, Currency);
	}

	@Override
	public java.lang.String getCurrency() 
	{
		return get_ValueAsString(COLUMNNAME_Currency);
	}

	@Override
	public void setDateAcct (final @Nullable java.sql.Timestamp DateAcct)
	{
		set_Value (COLUMNNAME_DateAcct, DateAcct);
	}

	@Override
	public java.sql.Timestamp getDateAcct() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_DateAcct);
	}

	@Override
	public de.metas.datev.model.I_DATEV_Export getDATEV_Export()
	{
		return get_ValueAsPO(COLUMNNAME_DATEV_Export_ID, de.metas.datev.model.I_DATEV_Export.class);
	}

	@Override
	public void setDATEV_Export(final de.metas.datev.model.I_DATEV_Export DATEV_Export)
	{
		set_ValueFromPO(COLUMNNAME_DATEV_Export_ID, de.metas.datev.model.I_DATEV_Export.class, DATEV_Export);
	}

	@Override
	public void setDATEV_Export_ID (final int DATEV_Export_ID)
	{
		if (DATEV_Export_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_DATEV_Export_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_DATEV_Export_ID, DATEV_Export_ID);
	}

	@Override
	public int getDATEV_Export_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_DATEV_Export_ID);
	}

	@Override
	public void setDATEV_ExportLine_ID (final int DATEV_ExportLine_ID)
	{
		if (DATEV_ExportLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_DATEV_ExportLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_DATEV_ExportLine_ID, DATEV_ExportLine_ID);
	}

	@Override
	public int getDATEV_ExportLine_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_DATEV_ExportLine_ID);
	}

	@Override
	public void setDebitOrCreditIndicator (final boolean DebitOrCreditIndicator)
	{
		set_Value (COLUMNNAME_DebitOrCreditIndicator, DebitOrCreditIndicator);
	}

	@Override
	public boolean isDebitOrCreditIndicator() 
	{
		return get_ValueAsBoolean(COLUMNNAME_DebitOrCreditIndicator);
	}

	@Override
	public void setDescription (final @Nullable java.lang.String Description)
	{
		set_ValueNoCheck (COLUMNNAME_Description, Description);
	}

	@Override
	public java.lang.String getDescription() 
	{
		return get_ValueAsString(COLUMNNAME_Description);
	}

	@Override
	public void setDocBaseType (final @Nullable java.lang.String DocBaseType)
	{
		set_Value (COLUMNNAME_DocBaseType, DocBaseType);
	}

	@Override
	public java.lang.String getDocBaseType() 
	{
		return get_ValueAsString(COLUMNNAME_DocBaseType);
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
	public void setDR_Account (final java.lang.String DR_Account)
	{
		set_Value (COLUMNNAME_DR_Account, DR_Account);
	}

	@Override
	public java.lang.String getDR_Account() 
	{
		return get_ValueAsString(COLUMNNAME_DR_Account);
	}

	@Override
	public void setDueDate (final @Nullable java.sql.Timestamp DueDate)
	{
		set_ValueNoCheck (COLUMNNAME_DueDate, DueDate);
	}

	@Override
	public java.sql.Timestamp getDueDate() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_DueDate);
	}

	@Override
	public org.compiere.model.I_Fact_Acct getFact_Acct()
	{
		return get_ValueAsPO(COLUMNNAME_Fact_Acct_ID, org.compiere.model.I_Fact_Acct.class);
	}

	@Override
	public void setFact_Acct(final org.compiere.model.I_Fact_Acct Fact_Acct)
	{
		set_ValueFromPO(COLUMNNAME_Fact_Acct_ID, org.compiere.model.I_Fact_Acct.class, Fact_Acct);
	}

	@Override
	public void setFact_Acct_ID (final int Fact_Acct_ID)
	{
		if (Fact_Acct_ID < 1) 
			set_Value (COLUMNNAME_Fact_Acct_ID, null);
		else 
			set_Value (COLUMNNAME_Fact_Acct_ID, Fact_Acct_ID);
	}

	@Override
	public int getFact_Acct_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Fact_Acct_ID);
	}

	@Override
	public void setGrandTotal (final @Nullable BigDecimal GrandTotal)
	{
		set_Value (COLUMNNAME_GrandTotal, GrandTotal);
	}

	@Override
	public BigDecimal getGrandTotal() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_GrandTotal);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setTaxAmt (final @Nullable BigDecimal TaxAmt)
	{
		set_Value (COLUMNNAME_TaxAmt, TaxAmt);
	}

	@Override
	public BigDecimal getTaxAmt() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_TaxAmt);
		return bd != null ? bd : BigDecimal.ZERO;
	}
}