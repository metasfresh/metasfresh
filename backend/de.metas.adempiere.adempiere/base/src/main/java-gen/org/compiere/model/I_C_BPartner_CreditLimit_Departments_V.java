package org.compiere.model;

import java.math.BigDecimal;
import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for C_BPartner_CreditLimit_Departments_V
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_C_BPartner_CreditLimit_Departments_V 
{

	String Table_Name = "C_BPartner_CreditLimit_Departments_V";

//	/** AD_Table_ID=542288 */
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
	 * Set Credit Limit (Departments).
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_BPartner_CreditLimit_Departments_v_ID (int C_BPartner_CreditLimit_Departments_v_ID);

	/**
	 * Get Credit Limit (Departments).
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_BPartner_CreditLimit_Departments_v_ID();

	ModelColumn<I_C_BPartner_CreditLimit_Departments_V, Object> COLUMN_C_BPartner_CreditLimit_Departments_v_ID = new ModelColumn<>(I_C_BPartner_CreditLimit_Departments_V.class, "C_BPartner_CreditLimit_Departments_v_ID", null);
	String COLUMNNAME_C_BPartner_CreditLimit_Departments_v_ID = "C_BPartner_CreditLimit_Departments_v_ID";

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

	ModelColumn<I_C_BPartner_CreditLimit_Departments_V, Object> COLUMN_Created = new ModelColumn<>(I_C_BPartner_CreditLimit_Departments_V.class, "Created", null);
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
	 * Set Credit limit.
	 * Amount of Credit allowed
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCreditLimit (@Nullable BigDecimal CreditLimit);

	/**
	 * Get Credit limit.
	 * Amount of Credit allowed
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getCreditLimit();

	ModelColumn<I_C_BPartner_CreditLimit_Departments_V, Object> COLUMN_CreditLimit = new ModelColumn<>(I_C_BPartner_CreditLimit_Departments_V.class, "CreditLimit", null);
	String COLUMNNAME_CreditLimit = "CreditLimit";

	/**
	 * Set Delivery Credit Used.
	 * Specifies the amount of money that was already spent from the credit for completed delivery instructions.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDelivery_CreditUsed (@Nullable BigDecimal Delivery_CreditUsed);

	/**
	 * Get Delivery Credit Used.
	 * Specifies the amount of money that was already spent from the credit for completed delivery instructions.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getDelivery_CreditUsed();

	ModelColumn<I_C_BPartner_CreditLimit_Departments_V, Object> COLUMN_Delivery_CreditUsed = new ModelColumn<>(I_C_BPartner_CreditLimit_Departments_V.class, "Delivery_CreditUsed", null);
	String COLUMNNAME_Delivery_CreditUsed = "Delivery_CreditUsed";

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

	ModelColumn<I_C_BPartner_CreditLimit_Departments_V, Object> COLUMN_IsActive = new ModelColumn<>(I_C_BPartner_CreditLimit_Departments_V.class, "IsActive", null);
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

	ModelColumn<I_C_BPartner_CreditLimit_Departments_V, org.compiere.model.I_M_Department> COLUMN_M_Department_ID = new ModelColumn<>(I_C_BPartner_CreditLimit_Departments_V.class, "M_Department_ID", org.compiere.model.I_M_Department.class);
	String COLUMNNAME_M_Department_ID = "M_Department_ID";

	/**
	 * Set Order Open Amount (Department).
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_Department_Order_OpenAmt (@Nullable BigDecimal M_Department_Order_OpenAmt);

	/**
	 * Get Order Open Amount (Department).
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getM_Department_Order_OpenAmt();

	ModelColumn<I_C_BPartner_CreditLimit_Departments_V, Object> COLUMN_M_Department_Order_OpenAmt = new ModelColumn<>(I_C_BPartner_CreditLimit_Departments_V.class, "M_Department_Order_OpenAmt", null);
	String COLUMNNAME_M_Department_Order_OpenAmt = "M_Department_Order_OpenAmt";

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

	ModelColumn<I_C_BPartner_CreditLimit_Departments_V, org.compiere.model.I_M_SectionCode> COLUMN_M_SectionCode_ID = new ModelColumn<>(I_C_BPartner_CreditLimit_Departments_V.class, "M_SectionCode_ID", org.compiere.model.I_M_SectionCode.class);
	String COLUMNNAME_M_SectionCode_ID = "M_SectionCode_ID";

	/**
	 * Set Open Balance.
	 * Total Open Balance Amount in primary Accounting Currency
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setOpenItems (@Nullable BigDecimal OpenItems);

	/**
	 * Get Open Balance.
	 * Total Open Balance Amount in primary Accounting Currency
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getOpenItems();

	ModelColumn<I_C_BPartner_CreditLimit_Departments_V, Object> COLUMN_OpenItems = new ModelColumn<>(I_C_BPartner_CreditLimit_Departments_V.class, "OpenItems", null);
	String COLUMNNAME_OpenItems = "OpenItems";

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
	 * Set Credit Used.
	 * Current open balance
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setSO_CreditUsed (@Nullable BigDecimal SO_CreditUsed);

	/**
	 * Get Credit Used.
	 * Current open balance
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getSO_CreditUsed();

	ModelColumn<I_C_BPartner_CreditLimit_Departments_V, Object> COLUMN_SO_CreditUsed = new ModelColumn<>(I_C_BPartner_CreditLimit_Departments_V.class, "SO_CreditUsed", null);
	String COLUMNNAME_SO_CreditUsed = "SO_CreditUsed";

	/**
	 * Set Total Order Value.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setTotalOrderValue (@Nullable BigDecimal TotalOrderValue);

	/**
	 * Get Total Order Value.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getTotalOrderValue();

	ModelColumn<I_C_BPartner_CreditLimit_Departments_V, Object> COLUMN_TotalOrderValue = new ModelColumn<>(I_C_BPartner_CreditLimit_Departments_V.class, "TotalOrderValue", null);
	String COLUMNNAME_TotalOrderValue = "TotalOrderValue";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getUpdated();

	ModelColumn<I_C_BPartner_CreditLimit_Departments_V, Object> COLUMN_Updated = new ModelColumn<>(I_C_BPartner_CreditLimit_Departments_V.class, "Updated", null);
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
