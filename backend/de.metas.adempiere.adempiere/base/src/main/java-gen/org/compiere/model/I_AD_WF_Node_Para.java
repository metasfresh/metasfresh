package org.compiere.model;

import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for AD_WF_Node_Para
 *  @author metasfresh (generated) 
 */
public interface I_AD_WF_Node_Para 
{

	String Table_Name = "AD_WF_Node_Para";

//	/** AD_Table_ID=643 */
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
	 * Set Prozess-Parameter.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_Process_Para_ID (int AD_Process_Para_ID);

	/**
	 * Get Prozess-Parameter.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_Process_Para_ID();

	@Nullable org.compiere.model.I_AD_Process_Para getAD_Process_Para();

	void setAD_Process_Para(@Nullable org.compiere.model.I_AD_Process_Para AD_Process_Para);

	ModelColumn<I_AD_WF_Node_Para, org.compiere.model.I_AD_Process_Para> COLUMN_AD_Process_Para_ID = new ModelColumn<>(I_AD_WF_Node_Para.class, "AD_Process_Para_ID", org.compiere.model.I_AD_Process_Para.class);
	String COLUMNNAME_AD_Process_Para_ID = "AD_Process_Para_ID";

	/**
	 * Set Knoten.
	 * Workflow Node (activity), step or process
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_WF_Node_ID (int AD_WF_Node_ID);

	/**
	 * Get Knoten.
	 * Workflow Node (activity), step or process
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_WF_Node_ID();

	String COLUMNNAME_AD_WF_Node_ID = "AD_WF_Node_ID";

	/**
	 * Set Workflow Node Parameter.
	 * Workflow Node Execution Parameter
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_WF_Node_Para_ID (int AD_WF_Node_Para_ID);

	/**
	 * Get Workflow Node Parameter.
	 * Workflow Node Execution Parameter
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_WF_Node_Para_ID();

	ModelColumn<I_AD_WF_Node_Para, Object> COLUMN_AD_WF_Node_Para_ID = new ModelColumn<>(I_AD_WF_Node_Para.class, "AD_WF_Node_Para_ID", null);
	String COLUMNNAME_AD_WF_Node_Para_ID = "AD_WF_Node_Para_ID";

	/**
	 * Set Attribute Name.
	 * Name of the Attribute
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAttributeName (@Nullable java.lang.String AttributeName);

	/**
	 * Get Attribute Name.
	 * Name of the Attribute
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getAttributeName();

	ModelColumn<I_AD_WF_Node_Para, Object> COLUMN_AttributeName = new ModelColumn<>(I_AD_WF_Node_Para.class, "AttributeName", null);
	String COLUMNNAME_AttributeName = "AttributeName";

	/**
	 * Set Merkmals-Wert.
	 * Value of the Attribute
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAttributeValue (@Nullable java.lang.String AttributeValue);

	/**
	 * Get Merkmals-Wert.
	 * Value of the Attribute
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getAttributeValue();

	ModelColumn<I_AD_WF_Node_Para, Object> COLUMN_AttributeValue = new ModelColumn<>(I_AD_WF_Node_Para.class, "AttributeValue", null);
	String COLUMNNAME_AttributeValue = "AttributeValue";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_AD_WF_Node_Para, Object> COLUMN_Created = new ModelColumn<>(I_AD_WF_Node_Para.class, "Created", null);
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

	ModelColumn<I_AD_WF_Node_Para, Object> COLUMN_Description = new ModelColumn<>(I_AD_WF_Node_Para.class, "Description", null);
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

	ModelColumn<I_AD_WF_Node_Para, Object> COLUMN_EntityType = new ModelColumn<>(I_AD_WF_Node_Para.class, "EntityType", null);
	String COLUMNNAME_EntityType = "EntityType";

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

	ModelColumn<I_AD_WF_Node_Para, Object> COLUMN_IsActive = new ModelColumn<>(I_AD_WF_Node_Para.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_AD_WF_Node_Para, Object> COLUMN_Updated = new ModelColumn<>(I_AD_WF_Node_Para.class, "Updated", null);
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
