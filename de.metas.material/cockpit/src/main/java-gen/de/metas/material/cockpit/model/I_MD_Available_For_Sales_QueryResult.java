package de.metas.material.cockpit.model;


/** Generated Interface for MD_Available_For_Sales_QueryResult
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_MD_Available_For_Sales_QueryResult 
{

    /** TableName=MD_Available_For_Sales_QueryResult */
    public static final String Table_Name = "MD_Available_For_Sales_QueryResult";

    /** AD_Table_ID=541340 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 4 - System
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(4);

    /** Load Meta Data */

	/**
	 * Set Maßeinheit.
	 * Maßeinheit
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_UOM_ID (int C_UOM_ID);

	/**
	 * Get Maßeinheit.
	 * Maßeinheit
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_UOM_ID();

	public org.compiere.model.I_C_UOM getC_UOM();

	public void setC_UOM(org.compiere.model.I_C_UOM C_UOM);

    /** Column definition for C_UOM_ID */
    public static final org.adempiere.model.ModelColumn<I_MD_Available_For_Sales_QueryResult, org.compiere.model.I_C_UOM> COLUMN_C_UOM_ID = new org.adempiere.model.ModelColumn<I_MD_Available_For_Sales_QueryResult, org.compiere.model.I_C_UOM>(I_MD_Available_For_Sales_QueryResult.class, "C_UOM_ID", org.compiere.model.I_C_UOM.class);
    /** Column name C_UOM_ID */
    public static final String COLUMNNAME_C_UOM_ID = "C_UOM_ID";

	/**
	 * Set Produkt.
	 * Produkt, Leistung, Artikel
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_Product_ID (int M_Product_ID);

	/**
	 * Get Produkt.
	 * Produkt, Leistung, Artikel
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_Product_ID();

	public org.compiere.model.I_M_Product getM_Product();

	public void setM_Product(org.compiere.model.I_M_Product M_Product);

    /** Column definition for M_Product_ID */
    public static final org.adempiere.model.ModelColumn<I_MD_Available_For_Sales_QueryResult, org.compiere.model.I_M_Product> COLUMN_M_Product_ID = new org.adempiere.model.ModelColumn<I_MD_Available_For_Sales_QueryResult, org.compiere.model.I_M_Product>(I_MD_Available_For_Sales_QueryResult.class, "M_Product_ID", org.compiere.model.I_M_Product.class);
    /** Column name M_Product_ID */
    public static final String COLUMNNAME_M_Product_ID = "M_Product_ID";

	/**
	 * Set Lagerbestand.
	 * Aktueller oder geplanter Lagerbestand
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setQtyOnHandStock (java.math.BigDecimal QtyOnHandStock);

	/**
	 * Get Lagerbestand.
	 * Aktueller oder geplanter Lagerbestand
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyOnHandStock();

    /** Column definition for QtyOnHandStock */
    public static final org.adempiere.model.ModelColumn<I_MD_Available_For_Sales_QueryResult, Object> COLUMN_QtyOnHandStock = new org.adempiere.model.ModelColumn<I_MD_Available_For_Sales_QueryResult, Object>(I_MD_Available_For_Sales_QueryResult.class, "QtyOnHandStock", null);
    /** Column name QtyOnHandStock */
    public static final String COLUMNNAME_QtyOnHandStock = "QtyOnHandStock";

	/**
	 * Set QtyToBeShipped.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setQtyToBeShipped (java.math.BigDecimal QtyToBeShipped);

	/**
	 * Get QtyToBeShipped.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyToBeShipped();

    /** Column definition for QtyToBeShipped */
    public static final org.adempiere.model.ModelColumn<I_MD_Available_For_Sales_QueryResult, Object> COLUMN_QtyToBeShipped = new org.adempiere.model.ModelColumn<I_MD_Available_For_Sales_QueryResult, Object>(I_MD_Available_For_Sales_QueryResult.class, "QtyToBeShipped", null);
    /** Column name QtyToBeShipped */
    public static final String COLUMNNAME_QtyToBeShipped = "QtyToBeShipped";

	/**
	 * Set QueryNo.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setQueryNo (int QueryNo);

	/**
	 * Get QueryNo.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getQueryNo();

    /** Column definition for QueryNo */
    public static final org.adempiere.model.ModelColumn<I_MD_Available_For_Sales_QueryResult, Object> COLUMN_QueryNo = new org.adempiere.model.ModelColumn<I_MD_Available_For_Sales_QueryResult, Object>(I_MD_Available_For_Sales_QueryResult.class, "QueryNo", null);
    /** Column name QueryNo */
    public static final String COLUMNNAME_QueryNo = "QueryNo";

	/**
	 * Set StorageAttributesKey (technical).
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setStorageAttributesKey (java.lang.String StorageAttributesKey);

	/**
	 * Get StorageAttributesKey (technical).
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getStorageAttributesKey();

    /** Column definition for StorageAttributesKey */
    public static final org.adempiere.model.ModelColumn<I_MD_Available_For_Sales_QueryResult, Object> COLUMN_StorageAttributesKey = new org.adempiere.model.ModelColumn<I_MD_Available_For_Sales_QueryResult, Object>(I_MD_Available_For_Sales_QueryResult.class, "StorageAttributesKey", null);
    /** Column name StorageAttributesKey */
    public static final String COLUMNNAME_StorageAttributesKey = "StorageAttributesKey";
}
