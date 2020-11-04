package de.metas.material.dispo.model;


/** Generated Interface for MD_Candidate_ATP_QueryResult
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_MD_Candidate_ATP_QueryResult 
{

    /** TableName=MD_Candidate_ATP_QueryResult */
    public static final String Table_Name = "MD_Candidate_ATP_QueryResult";

    /** AD_Table_ID=540859 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);


	/**
	 * Set Business Partner .
	 * Identifies a Business Partner
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_BPartner_Customer_ID (int C_BPartner_Customer_ID);

	/**
	 * Get Business Partner .
	 * Identifies a Business Partner
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_BPartner_Customer_ID();

    /** Column name C_BPartner_Customer_ID */
    public static final String COLUMNNAME_C_BPartner_Customer_ID = "C_BPartner_Customer_ID";

	/**
	 * Set Plandatum.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDateProjected (java.sql.Timestamp DateProjected);

	/**
	 * Get Plandatum.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getDateProjected();

    /** Column definition for DateProjected */
    public static final org.adempiere.model.ModelColumn<I_MD_Candidate_ATP_QueryResult, Object> COLUMN_DateProjected = new org.adempiere.model.ModelColumn<I_MD_Candidate_ATP_QueryResult, Object>(I_MD_Candidate_ATP_QueryResult.class, "DateProjected", null);
    /** Column name DateProjected */
    public static final String COLUMNNAME_DateProjected = "DateProjected";

	/**
	 * Set Product.
	 * Product, Service, Item
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_Product_ID (int M_Product_ID);

	/**
	 * Get Product.
	 * Product, Service, Item
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_Product_ID();

    /** Column definition for M_Product_ID */
    public static final org.adempiere.model.ModelColumn<I_MD_Candidate_ATP_QueryResult, Object> COLUMN_M_Product_ID = new org.adempiere.model.ModelColumn<I_MD_Candidate_ATP_QueryResult, Object>(I_MD_Candidate_ATP_QueryResult.class, "M_Product_ID", null);
    /** Column name M_Product_ID */
    public static final String COLUMNNAME_M_Product_ID = "M_Product_ID";

	/**
	 * Set Warehouse.
	 * Storage Warehouse and Service Point
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_Warehouse_ID (int M_Warehouse_ID);

	/**
	 * Get Warehouse.
	 * Storage Warehouse and Service Point
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_Warehouse_ID();

    /** Column definition for M_Warehouse_ID */
    public static final org.adempiere.model.ModelColumn<I_MD_Candidate_ATP_QueryResult, Object> COLUMN_M_Warehouse_ID = new org.adempiere.model.ModelColumn<I_MD_Candidate_ATP_QueryResult, Object>(I_MD_Candidate_ATP_QueryResult.class, "M_Warehouse_ID", null);
    /** Column name M_Warehouse_ID */
    public static final String COLUMNNAME_M_Warehouse_ID = "M_Warehouse_ID";

	/**
	 * Set Quantity.
	 * Quantity
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setQty (java.math.BigDecimal Qty);

	/**
	 * Get Quantity.
	 * Quantity
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQty();

    /** Column definition for Qty */
    public static final org.adempiere.model.ModelColumn<I_MD_Candidate_ATP_QueryResult, Object> COLUMN_Qty = new org.adempiere.model.ModelColumn<I_MD_Candidate_ATP_QueryResult, Object>(I_MD_Candidate_ATP_QueryResult.class, "Qty", null);
    /** Column name Qty */
    public static final String COLUMNNAME_Qty = "Qty";

	/**
	 * Set SeqNo.
	 * Method of ordering records;
 lowest number comes first
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setSeqNo (int SeqNo);

	/**
	 * Get SeqNo.
	 * Method of ordering records;
 lowest number comes first
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getSeqNo();

    /** Column definition for SeqNo */
    public static final org.adempiere.model.ModelColumn<I_MD_Candidate_ATP_QueryResult, Object> COLUMN_SeqNo = new org.adempiere.model.ModelColumn<I_MD_Candidate_ATP_QueryResult, Object>(I_MD_Candidate_ATP_QueryResult.class, "SeqNo", null);
    /** Column name SeqNo */
    public static final String COLUMNNAME_SeqNo = "SeqNo";

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
    public static final org.adempiere.model.ModelColumn<I_MD_Candidate_ATP_QueryResult, Object> COLUMN_StorageAttributesKey = new org.adempiere.model.ModelColumn<I_MD_Candidate_ATP_QueryResult, Object>(I_MD_Candidate_ATP_QueryResult.class, "StorageAttributesKey", null);
    /** Column name StorageAttributesKey */
    public static final String COLUMNNAME_StorageAttributesKey = "StorageAttributesKey";
}
