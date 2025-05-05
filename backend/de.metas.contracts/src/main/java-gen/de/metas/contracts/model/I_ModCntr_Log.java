package de.metas.contracts.model;

import org.adempiere.model.ModelColumn;
import org.compiere.model.I_M_InOutLine;

import javax.annotation.Nullable;
import java.math.BigDecimal;

/** Generated Interface for ModCntr_Log
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_ModCntr_Log 
{

	String Table_Name = "ModCntr_Log";

//	/** AD_Table_ID=542338 */
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
	 * Set Table.
	 * Database Table information
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_Table_ID (int AD_Table_ID);

	/**
	 * Get Table.
	 * Database Table information
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_Table_ID();

	String COLUMNNAME_AD_Table_ID = "AD_Table_ID";

	/**
	 * Set Amount.
	 * Amount in a defined currency
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAmount (@Nullable BigDecimal Amount);

	/**
	 * Get Amount.
	 * Amount in a defined currency
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getAmount();

	ModelColumn<I_ModCntr_Log, Object> COLUMN_Amount = new ModelColumn<>(I_ModCntr_Log.class, "Amount", null);
	String COLUMNNAME_Amount = "Amount";

	/**
	 * Set Bill Business Partner.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setBill_BPartner_ID (int Bill_BPartner_ID);

	/**
	 * Get Bill Business Partner.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getBill_BPartner_ID();

	String COLUMNNAME_Bill_BPartner_ID = "Bill_BPartner_ID";

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
	 * Set Flatrate Term.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Flatrate_Term_ID (int C_Flatrate_Term_ID);

	/**
	 * Get Flatrate Term.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Flatrate_Term_ID();

	@Nullable de.metas.contracts.model.I_C_Flatrate_Term getC_Flatrate_Term();

	void setC_Flatrate_Term(@Nullable de.metas.contracts.model.I_C_Flatrate_Term C_Flatrate_Term);

	ModelColumn<I_ModCntr_Log, de.metas.contracts.model.I_C_Flatrate_Term> COLUMN_C_Flatrate_Term_ID = new ModelColumn<>(I_ModCntr_Log.class, "C_Flatrate_Term_ID", de.metas.contracts.model.I_C_Flatrate_Term.class);
	String COLUMNNAME_C_Flatrate_Term_ID = "C_Flatrate_Term_ID";

	/**
	 * Set Invoice candidate.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Invoice_Candidate_ID (int C_Invoice_Candidate_ID);

	/**
	 * Get Invoice candidate.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Invoice_Candidate_ID();

	ModelColumn<I_ModCntr_Log, Object> COLUMN_C_Invoice_Candidate_ID = new ModelColumn<>(I_ModCntr_Log.class, "C_Invoice_Candidate_ID", null);
	String COLUMNNAME_C_Invoice_Candidate_ID = "C_Invoice_Candidate_ID";

	/**
	 * Set Collection Point.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCollectionPoint_BPartner_ID (int CollectionPoint_BPartner_ID);

	/**
	 * Get Collection Point.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getCollectionPoint_BPartner_ID();

	String COLUMNNAME_CollectionPoint_BPartner_ID = "CollectionPoint_BPartner_ID";

	/**
	 * Set Contract Type.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setContractType (java.lang.String ContractType);

	/**
	 * Get Contract Type.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getContractType();

	ModelColumn<I_ModCntr_Log, Object> COLUMN_ContractType = new ModelColumn<>(I_ModCntr_Log.class, "ContractType", null);
	String COLUMNNAME_ContractType = "ContractType";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_ModCntr_Log, Object> COLUMN_Created = new ModelColumn<>(I_ModCntr_Log.class, "Created", null);
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
	 * Set Date.
	 * Transaction Date
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDateTrx (java.sql.Timestamp DateTrx);

	/**
	 * Get Date.
	 * Transaction Date
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getDateTrx();

	ModelColumn<I_ModCntr_Log, Object> COLUMN_DateTrx = new ModelColumn<>(I_ModCntr_Log.class, "DateTrx", null);
	String COLUMNNAME_DateTrx = "DateTrx";

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

	ModelColumn<I_ModCntr_Log, Object> COLUMN_Description = new ModelColumn<>(I_ModCntr_Log.class, "Description", null);
	String COLUMNNAME_Description = "Description";

	/**
	 * Set Harvesting Year.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setHarvesting_Year_ID (int Harvesting_Year_ID);

	/**
	 * Get Harvesting Year.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getHarvesting_Year_ID();

	org.compiere.model.I_C_Year getHarvesting_Year();

	void setHarvesting_Year(org.compiere.model.I_C_Year Harvesting_Year);

	ModelColumn<I_ModCntr_Log, org.compiere.model.I_C_Year> COLUMN_Harvesting_Year_ID = new ModelColumn<>(I_ModCntr_Log.class, "Harvesting_Year_ID", org.compiere.model.I_C_Year.class);
	String COLUMNNAME_Harvesting_Year_ID = "Harvesting_Year_ID";

	/**
	 * Set Initial Product.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setInitial_Product_ID (int Initial_Product_ID);

	/**
	 * Get Initial Product.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getInitial_Product_ID();

	String COLUMNNAME_Initial_Product_ID = "Initial_Product_ID";

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

	ModelColumn<I_ModCntr_Log, Object> COLUMN_IsActive = new ModelColumn<>(I_ModCntr_Log.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Invoiceable.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsBillable (boolean IsBillable);

	/**
	 * Get Invoiceable.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isBillable();

	ModelColumn<I_ModCntr_Log, Object> COLUMN_IsBillable = new ModelColumn<>(I_ModCntr_Log.class, "IsBillable", null);
	String COLUMNNAME_IsBillable = "IsBillable";

	/**
	 * Set Sales Transaction.
	 * This is a Sales Transaction
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsSOTrx (boolean IsSOTrx);

	/**
	 * Get Sales Transaction.
	 * This is a Sales Transaction
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isSOTrx();

	ModelColumn<I_ModCntr_Log, Object> COLUMN_IsSOTrx = new ModelColumn<>(I_ModCntr_Log.class, "IsSOTrx", null);
	String COLUMNNAME_IsSOTrx = "IsSOTrx";

	/**
	 * Set Invoice Group.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setModCntr_InvoicingGroup_ID (int ModCntr_InvoicingGroup_ID);

	/**
	 * Get Invoice Group.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getModCntr_InvoicingGroup_ID();

	@Nullable de.metas.contracts.model.I_ModCntr_InvoicingGroup getModCntr_InvoicingGroup();

	void setModCntr_InvoicingGroup(@Nullable de.metas.contracts.model.I_ModCntr_InvoicingGroup ModCntr_InvoicingGroup);

	ModelColumn<I_ModCntr_Log, de.metas.contracts.model.I_ModCntr_InvoicingGroup> COLUMN_ModCntr_InvoicingGroup_ID = new ModelColumn<>(I_ModCntr_Log.class, "ModCntr_InvoicingGroup_ID", de.metas.contracts.model.I_ModCntr_InvoicingGroup.class);
	String COLUMNNAME_ModCntr_InvoicingGroup_ID = "ModCntr_InvoicingGroup_ID";

	/**
	 * Set Document Type.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setModCntr_Log_DocumentType (java.lang.String ModCntr_Log_DocumentType);

	/**
	 * Get Document Type.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getModCntr_Log_DocumentType();

	ModelColumn<I_ModCntr_Log, Object> COLUMN_ModCntr_Log_DocumentType = new ModelColumn<>(I_ModCntr_Log.class, "ModCntr_Log_DocumentType", null);
	String COLUMNNAME_ModCntr_Log_DocumentType = "ModCntr_Log_DocumentType";

	/**
	 * Set Contract Module Log.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setModCntr_Log_ID (int ModCntr_Log_ID);

	/**
	 * Get Contract Module Log.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getModCntr_Log_ID();

	ModelColumn<I_ModCntr_Log, Object> COLUMN_ModCntr_Log_ID = new ModelColumn<>(I_ModCntr_Log.class, "ModCntr_Log_ID", null);
	String COLUMNNAME_ModCntr_Log_ID = "ModCntr_Log_ID";

	/**
	 * Set Modules.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setModCntr_Module_ID (int ModCntr_Module_ID);

	/**
	 * Get Modules.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getModCntr_Module_ID();

	@Nullable de.metas.contracts.model.I_ModCntr_Module getModCntr_Module();

	void setModCntr_Module(@Nullable de.metas.contracts.model.I_ModCntr_Module ModCntr_Module);

	ModelColumn<I_ModCntr_Log, de.metas.contracts.model.I_ModCntr_Module> COLUMN_ModCntr_Module_ID = new ModelColumn<>(I_ModCntr_Log.class, "ModCntr_Module_ID", de.metas.contracts.model.I_ModCntr_Module.class);
	String COLUMNNAME_ModCntr_Module_ID = "ModCntr_Module_ID";

	/**
	 * Set Computing Method.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setModCntr_Type_ID (int ModCntr_Type_ID);

	/**
	 * Get Computing Method.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getModCntr_Type_ID();

	@Nullable de.metas.contracts.model.I_ModCntr_Type getModCntr_Type();

	void setModCntr_Type(@Nullable de.metas.contracts.model.I_ModCntr_Type ModCntr_Type);

	ModelColumn<I_ModCntr_Log, de.metas.contracts.model.I_ModCntr_Type> COLUMN_ModCntr_Type_ID = new ModelColumn<>(I_ModCntr_Log.class, "ModCntr_Type_ID", de.metas.contracts.model.I_ModCntr_Type.class);
	String COLUMNNAME_ModCntr_Type_ID = "ModCntr_Type_ID";

	/**
	 * Set Product.
	 * Product, Service, Item
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_Product_ID (int M_Product_ID);

	/**
	 * Get Product.
	 * Product, Service, Item
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_Product_ID();

	String COLUMNNAME_M_Product_ID = "M_Product_ID";

	/**
	 * Set Warehouse.
	 * Storage Warehouse and Service Point
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_Warehouse_ID (int M_Warehouse_ID);

	/**
	 * Get Warehouse.
	 * Storage Warehouse and Service Point
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_Warehouse_ID();

	String COLUMNNAME_M_Warehouse_ID = "M_Warehouse_ID";

	/**
	 * Set Manufacturing Cost Collector.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPP_Cost_Collector_ID (int PP_Cost_Collector_ID);

	/**
	 * Get Manufacturing Cost Collector.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getPP_Cost_Collector_ID();

	@Nullable org.eevolution.model.I_PP_Cost_Collector getPP_Cost_Collector();

	void setPP_Cost_Collector(@Nullable org.eevolution.model.I_PP_Cost_Collector PP_Cost_Collector);

	ModelColumn<I_ModCntr_Log, org.eevolution.model.I_PP_Cost_Collector> COLUMN_PP_Cost_Collector_ID = new ModelColumn<>(I_ModCntr_Log.class, "PP_Cost_Collector_ID", org.eevolution.model.I_PP_Cost_Collector.class);
	String COLUMNNAME_PP_Cost_Collector_ID = "PP_Cost_Collector_ID";

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

	ModelColumn<I_ModCntr_Log, Object> COLUMN_PriceActual = new ModelColumn<>(I_ModCntr_Log.class, "PriceActual", null);
	String COLUMNNAME_PriceActual = "PriceActual";

	/**
	 * Set Price Unit.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPrice_UOM_ID (int Price_UOM_ID);

	/**
	 * Get Price Unit.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getPrice_UOM_ID();

	String COLUMNNAME_Price_UOM_ID = "Price_UOM_ID";

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

	ModelColumn<I_ModCntr_Log, Object> COLUMN_Processed = new ModelColumn<>(I_ModCntr_Log.class, "Processed", null);
	String COLUMNNAME_Processed = "Processed";

	/**
	 * Set Producer.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setProducer_BPartner_ID (int Producer_BPartner_ID);

	/**
	 * Get Producer.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getProducer_BPartner_ID();

	String COLUMNNAME_Producer_BPartner_ID = "Producer_BPartner_ID";

	/**
	 * Set Product Name.
	 * Name of the Product
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setProductName (java.lang.String ProductName);

	/**
	 * Get Product Name.
	 * Name of the Product
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getProductName();

	ModelColumn<I_ModCntr_Log, Object> COLUMN_ProductName = new ModelColumn<>(I_ModCntr_Log.class, "ProductName", null);
	String COLUMNNAME_ProductName = "ProductName";

	/**
	 * Set Quantity.
	 * Quantity
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQty (@Nullable BigDecimal Qty);

	/**
	 * Get Quantity.
	 * Quantity
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQty();

	ModelColumn<I_ModCntr_Log, Object> COLUMN_Qty = new ModelColumn<>(I_ModCntr_Log.class, "Qty", null);
	String COLUMNNAME_Qty = "Qty";

	/**
	 * Set Record ID.
	 * Direct internal record ID
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setRecord_ID (int Record_ID);

	/**
	 * Get Record ID.
	 * Direct internal record ID
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getRecord_ID();

	ModelColumn<I_ModCntr_Log, Object> COLUMN_Record_ID = new ModelColumn<>(I_ModCntr_Log.class, "Record_ID", null);
	String COLUMNNAME_Record_ID = "Record_ID";

	/**
	 * Set Storage days.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setStorageDays (int StorageDays);

	/**
	 * Get Storage days.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getStorageDays();

	ModelColumn<I_ModCntr_Log, Object> COLUMN_StorageDays = new ModelColumn<>(I_ModCntr_Log.class, "StorageDays", null);
	String COLUMNNAME_StorageDays = "StorageDays";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_ModCntr_Log, Object> COLUMN_Updated = new ModelColumn<>(I_ModCntr_Log.class, "Updated", null);
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
	 * Set UserElementNumber1.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setUserElementNumber1 (@Nullable BigDecimal UserElementNumber1);

	/**
	 * Get UserElementNumber1.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getUserElementNumber1();

	ModelColumn<org.compiere.model.I_M_InOutLine, Object> COLUMN_UserElementNumber1 = new ModelColumn<>(org.compiere.model.I_M_InOutLine.class, "UserElementNumber1", null);
	String COLUMNNAME_UserElementNumber1 = "UserElementNumber1";

	/**
	 * Set UserElementNumber2.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setUserElementNumber2 (@Nullable BigDecimal UserElementNumber2);

	/**
	 * Get UserElementNumber2.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getUserElementNumber2();

	ModelColumn<org.compiere.model.I_M_InOutLine, Object> COLUMN_UserElementNumber2 = new ModelColumn<>(I_M_InOutLine.class, "UserElementNumber2", null);
	String COLUMNNAME_UserElementNumber2 = "UserElementNumber2";

}
