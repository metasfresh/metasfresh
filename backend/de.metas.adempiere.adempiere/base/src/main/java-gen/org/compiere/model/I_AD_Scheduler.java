package org.compiere.model;


/** Generated Interface for AD_Scheduler
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_AD_Scheduler 
{

    /** TableName=AD_Scheduler */
    public static final String Table_Name = "AD_Scheduler";

    /** AD_Table_ID=688 */
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
    public static final org.adempiere.model.ModelColumn<I_AD_Scheduler, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_AD_Scheduler, org.compiere.model.I_AD_Client>(I_AD_Scheduler.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_AD_Scheduler, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_AD_Scheduler, org.compiere.model.I_AD_Org>(I_AD_Scheduler.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Prozess.
	 * Process or Report
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_Process_ID (int AD_Process_ID);

	/**
	 * Get Prozess.
	 * Process or Report
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_Process_ID();

	public org.compiere.model.I_AD_Process getAD_Process();

	public void setAD_Process(org.compiere.model.I_AD_Process AD_Process);

    /** Column definition for AD_Process_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_Scheduler, org.compiere.model.I_AD_Process> COLUMN_AD_Process_ID = new org.adempiere.model.ModelColumn<I_AD_Scheduler, org.compiere.model.I_AD_Process>(I_AD_Scheduler.class, "AD_Process_ID", org.compiere.model.I_AD_Process.class);
    /** Column name AD_Process_ID */
    public static final String COLUMNNAME_AD_Process_ID = "AD_Process_ID";

	/**
	 * Set Rolle.
	 * Responsibility Role
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_Role_ID (int AD_Role_ID);

	/**
	 * Get Rolle.
	 * Responsibility Role
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_Role_ID();

	public org.compiere.model.I_AD_Role getAD_Role();

	public void setAD_Role(org.compiere.model.I_AD_Role AD_Role);

    /** Column definition for AD_Role_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_Scheduler, org.compiere.model.I_AD_Role> COLUMN_AD_Role_ID = new org.adempiere.model.ModelColumn<I_AD_Scheduler, org.compiere.model.I_AD_Role>(I_AD_Scheduler.class, "AD_Role_ID", org.compiere.model.I_AD_Role.class);
    /** Column name AD_Role_ID */
    public static final String COLUMNNAME_AD_Role_ID = "AD_Role_ID";

	/**
	 * Set Ablaufsteuerung.
	 * Schedule Processes
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_Scheduler_ID (int AD_Scheduler_ID);

	/**
	 * Get Ablaufsteuerung.
	 * Schedule Processes
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Scheduler_ID();

    /** Column definition for AD_Scheduler_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_Scheduler, Object> COLUMN_AD_Scheduler_ID = new org.adempiere.model.ModelColumn<I_AD_Scheduler, Object>(I_AD_Scheduler.class, "AD_Scheduler_ID", null);
    /** Column name AD_Scheduler_ID */
    public static final String COLUMNNAME_AD_Scheduler_ID = "AD_Scheduler_ID";

	/**
	 * Set Externer Prozess.
	 * Ausserhalb von ADempiere zu startender Prozess
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_Task_ID (int AD_Task_ID);

	/**
	 * Get Externer Prozess.
	 * Ausserhalb von ADempiere zu startender Prozess
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_Task_ID();

	public org.compiere.model.I_AD_Task getAD_Task();

	public void setAD_Task(org.compiere.model.I_AD_Task AD_Task);

    /** Column definition for AD_Task_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_Scheduler, org.compiere.model.I_AD_Task> COLUMN_AD_Task_ID = new org.adempiere.model.ModelColumn<I_AD_Scheduler, org.compiere.model.I_AD_Task>(I_AD_Scheduler.class, "AD_Task_ID", org.compiere.model.I_AD_Task.class);
    /** Column name AD_Task_ID */
    public static final String COLUMNNAME_AD_Task_ID = "AD_Task_ID";

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
    public static final org.adempiere.model.ModelColumn<I_AD_Scheduler, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_AD_Scheduler, Object>(I_AD_Scheduler.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_AD_Scheduler, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_AD_Scheduler, org.compiere.model.I_AD_User>(I_AD_Scheduler.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Cron-Zeitplan.
	 * Cron pattern to define when the process should be invoked.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setCronPattern (java.lang.String CronPattern);

	/**
	 * Get Cron-Zeitplan.
	 * Cron pattern to define when the process should be invoked.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getCronPattern();

    /** Column definition for CronPattern */
    public static final org.adempiere.model.ModelColumn<I_AD_Scheduler, Object> COLUMN_CronPattern = new org.adempiere.model.ModelColumn<I_AD_Scheduler, Object>(I_AD_Scheduler.class, "CronPattern", null);
    /** Column name CronPattern */
    public static final String COLUMNNAME_CronPattern = "CronPattern";

	/**
	 * Set Tag letzter Lauf.
	 * Date the process was last run.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDateLastRun (java.sql.Timestamp DateLastRun);

	/**
	 * Get Tag letzter Lauf.
	 * Date the process was last run.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getDateLastRun();

    /** Column definition for DateLastRun */
    public static final org.adempiere.model.ModelColumn<I_AD_Scheduler, Object> COLUMN_DateLastRun = new org.adempiere.model.ModelColumn<I_AD_Scheduler, Object>(I_AD_Scheduler.class, "DateLastRun", null);
    /** Column name DateLastRun */
    public static final String COLUMNNAME_DateLastRun = "DateLastRun";

	/**
	 * Set Tag nächster Lauf.
	 * Date the process will run next
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDateNextRun (java.sql.Timestamp DateNextRun);

	/**
	 * Get Tag nächster Lauf.
	 * Date the process will run next
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getDateNextRun();

    /** Column definition for DateNextRun */
    public static final org.adempiere.model.ModelColumn<I_AD_Scheduler, Object> COLUMN_DateNextRun = new org.adempiere.model.ModelColumn<I_AD_Scheduler, Object>(I_AD_Scheduler.class, "DateNextRun", null);
    /** Column name DateNextRun */
    public static final String COLUMNNAME_DateNextRun = "DateNextRun";

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
    public static final org.adempiere.model.ModelColumn<I_AD_Scheduler, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<I_AD_Scheduler, Object>(I_AD_Scheduler.class, "Description", null);
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
    public static final org.adempiere.model.ModelColumn<I_AD_Scheduler, Object> COLUMN_EntityType = new org.adempiere.model.ModelColumn<I_AD_Scheduler, Object>(I_AD_Scheduler.class, "EntityType", null);
    /** Column name EntityType */
    public static final String COLUMNNAME_EntityType = "EntityType";

	/**
	 * Set Häufigkeit.
	 * Frequency of events
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setFrequency (int Frequency);

	/**
	 * Get Häufigkeit.
	 * Frequency of events
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getFrequency();

    /** Column definition for Frequency */
    public static final org.adempiere.model.ModelColumn<I_AD_Scheduler, Object> COLUMN_Frequency = new org.adempiere.model.ModelColumn<I_AD_Scheduler, Object>(I_AD_Scheduler.class, "Frequency", null);
    /** Column name Frequency */
    public static final String COLUMNNAME_Frequency = "Frequency";

	/**
	 * Set Häufigkeitsart.
	 * Frequency of event
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setFrequencyType (java.lang.String FrequencyType);

	/**
	 * Get Häufigkeitsart.
	 * Frequency of event
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getFrequencyType();

    /** Column definition for FrequencyType */
    public static final org.adempiere.model.ModelColumn<I_AD_Scheduler, Object> COLUMN_FrequencyType = new org.adempiere.model.ModelColumn<I_AD_Scheduler, Object>(I_AD_Scheduler.class, "FrequencyType", null);
    /** Column name FrequencyType */
    public static final String COLUMNNAME_FrequencyType = "FrequencyType";

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
    public static final org.adempiere.model.ModelColumn<I_AD_Scheduler, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_AD_Scheduler, Object>(I_AD_Scheduler.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Ignore Processing Time.
	 * Do not include processing time for the DateNextRun calculation
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setIsIgnoreProcessingTime (boolean IsIgnoreProcessingTime);

	/**
	 * Get Ignore Processing Time.
	 * Do not include processing time for the DateNextRun calculation
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isIgnoreProcessingTime();

    /** Column definition for IsIgnoreProcessingTime */
    public static final org.adempiere.model.ModelColumn<I_AD_Scheduler, Object> COLUMN_IsIgnoreProcessingTime = new org.adempiere.model.ModelColumn<I_AD_Scheduler, Object>(I_AD_Scheduler.class, "IsIgnoreProcessingTime", null);
    /** Column name IsIgnoreProcessingTime */
    public static final String COLUMNNAME_IsIgnoreProcessingTime = "IsIgnoreProcessingTime";

	/**
	 * Set Tage Protokoll aufheben.
	 * Number of days to keep the log entries
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setKeepLogDays (int KeepLogDays);

	/**
	 * Get Tage Protokoll aufheben.
	 * Number of days to keep the log entries
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getKeepLogDays();

    /** Column definition for KeepLogDays */
    public static final org.adempiere.model.ModelColumn<I_AD_Scheduler, Object> COLUMN_KeepLogDays = new org.adempiere.model.ModelColumn<I_AD_Scheduler, Object>(I_AD_Scheduler.class, "KeepLogDays", null);
    /** Column name KeepLogDays */
    public static final String COLUMNNAME_KeepLogDays = "KeepLogDays";

	/**
	 * Set Dienst-Verwaltung.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setManageScheduler (java.lang.String ManageScheduler);

	/**
	 * Get Dienst-Verwaltung.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getManageScheduler();

    /** Column definition for ManageScheduler */
    public static final org.adempiere.model.ModelColumn<I_AD_Scheduler, Object> COLUMN_ManageScheduler = new org.adempiere.model.ModelColumn<I_AD_Scheduler, Object>(I_AD_Scheduler.class, "ManageScheduler", null);
    /** Column name ManageScheduler */
    public static final String COLUMNNAME_ManageScheduler = "ManageScheduler";

	/**
	 * Set Day of the Month.
	 * Day of the month 1 to 28/29/30/31
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setMonthDay (int MonthDay);

	/**
	 * Get Day of the Month.
	 * Day of the month 1 to 28/29/30/31
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getMonthDay();

    /** Column definition for MonthDay */
    public static final org.adempiere.model.ModelColumn<I_AD_Scheduler, Object> COLUMN_MonthDay = new org.adempiere.model.ModelColumn<I_AD_Scheduler, Object>(I_AD_Scheduler.class, "MonthDay", null);
    /** Column name MonthDay */
    public static final String COLUMNNAME_MonthDay = "MonthDay";

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
    public static final org.adempiere.model.ModelColumn<I_AD_Scheduler, Object> COLUMN_Name = new org.adempiere.model.ModelColumn<I_AD_Scheduler, Object>(I_AD_Scheduler.class, "Name", null);
    /** Column name Name */
    public static final String COLUMNNAME_Name = "Name";

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
    public static final org.adempiere.model.ModelColumn<I_AD_Scheduler, Object> COLUMN_Processing = new org.adempiere.model.ModelColumn<I_AD_Scheduler, Object>(I_AD_Scheduler.class, "Processing", null);
    /** Column name Processing */
    public static final String COLUMNNAME_Processing = "Processing";

	/**
	 * Set Prozess-Art.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setSchedulerProcessType (java.lang.String SchedulerProcessType);

	/**
	 * Get Prozess-Art.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getSchedulerProcessType();

    /** Column definition for SchedulerProcessType */
    public static final org.adempiere.model.ModelColumn<I_AD_Scheduler, Object> COLUMN_SchedulerProcessType = new org.adempiere.model.ModelColumn<I_AD_Scheduler, Object>(I_AD_Scheduler.class, "SchedulerProcessType", null);
    /** Column name SchedulerProcessType */
    public static final String COLUMNNAME_SchedulerProcessType = "SchedulerProcessType";

	/**
	 * Set Planungs-Art.
	 * Type of schedule
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setScheduleType (java.lang.String ScheduleType);

	/**
	 * Get Planungs-Art.
	 * Type of schedule
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getScheduleType();

    /** Column definition for ScheduleType */
    public static final org.adempiere.model.ModelColumn<I_AD_Scheduler, Object> COLUMN_ScheduleType = new org.adempiere.model.ModelColumn<I_AD_Scheduler, Object>(I_AD_Scheduler.class, "ScheduleType", null);
    /** Column name ScheduleType */
    public static final String COLUMNNAME_ScheduleType = "ScheduleType";

	/**
	 * Set Status.
	 * Status of the currently running check
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setStatus (java.lang.String Status);

	/**
	 * Get Status.
	 * Status of the currently running check
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getStatus();

    /** Column definition for Status */
    public static final org.adempiere.model.ModelColumn<I_AD_Scheduler, Object> COLUMN_Status = new org.adempiere.model.ModelColumn<I_AD_Scheduler, Object>(I_AD_Scheduler.class, "Status", null);
    /** Column name Status */
    public static final String COLUMNNAME_Status = "Status";

	/**
	 * Set Vorgesetzter.
	 * Supervisor for this user/organization - used for escalation and approval
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setSupervisor_ID (int Supervisor_ID);

	/**
	 * Get Vorgesetzter.
	 * Supervisor for this user/organization - used for escalation and approval
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getSupervisor_ID();

	public org.compiere.model.I_AD_User getSupervisor();

	public void setSupervisor(org.compiere.model.I_AD_User Supervisor);

    /** Column definition for Supervisor_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_Scheduler, org.compiere.model.I_AD_User> COLUMN_Supervisor_ID = new org.adempiere.model.ModelColumn<I_AD_Scheduler, org.compiere.model.I_AD_User>(I_AD_Scheduler.class, "Supervisor_ID", org.compiere.model.I_AD_User.class);
    /** Column name Supervisor_ID */
    public static final String COLUMNNAME_Supervisor_ID = "Supervisor_ID";

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
    public static final org.adempiere.model.ModelColumn<I_AD_Scheduler, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_AD_Scheduler, Object>(I_AD_Scheduler.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_AD_Scheduler, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_AD_Scheduler, org.compiere.model.I_AD_User>(I_AD_Scheduler.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/**
	 * Set Day of the Week.
	 * Day of the Week
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setWeekDay (java.lang.String WeekDay);

	/**
	 * Get Day of the Week.
	 * Day of the Week
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getWeekDay();

    /** Column definition for WeekDay */
    public static final org.adempiere.model.ModelColumn<I_AD_Scheduler, Object> COLUMN_WeekDay = new org.adempiere.model.ModelColumn<I_AD_Scheduler, Object>(I_AD_Scheduler.class, "WeekDay", null);
    /** Column name WeekDay */
    public static final String COLUMNNAME_WeekDay = "WeekDay";
}
