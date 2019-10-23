package org.compiere.model;


/** Generated Interface for AD_ChangeLog
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_AD_ChangeLog 
{

    /** TableName=AD_ChangeLog */
    public static final String Table_Name = "AD_ChangeLog";

    /** AD_Table_ID=580 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 6 - System - Client
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(6);

    /** Load Meta Data */

	/**
	 * Set Änderungsprotokoll.
	 * Log of data changes
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_ChangeLog_ID (int AD_ChangeLog_ID);

	/**
	 * Get Änderungsprotokoll.
	 * Log of data changes
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_ChangeLog_ID();

    /** Column definition for AD_ChangeLog_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_ChangeLog, Object> COLUMN_AD_ChangeLog_ID = new org.adempiere.model.ModelColumn<I_AD_ChangeLog, Object>(I_AD_ChangeLog.class, "AD_ChangeLog_ID", null);
    /** Column name AD_ChangeLog_ID */
    public static final String COLUMNNAME_AD_ChangeLog_ID = "AD_ChangeLog_ID";

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
    public static final org.adempiere.model.ModelColumn<I_AD_ChangeLog, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_AD_ChangeLog, org.compiere.model.I_AD_Client>(I_AD_ChangeLog.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Spalte.
	 * Column in the table
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_Column_ID (int AD_Column_ID);

	/**
	 * Get Spalte.
	 * Column in the table
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Column_ID();

	public org.compiere.model.I_AD_Column getAD_Column();

	public void setAD_Column(org.compiere.model.I_AD_Column AD_Column);

    /** Column definition for AD_Column_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_ChangeLog, org.compiere.model.I_AD_Column> COLUMN_AD_Column_ID = new org.adempiere.model.ModelColumn<I_AD_ChangeLog, org.compiere.model.I_AD_Column>(I_AD_ChangeLog.class, "AD_Column_ID", org.compiere.model.I_AD_Column.class);
    /** Column name AD_Column_ID */
    public static final String COLUMNNAME_AD_Column_ID = "AD_Column_ID";

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
    public static final org.adempiere.model.ModelColumn<I_AD_ChangeLog, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_AD_ChangeLog, org.compiere.model.I_AD_Org>(I_AD_ChangeLog.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
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
    public static final org.adempiere.model.ModelColumn<I_AD_ChangeLog, org.compiere.model.I_AD_PInstance> COLUMN_AD_PInstance_ID = new org.adempiere.model.ModelColumn<I_AD_ChangeLog, org.compiere.model.I_AD_PInstance>(I_AD_ChangeLog.class, "AD_PInstance_ID", org.compiere.model.I_AD_PInstance.class);
    /** Column name AD_PInstance_ID */
    public static final String COLUMNNAME_AD_PInstance_ID = "AD_PInstance_ID";

	/**
	 * Set Nutzersitzung.
	 * User Session Online or Web
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_Session_ID (int AD_Session_ID);

	/**
	 * Get Nutzersitzung.
	 * User Session Online or Web
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_Session_ID();

	public org.compiere.model.I_AD_Session getAD_Session();

	public void setAD_Session(org.compiere.model.I_AD_Session AD_Session);

    /** Column definition for AD_Session_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_ChangeLog, org.compiere.model.I_AD_Session> COLUMN_AD_Session_ID = new org.adempiere.model.ModelColumn<I_AD_ChangeLog, org.compiere.model.I_AD_Session>(I_AD_ChangeLog.class, "AD_Session_ID", org.compiere.model.I_AD_Session.class);
    /** Column name AD_Session_ID */
    public static final String COLUMNNAME_AD_Session_ID = "AD_Session_ID";

	/**
	 * Set DB-Tabelle.
	 * Database Table information
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_Table_ID (int AD_Table_ID);

	/**
	 * Get DB-Tabelle.
	 * Database Table information
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Table_ID();

	public org.compiere.model.I_AD_Table getAD_Table();

	public void setAD_Table(org.compiere.model.I_AD_Table AD_Table);

    /** Column definition for AD_Table_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_ChangeLog, org.compiere.model.I_AD_Table> COLUMN_AD_Table_ID = new org.adempiere.model.ModelColumn<I_AD_ChangeLog, org.compiere.model.I_AD_Table>(I_AD_ChangeLog.class, "AD_Table_ID", org.compiere.model.I_AD_Table.class);
    /** Column name AD_Table_ID */
    public static final String COLUMNNAME_AD_Table_ID = "AD_Table_ID";

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
    public static final org.adempiere.model.ModelColumn<I_AD_ChangeLog, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_AD_ChangeLog, Object>(I_AD_ChangeLog.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_AD_ChangeLog, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_AD_ChangeLog, org.compiere.model.I_AD_User>(I_AD_ChangeLog.class, "CreatedBy", org.compiere.model.I_AD_User.class);
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
    public static final org.adempiere.model.ModelColumn<I_AD_ChangeLog, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<I_AD_ChangeLog, Object>(I_AD_ChangeLog.class, "Description", null);
    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/**
	 * Set Event Change Log.
	 * Type of Event in Change Log
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setEventChangeLog (java.lang.String EventChangeLog);

	/**
	 * Get Event Change Log.
	 * Type of Event in Change Log
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getEventChangeLog();

    /** Column definition for EventChangeLog */
    public static final org.adempiere.model.ModelColumn<I_AD_ChangeLog, Object> COLUMN_EventChangeLog = new org.adempiere.model.ModelColumn<I_AD_ChangeLog, Object>(I_AD_ChangeLog.class, "EventChangeLog", null);
    /** Column name EventChangeLog */
    public static final String COLUMNNAME_EventChangeLog = "EventChangeLog";

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
    public static final org.adempiere.model.ModelColumn<I_AD_ChangeLog, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_AD_ChangeLog, Object>(I_AD_ChangeLog.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Anpassung/Erweiterung.
	 * The change is a customization of the data dictionary and can be applied after Migration
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsCustomization (boolean IsCustomization);

	/**
	 * Get Anpassung/Erweiterung.
	 * The change is a customization of the data dictionary and can be applied after Migration
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isCustomization();

    /** Column definition for IsCustomization */
    public static final org.adempiere.model.ModelColumn<I_AD_ChangeLog, Object> COLUMN_IsCustomization = new org.adempiere.model.ModelColumn<I_AD_ChangeLog, Object>(I_AD_ChangeLog.class, "IsCustomization", null);
    /** Column name IsCustomization */
    public static final String COLUMNNAME_IsCustomization = "IsCustomization";

	/**
	 * Set Neuer Wert.
	 * New field value
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setNewValue (java.lang.String NewValue);

	/**
	 * Get Neuer Wert.
	 * New field value
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getNewValue();

    /** Column definition for NewValue */
    public static final org.adempiere.model.ModelColumn<I_AD_ChangeLog, Object> COLUMN_NewValue = new org.adempiere.model.ModelColumn<I_AD_ChangeLog, Object>(I_AD_ChangeLog.class, "NewValue", null);
    /** Column name NewValue */
    public static final String COLUMNNAME_NewValue = "NewValue";

	/**
	 * Set Alter Wert.
	 * The old file data
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setOldValue (java.lang.String OldValue);

	/**
	 * Get Alter Wert.
	 * The old file data
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getOldValue();

    /** Column definition for OldValue */
    public static final org.adempiere.model.ModelColumn<I_AD_ChangeLog, Object> COLUMN_OldValue = new org.adempiere.model.ModelColumn<I_AD_ChangeLog, Object>(I_AD_ChangeLog.class, "OldValue", null);
    /** Column name OldValue */
    public static final String COLUMNNAME_OldValue = "OldValue";

	/**
	 * Set Datensatz-ID.
	 * Direct internal record ID
	 *
	 * <br>Type: Button
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setRecord_ID (int Record_ID);

	/**
	 * Get Datensatz-ID.
	 * Direct internal record ID
	 *
	 * <br>Type: Button
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getRecord_ID();

    /** Column definition for Record_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_ChangeLog, Object> COLUMN_Record_ID = new org.adempiere.model.ModelColumn<I_AD_ChangeLog, Object>(I_AD_ChangeLog.class, "Record_ID", null);
    /** Column name Record_ID */
    public static final String COLUMNNAME_Record_ID = "Record_ID";

	/**
	 * Set Redo.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setRedo (java.lang.String Redo);

	/**
	 * Get Redo.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getRedo();

    /** Column definition for Redo */
    public static final org.adempiere.model.ModelColumn<I_AD_ChangeLog, Object> COLUMN_Redo = new org.adempiere.model.ModelColumn<I_AD_ChangeLog, Object>(I_AD_ChangeLog.class, "Redo", null);
    /** Column name Redo */
    public static final String COLUMNNAME_Redo = "Redo";

	/**
	 * Set Transaktion.
	 * Name of the transaction
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setTrxName (java.lang.String TrxName);

	/**
	 * Get Transaktion.
	 * Name of the transaction
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getTrxName();

    /** Column definition for TrxName */
    public static final org.adempiere.model.ModelColumn<I_AD_ChangeLog, Object> COLUMN_TrxName = new org.adempiere.model.ModelColumn<I_AD_ChangeLog, Object>(I_AD_ChangeLog.class, "TrxName", null);
    /** Column name TrxName */
    public static final String COLUMNNAME_TrxName = "TrxName";

	/**
	 * Set Undo.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setUndo (java.lang.String Undo);

	/**
	 * Get Undo.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getUndo();

    /** Column definition for Undo */
    public static final org.adempiere.model.ModelColumn<I_AD_ChangeLog, Object> COLUMN_Undo = new org.adempiere.model.ModelColumn<I_AD_ChangeLog, Object>(I_AD_ChangeLog.class, "Undo", null);
    /** Column name Undo */
    public static final String COLUMNNAME_Undo = "Undo";

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
    public static final org.adempiere.model.ModelColumn<I_AD_ChangeLog, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_AD_ChangeLog, Object>(I_AD_ChangeLog.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_AD_ChangeLog, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_AD_ChangeLog, org.compiere.model.I_AD_User>(I_AD_ChangeLog.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
