package org.compiere.model;

import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for AD_WF_ActivityResult
 *  @author metasfresh (generated) 
 */
public interface I_AD_WF_ActivityResult 
{

	String Table_Name = "AD_WF_ActivityResult";

//	/** AD_Table_ID=650 */
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
	 * Set Workflow-Aktivität.
	 * Workflow Activity
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_WF_Activity_ID (int AD_WF_Activity_ID);

	/**
	 * Get Workflow-Aktivität.
	 * Workflow Activity
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_WF_Activity_ID();

	String COLUMNNAME_AD_WF_Activity_ID = "AD_WF_Activity_ID";

	/**
	 * Set Workflow Activity Result.
	 * Result of the Workflow Process Activity
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_WF_ActivityResult_ID (int AD_WF_ActivityResult_ID);

	/**
	 * Get Workflow Activity Result.
	 * Result of the Workflow Process Activity
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_WF_ActivityResult_ID();

	ModelColumn<I_AD_WF_ActivityResult, Object> COLUMN_AD_WF_ActivityResult_ID = new ModelColumn<>(I_AD_WF_ActivityResult.class, "AD_WF_ActivityResult_ID", null);
	String COLUMNNAME_AD_WF_ActivityResult_ID = "AD_WF_ActivityResult_ID";

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

	ModelColumn<I_AD_WF_ActivityResult, Object> COLUMN_AttributeName = new ModelColumn<>(I_AD_WF_ActivityResult.class, "AttributeName", null);
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

	ModelColumn<I_AD_WF_ActivityResult, Object> COLUMN_AttributeValue = new ModelColumn<>(I_AD_WF_ActivityResult.class, "AttributeValue", null);
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

	ModelColumn<I_AD_WF_ActivityResult, Object> COLUMN_Created = new ModelColumn<>(I_AD_WF_ActivityResult.class, "Created", null);
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

	ModelColumn<I_AD_WF_ActivityResult, Object> COLUMN_Description = new ModelColumn<>(I_AD_WF_ActivityResult.class, "Description", null);
	String COLUMNNAME_Description = "Description";

	/**
	 * Set Help.
	 * Comment or Hint
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setHelp (@Nullable java.lang.String Help);

	/**
	 * Get Help.
	 * Comment or Hint
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getHelp();

	ModelColumn<I_AD_WF_ActivityResult, Object> COLUMN_Help = new ModelColumn<>(I_AD_WF_ActivityResult.class, "Help", null);
	String COLUMNNAME_Help = "Help";

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

	ModelColumn<I_AD_WF_ActivityResult, Object> COLUMN_IsActive = new ModelColumn<>(I_AD_WF_ActivityResult.class, "IsActive", null);
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

	ModelColumn<I_AD_WF_ActivityResult, Object> COLUMN_Updated = new ModelColumn<>(I_AD_WF_ActivityResult.class, "Updated", null);
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
