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


/** Generated Interface for AD_System
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_AD_System 
{

    /** TableName=AD_System */
    public static final String Table_Name = "AD_System";

    /** AD_Table_ID=531 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 4 - System 
     */
    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(4);

    /** Load Meta Data */

    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/** Get Mandant.
	  * Client/Tenant for this installation.
	  */
	public int getAD_Client_ID();

	public org.compiere.model.I_AD_Client getAD_Client() throws RuntimeException;

    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/** Set Sektion.
	  * Organisatorische Einheit des Mandanten
	  */
	public void setAD_Org_ID (int AD_Org_ID);

	/** Get Sektion.
	  * Organisatorische Einheit des Mandanten
	  */
	public int getAD_Org_ID();

	public org.compiere.model.I_AD_Org getAD_Org() throws RuntimeException;

	public void setAD_Org(org.compiere.model.I_AD_Org AD_Org);

    /** Column name AD_System_ID */
    public static final String COLUMNNAME_AD_System_ID = "AD_System_ID";

	/** Set System.
	  * System Definition
	  */
	public void setAD_System_ID (int AD_System_ID);

	/** Get System.
	  * System Definition
	  */
	public int getAD_System_ID();

    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/** Get Erstellt.
	  * Date this record was created
	  */
	public java.sql.Timestamp getCreated();

    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/** Get Erstellt durch.
	  * User who created this records
	  */
	public int getCreatedBy();

    /** Column name CustomPrefix */
    public static final String COLUMNNAME_CustomPrefix = "CustomPrefix";

	/** Set Custom Prefix.
	  * Prefix for Custom entities
	  */
	public void setCustomPrefix (java.lang.String CustomPrefix);

	/** Get Custom Prefix.
	  * Prefix for Custom entities
	  */
	public java.lang.String getCustomPrefix();

    /** Column name DBAddress */
    public static final String COLUMNNAME_DBAddress = "DBAddress";

	/** Set DB Address.
	  * JDBC URL of the database server
	  */
	public void setDBAddress (java.lang.String DBAddress);

	/** Get DB Address.
	  * JDBC URL of the database server
	  */
	public java.lang.String getDBAddress();

    /** Column name DBInstance */
    public static final String COLUMNNAME_DBInstance = "DBInstance";

	/** Set Database Name.
	  * Database Name
	  */
	public void setDBInstance (java.lang.String DBInstance);

	/** Get Database Name.
	  * Database Name
	  */
	public java.lang.String getDBInstance();

    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/** Set Beschreibung	  */
	public void setDescription (java.lang.String Description);

	/** Get Beschreibung	  */
	public java.lang.String getDescription();

    /** Column name EncryptionKey */
    public static final String COLUMNNAME_EncryptionKey = "EncryptionKey";

	/** Set Chiffrier-Schlüssel.
	  * Encryption Class used for securing data content
	  */
	public void setEncryptionKey (java.lang.String EncryptionKey);

	/** Get Chiffrier-Schlüssel.
	  * Encryption Class used for securing data content
	  */
	public java.lang.String getEncryptionKey();

    /** Column name IDRangeEnd */
    public static final String COLUMNNAME_IDRangeEnd = "IDRangeEnd";

	/** Set ID Range End.
	  * End if the ID Range used
	  */
	public void setIDRangeEnd (java.math.BigDecimal IDRangeEnd);

	/** Get ID Range End.
	  * End if the ID Range used
	  */
	public java.math.BigDecimal getIDRangeEnd();

    /** Column name IDRangeStart */
    public static final String COLUMNNAME_IDRangeStart = "IDRangeStart";

	/** Set ID Range Start.
	  * Start of the ID Range used
	  */
	public void setIDRangeStart (java.math.BigDecimal IDRangeStart);

	/** Get ID Range Start.
	  * Start of the ID Range used
	  */
	public java.math.BigDecimal getIDRangeStart();

    /** Column name Info */
    public static final String COLUMNNAME_Info = "Info";

	/** Set Info.
	  * Information
	  */
	public void setInfo (java.lang.String Info);

	/** Get Info.
	  * Information
	  */
	public java.lang.String getInfo();

    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/** Set Aktiv.
	  * The record is active in the system
	  */
	public void setIsActive (boolean IsActive);

	/** Get Aktiv.
	  * The record is active in the system
	  */
	public boolean isActive();

    /** Column name IsAllowStatistics */
    public static final String COLUMNNAME_IsAllowStatistics = "IsAllowStatistics";

	/** Set Maintain Statistics.
	  * Maintain general statistics
	  */
	public void setIsAllowStatistics (boolean IsAllowStatistics);

	/** Get Maintain Statistics.
	  * Maintain general statistics
	  */
	public boolean isAllowStatistics();

    /** Column name IsAutoErrorReport */
    public static final String COLUMNNAME_IsAutoErrorReport = "IsAutoErrorReport";

	/** Set Error Reporting.
	  * Automatically report Errors
	  */
	public void setIsAutoErrorReport (boolean IsAutoErrorReport);

	/** Get Error Reporting.
	  * Automatically report Errors
	  */
	public boolean isAutoErrorReport();

    /** Column name IsFailOnBuildDiffer */
    public static final String COLUMNNAME_IsFailOnBuildDiffer = "IsFailOnBuildDiffer";

	/** Set Fail if Build Differ	  */
	public void setIsFailOnBuildDiffer (boolean IsFailOnBuildDiffer);

	/** Get Fail if Build Differ	  */
	public boolean isFailOnBuildDiffer();

    /** Column name IsFailOnMissingModelValidator */
    public static final String COLUMNNAME_IsFailOnMissingModelValidator = "IsFailOnMissingModelValidator";

	/** Set Fail on Missing Model Validator	  */
	public void setIsFailOnMissingModelValidator (boolean IsFailOnMissingModelValidator);

	/** Get Fail on Missing Model Validator	  */
	public boolean isFailOnMissingModelValidator();

    /** Column name IsJustMigrated */
    public static final String COLUMNNAME_IsJustMigrated = "IsJustMigrated";

	/** Set Just Migrated.
	  * Value set by Migration for post-Migation tasks.
	  */
	public void setIsJustMigrated (boolean IsJustMigrated);

	/** Get Just Migrated.
	  * Value set by Migration for post-Migation tasks.
	  */
	public boolean isJustMigrated();

    /** Column name LastBuildInfo */
    public static final String COLUMNNAME_LastBuildInfo = "LastBuildInfo";

	/** Set Last Build Info	  */
	public void setLastBuildInfo (java.lang.String LastBuildInfo);

	/** Get Last Build Info	  */
	public java.lang.String getLastBuildInfo();

    /** Column name Name */
    public static final String COLUMNNAME_Name = "Name";

	/** Set Name.
	  * Alphanumeric identifier of the entity
	  */
	public void setName (java.lang.String Name);

	/** Get Name.
	  * Alphanumeric identifier of the entity
	  */
	public java.lang.String getName();

    /** Column name NoProcessors */
    public static final String COLUMNNAME_NoProcessors = "NoProcessors";

	/** Set Processors.
	  * Number of Database Processors
	  */
	public void setNoProcessors (int NoProcessors);

	/** Get Processors.
	  * Number of Database Processors
	  */
	public int getNoProcessors();

    /** Column name OldName */
    public static final String COLUMNNAME_OldName = "OldName";

	/** Set Old Name	  */
	public void setOldName (java.lang.String OldName);

	/** Get Old Name	  */
	public java.lang.String getOldName();

    /** Column name Password */
    public static final String COLUMNNAME_Password = "Password";

	/** Set Kennwort.
	  * Password of any length (case sensitive)
	  */
	public void setPassword (java.lang.String Password);

	/** Get Kennwort.
	  * Password of any length (case sensitive)
	  */
	public java.lang.String getPassword();

    /** Column name Processing */
    public static final String COLUMNNAME_Processing = "Processing";

	/** Set Verarbeiten	  */
	public void setProcessing (boolean Processing);

	/** Get Verarbeiten	  */
	public boolean isProcessing();

    /** Column name ProfileInfo */
    public static final String COLUMNNAME_ProfileInfo = "ProfileInfo";

	/** Set Profile.
	  * Information to help profiling the system for solving support issues
	  */
	public void setProfileInfo (java.lang.String ProfileInfo);

	/** Get Profile.
	  * Information to help profiling the system for solving support issues
	  */
	public java.lang.String getProfileInfo();

    /** Column name Record_ID */
    public static final String COLUMNNAME_Record_ID = "Record_ID";

	/** Set Datensatz-ID.
	  * Direct internal record ID
	  */
	public void setRecord_ID (int Record_ID);

	/** Get Datensatz-ID.
	  * Direct internal record ID
	  */
	public int getRecord_ID();

    /** Column name ReleaseNo */
    public static final String COLUMNNAME_ReleaseNo = "ReleaseNo";

	/** Set Release No.
	  * Internal Release Number
	  */
	public void setReleaseNo (java.lang.String ReleaseNo);

	/** Get Release No.
	  * Internal Release Number
	  */
	public java.lang.String getReleaseNo();

    /** Column name ReplicationType */
    public static final String COLUMNNAME_ReplicationType = "ReplicationType";

	/** Set Replication Type.
	  * Type of Data Replication
	  */
	public void setReplicationType (java.lang.String ReplicationType);

	/** Get Replication Type.
	  * Type of Data Replication
	  */
	public java.lang.String getReplicationType();

    /** Column name StatisticsInfo */
    public static final String COLUMNNAME_StatisticsInfo = "StatisticsInfo";

	/** Set Statistik.
	  * Information to help profiling the system for solving support issues
	  */
	public void setStatisticsInfo (java.lang.String StatisticsInfo);

	/** Get Statistik.
	  * Information to help profiling the system for solving support issues
	  */
	public java.lang.String getStatisticsInfo();

    /** Column name Summary */
    public static final String COLUMNNAME_Summary = "Summary";

	/** Set Zusammenfassung.
	  * Textual summary of this request
	  */
	public void setSummary (java.lang.String Summary);

	/** Get Zusammenfassung.
	  * Textual summary of this request
	  */
	public java.lang.String getSummary();

    /** Column name SupportEMail */
    public static final String COLUMNNAME_SupportEMail = "SupportEMail";

	/** Set Support EMail.
	  * EMail address to send support information and updates to
	  */
	public void setSupportEMail (java.lang.String SupportEMail);

	/** Get Support EMail.
	  * EMail address to send support information and updates to
	  */
	public java.lang.String getSupportEMail();

    /** Column name SupportExpDate */
    public static final String COLUMNNAME_SupportExpDate = "SupportExpDate";

	/** Set Support Expires.
	  * Date when the Adempiere support expires
	  */
	public void setSupportExpDate (java.sql.Timestamp SupportExpDate);

	/** Get Support Expires.
	  * Date when the Adempiere support expires
	  */
	public java.sql.Timestamp getSupportExpDate();

    /** Column name SupportUnits */
    public static final String COLUMNNAME_SupportUnits = "SupportUnits";

	/** Set Internal Users.
	  * Number of Internal Users for Adempiere Support
	  */
	public void setSupportUnits (int SupportUnits);

	/** Get Internal Users.
	  * Number of Internal Users for Adempiere Support
	  */
	public int getSupportUnits();

    /** Column name SystemStatus */
    public static final String COLUMNNAME_SystemStatus = "SystemStatus";

	/** Set System Status.
	  * Status of the system - Support priority depends on system status
	  */
	public void setSystemStatus (java.lang.String SystemStatus);

	/** Get System Status.
	  * Status of the system - Support priority depends on system status
	  */
	public java.lang.String getSystemStatus();

    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/** Get Aktualisiert.
	  * Date this record was updated
	  */
	public java.sql.Timestamp getUpdated();

    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/** Get Aktualisiert durch.
	  * User who updated this records
	  */
	public int getUpdatedBy();

    /** Column name UserName */
    public static final String COLUMNNAME_UserName = "UserName";

	/** Set Registered EMail.
	  * Email of the responsible for the System
	  */
	public void setUserName (java.lang.String UserName);

	/** Get Registered EMail.
	  * Email of the responsible for the System
	  */
	public java.lang.String getUserName();

    /** Column name Version */
    public static final String COLUMNNAME_Version = "Version";

	/** Set Version.
	  * Version of the table definition
	  */
	public void setVersion (java.lang.String Version);

	/** Get Version.
	  * Version of the table definition
	  */
	public java.lang.String getVersion();

    /** Column name WebUI_URL */
    public static final String COLUMNNAME_WebUI_URL = "WebUI_URL";

	/** Set ZK WebUI URL.
	  * ZK WebUI root URL
	  */
	public void setWebUI_URL (java.lang.String WebUI_URL);

	/** Get ZK WebUI URL.
	  * ZK WebUI root URL
	  */
	public java.lang.String getWebUI_URL();
}
