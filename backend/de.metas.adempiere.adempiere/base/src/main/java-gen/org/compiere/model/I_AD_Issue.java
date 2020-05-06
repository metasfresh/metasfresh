/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2007 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software, you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY, without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program, if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import org.compiere.util.KeyNamePair;

/** Generated Interface for AD_Issue
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a
 */
public interface I_AD_Issue 
{

    /** TableName=AD_Issue */
    public static final String Table_Name = "AD_Issue";

    /** AD_Table_ID=828 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 6 - System - Client 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(6);

    /** Load Meta Data */

    /** Column name A_Asset_ID */
    public static final String COLUMNNAME_A_Asset_ID = "A_Asset_ID";

	/** Set Asset.
	  * Asset used internally or by customers
	  */
	public void setA_Asset_ID (int A_Asset_ID);

	/** Get Asset.
	  * Asset used internally or by customers
	  */
	public int getA_Asset_ID();

	public I_A_Asset getA_Asset() throws RuntimeException;

    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/** Get Client.
	  * Client/Tenant for this installation.
	  */
	public int getAD_Client_ID();

    /** Column name AD_Form_ID */
    public static final String COLUMNNAME_AD_Form_ID = "AD_Form_ID";

	/** Set Special Form.
	  * Special Form
	  */
	public void setAD_Form_ID (int AD_Form_ID);

	/** Get Special Form.
	  * Special Form
	  */
	public int getAD_Form_ID();

	public I_AD_Form getAD_Form() throws RuntimeException;

    /** Column name AD_Issue_ID */
    public static final String COLUMNNAME_AD_Issue_ID = "AD_Issue_ID";

	/** Set System Issue.
	  * Automatically created or manually entered System Issue
	  */
	public void setAD_Issue_ID (int AD_Issue_ID);

	/** Get System Issue.
	  * Automatically created or manually entered System Issue
	  */
	public int getAD_Issue_ID();

    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/** Set Organization.
	  * Organizational entity within client
	  */
	public void setAD_Org_ID (int AD_Org_ID);

	/** Get Organization.
	  * Organizational entity within client
	  */
	public int getAD_Org_ID();

    /** Column name AD_Process_ID */
    public static final String COLUMNNAME_AD_Process_ID = "AD_Process_ID";

	/** Set Process.
	  * Process or Report
	  */
	public void setAD_Process_ID (int AD_Process_ID);

	/** Get Process.
	  * Process or Report
	  */
	public int getAD_Process_ID();

	public I_AD_Process getAD_Process() throws RuntimeException;

    /** Column name AD_Window_ID */
    public static final String COLUMNNAME_AD_Window_ID = "AD_Window_ID";

	/** Set Window.
	  * Data entry or display window
	  */
	public void setAD_Window_ID (int AD_Window_ID);

	/** Get Window.
	  * Data entry or display window
	  */
	public int getAD_Window_ID();

	public I_AD_Window getAD_Window() throws RuntimeException;

    /** Column name Comments */
    public static final String COLUMNNAME_Comments = "Comments";

	/** Set Comments.
	  * Comments or additional information
	  */
	public void setComments (String Comments);

	/** Get Comments.
	  * Comments or additional information
	  */
	public String getComments();

    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/** Get Created.
	  * Date this record was created
	  */
	public Timestamp getCreated();

    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/** Get Created By.
	  * User who created this records
	  */
	public int getCreatedBy();

    /** Column name DatabaseInfo */
    public static final String COLUMNNAME_DatabaseInfo = "DatabaseInfo";

	/** Set Database.
	  * Database Information
	  */
	public void setDatabaseInfo (String DatabaseInfo);

	/** Get Database.
	  * Database Information
	  */
	public String getDatabaseInfo();

    /** Column name DBAddress */
    public static final String COLUMNNAME_DBAddress = "DBAddress";

	/** Set DB Address.
	  * JDBC URL of the database server
	  */
	public void setDBAddress (String DBAddress);

	/** Get DB Address.
	  * JDBC URL of the database server
	  */
	public String getDBAddress();

    /** Column name ErrorTrace */
    public static final String COLUMNNAME_ErrorTrace = "ErrorTrace";

	/** Set Error Trace.
	  * System Error Trace
	  */
	public void setErrorTrace (String ErrorTrace);

	/** Get Error Trace.
	  * System Error Trace
	  */
	public String getErrorTrace();

    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/** Set Active.
	  * The record is active in the system
	  */
	public void setIsActive (boolean IsActive);

	/** Get Active.
	  * The record is active in the system
	  */
	public boolean isActive();

    /** Column name IsReproducible */
    public static final String COLUMNNAME_IsReproducible = "IsReproducible";

	/** Set Reproducible.
	  * Problem can re reproduced in Gardenworld
	  */
	public void setIsReproducible (String IsReproducible);

	/** Get Reproducible.
	  * Problem can re reproduced in Gardenworld
	  */
	public String getIsReproducible();

    /** Column name IssueSource */
    public static final String COLUMNNAME_IssueSource = "IssueSource";

	/** Set Source.
	  * Issue Source
	  */
	public void setIssueSource (String IssueSource);

	/** Get Source.
	  * Issue Source
	  */
	public String getIssueSource();

    /** Column name IssueSummary */
    public static final String COLUMNNAME_IssueSummary = "IssueSummary";

	/** Set Issue Summary.
	  * Issue Summary
	  */
	public void setIssueSummary (String IssueSummary);

	/** Get Issue Summary.
	  * Issue Summary
	  */
	public String getIssueSummary();

    /** Column name IsVanillaSystem */
    public static final String COLUMNNAME_IsVanillaSystem = "IsVanillaSystem";

	/** Set Vanilla System.
	  * The system was NOT compiled from Source - i.e. standard distribution
	  */
	public void setIsVanillaSystem (String IsVanillaSystem);

	/** Get Vanilla System.
	  * The system was NOT compiled from Source - i.e. standard distribution
	  */
	public String getIsVanillaSystem();

    /** Column name JavaInfo */
    public static final String COLUMNNAME_JavaInfo = "JavaInfo";

	/** Set Java Info.
	  * Java Version Info
	  */
	public void setJavaInfo (String JavaInfo);

	/** Get Java Info.
	  * Java Version Info
	  */
	public String getJavaInfo();

    /** Column name LineNo */
    public static final String COLUMNNAME_LineNo = "LineNo";

	/** Set Line.
	  * Line No
	  */
	public void setLineNo (int LineNo);

	/** Get Line.
	  * Line No
	  */
	public int getLineNo();

    /** Column name Local_Host */
    public static final String COLUMNNAME_Local_Host = "Local_Host";

	/** Set Local Host.
	  * Local Host Info
	  */
	public void setLocal_Host (String Local_Host);

	/** Get Local Host.
	  * Local Host Info
	  */
	public String getLocal_Host();

    /** Column name LoggerName */
    public static final String COLUMNNAME_LoggerName = "LoggerName";

	/** Set Logger.
	  * Logger Name
	  */
	public void setLoggerName (String LoggerName);

	/** Get Logger.
	  * Logger Name
	  */
	public String getLoggerName();

    /** Column name Name */
    public static final String COLUMNNAME_Name = "Name";

	/** Set Name.
	  * Alphanumeric identifier of the entity
	  */
	public void setName (String Name);

	/** Get Name.
	  * Alphanumeric identifier of the entity
	  */
	public String getName();

    /** Column name OperatingSystemInfo */
    public static final String COLUMNNAME_OperatingSystemInfo = "OperatingSystemInfo";

	/** Set Operating System.
	  * Operating System Info
	  */
	public void setOperatingSystemInfo (String OperatingSystemInfo);

	/** Get Operating System.
	  * Operating System Info
	  */
	public String getOperatingSystemInfo();

    /** Column name Processed */
    public static final String COLUMNNAME_Processed = "Processed";

	/** Set Processed.
	  * The document has been processed
	  */
	public void setProcessed (boolean Processed);

	/** Get Processed.
	  * The document has been processed
	  */
	public boolean isProcessed();

    /** Column name Processing */
    public static final String COLUMNNAME_Processing = "Processing";

	/** Set Process Now	  */
	public void setProcessing (boolean Processing);

	/** Get Process Now	  */
	public boolean isProcessing();

    /** Column name ProfileInfo */
    public static final String COLUMNNAME_ProfileInfo = "ProfileInfo";

	/** Set Profile.
	  * Information to help profiling the system for solving support issues
	  */
	public void setProfileInfo (String ProfileInfo);

	/** Get Profile.
	  * Information to help profiling the system for solving support issues
	  */
	public String getProfileInfo();

    /** Column name Record_ID */
    public static final String COLUMNNAME_Record_ID = "Record_ID";

	/** Set Record ID.
	  * Direct internal record ID
	  */
	public void setRecord_ID (int Record_ID);

	/** Get Record ID.
	  * Direct internal record ID
	  */
	public int getRecord_ID();

    /** Column name ReleaseNo */
    public static final String COLUMNNAME_ReleaseNo = "ReleaseNo";

	/** Set Release No.
	  * Internal Release Number
	  */
	public void setReleaseNo (String ReleaseNo);

	/** Get Release No.
	  * Internal Release Number
	  */
	public String getReleaseNo();

    /** Column name ReleaseTag */
    public static final String COLUMNNAME_ReleaseTag = "ReleaseTag";

	/** Set Release Tag.
	  * Release Tag
	  */
	public void setReleaseTag (String ReleaseTag);

	/** Get Release Tag.
	  * Release Tag
	  */
	public String getReleaseTag();

    /** Column name Remote_Addr */
    public static final String COLUMNNAME_Remote_Addr = "Remote_Addr";

	/** Set Remote Addr.
	  * Remote Address
	  */
	public void setRemote_Addr (String Remote_Addr);

	/** Get Remote Addr.
	  * Remote Address
	  */
	public String getRemote_Addr();

    /** Column name Remote_Host */
    public static final String COLUMNNAME_Remote_Host = "Remote_Host";

	/** Set Remote Host.
	  * Remote host Info
	  */
	public void setRemote_Host (String Remote_Host);

	/** Get Remote Host.
	  * Remote host Info
	  */
	public String getRemote_Host();

    /** Column name RequestDocumentNo */
    public static final String COLUMNNAME_RequestDocumentNo = "RequestDocumentNo";

	/** Set Request Document No.
	  * Adempiere Request Document No
	  */
	public void setRequestDocumentNo (String RequestDocumentNo);

	/** Get Request Document No.
	  * Adempiere Request Document No
	  */
	public String getRequestDocumentNo();

    /** Column name ResponseText */
    public static final String COLUMNNAME_ResponseText = "ResponseText";

	/** Set Response Text.
	  * Request Response Text
	  */
	public void setResponseText (String ResponseText);

	/** Get Response Text.
	  * Request Response Text
	  */
	public String getResponseText();

    /** Column name R_IssueKnown_ID */
    public static final String COLUMNNAME_R_IssueKnown_ID = "R_IssueKnown_ID";

	/** Set Known Issue.
	  * Known Issue
	  */
	public void setR_IssueKnown_ID (int R_IssueKnown_ID);

	/** Get Known Issue.
	  * Known Issue
	  */
	public int getR_IssueKnown_ID();

	public I_R_IssueKnown getR_IssueKnown() throws RuntimeException;

    /** Column name R_IssueProject_ID */
    public static final String COLUMNNAME_R_IssueProject_ID = "R_IssueProject_ID";

	/** Set Issue Project.
	  * Implementation Projects
	  */
	public void setR_IssueProject_ID (int R_IssueProject_ID);

	/** Get Issue Project.
	  * Implementation Projects
	  */
	public int getR_IssueProject_ID();

	public I_R_IssueProject getR_IssueProject() throws RuntimeException;

    /** Column name R_IssueSystem_ID */
    public static final String COLUMNNAME_R_IssueSystem_ID = "R_IssueSystem_ID";

	/** Set Issue System.
	  * System creating the issue
	  */
	public void setR_IssueSystem_ID (int R_IssueSystem_ID);

	/** Get Issue System.
	  * System creating the issue
	  */
	public int getR_IssueSystem_ID();

	public I_R_IssueSystem getR_IssueSystem() throws RuntimeException;

    /** Column name R_IssueUser_ID */
    public static final String COLUMNNAME_R_IssueUser_ID = "R_IssueUser_ID";

	/** Set IssueUser.
	  * User who reported issues
	  */
	public void setR_IssueUser_ID (int R_IssueUser_ID);

	/** Get IssueUser.
	  * User who reported issues
	  */
	public int getR_IssueUser_ID();

	public I_R_IssueUser getR_IssueUser() throws RuntimeException;

    /** Column name R_Request_ID */
    public static final String COLUMNNAME_R_Request_ID = "R_Request_ID";

	/** Set Request.
	  * Request from a Business Partner or Prospect
	  */
	public void setR_Request_ID (int R_Request_ID);

	/** Get Request.
	  * Request from a Business Partner or Prospect
	  */
	public int getR_Request_ID();

	public I_R_Request getR_Request() throws RuntimeException;

    /** Column name SourceClassName */
    public static final String COLUMNNAME_SourceClassName = "SourceClassName";

	/** Set Source Class.
	  * Source Class Name
	  */
	public void setSourceClassName (String SourceClassName);

	/** Get Source Class.
	  * Source Class Name
	  */
	public String getSourceClassName();

    /** Column name SourceMethodName */
    public static final String COLUMNNAME_SourceMethodName = "SourceMethodName";

	/** Set Source Method.
	  * Source Method Name
	  */
	public void setSourceMethodName (String SourceMethodName);

	/** Get Source Method.
	  * Source Method Name
	  */
	public String getSourceMethodName();

    /** Column name StackTrace */
    public static final String COLUMNNAME_StackTrace = "StackTrace";

	/** Set Stack Trace.
	  * System Log Trace
	  */
	public void setStackTrace (String StackTrace);

	/** Get Stack Trace.
	  * System Log Trace
	  */
	public String getStackTrace();

    /** Column name StatisticsInfo */
    public static final String COLUMNNAME_StatisticsInfo = "StatisticsInfo";

	/** Set Statistics.
	  * Information to help profiling the system for solving support issues
	  */
	public void setStatisticsInfo (String StatisticsInfo);

	/** Get Statistics.
	  * Information to help profiling the system for solving support issues
	  */
	public String getStatisticsInfo();

    /** Column name SupportEMail */
    public static final String COLUMNNAME_SupportEMail = "SupportEMail";

	/** Set Support EMail.
	  * EMail address to send support information and updates to
	  */
	public void setSupportEMail (String SupportEMail);

	/** Get Support EMail.
	  * EMail address to send support information and updates to
	  */
	public String getSupportEMail();

    /** Column name SystemStatus */
    public static final String COLUMNNAME_SystemStatus = "SystemStatus";

	/** Set System Status.
	  * Status of the system - Support priority depends on system status
	  */
	public void setSystemStatus (String SystemStatus);

	/** Get System Status.
	  * Status of the system - Support priority depends on system status
	  */
	public String getSystemStatus();

    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/** Get Updated.
	  * Date this record was updated
	  */
	public Timestamp getUpdated();

    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/** Get Updated By.
	  * User who updated this records
	  */
	public int getUpdatedBy();

    /** Column name UserName */
    public static final String COLUMNNAME_UserName = "UserName";

	/** Set Registered EMail.
	  * Email of the responsible for the System
	  */
	public void setUserName (String UserName);

	/** Get Registered EMail.
	  * Email of the responsible for the System
	  */
	public String getUserName();

    /** Column name Version */
    public static final String COLUMNNAME_Version = "Version";

	/** Set Version.
	  * Version of the table definition
	  */
	public void setVersion (String Version);

	/** Get Version.
	  * Version of the table definition
	  */
	public String getVersion();
}
