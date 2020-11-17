/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for AD_Process
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("javadoc")
public class X_AD_Process extends org.compiere.model.PO implements I_AD_Process, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1097443979L;

    /** Standard Constructor */
    public X_AD_Process (Properties ctx, int AD_Process_ID, String trxName)
    {
      super (ctx, AD_Process_ID, trxName);
    }

    /** Load Constructor */
    public X_AD_Process (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }


	/** Load Meta Data */
	@Override
	protected org.compiere.model.POInfo initPO(Properties ctx)
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
	public void setAccessLevel (java.lang.String AccessLevel)
	{

		set_Value (COLUMNNAME_AccessLevel, AccessLevel);
	}

	@Override
	public java.lang.String getAccessLevel() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_AccessLevel);
	}

	@Override
	public org.compiere.model.I_AD_Form getAD_Form()
	{
		return get_ValueAsPO(COLUMNNAME_AD_Form_ID, org.compiere.model.I_AD_Form.class);
	}

	@Override
	public void setAD_Form(org.compiere.model.I_AD_Form AD_Form)
	{
		set_ValueFromPO(COLUMNNAME_AD_Form_ID, org.compiere.model.I_AD_Form.class, AD_Form);
	}

	@Override
	public void setAD_Form_ID (int AD_Form_ID)
	{
		if (AD_Form_ID < 1) 
			set_Value (COLUMNNAME_AD_Form_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Form_ID, Integer.valueOf(AD_Form_ID));
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
	public void setAD_PrintFormat(org.compiere.model.I_AD_PrintFormat AD_PrintFormat)
	{
		set_ValueFromPO(COLUMNNAME_AD_PrintFormat_ID, org.compiere.model.I_AD_PrintFormat.class, AD_PrintFormat);
	}

	@Override
	public void setAD_PrintFormat_ID (int AD_PrintFormat_ID)
	{
		if (AD_PrintFormat_ID < 1) 
			set_Value (COLUMNNAME_AD_PrintFormat_ID, null);
		else 
			set_Value (COLUMNNAME_AD_PrintFormat_ID, Integer.valueOf(AD_PrintFormat_ID));
	}

	@Override
	public int getAD_PrintFormat_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_PrintFormat_ID);
	}

	@Override
	public void setAD_Process_ID (int AD_Process_ID)
	{
		if (AD_Process_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Process_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Process_ID, Integer.valueOf(AD_Process_ID));
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
	public void setAD_ReportView(org.compiere.model.I_AD_ReportView AD_ReportView)
	{
		set_ValueFromPO(COLUMNNAME_AD_ReportView_ID, org.compiere.model.I_AD_ReportView.class, AD_ReportView);
	}

	@Override
	public void setAD_ReportView_ID (int AD_ReportView_ID)
	{
		if (AD_ReportView_ID < 1) 
			set_Value (COLUMNNAME_AD_ReportView_ID, null);
		else 
			set_Value (COLUMNNAME_AD_ReportView_ID, Integer.valueOf(AD_ReportView_ID));
	}

	@Override
	public int getAD_ReportView_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_ReportView_ID);
	}

	@Override
	public org.compiere.model.I_AD_Workflow getAD_Workflow()
	{
		return get_ValueAsPO(COLUMNNAME_AD_Workflow_ID, org.compiere.model.I_AD_Workflow.class);
	}

	@Override
	public void setAD_Workflow(org.compiere.model.I_AD_Workflow AD_Workflow)
	{
		set_ValueFromPO(COLUMNNAME_AD_Workflow_ID, org.compiere.model.I_AD_Workflow.class, AD_Workflow);
	}

	@Override
	public void setAD_Workflow_ID (int AD_Workflow_ID)
	{
		if (AD_Workflow_ID < 1) 
			set_Value (COLUMNNAME_AD_Workflow_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Workflow_ID, Integer.valueOf(AD_Workflow_ID));
	}

	@Override
	public int getAD_Workflow_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Workflow_ID);
	}

	@Override
	public void setAllowProcessReRun (boolean AllowProcessReRun)
	{
		set_Value (COLUMNNAME_AllowProcessReRun, Boolean.valueOf(AllowProcessReRun));
	}

	@Override
	public boolean isAllowProcessReRun() 
	{
		return get_ValueAsBoolean(COLUMNNAME_AllowProcessReRun);
	}

	@Override
	public void setClassname (java.lang.String Classname)
	{
		set_Value (COLUMNNAME_Classname, Classname);
	}

	@Override
	public java.lang.String getClassname() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Classname);
	}

	@Override
	public void setCopyFromProcess (java.lang.String CopyFromProcess)
	{
		set_Value (COLUMNNAME_CopyFromProcess, CopyFromProcess);
	}

	@Override
	public java.lang.String getCopyFromProcess() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_CopyFromProcess);
	}

	@Override
	public void setDescription (java.lang.String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	@Override
	public java.lang.String getDescription() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Description);
	}

	/** 
	 * EntityType AD_Reference_ID=389
	 * Reference name: _EntityTypeNew
	 */
	public static final int ENTITYTYPE_AD_Reference_ID=389;
	@Override
	public void setEntityType (java.lang.String EntityType)
	{

		set_Value (COLUMNNAME_EntityType, EntityType);
	}

	@Override
	public java.lang.String getEntityType() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_EntityType);
	}

	@Override
	public void setHelp (java.lang.String Help)
	{
		set_Value (COLUMNNAME_Help, Help);
	}

	@Override
	public java.lang.String getHelp() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Help);
	}

	@Override
	public void setIsApplySecuritySettings (boolean IsApplySecuritySettings)
	{
		set_Value (COLUMNNAME_IsApplySecuritySettings, Boolean.valueOf(IsApplySecuritySettings));
	}

	@Override
	public boolean isApplySecuritySettings() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsApplySecuritySettings);
	}

	@Override
	public void setIsBetaFunctionality (boolean IsBetaFunctionality)
	{
		set_Value (COLUMNNAME_IsBetaFunctionality, Boolean.valueOf(IsBetaFunctionality));
	}

	@Override
	public boolean isBetaFunctionality() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsBetaFunctionality);
	}

	@Override
	public void setIsDirectPrint (boolean IsDirectPrint)
	{
		set_Value (COLUMNNAME_IsDirectPrint, Boolean.valueOf(IsDirectPrint));
	}

	@Override
	public boolean isDirectPrint() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsDirectPrint);
	}

	@Override
	public void setIsNotifyUserAfterExecution (boolean IsNotifyUserAfterExecution)
	{
		set_Value (COLUMNNAME_IsNotifyUserAfterExecution, Boolean.valueOf(IsNotifyUserAfterExecution));
	}

	@Override
	public boolean isNotifyUserAfterExecution() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsNotifyUserAfterExecution);
	}

	@Override
	public void setIsOneInstanceOnly (boolean IsOneInstanceOnly)
	{
		set_Value (COLUMNNAME_IsOneInstanceOnly, Boolean.valueOf(IsOneInstanceOnly));
	}

	@Override
	public boolean isOneInstanceOnly() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsOneInstanceOnly);
	}

	@Override
	public void setIsReport (boolean IsReport)
	{
		set_Value (COLUMNNAME_IsReport, Boolean.valueOf(IsReport));
	}

	@Override
	public boolean isReport() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsReport);
	}

	@Override
	public void setIsServerProcess (boolean IsServerProcess)
	{
		set_Value (COLUMNNAME_IsServerProcess, Boolean.valueOf(IsServerProcess));
	}

	@Override
	public boolean isServerProcess() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsServerProcess);
	}

	@Override
	public void setIsTranslateExcelHeaders (boolean IsTranslateExcelHeaders)
	{
		set_Value (COLUMNNAME_IsTranslateExcelHeaders, Boolean.valueOf(IsTranslateExcelHeaders));
	}

	@Override
	public boolean isTranslateExcelHeaders() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsTranslateExcelHeaders);
	}

	@Override
	public void setIsUseBPartnerLanguage (boolean IsUseBPartnerLanguage)
	{
		set_Value (COLUMNNAME_IsUseBPartnerLanguage, Boolean.valueOf(IsUseBPartnerLanguage));
	}

	@Override
	public boolean isUseBPartnerLanguage() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsUseBPartnerLanguage);
	}

	@Override
	public void setJasperReport (java.lang.String JasperReport)
	{
		set_Value (COLUMNNAME_JasperReport, JasperReport);
	}

	@Override
	public java.lang.String getJasperReport() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_JasperReport);
	}

	@Override
	public void setJasperReport_Tabular (java.lang.String JasperReport_Tabular)
	{
		set_Value (COLUMNNAME_JasperReport_Tabular, JasperReport_Tabular);
	}

	@Override
	public java.lang.String getJasperReport_Tabular() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_JasperReport_Tabular);
	}

	@Override
	public void setJSONPath (java.lang.String JSONPath)
	{
		set_Value (COLUMNNAME_JSONPath, JSONPath);
	}

	@Override
	public java.lang.String getJSONPath() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_JSONPath);
	}

	@Override
	public void setLockWaitTimeout (int LockWaitTimeout)
	{
		set_Value (COLUMNNAME_LockWaitTimeout, Integer.valueOf(LockWaitTimeout));
	}

	@Override
	public int getLockWaitTimeout() 
	{
		return get_ValueAsInt(COLUMNNAME_LockWaitTimeout);
	}

	@Override
	public void setName (java.lang.String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	@Override
	public java.lang.String getName() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Name);
	}

	@Override
	public void setProcedureName (java.lang.String ProcedureName)
	{
		set_Value (COLUMNNAME_ProcedureName, ProcedureName);
	}

	@Override
	public java.lang.String getProcedureName() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ProcedureName);
	}

	@Override
	public void setRefreshAllAfterExecution (boolean RefreshAllAfterExecution)
	{
		set_Value (COLUMNNAME_RefreshAllAfterExecution, Boolean.valueOf(RefreshAllAfterExecution));
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
	public void setShowHelp (java.lang.String ShowHelp)
	{

		set_Value (COLUMNNAME_ShowHelp, ShowHelp);
	}

	@Override
	public java.lang.String getShowHelp() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ShowHelp);
	}

	@Override
	public void setSQLStatement (java.lang.String SQLStatement)
	{
		set_Value (COLUMNNAME_SQLStatement, SQLStatement);
	}

	@Override
	public java.lang.String getSQLStatement() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_SQLStatement);
	}

	@Override
	public void setTechnicalNote (java.lang.String TechnicalNote)
	{
		set_Value (COLUMNNAME_TechnicalNote, TechnicalNote);
	}

	@Override
	public java.lang.String getTechnicalNote() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_TechnicalNote);
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
	public void setType (java.lang.String Type)
	{

		set_Value (COLUMNNAME_Type, Type);
	}

	@Override
	public java.lang.String getType() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Type);
	}

	@Override
	public void setValue (java.lang.String Value)
	{
		set_Value (COLUMNNAME_Value, Value);
	}

	@Override
	public java.lang.String getValue() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Value);
	}

	@Override
	public void setWorkflowValue (java.lang.String WorkflowValue)
	{
		set_Value (COLUMNNAME_WorkflowValue, WorkflowValue);
	}

	@Override
	public java.lang.String getWorkflowValue() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_WorkflowValue);
	}
}