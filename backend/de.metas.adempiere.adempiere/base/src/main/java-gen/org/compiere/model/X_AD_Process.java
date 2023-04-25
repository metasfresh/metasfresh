// Generated Model - DO NOT CHANGE
package org.compiere.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for AD_Process
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_AD_Process extends org.compiere.model.PO implements I_AD_Process, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1946797104L;

    /** Standard Constructor */
    public X_AD_Process (final Properties ctx, final int AD_Process_ID, @Nullable final String trxName)
    {
      super (ctx, AD_Process_ID, trxName);
    }

    /** Load Constructor */
    public X_AD_Process (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
    {
      super (ctx, rs, trxName);
    }


	/** Load Meta Data */
	@Override
	protected org.compiere.model.POInfo initPO(final Properties ctx)
	{
		return org.compiere.model.POInfo.getPOInfo(Table_Name);
	}

	/** 
	 * AccessLevel AD_Reference_ID=5
	 * Reference name: AD_Table Access Levels
	 */
	public static final int ACCESSLEVEL_AD_Reference_ID=5;
	/** Organization = 1 */
	public static final String ACCESSLEVEL_Organization = "1";
	/** ClientPlusOrganization = 3 */
	public static final String ACCESSLEVEL_ClientPlusOrganization = "3";
	/** SystemOnly = 4 */
	public static final String ACCESSLEVEL_SystemOnly = "4";
	/** All = 7 */
	public static final String ACCESSLEVEL_All = "7";
	/** SystemPlusClient = 6 */
	public static final String ACCESSLEVEL_SystemPlusClient = "6";
	/** ClientOnly = 2 */
	public static final String ACCESSLEVEL_ClientOnly = "2";
	@Override
	public void setAccessLevel (final java.lang.String AccessLevel)
	{
		set_Value (COLUMNNAME_AccessLevel, AccessLevel);
	}

	@Override
	public java.lang.String getAccessLevel() 
	{
		return get_ValueAsString(COLUMNNAME_AccessLevel);
	}

	@Override
	public org.compiere.model.I_AD_Form getAD_Form()
	{
		return get_ValueAsPO(COLUMNNAME_AD_Form_ID, org.compiere.model.I_AD_Form.class);
	}

	@Override
	public void setAD_Form(final org.compiere.model.I_AD_Form AD_Form)
	{
		set_ValueFromPO(COLUMNNAME_AD_Form_ID, org.compiere.model.I_AD_Form.class, AD_Form);
	}

	@Override
	public void setAD_Form_ID (final int AD_Form_ID)
	{
		if (AD_Form_ID < 1) 
			set_Value (COLUMNNAME_AD_Form_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Form_ID, AD_Form_ID);
	}

	@Override
	public int getAD_Form_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Form_ID);
	}

	@Override
	public org.compiere.model.I_AD_PrintFormat getAD_PrintFormat()
	{
		return get_ValueAsPO(COLUMNNAME_AD_PrintFormat_ID, org.compiere.model.I_AD_PrintFormat.class);
	}

	@Override
	public void setAD_PrintFormat(final org.compiere.model.I_AD_PrintFormat AD_PrintFormat)
	{
		set_ValueFromPO(COLUMNNAME_AD_PrintFormat_ID, org.compiere.model.I_AD_PrintFormat.class, AD_PrintFormat);
	}

	@Override
	public void setAD_PrintFormat_ID (final int AD_PrintFormat_ID)
	{
		if (AD_PrintFormat_ID < 1) 
			set_Value (COLUMNNAME_AD_PrintFormat_ID, null);
		else 
			set_Value (COLUMNNAME_AD_PrintFormat_ID, AD_PrintFormat_ID);
	}

	@Override
	public int getAD_PrintFormat_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_PrintFormat_ID);
	}

	@Override
	public void setAD_Process_ID (final int AD_Process_ID)
	{
		if (AD_Process_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Process_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Process_ID, AD_Process_ID);
	}

	@Override
	public int getAD_Process_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Process_ID);
	}

	@Override
	public org.compiere.model.I_AD_ReportView getAD_ReportView()
	{
		return get_ValueAsPO(COLUMNNAME_AD_ReportView_ID, org.compiere.model.I_AD_ReportView.class);
	}

	@Override
	public void setAD_ReportView(final org.compiere.model.I_AD_ReportView AD_ReportView)
	{
		set_ValueFromPO(COLUMNNAME_AD_ReportView_ID, org.compiere.model.I_AD_ReportView.class, AD_ReportView);
	}

	@Override
	public void setAD_ReportView_ID (final int AD_ReportView_ID)
	{
		if (AD_ReportView_ID < 1) 
			set_Value (COLUMNNAME_AD_ReportView_ID, null);
		else 
			set_Value (COLUMNNAME_AD_ReportView_ID, AD_ReportView_ID);
	}

	@Override
	public int getAD_ReportView_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_ReportView_ID);
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
	public void setAllowProcessReRun (final boolean AllowProcessReRun)
	{
		set_Value (COLUMNNAME_AllowProcessReRun, AllowProcessReRun);
	}

	@Override
	public boolean isAllowProcessReRun() 
	{
		return get_ValueAsBoolean(COLUMNNAME_AllowProcessReRun);
	}

	@Override
	public void setClassname (final @Nullable java.lang.String Classname)
	{
		set_Value (COLUMNNAME_Classname, Classname);
	}

	@Override
	public java.lang.String getClassname() 
	{
		return get_ValueAsString(COLUMNNAME_Classname);
	}

	@Override
	public void setCopyFromProcess (final @Nullable java.lang.String CopyFromProcess)
	{
		set_Value (COLUMNNAME_CopyFromProcess, CopyFromProcess);
	}

	@Override
	public java.lang.String getCopyFromProcess() 
	{
		return get_ValueAsString(COLUMNNAME_CopyFromProcess);
	}

	@Override
	public void setCSVFieldDelimiter (final @Nullable java.lang.String CSVFieldDelimiter)
	{
		set_Value (COLUMNNAME_CSVFieldDelimiter, CSVFieldDelimiter);
	}

	@Override
	public java.lang.String getCSVFieldDelimiter() 
	{
		return get_ValueAsString(COLUMNNAME_CSVFieldDelimiter);
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
	 * EntityType AD_Reference_ID=389
	 * Reference name: _EntityTypeNew
	 */
	public static final int ENTITYTYPE_AD_Reference_ID=389;
	@Override
	public void setEntityType (final java.lang.String EntityType)
	{
		set_Value (COLUMNNAME_EntityType, EntityType);
	}

	@Override
	public java.lang.String getEntityType() 
	{
		return get_ValueAsString(COLUMNNAME_EntityType);
	}

	@Override
	public void setHelp (final @Nullable java.lang.String Help)
	{
		set_Value (COLUMNNAME_Help, Help);
	}

	@Override
	public java.lang.String getHelp() 
	{
		return get_ValueAsString(COLUMNNAME_Help);
	}

	@Override
	public void setIsApplySecuritySettings (final boolean IsApplySecuritySettings)
	{
		set_Value (COLUMNNAME_IsApplySecuritySettings, IsApplySecuritySettings);
	}

	@Override
	public boolean isApplySecuritySettings() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsApplySecuritySettings);
	}

	@Override
	public void setIsBetaFunctionality (final boolean IsBetaFunctionality)
	{
		set_Value (COLUMNNAME_IsBetaFunctionality, IsBetaFunctionality);
	}

	@Override
	public boolean isBetaFunctionality() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsBetaFunctionality);
	}

	@Override
	public void setIsDirectPrint (final boolean IsDirectPrint)
	{
		set_Value (COLUMNNAME_IsDirectPrint, IsDirectPrint);
	}

	@Override
	public boolean isDirectPrint() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsDirectPrint);
	}

	@Override
	public void setIsNotifyUserAfterExecution (final boolean IsNotifyUserAfterExecution)
	{
		set_Value (COLUMNNAME_IsNotifyUserAfterExecution, IsNotifyUserAfterExecution);
	}

	@Override
	public boolean isNotifyUserAfterExecution() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsNotifyUserAfterExecution);
	}

	@Override
	public void setIsOneInstanceOnly (final boolean IsOneInstanceOnly)
	{
		set_Value (COLUMNNAME_IsOneInstanceOnly, IsOneInstanceOnly);
	}

	@Override
	public boolean isOneInstanceOnly() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsOneInstanceOnly);
	}

	@Override
	public void setIsReport (final boolean IsReport)
	{
		set_Value (COLUMNNAME_IsReport, IsReport);
	}

	@Override
	public boolean isReport() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsReport);
	}

	@Override
	public void setIsServerProcess (final boolean IsServerProcess)
	{
		set_Value (COLUMNNAME_IsServerProcess, IsServerProcess);
	}

	@Override
	public boolean isServerProcess() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsServerProcess);
	}

	@Override
	public void setIsTranslateExcelHeaders (final boolean IsTranslateExcelHeaders)
	{
		set_Value (COLUMNNAME_IsTranslateExcelHeaders, IsTranslateExcelHeaders);
	}

	@Override
	public boolean isTranslateExcelHeaders() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsTranslateExcelHeaders);
	}

	@Override
	public void setIsUpdateExportDate (final boolean IsUpdateExportDate)
	{
		set_Value (COLUMNNAME_IsUpdateExportDate, IsUpdateExportDate);
	}

	@Override
	public boolean isUpdateExportDate() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsUpdateExportDate);
	}

	@Override
	public void setIsUseBPartnerLanguage (final boolean IsUseBPartnerLanguage)
	{
		set_Value (COLUMNNAME_IsUseBPartnerLanguage, IsUseBPartnerLanguage);
	}

	@Override
	public boolean isUseBPartnerLanguage() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsUseBPartnerLanguage);
	}

	@Override
	public void setJasperReport (final @Nullable java.lang.String JasperReport)
	{
		set_Value (COLUMNNAME_JasperReport, JasperReport);
	}

	@Override
	public java.lang.String getJasperReport() 
	{
		return get_ValueAsString(COLUMNNAME_JasperReport);
	}

	@Override
	public void setJasperReport_Tabular (final @Nullable java.lang.String JasperReport_Tabular)
	{
		set_Value (COLUMNNAME_JasperReport_Tabular, JasperReport_Tabular);
	}

	@Override
	public java.lang.String getJasperReport_Tabular() 
	{
		return get_ValueAsString(COLUMNNAME_JasperReport_Tabular);
	}

	@Override
	public void setJSONPath (final @Nullable java.lang.String JSONPath)
	{
		set_Value (COLUMNNAME_JSONPath, JSONPath);
	}

	@Override
	public java.lang.String getJSONPath() 
	{
		return get_ValueAsString(COLUMNNAME_JSONPath);
	}

	@Override
	public void setLockWaitTimeout (final int LockWaitTimeout)
	{
		set_Value (COLUMNNAME_LockWaitTimeout, LockWaitTimeout);
	}

	@Override
	public int getLockWaitTimeout() 
	{
		return get_ValueAsInt(COLUMNNAME_LockWaitTimeout);
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

	/** 
	 * PostgrestResponseFormat AD_Reference_ID=541210
	 * Reference name: PostgrestResponseFormat
	 */
	public static final int POSTGRESTRESPONSEFORMAT_AD_Reference_ID=541210;
	/** csv = csv */
	public static final String POSTGRESTRESPONSEFORMAT_Csv = "csv";
	/** json = json */
	public static final String POSTGRESTRESPONSEFORMAT_Json = "json";
	@Override
	public void setPostgrestResponseFormat (final java.lang.String PostgrestResponseFormat)
	{
		set_Value (COLUMNNAME_PostgrestResponseFormat, PostgrestResponseFormat);
	}

	@Override
	public java.lang.String getPostgrestResponseFormat() 
	{
		return get_ValueAsString(COLUMNNAME_PostgrestResponseFormat);
	}

	@Override
	public void setProcedureName (final @Nullable java.lang.String ProcedureName)
	{
		set_Value (COLUMNNAME_ProcedureName, ProcedureName);
	}

	@Override
	public java.lang.String getProcedureName() 
	{
		return get_ValueAsString(COLUMNNAME_ProcedureName);
	}

	@Override
	public void setRefreshAllAfterExecution (final boolean RefreshAllAfterExecution)
	{
		set_Value (COLUMNNAME_RefreshAllAfterExecution, RefreshAllAfterExecution);
	}

	@Override
	public boolean isRefreshAllAfterExecution() 
	{
		return get_ValueAsBoolean(COLUMNNAME_RefreshAllAfterExecution);
	}

	/** 
	 * ShowHelp AD_Reference_ID=50007
	 * Reference name: AD_Process_ShowHelp
	 */
	public static final int SHOWHELP_AD_Reference_ID=50007;
	/** Don't show help = N */
	public static final String SHOWHELP_DonTShowHelp = "N";
	/** Show Help = Y */
	public static final String SHOWHELP_ShowHelp = "Y";
	/** Run silently - Take Defaults = S */
	public static final String SHOWHELP_RunSilently_TakeDefaults = "S";
	@Override
	public void setShowHelp (final @Nullable java.lang.String ShowHelp)
	{
		set_Value (COLUMNNAME_ShowHelp, ShowHelp);
	}

	@Override
	public java.lang.String getShowHelp() 
	{
		return get_ValueAsString(COLUMNNAME_ShowHelp);
	}

	/** 
	 * SpreadsheetFormat AD_Reference_ID=541369
	 * Reference name: SpreadsheetFormat
	 */
	public static final int SPREADSHEETFORMAT_AD_Reference_ID=541369;
	/** Excel = xls */
	public static final String SPREADSHEETFORMAT_Excel = "xls";
	/** CSV = csv */
	public static final String SPREADSHEETFORMAT_CSV = "csv";
	@Override
	public void setSpreadsheetFormat (final @Nullable java.lang.String SpreadsheetFormat)
	{
		set_Value (COLUMNNAME_SpreadsheetFormat, SpreadsheetFormat);
	}

	@Override
	public java.lang.String getSpreadsheetFormat() 
	{
		return get_ValueAsString(COLUMNNAME_SpreadsheetFormat);
	}

	@Override
	public void setSQLStatement (final @Nullable java.lang.String SQLStatement)
	{
		set_Value (COLUMNNAME_SQLStatement, SQLStatement);
	}

	@Override
	public java.lang.String getSQLStatement() 
	{
		return get_ValueAsString(COLUMNNAME_SQLStatement);
	}

	@Override
	public void setTechnicalNote (final @Nullable java.lang.String TechnicalNote)
	{
		set_Value (COLUMNNAME_TechnicalNote, TechnicalNote);
	}

	@Override
	public java.lang.String getTechnicalNote() 
	{
		return get_ValueAsString(COLUMNNAME_TechnicalNote);
	}

	/** 
	 * Type AD_Reference_ID=540087
	 * Reference name: AD_Process Type
	 */
	public static final int TYPE_AD_Reference_ID=540087;
	/** SQL = SQL */
	public static final String TYPE_SQL = "SQL";
	/** Java = Java */
	public static final String TYPE_Java = "Java";
	/** Excel = Excel */
	public static final String TYPE_Excel = "Excel";
	/** JasperReportsSQL = JasperReportsSQL */
	public static final String TYPE_JasperReportsSQL = "JasperReportsSQL";
	/** JasperReportsJSON = JasperReportsJSON */
	public static final String TYPE_JasperReportsJSON = "JasperReportsJSON";
	/** PostgREST = PostgREST */
	public static final String TYPE_PostgREST = "PostgREST";
	@Override
	public void setType (final java.lang.String Type)
	{
		set_Value (COLUMNNAME_Type, Type);
	}

	@Override
	public java.lang.String getType() 
	{
		return get_ValueAsString(COLUMNNAME_Type);
	}

	@Override
	public void setValue (final java.lang.String Value)
	{
		set_Value (COLUMNNAME_Value, Value);
	}

	@Override
	public java.lang.String getValue() 
	{
		return get_ValueAsString(COLUMNNAME_Value);
	}

	@Override
	public void setWorkflowValue (final @Nullable java.lang.String WorkflowValue)
	{
		set_Value (COLUMNNAME_WorkflowValue, WorkflowValue);
	}

	@Override
	public java.lang.String getWorkflowValue() 
	{
		return get_ValueAsString(COLUMNNAME_WorkflowValue);
	}
}