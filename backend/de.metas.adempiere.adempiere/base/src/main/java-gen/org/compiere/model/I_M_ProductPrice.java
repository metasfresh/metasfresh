package org.compiere.model;

import java.math.BigDecimal;
import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for M_ProductPrice
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_M_ProductPrice 
{

	String Table_Name = "M_ProductPrice";

//	/** AD_Table_ID=251 */
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

	ModelColumn<I_M_ProductPrice, Object> COLUMN_Created = new ModelColumn<>(I_M_ProductPrice.class, "Created", null);
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
	 * Set Tax Category.
	 * Tax Category
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_TaxCategory_ID (int C_TaxCategory_ID);

	/**
	 * Get Tax Category.
	 * Tax Category
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_TaxCategory_ID();

	String COLUMNNAME_C_TaxCategory_ID = "C_TaxCategory_ID";

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

	ModelColumn<I_M_ProductPrice, Object> COLUMN_InvoicableQtyBasedOn = new ModelColumn<>(I_M_ProductPrice.class, "InvoicableQtyBasedOn", null);
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

	ModelColumn<I_M_ProductPrice, Object> COLUMN_IsActive = new ModelColumn<>(I_M_ProductPrice.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set isAttributeDependant.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsAttributeDependant (boolean IsAttributeDependant);

	/**
	 * Get isAttributeDependant.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isAttributeDependant();

	ModelColumn<I_M_ProductPrice, Object> COLUMN_IsAttributeDependant = new ModelColumn<>(I_M_ProductPrice.class, "IsAttributeDependant", null);
	String COLUMNNAME_IsAttributeDependant = "IsAttributeDependant";

	/**
	 * Set Default.
	 * Default value
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsDefault (boolean IsDefault);

	/**
	 * Get Default.
	 * Default value
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isDefault();

	ModelColumn<I_M_ProductPrice, Object> COLUMN_IsDefault = new ModelColumn<>(I_M_ProductPrice.class, "IsDefault", null);
	String COLUMNNAME_IsDefault = "IsDefault";

	/**
	 * Set Discount Editable.
	 * Allow user to change the discount
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsDiscountEditable (boolean IsDiscountEditable);

	/**
	 * Get Discount Editable.
	 * Allow user to change the discount
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isDiscountEditable();

	ModelColumn<I_M_ProductPrice, Object> COLUMN_IsDiscountEditable = new ModelColumn<>(I_M_ProductPrice.class, "IsDiscountEditable", null);
	String COLUMNNAME_IsDiscountEditable = "IsDiscountEditable";

	/**
	 * Set Ignore Price.
	 * If "Ignore Price" = Y then the Product Price can be copied but it will not be applied by the pricing engine when it computes a price.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsInvalidPrice (boolean IsInvalidPrice);

	/**
	 * Get Ignore Price.
	 * If "Ignore Price" = Y then the Product Price can be copied but it will not be applied by the pricing engine when it computes a price.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isInvalidPrice();

	ModelColumn<I_M_ProductPrice, Object> COLUMN_IsInvalidPrice = new ModelColumn<>(I_M_ProductPrice.class, "IsInvalidPrice", null);
	String COLUMNNAME_IsInvalidPrice = "IsInvalidPrice";

	/**
	 * Set Price Editable.
	 * Allow user to change the price
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsPriceEditable (boolean IsPriceEditable);

	/**
	 * Get Price Editable.
	 * Allow user to change the price
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isPriceEditable();

	ModelColumn<I_M_ProductPrice, Object> COLUMN_IsPriceEditable = new ModelColumn<>(I_M_ProductPrice.class, "IsPriceEditable", null);
	String COLUMNNAME_IsPriceEditable = "IsPriceEditable";

	/**
	 * Set Season fixed Price.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsSeasonFixedPrice (boolean IsSeasonFixedPrice);

	/**
	 * Get Season fixed Price.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isSeasonFixedPrice();

	ModelColumn<I_M_ProductPrice, Object> COLUMN_IsSeasonFixedPrice = new ModelColumn<>(I_M_ProductPrice.class, "IsSeasonFixedPrice", null);
	String COLUMNNAME_IsSeasonFixedPrice = "IsSeasonFixedPrice";

	/**
	 * Set Matching Order.
	 * If there are several matching prices for a price list version/product combination, the price with the smallest value is used.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setMatchSeqNo (int MatchSeqNo);

	/**
	 * Get Matching Order.
	 * If there are several matching prices for a price list version/product combination, the price with the smallest value is used.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getMatchSeqNo();

	ModelColumn<I_M_ProductPrice, Object> COLUMN_MatchSeqNo = new ModelColumn<>(I_M_ProductPrice.class, "MatchSeqNo", null);
	String COLUMNNAME_MatchSeqNo = "MatchSeqNo";

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

	ModelColumn<I_M_ProductPrice, org.compiere.model.I_M_AttributeSetInstance> COLUMN_M_AttributeSetInstance_ID = new ModelColumn<>(I_M_ProductPrice.class, "M_AttributeSetInstance_ID", org.compiere.model.I_M_AttributeSetInstance.class);
	String COLUMNNAME_M_AttributeSetInstance_ID = "M_AttributeSetInstance_ID";

	/**
	 * Set Discount Pricelist.
	 * Line of the pricelist trade discount schema
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_DiscountSchemaLine_ID (int M_DiscountSchemaLine_ID);

	/**
	 * Get Discount Pricelist.
	 * Line of the pricelist trade discount schema
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_DiscountSchemaLine_ID();

	@Nullable org.compiere.model.I_M_DiscountSchemaLine getM_DiscountSchemaLine();

	void setM_DiscountSchemaLine(@Nullable org.compiere.model.I_M_DiscountSchemaLine M_DiscountSchemaLine);

	ModelColumn<I_M_ProductPrice, org.compiere.model.I_M_DiscountSchemaLine> COLUMN_M_DiscountSchemaLine_ID = new ModelColumn<>(I_M_ProductPrice.class, "M_DiscountSchemaLine_ID", org.compiere.model.I_M_DiscountSchemaLine.class);
	String COLUMNNAME_M_DiscountSchemaLine_ID = "M_DiscountSchemaLine_ID";

	/**
	 * Set Price List.
	 * Unique identifier of a Price List
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setM_PriceList_ID (int M_PriceList_ID);

	/**
	 * Get Price List.
	 * Unique identifier of a Price List
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	int getM_PriceList_ID();

	String COLUMNNAME_M_PriceList_ID = "M_PriceList_ID";

	/**
	 * Set Price List Version.
	 * Identifies a unique instance of a Price List
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_PriceList_Version_ID (int M_PriceList_Version_ID);

	/**
	 * Get Price List Version.
	 * Identifies a unique instance of a Price List
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_PriceList_Version_ID();

	String COLUMNNAME_M_PriceList_Version_ID = "M_PriceList_Version_ID";

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
	 * Set Base product price.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_ProductPrice_Base_ID (int M_ProductPrice_Base_ID);

	/**
	 * Get Base product price.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_ProductPrice_Base_ID();

	@Nullable org.compiere.model.I_M_ProductPrice getM_ProductPrice_Base();

	void setM_ProductPrice_Base(@Nullable org.compiere.model.I_M_ProductPrice M_ProductPrice_Base);

	ModelColumn<I_M_ProductPrice, org.compiere.model.I_M_ProductPrice> COLUMN_M_ProductPrice_Base_ID = new ModelColumn<>(I_M_ProductPrice.class, "M_ProductPrice_Base_ID", org.compiere.model.I_M_ProductPrice.class);
	String COLUMNNAME_M_ProductPrice_Base_ID = "M_ProductPrice_Base_ID";

	/**
	 * Set Product Price.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_ProductPrice_ID (int M_ProductPrice_ID);

	/**
	 * Get Product Price.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_ProductPrice_ID();

	ModelColumn<I_M_ProductPrice, Object> COLUMN_M_ProductPrice_ID = new ModelColumn<>(I_M_ProductPrice.class, "M_ProductPrice_ID", null);
	String COLUMNNAME_M_ProductPrice_ID = "M_ProductPrice_ID";

	/**
	 * Set Limit Price.
	 * Lowest price for a product
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPriceLimit (BigDecimal PriceLimit);

	/**
	 * Get Limit Price.
	 * Lowest price for a product
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getPriceLimit();

	ModelColumn<I_M_ProductPrice, Object> COLUMN_PriceLimit = new ModelColumn<>(I_M_ProductPrice.class, "PriceLimit", null);
	String COLUMNNAME_PriceLimit = "PriceLimit";

	/**
	 * Set List Price.
	 * List Price
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPriceList (BigDecimal PriceList);

	/**
	 * Get List Price.
	 * List Price
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getPriceList();

	ModelColumn<I_M_ProductPrice, Object> COLUMN_PriceList = new ModelColumn<>(I_M_ProductPrice.class, "PriceList", null);
	String COLUMNNAME_PriceList = "PriceList";

	/**
	 * Set Standard Price.
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPriceStd (BigDecimal PriceStd);

	/**
	 * Get Standard Price.
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getPriceStd();

	ModelColumn<I_M_ProductPrice, Object> COLUMN_PriceStd = new ModelColumn<>(I_M_ProductPrice.class, "PriceStd", null);
	String COLUMNNAME_PriceStd = "PriceStd";

	/**
	 * Set Scale price quantity from.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setScalePriceQuantityFrom (java.lang.String ScalePriceQuantityFrom);

	/**
	 * Get Scale price quantity from.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getScalePriceQuantityFrom();

	ModelColumn<I_M_ProductPrice, Object> COLUMN_ScalePriceQuantityFrom = new ModelColumn<>(I_M_ProductPrice.class, "ScalePriceQuantityFrom", null);
	String COLUMNNAME_ScalePriceQuantityFrom = "ScalePriceQuantityFrom";

	/**
	 * Set SeqNo.
	 * Method of ordering records;
 lowest number comes first
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setSeqNo (int SeqNo);

	/**
	 * Get SeqNo.
	 * Method of ordering records;
 lowest number comes first
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getSeqNo();

	ModelColumn<I_M_ProductPrice, Object> COLUMN_SeqNo = new ModelColumn<>(I_M_ProductPrice.class, "SeqNo", null);
	String COLUMNNAME_SeqNo = "SeqNo";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_M_ProductPrice, Object> COLUMN_Updated = new ModelColumn<>(I_M_ProductPrice.class, "Updated", null);
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
	 * Set Staffelpreis.
	 * Legt fest, ob zu diesem Artikel Staffelpreise gehören.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setUseScalePrice (java.lang.String UseScalePrice);

	/**
	 * Get Staffelpreis.
	 * Legt fest, ob zu diesem Artikel Staffelpreise gehören.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getUseScalePrice();

	ModelColumn<I_M_ProductPrice, Object> COLUMN_UseScalePrice = new ModelColumn<>(I_M_ProductPrice.class, "UseScalePrice", null);
	String COLUMNNAME_UseScalePrice = "UseScalePrice";

	/**
	 * Set Valid From.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setValidFrom (@Nullable java.sql.Timestamp ValidFrom);

	/**
	 * Get Valid From.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	@Nullable java.sql.Timestamp getValidFrom();

	ModelColumn<I_M_ProductPrice, Object> COLUMN_ValidFrom = new ModelColumn<>(I_M_ProductPrice.class, "ValidFrom", null);
	String COLUMNNAME_ValidFrom = "ValidFrom";
}
