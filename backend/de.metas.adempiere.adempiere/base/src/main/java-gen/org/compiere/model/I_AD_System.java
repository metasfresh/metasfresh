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
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 4 - System
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(4);

    /** Load Meta Data */

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
    public static final org.adempiere.model.ModelColumn<I_AD_System, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_AD_System, org.compiere.model.I_AD_Client>(I_AD_System.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

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
    public static final org.adempiere.model.ModelColumn<I_AD_System, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_AD_System, org.compiere.model.I_AD_Org>(I_AD_System.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set System.
	 * System Definition
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_System_ID (int AD_System_ID);

	/**
	 * Get System.
	 * System Definition
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_System_ID();

    /** Column definition for AD_System_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_System, Object> COLUMN_AD_System_ID = new org.adempiere.model.ModelColumn<I_AD_System, Object>(I_AD_System.class, "AD_System_ID", null);
    /** Column name AD_System_ID */
    public static final String COLUMNNAME_AD_System_ID = "AD_System_ID";

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
    public static final org.adempiere.model.ModelColumn<I_AD_System, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_AD_System, Object>(I_AD_System.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_AD_System, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_AD_System, org.compiere.model.I_AD_User>(I_AD_System.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Custom Prefix.
	 * Prefix for Custom entities
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setCustomPrefix (java.lang.String CustomPrefix);

	/**
	 * Get Custom Prefix.
	 * Prefix for Custom entities
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getCustomPrefix();

    /** Column definition for CustomPrefix */
    public static final org.adempiere.model.ModelColumn<I_AD_System, Object> COLUMN_CustomPrefix = new org.adempiere.model.ModelColumn<I_AD_System, Object>(I_AD_System.class, "CustomPrefix", null);
    /** Column name CustomPrefix */
    public static final String COLUMNNAME_CustomPrefix = "CustomPrefix";

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
    public static final org.adempiere.model.ModelColumn<I_AD_System, Object> COLUMN_DBAddress = new org.adempiere.model.ModelColumn<I_AD_System, Object>(I_AD_System.class, "DBAddress", null);
    /** Column name DBAddress */
    public static final String COLUMNNAME_DBAddress = "DBAddress";

	/**
	 * Set Database Name.
	 * Database Name
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDBInstance (java.lang.String DBInstance);

	/**
	 * Get Database Name.
	 * Database Name
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDBInstance();

    /** Column definition for DBInstance */
    public static final org.adempiere.model.ModelColumn<I_AD_System, Object> COLUMN_DBInstance = new org.adempiere.model.ModelColumn<I_AD_System, Object>(I_AD_System.class, "DBInstance", null);
    /** Column name DBInstance */
    public static final String COLUMNNAME_DBInstance = "DBInstance";

	/**
	 * Set DBVersion.
	 * version of the SQL migration scripts that were last rolled out
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDBVersion (java.lang.String DBVersion);

	/**
	 * Get DBVersion.
	 * version of the SQL migration scripts that were last rolled out
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDBVersion();

    /** Column definition for DBVersion */
    public static final org.adempiere.model.ModelColumn<I_AD_System, Object> COLUMN_DBVersion = new org.adempiere.model.ModelColumn<I_AD_System, Object>(I_AD_System.class, "DBVersion", null);
    /** Column name DBVersion */
    public static final String COLUMNNAME_DBVersion = "DBVersion";

	/**
	 * Set Beschreibung.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDescription (java.lang.String Description);

	/**
	 * Get Beschreibung.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDescription();

    /** Column definition for Description */
    public static final org.adempiere.model.ModelColumn<I_AD_System, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<I_AD_System, Object>(I_AD_System.class, "Description", null);
    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/**
	 * Set Chiffrier-Schlüssel.
	 * Encryption Class used for securing data content
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setEncryptionKey (java.lang.String EncryptionKey);

	/**
	 * Get Chiffrier-Schlüssel.
	 * Encryption Class used for securing data content
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getEncryptionKey();

    /** Column definition for EncryptionKey */
    public static final org.adempiere.model.ModelColumn<I_AD_System, Object> COLUMN_EncryptionKey = new org.adempiere.model.ModelColumn<I_AD_System, Object>(I_AD_System.class, "EncryptionKey", null);
    /** Column name EncryptionKey */
    public static final String COLUMNNAME_EncryptionKey = "EncryptionKey";

	/**
	 * Set ID Range End.
	 * End if the ID Range used
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setIDRangeEnd (java.math.BigDecimal IDRangeEnd);

	/**
	 * Get ID Range End.
	 * End if the ID Range used
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getIDRangeEnd();

    /** Column definition for IDRangeEnd */
    public static final org.adempiere.model.ModelColumn<I_AD_System, Object> COLUMN_IDRangeEnd = new org.adempiere.model.ModelColumn<I_AD_System, Object>(I_AD_System.class, "IDRangeEnd", null);
    /** Column name IDRangeEnd */
    public static final String COLUMNNAME_IDRangeEnd = "IDRangeEnd";

	/**
	 * Set ID Range Start.
	 * Start of the ID Range used
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setIDRangeStart (java.math.BigDecimal IDRangeStart);

	/**
	 * Get ID Range Start.
	 * Start of the ID Range used
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getIDRangeStart();

    /** Column definition for IDRangeStart */
    public static final org.adempiere.model.ModelColumn<I_AD_System, Object> COLUMN_IDRangeStart = new org.adempiere.model.ModelColumn<I_AD_System, Object>(I_AD_System.class, "IDRangeStart", null);
    /** Column name IDRangeStart */
    public static final String COLUMNNAME_IDRangeStart = "IDRangeStart";

	/**
	 * Set Info.
	 * Information
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setInfo (java.lang.String Info);

	/**
	 * Get Info.
	 * Information
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getInfo();

    /** Column definition for Info */
    public static final org.adempiere.model.ModelColumn<I_AD_System, Object> COLUMN_Info = new org.adempiere.model.ModelColumn<I_AD_System, Object>(I_AD_System.class, "Info", null);
    /** Column name Info */
    public static final String COLUMNNAME_Info = "Info";

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
    public static final org.adempiere.model.ModelColumn<I_AD_System, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_AD_System, Object>(I_AD_System.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Maintain Statistics.
	 * Maintain general statistics
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsAllowStatistics (boolean IsAllowStatistics);

	/**
	 * Get Maintain Statistics.
	 * Maintain general statistics
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isAllowStatistics();

    /** Column definition for IsAllowStatistics */
    public static final org.adempiere.model.ModelColumn<I_AD_System, Object> COLUMN_IsAllowStatistics = new org.adempiere.model.ModelColumn<I_AD_System, Object>(I_AD_System.class, "IsAllowStatistics", null);
    /** Column name IsAllowStatistics */
    public static final String COLUMNNAME_IsAllowStatistics = "IsAllowStatistics";

	/**
	 * Set Error Reporting.
	 * Automatically report Errors
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsAutoErrorReport (boolean IsAutoErrorReport);

	/**
	 * Get Error Reporting.
	 * Automatically report Errors
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isAutoErrorReport();

    /** Column definition for IsAutoErrorReport */
    public static final org.adempiere.model.ModelColumn<I_AD_System, Object> COLUMN_IsAutoErrorReport = new org.adempiere.model.ModelColumn<I_AD_System, Object>(I_AD_System.class, "IsAutoErrorReport", null);
    /** Column name IsAutoErrorReport */
    public static final String COLUMNNAME_IsAutoErrorReport = "IsAutoErrorReport";

	/**
	 * Set Fail if Build Differ.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsFailOnBuildDiffer (boolean IsFailOnBuildDiffer);

	/**
	 * Get Fail if Build Differ.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isFailOnBuildDiffer();

    /** Column definition for IsFailOnBuildDiffer */
    public static final org.adempiere.model.ModelColumn<I_AD_System, Object> COLUMN_IsFailOnBuildDiffer = new org.adempiere.model.ModelColumn<I_AD_System, Object>(I_AD_System.class, "IsFailOnBuildDiffer", null);
    /** Column name IsFailOnBuildDiffer */
    public static final String COLUMNNAME_IsFailOnBuildDiffer = "IsFailOnBuildDiffer";

	/**
	 * Set Fail on Missing Model Validator.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsFailOnMissingModelValidator (boolean IsFailOnMissingModelValidator);

	/**
	 * Get Fail on Missing Model Validator.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isFailOnMissingModelValidator();

    /** Column definition for IsFailOnMissingModelValidator */
    public static final org.adempiere.model.ModelColumn<I_AD_System, Object> COLUMN_IsFailOnMissingModelValidator = new org.adempiere.model.ModelColumn<I_AD_System, Object>(I_AD_System.class, "IsFailOnMissingModelValidator", null);
    /** Column name IsFailOnMissingModelValidator */
    public static final String COLUMNNAME_IsFailOnMissingModelValidator = "IsFailOnMissingModelValidator";

	/**
	 * Set Just Migrated.
	 * Value set by Migration for post-Migation tasks.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setIsJustMigrated (boolean IsJustMigrated);

	/**
	 * Get Just Migrated.
	 * Value set by Migration for post-Migation tasks.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isJustMigrated();

    /** Column definition for IsJustMigrated */
    public static final org.adempiere.model.ModelColumn<I_AD_System, Object> COLUMN_IsJustMigrated = new org.adempiere.model.ModelColumn<I_AD_System, Object>(I_AD_System.class, "IsJustMigrated", null);
    /** Column name IsJustMigrated */
    public static final String COLUMNNAME_IsJustMigrated = "IsJustMigrated";

	/**
	 * Set Last Build Info.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setLastBuildInfo (java.lang.String LastBuildInfo);

	/**
	 * Get Last Build Info.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getLastBuildInfo();

    /** Column definition for LastBuildInfo */
    public static final org.adempiere.model.ModelColumn<I_AD_System, Object> COLUMN_LastBuildInfo = new org.adempiere.model.ModelColumn<I_AD_System, Object>(I_AD_System.class, "LastBuildInfo", null);
    /** Column name LastBuildInfo */
    public static final String COLUMNNAME_LastBuildInfo = "LastBuildInfo";

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
    public static final org.adempiere.model.ModelColumn<I_AD_System, Object> COLUMN_Name = new org.adempiere.model.ModelColumn<I_AD_System, Object>(I_AD_System.class, "Name", null);
    /** Column name Name */
    public static final String COLUMNNAME_Name = "Name";

	/**
	 * Set Processors.
	 * Number of Database Processors
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setNoProcessors (int NoProcessors);

	/**
	 * Get Processors.
	 * Number of Database Processors
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getNoProcessors();

    /** Column definition for NoProcessors */
    public static final org.adempiere.model.ModelColumn<I_AD_System, Object> COLUMN_NoProcessors = new org.adempiere.model.ModelColumn<I_AD_System, Object>(I_AD_System.class, "NoProcessors", null);
    /** Column name NoProcessors */
    public static final String COLUMNNAME_NoProcessors = "NoProcessors";

	/**
	 * Set Old Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setOldName (java.lang.String OldName);

	/**
	 * Get Old Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getOldName();

    /** Column definition for OldName */
    public static final org.adempiere.model.ModelColumn<I_AD_System, Object> COLUMN_OldName = new org.adempiere.model.ModelColumn<I_AD_System, Object>(I_AD_System.class, "OldName", null);
    /** Column name OldName */
    public static final String COLUMNNAME_OldName = "OldName";

	/**
	 * Set Kennwort.
	 * Password of any length (case sensitive)
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setPassword (java.lang.String Password);

	/**
	 * Get Kennwort.
	 * Password of any length (case sensitive)
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getPassword();

    /** Column definition for Password */
    public static final org.adempiere.model.ModelColumn<I_AD_System, Object> COLUMN_Password = new org.adempiere.model.ModelColumn<I_AD_System, Object>(I_AD_System.class, "Password", null);
    /** Column name Password */
    public static final String COLUMNNAME_Password = "Password";

	/**
	 * Set Verarbeiten.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setProcessing (boolean Processing);

	/**
	 * Get Verarbeiten.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isProcessing();

    /** Column definition for Processing */
    public static final org.adempiere.model.ModelColumn<I_AD_System, Object> COLUMN_Processing = new org.adempiere.model.ModelColumn<I_AD_System, Object>(I_AD_System.class, "Processing", null);
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
    public static final org.adempiere.model.ModelColumn<I_AD_System, Object> COLUMN_ProfileInfo = new org.adempiere.model.ModelColumn<I_AD_System, Object>(I_AD_System.class, "ProfileInfo", null);
    /** Column name ProfileInfo */
    public static final String COLUMNNAME_ProfileInfo = "ProfileInfo";

	/**
	 * Set Datensatz-ID.
	 * Direct internal record ID
	 *
	 * <br>Type: ID
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setRecord_ID (int Record_ID);

	/**
	 * Get Datensatz-ID.
	 * Direct internal record ID
	 *
	 * <br>Type: ID
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getRecord_ID();

    /** Column definition for Record_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_System, Object> COLUMN_Record_ID = new org.adempiere.model.ModelColumn<I_AD_System, Object>(I_AD_System.class, "Record_ID", null);
    /** Column name Record_ID */
    public static final String COLUMNNAME_Record_ID = "Record_ID";

	/**
	 * Set Release No.
	 * Internal Release Number
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setReleaseNo (java.lang.String ReleaseNo);

	/**
	 * Get Release No.
	 * Internal Release Number
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getReleaseNo();

    /** Column definition for ReleaseNo */
    public static final org.adempiere.model.ModelColumn<I_AD_System, Object> COLUMN_ReleaseNo = new org.adempiere.model.ModelColumn<I_AD_System, Object>(I_AD_System.class, "ReleaseNo", null);
    /** Column name ReleaseNo */
    public static final String COLUMNNAME_ReleaseNo = "ReleaseNo";

	/**
	 * Set Replication Type.
	 * Type of Data Replication
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setReplicationType (java.lang.String ReplicationType);

	/**
	 * Get Replication Type.
	 * Type of Data Replication
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getReplicationType();

    /** Column definition for ReplicationType */
    public static final org.adempiere.model.ModelColumn<I_AD_System, Object> COLUMN_ReplicationType = new org.adempiere.model.ModelColumn<I_AD_System, Object>(I_AD_System.class, "ReplicationType", null);
    /** Column name ReplicationType */
    public static final String COLUMNNAME_ReplicationType = "ReplicationType";

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
    public static final org.adempiere.model.ModelColumn<I_AD_System, Object> COLUMN_StatisticsInfo = new org.adempiere.model.ModelColumn<I_AD_System, Object>(I_AD_System.class, "StatisticsInfo", null);
    /** Column name StatisticsInfo */
    public static final String COLUMNNAME_StatisticsInfo = "StatisticsInfo";

	/**
	 * Set Zusammenfassung.
	 * Textual summary of this request
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setSummary (java.lang.String Summary);

	/**
	 * Get Zusammenfassung.
	 * Textual summary of this request
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getSummary();

    /** Column definition for Summary */
    public static final org.adempiere.model.ModelColumn<I_AD_System, Object> COLUMN_Summary = new org.adempiere.model.ModelColumn<I_AD_System, Object>(I_AD_System.class, "Summary", null);
    /** Column name Summary */
    public static final String COLUMNNAME_Summary = "Summary";

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
    public static final org.adempiere.model.ModelColumn<I_AD_System, Object> COLUMN_SupportEMail = new org.adempiere.model.ModelColumn<I_AD_System, Object>(I_AD_System.class, "SupportEMail", null);
    /** Column name SupportEMail */
    public static final String COLUMNNAME_SupportEMail = "SupportEMail";

	/**
	 * Set Support Expires.
	 * Date when the Adempiere support expires
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setSupportExpDate (java.sql.Timestamp SupportExpDate);

	/**
	 * Get Support Expires.
	 * Date when the Adempiere support expires
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getSupportExpDate();

    /** Column definition for SupportExpDate */
    public static final org.adempiere.model.ModelColumn<I_AD_System, Object> COLUMN_SupportExpDate = new org.adempiere.model.ModelColumn<I_AD_System, Object>(I_AD_System.class, "SupportExpDate", null);
    /** Column name SupportExpDate */
    public static final String COLUMNNAME_SupportExpDate = "SupportExpDate";

	/**
	 * Set Internal Users.
	 * Number of Internal Users for Adempiere Support
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setSupportUnits (int SupportUnits);

	/**
	 * Get Internal Users.
	 * Number of Internal Users for Adempiere Support
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getSupportUnits();

    /** Column definition for SupportUnits */
    public static final org.adempiere.model.ModelColumn<I_AD_System, Object> COLUMN_SupportUnits = new org.adempiere.model.ModelColumn<I_AD_System, Object>(I_AD_System.class, "SupportUnits", null);
    /** Column name SupportUnits */
    public static final String COLUMNNAME_SupportUnits = "SupportUnits";

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
    public static final org.adempiere.model.ModelColumn<I_AD_System, Object> COLUMN_SystemStatus = new org.adempiere.model.ModelColumn<I_AD_System, Object>(I_AD_System.class, "SystemStatus", null);
    /** Column name SystemStatus */
    public static final String COLUMNNAME_SystemStatus = "SystemStatus";

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
    public static final org.adempiere.model.ModelColumn<I_AD_System, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_AD_System, Object>(I_AD_System.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_AD_System, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_AD_System, org.compiere.model.I_AD_User>(I_AD_System.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/**
	 * Set Registered EMail.
	 * Email of the responsible for the System
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setUserName (java.lang.String UserName);

	/**
	 * Get Registered EMail.
	 * Email of the responsible for the System
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getUserName();

    /** Column definition for UserName */
    public static final org.adempiere.model.ModelColumn<I_AD_System, Object> COLUMN_UserName = new org.adempiere.model.ModelColumn<I_AD_System, Object>(I_AD_System.class, "UserName", null);
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
    public static final org.adempiere.model.ModelColumn<I_AD_System, Object> COLUMN_Version = new org.adempiere.model.ModelColumn<I_AD_System, Object>(I_AD_System.class, "Version", null);
    /** Column name Version */
    public static final String COLUMNNAME_Version = "Version";

	/**
	 * Set ZK WebUI URL.
	 * ZK WebUI root URL
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setWebUI_URL (java.lang.String WebUI_URL);

	/**
	 * Get ZK WebUI URL.
	 * ZK WebUI root URL
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getWebUI_URL();

    /** Column definition for WebUI_URL */
    public static final org.adempiere.model.ModelColumn<I_AD_System, Object> COLUMN_WebUI_URL = new org.adempiere.model.ModelColumn<I_AD_System, Object>(I_AD_System.class, "WebUI_URL", null);
    /** Column name WebUI_URL */
    public static final String COLUMNNAME_WebUI_URL = "WebUI_URL";
}
