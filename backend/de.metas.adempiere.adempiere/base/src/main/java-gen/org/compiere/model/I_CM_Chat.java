package org.compiere.model;


/** Generated Interface for CM_Chat
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_CM_Chat 
{

    /** TableName=CM_Chat */
    public static final String Table_Name = "CM_Chat";

    /** AD_Table_ID=876 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 7 - System - Client - Org
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(7);

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

    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Sektion.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Sektion.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Org_ID();

    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

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

    /** Column name AD_Table_ID */
    public static final String COLUMNNAME_AD_Table_ID = "AD_Table_ID";

	/**
	 * Set Chat.
	 * Chat or discussion thread
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setCM_Chat_ID (int CM_Chat_ID);

	/**
	 * Get Chat.
	 * Chat or discussion thread
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getCM_Chat_ID();

    /** Column definition for CM_Chat_ID */
    public static final org.adempiere.model.ModelColumn<I_CM_Chat, Object> COLUMN_CM_Chat_ID = new org.adempiere.model.ModelColumn<I_CM_Chat, Object>(I_CM_Chat.class, "CM_Chat_ID", null);
    /** Column name CM_Chat_ID */
    public static final String COLUMNNAME_CM_Chat_ID = "CM_Chat_ID";

	/**
	 * Set Chat-Art.
	 * Type of discussion / chat
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setCM_ChatType_ID (int CM_ChatType_ID);

	/**
	 * Get Chat-Art.
	 * Type of discussion / chat
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getCM_ChatType_ID();

	public org.compiere.model.I_CM_ChatType getCM_ChatType();

	public void setCM_ChatType(org.compiere.model.I_CM_ChatType CM_ChatType);

    /** Column definition for CM_ChatType_ID */
    public static final org.adempiere.model.ModelColumn<I_CM_Chat, org.compiere.model.I_CM_ChatType> COLUMN_CM_ChatType_ID = new org.adempiere.model.ModelColumn<I_CM_Chat, org.compiere.model.I_CM_ChatType>(I_CM_Chat.class, "CM_ChatType_ID", org.compiere.model.I_CM_ChatType.class);
    /** Column name CM_ChatType_ID */
    public static final String COLUMNNAME_CM_ChatType_ID = "CM_ChatType_ID";

	/**
	 * Set Vertraulichkeit.
	 * Type of Confidentiality
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setConfidentialType (java.lang.String ConfidentialType);

	/**
	 * Get Vertraulichkeit.
	 * Type of Confidentiality
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getConfidentialType();

    /** Column definition for ConfidentialType */
    public static final org.adempiere.model.ModelColumn<I_CM_Chat, Object> COLUMN_ConfidentialType = new org.adempiere.model.ModelColumn<I_CM_Chat, Object>(I_CM_Chat.class, "ConfidentialType", null);
    /** Column name ConfidentialType */
    public static final String COLUMNNAME_ConfidentialType = "ConfidentialType";

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
    public static final org.adempiere.model.ModelColumn<I_CM_Chat, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_CM_Chat, Object>(I_CM_Chat.class, "Created", null);
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

    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Beschreibung.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDescription (java.lang.String Description);

	/**
	 * Get Beschreibung.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDescription();

    /** Column definition for Description */
    public static final org.adempiere.model.ModelColumn<I_CM_Chat, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<I_CM_Chat, Object>(I_CM_Chat.class, "Description", null);
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
    public static final org.adempiere.model.ModelColumn<I_CM_Chat, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_CM_Chat, Object>(I_CM_Chat.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Moderation Type.
	 * Type of moderation
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setModerationType (java.lang.String ModerationType);

	/**
	 * Get Moderation Type.
	 * Type of moderation
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getModerationType();

    /** Column definition for ModerationType */
    public static final org.adempiere.model.ModelColumn<I_CM_Chat, Object> COLUMN_ModerationType = new org.adempiere.model.ModelColumn<I_CM_Chat, Object>(I_CM_Chat.class, "ModerationType", null);
    /** Column name ModerationType */
    public static final String COLUMNNAME_ModerationType = "ModerationType";

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
    public static final org.adempiere.model.ModelColumn<I_CM_Chat, Object> COLUMN_Record_ID = new org.adempiere.model.ModelColumn<I_CM_Chat, Object>(I_CM_Chat.class, "Record_ID", null);
    /** Column name Record_ID */
    public static final String COLUMNNAME_Record_ID = "Record_ID";

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
    public static final org.adempiere.model.ModelColumn<I_CM_Chat, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_CM_Chat, Object>(I_CM_Chat.class, "Updated", null);
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

    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
