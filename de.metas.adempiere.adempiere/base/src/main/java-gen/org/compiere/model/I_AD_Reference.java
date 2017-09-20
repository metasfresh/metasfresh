package org.compiere.model;


/** Generated Interface for AD_Reference
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_AD_Reference 
{

    /** TableName=AD_Reference */
    public static final String Table_Name = "AD_Reference";

    /** AD_Table_ID=102 */
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
    public static final org.adempiere.model.ModelColumn<I_AD_Reference, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_AD_Reference, org.compiere.model.I_AD_Client>(I_AD_Reference.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_AD_Reference, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_AD_Reference, org.compiere.model.I_AD_Org>(I_AD_Reference.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Referenz.
	 * System Reference and Validation
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_Reference_ID (int AD_Reference_ID);

	/**
	 * Get Referenz.
	 * System Reference and Validation
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Reference_ID();

    /** Column definition for AD_Reference_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_Reference, Object> COLUMN_AD_Reference_ID = new org.adempiere.model.ModelColumn<I_AD_Reference, Object>(I_AD_Reference.class, "AD_Reference_ID", null);
    /** Column name AD_Reference_ID */
    public static final String COLUMNNAME_AD_Reference_ID = "AD_Reference_ID";

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
    public static final org.adempiere.model.ModelColumn<I_AD_Reference, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_AD_Reference, Object>(I_AD_Reference.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_AD_Reference, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_AD_Reference, org.compiere.model.I_AD_User>(I_AD_Reference.class, "CreatedBy", org.compiere.model.I_AD_User.class);
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
    public static final org.adempiere.model.ModelColumn<I_AD_Reference, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<I_AD_Reference, Object>(I_AD_Reference.class, "Description", null);
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
    public static final org.adempiere.model.ModelColumn<I_AD_Reference, Object> COLUMN_EntityType = new org.adempiere.model.ModelColumn<I_AD_Reference, Object>(I_AD_Reference.class, "EntityType", null);
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
    public static final org.adempiere.model.ModelColumn<I_AD_Reference, Object> COLUMN_Help = new org.adempiere.model.ModelColumn<I_AD_Reference, Object>(I_AD_Reference.class, "Help", null);
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
    public static final org.adempiere.model.ModelColumn<I_AD_Reference, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_AD_Reference, Object>(I_AD_Reference.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Order By Value.
	 * Order list using the value column instead of the name column
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setIsOrderByValue (boolean IsOrderByValue);

	/**
	 * Get Order By Value.
	 * Order list using the value column instead of the name column
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isOrderByValue();

    /** Column definition for IsOrderByValue */
    public static final org.adempiere.model.ModelColumn<I_AD_Reference, Object> COLUMN_IsOrderByValue = new org.adempiere.model.ModelColumn<I_AD_Reference, Object>(I_AD_Reference.class, "IsOrderByValue", null);
    /** Column name IsOrderByValue */
    public static final String COLUMNNAME_IsOrderByValue = "IsOrderByValue";

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
    public static final org.adempiere.model.ModelColumn<I_AD_Reference, Object> COLUMN_Name = new org.adempiere.model.ModelColumn<I_AD_Reference, Object>(I_AD_Reference.class, "Name", null);
    /** Column name Name */
    public static final String COLUMNNAME_Name = "Name";

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
    public static final org.adempiere.model.ModelColumn<I_AD_Reference, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_AD_Reference, Object>(I_AD_Reference.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_AD_Reference, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_AD_Reference, org.compiere.model.I_AD_User>(I_AD_Reference.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/**
	 * Set Validation type.
	 * Different method of validating data
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setValidationType (java.lang.String ValidationType);

	/**
	 * Get Validation type.
	 * Different method of validating data
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getValidationType();

    /** Column definition for ValidationType */
    public static final org.adempiere.model.ModelColumn<I_AD_Reference, Object> COLUMN_ValidationType = new org.adempiere.model.ModelColumn<I_AD_Reference, Object>(I_AD_Reference.class, "ValidationType", null);
    /** Column name ValidationType */
    public static final String COLUMNNAME_ValidationType = "ValidationType";

	/**
	 * Set Value Format.
	 * Format of the value;
 Can contain fixed format elements, Variables: "_lLoOaAcCa09"
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setVFormat (java.lang.String VFormat);

	/**
	 * Get Value Format.
	 * Format of the value;
 Can contain fixed format elements, Variables: "_lLoOaAcCa09"
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getVFormat();

    /** Column definition for VFormat */
    public static final org.adempiere.model.ModelColumn<I_AD_Reference, Object> COLUMN_VFormat = new org.adempiere.model.ModelColumn<I_AD_Reference, Object>(I_AD_Reference.class, "VFormat", null);
    /** Column name VFormat */
    public static final String COLUMNNAME_VFormat = "VFormat";
}
