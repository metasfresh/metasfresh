package de.metas.externalsystem.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;

/** Generated Interface for ExternalSystem_Config_Shopware6
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_ExternalSystem_Config_Shopware6 
{

	String Table_Name = "ExternalSystem_Config_Shopware6";

//	/** AD_Table_ID=541585 */
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
	 * Set Base-URL.
	 *
	 * <br>Type: URL
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setBaseURL (java.lang.String BaseURL);

	/**
	 * Get Base-URL.
	 *
	 * <br>Type: URL
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getBaseURL();

	ModelColumn<I_ExternalSystem_Config_Shopware6, Object> COLUMN_BaseURL = new ModelColumn<>(I_ExternalSystem_Config_Shopware6.class, "BaseURL", null);
	String COLUMNNAME_BaseURL = "BaseURL";

	/**
	 * Set Access key ID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setClient_Id (java.lang.String Client_Id);

	/**
	 * Get Access key ID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getClient_Id();

	ModelColumn<I_ExternalSystem_Config_Shopware6, Object> COLUMN_Client_Id = new ModelColumn<>(I_ExternalSystem_Config_Shopware6.class, "Client_Id", null);
	String COLUMNNAME_Client_Id = "Client_Id";

	/**
	 * Set Secret access key.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setClient_Secret (java.lang.String Client_Secret);

	/**
	 * Get Secret access key.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getClient_Secret();

	ModelColumn<I_ExternalSystem_Config_Shopware6, Object> COLUMN_Client_Secret = new ModelColumn<>(I_ExternalSystem_Config_Shopware6.class, "Client_Secret", null);
	String COLUMNNAME_Client_Secret = "Client_Secret";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_ExternalSystem_Config_Shopware6, Object> COLUMN_Created = new ModelColumn<>(I_ExternalSystem_Config_Shopware6.class, "Created", null);
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
	 * Set External System Config.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setExternalSystem_Config_ID (int ExternalSystem_Config_ID);

	/**
	 * Get External System Config.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getExternalSystem_Config_ID();

	de.metas.externalsystem.model.I_ExternalSystem_Config getExternalSystem_Config();

	void setExternalSystem_Config(de.metas.externalsystem.model.I_ExternalSystem_Config ExternalSystem_Config);

	ModelColumn<I_ExternalSystem_Config_Shopware6, de.metas.externalsystem.model.I_ExternalSystem_Config> COLUMN_ExternalSystem_Config_ID = new ModelColumn<>(I_ExternalSystem_Config_Shopware6.class, "ExternalSystem_Config_ID", de.metas.externalsystem.model.I_ExternalSystem_Config.class);
	String COLUMNNAME_ExternalSystem_Config_ID = "ExternalSystem_Config_ID";

	/**
	 * Set External system config Shopware6.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setExternalSystem_Config_Shopware6_ID (int ExternalSystem_Config_Shopware6_ID);

	/**
	 * Get External system config Shopware6.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getExternalSystem_Config_Shopware6_ID();

	ModelColumn<I_ExternalSystem_Config_Shopware6, Object> COLUMN_ExternalSystem_Config_Shopware6_ID = new ModelColumn<>(I_ExternalSystem_Config_Shopware6.class, "ExternalSystem_Config_Shopware6_ID", null);
	String COLUMNNAME_ExternalSystem_Config_Shopware6_ID = "ExternalSystem_Config_Shopware6_ID";

	/**
	 * Set Value.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setExternalSystemValue (java.lang.String ExternalSystemValue);

	/**
	 * Get Value.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getExternalSystemValue();

	ModelColumn<I_ExternalSystem_Config_Shopware6, Object> COLUMN_ExternalSystemValue = new ModelColumn<>(I_ExternalSystem_Config_Shopware6.class, "ExternalSystemValue", null);
	String COLUMNNAME_ExternalSystemValue = "ExternalSystemValue";

	/**
	 * Set Normal VAT rates.
	 * Comma delimited list of freightcost tax rates for which the "Freight cost product (normal VAT)" shall be used. Example: "0, 7.7, 19"
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setFreightCost_NormalVAT_Rates (@Nullable java.lang.String FreightCost_NormalVAT_Rates);

	/**
	 * Get Normal VAT rates.
	 * Comma delimited list of freightcost tax rates for which the "Freight cost product (normal VAT)" shall be used. Example: "0, 7.7, 19"
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getFreightCost_NormalVAT_Rates();

	ModelColumn<I_ExternalSystem_Config_Shopware6, Object> COLUMN_FreightCost_NormalVAT_Rates = new ModelColumn<>(I_ExternalSystem_Config_Shopware6.class, "FreightCost_NormalVAT_Rates", null);
	String COLUMNNAME_FreightCost_NormalVAT_Rates = "FreightCost_NormalVAT_Rates";

	/**
	 * Set Reduced VAT rates.
	 * Comma delimited list of freightcost tax rates for which the "Freight cost product (reduced VAT)" shall be used. Example: "0, 7.7, 19"
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setFreightCost_Reduced_VAT_Rates (@Nullable java.lang.String FreightCost_Reduced_VAT_Rates);

	/**
	 * Get Reduced VAT rates.
	 * Comma delimited list of freightcost tax rates for which the "Freight cost product (reduced VAT)" shall be used. Example: "0, 7.7, 19"
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getFreightCost_Reduced_VAT_Rates();

	ModelColumn<I_ExternalSystem_Config_Shopware6, Object> COLUMN_FreightCost_Reduced_VAT_Rates = new ModelColumn<>(I_ExternalSystem_Config_Shopware6.class, "FreightCost_Reduced_VAT_Rates", null);
	String COLUMNNAME_FreightCost_Reduced_VAT_Rates = "FreightCost_Reduced_VAT_Rates";

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

	ModelColumn<I_ExternalSystem_Config_Shopware6, Object> COLUMN_IsActive = new ModelColumn<>(I_ExternalSystem_Config_Shopware6.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Address JSON-path.
	 * JSON-Path expression that specifies where within a customized Shopware address the permanent address-ID can be found. IMPORTANT: if set, then addresses without a respective value are ignored!
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setJSONPathConstantBPartnerLocationID (@Nullable java.lang.String JSONPathConstantBPartnerLocationID);

	/**
	 * Get Address JSON-path.
	 * JSON-Path expression that specifies where within a customized Shopware address the permanent address-ID can be found. IMPORTANT: if set, then addresses without a respective value are ignored!
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getJSONPathConstantBPartnerLocationID();

	ModelColumn<I_ExternalSystem_Config_Shopware6, Object> COLUMN_JSONPathConstantBPartnerLocationID = new ModelColumn<>(I_ExternalSystem_Config_Shopware6.class, "JSONPathConstantBPartnerLocationID", null);
	String COLUMNNAME_JSONPathConstantBPartnerLocationID = "JSONPathConstantBPartnerLocationID";

	/**
	 * Set Recipient EMail JSON Path.
	 * JSON path specifying where within a customised Shopware address the delivery recipient's email address can be read, if set there. This value is transferred to the address master data and to the order.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setJSONPathEmail (@Nullable java.lang.String JSONPathEmail);

	/**
	 * Get Recipient EMail JSON Path.
	 * JSON path specifying where within a customised Shopware address the delivery recipient's email address can be read, if set there. This value is transferred to the address master data and to the order.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getJSONPathEmail();

	ModelColumn<I_ExternalSystem_Config_Shopware6, Object> COLUMN_JSONPathEmail = new ModelColumn<>(I_ExternalSystem_Config_Shopware6.class, "JSONPathEmail", null);
	String COLUMNNAME_JSONPathEmail = "JSONPathEmail";

	/**
	 * Set Business partner mapping - metasfresh-ID JSON-Path.
	 * JSON path that specifies where within a customized Shopware order the customer's metasfresh ID (C_BPartner_ID) can be read.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setJSONPathMetasfreshID (@Nullable java.lang.String JSONPathMetasfreshID);

	/**
	 * Get Business partner mapping - metasfresh-ID JSON-Path.
	 * JSON path that specifies where within a customized Shopware order the customer's metasfresh ID (C_BPartner_ID) can be read.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getJSONPathMetasfreshID();

	ModelColumn<I_ExternalSystem_Config_Shopware6, Object> COLUMN_JSONPathMetasfreshID = new ModelColumn<>(I_ExternalSystem_Config_Shopware6.class, "JSONPathMetasfreshID", null);
	String COLUMNNAME_JSONPathMetasfreshID = "JSONPathMetasfreshID";

	/**
	 * Set Sales rep JSON-path.
	 * JSON-Path expression that specifies where within a customized Shopware order the sales rep's search-key can be found.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setJSONPathSalesRepID (@Nullable java.lang.String JSONPathSalesRepID);

	/**
	 * Get Sales rep JSON-path.
	 * JSON-Path expression that specifies where within a customized Shopware order the sales rep's search-key can be found.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getJSONPathSalesRepID();

	ModelColumn<I_ExternalSystem_Config_Shopware6, Object> COLUMN_JSONPathSalesRepID = new ModelColumn<>(I_ExternalSystem_Config_Shopware6.class, "JSONPathSalesRepID", null);
	String COLUMNNAME_JSONPathSalesRepID = "JSONPathSalesRepID";

	/**
	 * Set Business partner mapping - Shopware6-ID JSON-Path.
	 * JSON path that specifies where within a customized Shopware order the customer's Shopware6 reference can be read.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setJSONPathShopwareID (@Nullable java.lang.String JSONPathShopwareID);

	/**
	 * Get Business partner mapping - Shopware6-ID JSON-Path.
	 * JSON path that specifies where within a customized Shopware order the customer's Shopware6 reference can be read.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getJSONPathShopwareID();

	ModelColumn<I_ExternalSystem_Config_Shopware6, Object> COLUMN_JSONPathShopwareID = new ModelColumn<>(I_ExternalSystem_Config_Shopware6.class, "JSONPathShopwareID", null);
	String COLUMNNAME_JSONPathShopwareID = "JSONPathShopwareID";

	/**
	 * Set Freight cost product (normal VAT).
	 * If a shopware order's freight costs have the normal VAT rate, then a line with this product is created. The product's tax category needs to have a tax with a matching rate.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_FreightCost_NormalVAT_Product_ID (int M_FreightCost_NormalVAT_Product_ID);

	/**
	 * Get Freight cost product (normal VAT).
	 * If a shopware order's freight costs have the normal VAT rate, then a line with this product is created. The product's tax category needs to have a tax with a matching rate.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_FreightCost_NormalVAT_Product_ID();

	String COLUMNNAME_M_FreightCost_NormalVAT_Product_ID = "M_FreightCost_NormalVAT_Product_ID";

	/**
	 * Set Freight cost product (reduced VAT).
	 * If a shopware order's freight costs have the reduced VAT rate, then a line with this product is created. The product's tax category needs to have a tax with a matching rate.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_FreightCost_ReducedVAT_Product_ID (int M_FreightCost_ReducedVAT_Product_ID);

	/**
	 * Get Freight cost product (reduced VAT).
	 * If a shopware order's freight costs have the reduced VAT rate, then a line with this product is created. The product's tax category needs to have a tax with a matching rate.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_FreightCost_ReducedVAT_Product_ID();

	String COLUMNNAME_M_FreightCost_ReducedVAT_Product_ID = "M_FreightCost_ReducedVAT_Product_ID";

	/**
	 * Set Price List.
	 * Unique identifier of a Price List
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_PriceList_ID (int M_PriceList_ID);

	/**
	 * Get Price List.
	 * Unique identifier of a Price List
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_PriceList_ID();

	String COLUMNNAME_M_PriceList_ID = "M_PriceList_ID";

	/**
	 * Set Product Lookup.
	 * Determines how the product for a Shopware line-item is determined in metasfresh.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setProductLookup (java.lang.String ProductLookup);

	/**
	 * Get Product Lookup.
	 * Determines how the product for a Shopware line-item is determined in metasfresh.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getProductLookup();

	ModelColumn<I_ExternalSystem_Config_Shopware6, Object> COLUMN_ProductLookup = new ModelColumn<>(I_ExternalSystem_Config_Shopware6.class, "ProductLookup", null);
	String COLUMNNAME_ProductLookup = "ProductLookup";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_ExternalSystem_Config_Shopware6, Object> COLUMN_Updated = new ModelColumn<>(I_ExternalSystem_Config_Shopware6.class, "Updated", null);
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
