package org.compiere.model;


/** Generated Interface for AD_EntityType
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_AD_EntityType 
{

    /** TableName=AD_EntityType */
    public static final String Table_Name = "AD_EntityType";

    /** AD_Table_ID=882 */
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
    public static final org.adempiere.model.ModelColumn<I_AD_EntityType, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_AD_EntityType, org.compiere.model.I_AD_Client>(I_AD_EntityType.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Entitäts-Art.
	 * Systementitäts-Art
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_EntityType_ID (int AD_EntityType_ID);

	/**
	 * Get Entitäts-Art.
	 * Systementitäts-Art
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_EntityType_ID();

    /** Column definition for AD_EntityType_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_EntityType, Object> COLUMN_AD_EntityType_ID = new org.adempiere.model.ModelColumn<I_AD_EntityType, Object>(I_AD_EntityType.class, "AD_EntityType_ID", null);
    /** Column name AD_EntityType_ID */
    public static final String COLUMNNAME_AD_EntityType_ID = "AD_EntityType_ID";

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
    public static final org.adempiere.model.ModelColumn<I_AD_EntityType, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_AD_EntityType, org.compiere.model.I_AD_Org>(I_AD_EntityType.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Classpath.
	 * Extension Classpath
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setClasspath (java.lang.String Classpath);

	/**
	 * Get Classpath.
	 * Extension Classpath
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getClasspath();

    /** Column definition for Classpath */
    public static final org.adempiere.model.ModelColumn<I_AD_EntityType, Object> COLUMN_Classpath = new org.adempiere.model.ModelColumn<I_AD_EntityType, Object>(I_AD_EntityType.class, "Classpath", null);
    /** Column name Classpath */
    public static final String COLUMNNAME_Classpath = "Classpath";

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
    public static final org.adempiere.model.ModelColumn<I_AD_EntityType, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_AD_EntityType, Object>(I_AD_EntityType.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_AD_EntityType, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_AD_EntityType, org.compiere.model.I_AD_User>(I_AD_EntityType.class, "CreatedBy", org.compiere.model.I_AD_User.class);
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
    public static final org.adempiere.model.ModelColumn<I_AD_EntityType, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<I_AD_EntityType, Object>(I_AD_EntityType.class, "Description", null);
    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/**
	 * Set Entitäts-Art.
	 * Dictionary Entity Type;
 Determines ownership and synchronization
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setEntityType (java.lang.String EntityType);

	/**
	 * Get Entitäts-Art.
	 * Dictionary Entity Type;
 Determines ownership and synchronization
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getEntityType();

    /** Column definition for EntityType */
    public static final org.adempiere.model.ModelColumn<I_AD_EntityType, Object> COLUMN_EntityType = new org.adempiere.model.ModelColumn<I_AD_EntityType, Object>(I_AD_EntityType.class, "EntityType", null);
    /** Column name EntityType */
    public static final String COLUMNNAME_EntityType = "EntityType";

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
    public static final org.adempiere.model.ModelColumn<I_AD_EntityType, Object> COLUMN_Help = new org.adempiere.model.ModelColumn<I_AD_EntityType, Object>(I_AD_EntityType.class, "Help", null);
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
    public static final org.adempiere.model.ModelColumn<I_AD_EntityType, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_AD_EntityType, Object>(I_AD_EntityType.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Displayed.
	 * Determines, if this field is displayed
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsDisplayed (boolean IsDisplayed);

	/**
	 * Get Displayed.
	 * Determines, if this field is displayed
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isDisplayed();

    /** Column definition for IsDisplayed */
    public static final org.adempiere.model.ModelColumn<I_AD_EntityType, Object> COLUMN_IsDisplayed = new org.adempiere.model.ModelColumn<I_AD_EntityType, Object>(I_AD_EntityType.class, "IsDisplayed", null);
    /** Column name IsDisplayed */
    public static final String COLUMNNAME_IsDisplayed = "IsDisplayed";

	/**
	 * Set ModelPackage.
	 * Java Package of the model classes
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setModelPackage (java.lang.String ModelPackage);

	/**
	 * Get ModelPackage.
	 * Java Package of the model classes
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getModelPackage();

    /** Column definition for ModelPackage */
    public static final org.adempiere.model.ModelColumn<I_AD_EntityType, Object> COLUMN_ModelPackage = new org.adempiere.model.ModelColumn<I_AD_EntityType, Object>(I_AD_EntityType.class, "ModelPackage", null);
    /** Column name ModelPackage */
    public static final String COLUMNNAME_ModelPackage = "ModelPackage";

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
    public static final org.adempiere.model.ModelColumn<I_AD_EntityType, Object> COLUMN_Name = new org.adempiere.model.ModelColumn<I_AD_EntityType, Object>(I_AD_EntityType.class, "Name", null);
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
    public static final org.adempiere.model.ModelColumn<I_AD_EntityType, Object> COLUMN_Processing = new org.adempiere.model.ModelColumn<I_AD_EntityType, Object>(I_AD_EntityType.class, "Processing", null);
    /** Column name Processing */
    public static final String COLUMNNAME_Processing = "Processing";

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
    public static final org.adempiere.model.ModelColumn<I_AD_EntityType, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_AD_EntityType, Object>(I_AD_EntityType.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_AD_EntityType, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_AD_EntityType, org.compiere.model.I_AD_User>(I_AD_EntityType.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/**
	 * Set Version.
	 * Version of the table definition
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setVersion (java.lang.String Version);

	/**
	 * Get Version.
	 * Version of the table definition
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getVersion();

    /** Column definition for Version */
    public static final org.adempiere.model.ModelColumn<I_AD_EntityType, Object> COLUMN_Version = new org.adempiere.model.ModelColumn<I_AD_EntityType, Object>(I_AD_EntityType.class, "Version", null);
    /** Column name Version */
    public static final String COLUMNNAME_Version = "Version";

	/**
	 * Set WebUIServletListener Class.
	 * Optional class to execute custom code on WebUI startup;
 A declared class needs to implement IWebUIServletListener
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setWebUIServletListenerClass (java.lang.String WebUIServletListenerClass);

	/**
	 * Get WebUIServletListener Class.
	 * Optional class to execute custom code on WebUI startup;
 A declared class needs to implement IWebUIServletListener
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getWebUIServletListenerClass();

    /** Column definition for WebUIServletListenerClass */
    public static final org.adempiere.model.ModelColumn<I_AD_EntityType, Object> COLUMN_WebUIServletListenerClass = new org.adempiere.model.ModelColumn<I_AD_EntityType, Object>(I_AD_EntityType.class, "WebUIServletListenerClass", null);
    /** Column name WebUIServletListenerClass */
    public static final String COLUMNNAME_WebUIServletListenerClass = "WebUIServletListenerClass";
}
