package org.compiere.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;

/** Generated Interface for M_Product_Ingredients
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_M_Product_Ingredients 
{

	String Table_Name = "M_Product_Ingredients";

//	/** AD_Table_ID=541412 */
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

	ModelColumn<I_M_Product_Ingredients, Object> COLUMN_Created = new ModelColumn<>(I_M_Product_Ingredients.class, "Created", null);
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
	 * Set UOM.
	 * Unit of Measure
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_UOM_ID (int C_UOM_ID);

	/**
	 * Get UOM.
	 * Unit of Measure
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_UOM_ID();

	String COLUMNNAME_C_UOM_ID = "C_UOM_ID";

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

	ModelColumn<I_M_Product_Ingredients, Object> COLUMN_IsActive = new ModelColumn<>(I_M_Product_Ingredients.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Ingredients.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_Ingredients_ID (int M_Ingredients_ID);

	/**
	 * Get Ingredients.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_Ingredients_ID();

	org.compiere.model.I_M_Ingredients getM_Ingredients();

	void setM_Ingredients(org.compiere.model.I_M_Ingredients M_Ingredients);

	ModelColumn<I_M_Product_Ingredients, org.compiere.model.I_M_Ingredients> COLUMN_M_Ingredients_ID = new ModelColumn<>(I_M_Product_Ingredients.class, "M_Ingredients_ID", org.compiere.model.I_M_Ingredients.class);
	String COLUMNNAME_M_Ingredients_ID = "M_Ingredients_ID";

	/**
	 * Set Product.
	 * Product, Service, Item
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_Product_ID (int M_Product_ID);

	/**
	 * Get Product.
	 * Product, Service, Item
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_Product_ID();

	String COLUMNNAME_M_Product_ID = "M_Product_ID";

	/**
	 * Set Product Ingredients.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_Product_Ingredients_ID (int M_Product_Ingredients_ID);

	/**
	 * Get Product Ingredients.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_Product_Ingredients_ID();

	ModelColumn<I_M_Product_Ingredients, Object> COLUMN_M_Product_Ingredients_ID = new ModelColumn<>(I_M_Product_Ingredients.class, "M_Product_Ingredients_ID", null);
	String COLUMNNAME_M_Product_Ingredients_ID = "M_Product_Ingredients_ID";

	/**
	 * Set NRV.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setNRV (int NRV);

	/**
	 * Get NRV.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getNRV();

	ModelColumn<I_M_Product_Ingredients, Object> COLUMN_NRV = new ModelColumn<>(I_M_Product_Ingredients.class, "NRV", null);
	String COLUMNNAME_NRV = "NRV";

	/**
	 * Set Parent Element.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setParentElement_ID (int ParentElement_ID);

	/**
	 * Get Parent Element.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getParentElement_ID();

	@Nullable org.compiere.model.I_M_Ingredients getParentElement();

	void setParentElement(@Nullable org.compiere.model.I_M_Ingredients ParentElement);

	ModelColumn<I_M_Product_Ingredients, org.compiere.model.I_M_Ingredients> COLUMN_ParentElement_ID = new ModelColumn<>(I_M_Product_Ingredients.class, "ParentElement_ID", org.compiere.model.I_M_Ingredients.class);
	String COLUMNNAME_ParentElement_ID = "ParentElement_ID";

	/**
	 * Set Precision.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPrecision (int Precision);

	/**
	 * Get Precision.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getPrecision();

	ModelColumn<I_M_Product_Ingredients, Object> COLUMN_Precision = new ModelColumn<>(I_M_Product_Ingredients.class, "Precision", null);
	String COLUMNNAME_Precision = "Precision";

	/**
	 * Set Quantity.
	 * Quantity
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQty (@Nullable java.lang.String Qty);

	/**
	 * Get Quantity.
	 * Quantity
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getQty();

	ModelColumn<I_M_Product_Ingredients, Object> COLUMN_Qty = new ModelColumn<>(I_M_Product_Ingredients.class, "Qty", null);
	String COLUMNNAME_Qty = "Qty";

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

	ModelColumn<I_M_Product_Ingredients, Object> COLUMN_SeqNo = new ModelColumn<>(I_M_Product_Ingredients.class, "SeqNo", null);
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

	ModelColumn<I_M_Product_Ingredients, Object> COLUMN_Updated = new ModelColumn<>(I_M_Product_Ingredients.class, "Updated", null);
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
