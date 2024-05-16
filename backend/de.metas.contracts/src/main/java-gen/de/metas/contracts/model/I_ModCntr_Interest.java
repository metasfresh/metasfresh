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
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setInterestDays (int InterestDays);

	/**
	 * Get Interest days.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getInterestDays();

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
	 * Set Interim Invoice Log.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setInterimInvoice_ModCntr_Log_ID (int InterimInvoice_ModCntr_Log_ID);

	/**
	 * Get Interim Invoice Log.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getInterimInvoice_ModCntr_Log_ID();

	@Nullable I_ModCntr_Log getInterimInvoice_ModCntr_Log();

	void setInterimInvoice_ModCntr_Log(@Nullable I_ModCntr_Log InterimInvoice_ModCntr_Log);

	ModelColumn<I_ModCntr_Interest, I_ModCntr_Log> COLUMN_InterimInvoice_ModCntr_Log_ID = new ModelColumn<>(I_ModCntr_Interest.class, "InterimInvoice_ModCntr_Log_ID", I_ModCntr_Log.class);
	String COLUMNNAME_InterimInvoice_ModCntr_Log_ID = "InterimInvoice_ModCntr_Log_ID";

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

	I_ModCntr_Interest_Run getModCntr_Interest_Run();

	void setModCntr_Interest_Run(I_ModCntr_Interest_Run ModCntr_Interest_Run);

	ModelColumn<I_ModCntr_Interest, I_ModCntr_Interest_Run> COLUMN_ModCntr_Interest_Run_ID = new ModelColumn<>(I_ModCntr_Interest.class, "ModCntr_Interest_Run_ID", I_ModCntr_Interest_Run.class);
	String COLUMNNAME_ModCntr_Interest_Run_ID = "ModCntr_Interest_Run_ID";

	/**
	 * Set Shipping Notification Log.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setShippingNotification_ModCntr_Log_ID (int ShippingNotification_ModCntr_Log_ID);

	/**
	 * Get Shipping Notification Log.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getShippingNotification_ModCntr_Log_ID();

	I_ModCntr_Log getShippingNotification_ModCntr_Log();

	void setShippingNotification_ModCntr_Log(I_ModCntr_Log ShippingNotification_ModCntr_Log);

	ModelColumn<I_ModCntr_Interest, I_ModCntr_Log> COLUMN_ShippingNotification_ModCntr_Log_ID = new ModelColumn<>(I_ModCntr_Interest.class, "ShippingNotification_ModCntr_Log_ID", I_ModCntr_Log.class);
	String COLUMNNAME_ShippingNotification_ModCntr_Log_ID = "ShippingNotification_ModCntr_Log_ID";

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
