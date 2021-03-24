package de.metas.handlingunits.model;

import java.math.BigDecimal;
import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for RV_HU_Quantities
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_RV_HU_Quantities 
{

	String Table_Name = "RV_HU_Quantities";

//	/** AD_Table_ID=540598 */
//	int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);


	/**
	 * Get Client.
	 * Client/Tenant for this installation.
	 *
	 * <br>Type: TableDir
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
	 * Set ASI Key.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setASIKey (@Nullable java.lang.String ASIKey);

	/**
	 * Get ASI Key.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getASIKey();

	ModelColumn<I_RV_HU_Quantities, Object> COLUMN_ASIKey = new ModelColumn<>(I_RV_HU_Quantities.class, "ASIKey", null);
	String COLUMNNAME_ASIKey = "ASIKey";

	/**
	 * Set Attributwerte.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setASIKeyName (@Nullable java.lang.String ASIKeyName);

	/**
	 * Get Attributwerte.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getASIKeyName();

	ModelColumn<I_RV_HU_Quantities, Object> COLUMN_ASIKeyName = new ModelColumn<>(I_RV_HU_Quantities.class, "ASIKeyName", null);
	String COLUMNNAME_ASIKeyName = "ASIKeyName";

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
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getCreated();

	ModelColumn<I_RV_HU_Quantities, Object> COLUMN_Created = new ModelColumn<>(I_RV_HU_Quantities.class, "Created", null);
	String COLUMNNAME_Created = "Created";

	/**
	 * Get Created By.
	 * User who created this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getCreatedBy();

	String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Active.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIsActive (boolean IsActive);

	/**
	 * Get Active.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isActive();

	ModelColumn<I_RV_HU_Quantities, Object> COLUMN_IsActive = new ModelColumn<>(I_RV_HU_Quantities.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Eingekauft.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIsPurchased (boolean IsPurchased);

	/**
	 * Get Eingekauft.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isPurchased();

	ModelColumn<I_RV_HU_Quantities, Object> COLUMN_IsPurchased = new ModelColumn<>(I_RV_HU_Quantities.class, "IsPurchased", null);
	String COLUMNNAME_IsPurchased = "IsPurchased";

	/**
	 * Set Verkauft.
	 * Die Organisation verkauft dieses Produkt
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIsSold (boolean IsSold);

	/**
	 * Get Verkauft.
	 * Die Organisation verkauft dieses Produkt
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isSold();

	ModelColumn<I_RV_HU_Quantities, Object> COLUMN_IsSold = new ModelColumn<>(I_RV_HU_Quantities.class, "IsSold", null);
	String COLUMNNAME_IsSold = "IsSold";

	/**
	 * Set Packvorschrift-Produkt Zuordnung.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_HU_PI_Item_Product (@Nullable java.lang.String M_HU_PI_Item_Product);

	/**
	 * Get Packvorschrift-Produkt Zuordnung.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getM_HU_PI_Item_Product();

	ModelColumn<I_RV_HU_Quantities, Object> COLUMN_M_HU_PI_Item_Product = new ModelColumn<>(I_RV_HU_Quantities.class, "M_HU_PI_Item_Product", null);
	String COLUMNNAME_M_HU_PI_Item_Product = "M_HU_PI_Item_Product";

	/**
	 * Set Product Category.
	 * Category of a Product
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_Product_Category_ID (int M_Product_Category_ID);

	/**
	 * Get Product Category.
	 * Category of a Product
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_Product_Category_ID();

	String COLUMNNAME_M_Product_Category_ID = "M_Product_Category_ID";

	/**
	 * Set Product.
	 * Product, Service, Item
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_Product_ID (int M_Product_ID);

	/**
	 * Get Product.
	 * Product, Service, Item
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_Product_ID();

	String COLUMNNAME_M_Product_ID = "M_Product_ID";

	/**
	 * Set Produktname.
	 * Name des Produktes
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setProductName (@Nullable java.lang.String ProductName);

	/**
	 * Get Produktname.
	 * Name des Produktes
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getProductName();

	ModelColumn<I_RV_HU_Quantities, Object> COLUMN_ProductName = new ModelColumn<>(I_RV_HU_Quantities.class, "ProductName", null);
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

	ModelColumn<I_RV_HU_Quantities, Object> COLUMN_ProductValue = new ModelColumn<>(I_RV_HU_Quantities.class, "ProductValue", null);
	String COLUMNNAME_ProductValue = "ProductValue";

	/**
	 * Set Available Quantity.
	 * Available Quantity (On Hand - Reserved)
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQtyAvailable (@Nullable BigDecimal QtyAvailable);

	/**
	 * Get Available Quantity.
	 * Available Quantity (On Hand - Reserved)
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyAvailable();

	ModelColumn<I_RV_HU_Quantities, Object> COLUMN_QtyAvailable = new ModelColumn<>(I_RV_HU_Quantities.class, "QtyAvailable", null);
	String COLUMNNAME_QtyAvailable = "QtyAvailable";

	/**
	 * Set Bestand.
	 * Bestand
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQtyOnHand (@Nullable BigDecimal QtyOnHand);

	/**
	 * Get Bestand.
	 * Bestand
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyOnHand();

	ModelColumn<I_RV_HU_Quantities, Object> COLUMN_QtyOnHand = new ModelColumn<>(I_RV_HU_Quantities.class, "QtyOnHand", null);
	String COLUMNNAME_QtyOnHand = "QtyOnHand";

	/**
	 * Set Qty Ordered.
	 * Qty Ordered
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQtyOrdered (@Nullable BigDecimal QtyOrdered);

	/**
	 * Get Qty Ordered.
	 * Qty Ordered
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyOrdered();

	ModelColumn<I_RV_HU_Quantities, Object> COLUMN_QtyOrdered = new ModelColumn<>(I_RV_HU_Quantities.class, "QtyOrdered", null);
	String COLUMNNAME_QtyOrdered = "QtyOrdered";

	/**
	 * Set Open Qty.
	 * Open Qty
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQtyReserved (@Nullable BigDecimal QtyReserved);

	/**
	 * Get Open Qty.
	 * Open Qty
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyReserved();

	ModelColumn<I_RV_HU_Quantities, Object> COLUMN_QtyReserved = new ModelColumn<>(I_RV_HU_Quantities.class, "QtyReserved", null);
	String COLUMNNAME_QtyReserved = "QtyReserved";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getUpdated();

	ModelColumn<I_RV_HU_Quantities, Object> COLUMN_Updated = new ModelColumn<>(I_RV_HU_Quantities.class, "Updated", null);
	String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Updated By.
	 * User who updated this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getUpdatedBy();

	String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
