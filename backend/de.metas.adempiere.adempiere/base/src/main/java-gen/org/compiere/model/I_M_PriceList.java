package org.compiere.model;

import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for M_PriceList
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_M_PriceList 
{

	String Table_Name = "M_PriceList";

//	/** AD_Table_ID=255 */
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
	 * Set Base Pricelist.
	 * Pricelist to be used, if product not found on this pricelist
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setBasePriceList_ID (int BasePriceList_ID);

	/**
	 * Get Base Pricelist.
	 * Pricelist to be used, if product not found on this pricelist
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getBasePriceList_ID();

	String COLUMNNAME_BasePriceList_ID = "BasePriceList_ID";

	/**
	 * Set Country.
	 * Country
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Country_ID (int C_Country_ID);

	/**
	 * Get Country.
	 * Country
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Country_ID();

	@Nullable org.compiere.model.I_C_Country getC_Country();

	void setC_Country(@Nullable org.compiere.model.I_C_Country C_Country);

	ModelColumn<I_M_PriceList, org.compiere.model.I_C_Country> COLUMN_C_Country_ID = new ModelColumn<>(I_M_PriceList.class, "C_Country_ID", org.compiere.model.I_C_Country.class);
	String COLUMNNAME_C_Country_ID = "C_Country_ID";

	/**
	 * Set Currency.
	 * The Currency for this record
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Currency_ID (int C_Currency_ID);

	/**
	 * Get Currency.
	 * The Currency for this record
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Currency_ID();

	String COLUMNNAME_C_Currency_ID = "C_Currency_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_M_PriceList, Object> COLUMN_Created = new ModelColumn<>(I_M_PriceList.class, "Created", null);
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
	 * Set Default Tax Category.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDefault_TaxCategory_ID (int Default_TaxCategory_ID);

	/**
	 * Get Default Tax Category.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getDefault_TaxCategory_ID();

	String COLUMNNAME_Default_TaxCategory_ID = "Default_TaxCategory_ID";

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

	ModelColumn<I_M_PriceList, Object> COLUMN_Description = new ModelColumn<>(I_M_PriceList.class, "Description", null);
	String COLUMNNAME_Description = "Description";

	/**
	 * Set Enforce price limit.
	 * Do not allow prices below the limit price
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setEnforcePriceLimit (boolean EnforcePriceLimit);

	/**
	 * Get Enforce price limit.
	 * Do not allow prices below the limit price
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isEnforcePriceLimit();

	ModelColumn<I_M_PriceList, Object> COLUMN_EnforcePriceLimit = new ModelColumn<>(I_M_PriceList.class, "EnforcePriceLimit", null);
	String COLUMNNAME_EnforcePriceLimit = "EnforcePriceLimit";

	/**
	 * Set Internal Name.
	 * Generally used to give records a name that can be safely referenced from code.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setInternalName (@Nullable java.lang.String InternalName);

	/**
	 * Get Internal Name.
	 * Generally used to give records a name that can be safely referenced from code.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getInternalName();

	ModelColumn<I_M_PriceList, Object> COLUMN_InternalName = new ModelColumn<>(I_M_PriceList.class, "InternalName", null);
	String COLUMNNAME_InternalName = "InternalName";

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

	ModelColumn<I_M_PriceList, Object> COLUMN_IsActive = new ModelColumn<>(I_M_PriceList.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Default.
	 * Default value
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsDefault (boolean IsDefault);

	/**
	 * Get Default.
	 * Default value
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isDefault();

	ModelColumn<I_M_PriceList, Object> COLUMN_IsDefault = new ModelColumn<>(I_M_PriceList.class, "IsDefault", null);
	String COLUMNNAME_IsDefault = "IsDefault";

	/**
	 * Set mandatory.
	 * Data entry is required in this column
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIsMandatory (boolean IsMandatory);

	/**
	 * Get mandatory.
	 * Data entry is required in this column
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isMandatory();

	ModelColumn<I_M_PriceList, Object> COLUMN_IsMandatory = new ModelColumn<>(I_M_PriceList.class, "IsMandatory", null);
	String COLUMNNAME_IsMandatory = "IsMandatory";

	/**
	 * Set isPresentForProduct.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIsPresentForProduct (boolean IsPresentForProduct);

	/**
	 * Get isPresentForProduct.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isPresentForProduct();

	ModelColumn<I_M_PriceList, Object> COLUMN_IsPresentForProduct = new ModelColumn<>(I_M_PriceList.class, "IsPresentForProduct", null);
	String COLUMNNAME_IsPresentForProduct = "IsPresentForProduct";

	/**
	 * Set Round Net Amount To Currency Precision.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsRoundNetAmountToCurrencyPrecision (boolean IsRoundNetAmountToCurrencyPrecision);

	/**
	 * Get Round Net Amount To Currency Precision.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isRoundNetAmountToCurrencyPrecision();

	ModelColumn<I_M_PriceList, Object> COLUMN_IsRoundNetAmountToCurrencyPrecision = new ModelColumn<>(I_M_PriceList.class, "IsRoundNetAmountToCurrencyPrecision", null);
	String COLUMNNAME_IsRoundNetAmountToCurrencyPrecision = "IsRoundNetAmountToCurrencyPrecision";

	/**
	 * Set Sales Pricelist.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsSOPriceList (boolean IsSOPriceList);

	/**
	 * Get Sales Pricelist.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isSOPriceList();

	ModelColumn<I_M_PriceList, Object> COLUMN_IsSOPriceList = new ModelColumn<>(I_M_PriceList.class, "IsSOPriceList", null);
	String COLUMNNAME_IsSOPriceList = "IsSOPriceList";

	/**
	 * Set Tax Included.
	 * Tax Included
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsTaxIncluded (boolean IsTaxIncluded);

	/**
	 * Get Tax Included.
	 * Tax Included
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isTaxIncluded();

	ModelColumn<I_M_PriceList, Object> COLUMN_IsTaxIncluded = new ModelColumn<>(I_M_PriceList.class, "IsTaxIncluded", null);
	String COLUMNNAME_IsTaxIncluded = "IsTaxIncluded";

	/**
	 * Set Price List.
	 * Unique identifier of a Price List
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_PriceList_ID (int M_PriceList_ID);

	/**
	 * Get Price List.
	 * Unique identifier of a Price List
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_PriceList_ID();

	ModelColumn<I_M_PriceList, Object> COLUMN_M_PriceList_ID = new ModelColumn<>(I_M_PriceList.class, "M_PriceList_ID", null);
	String COLUMNNAME_M_PriceList_ID = "M_PriceList_ID";

	/**
	 * Set Pricing System.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_PricingSystem_ID (int M_PricingSystem_ID);

	/**
	 * Get Pricing System.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_PricingSystem_ID();

	String COLUMNNAME_M_PricingSystem_ID = "M_PricingSystem_ID";

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

	ModelColumn<I_M_PriceList, Object> COLUMN_Name = new ModelColumn<>(I_M_PriceList.class, "Name", null);
	String COLUMNNAME_Name = "Name";

	/**
	 * Set Price Precision.
	 * Precision (number of decimals) for the Price
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPricePrecision (int PricePrecision);

	/**
	 * Get Price Precision.
	 * Precision (number of decimals) for the Price
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getPricePrecision();

	ModelColumn<I_M_PriceList, Object> COLUMN_PricePrecision = new ModelColumn<>(I_M_PriceList.class, "PricePrecision", null);
	String COLUMNNAME_PricePrecision = "PricePrecision";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_M_PriceList, Object> COLUMN_Updated = new ModelColumn<>(I_M_PriceList.class, "Updated", null);
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
