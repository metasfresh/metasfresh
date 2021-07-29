package de.metas.serviceprovider.model;

import org.adempiere.model.ModelColumn;

/** Generated Interface for S_IssueLabel
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_S_IssueLabel 
{

	String Table_Name = "S_IssueLabel";

//	/** AD_Table_ID=541467 */
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
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Organisation.
	 * Organisational entity within client
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_Org_ID();

	String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_S_IssueLabel, Object> COLUMN_Created = new ModelColumn<>(I_S_IssueLabel.class, "Created", null);
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

	ModelColumn<I_S_IssueLabel, Object> COLUMN_IsActive = new ModelColumn<>(I_S_IssueLabel.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Label.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setLabel (java.lang.String Label);

	/**
	 * Get Label.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getLabel();

	ModelColumn<I_S_IssueLabel, Object> COLUMN_Label = new ModelColumn<>(I_S_IssueLabel.class, "Label", null);
	String COLUMNNAME_Label = "Label";

	/**
	 * Set Issue.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setS_Issue_ID (int S_Issue_ID);

	/**
	 * Get Issue.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getS_Issue_ID();

	de.metas.serviceprovider.model.I_S_Issue getS_Issue();

	void setS_Issue(de.metas.serviceprovider.model.I_S_Issue S_Issue);

	ModelColumn<I_S_IssueLabel, de.metas.serviceprovider.model.I_S_Issue> COLUMN_S_Issue_ID = new ModelColumn<>(I_S_IssueLabel.class, "S_Issue_ID", de.metas.serviceprovider.model.I_S_Issue.class);
	String COLUMNNAME_S_Issue_ID = "S_Issue_ID";

	/**
	 * Set Issue label ID.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setS_IssueLabel_ID (int S_IssueLabel_ID);

	/**
	 * Get Issue label ID.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getS_IssueLabel_ID();

	ModelColumn<I_S_IssueLabel, Object> COLUMN_S_IssueLabel_ID = new ModelColumn<>(I_S_IssueLabel.class, "S_IssueLabel_ID", null);
	String COLUMNNAME_S_IssueLabel_ID = "S_IssueLabel_ID";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_S_IssueLabel, Object> COLUMN_Updated = new ModelColumn<>(I_S_IssueLabel.class, "Updated", null);
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
