package de.metas.datev.model;

import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for DATEV_Export
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_DATEV_Export 
{

	String Table_Name = "DATEV_Export";

//	/** AD_Table_ID=540934 */
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
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_DATEV_Export, Object> COLUMN_Created = new ModelColumn<>(I_DATEV_Export.class, "Created", null);
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
	 * Set Date Accounted from.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDateAcctFrom (@Nullable java.sql.Timestamp DateAcctFrom);

	/**
	 * Get Date Accounted from.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getDateAcctFrom();

	ModelColumn<I_DATEV_Export, Object> COLUMN_DateAcctFrom = new ModelColumn<>(I_DATEV_Export.class, "DateAcctFrom", null);
	String COLUMNNAME_DateAcctFrom = "DateAcctFrom";

	/**
	 * Set Date Accounted to.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDateAcctTo (java.sql.Timestamp DateAcctTo);

	/**
	 * Get Date Accounted to.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getDateAcctTo();

	ModelColumn<I_DATEV_Export, Object> COLUMN_DateAcctTo = new ModelColumn<>(I_DATEV_Export.class, "DateAcctTo", null);
	String COLUMNNAME_DateAcctTo = "DateAcctTo";

	/**
	 * Set DATEV Export.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDATEV_Export_ID (int DATEV_Export_ID);

	/**
	 * Get DATEV Export.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getDATEV_Export_ID();

	ModelColumn<I_DATEV_Export, Object> COLUMN_DATEV_Export_ID = new ModelColumn<>(I_DATEV_Export.class, "DATEV_Export_ID", null);
	String COLUMNNAME_DATEV_Export_ID = "DATEV_Export_ID";

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

	ModelColumn<I_DATEV_Export, Object> COLUMN_Description = new ModelColumn<>(I_DATEV_Export.class, "Description", null);
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

	ModelColumn<I_DATEV_Export, Object> COLUMN_IsActive = new ModelColumn<>(I_DATEV_Export.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Exclude already exported.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsExcludeAlreadyExported (boolean IsExcludeAlreadyExported);

	/**
	 * Get Exclude already exported.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isExcludeAlreadyExported();

	ModelColumn<I_DATEV_Export, Object> COLUMN_IsExcludeAlreadyExported = new ModelColumn<>(I_DATEV_Export.class, "IsExcludeAlreadyExported", null);
	String COLUMNNAME_IsExcludeAlreadyExported = "IsExcludeAlreadyExported";

	/**
	 * Set Negate Inbound Amounts.
	 * When ticked, the tax and amount of 'APC' and 'ARI' invoices are negated.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsNegateInboundAmounts (boolean IsNegateInboundAmounts);

	/**
	 * Get Negate Inbound Amounts.
	 * When ticked, the tax and amount of 'APC' and 'ARI' invoices are negated.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isNegateInboundAmounts();

	ModelColumn<I_DATEV_Export, Object> COLUMN_IsNegateInboundAmounts = new ModelColumn<>(I_DATEV_Export.class, "IsNegateInboundAmounts", null);
	String COLUMNNAME_IsNegateInboundAmounts = "IsNegateInboundAmounts";

	/**
	 * Set Place BP Accounts On Credit.
	 * When ticked, the partner accounts are moved from debit to credit side.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsPlaceBPAccountsOnCredit (boolean IsPlaceBPAccountsOnCredit);

	/**
	 * Get Place BP Accounts On Credit.
	 * When ticked, the partner accounts are moved from debit to credit side.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isPlaceBPAccountsOnCredit();

	ModelColumn<I_DATEV_Export, Object> COLUMN_IsPlaceBPAccountsOnCredit = new ModelColumn<>(I_DATEV_Export.class, "IsPlaceBPAccountsOnCredit", null);
	String COLUMNNAME_IsPlaceBPAccountsOnCredit = "IsPlaceBPAccountsOnCredit";

	/**
	 * Set Sales Transaction.
	 * This is a Sales Transaction
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIsSOTrx (@Nullable java.lang.String IsSOTrx);

	/**
	 * Get Sales Transaction.
	 * This is a Sales Transaction
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getIsSOTrx();

	ModelColumn<I_DATEV_Export, Object> COLUMN_IsSOTrx = new ModelColumn<>(I_DATEV_Export.class, "IsSOTrx", null);
	String COLUMNNAME_IsSOTrx = "IsSOTrx";

	/**
	 * Set Switch Credit Memo.
	 * If ticked, for credit memo invoices, the credit account and the debit account are swapped. The amount and tax are multiplied by -1.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsSwitchCreditMemo (boolean IsSwitchCreditMemo);

	/**
	 * Get Switch Credit Memo.
	 * If ticked, for credit memo invoices, the credit account and the debit account are swapped. The amount and tax are multiplied by -1.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isSwitchCreditMemo();

	ModelColumn<I_DATEV_Export, Object> COLUMN_IsSwitchCreditMemo = new ModelColumn<>(I_DATEV_Export.class, "IsSwitchCreditMemo", null);
	String COLUMNNAME_IsSwitchCreditMemo = "IsSwitchCreditMemo";

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

	ModelColumn<I_DATEV_Export, Object> COLUMN_Processed = new ModelColumn<>(I_DATEV_Export.class, "Processed", null);
	String COLUMNNAME_Processed = "Processed";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_DATEV_Export, Object> COLUMN_Updated = new ModelColumn<>(I_DATEV_Export.class, "Updated", null);
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
