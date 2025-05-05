package org.compiere.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;
import java.math.BigDecimal;

/** Generated Interface for C_BPartner_Product
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_C_BPartner_Product 
{

	String Table_Name = "C_BPartner_Product";

//	/** AD_Table_ID=632 */
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
	 * Set Business Partner.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_BPartner_ID (int C_BPartner_ID);

	/**
	 * Get Business Partner.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_BPartner_ID();

	String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/**
	 * Set Business Partner Product.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_BPartner_Product_ID (int C_BPartner_Product_ID);

	/**
	 * Get Business Partner Product.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_BPartner_Product_ID();

	ModelColumn<I_C_BPartner_Product, Object> COLUMN_C_BPartner_Product_ID = new ModelColumn<>(I_C_BPartner_Product.class, "C_BPartner_Product_ID", null);
	String COLUMNNAME_C_BPartner_Product_ID = "C_BPartner_Product_ID";

	/**
	 * Set C_BPartner_Vendor_ID.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_BPartner_Vendor_ID (int C_BPartner_Vendor_ID);

	/**
	 * Get C_BPartner_Vendor_ID.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_BPartner_Vendor_ID();

	String COLUMNNAME_C_BPartner_Vendor_ID = "C_BPartner_Vendor_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_C_BPartner_Product, Object> COLUMN_Created = new ModelColumn<>(I_C_BPartner_Product.class, "Created", null);
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
	 * Set Auszeichnungsname.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCustomerLabelName (@Nullable java.lang.String CustomerLabelName);

	/**
	 * Get Auszeichnungsname.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getCustomerLabelName();

	ModelColumn<I_C_BPartner_Product, Object> COLUMN_CustomerLabelName = new ModelColumn<>(I_C_BPartner_Product.class, "CustomerLabelName", null);
	String COLUMNNAME_CustomerLabelName = "CustomerLabelName";

	/**
	 * Set Zugesicherte Lieferzeit.
	 * Zugesicherte Anzahl Tage zwischen Bestellung und Lieferung
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDeliveryTime_Promised (int DeliveryTime_Promised);

	/**
	 * Get Zugesicherte Lieferzeit.
	 * Zugesicherte Anzahl Tage zwischen Bestellung und Lieferung
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getDeliveryTime_Promised();

	ModelColumn<I_C_BPartner_Product, Object> COLUMN_DeliveryTime_Promised = new ModelColumn<>(I_C_BPartner_Product.class, "DeliveryTime_Promised", null);
	String COLUMNNAME_DeliveryTime_Promised = "DeliveryTime_Promised";

	/**
	 * Set Description.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDescription (@Nullable java.lang.String Description);

	/**
	 * Get Description.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDescription();

	ModelColumn<I_C_BPartner_Product, Object> COLUMN_Description = new ModelColumn<>(I_C_BPartner_Product.class, "Description", null);
	String COLUMNNAME_Description = "Description";

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

	ModelColumn<I_C_BPartner_Product, Object> COLUMN_EAN_CU = new ModelColumn<>(I_C_BPartner_Product.class, "EAN_CU", null);
	String COLUMNNAME_EAN_CU = "EAN_CU";

	/**
	 * Set Exclusion From Purchase Reason.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setExclusionFromPurchaseReason (@Nullable java.lang.String ExclusionFromPurchaseReason);

	/**
	 * Get Exclusion From Purchase Reason.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getExclusionFromPurchaseReason();

	ModelColumn<I_C_BPartner_Product, Object> COLUMN_ExclusionFromPurchaseReason = new ModelColumn<>(I_C_BPartner_Product.class, "ExclusionFromPurchaseReason", null);
	String COLUMNNAME_ExclusionFromPurchaseReason = "ExclusionFromPurchaseReason";

	/**
	 * Set Exclusion From Sale Reason.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setExclusionFromSaleReason (@Nullable java.lang.String ExclusionFromSaleReason);

	/**
	 * Get Exclusion From Sale Reason.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getExclusionFromSaleReason();

	ModelColumn<I_C_BPartner_Product, Object> COLUMN_ExclusionFromSaleReason = new ModelColumn<>(I_C_BPartner_Product.class, "ExclusionFromSaleReason", null);
	String COLUMNNAME_ExclusionFromSaleReason = "ExclusionFromSaleReason";

	/**
	 * Set FLO ID.
	 * ID granted by FLOCERT for companies to put it on their products.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setFLO_Identifier (@Nullable java.lang.String FLO_Identifier);

	/**
	 * Get FLO ID.
	 * ID granted by FLOCERT for companies to put it on their products.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getFLO_Identifier();

	ModelColumn<I_C_BPartner_Product, Object> COLUMN_FLO_Identifier = new ModelColumn<>(I_C_BPartner_Product.class, "FLO_Identifier", null);
	String COLUMNNAME_FLO_Identifier = "FLO_Identifier";

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

	ModelColumn<I_C_BPartner_Product, Object> COLUMN_GTIN = new ModelColumn<>(I_C_BPartner_Product.class, "GTIN", null);
	String COLUMNNAME_GTIN = "GTIN";

	/**
	 * Set Ingredients.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIngredients (@Nullable java.lang.String Ingredients);

	/**
	 * Get Ingredients.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getIngredients();

	ModelColumn<I_C_BPartner_Product, Object> COLUMN_Ingredients = new ModelColumn<>(I_C_BPartner_Product.class, "Ingredients", null);
	String COLUMNNAME_Ingredients = "Ingredients";

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

	ModelColumn<I_C_BPartner_Product, Object> COLUMN_IsActive = new ModelColumn<>(I_C_BPartner_Product.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Gegenwärtiger Lieferant.
	 * Diesen Lieferanten für Bepreisung und Lagerauffüllung verwenden
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsCurrentVendor (boolean IsCurrentVendor);

	/**
	 * Get Gegenwärtiger Lieferant.
	 * Diesen Lieferanten für Bepreisung und Lagerauffüllung verwenden
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isCurrentVendor();

	ModelColumn<I_C_BPartner_Product, Object> COLUMN_IsCurrentVendor = new ModelColumn<>(I_C_BPartner_Product.class, "IsCurrentVendor", null);
	String COLUMNNAME_IsCurrentVendor = "IsCurrentVendor";

	/**
	 * Set Streckengeschäft.
	 * Beim Streckengeschäft wird die Ware direkt vom Lieferanten zum Kunden geliefert
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsDropShip (boolean IsDropShip);

	/**
	 * Get Streckengeschäft.
	 * Beim Streckengeschäft wird die Ware direkt vom Lieferanten zum Kunden geliefert
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isDropShip();

	ModelColumn<I_C_BPartner_Product, Object> COLUMN_IsDropShip = new ModelColumn<>(I_C_BPartner_Product.class, "IsDropShip", null);
	String COLUMNNAME_IsDropShip = "IsDropShip";

	/**
	 * Set Exclude from purchase.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsExcludedFromPurchase (boolean IsExcludedFromPurchase);

	/**
	 * Get Exclude from purchase.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isExcludedFromPurchase();

	ModelColumn<I_C_BPartner_Product, Object> COLUMN_IsExcludedFromPurchase = new ModelColumn<>(I_C_BPartner_Product.class, "IsExcludedFromPurchase", null);
	String COLUMNNAME_IsExcludedFromPurchase = "IsExcludedFromPurchase";

	/**
	 * Set Exclude from sales.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsExcludedFromSale (boolean IsExcludedFromSale);

	/**
	 * Get Exclude from sales.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isExcludedFromSale();

	ModelColumn<I_C_BPartner_Product, Object> COLUMN_IsExcludedFromSale = new ModelColumn<>(I_C_BPartner_Product.class, "IsExcludedFromSale", null);
	String COLUMNNAME_IsExcludedFromSale = "IsExcludedFromSale";

	/**
	 * Set LeadTime.
	 * This column indicates the replenish time in days for a product
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setLeadTime (int LeadTime);

	/**
	 * Get LeadTime.
	 * This column indicates the replenish time in days for a product
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getLeadTime();

	ModelColumn<I_C_BPartner_Product, Object> COLUMN_LeadTime = new ModelColumn<>(I_C_BPartner_Product.class, "LeadTime", null);
	String COLUMNNAME_LeadTime = "LeadTime";

	/**
	 * Set Hersteller.
	 * Manufacturer of the Product
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setManufacturer (@Nullable java.lang.String Manufacturer);

	/**
	 * Get Hersteller.
	 * Manufacturer of the Product
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getManufacturer();

	ModelColumn<I_C_BPartner_Product, Object> COLUMN_Manufacturer = new ModelColumn<>(I_C_BPartner_Product.class, "Manufacturer", null);
	String COLUMNNAME_Manufacturer = "Manufacturer";

	/**
	 * Set Attributes.
	 * Attribute Instances for Products
	 *
	 * <br>Type: PAttribute
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_AttributeSetInstance_ID (int M_AttributeSetInstance_ID);

	/**
	 * Get Attributes.
	 * Attribute Instances for Products
	 *
	 * <br>Type: PAttribute
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_AttributeSetInstance_ID();

	@Nullable org.compiere.model.I_M_AttributeSetInstance getM_AttributeSetInstance();

	void setM_AttributeSetInstance(@Nullable org.compiere.model.I_M_AttributeSetInstance M_AttributeSetInstance);

	ModelColumn<I_C_BPartner_Product, org.compiere.model.I_M_AttributeSetInstance> COLUMN_M_AttributeSetInstance_ID = new ModelColumn<>(I_C_BPartner_Product.class, "M_AttributeSetInstance_ID", org.compiere.model.I_M_AttributeSetInstance.class);
	String COLUMNNAME_M_AttributeSetInstance_ID = "M_AttributeSetInstance_ID";

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
	 * Set Minimum Order Qty.
	 * Minimum order quantity in UOM
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setOrder_Min (@Nullable BigDecimal Order_Min);

	/**
	 * Get Minimum Order Qty.
	 * Minimum order quantity in UOM
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getOrder_Min();

	ModelColumn<I_C_BPartner_Product, Object> COLUMN_Order_Min = new ModelColumn<>(I_C_BPartner_Product.class, "Order_Min", null);
	String COLUMNNAME_Order_Min = "Order_Min";

	/**
	 * Set Packungsgröße.
	 * Größe einer Bestellpackung in Mengeneinheit (z.B. Satz von 5 Einheiten)
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setOrder_Pack (@Nullable BigDecimal Order_Pack);

	/**
	 * Get Packungsgröße.
	 * Größe einer Bestellpackung in Mengeneinheit (z.B. Satz von 5 Einheiten)
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getOrder_Pack();

	ModelColumn<I_C_BPartner_Product, Object> COLUMN_Order_Pack = new ModelColumn<>(I_C_BPartner_Product.class, "Order_Pack", null);
	String COLUMNNAME_Order_Pack = "Order_Pack";

	/**
	 * Set Picking_AgeTolerance_AfterMonths.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPicking_AgeTolerance_AfterMonths (int Picking_AgeTolerance_AfterMonths);

	/**
	 * Get Picking_AgeTolerance_AfterMonths.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getPicking_AgeTolerance_AfterMonths();

	ModelColumn<I_C_BPartner_Product, Object> COLUMN_Picking_AgeTolerance_AfterMonths = new ModelColumn<>(I_C_BPartner_Product.class, "Picking_AgeTolerance_AfterMonths", null);
	String COLUMNNAME_Picking_AgeTolerance_AfterMonths = "Picking_AgeTolerance_AfterMonths";

	/**
	 * Set Picking_AgeTolerance_BeforeMonths.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPicking_AgeTolerance_BeforeMonths (int Picking_AgeTolerance_BeforeMonths);

	/**
	 * Get Picking_AgeTolerance_BeforeMonths.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getPicking_AgeTolerance_BeforeMonths();

	ModelColumn<I_C_BPartner_Product, Object> COLUMN_Picking_AgeTolerance_BeforeMonths = new ModelColumn<>(I_C_BPartner_Product.class, "Picking_AgeTolerance_BeforeMonths", null);
	String COLUMNNAME_Picking_AgeTolerance_BeforeMonths = "Picking_AgeTolerance_BeforeMonths";

	/**
	 * Set Produktkategorie.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setProductCategory (@Nullable java.lang.String ProductCategory);

	/**
	 * Get Produktkategorie.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getProductCategory();

	ModelColumn<I_C_BPartner_Product, Object> COLUMN_ProductCategory = new ModelColumn<>(I_C_BPartner_Product.class, "ProductCategory", null);
	String COLUMNNAME_ProductCategory = "ProductCategory";

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

	ModelColumn<I_C_BPartner_Product, Object> COLUMN_ProductDescription = new ModelColumn<>(I_C_BPartner_Product.class, "ProductDescription", null);
	String COLUMNNAME_ProductDescription = "ProductDescription";

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

	ModelColumn<I_C_BPartner_Product, Object> COLUMN_ProductName = new ModelColumn<>(I_C_BPartner_Product.class, "ProductName", null);
	String COLUMNNAME_ProductName = "ProductName";

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

	ModelColumn<I_C_BPartner_Product, Object> COLUMN_ProductNo = new ModelColumn<>(I_C_BPartner_Product.class, "ProductNo", null);
	String COLUMNNAME_ProductNo = "ProductNo";

	/**
	 * Set Qualitäts-Einstufung.
	 * Method for rating vendors
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQualityRating (@Nullable BigDecimal QualityRating);

	/**
	 * Get Qualitäts-Einstufung.
	 * Method for rating vendors
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQualityRating();

	ModelColumn<I_C_BPartner_Product, Object> COLUMN_QualityRating = new ModelColumn<>(I_C_BPartner_Product.class, "QualityRating", null);
	String COLUMNNAME_QualityRating = "QualityRating";

	/**
	 * Set SeqNo.
	 * Method of ordering records;
 lowest number comes first
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setSeqNo (int SeqNo);

	/**
	 * Get SeqNo.
	 * Method of ordering records;
 lowest number comes first
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getSeqNo();

	ModelColumn<I_C_BPartner_Product, Object> COLUMN_SeqNo = new ModelColumn<>(I_C_BPartner_Product.class, "SeqNo", null);
	String COLUMNNAME_SeqNo = "SeqNo";

	/**
	 * Set Mindesthaltbarkeit Tage.
	 * Minimum Shelf Life in days based on Product Instance Guarantee Date
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setShelfLifeMinDays (int ShelfLifeMinDays);

	/**
	 * Get Mindesthaltbarkeit Tage.
	 * Minimum Shelf Life in days based on Product Instance Guarantee Date
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getShelfLifeMinDays();

	ModelColumn<I_C_BPartner_Product, Object> COLUMN_ShelfLifeMinDays = new ModelColumn<>(I_C_BPartner_Product.class, "ShelfLifeMinDays", null);
	String COLUMNNAME_ShelfLifeMinDays = "ShelfLifeMinDays";

	/**
	 * Set Mindesthaltbarkeit %.
	 * Minimum Shelf Life in percent based on Product Instance Guarantee Date
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setShelfLifeMinPct (int ShelfLifeMinPct);

	/**
	 * Get Mindesthaltbarkeit %.
	 * Minimum Shelf Life in percent based on Product Instance Guarantee Date
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getShelfLifeMinPct();

	ModelColumn<I_C_BPartner_Product, Object> COLUMN_ShelfLifeMinPct = new ModelColumn<>(I_C_BPartner_Product.class, "ShelfLifeMinPct", null);
	String COLUMNNAME_ShelfLifeMinPct = "ShelfLifeMinPct";

	/**
	 * Set UPC.
	 * Bar Code (Universal Product Code or its superset European Article Number)
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setUPC (@Nullable java.lang.String UPC);

	/**
	 * Get UPC.
	 * Bar Code (Universal Product Code or its superset European Article Number)
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getUPC();

	ModelColumn<I_C_BPartner_Product, Object> COLUMN_UPC = new ModelColumn<>(I_C_BPartner_Product.class, "UPC", null);
	String COLUMNNAME_UPC = "UPC";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_C_BPartner_Product, Object> COLUMN_Updated = new ModelColumn<>(I_C_BPartner_Product.class, "Updated", null);
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

	/**
	 * Set Verwendet für Kunden.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setUsedForCustomer (boolean UsedForCustomer);

	/**
	 * Get Verwendet für Kunden.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isUsedForCustomer();

	ModelColumn<I_C_BPartner_Product, Object> COLUMN_UsedForCustomer = new ModelColumn<>(I_C_BPartner_Product.class, "UsedForCustomer", null);
	String COLUMNNAME_UsedForCustomer = "UsedForCustomer";

	/**
	 * Set Verwendet für Lieferant.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setUsedForVendor (boolean UsedForVendor);

	/**
	 * Get Verwendet für Lieferant.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isUsedForVendor();

	ModelColumn<I_C_BPartner_Product, Object> COLUMN_UsedForVendor = new ModelColumn<>(I_C_BPartner_Product.class, "UsedForVendor", null);
	String COLUMNNAME_UsedForVendor = "UsedForVendor";

	/**
	 * Set Vendor Category.
	 * Lieferanten Kategorie
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setVendorCategory (@Nullable java.lang.String VendorCategory);

	/**
	 * Get Vendor Category.
	 * Lieferanten Kategorie
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getVendorCategory();

	ModelColumn<I_C_BPartner_Product, Object> COLUMN_VendorCategory = new ModelColumn<>(I_C_BPartner_Product.class, "VendorCategory", null);
	String COLUMNNAME_VendorCategory = "VendorCategory";

	/**
	 * Set Produkt-Nr. Geschäftspartner.
	 * Product Key of the Business Partner
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setVendorProductNo (@Nullable java.lang.String VendorProductNo);

	/**
	 * Get Produkt-Nr. Geschäftspartner.
	 * Product Key of the Business Partner
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getVendorProductNo();

	ModelColumn<I_C_BPartner_Product, Object> COLUMN_VendorProductNo = new ModelColumn<>(I_C_BPartner_Product.class, "VendorProductNo", null);
	String COLUMNNAME_VendorProductNo = "VendorProductNo";
}
