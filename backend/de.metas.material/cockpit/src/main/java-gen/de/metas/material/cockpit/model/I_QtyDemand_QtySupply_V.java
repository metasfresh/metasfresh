package de.metas.material.cockpit.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;
import java.math.BigDecimal;

/** Generated Interface for QtyDemand_QtySupply_V
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_QtyDemand_QtySupply_V 
{

	String Table_Name = "QtyDemand_QtySupply_V";

//	/** AD_Table_ID=542218 */
//	int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);


	/**
	 * Get Client.
	 * Client/Tenant for this installation.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_Client_ID();

	String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

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
	 * Set AttributesKey (technical).
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAttributesKey (@Nullable java.lang.String AttributesKey);

	/**
	 * Get AttributesKey (technical).
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getAttributesKey();

	ModelColumn<I_QtyDemand_QtySupply_V, Object> COLUMN_AttributesKey = new ModelColumn<>(I_QtyDemand_QtySupply_V.class, "AttributesKey", null);
	String COLUMNNAME_AttributesKey = "AttributesKey";

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
	 * Set Product Category.
	 * Category of a Product
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_Product_Category_ID (int M_Product_Category_ID);

	/**
	 * Get Product Category.
	 * Category of a Product
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_Product_Category_ID();

	String COLUMNNAME_M_Product_Category_ID = "M_Product_Category_ID";

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
	 * Set Warehouse.
	 * Storage Warehouse and Service Point
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_Warehouse_ID (int M_Warehouse_ID);

	/**
	 * Get Warehouse.
	 * Storage Warehouse and Service Point
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_Warehouse_ID();

	String COLUMNNAME_M_Warehouse_ID = "M_Warehouse_ID";

	/**
	 * Set Product Name.
	 * Name of the Product
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setProductName (@Nullable java.lang.String ProductName);

	/**
	 * Get Product Name.
	 * Name of the Product
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getProductName();

	ModelColumn<I_QtyDemand_QtySupply_V, Object> COLUMN_ProductName = new ModelColumn<>(I_QtyDemand_QtySupply_V.class, "ProductName", null);
	String COLUMNNAME_ProductName = "ProductName";

	/**
	 * Set Product Value.
	 * Product identifier;
 "val-<search key>", "ext-<external id>" or internal M_Product_ID
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setProductValue (@Nullable java.lang.String ProductValue);

	/**
	 * Get Product Value.
	 * Product identifier;
 "val-<search key>", "ext-<external id>" or internal M_Product_ID
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getProductValue();

	ModelColumn<I_QtyDemand_QtySupply_V, Object> COLUMN_ProductValue = new ModelColumn<>(I_QtyDemand_QtySupply_V.class, "ProductValue", null);
	String COLUMNNAME_ProductValue = "ProductValue";

	/**
	 * Set MD_Cockpit QtyDemand and QtySupply.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQtyDemand_QtySupply_V_ID (int QtyDemand_QtySupply_V_ID);

	/**
	 * Get MD_Cockpit QtyDemand and QtySupply.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getQtyDemand_QtySupply_V_ID();

	ModelColumn<I_QtyDemand_QtySupply_V, Object> COLUMN_QtyDemand_QtySupply_V_ID = new ModelColumn<>(I_QtyDemand_QtySupply_V.class, "QtyDemand_QtySupply_V_ID", null);
	String COLUMNNAME_QtyDemand_QtySupply_V_ID = "QtyDemand_QtySupply_V_ID";

	/**
	 * Set Forecast - pending.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQtyForecasted (@Nullable BigDecimal QtyForecasted);

	/**
	 * Get Forecast - pending.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyForecasted();

	ModelColumn<I_QtyDemand_QtySupply_V, Object> COLUMN_QtyForecasted = new ModelColumn<>(I_QtyDemand_QtySupply_V.class, "QtyForecasted", null);
	String COLUMNNAME_QtyForecasted = "QtyForecasted";

	/**
	 * Set Qty Reserved.
	 * Open Qty
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQtyReserved (@Nullable BigDecimal QtyReserved);

	/**
	 * Get Qty Reserved.
	 * Open Qty
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyReserved();

	ModelColumn<I_QtyDemand_QtySupply_V, Object> COLUMN_QtyReserved = new ModelColumn<>(I_QtyDemand_QtySupply_V.class, "QtyReserved", null);
	String COLUMNNAME_QtyReserved = "QtyReserved";

	/**
	 * Set Stock.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQtyStock (@Nullable BigDecimal QtyStock);

	/**
	 * Get Stock.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyStock();

	ModelColumn<I_QtyDemand_QtySupply_V, Object> COLUMN_QtyStock = new ModelColumn<>(I_QtyDemand_QtySupply_V.class, "QtyStock", null);
	String COLUMNNAME_QtyStock = "QtyStock";

	/**
	 * Set Quantity to move.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQtyToMove (@Nullable BigDecimal QtyToMove);

	/**
	 * Get Quantity to move.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyToMove();

	ModelColumn<I_QtyDemand_QtySupply_V, Object> COLUMN_QtyToMove = new ModelColumn<>(I_QtyDemand_QtySupply_V.class, "QtyToMove", null);
	String COLUMNNAME_QtyToMove = "QtyToMove";

	/**
	 * Set Production - pending.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQtyToProduce (@Nullable BigDecimal QtyToProduce);

	/**
	 * Get Production - pending.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyToProduce();

	ModelColumn<I_QtyDemand_QtySupply_V, Object> COLUMN_QtyToProduce = new ModelColumn<>(I_QtyDemand_QtySupply_V.class, "QtyToProduce", null);
	String COLUMNNAME_QtyToProduce = "QtyToProduce";
}
