package org.compiere.model;

import java.math.BigDecimal;
import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for C_ProjectIssue
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_C_ProjectIssue 
{

	String Table_Name = "C_ProjectIssue";

//	/** AD_Table_ID=623 */
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
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_ProjectIssue_ID (int C_ProjectIssue_ID);

	/**
	 * Get Projekt-Problem.
	 * Project Issues (Material, Labor)
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_ProjectIssue_ID();

	ModelColumn<I_C_ProjectIssue, Object> COLUMN_C_ProjectIssue_ID = new ModelColumn<>(I_C_ProjectIssue.class, "C_ProjectIssue_ID", null);
	String COLUMNNAME_C_ProjectIssue_ID = "C_ProjectIssue_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_C_ProjectIssue, Object> COLUMN_Created = new ModelColumn<>(I_C_ProjectIssue.class, "Created", null);
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

	ModelColumn<I_C_ProjectIssue, Object> COLUMN_Description = new ModelColumn<>(I_C_ProjectIssue.class, "Description", null);
	String COLUMNNAME_Description = "Description";

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

	ModelColumn<I_C_ProjectIssue, Object> COLUMN_IsActive = new ModelColumn<>(I_C_ProjectIssue.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

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

	ModelColumn<I_C_ProjectIssue, Object> COLUMN_Line = new ModelColumn<>(I_C_ProjectIssue.class, "Line", null);
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

	ModelColumn<I_C_ProjectIssue, org.compiere.model.I_M_AttributeSetInstance> COLUMN_M_AttributeSetInstance_ID = new ModelColumn<>(I_C_ProjectIssue.class, "M_AttributeSetInstance_ID", org.compiere.model.I_M_AttributeSetInstance.class);
	String COLUMNNAME_M_AttributeSetInstance_ID = "M_AttributeSetInstance_ID";

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

	ModelColumn<I_C_ProjectIssue, org.compiere.model.I_M_InOutLine> COLUMN_M_InOutLine_ID = new ModelColumn<>(I_C_ProjectIssue.class, "M_InOutLine_ID", org.compiere.model.I_M_InOutLine.class);
	String COLUMNNAME_M_InOutLine_ID = "M_InOutLine_ID";

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
	 * Set Bewegungs-Datum.
	 * Datum, an dem eine Produkt in oder aus dem Bestand bewegt wurde
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setMovementDate (java.sql.Timestamp MovementDate);

	/**
	 * Get Bewegungs-Datum.
	 * Datum, an dem eine Produkt in oder aus dem Bestand bewegt wurde
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getMovementDate();

	ModelColumn<I_C_ProjectIssue, Object> COLUMN_MovementDate = new ModelColumn<>(I_C_ProjectIssue.class, "MovementDate", null);
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

	ModelColumn<I_C_ProjectIssue, Object> COLUMN_MovementQty = new ModelColumn<>(I_C_ProjectIssue.class, "MovementQty", null);
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

	ModelColumn<I_C_ProjectIssue, Object> COLUMN_Posted = new ModelColumn<>(I_C_ProjectIssue.class, "Posted", null);
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

	ModelColumn<I_C_ProjectIssue, Object> COLUMN_Processed = new ModelColumn<>(I_C_ProjectIssue.class, "Processed", null);
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

	ModelColumn<I_C_ProjectIssue, Object> COLUMN_Processing = new ModelColumn<>(I_C_ProjectIssue.class, "Processing", null);
	String COLUMNNAME_Processing = "Processing";

	/**
	 * Set Expense Line.
	 * Time and Expense Report Line
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setS_TimeExpenseLine_ID (int S_TimeExpenseLine_ID);

	/**
	 * Get Expense Line.
	 * Time and Expense Report Line
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getS_TimeExpenseLine_ID();

	@Nullable org.compiere.model.I_S_TimeExpenseLine getS_TimeExpenseLine();

	void setS_TimeExpenseLine(@Nullable org.compiere.model.I_S_TimeExpenseLine S_TimeExpenseLine);

	ModelColumn<I_C_ProjectIssue, org.compiere.model.I_S_TimeExpenseLine> COLUMN_S_TimeExpenseLine_ID = new ModelColumn<>(I_C_ProjectIssue.class, "S_TimeExpenseLine_ID", org.compiere.model.I_S_TimeExpenseLine.class);
	String COLUMNNAME_S_TimeExpenseLine_ID = "S_TimeExpenseLine_ID";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_C_ProjectIssue, Object> COLUMN_Updated = new ModelColumn<>(I_C_ProjectIssue.class, "Updated", null);
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
