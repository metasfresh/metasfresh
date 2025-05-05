package org.compiere.model;

import java.math.BigDecimal;
import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for C_InvoiceLine
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_C_InvoiceLine 
{

	String Table_Name = "C_InvoiceLine";

//	/** AD_Table_ID=333 */
//	int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);


	/**
	 * Set Asset Group.
	 * Group of Assets
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setA_Asset_Group_ID (int A_Asset_Group_ID);

	/**
	 * Get Asset Group.
	 * Group of Assets
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getA_Asset_Group_ID();

	@Nullable org.compiere.model.I_A_Asset_Group getA_Asset_Group();

	void setA_Asset_Group(@Nullable org.compiere.model.I_A_Asset_Group A_Asset_Group);

	ModelColumn<I_C_InvoiceLine, org.compiere.model.I_A_Asset_Group> COLUMN_A_Asset_Group_ID = new ModelColumn<>(I_C_InvoiceLine.class, "A_Asset_Group_ID", org.compiere.model.I_A_Asset_Group.class);
	String COLUMNNAME_A_Asset_Group_ID = "A_Asset_Group_ID";

	/**
	 * Set Asset.
	 * Asset used internally or by customers
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setA_Asset_ID (int A_Asset_ID);

	/**
	 * Get Asset.
	 * Asset used internally or by customers
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getA_Asset_ID();

	@Nullable org.compiere.model.I_A_Asset getA_Asset();

	void setA_Asset(@Nullable org.compiere.model.I_A_Asset A_Asset);

	ModelColumn<I_C_InvoiceLine, org.compiere.model.I_A_Asset> COLUMN_A_Asset_ID = new ModelColumn<>(I_C_InvoiceLine.class, "A_Asset_ID", org.compiere.model.I_A_Asset.class);
	String COLUMNNAME_A_Asset_ID = "A_Asset_ID";

	/**
	 * Set Capital/Expense.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setA_CapvsExp (@Nullable java.lang.String A_CapvsExp);

	/**
	 * Get Capital/Expense.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getA_CapvsExp();

	ModelColumn<I_C_InvoiceLine, Object> COLUMN_A_CapvsExp = new ModelColumn<>(I_C_InvoiceLine.class, "A_CapvsExp", null);
	String COLUMNNAME_A_CapvsExp = "A_CapvsExp";

	/**
	 * Set Create Asset.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setA_CreateAsset (boolean A_CreateAsset);

	/**
	 * Get Create Asset.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isA_CreateAsset();

	ModelColumn<I_C_InvoiceLine, Object> COLUMN_A_CreateAsset = new ModelColumn<>(I_C_InvoiceLine.class, "A_CreateAsset", null);
	String COLUMNNAME_A_CreateAsset = "A_CreateAsset";

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
	 * Set Trx Organization.
	 * Performing or initiating organization
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_OrgTrx_ID (int AD_OrgTrx_ID);

	/**
	 * Get Trx Organization.
	 * Performing or initiating organization
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_OrgTrx_ID();

	String COLUMNNAME_AD_OrgTrx_ID = "AD_OrgTrx_ID";

	/**
	 * Set Processed.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setA_Processed (boolean A_Processed);

	/**
	 * Get Processed.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isA_Processed();

	ModelColumn<I_C_InvoiceLine, Object> COLUMN_A_Processed = new ModelColumn<>(I_C_InvoiceLine.class, "A_Processed", null);
	String COLUMNNAME_A_Processed = "A_Processed";

	/**
	 * Set Pricing system.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setBase_PricingSystem_ID (int Base_PricingSystem_ID);

	/**
	 * Get Pricing system.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getBase_PricingSystem_ID();

	String COLUMNNAME_Base_PricingSystem_ID = "Base_PricingSystem_ID";

	/**
	 * Set Activity.
	 * Business Activity
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Activity_ID (int C_Activity_ID);

	/**
	 * Get Activity.
	 * Business Activity
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Activity_ID();

	String COLUMNNAME_C_Activity_ID = "C_Activity_ID";

	/**
	 * Set Business Partner (2).
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_BPartner2_ID (int C_BPartner2_ID);

	/**
	 * Get Business Partner (2).
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_BPartner2_ID();

	String COLUMNNAME_C_BPartner2_ID = "C_BPartner2_ID";

	/**
	 * Set Campaign.
	 * Marketing Campaign
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Campaign_ID (int C_Campaign_ID);

	/**
	 * Get Campaign.
	 * Marketing Campaign
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Campaign_ID();

	@Nullable org.compiere.model.I_C_Campaign getC_Campaign();

	void setC_Campaign(@Nullable org.compiere.model.I_C_Campaign C_Campaign);

	ModelColumn<I_C_InvoiceLine, org.compiere.model.I_C_Campaign> COLUMN_C_Campaign_ID = new ModelColumn<>(I_C_InvoiceLine.class, "C_Campaign_ID", org.compiere.model.I_C_Campaign.class);
	String COLUMNNAME_C_Campaign_ID = "C_Campaign_ID";

	/**
	 * Set Costs.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Charge_ID (int C_Charge_ID);

	/**
	 * Get Costs.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Charge_ID();

	String COLUMNNAME_C_Charge_ID = "C_Charge_ID";

	/**
	 * Set Flatrate Term.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Flatrate_Term_ID (int C_Flatrate_Term_ID);

	/**
	 * Get Flatrate Term.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Flatrate_Term_ID();

	ModelColumn<I_C_InvoiceLine, Object> COLUMN_C_Flatrate_Term_ID = new ModelColumn<>(I_C_InvoiceLine.class, "C_Flatrate_Term_ID", null);
	String COLUMNNAME_C_Flatrate_Term_ID = "C_Flatrate_Term_ID";

	/**
	 * Set Harvesting Calendar.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Harvesting_Calendar_ID (int C_Harvesting_Calendar_ID);

	/**
	 * Get Harvesting Calendar.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Harvesting_Calendar_ID();

	@Nullable org.compiere.model.I_C_Calendar getC_Harvesting_Calendar();

	void setC_Harvesting_Calendar(@Nullable org.compiere.model.I_C_Calendar C_Harvesting_Calendar);

	ModelColumn<I_C_InvoiceLine, org.compiere.model.I_C_Calendar> COLUMN_C_Harvesting_Calendar_ID = new ModelColumn<>(I_C_InvoiceLine.class, "C_Harvesting_Calendar_ID", org.compiere.model.I_C_Calendar.class);
	String COLUMNNAME_C_Harvesting_Calendar_ID = "C_Harvesting_Calendar_ID";

	/**
	 * Set Invoice.
	 * Invoice Identifier
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Invoice_ID (int C_Invoice_ID);

	/**
	 * Get Invoice.
	 * Invoice Identifier
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Invoice_ID();

	org.compiere.model.I_C_Invoice getC_Invoice();

	void setC_Invoice(org.compiere.model.I_C_Invoice C_Invoice);

	ModelColumn<I_C_InvoiceLine, org.compiere.model.I_C_Invoice> COLUMN_C_Invoice_ID = new ModelColumn<>(I_C_InvoiceLine.class, "C_Invoice_ID", org.compiere.model.I_C_Invoice.class);
	String COLUMNNAME_C_Invoice_ID = "C_Invoice_ID";

	/**
	 * Set Invoice Line.
	 * Rechnungszeile
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_InvoiceLine_ID (int C_InvoiceLine_ID);

	/**
	 * Get Invoice Line.
	 * Rechnungszeile
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_InvoiceLine_ID();

	ModelColumn<I_C_InvoiceLine, Object> COLUMN_C_InvoiceLine_ID = new ModelColumn<>(I_C_InvoiceLine.class, "C_InvoiceLine_ID", null);
	String COLUMNNAME_C_InvoiceLine_ID = "C_InvoiceLine_ID";

	/**
	 * Set Sales order.
	 * Order
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Order_ID (int C_Order_ID);

	/**
	 * Get Sales order.
	 * Order
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Order_ID();

	@Nullable org.compiere.model.I_C_Order getC_Order();

	void setC_Order(@Nullable org.compiere.model.I_C_Order C_Order);

	ModelColumn<I_C_InvoiceLine, org.compiere.model.I_C_Order> COLUMN_C_Order_ID = new ModelColumn<>(I_C_InvoiceLine.class, "C_Order_ID", org.compiere.model.I_C_Order.class);
	String COLUMNNAME_C_Order_ID = "C_Order_ID";

	/**
	 * Set Orderline.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_OrderLine_ID (int C_OrderLine_ID);

	/**
	 * Get Orderline.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_OrderLine_ID();

	@Nullable org.compiere.model.I_C_OrderLine getC_OrderLine();

	void setC_OrderLine(@Nullable org.compiere.model.I_C_OrderLine C_OrderLine);

	ModelColumn<I_C_InvoiceLine, org.compiere.model.I_C_OrderLine> COLUMN_C_OrderLine_ID = new ModelColumn<>(I_C_InvoiceLine.class, "C_OrderLine_ID", org.compiere.model.I_C_OrderLine.class);
	String COLUMNNAME_C_OrderLine_ID = "C_OrderLine_ID";

	/**
	 * Set Sales Order.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_OrderSO_ID (int C_OrderSO_ID);

	/**
	 * Get Sales Order.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_OrderSO_ID();

	@Nullable org.compiere.model.I_C_Order getC_OrderSO();

	void setC_OrderSO(@Nullable org.compiere.model.I_C_Order C_OrderSO);

	ModelColumn<I_C_InvoiceLine, org.compiere.model.I_C_Order> COLUMN_C_OrderSO_ID = new ModelColumn<>(I_C_InvoiceLine.class, "C_OrderSO_ID", org.compiere.model.I_C_Order.class);
	String COLUMNNAME_C_OrderSO_ID = "C_OrderSO_ID";

	/**
	 * Set Project.
	 * Financial Project
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Project_ID (int C_Project_ID);

	/**
	 * Get Project.
	 * Financial Project
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Project_ID();

	String COLUMNNAME_C_Project_ID = "C_Project_ID";

	/**
	 * Set Project Phase.
	 * Phase of a Project
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_ProjectPhase_ID (int C_ProjectPhase_ID);

	/**
	 * Get Project Phase.
	 * Phase of a Project
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_ProjectPhase_ID();

	@Nullable org.compiere.model.I_C_ProjectPhase getC_ProjectPhase();

	void setC_ProjectPhase(@Nullable org.compiere.model.I_C_ProjectPhase C_ProjectPhase);

	ModelColumn<I_C_InvoiceLine, org.compiere.model.I_C_ProjectPhase> COLUMN_C_ProjectPhase_ID = new ModelColumn<>(I_C_InvoiceLine.class, "C_ProjectPhase_ID", org.compiere.model.I_C_ProjectPhase.class);
	String COLUMNNAME_C_ProjectPhase_ID = "C_ProjectPhase_ID";

	/**
	 * Set Project Task.
	 * Actual Project Task in a Phase
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_ProjectTask_ID (int C_ProjectTask_ID);

	/**
	 * Get Project Task.
	 * Actual Project Task in a Phase
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_ProjectTask_ID();

	@Nullable org.compiere.model.I_C_ProjectTask getC_ProjectTask();

	void setC_ProjectTask(@Nullable org.compiere.model.I_C_ProjectTask C_ProjectTask);

	ModelColumn<I_C_InvoiceLine, org.compiere.model.I_C_ProjectTask> COLUMN_C_ProjectTask_ID = new ModelColumn<>(I_C_InvoiceLine.class, "C_ProjectTask_ID", org.compiere.model.I_C_ProjectTask.class);
	String COLUMNNAME_C_ProjectTask_ID = "C_ProjectTask_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_C_InvoiceLine, Object> COLUMN_Created = new ModelColumn<>(I_C_InvoiceLine.class, "Created", null);
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
	 * Set Shipping Location.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Shipping_Location_ID (int C_Shipping_Location_ID);

	/**
	 * Get Shipping Location.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Shipping_Location_ID();

	ModelColumn<I_C_InvoiceLine, Object> COLUMN_C_Shipping_Location_ID = new ModelColumn<>(I_C_InvoiceLine.class, "C_Shipping_Location_ID", null);
	String COLUMNNAME_C_Shipping_Location_ID = "C_Shipping_Location_ID";

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
	 * Set Tax.
	 * Tax identifier
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Tax_ID (int C_Tax_ID);

	/**
	 * Get Tax.
	 * Tax identifier
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Tax_ID();

	String COLUMNNAME_C_Tax_ID = "C_Tax_ID";

	/**
	 * Set BPartner UOM.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_UOM_BPartner_ID (int C_UOM_BPartner_ID);

	/**
	 * Get BPartner UOM.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_UOM_BPartner_ID();

	String COLUMNNAME_C_UOM_BPartner_ID = "C_UOM_BPartner_ID";

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
	 * Set VAT Code.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_VAT_Code_ID (int C_VAT_Code_ID);

	/**
	 * Get VAT Code.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_VAT_Code_ID();

	ModelColumn<I_C_InvoiceLine, Object> COLUMN_C_VAT_Code_ID = new ModelColumn<>(I_C_InvoiceLine.class, "C_VAT_Code_ID", null);
	String COLUMNNAME_C_VAT_Code_ID = "C_VAT_Code_ID";

	/**
	 * Set Description.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDescription (@Nullable java.lang.String Description);

	/**
	 * Get Description.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDescription();

	ModelColumn<I_C_InvoiceLine, Object> COLUMN_Description = new ModelColumn<>(I_C_InvoiceLine.class, "Description", null);
	String COLUMNNAME_Description = "Description";

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

	ModelColumn<I_C_InvoiceLine, Object> COLUMN_Discount = new ModelColumn<>(I_C_InvoiceLine.class, "Discount", null);
	String COLUMNNAME_Discount = "Discount";

	/**
	 * Set External IDs.
	 * List of external IDs from C_Invoice_Candidates;
 delimited with ';
,;
'
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setExternalIds (@Nullable java.lang.String ExternalIds);

	/**
	 * Get External IDs.
	 * List of external IDs from C_Invoice_Candidates;
 delimited with ';
,;
'
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getExternalIds();

	ModelColumn<I_C_InvoiceLine, Object> COLUMN_ExternalIds = new ModelColumn<>(I_C_InvoiceLine.class, "ExternalIds", null);
	String COLUMNNAME_ExternalIds = "ExternalIds";

	/**
	 * Set Harvesting Year.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setHarvesting_Year_ID (int Harvesting_Year_ID);

	/**
	 * Get Harvesting Year.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getHarvesting_Year_ID();

	@Nullable org.compiere.model.I_C_Year getHarvesting_Year();

	void setHarvesting_Year(@Nullable org.compiere.model.I_C_Year Harvesting_Year);

	ModelColumn<I_C_InvoiceLine, org.compiere.model.I_C_Year> COLUMN_Harvesting_Year_ID = new ModelColumn<>(I_C_InvoiceLine.class, "Harvesting_Year_ID", org.compiere.model.I_C_Year.class);
	String COLUMNNAME_Harvesting_Year_ID = "Harvesting_Year_ID";

	/**
	 * Set Invoice Line Group.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setInvoicingGroup (@Nullable java.lang.String InvoicingGroup);

	/**
	 * Get Invoice Line Group.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getInvoicingGroup();

	ModelColumn<I_C_InvoiceLine, Object> COLUMN_InvoicingGroup = new ModelColumn<>(I_C_InvoiceLine.class, "InvoicingGroup", null);
	String COLUMNNAME_InvoicingGroup = "InvoicingGroup";

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

	ModelColumn<I_C_InvoiceLine, Object> COLUMN_IsActive = new ModelColumn<>(I_C_InvoiceLine.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Description Only.
	 * if true, the line is just description and no transaction
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsDescription (boolean IsDescription);

	/**
	 * Get Description Only.
	 * if true, the line is just description and no transaction
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isDescription();

	ModelColumn<I_C_InvoiceLine, Object> COLUMN_IsDescription = new ModelColumn<>(I_C_InvoiceLine.class, "IsDescription", null);
	String COLUMNNAME_IsDescription = "IsDescription";

	/**
	 * Set Freight Cost Line.
	 * This is a Freight Cost Line
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setIsFreightCostLine (boolean IsFreightCostLine);

	/**
	 * Get Freight Cost Line.
	 * This is a Freight Cost Line
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	boolean isFreightCostLine();

	ModelColumn<I_C_InvoiceLine, Object> COLUMN_IsFreightCostLine = new ModelColumn<>(I_C_InvoiceLine.class, "IsFreightCostLine", null);
	String COLUMNNAME_IsFreightCostLine = "IsFreightCostLine";

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

	ModelColumn<I_C_InvoiceLine, Object> COLUMN_IsManualPrice = new ModelColumn<>(I_C_InvoiceLine.class, "IsManualPrice", null);
	String COLUMNNAME_IsManualPrice = "IsManualPrice";

	/**
	 * Set IsOrderLineReadOnly.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsOrderLineReadOnly (boolean IsOrderLineReadOnly);

	/**
	 * Get IsOrderLineReadOnly.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isOrderLineReadOnly();

	ModelColumn<I_C_InvoiceLine, Object> COLUMN_IsOrderLineReadOnly = new ModelColumn<>(I_C_InvoiceLine.class, "IsOrderLineReadOnly", null);
	String COLUMNNAME_IsOrderLineReadOnly = "IsOrderLineReadOnly";

	/**
	 * Set Packaging Material .
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsPackagingMaterial (boolean IsPackagingMaterial);

	/**
	 * Get Packaging Material .
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isPackagingMaterial();

	ModelColumn<I_C_InvoiceLine, Object> COLUMN_IsPackagingMaterial = new ModelColumn<>(I_C_InvoiceLine.class, "IsPackagingMaterial", null);
	String COLUMNNAME_IsPackagingMaterial = "IsPackagingMaterial";

	/**
	 * Set Price ReadOnly.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsPriceReadOnly (boolean IsPriceReadOnly);

	/**
	 * Get Price ReadOnly.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isPriceReadOnly();

	ModelColumn<I_C_InvoiceLine, Object> COLUMN_IsPriceReadOnly = new ModelColumn<>(I_C_InvoiceLine.class, "IsPriceReadOnly", null);
	String COLUMNNAME_IsPriceReadOnly = "IsPriceReadOnly";

	/**
	 * Set Printed.
	 * Indicates if this document / line is printed
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsPrinted (boolean IsPrinted);

	/**
	 * Get Printed.
	 * Indicates if this document / line is printed
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isPrinted();

	ModelColumn<I_C_InvoiceLine, Object> COLUMN_IsPrinted = new ModelColumn<>(I_C_InvoiceLine.class, "IsPrinted", null);
	String COLUMNNAME_IsPrinted = "IsPrinted";

	/**
	 * Set Qty ReadOnly.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsQtyReadOnly (boolean IsQtyReadOnly);

	/**
	 * Get Qty ReadOnly.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isQtyReadOnly();

	ModelColumn<I_C_InvoiceLine, Object> COLUMN_IsQtyReadOnly = new ModelColumn<>(I_C_InvoiceLine.class, "IsQtyReadOnly", null);
	String COLUMNNAME_IsQtyReadOnly = "IsQtyReadOnly";

	/**
	 * Set SeqNo..
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setLine (int Line);

	/**
	 * Get SeqNo..
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getLine();

	ModelColumn<I_C_InvoiceLine, Object> COLUMN_Line = new ModelColumn<>(I_C_InvoiceLine.class, "Line", null);
	String COLUMNNAME_Line = "Line";

	/**
	 * Set Credit Reason.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setLine_CreditMemoReason (@Nullable java.lang.String Line_CreditMemoReason);

	/**
	 * Get Credit Reason.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getLine_CreditMemoReason();

	ModelColumn<I_C_InvoiceLine, Object> COLUMN_Line_CreditMemoReason = new ModelColumn<>(I_C_InvoiceLine.class, "Line_CreditMemoReason", null);
	String COLUMNNAME_Line_CreditMemoReason = "Line_CreditMemoReason";

	/**
	 * Set Line Net Amount.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setLineNetAmt (BigDecimal LineNetAmt);

	/**
	 * Get Line Net Amount.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getLineNetAmt();

	ModelColumn<I_C_InvoiceLine, Object> COLUMN_LineNetAmt = new ModelColumn<>(I_C_InvoiceLine.class, "LineNetAmt", null);
	String COLUMNNAME_LineNetAmt = "LineNetAmt";

	/**
	 * Set Line Total.
	 * Total line amount incl. Tax
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setLineTotalAmt (@Nullable BigDecimal LineTotalAmt);

	/**
	 * Get Line Total.
	 * Total line amount incl. Tax
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getLineTotalAmt();

	ModelColumn<I_C_InvoiceLine, Object> COLUMN_LineTotalAmt = new ModelColumn<>(I_C_InvoiceLine.class, "LineTotalAmt", null);
	String COLUMNNAME_LineTotalAmt = "LineTotalAmt";

	/**
	 * Set Attributes.
	 * Attribute Instances for Products
	 *
	 * <br>Type: PAttribute
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_AttributeSetInstance_ID (int M_AttributeSetInstance_ID);

	/**
	 * Get Attributes.
	 * Attribute Instances for Products
	 *
	 * <br>Type: PAttribute
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_AttributeSetInstance_ID();

	org.compiere.model.I_M_AttributeSetInstance getM_AttributeSetInstance();

	void setM_AttributeSetInstance(org.compiere.model.I_M_AttributeSetInstance M_AttributeSetInstance);

	ModelColumn<I_C_InvoiceLine, org.compiere.model.I_M_AttributeSetInstance> COLUMN_M_AttributeSetInstance_ID = new ModelColumn<>(I_C_InvoiceLine.class, "M_AttributeSetInstance_ID", org.compiere.model.I_M_AttributeSetInstance.class);
	String COLUMNNAME_M_AttributeSetInstance_ID = "M_AttributeSetInstance_ID";

	/**
	 * Set Packing Instruction.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_HU_PI_Item_Product_ID (int M_HU_PI_Item_Product_ID);

	/**
	 * Get Packing Instruction.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_HU_PI_Item_Product_ID();

	ModelColumn<I_C_InvoiceLine, Object> COLUMN_M_HU_PI_Item_Product_ID = new ModelColumn<>(I_C_InvoiceLine.class, "M_HU_PI_Item_Product_ID", null);
	String COLUMNNAME_M_HU_PI_Item_Product_ID = "M_HU_PI_Item_Product_ID";

	/**
	 * Set Shipment/ Receipt.
	 * Material Shipment Document
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setM_InOut_ID (int M_InOut_ID);

	/**
	 * Get Shipment/ Receipt.
	 * Material Shipment Document
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	int getM_InOut_ID();

	@Deprecated
	@Nullable org.compiere.model.I_M_InOut getM_InOut();

	@Deprecated
	void setM_InOut(@Nullable org.compiere.model.I_M_InOut M_InOut);

	ModelColumn<I_C_InvoiceLine, org.compiere.model.I_M_InOut> COLUMN_M_InOut_ID = new ModelColumn<>(I_C_InvoiceLine.class, "M_InOut_ID", org.compiere.model.I_M_InOut.class);
	String COLUMNNAME_M_InOut_ID = "M_InOut_ID";

	/**
	 * Set Receipt Line.
	 * Line on Receipt document
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_InOutLine_ID (int M_InOutLine_ID);

	/**
	 * Get Receipt Line.
	 * Line on Receipt document
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_InOutLine_ID();

	@Nullable org.compiere.model.I_M_InOutLine getM_InOutLine();

	void setM_InOutLine(@Nullable org.compiere.model.I_M_InOutLine M_InOutLine);

	ModelColumn<I_C_InvoiceLine, org.compiere.model.I_M_InOutLine> COLUMN_M_InOutLine_ID = new ModelColumn<>(I_C_InvoiceLine.class, "M_InOutLine_ID", org.compiere.model.I_M_InOutLine.class);
	String COLUMNNAME_M_InOutLine_ID = "M_InOutLine_ID";

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
	 * Set RMA Line.
	 * Return Material Authorization Line
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_RMALine_ID (int M_RMALine_ID);

	/**
	 * Get RMA Line.
	 * Return Material Authorization Line
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_RMALine_ID();

	@Nullable org.compiere.model.I_M_RMALine getM_RMALine();

	void setM_RMALine(@Nullable org.compiere.model.I_M_RMALine M_RMALine);

	ModelColumn<I_C_InvoiceLine, org.compiere.model.I_M_RMALine> COLUMN_M_RMALine_ID = new ModelColumn<>(I_C_InvoiceLine.class, "M_RMALine_ID", org.compiere.model.I_M_RMALine.class);
	String COLUMNNAME_M_RMALine_ID = "M_RMALine_ID";

	/**
	 * Set Section Code.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_SectionCode_ID (int M_SectionCode_ID);

	/**
	 * Get Section Code.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_SectionCode_ID();

	@Nullable org.compiere.model.I_M_SectionCode getM_SectionCode();

	void setM_SectionCode(@Nullable org.compiere.model.I_M_SectionCode M_SectionCode);

	ModelColumn<I_C_InvoiceLine, org.compiere.model.I_M_SectionCode> COLUMN_M_SectionCode_ID = new ModelColumn<>(I_C_InvoiceLine.class, "M_SectionCode_ID", org.compiere.model.I_M_SectionCode.class);
	String COLUMNNAME_M_SectionCode_ID = "M_SectionCode_ID";

	/**
	 * Set Price Actual.
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPriceActual (BigDecimal PriceActual);

	/**
	 * Get Price Actual.
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getPriceActual();

	ModelColumn<I_C_InvoiceLine, Object> COLUMN_PriceActual = new ModelColumn<>(I_C_InvoiceLine.class, "PriceActual", null);
	String COLUMNNAME_PriceActual = "PriceActual";

	/**
	 * Set Price imp..
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPriceEntered (BigDecimal PriceEntered);

	/**
	 * Get Price imp..
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getPriceEntered();

	ModelColumn<I_C_InvoiceLine, Object> COLUMN_PriceEntered = new ModelColumn<>(I_C_InvoiceLine.class, "PriceEntered", null);
	String COLUMNNAME_PriceEntered = "PriceEntered";

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

	ModelColumn<I_C_InvoiceLine, Object> COLUMN_PriceLimit = new ModelColumn<>(I_C_InvoiceLine.class, "PriceLimit", null);
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

	ModelColumn<I_C_InvoiceLine, Object> COLUMN_PriceList = new ModelColumn<>(I_C_InvoiceLine.class, "PriceList", null);
	String COLUMNNAME_PriceList = "PriceList";

	/**
	 * Set Price Unit.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPrice_UOM_ID (int Price_UOM_ID);

	/**
	 * Get Price Unit.
	 *
	 * <br>Type: Table
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

	ModelColumn<I_C_InvoiceLine, Object> COLUMN_Processed = new ModelColumn<>(I_C_InvoiceLine.class, "Processed", null);
	String COLUMNNAME_Processed = "Processed";

	/**
	 * Set Product Description.
	 * Product Description
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setProductDescription (@Nullable java.lang.String ProductDescription);

	/**
	 * Get Product Description.
	 * Product Description
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getProductDescription();

	ModelColumn<I_C_InvoiceLine, Object> COLUMN_ProductDescription = new ModelColumn<>(I_C_InvoiceLine.class, "ProductDescription", null);
	String COLUMNNAME_ProductDescription = "ProductDescription";

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

	ModelColumn<I_C_InvoiceLine, Object> COLUMN_ProductName = new ModelColumn<>(I_C_InvoiceLine.class, "ProductName", null);
	String COLUMNNAME_ProductName = "ProductName";

	/**
	 * Set Qty.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setQtyEntered (BigDecimal QtyEntered);

	/**
	 * Get Qty.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyEntered();

	ModelColumn<I_C_InvoiceLine, Object> COLUMN_QtyEntered = new ModelColumn<>(I_C_InvoiceLine.class, "QtyEntered", null);
	String COLUMNNAME_QtyEntered = "QtyEntered";

	/**
	 * Set Qty Entered In BPartner UOM.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQtyEnteredInBPartnerUOM (@Nullable BigDecimal QtyEnteredInBPartnerUOM);

	/**
	 * Get Qty Entered In BPartner UOM.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyEnteredInBPartnerUOM();

	ModelColumn<I_C_InvoiceLine, Object> COLUMN_QtyEnteredInBPartnerUOM = new ModelColumn<>(I_C_InvoiceLine.class, "QtyEnteredInBPartnerUOM", null);
	String COLUMNNAME_QtyEnteredInBPartnerUOM = "QtyEnteredInBPartnerUOM";

	/**
	 * Set Quantity TU.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQtyEnteredTU (@Nullable BigDecimal QtyEnteredTU);

	/**
	 * Get Quantity TU.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyEnteredTU();

	ModelColumn<I_C_InvoiceLine, Object> COLUMN_QtyEnteredTU = new ModelColumn<>(I_C_InvoiceLine.class, "QtyEnteredTU", null);
	String COLUMNNAME_QtyEnteredTU = "QtyEnteredTU";

	/**
	 * Set Quantity Invoiced.
	 * Invoiced quantity in the product's UOM.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setQtyInvoiced (BigDecimal QtyInvoiced);

	/**
	 * Get Quantity Invoiced.
	 * Invoiced quantity in the product's UOM.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyInvoiced();

	ModelColumn<I_C_InvoiceLine, Object> COLUMN_QtyInvoiced = new ModelColumn<>(I_C_InvoiceLine.class, "QtyInvoiced", null);
	String COLUMNNAME_QtyInvoiced = "QtyInvoiced";

	/**
	 * Set Quantity in Price Unit.
	 * Menge, die bereits in Rechnung gestellt wurde, umgerechnet in die Einheit auf die sich der Preis bezieht
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQtyInvoicedInPriceUOM (@Nullable BigDecimal QtyInvoicedInPriceUOM);

	/**
	 * Get Quantity in Price Unit.
	 * Menge, die bereits in Rechnung gestellt wurde, umgerechnet in die Einheit auf die sich der Preis bezieht
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyInvoicedInPriceUOM();

	ModelColumn<I_C_InvoiceLine, Object> COLUMN_QtyInvoicedInPriceUOM = new ModelColumn<>(I_C_InvoiceLine.class, "QtyInvoicedInPriceUOM", null);
	String COLUMNNAME_QtyInvoicedInPriceUOM = "QtyInvoicedInPriceUOM";

	/**
	 * Set Referenced Invoice Line.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setRef_InvoiceLine_ID (int Ref_InvoiceLine_ID);

	/**
	 * Get Referenced Invoice Line.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getRef_InvoiceLine_ID();

	ModelColumn<I_C_InvoiceLine, Object> COLUMN_Ref_InvoiceLine_ID = new ModelColumn<>(I_C_InvoiceLine.class, "Ref_InvoiceLine_ID", null);
	String COLUMNNAME_Ref_InvoiceLine_ID = "Ref_InvoiceLine_ID";

	/**
	 * Set Revenue Recognition Amt.
	 * Revenue Recognition Amount
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setRRAmt (@Nullable BigDecimal RRAmt);

	/**
	 * Get Revenue Recognition Amt.
	 * Revenue Recognition Amount
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getRRAmt();

	ModelColumn<I_C_InvoiceLine, Object> COLUMN_RRAmt = new ModelColumn<>(I_C_InvoiceLine.class, "RRAmt", null);
	String COLUMNNAME_RRAmt = "RRAmt";

	/**
	 * Set Revenue Recognition Start.
	 * Revenue Recognition Start Date
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setRRStartDate (@Nullable java.sql.Timestamp RRStartDate);

	/**
	 * Get Revenue Recognition Start.
	 * Revenue Recognition Start Date
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getRRStartDate();

	ModelColumn<I_C_InvoiceLine, Object> COLUMN_RRStartDate = new ModelColumn<>(I_C_InvoiceLine.class, "RRStartDate", null);
	String COLUMNNAME_RRStartDate = "RRStartDate";

	/**
	 * Set Resource Assignment.
	 * Resource Assignment
	 *
	 * <br>Type: Assignment
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setS_ResourceAssignment_ID (int S_ResourceAssignment_ID);

	/**
	 * Get Resource Assignment.
	 * Resource Assignment
	 *
	 * <br>Type: Assignment
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getS_ResourceAssignment_ID();

	ModelColumn<I_C_InvoiceLine, Object> COLUMN_S_ResourceAssignment_ID = new ModelColumn<>(I_C_InvoiceLine.class, "S_ResourceAssignment_ID", null);
	String COLUMNNAME_S_ResourceAssignment_ID = "S_ResourceAssignment_ID";

	/**
	 * Set Tax Amount.
	 * Tax Amount for Credit Card transaction
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setTaxAmt (@Nullable BigDecimal TaxAmt);

	/**
	 * Get Tax Amount.
	 * Tax Amount for Credit Card transaction
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getTaxAmt();

	ModelColumn<I_C_InvoiceLine, Object> COLUMN_TaxAmt = new ModelColumn<>(I_C_InvoiceLine.class, "TaxAmt", null);
	String COLUMNNAME_TaxAmt = "TaxAmt";

	/**
	 * Set Positions-Steuer.
	 * Betrag der enthaltenen oder zuzgl. Steuer in einer Rechungs- oder Auftragsposition
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setTaxAmtInfo (@Nullable BigDecimal TaxAmtInfo);

	/**
	 * Get Positions-Steuer.
	 * Betrag der enthaltenen oder zuzgl. Steuer in einer Rechungs- oder Auftragsposition
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getTaxAmtInfo();

	ModelColumn<I_C_InvoiceLine, Object> COLUMN_TaxAmtInfo = new ModelColumn<>(I_C_InvoiceLine.class, "TaxAmtInfo", null);
	String COLUMNNAME_TaxAmtInfo = "TaxAmtInfo";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_C_InvoiceLine, Object> COLUMN_Updated = new ModelColumn<>(I_C_InvoiceLine.class, "Updated", null);
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
	 * Set User List 1.
	 * User defined list element #1
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setUser1_ID (int User1_ID);

	/**
	 * Get User List 1.
	 * User defined list element #1
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getUser1_ID();

	String COLUMNNAME_User1_ID = "User1_ID";

	/**
	 * Set User 2.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setUser2_ID (int User2_ID);

	/**
	 * Get User 2.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getUser2_ID();

	String COLUMNNAME_User2_ID = "User2_ID";

	/**
	 * Set UserElementDate1.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setUserElementDate1 (@Nullable java.sql.Timestamp UserElementDate1);

	/**
	 * Get UserElementDate1.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getUserElementDate1();

	ModelColumn<I_C_InvoiceLine, Object> COLUMN_UserElementDate1 = new ModelColumn<>(I_C_InvoiceLine.class, "UserElementDate1", null);
	String COLUMNNAME_UserElementDate1 = "UserElementDate1";

	/**
	 * Set UserElementDate2.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setUserElementDate2 (@Nullable java.sql.Timestamp UserElementDate2);

	/**
	 * Get UserElementDate2.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getUserElementDate2();

	ModelColumn<I_C_InvoiceLine, Object> COLUMN_UserElementDate2 = new ModelColumn<>(I_C_InvoiceLine.class, "UserElementDate2", null);
	String COLUMNNAME_UserElementDate2 = "UserElementDate2";

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

	ModelColumn<I_C_InvoiceLine, Object> COLUMN_UserElementNumber1 = new ModelColumn<>(I_C_InvoiceLine.class, "UserElementNumber1", null);
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

	ModelColumn<I_C_InvoiceLine, Object> COLUMN_UserElementNumber2 = new ModelColumn<>(I_C_InvoiceLine.class, "UserElementNumber2", null);
	String COLUMNNAME_UserElementNumber2 = "UserElementNumber2";

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

	ModelColumn<I_C_InvoiceLine, Object> COLUMN_UserElementString1 = new ModelColumn<>(I_C_InvoiceLine.class, "UserElementString1", null);
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

	ModelColumn<I_C_InvoiceLine, Object> COLUMN_UserElementString2 = new ModelColumn<>(I_C_InvoiceLine.class, "UserElementString2", null);
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

	ModelColumn<I_C_InvoiceLine, Object> COLUMN_UserElementString3 = new ModelColumn<>(I_C_InvoiceLine.class, "UserElementString3", null);
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

	ModelColumn<I_C_InvoiceLine, Object> COLUMN_UserElementString4 = new ModelColumn<>(I_C_InvoiceLine.class, "UserElementString4", null);
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

	ModelColumn<I_C_InvoiceLine, Object> COLUMN_UserElementString5 = new ModelColumn<>(I_C_InvoiceLine.class, "UserElementString5", null);
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

	ModelColumn<I_C_InvoiceLine, Object> COLUMN_UserElementString6 = new ModelColumn<>(I_C_InvoiceLine.class, "UserElementString6", null);
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

	ModelColumn<I_C_InvoiceLine, Object> COLUMN_UserElementString7 = new ModelColumn<>(I_C_InvoiceLine.class, "UserElementString7", null);
	String COLUMNNAME_UserElementString7 = "UserElementString7";
}
