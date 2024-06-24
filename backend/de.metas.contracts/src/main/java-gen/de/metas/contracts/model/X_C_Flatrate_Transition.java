// Generated Model - DO NOT CHANGE
package de.metas.contracts.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_Flatrate_Transition
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_Flatrate_Transition extends org.compiere.model.PO implements I_C_Flatrate_Transition, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 1787752213L;

    /** Standard Constructor */
    public X_C_Flatrate_Transition (final Properties ctx, final int C_Flatrate_Transition_ID, @Nullable final String trxName)
    {
      super (ctx, C_Flatrate_Transition_ID, trxName);
    }

    /** Load Constructor */
    public X_C_Flatrate_Transition (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public org.compiere.model.I_C_Calendar getC_Calendar_Contract()
	{
		return get_ValueAsPO(COLUMNNAME_C_Calendar_Contract_ID, org.compiere.model.I_C_Calendar.class);
	}

	@Override
	public void setC_Calendar_Contract(final org.compiere.model.I_C_Calendar C_Calendar_Contract)
	{
		set_ValueFromPO(COLUMNNAME_C_Calendar_Contract_ID, org.compiere.model.I_C_Calendar.class, C_Calendar_Contract);
	}

	@Override
	public void setC_Calendar_Contract_ID (final int C_Calendar_Contract_ID)
	{
		if (C_Calendar_Contract_ID < 1) 
			set_Value (COLUMNNAME_C_Calendar_Contract_ID, null);
		else 
			set_Value (COLUMNNAME_C_Calendar_Contract_ID, C_Calendar_Contract_ID);
	}

	@Override
	public int getC_Calendar_Contract_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Calendar_Contract_ID);
	}

	@Override
	public de.metas.contracts.model.I_C_Flatrate_Conditions getC_Flatrate_Conditions_Next()
	{
		return get_ValueAsPO(COLUMNNAME_C_Flatrate_Conditions_Next_ID, de.metas.contracts.model.I_C_Flatrate_Conditions.class);
	}

	@Override
	public void setC_Flatrate_Conditions_Next(final de.metas.contracts.model.I_C_Flatrate_Conditions C_Flatrate_Conditions_Next)
	{
		set_ValueFromPO(COLUMNNAME_C_Flatrate_Conditions_Next_ID, de.metas.contracts.model.I_C_Flatrate_Conditions.class, C_Flatrate_Conditions_Next);
	}

	@Override
	public void setC_Flatrate_Conditions_Next_ID (final int C_Flatrate_Conditions_Next_ID)
	{
		if (C_Flatrate_Conditions_Next_ID < 1) 
			set_Value (COLUMNNAME_C_Flatrate_Conditions_Next_ID, null);
		else 
			set_Value (COLUMNNAME_C_Flatrate_Conditions_Next_ID, C_Flatrate_Conditions_Next_ID);
	}

	@Override
	public int getC_Flatrate_Conditions_Next_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Flatrate_Conditions_Next_ID);
	}

	@Override
	public void setC_Flatrate_Transition_ID (final int C_Flatrate_Transition_ID)
	{
		if (C_Flatrate_Transition_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Flatrate_Transition_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Flatrate_Transition_ID, C_Flatrate_Transition_ID);
	}

	@Override
	public int getC_Flatrate_Transition_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Flatrate_Transition_ID);
	}

	@Override
	public void setDeliveryInterval (final int DeliveryInterval)
	{
		set_Value (COLUMNNAME_DeliveryInterval, DeliveryInterval);
	}

	@Override
	public int getDeliveryInterval() 
	{
		return get_ValueAsInt(COLUMNNAME_DeliveryInterval);
	}

	/** 
	 * DeliveryIntervalUnit AD_Reference_ID=540281
	 * Reference name: TermDurationUnit
	 */
	public static final int DELIVERYINTERVALUNIT_AD_Reference_ID=540281;
	/** Monat(e) = month */
	public static final String DELIVERYINTERVALUNIT_MonatE = "month";
	/** Woche(n) = week */
	public static final String DELIVERYINTERVALUNIT_WocheN = "week";
	/** Tag(e) = day */
	public static final String DELIVERYINTERVALUNIT_TagE = "day";
	/** Jahr(e) = year */
	public static final String DELIVERYINTERVALUNIT_JahrE = "year";
	@Override
	public void setDeliveryIntervalUnit (final @Nullable java.lang.String DeliveryIntervalUnit)
	{
		set_Value (COLUMNNAME_DeliveryIntervalUnit, DeliveryIntervalUnit);
	}

	@Override
	public java.lang.String getDeliveryIntervalUnit() 
	{
		return get_ValueAsString(COLUMNNAME_DeliveryIntervalUnit);
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
	public java.lang.String getDocAction() 
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
	public java.lang.String getDocStatus() 
	{
		return get_ValueAsString(COLUMNNAME_DocStatus);
	}

	@Override
	public void setEndsWithCalendarYear (final boolean EndsWithCalendarYear)
	{
		set_Value (COLUMNNAME_EndsWithCalendarYear, EndsWithCalendarYear);
	}

	@Override
	public boolean isEndsWithCalendarYear() 
	{
		return get_ValueAsBoolean(COLUMNNAME_EndsWithCalendarYear);
	}

	/** 
	 * ExtensionType AD_Reference_ID=540843
	 * Reference name: ExtensionType
	 */
	public static final int EXTENSIONTYPE_AD_Reference_ID=540843;
	/** ExtendAll = EA */
	public static final String EXTENSIONTYPE_ExtendAll = "EA";
	/** ExtendOne = EO */
	public static final String EXTENSIONTYPE_ExtendOne = "EO";
	@Override
	public void setExtensionType (final @Nullable java.lang.String ExtensionType)
	{
		set_Value (COLUMNNAME_ExtensionType, ExtensionType);
	}

	@Override
	public java.lang.String getExtensionType() 
	{
		return get_ValueAsString(COLUMNNAME_ExtensionType);
	}

	@Override
	public void setIsAutoCompleteNewTerm (final boolean IsAutoCompleteNewTerm)
	{
		set_Value (COLUMNNAME_IsAutoCompleteNewTerm, IsAutoCompleteNewTerm);
	}

	@Override
	public boolean isAutoCompleteNewTerm() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsAutoCompleteNewTerm);
	}

	@Override
	public void setIsNotifyUserInCharge (final boolean IsNotifyUserInCharge)
	{
		set_Value (COLUMNNAME_IsNotifyUserInCharge, IsNotifyUserInCharge);
	}

	@Override
	public boolean isNotifyUserInCharge() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsNotifyUserInCharge);
	}

	@Override
	public void setName (final java.lang.String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	@Override
	public java.lang.String getName() 
	{
		return get_ValueAsString(COLUMNNAME_Name);
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

	@Override
	public void setProcessing (final boolean Processing)
	{
		set_Value (COLUMNNAME_Processing, Processing);
	}

	@Override
	public boolean isProcessing() 
	{
		return get_ValueAsBoolean(COLUMNNAME_Processing);
	}

	@Override
	public void setTermDuration (final int TermDuration)
	{
		set_Value (COLUMNNAME_TermDuration, TermDuration);
	}

	@Override
	public int getTermDuration() 
	{
		return get_ValueAsInt(COLUMNNAME_TermDuration);
	}

	/** 
	 * TermDurationUnit AD_Reference_ID=540281
	 * Reference name: TermDurationUnit
	 */
	public static final int TERMDURATIONUNIT_AD_Reference_ID=540281;
	/** Monat(e) = month */
	public static final String TERMDURATIONUNIT_MonatE = "month";
	/** Woche(n) = week */
	public static final String TERMDURATIONUNIT_WocheN = "week";
	/** Tag(e) = day */
	public static final String TERMDURATIONUNIT_TagE = "day";
	/** Jahr(e) = year */
	public static final String TERMDURATIONUNIT_JahrE = "year";
	@Override
	public void setTermDurationUnit (final java.lang.String TermDurationUnit)
	{
		set_Value (COLUMNNAME_TermDurationUnit, TermDurationUnit);
	}

	@Override
	public java.lang.String getTermDurationUnit() 
	{
		return get_ValueAsString(COLUMNNAME_TermDurationUnit);
	}

	@Override
	public void setTermOfNotice (final int TermOfNotice)
	{
		set_Value (COLUMNNAME_TermOfNotice, TermOfNotice);
	}

	@Override
	public int getTermOfNotice() 
	{
		return get_ValueAsInt(COLUMNNAME_TermOfNotice);
	}

	/** 
	 * TermOfNoticeUnit AD_Reference_ID=540281
	 * Reference name: TermDurationUnit
	 */
	public static final int TERMOFNOTICEUNIT_AD_Reference_ID=540281;
	/** Monat(e) = month */
	public static final String TERMOFNOTICEUNIT_MonatE = "month";
	/** Woche(n) = week */
	public static final String TERMOFNOTICEUNIT_WocheN = "week";
	/** Tag(e) = day */
	public static final String TERMOFNOTICEUNIT_TagE = "day";
	/** Jahr(e) = year */
	public static final String TERMOFNOTICEUNIT_JahrE = "year";
	@Override
	public void setTermOfNoticeUnit (final java.lang.String TermOfNoticeUnit)
	{
		set_Value (COLUMNNAME_TermOfNoticeUnit, TermOfNoticeUnit);
	}

	@Override
	public java.lang.String getTermOfNoticeUnit() 
	{
		return get_ValueAsString(COLUMNNAME_TermOfNoticeUnit);
	}
}