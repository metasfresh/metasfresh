package org.compiere.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;
import java.math.BigDecimal;

/** Generated Interface for InvoiceProcessingServiceCompany_BPartnerAssignment
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_InvoiceProcessingServiceCompany_BPartnerAssignment 
{

	String Table_Name = "InvoiceProcessingServiceCompany_BPartnerAssignment";

    /** AD_Table_ID=541494 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);


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
	 * Set Document Type.
	 * Document type or rules
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_DocType_ID (int C_DocType_ID);

	/**
	 * Get Document Type.
	 * Document type or rules
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
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

	ModelColumn<I_InvoiceProcessingServiceCompany_BPartnerAssignment, Object> COLUMN_Created = new ModelColumn<>(I_InvoiceProcessingServiceCompany_BPartnerAssignment.class, "Created", null);
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
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDescription (@Nullable java.lang.String Description);

	/**
	 * Get Description.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDescription();

	ModelColumn<I_InvoiceProcessingServiceCompany_BPartnerAssignment, Object> COLUMN_Description = new ModelColumn<>(I_InvoiceProcessingServiceCompany_BPartnerAssignment.class, "Description", null);
	String COLUMNNAME_Description = "Description";

	/**
	 * Set Fee Percentage of Invoice Grand Total.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setFeePercentageOfGrandTotal (BigDecimal FeePercentageOfGrandTotal);

	/**
	 * Get Fee Percentage of Invoice Grand Total.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getFeePercentageOfGrandTotal();

	ModelColumn<I_InvoiceProcessingServiceCompany_BPartnerAssignment, Object> COLUMN_FeePercentageOfGrandTotal = new ModelColumn<>(I_InvoiceProcessingServiceCompany_BPartnerAssignment.class, "FeePercentageOfGrandTotal", null);
	String COLUMNNAME_FeePercentageOfGrandTotal = "FeePercentageOfGrandTotal";

	/**
	 * Set Assigned Customers.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setInvoiceProcessingServiceCompany_BPartnerAssignment_ID (int InvoiceProcessingServiceCompany_BPartnerAssignment_ID);

	/**
	 * Get Assigned Customers.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getInvoiceProcessingServiceCompany_BPartnerAssignment_ID();

	ModelColumn<I_InvoiceProcessingServiceCompany_BPartnerAssignment, Object> COLUMN_InvoiceProcessingServiceCompany_BPartnerAssignment_ID = new ModelColumn<>(I_InvoiceProcessingServiceCompany_BPartnerAssignment.class, "InvoiceProcessingServiceCompany_BPartnerAssignment_ID", null);
	String COLUMNNAME_InvoiceProcessingServiceCompany_BPartnerAssignment_ID = "InvoiceProcessingServiceCompany_BPartnerAssignment_ID";

	/**
	 * Set Invoice Processing Service Company.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setInvoiceProcessingServiceCompany_ID (int InvoiceProcessingServiceCompany_ID);

	/**
	 * Get Invoice Processing Service Company.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getInvoiceProcessingServiceCompany_ID();

	org.compiere.model.I_InvoiceProcessingServiceCompany getInvoiceProcessingServiceCompany();

	void setInvoiceProcessingServiceCompany(org.compiere.model.I_InvoiceProcessingServiceCompany InvoiceProcessingServiceCompany);

	ModelColumn<I_InvoiceProcessingServiceCompany_BPartnerAssignment, org.compiere.model.I_InvoiceProcessingServiceCompany> COLUMN_InvoiceProcessingServiceCompany_ID = new ModelColumn<>(I_InvoiceProcessingServiceCompany_BPartnerAssignment.class, "InvoiceProcessingServiceCompany_ID", org.compiere.model.I_InvoiceProcessingServiceCompany.class);
	String COLUMNNAME_InvoiceProcessingServiceCompany_ID = "InvoiceProcessingServiceCompany_ID";

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

	ModelColumn<I_InvoiceProcessingServiceCompany_BPartnerAssignment, Object> COLUMN_IsActive = new ModelColumn<>(I_InvoiceProcessingServiceCompany_BPartnerAssignment.class, "IsActive", null);
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

	ModelColumn<I_InvoiceProcessingServiceCompany_BPartnerAssignment, Object> COLUMN_Updated = new ModelColumn<>(I_InvoiceProcessingServiceCompany_BPartnerAssignment.class, "Updated", null);
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
