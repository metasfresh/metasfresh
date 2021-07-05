package de.metas.invoicecandidate.model;

import org.adempiere.model.ModelColumn;

/** Generated Interface for C_Invoice_Candidate_HeaderAggregation
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_C_Invoice_Candidate_HeaderAggregation 
{

	String Table_Name = "C_Invoice_Candidate_HeaderAggregation";

//	/** AD_Table_ID=540648 */
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
	 * Set Business Partner.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_BPartner_ID (int C_BPartner_ID);

	/**
	 * Get Business Partner.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_BPartner_ID();

	String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/**
	 * Set Abrechnungsgruppe.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Invoice_Candidate_HeaderAggregation_ID (int C_Invoice_Candidate_HeaderAggregation_ID);

	/**
	 * Get Abrechnungsgruppe.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Invoice_Candidate_HeaderAggregation_ID();

	ModelColumn<I_C_Invoice_Candidate_HeaderAggregation, Object> COLUMN_C_Invoice_Candidate_HeaderAggregation_ID = new ModelColumn<>(I_C_Invoice_Candidate_HeaderAggregation.class, "C_Invoice_Candidate_HeaderAggregation_ID", null);
	String COLUMNNAME_C_Invoice_Candidate_HeaderAggregation_ID = "C_Invoice_Candidate_HeaderAggregation_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_C_Invoice_Candidate_HeaderAggregation, Object> COLUMN_Created = new ModelColumn<>(I_C_Invoice_Candidate_HeaderAggregation.class, "Created", null);
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
	 * Set Kopf-Aggregationsmerkmal.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setHeaderAggregationKey (java.lang.String HeaderAggregationKey);

	/**
	 * Get Kopf-Aggregationsmerkmal.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getHeaderAggregationKey();

	ModelColumn<I_C_Invoice_Candidate_HeaderAggregation, Object> COLUMN_HeaderAggregationKey = new ModelColumn<>(I_C_Invoice_Candidate_HeaderAggregation.class, "HeaderAggregationKey", null);
	String COLUMNNAME_HeaderAggregationKey = "HeaderAggregationKey";

	/**
	 * Set Header aggregation builder.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setHeaderAggregationKeyBuilder_ID (int HeaderAggregationKeyBuilder_ID);

	/**
	 * Get Header aggregation builder.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getHeaderAggregationKeyBuilder_ID();

	ModelColumn<I_C_Invoice_Candidate_HeaderAggregation, Object> COLUMN_HeaderAggregationKeyBuilder_ID = new ModelColumn<>(I_C_Invoice_Candidate_HeaderAggregation.class, "HeaderAggregationKeyBuilder_ID", null);
	String COLUMNNAME_HeaderAggregationKeyBuilder_ID = "HeaderAggregationKeyBuilder_ID";

	/**
	 * Set Abrechnungsgruppe.
	 * Rechnungskandidaten mit der selben Abrechnungsgruppe können zu einer Rechnung zusammengefasst werden
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setInvoicingGroupNo (int InvoicingGroupNo);

	/**
	 * Get Abrechnungsgruppe.
	 * Rechnungskandidaten mit der selben Abrechnungsgruppe können zu einer Rechnung zusammengefasst werden
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getInvoicingGroupNo();

	ModelColumn<I_C_Invoice_Candidate_HeaderAggregation, Object> COLUMN_InvoicingGroupNo = new ModelColumn<>(I_C_Invoice_Candidate_HeaderAggregation.class, "InvoicingGroupNo", null);
	String COLUMNNAME_InvoicingGroupNo = "InvoicingGroupNo";

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

	ModelColumn<I_C_Invoice_Candidate_HeaderAggregation, Object> COLUMN_IsActive = new ModelColumn<>(I_C_Invoice_Candidate_HeaderAggregation.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Sales Transaction.
	 * This is a Sales Transaction
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsSOTrx (boolean IsSOTrx);

	/**
	 * Get Sales Transaction.
	 * This is a Sales Transaction
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isSOTrx();

	ModelColumn<I_C_Invoice_Candidate_HeaderAggregation, Object> COLUMN_IsSOTrx = new ModelColumn<>(I_C_Invoice_Candidate_HeaderAggregation.class, "IsSOTrx", null);
	String COLUMNNAME_IsSOTrx = "IsSOTrx";

	/**
	 * Set Processed.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setProcessed (boolean Processed);

	/**
	 * Get Processed.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isProcessed();

	ModelColumn<I_C_Invoice_Candidate_HeaderAggregation, Object> COLUMN_Processed = new ModelColumn<>(I_C_Invoice_Candidate_HeaderAggregation.class, "Processed", null);
	String COLUMNNAME_Processed = "Processed";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_C_Invoice_Candidate_HeaderAggregation, Object> COLUMN_Updated = new ModelColumn<>(I_C_Invoice_Candidate_HeaderAggregation.class, "Updated", null);
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
