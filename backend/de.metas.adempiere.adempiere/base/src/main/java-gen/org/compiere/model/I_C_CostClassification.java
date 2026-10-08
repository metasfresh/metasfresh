package org.compiere.model;

import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for C_CostClassification
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_C_CostClassification 
{

	String Table_Name = "C_CostClassification";

//	/** AD_Table_ID=542567 */
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
	 * Set Cost Classification Category.
	 * Groups cost classifications into higher-level analytical categories to support structured and aggregated controlling reports.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_CostClassification_Category_ID (int C_CostClassification_Category_ID);

	/**
	 * Get Cost Classification Category.
	 * Groups cost classifications into higher-level analytical categories to support structured and aggregated controlling reports.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_CostClassification_Category_ID();

	ModelColumn<I_C_CostClassification, org.compiere.model.I_C_CostClassification_Category> COLUMN_C_CostClassification_Category_ID = new ModelColumn<>(I_C_CostClassification.class, "C_CostClassification_Category_ID", org.compiere.model.I_C_CostClassification_Category.class);
	String COLUMNNAME_C_CostClassification_Category_ID = "C_CostClassification_Category_ID";

	/**
	 * Set Cost Classification.
	 * Defines the economic nature of a cost or revenue (Kostenart) as an independent controlling dimension for financial accounting and reporting.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_CostClassification_ID (int C_CostClassification_ID);

	/**
	 * Get Cost Classification.
	 * Defines the economic nature of a cost or revenue (Kostenart) as an independent controlling dimension for financial accounting and reporting.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_CostClassification_ID();

	ModelColumn<I_C_CostClassification, Object> COLUMN_C_CostClassification_ID = new ModelColumn<>(I_C_CostClassification.class, "C_CostClassification_ID", null);
	String COLUMNNAME_C_CostClassification_ID = "C_CostClassification_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_C_CostClassification, Object> COLUMN_Created = new ModelColumn<>(I_C_CostClassification.class, "Created", null);
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

	ModelColumn<I_C_CostClassification, Object> COLUMN_Description = new ModelColumn<>(I_C_CostClassification.class, "Description", null);
	String COLUMNNAME_Description = "Description";

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

	ModelColumn<I_C_CostClassification, Object> COLUMN_IsActive = new ModelColumn<>(I_C_CostClassification.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

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

	ModelColumn<I_C_CostClassification, Object> COLUMN_Name = new ModelColumn<>(I_C_CostClassification.class, "Name", null);
	String COLUMNNAME_Name = "Name";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_C_CostClassification, Object> COLUMN_Updated = new ModelColumn<>(I_C_CostClassification.class, "Updated", null);
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

	/**
	 * Set Search Key.
	 * Search key for the record in the format required - must be unique
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setValue (@Nullable java.lang.String Value);

	/**
	 * Get Search Key.
	 * Search key for the record in the format required - must be unique
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getValue();

	ModelColumn<I_C_CostClassification, Object> COLUMN_Value = new ModelColumn<>(I_C_CostClassification.class, "Value", null);
	String COLUMNNAME_Value = "Value";
}
