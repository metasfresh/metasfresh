package org.compiere.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;

/** Generated Interface for C_Invoice_Verification_Run
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_C_Invoice_Verification_Run 
{

	String Table_Name = "C_Invoice_Verification_Run";

//	/** AD_Table_ID=541664 */
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
	 * Set Process Instance.
	 * Instance of a Process
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_PInstance_ID (int AD_PInstance_ID);

	/**
	 * Get Process Instance.
	 * Instance of a Process
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_PInstance_ID();

	@Nullable org.compiere.model.I_AD_PInstance getAD_PInstance();

	void setAD_PInstance(@Nullable org.compiere.model.I_AD_PInstance AD_PInstance);

	ModelColumn<I_C_Invoice_Verification_Run, org.compiere.model.I_AD_PInstance> COLUMN_AD_PInstance_ID = new ModelColumn<>(I_C_Invoice_Verification_Run.class, "AD_PInstance_ID", org.compiere.model.I_AD_PInstance.class);
	String COLUMNNAME_AD_PInstance_ID = "AD_PInstance_ID";

	/**
	 * Set Invoice Verification Run.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Invoice_Verification_Run_ID (int C_Invoice_Verification_Run_ID);

	/**
	 * Get Invoice Verification Run.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Invoice_Verification_Run_ID();

	ModelColumn<I_C_Invoice_Verification_Run, Object> COLUMN_C_Invoice_Verification_Run_ID = new ModelColumn<>(I_C_Invoice_Verification_Run.class, "C_Invoice_Verification_Run_ID", null);
	String COLUMNNAME_C_Invoice_Verification_Run_ID = "C_Invoice_Verification_Run_ID";

	/**
	 * Set Invoice Verification Set.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Invoice_Verification_Set_ID (int C_Invoice_Verification_Set_ID);

	/**
	 * Get Invoice Verification Set.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Invoice_Verification_Set_ID();

	org.compiere.model.I_C_Invoice_Verification_Set getC_Invoice_Verification_Set();

	void setC_Invoice_Verification_Set(org.compiere.model.I_C_Invoice_Verification_Set C_Invoice_Verification_Set);

	ModelColumn<I_C_Invoice_Verification_Run, org.compiere.model.I_C_Invoice_Verification_Set> COLUMN_C_Invoice_Verification_Set_ID = new ModelColumn<>(I_C_Invoice_Verification_Run.class, "C_Invoice_Verification_Set_ID", org.compiere.model.I_C_Invoice_Verification_Set.class);
	String COLUMNNAME_C_Invoice_Verification_Set_ID = "C_Invoice_Verification_Set_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_C_Invoice_Verification_Run, Object> COLUMN_Created = new ModelColumn<>(I_C_Invoice_Verification_Run.class, "Created", null);
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
	 * Set Date End.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDateEnd (@Nullable java.sql.Timestamp DateEnd);

	/**
	 * Get Date End.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getDateEnd();

	ModelColumn<I_C_Invoice_Verification_Run, Object> COLUMN_DateEnd = new ModelColumn<>(I_C_Invoice_Verification_Run.class, "DateEnd", null);
	String COLUMNNAME_DateEnd = "DateEnd";

	/**
	 * Set Start Date.
	 * Indicate the real date to start
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDateStart (@Nullable java.sql.Timestamp DateStart);

	/**
	 * Get Start Date.
	 * Indicate the real date to start
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getDateStart();

	ModelColumn<I_C_Invoice_Verification_Run, Object> COLUMN_DateStart = new ModelColumn<>(I_C_Invoice_Verification_Run.class, "DateStart", null);
	String COLUMNNAME_DateStart = "DateStart";

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

	ModelColumn<I_C_Invoice_Verification_Run, Object> COLUMN_IsActive = new ModelColumn<>(I_C_Invoice_Verification_Run.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Override Movement Date.
	 * Overriding movement date to be used when the verification set's invoice lines are verified. Leave empty to use the invoice's original date.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setMovementDate_Override (@Nullable java.sql.Timestamp MovementDate_Override);

	/**
	 * Get Override Movement Date.
	 * Overriding movement date to be used when the verification set's invoice lines are verified. Leave empty to use the invoice's original date.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getMovementDate_Override();

	ModelColumn<I_C_Invoice_Verification_Run, Object> COLUMN_MovementDate_Override = new ModelColumn<>(I_C_Invoice_Verification_Run.class, "MovementDate_Override", null);
	String COLUMNNAME_MovementDate_Override = "MovementDate_Override";

	/**
	 * Set Note.
	 * Optional additional user defined information
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setNote (@Nullable java.lang.String Note);

	/**
	 * Get Note.
	 * Optional additional user defined information
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getNote();

	ModelColumn<I_C_Invoice_Verification_Run, Object> COLUMN_Note = new ModelColumn<>(I_C_Invoice_Verification_Run.class, "Note", null);
	String COLUMNNAME_Note = "Note";

	/**
	 * Set Status.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setStatus (java.lang.String Status);

	/**
	 * Get Status.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getStatus();

	ModelColumn<I_C_Invoice_Verification_Run, Object> COLUMN_Status = new ModelColumn<>(I_C_Invoice_Verification_Run.class, "Status", null);
	String COLUMNNAME_Status = "Status";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_C_Invoice_Verification_Run, Object> COLUMN_Updated = new ModelColumn<>(I_C_Invoice_Verification_Run.class, "Updated", null);
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
