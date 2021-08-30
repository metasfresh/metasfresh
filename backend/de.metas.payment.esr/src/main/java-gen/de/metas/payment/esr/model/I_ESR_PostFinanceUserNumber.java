package de.metas.payment.esr.model;

import org.adempiere.model.ModelColumn;

/** Generated Interface for ESR_PostFinanceUserNumber
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_ESR_PostFinanceUserNumber 
{

	String Table_Name = "ESR_PostFinanceUserNumber";

//	/** AD_Table_ID=540860 */
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

	ModelColumn<I_ESR_PostFinanceUserNumber, Object> COLUMN_Created = new ModelColumn<>(I_ESR_PostFinanceUserNumber.class, "Created", null);
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
	 * Set ESR_PostFinanceUserNumber.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setESR_PostFinanceUserNumber_ID (int ESR_PostFinanceUserNumber_ID);

	/**
	 * Get ESR_PostFinanceUserNumber.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getESR_PostFinanceUserNumber_ID();

	ModelColumn<I_ESR_PostFinanceUserNumber, Object> COLUMN_ESR_PostFinanceUserNumber_ID = new ModelColumn<>(I_ESR_PostFinanceUserNumber.class, "ESR_PostFinanceUserNumber_ID", null);
	String COLUMNNAME_ESR_PostFinanceUserNumber_ID = "ESR_PostFinanceUserNumber_ID";

	/**
	 * Set ESR Participant.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setESR_RenderedAccountNo (java.lang.String ESR_RenderedAccountNo);

	/**
	 * Get ESR Participant.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getESR_RenderedAccountNo();

	ModelColumn<I_ESR_PostFinanceUserNumber, Object> COLUMN_ESR_RenderedAccountNo = new ModelColumn<>(I_ESR_PostFinanceUserNumber.class, "ESR_RenderedAccountNo", null);
	String COLUMNNAME_ESR_RenderedAccountNo = "ESR_RenderedAccountNo";

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

	ModelColumn<I_ESR_PostFinanceUserNumber, Object> COLUMN_IsActive = new ModelColumn<>(I_ESR_PostFinanceUserNumber.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_ESR_PostFinanceUserNumber, Object> COLUMN_Updated = new ModelColumn<>(I_ESR_PostFinanceUserNumber.class, "Updated", null);
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
