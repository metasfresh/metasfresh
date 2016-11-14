package org.compiere.model;


/** Generated Interface for AD_Process
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_AD_Process 
{

    /** TableName=AD_Process */
    public static final String Table_Name = "AD_Process";

    /** AD_Table_ID=284 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 4 - System
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(4);

    /** Load Meta Data */

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
	 * Get Mandant.
	 * Client/Tenant for this installation.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Client_ID();

	public org.compiere.model.I_AD_Client getAD_Client();

    /** Column definition for AD_Client_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_Process, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_AD_Process, org.compiere.model.I_AD_Client>(I_AD_Process.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
	 * Set Sektion.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Sektion.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Org_ID();

	public org.compiere.model.I_AD_Org getAD_Org();

	public void setAD_Org(org.compiere.model.I_AD_Org AD_Org);

    /** Column definition for AD_Org_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_Process, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_AD_Process, org.compiere.model.I_AD_Org>(I_AD_Process.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
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
	 * Get Erstellt.
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
	 * Get Erstellt durch.
	 * User who created this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getCreatedBy();

    /** Column definition for CreatedBy */
    public static final org.adempiere.model.ModelColumn<I_AD_Process, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_AD_Process, org.compiere.model.I_AD_User>(I_AD_Process.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Beschreibung.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDescription (java.lang.String Description);

	/**
	 * Get Beschreibung.
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
	 * Set Kommentar/Hilfe.
	 * Comment or Hint
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setHelp (java.lang.String Help);

	/**
	 * Get Kommentar/Hilfe.
	 * Comment or Hint
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getHelp();

    /** Column definition for Help */
    public static final org.adempiere.model.ModelColumn<I_AD_Process, Object> COLUMN_Help = new org.adempiere.model.ModelColumn<I_AD_Process, Object>(I_AD_Process.class, "Help", null);
    /** Column name Help */
    public static final String COLUMNNAME_Help = "Help";

	/**
	 * Set Aktiv.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsActive (boolean IsActive);

	/**
	 * Get Aktiv.
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
	 * Run this Process on Server only
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsServerProcess (boolean IsServerProcess);

	/**
	 * Get Server Process.
	 * Run this Process on Server only
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
	 * Set Name.
	 * Alphanumeric identifier of the entity
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setName (java.lang.String Name);

	/**
	 * Get Name.
	 * Alphanumeric identifier of the entity
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
	 * Get Aktualisiert.
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
	 * Get Aktualisiert durch.
	 * User who updated this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getUpdatedBy();

    /** Column definition for UpdatedBy */
    public static final org.adempiere.model.ModelColumn<I_AD_Process, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_AD_Process, org.compiere.model.I_AD_User>(I_AD_Process.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/**
	 * Set Suchschlüssel.
	 * Search key for the record in the format required - must be unique
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setValue (java.lang.String Value);

	/**
	 * Get Suchschlüssel.
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
