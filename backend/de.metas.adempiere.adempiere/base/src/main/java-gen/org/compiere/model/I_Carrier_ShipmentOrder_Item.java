package org.compiere.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;
import java.math.BigDecimal;

/** Generated Interface for Carrier_ShipmentOrder_Item
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_Carrier_ShipmentOrder_Item 
{

	String Table_Name = "Carrier_ShipmentOrder_Item";

//	/** AD_Table_ID=542536 */
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
	 * Set ArtikelNr.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setArticleValue (@Nullable java.lang.String ArticleValue);

	/**
	 * Get ArtikelNr.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getArticleValue();

	ModelColumn<I_Carrier_ShipmentOrder_Item, Object> COLUMN_ArticleValue = new ModelColumn<>(I_Carrier_ShipmentOrder_Item.class, "ArticleValue", null);
	String COLUMNNAME_ArticleValue = "ArticleValue";

	/**
	 * Set Shipment Order Item.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setCarrier_ShipmentOrder_Item_ID (int Carrier_ShipmentOrder_Item_ID);

	/**
	 * Get Shipment Order Item.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getCarrier_ShipmentOrder_Item_ID();

	ModelColumn<I_Carrier_ShipmentOrder_Item, Object> COLUMN_Carrier_ShipmentOrder_Item_ID = new ModelColumn<>(I_Carrier_ShipmentOrder_Item.class, "Carrier_ShipmentOrder_Item_ID", null);
	String COLUMNNAME_Carrier_ShipmentOrder_Item_ID = "Carrier_ShipmentOrder_Item_ID";

	/**
	 * Set Shipment Order Parcel.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setCarrier_ShipmentOrder_Parcel_ID (int Carrier_ShipmentOrder_Parcel_ID);

	/**
	 * Get Shipment Order Parcel.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getCarrier_ShipmentOrder_Parcel_ID();

	ModelColumn<I_Carrier_ShipmentOrder_Item, org.compiere.model.I_Carrier_ShipmentOrder_Parcel> COLUMN_Carrier_ShipmentOrder_Parcel_ID = new ModelColumn<>(I_Carrier_ShipmentOrder_Item.class, "Carrier_ShipmentOrder_Parcel_ID", org.compiere.model.I_Carrier_ShipmentOrder_Parcel.class);
	String COLUMNNAME_Carrier_ShipmentOrder_Parcel_ID = "Carrier_ShipmentOrder_Parcel_ID";

	/**
	 * Set Currency.
	 * The Currency for this record
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Currency_ID (int C_Currency_ID);

	/**
	 * Get Currency.
	 * The Currency for this record
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Currency_ID();

	String COLUMNNAME_C_Currency_ID = "C_Currency_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_Carrier_ShipmentOrder_Item, Object> COLUMN_Created = new ModelColumn<>(I_Carrier_ShipmentOrder_Item.class, "Created", null);
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
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_UOM_ID (int C_UOM_ID);

	/**
	 * Get UOM.
	 * Unit of Measure
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_UOM_ID();

	String COLUMNNAME_C_UOM_ID = "C_UOM_ID";

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

	ModelColumn<I_Carrier_ShipmentOrder_Item, Object> COLUMN_IsActive = new ModelColumn<>(I_Carrier_ShipmentOrder_Item.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Price.
	 * Price
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPrice (BigDecimal Price);

	/**
	 * Get Price.
	 * Price
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getPrice();

	ModelColumn<I_Carrier_ShipmentOrder_Item, Object> COLUMN_Price = new ModelColumn<>(I_Carrier_ShipmentOrder_Item.class, "Price", null);
	String COLUMNNAME_Price = "Price";

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

	ModelColumn<I_Carrier_ShipmentOrder_Item, Object> COLUMN_ProductName = new ModelColumn<>(I_Carrier_ShipmentOrder_Item.class, "ProductName", null);
	String COLUMNNAME_ProductName = "ProductName";

	/**
	 * Set Qty Shipped.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQtyShipped (@Nullable BigDecimal QtyShipped);

	/**
	 * Get Qty Shipped.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyShipped();

	ModelColumn<I_Carrier_ShipmentOrder_Item, Object> COLUMN_QtyShipped = new ModelColumn<>(I_Carrier_ShipmentOrder_Item.class, "QtyShipped", null);
	String COLUMNNAME_QtyShipped = "QtyShipped";

	/**
	 * Set Gesamtpreis.
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setTotalPrice (BigDecimal TotalPrice);

	/**
	 * Get Gesamtpreis.
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getTotalPrice();

	ModelColumn<I_Carrier_ShipmentOrder_Item, Object> COLUMN_TotalPrice = new ModelColumn<>(I_Carrier_ShipmentOrder_Item.class, "TotalPrice", null);
	String COLUMNNAME_TotalPrice = "TotalPrice";

	/**
	 * Set Total Weight In Kg.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setTotalWeightInKg (BigDecimal TotalWeightInKg);

	/**
	 * Get Total Weight In Kg.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getTotalWeightInKg();

	ModelColumn<I_Carrier_ShipmentOrder_Item, Object> COLUMN_TotalWeightInKg = new ModelColumn<>(I_Carrier_ShipmentOrder_Item.class, "TotalWeightInKg", null);
	String COLUMNNAME_TotalWeightInKg = "TotalWeightInKg";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_Carrier_ShipmentOrder_Item, Object> COLUMN_Updated = new ModelColumn<>(I_Carrier_ShipmentOrder_Item.class, "Updated", null);
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
