package de.metas.contracts.model;

import java.math.BigDecimal;
import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for C_Flatrate_RefundConfig
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_C_Flatrate_RefundConfig 
{

	String Table_Name = "C_Flatrate_RefundConfig";

//	/** AD_Table_ID=540980 */
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
	 * Set Bonus product.
	 * Product of the credit memo line. The tax and accounts of the credit memo follow this product.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setBonus_Product_ID (int Bonus_Product_ID);

	/**
	 * Get Bonus product.
	 * Product of the credit memo line. The tax and accounts of the credit memo follow this product.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getBonus_Product_ID();

	String COLUMNNAME_Bonus_Product_ID = "Bonus_Product_ID";

	/**
	 * Set Bonus recipient.
	 * Specifies the partner the bonus is issued to.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setBonusRecipient (java.lang.String BonusRecipient);

	/**
	 * Get Bonus recipient.
	 * Specifies the partner the bonus is issued to.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getBonusRecipient();

	ModelColumn<I_C_Flatrate_RefundConfig, Object> COLUMN_BonusRecipient = new ModelColumn<>(I_C_Flatrate_RefundConfig.class, "BonusRecipient", null);
	String COLUMNNAME_BonusRecipient = "BonusRecipient";

	/**
	 * Set Currency.
	 * The Currency for this record
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Currency_ID (int C_Currency_ID);

	/**
	 * Get Currency.
	 * The Currency for this record
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Currency_ID();

	String COLUMNNAME_C_Currency_ID = "C_Currency_ID";

	/**
	 * Set Contract Terms.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Flatrate_Conditions_ID (int C_Flatrate_Conditions_ID);

	/**
	 * Get Contract Terms.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Flatrate_Conditions_ID();

	ModelColumn<I_C_Flatrate_RefundConfig, Object> COLUMN_C_Flatrate_Conditions_ID = new ModelColumn<>(I_C_Flatrate_RefundConfig.class, "C_Flatrate_Conditions_ID", null);
	String COLUMNNAME_C_Flatrate_Conditions_ID = "C_Flatrate_Conditions_ID";

	/**
	 * Set Flatrate Refund Configuration.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Flatrate_RefundConfig_ID (int C_Flatrate_RefundConfig_ID);

	/**
	 * Get Flatrate Refund Configuration.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Flatrate_RefundConfig_ID();

	ModelColumn<I_C_Flatrate_RefundConfig, Object> COLUMN_C_Flatrate_RefundConfig_ID = new ModelColumn<>(I_C_Flatrate_RefundConfig.class, "C_Flatrate_RefundConfig_ID", null);
	String COLUMNNAME_C_Flatrate_RefundConfig_ID = "C_Flatrate_RefundConfig_ID";

	/**
	 * Set Invoice Schedule.
	 * Schedule for generating Invoices
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_InvoiceSchedule_ID (int C_InvoiceSchedule_ID);

	/**
	 * Get Invoice Schedule.
	 * Schedule for generating Invoices
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_InvoiceSchedule_ID();

	org.compiere.model.I_C_InvoiceSchedule getC_InvoiceSchedule();

	void setC_InvoiceSchedule(org.compiere.model.I_C_InvoiceSchedule C_InvoiceSchedule);

	ModelColumn<I_C_Flatrate_RefundConfig, org.compiere.model.I_C_InvoiceSchedule> COLUMN_C_InvoiceSchedule_ID = new ModelColumn<>(I_C_Flatrate_RefundConfig.class, "C_InvoiceSchedule_ID", org.compiere.model.I_C_InvoiceSchedule.class);
	String COLUMNNAME_C_InvoiceSchedule_ID = "C_InvoiceSchedule_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_C_Flatrate_RefundConfig, Object> COLUMN_Created = new ModelColumn<>(I_C_Flatrate_RefundConfig.class, "Created", null);
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

	ModelColumn<I_C_Flatrate_RefundConfig, Object> COLUMN_IsActive = new ModelColumn<>(I_C_Flatrate_RefundConfig.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Filter by packaging.
	 * If enabled, only lines with one of the listed packaging options get the bonus.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsPackingOptionFiltered (boolean IsPackingOptionFiltered);

	/**
	 * Get Filter by packaging.
	 * If enabled, only lines with one of the listed packaging options get the bonus.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isPackingOptionFiltered();

	ModelColumn<I_C_Flatrate_RefundConfig, Object> COLUMN_IsPackingOptionFiltered = new ModelColumn<>(I_C_Flatrate_RefundConfig.class, "IsPackingOptionFiltered", null);
	String COLUMNNAME_IsPackingOptionFiltered = "IsPackingOptionFiltered";

	/**
	 * Set In profit calculation.
	 * Specifies whether the refund parameters shall be included when calculating the expected profit.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsUseInProfitCalculation (boolean IsUseInProfitCalculation);

	/**
	 * Get In profit calculation.
	 * Specifies whether the refund parameters shall be included when calculating the expected profit.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isUseInProfitCalculation();

	ModelColumn<I_C_Flatrate_RefundConfig, Object> COLUMN_IsUseInProfitCalculation = new ModelColumn<>(I_C_Flatrate_RefundConfig.class, "IsUseInProfitCalculation", null);
	String COLUMNNAME_IsUseInProfitCalculation = "IsUseInProfitCalculation";

	/**
	 * Set Minimum quantity.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setMinQty (BigDecimal MinQty);

	/**
	 * Get Minimum quantity.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getMinQty();

	ModelColumn<I_C_Flatrate_RefundConfig, Object> COLUMN_MinQty = new ModelColumn<>(I_C_Flatrate_RefundConfig.class, "MinQty", null);
	String COLUMNNAME_MinQty = "MinQty";

	/**
	 * Set Product Category.
	 * Category of a Product
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_Product_Category_ID (int M_Product_Category_ID);

	/**
	 * Get Product Category.
	 * Category of a Product
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_Product_Category_ID();

	String COLUMNNAME_M_Product_Category_ID = "M_Product_Category_ID";

	/**
	 * Set Product.
	 * Product, Service, Item
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_Product_ID (int M_Product_ID);

	/**
	 * Get Product.
	 * Product, Service, Item
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_Product_ID();

	String COLUMNNAME_M_Product_ID = "M_Product_ID";

	/**
	 * Set Refund amount.
	 * Refund amount per product unit
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setRefundAmt (@Nullable BigDecimal RefundAmt);

	/**
	 * Get Refund amount.
	 * Refund amount per product unit
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getRefundAmt();

	ModelColumn<I_C_Flatrate_RefundConfig, Object> COLUMN_RefundAmt = new ModelColumn<>(I_C_Flatrate_RefundConfig.class, "RefundAmt", null);
	String COLUMNNAME_RefundAmt = "RefundAmt";

	/**
	 * Set Refund based on.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setRefundBase (java.lang.String RefundBase);

	/**
	 * Get Refund based on.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getRefundBase();

	ModelColumn<I_C_Flatrate_RefundConfig, Object> COLUMN_RefundBase = new ModelColumn<>(I_C_Flatrate_RefundConfig.class, "RefundBase", null);
	String COLUMNNAME_RefundBase = "RefundBase";

	/**
	 * Set Refund per.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setRefundInvoiceType (java.lang.String RefundInvoiceType);

	/**
	 * Get Refund per.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getRefundInvoiceType();

	ModelColumn<I_C_Flatrate_RefundConfig, Object> COLUMN_RefundInvoiceType = new ModelColumn<>(I_C_Flatrate_RefundConfig.class, "RefundInvoiceType", null);
	String COLUMNNAME_RefundInvoiceType = "RefundInvoiceType";

	/**
	 * Set Mode.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setRefundMode (java.lang.String RefundMode);

	/**
	 * Get Mode.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getRefundMode();

	ModelColumn<I_C_Flatrate_RefundConfig, Object> COLUMN_RefundMode = new ModelColumn<>(I_C_Flatrate_RefundConfig.class, "RefundMode", null);
	String COLUMNNAME_RefundMode = "RefundMode";

	/**
	 * Set Percent.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setRefundPercent (@Nullable BigDecimal RefundPercent);

	/**
	 * Get Percent.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getRefundPercent();

	ModelColumn<I_C_Flatrate_RefundConfig, Object> COLUMN_RefundPercent = new ModelColumn<>(I_C_Flatrate_RefundConfig.class, "RefundPercent", null);
	String COLUMNNAME_RefundPercent = "RefundPercent";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_C_Flatrate_RefundConfig, Object> COLUMN_Updated = new ModelColumn<>(I_C_Flatrate_RefundConfig.class, "Updated", null);
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
