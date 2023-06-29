package org.compiere.model;

import java.math.BigDecimal;
import org.adempiere.model.ModelColumn;

/** Generated Interface for GPLR_Report_Summary
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_GPLR_Report_Summary 
{

	String Table_Name = "GPLR_Report_Summary";

//	/** AD_Table_ID=542345 */
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
	 * Set Charges (FC).
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setCharges_FC (BigDecimal Charges_FC);

	/**
	 * Get Charges (FC).
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getCharges_FC();

	ModelColumn<I_GPLR_Report_Summary, Object> COLUMN_Charges_FC = new ModelColumn<>(I_GPLR_Report_Summary.class, "Charges_FC", null);
	String COLUMNNAME_Charges_FC = "Charges_FC";

	/**
	 * Set Charges (LC).
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setCharges_LC (BigDecimal Charges_LC);

	/**
	 * Get Charges (LC).
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getCharges_LC();

	ModelColumn<I_GPLR_Report_Summary, Object> COLUMN_Charges_LC = new ModelColumn<>(I_GPLR_Report_Summary.class, "Charges_LC", null);
	String COLUMNNAME_Charges_LC = "Charges_LC";

	/**
	 * Set COGS (LC).
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setCOGS_LC (BigDecimal COGS_LC);

	/**
	 * Get COGS (LC).
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getCOGS_LC();

	ModelColumn<I_GPLR_Report_Summary, Object> COLUMN_COGS_LC = new ModelColumn<>(I_GPLR_Report_Summary.class, "COGS_LC", null);
	String COLUMNNAME_COGS_LC = "COGS_LC";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_GPLR_Report_Summary, Object> COLUMN_Created = new ModelColumn<>(I_GPLR_Report_Summary.class, "Created", null);
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
	 * Set Estimated (FC).
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setEstimated_FC (BigDecimal Estimated_FC);

	/**
	 * Get Estimated (FC).
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getEstimated_FC();

	ModelColumn<I_GPLR_Report_Summary, Object> COLUMN_Estimated_FC = new ModelColumn<>(I_GPLR_Report_Summary.class, "Estimated_FC", null);
	String COLUMNNAME_Estimated_FC = "Estimated_FC";

	/**
	 * Set Estimated (LC).
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setEstimated_LC (BigDecimal Estimated_LC);

	/**
	 * Get Estimated (LC).
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getEstimated_LC();

	ModelColumn<I_GPLR_Report_Summary, Object> COLUMN_Estimated_LC = new ModelColumn<>(I_GPLR_Report_Summary.class, "Estimated_LC", null);
	String COLUMNNAME_Estimated_LC = "Estimated_LC";

	/**
	 * Set Foreign Currency.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setForeignCurrencyCode (java.lang.String ForeignCurrencyCode);

	/**
	 * Get Foreign Currency.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getForeignCurrencyCode();

	ModelColumn<I_GPLR_Report_Summary, Object> COLUMN_ForeignCurrencyCode = new ModelColumn<>(I_GPLR_Report_Summary.class, "ForeignCurrencyCode", null);
	String COLUMNNAME_ForeignCurrencyCode = "ForeignCurrencyCode";

	/**
	 * Set GPLR Report.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setGPLR_Report_ID (int GPLR_Report_ID);

	/**
	 * Get GPLR Report.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getGPLR_Report_ID();

	org.compiere.model.I_GPLR_Report getGPLR_Report();

	void setGPLR_Report(org.compiere.model.I_GPLR_Report GPLR_Report);

	ModelColumn<I_GPLR_Report_Summary, org.compiere.model.I_GPLR_Report> COLUMN_GPLR_Report_ID = new ModelColumn<>(I_GPLR_Report_Summary.class, "GPLR_Report_ID", org.compiere.model.I_GPLR_Report.class);
	String COLUMNNAME_GPLR_Report_ID = "GPLR_Report_ID";

	/**
	 * Set GPLR Report - Summary.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setGPLR_Report_Summary_ID (int GPLR_Report_Summary_ID);

	/**
	 * Get GPLR Report - Summary.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getGPLR_Report_Summary_ID();

	ModelColumn<I_GPLR_Report_Summary, Object> COLUMN_GPLR_Report_Summary_ID = new ModelColumn<>(I_GPLR_Report_Summary.class, "GPLR_Report_Summary_ID", null);
	String COLUMNNAME_GPLR_Report_Summary_ID = "GPLR_Report_Summary_ID";

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

	ModelColumn<I_GPLR_Report_Summary, Object> COLUMN_IsActive = new ModelColumn<>(I_GPLR_Report_Summary.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Local Currency.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setLocalCurrencyCode (java.lang.String LocalCurrencyCode);

	/**
	 * Get Local Currency.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getLocalCurrencyCode();

	ModelColumn<I_GPLR_Report_Summary, Object> COLUMN_LocalCurrencyCode = new ModelColumn<>(I_GPLR_Report_Summary.class, "LocalCurrencyCode", null);
	String COLUMNNAME_LocalCurrencyCode = "LocalCurrencyCode";

	/**
	 * Set Profit/Loss (LC).
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setProfitOrLoss_LC (BigDecimal ProfitOrLoss_LC);

	/**
	 * Get Profit/Loss (LC).
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getProfitOrLoss_LC();

	ModelColumn<I_GPLR_Report_Summary, Object> COLUMN_ProfitOrLoss_LC = new ModelColumn<>(I_GPLR_Report_Summary.class, "ProfitOrLoss_LC", null);
	String COLUMNNAME_ProfitOrLoss_LC = "ProfitOrLoss_LC";

	/**
	 * Set Profit Rate.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setProfitRate (BigDecimal ProfitRate);

	/**
	 * Get Profit Rate.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getProfitRate();

	ModelColumn<I_GPLR_Report_Summary, Object> COLUMN_ProfitRate = new ModelColumn<>(I_GPLR_Report_Summary.class, "ProfitRate", null);
	String COLUMNNAME_ProfitRate = "ProfitRate";

	/**
	 * Set Sales (FC).
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setSales_FC (BigDecimal Sales_FC);

	/**
	 * Get Sales (FC).
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getSales_FC();

	ModelColumn<I_GPLR_Report_Summary, Object> COLUMN_Sales_FC = new ModelColumn<>(I_GPLR_Report_Summary.class, "Sales_FC", null);
	String COLUMNNAME_Sales_FC = "Sales_FC";

	/**
	 * Set Sales (LC).
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setSales_LC (BigDecimal Sales_LC);

	/**
	 * Get Sales (LC).
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getSales_LC();

	ModelColumn<I_GPLR_Report_Summary, Object> COLUMN_Sales_LC = new ModelColumn<>(I_GPLR_Report_Summary.class, "Sales_LC", null);
	String COLUMNNAME_Sales_LC = "Sales_LC";

	/**
	 * Set Taxes (LC).
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setTaxes_LC (BigDecimal Taxes_LC);

	/**
	 * Get Taxes (LC).
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getTaxes_LC();

	ModelColumn<I_GPLR_Report_Summary, Object> COLUMN_Taxes_LC = new ModelColumn<>(I_GPLR_Report_Summary.class, "Taxes_LC", null);
	String COLUMNNAME_Taxes_LC = "Taxes_LC";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_GPLR_Report_Summary, Object> COLUMN_Updated = new ModelColumn<>(I_GPLR_Report_Summary.class, "Updated", null);
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
