package de.metas.esb.edi.model;

import java.math.BigDecimal;
import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for EDI_DesadvLine
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_EDI_DesadvLine 
{

	String Table_Name = "EDI_DesadvLine";

//	/** AD_Table_ID=540645 */
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

	ModelColumn<I_EDI_DesadvLine, Object> COLUMN_Created = new ModelColumn<>(I_EDI_DesadvLine.class, "Created", null);
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
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_UOM_ID (int C_UOM_ID);

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
	 * Set Invoicing-UOM.
	 * Maßeinheit in der die betreffende Zeile abgerechnet wird
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_UOM_Invoice_ID (int C_UOM_Invoice_ID);

	/**
	 * Get Invoicing-UOM.
	 * Maßeinheit in der die betreffende Zeile abgerechnet wird
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_UOM_Invoice_ID();

	String COLUMNNAME_C_UOM_Invoice_ID = "C_UOM_Invoice_ID";

	/**
	 * Set EanCom_Invoice_UOM.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setEanCom_Invoice_UOM (@Nullable java.lang.String EanCom_Invoice_UOM);

	/**
	 * Get EanCom_Invoice_UOM.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	@Nullable java.lang.String getEanCom_Invoice_UOM();

	ModelColumn<I_EDI_DesadvLine, Object> COLUMN_EanCom_Invoice_UOM = new ModelColumn<>(I_EDI_DesadvLine.class, "EanCom_Invoice_UOM", null);
	String COLUMNNAME_EanCom_Invoice_UOM = "EanCom_Invoice_UOM";

	/**
	 * Set CU-EAN.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setEAN_CU (@Nullable java.lang.String EAN_CU);

	/**
	 * Get CU-EAN.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getEAN_CU();

	ModelColumn<I_EDI_DesadvLine, Object> COLUMN_EAN_CU = new ModelColumn<>(I_EDI_DesadvLine.class, "EAN_CU", null);
	String COLUMNNAME_EAN_CU = "EAN_CU";

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

	ModelColumn<I_EDI_DesadvLine, Object> COLUMN_EAN_TU = new ModelColumn<>(I_EDI_DesadvLine.class, "EAN_TU", null);
	String COLUMNNAME_EAN_TU = "EAN_TU";

	/**
	 * Set DESADV.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setEDI_Desadv_ID (int EDI_Desadv_ID);

	/**
	 * Get DESADV.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getEDI_Desadv_ID();

	de.metas.esb.edi.model.I_EDI_Desadv getEDI_Desadv();

	void setEDI_Desadv(de.metas.esb.edi.model.I_EDI_Desadv EDI_Desadv);

	ModelColumn<I_EDI_DesadvLine, de.metas.esb.edi.model.I_EDI_Desadv> COLUMN_EDI_Desadv_ID = new ModelColumn<>(I_EDI_DesadvLine.class, "EDI_Desadv_ID", de.metas.esb.edi.model.I_EDI_Desadv.class);
	String COLUMNNAME_EDI_Desadv_ID = "EDI_Desadv_ID";

	/**
	 * Set DESADV Line.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setEDI_DesadvLine_ID (int EDI_DesadvLine_ID);

	/**
	 * Get DESADV Line.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getEDI_DesadvLine_ID();

	ModelColumn<I_EDI_DesadvLine, Object> COLUMN_EDI_DesadvLine_ID = new ModelColumn<>(I_EDI_DesadvLine.class, "EDI_DesadvLine_ID", null);
	String COLUMNNAME_EDI_DesadvLine_ID = "EDI_DesadvLine_ID";

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

	ModelColumn<I_EDI_DesadvLine, Object> COLUMN_GTIN = new ModelColumn<>(I_EDI_DesadvLine.class, "GTIN", null);
	String COLUMNNAME_GTIN = "GTIN";

	/**
	 * Set Invoicable Quantity per.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setInvoicableQtyBasedOn (java.lang.String InvoicableQtyBasedOn);

	/**
	 * Get Invoicable Quantity per.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getInvoicableQtyBasedOn();

	ModelColumn<I_EDI_DesadvLine, Object> COLUMN_InvoicableQtyBasedOn = new ModelColumn<>(I_EDI_DesadvLine.class, "InvoicableQtyBasedOn", null);
	String COLUMNNAME_InvoicableQtyBasedOn = "InvoicableQtyBasedOn";

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

	ModelColumn<I_EDI_DesadvLine, Object> COLUMN_IsActive = new ModelColumn<>(I_EDI_DesadvLine.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Spätere Nachlieferung.
	 * Falls "ja", wird das Feld "Abweichungscode" in der DESADV-Datei auf "BP" (back order to follow) gesetzt, d.h. es wird signalisiert, das später noch eine Nachliefrung erfolgen wird.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsSubsequentDeliveryPlanned (boolean IsSubsequentDeliveryPlanned);

	/**
	 * Get Spätere Nachlieferung.
	 * Falls "ja", wird das Feld "Abweichungscode" in der DESADV-Datei auf "BP" (back order to follow) gesetzt, d.h. es wird signalisiert, das später noch eine Nachliefrung erfolgen wird.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isSubsequentDeliveryPlanned();

	ModelColumn<I_EDI_DesadvLine, Object> COLUMN_IsSubsequentDeliveryPlanned = new ModelColumn<>(I_EDI_DesadvLine.class, "IsSubsequentDeliveryPlanned", null);
	String COLUMNNAME_IsSubsequentDeliveryPlanned = "IsSubsequentDeliveryPlanned";

	/**
	 * Set SeqNo..
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setLine (int Line);

	/**
	 * Get SeqNo..
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getLine();

	ModelColumn<I_EDI_DesadvLine, Object> COLUMN_Line = new ModelColumn<>(I_EDI_DesadvLine.class, "Line", null);
	String COLUMNNAME_Line = "Line";

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
	 * Set Order Line.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setOrderLine (int OrderLine);

	/**
	 * Get Order Line.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getOrderLine();

	ModelColumn<I_EDI_DesadvLine, Object> COLUMN_OrderLine = new ModelColumn<>(I_EDI_DesadvLine.class, "OrderLine", null);
	String COLUMNNAME_OrderLine = "OrderLine";

	/**
	 * Set Auftragsreferenz.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setOrderPOReference (@Nullable java.lang.String OrderPOReference);

	/**
	 * Get Auftragsreferenz.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getOrderPOReference();

	ModelColumn<I_EDI_DesadvLine, Object> COLUMN_OrderPOReference = new ModelColumn<>(I_EDI_DesadvLine.class, "OrderPOReference", null);
	String COLUMNNAME_OrderPOReference = "OrderPOReference";

	/**
	 * Set Price Actual.
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPriceActual (@Nullable BigDecimal PriceActual);

	/**
	 * Get Price Actual.
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getPriceActual();

	ModelColumn<I_EDI_DesadvLine, Object> COLUMN_PriceActual = new ModelColumn<>(I_EDI_DesadvLine.class, "PriceActual", null);
	String COLUMNNAME_PriceActual = "PriceActual";

	/**
	 * Set Produktbeschreibung.
	 * Produktbeschreibung
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setProductDescription (@Nullable java.lang.String ProductDescription);

	/**
	 * Get Produktbeschreibung.
	 * Produktbeschreibung
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getProductDescription();

	ModelColumn<I_EDI_DesadvLine, Object> COLUMN_ProductDescription = new ModelColumn<>(I_EDI_DesadvLine.class, "ProductDescription", null);
	String COLUMNNAME_ProductDescription = "ProductDescription";

	/**
	 * Set Product No.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setProductNo (@Nullable java.lang.String ProductNo);

	/**
	 * Get Product No.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getProductNo();

	ModelColumn<I_EDI_DesadvLine, Object> COLUMN_ProductNo = new ModelColumn<>(I_EDI_DesadvLine.class, "ProductNo", null);
	String COLUMNNAME_ProductNo = "ProductNo";

	/**
	 * Set Delviered (invoicing UOM).
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQtyDeliveredInInvoiceUOM (@Nullable BigDecimal QtyDeliveredInInvoiceUOM);

	/**
	 * Get Delviered (invoicing UOM).
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyDeliveredInInvoiceUOM();

	ModelColumn<I_EDI_DesadvLine, Object> COLUMN_QtyDeliveredInInvoiceUOM = new ModelColumn<>(I_EDI_DesadvLine.class, "QtyDeliveredInInvoiceUOM", null);
	String COLUMNNAME_QtyDeliveredInInvoiceUOM = "QtyDeliveredInInvoiceUOM";

	/**
	 * Set Delivered (stock unit).
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQtyDeliveredInStockingUOM (@Nullable BigDecimal QtyDeliveredInStockingUOM);

	/**
	 * Get Delivered (stock unit).
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyDeliveredInStockingUOM();

	ModelColumn<I_EDI_DesadvLine, Object> COLUMN_QtyDeliveredInStockingUOM = new ModelColumn<>(I_EDI_DesadvLine.class, "QtyDeliveredInStockingUOM", null);
	String COLUMNNAME_QtyDeliveredInStockingUOM = "QtyDeliveredInStockingUOM";

	/**
	 * Set Delivered quantity.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQtyDeliveredInUOM (@Nullable BigDecimal QtyDeliveredInUOM);

	/**
	 * Get Delivered quantity.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyDeliveredInUOM();

	ModelColumn<I_EDI_DesadvLine, Object> COLUMN_QtyDeliveredInUOM = new ModelColumn<>(I_EDI_DesadvLine.class, "QtyDeliveredInUOM", null);
	String COLUMNNAME_QtyDeliveredInUOM = "QtyDeliveredInUOM";

	/**
	 * Set Qty.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQtyEntered (@Nullable BigDecimal QtyEntered);

	/**
	 * Get Qty.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyEntered();

	ModelColumn<I_EDI_DesadvLine, Object> COLUMN_QtyEntered = new ModelColumn<>(I_EDI_DesadvLine.class, "QtyEntered", null);
	String COLUMNNAME_QtyEntered = "QtyEntered";

	/**
	 * Set Packaging capacity.
	 * Capacity in the respective product's unit of measuerement
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQtyItemCapacity (@Nullable BigDecimal QtyItemCapacity);

	/**
	 * Get Packaging capacity.
	 * Capacity in the respective product's unit of measuerement
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyItemCapacity();

	ModelColumn<I_EDI_DesadvLine, Object> COLUMN_QtyItemCapacity = new ModelColumn<>(I_EDI_DesadvLine.class, "QtyItemCapacity", null);
	String COLUMNNAME_QtyItemCapacity = "QtyItemCapacity";

	/**
	 * Set Qty Ordered.
	 * Qty Ordered
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setQtyOrdered (BigDecimal QtyOrdered);

	/**
	 * Get Qty Ordered.
	 * Qty Ordered
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyOrdered();

	ModelColumn<I_EDI_DesadvLine, Object> COLUMN_QtyOrdered = new ModelColumn<>(I_EDI_DesadvLine.class, "QtyOrdered", null);
	String COLUMNNAME_QtyOrdered = "QtyOrdered";

	/**
	 * Set CU-UPC.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setUPC_CU (@Nullable java.lang.String UPC_CU);

	/**
	 * Get CU-UPC.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getUPC_CU();

	ModelColumn<I_EDI_DesadvLine, Object> COLUMN_UPC_CU = new ModelColumn<>(I_EDI_DesadvLine.class, "UPC_CU", null);
	String COLUMNNAME_UPC_CU = "UPC_CU";

	/**
	 * Set TU-UPC.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setUPC_TU (@Nullable java.lang.String UPC_TU);

	/**
	 * Get TU-UPC.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getUPC_TU();

	ModelColumn<I_EDI_DesadvLine, Object> COLUMN_UPC_TU = new ModelColumn<>(I_EDI_DesadvLine.class, "UPC_TU", null);
	String COLUMNNAME_UPC_TU = "UPC_TU";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_EDI_DesadvLine, Object> COLUMN_Updated = new ModelColumn<>(I_EDI_DesadvLine.class, "Updated", null);
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
