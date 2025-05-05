package org.compiere.model;

import org.adempiere.model.ModelColumn;

import java.math.BigDecimal;

/**
 * Generated Interface for purchase_prices_in_stock_uom_plv_v
 *
 * @author metasfresh (generated)
 */
@SuppressWarnings("unused")
public interface I_purchase_prices_in_stock_uom_plv_v
{

	String Table_Name = "purchase_prices_in_stock_uom_plv_v";

	//	/** AD_Table_ID=542382 */
	//	int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

	/**
	 * Set Currency.
	 * The Currency for this record
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Currency_ID(int C_Currency_ID);

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
	 * Set UOM.
	 * Unit of Measure
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_UOM_ID(int C_UOM_ID);

	/**
	 * Get UOM.
	 * Unit of Measure
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_UOM_ID();

	String COLUMNNAME_C_UOM_ID = "C_UOM_ID";

	/**
	 * Set Price List.
	 * Unique identifier of a Price List
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_PriceList_ID(int M_PriceList_ID);

	/**
	 * Get Price List.
	 * Unique identifier of a Price List
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_PriceList_ID();

	String COLUMNNAME_M_PriceList_ID = "M_PriceList_ID";

	/**
	 * Set Price List Version.
	 * Identifies a unique instance of a Price List
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_PriceList_Version_ID(int M_PriceList_Version_ID);

	/**
	 * Get Price List Version.
	 * Identifies a unique instance of a Price List
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_PriceList_Version_ID();

	String COLUMNNAME_M_PriceList_Version_ID = "M_PriceList_Version_ID";

	/**
	 * Set Product.
	 * Product, Service, Item
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_Product_ID(int M_Product_ID);

	/**
	 * Get Product.
	 * Product, Service, Item
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_Product_ID();

	String COLUMNNAME_M_Product_ID = "M_Product_ID";

	/**
	 * Set ProductPriceInStockUOM.
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setProductPriceInStockUOM(BigDecimal ProductPriceInStockUOM);

	/**
	 * Get ProductPriceInStockUOM.
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getProductPriceInStockUOM();

	ModelColumn<I_purchase_prices_in_stock_uom_plv_v, Object> COLUMN_ProductPriceInStockUOM = new ModelColumn<>(I_purchase_prices_in_stock_uom_plv_v.class, "ProductPriceInStockUOM", null);
	String COLUMNNAME_ProductPriceInStockUOM = "ProductPriceInStockUOM";

	/**
	 * Set Valid From.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setValidFrom(java.sql.Timestamp ValidFrom);

	/**
	 * Get Valid From.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getValidFrom();

	ModelColumn<I_purchase_prices_in_stock_uom_plv_v, Object> COLUMN_ValidFrom = new ModelColumn<>(I_purchase_prices_in_stock_uom_plv_v.class, "ValidFrom", null);
	String COLUMNNAME_ValidFrom = "ValidFrom";

	/**
	 * Set Valid to.
	 * Valid to including this date (last day)
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setValidTo(java.sql.Timestamp ValidTo);

	/**
	 * Get Valid to.
	 * Valid to including this date (last day)
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getValidTo();

	ModelColumn<I_purchase_prices_in_stock_uom_plv_v, Object> COLUMN_ValidTo = new ModelColumn<>(I_purchase_prices_in_stock_uom_plv_v.class, "ValidTo", null);
	String COLUMNNAME_ValidTo = "ValidTo";
}
