package org.compiere.model;

import java.math.BigDecimal;
import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for C_ProjectTask
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_C_ProjectTask 
{

	String Table_Name = "C_ProjectTask";

//	/** AD_Table_ID=584 */
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
	 * Set Project Phase.
	 * Phase of a Project
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_ProjectPhase_ID (int C_ProjectPhase_ID);

	/**
	 * Get Project Phase.
	 * Phase of a Project
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_ProjectPhase_ID();

	org.compiere.model.I_C_ProjectPhase getC_ProjectPhase();

	void setC_ProjectPhase(org.compiere.model.I_C_ProjectPhase C_ProjectPhase);

	ModelColumn<I_C_ProjectTask, org.compiere.model.I_C_ProjectPhase> COLUMN_C_ProjectPhase_ID = new ModelColumn<>(I_C_ProjectTask.class, "C_ProjectPhase_ID", org.compiere.model.I_C_ProjectPhase.class);
	String COLUMNNAME_C_ProjectPhase_ID = "C_ProjectPhase_ID";

	/**
	 * Set Project Task.
	 * Actual Project Task in a Phase
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_ProjectTask_ID (int C_ProjectTask_ID);

	/**
	 * Get Project Task.
	 * Actual Project Task in a Phase
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_ProjectTask_ID();

	ModelColumn<I_C_ProjectTask, Object> COLUMN_C_ProjectTask_ID = new ModelColumn<>(I_C_ProjectTask.class, "C_ProjectTask_ID", null);
	String COLUMNNAME_C_ProjectTask_ID = "C_ProjectTask_ID";

	/**
	 * Set Standard-Aufgabe.
	 * Standard Project Type Task
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Task_ID (int C_Task_ID);

	/**
	 * Get Standard-Aufgabe.
	 * Standard Project Type Task
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Task_ID();

	@Nullable org.compiere.model.I_C_Task getC_Task();

	void setC_Task(@Nullable org.compiere.model.I_C_Task C_Task);

	ModelColumn<I_C_ProjectTask, org.compiere.model.I_C_Task> COLUMN_C_Task_ID = new ModelColumn<>(I_C_ProjectTask.class, "C_Task_ID", org.compiere.model.I_C_Task.class);
	String COLUMNNAME_C_Task_ID = "C_Task_ID";

	/**
	 * Set Committed Amount.
	 * The (legal) commitment amount
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setCommittedAmt (BigDecimal CommittedAmt);

	/**
	 * Get Committed Amount.
	 * The (legal) commitment amount
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getCommittedAmt();

	ModelColumn<I_C_ProjectTask, Object> COLUMN_CommittedAmt = new ModelColumn<>(I_C_ProjectTask.class, "CommittedAmt", null);
	String COLUMNNAME_CommittedAmt = "CommittedAmt";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_C_ProjectTask, Object> COLUMN_Created = new ModelColumn<>(I_C_ProjectTask.class, "Created", null);
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

	ModelColumn<I_C_ProjectTask, Object> COLUMN_Description = new ModelColumn<>(I_C_ProjectTask.class, "Description", null);
	String COLUMNNAME_Description = "Description";

	/**
	 * Set Help.
	 * Comment or Hint
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setHelp (@Nullable java.lang.String Help);

	/**
	 * Get Help.
	 * Comment or Hint
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getHelp();

	ModelColumn<I_C_ProjectTask, Object> COLUMN_Help = new ModelColumn<>(I_C_ProjectTask.class, "Help", null);
	String COLUMNNAME_Help = "Help";

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

	ModelColumn<I_C_ProjectTask, Object> COLUMN_IsActive = new ModelColumn<>(I_C_ProjectTask.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

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

	ModelColumn<I_C_ProjectTask, Object> COLUMN_Name = new ModelColumn<>(I_C_ProjectTask.class, "Name", null);
	String COLUMNNAME_Name = "Name";

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

	ModelColumn<I_C_ProjectTask, Object> COLUMN_PlannedAmt = new ModelColumn<>(I_C_ProjectTask.class, "PlannedAmt", null);
	String COLUMNNAME_PlannedAmt = "PlannedAmt";

	/**
	 * Set Rechnungsstellung.
	 * Invoice Rule for the project
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setProjInvoiceRule (java.lang.String ProjInvoiceRule);

	/**
	 * Get Rechnungsstellung.
	 * Invoice Rule for the project
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getProjInvoiceRule();

	ModelColumn<I_C_ProjectTask, Object> COLUMN_ProjInvoiceRule = new ModelColumn<>(I_C_ProjectTask.class, "ProjInvoiceRule", null);
	String COLUMNNAME_ProjInvoiceRule = "ProjInvoiceRule";

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

	ModelColumn<I_C_ProjectTask, Object> COLUMN_Qty = new ModelColumn<>(I_C_ProjectTask.class, "Qty", null);
	String COLUMNNAME_Qty = "Qty";

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

	ModelColumn<I_C_ProjectTask, Object> COLUMN_SeqNo = new ModelColumn<>(I_C_ProjectTask.class, "SeqNo", null);
	String COLUMNNAME_SeqNo = "SeqNo";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_C_ProjectTask, Object> COLUMN_Updated = new ModelColumn<>(I_C_ProjectTask.class, "Updated", null);
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
