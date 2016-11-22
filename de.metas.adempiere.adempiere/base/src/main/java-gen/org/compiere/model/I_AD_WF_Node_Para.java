package org.compiere.model;


/** Generated Interface for AD_WF_Node_Para
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_AD_WF_Node_Para 
{

    /** TableName=AD_WF_Node_Para */
    public static final String Table_Name = "AD_WF_Node_Para";

    /** AD_Table_ID=643 */
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
    public static final org.adempiere.model.ModelColumn<I_AD_WF_Node_Para, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_AD_WF_Node_Para, org.compiere.model.I_AD_Client>(I_AD_WF_Node_Para.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_AD_WF_Node_Para, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_AD_WF_Node_Para, org.compiere.model.I_AD_Org>(I_AD_WF_Node_Para.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Prozess-Parameter.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_Process_Para_ID (int AD_Process_Para_ID);

	/**
	 * Get Prozess-Parameter.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_Process_Para_ID();

	public org.compiere.model.I_AD_Process_Para getAD_Process_Para();

	public void setAD_Process_Para(org.compiere.model.I_AD_Process_Para AD_Process_Para);

    /** Column definition for AD_Process_Para_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_WF_Node_Para, org.compiere.model.I_AD_Process_Para> COLUMN_AD_Process_Para_ID = new org.adempiere.model.ModelColumn<I_AD_WF_Node_Para, org.compiere.model.I_AD_Process_Para>(I_AD_WF_Node_Para.class, "AD_Process_Para_ID", org.compiere.model.I_AD_Process_Para.class);
    /** Column name AD_Process_Para_ID */
    public static final String COLUMNNAME_AD_Process_Para_ID = "AD_Process_Para_ID";

	/**
	 * Set Knoten.
	 * Workflow Node (activity), step or process
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_WF_Node_ID (int AD_WF_Node_ID);

	/**
	 * Get Knoten.
	 * Workflow Node (activity), step or process
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_WF_Node_ID();

	public org.compiere.model.I_AD_WF_Node getAD_WF_Node();

	public void setAD_WF_Node(org.compiere.model.I_AD_WF_Node AD_WF_Node);

    /** Column definition for AD_WF_Node_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_WF_Node_Para, org.compiere.model.I_AD_WF_Node> COLUMN_AD_WF_Node_ID = new org.adempiere.model.ModelColumn<I_AD_WF_Node_Para, org.compiere.model.I_AD_WF_Node>(I_AD_WF_Node_Para.class, "AD_WF_Node_ID", org.compiere.model.I_AD_WF_Node.class);
    /** Column name AD_WF_Node_ID */
    public static final String COLUMNNAME_AD_WF_Node_ID = "AD_WF_Node_ID";

	/**
	 * Set Workflow Node Parameter.
	 * Workflow Node Execution Parameter
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_WF_Node_Para_ID (int AD_WF_Node_Para_ID);

	/**
	 * Get Workflow Node Parameter.
	 * Workflow Node Execution Parameter
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_WF_Node_Para_ID();

    /** Column definition for AD_WF_Node_Para_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_WF_Node_Para, Object> COLUMN_AD_WF_Node_Para_ID = new org.adempiere.model.ModelColumn<I_AD_WF_Node_Para, Object>(I_AD_WF_Node_Para.class, "AD_WF_Node_Para_ID", null);
    /** Column name AD_WF_Node_Para_ID */
    public static final String COLUMNNAME_AD_WF_Node_Para_ID = "AD_WF_Node_Para_ID";

	/**
	 * Set Attribute Name.
	 * Name of the Attribute
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAttributeName (java.lang.String AttributeName);

	/**
	 * Get Attribute Name.
	 * Name of the Attribute
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getAttributeName();

    /** Column definition for AttributeName */
    public static final org.adempiere.model.ModelColumn<I_AD_WF_Node_Para, Object> COLUMN_AttributeName = new org.adempiere.model.ModelColumn<I_AD_WF_Node_Para, Object>(I_AD_WF_Node_Para.class, "AttributeName", null);
    /** Column name AttributeName */
    public static final String COLUMNNAME_AttributeName = "AttributeName";

	/**
	 * Set Merkmals-Wert.
	 * Value of the Attribute
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAttributeValue (java.lang.String AttributeValue);

	/**
	 * Get Merkmals-Wert.
	 * Value of the Attribute
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getAttributeValue();

    /** Column definition for AttributeValue */
    public static final org.adempiere.model.ModelColumn<I_AD_WF_Node_Para, Object> COLUMN_AttributeValue = new org.adempiere.model.ModelColumn<I_AD_WF_Node_Para, Object>(I_AD_WF_Node_Para.class, "AttributeValue", null);
    /** Column name AttributeValue */
    public static final String COLUMNNAME_AttributeValue = "AttributeValue";

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
    public static final org.adempiere.model.ModelColumn<I_AD_WF_Node_Para, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_AD_WF_Node_Para, Object>(I_AD_WF_Node_Para.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_AD_WF_Node_Para, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_AD_WF_Node_Para, org.compiere.model.I_AD_User>(I_AD_WF_Node_Para.class, "CreatedBy", org.compiere.model.I_AD_User.class);
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
    public static final org.adempiere.model.ModelColumn<I_AD_WF_Node_Para, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<I_AD_WF_Node_Para, Object>(I_AD_WF_Node_Para.class, "Description", null);
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
    public static final org.adempiere.model.ModelColumn<I_AD_WF_Node_Para, Object> COLUMN_EntityType = new org.adempiere.model.ModelColumn<I_AD_WF_Node_Para, Object>(I_AD_WF_Node_Para.class, "EntityType", null);
    /** Column name EntityType */
    public static final String COLUMNNAME_EntityType = "EntityType";

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
    public static final org.adempiere.model.ModelColumn<I_AD_WF_Node_Para, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_AD_WF_Node_Para, Object>(I_AD_WF_Node_Para.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

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
    public static final org.adempiere.model.ModelColumn<I_AD_WF_Node_Para, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_AD_WF_Node_Para, Object>(I_AD_WF_Node_Para.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_AD_WF_Node_Para, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_AD_WF_Node_Para, org.compiere.model.I_AD_User>(I_AD_WF_Node_Para.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
