package de.metas.dunning.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;
import java.math.BigDecimal;

/** Generated Interface for C_Dunning_Candidate
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_C_Dunning_Candidate 
{

	String Table_Name = "C_Dunning_Candidate";

//	/** AD_Table_ID=540396 */
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
	 * Set Table.
	 * Database Table information
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_Table_ID (int AD_Table_ID);

	/**
	 * Get Table.
	 * Database Table information
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_Table_ID();

	String COLUMNNAME_AD_Table_ID = "AD_Table_ID";

	/**
	 * Set Business Partner.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_BPartner_ID (int C_BPartner_ID);

	/**
	 * Get Business Partner.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_BPartner_ID();

	String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/**
	 * Set Location.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_BPartner_Location_ID (int C_BPartner_Location_ID);

	/**
	 * Get Location.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_BPartner_Location_ID();

	String COLUMNNAME_C_BPartner_Location_ID = "C_BPartner_Location_ID";

	/**
	 * Set Currency.
	 * The Currency for this record
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Currency_ID (int C_Currency_ID);

	/**
	 * Get Currency.
	 * The Currency for this record
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Currency_ID();

	String COLUMNNAME_C_Currency_ID = "C_Currency_ID";

	/**
	 * Set Mahnungsdisposition.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Dunning_Candidate_ID (int C_Dunning_Candidate_ID);

	/**
	 * Get Mahnungsdisposition.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Dunning_Candidate_ID();

	ModelColumn<I_C_Dunning_Candidate, Object> COLUMN_C_Dunning_Candidate_ID = new ModelColumn<>(I_C_Dunning_Candidate.class, "C_Dunning_Candidate_ID", null);
	String COLUMNNAME_C_Dunning_Candidate_ID = "C_Dunning_Candidate_ID";

	/**
	 * Set Mahnkontakt.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Dunning_Contact_ID (int C_Dunning_Contact_ID);

	/**
	 * Get Mahnkontakt.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Dunning_Contact_ID();

	String COLUMNNAME_C_Dunning_Contact_ID = "C_Dunning_Contact_ID";

	/**
	 * Set Dunning Level.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_DunningLevel_ID (int C_DunningLevel_ID);

	/**
	 * Get Dunning Level.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_DunningLevel_ID();

	org.compiere.model.I_C_DunningLevel getC_DunningLevel();

	void setC_DunningLevel(org.compiere.model.I_C_DunningLevel C_DunningLevel);

	ModelColumn<I_C_Dunning_Candidate, org.compiere.model.I_C_DunningLevel> COLUMN_C_DunningLevel_ID = new ModelColumn<>(I_C_Dunning_Candidate.class, "C_DunningLevel_ID", org.compiere.model.I_C_DunningLevel.class);
	String COLUMNNAME_C_DunningLevel_ID = "C_DunningLevel_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_C_Dunning_Candidate, Object> COLUMN_Created = new ModelColumn<>(I_C_Dunning_Candidate.class, "Created", null);
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
	 * Set Days Due.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDaysDue (int DaysDue);

	/**
	 * Get Days Due.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getDaysDue();

	ModelColumn<I_C_Dunning_Candidate, Object> COLUMN_DaysDue = new ModelColumn<>(I_C_Dunning_Candidate.class, "DaysDue", null);
	String COLUMNNAME_DaysDue = "DaysDue";

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

	ModelColumn<I_C_Dunning_Candidate, Object> COLUMN_DocumentNo = new ModelColumn<>(I_C_Dunning_Candidate.class, "DocumentNo", null);
	String COLUMNNAME_DocumentNo = "DocumentNo";

	/**
	 * Set Due Date.
	 * Date when the payment is due
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDueDate (java.sql.Timestamp DueDate);

	/**
	 * Get Due Date.
	 * Date when the payment is due
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getDueDate();

	ModelColumn<I_C_Dunning_Candidate, Object> COLUMN_DueDate = new ModelColumn<>(I_C_Dunning_Candidate.class, "DueDate", null);
	String COLUMNNAME_DueDate = "DueDate";

	/**
	 * Set Dunning Date.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDunningDate (java.sql.Timestamp DunningDate);

	/**
	 * Get Dunning Date.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getDunningDate();

	ModelColumn<I_C_Dunning_Candidate, Object> COLUMN_DunningDate = new ModelColumn<>(I_C_Dunning_Candidate.class, "DunningDate", null);
	String COLUMNNAME_DunningDate = "DunningDate";

	/**
	 * Set Dunning Date Effective.
	 * Effective Date of Dunning
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDunningDateEffective (@Nullable java.sql.Timestamp DunningDateEffective);

	/**
	 * Get Dunning Date Effective.
	 * Effective Date of Dunning
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getDunningDateEffective();

	ModelColumn<I_C_Dunning_Candidate, Object> COLUMN_DunningDateEffective = new ModelColumn<>(I_C_Dunning_Candidate.class, "DunningDateEffective", null);
	String COLUMNNAME_DunningDateEffective = "DunningDateEffective";

	/**
	 * Set Dunning Grace Date.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDunningGrace (@Nullable java.sql.Timestamp DunningGrace);

	/**
	 * Get Dunning Grace Date.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getDunningGrace();

	ModelColumn<I_C_Dunning_Candidate, Object> COLUMN_DunningGrace = new ModelColumn<>(I_C_Dunning_Candidate.class, "DunningGrace", null);
	String COLUMNNAME_DunningGrace = "DunningGrace";

	/**
	 * Set Dunning Interest Amount.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDunningInterestAmt (@Nullable BigDecimal DunningInterestAmt);

	/**
	 * Get Dunning Interest Amount.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getDunningInterestAmt();

	ModelColumn<I_C_Dunning_Candidate, Object> COLUMN_DunningInterestAmt = new ModelColumn<>(I_C_Dunning_Candidate.class, "DunningInterestAmt", null);
	String COLUMNNAME_DunningInterestAmt = "DunningInterestAmt";

	/**
	 * Set Fee Amount.
	 * Fee amount in invoice currency
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setFeeAmt (@Nullable BigDecimal FeeAmt);

	/**
	 * Get Fee Amount.
	 * Fee amount in invoice currency
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getFeeAmt();

	ModelColumn<I_C_Dunning_Candidate, Object> COLUMN_FeeAmt = new ModelColumn<>(I_C_Dunning_Candidate.class, "FeeAmt", null);
	String COLUMNNAME_FeeAmt = "FeeAmt";

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

	ModelColumn<I_C_Dunning_Candidate, Object> COLUMN_IsActive = new ModelColumn<>(I_C_Dunning_Candidate.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Dunning processed.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIsDunningDocProcessed (boolean IsDunningDocProcessed);

	/**
	 * Get Dunning processed.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isDunningDocProcessed();

	ModelColumn<I_C_Dunning_Candidate, Object> COLUMN_IsDunningDocProcessed = new ModelColumn<>(I_C_Dunning_Candidate.class, "IsDunningDocProcessed", null);
	String COLUMNNAME_IsDunningDocProcessed = "IsDunningDocProcessed";

	/**
	 * Set Zu aktualisieren.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setIsStaled (boolean IsStaled);

	/**
	 * Get Zu aktualisieren.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	boolean isStaled();

	ModelColumn<I_C_Dunning_Candidate, Object> COLUMN_IsStaled = new ModelColumn<>(I_C_Dunning_Candidate.class, "IsStaled", null);
	String COLUMNNAME_IsStaled = "IsStaled";

	/**
	 * Set Massenaustritt.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsWriteOff (boolean IsWriteOff);

	/**
	 * Get Massenaustritt.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isWriteOff();

	ModelColumn<I_C_Dunning_Candidate, Object> COLUMN_IsWriteOff = new ModelColumn<>(I_C_Dunning_Candidate.class, "IsWriteOff", null);
	String COLUMNNAME_IsWriteOff = "IsWriteOff";

	/**
	 * Set Open Amount.
	 * Open item amount
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setOpenAmt (@Nullable BigDecimal OpenAmt);

	/**
	 * Get Open Amount.
	 * Open item amount
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getOpenAmt();

	ModelColumn<I_C_Dunning_Candidate, Object> COLUMN_OpenAmt = new ModelColumn<>(I_C_Dunning_Candidate.class, "OpenAmt", null);
	String COLUMNNAME_OpenAmt = "OpenAmt";

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

	ModelColumn<I_C_Dunning_Candidate, Object> COLUMN_POReference = new ModelColumn<>(I_C_Dunning_Candidate.class, "POReference", null);
	String COLUMNNAME_POReference = "POReference";

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

	ModelColumn<I_C_Dunning_Candidate, Object> COLUMN_Processed = new ModelColumn<>(I_C_Dunning_Candidate.class, "Processed", null);
	String COLUMNNAME_Processed = "Processed";

	/**
	 * Set Record ID.
	 * Direct internal record ID
	 *
	 * <br>Type: Button
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setRecord_ID (int Record_ID);

	/**
	 * Get Record ID.
	 * Direct internal record ID
	 *
	 * <br>Type: Button
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getRecord_ID();

	ModelColumn<I_C_Dunning_Candidate, Object> COLUMN_Record_ID = new ModelColumn<>(I_C_Dunning_Candidate.class, "Record_ID", null);
	String COLUMNNAME_Record_ID = "Record_ID";

	/**
	 * Set Total Amount.
	 * Total Amount
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setTotalAmt (@Nullable BigDecimal TotalAmt);

	/**
	 * Get Total Amount.
	 * Total Amount
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getTotalAmt();

	ModelColumn<I_C_Dunning_Candidate, Object> COLUMN_TotalAmt = new ModelColumn<>(I_C_Dunning_Candidate.class, "TotalAmt", null);
	String COLUMNNAME_TotalAmt = "TotalAmt";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_C_Dunning_Candidate, Object> COLUMN_Updated = new ModelColumn<>(I_C_Dunning_Candidate.class, "Updated", null);
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
