package org.compiere.model;

import java.math.BigDecimal;
import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for MD_Available_For_Sales
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_MD_Available_For_Sales 
{

	String Table_Name = "MD_Available_For_Sales";

//	/** AD_Table_ID=542164 */
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

	ModelColumn<I_MD_Available_For_Sales, Object> COLUMN_Created = new ModelColumn<>(I_MD_Available_For_Sales.class, "Created", null);
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

	ModelColumn<I_MD_Available_For_Sales, Object> COLUMN_IsActive = new ModelColumn<>(I_MD_Available_For_Sales.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

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
	 * Set Available for sales.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setMD_Available_For_Sales_ID (int MD_Available_For_Sales_ID);

	/**
	 * Get Available for sales.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getMD_Available_For_Sales_ID();

	ModelColumn<I_MD_Available_For_Sales, Object> COLUMN_MD_Available_For_Sales_ID = new ModelColumn<>(I_MD_Available_For_Sales.class, "MD_Available_For_Sales_ID", null);
	String COLUMNNAME_MD_Available_For_Sales_ID = "MD_Available_For_Sales_ID";

	/**
	 * Set Qty On Hand Stock.
	 * Qty On Hand Stock
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQtyOnHandStock (@Nullable BigDecimal QtyOnHandStock);

	/**
	 * Get Qty On Hand Stock.
	 * Qty On Hand Stock
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyOnHandStock();

	ModelColumn<I_MD_Available_For_Sales, Object> COLUMN_QtyOnHandStock = new ModelColumn<>(I_MD_Available_For_Sales.class, "QtyOnHandStock", null);
	String COLUMNNAME_QtyOnHandStock = "QtyOnHandStock";

	/**
	 * Set QtyToBeShipped.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQtyToBeShipped (@Nullable BigDecimal QtyToBeShipped);

	/**
	 * Get QtyToBeShipped.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyToBeShipped();

	ModelColumn<I_MD_Available_For_Sales, Object> COLUMN_QtyToBeShipped = new ModelColumn<>(I_MD_Available_For_Sales.class, "QtyToBeShipped", null);
	String COLUMNNAME_QtyToBeShipped = "QtyToBeShipped";

	/**
	 * Set StorageAttributesKey (technical).
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setStorageAttributesKey (java.lang.String StorageAttributesKey);

	/**
	 * Get StorageAttributesKey (technical).
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getStorageAttributesKey();

	ModelColumn<I_MD_Available_For_Sales, Object> COLUMN_StorageAttributesKey = new ModelColumn<>(I_MD_Available_For_Sales.class, "StorageAttributesKey", null);
	String COLUMNNAME_StorageAttributesKey = "StorageAttributesKey";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_MD_Available_For_Sales, Object> COLUMN_Updated = new ModelColumn<>(I_MD_Available_For_Sales.class, "Updated", null);
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
