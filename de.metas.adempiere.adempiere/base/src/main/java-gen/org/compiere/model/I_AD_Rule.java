package org.compiere.model;


/** Generated Interface for AD_Rule
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_AD_Rule 
{

    /** TableName=AD_Rule */
    public static final String Table_Name = "AD_Rule";

    /** AD_Table_ID=53058 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 4 - System
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(4);

    /** Load Meta Data */

	/**
	 * Set Berechtigungsstufe.
	 * Access Level required
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAccessLevel (java.lang.String AccessLevel);

	/**
	 * Get Berechtigungsstufe.
	 * Access Level required
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getAccessLevel();

    /** Column definition for AccessLevel */
    public static final org.adempiere.model.ModelColumn<I_AD_Rule, Object> COLUMN_AccessLevel = new org.adempiere.model.ModelColumn<I_AD_Rule, Object>(I_AD_Rule.class, "AccessLevel", null);
    /** Column name AccessLevel */
    public static final String COLUMNNAME_AccessLevel = "AccessLevel";

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
    public static final org.adempiere.model.ModelColumn<I_AD_Rule, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_AD_Rule, org.compiere.model.I_AD_Client>(I_AD_Rule.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_AD_Rule, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_AD_Rule, org.compiere.model.I_AD_Org>(I_AD_Rule.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Regel.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_Rule_ID (int AD_Rule_ID);

	/**
	 * Get Regel.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Rule_ID();

    /** Column definition for AD_Rule_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_Rule, Object> COLUMN_AD_Rule_ID = new org.adempiere.model.ModelColumn<I_AD_Rule, Object>(I_AD_Rule.class, "AD_Rule_ID", null);
    /** Column name AD_Rule_ID */
    public static final String COLUMNNAME_AD_Rule_ID = "AD_Rule_ID";

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
    public static final org.adempiere.model.ModelColumn<I_AD_Rule, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_AD_Rule, Object>(I_AD_Rule.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_AD_Rule, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_AD_Rule, org.compiere.model.I_AD_User>(I_AD_Rule.class, "CreatedBy", org.compiere.model.I_AD_User.class);
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
    public static final org.adempiere.model.ModelColumn<I_AD_Rule, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<I_AD_Rule, Object>(I_AD_Rule.class, "Description", null);
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
    public static final org.adempiere.model.ModelColumn<I_AD_Rule, Object> COLUMN_EntityType = new org.adempiere.model.ModelColumn<I_AD_Rule, Object>(I_AD_Rule.class, "EntityType", null);
    /** Column name EntityType */
    public static final String COLUMNNAME_EntityType = "EntityType";

	/**
	 * Set Ereignisart.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setEventType (java.lang.String EventType);

	/**
	 * Get Ereignisart.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getEventType();

    /** Column definition for EventType */
    public static final org.adempiere.model.ModelColumn<I_AD_Rule, Object> COLUMN_EventType = new org.adempiere.model.ModelColumn<I_AD_Rule, Object>(I_AD_Rule.class, "EventType", null);
    /** Column name EventType */
    public static final String COLUMNNAME_EventType = "EventType";

	/**
	 * Set Kommentar/Hilfe.
	 * Comment or Hint
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setHelp (java.lang.String Help);

	/**
	 * Get Kommentar/Hilfe.
	 * Comment or Hint
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getHelp();

    /** Column definition for Help */
    public static final org.adempiere.model.ModelColumn<I_AD_Rule, Object> COLUMN_Help = new org.adempiere.model.ModelColumn<I_AD_Rule, Object>(I_AD_Rule.class, "Help", null);
    /** Column name Help */
    public static final String COLUMNNAME_Help = "Help";

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
    public static final org.adempiere.model.ModelColumn<I_AD_Rule, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_AD_Rule, Object>(I_AD_Rule.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

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
    public static final org.adempiere.model.ModelColumn<I_AD_Rule, Object> COLUMN_Name = new org.adempiere.model.ModelColumn<I_AD_Rule, Object>(I_AD_Rule.class, "Name", null);
    /** Column name Name */
    public static final String COLUMNNAME_Name = "Name";

	/**
	 * Set Rule Type.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setRuleType (java.lang.String RuleType);

	/**
	 * Get Rule Type.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getRuleType();

    /** Column definition for RuleType */
    public static final org.adempiere.model.ModelColumn<I_AD_Rule, Object> COLUMN_RuleType = new org.adempiere.model.ModelColumn<I_AD_Rule, Object>(I_AD_Rule.class, "RuleType", null);
    /** Column name RuleType */
    public static final String COLUMNNAME_RuleType = "RuleType";

	/**
	 * Set Skript.
	 * Dynamic Java Language Script to calculate result
	 *
	 * <br>Type: Memo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setScript (java.lang.String Script);

	/**
	 * Get Skript.
	 * Dynamic Java Language Script to calculate result
	 *
	 * <br>Type: Memo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getScript();

    /** Column definition for Script */
    public static final org.adempiere.model.ModelColumn<I_AD_Rule, Object> COLUMN_Script = new org.adempiere.model.ModelColumn<I_AD_Rule, Object>(I_AD_Rule.class, "Script", null);
    /** Column name Script */
    public static final String COLUMNNAME_Script = "Script";

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
    public static final org.adempiere.model.ModelColumn<I_AD_Rule, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_AD_Rule, Object>(I_AD_Rule.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_AD_Rule, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_AD_Rule, org.compiere.model.I_AD_User>(I_AD_Rule.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/**
	 * Set Suchschlüssel.
	 * Search key for the record in the format required - must be unique
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setValue (java.lang.String Value);

	/**
	 * Get Suchschlüssel.
	 * Search key for the record in the format required - must be unique
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getValue();

    /** Column definition for Value */
    public static final org.adempiere.model.ModelColumn<I_AD_Rule, Object> COLUMN_Value = new org.adempiere.model.ModelColumn<I_AD_Rule, Object>(I_AD_Rule.class, "Value", null);
    /** Column name Value */
    public static final String COLUMNNAME_Value = "Value";
}
