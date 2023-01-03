package org.compiere.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;

/** Generated Interface for AD_Process
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_AD_Process 
{

	String Table_Name = "AD_Process";

//	/** AD_Table_ID=284 */
//	int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);


	/**
	 * Set Berechtigungsstufe.
	 * Access Level required
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAccessLevel (java.lang.String AccessLevel);

	/**
	 * Get Berechtigungsstufe.
	 * Access Level required
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getAccessLevel();

	ModelColumn<I_AD_Process, Object> COLUMN_AccessLevel = new ModelColumn<>(I_AD_Process.class, "AccessLevel", null);
	String COLUMNNAME_AccessLevel = "AccessLevel";

	/**
	 * Get Client.
	 * Client/Tenant for this installation.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_Client_ID();

	String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Special Form.
	 * Special Form
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_Form_ID (int AD_Form_ID);

	/**
	 * Get Special Form.
	 * Special Form
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_Form_ID();

	@Nullable org.compiere.model.I_AD_Form getAD_Form();

	void setAD_Form(@Nullable org.compiere.model.I_AD_Form AD_Form);

	ModelColumn<I_AD_Process, org.compiere.model.I_AD_Form> COLUMN_AD_Form_ID = new ModelColumn<>(I_AD_Process.class, "AD_Form_ID", org.compiere.model.I_AD_Form.class);
	String COLUMNNAME_AD_Form_ID = "AD_Form_ID";

	/**
	 * Set Organisation.
	 * Organisational entity within client
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Organisation.
	 * Organisational entity within client
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_Org_ID();

	String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Print Format.
	 * Data Print Format
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_PrintFormat_ID (int AD_PrintFormat_ID);

	/**
	 * Get Print Format.
	 * Data Print Format
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_PrintFormat_ID();

	@Nullable org.compiere.model.I_AD_PrintFormat getAD_PrintFormat();

	void setAD_PrintFormat(@Nullable org.compiere.model.I_AD_PrintFormat AD_PrintFormat);

	ModelColumn<I_AD_Process, org.compiere.model.I_AD_PrintFormat> COLUMN_AD_PrintFormat_ID = new ModelColumn<>(I_AD_Process.class, "AD_PrintFormat_ID", org.compiere.model.I_AD_PrintFormat.class);
	String COLUMNNAME_AD_PrintFormat_ID = "AD_PrintFormat_ID";

	/**
	 * Set Process.
	 * Process or Report
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_Process_ID (int AD_Process_ID);

	/**
	 * Get Process.
	 * Process or Report
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_Process_ID();

	ModelColumn<I_AD_Process, Object> COLUMN_AD_Process_ID = new ModelColumn<>(I_AD_Process.class, "AD_Process_ID", null);
	String COLUMNNAME_AD_Process_ID = "AD_Process_ID";

	/**
	 * Set Berichts-View.
	 * View used to generate this report
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_ReportView_ID (int AD_ReportView_ID);

	/**
	 * Get Berichts-View.
	 * View used to generate this report
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_ReportView_ID();

	@Nullable org.compiere.model.I_AD_ReportView getAD_ReportView();

	void setAD_ReportView(@Nullable org.compiere.model.I_AD_ReportView AD_ReportView);

	ModelColumn<I_AD_Process, org.compiere.model.I_AD_ReportView> COLUMN_AD_ReportView_ID = new ModelColumn<>(I_AD_Process.class, "AD_ReportView_ID", org.compiere.model.I_AD_ReportView.class);
	String COLUMNNAME_AD_ReportView_ID = "AD_ReportView_ID";

	/**
	 * Set Workflow.
	 * Workflow or combination of tasks
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_Workflow_ID (int AD_Workflow_ID);

	/**
	 * Get Workflow.
	 * Workflow or combination of tasks
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_Workflow_ID();

	String COLUMNNAME_AD_Workflow_ID = "AD_Workflow_ID";

	/**
	 * Set Mehrfachausführung erlaubt.
	 * Allows this process to be executed again. If enabled, the "Back" button will be displayed in process panel.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAllowProcessReRun (boolean AllowProcessReRun);

	/**
	 * Get Mehrfachausführung erlaubt.
	 * Allows this process to be executed again. If enabled, the "Back" button will be displayed in process panel.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isAllowProcessReRun();

	ModelColumn<I_AD_Process, Object> COLUMN_AllowProcessReRun = new ModelColumn<>(I_AD_Process.class, "AllowProcessReRun", null);
	String COLUMNNAME_AllowProcessReRun = "AllowProcessReRun";

	/**
	 * Set Java-Klasse.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setClassname (@Nullable java.lang.String Classname);

	/**
	 * Get Java-Klasse.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getClassname();

	ModelColumn<I_AD_Process, Object> COLUMN_Classname = new ModelColumn<>(I_AD_Process.class, "Classname", null);
	String COLUMNNAME_Classname = "Classname";

	/**
	 * Set Copy From Report and Process.
	 * Copy settings from one report and process to another.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCopyFromProcess (@Nullable java.lang.String CopyFromProcess);

	/**
	 * Get Copy From Report and Process.
	 * Copy settings from one report and process to another.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getCopyFromProcess();

	ModelColumn<I_AD_Process, Object> COLUMN_CopyFromProcess = new ModelColumn<>(I_AD_Process.class, "CopyFromProcess", null);
	String COLUMNNAME_CopyFromProcess = "CopyFromProcess";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_AD_Process, Object> COLUMN_Created = new ModelColumn<>(I_AD_Process.class, "Created", null);
	String COLUMNNAME_Created = "Created";

	/**
	 * Get Created By.
	 * User who created this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getCreatedBy();

	String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set CSV Field Delimiter.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCSVFieldDelimiter (@Nullable java.lang.String CSVFieldDelimiter);

	/**
	 * Get CSV Field Delimiter.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getCSVFieldDelimiter();

	ModelColumn<I_AD_Process, Object> COLUMN_CSVFieldDelimiter = new ModelColumn<>(I_AD_Process.class, "CSVFieldDelimiter", null);
	String COLUMNNAME_CSVFieldDelimiter = "CSVFieldDelimiter";

	/**
	 * Set Description.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDescription (@Nullable java.lang.String Description);

	/**
	 * Get Description.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDescription();

	ModelColumn<I_AD_Process, Object> COLUMN_Description = new ModelColumn<>(I_AD_Process.class, "Description", null);
	String COLUMNNAME_Description = "Description";

	/**
	 * Set Entitäts-Art.
	 * Dictionary Entity Type;
 Determines ownership and synchronization
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setEntityType (java.lang.String EntityType);

	/**
	 * Get Entitäts-Art.
	 * Dictionary Entity Type;
 Determines ownership and synchronization
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getEntityType();

	ModelColumn<I_AD_Process, Object> COLUMN_EntityType = new ModelColumn<>(I_AD_Process.class, "EntityType", null);
	String COLUMNNAME_EntityType = "EntityType";

	/**
	 * Set Help.
	 * Comment or Hint
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setHelp (@Nullable java.lang.String Help);

	/**
	 * Get Help.
	 * Comment or Hint
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getHelp();

	ModelColumn<I_AD_Process, Object> COLUMN_Help = new ModelColumn<>(I_AD_Process.class, "Help", null);
	String COLUMNNAME_Help = "Help";

	/**
	 * Set Active.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsActive (boolean IsActive);

	/**
	 * Get Active.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isActive();

	ModelColumn<I_AD_Process, Object> COLUMN_IsActive = new ModelColumn<>(I_AD_Process.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Apply Security Settings.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsApplySecuritySettings (boolean IsApplySecuritySettings);

	/**
	 * Get Apply Security Settings.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isApplySecuritySettings();

	ModelColumn<I_AD_Process, Object> COLUMN_IsApplySecuritySettings = new ModelColumn<>(I_AD_Process.class, "IsApplySecuritySettings", null);
	String COLUMNNAME_IsApplySecuritySettings = "IsApplySecuritySettings";

	/**
	 * Set Beta-Funktionalität.
	 * This functionality is considered Beta
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsBetaFunctionality (boolean IsBetaFunctionality);

	/**
	 * Get Beta-Funktionalität.
	 * This functionality is considered Beta
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isBetaFunctionality();

	ModelColumn<I_AD_Process, Object> COLUMN_IsBetaFunctionality = new ModelColumn<>(I_AD_Process.class, "IsBetaFunctionality", null);
	String COLUMNNAME_IsBetaFunctionality = "IsBetaFunctionality";

	/**
	 * Set Direct print.
	 * Print without dialog
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIsDirectPrint (boolean IsDirectPrint);

	/**
	 * Get Direct print.
	 * Print without dialog
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isDirectPrint();

	ModelColumn<I_AD_Process, Object> COLUMN_IsDirectPrint = new ModelColumn<>(I_AD_Process.class, "IsDirectPrint", null);
	String COLUMNNAME_IsDirectPrint = "IsDirectPrint";

	/**
	 * Set Notify user after execution.
	 * Only for java processes. Ignored if the process is started via scheduler. The notification contains a link to the execution's process instance record.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsNotifyUserAfterExecution (boolean IsNotifyUserAfterExecution);

	/**
	 * Get Notify user after execution.
	 * Only for java processes. Ignored if the process is started via scheduler. The notification contains a link to the execution's process instance record.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isNotifyUserAfterExecution();

	ModelColumn<I_AD_Process, Object> COLUMN_IsNotifyUserAfterExecution = new ModelColumn<>(I_AD_Process.class, "IsNotifyUserAfterExecution", null);
	String COLUMNNAME_IsNotifyUserAfterExecution = "IsNotifyUserAfterExecution";

	/**
	 * Set Allow one instance only.
	 * At the same time allow to run only one instance of this process
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsOneInstanceOnly (boolean IsOneInstanceOnly);

	/**
	 * Get Allow one instance only.
	 * At the same time allow to run only one instance of this process
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isOneInstanceOnly();

	ModelColumn<I_AD_Process, Object> COLUMN_IsOneInstanceOnly = new ModelColumn<>(I_AD_Process.class, "IsOneInstanceOnly", null);
	String COLUMNNAME_IsOneInstanceOnly = "IsOneInstanceOnly";

	/**
	 * Set Bericht.
	 * Indicates a Report record
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsReport (boolean IsReport);

	/**
	 * Get Bericht.
	 * Indicates a Report record
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isReport();

	ModelColumn<I_AD_Process, Object> COLUMN_IsReport = new ModelColumn<>(I_AD_Process.class, "IsReport", null);
	String COLUMNNAME_IsReport = "IsReport";

	/**
	 * Set Server Process.
	 * (DO NOT USE THIS, IT IS LEGACY) Run this Process on Server only.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsServerProcess (boolean IsServerProcess);

	/**
	 * Get Server Process.
	 * (DO NOT USE THIS, IT IS LEGACY) Run this Process on Server only.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isServerProcess();

	ModelColumn<I_AD_Process, Object> COLUMN_IsServerProcess = new ModelColumn<>(I_AD_Process.class, "IsServerProcess", null);
	String COLUMNNAME_IsServerProcess = "IsServerProcess";

	/**
	 * Set Translate Excel Headers.
	 * Wenn angehakt, dann wird metasfresh die jeweiligen Spaltenüberschriften durch Übersetzungen ersetzen, sofern welche in Meldung (AD_Message) oder Element (AD_Element) vorhanden sind.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsTranslateExcelHeaders (boolean IsTranslateExcelHeaders);

	/**
	 * Get Translate Excel Headers.
	 * Wenn angehakt, dann wird metasfresh die jeweiligen Spaltenüberschriften durch Übersetzungen ersetzen, sofern welche in Meldung (AD_Message) oder Element (AD_Element) vorhanden sind.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isTranslateExcelHeaders();

	ModelColumn<I_AD_Process, Object> COLUMN_IsTranslateExcelHeaders = new ModelColumn<>(I_AD_Process.class, "IsTranslateExcelHeaders", null);
	String COLUMNNAME_IsTranslateExcelHeaders = "IsTranslateExcelHeaders";

	/**
	 * Set Update Export Date.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIsUpdateExportDate (boolean IsUpdateExportDate);

	/**
	 * Get Update Export Date.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isUpdateExportDate();

	ModelColumn<I_AD_Process, Object> COLUMN_IsUpdateExportDate = new ModelColumn<>(I_AD_Process.class, "IsUpdateExportDate", null);
	String COLUMNNAME_IsUpdateExportDate = "IsUpdateExportDate";

	/**
	 * Set IsUseBPartnerLanguage.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsUseBPartnerLanguage (boolean IsUseBPartnerLanguage);

	/**
	 * Get IsUseBPartnerLanguage.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isUseBPartnerLanguage();

	ModelColumn<I_AD_Process, Object> COLUMN_IsUseBPartnerLanguage = new ModelColumn<>(I_AD_Process.class, "IsUseBPartnerLanguage", null);
	String COLUMNNAME_IsUseBPartnerLanguage = "IsUseBPartnerLanguage";

	/**
	 * Set Jasper Report.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setJasperReport (@Nullable java.lang.String JasperReport);

	/**
	 * Get Jasper Report.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getJasperReport();

	ModelColumn<I_AD_Process, Object> COLUMN_JasperReport = new ModelColumn<>(I_AD_Process.class, "JasperReport", null);
	String COLUMNNAME_JasperReport = "JasperReport";

	/**
	 * Set Jasper Report (tabular).
	 * Jasper report to optimized for tabular preview. If specified, this report will be used when the report needs to be exported to XLS, CSV or any other tabular formats.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setJasperReport_Tabular (@Nullable java.lang.String JasperReport_Tabular);

	/**
	 * Get Jasper Report (tabular).
	 * Jasper report to optimized for tabular preview. If specified, this report will be used when the report needs to be exported to XLS, CSV or any other tabular formats.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getJasperReport_Tabular();

	ModelColumn<I_AD_Process, Object> COLUMN_JasperReport_Tabular = new ModelColumn<>(I_AD_Process.class, "JasperReport_Tabular", null);
	String COLUMNNAME_JasperReport_Tabular = "JasperReport_Tabular";

	/**
	 * Set JSON Path.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setJSONPath (@Nullable java.lang.String JSONPath);

	/**
	 * Get JSON Path.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getJSONPath();

	ModelColumn<I_AD_Process, Object> COLUMN_JSONPath = new ModelColumn<>(I_AD_Process.class, "JSONPath", null);
	String COLUMNNAME_JSONPath = "JSONPath";

	/**
	 * Set Wait Timeout.
	 * If only one instance is allowed to run at a time, how many seconds to wait for it. Zero or negative number means forever.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setLockWaitTimeout (int LockWaitTimeout);

	/**
	 * Get Wait Timeout.
	 * If only one instance is allowed to run at a time, how many seconds to wait for it. Zero or negative number means forever.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getLockWaitTimeout();

	ModelColumn<I_AD_Process, Object> COLUMN_LockWaitTimeout = new ModelColumn<>(I_AD_Process.class, "LockWaitTimeout", null);
	String COLUMNNAME_LockWaitTimeout = "LockWaitTimeout";

	/**
	 * Set Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setName (java.lang.String Name);

	/**
	 * Get Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getName();

	ModelColumn<I_AD_Process, Object> COLUMN_Name = new ModelColumn<>(I_AD_Process.class, "Name", null);
	String COLUMNNAME_Name = "Name";

	/**
	 * Set Response format.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPostgrestResponseFormat (java.lang.String PostgrestResponseFormat);

	/**
	 * Get Response format.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getPostgrestResponseFormat();

	ModelColumn<I_AD_Process, Object> COLUMN_PostgrestResponseFormat = new ModelColumn<>(I_AD_Process.class, "PostgrestResponseFormat", null);
	String COLUMNNAME_PostgrestResponseFormat = "PostgrestResponseFormat";

	/**
	 * Set Procedure.
	 * Name of the Database Procedure
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setProcedureName (@Nullable java.lang.String ProcedureName);

	/**
	 * Get Procedure.
	 * Name of the Database Procedure
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getProcedureName();

	ModelColumn<I_AD_Process, Object> COLUMN_ProcedureName = new ModelColumn<>(I_AD_Process.class, "ProcedureName", null);
	String COLUMNNAME_ProcedureName = "ProcedureName";

	/**
	 * Set Refresh All After Execution.
	 * If true, then refresh all entries after a process's execution;
 otherwise, refresh only current selection
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setRefreshAllAfterExecution (boolean RefreshAllAfterExecution);

	/**
	 * Get Refresh All After Execution.
	 * If true, then refresh all entries after a process's execution;
 otherwise, refresh only current selection
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isRefreshAllAfterExecution();

	ModelColumn<I_AD_Process, Object> COLUMN_RefreshAllAfterExecution = new ModelColumn<>(I_AD_Process.class, "RefreshAllAfterExecution", null);
	String COLUMNNAME_RefreshAllAfterExecution = "RefreshAllAfterExecution";

	/**
	 * Set Show Help.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setShowHelp (@Nullable java.lang.String ShowHelp);

	/**
	 * Get Show Help.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getShowHelp();

	ModelColumn<I_AD_Process, Object> COLUMN_ShowHelp = new ModelColumn<>(I_AD_Process.class, "ShowHelp", null);
	String COLUMNNAME_ShowHelp = "ShowHelp";

	/**
	 * Set SpreadsheetFormat.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setSpreadsheetFormat (@Nullable java.lang.String SpreadsheetFormat);

	/**
	 * Get SpreadsheetFormat.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getSpreadsheetFormat();

	ModelColumn<I_AD_Process, Object> COLUMN_SpreadsheetFormat = new ModelColumn<>(I_AD_Process.class, "SpreadsheetFormat", null);
	String COLUMNNAME_SpreadsheetFormat = "SpreadsheetFormat";

	/**
	 * Set SQLStatement.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setSQLStatement (@Nullable java.lang.String SQLStatement);

	/**
	 * Get SQLStatement.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getSQLStatement();

	ModelColumn<I_AD_Process, Object> COLUMN_SQLStatement = new ModelColumn<>(I_AD_Process.class, "SQLStatement", null);
	String COLUMNNAME_SQLStatement = "SQLStatement";

	/**
	 * Set Technical note.
	 * A note that is not indended for the user documentation, but for developers, customizers etc
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setTechnicalNote (@Nullable java.lang.String TechnicalNote);

	/**
	 * Get Technical note.
	 * A note that is not indended for the user documentation, but for developers, customizers etc
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getTechnicalNote();

	ModelColumn<I_AD_Process, Object> COLUMN_TechnicalNote = new ModelColumn<>(I_AD_Process.class, "TechnicalNote", null);
	String COLUMNNAME_TechnicalNote = "TechnicalNote";

	/**
	 * Set Type.
	 * Type of Validation (SQL, Java Script, Java Language)
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setType (java.lang.String Type);

	/**
	 * Get Type.
	 * Type of Validation (SQL, Java Script, Java Language)
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getType();

	ModelColumn<I_AD_Process, Object> COLUMN_Type = new ModelColumn<>(I_AD_Process.class, "Type", null);
	String COLUMNNAME_Type = "Type";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_AD_Process, Object> COLUMN_Updated = new ModelColumn<>(I_AD_Process.class, "Updated", null);
	String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Updated By.
	 * User who updated this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getUpdatedBy();

	String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/**
	 * Set Search Key.
	 * Search key for the record in the format required - must be unique
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setValue (java.lang.String Value);

	/**
	 * Get Search Key.
	 * Search key for the record in the format required - must be unique
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getValue();

	ModelColumn<I_AD_Process, Object> COLUMN_Value = new ModelColumn<>(I_AD_Process.class, "Value", null);
	String COLUMNNAME_Value = "Value";

	/**
	 * Set Workflow Key.
	 * Key of the Workflow to start
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setWorkflowValue (@Nullable java.lang.String WorkflowValue);

	/**
	 * Get Workflow Key.
	 * Key of the Workflow to start
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getWorkflowValue();

	ModelColumn<I_AD_Process, Object> COLUMN_WorkflowValue = new ModelColumn<>(I_AD_Process.class, "WorkflowValue", null);
	String COLUMNNAME_WorkflowValue = "WorkflowValue";
}
