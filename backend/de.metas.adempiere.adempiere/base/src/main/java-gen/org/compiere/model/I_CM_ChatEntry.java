package org.compiere.model;


/** Generated Interface for CM_ChatEntry
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_CM_ChatEntry 
{

    /** TableName=CM_ChatEntry */
    public static final String Table_Name = "CM_ChatEntry";

    /** AD_Table_ID=877 */
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
	 * Set Ansprechpartner.
	 * User within the system - Internal or Business Partner Contact
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_User_ID (int AD_User_ID);

	/**
	 * Get Ansprechpartner.
	 * User within the system - Internal or Business Partner Contact
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_User_ID();

    /** Column name AD_User_ID */
    public static final String COLUMNNAME_AD_User_ID = "AD_User_ID";

	/**
	 * Set Character Data.
	 * Long Character Field
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setCharacterData (java.lang.String CharacterData);

	/**
	 * Get Character Data.
	 * Long Character Field
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getCharacterData();

    /** Column definition for CharacterData */
    public static final org.adempiere.model.ModelColumn<I_CM_ChatEntry, Object> COLUMN_CharacterData = new org.adempiere.model.ModelColumn<I_CM_ChatEntry, Object>(I_CM_ChatEntry.class, "CharacterData", null);
    /** Column name CharacterData */
    public static final String COLUMNNAME_CharacterData = "CharacterData";

	/**
	 * Set Chat Entry Type.
	 * Type of Chat/Forum Entry
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setChatEntryType (java.lang.String ChatEntryType);

	/**
	 * Get Chat Entry Type.
	 * Type of Chat/Forum Entry
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getChatEntryType();

    /** Column definition for ChatEntryType */
    public static final org.adempiere.model.ModelColumn<I_CM_ChatEntry, Object> COLUMN_ChatEntryType = new org.adempiere.model.ModelColumn<I_CM_ChatEntry, Object>(I_CM_ChatEntry.class, "ChatEntryType", null);
    /** Column name ChatEntryType */
    public static final String COLUMNNAME_ChatEntryType = "ChatEntryType";

	/**
	 * Set Chat Entry Grandparent.
	 * Link to Grand Parent (root level)
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setCM_ChatEntryGrandParent_ID (int CM_ChatEntryGrandParent_ID);

	/**
	 * Get Chat Entry Grandparent.
	 * Link to Grand Parent (root level)
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getCM_ChatEntryGrandParent_ID();

	public org.compiere.model.I_CM_ChatEntry getCM_ChatEntryGrandParent();

	public void setCM_ChatEntryGrandParent(org.compiere.model.I_CM_ChatEntry CM_ChatEntryGrandParent);

    /** Column definition for CM_ChatEntryGrandParent_ID */
    public static final org.adempiere.model.ModelColumn<I_CM_ChatEntry, org.compiere.model.I_CM_ChatEntry> COLUMN_CM_ChatEntryGrandParent_ID = new org.adempiere.model.ModelColumn<I_CM_ChatEntry, org.compiere.model.I_CM_ChatEntry>(I_CM_ChatEntry.class, "CM_ChatEntryGrandParent_ID", org.compiere.model.I_CM_ChatEntry.class);
    /** Column name CM_ChatEntryGrandParent_ID */
    public static final String COLUMNNAME_CM_ChatEntryGrandParent_ID = "CM_ChatEntryGrandParent_ID";

	/**
	 * Set Chat-Eintrag.
	 * Individual Chat / Discussion Entry
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setCM_ChatEntry_ID (int CM_ChatEntry_ID);

	/**
	 * Get Chat-Eintrag.
	 * Individual Chat / Discussion Entry
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getCM_ChatEntry_ID();

    /** Column definition for CM_ChatEntry_ID */
    public static final org.adempiere.model.ModelColumn<I_CM_ChatEntry, Object> COLUMN_CM_ChatEntry_ID = new org.adempiere.model.ModelColumn<I_CM_ChatEntry, Object>(I_CM_ChatEntry.class, "CM_ChatEntry_ID", null);
    /** Column name CM_ChatEntry_ID */
    public static final String COLUMNNAME_CM_ChatEntry_ID = "CM_ChatEntry_ID";

	/**
	 * Set Chat Entry Parent.
	 * Link to direct Parent
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setCM_ChatEntryParent_ID (int CM_ChatEntryParent_ID);

	/**
	 * Get Chat Entry Parent.
	 * Link to direct Parent
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getCM_ChatEntryParent_ID();

	public org.compiere.model.I_CM_ChatEntry getCM_ChatEntryParent();

	public void setCM_ChatEntryParent(org.compiere.model.I_CM_ChatEntry CM_ChatEntryParent);

    /** Column definition for CM_ChatEntryParent_ID */
    public static final org.adempiere.model.ModelColumn<I_CM_ChatEntry, org.compiere.model.I_CM_ChatEntry> COLUMN_CM_ChatEntryParent_ID = new org.adempiere.model.ModelColumn<I_CM_ChatEntry, org.compiere.model.I_CM_ChatEntry>(I_CM_ChatEntry.class, "CM_ChatEntryParent_ID", org.compiere.model.I_CM_ChatEntry.class);
    /** Column name CM_ChatEntryParent_ID */
    public static final String COLUMNNAME_CM_ChatEntryParent_ID = "CM_ChatEntryParent_ID";

	/**
	 * Set Chat.
	 * Chat or discussion thread
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setCM_Chat_ID (int CM_Chat_ID);

	/**
	 * Get Chat.
	 * Chat or discussion thread
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getCM_Chat_ID();

	public org.compiere.model.I_CM_Chat getCM_Chat();

	public void setCM_Chat(org.compiere.model.I_CM_Chat CM_Chat);

    /** Column definition for CM_Chat_ID */
    public static final org.adempiere.model.ModelColumn<I_CM_ChatEntry, org.compiere.model.I_CM_Chat> COLUMN_CM_Chat_ID = new org.adempiere.model.ModelColumn<I_CM_ChatEntry, org.compiere.model.I_CM_Chat>(I_CM_ChatEntry.class, "CM_Chat_ID", org.compiere.model.I_CM_Chat.class);
    /** Column name CM_Chat_ID */
    public static final String COLUMNNAME_CM_Chat_ID = "CM_Chat_ID";

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
    public static final org.adempiere.model.ModelColumn<I_CM_ChatEntry, Object> COLUMN_ConfidentialType = new org.adempiere.model.ModelColumn<I_CM_ChatEntry, Object>(I_CM_ChatEntry.class, "ConfidentialType", null);
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
    public static final org.adempiere.model.ModelColumn<I_CM_ChatEntry, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_CM_ChatEntry, Object>(I_CM_ChatEntry.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_CM_ChatEntry, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_CM_ChatEntry, Object>(I_CM_ChatEntry.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Moderation Status.
	 * Status of Moderation
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setModeratorStatus (java.lang.String ModeratorStatus);

	/**
	 * Get Moderation Status.
	 * Status of Moderation
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getModeratorStatus();

    /** Column definition for ModeratorStatus */
    public static final org.adempiere.model.ModelColumn<I_CM_ChatEntry, Object> COLUMN_ModeratorStatus = new org.adempiere.model.ModelColumn<I_CM_ChatEntry, Object>(I_CM_ChatEntry.class, "ModeratorStatus", null);
    /** Column name ModeratorStatus */
    public static final String COLUMNNAME_ModeratorStatus = "ModeratorStatus";

	/**
	 * Set Betreff.
	 * Mail Betreff
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setSubject (java.lang.String Subject);

	/**
	 * Get Betreff.
	 * Mail Betreff
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getSubject();

    /** Column definition for Subject */
    public static final org.adempiere.model.ModelColumn<I_CM_ChatEntry, Object> COLUMN_Subject = new org.adempiere.model.ModelColumn<I_CM_ChatEntry, Object>(I_CM_ChatEntry.class, "Subject", null);
    /** Column name Subject */
    public static final String COLUMNNAME_Subject = "Subject";

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
    public static final org.adempiere.model.ModelColumn<I_CM_ChatEntry, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_CM_ChatEntry, Object>(I_CM_ChatEntry.class, "Updated", null);
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
