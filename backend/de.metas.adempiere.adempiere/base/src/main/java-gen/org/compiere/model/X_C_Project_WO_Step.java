// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for C_Project_WO_Step
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_Project_WO_Step extends org.compiere.model.PO implements I_C_Project_WO_Step, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -852883066L;

    /** Standard Constructor */
    public X_C_Project_WO_Step (final Properties ctx, final int C_Project_WO_Step_ID, @Nullable final String trxName)
    {
      super (ctx, C_Project_WO_Step_ID, trxName);
    }

    /** Load Constructor */
    public X_C_Project_WO_Step (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setC_Project_ID (final int C_Project_ID)
	{
		if (C_Project_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Project_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Project_ID, C_Project_ID);
	}

	@Override
	public int getC_Project_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Project_ID);
	}

	@Override
	public void setC_Project_WO_Step_ID (final int C_Project_WO_Step_ID)
	{
		if (C_Project_WO_Step_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Project_WO_Step_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Project_WO_Step_ID, C_Project_WO_Step_ID);
	}

	@Override
	public int getC_Project_WO_Step_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Project_WO_Step_ID);
	}

	@Override
	public void setDateEnd (final @Nullable java.sql.Timestamp DateEnd)
	{
		set_Value (COLUMNNAME_DateEnd, DateEnd);
	}

	@Override
	public java.sql.Timestamp getDateEnd() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_DateEnd);
	}

	@Override
	public void setDateStart (final @Nullable java.sql.Timestamp DateStart)
	{
		set_Value (COLUMNNAME_DateStart, DateStart);
	}

	@Override
	public java.sql.Timestamp getDateStart() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_DateStart);
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
	public void setExternalId (final @Nullable java.lang.String ExternalId)
	{
		set_Value (COLUMNNAME_ExternalId, ExternalId);
	}

	@Override
	public java.lang.String getExternalId() 
	{
		return get_ValueAsString(COLUMNNAME_ExternalId);
	}

	@Override
	public void setIsManuallyLocked (final boolean IsManuallyLocked)
	{
		set_Value (COLUMNNAME_IsManuallyLocked, IsManuallyLocked);
	}

	@Override
	public boolean isManuallyLocked() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsManuallyLocked);
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
	public void setSeqNo (final int SeqNo)
	{
		set_Value (COLUMNNAME_SeqNo, SeqNo);
	}

	@Override
	public int getSeqNo() 
	{
		return get_ValueAsInt(COLUMNNAME_SeqNo);
	}

	@Override
	public void setWOActualFacilityHours (final int WOActualFacilityHours)
	{
		set_Value (COLUMNNAME_WOActualFacilityHours, WOActualFacilityHours);
	}

	@Override
	public int getWOActualFacilityHours() 
	{
		return get_ValueAsInt(COLUMNNAME_WOActualFacilityHours);
	}

	@Override
	public void setWOActualManHours (final int WOActualManHours)
	{
		set_Value (COLUMNNAME_WOActualManHours, WOActualManHours);
	}

	@Override
	public int getWOActualManHours() 
	{
		return get_ValueAsInt(COLUMNNAME_WOActualManHours);
	}

	@Override
	public void setWODeliveryDate (final @Nullable java.sql.Timestamp WODeliveryDate)
	{
		set_Value (COLUMNNAME_WODeliveryDate, WODeliveryDate);
	}

	@Override
	public java.sql.Timestamp getWODeliveryDate() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_WODeliveryDate);
	}

	@Override
	public void setWODueDate (final @Nullable java.sql.Timestamp WODueDate)
	{
		set_Value (COLUMNNAME_WODueDate, WODueDate);
	}

	@Override
	public java.sql.Timestamp getWODueDate() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_WODueDate);
	}

	@Override
	public void setWOFindingsCreatedDate (final @Nullable java.sql.Timestamp WOFindingsCreatedDate)
	{
		set_Value (COLUMNNAME_WOFindingsCreatedDate, WOFindingsCreatedDate);
	}

	@Override
	public java.sql.Timestamp getWOFindingsCreatedDate() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_WOFindingsCreatedDate);
	}

	@Override
	public void setWOFindingsReleasedDate (final @Nullable java.sql.Timestamp WOFindingsReleasedDate)
	{
		set_Value (COLUMNNAME_WOFindingsReleasedDate, WOFindingsReleasedDate);
	}

	@Override
	public java.sql.Timestamp getWOFindingsReleasedDate() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_WOFindingsReleasedDate);
	}

	@Override
	public void setWOPartialReportDate (final @Nullable java.sql.Timestamp WOPartialReportDate)
	{
		set_Value (COLUMNNAME_WOPartialReportDate, WOPartialReportDate);
	}

	@Override
	public java.sql.Timestamp getWOPartialReportDate() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_WOPartialReportDate);
	}

	@Override
	public void setWOPlannedPersonDurationHours (final int WOPlannedPersonDurationHours)
	{
		set_Value (COLUMNNAME_WOPlannedPersonDurationHours, WOPlannedPersonDurationHours);
	}

	@Override
	public int getWOPlannedPersonDurationHours() 
	{
		return get_ValueAsInt(COLUMNNAME_WOPlannedPersonDurationHours);
	}

	@Override
	public void setWOPlannedResourceDurationHours (final int WOPlannedResourceDurationHours)
	{
		set_Value (COLUMNNAME_WOPlannedResourceDurationHours, WOPlannedResourceDurationHours);
	}

	@Override
	public int getWOPlannedResourceDurationHours() 
	{
		return get_ValueAsInt(COLUMNNAME_WOPlannedResourceDurationHours);
	}

	@Override
	public void setWO_Step_ExternalId (final @Nullable java.lang.String WO_Step_ExternalId)
	{
		throw new IllegalArgumentException ("WO_Step_ExternalId is virtual column");	}

	@Override
	public java.lang.String getWO_Step_ExternalId() 
	{
		return get_ValueAsString(COLUMNNAME_WO_Step_ExternalId);
	}

	/** 
	 * WOStepStatus AD_Reference_ID=541599
	 * Reference name: WOStepStatus
	 */
	public static final int WOSTEPSTATUS_AD_Reference_ID=541599;
	/** CREATED = 0 */
	public static final String WOSTEPSTATUS_CREATED = "0";
	/** RECEIVED = 1 */
	public static final String WOSTEPSTATUS_RECEIVED = "1";
	/** RELEASED = 2 */
	public static final String WOSTEPSTATUS_RELEASED = "2";
	/** EARMARKED = 3 */
	public static final String WOSTEPSTATUS_EARMARKED = "3";
	/** READYFORTESTING = 4 */
	public static final String WOSTEPSTATUS_READYFORTESTING = "4";
	/** INTESTING = 5 */
	public static final String WOSTEPSTATUS_INTESTING = "5";
	/** EXECUTED = 6 */
	public static final String WOSTEPSTATUS_EXECUTED = "6";
	/** READY = 7 */
	public static final String WOSTEPSTATUS_READY = "7";
	/** CANCELED = 100 */
	public static final String WOSTEPSTATUS_CANCELED = "100";
	@Override
	public void setWOStepStatus (final java.lang.String WOStepStatus)
	{
		set_Value (COLUMNNAME_WOStepStatus, WOStepStatus);
	}

	@Override
	public java.lang.String getWOStepStatus() 
	{
		return get_ValueAsString(COLUMNNAME_WOStepStatus);
	}

	@Override
	public void setWOTargetEndDate (final @Nullable java.sql.Timestamp WOTargetEndDate)
	{
		set_Value (COLUMNNAME_WOTargetEndDate, WOTargetEndDate);
	}

	@Override
	public java.sql.Timestamp getWOTargetEndDate() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_WOTargetEndDate);
	}

	@Override
	public void setWOTargetStartDate (final @Nullable java.sql.Timestamp WOTargetStartDate)
	{
		set_Value (COLUMNNAME_WOTargetStartDate, WOTargetStartDate);
	}

	@Override
	public java.sql.Timestamp getWOTargetStartDate() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_WOTargetStartDate);
	}
}