package org.compiere.model;

import java.math.BigDecimal;
import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for C_BPartner_CreditLimit_Department_Lines_V
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_C_BPartner_CreditLimit_Department_Lines_V 
{

	String Table_Name = "C_BPartner_CreditLimit_Department_Lines_V";

//	/** AD_Table_ID=542289 */
//	int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);


	/**
	 * Get Client.
	 * Client/Tenant for this installation.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_Client_ID();

	String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Organisation.
	 * Organisational entity within client
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Organisation.
	 * Organisational entity within client
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_Org_ID();

	String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Amount.
	 * Amount in a defined currency
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAmount (@Nullable BigDecimal Amount);

	/**
	 * Get Amount.
	 * Amount in a defined currency
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getAmount();

	ModelColumn<I_C_BPartner_CreditLimit_Department_Lines_V, Object> COLUMN_Amount = new ModelColumn<>(I_C_BPartner_CreditLimit_Department_Lines_V.class, "Amount", null);
	String COLUMNNAME_Amount = "Amount";

	/**
	 * Set Approved By.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setApprovedBy_ID (int ApprovedBy_ID);

	/**
	 * Get Approved By.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getApprovedBy_ID();

	String COLUMNNAME_ApprovedBy_ID = "ApprovedBy_ID";

	/**
	 * Set Credit Limit (Department).
	 *
	 * <br>Type: ID
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_BPartner_CreditLimit_Department_Lines_V_ID (int C_BPartner_CreditLimit_Department_Lines_V_ID);

	/**
	 * Get Credit Limit (Department).
	 *
	 * <br>Type: ID
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_BPartner_CreditLimit_Department_Lines_V_ID();

	ModelColumn<I_C_BPartner_CreditLimit_Department_Lines_V, Object> COLUMN_C_BPartner_CreditLimit_Department_Lines_V_ID = new ModelColumn<>(I_C_BPartner_CreditLimit_Department_Lines_V.class, "C_BPartner_CreditLimit_Department_Lines_V_ID", null);
	String COLUMNNAME_C_BPartner_CreditLimit_Department_Lines_V_ID = "C_BPartner_CreditLimit_Department_Lines_V_ID";

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
	 * Set Type.
	 * Type of Validation (SQL, Java Script, Java Language)
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_CreditLimit_Type_ID (int C_CreditLimit_Type_ID);

	/**
	 * Get Type.
	 * Type of Validation (SQL, Java Script, Java Language)
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_CreditLimit_Type_ID();

	@Nullable org.compiere.model.I_C_CreditLimit_Type getC_CreditLimit_Type();

	void setC_CreditLimit_Type(@Nullable org.compiere.model.I_C_CreditLimit_Type C_CreditLimit_Type);

	ModelColumn<I_C_BPartner_CreditLimit_Department_Lines_V, org.compiere.model.I_C_CreditLimit_Type> COLUMN_C_CreditLimit_Type_ID = new ModelColumn<>(I_C_BPartner_CreditLimit_Department_Lines_V.class, "C_CreditLimit_Type_ID", org.compiere.model.I_C_CreditLimit_Type.class);
	String COLUMNNAME_C_CreditLimit_Type_ID = "C_CreditLimit_Type_ID";

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
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getCreated();

	ModelColumn<I_C_BPartner_CreditLimit_Department_Lines_V, Object> COLUMN_Created = new ModelColumn<>(I_C_BPartner_CreditLimit_Department_Lines_V.class, "Created", null);
	String COLUMNNAME_Created = "Created";

	/**
	 * Get Created By.
	 * User who created this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getCreatedBy();

	String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Date From.
	 * Starting date for a range
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDateFrom (@Nullable java.sql.Timestamp DateFrom);

	/**
	 * Get Date From.
	 * Starting date for a range
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getDateFrom();

	ModelColumn<I_C_BPartner_CreditLimit_Department_Lines_V, Object> COLUMN_DateFrom = new ModelColumn<>(I_C_BPartner_CreditLimit_Department_Lines_V.class, "DateFrom", null);
	String COLUMNNAME_DateFrom = "DateFrom";

	/**
	 * Set Active.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIsActive (boolean IsActive);

	/**
	 * Get Active.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isActive();

	ModelColumn<I_C_BPartner_CreditLimit_Department_Lines_V, Object> COLUMN_IsActive = new ModelColumn<>(I_C_BPartner_CreditLimit_Department_Lines_V.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Department.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_Department_ID (int M_Department_ID);

	/**
	 * Get Department.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_Department_ID();

	@Nullable org.compiere.model.I_M_Department getM_Department();

	void setM_Department(@Nullable org.compiere.model.I_M_Department M_Department);

	ModelColumn<I_C_BPartner_CreditLimit_Department_Lines_V, org.compiere.model.I_M_Department> COLUMN_M_Department_ID = new ModelColumn<>(I_C_BPartner_CreditLimit_Department_Lines_V.class, "M_Department_ID", org.compiere.model.I_M_Department.class);
	String COLUMNNAME_M_Department_ID = "M_Department_ID";

	/**
	 * Set Processed.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setProcessed (boolean Processed);

	/**
	 * Get Processed.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isProcessed();

	ModelColumn<I_C_BPartner_CreditLimit_Department_Lines_V, Object> COLUMN_Processed = new ModelColumn<>(I_C_BPartner_CreditLimit_Department_Lines_V.class, "Processed", null);
	String COLUMNNAME_Processed = "Processed";

	/**
	 * Set Section Group Partner.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setSection_Group_Partner_ID (int Section_Group_Partner_ID);

	/**
	 * Get Section Group Partner.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getSection_Group_Partner_ID();

	String COLUMNNAME_Section_Group_Partner_ID = "Section_Group_Partner_ID";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getUpdated();

	ModelColumn<I_C_BPartner_CreditLimit_Department_Lines_V, Object> COLUMN_Updated = new ModelColumn<>(I_C_BPartner_CreditLimit_Department_Lines_V.class, "Updated", null);
	String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Updated By.
	 * User who updated this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getUpdatedBy();

	String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
