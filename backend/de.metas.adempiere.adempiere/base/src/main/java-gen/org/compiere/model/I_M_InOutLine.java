package org.compiere.model;

import java.math.BigDecimal;
import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for M_InOutLine
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_M_InOutLine 
{

	String Table_Name = "M_InOutLine";

//	/** AD_Table_ID=320 */
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
	 * Set Catch UOM.
	 * Catch weight UOM as taken from the product master data.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCatch_UOM_ID (int Catch_UOM_ID);

	/**
	 * Get Catch UOM.
	 * Catch weight UOM as taken from the product master data.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getCatch_UOM_ID();

	String COLUMNNAME_Catch_UOM_ID = "Catch_UOM_ID";

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

	ModelColumn<I_M_InOutLine, org.compiere.model.I_C_Campaign> COLUMN_C_Campaign_ID = new ModelColumn<>(I_M_InOutLine.class, "C_Campaign_ID", org.compiere.model.I_C_Campaign.class);
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

	ModelColumn<I_M_InOutLine, Object> COLUMN_C_Flatrate_Term_ID = new ModelColumn<>(I_M_InOutLine.class, "C_Flatrate_Term_ID", null);
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

	ModelColumn<I_M_InOutLine, org.compiere.model.I_C_Calendar> COLUMN_C_Harvesting_Calendar_ID = new ModelColumn<>(I_M_InOutLine.class, "C_Harvesting_Calendar_ID", org.compiere.model.I_C_Calendar.class);
	String COLUMNNAME_C_Harvesting_Calendar_ID = "C_Harvesting_Calendar_ID";

	/**
	 * Set Qty Confirmed.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setConfirmedQty (@Nullable BigDecimal ConfirmedQty);

	/**
	 * Get Qty Confirmed.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getConfirmedQty();

	ModelColumn<I_M_InOutLine, Object> COLUMN_ConfirmedQty = new ModelColumn<>(I_M_InOutLine.class, "ConfirmedQty", null);
	String COLUMNNAME_ConfirmedQty = "ConfirmedQty";

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

	ModelColumn<I_M_InOutLine, org.compiere.model.I_C_Order> COLUMN_C_Order_ID = new ModelColumn<>(I_M_InOutLine.class, "C_Order_ID", org.compiere.model.I_C_Order.class);
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

	ModelColumn<I_M_InOutLine, org.compiere.model.I_C_OrderLine> COLUMN_C_OrderLine_ID = new ModelColumn<>(I_M_InOutLine.class, "C_OrderLine_ID", org.compiere.model.I_C_OrderLine.class);
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

	ModelColumn<I_M_InOutLine, org.compiere.model.I_C_Order> COLUMN_C_OrderSO_ID = new ModelColumn<>(I_M_InOutLine.class, "C_OrderSO_ID", org.compiere.model.I_C_Order.class);
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

	ModelColumn<I_M_InOutLine, org.compiere.model.I_C_ProjectPhase> COLUMN_C_ProjectPhase_ID = new ModelColumn<>(I_M_InOutLine.class, "C_ProjectPhase_ID", org.compiere.model.I_C_ProjectPhase.class);
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

	ModelColumn<I_M_InOutLine, org.compiere.model.I_C_ProjectTask> COLUMN_C_ProjectTask_ID = new ModelColumn<>(I_M_InOutLine.class, "C_ProjectTask_ID", org.compiere.model.I_C_ProjectTask.class);
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

	ModelColumn<I_M_InOutLine, Object> COLUMN_Created = new ModelColumn<>(I_M_InOutLine.class, "Created", null);
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
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_UOM_ID (int C_UOM_ID);

	/**
	 * Get UOM.
	 * Unit of Measure
	 *
	 * <br>Type: Table
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
	void setDescription (@Nullable java.lang.String Description);

	/**
	 * Get Description.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDescription();

	ModelColumn<I_M_InOutLine, Object> COLUMN_Description = new ModelColumn<>(I_M_InOutLine.class, "Description", null);
	String COLUMNNAME_Description = "Description";

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

	ModelColumn<I_M_InOutLine, org.compiere.model.I_C_Year> COLUMN_Harvesting_Year_ID = new ModelColumn<>(I_M_InOutLine.class, "Harvesting_Year_ID", org.compiere.model.I_C_Year.class);
	String COLUMNNAME_Harvesting_Year_ID = "Harvesting_Year_ID";

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

	ModelColumn<I_M_InOutLine, Object> COLUMN_IsActive = new ModelColumn<>(I_M_InOutLine.class, "IsActive", null);
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

	ModelColumn<I_M_InOutLine, Object> COLUMN_IsDescription = new ModelColumn<>(I_M_InOutLine.class, "IsDescription", null);
	String COLUMNNAME_IsDescription = "IsDescription";

	/**
	 * Set Invoiced.
	 * Is this invoiced?
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsInvoiced (boolean IsInvoiced);

	/**
	 * Get Invoiced.
	 * Is this invoiced?
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isInvoiced();

	ModelColumn<I_M_InOutLine, Object> COLUMN_IsInvoiced = new ModelColumn<>(I_M_InOutLine.class, "IsInvoiced", null);
	String COLUMNNAME_IsInvoiced = "IsInvoiced";

	/**
	 * Set Warranty Case.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsWarrantyCase (boolean IsWarrantyCase);

	/**
	 * Get Warranty Case.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isWarrantyCase();

	ModelColumn<I_M_InOutLine, Object> COLUMN_IsWarrantyCase = new ModelColumn<>(I_M_InOutLine.class, "IsWarrantyCase", null);
	String COLUMNNAME_IsWarrantyCase = "IsWarrantyCase";

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

	ModelColumn<I_M_InOutLine, Object> COLUMN_Line = new ModelColumn<>(I_M_InOutLine.class, "Line", null);
	String COLUMNNAME_Line = "Line";

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

	ModelColumn<I_M_InOutLine, org.compiere.model.I_M_AttributeSetInstance> COLUMN_M_AttributeSetInstance_ID = new ModelColumn<>(I_M_InOutLine.class, "M_AttributeSetInstance_ID", org.compiere.model.I_M_AttributeSetInstance.class);
	String COLUMNNAME_M_AttributeSetInstance_ID = "M_AttributeSetInstance_ID";

	/**
	 * Set Shipment/ Receipt.
	 * Material Shipment Document
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_InOut_ID (int M_InOut_ID);

	/**
	 * Get Shipment/ Receipt.
	 * Material Shipment Document
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_InOut_ID();

	org.compiere.model.I_M_InOut getM_InOut();

	void setM_InOut(org.compiere.model.I_M_InOut M_InOut);

	ModelColumn<I_M_InOutLine, org.compiere.model.I_M_InOut> COLUMN_M_InOut_ID = new ModelColumn<>(I_M_InOutLine.class, "M_InOut_ID", org.compiere.model.I_M_InOut.class);
	String COLUMNNAME_M_InOut_ID = "M_InOut_ID";

	/**
	 * Set Receipt Line.
	 * Line on Receipt document
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_InOutLine_ID (int M_InOutLine_ID);

	/**
	 * Get Receipt Line.
	 * Line on Receipt document
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_InOutLine_ID();

	ModelColumn<I_M_InOutLine, Object> COLUMN_M_InOutLine_ID = new ModelColumn<>(I_M_InOutLine.class, "M_InOutLine_ID", null);
	String COLUMNNAME_M_InOutLine_ID = "M_InOutLine_ID";

	/**
	 * Set Locator.
	 * Warehouse Locator
	 *
	 * <br>Type: Locator
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_Locator_ID (int M_Locator_ID);

	/**
	 * Get Locator.
	 * Warehouse Locator
	 *
	 * <br>Type: Locator
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_Locator_ID();

	String COLUMNNAME_M_Locator_ID = "M_Locator_ID";

	/**
	 * Set Qty.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setMovementQty (BigDecimal MovementQty);

	/**
	 * Get Qty.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getMovementQty();

	ModelColumn<I_M_InOutLine, Object> COLUMN_MovementQty = new ModelColumn<>(I_M_InOutLine.class, "MovementQty", null);
	String COLUMNNAME_MovementQty = "MovementQty";

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

	ModelColumn<I_M_InOutLine, org.compiere.model.I_M_RMALine> COLUMN_M_RMALine_ID = new ModelColumn<>(I_M_InOutLine.class, "M_RMALine_ID", org.compiere.model.I_M_RMALine.class);
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

	ModelColumn<I_M_InOutLine, org.compiere.model.I_M_SectionCode> COLUMN_M_SectionCode_ID = new ModelColumn<>(I_M_InOutLine.class, "M_SectionCode_ID", org.compiere.model.I_M_SectionCode.class);
	String COLUMNNAME_M_SectionCode_ID = "M_SectionCode_ID";

	/**
	 * Set Picked Quantity.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPickedQty (@Nullable BigDecimal PickedQty);

	/**
	 * Get Picked Quantity.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getPickedQty();

	ModelColumn<I_M_InOutLine, Object> COLUMN_PickedQty = new ModelColumn<>(I_M_InOutLine.class, "PickedQty", null);
	String COLUMNNAME_PickedQty = "PickedQty";

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

	ModelColumn<I_M_InOutLine, Object> COLUMN_Processed = new ModelColumn<>(I_M_InOutLine.class, "Processed", null);
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

	ModelColumn<I_M_InOutLine, Object> COLUMN_ProductDescription = new ModelColumn<>(I_M_InOutLine.class, "ProductDescription", null);
	String COLUMNNAME_ProductDescription = "ProductDescription";

	/**
	 * Set Delivered catch.
	 * Actually delivered quantity
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQtyDeliveredCatch (@Nullable BigDecimal QtyDeliveredCatch);

	/**
	 * Get Delivered catch.
	 * Actually delivered quantity
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyDeliveredCatch();

	ModelColumn<I_M_InOutLine, Object> COLUMN_QtyDeliveredCatch = new ModelColumn<>(I_M_InOutLine.class, "QtyDeliveredCatch", null);
	String COLUMNNAME_QtyDeliveredCatch = "QtyDeliveredCatch";

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

	ModelColumn<I_M_InOutLine, Object> COLUMN_QtyEntered = new ModelColumn<>(I_M_InOutLine.class, "QtyEntered", null);
	String COLUMNNAME_QtyEntered = "QtyEntered";

	/**
	 * Set Referenced Shipment Line.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setRef_InOutLine_ID (int Ref_InOutLine_ID);

	/**
	 * Get Referenced Shipment Line.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getRef_InOutLine_ID();

	ModelColumn<I_M_InOutLine, Object> COLUMN_Ref_InOutLine_ID = new ModelColumn<>(I_M_InOutLine.class, "Ref_InOutLine_ID", null);
	String COLUMNNAME_Ref_InOutLine_ID = "Ref_InOutLine_ID";

	/**
	 * Set Reverse Line.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setReversalLine_ID (int ReversalLine_ID);

	/**
	 * Get Reverse Line.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getReversalLine_ID();

	@Nullable org.compiere.model.I_M_InOutLine getReversalLine();

	void setReversalLine(@Nullable org.compiere.model.I_M_InOutLine ReversalLine);

	ModelColumn<I_M_InOutLine, org.compiere.model.I_M_InOutLine> COLUMN_ReversalLine_ID = new ModelColumn<>(I_M_InOutLine.class, "ReversalLine_ID", org.compiere.model.I_M_InOutLine.class);
	String COLUMNNAME_ReversalLine_ID = "ReversalLine_ID";

	/**
	 * Set Qty Scrapped.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setScrappedQty (@Nullable BigDecimal ScrappedQty);

	/**
	 * Get Qty Scrapped.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getScrappedQty();

	ModelColumn<I_M_InOutLine, Object> COLUMN_ScrappedQty = new ModelColumn<>(I_M_InOutLine.class, "ScrappedQty", null);
	String COLUMNNAME_ScrappedQty = "ScrappedQty";

	/**
	 * Set Target Qty.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setTargetQty (@Nullable BigDecimal TargetQty);

	/**
	 * Get Target Qty.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getTargetQty();

	ModelColumn<I_M_InOutLine, Object> COLUMN_TargetQty = new ModelColumn<>(I_M_InOutLine.class, "TargetQty", null);
	String COLUMNNAME_TargetQty = "TargetQty";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_M_InOutLine, Object> COLUMN_Updated = new ModelColumn<>(I_M_InOutLine.class, "Updated", null);
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

	ModelColumn<I_M_InOutLine, Object> COLUMN_UserElementNumber1 = new ModelColumn<>(I_M_InOutLine.class, "UserElementNumber1", null);
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

	ModelColumn<I_M_InOutLine, Object> COLUMN_UserElementNumber2 = new ModelColumn<>(I_M_InOutLine.class, "UserElementNumber2", null);
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

	ModelColumn<I_M_InOutLine, Object> COLUMN_UserElementString1 = new ModelColumn<>(I_M_InOutLine.class, "UserElementString1", null);
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

	ModelColumn<I_M_InOutLine, Object> COLUMN_UserElementString2 = new ModelColumn<>(I_M_InOutLine.class, "UserElementString2", null);
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

	ModelColumn<I_M_InOutLine, Object> COLUMN_UserElementString3 = new ModelColumn<>(I_M_InOutLine.class, "UserElementString3", null);
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

	ModelColumn<I_M_InOutLine, Object> COLUMN_UserElementString4 = new ModelColumn<>(I_M_InOutLine.class, "UserElementString4", null);
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

	ModelColumn<I_M_InOutLine, Object> COLUMN_UserElementString5 = new ModelColumn<>(I_M_InOutLine.class, "UserElementString5", null);
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

	ModelColumn<I_M_InOutLine, Object> COLUMN_UserElementString6 = new ModelColumn<>(I_M_InOutLine.class, "UserElementString6", null);
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

	ModelColumn<I_M_InOutLine, Object> COLUMN_UserElementString7 = new ModelColumn<>(I_M_InOutLine.class, "UserElementString7", null);
	String COLUMNNAME_UserElementString7 = "UserElementString7";
}
