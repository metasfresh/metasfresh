package de.metas.contracts.commission.model;

import java.math.BigDecimal;
import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for C_Commission_Overview_V
 *  @author metasfresh (generated) 
 */
public interface I_C_Commission_Overview_V 
{

	String Table_Name = "C_Commission_Overview_V";

//	/** AD_Table_ID=541463 */
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
	 * Set Sales partner.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_BPartner_SalesRep_ID (int C_BPartner_SalesRep_ID);

	/**
	 * Get Sales partner.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_BPartner_SalesRep_ID();

	String COLUMNNAME_C_BPartner_SalesRep_ID = "C_BPartner_SalesRep_ID";

	/**
	 * Set Commission instance.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Commission_Instance_ID (int C_Commission_Instance_ID);

	/**
	 * Get Commission instance.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Commission_Instance_ID();

	@Nullable de.metas.contracts.commission.model.I_C_Commission_Instance getC_Commission_Instance();

	void setC_Commission_Instance(@Nullable de.metas.contracts.commission.model.I_C_Commission_Instance C_Commission_Instance);

	ModelColumn<I_C_Commission_Overview_V, de.metas.contracts.commission.model.I_C_Commission_Instance> COLUMN_C_Commission_Instance_ID = new ModelColumn<>(I_C_Commission_Overview_V.class, "C_Commission_Instance_ID", de.metas.contracts.commission.model.I_C_Commission_Instance.class);
	String COLUMNNAME_C_Commission_Instance_ID = "C_Commission_Instance_ID";

	/**
	 * Set Commission Overview.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Commission_Overview_V_ID (int C_Commission_Overview_V_ID);

	/**
	 * Get Commission Overview.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Commission_Overview_V_ID();

	ModelColumn<I_C_Commission_Overview_V, Object> COLUMN_C_Commission_Overview_V_ID = new ModelColumn<>(I_C_Commission_Overview_V.class, "C_Commission_Overview_V_ID", null);
	String COLUMNNAME_C_Commission_Overview_V_ID = "C_Commission_Overview_V_ID";

	/**
	 * Set Settings detail.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_CommissionSettingsLine_ID (int C_CommissionSettingsLine_ID);

	/**
	 * Get Settings detail.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_CommissionSettingsLine_ID();

	@Nullable de.metas.contracts.commission.model.I_C_CommissionSettingsLine getC_CommissionSettingsLine();

	void setC_CommissionSettingsLine(@Nullable de.metas.contracts.commission.model.I_C_CommissionSettingsLine C_CommissionSettingsLine);

	ModelColumn<I_C_Commission_Overview_V, de.metas.contracts.commission.model.I_C_CommissionSettingsLine> COLUMN_C_CommissionSettingsLine_ID = new ModelColumn<>(I_C_Commission_Overview_V.class, "C_CommissionSettingsLine_ID", de.metas.contracts.commission.model.I_C_CommissionSettingsLine.class);
	String COLUMNNAME_C_CommissionSettingsLine_ID = "C_CommissionSettingsLine_ID";

	/**
	 * Set Commission share.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Commission_Share_ID (int C_Commission_Share_ID);

	/**
	 * Get Commission share.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Commission_Share_ID();

	@Nullable de.metas.contracts.commission.model.I_C_Commission_Share getC_Commission_Share();

	void setC_Commission_Share(@Nullable de.metas.contracts.commission.model.I_C_Commission_Share C_Commission_Share);

	ModelColumn<I_C_Commission_Overview_V, de.metas.contracts.commission.model.I_C_Commission_Share> COLUMN_C_Commission_Share_ID = new ModelColumn<>(I_C_Commission_Overview_V.class, "C_Commission_Share_ID", de.metas.contracts.commission.model.I_C_Commission_Share.class);
	String COLUMNNAME_C_Commission_Share_ID = "C_Commission_Share_ID";

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

	ModelColumn<I_C_Commission_Overview_V, Object> COLUMN_C_Flatrate_Term_ID = new ModelColumn<>(I_C_Commission_Overview_V.class, "C_Flatrate_Term_ID", null);
	String COLUMNNAME_C_Flatrate_Term_ID = "C_Flatrate_Term_ID";

	/**
	 * Set Commission settlement candidate.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Invoice_Candidate_Commission_ID (int C_Invoice_Candidate_Commission_ID);

	/**
	 * Get Commission settlement candidate.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Invoice_Candidate_Commission_ID();

	ModelColumn<I_C_Commission_Overview_V, Object> COLUMN_C_Invoice_Candidate_Commission_ID = new ModelColumn<>(I_C_Commission_Overview_V.class, "C_Invoice_Candidate_Commission_ID", null);
	String COLUMNNAME_C_Invoice_Candidate_Commission_ID = "C_Invoice_Candidate_Commission_ID";

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

	ModelColumn<I_C_Commission_Overview_V, Object> COLUMN_C_Invoice_Candidate_ID = new ModelColumn<>(I_C_Commission_Overview_V.class, "C_Invoice_Candidate_ID", null);
	String COLUMNNAME_C_Invoice_Candidate_ID = "C_Invoice_Candidate_ID";

	/**
	 * Set Commission settlement.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Invoice_Commission_ID (int C_Invoice_Commission_ID);

	/**
	 * Get Commission settlement.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Invoice_Commission_ID();

	@Nullable org.compiere.model.I_C_Invoice getC_Invoice_Commission();

	void setC_Invoice_Commission(@Nullable org.compiere.model.I_C_Invoice C_Invoice_Commission);

	ModelColumn<I_C_Commission_Overview_V, org.compiere.model.I_C_Invoice> COLUMN_C_Invoice_Commission_ID = new ModelColumn<>(I_C_Commission_Overview_V.class, "C_Invoice_Commission_ID", org.compiere.model.I_C_Invoice.class);
	String COLUMNNAME_C_Invoice_Commission_ID = "C_Invoice_Commission_ID";

	/**
	 * Set Invoice.
	 * Invoice Identifier
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Invoice_ID (int C_Invoice_ID);

	/**
	 * Get Invoice.
	 * Invoice Identifier
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Invoice_ID();

	@Nullable org.compiere.model.I_C_Invoice getC_Invoice();

	void setC_Invoice(@Nullable org.compiere.model.I_C_Invoice C_Invoice);

	ModelColumn<I_C_Commission_Overview_V, org.compiere.model.I_C_Invoice> COLUMN_C_Invoice_ID = new ModelColumn<>(I_C_Commission_Overview_V.class, "C_Invoice_ID", org.compiere.model.I_C_Invoice.class);
	String COLUMNNAME_C_Invoice_ID = "C_Invoice_ID";

	/**
	 * Set Commission settlement line.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_InvoiceLine_Commission_ID (int C_InvoiceLine_Commission_ID);

	/**
	 * Get Commission settlement line.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_InvoiceLine_Commission_ID();

	@Nullable org.compiere.model.I_C_InvoiceLine getC_InvoiceLine_Commission();

	void setC_InvoiceLine_Commission(@Nullable org.compiere.model.I_C_InvoiceLine C_InvoiceLine_Commission);

	ModelColumn<I_C_Commission_Overview_V, org.compiere.model.I_C_InvoiceLine> COLUMN_C_InvoiceLine_Commission_ID = new ModelColumn<>(I_C_Commission_Overview_V.class, "C_InvoiceLine_Commission_ID", org.compiere.model.I_C_InvoiceLine.class);
	String COLUMNNAME_C_InvoiceLine_Commission_ID = "C_InvoiceLine_Commission_ID";

	/**
	 * Set Invoice Line.
	 * Rechnungszeile
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_InvoiceLine_ID (int C_InvoiceLine_ID);

	/**
	 * Get Invoice Line.
	 * Rechnungszeile
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_InvoiceLine_ID();

	@Nullable org.compiere.model.I_C_InvoiceLine getC_InvoiceLine();

	void setC_InvoiceLine(@Nullable org.compiere.model.I_C_InvoiceLine C_InvoiceLine);

	ModelColumn<I_C_Commission_Overview_V, org.compiere.model.I_C_InvoiceLine> COLUMN_C_InvoiceLine_ID = new ModelColumn<>(I_C_Commission_Overview_V.class, "C_InvoiceLine_ID", org.compiere.model.I_C_InvoiceLine.class);
	String COLUMNNAME_C_InvoiceLine_ID = "C_InvoiceLine_ID";

	/**
	 * Set Date.
	 * Document date of the commission trigger
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCommissionDate (@Nullable java.sql.Timestamp CommissionDate);

	/**
	 * Get Date.
	 * Document date of the commission trigger
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getCommissionDate();

	ModelColumn<I_C_Commission_Overview_V, Object> COLUMN_CommissionDate = new ModelColumn<>(I_C_Commission_Overview_V.class, "CommissionDate", null);
	String COLUMNNAME_CommissionDate = "CommissionDate";

	/**
	 * Set Commission trigger.
	 * Type of the document that triggered the commission instance
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCommissionTrigger_Type (@Nullable java.lang.String CommissionTrigger_Type);

	/**
	 * Get Commission trigger.
	 * Type of the document that triggered the commission instance
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getCommissionTrigger_Type();

	ModelColumn<I_C_Commission_Overview_V, Object> COLUMN_CommissionTrigger_Type = new ModelColumn<>(I_C_Commission_Overview_V.class, "CommissionTrigger_Type", null);
	String COLUMNNAME_CommissionTrigger_Type = "CommissionTrigger_Type";

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

	ModelColumn<I_C_Commission_Overview_V, org.compiere.model.I_C_Order> COLUMN_C_Order_ID = new ModelColumn<>(I_C_Commission_Overview_V.class, "C_Order_ID", org.compiere.model.I_C_Order.class);
	String COLUMNNAME_C_Order_ID = "C_Order_ID";

	/**
	 * Set Orderline.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_OrderLine_ID (int C_OrderLine_ID);

	/**
	 * Get Orderline.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_OrderLine_ID();

	@Nullable org.compiere.model.I_C_OrderLine getC_OrderLine();

	void setC_OrderLine(@Nullable org.compiere.model.I_C_OrderLine C_OrderLine);

	ModelColumn<I_C_Commission_Overview_V, org.compiere.model.I_C_OrderLine> COLUMN_C_OrderLine_ID = new ModelColumn<>(I_C_Commission_Overview_V.class, "C_OrderLine_ID", org.compiere.model.I_C_OrderLine.class);
	String COLUMNNAME_C_OrderLine_ID = "C_OrderLine_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_C_Commission_Overview_V, Object> COLUMN_Created = new ModelColumn<>(I_C_Commission_Overview_V.class, "Created", null);
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
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_UOM_ID (int C_UOM_ID);

	/**
	 * Get UOM.
	 * Unit of Measure
	 *
	 * <br>Type: Search
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

	ModelColumn<I_C_Commission_Overview_V, Object> COLUMN_IsActive = new ModelColumn<>(I_C_Commission_Overview_V.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

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

	ModelColumn<I_C_Commission_Overview_V, Object> COLUMN_IsSimulation = new ModelColumn<>(I_C_Commission_Overview_V.class, "IsSimulation", null);
	String COLUMNNAME_IsSimulation = "IsSimulation";

	/**
	 * Set Hierarchy level.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setLevelHierarchy (int LevelHierarchy);

	/**
	 * Get Hierarchy level.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getLevelHierarchy();

	ModelColumn<I_C_Commission_Overview_V, Object> COLUMN_LevelHierarchy = new ModelColumn<>(I_C_Commission_Overview_V.class, "LevelHierarchy", null);
	String COLUMNNAME_LevelHierarchy = "LevelHierarchy";

	/**
	 * Set Commission trigger time.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setMostRecentTriggerTimestamp (@Nullable java.sql.Timestamp MostRecentTriggerTimestamp);

	/**
	 * Get Commission trigger time.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getMostRecentTriggerTimestamp();

	ModelColumn<I_C_Commission_Overview_V, Object> COLUMN_MostRecentTriggerTimestamp = new ModelColumn<>(I_C_Commission_Overview_V.class, "MostRecentTriggerTimestamp", null);
	String COLUMNNAME_MostRecentTriggerTimestamp = "MostRecentTriggerTimestamp";

	/**
	 * Set Ordered product.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_Product_Order_ID (int M_Product_Order_ID);

	/**
	 * Get Ordered product.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_Product_Order_ID();

	String COLUMNNAME_M_Product_Order_ID = "M_Product_Order_ID";

	/**
	 * Set % of base points.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPercentOfBasePoints (@Nullable BigDecimal PercentOfBasePoints);

	/**
	 * Get % of base points.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getPercentOfBasePoints();

	ModelColumn<I_C_Commission_Overview_V, Object> COLUMN_PercentOfBasePoints = new ModelColumn<>(I_C_Commission_Overview_V.class, "PercentOfBasePoints", null);
	String COLUMNNAME_PercentOfBasePoints = "PercentOfBasePoints";

	/**
	 * Set Ordered based points.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPointsBase_Forecasted (@Nullable BigDecimal PointsBase_Forecasted);

	/**
	 * Get Ordered based points.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getPointsBase_Forecasted();

	ModelColumn<I_C_Commission_Overview_V, Object> COLUMN_PointsBase_Forecasted = new ModelColumn<>(I_C_Commission_Overview_V.class, "PointsBase_Forecasted", null);
	String COLUMNNAME_PointsBase_Forecasted = "PointsBase_Forecasted";

	/**
	 * Set Invoiceable base points.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPointsBase_Invoiceable (@Nullable BigDecimal PointsBase_Invoiceable);

	/**
	 * Get Invoiceable base points.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getPointsBase_Invoiceable();

	ModelColumn<I_C_Commission_Overview_V, Object> COLUMN_PointsBase_Invoiceable = new ModelColumn<>(I_C_Commission_Overview_V.class, "PointsBase_Invoiceable", null);
	String COLUMNNAME_PointsBase_Invoiceable = "PointsBase_Invoiceable";

	/**
	 * Set Invoiced base points.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPointsBase_Invoiced (@Nullable BigDecimal PointsBase_Invoiced);

	/**
	 * Get Invoiced base points.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getPointsBase_Invoiced();

	ModelColumn<I_C_Commission_Overview_V, Object> COLUMN_PointsBase_Invoiced = new ModelColumn<>(I_C_Commission_Overview_V.class, "PointsBase_Invoiced", null);
	String COLUMNNAME_PointsBase_Invoiced = "PointsBase_Invoiced";

	/**
	 * Set Settled points.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPointsSum_Settled (@Nullable BigDecimal PointsSum_Settled);

	/**
	 * Get Settled points.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getPointsSum_Settled();

	ModelColumn<I_C_Commission_Overview_V, Object> COLUMN_PointsSum_Settled = new ModelColumn<>(I_C_Commission_Overview_V.class, "PointsSum_Settled", null);
	String COLUMNNAME_PointsSum_Settled = "PointsSum_Settled";

	/**
	 * Set Points to settle.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPointsSum_ToSettle (@Nullable BigDecimal PointsSum_ToSettle);

	/**
	 * Get Points to settle.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getPointsSum_ToSettle();

	ModelColumn<I_C_Commission_Overview_V, Object> COLUMN_PointsSum_ToSettle = new ModelColumn<>(I_C_Commission_Overview_V.class, "PointsSum_ToSettle", null);
	String COLUMNNAME_PointsSum_ToSettle = "PointsSum_ToSettle";

	/**
	 * Set Order Reference.
	 * Transaction Reference Number (Sales Order, Purchase Order) of your Business Partner
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPOReference (@Nullable java.lang.String POReference);

	/**
	 * Get Order Reference.
	 * Transaction Reference Number (Sales Order, Purchase Order) of your Business Partner
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getPOReference();

	ModelColumn<I_C_Commission_Overview_V, Object> COLUMN_POReference = new ModelColumn<>(I_C_Commission_Overview_V.class, "POReference", null);
	String COLUMNNAME_POReference = "POReference";

	/**
	 * Set Qty.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQtyEntered (@Nullable BigDecimal QtyEntered);

	/**
	 * Get Qty.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyEntered();

	ModelColumn<I_C_Commission_Overview_V, Object> COLUMN_QtyEntered = new ModelColumn<>(I_C_Commission_Overview_V.class, "QtyEntered", null);
	String COLUMNNAME_QtyEntered = "QtyEntered";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_C_Commission_Overview_V, Object> COLUMN_Updated = new ModelColumn<>(I_C_Commission_Overview_V.class, "Updated", null);
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
