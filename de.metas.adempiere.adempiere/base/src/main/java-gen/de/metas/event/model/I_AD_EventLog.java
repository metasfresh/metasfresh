package de.metas.event.model;

import org.compiere.model.I_AD_Client;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_AD_User;

/** Generated Interface for AD_EventLog
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_AD_EventLog 
{

    /** TableName=AD_EventLog */
    public static final String Table_Name = "AD_EventLog";

    /** AD_Table_ID=540888 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 4 - System
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(4);

    /** Load Meta Data */

	/**
	 * Get Mandant.
	 * Mandant für diese Installation.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Client_ID();

	public org.compiere.model.I_AD_Client getAD_Client();

    /** Column definition for AD_Client_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_EventLog, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_AD_EventLog, org.compiere.model.I_AD_Client>(I_AD_EventLog.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Event log.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_EventLog_ID (int AD_EventLog_ID);

	/**
	 * Get Event log.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_EventLog_ID();

    /** Column definition for AD_EventLog_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_EventLog, Object> COLUMN_AD_EventLog_ID = new org.adempiere.model.ModelColumn<I_AD_EventLog, Object>(I_AD_EventLog.class, "AD_EventLog_ID", null);
    /** Column name AD_EventLog_ID */
    public static final String COLUMNNAME_AD_EventLog_ID = "AD_EventLog_ID";

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
    public static final org.adempiere.model.ModelColumn<I_AD_EventLog, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_AD_EventLog, org.compiere.model.I_AD_Org>(I_AD_EventLog.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Get Erstellt.
	 * Datum, an dem dieser Eintrag erstellt wurde
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getCreated();

    /** Column definition for Created */
    public static final org.adempiere.model.ModelColumn<I_AD_EventLog, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_AD_EventLog, Object>(I_AD_EventLog.class, "Created", null);
    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/**
	 * Get Erstellt durch.
	 * Nutzer, der diesen Eintrag erstellt hat
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getCreatedBy();

    /** Column definition for CreatedBy */
    public static final org.adempiere.model.ModelColumn<I_AD_EventLog, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_AD_EventLog, org.compiere.model.I_AD_User>(I_AD_EventLog.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Daten.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setEventData (java.lang.String EventData);

	/**
	 * Get Daten.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getEventData();

    /** Column definition for EventData */
    public static final org.adempiere.model.ModelColumn<I_AD_EventLog, Object> COLUMN_EventData = new org.adempiere.model.ModelColumn<I_AD_EventLog, Object>(I_AD_EventLog.class, "EventData", null);
    /** Column name EventData */
    public static final String COLUMNNAME_EventData = "EventData";

	/**
	 * Set Zeitpunkt.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setEventTime (java.sql.Timestamp EventTime);

	/**
	 * Get Zeitpunkt.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getEventTime();

    /** Column definition for EventTime */
    public static final org.adempiere.model.ModelColumn<I_AD_EventLog, Object> COLUMN_EventTime = new org.adempiere.model.ModelColumn<I_AD_EventLog, Object>(I_AD_EventLog.class, "EventTime", null);
    /** Column name EventTime */
    public static final String COLUMNNAME_EventTime = "EventTime";

	/**
	 * Set Topicname.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setEventTopicName (java.lang.String EventTopicName);

	/**
	 * Get Topicname.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getEventTopicName();

    /** Column definition for EventTopicName */
    public static final org.adempiere.model.ModelColumn<I_AD_EventLog, Object> COLUMN_EventTopicName = new org.adempiere.model.ModelColumn<I_AD_EventLog, Object>(I_AD_EventLog.class, "EventTopicName", null);
    /** Column name EventTopicName */
    public static final String COLUMNNAME_EventTopicName = "EventTopicName";

	/**
	 * Set Type.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setEventTypeName (java.lang.String EventTypeName);

	/**
	 * Get Type.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getEventTypeName();

    /** Column definition for EventTypeName */
    public static final org.adempiere.model.ModelColumn<I_AD_EventLog, Object> COLUMN_EventTypeName = new org.adempiere.model.ModelColumn<I_AD_EventLog, Object>(I_AD_EventLog.class, "EventTypeName", null);
    /** Column name EventTypeName */
    public static final String COLUMNNAME_EventTypeName = "EventTypeName";

	/**
	 * Set Event UUID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setEvent_UUID (java.lang.String Event_UUID);

	/**
	 * Get Event UUID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getEvent_UUID();

    /** Column definition for Event_UUID */
    public static final org.adempiere.model.ModelColumn<I_AD_EventLog, Object> COLUMN_Event_UUID = new org.adempiere.model.ModelColumn<I_AD_EventLog, Object>(I_AD_EventLog.class, "Event_UUID", null);
    /** Column name Event_UUID */
    public static final String COLUMNNAME_Event_UUID = "Event_UUID";

	/**
	 * Set Aktiv.
	 * Der Eintrag ist im System aktiv
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsActive (boolean IsActive);

	/**
	 * Get Aktiv.
	 * Der Eintrag ist im System aktiv
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isActive();

    /** Column definition for IsActive */
    public static final org.adempiere.model.ModelColumn<I_AD_EventLog, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_AD_EventLog, Object>(I_AD_EventLog.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Fehler.
	 * Ein Fehler ist bei der Durchführung aufgetreten
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsError (boolean IsError);

	/**
	 * Get Fehler.
	 * Ein Fehler ist bei der Durchführung aufgetreten
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isError();

    /** Column definition for IsError */
    public static final org.adempiere.model.ModelColumn<I_AD_EventLog, Object> COLUMN_IsError = new org.adempiere.model.ModelColumn<I_AD_EventLog, Object>(I_AD_EventLog.class, "IsError", null);
    /** Column name IsError */
    public static final String COLUMNNAME_IsError = "IsError";

	/**
	 * Set Fehler zur Kentnis genommen.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsErrorAcknowledged (boolean IsErrorAcknowledged);

	/**
	 * Get Fehler zur Kentnis genommen.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isErrorAcknowledged();

    /** Column definition for IsErrorAcknowledged */
    public static final org.adempiere.model.ModelColumn<I_AD_EventLog, Object> COLUMN_IsErrorAcknowledged = new org.adempiere.model.ModelColumn<I_AD_EventLog, Object>(I_AD_EventLog.class, "IsErrorAcknowledged", null);
    /** Column name IsErrorAcknowledged */
    public static final String COLUMNNAME_IsErrorAcknowledged = "IsErrorAcknowledged";

	/**
	 * Get Aktualisiert.
	 * Datum, an dem dieser Eintrag aktualisiert wurde
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getUpdated();

    /** Column definition for Updated */
    public static final org.adempiere.model.ModelColumn<I_AD_EventLog, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_AD_EventLog, Object>(I_AD_EventLog.class, "Updated", null);
    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Aktualisiert durch.
	 * Nutzer, der diesen Eintrag aktualisiert hat
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getUpdatedBy();

    /** Column definition for UpdatedBy */
    public static final org.adempiere.model.ModelColumn<I_AD_EventLog, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_AD_EventLog, org.compiere.model.I_AD_User>(I_AD_EventLog.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
