package org.compiere.model;


/** Generated Interface for AD_Process
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_AD_Process 
{

    /** TableName=AD_Process */
    public static final String Table_Name = "AD_Process";

    /** AD_Table_ID=284 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);


	/**
	 * Set Berechtigungsstufe.
	 * Access Level required
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAccessLevel (java.lang.String AccessLevel);

	/**
	 * Get Berechtigungsstufe.
	 * Access Level required
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getAccessLevel();

    /** Column definition for AccessLevel */
    public static final org.adempiere.model.ModelColumn<I_AD_Process, Object> COLUMN_AccessLevel = new org.adempiere.model.ModelColumn<I_AD_Process, Object>(I_AD_Process.class, "AccessLevel", null);
    /** Column name AccessLevel */
    public static final String COLUMNNAME_AccessLevel = "AccessLevel";

	/**
	 * Get Client.
	 * Client/Tenant for this installation.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Client_ID();

    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Special Form.
	 * Special Form
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_Form_ID (int AD_Form_ID);

	/**
	 * Get Special Form.
	 * Special Form
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_Form_ID();

	public org.compiere.model.I_AD_Form getAD_Form();

	public void setAD_Form(org.compiere.model.I_AD_Form AD_Form);

    /** Column definition for AD_Form_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_Process, org.compiere.model.I_AD_Form> COLUMN_AD_Form_ID = new org.adempiere.model.ModelColumn<I_AD_Process, org.compiere.model.I_AD_Form>(I_AD_Process.class, "AD_Form_ID", org.compiere.model.I_AD_Form.class);
    /** Column name AD_Form_ID */
    public static final String COLUMNNAME_AD_Form_ID = "AD_Form_ID";

	/**
	 * Set Organisation.
	 * Organisational entity within client
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Organisation.
	 * Organisational entity within client
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Org_ID();

    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Druck - Format.
	 * Data Print Format
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_PrintFormat_ID (int AD_PrintFormat_ID);

	/**
	 * Get Druck - Format.
	 * Data Print Format
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_PrintFormat_ID();

	public org.compiere.model.I_AD_PrintFormat getAD_PrintFormat();

	public void setAD_PrintFormat(org.compiere.model.I_AD_PrintFormat AD_PrintFormat);

    /** Column definition for AD_PrintFormat_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_Process, org.compiere.model.I_AD_PrintFormat> COLUMN_AD_PrintFormat_ID = new org.adempiere.model.ModelColumn<I_AD_Process, org.compiere.model.I_AD_PrintFormat>(I_AD_Process.class, "AD_PrintFormat_ID", org.compiere.model.I_AD_PrintFormat.class);
    /** Column name AD_PrintFormat_ID */
    public static final String COLUMNNAME_AD_PrintFormat_ID = "AD_PrintFormat_ID";

	/**
	 * Set Prozess.
	 * Process or Report
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_Process_ID (int AD_Process_ID);

	/**
	 * Get Prozess.
	 * Process or Report
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Process_ID();

    /** Column definition for AD_Process_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_Process, Object> COLUMN_AD_Process_ID = new org.adempiere.model.ModelColumn<I_AD_Process, Object>(I_AD_Process.class, "AD_Process_ID", null);
    /** Column name AD_Process_ID */
    public static final String COLUMNNAME_AD_Process_ID = "AD_Process_ID";

	/**
	 * Set Berichts-View.
	 * View used to generate this report
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_ReportView_ID (int AD_ReportView_ID);

	/**
	 * Get Berichts-View.
	 * View used to generate this report
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_ReportView_ID();

	public org.compiere.model.I_AD_ReportView getAD_ReportView();

	public void setAD_ReportView(org.compiere.model.I_AD_ReportView AD_ReportView);

    /** Column definition for AD_ReportView_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_Process, org.compiere.model.I_AD_ReportView> COLUMN_AD_ReportView_ID = new org.adempiere.model.ModelColumn<I_AD_Process, org.compiere.model.I_AD_ReportView>(I_AD_Process.class, "AD_ReportView_ID", org.compiere.model.I_AD_ReportView.class);
    /** Column name AD_ReportView_ID */
    public static final String COLUMNNAME_AD_ReportView_ID = "AD_ReportView_ID";

	/**
	 * Set Workflow.
	 * Workflow or combination of tasks
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_Workflow_ID (int AD_Workflow_ID);

	/**
	 * Get Workflow.
	 * Workflow or combination of tasks
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_Workflow_ID();

	public org.compiere.model.I_AD_Workflow getAD_Workflow();

	public void setAD_Workflow(org.compiere.model.I_AD_Workflow AD_Workflow);

    /** Column definition for AD_Workflow_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_Process, org.compiere.model.I_AD_Workflow> COLUMN_AD_Workflow_ID = new org.adempiere.model.ModelColumn<I_AD_Process, org.compiere.model.I_AD_Workflow>(I_AD_Process.class, "AD_Workflow_ID", org.compiere.model.I_AD_Workflow.class);
    /** Column name AD_Workflow_ID */
    public static final String COLUMNNAME_AD_Workflow_ID = "AD_Workflow_ID";

	/**
	 * Set Mehrfachausführung erlaubt.
	 * Allows this process to be executed again. If enabled, the "Back" button will be displayed in process panel.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAllowProcessReRun (boolean AllowProcessReRun);

	/**
	 * Get Mehrfachausführung erlaubt.
	 * Allows this process to be executed again. If enabled, the "Back" button will be displayed in process panel.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isAllowProcessReRun();

    /** Column definition for AllowProcessReRun */
    public static final org.adempiere.model.ModelColumn<I_AD_Process, Object> COLUMN_AllowProcessReRun = new org.adempiere.model.ModelColumn<I_AD_Process, Object>(I_AD_Process.class, "AllowProcessReRun", null);
    /** Column name AllowProcessReRun */
    public static final String COLUMNNAME_AllowProcessReRun = "AllowProcessReRun";

	/**
	 * Set Java-Klasse.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setClassname (java.lang.String Classname);

	/**
	 * Get Java-Klasse.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getClassname();

    /** Column definition for Classname */
    public static final org.adempiere.model.ModelColumn<I_AD_Process, Object> COLUMN_Classname = new org.adempiere.model.ModelColumn<I_AD_Process, Object>(I_AD_Process.class, "Classname", null);
    /** Column name Classname */
    public static final String COLUMNNAME_Classname = "Classname";

	/**
	 * Set Copy From Report and Process.
	 * Copy settings from one report and process to another.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setCopyFromProcess (java.lang.String CopyFromProcess);

	/**
	 * Get Copy From Report and Process.
	 * Copy settings from one report and process to another.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getCopyFromProcess();

    /** Column definition for CopyFromProcess */
    public static final org.adempiere.model.ModelColumn<I_AD_Process, Object> COLUMN_CopyFromProcess = new org.adempiere.model.ModelColumn<I_AD_Process, Object>(I_AD_Process.class, "CopyFromProcess", null);
    /** Column name CopyFromProcess */
    public static final String COLUMNNAME_CopyFromProcess = "CopyFromProcess";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getCreated();

    /** Column definition for Created */
    public static final org.adempiere.model.ModelColumn<I_AD_Process, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_AD_Process, Object>(I_AD_Process.class, "Created", null);
    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/**
	 * Get Created By.
	 * User who created this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getCreatedBy();

    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Description.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDescription (java.lang.String Description);

	/**
	 * Get Description.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDescription();

    /** Column definition for Description */
    public static final org.adempiere.model.ModelColumn<I_AD_Process, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<I_AD_Process, Object>(I_AD_Process.class, "Description", null);
    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/**
	 * Set Entitäts-Art.
	 * Dictionary Entity Type;
 Determines ownership and synchronization
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setEntityType (java.lang.String EntityType);

	/**
	 * Get Entitäts-Art.
	 * Dictionary Entity Type;
 Determines ownership and synchronization
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getEntityType();

    /** Column definition for EntityType */
    public static final org.adempiere.model.ModelColumn<I_AD_Process, Object> COLUMN_EntityType = new org.adempiere.model.ModelColumn<I_AD_Process, Object>(I_AD_Process.class, "EntityType", null);
    /** Column name EntityType */
    public static final String COLUMNNAME_EntityType = "EntityType";

	/**
	 * Set Help.
	 * Comment or Hint
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setHelp (java.lang.String Help);

	/**
	 * Get Help.
	 * Comment or Hint
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getHelp();

    /** Column definition for Help */
    public static final org.adempiere.model.ModelColumn<I_AD_Process, Object> COLUMN_Help = new org.adempiere.model.ModelColumn<I_AD_Process, Object>(I_AD_Process.class, "Help", null);
    /** Column name Help */
    public static final String COLUMNNAME_Help = "Help";

	/**
	 * Set Active.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsActive (boolean IsActive);

	/**
	 * Get Active.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isActive();

    /** Column definition for IsActive */
    public static final org.adempiere.model.ModelColumn<I_AD_Process, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_AD_Process, Object>(I_AD_Process.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set IsApplySecuritySettings.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsApplySecuritySettings (boolean IsApplySecuritySettings);

	/**
	 * Get IsApplySecuritySettings.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isApplySecuritySettings();

    /** Column definition for IsApplySecuritySettings */
    public static final org.adempiere.model.ModelColumn<I_AD_Process, Object> COLUMN_IsApplySecuritySettings = new org.adempiere.model.ModelColumn<I_AD_Process, Object>(I_AD_Process.class, "IsApplySecuritySettings", null);
    /** Column name IsApplySecuritySettings */
    public static final String COLUMNNAME_IsApplySecuritySettings = "IsApplySecuritySettings";

	/**
	 * Set Beta-Funktionalität.
	 * This functionality is considered Beta
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsBetaFunctionality (boolean IsBetaFunctionality);

	/**
	 * Get Beta-Funktionalität.
	 * This functionality is considered Beta
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isBetaFunctionality();

    /** Column definition for IsBetaFunctionality */
    public static final org.adempiere.model.ModelColumn<I_AD_Process, Object> COLUMN_IsBetaFunctionality = new org.adempiere.model.ModelColumn<I_AD_Process, Object>(I_AD_Process.class, "IsBetaFunctionality", null);
    /** Column name IsBetaFunctionality */
    public static final String COLUMNNAME_IsBetaFunctionality = "IsBetaFunctionality";

	/**
	 * Set Direct print.
	 * Print without dialog
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setIsDirectPrint (boolean IsDirectPrint);

	/**
	 * Get Direct print.
	 * Print without dialog
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isDirectPrint();

    /** Column definition for IsDirectPrint */
    public static final org.adempiere.model.ModelColumn<I_AD_Process, Object> COLUMN_IsDirectPrint = new org.adempiere.model.ModelColumn<I_AD_Process, Object>(I_AD_Process.class, "IsDirectPrint", null);
    /** Column name IsDirectPrint */
    public static final String COLUMNNAME_IsDirectPrint = "IsDirectPrint";

	/**
	 * Set Notify user after execution.
	 * Only for java processes. Ignored if the process is started via scheduler. The notification contains a link to the execution's process instance record.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsNotifyUserAfterExecution (boolean IsNotifyUserAfterExecution);

	/**
	 * Get Notify user after execution.
	 * Only for java processes. Ignored if the process is started via scheduler. The notification contains a link to the execution's process instance record.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isNotifyUserAfterExecution();

    /** Column definition for IsNotifyUserAfterExecution */
    public static final org.adempiere.model.ModelColumn<I_AD_Process, Object> COLUMN_IsNotifyUserAfterExecution = new org.adempiere.model.ModelColumn<I_AD_Process, Object>(I_AD_Process.class, "IsNotifyUserAfterExecution", null);
    /** Column name IsNotifyUserAfterExecution */
    public static final String COLUMNNAME_IsNotifyUserAfterExecution = "IsNotifyUserAfterExecution";

	/**
	 * Set Allow one instance only.
	 * At the same time allow to run only one instance of this process
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsOneInstanceOnly (boolean IsOneInstanceOnly);

	/**
	 * Get Allow one instance only.
	 * At the same time allow to run only one instance of this process
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isOneInstanceOnly();

    /** Column definition for IsOneInstanceOnly */
    public static final org.adempiere.model.ModelColumn<I_AD_Process, Object> COLUMN_IsOneInstanceOnly = new org.adempiere.model.ModelColumn<I_AD_Process, Object>(I_AD_Process.class, "IsOneInstanceOnly", null);
    /** Column name IsOneInstanceOnly */
    public static final String COLUMNNAME_IsOneInstanceOnly = "IsOneInstanceOnly";

	/**
	 * Set Bericht.
	 * Indicates a Report record
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsReport (boolean IsReport);

	/**
	 * Get Bericht.
	 * Indicates a Report record
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isReport();

    /** Column definition for IsReport */
    public static final org.adempiere.model.ModelColumn<I_AD_Process, Object> COLUMN_IsReport = new org.adempiere.model.ModelColumn<I_AD_Process, Object>(I_AD_Process.class, "IsReport", null);
    /** Column name IsReport */
    public static final String COLUMNNAME_IsReport = "IsReport";

	/**
	 * Set Server Process.
	 * (DO NOT USE THIS, IT IS LEGACY) Run this Process on Server only.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsServerProcess (boolean IsServerProcess);

	/**
	 * Get Server Process.
	 * (DO NOT USE THIS, IT IS LEGACY) Run this Process on Server only.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isServerProcess();

    /** Column definition for IsServerProcess */
    public static final org.adempiere.model.ModelColumn<I_AD_Process, Object> COLUMN_IsServerProcess = new org.adempiere.model.ModelColumn<I_AD_Process, Object>(I_AD_Process.class, "IsServerProcess", null);
    /** Column name IsServerProcess */
    public static final String COLUMNNAME_IsServerProcess = "IsServerProcess";

	/**
	 * Set Translate Excel Headers.
	 * Wenn angehakt, dann wird metasfresh die jeweiligen Spaltenüberschriften durch Übersetzungen ersetzen, sofern welche in Meldung (AD_Message) oder Element (AD_Element) vorhanden sind.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsTranslateExcelHeaders (boolean IsTranslateExcelHeaders);

	/**
	 * Get Translate Excel Headers.
	 * Wenn angehakt, dann wird metasfresh die jeweiligen Spaltenüberschriften durch Übersetzungen ersetzen, sofern welche in Meldung (AD_Message) oder Element (AD_Element) vorhanden sind.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isTranslateExcelHeaders();

    /** Column definition for IsTranslateExcelHeaders */
    public static final org.adempiere.model.ModelColumn<I_AD_Process, Object> COLUMN_IsTranslateExcelHeaders = new org.adempiere.model.ModelColumn<I_AD_Process, Object>(I_AD_Process.class, "IsTranslateExcelHeaders", null);
    /** Column name IsTranslateExcelHeaders */
    public static final String COLUMNNAME_IsTranslateExcelHeaders = "IsTranslateExcelHeaders";

	/**
	 * Set IsUseBPartnerLanguage.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsUseBPartnerLanguage (boolean IsUseBPartnerLanguage);

	/**
	 * Get IsUseBPartnerLanguage.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isUseBPartnerLanguage();

    /** Column definition for IsUseBPartnerLanguage */
    public static final org.adempiere.model.ModelColumn<I_AD_Process, Object> COLUMN_IsUseBPartnerLanguage = new org.adempiere.model.ModelColumn<I_AD_Process, Object>(I_AD_Process.class, "IsUseBPartnerLanguage", null);
    /** Column name IsUseBPartnerLanguage */
    public static final String COLUMNNAME_IsUseBPartnerLanguage = "IsUseBPartnerLanguage";

	/**
	 * Set Jasper Report.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setJasperReport (java.lang.String JasperReport);

	/**
	 * Get Jasper Report.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getJasperReport();

    /** Column definition for JasperReport */
    public static final org.adempiere.model.ModelColumn<I_AD_Process, Object> COLUMN_JasperReport = new org.adempiere.model.ModelColumn<I_AD_Process, Object>(I_AD_Process.class, "JasperReport", null);
    /** Column name JasperReport */
    public static final String COLUMNNAME_JasperReport = "JasperReport";

	/**
	 * Set Jasper Report (tabular).
	 * Jasper report to optimized for tabular preview. If specified, this report will be used when the report needs to be exported to XLS, CSV or any other tabular formats.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setJasperReport_Tabular (java.lang.String JasperReport_Tabular);

	/**
	 * Get Jasper Report (tabular).
	 * Jasper report to optimized for tabular preview. If specified, this report will be used when the report needs to be exported to XLS, CSV or any other tabular formats.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getJasperReport_Tabular();

    /** Column definition for JasperReport_Tabular */
    public static final org.adempiere.model.ModelColumn<I_AD_Process, Object> COLUMN_JasperReport_Tabular = new org.adempiere.model.ModelColumn<I_AD_Process, Object>(I_AD_Process.class, "JasperReport_Tabular", null);
    /** Column name JasperReport_Tabular */
    public static final String COLUMNNAME_JasperReport_Tabular = "JasperReport_Tabular";

	/**
	 * Set JSON Path.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setJSONPath (java.lang.String JSONPath);

	/**
	 * Get JSON Path.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getJSONPath();

    /** Column definition for JSONPath */
    public static final org.adempiere.model.ModelColumn<I_AD_Process, Object> COLUMN_JSONPath = new org.adempiere.model.ModelColumn<I_AD_Process, Object>(I_AD_Process.class, "JSONPath", null);
    /** Column name JSONPath */
    public static final String COLUMNNAME_JSONPath = "JSONPath";

	/**
	 * Set Wait Timeout.
	 * If only one instance is allowed to run at a time, how many seconds to wait for it. Zero or negative number means forever.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setLockWaitTimeout (int LockWaitTimeout);

	/**
	 * Get Wait Timeout.
	 * If only one instance is allowed to run at a time, how many seconds to wait for it. Zero or negative number means forever.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getLockWaitTimeout();

    /** Column definition for LockWaitTimeout */
    public static final org.adempiere.model.ModelColumn<I_AD_Process, Object> COLUMN_LockWaitTimeout = new org.adempiere.model.ModelColumn<I_AD_Process, Object>(I_AD_Process.class, "LockWaitTimeout", null);
    /** Column name LockWaitTimeout */
    public static final String COLUMNNAME_LockWaitTimeout = "LockWaitTimeout";

	/**
	 * Set Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setName (java.lang.String Name);

	/**
	 * Get Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getName();

    /** Column definition for Name */
    public static final org.adempiere.model.ModelColumn<I_AD_Process, Object> COLUMN_Name = new org.adempiere.model.ModelColumn<I_AD_Process, Object>(I_AD_Process.class, "Name", null);
    /** Column name Name */
    public static final String COLUMNNAME_Name = "Name";

	/**
	 * Set Procedure.
	 * Name of the Database Procedure
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setProcedureName (java.lang.String ProcedureName);

	/**
	 * Get Procedure.
	 * Name of the Database Procedure
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getProcedureName();

    /** Column definition for ProcedureName */
    public static final org.adempiere.model.ModelColumn<I_AD_Process, Object> COLUMN_ProcedureName = new org.adempiere.model.ModelColumn<I_AD_Process, Object>(I_AD_Process.class, "ProcedureName", null);
    /** Column name ProcedureName */
    public static final String COLUMNNAME_ProcedureName = "ProcedureName";

	/**
	 * Set Refresh All After Execution.
	 * If true, then refresh all entries after a process's execution;
 otherwise, refresh only current selection
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setRefreshAllAfterExecution (boolean RefreshAllAfterExecution);

	/**
	 * Get Refresh All After Execution.
	 * If true, then refresh all entries after a process's execution;
 otherwise, refresh only current selection
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isRefreshAllAfterExecution();

    /** Column definition for RefreshAllAfterExecution */
    public static final org.adempiere.model.ModelColumn<I_AD_Process, Object> COLUMN_RefreshAllAfterExecution = new org.adempiere.model.ModelColumn<I_AD_Process, Object>(I_AD_Process.class, "RefreshAllAfterExecution", null);
    /** Column name RefreshAllAfterExecution */
    public static final String COLUMNNAME_RefreshAllAfterExecution = "RefreshAllAfterExecution";

	/**
	 * Set Show Help.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setShowHelp (java.lang.String ShowHelp);

	/**
	 * Get Show Help.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getShowHelp();

    /** Column definition for ShowHelp */
    public static final org.adempiere.model.ModelColumn<I_AD_Process, Object> COLUMN_ShowHelp = new org.adempiere.model.ModelColumn<I_AD_Process, Object>(I_AD_Process.class, "ShowHelp", null);
    /** Column name ShowHelp */
    public static final String COLUMNNAME_ShowHelp = "ShowHelp";

	/**
	 * Set SQLStatement.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setSQLStatement (java.lang.String SQLStatement);

	/**
	 * Get SQLStatement.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getSQLStatement();

    /** Column definition for SQLStatement */
    public static final org.adempiere.model.ModelColumn<I_AD_Process, Object> COLUMN_SQLStatement = new org.adempiere.model.ModelColumn<I_AD_Process, Object>(I_AD_Process.class, "SQLStatement", null);
    /** Column name SQLStatement */
    public static final String COLUMNNAME_SQLStatement = "SQLStatement";

	/**
	 * Set Technical note.
	 * A note that is not indended for the user documentation, but for developers, customizers etc
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setTechnicalNote (java.lang.String TechnicalNote);

	/**
	 * Get Technical note.
	 * A note that is not indended for the user documentation, but for developers, customizers etc
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getTechnicalNote();

    /** Column definition for TechnicalNote */
    public static final org.adempiere.model.ModelColumn<I_AD_Process, Object> COLUMN_TechnicalNote = new org.adempiere.model.ModelColumn<I_AD_Process, Object>(I_AD_Process.class, "TechnicalNote", null);
    /** Column name TechnicalNote */
    public static final String COLUMNNAME_TechnicalNote = "TechnicalNote";

	/**
	 * Set Type.
	 * Type of Validation (SQL, Java Script, Java Language)
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setType (java.lang.String Type);

	/**
	 * Get Type.
	 * Type of Validation (SQL, Java Script, Java Language)
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getType();

    /** Column definition for Type */
    public static final org.adempiere.model.ModelColumn<I_AD_Process, Object> COLUMN_Type = new org.adempiere.model.ModelColumn<I_AD_Process, Object>(I_AD_Process.class, "Type", null);
    /** Column name Type */
    public static final String COLUMNNAME_Type = "Type";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getUpdated();

    /** Column definition for Updated */
    public static final org.adempiere.model.ModelColumn<I_AD_Process, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_AD_Process, Object>(I_AD_Process.class, "Updated", null);
    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Updated By.
	 * User who updated this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getUpdatedBy();

    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/**
	 * Set Search Key.
	 * Search key for the record in the format required - must be unique
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setValue (java.lang.String Value);

	/**
	 * Get Search Key.
	 * Search key for the record in the format required - must be unique
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getValue();

    /** Column definition for Value */
    public static final org.adempiere.model.ModelColumn<I_AD_Process, Object> COLUMN_Value = new org.adempiere.model.ModelColumn<I_AD_Process, Object>(I_AD_Process.class, "Value", null);
    /** Column name Value */
    public static final String COLUMNNAME_Value = "Value";

	/**
	 * Set Workflow Key.
	 * Key of the Workflow to start
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setWorkflowValue (java.lang.String WorkflowValue);

	/**
	 * Get Workflow Key.
	 * Key of the Workflow to start
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getWorkflowValue();

    /** Column definition for WorkflowValue */
    public static final org.adempiere.model.ModelColumn<I_AD_Process, Object> COLUMN_WorkflowValue = new org.adempiere.model.ModelColumn<I_AD_Process, Object>(I_AD_Process.class, "WorkflowValue", null);
    /** Column name WorkflowValue */
    public static final String COLUMNNAME_WorkflowValue = "WorkflowValue";
}
