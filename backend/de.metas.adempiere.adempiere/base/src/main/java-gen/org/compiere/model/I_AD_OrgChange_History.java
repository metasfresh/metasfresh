package org.compiere.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;

/** Generated Interface for AD_OrgChange_History
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_AD_OrgChange_History 
{

	String Table_Name = "AD_OrgChange_History";

//	/** AD_Table_ID=541601 */
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
	 * Set Organization Change History.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_OrgChange_History_ID (int AD_OrgChange_History_ID);

	/**
	 * Get Organization Change History.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_OrgChange_History_ID();

	ModelColumn<I_AD_OrgChange_History, Object> COLUMN_AD_OrgChange_History_ID = new ModelColumn<>(I_AD_OrgChange_History.class, "AD_OrgChange_History_ID", null);
	String COLUMNNAME_AD_OrgChange_History_ID = "AD_OrgChange_History_ID";

	/**
	 * Set Organization From.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_Org_From_ID (int AD_Org_From_ID);

	/**
	 * Get Organization From.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_Org_From_ID();

	String COLUMNNAME_AD_Org_From_ID = "AD_Org_From_ID";

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
	 * Set Org Mapping.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_Org_Mapping_ID (int AD_Org_Mapping_ID);

	/**
	 * Get Org Mapping.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_Org_Mapping_ID();

	@Nullable org.compiere.model.I_AD_Org_Mapping getAD_Org_Mapping();

	void setAD_Org_Mapping(@Nullable org.compiere.model.I_AD_Org_Mapping AD_Org_Mapping);

	ModelColumn<I_AD_OrgChange_History, org.compiere.model.I_AD_Org_Mapping> COLUMN_AD_Org_Mapping_ID = new ModelColumn<>(I_AD_OrgChange_History.class, "AD_Org_Mapping_ID", org.compiere.model.I_AD_Org_Mapping.class);
	String COLUMNNAME_AD_Org_Mapping_ID = "AD_Org_Mapping_ID";

	/**
	 * Set Inter-Organization.
	 * Organization valid for intercompany documents
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_OrgTo_ID (int AD_OrgTo_ID);

	/**
	 * Get Inter-Organization.
	 * Organization valid for intercompany documents
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_OrgTo_ID();

	String COLUMNNAME_AD_OrgTo_ID = "AD_OrgTo_ID";

	/**
	 * Set Partner From.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_BPartner_From_ID (int C_BPartner_From_ID);

	/**
	 * Get Partner From.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_BPartner_From_ID();

	String COLUMNNAME_C_BPartner_From_ID = "C_BPartner_From_ID";

	/**
	 * Set Partner To.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_BPartner_To_ID (int C_BPartner_To_ID);

	/**
	 * Get Partner To.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_BPartner_To_ID();

	String COLUMNNAME_C_BPartner_To_ID = "C_BPartner_To_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_AD_OrgChange_History, Object> COLUMN_Created = new ModelColumn<>(I_AD_OrgChange_History.class, "Created", null);
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
	 * Set Organization Change Date.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDate_OrgChange (java.sql.Timestamp Date_OrgChange);

	/**
	 * Get Organization Change Date.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getDate_OrgChange();

	ModelColumn<I_AD_OrgChange_History, Object> COLUMN_Date_OrgChange = new ModelColumn<>(I_AD_OrgChange_History.class, "Date_OrgChange", null);
	String COLUMNNAME_Date_OrgChange = "Date_OrgChange";

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

	ModelColumn<I_AD_OrgChange_History, Object> COLUMN_IsActive = new ModelColumn<>(I_AD_OrgChange_History.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Close Invoice Candidate.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsCloseInvoiceCandidate (boolean IsCloseInvoiceCandidate);

	/**
	 * Get Close Invoice Candidate.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isCloseInvoiceCandidate();

	ModelColumn<I_AD_OrgChange_History, Object> COLUMN_IsCloseInvoiceCandidate = new ModelColumn<>(I_AD_OrgChange_History.class, "IsCloseInvoiceCandidate", null);
	String COLUMNNAME_IsCloseInvoiceCandidate = "IsCloseInvoiceCandidate";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_AD_OrgChange_History, Object> COLUMN_Updated = new ModelColumn<>(I_AD_OrgChange_History.class, "Updated", null);
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
