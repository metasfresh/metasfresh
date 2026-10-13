package org.compiere.model;

import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for InvoiceProcessingServiceCompany
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_InvoiceProcessingServiceCompany 
{

	String Table_Name = "InvoiceProcessingServiceCompany";

//	/** AD_Table_ID=541493 */
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
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_InvoiceProcessingServiceCompany, Object> COLUMN_Created = new ModelColumn<>(I_InvoiceProcessingServiceCompany.class, "Created", null);
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
	 * Optional short description of the record.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDescription (@Nullable java.lang.String Description);

	/**
	 * Get Description.
	 * Optional short description of the record.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDescription();

	ModelColumn<I_InvoiceProcessingServiceCompany, Object> COLUMN_Description = new ModelColumn<>(I_InvoiceProcessingServiceCompany.class, "Description", null);
	String COLUMNNAME_Description = "Description";

	/**
	 * Set Invoice Processing Service Company.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setInvoiceProcessingServiceCompany_ID (int InvoiceProcessingServiceCompany_ID);

	/**
	 * Get Invoice Processing Service Company.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getInvoiceProcessingServiceCompany_ID();

	ModelColumn<I_InvoiceProcessingServiceCompany, Object> COLUMN_InvoiceProcessingServiceCompany_ID = new ModelColumn<>(I_InvoiceProcessingServiceCompany.class, "InvoiceProcessingServiceCompany_ID", null);
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

	ModelColumn<I_InvoiceProcessingServiceCompany, Object> COLUMN_IsActive = new ModelColumn<>(I_InvoiceProcessingServiceCompany.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Service Company.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setServiceCompany_BPartner_ID (int ServiceCompany_BPartner_ID);

	/**
	 * Get Service Company.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getServiceCompany_BPartner_ID();

	String COLUMNNAME_ServiceCompany_BPartner_ID = "ServiceCompany_BPartner_ID";

	/**
	 * Set Name.
	 * Alphanumeric identifier of the entity.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setServiceCompany_BPartner_Name (@Nullable java.lang.String ServiceCompany_BPartner_Name);

	/**
	 * Get Name.
	 * Alphanumeric identifier of the entity.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	@Nullable java.lang.String getServiceCompany_BPartner_Name();

	ModelColumn<I_InvoiceProcessingServiceCompany, Object> COLUMN_ServiceCompany_BPartner_Name = new ModelColumn<>(I_InvoiceProcessingServiceCompany.class, "ServiceCompany_BPartner_Name", null);
	String COLUMNNAME_ServiceCompany_BPartner_Name = "ServiceCompany_BPartner_Name";

	/**
	 * Set Service Fee Product.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setServiceFee_Product_ID (int ServiceFee_Product_ID);

	/**
	 * Get Service Fee Product.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getServiceFee_Product_ID();

	String COLUMNNAME_ServiceFee_Product_ID = "ServiceFee_Product_ID";

	/**
	 * Set Service Invoice Doc Type.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setServiceInvoice_DocType_ID (int ServiceInvoice_DocType_ID);

	/**
	 * Get Service Invoice Doc Type.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getServiceInvoice_DocType_ID();

	String COLUMNNAME_ServiceInvoice_DocType_ID = "ServiceInvoice_DocType_ID";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_InvoiceProcessingServiceCompany, Object> COLUMN_Updated = new ModelColumn<>(I_InvoiceProcessingServiceCompany.class, "Updated", null);
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
	 * Set Valid From.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setValidFrom (@Nullable java.sql.Timestamp ValidFrom);

	/**
	 * Get Valid From.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getValidFrom();

	ModelColumn<I_InvoiceProcessingServiceCompany, Object> COLUMN_ValidFrom = new ModelColumn<>(I_InvoiceProcessingServiceCompany.class, "ValidFrom", null);
	String COLUMNNAME_ValidFrom = "ValidFrom";
}
