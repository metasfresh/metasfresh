package de.metas.acct.model;

import java.math.BigDecimal;
import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for Fact_Acct_UserChange
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_Fact_Acct_UserChange 
{

	String Table_Name = "Fact_Acct_UserChange";

//	/** AD_Table_ID=542356 */
//	int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);


	/**
	 * Set Account.
	 * Account used
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAccount_ID (int Account_ID);

	/**
	 * Get Account.
	 * Account used
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAccount_ID();

	String COLUMNNAME_Account_ID = "Account_ID";

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
	 * Set Amount (DC).
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAmount_DC (BigDecimal Amount_DC);

	/**
	 * Get Amount (DC).
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getAmount_DC();

	ModelColumn<I_Fact_Acct_UserChange, Object> COLUMN_Amount_DC = new ModelColumn<>(I_Fact_Acct_UserChange.class, "Amount_DC", null);
	String COLUMNNAME_Amount_DC = "Amount_DC";

	/**
	 * Set Amount (LC).
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAmount_LC (BigDecimal Amount_LC);

	/**
	 * Get Amount (LC).
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getAmount_LC();

	ModelColumn<I_Fact_Acct_UserChange, Object> COLUMN_Amount_LC = new ModelColumn<>(I_Fact_Acct_UserChange.class, "Amount_LC", null);
	String COLUMNNAME_Amount_LC = "Amount_LC";

	/**
	 * Set Accounting Schema.
	 * Rules for accounting
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_AcctSchema_ID (int C_AcctSchema_ID);

	/**
	 * Get Accounting Schema.
	 * Rules for accounting
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_AcctSchema_ID();

	org.compiere.model.I_C_AcctSchema getC_AcctSchema();

	void setC_AcctSchema(org.compiere.model.I_C_AcctSchema C_AcctSchema);

	ModelColumn<I_Fact_Acct_UserChange, org.compiere.model.I_C_AcctSchema> COLUMN_C_AcctSchema_ID = new ModelColumn<>(I_Fact_Acct_UserChange.class, "C_AcctSchema_ID", org.compiere.model.I_C_AcctSchema.class);
	String COLUMNNAME_C_AcctSchema_ID = "C_AcctSchema_ID";

	/**
	 * Set Activity.
	 * Business Activity
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Activity_ID (int C_Activity_ID);

	/**
	 * Get Activity.
	 * Business Activity
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Activity_ID();

	String COLUMNNAME_C_Activity_ID = "C_Activity_ID";

	/**
	 * Set ChangeType.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setChangeType (java.lang.String ChangeType);

	/**
	 * Get ChangeType.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getChangeType();

	ModelColumn<I_Fact_Acct_UserChange, Object> COLUMN_ChangeType = new ModelColumn<>(I_Fact_Acct_UserChange.class, "ChangeType", null);
	String COLUMNNAME_ChangeType = "ChangeType";

	/**
	 * Set Invoice.
	 * Invoice Identifier
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Invoice_ID (int C_Invoice_ID);

	/**
	 * Get Invoice.
	 * Invoice Identifier
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Invoice_ID();

	@Nullable org.compiere.model.I_C_Invoice getC_Invoice();

	void setC_Invoice(@Nullable org.compiere.model.I_C_Invoice C_Invoice);

	ModelColumn<I_Fact_Acct_UserChange, org.compiere.model.I_C_Invoice> COLUMN_C_Invoice_ID = new ModelColumn<>(I_Fact_Acct_UserChange.class, "C_Invoice_ID", org.compiere.model.I_C_Invoice.class);
	String COLUMNNAME_C_Invoice_ID = "C_Invoice_ID";

	/**
	 * Set Sales Order.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_OrderSO_ID (int C_OrderSO_ID);

	/**
	 * Get Sales Order.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_OrderSO_ID();

	@Nullable org.compiere.model.I_C_Order getC_OrderSO();

	void setC_OrderSO(@Nullable org.compiere.model.I_C_Order C_OrderSO);

	ModelColumn<I_Fact_Acct_UserChange, org.compiere.model.I_C_Order> COLUMN_C_OrderSO_ID = new ModelColumn<>(I_Fact_Acct_UserChange.class, "C_OrderSO_ID", org.compiere.model.I_C_Order.class);
	String COLUMNNAME_C_OrderSO_ID = "C_OrderSO_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_Fact_Acct_UserChange, Object> COLUMN_Created = new ModelColumn<>(I_Fact_Acct_UserChange.class, "Created", null);
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
	 * Set Tax.
	 * Tax identifier
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Tax_ID (int C_Tax_ID);

	/**
	 * Get Tax.
	 * Tax identifier
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Tax_ID();

	String COLUMNNAME_C_Tax_ID = "C_Tax_ID";

	/**
	 * Set Description.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDescription (@Nullable java.lang.String Description);

	/**
	 * Get Description.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDescription();

	ModelColumn<I_Fact_Acct_UserChange, Object> COLUMN_Description = new ModelColumn<>(I_Fact_Acct_UserChange.class, "Description", null);
	String COLUMNNAME_Description = "Description";

	/**
	 * Set Document Currency.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDocument_Currency_ID (int Document_Currency_ID);

	/**
	 * Get Document Currency.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getDocument_Currency_ID();

	String COLUMNNAME_Document_Currency_ID = "Document_Currency_ID";

	/**
	 * Set Accounting override/adjustments.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setFact_Acct_UserChange_ID (int Fact_Acct_UserChange_ID);

	/**
	 * Get Accounting override/adjustments.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getFact_Acct_UserChange_ID();

	ModelColumn<I_Fact_Acct_UserChange, Object> COLUMN_Fact_Acct_UserChange_ID = new ModelColumn<>(I_Fact_Acct_UserChange.class, "Fact_Acct_UserChange_ID", null);
	String COLUMNNAME_Fact_Acct_UserChange_ID = "Fact_Acct_UserChange_ID";

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

	ModelColumn<I_Fact_Acct_UserChange, Object> COLUMN_IsActive = new ModelColumn<>(I_Fact_Acct_UserChange.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Local Currency.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setLocal_Currency_ID (int Local_Currency_ID);

	/**
	 * Get Local Currency.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getLocal_Currency_ID();

	String COLUMNNAME_Local_Currency_ID = "Local_Currency_ID";

	/**
	 * Set Match Key.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setMatchKey (@Nullable java.lang.String MatchKey);

	/**
	 * Get Match Key.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getMatchKey();

	ModelColumn<I_Fact_Acct_UserChange, Object> COLUMN_MatchKey = new ModelColumn<>(I_Fact_Acct_UserChange.class, "MatchKey", null);
	String COLUMNNAME_MatchKey = "MatchKey";

	/**
	 * Set Product.
	 * Product, Service, Item
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_Product_ID (int M_Product_ID);

	/**
	 * Get Product.
	 * Product, Service, Item
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_Product_ID();

	String COLUMNNAME_M_Product_ID = "M_Product_ID";

	/**
	 * Set Section Code.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_SectionCode_ID (int M_SectionCode_ID);

	/**
	 * Get Section Code.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_SectionCode_ID();

	@Nullable org.compiere.model.I_M_SectionCode getM_SectionCode();

	void setM_SectionCode(@Nullable org.compiere.model.I_M_SectionCode M_SectionCode);

	ModelColumn<I_Fact_Acct_UserChange, org.compiere.model.I_M_SectionCode> COLUMN_M_SectionCode_ID = new ModelColumn<>(I_Fact_Acct_UserChange.class, "M_SectionCode_ID", org.compiere.model.I_M_SectionCode.class);
	String COLUMNNAME_M_SectionCode_ID = "M_SectionCode_ID";

	/**
	 * Set DR/CR.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPostingSign (java.lang.String PostingSign);

	/**
	 * Get DR/CR.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getPostingSign();

	ModelColumn<I_Fact_Acct_UserChange, Object> COLUMN_PostingSign = new ModelColumn<>(I_Fact_Acct_UserChange.class, "PostingSign", null);
	String COLUMNNAME_PostingSign = "PostingSign";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_Fact_Acct_UserChange, Object> COLUMN_Updated = new ModelColumn<>(I_Fact_Acct_UserChange.class, "Updated", null);
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

	/**
	 * Set Assignment.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setUserElementString1 (@Nullable java.lang.String UserElementString1);

	/**
	 * Get Assignment.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getUserElementString1();

	ModelColumn<I_Fact_Acct_UserChange, Object> COLUMN_UserElementString1 = new ModelColumn<>(I_Fact_Acct_UserChange.class, "UserElementString1", null);
	String COLUMNNAME_UserElementString1 = "UserElementString1";
}
