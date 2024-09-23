package de.metas.pos.repository.model;

import java.math.BigDecimal;
import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for C_POS_Journal
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_C_POS_Journal 
{

	String Table_Name = "C_POS_Journal";

//	/** AD_Table_ID=542438 */
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
	 * Set Cash Beginning Balance.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setCashBeginningBalance (BigDecimal CashBeginningBalance);

	/**
	 * Get Cash Beginning Balance.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getCashBeginningBalance();

	ModelColumn<I_C_POS_Journal, Object> COLUMN_CashBeginningBalance = new ModelColumn<>(I_C_POS_Journal.class, "CashBeginningBalance", null);
	String COLUMNNAME_CashBeginningBalance = "CashBeginningBalance";

	/**
	 * Set Cash Ending Balance.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setCashEndingBalance (BigDecimal CashEndingBalance);

	/**
	 * Get Cash Ending Balance.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getCashEndingBalance();

	ModelColumn<I_C_POS_Journal, Object> COLUMN_CashEndingBalance = new ModelColumn<>(I_C_POS_Journal.class, "CashEndingBalance", null);
	String COLUMNNAME_CashEndingBalance = "CashEndingBalance";

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
	 * Set Closing Note.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setClosingNote (@Nullable java.lang.String ClosingNote);

	/**
	 * Get Closing Note.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getClosingNote();

	ModelColumn<I_C_POS_Journal, Object> COLUMN_ClosingNote = new ModelColumn<>(I_C_POS_Journal.class, "ClosingNote", null);
	String COLUMNNAME_ClosingNote = "ClosingNote";

	/**
	 * Set POS Terminal.
	 * Point of Sales Terminal
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_POS_ID (int C_POS_ID);

	/**
	 * Get POS Terminal.
	 * Point of Sales Terminal
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_POS_ID();

	org.compiere.model.I_C_POS getC_POS();

	void setC_POS(org.compiere.model.I_C_POS C_POS);

	ModelColumn<I_C_POS_Journal, org.compiere.model.I_C_POS> COLUMN_C_POS_ID = new ModelColumn<>(I_C_POS_Journal.class, "C_POS_ID", org.compiere.model.I_C_POS.class);
	String COLUMNNAME_C_POS_ID = "C_POS_ID";

	/**
	 * Set POS Cash Journal.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_POS_Journal_ID (int C_POS_Journal_ID);

	/**
	 * Get POS Cash Journal.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_POS_Journal_ID();

	ModelColumn<I_C_POS_Journal, Object> COLUMN_C_POS_Journal_ID = new ModelColumn<>(I_C_POS_Journal.class, "C_POS_Journal_ID", null);
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

	ModelColumn<I_C_POS_Journal, Object> COLUMN_Created = new ModelColumn<>(I_C_POS_Journal.class, "Created", null);
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
	 * Set Date.
	 * Transaction Date
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDateTrx (java.sql.Timestamp DateTrx);

	/**
	 * Get Date.
	 * Transaction Date
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getDateTrx();

	ModelColumn<I_C_POS_Journal, Object> COLUMN_DateTrx = new ModelColumn<>(I_C_POS_Journal.class, "DateTrx", null);
	String COLUMNNAME_DateTrx = "DateTrx";

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

	ModelColumn<I_C_POS_Journal, Object> COLUMN_IsActive = new ModelColumn<>(I_C_POS_Journal.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Closed.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsClosed (boolean IsClosed);

	/**
	 * Get Closed.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isClosed();

	ModelColumn<I_C_POS_Journal, Object> COLUMN_IsClosed = new ModelColumn<>(I_C_POS_Journal.class, "IsClosed", null);
	String COLUMNNAME_IsClosed = "IsClosed";

	/**
	 * Set Opening Note.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setOpeningNote (@Nullable java.lang.String OpeningNote);

	/**
	 * Get Opening Note.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getOpeningNote();

	ModelColumn<I_C_POS_Journal, Object> COLUMN_OpeningNote = new ModelColumn<>(I_C_POS_Journal.class, "OpeningNote", null);
	String COLUMNNAME_OpeningNote = "OpeningNote";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_C_POS_Journal, Object> COLUMN_Updated = new ModelColumn<>(I_C_POS_Journal.class, "Updated", null);
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
