package org.compiere.model;

import org.adempiere.model.ModelColumn;

/** Generated Interface for Carrier_Product_GoodsType_Alloc
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_Carrier_Product_GoodsType_Alloc 
{

	String Table_Name = "Carrier_Product_GoodsType_Alloc";

//	/** AD_Table_ID=542607 */
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
	 * Set Carrier Material Assignment.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setCarrier_Goods_Type_ID (int Carrier_Goods_Type_ID);

	/**
	 * Get Carrier Material Assignment.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getCarrier_Goods_Type_ID();

	ModelColumn<I_Carrier_Product_GoodsType_Alloc, org.compiere.model.I_Carrier_Goods_Type> COLUMN_Carrier_Goods_Type_ID = new ModelColumn<>(I_Carrier_Product_GoodsType_Alloc.class, "Carrier_Goods_Type_ID", org.compiere.model.I_Carrier_Goods_Type.class);
	String COLUMNNAME_Carrier_Goods_Type_ID = "Carrier_Goods_Type_ID";

	/**
	 * Set Product Goods Type Allocation.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setCarrier_Product_GoodsType_Alloc_ID (int Carrier_Product_GoodsType_Alloc_ID);

	/**
	 * Get Product Goods Type Allocation.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getCarrier_Product_GoodsType_Alloc_ID();

	ModelColumn<I_Carrier_Product_GoodsType_Alloc, Object> COLUMN_Carrier_Product_GoodsType_Alloc_ID = new ModelColumn<>(I_Carrier_Product_GoodsType_Alloc.class, "Carrier_Product_GoodsType_Alloc_ID", null);
	String COLUMNNAME_Carrier_Product_GoodsType_Alloc_ID = "Carrier_Product_GoodsType_Alloc_ID";

	/**
	 * Set Carrier Product.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setCarrier_Product_ID (int Carrier_Product_ID);

	/**
	 * Get Carrier Product.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getCarrier_Product_ID();

	ModelColumn<I_Carrier_Product_GoodsType_Alloc, org.compiere.model.I_Carrier_Product> COLUMN_Carrier_Product_ID = new ModelColumn<>(I_Carrier_Product_GoodsType_Alloc.class, "Carrier_Product_ID", org.compiere.model.I_Carrier_Product.class);
	String COLUMNNAME_Carrier_Product_ID = "Carrier_Product_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_Carrier_Product_GoodsType_Alloc, Object> COLUMN_Created = new ModelColumn<>(I_Carrier_Product_GoodsType_Alloc.class, "Created", null);
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

	ModelColumn<I_Carrier_Product_GoodsType_Alloc, Object> COLUMN_IsActive = new ModelColumn<>(I_Carrier_Product_GoodsType_Alloc.class, "IsActive", null);
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

	ModelColumn<I_Carrier_Product_GoodsType_Alloc, Object> COLUMN_Updated = new ModelColumn<>(I_Carrier_Product_GoodsType_Alloc.class, "Updated", null);
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
