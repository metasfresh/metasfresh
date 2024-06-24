package org.compiere.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;

/** Generated Interface for AD_Issue
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_AD_Issue 
{

	String Table_Name = "AD_Issue";

//	/** AD_Table_ID=828 */
//	int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);


	/**
	 * Set Asset.
	 * Asset used internally or by customers
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setA_Asset_ID (int A_Asset_ID);

	/**
	 * Get Asset.
	 * Asset used internally or by customers
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getA_Asset_ID();

	@Nullable org.compiere.model.I_A_Asset getA_Asset();

	void setA_Asset(@Nullable org.compiere.model.I_A_Asset A_Asset);

	ModelColumn<I_AD_Issue, org.compiere.model.I_A_Asset> COLUMN_A_Asset_ID = new ModelColumn<>(I_AD_Issue.class, "A_Asset_ID", org.compiere.model.I_A_Asset.class);
	String COLUMNNAME_A_Asset_ID = "A_Asset_ID";

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

	ModelColumn<I_AD_Issue, org.compiere.model.I_AD_Form> COLUMN_AD_Form_ID = new ModelColumn<>(I_AD_Issue.class, "AD_Form_ID", org.compiere.model.I_AD_Form.class);
	String COLUMNNAME_AD_Form_ID = "AD_Form_ID";

	/**
	 * Set Issues.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_Issue_ID (int AD_Issue_ID);

	/**
	 * Get Issues.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_Issue_ID();

	ModelColumn<I_AD_Issue, Object> COLUMN_AD_Issue_ID = new ModelColumn<>(I_AD_Issue.class, "AD_Issue_ID", null);
	String COLUMNNAME_AD_Issue_ID = "AD_Issue_ID";

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
	 * Set Process Instance.
	 * Instance of a Process
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_PInstance_ID (int AD_PInstance_ID);

	/**
	 * Get Process Instance.
	 * Instance of a Process
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_PInstance_ID();

	@Nullable org.compiere.model.I_AD_PInstance getAD_PInstance();

	void setAD_PInstance(@Nullable org.compiere.model.I_AD_PInstance AD_PInstance);

	ModelColumn<I_AD_Issue, org.compiere.model.I_AD_PInstance> COLUMN_AD_PInstance_ID = new ModelColumn<>(I_AD_Issue.class, "AD_PInstance_ID", org.compiere.model.I_AD_PInstance.class);
	String COLUMNNAME_AD_PInstance_ID = "AD_PInstance_ID";

	/**
	 * Set Process.
	 * Process or Report
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_Process_ID (int AD_Process_ID);

	/**
	 * Get Process.
	 * Process or Report
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_Process_ID();

	@Nullable org.compiere.model.I_AD_Process getAD_Process();

	void setAD_Process(@Nullable org.compiere.model.I_AD_Process AD_Process);

	ModelColumn<I_AD_Issue, org.compiere.model.I_AD_Process> COLUMN_AD_Process_ID = new ModelColumn<>(I_AD_Issue.class, "AD_Process_ID", org.compiere.model.I_AD_Process.class);
	String COLUMNNAME_AD_Process_ID = "AD_Process_ID";

	/**
	 * Set Table.
	 * Database Table information
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_Table_ID (int AD_Table_ID);

	/**
	 * Get Table.
	 * Database Table information
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_Table_ID();

	String COLUMNNAME_AD_Table_ID = "AD_Table_ID";

	/**
	 * Set Window.
	 * Data entry or display window
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_Window_ID (int AD_Window_ID);

	/**
	 * Get Window.
	 * Data entry or display window
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_Window_ID();

	@Nullable org.compiere.model.I_AD_Window getAD_Window();

	void setAD_Window(@Nullable org.compiere.model.I_AD_Window AD_Window);

	ModelColumn<I_AD_Issue, org.compiere.model.I_AD_Window> COLUMN_AD_Window_ID = new ModelColumn<>(I_AD_Issue.class, "AD_Window_ID", org.compiere.model.I_AD_Window.class);
	String COLUMNNAME_AD_Window_ID = "AD_Window_ID";

	/**
	 * Set Comments.
	 * Comments or additional information
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setComments (@Nullable java.lang.String Comments);

	/**
	 * Get Comments.
	 * Comments or additional information
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getComments();

	ModelColumn<I_AD_Issue, Object> COLUMN_Comments = new ModelColumn<>(I_AD_Issue.class, "Comments", null);
	String COLUMNNAME_Comments = "Comments";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_AD_Issue, Object> COLUMN_Created = new ModelColumn<>(I_AD_Issue.class, "Created", null);
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
	 * Set Database.
	 * Database Information
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDatabaseInfo (@Nullable java.lang.String DatabaseInfo);

	/**
	 * Get Database.
	 * Database Information
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDatabaseInfo();

	ModelColumn<I_AD_Issue, Object> COLUMN_DatabaseInfo = new ModelColumn<>(I_AD_Issue.class, "DatabaseInfo", null);
	String COLUMNNAME_DatabaseInfo = "DatabaseInfo";

	/**
	 * Set DB Address.
	 * JDBC URL of the database server
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDBAddress (@Nullable java.lang.String DBAddress);

	/**
	 * Get DB Address.
	 * JDBC URL of the database server
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDBAddress();

	ModelColumn<I_AD_Issue, Object> COLUMN_DBAddress = new ModelColumn<>(I_AD_Issue.class, "DBAddress", null);
	String COLUMNNAME_DBAddress = "DBAddress";

	/**
	 * Set Error Trace.
	 * System Error Trace
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setErrorTrace (@Nullable java.lang.String ErrorTrace);

	/**
	 * Get Error Trace.
	 * System Error Trace
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getErrorTrace();

	ModelColumn<I_AD_Issue, Object> COLUMN_ErrorTrace = new ModelColumn<>(I_AD_Issue.class, "ErrorTrace", null);
	String COLUMNNAME_ErrorTrace = "ErrorTrace";

	/**
	 * Set Frontend URL.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setFrontendURL (@Nullable java.lang.String FrontendURL);

	/**
	 * Get Frontend URL.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getFrontendURL();

	ModelColumn<I_AD_Issue, Object> COLUMN_FrontendURL = new ModelColumn<>(I_AD_Issue.class, "FrontendURL", null);
	String COLUMNNAME_FrontendURL = "FrontendURL";

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

	ModelColumn<I_AD_Issue, Object> COLUMN_IsActive = new ModelColumn<>(I_AD_Issue.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Reproducible.
	 * Problem can re reproduced in Gardenworld
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIsReproducible (@Nullable java.lang.String IsReproducible);

	/**
	 * Get Reproducible.
	 * Problem can re reproduced in Gardenworld
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getIsReproducible();

	ModelColumn<I_AD_Issue, Object> COLUMN_IsReproducible = new ModelColumn<>(I_AD_Issue.class, "IsReproducible", null);
	String COLUMNNAME_IsReproducible = "IsReproducible";

	/**
	 * Set Issue Category.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIssueCategory (java.lang.String IssueCategory);

	/**
	 * Get Issue Category.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getIssueCategory();

	ModelColumn<I_AD_Issue, Object> COLUMN_IssueCategory = new ModelColumn<>(I_AD_Issue.class, "IssueCategory", null);
	String COLUMNNAME_IssueCategory = "IssueCategory";

	/**
	 * Set Source.
	 * Issue Source
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIssueSource (@Nullable java.lang.String IssueSource);

	/**
	 * Get Source.
	 * Issue Source
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getIssueSource();

	ModelColumn<I_AD_Issue, Object> COLUMN_IssueSource = new ModelColumn<>(I_AD_Issue.class, "IssueSource", null);
	String COLUMNNAME_IssueSource = "IssueSource";

	/**
	 * Set Issue Summary.
	 * Issue Summary
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIssueSummary (java.lang.String IssueSummary);

	/**
	 * Get Issue Summary.
	 * Issue Summary
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getIssueSummary();

	ModelColumn<I_AD_Issue, Object> COLUMN_IssueSummary = new ModelColumn<>(I_AD_Issue.class, "IssueSummary", null);
	String COLUMNNAME_IssueSummary = "IssueSummary";

	/**
	 * Set Vanilla System.
	 * The system was NOT compiled from Source - i.e. standard distribution
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIsVanillaSystem (@Nullable java.lang.String IsVanillaSystem);

	/**
	 * Get Vanilla System.
	 * The system was NOT compiled from Source - i.e. standard distribution
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getIsVanillaSystem();

	ModelColumn<I_AD_Issue, Object> COLUMN_IsVanillaSystem = new ModelColumn<>(I_AD_Issue.class, "IsVanillaSystem", null);
	String COLUMNNAME_IsVanillaSystem = "IsVanillaSystem";

	/**
	 * Set Java Info.
	 * Java Version Info
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setJavaInfo (@Nullable java.lang.String JavaInfo);

	/**
	 * Get Java Info.
	 * Java Version Info
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getJavaInfo();

	ModelColumn<I_AD_Issue, Object> COLUMN_JavaInfo = new ModelColumn<>(I_AD_Issue.class, "JavaInfo", null);
	String COLUMNNAME_JavaInfo = "JavaInfo";

	/**
	 * Set Line.
	 * Line No
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setLineNo (int LineNo);

	/**
	 * Get Line.
	 * Line No
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getLineNo();

	ModelColumn<I_AD_Issue, Object> COLUMN_LineNo = new ModelColumn<>(I_AD_Issue.class, "LineNo", null);
	String COLUMNNAME_LineNo = "LineNo";

	/**
	 * Set Local Host.
	 * Local Host Info
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setLocal_Host (@Nullable java.lang.String Local_Host);

	/**
	 * Get Local Host.
	 * Local Host Info
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getLocal_Host();

	ModelColumn<I_AD_Issue, Object> COLUMN_Local_Host = new ModelColumn<>(I_AD_Issue.class, "Local_Host", null);
	String COLUMNNAME_Local_Host = "Local_Host";

	/**
	 * Set Logger.
	 * Logger Name
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setLoggerName (@Nullable java.lang.String LoggerName);

	/**
	 * Get Logger.
	 * Logger Name
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getLoggerName();

	ModelColumn<I_AD_Issue, Object> COLUMN_LoggerName = new ModelColumn<>(I_AD_Issue.class, "LoggerName", null);
	String COLUMNNAME_LoggerName = "LoggerName";

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

	ModelColumn<I_AD_Issue, Object> COLUMN_Name = new ModelColumn<>(I_AD_Issue.class, "Name", null);
	String COLUMNNAME_Name = "Name";

	/**
	 * Set Operating System.
	 * Operating System Info
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setOperatingSystemInfo (@Nullable java.lang.String OperatingSystemInfo);

	/**
	 * Get Operating System.
	 * Operating System Info
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getOperatingSystemInfo();

	ModelColumn<I_AD_Issue, Object> COLUMN_OperatingSystemInfo = new ModelColumn<>(I_AD_Issue.class, "OperatingSystemInfo", null);
	String COLUMNNAME_OperatingSystemInfo = "OperatingSystemInfo";

	/**
	 * Set Processed.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setProcessed (boolean Processed);

	/**
	 * Get Processed.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isProcessed();

	ModelColumn<I_AD_Issue, Object> COLUMN_Processed = new ModelColumn<>(I_AD_Issue.class, "Processed", null);
	String COLUMNNAME_Processed = "Processed";

	/**
	 * Set Process Now.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setProcessing (boolean Processing);

	/**
	 * Get Process Now.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isProcessing();

	ModelColumn<I_AD_Issue, Object> COLUMN_Processing = new ModelColumn<>(I_AD_Issue.class, "Processing", null);
	String COLUMNNAME_Processing = "Processing";

	/**
	 * Set Profile.
	 * Information to help profiling the system for solving support issues
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setProfileInfo (@Nullable java.lang.String ProfileInfo);

	/**
	 * Get Profile.
	 * Information to help profiling the system for solving support issues
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getProfileInfo();

	ModelColumn<I_AD_Issue, Object> COLUMN_ProfileInfo = new ModelColumn<>(I_AD_Issue.class, "ProfileInfo", null);
	String COLUMNNAME_ProfileInfo = "ProfileInfo";

	/**
	 * Set Record ID.
	 * Direct internal record ID
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setRecord_ID (int Record_ID);

	/**
	 * Get Record ID.
	 * Direct internal record ID
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getRecord_ID();

	ModelColumn<I_AD_Issue, Object> COLUMN_Record_ID = new ModelColumn<>(I_AD_Issue.class, "Record_ID", null);
	String COLUMNNAME_Record_ID = "Record_ID";

	/**
	 * Set Release No.
	 * Internal Release Number
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setReleaseNo (java.lang.String ReleaseNo);

	/**
	 * Get Release No.
	 * Internal Release Number
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getReleaseNo();

	ModelColumn<I_AD_Issue, Object> COLUMN_ReleaseNo = new ModelColumn<>(I_AD_Issue.class, "ReleaseNo", null);
	String COLUMNNAME_ReleaseNo = "ReleaseNo";

	/**
	 * Set Release Tag.
	 * Release Tag
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setReleaseTag (@Nullable java.lang.String ReleaseTag);

	/**
	 * Get Release Tag.
	 * Release Tag
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getReleaseTag();

	ModelColumn<I_AD_Issue, Object> COLUMN_ReleaseTag = new ModelColumn<>(I_AD_Issue.class, "ReleaseTag", null);
	String COLUMNNAME_ReleaseTag = "ReleaseTag";

	/**
	 * Set Remote Addr.
	 * Remote Address
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setRemote_Addr (@Nullable java.lang.String Remote_Addr);

	/**
	 * Get Remote Addr.
	 * Remote Address
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getRemote_Addr();

	ModelColumn<I_AD_Issue, Object> COLUMN_Remote_Addr = new ModelColumn<>(I_AD_Issue.class, "Remote_Addr", null);
	String COLUMNNAME_Remote_Addr = "Remote_Addr";

	/**
	 * Set Remote Host.
	 * Remote host Info
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setRemote_Host (@Nullable java.lang.String Remote_Host);

	/**
	 * Get Remote Host.
	 * Remote host Info
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getRemote_Host();

	ModelColumn<I_AD_Issue, Object> COLUMN_Remote_Host = new ModelColumn<>(I_AD_Issue.class, "Remote_Host", null);
	String COLUMNNAME_Remote_Host = "Remote_Host";

	/**
	 * Set Request Document No.
	 * Adempiere Request Document No
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setRequestDocumentNo (@Nullable java.lang.String RequestDocumentNo);

	/**
	 * Get Request Document No.
	 * Adempiere Request Document No
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getRequestDocumentNo();

	ModelColumn<I_AD_Issue, Object> COLUMN_RequestDocumentNo = new ModelColumn<>(I_AD_Issue.class, "RequestDocumentNo", null);
	String COLUMNNAME_RequestDocumentNo = "RequestDocumentNo";

	/**
	 * Set Response Text.
	 * Request Response Text
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setResponseText (@Nullable java.lang.String ResponseText);

	/**
	 * Get Response Text.
	 * Request Response Text
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getResponseText();

	ModelColumn<I_AD_Issue, Object> COLUMN_ResponseText = new ModelColumn<>(I_AD_Issue.class, "ResponseText", null);
	String COLUMNNAME_ResponseText = "ResponseText";

	/**
	 * Set Known Issue.
	 * Known Issue
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setR_IssueKnown_ID (int R_IssueKnown_ID);

	/**
	 * Get Known Issue.
	 * Known Issue
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getR_IssueKnown_ID();

	@Nullable org.compiere.model.I_R_IssueKnown getR_IssueKnown();

	void setR_IssueKnown(@Nullable org.compiere.model.I_R_IssueKnown R_IssueKnown);

	ModelColumn<I_AD_Issue, org.compiere.model.I_R_IssueKnown> COLUMN_R_IssueKnown_ID = new ModelColumn<>(I_AD_Issue.class, "R_IssueKnown_ID", org.compiere.model.I_R_IssueKnown.class);
	String COLUMNNAME_R_IssueKnown_ID = "R_IssueKnown_ID";

	/**
	 * Set Issue Project.
	 * Implementation Projects
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setR_IssueProject_ID (int R_IssueProject_ID);

	/**
	 * Get Issue Project.
	 * Implementation Projects
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getR_IssueProject_ID();

	@Nullable org.compiere.model.I_R_IssueProject getR_IssueProject();

	void setR_IssueProject(@Nullable org.compiere.model.I_R_IssueProject R_IssueProject);

	ModelColumn<I_AD_Issue, org.compiere.model.I_R_IssueProject> COLUMN_R_IssueProject_ID = new ModelColumn<>(I_AD_Issue.class, "R_IssueProject_ID", org.compiere.model.I_R_IssueProject.class);
	String COLUMNNAME_R_IssueProject_ID = "R_IssueProject_ID";

	/**
	 * Set Issue System.
	 * System creating the issue
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setR_IssueSystem_ID (int R_IssueSystem_ID);

	/**
	 * Get Issue System.
	 * System creating the issue
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getR_IssueSystem_ID();

	@Nullable org.compiere.model.I_R_IssueSystem getR_IssueSystem();

	void setR_IssueSystem(@Nullable org.compiere.model.I_R_IssueSystem R_IssueSystem);

	ModelColumn<I_AD_Issue, org.compiere.model.I_R_IssueSystem> COLUMN_R_IssueSystem_ID = new ModelColumn<>(I_AD_Issue.class, "R_IssueSystem_ID", org.compiere.model.I_R_IssueSystem.class);
	String COLUMNNAME_R_IssueSystem_ID = "R_IssueSystem_ID";

	/**
	 * Set IssueUser.
	 * User who reported issues
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setR_IssueUser_ID (int R_IssueUser_ID);

	/**
	 * Get IssueUser.
	 * User who reported issues
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getR_IssueUser_ID();

	@Nullable org.compiere.model.I_R_IssueUser getR_IssueUser();

	void setR_IssueUser(@Nullable org.compiere.model.I_R_IssueUser R_IssueUser);

	ModelColumn<I_AD_Issue, org.compiere.model.I_R_IssueUser> COLUMN_R_IssueUser_ID = new ModelColumn<>(I_AD_Issue.class, "R_IssueUser_ID", org.compiere.model.I_R_IssueUser.class);
	String COLUMNNAME_R_IssueUser_ID = "R_IssueUser_ID";

	/**
	 * Set Request.
	 * Request from a Business Partner or Prospect
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setR_Request_ID (int R_Request_ID);

	/**
	 * Get Request.
	 * Request from a Business Partner or Prospect
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getR_Request_ID();

	@Nullable org.compiere.model.I_R_Request getR_Request();

	void setR_Request(@Nullable org.compiere.model.I_R_Request R_Request);

	ModelColumn<I_AD_Issue, org.compiere.model.I_R_Request> COLUMN_R_Request_ID = new ModelColumn<>(I_AD_Issue.class, "R_Request_ID", org.compiere.model.I_R_Request.class);
	String COLUMNNAME_R_Request_ID = "R_Request_ID";

	/**
	 * Set Source Class.
	 * Source Class Name
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setSourceClassName (@Nullable java.lang.String SourceClassName);

	/**
	 * Get Source Class.
	 * Source Class Name
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getSourceClassName();

	ModelColumn<I_AD_Issue, Object> COLUMN_SourceClassName = new ModelColumn<>(I_AD_Issue.class, "SourceClassName", null);
	String COLUMNNAME_SourceClassName = "SourceClassName";

	/**
	 * Set Source Method.
	 * Source Method Name
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setSourceMethodName (@Nullable java.lang.String SourceMethodName);

	/**
	 * Get Source Method.
	 * Source Method Name
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getSourceMethodName();

	ModelColumn<I_AD_Issue, Object> COLUMN_SourceMethodName = new ModelColumn<>(I_AD_Issue.class, "SourceMethodName", null);
	String COLUMNNAME_SourceMethodName = "SourceMethodName";

	/**
	 * Set Stack Trace.
	 * System Log Trace
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setStackTrace (@Nullable java.lang.String StackTrace);

	/**
	 * Get Stack Trace.
	 * System Log Trace
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getStackTrace();

	ModelColumn<I_AD_Issue, Object> COLUMN_StackTrace = new ModelColumn<>(I_AD_Issue.class, "StackTrace", null);
	String COLUMNNAME_StackTrace = "StackTrace";

	/**
	 * Set Statistics.
	 * Information to help profiling the system for solving support issues
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setStatisticsInfo (@Nullable java.lang.String StatisticsInfo);

	/**
	 * Get Statistics.
	 * Information to help profiling the system for solving support issues
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getStatisticsInfo();

	ModelColumn<I_AD_Issue, Object> COLUMN_StatisticsInfo = new ModelColumn<>(I_AD_Issue.class, "StatisticsInfo", null);
	String COLUMNNAME_StatisticsInfo = "StatisticsInfo";

	/**
	 * Set Support EMail.
	 * EMail address to send support information and updates to
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setSupportEMail (@Nullable java.lang.String SupportEMail);

	/**
	 * Get Support EMail.
	 * EMail address to send support information and updates to
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getSupportEMail();

	ModelColumn<I_AD_Issue, Object> COLUMN_SupportEMail = new ModelColumn<>(I_AD_Issue.class, "SupportEMail", null);
	String COLUMNNAME_SupportEMail = "SupportEMail";

	/**
	 * Set System Status.
	 * Status of the system - Support priority depends on system status
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setSystemStatus (java.lang.String SystemStatus);

	/**
	 * Get System Status.
	 * Status of the system - Support priority depends on system status
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getSystemStatus();

	ModelColumn<I_AD_Issue, Object> COLUMN_SystemStatus = new ModelColumn<>(I_AD_Issue.class, "SystemStatus", null);
	String COLUMNNAME_SystemStatus = "SystemStatus";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_AD_Issue, Object> COLUMN_Updated = new ModelColumn<>(I_AD_Issue.class, "Updated", null);
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
	 * Set User Agent.
	 * Browser Used
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setUserAgent (@Nullable java.lang.String UserAgent);

	/**
	 * Get User Agent.
	 * Browser Used
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getUserAgent();

	ModelColumn<I_AD_Issue, Object> COLUMN_UserAgent = new ModelColumn<>(I_AD_Issue.class, "UserAgent", null);
	String COLUMNNAME_UserAgent = "UserAgent";

	/**
	 * Set UserName.
	 * UserName / Login to use for login
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setUserName (java.lang.String UserName);

	/**
	 * Get UserName.
	 * UserName / Login to use for login
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getUserName();

	ModelColumn<I_AD_Issue, Object> COLUMN_UserName = new ModelColumn<>(I_AD_Issue.class, "UserName", null);
	String COLUMNNAME_UserName = "UserName";

	/**
	 * Set Version.
	 * Version of the table definition
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setVersion (java.lang.String Version);

	/**
	 * Get Version.
	 * Version of the table definition
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getVersion();

	ModelColumn<I_AD_Issue, Object> COLUMN_Version = new ModelColumn<>(I_AD_Issue.class, "Version", null);
	String COLUMNNAME_Version = "Version";
}
