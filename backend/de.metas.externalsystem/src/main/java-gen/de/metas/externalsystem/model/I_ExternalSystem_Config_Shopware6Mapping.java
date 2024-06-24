package de.metas.externalsystem.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;

/** Generated Interface for ExternalSystem_Config_Shopware6Mapping
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_ExternalSystem_Config_Shopware6Mapping 
{

	String Table_Name = "ExternalSystem_Config_Shopware6Mapping";

//	/** AD_Table_ID=541621 */
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

	ModelColumn<I_ExternalSystem_Config_Shopware6Mapping, Object> COLUMN_BPartner_IfExists = new ModelColumn<>(I_ExternalSystem_Config_Shopware6Mapping.class, "BPartner_IfExists", null);
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

	ModelColumn<I_ExternalSystem_Config_Shopware6Mapping, Object> COLUMN_BPartner_IfNotExists = new ModelColumn<>(I_ExternalSystem_Config_Shopware6Mapping.class, "BPartner_IfNotExists", null);
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

	ModelColumn<I_ExternalSystem_Config_Shopware6Mapping, Object> COLUMN_BPartnerLocation_IfExists = new ModelColumn<>(I_ExternalSystem_Config_Shopware6Mapping.class, "BPartnerLocation_IfExists", null);
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

	ModelColumn<I_ExternalSystem_Config_Shopware6Mapping, Object> COLUMN_BPartnerLocation_IfNotExists = new ModelColumn<>(I_ExternalSystem_Config_Shopware6Mapping.class, "BPartnerLocation_IfNotExists", null);
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

	ModelColumn<I_ExternalSystem_Config_Shopware6Mapping, Object> COLUMN_Created = new ModelColumn<>(I_ExternalSystem_Config_Shopware6Mapping.class, "Created", null);
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

	ModelColumn<I_ExternalSystem_Config_Shopware6Mapping, Object> COLUMN_Description = new ModelColumn<>(I_ExternalSystem_Config_Shopware6Mapping.class, "Description", null);
	String COLUMNNAME_Description = "Description";

	/**
	 * Set External system config Shopware6.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setExternalSystem_Config_Shopware6_ID (int ExternalSystem_Config_Shopware6_ID);

	/**
	 * Get External system config Shopware6.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getExternalSystem_Config_Shopware6_ID();

	de.metas.externalsystem.model.I_ExternalSystem_Config_Shopware6 getExternalSystem_Config_Shopware6();

	void setExternalSystem_Config_Shopware6(de.metas.externalsystem.model.I_ExternalSystem_Config_Shopware6 ExternalSystem_Config_Shopware6);

	ModelColumn<I_ExternalSystem_Config_Shopware6Mapping, de.metas.externalsystem.model.I_ExternalSystem_Config_Shopware6> COLUMN_ExternalSystem_Config_Shopware6_ID = new ModelColumn<>(I_ExternalSystem_Config_Shopware6Mapping.class, "ExternalSystem_Config_Shopware6_ID", de.metas.externalsystem.model.I_ExternalSystem_Config_Shopware6.class);
	String COLUMNNAME_ExternalSystem_Config_Shopware6_ID = "ExternalSystem_Config_Shopware6_ID";

	/**
	 * Set Sales order disposition.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setExternalSystem_Config_Shopware6Mapping_ID (int ExternalSystem_Config_Shopware6Mapping_ID);

	/**
	 * Get Sales order disposition.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getExternalSystem_Config_Shopware6Mapping_ID();

	ModelColumn<I_ExternalSystem_Config_Shopware6Mapping, Object> COLUMN_ExternalSystem_Config_Shopware6Mapping_ID = new ModelColumn<>(I_ExternalSystem_Config_Shopware6Mapping.class, "ExternalSystem_Config_Shopware6Mapping_ID", null);
	String COLUMNNAME_ExternalSystem_Config_Shopware6Mapping_ID = "ExternalSystem_Config_Shopware6Mapping_ID";

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

	ModelColumn<I_ExternalSystem_Config_Shopware6Mapping, Object> COLUMN_IsActive = new ModelColumn<>(I_ExternalSystem_Config_Shopware6Mapping.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Invoice Email Enabled.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIsInvoiceEmailEnabled (@Nullable java.lang.String IsInvoiceEmailEnabled);

	/**
	 * Get Invoice Email Enabled.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getIsInvoiceEmailEnabled();

	ModelColumn<I_ExternalSystem_Config_Shopware6Mapping, Object> COLUMN_IsInvoiceEmailEnabled = new ModelColumn<>(I_ExternalSystem_Config_Shopware6Mapping.class, "IsInvoiceEmailEnabled", null);
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

	ModelColumn<I_ExternalSystem_Config_Shopware6Mapping, Object> COLUMN_PaymentRule = new ModelColumn<>(I_ExternalSystem_Config_Shopware6Mapping.class, "PaymentRule", null);
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

	ModelColumn<I_ExternalSystem_Config_Shopware6Mapping, Object> COLUMN_SeqNo = new ModelColumn<>(I_ExternalSystem_Config_Shopware6Mapping.class, "SeqNo", null);
	String COLUMNNAME_SeqNo = "SeqNo";

	/**
	 * Set SW6 customer group.
	 * Optional name of a Shopware customer group as a matching criterion. Leave empty to match all customer groups.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setSW6_Customer_Group (@Nullable java.lang.String SW6_Customer_Group);

	/**
	 * Get SW6 customer group.
	 * Optional name of a Shopware customer group as a matching criterion. Leave empty to match all customer groups.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getSW6_Customer_Group();

	ModelColumn<I_ExternalSystem_Config_Shopware6Mapping, Object> COLUMN_SW6_Customer_Group = new ModelColumn<>(I_ExternalSystem_Config_Shopware6Mapping.class, "SW6_Customer_Group", null);
	String COLUMNNAME_SW6_Customer_Group = "SW6_Customer_Group";

	/**
	 * Set SW6 Payment method.
	 * Optional Shopware payment method as a matching criterion. Leave empty to match all payment methods.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setSW6_Payment_Method (@Nullable java.lang.String SW6_Payment_Method);

	/**
	 * Get SW6 Payment method.
	 * Optional Shopware payment method as a matching criterion. Leave empty to match all payment methods.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getSW6_Payment_Method();

	ModelColumn<I_ExternalSystem_Config_Shopware6Mapping, Object> COLUMN_SW6_Payment_Method = new ModelColumn<>(I_ExternalSystem_Config_Shopware6Mapping.class, "SW6_Payment_Method", null);
	String COLUMNNAME_SW6_Payment_Method = "SW6_Payment_Method";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_ExternalSystem_Config_Shopware6Mapping, Object> COLUMN_Updated = new ModelColumn<>(I_ExternalSystem_Config_Shopware6Mapping.class, "Updated", null);
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
