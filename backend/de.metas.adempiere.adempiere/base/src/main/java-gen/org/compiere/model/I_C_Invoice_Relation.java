package org.compiere.model;

import org.adempiere.model.ModelColumn;

/** Generated Interface for C_Invoice_Relation
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_C_Invoice_Relation 
{

	String Table_Name = "C_Invoice_Relation";

//	/** AD_Table_ID=541928 */
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
	 * Set Source Invoice.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Invoice_From_ID (int C_Invoice_From_ID);

	/**
	 * Get Source Invoice.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Invoice_From_ID();

	org.compiere.model.I_C_Invoice getC_Invoice_From();

	void setC_Invoice_From(org.compiere.model.I_C_Invoice C_Invoice_From);

	ModelColumn<I_C_Invoice_Relation, org.compiere.model.I_C_Invoice> COLUMN_C_Invoice_From_ID = new ModelColumn<>(I_C_Invoice_Relation.class, "C_Invoice_From_ID", org.compiere.model.I_C_Invoice.class);
	String COLUMNNAME_C_Invoice_From_ID = "C_Invoice_From_ID";

	/**
	 * Set C_Invoice_Relation.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Invoice_Relation_ID (int C_Invoice_Relation_ID);

	/**
	 * Get C_Invoice_Relation.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Invoice_Relation_ID();

	ModelColumn<I_C_Invoice_Relation, Object> COLUMN_C_Invoice_Relation_ID = new ModelColumn<>(I_C_Invoice_Relation.class, "C_Invoice_Relation_ID", null);
	String COLUMNNAME_C_Invoice_Relation_ID = "C_Invoice_Relation_ID";

	/**
	 * Set Invoice Relation Type.
	 * The type of the relation between the Source and Target invoice
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Invoice_Relation_Type (java.lang.String C_Invoice_Relation_Type);

	/**
	 * Get Invoice Relation Type.
	 * The type of the relation between the Source and Target invoice
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getC_Invoice_Relation_Type();

	ModelColumn<I_C_Invoice_Relation, Object> COLUMN_C_Invoice_Relation_Type = new ModelColumn<>(I_C_Invoice_Relation.class, "C_Invoice_Relation_Type", null);
	String COLUMNNAME_C_Invoice_Relation_Type = "C_Invoice_Relation_Type";

	/**
	 * Set Target Invoice.
	 * The target invoice of a relation
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Invoice_To_ID (int C_Invoice_To_ID);

	/**
	 * Get Target Invoice.
	 * The target invoice of a relation
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Invoice_To_ID();

	org.compiere.model.I_C_Invoice getC_Invoice_To();

	void setC_Invoice_To(org.compiere.model.I_C_Invoice C_Invoice_To);

	ModelColumn<I_C_Invoice_Relation, org.compiere.model.I_C_Invoice> COLUMN_C_Invoice_To_ID = new ModelColumn<>(I_C_Invoice_Relation.class, "C_Invoice_To_ID", org.compiere.model.I_C_Invoice.class);
	String COLUMNNAME_C_Invoice_To_ID = "C_Invoice_To_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_C_Invoice_Relation, Object> COLUMN_Created = new ModelColumn<>(I_C_Invoice_Relation.class, "Created", null);
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

	ModelColumn<I_C_Invoice_Relation, Object> COLUMN_IsActive = new ModelColumn<>(I_C_Invoice_Relation.class, "IsActive", null);
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

	ModelColumn<I_C_Invoice_Relation, Object> COLUMN_Updated = new ModelColumn<>(I_C_Invoice_Relation.class, "Updated", null);
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
