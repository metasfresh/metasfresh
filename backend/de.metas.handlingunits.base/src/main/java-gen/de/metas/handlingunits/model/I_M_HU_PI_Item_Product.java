package de.metas.handlingunits.model;

import java.math.BigDecimal;
import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for M_HU_PI_Item_Product
 *  @author metasfresh (generated) 
 */
public interface I_M_HU_PI_Item_Product 
{

	String Table_Name = "M_HU_PI_Item_Product";

//	/** AD_Table_ID=540508 */
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
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_BPartner_ID (int C_BPartner_ID);

	/**
	 * Get Business Partner.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_BPartner_ID();

	String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_M_HU_PI_Item_Product, Object> COLUMN_Created = new ModelColumn<>(I_M_HU_PI_Item_Product.class, "Created", null);
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

	ModelColumn<I_M_HU_PI_Item_Product, Object> COLUMN_Description = new ModelColumn<>(I_M_HU_PI_Item_Product.class, "Description", null);
	String COLUMNNAME_Description = "Description";

	/**
	 * Set TU-EAN.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setEAN_TU (@Nullable java.lang.String EAN_TU);

	/**
	 * Get TU-EAN.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getEAN_TU();

	ModelColumn<I_M_HU_PI_Item_Product, Object> COLUMN_EAN_TU = new ModelColumn<>(I_M_HU_PI_Item_Product.class, "EAN_TU", null);
	String COLUMNNAME_EAN_TU = "EAN_TU";

	/**
	 * Set GTIN.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setGTIN (@Nullable java.lang.String GTIN);

	/**
	 * Get GTIN.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getGTIN();

	ModelColumn<I_M_HU_PI_Item_Product, Object> COLUMN_GTIN = new ModelColumn<>(I_M_HU_PI_Item_Product.class, "GTIN", null);
	String COLUMNNAME_GTIN = "GTIN";

	/**
	 * Set LU fallback packaging GTIN.
	 * Used if a LU packaging GTIN is required, but no HU is assigned in metasfresh.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setGTIN_LU_PackingMaterial_Fallback (@Nullable java.lang.String GTIN_LU_PackingMaterial_Fallback);

	/**
	 * Get LU fallback packaging GTIN.
	 * Used if a LU packaging GTIN is required, but no HU is assigned in metasfresh.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getGTIN_LU_PackingMaterial_Fallback();

	ModelColumn<I_M_HU_PI_Item_Product, Object> COLUMN_GTIN_LU_PackingMaterial_Fallback = new ModelColumn<>(I_M_HU_PI_Item_Product.class, "GTIN_LU_PackingMaterial_Fallback", null);
	String COLUMNNAME_GTIN_LU_PackingMaterial_Fallback = "GTIN_LU_PackingMaterial_Fallback";

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

	ModelColumn<I_M_HU_PI_Item_Product, Object> COLUMN_IsActive = new ModelColumn<>(I_M_HU_PI_Item_Product.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Jedes Produkt erlauben.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsAllowAnyProduct (boolean IsAllowAnyProduct);

	/**
	 * Get Jedes Produkt erlauben.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isAllowAnyProduct();

	ModelColumn<I_M_HU_PI_Item_Product, Object> COLUMN_IsAllowAnyProduct = new ModelColumn<>(I_M_HU_PI_Item_Product.class, "IsAllowAnyProduct", null);
	String COLUMNNAME_IsAllowAnyProduct = "IsAllowAnyProduct";

	/**
	 * Set Default for Product.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsDefaultForProduct (boolean IsDefaultForProduct);

	/**
	 * Get Default for Product.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isDefaultForProduct();

	ModelColumn<I_M_HU_PI_Item_Product, Object> COLUMN_IsDefaultForProduct = new ModelColumn<>(I_M_HU_PI_Item_Product.class, "IsDefaultForProduct", null);
	String COLUMNNAME_IsDefaultForProduct = "IsDefaultForProduct";

	/**
	 * Set Infinite Capacity.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsInfiniteCapacity (boolean IsInfiniteCapacity);

	/**
	 * Get Infinite Capacity.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isInfiniteCapacity();

	ModelColumn<I_M_HU_PI_Item_Product, Object> COLUMN_IsInfiniteCapacity = new ModelColumn<>(I_M_HU_PI_Item_Product.class, "IsInfiniteCapacity", null);
	String COLUMNNAME_IsInfiniteCapacity = "IsInfiniteCapacity";

	/**
	 * Set LU fallback packaging code.
	 * Used if a LU packaging code is required, but no HU is assigned in metasfresh
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_HU_PackagingCode_LU_Fallback_ID (int M_HU_PackagingCode_LU_Fallback_ID);

	/**
	 * Get LU fallback packaging code.
	 * Used if a LU packaging code is required, but no HU is assigned in metasfresh
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_HU_PackagingCode_LU_Fallback_ID();

	@Nullable de.metas.handlingunits.model.I_M_HU_PackagingCode getM_HU_PackagingCode_LU_Fallback();

	void setM_HU_PackagingCode_LU_Fallback(@Nullable de.metas.handlingunits.model.I_M_HU_PackagingCode M_HU_PackagingCode_LU_Fallback);

	ModelColumn<I_M_HU_PI_Item_Product, de.metas.handlingunits.model.I_M_HU_PackagingCode> COLUMN_M_HU_PackagingCode_LU_Fallback_ID = new ModelColumn<>(I_M_HU_PI_Item_Product.class, "M_HU_PackagingCode_LU_Fallback_ID", de.metas.handlingunits.model.I_M_HU_PackagingCode.class);
	String COLUMNNAME_M_HU_PackagingCode_LU_Fallback_ID = "M_HU_PackagingCode_LU_Fallback_ID";

	/**
	 * Set Packing Instruction Item.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_HU_PI_Item_ID (int M_HU_PI_Item_ID);

	/**
	 * Get Packing Instruction Item.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_HU_PI_Item_ID();

	de.metas.handlingunits.model.I_M_HU_PI_Item getM_HU_PI_Item();

	void setM_HU_PI_Item(de.metas.handlingunits.model.I_M_HU_PI_Item M_HU_PI_Item);

	ModelColumn<I_M_HU_PI_Item_Product, de.metas.handlingunits.model.I_M_HU_PI_Item> COLUMN_M_HU_PI_Item_ID = new ModelColumn<>(I_M_HU_PI_Item_Product.class, "M_HU_PI_Item_ID", de.metas.handlingunits.model.I_M_HU_PI_Item.class);
	String COLUMNNAME_M_HU_PI_Item_ID = "M_HU_PI_Item_ID";

	/**
	 * Set Packing Instruction.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_HU_PI_Item_Product_ID (int M_HU_PI_Item_Product_ID);

	/**
	 * Get Packing Instruction.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_HU_PI_Item_Product_ID();

	ModelColumn<I_M_HU_PI_Item_Product, Object> COLUMN_M_HU_PI_Item_Product_ID = new ModelColumn<>(I_M_HU_PI_Item_Product.class, "M_HU_PI_Item_Product_ID", null);
	String COLUMNNAME_M_HU_PI_Item_Product_ID = "M_HU_PI_Item_Product_ID";

	/**
	 * Set Product.
	 * Product, Service, Item
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_Product_ID (int M_Product_ID);

	/**
	 * Get Product.
	 * Product, Service, Item
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_Product_ID();

	String COLUMNNAME_M_Product_ID = "M_Product_ID";

	/**
	 * Set Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setName (@Nullable java.lang.String Name);

	/**
	 * Get Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getName();

	ModelColumn<I_M_HU_PI_Item_Product, Object> COLUMN_Name = new ModelColumn<>(I_M_HU_PI_Item_Product.class, "Name", null);
	String COLUMNNAME_Name = "Name";

	/**
	 * Set Quantity.
	 * Quantity
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setQty (BigDecimal Qty);

	/**
	 * Get Quantity.
	 * Quantity
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getQty();

	ModelColumn<I_M_HU_PI_Item_Product, Object> COLUMN_Qty = new ModelColumn<>(I_M_HU_PI_Item_Product.class, "Qty", null);
	String COLUMNNAME_Qty = "Qty";

	/**
	 * Set UPC.
	 * Bar Code (Universal Product Code or its superset European Article Number)
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setUPC (@Nullable java.lang.String UPC);

	/**
	 * Get UPC.
	 * Bar Code (Universal Product Code or its superset European Article Number)
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getUPC();

	ModelColumn<I_M_HU_PI_Item_Product, Object> COLUMN_UPC = new ModelColumn<>(I_M_HU_PI_Item_Product.class, "UPC", null);
	String COLUMNNAME_UPC = "UPC";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_M_HU_PI_Item_Product, Object> COLUMN_Updated = new ModelColumn<>(I_M_HU_PI_Item_Product.class, "Updated", null);
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
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setValidFrom (java.sql.Timestamp ValidFrom);

	/**
	 * Get Valid From.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getValidFrom();

	ModelColumn<I_M_HU_PI_Item_Product, Object> COLUMN_ValidFrom = new ModelColumn<>(I_M_HU_PI_Item_Product.class, "ValidFrom", null);
	String COLUMNNAME_ValidFrom = "ValidFrom";

	/**
	 * Set Valid to.
	 * Valid to including this date (last day)
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setValidTo (@Nullable java.sql.Timestamp ValidTo);

	/**
	 * Get Valid to.
	 * Valid to including this date (last day)
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getValidTo();

	ModelColumn<I_M_HU_PI_Item_Product, Object> COLUMN_ValidTo = new ModelColumn<>(I_M_HU_PI_Item_Product.class, "ValidTo", null);
	String COLUMNNAME_ValidTo = "ValidTo";
}
