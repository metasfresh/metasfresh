package de.metas.contracts.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;
import java.math.BigDecimal;

/** Generated Interface for C_Flatrate_Conditions
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_C_Flatrate_Conditions 
{

	String Table_Name = "C_Flatrate_Conditions";

//	/** AD_Table_ID=540311 */
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
	 * Set Customer Margin Settings.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Customer_Trade_Margin_ID (int C_Customer_Trade_Margin_ID);

	/**
	 * Get Customer Margin Settings.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Customer_Trade_Margin_ID();

	ModelColumn<I_C_Flatrate_Conditions, Object> COLUMN_C_Customer_Trade_Margin_ID = new ModelColumn<>(I_C_Flatrate_Conditions.class, "C_Customer_Trade_Margin_ID", null);
	String COLUMNNAME_C_Customer_Trade_Margin_ID = "C_Customer_Trade_Margin_ID";

	/**
	 * Set Contract Terms.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Flatrate_Conditions_ID (int C_Flatrate_Conditions_ID);

	/**
	 * Get Contract Terms.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Flatrate_Conditions_ID();

	ModelColumn<I_C_Flatrate_Conditions, Object> COLUMN_C_Flatrate_Conditions_ID = new ModelColumn<>(I_C_Flatrate_Conditions.class, "C_Flatrate_Conditions_ID", null);
	String COLUMNNAME_C_Flatrate_Conditions_ID = "C_Flatrate_Conditions_ID";

	/**
	 * Set Flatrate Matching.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setC_Flatrate_Matching_IncludedT (@Nullable java.lang.String C_Flatrate_Matching_IncludedT);

	/**
	 * Get Flatrate Matching.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	@Nullable java.lang.String getC_Flatrate_Matching_IncludedT();

	ModelColumn<I_C_Flatrate_Conditions, Object> COLUMN_C_Flatrate_Matching_IncludedT = new ModelColumn<>(I_C_Flatrate_Conditions.class, "C_Flatrate_Matching_IncludedT", null);
	String COLUMNNAME_C_Flatrate_Matching_IncludedT = "C_Flatrate_Matching_IncludedT";

	/**
	 * Set Contract Transition.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Flatrate_Transition_ID (int C_Flatrate_Transition_ID);

	/**
	 * Get Contract Transition.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Flatrate_Transition_ID();

	@Nullable de.metas.contracts.model.I_C_Flatrate_Transition getC_Flatrate_Transition();

	void setC_Flatrate_Transition(@Nullable de.metas.contracts.model.I_C_Flatrate_Transition C_Flatrate_Transition);

	ModelColumn<I_C_Flatrate_Conditions, de.metas.contracts.model.I_C_Flatrate_Transition> COLUMN_C_Flatrate_Transition_ID = new ModelColumn<>(I_C_Flatrate_Conditions.class, "C_Flatrate_Transition_ID", de.metas.contracts.model.I_C_Flatrate_Transition.class);
	String COLUMNNAME_C_Flatrate_Transition_ID = "C_Flatrate_Transition_ID";

	/**
	 * Set Hierarchy commission settings.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_HierarchyCommissionSettings_ID(int C_HierarchyCommissionSettings_ID);

	/**
	 * Get Hierarchy commission settings.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_HierarchyCommissionSettings_ID();

	ModelColumn<I_C_Flatrate_Conditions, Object> COLUMN_C_HierarchyCommissionSettings_ID = new ModelColumn<>(I_C_Flatrate_Conditions.class, "C_HierarchyCommissionSettings_ID", null);
	String COLUMNNAME_C_HierarchyCommissionSettings_ID = "C_HierarchyCommissionSettings_ID";

	/**
	 * Set Clearing Base.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setClearingAmtBaseOn(@Nullable java.lang.String ClearingAmtBaseOn);

	/**
	 * Get Clearing Base.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getClearingAmtBaseOn();

	ModelColumn<I_C_Flatrate_Conditions, Object> COLUMN_ClearingAmtBaseOn = new ModelColumn<>(I_C_Flatrate_Conditions.class, "ClearingAmtBaseOn", null);
	String COLUMNNAME_ClearingAmtBaseOn = "ClearingAmtBaseOn";

	/**
	 * Set License fee settings.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_LicenseFeeSettings_ID (int C_LicenseFeeSettings_ID);

	/**
	 * Get License fee settings.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_LicenseFeeSettings_ID();

	ModelColumn<I_C_Flatrate_Conditions, Object> COLUMN_C_LicenseFeeSettings_ID = new ModelColumn<>(I_C_Flatrate_Conditions.class, "C_LicenseFeeSettings_ID", null);
	String COLUMNNAME_C_LicenseFeeSettings_ID = "C_LicenseFeeSettings_ID";

	/**
	 * Set Brokerage commission settings.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_MediatedCommissionSettings_ID (int C_MediatedCommissionSettings_ID);

	/**
	 * Get Brokerage commission settings.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_MediatedCommissionSettings_ID();

	ModelColumn<I_C_Flatrate_Conditions, Object> COLUMN_C_MediatedCommissionSettings_ID = new ModelColumn<>(I_C_Flatrate_Conditions.class, "C_MediatedCommissionSettings_ID", null);
	String COLUMNNAME_C_MediatedCommissionSettings_ID = "C_MediatedCommissionSettings_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_C_Flatrate_Conditions, Object> COLUMN_Created = new ModelColumn<>(I_C_Flatrate_Conditions.class, "Created", null);
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
	 * Set Abo-Rabatt.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_SubscrDiscount_ID(int C_SubscrDiscount_ID);

	/**
	 * Get Abo-Rabatt.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_SubscrDiscount_ID();

	@Nullable de.metas.contracts.model.I_C_SubscrDiscount getC_SubscrDiscount();

	void setC_SubscrDiscount(@Nullable de.metas.contracts.model.I_C_SubscrDiscount C_SubscrDiscount);

	ModelColumn<I_C_Flatrate_Conditions, de.metas.contracts.model.I_C_SubscrDiscount> COLUMN_C_SubscrDiscount_ID = new ModelColumn<>(I_C_Flatrate_Conditions.class, "C_SubscrDiscount_ID", de.metas.contracts.model.I_C_SubscrDiscount.class);
	String COLUMNNAME_C_SubscrDiscount_ID = "C_SubscrDiscount_ID";

	/**
	 * Set UOM.
	 * Unit of Measure
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_UOM_ID(int C_UOM_ID);

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
	 * Set Description.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDescription(@Nullable java.lang.String Description);

	/**
	 * Get Description.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable
	java.lang.String getDescription();

	ModelColumn<I_C_Flatrate_Conditions, Object> COLUMN_Description = new ModelColumn<>(I_C_Flatrate_Conditions.class, "Description", null);
	String COLUMNNAME_Description = "Description";

	/**
	 * Set Process Batch.
	 * Der zukünftige Status des Belegs
	 *
	 * <br>Type: Button
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDocAction (java.lang.String DocAction);

	/**
	 * Get Process Batch.
	 * Der zukünftige Status des Belegs
	 *
	 * <br>Type: Button
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getDocAction();

	ModelColumn<I_C_Flatrate_Conditions, Object> COLUMN_DocAction = new ModelColumn<>(I_C_Flatrate_Conditions.class, "DocAction", null);
	String COLUMNNAME_DocAction = "DocAction";

	/**
	 * Set Status.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDocStatus (java.lang.String DocStatus);

	/**
	 * Get Status.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getDocStatus();

	ModelColumn<I_C_Flatrate_Conditions, Object> COLUMN_DocStatus = new ModelColumn<>(I_C_Flatrate_Conditions.class, "DocStatus", null);
	String COLUMNNAME_DocStatus = "DocStatus";

	/**
	 * Set Invoice Rule.
	 * Frequency and method of invoicing
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setInvoiceRule (java.lang.String InvoiceRule);

	/**
	 * Get Invoice Rule.
	 * Frequency and method of invoicing
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getInvoiceRule();

	ModelColumn<I_C_Flatrate_Conditions, Object> COLUMN_InvoiceRule = new ModelColumn<>(I_C_Flatrate_Conditions.class, "InvoiceRule", null);
	String COLUMNNAME_InvoiceRule = "InvoiceRule";

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

	ModelColumn<I_C_Flatrate_Conditions, Object> COLUMN_IsActive = new ModelColumn<>(I_C_Flatrate_Conditions.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Cleat with actual Amount.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsClosingWithActualSum (boolean IsClosingWithActualSum);

	/**
	 * Get Cleat with actual Amount.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isClosingWithActualSum();

	ModelColumn<I_C_Flatrate_Conditions, Object> COLUMN_IsClosingWithActualSum = new ModelColumn<>(I_C_Flatrate_Conditions.class, "IsClosingWithActualSum", null);
	String COLUMNNAME_IsClosingWithActualSum = "IsClosingWithActualSum";

	/**
	 * Set Clearing with Correction.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsClosingWithCorrectionSum (boolean IsClosingWithCorrectionSum);

	/**
	 * Get Clearing with Correction.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isClosingWithCorrectionSum();

	ModelColumn<I_C_Flatrate_Conditions, Object> COLUMN_IsClosingWithCorrectionSum = new ModelColumn<>(I_C_Flatrate_Conditions.class, "IsClosingWithCorrectionSum", null);
	String COLUMNNAME_IsClosingWithCorrectionSum = "IsClosingWithCorrectionSum";

	/**
	 * Set Clearing after Correction.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsCorrectionAmtAtClosing (boolean IsCorrectionAmtAtClosing);

	/**
	 * Get Clearing after Correction.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isCorrectionAmtAtClosing();

	ModelColumn<I_C_Flatrate_Conditions, Object> COLUMN_IsCorrectionAmtAtClosing = new ModelColumn<>(I_C_Flatrate_Conditions.class, "IsCorrectionAmtAtClosing", null);
	String COLUMNNAME_IsCorrectionAmtAtClosing = "IsCorrectionAmtAtClosing";

	/**
	 * Set No Invoicing.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsCreateNoInvoice (boolean IsCreateNoInvoice);

	/**
	 * Get No Invoicing.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isCreateNoInvoice();

	ModelColumn<I_C_Flatrate_Conditions, Object> COLUMN_IsCreateNoInvoice = new ModelColumn<>(I_C_Flatrate_Conditions.class, "IsCreateNoInvoice", null);
	String COLUMNNAME_IsCreateNoInvoice = "IsCreateNoInvoice";

	/**
	 * Set Free of Charge.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsFreeOfCharge (boolean IsFreeOfCharge);

	/**
	 * Get Free of Charge.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isFreeOfCharge();

	ModelColumn<I_C_Flatrate_Conditions, Object> COLUMN_IsFreeOfCharge = new ModelColumn<>(I_C_Flatrate_Conditions.class, "IsFreeOfCharge", null);
	String COLUMNNAME_IsFreeOfCharge = "IsFreeOfCharge";

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

	ModelColumn<I_C_Flatrate_Conditions, Object> COLUMN_IsManualPrice = new ModelColumn<>(I_C_Flatrate_Conditions.class, "IsManualPrice", null);
	String COLUMNNAME_IsManualPrice = "IsManualPrice";

	/**
	 * Set Simulation.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsSimulation (boolean IsSimulation);

	/**
	 * Get Simulation.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isSimulation();

	ModelColumn<I_C_Flatrate_Conditions, Object> COLUMN_IsSimulation = new ModelColumn<>(I_C_Flatrate_Conditions.class, "IsSimulation", null);
	String COLUMNNAME_IsSimulation = "IsSimulation";

	/**
	 * Set Maximum Corridor.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setMargin_Max (BigDecimal Margin_Max);

	/**
	 * Get Maximum Corridor.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getMargin_Max();

	ModelColumn<I_C_Flatrate_Conditions, Object> COLUMN_Margin_Max = new ModelColumn<>(I_C_Flatrate_Conditions.class, "Margin_Max", null);
	String COLUMNNAME_Margin_Max = "Margin_Max";

	/**
	 * Set Minimum Corridor.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setMargin_Min (BigDecimal Margin_Min);

	/**
	 * Get Minimum Corridor.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getMargin_Min();

	ModelColumn<I_C_Flatrate_Conditions, Object> COLUMN_Margin_Min = new ModelColumn<>(I_C_Flatrate_Conditions.class, "Margin_Min", null);
	String COLUMNNAME_Margin_Min = "Margin_Min";

	/**
	 * Set Modular Contract Settings.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setModCntr_Settings_ID (int ModCntr_Settings_ID);

	/**
	 * Get Modular Contract Settings.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getModCntr_Settings_ID();

	@Nullable de.metas.contracts.model.I_ModCntr_Settings getModCntr_Settings();

	void setModCntr_Settings(@Nullable de.metas.contracts.model.I_ModCntr_Settings ModCntr_Settings);

	ModelColumn<I_C_Flatrate_Conditions, de.metas.contracts.model.I_ModCntr_Settings> COLUMN_ModCntr_Settings_ID = new ModelColumn<>(I_C_Flatrate_Conditions.class, "ModCntr_Settings_ID", de.metas.contracts.model.I_ModCntr_Settings.class);
	String COLUMNNAME_ModCntr_Settings_ID = "ModCntr_Settings_ID";

	/**
	 * Set Pricing System.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_PricingSystem_ID (int M_PricingSystem_ID);

	/**
	 * Get Pricing System.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_PricingSystem_ID();

	String COLUMNNAME_M_PricingSystem_ID = "M_PricingSystem_ID";

	/**
	 * Set Clearing Product.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_Product_Actual_ID (int M_Product_Actual_ID);

	/**
	 * Get Clearing Product.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_Product_Actual_ID();

	String COLUMNNAME_M_Product_Actual_ID = "M_Product_Actual_ID";

	/**
	 * Set Correction Product.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_Product_Correction_ID (int M_Product_Correction_ID);

	/**
	 * Get Correction Product.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_Product_Correction_ID();

	String COLUMNNAME_M_Product_Correction_ID = "M_Product_Correction_ID";

	/**
	 * Set Flatrate Product.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_Product_Flatrate_ID(int M_Product_Flatrate_ID);

	/**
	 * Get Flatrate Product.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_Product_Flatrate_ID();

	String COLUMNNAME_M_Product_Flatrate_ID = "M_Product_Flatrate_ID";

	/**
	 * Set Quality Inspection Conference.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_QualityInsp_LagerKonf_ID(int M_QualityInsp_LagerKonf_ID);

	/**
	 * Get Quality Inspection Conference.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_QualityInsp_LagerKonf_ID();

	ModelColumn<I_C_Flatrate_Conditions, Object> COLUMN_M_QualityInsp_LagerKonf_ID = new ModelColumn<>(I_C_Flatrate_Conditions.class, "M_QualityInsp_LagerKonf_ID", null);
	String COLUMNNAME_M_QualityInsp_LagerKonf_ID = "M_QualityInsp_LagerKonf_ID";

	/**
	 * Set Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setName (java.lang.String Name);

	/**
	 * Get Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getName();

	ModelColumn<I_C_Flatrate_Conditions, Object> COLUMN_Name = new ModelColumn<>(I_C_Flatrate_Conditions.class, "Name", null);
	String COLUMNNAME_Name = "Name";

	/**
	 * Set When extending contract.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setOnFlatrateTermExtend (java.lang.String OnFlatrateTermExtend);

	/**
	 * Get When extending contract.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getOnFlatrateTermExtend();

	ModelColumn<I_C_Flatrate_Conditions, Object> COLUMN_OnFlatrateTermExtend = new ModelColumn<>(I_C_Flatrate_Conditions.class, "OnFlatrateTermExtend", null);
	String COLUMNNAME_OnFlatrateTermExtend = "OnFlatrateTermExtend";

	/**
	 * Set Print Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPrintName (@Nullable java.lang.String PrintName);

	/**
	 * Get Print Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getPrintName();

	ModelColumn<I_C_Flatrate_Conditions, Object> COLUMN_PrintName = new ModelColumn<>(I_C_Flatrate_Conditions.class, "PrintName", null);
	String COLUMNNAME_PrintName = "PrintName";

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

	ModelColumn<I_C_Flatrate_Conditions, Object> COLUMN_Processed = new ModelColumn<>(I_C_Flatrate_Conditions.class, "Processed", null);
	String COLUMNNAME_Processed = "Processed";

	/**
	 * Set Process Now.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setProcessing (boolean Processing);

	/**
	 * Get Process Now.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isProcessing();

	ModelColumn<I_C_Flatrate_Conditions, Object> COLUMN_Processing = new ModelColumn<>(I_C_Flatrate_Conditions.class, "Processing", null);
	String COLUMNNAME_Processing = "Processing";

	/**
	 * Set Clearing Type.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setType_Clearing (java.lang.String Type_Clearing);

	/**
	 * Get Clearing Type.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getType_Clearing();

	ModelColumn<I_C_Flatrate_Conditions, Object> COLUMN_Type_Clearing = new ModelColumn<>(I_C_Flatrate_Conditions.class, "Type_Clearing", null);
	String COLUMNNAME_Type_Clearing = "Type_Clearing";

	/**
	 * Set Contract Type.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setType_Conditions (java.lang.String Type_Conditions);

	/**
	 * Get Contract Type.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getType_Conditions();

	ModelColumn<I_C_Flatrate_Conditions, Object> COLUMN_Type_Conditions = new ModelColumn<>(I_C_Flatrate_Conditions.class, "Type_Conditions", null);
	String COLUMNNAME_Type_Conditions = "Type_Conditions";

	/**
	 * Set Flatrate Type.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setType_Flatrate (java.lang.String Type_Flatrate);

	/**
	 * Get Flatrate Type.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getType_Flatrate();

	ModelColumn<I_C_Flatrate_Conditions, Object> COLUMN_Type_Flatrate = new ModelColumn<>(I_C_Flatrate_Conditions.class, "Type_Flatrate", null);
	String COLUMNNAME_Type_Flatrate = "Type_Flatrate";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_C_Flatrate_Conditions, Object> COLUMN_Updated = new ModelColumn<>(I_C_Flatrate_Conditions.class, "Updated", null);
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
