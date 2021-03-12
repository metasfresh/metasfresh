// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for AD_WF_Activity
 *  @author metasfresh (generated) 
 */
public class X_AD_WF_Activity extends org.compiere.model.PO implements I_AD_WF_Activity, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1587274328L;

    /** Standard Constructor */
    public X_AD_WF_Activity (final Properties ctx, final int AD_WF_Activity_ID, @Nullable final String trxName)
    {
      super (ctx, AD_WF_Activity_ID, trxName);
    }

    /** Load Constructor */
    public X_AD_WF_Activity (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setAD_Message_ID (final int AD_Message_ID)
	{
		if (AD_Message_ID < 1) 
			set_Value (COLUMNNAME_AD_Message_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Message_ID, AD_Message_ID);
	}

	@Override
	public int getAD_Message_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Message_ID);
	}

	@Override
	public void setAD_Table_ID (final int AD_Table_ID)
	{
		if (AD_Table_ID < 1) 
			set_Value (COLUMNNAME_AD_Table_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Table_ID, AD_Table_ID);
	}

	@Override
	public int getAD_Table_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Table_ID);
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
	public void setAD_WF_Activity_ID (final int AD_WF_Activity_ID)
	{
		if (AD_WF_Activity_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_WF_Activity_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_WF_Activity_ID, AD_WF_Activity_ID);
	}

	@Override
	public int getAD_WF_Activity_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_WF_Activity_ID);
	}

	@Override
	public void setAD_WF_Node_ID (final int AD_WF_Node_ID)
	{
		if (AD_WF_Node_ID < 1) 
			set_Value (COLUMNNAME_AD_WF_Node_ID, null);
		else 
			set_Value (COLUMNNAME_AD_WF_Node_ID, AD_WF_Node_ID);
	}

	@Override
	public int getAD_WF_Node_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_WF_Node_ID);
	}

	@Override
	public void setAD_WF_Process_ID (final int AD_WF_Process_ID)
	{
		if (AD_WF_Process_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_WF_Process_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_WF_Process_ID, AD_WF_Process_ID);
	}

	@Override
	public int getAD_WF_Process_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_WF_Process_ID);
	}

	@Override
	public void setAD_WF_Responsible_ID (final int AD_WF_Responsible_ID)
	{
		if (AD_WF_Responsible_ID < 1) 
			set_Value (COLUMNNAME_AD_WF_Responsible_ID, null);
		else 
			set_Value (COLUMNNAME_AD_WF_Responsible_ID, AD_WF_Responsible_ID);
	}

	@Override
	public int getAD_WF_Responsible_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_WF_Responsible_ID);
	}

	@Override
	public void setAD_Workflow_ID (final int AD_Workflow_ID)
	{
		if (AD_Workflow_ID < 1) 
			set_Value (COLUMNNAME_AD_Workflow_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Workflow_ID, AD_Workflow_ID);
	}

	@Override
	public int getAD_Workflow_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Workflow_ID);
	}

	@Override
	public void setDateLastAlert (final java.sql.Timestamp DateLastAlert)
	{
		set_Value (COLUMNNAME_DateLastAlert, DateLastAlert);
	}

	@Override
	public java.sql.Timestamp getDateLastAlert() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_DateLastAlert);
	}

	@Override
	public void setDynPriorityStart (final int DynPriorityStart)
	{
		set_Value (COLUMNNAME_DynPriorityStart, DynPriorityStart);
	}

	@Override
	public int getDynPriorityStart() 
	{
		return get_ValueAsInt(COLUMNNAME_DynPriorityStart);
	}

	@Override
	public void setEndWaitTime (final java.sql.Timestamp EndWaitTime)
	{
		set_Value (COLUMNNAME_EndWaitTime, EndWaitTime);
	}

	@Override
	public java.sql.Timestamp getEndWaitTime() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_EndWaitTime);
	}

	@Override
	public void setPriority (final int Priority)
	{
		set_Value (COLUMNNAME_Priority, Priority);
	}

	@Override
	public int getPriority() 
	{
		return get_ValueAsInt(COLUMNNAME_Priority);
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
	public void setRecord_ID (final int Record_ID)
	{
		if (Record_ID < 0) 
			set_Value (COLUMNNAME_Record_ID, null);
		else 
			set_Value (COLUMNNAME_Record_ID, Record_ID);
	}

	@Override
	public int getRecord_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Record_ID);
	}

	@Override
	public void setTextMsg (final java.lang.String TextMsg)
	{
		set_Value (COLUMNNAME_TextMsg, TextMsg);
	}

	@Override
	public java.lang.String getTextMsg() 
	{
		return get_ValueAsString(COLUMNNAME_TextMsg);
	}

	/** 
	 * WFState AD_Reference_ID=305
	 * Reference name: WF_Instance State
	 */
	public static final int WFSTATE_AD_Reference_ID=305;
	/** NotStarted = ON */
	public static final String WFSTATE_NotStarted = "ON";
	/** Running = OR */
	public static final String WFSTATE_Running = "OR";
	/** Suspended = OS */
	public static final String WFSTATE_Suspended = "OS";
	/** Completed = CC */
	public static final String WFSTATE_Completed = "CC";
	/** Aborted = CA */
	public static final String WFSTATE_Aborted = "CA";
	/** Terminated = CT */
	public static final String WFSTATE_Terminated = "CT";
	@Override
	public void setWFState (final java.lang.String WFState)
	{
		set_Value (COLUMNNAME_WFState, WFState);
	}

	@Override
	public java.lang.String getWFState() 
	{
		return get_ValueAsString(COLUMNNAME_WFState);
	}
}