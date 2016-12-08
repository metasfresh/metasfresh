package org.compiere.model;


/** Generated Interface for AD_SchedulerLog
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_AD_SchedulerLog 
{

    /** TableName=AD_SchedulerLog */
    public static final String Table_Name = "AD_SchedulerLog";

    /** AD_Table_ID=687 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 6 - System - Client
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(6);

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
    public static final org.adempiere.model.ModelColumn<I_AD_SchedulerLog, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_AD_SchedulerLog, org.compiere.model.I_AD_Client>(I_AD_SchedulerLog.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_AD_SchedulerLog, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_AD_SchedulerLog, org.compiere.model.I_AD_Org>(I_AD_SchedulerLog.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Prozess-Instanz.
	 * Instanz eines Prozesses
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_PInstance_ID (int AD_PInstance_ID);

	/**
	 * Get Prozess-Instanz.
	 * Instanz eines Prozesses
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_PInstance_ID();

	public org.compiere.model.I_AD_PInstance getAD_PInstance();

	public void setAD_PInstance(org.compiere.model.I_AD_PInstance AD_PInstance);

    /** Column definition for AD_PInstance_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_SchedulerLog, org.compiere.model.I_AD_PInstance> COLUMN_AD_PInstance_ID = new org.adempiere.model.ModelColumn<I_AD_SchedulerLog, org.compiere.model.I_AD_PInstance>(I_AD_SchedulerLog.class, "AD_PInstance_ID", org.compiere.model.I_AD_PInstance.class);
    /** Column name AD_PInstance_ID */
    public static final String COLUMNNAME_AD_PInstance_ID = "AD_PInstance_ID";

	/**
	 * Set Ablaufsteuerung.
	 * Schedule Processes
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_Scheduler_ID (int AD_Scheduler_ID);

	/**
	 * Get Ablaufsteuerung.
	 * Schedule Processes
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Scheduler_ID();

	public org.compiere.model.I_AD_Scheduler getAD_Scheduler();

	public void setAD_Scheduler(org.compiere.model.I_AD_Scheduler AD_Scheduler);

    /** Column definition for AD_Scheduler_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_SchedulerLog, org.compiere.model.I_AD_Scheduler> COLUMN_AD_Scheduler_ID = new org.adempiere.model.ModelColumn<I_AD_SchedulerLog, org.compiere.model.I_AD_Scheduler>(I_AD_SchedulerLog.class, "AD_Scheduler_ID", org.compiere.model.I_AD_Scheduler.class);
    /** Column name AD_Scheduler_ID */
    public static final String COLUMNNAME_AD_Scheduler_ID = "AD_Scheduler_ID";

	/**
	 * Set Ablauf-Protokoll.
	 * Result of the execution of the Scheduler
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_SchedulerLog_ID (int AD_SchedulerLog_ID);

	/**
	 * Get Ablauf-Protokoll.
	 * Result of the execution of the Scheduler
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_SchedulerLog_ID();

    /** Column definition for AD_SchedulerLog_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_SchedulerLog, Object> COLUMN_AD_SchedulerLog_ID = new org.adempiere.model.ModelColumn<I_AD_SchedulerLog, Object>(I_AD_SchedulerLog.class, "AD_SchedulerLog_ID", null);
    /** Column name AD_SchedulerLog_ID */
    public static final String COLUMNNAME_AD_SchedulerLog_ID = "AD_SchedulerLog_ID";

	/**
	 * Set Binärwert.
	 * Binary Data
	 *
	 * <br>Type: Binary
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setBinaryData (byte[] BinaryData);

	/**
	 * Get Binärwert.
	 * Binary Data
	 *
	 * <br>Type: Binary
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public byte[] getBinaryData();

    /** Column definition for BinaryData */
    public static final org.adempiere.model.ModelColumn<I_AD_SchedulerLog, Object> COLUMN_BinaryData = new org.adempiere.model.ModelColumn<I_AD_SchedulerLog, Object>(I_AD_SchedulerLog.class, "BinaryData", null);
    /** Column name BinaryData */
    public static final String COLUMNNAME_BinaryData = "BinaryData";

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
    public static final org.adempiere.model.ModelColumn<I_AD_SchedulerLog, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_AD_SchedulerLog, Object>(I_AD_SchedulerLog.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_AD_SchedulerLog, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_AD_SchedulerLog, org.compiere.model.I_AD_User>(I_AD_SchedulerLog.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

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
    public static final org.adempiere.model.ModelColumn<I_AD_SchedulerLog, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<I_AD_SchedulerLog, Object>(I_AD_SchedulerLog.class, "Description", null);
    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

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
    public static final org.adempiere.model.ModelColumn<I_AD_SchedulerLog, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_AD_SchedulerLog, Object>(I_AD_SchedulerLog.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Fehler.
	 * An Error occured in the execution
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsError (boolean IsError);

	/**
	 * Get Fehler.
	 * An Error occured in the execution
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isError();

    /** Column definition for IsError */
    public static final org.adempiere.model.ModelColumn<I_AD_SchedulerLog, Object> COLUMN_IsError = new org.adempiere.model.ModelColumn<I_AD_SchedulerLog, Object>(I_AD_SchedulerLog.class, "IsError", null);
    /** Column name IsError */
    public static final String COLUMNNAME_IsError = "IsError";

	/**
	 * Set Referenz.
	 * Reference for this record
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setReference (java.lang.String Reference);

	/**
	 * Get Referenz.
	 * Reference for this record
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getReference();

    /** Column definition for Reference */
    public static final org.adempiere.model.ModelColumn<I_AD_SchedulerLog, Object> COLUMN_Reference = new org.adempiere.model.ModelColumn<I_AD_SchedulerLog, Object>(I_AD_SchedulerLog.class, "Reference", null);
    /** Column name Reference */
    public static final String COLUMNNAME_Reference = "Reference";

	/**
	 * Set Zusammenfassung.
	 * Textual summary of this request
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setSummary (java.lang.String Summary);

	/**
	 * Get Zusammenfassung.
	 * Textual summary of this request
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getSummary();

    /** Column definition for Summary */
    public static final org.adempiere.model.ModelColumn<I_AD_SchedulerLog, Object> COLUMN_Summary = new org.adempiere.model.ModelColumn<I_AD_SchedulerLog, Object>(I_AD_SchedulerLog.class, "Summary", null);
    /** Column name Summary */
    public static final String COLUMNNAME_Summary = "Summary";

	/**
	 * Set Mitteilung.
	 * Text Message
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setTextMsg (java.lang.String TextMsg);

	/**
	 * Get Mitteilung.
	 * Text Message
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getTextMsg();

    /** Column definition for TextMsg */
    public static final org.adempiere.model.ModelColumn<I_AD_SchedulerLog, Object> COLUMN_TextMsg = new org.adempiere.model.ModelColumn<I_AD_SchedulerLog, Object>(I_AD_SchedulerLog.class, "TextMsg", null);
    /** Column name TextMsg */
    public static final String COLUMNNAME_TextMsg = "TextMsg";

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
    public static final org.adempiere.model.ModelColumn<I_AD_SchedulerLog, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_AD_SchedulerLog, Object>(I_AD_SchedulerLog.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_AD_SchedulerLog, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_AD_SchedulerLog, org.compiere.model.I_AD_User>(I_AD_SchedulerLog.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
