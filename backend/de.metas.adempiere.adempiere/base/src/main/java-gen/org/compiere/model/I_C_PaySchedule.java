package org.compiere.model;

import java.math.BigDecimal;
import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for C_PaySchedule
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_C_PaySchedule 
{

	String Table_Name = "C_PaySchedule";

//	/** AD_Table_ID=548 */
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
	 * Set Payment Schedule.
	 * Payment Schedule Template
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_PaySchedule_ID (int C_PaySchedule_ID);

	/**
	 * Get Payment Schedule.
	 * Payment Schedule Template
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_PaySchedule_ID();

	ModelColumn<I_C_PaySchedule, Object> COLUMN_C_PaySchedule_ID = new ModelColumn<>(I_C_PaySchedule.class, "C_PaySchedule_ID", null);
	String COLUMNNAME_C_PaySchedule_ID = "C_PaySchedule_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_C_PaySchedule, Object> COLUMN_Created = new ModelColumn<>(I_C_PaySchedule.class, "Created", null);
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

	ModelColumn<I_C_PaySchedule, Object> COLUMN_Discount = new ModelColumn<>(I_C_PaySchedule.class, "Discount", null);
	String COLUMNNAME_Discount = "Discount";

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

	ModelColumn<I_C_PaySchedule, Object> COLUMN_DiscountDays = new ModelColumn<>(I_C_PaySchedule.class, "DiscountDays", null);
	String COLUMNNAME_DiscountDays = "DiscountDays";

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

	ModelColumn<I_C_PaySchedule, Object> COLUMN_GraceDays = new ModelColumn<>(I_C_PaySchedule.class, "GraceDays", null);
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

	ModelColumn<I_C_PaySchedule, Object> COLUMN_IsActive = new ModelColumn<>(I_C_PaySchedule.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

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

	ModelColumn<I_C_PaySchedule, Object> COLUMN_IsValid = new ModelColumn<>(I_C_PaySchedule.class, "IsValid", null);
	String COLUMNNAME_IsValid = "IsValid";

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

	ModelColumn<I_C_PaySchedule, Object> COLUMN_NetDay = new ModelColumn<>(I_C_PaySchedule.class, "NetDay", null);
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

	ModelColumn<I_C_PaySchedule, Object> COLUMN_NetDays = new ModelColumn<>(I_C_PaySchedule.class, "NetDays", null);
	String COLUMNNAME_NetDays = "NetDays";

	/**
	 * Set Percentage.
	 * Percent of the entire amount
	 *
	 * <br>Type: Number
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPercentage (BigDecimal Percentage);

	/**
	 * Get Percentage.
	 * Percent of the entire amount
	 *
	 * <br>Type: Number
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getPercentage();

	ModelColumn<I_C_PaySchedule, Object> COLUMN_Percentage = new ModelColumn<>(I_C_PaySchedule.class, "Percentage", null);
	String COLUMNNAME_Percentage = "Percentage";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_C_PaySchedule, Object> COLUMN_Updated = new ModelColumn<>(I_C_PaySchedule.class, "Updated", null);
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
