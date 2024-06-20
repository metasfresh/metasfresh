package org.compiere.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;
import java.math.BigDecimal;

/** Generated Interface for M_Inventory
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_M_Inventory 
{

	String Table_Name = "M_Inventory";

//	/** AD_Table_ID=321 */
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
	 * Set Approval Amount.
	 * Document Approval Amount
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setApprovalAmt (@Nullable BigDecimal ApprovalAmt);

	/**
	 * Get Approval Amount.
	 * Document Approval Amount
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getApprovalAmt();

	ModelColumn<I_M_Inventory, Object> COLUMN_ApprovalAmt = new ModelColumn<>(I_M_Inventory.class, "ApprovalAmt", null);
	String COLUMNNAME_ApprovalAmt = "ApprovalAmt";

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

	ModelColumn<I_M_Inventory, org.compiere.model.I_C_Campaign> COLUMN_C_Campaign_ID = new ModelColumn<>(I_M_Inventory.class, "C_Campaign_ID", org.compiere.model.I_C_Campaign.class);
	String COLUMNNAME_C_Campaign_ID = "C_Campaign_ID";

	/**
	 * Set Document Type.
	 * Document type or rules
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_DocType_ID (int C_DocType_ID);

	/**
	 * Get Document Type.
	 * Document type or rules
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_DocType_ID();

	String COLUMNNAME_C_DocType_ID = "C_DocType_ID";

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
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_M_Inventory, Object> COLUMN_Created = new ModelColumn<>(I_M_Inventory.class, "Created", null);
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

	ModelColumn<I_M_Inventory, Object> COLUMN_Description = new ModelColumn<>(I_M_Inventory.class, "Description", null);
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

	ModelColumn<I_M_Inventory, Object> COLUMN_DocAction = new ModelColumn<>(I_M_Inventory.class, "DocAction", null);
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

	ModelColumn<I_M_Inventory, Object> COLUMN_DocStatus = new ModelColumn<>(I_M_Inventory.class, "DocStatus", null);
	String COLUMNNAME_DocStatus = "DocStatus";

	/**
	 * Set Document No.
	 * Document sequence number of the document
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDocumentNo (java.lang.String DocumentNo);

	/**
	 * Get Document No.
	 * Document sequence number of the document
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getDocumentNo();

	ModelColumn<I_M_Inventory, Object> COLUMN_DocumentNo = new ModelColumn<>(I_M_Inventory.class, "DocumentNo", null);
	String COLUMNNAME_DocumentNo = "DocumentNo";

	/**
	 * Set External ID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setExternalId (@Nullable java.lang.String ExternalId);

	/**
	 * Get External ID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getExternalId();

	ModelColumn<I_M_Inventory, Object> COLUMN_ExternalId = new ModelColumn<>(I_M_Inventory.class, "ExternalId", null);
	String COLUMNNAME_ExternalId = "ExternalId";

	/**
	 * Set Create Inventory Count List .
	 * Create Inventory Count List
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setGenerateList (@Nullable java.lang.String GenerateList);

	/**
	 * Get Create Inventory Count List .
	 * Create Inventory Count List
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getGenerateList();

	ModelColumn<I_M_Inventory, Object> COLUMN_GenerateList = new ModelColumn<>(I_M_Inventory.class, "GenerateList", null);
	String COLUMNNAME_GenerateList = "GenerateList";

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

	ModelColumn<I_M_Inventory, Object> COLUMN_IsActive = new ModelColumn<>(I_M_Inventory.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Approved.
	 * Indicates if this document requires approval
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsApproved (boolean IsApproved);

	/**
	 * Get Approved.
	 * Indicates if this document requires approval
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isApproved();

	ModelColumn<I_M_Inventory, Object> COLUMN_IsApproved = new ModelColumn<>(I_M_Inventory.class, "IsApproved", null);
	String COLUMNNAME_IsApproved = "IsApproved";

	/**
	 * Set Phys. Inventory.
	 * Parameters for a Physical Inventory
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_Inventory_ID (int M_Inventory_ID);

	/**
	 * Get Phys. Inventory.
	 * Parameters for a Physical Inventory
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_Inventory_ID();

	ModelColumn<I_M_Inventory, Object> COLUMN_M_Inventory_ID = new ModelColumn<>(I_M_Inventory.class, "M_Inventory_ID", null);
	String COLUMNNAME_M_Inventory_ID = "M_Inventory_ID";

	/**
	 * Set Date.
	 * Date a product was moved in or out of inventory
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setMovementDate (java.sql.Timestamp MovementDate);

	/**
	 * Get Date.
	 * Date a product was moved in or out of inventory
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getMovementDate();

	ModelColumn<I_M_Inventory, Object> COLUMN_MovementDate = new ModelColumn<>(I_M_Inventory.class, "MovementDate", null);
	String COLUMNNAME_MovementDate = "MovementDate";

	/**
	 * Set Perpetual Inventory.
	 * Rules for generating physical inventory
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_PerpetualInv_ID (int M_PerpetualInv_ID);

	/**
	 * Get Perpetual Inventory.
	 * Rules for generating physical inventory
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_PerpetualInv_ID();

	@Nullable org.compiere.model.I_M_PerpetualInv getM_PerpetualInv();

	void setM_PerpetualInv(@Nullable org.compiere.model.I_M_PerpetualInv M_PerpetualInv);

	ModelColumn<I_M_Inventory, org.compiere.model.I_M_PerpetualInv> COLUMN_M_PerpetualInv_ID = new ModelColumn<>(I_M_Inventory.class, "M_PerpetualInv_ID", org.compiere.model.I_M_PerpetualInv.class);
	String COLUMNNAME_M_PerpetualInv_ID = "M_PerpetualInv_ID";

	/**
	 * Set Picking Job.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_Picking_Job_ID (int M_Picking_Job_ID);

	/**
	 * Get Picking Job.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_Picking_Job_ID();

	ModelColumn<I_M_Inventory, Object> COLUMN_M_Picking_Job_ID = new ModelColumn<>(I_M_Inventory.class, "M_Picking_Job_ID", null);
	String COLUMNNAME_M_Picking_Job_ID = "M_Picking_Job_ID";

	/**
	 * Set Warehouse.
	 * Storage Warehouse and Service Point
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_Warehouse_ID (int M_Warehouse_ID);

	/**
	 * Get Warehouse.
	 * Storage Warehouse and Service Point
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_Warehouse_ID();

	String COLUMNNAME_M_Warehouse_ID = "M_Warehouse_ID";

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

	ModelColumn<I_M_Inventory, Object> COLUMN_POReference = new ModelColumn<>(I_M_Inventory.class, "POReference", null);
	String COLUMNNAME_POReference = "POReference";

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

	ModelColumn<I_M_Inventory, Object> COLUMN_Posted = new ModelColumn<>(I_M_Inventory.class, "Posted", null);
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

	ModelColumn<I_M_Inventory, Object> COLUMN_Processed = new ModelColumn<>(I_M_Inventory.class, "Processed", null);
	String COLUMNNAME_Processed = "Processed";

	/**
	 * Set Process Now.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setProcessing (boolean Processing);

	/**
	 * Get Process Now.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isProcessing();

	ModelColumn<I_M_Inventory, Object> COLUMN_Processing = new ModelColumn<>(I_M_Inventory.class, "Processing", null);
	String COLUMNNAME_Processing = "Processing";

	/**
	 * Set Reversal ID.
	 * ID of document reversal
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setReversal_ID (int Reversal_ID);

	/**
	 * Get Reversal ID.
	 * ID of document reversal
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getReversal_ID();

	@Nullable org.compiere.model.I_M_Inventory getReversal();

	void setReversal(@Nullable org.compiere.model.I_M_Inventory Reversal);

	ModelColumn<I_M_Inventory, org.compiere.model.I_M_Inventory> COLUMN_Reversal_ID = new ModelColumn<>(I_M_Inventory.class, "Reversal_ID", org.compiere.model.I_M_Inventory.class);
	String COLUMNNAME_Reversal_ID = "Reversal_ID";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_M_Inventory, Object> COLUMN_Updated = new ModelColumn<>(I_M_Inventory.class, "Updated", null);
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
	 * Set Update Quantity.
	 * The Book Quantity is updated with current book quantity
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setUpdateQty (@Nullable java.lang.String UpdateQty);

	/**
	 * Get Update Quantity.
	 * The Book Quantity is updated with current book quantity
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getUpdateQty();

	ModelColumn<I_M_Inventory, Object> COLUMN_UpdateQty = new ModelColumn<>(I_M_Inventory.class, "UpdateQty", null);
	String COLUMNNAME_UpdateQty = "UpdateQty";

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

	@Nullable org.compiere.model.I_C_ElementValue getUser1();

	void setUser1(@Nullable org.compiere.model.I_C_ElementValue User1);

	ModelColumn<I_M_Inventory, org.compiere.model.I_C_ElementValue> COLUMN_User1_ID = new ModelColumn<>(I_M_Inventory.class, "User1_ID", org.compiere.model.I_C_ElementValue.class);
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

	@Nullable org.compiere.model.I_C_ElementValue getUser2();

	void setUser2(@Nullable org.compiere.model.I_C_ElementValue User2);

	ModelColumn<I_M_Inventory, org.compiere.model.I_C_ElementValue> COLUMN_User2_ID = new ModelColumn<>(I_M_Inventory.class, "User2_ID", org.compiere.model.I_C_ElementValue.class);
	String COLUMNNAME_User2_ID = "User2_ID";
}
