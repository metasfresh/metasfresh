package de.metas.handlingunits.model;

import java.math.BigDecimal;
import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for RV_HU_Quantities_Summary
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_RV_HU_Quantities_Summary 
{

	String Table_Name = "RV_HU_Quantities_Summary";

//	/** AD_Table_ID=540599 */
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

	ModelColumn<I_RV_HU_Quantities_Summary, Object> COLUMN_IsActive = new ModelColumn<>(I_RV_HU_Quantities_Summary.class, "IsActive", null);
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

	ModelColumn<I_RV_HU_Quantities_Summary, Object> COLUMN_IsPurchased = new ModelColumn<>(I_RV_HU_Quantities_Summary.class, "IsPurchased", null);
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

	ModelColumn<I_RV_HU_Quantities_Summary, Object> COLUMN_IsSold = new ModelColumn<>(I_RV_HU_Quantities_Summary.class, "IsSold", null);
	String COLUMNNAME_IsSold = "IsSold";

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
	 * Set Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setName (@Nullable java.lang.String Name);

	/**
	 * Get Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getName();

	ModelColumn<I_RV_HU_Quantities_Summary, Object> COLUMN_Name = new ModelColumn<>(I_RV_HU_Quantities_Summary.class, "Name", null);
	String COLUMNNAME_Name = "Name";

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

	ModelColumn<I_RV_HU_Quantities_Summary, Object> COLUMN_QtyAvailable = new ModelColumn<>(I_RV_HU_Quantities_Summary.class, "QtyAvailable", null);
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

	ModelColumn<I_RV_HU_Quantities_Summary, Object> COLUMN_QtyOnHand = new ModelColumn<>(I_RV_HU_Quantities_Summary.class, "QtyOnHand", null);
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

	ModelColumn<I_RV_HU_Quantities_Summary, Object> COLUMN_QtyOrdered = new ModelColumn<>(I_RV_HU_Quantities_Summary.class, "QtyOrdered", null);
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

	ModelColumn<I_RV_HU_Quantities_Summary, Object> COLUMN_QtyReserved = new ModelColumn<>(I_RV_HU_Quantities_Summary.class, "QtyReserved", null);
	String COLUMNNAME_QtyReserved = "QtyReserved";

	/**
	 * Set Search Key.
	 * Search key for the record in the format required - must be unique
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setValue (@Nullable java.lang.String Value);

	/**
	 * Get Search Key.
	 * Search key for the record in the format required - must be unique
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getValue();

	ModelColumn<I_RV_HU_Quantities_Summary, Object> COLUMN_Value = new ModelColumn<>(I_RV_HU_Quantities_Summary.class, "Value", null);
	String COLUMNNAME_Value = "Value";
}
