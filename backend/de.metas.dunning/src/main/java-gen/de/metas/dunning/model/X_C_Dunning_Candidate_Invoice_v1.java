// Generated Model - DO NOT CHANGE
package de.metas.dunning.model;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_Dunning_Candidate_Invoice_v1
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_Dunning_Candidate_Invoice_v1 extends org.compiere.model.PO implements I_C_Dunning_Candidate_Invoice_v1, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 1876105953L;

    /** Standard Constructor */
    public X_C_Dunning_Candidate_Invoice_v1 (final Properties ctx, final int C_Dunning_Candidate_Invoice_v1_ID, @Nullable final String trxName)
    {
      super (ctx, C_Dunning_Candidate_Invoice_v1_ID, trxName);
    }

    /** Load Constructor */
    public X_C_Dunning_Candidate_Invoice_v1 (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setAD_User_ID (final int AD_User_ID)
	{
		if (AD_User_ID < 0) 
			set_Value (COLUMNNAME_AD_User_ID, null);
		else 
			set_Value (COLUMNNAME_AD_User_ID, AD_User_ID);
	}

	@Override
	public int getAD_User_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_User_ID);
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
	public void setC_BPartner_Location_ID (final int C_BPartner_Location_ID)
	{
		if (C_BPartner_Location_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_Location_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_Location_ID, C_BPartner_Location_ID);
	}

	@Override
	public int getC_BPartner_Location_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_Location_ID);
	}

	@Override
	public void setC_Currency_ID (final int C_Currency_ID)
	{
		if (C_Currency_ID < 1) 
			set_Value (COLUMNNAME_C_Currency_ID, null);
		else 
			set_Value (COLUMNNAME_C_Currency_ID, C_Currency_ID);
	}

	@Override
	public int getC_Currency_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Currency_ID);
	}

	@Override
	public org.compiere.model.I_C_Dunning getC_Dunning()
	{
		return get_ValueAsPO(COLUMNNAME_C_Dunning_ID, org.compiere.model.I_C_Dunning.class);
	}

	@Override
	public void setC_Dunning(final org.compiere.model.I_C_Dunning C_Dunning)
	{
		set_ValueFromPO(COLUMNNAME_C_Dunning_ID, org.compiere.model.I_C_Dunning.class, C_Dunning);
	}

	@Override
	public void setC_Dunning_ID (final int C_Dunning_ID)
	{
		if (C_Dunning_ID < 1) 
			set_Value (COLUMNNAME_C_Dunning_ID, null);
		else 
			set_Value (COLUMNNAME_C_Dunning_ID, C_Dunning_ID);
	}

	@Override
	public int getC_Dunning_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Dunning_ID);
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
	public org.compiere.model.I_C_InvoicePaySchedule getC_InvoicePaySchedule()
	{
		return get_ValueAsPO(COLUMNNAME_C_InvoicePaySchedule_ID, org.compiere.model.I_C_InvoicePaySchedule.class);
	}

	@Override
	public void setC_InvoicePaySchedule(final org.compiere.model.I_C_InvoicePaySchedule C_InvoicePaySchedule)
	{
		set_ValueFromPO(COLUMNNAME_C_InvoicePaySchedule_ID, org.compiere.model.I_C_InvoicePaySchedule.class, C_InvoicePaySchedule);
	}

	@Override
	public void setC_InvoicePaySchedule_ID (final int C_InvoicePaySchedule_ID)
	{
		if (C_InvoicePaySchedule_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_InvoicePaySchedule_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_InvoicePaySchedule_ID, C_InvoicePaySchedule_ID);
	}

	@Override
	public int getC_InvoicePaySchedule_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_InvoicePaySchedule_ID);
	}

	@Override
	public void setC_PaymentTerm_ID (final int C_PaymentTerm_ID)
	{
		if (C_PaymentTerm_ID < 1) 
			set_Value (COLUMNNAME_C_PaymentTerm_ID, null);
		else 
			set_Value (COLUMNNAME_C_PaymentTerm_ID, C_PaymentTerm_ID);
	}

	@Override
	public int getC_PaymentTerm_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_PaymentTerm_ID);
	}

	@Override
	public void setDateInvoiced (final @Nullable java.sql.Timestamp DateInvoiced)
	{
		set_Value (COLUMNNAME_DateInvoiced, DateInvoiced);
	}

	@Override
	public java.sql.Timestamp getDateInvoiced() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_DateInvoiced);
	}

	@Override
	public void setDueDate (final @Nullable java.sql.Timestamp DueDate)
	{
		set_Value (COLUMNNAME_DueDate, DueDate);
	}

	@Override
	public java.sql.Timestamp getDueDate() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_DueDate);
	}

	@Override
	public void setDunningGrace (final @Nullable java.sql.Timestamp DunningGrace)
	{
		set_Value (COLUMNNAME_DunningGrace, DunningGrace);
	}

	@Override
	public java.sql.Timestamp getDunningGrace() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_DunningGrace);
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
	public void setIsInDispute (final boolean IsInDispute)
	{
		set_Value (COLUMNNAME_IsInDispute, IsInDispute);
	}

	@Override
	public boolean isInDispute() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsInDispute);
	}

	@Override
	public org.compiere.model.I_M_SectionCode getM_SectionCode()
	{
		return get_ValueAsPO(COLUMNNAME_M_SectionCode_ID, org.compiere.model.I_M_SectionCode.class);
	}

	@Override
	public void setM_SectionCode(final org.compiere.model.I_M_SectionCode M_SectionCode)
	{
		set_ValueFromPO(COLUMNNAME_M_SectionCode_ID, org.compiere.model.I_M_SectionCode.class, M_SectionCode);
	}

	@Override
	public void setM_SectionCode_ID (final int M_SectionCode_ID)
	{
		if (M_SectionCode_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_SectionCode_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_SectionCode_ID, M_SectionCode_ID);
	}

	@Override
	public int getM_SectionCode_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_SectionCode_ID);
	}

	@Override
	public void setOpenAmt (final @Nullable BigDecimal OpenAmt)
	{
		set_Value (COLUMNNAME_OpenAmt, OpenAmt);
	}

	@Override
	public BigDecimal getOpenAmt() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_OpenAmt);
		return bd != null ? bd : BigDecimal.ZERO;
	}
}