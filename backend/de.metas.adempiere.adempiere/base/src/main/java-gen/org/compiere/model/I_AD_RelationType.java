package org.compiere.model;

import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for AD_RelationType
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_AD_RelationType 
{

	String Table_Name = "AD_RelationType";

//	/** AD_Table_ID=53246 */
//	int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);


	/**
	 * Get Client.
	 * Client/Tenant for this installation.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_Client_ID();

	String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Organisation.
	 * Organisational entity within client
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Organisation.
	 * Organisational entity within client
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_Org_ID();

	String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Source Reference.
	 * For directed relation types, the where clause of this reference's AD_Ref_Table is used to decide if a given record is a possible relation source.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_Reference_Source_ID (int AD_Reference_Source_ID);

	/**
	 * Get Source Reference.
	 * For directed relation types, the where clause of this reference's AD_Ref_Table is used to decide if a given record is a possible relation source.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_Reference_Source_ID();

	@Nullable org.compiere.model.I_AD_Reference getAD_Reference_Source();

	void setAD_Reference_Source(@Nullable org.compiere.model.I_AD_Reference AD_Reference_Source);

	ModelColumn<I_AD_RelationType, org.compiere.model.I_AD_Reference> COLUMN_AD_Reference_Source_ID = new ModelColumn<>(I_AD_RelationType.class, "AD_Reference_Source_ID", org.compiere.model.I_AD_Reference.class);
	String COLUMNNAME_AD_Reference_Source_ID = "AD_Reference_Source_ID";

	/**
	 * Set Target Reference.
	 * For directed relation types, the table and where clause of this reference's AD_Ref_Table is used to select the relation partners for a given source-record (e.g. the shipments for a given order).
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_Reference_Target_ID (int AD_Reference_Target_ID);

	/**
	 * Get Target Reference.
	 * For directed relation types, the table and where clause of this reference's AD_Ref_Table is used to select the relation partners for a given source-record (e.g. the shipments for a given order).
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_Reference_Target_ID();

	@Nullable org.compiere.model.I_AD_Reference getAD_Reference_Target();

	void setAD_Reference_Target(@Nullable org.compiere.model.I_AD_Reference AD_Reference_Target);

	ModelColumn<I_AD_RelationType, org.compiere.model.I_AD_Reference> COLUMN_AD_Reference_Target_ID = new ModelColumn<>(I_AD_RelationType.class, "AD_Reference_Target_ID", org.compiere.model.I_AD_Reference.class);
	String COLUMNNAME_AD_Reference_Target_ID = "AD_Reference_Target_ID";

	/**
	 * Set Relation Type.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_RelationType_ID (int AD_RelationType_ID);

	/**
	 * Get Relation Type.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_RelationType_ID();

	ModelColumn<I_AD_RelationType, Object> COLUMN_AD_RelationType_ID = new ModelColumn<>(I_AD_RelationType.class, "AD_RelationType_ID", null);
	String COLUMNNAME_AD_RelationType_ID = "AD_RelationType_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_AD_RelationType, Object> COLUMN_Created = new ModelColumn<>(I_AD_RelationType.class, "Created", null);
	String COLUMNNAME_Created = "Created";

	/**
	 * Get Created By.
	 * User who created this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getCreatedBy();

	String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Description.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDescription (@Nullable java.lang.String Description);

	/**
	 * Get Description.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDescription();

	ModelColumn<I_AD_RelationType, Object> COLUMN_Description = new ModelColumn<>(I_AD_RelationType.class, "Description", null);
	String COLUMNNAME_Description = "Description";

	/**
	 * Set Entitäts-Art.
	 * Dictionary Entity Type;
 Determines ownership and synchronization
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setEntityType (java.lang.String EntityType);

	/**
	 * Get Entitäts-Art.
	 * Dictionary Entity Type;
 Determines ownership and synchronization
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getEntityType();

	ModelColumn<I_AD_RelationType, Object> COLUMN_EntityType = new ModelColumn<>(I_AD_RelationType.class, "EntityType", null);
	String COLUMNNAME_EntityType = "EntityType";

	/**
	 * Set Internal Name.
	 * Generally used to give records a name that can be safely referenced from code.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setInternalName (@Nullable java.lang.String InternalName);

	/**
	 * Get Internal Name.
	 * Generally used to give records a name that can be safely referenced from code.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getInternalName();

	ModelColumn<I_AD_RelationType, Object> COLUMN_InternalName = new ModelColumn<>(I_AD_RelationType.class, "InternalName", null);
	String COLUMNNAME_InternalName = "InternalName";

	/**
	 * Set Active.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsActive (boolean IsActive);

	/**
	 * Get Active.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isActive();

	ModelColumn<I_AD_RelationType, Object> COLUMN_IsActive = new ModelColumn<>(I_AD_RelationType.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set IsReferenceTarget.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsTableRecordIdTarget (boolean IsTableRecordIdTarget);

	/**
	 * Get IsReferenceTarget.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isTableRecordIdTarget();

	ModelColumn<I_AD_RelationType, Object> COLUMN_IsTableRecordIdTarget = new ModelColumn<>(I_AD_RelationType.class, "IsTableRecordIdTarget", null);
	String COLUMNNAME_IsTableRecordIdTarget = "IsTableRecordIdTarget";

	/**
	 * Set Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setName (java.lang.String Name);

	/**
	 * Get Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getName();

	ModelColumn<I_AD_RelationType, Object> COLUMN_Name = new ModelColumn<>(I_AD_RelationType.class, "Name", null);
	String COLUMNNAME_Name = "Name";

	/**
	 * Set Source Role.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setRole_Source (@Nullable java.lang.String Role_Source);

	/**
	 * Get Source Role.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getRole_Source();

	ModelColumn<I_AD_RelationType, Object> COLUMN_Role_Source = new ModelColumn<>(I_AD_RelationType.class, "Role_Source", null);
	String COLUMNNAME_Role_Source = "Role_Source";

	/**
	 * Set Target Role.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setRole_Target (@Nullable java.lang.String Role_Target);

	/**
	 * Get Target Role.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getRole_Target();

	ModelColumn<I_AD_RelationType, Object> COLUMN_Role_Target = new ModelColumn<>(I_AD_RelationType.class, "Role_Target", null);
	String COLUMNNAME_Role_Target = "Role_Target";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_AD_RelationType, Object> COLUMN_Updated = new ModelColumn<>(I_AD_RelationType.class, "Updated", null);
	String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Updated By.
	 * User who updated this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getUpdatedBy();

	String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
