package de.metas.esb.edi.model;


/** Generated Interface for EDI_C_BPartner_Product_v
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_EDI_C_BPartner_Product_v 
{

    /** TableName=EDI_C_BPartner_Product_v */
    public static final String Table_Name = "EDI_C_BPartner_Product_v";

    /** AD_Table_ID=53713 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(3);

    /** Load Meta Data */

	/**
	 * Set Geschäftspartner.
	 * Bezeichnet einen Geschäftspartner
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_BPartner_ID (int C_BPartner_ID);

	/**
	 * Get Geschäftspartner.
	 * Bezeichnet einen Geschäftspartner
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_BPartner_ID();

    /** Column definition for C_BPartner_ID */
    public static final org.adempiere.model.ModelColumn<I_EDI_C_BPartner_Product_v, org.compiere.model.I_C_BPartner> COLUMN_C_BPartner_ID = new org.adempiere.model.ModelColumn<I_EDI_C_BPartner_Product_v, org.compiere.model.I_C_BPartner>(I_EDI_C_BPartner_Product_v.class, "C_BPartner_ID", org.compiere.model.I_C_BPartner.class);
    /** Column name C_BPartner_ID */
    public static final String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/**
	 * Set Auftragsposition.
	 * Auftragsposition
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_OrderLine_ID (int C_OrderLine_ID);

	/**
	 * Get Auftragsposition.
	 * Auftragsposition
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_OrderLine_ID();

	public org.compiere.model.I_C_OrderLine getC_OrderLine();

	public void setC_OrderLine(org.compiere.model.I_C_OrderLine C_OrderLine);

    /** Column definition for C_OrderLine_ID */
    public static final org.adempiere.model.ModelColumn<I_EDI_C_BPartner_Product_v, org.compiere.model.I_C_OrderLine> COLUMN_C_OrderLine_ID = new org.adempiere.model.ModelColumn<I_EDI_C_BPartner_Product_v, org.compiere.model.I_C_OrderLine>(I_EDI_C_BPartner_Product_v.class, "C_OrderLine_ID", org.compiere.model.I_C_OrderLine.class);
    /** Column name C_OrderLine_ID */
    public static final String COLUMNNAME_C_OrderLine_ID = "C_OrderLine_ID";

	/**
	 * Set Versand-/Wareneingangsposition.
	 * Position auf Versand- oder Wareneingangsbeleg
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_InOutLine_ID (int M_InOutLine_ID);

	/**
	 * Get Versand-/Wareneingangsposition.
	 * Position auf Versand- oder Wareneingangsbeleg
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_InOutLine_ID();

	public org.compiere.model.I_M_InOutLine getM_InOutLine();

	public void setM_InOutLine(org.compiere.model.I_M_InOutLine M_InOutLine);

    /** Column definition for M_InOutLine_ID */
    public static final org.adempiere.model.ModelColumn<I_EDI_C_BPartner_Product_v, org.compiere.model.I_M_InOutLine> COLUMN_M_InOutLine_ID = new org.adempiere.model.ModelColumn<I_EDI_C_BPartner_Product_v, org.compiere.model.I_M_InOutLine>(I_EDI_C_BPartner_Product_v.class, "M_InOutLine_ID", org.compiere.model.I_M_InOutLine.class);
    /** Column name M_InOutLine_ID */
    public static final String COLUMNNAME_M_InOutLine_ID = "M_InOutLine_ID";

	/**
	 * Set Produkt.
	 * Produkt, Leistung, Artikel
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_Product_ID (int M_Product_ID);

	/**
	 * Get Produkt.
	 * Produkt, Leistung, Artikel
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_Product_ID();

    /** Column definition for M_Product_ID */
    public static final org.adempiere.model.ModelColumn<I_EDI_C_BPartner_Product_v, org.compiere.model.I_M_Product> COLUMN_M_Product_ID = new org.adempiere.model.ModelColumn<I_EDI_C_BPartner_Product_v, org.compiere.model.I_M_Product>(I_EDI_C_BPartner_Product_v.class, "M_Product_ID", org.compiere.model.I_M_Product.class);
    /** Column name M_Product_ID */
    public static final String COLUMNNAME_M_Product_ID = "M_Product_ID";

	/**
	 * Set Produktnummer.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setProductNo (java.lang.String ProductNo);

	/**
	 * Get Produktnummer.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getProductNo();

    /** Column definition for ProductNo */
    public static final org.adempiere.model.ModelColumn<I_EDI_C_BPartner_Product_v, Object> COLUMN_ProductNo = new org.adempiere.model.ModelColumn<I_EDI_C_BPartner_Product_v, Object>(I_EDI_C_BPartner_Product_v.class, "ProductNo", null);
    /** Column name ProductNo */
    public static final String COLUMNNAME_ProductNo = "ProductNo";

	/**
	 * Set UPC/EAN.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setUPC (java.lang.String UPC);

	/**
	 * Get UPC/EAN.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getUPC();

    /** Column definition for UPC */
    public static final org.adempiere.model.ModelColumn<I_EDI_C_BPartner_Product_v, Object> COLUMN_UPC = new org.adempiere.model.ModelColumn<I_EDI_C_BPartner_Product_v, Object>(I_EDI_C_BPartner_Product_v.class, "UPC", null);
    /** Column name UPC */
    public static final String COLUMNNAME_UPC = "UPC";
}
