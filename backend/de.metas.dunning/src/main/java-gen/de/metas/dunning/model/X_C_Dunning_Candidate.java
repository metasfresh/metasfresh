// Generated Model - DO NOT CHANGE
package de.metas.dunning.model;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_Dunning_Candidate
 *  @author metasfresh (generated)
 */
@SuppressWarnings("unused")
public class X_C_Dunning_Candidate extends org.compiere.model.PO implements I_C_Dunning_Candidate, org.compiere.model.I_Persistent
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1935613316L;

	/** Standard Constructor */
    public X_C_Dunning_Candidate (final Properties ctx, final int C_Dunning_Candidate_ID, @Nullable final String trxName)
	{
		super (ctx, C_Dunning_Candidate_ID, trxName);
	}

	/** Load Constructor */
    public X_C_Dunning_Candidate (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setAD_Table_ID (final int AD_Table_ID)
	{
		if (AD_Table_ID < 1)
			set_ValueNoCheck (COLUMNNAME_AD_Table_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_AD_Table_ID, AD_Table_ID);
	}

	@Override
	public int getAD_Table_ID ()
	{
		return get_ValueAsInt(COLUMNNAME_AD_Table_ID);
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
	public int getC_BPartner_ID ()
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
	public int getC_BPartner_Location_ID ()
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
	public int getC_Currency_ID ()
	{
		return get_ValueAsInt(COLUMNNAME_C_Currency_ID);
	}

	@Override
	public void setC_Dunning_Candidate_ID (final int C_Dunning_Candidate_ID)
	{
		if (C_Dunning_Candidate_ID < 1)
			set_ValueNoCheck (COLUMNNAME_C_Dunning_Candidate_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_C_Dunning_Candidate_ID, C_Dunning_Candidate_ID);
	}

	@Override
	public int getC_Dunning_Candidate_ID ()
	{
		return get_ValueAsInt(COLUMNNAME_C_Dunning_Candidate_ID);
	}

	@Override
	public void setC_Dunning_Contact_ID (final int C_Dunning_Contact_ID)
	{
		if (C_Dunning_Contact_ID < 1)
			set_Value (COLUMNNAME_C_Dunning_Contact_ID, null);
		else
			set_Value (COLUMNNAME_C_Dunning_Contact_ID, C_Dunning_Contact_ID);
	}

	@Override
	public int getC_Dunning_Contact_ID ()
	{
		return get_ValueAsInt(COLUMNNAME_C_Dunning_Contact_ID);
	}

	@Override
	public org.compiere.model.I_C_DunningLevel getC_DunningLevel()
	{
		return get_ValueAsPO(COLUMNNAME_C_DunningLevel_ID, org.compiere.model.I_C_DunningLevel.class);
	}

	@Override
	public void setC_DunningLevel(final org.compiere.model.I_C_DunningLevel C_DunningLevel)
	{
		set_ValueFromPO(COLUMNNAME_C_DunningLevel_ID, org.compiere.model.I_C_DunningLevel.class, C_DunningLevel);
	}

	@Override
	public void setC_DunningLevel_ID (final int C_DunningLevel_ID)
	{
		if (C_DunningLevel_ID < 1)
			set_Value (COLUMNNAME_C_DunningLevel_ID, null);
		else
			set_Value (COLUMNNAME_C_DunningLevel_ID, C_DunningLevel_ID);
	}

	@Override
	public int getC_DunningLevel_ID ()
	{
		return get_ValueAsInt(COLUMNNAME_C_DunningLevel_ID);
	}

	@Override
	public void setDaysDue (final int DaysDue)
	{
		set_Value (COLUMNNAME_DaysDue, DaysDue);
	}

	@Override
	public int getDaysDue ()
	{
		return get_ValueAsInt(COLUMNNAME_DaysDue);
	}

	@Override
	public void setDocumentNo (final @Nullable java.lang.String DocumentNo)
	{
		set_ValueNoCheck (COLUMNNAME_DocumentNo, DocumentNo);
	}

	@Override
	public java.lang.String getDocumentNo ()
	{
		return get_ValueAsString(COLUMNNAME_DocumentNo);
	}

	@Override
	public void setDueDate (final java.sql.Timestamp DueDate)
	{
		set_Value (COLUMNNAME_DueDate, DueDate);
	}

	@Override
	public java.sql.Timestamp getDueDate ()
	{
		return get_ValueAsTimestamp(COLUMNNAME_DueDate);
	}

	@Override
	public void setDunningDate (final java.sql.Timestamp DunningDate)
	{
		set_Value (COLUMNNAME_DunningDate, DunningDate);
	}

	@Override
	public java.sql.Timestamp getDunningDate ()
	{
		return get_ValueAsTimestamp(COLUMNNAME_DunningDate);
	}

	@Override
	public void setDunningDateEffective (final @Nullable java.sql.Timestamp DunningDateEffective)
	{
		set_Value (COLUMNNAME_DunningDateEffective, DunningDateEffective);
	}

	@Override
	public java.sql.Timestamp getDunningDateEffective ()
	{
		return get_ValueAsTimestamp(COLUMNNAME_DunningDateEffective);
	}

	@Override
	public void setDunningGrace (final @Nullable java.sql.Timestamp DunningGrace)
	{
		set_Value (COLUMNNAME_DunningGrace, DunningGrace);
	}

	@Override
	public java.sql.Timestamp getDunningGrace ()
	{
		return get_ValueAsTimestamp(COLUMNNAME_DunningGrace);
	}

	@Override
	public void setDunningInterestAmt (final @Nullable BigDecimal DunningInterestAmt)
	{
		set_Value (COLUMNNAME_DunningInterestAmt, DunningInterestAmt);
	}

	@Override
	public BigDecimal getDunningInterestAmt()
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_DunningInterestAmt);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setFeeAmt (final @Nullable BigDecimal FeeAmt)
	{
		set_Value (COLUMNNAME_FeeAmt, FeeAmt);
	}

	@Override
	public BigDecimal getFeeAmt()
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_FeeAmt);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setIsDunningDocProcessed (final boolean IsDunningDocProcessed)
	{
		set_Value (COLUMNNAME_IsDunningDocProcessed, IsDunningDocProcessed);
	}

	@Override
	public boolean isDunningDocProcessed ()
	{
		return get_ValueAsBoolean(COLUMNNAME_IsDunningDocProcessed);
	}

	@Override
	public void setIsWriteOff (final boolean IsWriteOff)
	{
		set_Value (COLUMNNAME_IsWriteOff, IsWriteOff);
	}

	@Override
	public boolean isWriteOff ()
	{
		return get_ValueAsBoolean(COLUMNNAME_IsWriteOff);
	}

	@Override
	public void setM_SectionCode_ID (final int M_SectionCode_ID)
	{
		if (M_SectionCode_ID < 1)
			set_Value (COLUMNNAME_M_SectionCode_ID, null);
		else
			set_Value (COLUMNNAME_M_SectionCode_ID, M_SectionCode_ID);
	}

	@Override
	public int getM_SectionCode_ID()
	{
		return get_ValueAsInt(COLUMNNAME_M_SectionCode_ID);
	}

	/** Set Offener Betrag.
	 @param OpenAmt Offener Betrag	  */
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

	@Override
	public void setPOReference (final @Nullable java.lang.String POReference)
	{
		set_Value (COLUMNNAME_POReference, POReference);
	}

	@Override
	public java.lang.String getPOReference()
	{
		return get_ValueAsString(COLUMNNAME_POReference);
	}

	@Override
	public void setProcessed (final boolean Processed)
	{
		set_Value (COLUMNNAME_Processed, Processed);
	}

	@Override
	public boolean isProcessed ()
	{
		return get_ValueAsBoolean(COLUMNNAME_Processed);
	}

	@Override
	public void setRecord_ID (final int Record_ID)
	{
		if (Record_ID < 0)
			set_ValueNoCheck (COLUMNNAME_Record_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_Record_ID, Record_ID);
	}

	@Override
	public int getRecord_ID ()
	{
		return get_ValueAsInt(COLUMNNAME_Record_ID);
	}

	@Override
	public void setTotalAmt (final @Nullable BigDecimal TotalAmt)
	{
		set_Value (COLUMNNAME_TotalAmt, TotalAmt);
	}

	@Override
	public BigDecimal getTotalAmt()
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_TotalAmt);
		return bd != null ? bd : BigDecimal.ZERO;
	}
}