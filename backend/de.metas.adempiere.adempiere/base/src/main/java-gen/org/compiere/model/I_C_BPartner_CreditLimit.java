package org.compiere.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;
import java.math.BigDecimal;

/** Generated Interface for C_BPartner_CreditLimit
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_C_BPartner_CreditLimit 
{

	String Table_Name = "C_BPartner_CreditLimit";

//	/** AD_Table_ID=540929 */
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
	 * Set Org Mapping.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_Org_Mapping_ID (int AD_Org_Mapping_ID);

	/**
	 * Get Org Mapping.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_Org_Mapping_ID();

	@Nullable org.compiere.model.I_AD_Org_Mapping getAD_Org_Mapping();

	void setAD_Org_Mapping(@Nullable org.compiere.model.I_AD_Org_Mapping AD_Org_Mapping);

	ModelColumn<I_C_BPartner_CreditLimit, org.compiere.model.I_AD_Org_Mapping> COLUMN_AD_Org_Mapping_ID = new ModelColumn<>(I_C_BPartner_CreditLimit.class, "AD_Org_Mapping_ID", org.compiere.model.I_AD_Org_Mapping.class);
	String COLUMNNAME_AD_Org_Mapping_ID = "AD_Org_Mapping_ID";

	/**
	 * Set Amount.
	 * Amount in a defined currency
	 *
	 * <br>Type: Number
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAmount (BigDecimal Amount);

	/**
	 * Get Amount.
	 * Amount in a defined currency
	 *
	 * <br>Type: Number
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getAmount();

	ModelColumn<I_C_BPartner_CreditLimit, Object> COLUMN_Amount = new ModelColumn<>(I_C_BPartner_CreditLimit.class, "Amount", null);
	String COLUMNNAME_Amount = "Amount";

	/**
	 * Set Approved By.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setApprovedBy_ID (int ApprovedBy_ID);

	/**
	 * Get Approved By.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getApprovedBy_ID();

	String COLUMNNAME_ApprovedBy_ID = "ApprovedBy_ID";

	/**
	 * Set Bussines Partner Credit Limit.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_BPartner_CreditLimit_ID (int C_BPartner_CreditLimit_ID);

	/**
	 * Get Bussines Partner Credit Limit.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_BPartner_CreditLimit_ID();

	ModelColumn<I_C_BPartner_CreditLimit, Object> COLUMN_C_BPartner_CreditLimit_ID = new ModelColumn<>(I_C_BPartner_CreditLimit.class, "C_BPartner_CreditLimit_ID", null);
	String COLUMNNAME_C_BPartner_CreditLimit_ID = "C_BPartner_CreditLimit_ID";

	/**
	 * Set Business Partner.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_BPartner_ID (int C_BPartner_ID);

	/**
	 * Get Business Partner.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_BPartner_ID();

	String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/**
	 * Set Type.
	 * Type of Validation (SQL, Java Script, Java Language)
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_CreditLimit_Type_ID (int C_CreditLimit_Type_ID);

	/**
	 * Get Type.
	 * Type of Validation (SQL, Java Script, Java Language)
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_CreditLimit_Type_ID();

	org.compiere.model.I_C_CreditLimit_Type getC_CreditLimit_Type();

	void setC_CreditLimit_Type(org.compiere.model.I_C_CreditLimit_Type C_CreditLimit_Type);

	ModelColumn<I_C_BPartner_CreditLimit, org.compiere.model.I_C_CreditLimit_Type> COLUMN_C_CreditLimit_Type_ID = new ModelColumn<>(I_C_BPartner_CreditLimit.class, "C_CreditLimit_Type_ID", org.compiere.model.I_C_CreditLimit_Type.class);
	String COLUMNNAME_C_CreditLimit_Type_ID = "C_CreditLimit_Type_ID";

	/**
	 * Set Currency.
	 * The Currency for this record
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setC_Currency_ID (int C_Currency_ID);

	/**
	 * Get Currency.
	 * The Currency for this record
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
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

	ModelColumn<I_C_BPartner_CreditLimit, Object> COLUMN_Created = new ModelColumn<>(I_C_BPartner_CreditLimit.class, "Created", null);
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
	 * Set Date From.
	 * Starting date for a range
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDateFrom (@Nullable java.sql.Timestamp DateFrom);

	/**
	 * Get Date From.
	 * Starting date for a range
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getDateFrom();

	ModelColumn<I_C_BPartner_CreditLimit, Object> COLUMN_DateFrom = new ModelColumn<>(I_C_BPartner_CreditLimit.class, "DateFrom", null);
	String COLUMNNAME_DateFrom = "DateFrom";

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

	ModelColumn<I_C_BPartner_CreditLimit, Object> COLUMN_IsActive = new ModelColumn<>(I_C_BPartner_CreditLimit.class, "IsActive", null);
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

	ModelColumn<I_C_BPartner_CreditLimit, Object> COLUMN_Processed = new ModelColumn<>(I_C_BPartner_CreditLimit.class, "Processed", null);
	String COLUMNNAME_Processed = "Processed";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_C_BPartner_CreditLimit, Object> COLUMN_Updated = new ModelColumn<>(I_C_BPartner_CreditLimit.class, "Updated", null);
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
