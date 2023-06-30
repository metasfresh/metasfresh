package org.compiere.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;

/** Generated Interface for C_TaxCategory
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_C_TaxCategory 
{

	String Table_Name = "C_TaxCategory";

//	/** AD_Table_ID=252 */
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
	 * Set Tax Category.
	 * Tax Category
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_TaxCategory_ID (int C_TaxCategory_ID);

	/**
	 * Get Tax Category.
	 * Tax Category
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_TaxCategory_ID();

	ModelColumn<I_C_TaxCategory, Object> COLUMN_C_TaxCategory_ID = new ModelColumn<>(I_C_TaxCategory.class, "C_TaxCategory_ID", null);
	String COLUMNNAME_C_TaxCategory_ID = "C_TaxCategory_ID";

	/**
	 * Set Statistische Warennummer.
	 * Commodity code used for tax calculation
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCommodityCode (@Nullable java.lang.String CommodityCode);

	/**
	 * Get Statistische Warennummer.
	 * Commodity code used for tax calculation
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getCommodityCode();

	ModelColumn<I_C_TaxCategory, Object> COLUMN_CommodityCode = new ModelColumn<>(I_C_TaxCategory.class, "CommodityCode", null);
	String COLUMNNAME_CommodityCode = "CommodityCode";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_C_TaxCategory, Object> COLUMN_Created = new ModelColumn<>(I_C_TaxCategory.class, "Created", null);
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

	ModelColumn<I_C_TaxCategory, Object> COLUMN_Description = new ModelColumn<>(I_C_TaxCategory.class, "Description", null);
	String COLUMNNAME_Description = "Description";

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

	ModelColumn<I_C_TaxCategory, Object> COLUMN_InternalName = new ModelColumn<>(I_C_TaxCategory.class, "InternalName", null);
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

	ModelColumn<I_C_TaxCategory, Object> COLUMN_IsActive = new ModelColumn<>(I_C_TaxCategory.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

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

	ModelColumn<I_C_TaxCategory, Object> COLUMN_Name = new ModelColumn<>(I_C_TaxCategory.class, "Name", null);
	String COLUMNNAME_Name = "Name";

	/**
	 * Set Product Type.
	 * Type of product
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setProductType (@Nullable java.lang.String ProductType);

	/**
	 * Get Product Type.
	 * Type of product
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getProductType();

	ModelColumn<I_C_TaxCategory, Object> COLUMN_ProductType = new ModelColumn<>(I_C_TaxCategory.class, "ProductType", null);
	String COLUMNNAME_ProductType = "ProductType";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_C_TaxCategory, Object> COLUMN_Updated = new ModelColumn<>(I_C_TaxCategory.class, "Updated", null);
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
	 * Set MwSt-Typ.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setVATType (@Nullable java.lang.String VATType);

	/**
	 * Get MwSt-Typ.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getVATType();

	ModelColumn<I_C_TaxCategory, Object> COLUMN_VATType = new ModelColumn<>(I_C_TaxCategory.class, "VATType", null);
	String COLUMNNAME_VATType = "VATType";
}
