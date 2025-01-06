package org.compiere.model;

import java.math.BigDecimal;
import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for C_POS
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_C_POS 
{

	String Table_Name = "C_POS";

//	/** AD_Table_ID=748 */
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
	 * Set Auto Logout Delay.
	 * Automatically logout if terminal inactive for this period
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAutoLogoutDelay (int AutoLogoutDelay);

	/**
	 * Get Auto Logout Delay.
	 * Automatically logout if terminal inactive for this period
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAutoLogoutDelay();

	ModelColumn<I_C_POS, Object> COLUMN_AutoLogoutDelay = new ModelColumn<>(I_C_POS.class, "AutoLogoutDelay", null);
	String COLUMNNAME_AutoLogoutDelay = "AutoLogoutDelay";

	/**
	 * Set Cash Last Balance.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setCashLastBalance (BigDecimal CashLastBalance);

	/**
	 * Get Cash Last Balance.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getCashLastBalance();

	ModelColumn<I_C_POS, Object> COLUMN_CashLastBalance = new ModelColumn<>(I_C_POS.class, "CashLastBalance", null);
	String COLUMNNAME_CashLastBalance = "CashLastBalance";

	/**
	 * Set Template B.Partner.
	 * Business Partner used for creating new Business Partners on the fly
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_BPartnerCashTrx_ID (int C_BPartnerCashTrx_ID);

	/**
	 * Get Template B.Partner.
	 * Business Partner used for creating new Business Partners on the fly
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_BPartnerCashTrx_ID();

	String COLUMNNAME_C_BPartnerCashTrx_ID = "C_BPartnerCashTrx_ID";

	/**
	 * Set Partner Bank Account.
	 * Bank Account of the Business Partner
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_BP_BankAccount_ID (int C_BP_BankAccount_ID);

	/**
	 * Get Partner Bank Account.
	 * Bank Account of the Business Partner
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_BP_BankAccount_ID();

	String COLUMNNAME_C_BP_BankAccount_ID = "C_BP_BankAccount_ID";

	/**
	 * Set Auftrags-Belegart.
	 * Document type used for the orders generated from this order candidate
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_DocTypeOrder_ID (int C_DocTypeOrder_ID);

	/**
	 * Get Auftrags-Belegart.
	 * Document type used for the orders generated from this order candidate
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_DocTypeOrder_ID();

	String COLUMNNAME_C_DocTypeOrder_ID = "C_DocTypeOrder_ID";

	/**
	 * Set POS Terminal.
	 * Point of Sales Terminal
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_POS_ID (int C_POS_ID);

	/**
	 * Get POS Terminal.
	 * Point of Sales Terminal
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_POS_ID();

	ModelColumn<I_C_POS, Object> COLUMN_C_POS_ID = new ModelColumn<>(I_C_POS.class, "C_POS_ID", null);
	String COLUMNNAME_C_POS_ID = "C_POS_ID";

	/**
	 * Set POS Cash Journal.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_POS_Journal_ID (int C_POS_Journal_ID);

	/**
	 * Get POS Cash Journal.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_POS_Journal_ID();

	ModelColumn<I_C_POS, Object> COLUMN_C_POS_Journal_ID = new ModelColumn<>(I_C_POS.class, "C_POS_Journal_ID", null);
	String COLUMNNAME_C_POS_Journal_ID = "C_POS_Journal_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_C_POS, Object> COLUMN_Created = new ModelColumn<>(I_C_POS.class, "Created", null);
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
	 * Set Workplace.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Workplace_ID (int C_Workplace_ID);

	/**
	 * Get Workplace.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Workplace_ID();

	@Nullable org.compiere.model.I_C_Workplace getC_Workplace();

	void setC_Workplace(@Nullable org.compiere.model.I_C_Workplace C_Workplace);

	ModelColumn<I_C_POS, org.compiere.model.I_C_Workplace> COLUMN_C_Workplace_ID = new ModelColumn<>(I_C_POS.class, "C_Workplace_ID", org.compiere.model.I_C_Workplace.class);
	String COLUMNNAME_C_Workplace_ID = "C_Workplace_ID";

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

	ModelColumn<I_C_POS, Object> COLUMN_Description = new ModelColumn<>(I_C_POS.class, "Description", null);
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

	ModelColumn<I_C_POS, Object> COLUMN_IsActive = new ModelColumn<>(I_C_POS.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Modify Price.
	 * Allow modifying the price
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsModifyPrice (boolean IsModifyPrice);

	/**
	 * Get Modify Price.
	 * Allow modifying the price
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isModifyPrice();

	ModelColumn<I_C_POS, Object> COLUMN_IsModifyPrice = new ModelColumn<>(I_C_POS.class, "IsModifyPrice", null);
	String COLUMNNAME_IsModifyPrice = "IsModifyPrice";

	/**
	 * Set Price List.
	 * Unique identifier of a Price List
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_PriceList_ID (int M_PriceList_ID);

	/**
	 * Get Price List.
	 * Unique identifier of a Price List
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_PriceList_ID();

	String COLUMNNAME_M_PriceList_ID = "M_PriceList_ID";

	/**
	 * Set Warehouse.
	 * Storage Warehouse and Service Point
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_Warehouse_ID (int M_Warehouse_ID);

	/**
	 * Get Warehouse.
	 * Storage Warehouse and Service Point
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_Warehouse_ID();

	String COLUMNNAME_M_Warehouse_ID = "M_Warehouse_ID";

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

	ModelColumn<I_C_POS, Object> COLUMN_Name = new ModelColumn<>(I_C_POS.class, "Name", null);
	String COLUMNNAME_Name = "Name";

	/**
	 * Set Payment Processor.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPOSPaymentProcessor (@Nullable java.lang.String POSPaymentProcessor);

	/**
	 * Get Payment Processor.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getPOSPaymentProcessor();

	ModelColumn<I_C_POS, Object> COLUMN_POSPaymentProcessor = new ModelColumn<>(I_C_POS.class, "POSPaymentProcessor", null);
	String COLUMNNAME_POSPaymentProcessor = "POSPaymentProcessor";

	/**
	 * Set Printer.
	 * Name of the Printer
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPrinterName (@Nullable java.lang.String PrinterName);

	/**
	 * Get Printer.
	 * Name of the Printer
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getPrinterName();

	ModelColumn<I_C_POS, Object> COLUMN_PrinterName = new ModelColumn<>(I_C_POS.class, "PrinterName", null);
	String COLUMNNAME_PrinterName = "PrinterName";

	/**
	 * Set SumUp Configuration.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setSUMUP_Config_ID (int SUMUP_Config_ID);

	/**
	 * Get SumUp Configuration.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getSUMUP_Config_ID();

	ModelColumn<I_C_POS, Object> COLUMN_SUMUP_Config_ID = new ModelColumn<>(I_C_POS.class, "SUMUP_Config_ID", null);
	String COLUMNNAME_SUMUP_Config_ID = "SUMUP_Config_ID";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_C_POS, Object> COLUMN_Updated = new ModelColumn<>(I_C_POS.class, "Updated", null);
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
