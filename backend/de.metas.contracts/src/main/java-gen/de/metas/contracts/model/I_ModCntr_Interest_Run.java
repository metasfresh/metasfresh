package de.metas.contracts.model;

import org.adempiere.model.ModelColumn;

import java.math.BigDecimal;

/** Generated Interface for ModCntr_Interest_Run
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_ModCntr_Interest_Run 
{

	String Table_Name = "ModCntr_Interest_Run";

//	/** AD_Table_ID=542409 */
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
	 * Set Billing Date.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setBillingDate (java.sql.Timestamp BillingDate);

	/**
	 * Get Billing Date.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getBillingDate();

	ModelColumn<I_ModCntr_Interest_Run, Object> COLUMN_BillingDate = new ModelColumn<>(I_ModCntr_Interest_Run.class, "BillingDate", null);
	String COLUMNNAME_BillingDate = "BillingDate";

	/**
	 * Set Currency.
	 * The Currency for this record
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Currency_ID (int C_Currency_ID);

	/**
	 * Get Currency.
	 * The Currency for this record
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Currency_ID();

	String COLUMNNAME_C_Currency_ID = "C_Currency_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_ModCntr_Interest_Run, Object> COLUMN_Created = new ModelColumn<>(I_ModCntr_Interest_Run.class, "Created", null);
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
	 * Set Interim Date.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setInterimDate (java.sql.Timestamp InterimDate);

	/**
	 * Get Interim Date.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getInterimDate();

	ModelColumn<I_ModCntr_Interest_Run, Object> COLUMN_InterimDate = new ModelColumn<>(I_ModCntr_Interest_Run.class, "InterimDate", null);
	String COLUMNNAME_InterimDate = "InterimDate";

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

	ModelColumn<I_ModCntr_Interest_Run, Object> COLUMN_IsActive = new ModelColumn<>(I_ModCntr_Interest_Run.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Zinsberechnungslauf.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setModCntr_Interest_Run_ID (int ModCntr_Interest_Run_ID);

	/**
	 * Get Zinsberechnungslauf.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getModCntr_Interest_Run_ID();

	ModelColumn<I_ModCntr_Interest_Run, Object> COLUMN_ModCntr_Interest_Run_ID = new ModelColumn<>(I_ModCntr_Interest_Run.class, "ModCntr_Interest_Run_ID", null);
	String COLUMNNAME_ModCntr_Interest_Run_ID = "ModCntr_Interest_Run_ID";

	/**
	 * Set Invoice Group.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setModCntr_InvoicingGroup_ID (int ModCntr_InvoicingGroup_ID);

	/**
	 * Get Invoice Group.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getModCntr_InvoicingGroup_ID();

	I_ModCntr_InvoicingGroup getModCntr_InvoicingGroup();

	void setModCntr_InvoicingGroup(I_ModCntr_InvoicingGroup ModCntr_InvoicingGroup);

	ModelColumn<I_ModCntr_Interest_Run, I_ModCntr_InvoicingGroup> COLUMN_ModCntr_InvoicingGroup_ID = new ModelColumn<>(I_ModCntr_Interest_Run.class, "ModCntr_InvoicingGroup_ID", I_ModCntr_InvoicingGroup.class);
	String COLUMNNAME_ModCntr_InvoicingGroup_ID = "ModCntr_InvoicingGroup_ID";

	/**
	 * Set Total interest.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setTotalInterest (BigDecimal TotalInterest);

	/**
	 * Get Total interest.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getTotalInterest();

	ModelColumn<I_ModCntr_Interest_Run, Object> COLUMN_TotalInterest = new ModelColumn<>(I_ModCntr_Interest_Run.class, "TotalInterest", null);
	String COLUMNNAME_TotalInterest = "TotalInterest";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_ModCntr_Interest_Run, Object> COLUMN_Updated = new ModelColumn<>(I_ModCntr_Interest_Run.class, "Updated", null);
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
