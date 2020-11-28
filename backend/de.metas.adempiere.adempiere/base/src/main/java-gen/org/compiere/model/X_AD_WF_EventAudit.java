// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for AD_WF_EventAudit
 *  @author metasfresh (generated) 
 */
public class X_AD_WF_EventAudit extends org.compiere.model.PO implements I_AD_WF_EventAudit, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 2070718024L;

    /** Standard Constructor */
    public X_AD_WF_EventAudit (final Properties ctx, final int AD_WF_EventAudit_ID, @Nullable final String trxName)
    {
      super (ctx, AD_WF_EventAudit_ID, trxName);
    }

    /** Load Constructor */
    public X_AD_WF_EventAudit (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setAD_WF_EventAudit_ID (final int AD_WF_EventAudit_ID)
	{
		if (AD_WF_EventAudit_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_WF_EventAudit_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_WF_EventAudit_ID, AD_WF_EventAudit_ID);
	}

	@Override
	public int getAD_WF_EventAudit_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_WF_EventAudit_ID);
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
	public void setAttributeName (final java.lang.String AttributeName)
	{
		set_Value (COLUMNNAME_AttributeName, AttributeName);
	}

	@Override
	public java.lang.String getAttributeName() 
	{
		return get_ValueAsString(COLUMNNAME_AttributeName);
	}

	@Override
	public void setDescription (final java.lang.String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	@Override
	public java.lang.String getDescription() 
	{
		return get_ValueAsString(COLUMNNAME_Description);
	}

	@Override
	public void setElapsedTimeMS (final BigDecimal ElapsedTimeMS)
	{
		set_Value (COLUMNNAME_ElapsedTimeMS, ElapsedTimeMS);
	}

	@Override
	public BigDecimal getElapsedTimeMS() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_ElapsedTimeMS);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	/** 
	 * EventType AD_Reference_ID=306
	 * Reference name: WF_EventType
	 */
	public static final int EVENTTYPE_AD_Reference_ID=306;
	/** Process Created = PC */
	public static final String EVENTTYPE_ProcessCreated = "PC";
	/** State Changed = SC */
	public static final String EVENTTYPE_StateChanged = "SC";
	/** Process Completed = PX */
	public static final String EVENTTYPE_ProcessCompleted = "PX";
	/** Trace = TR */
	public static final String EVENTTYPE_Trace = "TR";
	@Override
	public void setEventType (final java.lang.String EventType)
	{
		set_Value (COLUMNNAME_EventType, EventType);
	}

	@Override
	public java.lang.String getEventType() 
	{
		return get_ValueAsString(COLUMNNAME_EventType);
	}

	@Override
	public void setNewValue (final java.lang.String NewValue)
	{
		set_Value (COLUMNNAME_NewValue, NewValue);
	}

	@Override
	public java.lang.String getNewValue() 
	{
		return get_ValueAsString(COLUMNNAME_NewValue);
	}

	@Override
	public void setOldValue (final java.lang.String OldValue)
	{
		set_Value (COLUMNNAME_OldValue, OldValue);
	}

	@Override
	public java.lang.String getOldValue() 
	{
		return get_ValueAsString(COLUMNNAME_OldValue);
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