package de.metas.esb.edi.model;


/** Generated Interface for EDI_DesadvLine
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_EDI_DesadvLine 
{

    /** TableName=EDI_DesadvLine */
    public static final String Table_Name = "EDI_DesadvLine";

    /** AD_Table_ID=540645 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);


	/**
	 * Get Client.
	 * Client/Tenant for this installation.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Client_ID();

    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Organisation.
	 * Organisational entity within client
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Organisation.
	 * Organisational entity within client
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Org_ID();

    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getCreated();

    /** Column definition for Created */
    public static final org.adempiere.model.ModelColumn<I_EDI_DesadvLine, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_EDI_DesadvLine, Object>(I_EDI_DesadvLine.class, "Created", null);
    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/**
	 * Get Created By.
	 * User who created this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getCreatedBy();

    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set UOM.
	 * Unit of Measure
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_UOM_ID (int C_UOM_ID);

	/**
	 * Get UOM.
	 * Unit of Measure
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_UOM_ID();

    /** Column name C_UOM_ID */
    public static final String COLUMNNAME_C_UOM_ID = "C_UOM_ID";

	/**
	 * Set CU-EAN.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setEAN_CU (java.lang.String EAN_CU);

	/**
	 * Get CU-EAN.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getEAN_CU();

    /** Column definition for EAN_CU */
    public static final org.adempiere.model.ModelColumn<I_EDI_DesadvLine, Object> COLUMN_EAN_CU = new org.adempiere.model.ModelColumn<I_EDI_DesadvLine, Object>(I_EDI_DesadvLine.class, "EAN_CU", null);
    /** Column name EAN_CU */
    public static final String COLUMNNAME_EAN_CU = "EAN_CU";

	/**
	 * Set TU-EAN.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setEAN_TU (java.lang.String EAN_TU);

	/**
	 * Get TU-EAN.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getEAN_TU();

    /** Column definition for EAN_TU */
    public static final org.adempiere.model.ModelColumn<I_EDI_DesadvLine, Object> COLUMN_EAN_TU = new org.adempiere.model.ModelColumn<I_EDI_DesadvLine, Object>(I_EDI_DesadvLine.class, "EAN_TU", null);
    /** Column name EAN_TU */
    public static final String COLUMNNAME_EAN_TU = "EAN_TU";

	/**
	 * Set DESADV.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setEDI_Desadv_ID (int EDI_Desadv_ID);

	/**
	 * Get DESADV.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getEDI_Desadv_ID();

	public de.metas.esb.edi.model.I_EDI_Desadv getEDI_Desadv();

	public void setEDI_Desadv(de.metas.esb.edi.model.I_EDI_Desadv EDI_Desadv);

    /** Column definition for EDI_Desadv_ID */
    public static final org.adempiere.model.ModelColumn<I_EDI_DesadvLine, de.metas.esb.edi.model.I_EDI_Desadv> COLUMN_EDI_Desadv_ID = new org.adempiere.model.ModelColumn<I_EDI_DesadvLine, de.metas.esb.edi.model.I_EDI_Desadv>(I_EDI_DesadvLine.class, "EDI_Desadv_ID", de.metas.esb.edi.model.I_EDI_Desadv.class);
    /** Column name EDI_Desadv_ID */
    public static final String COLUMNNAME_EDI_Desadv_ID = "EDI_Desadv_ID";

	/**
	 * Set DESADV Line.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setEDI_DesadvLine_ID (int EDI_DesadvLine_ID);

	/**
	 * Get DESADV Line.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getEDI_DesadvLine_ID();

    /** Column definition for EDI_DesadvLine_ID */
    public static final org.adempiere.model.ModelColumn<I_EDI_DesadvLine, Object> COLUMN_EDI_DesadvLine_ID = new org.adempiere.model.ModelColumn<I_EDI_DesadvLine, Object>(I_EDI_DesadvLine.class, "EDI_DesadvLine_ID", null);
    /** Column name EDI_DesadvLine_ID */
    public static final String COLUMNNAME_EDI_DesadvLine_ID = "EDI_DesadvLine_ID";

	/**
	 * Set GTIN.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setGTIN (java.lang.String GTIN);

	/**
	 * Get GTIN.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getGTIN();

    /** Column definition for GTIN */
    public static final org.adempiere.model.ModelColumn<I_EDI_DesadvLine, Object> COLUMN_GTIN = new org.adempiere.model.ModelColumn<I_EDI_DesadvLine, Object>(I_EDI_DesadvLine.class, "GTIN", null);
    /** Column name GTIN */
    public static final String COLUMNNAME_GTIN = "GTIN";

	/**
	 * Set Invoicable Quantity per.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setInvoicableQtyBasedOn (java.lang.String InvoicableQtyBasedOn);

	/**
	 * Get Invoicable Quantity per.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getInvoicableQtyBasedOn();

    /** Column definition for InvoicableQtyBasedOn */
    public static final org.adempiere.model.ModelColumn<I_EDI_DesadvLine, Object> COLUMN_InvoicableQtyBasedOn = new org.adempiere.model.ModelColumn<I_EDI_DesadvLine, Object>(I_EDI_DesadvLine.class, "InvoicableQtyBasedOn", null);
    /** Column name InvoicableQtyBasedOn */
    public static final String COLUMNNAME_InvoicableQtyBasedOn = "InvoicableQtyBasedOn";

	/**
	 * Set Active.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsActive (boolean IsActive);

	/**
	 * Get Active.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isActive();

    /** Column definition for IsActive */
    public static final org.adempiere.model.ModelColumn<I_EDI_DesadvLine, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_EDI_DesadvLine, Object>(I_EDI_DesadvLine.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Spätere Nachlieferung.
	 * Falls "ja", wird das Feld "Abweichungscode" in der DESADV-Datei auf "BP" (back order to follow) gesetzt, d.h. es wird signalisiert, das später noch eine Nachliefrung erfolgen wird.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsSubsequentDeliveryPlanned (boolean IsSubsequentDeliveryPlanned);

	/**
	 * Get Spätere Nachlieferung.
	 * Falls "ja", wird das Feld "Abweichungscode" in der DESADV-Datei auf "BP" (back order to follow) gesetzt, d.h. es wird signalisiert, das später noch eine Nachliefrung erfolgen wird.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isSubsequentDeliveryPlanned();

    /** Column definition for IsSubsequentDeliveryPlanned */
    public static final org.adempiere.model.ModelColumn<I_EDI_DesadvLine, Object> COLUMN_IsSubsequentDeliveryPlanned = new org.adempiere.model.ModelColumn<I_EDI_DesadvLine, Object>(I_EDI_DesadvLine.class, "IsSubsequentDeliveryPlanned", null);
    /** Column name IsSubsequentDeliveryPlanned */
    public static final String COLUMNNAME_IsSubsequentDeliveryPlanned = "IsSubsequentDeliveryPlanned";

	/**
	 * Set SeqNo..
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setLine (int Line);

	/**
	 * Get SeqNo..
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getLine();

    /** Column definition for Line */
    public static final org.adempiere.model.ModelColumn<I_EDI_DesadvLine, Object> COLUMN_Line = new org.adempiere.model.ModelColumn<I_EDI_DesadvLine, Object>(I_EDI_DesadvLine.class, "Line", null);
    /** Column name Line */
    public static final String COLUMNNAME_Line = "Line";

	/**
	 * Set Product.
	 * Product, Service, Item
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_Product_ID (int M_Product_ID);

	/**
	 * Get Product.
	 * Product, Service, Item
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_Product_ID();

    /** Column name M_Product_ID */
    public static final String COLUMNNAME_M_Product_ID = "M_Product_ID";

	/**
	 * Set Price Actual.
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPriceActual (java.math.BigDecimal PriceActual);

	/**
	 * Get Price Actual.
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getPriceActual();

    /** Column definition for PriceActual */
    public static final org.adempiere.model.ModelColumn<I_EDI_DesadvLine, Object> COLUMN_PriceActual = new org.adempiere.model.ModelColumn<I_EDI_DesadvLine, Object>(I_EDI_DesadvLine.class, "PriceActual", null);
    /** Column name PriceActual */
    public static final String COLUMNNAME_PriceActual = "PriceActual";

	/**
	 * Set Produktbeschreibung.
	 * Produktbeschreibung
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setProductDescription (java.lang.String ProductDescription);

	/**
	 * Get Produktbeschreibung.
	 * Produktbeschreibung
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getProductDescription();

    /** Column definition for ProductDescription */
    public static final org.adempiere.model.ModelColumn<I_EDI_DesadvLine, Object> COLUMN_ProductDescription = new org.adempiere.model.ModelColumn<I_EDI_DesadvLine, Object>(I_EDI_DesadvLine.class, "ProductDescription", null);
    /** Column name ProductDescription */
    public static final String COLUMNNAME_ProductDescription = "ProductDescription";

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
    public static final org.adempiere.model.ModelColumn<I_EDI_DesadvLine, Object> COLUMN_ProductNo = new org.adempiere.model.ModelColumn<I_EDI_DesadvLine, Object>(I_EDI_DesadvLine.class, "ProductNo", null);
    /** Column name ProductNo */
    public static final String COLUMNNAME_ProductNo = "ProductNo";

	/**
	 * Set Delivered (stock unit).
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setQtyDeliveredInStockingUOM (java.math.BigDecimal QtyDeliveredInStockingUOM);

	/**
	 * Get Delivered (stock unit).
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyDeliveredInStockingUOM();

    /** Column definition for QtyDeliveredInStockingUOM */
    public static final org.adempiere.model.ModelColumn<I_EDI_DesadvLine, Object> COLUMN_QtyDeliveredInStockingUOM = new org.adempiere.model.ModelColumn<I_EDI_DesadvLine, Object>(I_EDI_DesadvLine.class, "QtyDeliveredInStockingUOM", null);
    /** Column name QtyDeliveredInStockingUOM */
    public static final String COLUMNNAME_QtyDeliveredInStockingUOM = "QtyDeliveredInStockingUOM";

	/**
	 * Set Delivered quantity.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setQtyDeliveredInUOM (java.math.BigDecimal QtyDeliveredInUOM);

	/**
	 * Get Delivered quantity.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyDeliveredInUOM();

    /** Column definition for QtyDeliveredInUOM */
    public static final org.adempiere.model.ModelColumn<I_EDI_DesadvLine, Object> COLUMN_QtyDeliveredInUOM = new org.adempiere.model.ModelColumn<I_EDI_DesadvLine, Object>(I_EDI_DesadvLine.class, "QtyDeliveredInUOM", null);
    /** Column name QtyDeliveredInUOM */
    public static final String COLUMNNAME_QtyDeliveredInUOM = "QtyDeliveredInUOM";

	/**
	 * Set Qty.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setQtyEntered (java.math.BigDecimal QtyEntered);

	/**
	 * Get Qty.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyEntered();

    /** Column definition for QtyEntered */
    public static final org.adempiere.model.ModelColumn<I_EDI_DesadvLine, Object> COLUMN_QtyEntered = new org.adempiere.model.ModelColumn<I_EDI_DesadvLine, Object>(I_EDI_DesadvLine.class, "QtyEntered", null);
    /** Column name QtyEntered */
    public static final String COLUMNNAME_QtyEntered = "QtyEntered";

	/**
	 * Set Qty Ordered.
	 * Qty Ordered
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setQtyOrdered (java.math.BigDecimal QtyOrdered);

	/**
	 * Get Qty Ordered.
	 * Qty Ordered
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyOrdered();

    /** Column definition for QtyOrdered */
    public static final org.adempiere.model.ModelColumn<I_EDI_DesadvLine, Object> COLUMN_QtyOrdered = new org.adempiere.model.ModelColumn<I_EDI_DesadvLine, Object>(I_EDI_DesadvLine.class, "QtyOrdered", null);
    /** Column name QtyOrdered */
    public static final String COLUMNNAME_QtyOrdered = "QtyOrdered";

	/**
	 * Set CU-UPC.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setUPC_CU (java.lang.String UPC_CU);

	/**
	 * Get CU-UPC.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getUPC_CU();

    /** Column definition for UPC_CU */
    public static final org.adempiere.model.ModelColumn<I_EDI_DesadvLine, Object> COLUMN_UPC_CU = new org.adempiere.model.ModelColumn<I_EDI_DesadvLine, Object>(I_EDI_DesadvLine.class, "UPC_CU", null);
    /** Column name UPC_CU */
    public static final String COLUMNNAME_UPC_CU = "UPC_CU";

	/**
	 * Set TU-UPC.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setUPC_TU (java.lang.String UPC_TU);

	/**
	 * Get TU-UPC.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getUPC_TU();

    /** Column definition for UPC_TU */
    public static final org.adempiere.model.ModelColumn<I_EDI_DesadvLine, Object> COLUMN_UPC_TU = new org.adempiere.model.ModelColumn<I_EDI_DesadvLine, Object>(I_EDI_DesadvLine.class, "UPC_TU", null);
    /** Column name UPC_TU */
    public static final String COLUMNNAME_UPC_TU = "UPC_TU";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getUpdated();

    /** Column definition for Updated */
    public static final org.adempiere.model.ModelColumn<I_EDI_DesadvLine, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_EDI_DesadvLine, Object>(I_EDI_DesadvLine.class, "Updated", null);
    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Updated By.
	 * User who updated this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getUpdatedBy();

    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
