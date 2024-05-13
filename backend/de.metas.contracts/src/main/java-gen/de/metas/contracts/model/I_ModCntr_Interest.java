package de.metas.contracts.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;
import java.math.BigDecimal;

/** Generated Interface for ModCntr_Interest
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_ModCntr_Interest 
{

	String Table_Name = "ModCntr_Interest";

//	/** AD_Table_ID=542410 */
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
	 * Set Invoice.
	 * Invoice Identifier
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Invoice_ID (int C_Invoice_ID);

	/**
	 * Get Invoice.
	 * Invoice Identifier
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Invoice_ID();

	@Nullable org.compiere.model.I_C_Invoice getC_Invoice();

	void setC_Invoice(@Nullable org.compiere.model.I_C_Invoice C_Invoice);

	ModelColumn<I_ModCntr_Interest, org.compiere.model.I_C_Invoice> COLUMN_C_Invoice_ID = new ModelColumn<>(I_ModCntr_Interest.class, "C_Invoice_ID", org.compiere.model.I_C_Invoice.class);
	String COLUMNNAME_C_Invoice_ID = "C_Invoice_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_ModCntr_Interest, Object> COLUMN_Created = new ModelColumn<>(I_ModCntr_Interest.class, "Created", null);
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
	 * Set Final Interest.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setFinalInterest (@Nullable BigDecimal FinalInterest);

	/**
	 * Get Final Interest.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getFinalInterest();

	ModelColumn<I_ModCntr_Interest, Object> COLUMN_FinalInterest = new ModelColumn<>(I_ModCntr_Interest.class, "FinalInterest", null);
	String COLUMNNAME_FinalInterest = "FinalInterest";

	/**
	 * Set Interest days.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setInterestDays (@Nullable BigDecimal InterestDays);

	/**
	 * Get Interest days.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getInterestDays();

	ModelColumn<I_ModCntr_Interest, Object> COLUMN_InterestDays = new ModelColumn<>(I_ModCntr_Interest.class, "InterestDays", null);
	String COLUMNNAME_InterestDays = "InterestDays";

	/**
	 * Set Interest Score.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setInterestScore (@Nullable BigDecimal InterestScore);

	/**
	 * Get Interest Score.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getInterestScore();

	ModelColumn<I_ModCntr_Interest, Object> COLUMN_InterestScore = new ModelColumn<>(I_ModCntr_Interest.class, "InterestScore", null);
	String COLUMNNAME_InterestScore = "InterestScore";

	/**
	 * Set Interim amount.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setInterimAmt (BigDecimal InterimAmt);

	/**
	 * Get Interim amount.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getInterimAmt();

	ModelColumn<I_ModCntr_Interest, Object> COLUMN_InterimAmt = new ModelColumn<>(I_ModCntr_Interest.class, "InterimAmt", null);
	String COLUMNNAME_InterimAmt = "InterimAmt";

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

	ModelColumn<I_ModCntr_Interest, Object> COLUMN_IsActive = new ModelColumn<>(I_ModCntr_Interest.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Matched amount.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setMatchedAmt (BigDecimal MatchedAmt);

	/**
	 * Get Matched amount.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getMatchedAmt();

	ModelColumn<I_ModCntr_Interest, Object> COLUMN_MatchedAmt = new ModelColumn<>(I_ModCntr_Interest.class, "MatchedAmt", null);
	String COLUMNNAME_MatchedAmt = "MatchedAmt";

	/**
	 * Set Zins.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setModCntr_Interest_ID (int ModCntr_Interest_ID);

	/**
	 * Get Zins.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getModCntr_Interest_ID();

	ModelColumn<I_ModCntr_Interest, Object> COLUMN_ModCntr_Interest_ID = new ModelColumn<>(I_ModCntr_Interest.class, "ModCntr_Interest_ID", null);
	String COLUMNNAME_ModCntr_Interest_ID = "ModCntr_Interest_ID";

	/**
	 * Set Zinsberechnungslauf.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setModCntr_Interest_Run_ID (int ModCntr_Interest_Run_ID);

	/**
	 * Get Zinsberechnungslauf.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getModCntr_Interest_Run_ID();

	de.metas.contracts.model.I_ModCntr_Interest_Run getModCntr_Interest_Run();

	void setModCntr_Interest_Run(de.metas.contracts.model.I_ModCntr_Interest_Run ModCntr_Interest_Run);

	ModelColumn<I_ModCntr_Interest, de.metas.contracts.model.I_ModCntr_Interest_Run> COLUMN_ModCntr_Interest_Run_ID = new ModelColumn<>(I_ModCntr_Interest.class, "ModCntr_Interest_Run_ID", de.metas.contracts.model.I_ModCntr_Interest_Run.class);
	String COLUMNNAME_ModCntr_Interest_Run_ID = "ModCntr_Interest_Run_ID";

	/**
	 * Set Contract Module Log.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setModCntr_Log_ID (int ModCntr_Log_ID);

	/**
	 * Get Contract Module Log.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getModCntr_Log_ID();

	@Nullable de.metas.contracts.model.I_ModCntr_Log getModCntr_Log();

	void setModCntr_Log(@Nullable de.metas.contracts.model.I_ModCntr_Log ModCntr_Log);

	ModelColumn<I_ModCntr_Interest, de.metas.contracts.model.I_ModCntr_Log> COLUMN_ModCntr_Log_ID = new ModelColumn<>(I_ModCntr_Interest.class, "ModCntr_Log_ID", de.metas.contracts.model.I_ModCntr_Log.class);
	String COLUMNNAME_ModCntr_Log_ID = "ModCntr_Log_ID";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_ModCntr_Interest, Object> COLUMN_Updated = new ModelColumn<>(I_ModCntr_Interest.class, "Updated", null);
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
