package de.metas.invoicecandidate.model;

import org.adempiere.model.ModelColumn;

/** Generated Interface for M_ProductGroup_Product
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_M_ProductGroup_Product 
{

	String Table_Name = "M_ProductGroup_Product";

//	/** AD_Table_ID=540324 */
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

	ModelColumn<I_M_ProductGroup_Product, Object> COLUMN_Created = new ModelColumn<>(I_M_ProductGroup_Product.class, "Created", null);
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

	ModelColumn<I_M_ProductGroup_Product, Object> COLUMN_IsActive = new ModelColumn<>(I_M_ProductGroup_Product.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Product Category.
	 * Category of a Product
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_Product_Category_ID (int M_Product_Category_ID);

	/**
	 * Get Product Category.
	 * Category of a Product
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_Product_Category_ID();

	String COLUMNNAME_M_Product_Category_ID = "M_Product_Category_ID";

	/**
	 * Set Product.
	 * Product, Service, Item
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_Product_ID (int M_Product_ID);

	/**
	 * Get Product.
	 * Product, Service, Item
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_Product_ID();

	String COLUMNNAME_M_Product_ID = "M_Product_ID";

	/**
	 * Set Produktgruppe.
	 * Fasst eine Anzahl von Produkten oder Produktkategorien zu einer Gruppe zusammen.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_ProductGroup_ID (int M_ProductGroup_ID);

	/**
	 * Get Produktgruppe.
	 * Fasst eine Anzahl von Produkten oder Produktkategorien zu einer Gruppe zusammen.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_ProductGroup_ID();

	de.metas.invoicecandidate.model.I_M_ProductGroup getM_ProductGroup();

	void setM_ProductGroup(de.metas.invoicecandidate.model.I_M_ProductGroup M_ProductGroup);

	ModelColumn<I_M_ProductGroup_Product, de.metas.invoicecandidate.model.I_M_ProductGroup> COLUMN_M_ProductGroup_ID = new ModelColumn<>(I_M_ProductGroup_Product.class, "M_ProductGroup_ID", de.metas.invoicecandidate.model.I_M_ProductGroup.class);
	String COLUMNNAME_M_ProductGroup_ID = "M_ProductGroup_ID";

	/**
	 * Set Produktgruppe - Produkt.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_ProductGroup_Product_ID (int M_ProductGroup_Product_ID);

	/**
	 * Get Produktgruppe - Produkt.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_ProductGroup_Product_ID();

	ModelColumn<I_M_ProductGroup_Product, Object> COLUMN_M_ProductGroup_Product_ID = new ModelColumn<>(I_M_ProductGroup_Product.class, "M_ProductGroup_Product_ID", null);
	String COLUMNNAME_M_ProductGroup_Product_ID = "M_ProductGroup_Product_ID";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_M_ProductGroup_Product, Object> COLUMN_Updated = new ModelColumn<>(I_M_ProductGroup_Product.class, "Updated", null);
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
