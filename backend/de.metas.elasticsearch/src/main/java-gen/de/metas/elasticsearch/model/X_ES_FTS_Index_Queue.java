// Generated Model - DO NOT CHANGE
package de.metas.elasticsearch.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for ES_FTS_Index_Queue
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_ES_FTS_Index_Queue extends org.compiere.model.PO implements I_ES_FTS_Index_Queue, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1801793198L;

    /** Standard Constructor */
    public X_ES_FTS_Index_Queue (final Properties ctx, final int ES_FTS_Index_Queue_ID, @Nullable final String trxName)
    {
      super (ctx, ES_FTS_Index_Queue_ID, trxName);
    }

    /** Load Constructor */
    public X_ES_FTS_Index_Queue (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setAD_Table_ID (final int AD_Table_ID)
	{
		if (AD_Table_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Table_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Table_ID, AD_Table_ID);
	}

	@Override
	public int getAD_Table_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Table_ID);
	}

	@Override
	public de.metas.elasticsearch.model.I_ES_FTS_Config getES_FTS_Config()
	{
		return get_ValueAsPO(COLUMNNAME_ES_FTS_Config_ID, de.metas.elasticsearch.model.I_ES_FTS_Config.class);
	}

	@Override
	public void setES_FTS_Config(final de.metas.elasticsearch.model.I_ES_FTS_Config ES_FTS_Config)
	{
		set_ValueFromPO(COLUMNNAME_ES_FTS_Config_ID, de.metas.elasticsearch.model.I_ES_FTS_Config.class, ES_FTS_Config);
	}

	@Override
	public void setES_FTS_Config_ID (final int ES_FTS_Config_ID)
	{
		if (ES_FTS_Config_ID < 1) 
			set_Value (COLUMNNAME_ES_FTS_Config_ID, null);
		else 
			set_Value (COLUMNNAME_ES_FTS_Config_ID, ES_FTS_Config_ID);
	}

	@Override
	public int getES_FTS_Config_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_ES_FTS_Config_ID);
	}

	@Override
	public void setES_FTS_Index_Queue_ID (final int ES_FTS_Index_Queue_ID)
	{
		if (ES_FTS_Index_Queue_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_ES_FTS_Index_Queue_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_ES_FTS_Index_Queue_ID, ES_FTS_Index_Queue_ID);
	}

	@Override
	public int getES_FTS_Index_Queue_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_ES_FTS_Index_Queue_ID);
	}

	/** 
	 * EventType AD_Reference_ID=541373
	 * Reference name: ES_FTS_Index_Queue_EventType
	 */
	public static final int EVENTTYPE_AD_Reference_ID=541373;
	/** Update = U */
	public static final String EVENTTYPE_Update = "U";
	/** Delete = D */
	public static final String EVENTTYPE_Delete = "D";
	/** DeleteIndex = X */
	public static final String EVENTTYPE_DeleteIndex = "X";
	@Override
	public void setEventType (final java.lang.String EventType)
	{
		set_ValueNoCheck (COLUMNNAME_EventType, EventType);
	}

	@Override
	public java.lang.String getEventType() 
	{
		return get_ValueAsString(COLUMNNAME_EventType);
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
	public void setProcessingTag (final @Nullable java.lang.String ProcessingTag)
	{
		set_Value (COLUMNNAME_ProcessingTag, ProcessingTag);
	}

	@Override
	public java.lang.String getProcessingTag() 
	{
		return get_ValueAsString(COLUMNNAME_ProcessingTag);
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
	public int getRecord_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Record_ID);
	}
}