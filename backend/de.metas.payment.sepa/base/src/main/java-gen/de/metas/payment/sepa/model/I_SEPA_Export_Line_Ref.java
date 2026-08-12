package de.metas.payment.sepa.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;
import java.math.BigDecimal;

/** Generated Interface for SEPA_Export_Line_Ref
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_SEPA_Export_Line_Ref 
{

	String Table_Name = "SEPA_Export_Line_Ref";

//	/** AD_Table_ID=542564 */
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
	 * Set Table.
	 * Database Table information
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_Table_ID (int AD_Table_ID);

	/**
	 * Get Table.
	 * Database Table information
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_Table_ID();

	String COLUMNNAME_AD_Table_ID = "AD_Table_ID";

	/**
	 * Set Amount.
	 * Amount
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAmt (BigDecimal Amt);

	/**
	 * Get Amount.
	 * Amount
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getAmt();

	ModelColumn<I_SEPA_Export_Line_Ref, Object> COLUMN_Amt = new ModelColumn<>(I_SEPA_Export_Line_Ref.class, "Amt", null);
	String COLUMNNAME_Amt = "Amt";

	/**
	 * Set Currency.
	 * The Currency for this record
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Currency_ID (int C_Currency_ID);

	/**
	 * Get Currency.
	 * The Currency for this record
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Currency_ID();

	String COLUMNNAME_C_Currency_ID = "C_Currency_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_SEPA_Export_Line_Ref, Object> COLUMN_Created = new ModelColumn<>(I_SEPA_Export_Line_Ref.class, "Created", null);
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
	void setDescription (@Nullable String Description);

	/**
	 * Get Description.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable String getDescription();

	ModelColumn<I_SEPA_Export_Line_Ref, Object> COLUMN_Description = new ModelColumn<>(I_SEPA_Export_Line_Ref.class, "Description", null);
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

	ModelColumn<I_SEPA_Export_Line_Ref, Object> COLUMN_IsActive = new ModelColumn<>(I_SEPA_Export_Line_Ref.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Record ID.
	 * Direct internal record ID
	 *
	 * <br>Type: Button
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setRecord_ID (int Record_ID);

	/**
	 * Get Record ID.
	 * Direct internal record ID
	 *
	 * <br>Type: Button
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getRecord_ID();

	ModelColumn<I_SEPA_Export_Line_Ref, Object> COLUMN_Record_ID = new ModelColumn<>(I_SEPA_Export_Line_Ref.class, "Record_ID", null);
	String COLUMNNAME_Record_ID = "Record_ID";

	/**
	 * Set SEPA Export.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setSEPA_Export_ID (int SEPA_Export_ID);

	/**
	 * Get SEPA Export.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getSEPA_Export_ID();

	ModelColumn<I_SEPA_Export_Line_Ref, I_SEPA_Export> COLUMN_SEPA_Export_ID = new ModelColumn<>(I_SEPA_Export_Line_Ref.class, "SEPA_Export_ID", I_SEPA_Export.class);
	String COLUMNNAME_SEPA_Export_ID = "SEPA_Export_ID";

	/**
	 * Set SEPA_Export_Line_ID.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setSEPA_Export_Line_ID (int SEPA_Export_Line_ID);

	/**
	 * Get SEPA_Export_Line_ID.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getSEPA_Export_Line_ID();

	ModelColumn<I_SEPA_Export_Line_Ref, I_SEPA_Export_Line> COLUMN_SEPA_Export_Line_ID = new ModelColumn<>(I_SEPA_Export_Line_Ref.class, "SEPA_Export_Line_ID", I_SEPA_Export_Line.class);
	String COLUMNNAME_SEPA_Export_Line_ID = "SEPA_Export_Line_ID";

	/**
	 * Set SEPA  Export Line Reference.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setSEPA_Export_Line_Ref_ID (int SEPA_Export_Line_Ref_ID);

	/**
	 * Get SEPA  Export Line Reference.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getSEPA_Export_Line_Ref_ID();

	ModelColumn<I_SEPA_Export_Line_Ref, Object> COLUMN_SEPA_Export_Line_Ref_ID = new ModelColumn<>(I_SEPA_Export_Line_Ref.class, "SEPA_Export_Line_Ref_ID", null);
	String COLUMNNAME_SEPA_Export_Line_Ref_ID = "SEPA_Export_Line_Ref_ID";

	/**
	 * Set StructuredRemittanceInfo.
	 * Structured Remittance Information
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setStructuredRemittanceInfo (@Nullable String StructuredRemittanceInfo);

	/**
	 * Get StructuredRemittanceInfo.
	 * Structured Remittance Information
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable String getStructuredRemittanceInfo();

	ModelColumn<I_SEPA_Export_Line_Ref, Object> COLUMN_StructuredRemittanceInfo = new ModelColumn<>(I_SEPA_Export_Line_Ref.class, "StructuredRemittanceInfo", null);
	String COLUMNNAME_StructuredRemittanceInfo = "StructuredRemittanceInfo";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_SEPA_Export_Line_Ref, Object> COLUMN_Updated = new ModelColumn<>(I_SEPA_Export_Line_Ref.class, "Updated", null);
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
