package org.compiere.model;

import org.adempiere.model.ModelColumn;

/** Generated Interface for C_PaymentTerm_Break
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_C_PaymentTerm_Break 
{

	String Table_Name = "C_PaymentTerm_Break";

//	/** AD_Table_ID=542538 */
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
	 * Set Payment Term Break.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_PaymentTerm_Break_ID (int C_PaymentTerm_Break_ID);

	/**
	 * Get Payment Term Break.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_PaymentTerm_Break_ID();

	ModelColumn<I_C_PaymentTerm_Break, Object> COLUMN_C_PaymentTerm_Break_ID = new ModelColumn<>(I_C_PaymentTerm_Break.class, "C_PaymentTerm_Break_ID", null);
	String COLUMNNAME_C_PaymentTerm_Break_ID = "C_PaymentTerm_Break_ID";

	/**
	 * Set Payment Term.
	 * The terms of Payment (timing, discount)
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_PaymentTerm_ID (int C_PaymentTerm_ID);

	/**
	 * Get Payment Term.
	 * The terms of Payment (timing, discount)
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_PaymentTerm_ID();

	String COLUMNNAME_C_PaymentTerm_ID = "C_PaymentTerm_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_C_PaymentTerm_Break, Object> COLUMN_Created = new ModelColumn<>(I_C_PaymentTerm_Break.class, "Created", null);
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
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDescription (java.lang.String Description);

	/**
	 * Get Description.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	java.lang.String getDescription();

	ModelColumn<I_C_PaymentTerm_Break, Object> COLUMN_Description = new ModelColumn<>(I_C_PaymentTerm_Break.class, "Description", null);
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

	ModelColumn<I_C_PaymentTerm_Break, Object> COLUMN_IsActive = new ModelColumn<>(I_C_PaymentTerm_Break.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Offset days.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setOffsetDays (int OffsetDays);

	/**
	 * Get Offset days.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getOffsetDays();

	ModelColumn<I_C_PaymentTerm_Break, Object> COLUMN_OffsetDays = new ModelColumn<>(I_C_PaymentTerm_Break.class, "OffsetDays", null);
	String COLUMNNAME_OffsetDays = "OffsetDays";

	/**
	 * Set Percent.
	 * Percentage
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPercent (int Percent);

	/**
	 * Get Percent.
	 * Percentage
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getPercent();

	ModelColumn<I_C_PaymentTerm_Break, Object> COLUMN_Percent = new ModelColumn<>(I_C_PaymentTerm_Break.class, "Percent", null);
	String COLUMNNAME_Percent = "Percent";

	/**
	 * Set Reference Date Type.
	 * Specifies the base date type used to calculate the due date of a payment term break (e.g., Invoice Date, Bill of Lading Date, Order Date, Letter of Credit Date, Estimated Arrival Date).
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setReferenceDateType (java.lang.String ReferenceDateType);

	/**
	 * Get Reference Date Type.
	 * Specifies the base date type used to calculate the due date of a payment term break (e.g., Invoice Date, Bill of Lading Date, Order Date, Letter of Credit Date, Estimated Arrival Date).
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getReferenceDateType();

	ModelColumn<I_C_PaymentTerm_Break, Object> COLUMN_ReferenceDateType = new ModelColumn<>(I_C_PaymentTerm_Break.class, "ReferenceDateType", null);
	String COLUMNNAME_ReferenceDateType = "ReferenceDateType";

	/**
	 * Set SeqNo..
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setSeqNo (int SeqNo);

	/**
	 * Get SeqNo..
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getSeqNo();

	ModelColumn<I_C_PaymentTerm_Break, Object> COLUMN_SeqNo = new ModelColumn<>(I_C_PaymentTerm_Break.class, "SeqNo", null);
	String COLUMNNAME_SeqNo = "SeqNo";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_C_PaymentTerm_Break, Object> COLUMN_Updated = new ModelColumn<>(I_C_PaymentTerm_Break.class, "Updated", null);
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
