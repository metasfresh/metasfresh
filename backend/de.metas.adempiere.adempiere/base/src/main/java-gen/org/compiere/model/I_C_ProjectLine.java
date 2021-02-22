package org.compiere.model;

import java.math.BigDecimal;
import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for C_ProjectLine
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_C_ProjectLine 
{

	String Table_Name = "C_ProjectLine";

//	/** AD_Table_ID=434 */
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

	ModelColumn<I_C_ProjectLine, org.compiere.model.I_C_Order> COLUMN_C_Order_ID = new ModelColumn<>(I_C_ProjectLine.class, "C_Order_ID", org.compiere.model.I_C_Order.class);
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

	ModelColumn<I_C_ProjectLine, org.compiere.model.I_C_OrderLine> COLUMN_C_OrderLine_ID = new ModelColumn<>(I_C_ProjectLine.class, "C_OrderLine_ID", org.compiere.model.I_C_OrderLine.class);
	String COLUMNNAME_C_OrderLine_ID = "C_OrderLine_ID";

	/**
	 * Set Bestellung.
	 * Purchase Order
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_OrderPO_ID (int C_OrderPO_ID);

	/**
	 * Get Bestellung.
	 * Purchase Order
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_OrderPO_ID();

	@Nullable org.compiere.model.I_C_Order getC_OrderPO();

	void setC_OrderPO(@Nullable org.compiere.model.I_C_Order C_OrderPO);

	ModelColumn<I_C_ProjectLine, org.compiere.model.I_C_Order> COLUMN_C_OrderPO_ID = new ModelColumn<>(I_C_ProjectLine.class, "C_OrderPO_ID", org.compiere.model.I_C_Order.class);
	String COLUMNNAME_C_OrderPO_ID = "C_OrderPO_ID";

	/**
	 * Set Project.
	 * Financial Project
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Project_ID (int C_Project_ID);

	/**
	 * Get Project.
	 * Financial Project
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Project_ID();

	String COLUMNNAME_C_Project_ID = "C_Project_ID";

	/**
	 * Set Projekt-Problem.
	 * Project Issues (Material, Labor)
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_ProjectIssue_ID (int C_ProjectIssue_ID);

	/**
	 * Get Projekt-Problem.
	 * Project Issues (Material, Labor)
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_ProjectIssue_ID();

	@Nullable org.compiere.model.I_C_ProjectIssue getC_ProjectIssue();

	void setC_ProjectIssue(@Nullable org.compiere.model.I_C_ProjectIssue C_ProjectIssue);

	ModelColumn<I_C_ProjectLine, org.compiere.model.I_C_ProjectIssue> COLUMN_C_ProjectIssue_ID = new ModelColumn<>(I_C_ProjectLine.class, "C_ProjectIssue_ID", org.compiere.model.I_C_ProjectIssue.class);
	String COLUMNNAME_C_ProjectIssue_ID = "C_ProjectIssue_ID";

	/**
	 * Set Project Line.
	 * Task or step in a project
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_ProjectLine_ID (int C_ProjectLine_ID);

	/**
	 * Get Project Line.
	 * Task or step in a project
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_ProjectLine_ID();

	ModelColumn<I_C_ProjectLine, Object> COLUMN_C_ProjectLine_ID = new ModelColumn<>(I_C_ProjectLine.class, "C_ProjectLine_ID", null);
	String COLUMNNAME_C_ProjectLine_ID = "C_ProjectLine_ID";

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

	ModelColumn<I_C_ProjectLine, org.compiere.model.I_C_ProjectPhase> COLUMN_C_ProjectPhase_ID = new ModelColumn<>(I_C_ProjectLine.class, "C_ProjectPhase_ID", org.compiere.model.I_C_ProjectPhase.class);
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

	ModelColumn<I_C_ProjectLine, org.compiere.model.I_C_ProjectTask> COLUMN_C_ProjectTask_ID = new ModelColumn<>(I_C_ProjectLine.class, "C_ProjectTask_ID", org.compiere.model.I_C_ProjectTask.class);
	String COLUMNNAME_C_ProjectTask_ID = "C_ProjectTask_ID";

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
	 * Set Committed Amount.
	 * The (legal) commitment amount
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCommittedAmt (@Nullable BigDecimal CommittedAmt);

	/**
	 * Get Committed Amount.
	 * The (legal) commitment amount
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getCommittedAmt();

	ModelColumn<I_C_ProjectLine, Object> COLUMN_CommittedAmt = new ModelColumn<>(I_C_ProjectLine.class, "CommittedAmt", null);
	String COLUMNNAME_CommittedAmt = "CommittedAmt";

	/**
	 * Set Committed Quantity.
	 * The (legal) commitment Quantity
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCommittedQty (@Nullable BigDecimal CommittedQty);

	/**
	 * Get Committed Quantity.
	 * The (legal) commitment Quantity
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getCommittedQty();

	ModelColumn<I_C_ProjectLine, Object> COLUMN_CommittedQty = new ModelColumn<>(I_C_ProjectLine.class, "CommittedQty", null);
	String COLUMNNAME_CommittedQty = "CommittedQty";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_C_ProjectLine, Object> COLUMN_Created = new ModelColumn<>(I_C_ProjectLine.class, "Created", null);
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

	ModelColumn<I_C_ProjectLine, Object> COLUMN_Description = new ModelColumn<>(I_C_ProjectLine.class, "Description", null);
	String COLUMNNAME_Description = "Description";

	/**
	 * Set Pricing.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDoPricing (@Nullable java.lang.String DoPricing);

	/**
	 * Get Pricing.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDoPricing();

	ModelColumn<I_C_ProjectLine, Object> COLUMN_DoPricing = new ModelColumn<>(I_C_ProjectLine.class, "DoPricing", null);
	String COLUMNNAME_DoPricing = "DoPricing";

	/**
	 * Set Invoiced Amount.
	 * The amount invoiced
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setInvoicedAmt (BigDecimal InvoicedAmt);

	/**
	 * Get Invoiced Amount.
	 * The amount invoiced
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getInvoicedAmt();

	ModelColumn<I_C_ProjectLine, Object> COLUMN_InvoicedAmt = new ModelColumn<>(I_C_ProjectLine.class, "InvoicedAmt", null);
	String COLUMNNAME_InvoicedAmt = "InvoicedAmt";

	/**
	 * Set Quantity Invoiced .
	 * The quantity invoiced
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setInvoicedQty (BigDecimal InvoicedQty);

	/**
	 * Get Quantity Invoiced .
	 * The quantity invoiced
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getInvoicedQty();

	ModelColumn<I_C_ProjectLine, Object> COLUMN_InvoicedQty = new ModelColumn<>(I_C_ProjectLine.class, "InvoicedQty", null);
	String COLUMNNAME_InvoicedQty = "InvoicedQty";

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

	ModelColumn<I_C_ProjectLine, Object> COLUMN_IsActive = new ModelColumn<>(I_C_ProjectLine.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set andrucken.
	 * Indicates if this document / line is printed
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsPrinted (boolean IsPrinted);

	/**
	 * Get andrucken.
	 * Indicates if this document / line is printed
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isPrinted();

	ModelColumn<I_C_ProjectLine, Object> COLUMN_IsPrinted = new ModelColumn<>(I_C_ProjectLine.class, "IsPrinted", null);
	String COLUMNNAME_IsPrinted = "IsPrinted";

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

	ModelColumn<I_C_ProjectLine, Object> COLUMN_Line = new ModelColumn<>(I_C_ProjectLine.class, "Line", null);
	String COLUMNNAME_Line = "Line";

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
	 * Set VK Total.
	 * Planned amount for this project
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPlannedAmt (BigDecimal PlannedAmt);

	/**
	 * Get VK Total.
	 * Planned amount for this project
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getPlannedAmt();

	ModelColumn<I_C_ProjectLine, Object> COLUMN_PlannedAmt = new ModelColumn<>(I_C_ProjectLine.class, "PlannedAmt", null);
	String COLUMNNAME_PlannedAmt = "PlannedAmt";

	/**
	 * Set DB1.
	 * Project's planned margin amount
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPlannedMarginAmt (BigDecimal PlannedMarginAmt);

	/**
	 * Get DB1.
	 * Project's planned margin amount
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getPlannedMarginAmt();

	ModelColumn<I_C_ProjectLine, Object> COLUMN_PlannedMarginAmt = new ModelColumn<>(I_C_ProjectLine.class, "PlannedMarginAmt", null);
	String COLUMNNAME_PlannedMarginAmt = "PlannedMarginAmt";

	/**
	 * Set Planned Price.
	 * Planned price for this project line
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPlannedPrice (BigDecimal PlannedPrice);

	/**
	 * Get Planned Price.
	 * Planned price for this project line
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getPlannedPrice();

	ModelColumn<I_C_ProjectLine, Object> COLUMN_PlannedPrice = new ModelColumn<>(I_C_ProjectLine.class, "PlannedPrice", null);
	String COLUMNNAME_PlannedPrice = "PlannedPrice";

	/**
	 * Set Menge.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPlannedQty (BigDecimal PlannedQty);

	/**
	 * Get Menge.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getPlannedQty();

	ModelColumn<I_C_ProjectLine, Object> COLUMN_PlannedQty = new ModelColumn<>(I_C_ProjectLine.class, "PlannedQty", null);
	String COLUMNNAME_PlannedQty = "PlannedQty";

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

	ModelColumn<I_C_ProjectLine, Object> COLUMN_Processed = new ModelColumn<>(I_C_ProjectLine.class, "Processed", null);
	String COLUMNNAME_Processed = "Processed";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_C_ProjectLine, Object> COLUMN_Updated = new ModelColumn<>(I_C_ProjectLine.class, "Updated", null);
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
