package org.compiere.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;

/** Generated Interface for C_BPartner_QuickInput_RelatedRecord1
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_C_BPartner_QuickInput_RelatedRecord1 
{

	String Table_Name = "C_BPartner_QuickInput_RelatedRecord1";

//	/** AD_Table_ID=541862 */
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
	 * Set New BPartner quick input.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_BPartner_QuickInput_ID (int C_BPartner_QuickInput_ID);

	/**
	 * Get New BPartner quick input.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_BPartner_QuickInput_ID();

	@Nullable org.compiere.model.I_C_BPartner_QuickInput getC_BPartner_QuickInput();

	void setC_BPartner_QuickInput(@Nullable org.compiere.model.I_C_BPartner_QuickInput C_BPartner_QuickInput);

	ModelColumn<I_C_BPartner_QuickInput_RelatedRecord1, org.compiere.model.I_C_BPartner_QuickInput> COLUMN_C_BPartner_QuickInput_ID = new ModelColumn<>(I_C_BPartner_QuickInput_RelatedRecord1.class, "C_BPartner_QuickInput_ID", org.compiere.model.I_C_BPartner_QuickInput.class);
	String COLUMNNAME_C_BPartner_QuickInput_ID = "C_BPartner_QuickInput_ID";

	/**
	 * Set C_BPartner_QuickInput_RelatedRecord1.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_BPartner_QuickInput_RelatedRecord1_ID (int C_BPartner_QuickInput_RelatedRecord1_ID);

	/**
	 * Get C_BPartner_QuickInput_RelatedRecord1.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_BPartner_QuickInput_RelatedRecord1_ID();

	ModelColumn<I_C_BPartner_QuickInput_RelatedRecord1, Object> COLUMN_C_BPartner_QuickInput_RelatedRecord1_ID = new ModelColumn<>(I_C_BPartner_QuickInput_RelatedRecord1.class, "C_BPartner_QuickInput_RelatedRecord1_ID", null);
	String COLUMNNAME_C_BPartner_QuickInput_RelatedRecord1_ID = "C_BPartner_QuickInput_RelatedRecord1_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_C_BPartner_QuickInput_RelatedRecord1, Object> COLUMN_Created = new ModelColumn<>(I_C_BPartner_QuickInput_RelatedRecord1.class, "Created", null);
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

	ModelColumn<I_C_BPartner_QuickInput_RelatedRecord1, Object> COLUMN_IsActive = new ModelColumn<>(I_C_BPartner_QuickInput_RelatedRecord1.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Record ID.
	 * Direct internal record ID
	 *
	 * <br>Type: ID
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setRecord_ID (int Record_ID);

	/**
	 * Get Record ID.
	 * Direct internal record ID
	 *
	 * <br>Type: ID
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getRecord_ID();

	ModelColumn<I_C_BPartner_QuickInput_RelatedRecord1, Object> COLUMN_Record_ID = new ModelColumn<>(I_C_BPartner_QuickInput_RelatedRecord1.class, "Record_ID", null);
	String COLUMNNAME_Record_ID = "Record_ID";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_C_BPartner_QuickInput_RelatedRecord1, Object> COLUMN_Updated = new ModelColumn<>(I_C_BPartner_QuickInput_RelatedRecord1.class, "Updated", null);
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
