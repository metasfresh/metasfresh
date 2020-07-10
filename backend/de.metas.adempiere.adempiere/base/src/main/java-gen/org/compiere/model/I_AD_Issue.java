package org.compiere.model;


/** Generated Interface for AD_Issue
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_AD_Issue 
{

    /** TableName=AD_Issue */
    public static final String Table_Name = "AD_Issue";

    /** AD_Table_ID=828 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);


	/**
	 * Set Asset.
	 * Asset used internally or by customers
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setA_Asset_ID (int A_Asset_ID);

	/**
	 * Get Asset.
	 * Asset used internally or by customers
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getA_Asset_ID();

	public org.compiere.model.I_A_Asset getA_Asset();

	public void setA_Asset(org.compiere.model.I_A_Asset A_Asset);

    /** Column definition for A_Asset_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_Issue, org.compiere.model.I_A_Asset> COLUMN_A_Asset_ID = new org.adempiere.model.ModelColumn<I_AD_Issue, org.compiere.model.I_A_Asset>(I_AD_Issue.class, "A_Asset_ID", org.compiere.model.I_A_Asset.class);
    /** Column name A_Asset_ID */
    public static final String COLUMNNAME_A_Asset_ID = "A_Asset_ID";

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
    public static final org.adempiere.model.ModelColumn<I_AD_Issue, org.compiere.model.I_AD_Form> COLUMN_AD_Form_ID = new org.adempiere.model.ModelColumn<I_AD_Issue, org.compiere.model.I_AD_Form>(I_AD_Issue.class, "AD_Form_ID", org.compiere.model.I_AD_Form.class);
    /** Column name AD_Form_ID */
    public static final String COLUMNNAME_AD_Form_ID = "AD_Form_ID";

	/**
	 * Set System Issue.
	 * Automatically created or manually entered System Issue
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_Issue_ID (int AD_Issue_ID);

	/**
	 * Get System Issue.
	 * Automatically created or manually entered System Issue
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Issue_ID();

    /** Column definition for AD_Issue_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_Issue, Object> COLUMN_AD_Issue_ID = new org.adempiere.model.ModelColumn<I_AD_Issue, Object>(I_AD_Issue.class, "AD_Issue_ID", null);
    /** Column name AD_Issue_ID */
    public static final String COLUMNNAME_AD_Issue_ID = "AD_Issue_ID";

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
	 * Set Process Instance.
	 * Instance of a Process
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_PInstance_ID (int AD_PInstance_ID);

	/**
	 * Get Process Instance.
	 * Instance of a Process
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_PInstance_ID();

	public org.compiere.model.I_AD_PInstance getAD_PInstance();

	public void setAD_PInstance(org.compiere.model.I_AD_PInstance AD_PInstance);

    /** Column definition for AD_PInstance_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_Issue, org.compiere.model.I_AD_PInstance> COLUMN_AD_PInstance_ID = new org.adempiere.model.ModelColumn<I_AD_Issue, org.compiere.model.I_AD_PInstance>(I_AD_Issue.class, "AD_PInstance_ID", org.compiere.model.I_AD_PInstance.class);
    /** Column name AD_PInstance_ID */
    public static final String COLUMNNAME_AD_PInstance_ID = "AD_PInstance_ID";

	/**
	 * Set Prozess.
	 * Process or Report
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_Process_ID (int AD_Process_ID);

	/**
	 * Get Prozess.
	 * Process or Report
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_Process_ID();

	public org.compiere.model.I_AD_Process getAD_Process();

	public void setAD_Process(org.compiere.model.I_AD_Process AD_Process);

    /** Column definition for AD_Process_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_Issue, org.compiere.model.I_AD_Process> COLUMN_AD_Process_ID = new org.adempiere.model.ModelColumn<I_AD_Issue, org.compiere.model.I_AD_Process>(I_AD_Issue.class, "AD_Process_ID", org.compiere.model.I_AD_Process.class);
    /** Column name AD_Process_ID */
    public static final String COLUMNNAME_AD_Process_ID = "AD_Process_ID";

	/**
	 * Set Table.
	 * Database Table information
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_Table_ID (int AD_Table_ID);

	/**
	 * Get Table.
	 * Database Table information
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_Table_ID();

    /** Column name AD_Table_ID */
    public static final String COLUMNNAME_AD_Table_ID = "AD_Table_ID";

	/**
	 * Set Window.
	 * Data entry or display window
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_Window_ID (int AD_Window_ID);

	/**
	 * Get Window.
	 * Data entry or display window
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_Window_ID();

	public org.compiere.model.I_AD_Window getAD_Window();

	public void setAD_Window(org.compiere.model.I_AD_Window AD_Window);

    /** Column definition for AD_Window_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_Issue, org.compiere.model.I_AD_Window> COLUMN_AD_Window_ID = new org.adempiere.model.ModelColumn<I_AD_Issue, org.compiere.model.I_AD_Window>(I_AD_Issue.class, "AD_Window_ID", org.compiere.model.I_AD_Window.class);
    /** Column name AD_Window_ID */
    public static final String COLUMNNAME_AD_Window_ID = "AD_Window_ID";

	/**
	 * Set Comments.
	 * Comments or additional information
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setComments (java.lang.String Comments);

	/**
	 * Get Comments.
	 * Comments or additional information
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getComments();

    /** Column definition for Comments */
    public static final org.adempiere.model.ModelColumn<I_AD_Issue, Object> COLUMN_Comments = new org.adempiere.model.ModelColumn<I_AD_Issue, Object>(I_AD_Issue.class, "Comments", null);
    /** Column name Comments */
    public static final String COLUMNNAME_Comments = "Comments";

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
    public static final org.adempiere.model.ModelColumn<I_AD_Issue, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_AD_Issue, Object>(I_AD_Issue.class, "Created", null);
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
	 * Set Datenbank.
	 * Database Information
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDatabaseInfo (java.lang.String DatabaseInfo);

	/**
	 * Get Datenbank.
	 * Database Information
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDatabaseInfo();

    /** Column definition for DatabaseInfo */
    public static final org.adempiere.model.ModelColumn<I_AD_Issue, Object> COLUMN_DatabaseInfo = new org.adempiere.model.ModelColumn<I_AD_Issue, Object>(I_AD_Issue.class, "DatabaseInfo", null);
    /** Column name DatabaseInfo */
    public static final String COLUMNNAME_DatabaseInfo = "DatabaseInfo";

	/**
	 * Set DB Address.
	 * JDBC URL of the database server
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDBAddress (java.lang.String DBAddress);

	/**
	 * Get DB Address.
	 * JDBC URL of the database server
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDBAddress();

    /** Column definition for DBAddress */
    public static final org.adempiere.model.ModelColumn<I_AD_Issue, Object> COLUMN_DBAddress = new org.adempiere.model.ModelColumn<I_AD_Issue, Object>(I_AD_Issue.class, "DBAddress", null);
    /** Column name DBAddress */
    public static final String COLUMNNAME_DBAddress = "DBAddress";

	/**
	 * Set Error Trace.
	 * System Error Trace
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setErrorTrace (java.lang.String ErrorTrace);

	/**
	 * Get Error Trace.
	 * System Error Trace
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getErrorTrace();

    /** Column definition for ErrorTrace */
    public static final org.adempiere.model.ModelColumn<I_AD_Issue, Object> COLUMN_ErrorTrace = new org.adempiere.model.ModelColumn<I_AD_Issue, Object>(I_AD_Issue.class, "ErrorTrace", null);
    /** Column name ErrorTrace */
    public static final String COLUMNNAME_ErrorTrace = "ErrorTrace";

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
    public static final org.adempiere.model.ModelColumn<I_AD_Issue, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_AD_Issue, Object>(I_AD_Issue.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Reproducible.
	 * Problem can re reproduced in Gardenworld
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setIsReproducible (java.lang.String IsReproducible);

	/**
	 * Get Reproducible.
	 * Problem can re reproduced in Gardenworld
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getIsReproducible();

    /** Column definition for IsReproducible */
    public static final org.adempiere.model.ModelColumn<I_AD_Issue, Object> COLUMN_IsReproducible = new org.adempiere.model.ModelColumn<I_AD_Issue, Object>(I_AD_Issue.class, "IsReproducible", null);
    /** Column name IsReproducible */
    public static final String COLUMNNAME_IsReproducible = "IsReproducible";

	/**
	 * Set Category.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setIssueCategory (java.lang.String IssueCategory);

	/**
	 * Get Category.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getIssueCategory();

    /** Column definition for IssueCategory */
    public static final org.adempiere.model.ModelColumn<I_AD_Issue, Object> COLUMN_IssueCategory = new org.adempiere.model.ModelColumn<I_AD_Issue, Object>(I_AD_Issue.class, "IssueCategory", null);
    /** Column name IssueCategory */
    public static final String COLUMNNAME_IssueCategory = "IssueCategory";

	/**
	 * Set Source.
	 * Issue Source
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setIssueSource (java.lang.String IssueSource);

	/**
	 * Get Source.
	 * Issue Source
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getIssueSource();

    /** Column definition for IssueSource */
    public static final org.adempiere.model.ModelColumn<I_AD_Issue, Object> COLUMN_IssueSource = new org.adempiere.model.ModelColumn<I_AD_Issue, Object>(I_AD_Issue.class, "IssueSource", null);
    /** Column name IssueSource */
    public static final String COLUMNNAME_IssueSource = "IssueSource";

	/**
	 * Set Issue Summary.
	 * Issue Summary
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIssueSummary (java.lang.String IssueSummary);

	/**
	 * Get Issue Summary.
	 * Issue Summary
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getIssueSummary();

    /** Column definition for IssueSummary */
    public static final org.adempiere.model.ModelColumn<I_AD_Issue, Object> COLUMN_IssueSummary = new org.adempiere.model.ModelColumn<I_AD_Issue, Object>(I_AD_Issue.class, "IssueSummary", null);
    /** Column name IssueSummary */
    public static final String COLUMNNAME_IssueSummary = "IssueSummary";

	/**
	 * Set Vanilla System.
	 * The system was NOT compiled from Source - i.e. standard distribution
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setIsVanillaSystem (java.lang.String IsVanillaSystem);

	/**
	 * Get Vanilla System.
	 * The system was NOT compiled from Source - i.e. standard distribution
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getIsVanillaSystem();

    /** Column definition for IsVanillaSystem */
    public static final org.adempiere.model.ModelColumn<I_AD_Issue, Object> COLUMN_IsVanillaSystem = new org.adempiere.model.ModelColumn<I_AD_Issue, Object>(I_AD_Issue.class, "IsVanillaSystem", null);
    /** Column name IsVanillaSystem */
    public static final String COLUMNNAME_IsVanillaSystem = "IsVanillaSystem";

	/**
	 * Set Java Info.
	 * Java Version Info
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setJavaInfo (java.lang.String JavaInfo);

	/**
	 * Get Java Info.
	 * Java Version Info
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getJavaInfo();

    /** Column definition for JavaInfo */
    public static final org.adempiere.model.ModelColumn<I_AD_Issue, Object> COLUMN_JavaInfo = new org.adempiere.model.ModelColumn<I_AD_Issue, Object>(I_AD_Issue.class, "JavaInfo", null);
    /** Column name JavaInfo */
    public static final String COLUMNNAME_JavaInfo = "JavaInfo";

	/**
	 * Set Line.
	 * Line No
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setLineNo (int LineNo);

	/**
	 * Get Line.
	 * Line No
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getLineNo();

    /** Column definition for LineNo */
    public static final org.adempiere.model.ModelColumn<I_AD_Issue, Object> COLUMN_LineNo = new org.adempiere.model.ModelColumn<I_AD_Issue, Object>(I_AD_Issue.class, "LineNo", null);
    /** Column name LineNo */
    public static final String COLUMNNAME_LineNo = "LineNo";

	/**
	 * Set Local Host.
	 * Local Host Info
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setLocal_Host (java.lang.String Local_Host);

	/**
	 * Get Local Host.
	 * Local Host Info
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getLocal_Host();

    /** Column definition for Local_Host */
    public static final org.adempiere.model.ModelColumn<I_AD_Issue, Object> COLUMN_Local_Host = new org.adempiere.model.ModelColumn<I_AD_Issue, Object>(I_AD_Issue.class, "Local_Host", null);
    /** Column name Local_Host */
    public static final String COLUMNNAME_Local_Host = "Local_Host";

	/**
	 * Set Logger.
	 * Logger Name
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setLoggerName (java.lang.String LoggerName);

	/**
	 * Get Logger.
	 * Logger Name
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getLoggerName();

    /** Column definition for LoggerName */
    public static final org.adempiere.model.ModelColumn<I_AD_Issue, Object> COLUMN_LoggerName = new org.adempiere.model.ModelColumn<I_AD_Issue, Object>(I_AD_Issue.class, "LoggerName", null);
    /** Column name LoggerName */
    public static final String COLUMNNAME_LoggerName = "LoggerName";

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
    public static final org.adempiere.model.ModelColumn<I_AD_Issue, Object> COLUMN_Name = new org.adempiere.model.ModelColumn<I_AD_Issue, Object>(I_AD_Issue.class, "Name", null);
    /** Column name Name */
    public static final String COLUMNNAME_Name = "Name";

	/**
	 * Set Operating System.
	 * Operating System Info
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setOperatingSystemInfo (java.lang.String OperatingSystemInfo);

	/**
	 * Get Operating System.
	 * Operating System Info
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getOperatingSystemInfo();

    /** Column definition for OperatingSystemInfo */
    public static final org.adempiere.model.ModelColumn<I_AD_Issue, Object> COLUMN_OperatingSystemInfo = new org.adempiere.model.ModelColumn<I_AD_Issue, Object>(I_AD_Issue.class, "OperatingSystemInfo", null);
    /** Column name OperatingSystemInfo */
    public static final String COLUMNNAME_OperatingSystemInfo = "OperatingSystemInfo";

	/**
	 * Set Processed.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setProcessed (boolean Processed);

	/**
	 * Get Processed.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isProcessed();

    /** Column definition for Processed */
    public static final org.adempiere.model.ModelColumn<I_AD_Issue, Object> COLUMN_Processed = new org.adempiere.model.ModelColumn<I_AD_Issue, Object>(I_AD_Issue.class, "Processed", null);
    /** Column name Processed */
    public static final String COLUMNNAME_Processed = "Processed";

	/**
	 * Set Process Now.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setProcessing (boolean Processing);

	/**
	 * Get Process Now.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isProcessing();

    /** Column definition for Processing */
    public static final org.adempiere.model.ModelColumn<I_AD_Issue, Object> COLUMN_Processing = new org.adempiere.model.ModelColumn<I_AD_Issue, Object>(I_AD_Issue.class, "Processing", null);
    /** Column name Processing */
    public static final String COLUMNNAME_Processing = "Processing";

	/**
	 * Set Profile.
	 * Information to help profiling the system for solving support issues
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setProfileInfo (java.lang.String ProfileInfo);

	/**
	 * Get Profile.
	 * Information to help profiling the system for solving support issues
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getProfileInfo();

    /** Column definition for ProfileInfo */
    public static final org.adempiere.model.ModelColumn<I_AD_Issue, Object> COLUMN_ProfileInfo = new org.adempiere.model.ModelColumn<I_AD_Issue, Object>(I_AD_Issue.class, "ProfileInfo", null);
    /** Column name ProfileInfo */
    public static final String COLUMNNAME_ProfileInfo = "ProfileInfo";

	/**
	 * Set Bekanntes Problem.
	 * Known Issue
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setR_IssueKnown_ID (int R_IssueKnown_ID);

	/**
	 * Get Bekanntes Problem.
	 * Known Issue
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getR_IssueKnown_ID();

	public org.compiere.model.I_R_IssueKnown getR_IssueKnown();

	public void setR_IssueKnown(org.compiere.model.I_R_IssueKnown R_IssueKnown);

    /** Column definition for R_IssueKnown_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_Issue, org.compiere.model.I_R_IssueKnown> COLUMN_R_IssueKnown_ID = new org.adempiere.model.ModelColumn<I_AD_Issue, org.compiere.model.I_R_IssueKnown>(I_AD_Issue.class, "R_IssueKnown_ID", org.compiere.model.I_R_IssueKnown.class);
    /** Column name R_IssueKnown_ID */
    public static final String COLUMNNAME_R_IssueKnown_ID = "R_IssueKnown_ID";

	/**
	 * Set Projekt-Problem.
	 * Implementation Projects
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setR_IssueProject_ID (int R_IssueProject_ID);

	/**
	 * Get Projekt-Problem.
	 * Implementation Projects
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getR_IssueProject_ID();

	public org.compiere.model.I_R_IssueProject getR_IssueProject();

	public void setR_IssueProject(org.compiere.model.I_R_IssueProject R_IssueProject);

    /** Column definition for R_IssueProject_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_Issue, org.compiere.model.I_R_IssueProject> COLUMN_R_IssueProject_ID = new org.adempiere.model.ModelColumn<I_AD_Issue, org.compiere.model.I_R_IssueProject>(I_AD_Issue.class, "R_IssueProject_ID", org.compiere.model.I_R_IssueProject.class);
    /** Column name R_IssueProject_ID */
    public static final String COLUMNNAME_R_IssueProject_ID = "R_IssueProject_ID";

	/**
	 * Set System-Problem.
	 * System creating the issue
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setR_IssueSystem_ID (int R_IssueSystem_ID);

	/**
	 * Get System-Problem.
	 * System creating the issue
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getR_IssueSystem_ID();

	public org.compiere.model.I_R_IssueSystem getR_IssueSystem();

	public void setR_IssueSystem(org.compiere.model.I_R_IssueSystem R_IssueSystem);

    /** Column definition for R_IssueSystem_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_Issue, org.compiere.model.I_R_IssueSystem> COLUMN_R_IssueSystem_ID = new org.adempiere.model.ModelColumn<I_AD_Issue, org.compiere.model.I_R_IssueSystem>(I_AD_Issue.class, "R_IssueSystem_ID", org.compiere.model.I_R_IssueSystem.class);
    /** Column name R_IssueSystem_ID */
    public static final String COLUMNNAME_R_IssueSystem_ID = "R_IssueSystem_ID";

	/**
	 * Set IssueUser.
	 * User who reported issues
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setR_IssueUser_ID (int R_IssueUser_ID);

	/**
	 * Get IssueUser.
	 * User who reported issues
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getR_IssueUser_ID();

	public org.compiere.model.I_R_IssueUser getR_IssueUser();

	public void setR_IssueUser(org.compiere.model.I_R_IssueUser R_IssueUser);

    /** Column definition for R_IssueUser_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_Issue, org.compiere.model.I_R_IssueUser> COLUMN_R_IssueUser_ID = new org.adempiere.model.ModelColumn<I_AD_Issue, org.compiere.model.I_R_IssueUser>(I_AD_Issue.class, "R_IssueUser_ID", org.compiere.model.I_R_IssueUser.class);
    /** Column name R_IssueUser_ID */
    public static final String COLUMNNAME_R_IssueUser_ID = "R_IssueUser_ID";

	/**
	 * Set Anfrage.
	 * Request from a Business Partner or Prospect
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setR_Request_ID (int R_Request_ID);

	/**
	 * Get Anfrage.
	 * Request from a Business Partner or Prospect
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getR_Request_ID();

	public org.compiere.model.I_R_Request getR_Request();

	public void setR_Request(org.compiere.model.I_R_Request R_Request);

    /** Column definition for R_Request_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_Issue, org.compiere.model.I_R_Request> COLUMN_R_Request_ID = new org.adempiere.model.ModelColumn<I_AD_Issue, org.compiere.model.I_R_Request>(I_AD_Issue.class, "R_Request_ID", org.compiere.model.I_R_Request.class);
    /** Column name R_Request_ID */
    public static final String COLUMNNAME_R_Request_ID = "R_Request_ID";

	/**
	 * Set Record ID.
	 * Direct internal record ID
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setRecord_ID (int Record_ID);

	/**
	 * Get Record ID.
	 * Direct internal record ID
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getRecord_ID();

    /** Column definition for Record_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_Issue, Object> COLUMN_Record_ID = new org.adempiere.model.ModelColumn<I_AD_Issue, Object>(I_AD_Issue.class, "Record_ID", null);
    /** Column name Record_ID */
    public static final String COLUMNNAME_Record_ID = "Record_ID";

	/**
	 * Set Release No.
	 * Internal Release Number
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setReleaseNo (java.lang.String ReleaseNo);

	/**
	 * Get Release No.
	 * Internal Release Number
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getReleaseNo();

    /** Column definition for ReleaseNo */
    public static final org.adempiere.model.ModelColumn<I_AD_Issue, Object> COLUMN_ReleaseNo = new org.adempiere.model.ModelColumn<I_AD_Issue, Object>(I_AD_Issue.class, "ReleaseNo", null);
    /** Column name ReleaseNo */
    public static final String COLUMNNAME_ReleaseNo = "ReleaseNo";

	/**
	 * Set Release Tag.
	 * Release Tag
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setReleaseTag (java.lang.String ReleaseTag);

	/**
	 * Get Release Tag.
	 * Release Tag
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getReleaseTag();

    /** Column definition for ReleaseTag */
    public static final org.adempiere.model.ModelColumn<I_AD_Issue, Object> COLUMN_ReleaseTag = new org.adempiere.model.ModelColumn<I_AD_Issue, Object>(I_AD_Issue.class, "ReleaseTag", null);
    /** Column name ReleaseTag */
    public static final String COLUMNNAME_ReleaseTag = "ReleaseTag";

	/**
	 * Set Remote Addr.
	 * Remote Address
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setRemote_Addr (java.lang.String Remote_Addr);

	/**
	 * Get Remote Addr.
	 * Remote Address
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getRemote_Addr();

    /** Column definition for Remote_Addr */
    public static final org.adempiere.model.ModelColumn<I_AD_Issue, Object> COLUMN_Remote_Addr = new org.adempiere.model.ModelColumn<I_AD_Issue, Object>(I_AD_Issue.class, "Remote_Addr", null);
    /** Column name Remote_Addr */
    public static final String COLUMNNAME_Remote_Addr = "Remote_Addr";

	/**
	 * Set Remote Host.
	 * Remote host Info
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setRemote_Host (java.lang.String Remote_Host);

	/**
	 * Get Remote Host.
	 * Remote host Info
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getRemote_Host();

    /** Column definition for Remote_Host */
    public static final org.adempiere.model.ModelColumn<I_AD_Issue, Object> COLUMN_Remote_Host = new org.adempiere.model.ModelColumn<I_AD_Issue, Object>(I_AD_Issue.class, "Remote_Host", null);
    /** Column name Remote_Host */
    public static final String COLUMNNAME_Remote_Host = "Remote_Host";

	/**
	 * Set Request Document No.
	 * Adempiere Request Document No
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setRequestDocumentNo (java.lang.String RequestDocumentNo);

	/**
	 * Get Request Document No.
	 * Adempiere Request Document No
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getRequestDocumentNo();

    /** Column definition for RequestDocumentNo */
    public static final org.adempiere.model.ModelColumn<I_AD_Issue, Object> COLUMN_RequestDocumentNo = new org.adempiere.model.ModelColumn<I_AD_Issue, Object>(I_AD_Issue.class, "RequestDocumentNo", null);
    /** Column name RequestDocumentNo */
    public static final String COLUMNNAME_RequestDocumentNo = "RequestDocumentNo";

	/**
	 * Set Response Text.
	 * Request Response Text
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setResponseText (java.lang.String ResponseText);

	/**
	 * Get Response Text.
	 * Request Response Text
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getResponseText();

    /** Column definition for ResponseText */
    public static final org.adempiere.model.ModelColumn<I_AD_Issue, Object> COLUMN_ResponseText = new org.adempiere.model.ModelColumn<I_AD_Issue, Object>(I_AD_Issue.class, "ResponseText", null);
    /** Column name ResponseText */
    public static final String COLUMNNAME_ResponseText = "ResponseText";

	/**
	 * Set Source Class.
	 * Source Class Name
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setSourceClassName (java.lang.String SourceClassName);

	/**
	 * Get Source Class.
	 * Source Class Name
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getSourceClassName();

    /** Column definition for SourceClassName */
    public static final org.adempiere.model.ModelColumn<I_AD_Issue, Object> COLUMN_SourceClassName = new org.adempiere.model.ModelColumn<I_AD_Issue, Object>(I_AD_Issue.class, "SourceClassName", null);
    /** Column name SourceClassName */
    public static final String COLUMNNAME_SourceClassName = "SourceClassName";

	/**
	 * Set Source Method.
	 * Source Method Name
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setSourceMethodName (java.lang.String SourceMethodName);

	/**
	 * Get Source Method.
	 * Source Method Name
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getSourceMethodName();

    /** Column definition for SourceMethodName */
    public static final org.adempiere.model.ModelColumn<I_AD_Issue, Object> COLUMN_SourceMethodName = new org.adempiere.model.ModelColumn<I_AD_Issue, Object>(I_AD_Issue.class, "SourceMethodName", null);
    /** Column name SourceMethodName */
    public static final String COLUMNNAME_SourceMethodName = "SourceMethodName";

	/**
	 * Set Stack Trace.
	 * System Log Trace
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setStackTrace (java.lang.String StackTrace);

	/**
	 * Get Stack Trace.
	 * System Log Trace
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getStackTrace();

    /** Column definition for StackTrace */
    public static final org.adempiere.model.ModelColumn<I_AD_Issue, Object> COLUMN_StackTrace = new org.adempiere.model.ModelColumn<I_AD_Issue, Object>(I_AD_Issue.class, "StackTrace", null);
    /** Column name StackTrace */
    public static final String COLUMNNAME_StackTrace = "StackTrace";

	/**
	 * Set Statistik.
	 * Information to help profiling the system for solving support issues
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setStatisticsInfo (java.lang.String StatisticsInfo);

	/**
	 * Get Statistik.
	 * Information to help profiling the system for solving support issues
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getStatisticsInfo();

    /** Column definition for StatisticsInfo */
    public static final org.adempiere.model.ModelColumn<I_AD_Issue, Object> COLUMN_StatisticsInfo = new org.adempiere.model.ModelColumn<I_AD_Issue, Object>(I_AD_Issue.class, "StatisticsInfo", null);
    /** Column name StatisticsInfo */
    public static final String COLUMNNAME_StatisticsInfo = "StatisticsInfo";

	/**
	 * Set Support EMail.
	 * EMail address to send support information and updates to
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setSupportEMail (java.lang.String SupportEMail);

	/**
	 * Get Support EMail.
	 * EMail address to send support information and updates to
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getSupportEMail();

    /** Column definition for SupportEMail */
    public static final org.adempiere.model.ModelColumn<I_AD_Issue, Object> COLUMN_SupportEMail = new org.adempiere.model.ModelColumn<I_AD_Issue, Object>(I_AD_Issue.class, "SupportEMail", null);
    /** Column name SupportEMail */
    public static final String COLUMNNAME_SupportEMail = "SupportEMail";

	/**
	 * Set System Status.
	 * Status of the system - Support priority depends on system status
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setSystemStatus (java.lang.String SystemStatus);

	/**
	 * Get System Status.
	 * Status of the system - Support priority depends on system status
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getSystemStatus();

    /** Column definition for SystemStatus */
    public static final org.adempiere.model.ModelColumn<I_AD_Issue, Object> COLUMN_SystemStatus = new org.adempiere.model.ModelColumn<I_AD_Issue, Object>(I_AD_Issue.class, "SystemStatus", null);
    /** Column name SystemStatus */
    public static final String COLUMNNAME_SystemStatus = "SystemStatus";

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
    public static final org.adempiere.model.ModelColumn<I_AD_Issue, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_AD_Issue, Object>(I_AD_Issue.class, "Updated", null);
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
	 * Set UserName.
	 * UserName / Login to use for login
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setUserName (java.lang.String UserName);

	/**
	 * Get UserName.
	 * UserName / Login to use for login
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getUserName();

    /** Column definition for UserName */
    public static final org.adempiere.model.ModelColumn<I_AD_Issue, Object> COLUMN_UserName = new org.adempiere.model.ModelColumn<I_AD_Issue, Object>(I_AD_Issue.class, "UserName", null);
    /** Column name UserName */
    public static final String COLUMNNAME_UserName = "UserName";

	/**
	 * Set Version.
	 * Version of the table definition
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setVersion (java.lang.String Version);

	/**
	 * Get Version.
	 * Version of the table definition
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getVersion();

    /** Column definition for Version */
    public static final org.adempiere.model.ModelColumn<I_AD_Issue, Object> COLUMN_Version = new org.adempiere.model.ModelColumn<I_AD_Issue, Object>(I_AD_Issue.class, "Version", null);
    /** Column name Version */
    public static final String COLUMNNAME_Version = "Version";
}
