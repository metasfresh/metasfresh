package org.eevolution.model;

import java.math.BigDecimal;
import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for PP_Cost_Collector
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_PP_Cost_Collector 
{

	String Table_Name = "PP_Cost_Collector";

//	/** AD_Table_ID=53035 */
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
	 * Set Contact.
	 * User within the system - Internal or Business Partner Contact
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_User_ID (int AD_User_ID);

	/**
	 * Get Contact.
	 * User within the system - Internal or Business Partner Contact
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_User_ID();

	String COLUMNNAME_AD_User_ID = "AD_User_ID";

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

	ModelColumn<I_PP_Cost_Collector, org.compiere.model.I_C_Campaign> COLUMN_C_Campaign_ID = new ModelColumn<>(I_PP_Cost_Collector.class, "C_Campaign_ID", org.compiere.model.I_C_Campaign.class);
	String COLUMNNAME_C_Campaign_ID = "C_Campaign_ID";

	/**
	 * Set Document Type.
	 * Document type or rules
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_DocType_ID (int C_DocType_ID);

	/**
	 * Get Document Type.
	 * Document type or rules
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_DocType_ID();

	String COLUMNNAME_C_DocType_ID = "C_DocType_ID";

	/**
	 * Set Zielbelegart.
	 * Target document type for conversing documents
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_DocTypeTarget_ID (int C_DocTypeTarget_ID);

	/**
	 * Get Zielbelegart.
	 * Target document type for conversing documents
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_DocTypeTarget_ID();

	String COLUMNNAME_C_DocTypeTarget_ID = "C_DocTypeTarget_ID";

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
	 * Set UOM.
	 * Unit of Measure
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_UOM_ID (int C_UOM_ID);

	/**
	 * Get UOM.
	 * Unit of Measure
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_UOM_ID();

	String COLUMNNAME_C_UOM_ID = "C_UOM_ID";

	/**
	 * Set Cost Collector Type.
	 * Transaction Type for Manufacturing Management
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setCostCollectorType (java.lang.String CostCollectorType);

	/**
	 * Get Cost Collector Type.
	 * Transaction Type for Manufacturing Management
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getCostCollectorType();

	ModelColumn<I_PP_Cost_Collector, Object> COLUMN_CostCollectorType = new ModelColumn<>(I_PP_Cost_Collector.class, "CostCollectorType", null);
	String COLUMNNAME_CostCollectorType = "CostCollectorType";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_PP_Cost_Collector, Object> COLUMN_Created = new ModelColumn<>(I_PP_Cost_Collector.class, "Created", null);
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
	 * Set Accounting Date.
	 * Accounting Date
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDateAcct (java.sql.Timestamp DateAcct);

	/**
	 * Get Accounting Date.
	 * Accounting Date
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getDateAcct();

	ModelColumn<I_PP_Cost_Collector, Object> COLUMN_DateAcct = new ModelColumn<>(I_PP_Cost_Collector.class, "DateAcct", null);
	String COLUMNNAME_DateAcct = "DateAcct";

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

	ModelColumn<I_PP_Cost_Collector, Object> COLUMN_Description = new ModelColumn<>(I_PP_Cost_Collector.class, "Description", null);
	String COLUMNNAME_Description = "Description";

	/**
	 * Set Process Batch.
	 * The targeted status of the document
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDocAction (@Nullable java.lang.String DocAction);

	/**
	 * Get Process Batch.
	 * The targeted status of the document
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDocAction();

	ModelColumn<I_PP_Cost_Collector, Object> COLUMN_DocAction = new ModelColumn<>(I_PP_Cost_Collector.class, "DocAction", null);
	String COLUMNNAME_DocAction = "DocAction";

	/**
	 * Set Status.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDocStatus (@Nullable java.lang.String DocStatus);

	/**
	 * Get Status.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDocStatus();

	ModelColumn<I_PP_Cost_Collector, Object> COLUMN_DocStatus = new ModelColumn<>(I_PP_Cost_Collector.class, "DocStatus", null);
	String COLUMNNAME_DocStatus = "DocStatus";

	/**
	 * Set Document No.
	 * Document sequence number of the document
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDocumentNo (@Nullable java.lang.String DocumentNo);

	/**
	 * Get Document No.
	 * Document sequence number of the document
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDocumentNo();

	ModelColumn<I_PP_Cost_Collector, Object> COLUMN_DocumentNo = new ModelColumn<>(I_PP_Cost_Collector.class, "DocumentNo", null);
	String COLUMNNAME_DocumentNo = "DocumentNo";

	/**
	 * Set Duration Real.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDurationReal (@Nullable BigDecimal DurationReal);

	/**
	 * Get Duration Real.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getDurationReal();

	ModelColumn<I_PP_Cost_Collector, Object> COLUMN_DurationReal = new ModelColumn<>(I_PP_Cost_Collector.class, "DurationReal", null);
	String COLUMNNAME_DurationReal = "DurationReal";

	/**
	 * Set Duration Unit.
	 * Unit of Duration
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDurationUnit (@Nullable java.lang.String DurationUnit);

	/**
	 * Get Duration Unit.
	 * Unit of Duration
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDurationUnit();

	ModelColumn<I_PP_Cost_Collector, Object> COLUMN_DurationUnit = new ModelColumn<>(I_PP_Cost_Collector.class, "DurationUnit", null);
	String COLUMNNAME_DurationUnit = "DurationUnit";

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

	ModelColumn<I_PP_Cost_Collector, Object> COLUMN_IsActive = new ModelColumn<>(I_PP_Cost_Collector.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Is BatchTime.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIsBatchTime (boolean IsBatchTime);

	/**
	 * Get Is BatchTime.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isBatchTime();

	ModelColumn<I_PP_Cost_Collector, Object> COLUMN_IsBatchTime = new ModelColumn<>(I_PP_Cost_Collector.class, "IsBatchTime", null);
	String COLUMNNAME_IsBatchTime = "IsBatchTime";

	/**
	 * Set Is Subcontracting.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIsSubcontracting (boolean IsSubcontracting);

	/**
	 * Get Is Subcontracting.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isSubcontracting();

	ModelColumn<I_PP_Cost_Collector, Object> COLUMN_IsSubcontracting = new ModelColumn<>(I_PP_Cost_Collector.class, "IsSubcontracting", null);
	String COLUMNNAME_IsSubcontracting = "IsSubcontracting";

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

	ModelColumn<I_PP_Cost_Collector, org.compiere.model.I_M_AttributeSetInstance> COLUMN_M_AttributeSetInstance_ID = new ModelColumn<>(I_PP_Cost_Collector.class, "M_AttributeSetInstance_ID", org.compiere.model.I_M_AttributeSetInstance.class);
	String COLUMNNAME_M_AttributeSetInstance_ID = "M_AttributeSetInstance_ID";

	/**
	 * Set Locator.
	 * Warehouse Locator
	 *
	 * <br>Type: Locator
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_Locator_ID (int M_Locator_ID);

	/**
	 * Get Locator.
	 * Warehouse Locator
	 *
	 * <br>Type: Locator
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_Locator_ID();

	String COLUMNNAME_M_Locator_ID = "M_Locator_ID";

	/**
	 * Set Picking candidate.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_Picking_Candidate_ID (int M_Picking_Candidate_ID);

	/**
	 * Get Picking candidate.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_Picking_Candidate_ID();

	ModelColumn<I_PP_Cost_Collector, Object> COLUMN_M_Picking_Candidate_ID = new ModelColumn<>(I_PP_Cost_Collector.class, "M_Picking_Candidate_ID", null);
	String COLUMNNAME_M_Picking_Candidate_ID = "M_Picking_Candidate_ID";

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
	 * Set Bewegungs-Datum.
	 * Datum, an dem eine Produkt in oder aus dem Bestand bewegt wurde
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setMovementDate (java.sql.Timestamp MovementDate);

	/**
	 * Get Bewegungs-Datum.
	 * Datum, an dem eine Produkt in oder aus dem Bestand bewegt wurde
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getMovementDate();

	ModelColumn<I_PP_Cost_Collector, Object> COLUMN_MovementDate = new ModelColumn<>(I_PP_Cost_Collector.class, "MovementDate", null);
	String COLUMNNAME_MovementDate = "MovementDate";

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

	ModelColumn<I_PP_Cost_Collector, Object> COLUMN_MovementQty = new ModelColumn<>(I_PP_Cost_Collector.class, "MovementQty", null);
	String COLUMNNAME_MovementQty = "MovementQty";

	/**
	 * Set Posting status.
	 * Posting status
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPosted (boolean Posted);

	/**
	 * Get Posting status.
	 * Posting status
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isPosted();

	ModelColumn<I_PP_Cost_Collector, Object> COLUMN_Posted = new ModelColumn<>(I_PP_Cost_Collector.class, "Posted", null);
	String COLUMNNAME_Posted = "Posted";

	/**
	 * Set Posting Error.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPostingError_Issue_ID (int PostingError_Issue_ID);

	/**
	 * Get Posting Error.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getPostingError_Issue_ID();

	String COLUMNNAME_PostingError_Issue_ID = "PostingError_Issue_ID";

	/**
	 * Set Manufacturing Cost Collector.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPP_Cost_Collector_ID (int PP_Cost_Collector_ID);

	/**
	 * Get Manufacturing Cost Collector.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getPP_Cost_Collector_ID();

	ModelColumn<I_PP_Cost_Collector, Object> COLUMN_PP_Cost_Collector_ID = new ModelColumn<>(I_PP_Cost_Collector.class, "PP_Cost_Collector_ID", null);
	String COLUMNNAME_PP_Cost_Collector_ID = "PP_Cost_Collector_ID";

	/**
	 * Set Manufacturing Cost Collector Parent.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPP_Cost_Collector_Parent_ID (int PP_Cost_Collector_Parent_ID);

	/**
	 * Get Manufacturing Cost Collector Parent.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getPP_Cost_Collector_Parent_ID();

	@Nullable org.eevolution.model.I_PP_Cost_Collector getPP_Cost_Collector_Parent();

	void setPP_Cost_Collector_Parent(@Nullable org.eevolution.model.I_PP_Cost_Collector PP_Cost_Collector_Parent);

	ModelColumn<I_PP_Cost_Collector, org.eevolution.model.I_PP_Cost_Collector> COLUMN_PP_Cost_Collector_Parent_ID = new ModelColumn<>(I_PP_Cost_Collector.class, "PP_Cost_Collector_Parent_ID", org.eevolution.model.I_PP_Cost_Collector.class);
	String COLUMNNAME_PP_Cost_Collector_Parent_ID = "PP_Cost_Collector_Parent_ID";

	/**
	 * Set Manufacturing Order BOM Line.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPP_Order_BOMLine_ID (int PP_Order_BOMLine_ID);

	/**
	 * Get Manufacturing Order BOM Line.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getPP_Order_BOMLine_ID();

	@Nullable org.eevolution.model.I_PP_Order_BOMLine getPP_Order_BOMLine();

	void setPP_Order_BOMLine(@Nullable org.eevolution.model.I_PP_Order_BOMLine PP_Order_BOMLine);

	ModelColumn<I_PP_Cost_Collector, org.eevolution.model.I_PP_Order_BOMLine> COLUMN_PP_Order_BOMLine_ID = new ModelColumn<>(I_PP_Cost_Collector.class, "PP_Order_BOMLine_ID", org.eevolution.model.I_PP_Order_BOMLine.class);
	String COLUMNNAME_PP_Order_BOMLine_ID = "PP_Order_BOMLine_ID";

	/**
	 * Set Manufacturing Order.
	 * Manufacturing Order
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPP_Order_ID (int PP_Order_ID);

	/**
	 * Get Manufacturing Order.
	 * Manufacturing Order
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getPP_Order_ID();

	org.eevolution.model.I_PP_Order getPP_Order();

	void setPP_Order(org.eevolution.model.I_PP_Order PP_Order);

	ModelColumn<I_PP_Cost_Collector, org.eevolution.model.I_PP_Order> COLUMN_PP_Order_ID = new ModelColumn<>(I_PP_Cost_Collector.class, "PP_Order_ID", org.eevolution.model.I_PP_Order.class);
	String COLUMNNAME_PP_Order_ID = "PP_Order_ID";

	/**
	 * Set Manufacturing Order Activity.
	 * Workflow Node (activity), step or process
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPP_Order_Node_ID (int PP_Order_Node_ID);

	/**
	 * Get Manufacturing Order Activity.
	 * Workflow Node (activity), step or process
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getPP_Order_Node_ID();

	@Nullable org.eevolution.model.I_PP_Order_Node getPP_Order_Node();

	void setPP_Order_Node(@Nullable org.eevolution.model.I_PP_Order_Node PP_Order_Node);

	ModelColumn<I_PP_Cost_Collector, org.eevolution.model.I_PP_Order_Node> COLUMN_PP_Order_Node_ID = new ModelColumn<>(I_PP_Cost_Collector.class, "PP_Order_Node_ID", org.eevolution.model.I_PP_Order_Node.class);
	String COLUMNNAME_PP_Order_Node_ID = "PP_Order_Node_ID";

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

	ModelColumn<I_PP_Cost_Collector, Object> COLUMN_Processed = new ModelColumn<>(I_PP_Cost_Collector.class, "Processed", null);
	String COLUMNNAME_Processed = "Processed";

	/**
	 * Set Process Now.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setProcessing (boolean Processing);

	/**
	 * Get Process Now.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isProcessing();

	ModelColumn<I_PP_Cost_Collector, Object> COLUMN_Processing = new ModelColumn<>(I_PP_Cost_Collector.class, "Processing", null);
	String COLUMNNAME_Processing = "Processing";

	/**
	 * Set Qty Reject.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQtyReject (@Nullable BigDecimal QtyReject);

	/**
	 * Get Qty Reject.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyReject();

	ModelColumn<I_PP_Cost_Collector, Object> COLUMN_QtyReject = new ModelColumn<>(I_PP_Cost_Collector.class, "QtyReject", null);
	String COLUMNNAME_QtyReject = "QtyReject";

	/**
	 * Set Reversal ID.
	 * ID of document reversal
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setReversal_ID (int Reversal_ID);

	/**
	 * Get Reversal ID.
	 * ID of document reversal
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getReversal_ID();

	@Nullable org.eevolution.model.I_PP_Cost_Collector getReversal();

	void setReversal(@Nullable org.eevolution.model.I_PP_Cost_Collector Reversal);

	ModelColumn<I_PP_Cost_Collector, org.eevolution.model.I_PP_Cost_Collector> COLUMN_Reversal_ID = new ModelColumn<>(I_PP_Cost_Collector.class, "Reversal_ID", org.eevolution.model.I_PP_Cost_Collector.class);
	String COLUMNNAME_Reversal_ID = "Reversal_ID";

	/**
	 * Set Ressource.
	 * Resource
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setS_Resource_ID (int S_Resource_ID);

	/**
	 * Get Ressource.
	 * Resource
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getS_Resource_ID();

	org.compiere.model.I_S_Resource getS_Resource();

	void setS_Resource(org.compiere.model.I_S_Resource S_Resource);

	ModelColumn<I_PP_Cost_Collector, org.compiere.model.I_S_Resource> COLUMN_S_Resource_ID = new ModelColumn<>(I_PP_Cost_Collector.class, "S_Resource_ID", org.compiere.model.I_S_Resource.class);
	String COLUMNNAME_S_Resource_ID = "S_Resource_ID";

	/**
	 * Set Verworfene Menge.
	 * The Quantity scrapped due to QA issues
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setScrappedQty (@Nullable BigDecimal ScrappedQty);

	/**
	 * Get Verworfene Menge.
	 * The Quantity scrapped due to QA issues
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getScrappedQty();

	ModelColumn<I_PP_Cost_Collector, Object> COLUMN_ScrappedQty = new ModelColumn<>(I_PP_Cost_Collector.class, "ScrappedQty", null);
	String COLUMNNAME_ScrappedQty = "ScrappedQty";

	/**
	 * Set Setup Time Real.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setSetupTimeReal (@Nullable BigDecimal SetupTimeReal);

	/**
	 * Get Setup Time Real.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getSetupTimeReal();

	ModelColumn<I_PP_Cost_Collector, Object> COLUMN_SetupTimeReal = new ModelColumn<>(I_PP_Cost_Collector.class, "SetupTimeReal", null);
	String COLUMNNAME_SetupTimeReal = "SetupTimeReal";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_PP_Cost_Collector, Object> COLUMN_Updated = new ModelColumn<>(I_PP_Cost_Collector.class, "Updated", null);
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
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setUser1_ID (int User1_ID);

	/**
	 * Get User List 1.
	 * User defined list element #1
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getUser1_ID();

	String COLUMNNAME_User1_ID = "User1_ID";

	/**
	 * Set User 2.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setUser2_ID (int User2_ID);

	/**
	 * Get User 2.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getUser2_ID();

	String COLUMNNAME_User2_ID = "User2_ID";
}
