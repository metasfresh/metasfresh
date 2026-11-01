// Generated Model - DO NOT CHANGE
package de.metas.externalsystem.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for ExternalSystem_ScriptedExportConversion_Status
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_ExternalSystem_ScriptedExportConversion_Status extends org.compiere.model.PO implements I_ExternalSystem_ScriptedExportConversion_Status, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -106460927L;

    /** Standard Constructor */
    public X_ExternalSystem_ScriptedExportConversion_Status (final Properties ctx, final int ExternalSystem_ScriptedExportConversion_Status_ID, @Nullable final String trxName)
    {
      super (ctx, ExternalSystem_ScriptedExportConversion_Status_ID, trxName);
    }

    /** Load Constructor */
    public X_ExternalSystem_ScriptedExportConversion_Status (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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

	/** 
	 * ExportStatus AD_Reference_ID=542104
	 * Reference name: ExternalSystem_ExportStatus
	 */
	public static final int EXPORTSTATUS_AD_Reference_ID=542104;
	/** Pending = P */
	public static final String EXPORTSTATUS_Pending = "P";
	/** Enqueued = U */
	public static final String EXPORTSTATUS_Enqueued = "U";
	/** SendingStarted = D */
	public static final String EXPORTSTATUS_SendingStarted = "D";
	/** Sent = S */
	public static final String EXPORTSTATUS_Sent = "S";
	/** Error = E */
	public static final String EXPORTSTATUS_Error = "E";
	/** Invalid = I */
	public static final String EXPORTSTATUS_Invalid = "I";
	/** DontSend = N */
	public static final String EXPORTSTATUS_DontSend = "N";
	@Override
	public void setExportStatus (final java.lang.String ExportStatus)
	{
		set_Value (COLUMNNAME_ExportStatus, ExportStatus);
	}

	@Override
	public java.lang.String getExportStatus() 
	{
		return get_ValueAsString(COLUMNNAME_ExportStatus);
	}

	@Override
	public void setExternalSystem_Config_ScriptedExportConversion_ID (final int ExternalSystem_Config_ScriptedExportConversion_ID)
	{
		if (ExternalSystem_Config_ScriptedExportConversion_ID < 1) 
			set_Value (COLUMNNAME_ExternalSystem_Config_ScriptedExportConversion_ID, null);
		else 
			set_Value (COLUMNNAME_ExternalSystem_Config_ScriptedExportConversion_ID, ExternalSystem_Config_ScriptedExportConversion_ID);
	}

	@Override
	public int getExternalSystem_Config_ScriptedExportConversion_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_ExternalSystem_Config_ScriptedExportConversion_ID);
	}

	@Override
	public void setExternalSystem_ScriptedExportConversion_Status_ID (final int ExternalSystem_ScriptedExportConversion_Status_ID)
	{
		if (ExternalSystem_ScriptedExportConversion_Status_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_ExternalSystem_ScriptedExportConversion_Status_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_ExternalSystem_ScriptedExportConversion_Status_ID, ExternalSystem_ScriptedExportConversion_Status_ID);
	}

	@Override
	public int getExternalSystem_ScriptedExportConversion_Status_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_ExternalSystem_ScriptedExportConversion_Status_ID);
	}

	@Override
	public void setHttpResponseCode (final int HttpResponseCode)
	{
		set_Value (COLUMNNAME_HttpResponseCode, HttpResponseCode);
	}

	@Override
	public int getHttpResponseCode() 
	{
		return get_ValueAsInt(COLUMNNAME_HttpResponseCode);
	}

	@Override
	public void setIsResend (final boolean IsResend)
	{
		set_Value (COLUMNNAME_IsResend, IsResend);
	}

	@Override
	public boolean isResend() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsResend);
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
	public void setStatusMessage (final @Nullable java.lang.String StatusMessage)
	{
		set_Value (COLUMNNAME_StatusMessage, StatusMessage);
	}

	@Override
	public java.lang.String getStatusMessage() 
	{
		return get_ValueAsString(COLUMNNAME_StatusMessage);
	}
}