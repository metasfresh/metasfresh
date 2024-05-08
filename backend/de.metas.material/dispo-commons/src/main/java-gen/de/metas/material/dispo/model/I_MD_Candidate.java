package de.metas.material.dispo.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;
import java.math.BigDecimal;

/** Generated Interface for MD_Candidate
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_MD_Candidate 
{

	String Table_Name = "MD_Candidate";

//	/** AD_Table_ID=540808 */
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
	 * Set Business Partner .
	 * Identifies a Business Partner
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_BPartner_Customer_ID (int C_BPartner_Customer_ID);

	/**
	 * Get Business Partner .
	 * Identifies a Business Partner
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_BPartner_Customer_ID();

	String COLUMNNAME_C_BPartner_Customer_ID = "C_BPartner_Customer_ID";

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

	ModelColumn<I_MD_Candidate, org.compiere.model.I_C_Campaign> COLUMN_C_Campaign_ID = new ModelColumn<>(I_MD_Candidate.class, "C_Campaign_ID", org.compiere.model.I_C_Campaign.class);
	String COLUMNNAME_C_Campaign_ID = "C_Campaign_ID";

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

	ModelColumn<I_MD_Candidate, org.compiere.model.I_C_Order> COLUMN_C_OrderSO_ID = new ModelColumn<>(I_MD_Candidate.class, "C_OrderSO_ID", org.compiere.model.I_C_Order.class);
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
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_MD_Candidate, Object> COLUMN_Created = new ModelColumn<>(I_MD_Candidate.class, "Created", null);
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
	 * Set Plandatum.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDateProjected (java.sql.Timestamp DateProjected);

	/**
	 * Get Plandatum.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getDateProjected();

	ModelColumn<I_MD_Candidate, Object> COLUMN_DateProjected = new ModelColumn<>(I_MD_Candidate.class, "DateProjected", null);
	String COLUMNNAME_DateProjected = "DateProjected";

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

	ModelColumn<I_MD_Candidate, Object> COLUMN_IsActive = new ModelColumn<>(I_MD_Candidate.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set ATP reserved for customer.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsReservedForCustomer (boolean IsReservedForCustomer);

	/**
	 * Get ATP reserved for customer.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isReservedForCustomer();

	ModelColumn<I_MD_Candidate, Object> COLUMN_IsReservedForCustomer = new ModelColumn<>(I_MD_Candidate.class, "IsReservedForCustomer", null);
	String COLUMNNAME_IsReservedForCustomer = "IsReservedForCustomer";

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

	ModelColumn<I_MD_Candidate, org.compiere.model.I_M_AttributeSetInstance> COLUMN_M_AttributeSetInstance_ID = new ModelColumn<>(I_MD_Candidate.class, "M_AttributeSetInstance_ID", org.compiere.model.I_M_AttributeSetInstance.class);
	String COLUMNNAME_M_AttributeSetInstance_ID = "M_AttributeSetInstance_ID";

	/**
	 * Set Forecast.
	 * Material Forecast
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_Forecast_ID (int M_Forecast_ID);

	/**
	 * Get Forecast.
	 * Material Forecast
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_Forecast_ID();

	@Nullable org.compiere.model.I_M_Forecast getM_Forecast();

	void setM_Forecast(@Nullable org.compiere.model.I_M_Forecast M_Forecast);

	ModelColumn<I_MD_Candidate, org.compiere.model.I_M_Forecast> COLUMN_M_Forecast_ID = new ModelColumn<>(I_MD_Candidate.class, "M_Forecast_ID", org.compiere.model.I_M_Forecast.class);
	String COLUMNNAME_M_Forecast_ID = "M_Forecast_ID";

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
	 * Set Shipment Candidate.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false (lazy loading)
	 */
	void setM_ShipmentSchedule_ID (int M_ShipmentSchedule_ID);

	/**
	 * Get Shipment Candidate.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false (lazy loading)
	 */
	int getM_ShipmentSchedule_ID();

	ModelColumn<I_MD_Candidate, Object> COLUMN_M_ShipmentSchedule_ID = new ModelColumn<>(I_MD_Candidate.class, "M_ShipmentSchedule_ID", null);
	String COLUMNNAME_M_ShipmentSchedule_ID = "M_ShipmentSchedule_ID";

	/**
	 * Set Warehouse.
	 * Storage Warehouse and Service Point
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_Warehouse_ID (int M_Warehouse_ID);

	/**
	 * Get Warehouse.
	 * Storage Warehouse and Service Point
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_Warehouse_ID();

	String COLUMNNAME_M_Warehouse_ID = "M_Warehouse_ID";

	/**
	 * Set Business case.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setMD_Candidate_BusinessCase (@Nullable String MD_Candidate_BusinessCase);

	/**
	 * Get Business case.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable String getMD_Candidate_BusinessCase();

	ModelColumn<I_MD_Candidate, Object> COLUMN_MD_Candidate_BusinessCase = new ModelColumn<>(I_MD_Candidate.class, "MD_Candidate_BusinessCase", null);
	String COLUMNNAME_MD_Candidate_BusinessCase = "MD_Candidate_BusinessCase";

	/**
	 * Set Gruppen-ID.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setMD_Candidate_GroupId (int MD_Candidate_GroupId);

	/**
	 * Get Gruppen-ID.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getMD_Candidate_GroupId();

	ModelColumn<I_MD_Candidate, Object> COLUMN_MD_Candidate_GroupId = new ModelColumn<>(I_MD_Candidate.class, "MD_Candidate_GroupId", null);
	String COLUMNNAME_MD_Candidate_GroupId = "MD_Candidate_GroupId";

	/**
	 * Set Dispo Candidate.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setMD_Candidate_ID (int MD_Candidate_ID);

	/**
	 * Get Dispo Candidate.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getMD_Candidate_ID();

	ModelColumn<I_MD_Candidate, Object> COLUMN_MD_Candidate_ID = new ModelColumn<>(I_MD_Candidate.class, "MD_Candidate_ID", null);
	String COLUMNNAME_MD_Candidate_ID = "MD_Candidate_ID";

	/**
	 * Set Elterndatensatz.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setMD_Candidate_Parent_ID (int MD_Candidate_Parent_ID);

	/**
	 * Get Elterndatensatz.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getMD_Candidate_Parent_ID();

	@Nullable I_MD_Candidate getMD_Candidate_Parent();

	void setMD_Candidate_Parent(@Nullable I_MD_Candidate MD_Candidate_Parent);

	ModelColumn<I_MD_Candidate, I_MD_Candidate> COLUMN_MD_Candidate_Parent_ID = new ModelColumn<>(I_MD_Candidate.class, "MD_Candidate_Parent_ID", I_MD_Candidate.class);
	String COLUMNNAME_MD_Candidate_Parent_ID = "MD_Candidate_Parent_ID";

	/**
	 * Set Status.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setMD_Candidate_Status (@Nullable String MD_Candidate_Status);

	/**
	 * Get Status.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable String getMD_Candidate_Status();

	ModelColumn<I_MD_Candidate, Object> COLUMN_MD_Candidate_Status = new ModelColumn<>(I_MD_Candidate.class, "MD_Candidate_Status", null);
	String COLUMNNAME_MD_Candidate_Status = "MD_Candidate_Status";

	/**
	 * Set Type.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setMD_Candidate_Type (String MD_Candidate_Type);

	/**
	 * Get Type.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	String getMD_Candidate_Type();

	ModelColumn<I_MD_Candidate, Object> COLUMN_MD_Candidate_Type = new ModelColumn<>(I_MD_Candidate.class, "MD_Candidate_Type", null);
	String COLUMNNAME_MD_Candidate_Type = "MD_Candidate_Type";

	/**
	 * Set Quantity.
	 * Quantity
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setQty (BigDecimal Qty);

	/**
	 * Get Quantity.
	 * Quantity
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getQty();

	ModelColumn<I_MD_Candidate, Object> COLUMN_Qty = new ModelColumn<>(I_MD_Candidate.class, "Qty", null);
	String COLUMNNAME_Qty = "Qty";

	/**
	 * Set Zusagbar (ATP).
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setQty_AvailableToPromise (@Nullable BigDecimal Qty_AvailableToPromise);

	/**
	 * Get Zusagbar (ATP).
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	BigDecimal getQty_AvailableToPromise();

	ModelColumn<I_MD_Candidate, Object> COLUMN_Qty_AvailableToPromise = new ModelColumn<>(I_MD_Candidate.class, "Qty_AvailableToPromise", null);
	String COLUMNNAME_Qty_AvailableToPromise = "Qty_AvailableToPromise";

	/**
	 * Set Menge.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setQty_Display (@Nullable BigDecimal Qty_Display);

	/**
	 * Get Menge.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	BigDecimal getQty_Display();

	ModelColumn<I_MD_Candidate, Object> COLUMN_Qty_Display = new ModelColumn<>(I_MD_Candidate.class, "Qty_Display", null);
	String COLUMNNAME_Qty_Display = "Qty_Display";

	/**
	 * Set Menge.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setQty_Planned_Display (@Nullable BigDecimal Qty_Planned_Display);

	/**
	 * Get Menge.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	BigDecimal getQty_Planned_Display();

	ModelColumn<I_MD_Candidate, Object> COLUMN_Qty_Planned_Display = new ModelColumn<>(I_MD_Candidate.class, "Qty_Planned_Display", null);
	String COLUMNNAME_Qty_Planned_Display = "Qty_Planned_Display";

	/**
	 * Set Fulfilled quantity.
	 * Summe der bereits eingetretenden Materialbewegungen
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQtyFulfilled (@Nullable BigDecimal QtyFulfilled);

	/**
	 * Get Fulfilled quantity.
	 * Summe der bereits eingetretenden Materialbewegungen
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyFulfilled();

	ModelColumn<I_MD_Candidate, Object> COLUMN_QtyFulfilled = new ModelColumn<>(I_MD_Candidate.class, "QtyFulfilled", null);
	String COLUMNNAME_QtyFulfilled = "QtyFulfilled";

	/**
	 * Set Erledigte Menge.
	 * Summe der bereits eingetretenden Materialbewegungen
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setQtyFulfilled_Display (@Nullable BigDecimal QtyFulfilled_Display);

	/**
	 * Get Erledigte Menge.
	 * Summe der bereits eingetretenden Materialbewegungen
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	BigDecimal getQtyFulfilled_Display();

	ModelColumn<I_MD_Candidate, Object> COLUMN_QtyFulfilled_Display = new ModelColumn<>(I_MD_Candidate.class, "QtyFulfilled_Display", null);
	String COLUMNNAME_QtyFulfilled_Display = "QtyFulfilled_Display";

	/**
	 * Set Höchstmenge.
	 * Maximaler ATP. Wenn die Material-Dispo eine Aufstockung veranlasst, dann auf diese Menge.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setReplenish_MaxQty (BigDecimal Replenish_MaxQty);

	/**
	 * Get Höchstmenge.
	 * Maximaler ATP. Wenn die Material-Dispo eine Aufstockung veranlasst, dann auf diese Menge.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getReplenish_MaxQty();

	ModelColumn<I_MD_Candidate, Object> COLUMN_Replenish_MaxQty = new ModelColumn<>(I_MD_Candidate.class, "Replenish_MaxQty", null);
	String COLUMNNAME_Replenish_MaxQty = "Replenish_MaxQty";

	/**
	 * Set Minimal ATP.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setReplenish_MinQty (BigDecimal Replenish_MinQty);

	/**
	 * Get Minimal ATP.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getReplenish_MinQty();

	ModelColumn<I_MD_Candidate, Object> COLUMN_Replenish_MinQty = new ModelColumn<>(I_MD_Candidate.class, "Replenish_MinQty", null);
	String COLUMNNAME_Replenish_MinQty = "Replenish_MinQty";

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

	ModelColumn<I_MD_Candidate, Object> COLUMN_SeqNo = new ModelColumn<>(I_MD_Candidate.class, "SeqNo", null);
	String COLUMNNAME_SeqNo = "SeqNo";

	/**
	 * Set StorageAttributesKey (technical).
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setStorageAttributesKey (String StorageAttributesKey);

	/**
	 * Get StorageAttributesKey (technical).
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	String getStorageAttributesKey();

	ModelColumn<I_MD_Candidate, Object> COLUMN_StorageAttributesKey = new ModelColumn<>(I_MD_Candidate.class, "StorageAttributesKey", null);
	String COLUMNNAME_StorageAttributesKey = "StorageAttributesKey";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_MD_Candidate, Object> COLUMN_Updated = new ModelColumn<>(I_MD_Candidate.class, "Updated", null);
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
	void setUserElementString1 (@Nullable String UserElementString1);

	/**
	 * Get UserElementString1.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable String getUserElementString1();

	ModelColumn<I_MD_Candidate, Object> COLUMN_UserElementString1 = new ModelColumn<>(I_MD_Candidate.class, "UserElementString1", null);
	String COLUMNNAME_UserElementString1 = "UserElementString1";

	/**
	 * Set UserElementString2.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setUserElementString2 (@Nullable String UserElementString2);

	/**
	 * Get UserElementString2.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable String getUserElementString2();

	ModelColumn<I_MD_Candidate, Object> COLUMN_UserElementString2 = new ModelColumn<>(I_MD_Candidate.class, "UserElementString2", null);
	String COLUMNNAME_UserElementString2 = "UserElementString2";

	/**
	 * Set UserElementString3.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setUserElementString3 (@Nullable String UserElementString3);

	/**
	 * Get UserElementString3.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable String getUserElementString3();

	ModelColumn<I_MD_Candidate, Object> COLUMN_UserElementString3 = new ModelColumn<>(I_MD_Candidate.class, "UserElementString3", null);
	String COLUMNNAME_UserElementString3 = "UserElementString3";

	/**
	 * Set UserElementString4.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setUserElementString4 (@Nullable String UserElementString4);

	/**
	 * Get UserElementString4.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable String getUserElementString4();

	ModelColumn<I_MD_Candidate, Object> COLUMN_UserElementString4 = new ModelColumn<>(I_MD_Candidate.class, "UserElementString4", null);
	String COLUMNNAME_UserElementString4 = "UserElementString4";

	/**
	 * Set UserElementString5.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setUserElementString5 (@Nullable String UserElementString5);

	/**
	 * Get UserElementString5.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable String getUserElementString5();

	ModelColumn<I_MD_Candidate, Object> COLUMN_UserElementString5 = new ModelColumn<>(I_MD_Candidate.class, "UserElementString5", null);
	String COLUMNNAME_UserElementString5 = "UserElementString5";

	/**
	 * Set UserElementString6.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setUserElementString6 (@Nullable String UserElementString6);

	/**
	 * Get UserElementString6.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable String getUserElementString6();

	ModelColumn<I_MD_Candidate, Object> COLUMN_UserElementString6 = new ModelColumn<>(I_MD_Candidate.class, "UserElementString6", null);
	String COLUMNNAME_UserElementString6 = "UserElementString6";

	/**
	 * Set UserElementString7.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setUserElementString7 (@Nullable String UserElementString7);

	/**
	 * Get UserElementString7.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable String getUserElementString7();

	ModelColumn<I_MD_Candidate, Object> COLUMN_UserElementString7 = new ModelColumn<>(I_MD_Candidate.class, "UserElementString7", null);
	String COLUMNNAME_UserElementString7 = "UserElementString7";
}
