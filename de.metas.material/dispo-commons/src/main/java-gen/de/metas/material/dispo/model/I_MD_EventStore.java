package de.metas.material.dispo.model;


/** Generated Interface for MD_EventStore
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_MD_EventStore 
{

    /** TableName=MD_EventStore */
    public static final String Table_Name = "MD_EventStore";

    /** AD_Table_ID=540814 */
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
    public static final org.adempiere.model.ModelColumn<I_MD_EventStore, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_MD_EventStore, org.compiere.model.I_AD_Client>(I_MD_EventStore.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set System-Problem.
	 * Automatically created or manually entered System Issue
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_Issue_ID (int AD_Issue_ID);

	/**
	 * Get System-Problem.
	 * Automatically created or manually entered System Issue
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_Issue_ID();

	public org.compiere.model.I_AD_Issue getAD_Issue();

	public void setAD_Issue(org.compiere.model.I_AD_Issue AD_Issue);

    /** Column definition for AD_Issue_ID */
    public static final org.adempiere.model.ModelColumn<I_MD_EventStore, org.compiere.model.I_AD_Issue> COLUMN_AD_Issue_ID = new org.adempiere.model.ModelColumn<I_MD_EventStore, org.compiere.model.I_AD_Issue>(I_MD_EventStore.class, "AD_Issue_ID", org.compiere.model.I_AD_Issue.class);
    /** Column name AD_Issue_ID */
    public static final String COLUMNNAME_AD_Issue_ID = "AD_Issue_ID";

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
    public static final org.adempiere.model.ModelColumn<I_MD_EventStore, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_MD_EventStore, org.compiere.model.I_AD_Org>(I_MD_EventStore.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
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
    public static final org.adempiere.model.ModelColumn<I_MD_EventStore, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_MD_EventStore, Object>(I_MD_EventStore.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_MD_EventStore, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_MD_EventStore, org.compiere.model.I_AD_User>(I_MD_EventStore.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Zeitpunkt.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setEventTime (java.sql.Timestamp EventTime);

	/**
	 * Get Zeitpunkt.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getEventTime();

    /** Column definition for EventTime */
    public static final org.adempiere.model.ModelColumn<I_MD_EventStore, Object> COLUMN_EventTime = new org.adempiere.model.ModelColumn<I_MD_EventStore, Object>(I_MD_EventStore.class, "EventTime", null);
    /** Column name EventTime */
    public static final String COLUMNNAME_EventTime = "EventTime";

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
    public static final org.adempiere.model.ModelColumn<I_MD_EventStore, Object> COLUMN_Event_UUID = new org.adempiere.model.ModelColumn<I_MD_EventStore, Object>(I_MD_EventStore.class, "Event_UUID", null);
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
    public static final org.adempiere.model.ModelColumn<I_MD_EventStore, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_MD_EventStore, Object>(I_MD_EventStore.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Dispositionskandidat.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setMD_Candidate_ID (int MD_Candidate_ID);

	/**
	 * Get Dispositionskandidat.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getMD_Candidate_ID();

	public de.metas.material.dispo.model.I_MD_Candidate getMD_Candidate();

	public void setMD_Candidate(de.metas.material.dispo.model.I_MD_Candidate MD_Candidate);

    /** Column definition for MD_Candidate_ID */
    public static final org.adempiere.model.ModelColumn<I_MD_EventStore, de.metas.material.dispo.model.I_MD_Candidate> COLUMN_MD_Candidate_ID = new org.adempiere.model.ModelColumn<I_MD_EventStore, de.metas.material.dispo.model.I_MD_Candidate>(I_MD_EventStore.class, "MD_Candidate_ID", de.metas.material.dispo.model.I_MD_Candidate.class);
    /** Column name MD_Candidate_ID */
    public static final String COLUMNNAME_MD_Candidate_ID = "MD_Candidate_ID";

	/**
	 * Set Material-Dispo-Eventstore.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setMD_EventStore_ID (int MD_EventStore_ID);

	/**
	 * Get Material-Dispo-Eventstore.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getMD_EventStore_ID();

    /** Column definition for MD_EventStore_ID */
    public static final org.adempiere.model.ModelColumn<I_MD_EventStore, Object> COLUMN_MD_EventStore_ID = new org.adempiere.model.ModelColumn<I_MD_EventStore, Object>(I_MD_EventStore.class, "MD_EventStore_ID", null);
    /** Column name MD_EventStore_ID */
    public static final String COLUMNNAME_MD_EventStore_ID = "MD_EventStore_ID";

	/**
	 * Set Verarbeitet.
	 * Checkbox sagt aus, ob der Beleg verarbeitet wurde.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setProcessed (boolean Processed);

	/**
	 * Get Verarbeitet.
	 * Checkbox sagt aus, ob der Beleg verarbeitet wurde.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isProcessed();

    /** Column definition for Processed */
    public static final org.adempiere.model.ModelColumn<I_MD_EventStore, Object> COLUMN_Processed = new org.adempiere.model.ModelColumn<I_MD_EventStore, Object>(I_MD_EventStore.class, "Processed", null);
    /** Column name Processed */
    public static final String COLUMNNAME_Processed = "Processed";

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
    public static final org.adempiere.model.ModelColumn<I_MD_EventStore, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_MD_EventStore, Object>(I_MD_EventStore.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_MD_EventStore, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_MD_EventStore, org.compiere.model.I_AD_User>(I_MD_EventStore.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
