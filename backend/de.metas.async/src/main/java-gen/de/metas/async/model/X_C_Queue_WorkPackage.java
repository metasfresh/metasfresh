// Generated Model - DO NOT CHANGE
package de.metas.async.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_Queue_WorkPackage
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_Queue_WorkPackage extends org.compiere.model.PO implements I_C_Queue_WorkPackage, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -2134246769L;

    /** Standard Constructor */
    public X_C_Queue_WorkPackage (final Properties ctx, final int C_Queue_WorkPackage_ID, @Nullable final String trxName)
    {
      super (ctx, C_Queue_WorkPackage_ID, trxName);
    }

    /** Load Constructor */
    public X_C_Queue_WorkPackage (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setAD_Issue_ID (final int AD_Issue_ID)
	{
		if (AD_Issue_ID < 1) 
			set_Value (COLUMNNAME_AD_Issue_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Issue_ID, AD_Issue_ID);
	}

	@Override
	public int getAD_Issue_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Issue_ID);
	}

	@Override
	public org.compiere.model.I_AD_Issue getAD_Issue()
	{
		return get_ValueAsPO(COLUMNNAME_AD_Issue_ID, org.compiere.model.I_AD_Issue.class);
	}

	@Override
	public void setAD_Issue(org.compiere.model.I_AD_Issue AD_Issue)
	{
		set_ValueFromPO(COLUMNNAME_AD_Issue_ID, org.compiere.model.I_AD_Issue.class, AD_Issue);
	}

	@Override
	public org.compiere.model.I_AD_PInstance getAD_PInstance()
	{
		return get_ValueAsPO(COLUMNNAME_AD_PInstance_ID, org.compiere.model.I_AD_PInstance.class);
	}

	@Override
	public void setAD_PInstance(final org.compiere.model.I_AD_PInstance AD_PInstance)
	{
		set_ValueFromPO(COLUMNNAME_AD_PInstance_ID, org.compiere.model.I_AD_PInstance.class, AD_PInstance);
	}

	@Override
	public void setAD_PInstance_ID (final int AD_PInstance_ID)
	{
		if (AD_PInstance_ID < 1) 
			set_Value (COLUMNNAME_AD_PInstance_ID, null);
		else 
			set_Value (COLUMNNAME_AD_PInstance_ID, AD_PInstance_ID);
	}

	@Override
	public int getAD_PInstance_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_PInstance_ID);
	}

	@Override
	public org.compiere.model.I_AD_Role getAD_Role()
	{
		return get_ValueAsPO(COLUMNNAME_AD_Role_ID, org.compiere.model.I_AD_Role.class);
	}

	@Override
	public void setAD_Role(final org.compiere.model.I_AD_Role AD_Role)
	{
		set_ValueFromPO(COLUMNNAME_AD_Role_ID, org.compiere.model.I_AD_Role.class, AD_Role);
	}

	@Override
	public void setAD_Role_ID (final int AD_Role_ID)
	{
		if (AD_Role_ID < 0) 
			set_ValueNoCheck (COLUMNNAME_AD_Role_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Role_ID, AD_Role_ID);
	}

	@Override
	public int getAD_Role_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Role_ID);
	}

	@Override
	public void setAD_User_ID (final int AD_User_ID)
	{
		if (AD_User_ID < 0) 
			set_ValueNoCheck (COLUMNNAME_AD_User_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_User_ID, AD_User_ID);
	}

	@Override
	public int getAD_User_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_User_ID);
	}

	@Override
	public void setAD_User_InCharge_ID (final int AD_User_InCharge_ID)
	{
		if (AD_User_InCharge_ID < 1) 
			set_Value (COLUMNNAME_AD_User_InCharge_ID, null);
		else 
			set_Value (COLUMNNAME_AD_User_InCharge_ID, AD_User_InCharge_ID);
	}

	@Override
	public int getAD_User_InCharge_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_User_InCharge_ID);
	}

	@Override
	public void setBatchEnqueuedCount (final int BatchEnqueuedCount)
	{
		set_Value (COLUMNNAME_BatchEnqueuedCount, BatchEnqueuedCount);
	}

	@Override
	public int getBatchEnqueuedCount() 
	{
		return get_ValueAsInt(COLUMNNAME_BatchEnqueuedCount);
	}

	@Override
	public I_C_Async_Batch getC_Async_Batch()
	{
		return get_ValueAsPO(COLUMNNAME_C_Async_Batch_ID, I_C_Async_Batch.class);
	}

	@Override
	public void setC_Async_Batch(final I_C_Async_Batch C_Async_Batch)
	{
		set_ValueFromPO(COLUMNNAME_C_Async_Batch_ID, I_C_Async_Batch.class, C_Async_Batch);
	}

	@Override
	public void setC_Async_Batch_ID (final int C_Async_Batch_ID)
	{
		if (C_Async_Batch_ID < 1) 
			set_Value (COLUMNNAME_C_Async_Batch_ID, null);
		else 
			set_Value (COLUMNNAME_C_Async_Batch_ID, C_Async_Batch_ID);
	}

	@Override
	public int getC_Async_Batch_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Async_Batch_ID);
	}

	@Override
	public I_C_Queue_PackageProcessor getC_Queue_PackageProcessor()
	{
		return get_ValueAsPO(COLUMNNAME_C_Queue_PackageProcessor_ID, I_C_Queue_PackageProcessor.class);
	}

	@Override
	public void setC_Queue_PackageProcessor(final I_C_Queue_PackageProcessor C_Queue_PackageProcessor)
	{
		set_ValueFromPO(COLUMNNAME_C_Queue_PackageProcessor_ID, I_C_Queue_PackageProcessor.class, C_Queue_PackageProcessor);
	}

	@Override
	public void setC_Queue_PackageProcessor_ID (final int C_Queue_PackageProcessor_ID)
	{
		if (C_Queue_PackageProcessor_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Queue_PackageProcessor_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Queue_PackageProcessor_ID, C_Queue_PackageProcessor_ID);
	}

	@Override
	public int getC_Queue_PackageProcessor_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Queue_PackageProcessor_ID);
	}

	@Override
	public void setC_Queue_WorkPackage_ID (final int C_Queue_WorkPackage_ID)
	{
		if (C_Queue_WorkPackage_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Queue_WorkPackage_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Queue_WorkPackage_ID, C_Queue_WorkPackage_ID);
	}

	@Override
	public int getC_Queue_WorkPackage_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Queue_WorkPackage_ID);
	}

	@Override
	public void setErrorMsg (final @Nullable String ErrorMsg)
	{
		set_Value (COLUMNNAME_ErrorMsg, ErrorMsg);
	}

	@Override
	public String getErrorMsg()
	{
		return get_ValueAsString(COLUMNNAME_ErrorMsg);
	}

	@Override
	public void setIsError (final boolean IsError)
	{
		set_Value (COLUMNNAME_IsError, IsError);
	}

	@Override
	public boolean isError() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsError);
	}

	@Override
	public void setIsErrorAcknowledged (final boolean IsErrorAcknowledged)
	{
		set_Value (COLUMNNAME_IsErrorAcknowledged, IsErrorAcknowledged);
	}

	@Override
	public boolean isErrorAcknowledged() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsErrorAcknowledged);
	}

	@Override
	public void setIsReadyForProcessing (final boolean IsReadyForProcessing)
	{
		set_Value (COLUMNNAME_IsReadyForProcessing, IsReadyForProcessing);
	}

	@Override
	public boolean isReadyForProcessing() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsReadyForProcessing);
	}

	@Override
	public void setLastDurationMillis (final int LastDurationMillis)
	{
		set_Value (COLUMNNAME_LastDurationMillis, LastDurationMillis);
	}

	@Override
	public int getLastDurationMillis() 
	{
		return get_ValueAsInt(COLUMNNAME_LastDurationMillis);
	}

	@Override
	public void setLastEndTime (final @Nullable java.sql.Timestamp LastEndTime)
	{
		set_Value (COLUMNNAME_LastEndTime, LastEndTime);
	}

	@Override
	public java.sql.Timestamp getLastEndTime() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_LastEndTime);
	}

	@Override
	public void setLastStartTime (final @Nullable java.sql.Timestamp LastStartTime)
	{
		set_Value (COLUMNNAME_LastStartTime, LastStartTime);
	}

	@Override
	public java.sql.Timestamp getLastStartTime() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_LastStartTime);
	}

	@Override
	public void setLocked (final boolean Locked)
	{
		throw new IllegalArgumentException ("Locked is virtual column");	}

	@Override
	public boolean isLocked() 
	{
		return get_ValueAsBoolean(COLUMNNAME_Locked);
	}

	/** 
	 * Priority AD_Reference_ID=154
	 * Reference name: _PriorityRule
	 */
	public static final int PRIORITY_AD_Reference_ID=154;
	/** High = 3 */
	public static final String PRIORITY_High = "3";
	/** Medium = 5 */
	public static final String PRIORITY_Medium = "5";
	/** Low = 7 */
	public static final String PRIORITY_Low = "7";
	/** Urgent = 1 */
	public static final String PRIORITY_Urgent = "1";
	/** Minor = 9 */
	public static final String PRIORITY_Minor = "9";
	@Override
	public void setPriority (final String Priority)
	{
		set_Value (COLUMNNAME_Priority, Priority);
	}

	@Override
	public String getPriority()
	{
		return get_ValueAsString(COLUMNNAME_Priority);
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
	public void setSkipped_Count (final int Skipped_Count)
	{
		set_Value (COLUMNNAME_Skipped_Count, Skipped_Count);
	}

	@Override
	public int getSkipped_Count() 
	{
		return get_ValueAsInt(COLUMNNAME_Skipped_Count);
	}

	@Override
	public void setSkipped_First_Time (final @Nullable java.sql.Timestamp Skipped_First_Time)
	{
		set_Value (COLUMNNAME_Skipped_First_Time, Skipped_First_Time);
	}

	@Override
	public java.sql.Timestamp getSkipped_First_Time() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_Skipped_First_Time);
	}

	@Override
	public void setSkipped_Last_Reason (final @Nullable String Skipped_Last_Reason)
	{
		set_Value (COLUMNNAME_Skipped_Last_Reason, Skipped_Last_Reason);
	}

	@Override
	public String getSkipped_Last_Reason()
	{
		return get_ValueAsString(COLUMNNAME_Skipped_Last_Reason);
	}

	@Override
	public void setSkippedAt (final @Nullable java.sql.Timestamp SkippedAt)
	{
		set_Value (COLUMNNAME_SkippedAt, SkippedAt);
	}

	@Override
	public java.sql.Timestamp getSkippedAt() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_SkippedAt);
	}

	@Override
	public void setSkipTimeoutMillis (final int SkipTimeoutMillis)
	{
		set_Value (COLUMNNAME_SkipTimeoutMillis, SkipTimeoutMillis);
	}

	@Override
	public int getSkipTimeoutMillis() 
	{
		return get_ValueAsInt(COLUMNNAME_SkipTimeoutMillis);
	}
}