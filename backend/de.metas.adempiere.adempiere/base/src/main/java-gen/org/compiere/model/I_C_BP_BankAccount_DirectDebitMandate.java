package org.compiere.model;

import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for C_BP_BankAccount_DirectDebitMandate
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_C_BP_BankAccount_DirectDebitMandate 
{

	String Table_Name = "C_BP_BankAccount_DirectDebitMandate";

//	/** AD_Table_ID=542522 */
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
	 * Set Business Partner.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_BPartner_ID (int C_BPartner_ID);

	/**
	 * Get Business Partner.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_BPartner_ID();

	String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/**
	 * Set Lastschrift Mandat.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_BP_BankAccount_DirectDebitMandate_ID (int C_BP_BankAccount_DirectDebitMandate_ID);

	/**
	 * Get Lastschrift Mandat.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_BP_BankAccount_DirectDebitMandate_ID();

	ModelColumn<I_C_BP_BankAccount_DirectDebitMandate, Object> COLUMN_C_BP_BankAccount_DirectDebitMandate_ID = new ModelColumn<>(I_C_BP_BankAccount_DirectDebitMandate.class, "C_BP_BankAccount_DirectDebitMandate_ID", null);
	String COLUMNNAME_C_BP_BankAccount_DirectDebitMandate_ID = "C_BP_BankAccount_DirectDebitMandate_ID";

	/**
	 * Set Partner Bank Account.
	 * Bank Account of the Business Partner
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_BP_BankAccount_ID (int C_BP_BankAccount_ID);

	/**
	 * Get Partner Bank Account.
	 * Bank Account of the Business Partner
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_BP_BankAccount_ID();

	String COLUMNNAME_C_BP_BankAccount_ID = "C_BP_BankAccount_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_C_BP_BankAccount_DirectDebitMandate, Object> COLUMN_Created = new ModelColumn<>(I_C_BP_BankAccount_DirectDebitMandate.class, "Created", null);
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
	 * Set Last Used.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDateLastUsed (@Nullable java.sql.Timestamp DateLastUsed);

	/**
	 * Get Last Used.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getDateLastUsed();

	ModelColumn<I_C_BP_BankAccount_DirectDebitMandate, Object> COLUMN_DateLastUsed = new ModelColumn<>(I_C_BP_BankAccount_DirectDebitMandate.class, "DateLastUsed", null);
	String COLUMNNAME_DateLastUsed = "DateLastUsed";

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

	ModelColumn<I_C_BP_BankAccount_DirectDebitMandate, Object> COLUMN_IsActive = new ModelColumn<>(I_C_BP_BankAccount_DirectDebitMandate.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Recurring.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsRecurring (boolean IsRecurring);

	/**
	 * Get Recurring.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isRecurring();

	ModelColumn<I_C_BP_BankAccount_DirectDebitMandate, Object> COLUMN_IsRecurring = new ModelColumn<>(I_C_BP_BankAccount_DirectDebitMandate.class, "IsRecurring", null);
	String COLUMNNAME_IsRecurring = "IsRecurring";

	/**
	 * Set Mandate Date.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setMandateDate (java.sql.Timestamp MandateDate);

	/**
	 * Get Mandate Date.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getMandateDate();

	ModelColumn<I_C_BP_BankAccount_DirectDebitMandate, Object> COLUMN_MandateDate = new ModelColumn<>(I_C_BP_BankAccount_DirectDebitMandate.class, "MandateDate", null);
	String COLUMNNAME_MandateDate = "MandateDate";

	/**
	 * Set Mandate Reference.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setMandateReference (java.lang.String MandateReference);

	/**
	 * Get Mandate Reference.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getMandateReference();

	ModelColumn<I_C_BP_BankAccount_DirectDebitMandate, Object> COLUMN_MandateReference = new ModelColumn<>(I_C_BP_BankAccount_DirectDebitMandate.class, "MandateReference", null);
	String COLUMNNAME_MandateReference = "MandateReference";

	/**
	 * Set Mandate Status.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setMandateStatus (java.lang.String MandateStatus);

	/**
	 * Get Mandate Status.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getMandateStatus();

	ModelColumn<I_C_BP_BankAccount_DirectDebitMandate, Object> COLUMN_MandateStatus = new ModelColumn<>(I_C_BP_BankAccount_DirectDebitMandate.class, "MandateStatus", null);
	String COLUMNNAME_MandateStatus = "MandateStatus";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_C_BP_BankAccount_DirectDebitMandate, Object> COLUMN_Updated = new ModelColumn<>(I_C_BP_BankAccount_DirectDebitMandate.class, "Updated", null);
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
