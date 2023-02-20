package org.compiere.model;

import org.adempiere.model.ModelColumn;

/** Generated Interface for AD_DocType_CountryBased
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_AD_DocType_CountryBased 
{

	String Table_Name = "AD_DocType_CountryBased";

//	/** AD_Table_ID=542310 */
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
	 * Set DocType CountryBased.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_DocType_CountryBased_ID (int AD_DocType_CountryBased_ID);

	/**
	 * Get DocType CountryBased.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_DocType_CountryBased_ID();

	ModelColumn<I_AD_DocType_CountryBased, Object> COLUMN_AD_DocType_CountryBased_ID = new ModelColumn<>(I_AD_DocType_CountryBased.class, "AD_DocType_CountryBased_ID", null);
	String COLUMNNAME_AD_DocType_CountryBased_ID = "AD_DocType_CountryBased_ID";

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
	 * Set Country.
	 * Country
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Country_ID (int C_Country_ID);

	/**
	 * Get Country.
	 * Country
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Country_ID();

	org.compiere.model.I_C_Country getC_Country();

	void setC_Country(org.compiere.model.I_C_Country C_Country);

	ModelColumn<I_AD_DocType_CountryBased, org.compiere.model.I_C_Country> COLUMN_C_Country_ID = new ModelColumn<>(I_AD_DocType_CountryBased.class, "C_Country_ID", org.compiere.model.I_C_Country.class);
	String COLUMNNAME_C_Country_ID = "C_Country_ID";

	/**
	 * Set Document Type.
	 * Document type or rules
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_DocType_ID (int C_DocType_ID);

	/**
	 * Get Document Type.
	 * Document type or rules
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_DocType_ID();

	String COLUMNNAME_C_DocType_ID = "C_DocType_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_AD_DocType_CountryBased, Object> COLUMN_Created = new ModelColumn<>(I_AD_DocType_CountryBased.class, "Created", null);
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
	 * Set Document Sequence.
	 * Document sequence determines the numbering of documents
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDocNoSequence_ID (int DocNoSequence_ID);

	/**
	 * Get Document Sequence.
	 * Document sequence determines the numbering of documents
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getDocNoSequence_ID();

	org.compiere.model.I_AD_Sequence getDocNoSequence();

	void setDocNoSequence(org.compiere.model.I_AD_Sequence DocNoSequence);

	ModelColumn<I_AD_DocType_CountryBased, org.compiere.model.I_AD_Sequence> COLUMN_DocNoSequence_ID = new ModelColumn<>(I_AD_DocType_CountryBased.class, "DocNoSequence_ID", org.compiere.model.I_AD_Sequence.class);
	String COLUMNNAME_DocNoSequence_ID = "DocNoSequence_ID";

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

	ModelColumn<I_AD_DocType_CountryBased, Object> COLUMN_IsActive = new ModelColumn<>(I_AD_DocType_CountryBased.class, "IsActive", null);
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

	ModelColumn<I_AD_DocType_CountryBased, Object> COLUMN_Updated = new ModelColumn<>(I_AD_DocType_CountryBased.class, "Updated", null);
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
