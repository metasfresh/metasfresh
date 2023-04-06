package org.compiere.model;

import java.math.BigDecimal;
import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for C_PaymentTerm
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_C_PaymentTerm 
{

	String Table_Name = "C_PaymentTerm";

//	/** AD_Table_ID=113 */
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
	 * Set Special Baseline Date Determination.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setBaseLineType (java.lang.String BaseLineType);

	/**
	 * Get Special Baseline Date Determination.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getBaseLineType();

	ModelColumn<I_C_PaymentTerm, Object> COLUMN_BaseLineType = new ModelColumn<>(I_C_PaymentTerm.class, "BaseLineType", null);
	String COLUMNNAME_BaseLineType = "BaseLineType";

	/**
	 * Set Calculation Method.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setCalculationMethod (java.lang.String CalculationMethod);

	/**
	 * Get Calculation Method.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getCalculationMethod();

	ModelColumn<I_C_PaymentTerm, Object> COLUMN_CalculationMethod = new ModelColumn<>(I_C_PaymentTerm.class, "CalculationMethod", null);
	String COLUMNNAME_CalculationMethod = "CalculationMethod";

	/**
	 * Set Payment Term.
	 * The terms of Payment (timing, discount)
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_PaymentTerm_ID (int C_PaymentTerm_ID);

	/**
	 * Get Payment Term.
	 * The terms of Payment (timing, discount)
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_PaymentTerm_ID();

	ModelColumn<I_C_PaymentTerm, Object> COLUMN_C_PaymentTerm_ID = new ModelColumn<>(I_C_PaymentTerm.class, "C_PaymentTerm_ID", null);
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

	ModelColumn<I_C_PaymentTerm, Object> COLUMN_Created = new ModelColumn<>(I_C_PaymentTerm.class, "Created", null);
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
	void setDescription (@Nullable java.lang.String Description);

	/**
	 * Get Description.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDescription();

	ModelColumn<I_C_PaymentTerm, Object> COLUMN_Description = new ModelColumn<>(I_C_PaymentTerm.class, "Description", null);
	String COLUMNNAME_Description = "Description";

	/**
	 * Set Discount %.
	 * Discount in percent
	 *
	 * <br>Type: Number
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDiscount (BigDecimal Discount);

	/**
	 * Get Discount %.
	 * Discount in percent
	 *
	 * <br>Type: Number
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getDiscount();

	ModelColumn<I_C_PaymentTerm, Object> COLUMN_Discount = new ModelColumn<>(I_C_PaymentTerm.class, "Discount", null);
	String COLUMNNAME_Discount = "Discount";

	/**
	 * Set Discount 2 %.
	 * Discount in percent
	 *
	 * <br>Type: Number
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDiscount2 (BigDecimal Discount2);

	/**
	 * Get Discount 2 %.
	 * Discount in percent
	 *
	 * <br>Type: Number
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getDiscount2();

	ModelColumn<I_C_PaymentTerm, Object> COLUMN_Discount2 = new ModelColumn<>(I_C_PaymentTerm.class, "Discount2", null);
	String COLUMNNAME_Discount2 = "Discount2";

	/**
	 * Set Discount Days.
	 * Number of days from invoice date to be eligible for discount
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDiscountDays (int DiscountDays);

	/**
	 * Get Discount Days.
	 * Number of days from invoice date to be eligible for discount
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getDiscountDays();

	ModelColumn<I_C_PaymentTerm, Object> COLUMN_DiscountDays = new ModelColumn<>(I_C_PaymentTerm.class, "DiscountDays", null);
	String COLUMNNAME_DiscountDays = "DiscountDays";

	/**
	 * Set Discount Days 2.
	 * Number of days from invoice date to be eligible for discount
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDiscountDays2 (int DiscountDays2);

	/**
	 * Get Discount Days 2.
	 * Number of days from invoice date to be eligible for discount
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getDiscountDays2();

	ModelColumn<I_C_PaymentTerm, Object> COLUMN_DiscountDays2 = new ModelColumn<>(I_C_PaymentTerm.class, "DiscountDays2", null);
	String COLUMNNAME_DiscountDays2 = "DiscountDays2";

	/**
	 * Set Note.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDocumentNote (@Nullable java.lang.String DocumentNote);

	/**
	 * Get Note.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDocumentNote();

	ModelColumn<I_C_PaymentTerm, Object> COLUMN_DocumentNote = new ModelColumn<>(I_C_PaymentTerm.class, "DocumentNote", null);
	String COLUMNNAME_DocumentNote = "DocumentNote";

	/**
	 * Set External ID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setExternalId (@Nullable java.lang.String ExternalId);

	/**
	 * Get External ID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getExternalId();

	ModelColumn<I_C_PaymentTerm, Object> COLUMN_ExternalId = new ModelColumn<>(I_C_PaymentTerm.class, "ExternalId", null);
	String COLUMNNAME_ExternalId = "ExternalId";

	/**
	 * Set Grace Days.
	 * Days after due date to send first dunning letter
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setGraceDays (int GraceDays);

	/**
	 * Get Grace Days.
	 * Days after due date to send first dunning letter
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getGraceDays();

	ModelColumn<I_C_PaymentTerm, Object> COLUMN_GraceDays = new ModelColumn<>(I_C_PaymentTerm.class, "GraceDays", null);
	String COLUMNNAME_GraceDays = "GraceDays";

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

	ModelColumn<I_C_PaymentTerm, Object> COLUMN_IsActive = new ModelColumn<>(I_C_PaymentTerm.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Allow overriding due date.
	 * If ticked, the due date determined at the time of invoicing based on this payment term can be overridden.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsAllowOverrideDueDate (boolean IsAllowOverrideDueDate);

	/**
	 * Get Allow overriding due date.
	 * If ticked, the due date determined at the time of invoicing based on this payment term can be overridden.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isAllowOverrideDueDate();

	ModelColumn<I_C_PaymentTerm, Object> COLUMN_IsAllowOverrideDueDate = new ModelColumn<>(I_C_PaymentTerm.class, "IsAllowOverrideDueDate", null);
	String COLUMNNAME_IsAllowOverrideDueDate = "IsAllowOverrideDueDate";

	/**
	 * Set Default.
	 * Default value
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIsDefault (boolean IsDefault);

	/**
	 * Get Default.
	 * Default value
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isDefault();

	ModelColumn<I_C_PaymentTerm, Object> COLUMN_IsDefault = new ModelColumn<>(I_C_PaymentTerm.class, "IsDefault", null);
	String COLUMNNAME_IsDefault = "IsDefault";

	/**
	 * Set Is Valid.
	 * The element is valid
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsValid (boolean IsValid);

	/**
	 * Get Is Valid.
	 * The element is valid
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isValid();

	ModelColumn<I_C_PaymentTerm, Object> COLUMN_IsValid = new ModelColumn<>(I_C_PaymentTerm.class, "IsValid", null);
	String COLUMNNAME_IsValid = "IsValid";

	/**
	 * Set Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setName (java.lang.String Name);

	/**
	 * Get Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getName();

	ModelColumn<I_C_PaymentTerm, Object> COLUMN_Name = new ModelColumn<>(I_C_PaymentTerm.class, "Name", null);
	String COLUMNNAME_Name = "Name";

	/**
	 * Set Name on Invoice.
	 * Alphanumeric identifier of the entity
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setName_Invoice (@Nullable java.lang.String Name_Invoice);

	/**
	 * Get Name on Invoice.
	 * Alphanumeric identifier of the entity
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getName_Invoice();

	ModelColumn<I_C_PaymentTerm, Object> COLUMN_Name_Invoice = new ModelColumn<>(I_C_PaymentTerm.class, "Name_Invoice", null);
	String COLUMNNAME_Name_Invoice = "Name_Invoice";

	/**
	 * Set Net Day.
	 * Day when payment is due net
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setNetDay (@Nullable java.lang.String NetDay);

	/**
	 * Get Net Day.
	 * Day when payment is due net
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getNetDay();

	ModelColumn<I_C_PaymentTerm, Object> COLUMN_NetDay = new ModelColumn<>(I_C_PaymentTerm.class, "NetDay", null);
	String COLUMNNAME_NetDay = "NetDay";

	/**
	 * Set Net Days.
	 * Net Days in which payment is due
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setNetDays (int NetDays);

	/**
	 * Get Net Days.
	 * Net Days in which payment is due
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getNetDays();

	ModelColumn<I_C_PaymentTerm, Object> COLUMN_NetDays = new ModelColumn<>(I_C_PaymentTerm.class, "NetDays", null);
	String COLUMNNAME_NetDays = "NetDays";

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

	ModelColumn<I_C_PaymentTerm, Object> COLUMN_Processing = new ModelColumn<>(I_C_PaymentTerm.class, "Processing", null);
	String COLUMNNAME_Processing = "Processing";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_C_PaymentTerm, Object> COLUMN_Updated = new ModelColumn<>(I_C_PaymentTerm.class, "Updated", null);
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
	 * Set Search Key.
	 * Search key for the record in the format required - must be unique
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setValue (java.lang.String Value);

	/**
	 * Get Search Key.
	 * Search key for the record in the format required - must be unique
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getValue();

	ModelColumn<I_C_PaymentTerm, Object> COLUMN_Value = new ModelColumn<>(I_C_PaymentTerm.class, "Value", null);
	String COLUMNNAME_Value = "Value";
}
