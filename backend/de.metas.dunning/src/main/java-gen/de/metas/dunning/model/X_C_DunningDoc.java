// Generated Model - DO NOT CHANGE
package de.metas.dunning.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_DunningDoc
 *  @author metasfresh (generated)
 */
@SuppressWarnings("unused")
public class X_C_DunningDoc extends org.compiere.model.PO implements I_C_DunningDoc, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 2120654377L;

    /** Standard Constructor */
    public X_C_DunningDoc (final Properties ctx, final int C_DunningDoc_ID, @Nullable final String trxName)
    {
      super (ctx, C_DunningDoc_ID, trxName);
    }

    /** Load Constructor */
    public X_C_DunningDoc (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setBPartnerAddress (final @Nullable java.lang.String BPartnerAddress)
	{
		set_Value (COLUMNNAME_BPartnerAddress, BPartnerAddress);
	}

	@Override
	public java.lang.String getBPartnerAddress ()
	{
		return get_ValueAsString(COLUMNNAME_BPartnerAddress);
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
	public void setC_DocType_ID (final int C_DocType_ID)
	{
		if (C_DocType_ID < 0) 
			set_ValueNoCheck (COLUMNNAME_C_DocType_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_DocType_ID, C_DocType_ID);
	}

	@Override
	public int getC_DocType_ID ()
	{
		return get_ValueAsInt(COLUMNNAME_C_DocType_ID);
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
	public void setC_DunningDoc_ID (final int C_DunningDoc_ID)
	{
		if (C_DunningDoc_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_DunningDoc_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_DunningDoc_ID, C_DunningDoc_ID);
	}

	@Override
	public int getC_DunningDoc_ID ()
	{
		return get_ValueAsInt(COLUMNNAME_C_DunningDoc_ID);
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
	public void setDescription (final @Nullable java.lang.String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	@Override
	public java.lang.String getDescription ()
	{
		return get_ValueAsString(COLUMNNAME_Description);
	}

	/** 
	 * DocAction AD_Reference_ID=135
	 * Reference name: _Document Action
	 */
	public static final int DOCACTION_AD_Reference_ID=135;
	/** Complete = CO */
	public static final String DOCACTION_Complete = "CO";
	/** Approve = AP */
	public static final String DOCACTION_Approve = "AP";
	/** Reject = RJ */
	public static final String DOCACTION_Reject = "RJ";
	/** Post = PO */
	public static final String DOCACTION_Post = "PO";
	/** Void = VO */
	public static final String DOCACTION_Void = "VO";
	/** Close = CL */
	public static final String DOCACTION_Close = "CL";
	/** Reverse_Correct = RC */
	public static final String DOCACTION_Reverse_Correct = "RC";
	/** Reverse_Accrual = RA */
	public static final String DOCACTION_Reverse_Accrual = "RA";
	/** Invalidate = IN */
	public static final String DOCACTION_Invalidate = "IN";
	/** Re_Activate = RE */
	public static final String DOCACTION_Re_Activate = "RE";
	/** None = -- */
	public static final String DOCACTION_None = "--";
	/** Prepare = PR */
	public static final String DOCACTION_Prepare = "PR";
	/** Unlock = XL */
	public static final String DOCACTION_Unlock = "XL";
	/** WaitComplete = WC */
	public static final String DOCACTION_WaitComplete = "WC";
	/** UnClose = UC */
	public static final String DOCACTION_UnClose = "UC";
	@Override
	public void setDocAction (final java.lang.String DocAction)
	{
		set_Value (COLUMNNAME_DocAction, DocAction);
	}

	@Override
	public java.lang.String getDocAction ()
	{
		return get_ValueAsString(COLUMNNAME_DocAction);
	}

	/** 
	 * DocStatus AD_Reference_ID=131
	 * Reference name: _Document Status
	 */
	public static final int DOCSTATUS_AD_Reference_ID=131;
	/** Drafted = DR */
	public static final String DOCSTATUS_Drafted = "DR";
	/** Completed = CO */
	public static final String DOCSTATUS_Completed = "CO";
	/** Approved = AP */
	public static final String DOCSTATUS_Approved = "AP";
	/** NotApproved = NA */
	public static final String DOCSTATUS_NotApproved = "NA";
	/** Voided = VO */
	public static final String DOCSTATUS_Voided = "VO";
	/** Invalid = IN */
	public static final String DOCSTATUS_Invalid = "IN";
	/** Reversed = RE */
	public static final String DOCSTATUS_Reversed = "RE";
	/** Closed = CL */
	public static final String DOCSTATUS_Closed = "CL";
	/** Unknown = ?? */
	public static final String DOCSTATUS_Unknown = "??";
	/** InProgress = IP */
	public static final String DOCSTATUS_InProgress = "IP";
	/** WaitingPayment = WP */
	public static final String DOCSTATUS_WaitingPayment = "WP";
	/** WaitingConfirmation = WC */
	public static final String DOCSTATUS_WaitingConfirmation = "WC";
	@Override
	public void setDocStatus (final java.lang.String DocStatus)
	{
		set_Value (COLUMNNAME_DocStatus, DocStatus);
	}

	@Override
	public java.lang.String getDocStatus ()
	{
		return get_ValueAsString(COLUMNNAME_DocStatus);
	}

	@Override
	public void setDocumentNo (final java.lang.String DocumentNo)
	{
		set_Value (COLUMNNAME_DocumentNo, DocumentNo);
	}

	@Override
	public java.lang.String getDocumentNo ()
	{
		return get_ValueAsString(COLUMNNAME_DocumentNo);
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
	public void setIsUseBPartnerAddress (final boolean IsUseBPartnerAddress)
	{
		set_Value (COLUMNNAME_IsUseBPartnerAddress, IsUseBPartnerAddress);
	}

	@Override
	public boolean isUseBPartnerAddress ()
	{
		return get_ValueAsBoolean(COLUMNNAME_IsUseBPartnerAddress);
	}

	@Override
	public void setIsWriteOff (final boolean IsWriteOff)
	{
		throw new IllegalArgumentException ("IsWriteOff is virtual column");	}

	@Override
	public boolean isWriteOff ()
	{
		return get_ValueAsBoolean(COLUMNNAME_IsWriteOff);
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
			set_Value (COLUMNNAME_M_SectionCode_ID, null);
		else
			set_Value (COLUMNNAME_M_SectionCode_ID, M_SectionCode_ID);
	}

	@Override
	public int getM_SectionCode_ID()
	{
		return get_ValueAsInt(COLUMNNAME_M_SectionCode_ID);
	}

	/** Set Verarbeitet.
		@param Processed
		Checkbox sagt aus, ob der Beleg verarbeitet wurde.
	  */
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
	public void setProcessing (final boolean Processing)
	{
		set_Value (COLUMNNAME_Processing, Processing);
	}

	@Override
	public boolean isProcessing ()
	{
		return get_ValueAsBoolean(COLUMNNAME_Processing);
	}
}