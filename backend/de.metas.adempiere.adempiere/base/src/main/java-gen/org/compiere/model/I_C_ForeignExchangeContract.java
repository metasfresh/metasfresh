package org.compiere.model;

import java.math.BigDecimal;
import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for C_ForeignExchangeContract
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_C_ForeignExchangeContract 
{

	String Table_Name = "C_ForeignExchangeContract";

//	/** AD_Table_ID=542281 */
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
	 * Set Foreign Exchange Contract.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_ForeignExchangeContract_ID (int C_ForeignExchangeContract_ID);

	/**
	 * Get Foreign Exchange Contract.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_ForeignExchangeContract_ID();

	ModelColumn<I_C_ForeignExchangeContract, Object> COLUMN_C_ForeignExchangeContract_ID = new ModelColumn<>(I_C_ForeignExchangeContract.class, "C_ForeignExchangeContract_ID", null);
	String COLUMNNAME_C_ForeignExchangeContract_ID = "C_ForeignExchangeContract_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_C_ForeignExchangeContract, Object> COLUMN_Created = new ModelColumn<>(I_C_ForeignExchangeContract.class, "Created", null);
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

	ModelColumn<I_C_ForeignExchangeContract, Object> COLUMN_Description = new ModelColumn<>(I_C_ForeignExchangeContract.class, "Description", null);
	String COLUMNNAME_Description = "Description";

	/**
	 * Set Process Batch.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDocAction (java.lang.String DocAction);

	/**
	 * Get Process Batch.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getDocAction();

	ModelColumn<I_C_ForeignExchangeContract, Object> COLUMN_DocAction = new ModelColumn<>(I_C_ForeignExchangeContract.class, "DocAction", null);
	String COLUMNNAME_DocAction = "DocAction";

	/**
	 * Set Status.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDocStatus (java.lang.String DocStatus);

	/**
	 * Get Status.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getDocStatus();

	ModelColumn<I_C_ForeignExchangeContract, Object> COLUMN_DocStatus = new ModelColumn<>(I_C_ForeignExchangeContract.class, "DocStatus", null);
	String COLUMNNAME_DocStatus = "DocStatus";

	/**
	 * Set Document No.
	 * Document sequence number of the document
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDocumentNo (java.lang.String DocumentNo);

	/**
	 * Get Document No.
	 * Document sequence number of the document
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getDocumentNo();

	ModelColumn<I_C_ForeignExchangeContract, Object> COLUMN_DocumentNo = new ModelColumn<>(I_C_ForeignExchangeContract.class, "DocumentNo", null);
	String COLUMNNAME_DocumentNo = "DocumentNo";

	/**
	 * Set FEC Amount.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setFEC_Amount (BigDecimal FEC_Amount);

	/**
	 * Get FEC Amount.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getFEC_Amount();

	ModelColumn<I_C_ForeignExchangeContract, Object> COLUMN_FEC_Amount = new ModelColumn<>(I_C_ForeignExchangeContract.class, "FEC_Amount", null);
	String COLUMNNAME_FEC_Amount = "FEC_Amount";

	/**
	 * Set FEC Allocated Amount.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setFEC_Amount_Alloc (BigDecimal FEC_Amount_Alloc);

	/**
	 * Get FEC Allocated Amount.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getFEC_Amount_Alloc();

	ModelColumn<I_C_ForeignExchangeContract, Object> COLUMN_FEC_Amount_Alloc = new ModelColumn<>(I_C_ForeignExchangeContract.class, "FEC_Amount_Alloc", null);
	String COLUMNNAME_FEC_Amount_Alloc = "FEC_Amount_Alloc";

	/**
	 * Set FEC Open Amount.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setFEC_Amount_Open (BigDecimal FEC_Amount_Open);

	/**
	 * Get FEC Open Amount.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getFEC_Amount_Open();

	ModelColumn<I_C_ForeignExchangeContract, Object> COLUMN_FEC_Amount_Open = new ModelColumn<>(I_C_ForeignExchangeContract.class, "FEC_Amount_Open", null);
	String COLUMNNAME_FEC_Amount_Open = "FEC_Amount_Open";

	/**
	 * Set FEC Rate.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setFEC_CurrencyRate (BigDecimal FEC_CurrencyRate);

	/**
	 * Get FEC Rate.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getFEC_CurrencyRate();

	ModelColumn<I_C_ForeignExchangeContract, Object> COLUMN_FEC_CurrencyRate = new ModelColumn<>(I_C_ForeignExchangeContract.class, "FEC_CurrencyRate", null);
	String COLUMNNAME_FEC_CurrencyRate = "FEC_CurrencyRate";

	/**
	 * Set Maturity Date.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setFEC_MaturityDate (java.sql.Timestamp FEC_MaturityDate);

	/**
	 * Get Maturity Date.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getFEC_MaturityDate();

	ModelColumn<I_C_ForeignExchangeContract, Object> COLUMN_FEC_MaturityDate = new ModelColumn<>(I_C_ForeignExchangeContract.class, "FEC_MaturityDate", null);
	String COLUMNNAME_FEC_MaturityDate = "FEC_MaturityDate";

	/**
	 * Set Validity Date.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setFEC_ValidityDate (java.sql.Timestamp FEC_ValidityDate);

	/**
	 * Get Validity Date.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getFEC_ValidityDate();

	ModelColumn<I_C_ForeignExchangeContract, Object> COLUMN_FEC_ValidityDate = new ModelColumn<>(I_C_ForeignExchangeContract.class, "FEC_ValidityDate", null);
	String COLUMNNAME_FEC_ValidityDate = "FEC_ValidityDate";

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

	ModelColumn<I_C_ForeignExchangeContract, Object> COLUMN_IsActive = new ModelColumn<>(I_C_ForeignExchangeContract.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

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

	ModelColumn<I_C_ForeignExchangeContract, Object> COLUMN_Processed = new ModelColumn<>(I_C_ForeignExchangeContract.class, "Processed", null);
	String COLUMNNAME_Processed = "Processed";

	/**
	 * Set Process Now.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setProcessing (boolean Processing);

	/**
	 * Get Process Now.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isProcessing();

	ModelColumn<I_C_ForeignExchangeContract, Object> COLUMN_Processing = new ModelColumn<>(I_C_ForeignExchangeContract.class, "Processing", null);
	String COLUMNNAME_Processing = "Processing";

	/**
	 * Set To Currency.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setTo_Currency_ID (int To_Currency_ID);

	/**
	 * Get To Currency.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getTo_Currency_ID();

	String COLUMNNAME_To_Currency_ID = "To_Currency_ID";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_C_ForeignExchangeContract, Object> COLUMN_Updated = new ModelColumn<>(I_C_ForeignExchangeContract.class, "Updated", null);
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
