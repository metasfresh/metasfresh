package de.metas.purchasecandidate.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;
import java.math.BigDecimal;

/**
 * Generated Interface for C_PurchaseCandidate
 *
 * @author metasfresh (generated)
 */
@SuppressWarnings("unused")
public interface I_C_PurchaseCandidate
{

	String Table_Name = "C_PurchaseCandidate";

//	/** AD_Table_ID=540861 */
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
	 * Set Activity.
	 * Business Activity
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Activity_ID (int C_Activity_ID);

	/**
	 * Get Activity.
	 * Business Activity
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Activity_ID();

	String COLUMNNAME_C_Activity_ID = "C_Activity_ID";

	/**
	 * Set Campaign.
	 * Marketing Campaign
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Campaign_ID (int C_Campaign_ID);

	/**
	 * Get Campaign.
	 * Marketing Campaign
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Campaign_ID();

	@Nullable org.compiere.model.I_C_Campaign getC_Campaign();

	void setC_Campaign(@Nullable org.compiere.model.I_C_Campaign C_Campaign);

	ModelColumn<I_C_PurchaseCandidate, org.compiere.model.I_C_Campaign> COLUMN_C_Campaign_ID = new ModelColumn<>(I_C_PurchaseCandidate.class, "C_Campaign_ID", org.compiere.model.I_C_Campaign.class);
	String COLUMNNAME_C_Campaign_ID = "C_Campaign_ID";

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
	 * Set Auftragsposition.
	 * Auftragsposition
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_OrderLineSO_ID (int C_OrderLineSO_ID);

	/**
	 * Get Auftragsposition.
	 * Auftragsposition
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_OrderLineSO_ID();

	@Nullable org.compiere.model.I_C_OrderLine getC_OrderLineSO();

	void setC_OrderLineSO(@Nullable org.compiere.model.I_C_OrderLine C_OrderLineSO);

	ModelColumn<I_C_PurchaseCandidate, org.compiere.model.I_C_OrderLine> COLUMN_C_OrderLineSO_ID = new ModelColumn<>(I_C_PurchaseCandidate.class, "C_OrderLineSO_ID", org.compiere.model.I_C_OrderLine.class);
	String COLUMNNAME_C_OrderLineSO_ID = "C_OrderLineSO_ID";

	/**
	 * Set Auftrag.
	 * Auftrag
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_OrderSO_ID (int C_OrderSO_ID);

	/**
	 * Get Auftrag.
	 * Auftrag
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_OrderSO_ID();

	@Nullable org.compiere.model.I_C_Order getC_OrderSO();

	void setC_OrderSO(@Nullable org.compiere.model.I_C_Order C_OrderSO);

	ModelColumn<I_C_PurchaseCandidate, org.compiere.model.I_C_Order> COLUMN_C_OrderSO_ID = new ModelColumn<>(I_C_PurchaseCandidate.class, "C_OrderSO_ID", org.compiere.model.I_C_Order.class);
	String COLUMNNAME_C_OrderSO_ID = "C_OrderSO_ID";

	/**
	 * Set Project.
	 * Financial Project
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Project_ID (int C_Project_ID);

	/**
	 * Get Project.
	 * Financial Project
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Project_ID();

	String COLUMNNAME_C_Project_ID = "C_Project_ID";

	/**
	 * Set Purchase candidate.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_PurchaseCandidate_ID (int C_PurchaseCandidate_ID);

	/**
	 * Get Purchase candidate.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_PurchaseCandidate_ID();

	ModelColumn<I_C_PurchaseCandidate, Object> COLUMN_C_PurchaseCandidate_ID = new ModelColumn<>(I_C_PurchaseCandidate.class, "C_PurchaseCandidate_ID", null);
	String COLUMNNAME_C_PurchaseCandidate_ID = "C_PurchaseCandidate_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_C_PurchaseCandidate, Object> COLUMN_Created = new ModelColumn<>(I_C_PurchaseCandidate.class, "Created", null);
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
	 * Set Bedarfs-ID.
	 * Bestelldispo-Zeilen, die den selben Bedarf (z.b. die selbe Auftragszeile) addressieren habe den selben Referenz-Wert
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDemandReference (java.lang.String DemandReference);

	/**
	 * Get Bedarfs-ID.
	 * Bestelldispo-Zeilen, die den selben Bedarf (z.b. die selbe Auftragszeile) addressieren habe den selben Referenz-Wert
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getDemandReference();

	ModelColumn<I_C_PurchaseCandidate, Object> COLUMN_DemandReference = new ModelColumn<>(I_C_PurchaseCandidate.class, "DemandReference", null);
	String COLUMNNAME_DemandReference = "DemandReference";

	/**
	 * Set Discount %.
	 * Discount in percent
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDiscount (@Nullable BigDecimal Discount);

	/**
	 * Get Discount %.
	 * Discount in percent
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getDiscount();

	ModelColumn<I_C_PurchaseCandidate, Object> COLUMN_Discount = new ModelColumn<>(I_C_PurchaseCandidate.class, "Discount", null);
	String COLUMNNAME_Discount = "Discount";

	/**
	 * Set DiscountEff.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDiscountEff (@Nullable BigDecimal DiscountEff);

	/**
	 * Get DiscountEff.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getDiscountEff();

	ModelColumn<I_C_PurchaseCandidate, Object> COLUMN_DiscountEff = new ModelColumn<>(I_C_PurchaseCandidate.class, "DiscountEff", null);
	String COLUMNNAME_DiscountEff = "DiscountEff";

	/**
	 * Set DiscountInternal.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDiscountInternal (@Nullable BigDecimal DiscountInternal);

	/**
	 * Get DiscountInternal.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getDiscountInternal();

	ModelColumn<I_C_PurchaseCandidate, Object> COLUMN_DiscountInternal = new ModelColumn<>(I_C_PurchaseCandidate.class, "DiscountInternal", null);
	String COLUMNNAME_DiscountInternal = "DiscountInternal";

	/**
	 * Set External Header ID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setExternalHeaderId (@Nullable java.lang.String ExternalHeaderId);

	/**
	 * Get External Header ID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getExternalHeaderId();

	ModelColumn<I_C_PurchaseCandidate, Object> COLUMN_ExternalHeaderId = new ModelColumn<>(I_C_PurchaseCandidate.class, "ExternalHeaderId", null);
	String COLUMNNAME_ExternalHeaderId = "ExternalHeaderId";

	/**
	 * Set External Line ID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setExternalLineId (@Nullable java.lang.String ExternalLineId);

	/**
	 * Get External Line ID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getExternalLineId();

	ModelColumn<I_C_PurchaseCandidate, Object> COLUMN_ExternalLineId = new ModelColumn<>(I_C_PurchaseCandidate.class, "ExternalLineId", null);
	String COLUMNNAME_ExternalLineId = "ExternalLineId";

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

	ModelColumn<I_C_PurchaseCandidate, Object> COLUMN_IsActive = new ModelColumn<>(I_C_PurchaseCandidate.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Aggregate Purchase Orders.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsAggregatePO (boolean IsAggregatePO);

	/**
	 * Get Aggregate Purchase Orders.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isAggregatePO();

	ModelColumn<I_C_PurchaseCandidate, Object> COLUMN_IsAggregatePO = new ModelColumn<>(I_C_PurchaseCandidate.class, "IsAggregatePO", null);
	String COLUMNNAME_IsAggregatePO = "IsAggregatePO";

	/**
	 * Set Discount Manual.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsManualDiscount (boolean IsManualDiscount);

	/**
	 * Get Discount Manual.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isManualDiscount();

	ModelColumn<I_C_PurchaseCandidate, Object> COLUMN_IsManualDiscount = new ModelColumn<>(I_C_PurchaseCandidate.class, "IsManualDiscount", null);
	String COLUMNNAME_IsManualDiscount = "IsManualDiscount";

	/**
	 * Set Manual Price.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsManualPrice (boolean IsManualPrice);

	/**
	 * Get Manual Price.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isManualPrice();

	ModelColumn<I_C_PurchaseCandidate, Object> COLUMN_IsManualPrice = new ModelColumn<>(I_C_PurchaseCandidate.class, "IsManualPrice", null);
	String COLUMNNAME_IsManualPrice = "IsManualPrice";

	/**
	 * Set Prepared.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsPrepared (boolean IsPrepared);

	/**
	 * Get Prepared.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isPrepared();

	ModelColumn<I_C_PurchaseCandidate, Object> COLUMN_IsPrepared = new ModelColumn<>(I_C_PurchaseCandidate.class, "IsPrepared", null);
	String COLUMNNAME_IsPrepared = "IsPrepared";

	/**
	 * Set RfQ Created.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsRequisitionCreated (boolean IsRequisitionCreated);

	/**
	 * Get RfQ Created.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isRequisitionCreated();

	ModelColumn<I_C_PurchaseCandidate, Object> COLUMN_IsRequisitionCreated = new ModelColumn<>(I_C_PurchaseCandidate.class, "IsRequisitionCreated", null);
	String COLUMNNAME_IsRequisitionCreated = "IsRequisitionCreated";

	/**
	 * Set Price incl. Tax.
	 * Tax is included in the price
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsTaxIncluded (boolean IsTaxIncluded);

	/**
	 * Get Price incl. Tax.
	 * Tax is included in the price
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isTaxIncluded();

	ModelColumn<I_C_PurchaseCandidate, Object> COLUMN_IsTaxIncluded = new ModelColumn<>(I_C_PurchaseCandidate.class, "IsTaxIncluded", null);
	String COLUMNNAME_IsTaxIncluded = "IsTaxIncluded";

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

	ModelColumn<I_C_PurchaseCandidate, org.compiere.model.I_M_AttributeSetInstance> COLUMN_M_AttributeSetInstance_ID = new ModelColumn<>(I_C_PurchaseCandidate.class, "M_AttributeSetInstance_ID", org.compiere.model.I_M_AttributeSetInstance.class);
	String COLUMNNAME_M_AttributeSetInstance_ID = "M_AttributeSetInstance_ID";

	/**
	 * Set Forecast.
	 * Material Forecast
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_Forecast_ID (int M_Forecast_ID);

	/**
	 * Get Forecast.
	 * Material Forecast
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_Forecast_ID();

	@Nullable org.compiere.model.I_M_Forecast getM_Forecast();

	void setM_Forecast(@Nullable org.compiere.model.I_M_Forecast M_Forecast);

	ModelColumn<I_C_PurchaseCandidate, org.compiere.model.I_M_Forecast> COLUMN_M_Forecast_ID = new ModelColumn<>(I_C_PurchaseCandidate.class, "M_Forecast_ID", org.compiere.model.I_M_Forecast.class);
	String COLUMNNAME_M_Forecast_ID = "M_Forecast_ID";

	/**
	 * Set Forecast Line.
	 * Forecast Line
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_ForecastLine_ID (int M_ForecastLine_ID);

	/**
	 * Get Forecast Line.
	 * Forecast Line
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_ForecastLine_ID();

	@Nullable org.compiere.model.I_M_ForecastLine getM_ForecastLine();

	void setM_ForecastLine(@Nullable org.compiere.model.I_M_ForecastLine M_ForecastLine);

	ModelColumn<I_C_PurchaseCandidate, org.compiere.model.I_M_ForecastLine> COLUMN_M_ForecastLine_ID = new ModelColumn<>(I_C_PurchaseCandidate.class, "M_ForecastLine_ID", org.compiere.model.I_M_ForecastLine.class);
	String COLUMNNAME_M_ForecastLine_ID = "M_ForecastLine_ID";

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
	 * Set Lager.
	 * Lager, an das der Lieferant eine Bestellung liefern soll.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_WarehousePO_ID (int M_WarehousePO_ID);

	/**
	 * Get Lager.
	 * Lager, an das der Lieferant eine Bestellung liefern soll.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_WarehousePO_ID();

	String COLUMNNAME_M_WarehousePO_ID = "M_WarehousePO_ID";

	/**
	 * Set Price diff..
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setPriceDifference (@Nullable BigDecimal PriceDifference);

	/**
	 * Get Price diff..
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	BigDecimal getPriceDifference();

	ModelColumn<I_C_PurchaseCandidate, Object> COLUMN_PriceDifference = new ModelColumn<>(I_C_PurchaseCandidate.class, "PriceDifference", null);
	String COLUMNNAME_PriceDifference = "PriceDifference";

	ModelColumn<I_C_PurchaseCandidate, Object> COLUMN_PriceEffective = new ModelColumn<>(I_C_PurchaseCandidate.class, "PriceEffective", null);
	String COLUMNNAME_PriceEffective = "PriceEffective";
	ModelColumn<I_C_PurchaseCandidate, Object> COLUMN_PriceEntered = new ModelColumn<>(I_C_PurchaseCandidate.class, "PriceEntered", null);
	String COLUMNNAME_PriceEntered = "PriceEntered";

	/**
	 * Get Price effective.
	 * Effective Date of Price
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getPriceEffective();

	/**
	 * Set Price effective.
	 * Effective Date of Price
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPriceEffective(@Nullable BigDecimal PriceEffective);

	/**
	 * Get Price imp..
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getPriceEntered();

	/**
	 * Set Price imp..
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPriceEntered (@Nullable BigDecimal PriceEntered);

	/**
	 * Set Price.
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPriceInternal (@Nullable BigDecimal PriceInternal);

	/**
	 * Get Price.
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getPriceInternal();

	ModelColumn<I_C_PurchaseCandidate, Object> COLUMN_PriceInternal = new ModelColumn<>(I_C_PurchaseCandidate.class, "PriceInternal", null);
	String COLUMNNAME_PriceInternal = "PriceInternal";

	/**
	 * Set Processed.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setProcessed (boolean Processed);

	/**
	 * Get Processed.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isProcessed();

	ModelColumn<I_C_PurchaseCandidate, Object> COLUMN_Processed = new ModelColumn<>(I_C_PurchaseCandidate.class, "Processed", null);
	String COLUMNNAME_Processed = "Processed";

	/**
	 * Set Purchase net.
	 * Effektiver Einkaufspreis pro Einheit, minus erwartetem Skonto und vertraglicher Rückerstattung
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setProfitPurchasePriceActual (@Nullable BigDecimal ProfitPurchasePriceActual);

	/**
	 * Get Purchase net.
	 * Effektiver Einkaufspreis pro Einheit, minus erwartetem Skonto und vertraglicher Rückerstattung
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getProfitPurchasePriceActual();

	ModelColumn<I_C_PurchaseCandidate, Object> COLUMN_ProfitPurchasePriceActual = new ModelColumn<>(I_C_PurchaseCandidate.class, "ProfitPurchasePriceActual", null);
	String COLUMNNAME_ProfitPurchasePriceActual = "ProfitPurchasePriceActual";

	/**
	 * Set Sales net.
	 * Effektiver Verkaufspreis pro Einheit, minus erwartetem Skonto und vertraglicher Rückerstattung
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setProfitSalesPriceActual (@Nullable BigDecimal ProfitSalesPriceActual);

	/**
	 * Get Sales net.
	 * Effektiver Verkaufspreis pro Einheit, minus erwartetem Skonto und vertraglicher Rückerstattung
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getProfitSalesPriceActual();

	ModelColumn<I_C_PurchaseCandidate, Object> COLUMN_ProfitSalesPriceActual = new ModelColumn<>(I_C_PurchaseCandidate.class, "ProfitSalesPriceActual", null);
	String COLUMNNAME_ProfitSalesPriceActual = "ProfitSalesPriceActual";

	/**
	 * Set Bestelldatum.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPurchaseDateOrdered (java.sql.Timestamp PurchaseDateOrdered);

	/**
	 * Get Bestelldatum.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getPurchaseDateOrdered();

	ModelColumn<I_C_PurchaseCandidate, Object> COLUMN_PurchaseDateOrdered = new ModelColumn<>(I_C_PurchaseCandidate.class, "PurchaseDateOrdered", null);
	String COLUMNNAME_PurchaseDateOrdered = "PurchaseDateOrdered";

	/**
	 * Set Zugesagter Termin.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPurchaseDatePromised (java.sql.Timestamp PurchaseDatePromised);

	/**
	 * Get Zugesagter Termin.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getPurchaseDatePromised();

	ModelColumn<I_C_PurchaseCandidate, Object> COLUMN_PurchaseDatePromised = new ModelColumn<>(I_C_PurchaseCandidate.class, "PurchaseDatePromised", null);
	String COLUMNNAME_PurchaseDatePromised = "PurchaseDatePromised";

	/**
	 * Set Bestellte Menge.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPurchasedQty (@Nullable BigDecimal PurchasedQty);

	/**
	 * Get Bestellte Menge.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getPurchasedQty();

	ModelColumn<I_C_PurchaseCandidate, Object> COLUMN_PurchasedQty = new ModelColumn<>(I_C_PurchaseCandidate.class, "PurchasedQty", null);
	String COLUMNNAME_PurchasedQty = "PurchasedQty";

	/**
	 * Set Rohertragspreis.
	 * Einkaufspreis pro Einheit, nach Abzug des Rabattes.
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPurchasePriceActual (@Nullable BigDecimal PurchasePriceActual);

	/**
	 * Get Rohertragspreis.
	 * Einkaufspreis pro Einheit, nach Abzug des Rabattes.
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getPurchasePriceActual();

	ModelColumn<I_C_PurchaseCandidate, Object> COLUMN_PurchasePriceActual = new ModelColumn<>(I_C_PurchaseCandidate.class, "PurchasePriceActual", null);
	String COLUMNNAME_PurchasePriceActual = "PurchasePriceActual";

	/**
	 * Set Menge angefragt.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setQtyToPurchase (BigDecimal QtyToPurchase);

	/**
	 * Get Menge angefragt.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyToPurchase();

	ModelColumn<I_C_PurchaseCandidate, Object> COLUMN_QtyToPurchase = new ModelColumn<>(I_C_PurchaseCandidate.class, "QtyToPurchase", null);
	String COLUMNNAME_QtyToPurchase = "QtyToPurchase";

	/**
	 * Set Wiedervorlage Datum.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setReminderDate (@Nullable java.sql.Timestamp ReminderDate);

	/**
	 * Get Wiedervorlage Datum.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getReminderDate();

	ModelColumn<I_C_PurchaseCandidate, Object> COLUMN_ReminderDate = new ModelColumn<>(I_C_PurchaseCandidate.class, "ReminderDate", null);
	String COLUMNNAME_ReminderDate = "ReminderDate";

	/**
	 * Set Source.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setSource (@Nullable java.lang.String Source);

	/**
	 * Get Source.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getSource();

	ModelColumn<I_C_PurchaseCandidate, Object> COLUMN_Source = new ModelColumn<>(I_C_PurchaseCandidate.class, "Source", null);
	String COLUMNNAME_Source = "Source";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_C_PurchaseCandidate, Object> COLUMN_Updated = new ModelColumn<>(I_C_PurchaseCandidate.class, "Updated", null);
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
	 * Set UserElementString1.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setUserElementString1 (@Nullable java.lang.String UserElementString1);

	/**
	 * Get UserElementString1.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getUserElementString1();

	ModelColumn<I_C_PurchaseCandidate, Object> COLUMN_UserElementString1 = new ModelColumn<>(I_C_PurchaseCandidate.class, "UserElementString1", null);
	String COLUMNNAME_UserElementString1 = "UserElementString1";

	/**
	 * Set UserElementString2.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setUserElementString2 (@Nullable java.lang.String UserElementString2);

	/**
	 * Get UserElementString2.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getUserElementString2();

	ModelColumn<I_C_PurchaseCandidate, Object> COLUMN_UserElementString2 = new ModelColumn<>(I_C_PurchaseCandidate.class, "UserElementString2", null);
	String COLUMNNAME_UserElementString2 = "UserElementString2";

	/**
	 * Set UserElementString3.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setUserElementString3 (@Nullable java.lang.String UserElementString3);

	/**
	 * Get UserElementString3.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getUserElementString3();

	ModelColumn<I_C_PurchaseCandidate, Object> COLUMN_UserElementString3 = new ModelColumn<>(I_C_PurchaseCandidate.class, "UserElementString3", null);
	String COLUMNNAME_UserElementString3 = "UserElementString3";

	/**
	 * Set UserElementString4.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setUserElementString4 (@Nullable java.lang.String UserElementString4);

	/**
	 * Get UserElementString4.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getUserElementString4();

	ModelColumn<I_C_PurchaseCandidate, Object> COLUMN_UserElementString4 = new ModelColumn<>(I_C_PurchaseCandidate.class, "UserElementString4", null);
	String COLUMNNAME_UserElementString4 = "UserElementString4";

	/**
	 * Set UserElementString5.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setUserElementString5 (@Nullable java.lang.String UserElementString5);

	/**
	 * Get UserElementString5.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getUserElementString5();

	ModelColumn<I_C_PurchaseCandidate, Object> COLUMN_UserElementString5 = new ModelColumn<>(I_C_PurchaseCandidate.class, "UserElementString5", null);
	String COLUMNNAME_UserElementString5 = "UserElementString5";

	/**
	 * Set UserElementString6.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setUserElementString6 (@Nullable java.lang.String UserElementString6);

	/**
	 * Get UserElementString6.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getUserElementString6();

	ModelColumn<I_C_PurchaseCandidate, Object> COLUMN_UserElementString6 = new ModelColumn<>(I_C_PurchaseCandidate.class, "UserElementString6", null);
	String COLUMNNAME_UserElementString6 = "UserElementString6";

	/**
	 * Set UserElementString7.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setUserElementString7 (@Nullable java.lang.String UserElementString7);

	/**
	 * Get UserElementString7.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getUserElementString7();

	ModelColumn<I_C_PurchaseCandidate, Object> COLUMN_UserElementString7 = new ModelColumn<>(I_C_PurchaseCandidate.class, "UserElementString7", null);
	String COLUMNNAME_UserElementString7 = "UserElementString7";

	/**
	 * Set Lieferant.
	 * The Vendor of the product/service
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setVendor_ID (int Vendor_ID);

	/**
	 * Get Lieferant.
	 * The Vendor of the product/service
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getVendor_ID();

	String COLUMNNAME_Vendor_ID = "Vendor_ID";
}
