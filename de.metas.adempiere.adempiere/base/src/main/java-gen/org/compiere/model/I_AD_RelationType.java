package org.compiere.model;


/** Generated Interface for AD_RelationType
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_AD_RelationType 
{

    /** TableName=AD_RelationType */
    public static final String Table_Name = "AD_RelationType";

    /** AD_Table_ID=53246 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 7 - System - Client - Org
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(7);

    /** Load Meta Data */

	/**
	 * Get Client.
	 * Client/Tenant for this installation.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Client_ID();

	public org.compiere.model.I_AD_Client getAD_Client();

    /** Column definition for AD_Client_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_RelationType, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_AD_RelationType, org.compiere.model.I_AD_Client>(I_AD_RelationType.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_AD_RelationType, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_AD_RelationType, org.compiere.model.I_AD_Org>(I_AD_RelationType.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Source Reference.
	 * For directed relation types, the where clause of this reference's AD_Ref_Table is used to decide if a given record is a possible relation source.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_Reference_Source_ID (int AD_Reference_Source_ID);

	/**
	 * Get Source Reference.
	 * For directed relation types, the where clause of this reference's AD_Ref_Table is used to decide if a given record is a possible relation source.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_Reference_Source_ID();

	public org.compiere.model.I_AD_Reference getAD_Reference_Source();

	public void setAD_Reference_Source(org.compiere.model.I_AD_Reference AD_Reference_Source);

    /** Column definition for AD_Reference_Source_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_RelationType, org.compiere.model.I_AD_Reference> COLUMN_AD_Reference_Source_ID = new org.adempiere.model.ModelColumn<I_AD_RelationType, org.compiere.model.I_AD_Reference>(I_AD_RelationType.class, "AD_Reference_Source_ID", org.compiere.model.I_AD_Reference.class);
    /** Column name AD_Reference_Source_ID */
    public static final String COLUMNNAME_AD_Reference_Source_ID = "AD_Reference_Source_ID";

	/**
	 * Set Target Reference.
	 * For directed relation types, the table and where clause of this reference's AD_Ref_Table is used to select the relation partners for a given source-record (e.g. the shipments for a given order).
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_Reference_Target_ID (int AD_Reference_Target_ID);

	/**
	 * Get Target Reference.
	 * For directed relation types, the table and where clause of this reference's AD_Ref_Table is used to select the relation partners for a given source-record (e.g. the shipments for a given order).
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_Reference_Target_ID();

	public org.compiere.model.I_AD_Reference getAD_Reference_Target();

	public void setAD_Reference_Target(org.compiere.model.I_AD_Reference AD_Reference_Target);

    /** Column definition for AD_Reference_Target_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_RelationType, org.compiere.model.I_AD_Reference> COLUMN_AD_Reference_Target_ID = new org.adempiere.model.ModelColumn<I_AD_RelationType, org.compiere.model.I_AD_Reference>(I_AD_RelationType.class, "AD_Reference_Target_ID", org.compiere.model.I_AD_Reference.class);
    /** Column name AD_Reference_Target_ID */
    public static final String COLUMNNAME_AD_Reference_Target_ID = "AD_Reference_Target_ID";

	/**
	 * Set Relation Type.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_RelationType_ID (int AD_RelationType_ID);

	/**
	 * Get Relation Type.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_RelationType_ID();

    /** Column definition for AD_RelationType_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_RelationType, Object> COLUMN_AD_RelationType_ID = new org.adempiere.model.ModelColumn<I_AD_RelationType, Object>(I_AD_RelationType.class, "AD_RelationType_ID", null);
    /** Column name AD_RelationType_ID */
    public static final String COLUMNNAME_AD_RelationType_ID = "AD_RelationType_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getCreated();

    /** Column definition for Created */
    public static final org.adempiere.model.ModelColumn<I_AD_RelationType, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_AD_RelationType, Object>(I_AD_RelationType.class, "Created", null);
    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/**
	 * Get Created By.
	 * User who created this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getCreatedBy();

    /** Column definition for CreatedBy */
    public static final org.adempiere.model.ModelColumn<I_AD_RelationType, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_AD_RelationType, org.compiere.model.I_AD_User>(I_AD_RelationType.class, "CreatedBy", org.compiere.model.I_AD_User.class);
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
    public static final org.adempiere.model.ModelColumn<I_AD_RelationType, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<I_AD_RelationType, Object>(I_AD_RelationType.class, "Description", null);
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
    public static final org.adempiere.model.ModelColumn<I_AD_RelationType, Object> COLUMN_EntityType = new org.adempiere.model.ModelColumn<I_AD_RelationType, Object>(I_AD_RelationType.class, "EntityType", null);
    /** Column name EntityType */
    public static final String COLUMNNAME_EntityType = "EntityType";

	/**
	 * Set Interner Name.
	 * Generally used to give records a name that can be safely referenced from code.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setInternalName (java.lang.String InternalName);

	/**
	 * Get Interner Name.
	 * Generally used to give records a name that can be safely referenced from code.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getInternalName();

    /** Column definition for InternalName */
    public static final org.adempiere.model.ModelColumn<I_AD_RelationType, Object> COLUMN_InternalName = new org.adempiere.model.ModelColumn<I_AD_RelationType, Object>(I_AD_RelationType.class, "InternalName", null);
    /** Column name InternalName */
    public static final String COLUMNNAME_InternalName = "InternalName";

	/**
	 * Set Active.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsActive (boolean IsActive);

	/**
	 * Get Active.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isActive();

    /** Column definition for IsActive */
    public static final org.adempiere.model.ModelColumn<I_AD_RelationType, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_AD_RelationType, Object>(I_AD_RelationType.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Directed.
	 * Tells whether one "sees" the other end of the relation from each end or just from the source.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsDirected (boolean IsDirected);

	/**
	 * Get Directed.
	 * Tells whether one "sees" the other end of the relation from each end or just from the source.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isDirected();

    /** Column definition for IsDirected */
    public static final org.adempiere.model.ModelColumn<I_AD_RelationType, Object> COLUMN_IsDirected = new org.adempiere.model.ModelColumn<I_AD_RelationType, Object>(I_AD_RelationType.class, "IsDirected", null);
    /** Column name IsDirected */
    public static final String COLUMNNAME_IsDirected = "IsDirected";

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
    public static final org.adempiere.model.ModelColumn<I_AD_RelationType, Object> COLUMN_Name = new org.adempiere.model.ModelColumn<I_AD_RelationType, Object>(I_AD_RelationType.class, "Name", null);
    /** Column name Name */
    public static final String COLUMNNAME_Name = "Name";

	/**
	 * Set Source Role.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setRole_Source (java.lang.String Role_Source);

	/**
	 * Get Source Role.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getRole_Source();

    /** Column definition for Role_Source */
    public static final org.adempiere.model.ModelColumn<I_AD_RelationType, Object> COLUMN_Role_Source = new org.adempiere.model.ModelColumn<I_AD_RelationType, Object>(I_AD_RelationType.class, "Role_Source", null);
    /** Column name Role_Source */
    public static final String COLUMNNAME_Role_Source = "Role_Source";

	/**
	 * Set Target Role.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setRole_Target (java.lang.String Role_Target);

	/**
	 * Get Target Role.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getRole_Target();

    /** Column definition for Role_Target */
    public static final org.adempiere.model.ModelColumn<I_AD_RelationType, Object> COLUMN_Role_Target = new org.adempiere.model.ModelColumn<I_AD_RelationType, Object>(I_AD_RelationType.class, "Role_Target", null);
    /** Column name Role_Target */
    public static final String COLUMNNAME_Role_Target = "Role_Target";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getUpdated();

    /** Column definition for Updated */
    public static final org.adempiere.model.ModelColumn<I_AD_RelationType, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_AD_RelationType, Object>(I_AD_RelationType.class, "Updated", null);
    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Updated By.
	 * User who updated this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getUpdatedBy();

    /** Column definition for UpdatedBy */
    public static final org.adempiere.model.ModelColumn<I_AD_RelationType, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_AD_RelationType, org.compiere.model.I_AD_User>(I_AD_RelationType.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
