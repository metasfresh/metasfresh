package org.compiere.model;

import java.math.BigDecimal;
import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for GPLR_Report_Charge
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_GPLR_Report_Charge 
{

	String Table_Name = "GPLR_Report_Charge";

//	/** AD_Table_ID=542347 */
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
	 * Set Amount (FC).
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAmount_FC (@Nullable BigDecimal Amount_FC);

	/**
	 * Get Amount (FC).
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getAmount_FC();

	ModelColumn<I_GPLR_Report_Charge, Object> COLUMN_Amount_FC = new ModelColumn<>(I_GPLR_Report_Charge.class, "Amount_FC", null);
	String COLUMNNAME_Amount_FC = "Amount_FC";

	/**
	 * Set Amount (LC).
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAmount_LC (@Nullable BigDecimal Amount_LC);

	/**
	 * Get Amount (LC).
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getAmount_LC();

	ModelColumn<I_GPLR_Report_Charge, Object> COLUMN_Amount_LC = new ModelColumn<>(I_GPLR_Report_Charge.class, "Amount_LC", null);
	String COLUMNNAME_Amount_LC = "Amount_LC";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_GPLR_Report_Charge, Object> COLUMN_Created = new ModelColumn<>(I_GPLR_Report_Charge.class, "Created", null);
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
	 * Set Foreign Currency.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setForeignCurrencyCode (@Nullable java.lang.String ForeignCurrencyCode);

	/**
	 * Get Foreign Currency.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getForeignCurrencyCode();

	ModelColumn<I_GPLR_Report_Charge, Object> COLUMN_ForeignCurrencyCode = new ModelColumn<>(I_GPLR_Report_Charge.class, "ForeignCurrencyCode", null);
	String COLUMNNAME_ForeignCurrencyCode = "ForeignCurrencyCode";

	/**
	 * Set GPLR Report - Charge.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setGPLR_Report_Charge_ID (int GPLR_Report_Charge_ID);

	/**
	 * Get GPLR Report - Charge.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getGPLR_Report_Charge_ID();

	ModelColumn<I_GPLR_Report_Charge, Object> COLUMN_GPLR_Report_Charge_ID = new ModelColumn<>(I_GPLR_Report_Charge.class, "GPLR_Report_Charge_ID", null);
	String COLUMNNAME_GPLR_Report_Charge_ID = "GPLR_Report_Charge_ID";

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

	ModelColumn<I_GPLR_Report_Charge, org.compiere.model.I_GPLR_Report> COLUMN_GPLR_Report_ID = new ModelColumn<>(I_GPLR_Report_Charge.class, "GPLR_Report_ID", org.compiere.model.I_GPLR_Report.class);
	String COLUMNNAME_GPLR_Report_ID = "GPLR_Report_ID";

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

	ModelColumn<I_GPLR_Report_Charge, Object> COLUMN_IsActive = new ModelColumn<>(I_GPLR_Report_Charge.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Line.
	 * Line No
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setLineNo (@Nullable java.lang.String LineNo);

	/**
	 * Get Line.
	 * Line No
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getLineNo();

	ModelColumn<I_GPLR_Report_Charge, Object> COLUMN_LineNo = new ModelColumn<>(I_GPLR_Report_Charge.class, "LineNo", null);
	String COLUMNNAME_LineNo = "LineNo";

	/**
	 * Set Local Currency.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setLocalCurrencyCode (@Nullable java.lang.String LocalCurrencyCode);

	/**
	 * Get Local Currency.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getLocalCurrencyCode();

	ModelColumn<I_GPLR_Report_Charge, Object> COLUMN_LocalCurrencyCode = new ModelColumn<>(I_GPLR_Report_Charge.class, "LocalCurrencyCode", null);
	String COLUMNNAME_LocalCurrencyCode = "LocalCurrencyCode";

	/**
	 * Set Cost Type Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setOrderCostTypeName (java.lang.String OrderCostTypeName);

	/**
	 * Get Cost Type Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getOrderCostTypeName();

	ModelColumn<I_GPLR_Report_Charge, Object> COLUMN_OrderCostTypeName = new ModelColumn<>(I_GPLR_Report_Charge.class, "OrderCostTypeName", null);
	String COLUMNNAME_OrderCostTypeName = "OrderCostTypeName";

	/**
	 * Set Order Document No.
	 * Document Number of the Order
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setOrderDocumentNo (@Nullable java.lang.String OrderDocumentNo);

	/**
	 * Get Order Document No.
	 * Document Number of the Order
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getOrderDocumentNo();

	ModelColumn<I_GPLR_Report_Charge, Object> COLUMN_OrderDocumentNo = new ModelColumn<>(I_GPLR_Report_Charge.class, "OrderDocumentNo", null);
	String COLUMNNAME_OrderDocumentNo = "OrderDocumentNo";

	/**
	 * Set Payee Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPayee_BPartnerName (@Nullable java.lang.String Payee_BPartnerName);

	/**
	 * Get Payee Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getPayee_BPartnerName();

	ModelColumn<I_GPLR_Report_Charge, Object> COLUMN_Payee_BPartnerName = new ModelColumn<>(I_GPLR_Report_Charge.class, "Payee_BPartnerName", null);
	String COLUMNNAME_Payee_BPartnerName = "Payee_BPartnerName";

	/**
	 * Set Payee Code.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPayee_BPartnerValue (@Nullable java.lang.String Payee_BPartnerValue);

	/**
	 * Get Payee Code.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getPayee_BPartnerValue();

	ModelColumn<I_GPLR_Report_Charge, Object> COLUMN_Payee_BPartnerValue = new ModelColumn<>(I_GPLR_Report_Charge.class, "Payee_BPartnerValue", null);
	String COLUMNNAME_Payee_BPartnerValue = "Payee_BPartnerValue";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_GPLR_Report_Charge, Object> COLUMN_Updated = new ModelColumn<>(I_GPLR_Report_Charge.class, "Updated", null);
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
