package org.compiere.model;

import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for AD_WF_ProcessData
 *  @author metasfresh (generated) 
 */
public interface I_AD_WF_ProcessData 
{

	String Table_Name = "AD_WF_ProcessData";

//	/** AD_Table_ID=648 */
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
	 * Set Workflow-Prozess.
	 * Actual Workflow Process Instance
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_WF_Process_ID (int AD_WF_Process_ID);

	/**
	 * Get Workflow-Prozess.
	 * Actual Workflow Process Instance
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_WF_Process_ID();

	String COLUMNNAME_AD_WF_Process_ID = "AD_WF_Process_ID";

	/**
	 * Set Workflow Process Data.
	 * Workflow Process Context
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_WF_ProcessData_ID (int AD_WF_ProcessData_ID);

	/**
	 * Get Workflow Process Data.
	 * Workflow Process Context
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_WF_ProcessData_ID();

	ModelColumn<I_AD_WF_ProcessData, Object> COLUMN_AD_WF_ProcessData_ID = new ModelColumn<>(I_AD_WF_ProcessData.class, "AD_WF_ProcessData_ID", null);
	String COLUMNNAME_AD_WF_ProcessData_ID = "AD_WF_ProcessData_ID";

	/**
	 * Set Attribute Name.
	 * Name of the Attribute
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAttributeName (java.lang.String AttributeName);

	/**
	 * Get Attribute Name.
	 * Name of the Attribute
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getAttributeName();

	ModelColumn<I_AD_WF_ProcessData, Object> COLUMN_AttributeName = new ModelColumn<>(I_AD_WF_ProcessData.class, "AttributeName", null);
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

	ModelColumn<I_AD_WF_ProcessData, Object> COLUMN_AttributeValue = new ModelColumn<>(I_AD_WF_ProcessData.class, "AttributeValue", null);
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

	ModelColumn<I_AD_WF_ProcessData, Object> COLUMN_Created = new ModelColumn<>(I_AD_WF_ProcessData.class, "Created", null);
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

	ModelColumn<I_AD_WF_ProcessData, Object> COLUMN_IsActive = new ModelColumn<>(I_AD_WF_ProcessData.class, "IsActive", null);
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

	ModelColumn<I_AD_WF_ProcessData, Object> COLUMN_Updated = new ModelColumn<>(I_AD_WF_ProcessData.class, "Updated", null);
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
