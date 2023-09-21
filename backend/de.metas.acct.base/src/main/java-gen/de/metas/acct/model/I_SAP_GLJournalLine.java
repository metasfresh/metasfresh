package de.metas.acct.model;

import java.math.BigDecimal;
import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for SAP_GLJournalLine
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_SAP_GLJournalLine 
{

	String Table_Name = "SAP_GLJournalLine";

//	/** AD_Table_ID=542276 */
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
	 * Set Amount.
	 * Amount in a defined currency
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAmount (BigDecimal Amount);

	/**
	 * Get Amount.
	 * Amount in a defined currency
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getAmount();

	ModelColumn<I_SAP_GLJournalLine, Object> COLUMN_Amount = new ModelColumn<>(I_SAP_GLJournalLine.class, "Amount", null);
	String COLUMNNAME_Amount = "Amount";

	/**
	 * Set Accounted Amount.
	 * Amount Balance in Currency of Accounting Schema
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAmtAcct (BigDecimal AmtAcct);

	/**
	 * Get Accounted Amount.
	 * Amount Balance in Currency of Accounting Schema
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getAmtAcct();

	ModelColumn<I_SAP_GLJournalLine, Object> COLUMN_AmtAcct = new ModelColumn<>(I_SAP_GLJournalLine.class, "AmtAcct", null);
	String COLUMNNAME_AmtAcct = "AmtAcct";

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
	 * Set Business Partner.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_BPartner_ID (int C_BPartner_ID);

	/**
	 * Get Business Partner.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_BPartner_ID();

	String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

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

	ModelColumn<I_SAP_GLJournalLine, org.compiere.model.I_C_Order> COLUMN_C_OrderSO_ID = new ModelColumn<>(I_SAP_GLJournalLine.class, "C_OrderSO_ID", org.compiere.model.I_C_Order.class);
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

	ModelColumn<I_SAP_GLJournalLine, Object> COLUMN_Created = new ModelColumn<>(I_SAP_GLJournalLine.class, "Created", null);
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
	 * Set Combination.
	 * Valid Account Combination
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_ValidCombination_ID (int C_ValidCombination_ID);

	/**
	 * Get Combination.
	 * Valid Account Combination
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_ValidCombination_ID();

	org.compiere.model.I_C_ValidCombination getC_ValidCombination();

	void setC_ValidCombination(org.compiere.model.I_C_ValidCombination C_ValidCombination);

	ModelColumn<I_SAP_GLJournalLine, org.compiere.model.I_C_ValidCombination> COLUMN_C_ValidCombination_ID = new ModelColumn<>(I_SAP_GLJournalLine.class, "C_ValidCombination_ID", org.compiere.model.I_C_ValidCombination.class);
	String COLUMNNAME_C_ValidCombination_ID = "C_ValidCombination_ID";

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

	ModelColumn<I_SAP_GLJournalLine, Object> COLUMN_Description = new ModelColumn<>(I_SAP_GLJournalLine.class, "Description", null);
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

	ModelColumn<I_SAP_GLJournalLine, Object> COLUMN_IsActive = new ModelColumn<>(I_SAP_GLJournalLine.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Fields are Read Only for User.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsFieldsReadOnlyInUI (boolean IsFieldsReadOnlyInUI);

	/**
	 * Get Fields are Read Only for User.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isFieldsReadOnlyInUI();

	ModelColumn<I_SAP_GLJournalLine, Object> COLUMN_IsFieldsReadOnlyInUI = new ModelColumn<>(I_SAP_GLJournalLine.class, "IsFieldsReadOnlyInUI", null);
	String COLUMNNAME_IsFieldsReadOnlyInUI = "IsFieldsReadOnlyInUI";

	/**
	 * Set Open Item Managed.
	 * This indicator shows that the account selected is an open item managed account.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsOpenItem (boolean IsOpenItem);

	/**
	 * Get Open Item Managed.
	 * This indicator shows that the account selected is an open item managed account.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isOpenItem();

	ModelColumn<I_SAP_GLJournalLine, Object> COLUMN_IsOpenItem = new ModelColumn<>(I_SAP_GLJournalLine.class, "IsOpenItem", null);
	String COLUMNNAME_IsOpenItem = "IsOpenItem";

	/**
	 * Set Price incl. Tax.
	 * Tax is included in the price
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIsTaxIncluded (boolean IsTaxIncluded);

	/**
	 * Get Price incl. Tax.
	 * Tax is included in the price
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isTaxIncluded();

	ModelColumn<I_SAP_GLJournalLine, Object> COLUMN_IsTaxIncluded = new ModelColumn<>(I_SAP_GLJournalLine.class, "IsTaxIncluded", null);
	String COLUMNNAME_IsTaxIncluded = "IsTaxIncluded";

	/**
	 * Set SeqNo..
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setLine (int Line);

	/**
	 * Get SeqNo..
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getLine();

	ModelColumn<I_SAP_GLJournalLine, Object> COLUMN_Line = new ModelColumn<>(I_SAP_GLJournalLine.class, "Line", null);
	String COLUMNNAME_Line = "Line";

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
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_SectionCode_ID (int M_SectionCode_ID);

	/**
	 * Get Section Code.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_SectionCode_ID();

	org.compiere.model.I_M_SectionCode getM_SectionCode();

	void setM_SectionCode(org.compiere.model.I_M_SectionCode M_SectionCode);

	ModelColumn<I_SAP_GLJournalLine, org.compiere.model.I_M_SectionCode> COLUMN_M_SectionCode_ID = new ModelColumn<>(I_SAP_GLJournalLine.class, "M_SectionCode_ID", org.compiere.model.I_M_SectionCode.class);
	String COLUMNNAME_M_SectionCode_ID = "M_SectionCode_ID";

	/**
	 * Set OI Clearing Account conceptual name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setOI_AccountConceptualName (@Nullable java.lang.String OI_AccountConceptualName);

	/**
	 * Get OI Clearing Account conceptual name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getOI_AccountConceptualName();

	ModelColumn<I_SAP_GLJournalLine, Object> COLUMN_OI_AccountConceptualName = new ModelColumn<>(I_SAP_GLJournalLine.class, "OI_AccountConceptualName", null);
	String COLUMNNAME_OI_AccountConceptualName = "OI_AccountConceptualName";

	/**
	 * Set OI Clearing Bank Statement.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setOI_BankStatement_ID (int OI_BankStatement_ID);

	/**
	 * Get OI Clearing Bank Statement.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getOI_BankStatement_ID();

	String COLUMNNAME_OI_BankStatement_ID = "OI_BankStatement_ID";

	/**
	 * Set OI Clearing Bank Statement Line.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setOI_BankStatementLine_ID (int OI_BankStatementLine_ID);

	/**
	 * Get OI Clearing Bank Statement Line.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getOI_BankStatementLine_ID();

	String COLUMNNAME_OI_BankStatementLine_ID = "OI_BankStatementLine_ID";

	/**
	 * Set OI Clearing Bank Statement Line Reference.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setOI_BankStatementLine_Ref_ID (int OI_BankStatementLine_Ref_ID);

	/**
	 * Get OI Clearing Bank Statement Line Reference.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getOI_BankStatementLine_Ref_ID();

	ModelColumn<I_SAP_GLJournalLine, Object> COLUMN_OI_BankStatementLine_Ref_ID = new ModelColumn<>(I_SAP_GLJournalLine.class, "OI_BankStatementLine_Ref_ID", null);
	String COLUMNNAME_OI_BankStatementLine_Ref_ID = "OI_BankStatementLine_Ref_ID";

	/**
	 * Set OI Clearing Invoice.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setOI_Invoice_ID (int OI_Invoice_ID);

	/**
	 * Get OI Clearing Invoice.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getOI_Invoice_ID();

	@Nullable org.compiere.model.I_C_Invoice getOI_Invoice();

	void setOI_Invoice(@Nullable org.compiere.model.I_C_Invoice OI_Invoice);

	ModelColumn<I_SAP_GLJournalLine, org.compiere.model.I_C_Invoice> COLUMN_OI_Invoice_ID = new ModelColumn<>(I_SAP_GLJournalLine.class, "OI_Invoice_ID", org.compiere.model.I_C_Invoice.class);
	String COLUMNNAME_OI_Invoice_ID = "OI_Invoice_ID";

	/**
	 * Set OI Clearing Payment.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setOI_Payment_ID (int OI_Payment_ID);

	/**
	 * Get OI Clearing Payment.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getOI_Payment_ID();

	String COLUMNNAME_OI_Payment_ID = "OI_Payment_ID";

	/**
	 * Set Open Item Transaction Type.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setOI_TrxType (@Nullable java.lang.String OI_TrxType);

	/**
	 * Get Open Item Transaction Type.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getOI_TrxType();

	ModelColumn<I_SAP_GLJournalLine, Object> COLUMN_OI_TrxType = new ModelColumn<>(I_SAP_GLJournalLine.class, "OI_TrxType", null);
	String COLUMNNAME_OI_TrxType = "OI_TrxType";

	/**
	 * Set Open Item Key.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setOpenItemKey (@Nullable java.lang.String OpenItemKey);

	/**
	 * Get Open Item Key.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getOpenItemKey();

	ModelColumn<I_SAP_GLJournalLine, Object> COLUMN_OpenItemKey = new ModelColumn<>(I_SAP_GLJournalLine.class, "OpenItemKey", null);
	String COLUMNNAME_OpenItemKey = "OpenItemKey";

	/**
	 * Set Parent.
	 * Parent of Entity
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setParent_ID (int Parent_ID);

	/**
	 * Get Parent.
	 * Parent of Entity
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getParent_ID();

	@Nullable de.metas.acct.model.I_SAP_GLJournalLine getParent();

	void setParent(@Nullable de.metas.acct.model.I_SAP_GLJournalLine Parent);

	ModelColumn<I_SAP_GLJournalLine, de.metas.acct.model.I_SAP_GLJournalLine> COLUMN_Parent_ID = new ModelColumn<>(I_SAP_GLJournalLine.class, "Parent_ID", de.metas.acct.model.I_SAP_GLJournalLine.class);
	String COLUMNNAME_Parent_ID = "Parent_ID";

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

	ModelColumn<I_SAP_GLJournalLine, Object> COLUMN_PostingSign = new ModelColumn<>(I_SAP_GLJournalLine.class, "PostingSign", null);
	String COLUMNNAME_PostingSign = "PostingSign";

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

	ModelColumn<I_SAP_GLJournalLine, Object> COLUMN_Processed = new ModelColumn<>(I_SAP_GLJournalLine.class, "Processed", null);
	String COLUMNNAME_Processed = "Processed";

	/**
	 * Set Calculate tax.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setSAP_CalculateTax (boolean SAP_CalculateTax);

	/**
	 * Get Calculate tax.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isSAP_CalculateTax();

	ModelColumn<I_SAP_GLJournalLine, Object> COLUMN_SAP_CalculateTax = new ModelColumn<>(I_SAP_GLJournalLine.class, "SAP_CalculateTax", null);
	String COLUMNNAME_SAP_CalculateTax = "SAP_CalculateTax";

	/**
	 * Set Determine tax base.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setSAP_DetermineTaxBase (boolean SAP_DetermineTaxBase);

	/**
	 * Get Determine tax base.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isSAP_DetermineTaxBase();

	ModelColumn<I_SAP_GLJournalLine, Object> COLUMN_SAP_DetermineTaxBase = new ModelColumn<>(I_SAP_GLJournalLine.class, "SAP_DetermineTaxBase", null);
	String COLUMNNAME_SAP_DetermineTaxBase = "SAP_DetermineTaxBase";

	/**
	 * Set GL Journal (SAP).
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setSAP_GLJournal_ID (int SAP_GLJournal_ID);

	/**
	 * Get GL Journal (SAP).
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getSAP_GLJournal_ID();

	de.metas.acct.model.I_SAP_GLJournal getSAP_GLJournal();

	void setSAP_GLJournal(de.metas.acct.model.I_SAP_GLJournal SAP_GLJournal);

	ModelColumn<I_SAP_GLJournalLine, de.metas.acct.model.I_SAP_GLJournal> COLUMN_SAP_GLJournal_ID = new ModelColumn<>(I_SAP_GLJournalLine.class, "SAP_GLJournal_ID", de.metas.acct.model.I_SAP_GLJournal.class);
	String COLUMNNAME_SAP_GLJournal_ID = "SAP_GLJournal_ID";

	/**
	 * Set GL Journal Line (SAP).
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setSAP_GLJournalLine_ID (int SAP_GLJournalLine_ID);

	/**
	 * Get GL Journal Line (SAP).
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getSAP_GLJournalLine_ID();

	ModelColumn<I_SAP_GLJournalLine, Object> COLUMN_SAP_GLJournalLine_ID = new ModelColumn<>(I_SAP_GLJournalLine.class, "SAP_GLJournalLine_ID", null);
	String COLUMNNAME_SAP_GLJournalLine_ID = "SAP_GLJournalLine_ID";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_SAP_GLJournalLine, Object> COLUMN_Updated = new ModelColumn<>(I_SAP_GLJournalLine.class, "Updated", null);
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

	ModelColumn<I_SAP_GLJournalLine, Object> COLUMN_UserElementString1 = new ModelColumn<>(I_SAP_GLJournalLine.class, "UserElementString1", null);
	String COLUMNNAME_UserElementString1 = "UserElementString1";

	/**
	 * Set Ship-from.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setUserElementString2 (@Nullable java.lang.String UserElementString2);

	/**
	 * Get Ship-from.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getUserElementString2();

	ModelColumn<I_SAP_GLJournalLine, Object> COLUMN_UserElementString2 = new ModelColumn<>(I_SAP_GLJournalLine.class, "UserElementString2", null);
	String COLUMNNAME_UserElementString2 = "UserElementString2";

	/**
	 * Set Ship-to.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setUserElementString3 (@Nullable java.lang.String UserElementString3);

	/**
	 * Get Ship-to.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getUserElementString3();

	ModelColumn<I_SAP_GLJournalLine, Object> COLUMN_UserElementString3 = new ModelColumn<>(I_SAP_GLJournalLine.class, "UserElementString3", null);
	String COLUMNNAME_UserElementString3 = "UserElementString3";

	/**
	 * Set UserElementString4.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setUserElementString4 (@Nullable java.lang.String UserElementString4);

	/**
	 * Get UserElementString4.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getUserElementString4();

	ModelColumn<I_SAP_GLJournalLine, Object> COLUMN_UserElementString4 = new ModelColumn<>(I_SAP_GLJournalLine.class, "UserElementString4", null);
	String COLUMNNAME_UserElementString4 = "UserElementString4";

	/**
	 * Set UserElementString5.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setUserElementString5 (@Nullable java.lang.String UserElementString5);

	/**
	 * Get UserElementString5.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getUserElementString5();

	ModelColumn<I_SAP_GLJournalLine, Object> COLUMN_UserElementString5 = new ModelColumn<>(I_SAP_GLJournalLine.class, "UserElementString5", null);
	String COLUMNNAME_UserElementString5 = "UserElementString5";

	/**
	 * Set UserElementString6.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setUserElementString6 (@Nullable java.lang.String UserElementString6);

	/**
	 * Get UserElementString6.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getUserElementString6();

	ModelColumn<I_SAP_GLJournalLine, Object> COLUMN_UserElementString6 = new ModelColumn<>(I_SAP_GLJournalLine.class, "UserElementString6", null);
	String COLUMNNAME_UserElementString6 = "UserElementString6";

	/**
	 * Set UserElementString7.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setUserElementString7 (@Nullable java.lang.String UserElementString7);

	/**
	 * Get UserElementString7.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getUserElementString7();

	ModelColumn<I_SAP_GLJournalLine, Object> COLUMN_UserElementString7 = new ModelColumn<>(I_SAP_GLJournalLine.class, "UserElementString7", null);
	String COLUMNNAME_UserElementString7 = "UserElementString7";
}
