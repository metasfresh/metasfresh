package de.metas.externalsystem.model;

import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for ExternalSystem_Config_Ebay_Mapping
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_ExternalSystem_Config_Ebay_Mapping 
{

	String Table_Name = "ExternalSystem_Config_Ebay_Mapping";

//	/** AD_Table_ID=542124 */
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
	 * Set If business partner exists.
	 * Specifies what to do if a Shopware customer already exists as business partner in metasfresh.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setBPartner_IfExists (java.lang.String BPartner_IfExists);

	/**
	 * Get If business partner exists.
	 * Specifies what to do if a Shopware customer already exists as business partner in metasfresh.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getBPartner_IfExists();

	ModelColumn<I_ExternalSystem_Config_Ebay_Mapping, Object> COLUMN_BPartner_IfExists = new ModelColumn<>(I_ExternalSystem_Config_Ebay_Mapping.class, "BPartner_IfExists", null);
	String COLUMNNAME_BPartner_IfExists = "BPartner_IfExists";

	/**
	 * Set If business partner doesn't exist.
	 * Specifies what to do if a Shopware customer does not yet exist as business partner in metasfresh.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setBPartner_IfNotExists (java.lang.String BPartner_IfNotExists);

	/**
	 * Get If business partner doesn't exist.
	 * Specifies what to do if a Shopware customer does not yet exist as business partner in metasfresh.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getBPartner_IfNotExists();

	ModelColumn<I_ExternalSystem_Config_Ebay_Mapping, Object> COLUMN_BPartner_IfNotExists = new ModelColumn<>(I_ExternalSystem_Config_Ebay_Mapping.class, "BPartner_IfNotExists", null);
	String COLUMNNAME_BPartner_IfNotExists = "BPartner_IfNotExists";

	/**
	 * Set If address exists.
	 * Specifies what to do if a Shopware customer's address already exists in metasfresh.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setBPartnerLocation_IfExists (java.lang.String BPartnerLocation_IfExists);

	/**
	 * Get If address exists.
	 * Specifies what to do if a Shopware customer's address already exists in metasfresh.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getBPartnerLocation_IfExists();

	ModelColumn<I_ExternalSystem_Config_Ebay_Mapping, Object> COLUMN_BPartnerLocation_IfExists = new ModelColumn<>(I_ExternalSystem_Config_Ebay_Mapping.class, "BPartnerLocation_IfExists", null);
	String COLUMNNAME_BPartnerLocation_IfExists = "BPartnerLocation_IfExists";

	/**
	 * Set If addr doesn't exist.
	 * Specifies what to do if a Shopware customer's address does not yet exist in metasfresh.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setBPartnerLocation_IfNotExists (java.lang.String BPartnerLocation_IfNotExists);

	/**
	 * Get If addr doesn't exist.
	 * Specifies what to do if a Shopware customer's address does not yet exist in metasfresh.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getBPartnerLocation_IfNotExists();

	ModelColumn<I_ExternalSystem_Config_Ebay_Mapping, Object> COLUMN_BPartnerLocation_IfNotExists = new ModelColumn<>(I_ExternalSystem_Config_Ebay_Mapping.class, "BPartnerLocation_IfNotExists", null);
	String COLUMNNAME_BPartnerLocation_IfNotExists = "BPartnerLocation_IfNotExists";

	/**
	 * Set Auftrags-Belegart.
	 * Document type used for the orders generated from this order candidate
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_DocTypeOrder_ID (int C_DocTypeOrder_ID);

	/**
	 * Get Auftrags-Belegart.
	 * Document type used for the orders generated from this order candidate
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_DocTypeOrder_ID();

	String COLUMNNAME_C_DocTypeOrder_ID = "C_DocTypeOrder_ID";

	/**
	 * Set Payment Term.
	 * The terms of Payment (timing, discount)
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_PaymentTerm_ID (int C_PaymentTerm_ID);

	/**
	 * Get Payment Term.
	 * The terms of Payment (timing, discount)
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_PaymentTerm_ID();

	String COLUMNNAME_C_PaymentTerm_ID = "C_PaymentTerm_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_ExternalSystem_Config_Ebay_Mapping, Object> COLUMN_Created = new ModelColumn<>(I_ExternalSystem_Config_Ebay_Mapping.class, "Created", null);
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

	ModelColumn<I_ExternalSystem_Config_Ebay_Mapping, Object> COLUMN_Description = new ModelColumn<>(I_ExternalSystem_Config_Ebay_Mapping.class, "Description", null);
	String COLUMNNAME_Description = "Description";

	/**
	 * Set eBay Customer Group.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setEBayCustomerGroup (@Nullable java.lang.String EBayCustomerGroup);

	/**
	 * Get eBay Customer Group.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getEBayCustomerGroup();

	ModelColumn<I_ExternalSystem_Config_Ebay_Mapping, Object> COLUMN_EBayCustomerGroup = new ModelColumn<>(I_ExternalSystem_Config_Ebay_Mapping.class, "EBayCustomerGroup", null);
	String COLUMNNAME_EBayCustomerGroup = "EBayCustomerGroup";

	/**
	 * Set eBay Payment Method.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setEBayPaymentMethod (@Nullable java.lang.String EBayPaymentMethod);

	/**
	 * Get eBay Payment Method.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getEBayPaymentMethod();

	ModelColumn<I_ExternalSystem_Config_Ebay_Mapping, Object> COLUMN_EBayPaymentMethod = new ModelColumn<>(I_ExternalSystem_Config_Ebay_Mapping.class, "EBayPaymentMethod", null);
	String COLUMNNAME_EBayPaymentMethod = "EBayPaymentMethod";

	/**
	 * Set eBay.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setExternalSystem_Config_Ebay_ID (int ExternalSystem_Config_Ebay_ID);

	/**
	 * Get eBay.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getExternalSystem_Config_Ebay_ID();

	@Nullable de.metas.externalsystem.model.I_ExternalSystem_Config_Ebay getExternalSystem_Config_Ebay();

	void setExternalSystem_Config_Ebay(@Nullable de.metas.externalsystem.model.I_ExternalSystem_Config_Ebay ExternalSystem_Config_Ebay);

	ModelColumn<I_ExternalSystem_Config_Ebay_Mapping, de.metas.externalsystem.model.I_ExternalSystem_Config_Ebay> COLUMN_ExternalSystem_Config_Ebay_ID = new ModelColumn<>(I_ExternalSystem_Config_Ebay_Mapping.class, "ExternalSystem_Config_Ebay_ID", de.metas.externalsystem.model.I_ExternalSystem_Config_Ebay.class);
	String COLUMNNAME_ExternalSystem_Config_Ebay_ID = "ExternalSystem_Config_Ebay_ID";

	/**
	 * Set ExternalSystem_Config_Ebay_Mapping.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setExternalSystem_Config_Ebay_Mapping_ID (int ExternalSystem_Config_Ebay_Mapping_ID);

	/**
	 * Get ExternalSystem_Config_Ebay_Mapping.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getExternalSystem_Config_Ebay_Mapping_ID();

	ModelColumn<I_ExternalSystem_Config_Ebay_Mapping, Object> COLUMN_ExternalSystem_Config_Ebay_Mapping_ID = new ModelColumn<>(I_ExternalSystem_Config_Ebay_Mapping.class, "ExternalSystem_Config_Ebay_Mapping_ID", null);
	String COLUMNNAME_ExternalSystem_Config_Ebay_Mapping_ID = "ExternalSystem_Config_Ebay_Mapping_ID";

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

	ModelColumn<I_ExternalSystem_Config_Ebay_Mapping, Object> COLUMN_IsActive = new ModelColumn<>(I_ExternalSystem_Config_Ebay_Mapping.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Invoice Email Enabled.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsInvoiceEmailEnabled (boolean IsInvoiceEmailEnabled);

	/**
	 * Get Invoice Email Enabled.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isInvoiceEmailEnabled();

	ModelColumn<I_ExternalSystem_Config_Ebay_Mapping, Object> COLUMN_IsInvoiceEmailEnabled = new ModelColumn<>(I_ExternalSystem_Config_Ebay_Mapping.class, "IsInvoiceEmailEnabled", null);
	String COLUMNNAME_IsInvoiceEmailEnabled = "IsInvoiceEmailEnabled";

	/**
	 * Set Payment Rule.
	 * How you pay the invoice
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPaymentRule (@Nullable java.lang.String PaymentRule);

	/**
	 * Get Payment Rule.
	 * How you pay the invoice
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getPaymentRule();

	ModelColumn<I_ExternalSystem_Config_Ebay_Mapping, Object> COLUMN_PaymentRule = new ModelColumn<>(I_ExternalSystem_Config_Ebay_Mapping.class, "PaymentRule", null);
	String COLUMNNAME_PaymentRule = "PaymentRule";

	/**
	 * Set SeqNo.
	 * Method of ordering records;
 lowest number comes first
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setSeqNo (int SeqNo);

	/**
	 * Get SeqNo.
	 * Method of ordering records;
 lowest number comes first
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getSeqNo();

	ModelColumn<I_ExternalSystem_Config_Ebay_Mapping, Object> COLUMN_SeqNo = new ModelColumn<>(I_ExternalSystem_Config_Ebay_Mapping.class, "SeqNo", null);
	String COLUMNNAME_SeqNo = "SeqNo";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_ExternalSystem_Config_Ebay_Mapping, Object> COLUMN_Updated = new ModelColumn<>(I_ExternalSystem_Config_Ebay_Mapping.class, "Updated", null);
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
