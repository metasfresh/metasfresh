package de.metas.material.cockpit.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;
import java.math.BigDecimal;

/** Generated Interface for MD_Available_For_Sales_QueryResult
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_MD_Available_For_Sales_QueryResult 
{

	String Table_Name = "MD_Available_For_Sales_QueryResult";

//	/** AD_Table_ID=541340 */
//	int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);


	/**
	 * Set Organisation.
	 * Organisational entity within client
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Organisation.
	 * Organisational entity within client
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_Org_ID();

	String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set UOM.
	 * Unit of Measure
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_UOM_ID (int C_UOM_ID);

	/**
	 * Get UOM.
	 * Unit of Measure
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_UOM_ID();

	String COLUMNNAME_C_UOM_ID = "C_UOM_ID";

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

	ModelColumn<I_MD_Available_For_Sales_QueryResult, Object> COLUMN_QtyOnHandStock = new ModelColumn<>(I_MD_Available_For_Sales_QueryResult.class, "QtyOnHandStock", null);
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

	ModelColumn<I_MD_Available_For_Sales_QueryResult, Object> COLUMN_QtyToBeShipped = new ModelColumn<>(I_MD_Available_For_Sales_QueryResult.class, "QtyToBeShipped", null);
	String COLUMNNAME_QtyToBeShipped = "QtyToBeShipped";

	/**
	 * Set QueryNo.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setQueryNo (int QueryNo);

	/**
	 * Get QueryNo.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getQueryNo();

	ModelColumn<I_MD_Available_For_Sales_QueryResult, Object> COLUMN_QueryNo = new ModelColumn<>(I_MD_Available_For_Sales_QueryResult.class, "QueryNo", null);
	String COLUMNNAME_QueryNo = "QueryNo";

	/**
	 * Set StorageAttributesKey (technical).
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setStorageAttributesKey (@Nullable String StorageAttributesKey);

	/**
	 * Get StorageAttributesKey (technical).
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable String getStorageAttributesKey();

	ModelColumn<I_MD_Available_For_Sales_QueryResult, Object> COLUMN_StorageAttributesKey = new ModelColumn<>(I_MD_Available_For_Sales_QueryResult.class, "StorageAttributesKey", null);
	String COLUMNNAME_StorageAttributesKey = "StorageAttributesKey";
}
