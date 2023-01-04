package de.metas.contracts.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;
import java.math.BigDecimal;

/** Generated Interface for C_SubscrDiscount_Line
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_C_SubscrDiscount_Line 
{

	String Table_Name = "C_SubscrDiscount_Line";

//	/** AD_Table_ID=541831 */
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
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_C_SubscrDiscount_Line, Object> COLUMN_Created = new ModelColumn<>(I_C_SubscrDiscount_Line.class, "Created", null);
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
	 * Set Subscription discount.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_SubscrDiscount_ID (int C_SubscrDiscount_ID);

	/**
	 * Get Subscription discount.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_SubscrDiscount_ID();

	@Nullable de.metas.contracts.model.I_C_SubscrDiscount getC_SubscrDiscount();

	void setC_SubscrDiscount(@Nullable de.metas.contracts.model.I_C_SubscrDiscount C_SubscrDiscount);

	ModelColumn<I_C_SubscrDiscount_Line, de.metas.contracts.model.I_C_SubscrDiscount> COLUMN_C_SubscrDiscount_ID = new ModelColumn<>(I_C_SubscrDiscount_Line.class, "C_SubscrDiscount_ID", de.metas.contracts.model.I_C_SubscrDiscount.class);
	String COLUMNNAME_C_SubscrDiscount_ID = "C_SubscrDiscount_ID";

	/**
	 * Set C_SubscrDiscount_Line.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_SubscrDiscount_Line_ID (int C_SubscrDiscount_Line_ID);

	/**
	 * Get C_SubscrDiscount_Line.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_SubscrDiscount_Line_ID();

	ModelColumn<I_C_SubscrDiscount_Line, Object> COLUMN_C_SubscrDiscount_Line_ID = new ModelColumn<>(I_C_SubscrDiscount_Line.class, "C_SubscrDiscount_Line_ID", null);
	String COLUMNNAME_C_SubscrDiscount_Line_ID = "C_SubscrDiscount_Line_ID";

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

	ModelColumn<I_C_SubscrDiscount_Line, Object> COLUMN_Discount = new ModelColumn<>(I_C_SubscrDiscount_Line.class, "Discount", null);
	String COLUMNNAME_Discount = "Discount";

	/**
	 * Set On other discounts.
	 * Decides on what to do if there would already be a discount due to a general discount schema.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIfForeignDiscountsExist (java.lang.String IfForeignDiscountsExist);

	/**
	 * Get On other discounts.
	 * Decides on what to do if there would already be a discount due to a general discount schema.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getIfForeignDiscountsExist();

	ModelColumn<I_C_SubscrDiscount_Line, Object> COLUMN_IfForeignDiscountsExist = new ModelColumn<>(I_C_SubscrDiscount_Line.class, "IfForeignDiscountsExist", null);
	String COLUMNNAME_IfForeignDiscountsExist = "IfForeignDiscountsExist";

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

	ModelColumn<I_C_SubscrDiscount_Line, Object> COLUMN_IsActive = new ModelColumn<>(I_C_SubscrDiscount_Line.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Ends w Calendar year.
	 * If set, then this value needs to match the setting in the contract condition's period setting.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setMatchIfTermEndsWithCalendarYear (boolean MatchIfTermEndsWithCalendarYear);

	/**
	 * Get Ends w Calendar year.
	 * If set, then this value needs to match the setting in the contract condition's period setting.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isMatchIfTermEndsWithCalendarYear();

	ModelColumn<I_C_SubscrDiscount_Line, Object> COLUMN_MatchIfTermEndsWithCalendarYear = new ModelColumn<>(I_C_SubscrDiscount_Line.class, "MatchIfTermEndsWithCalendarYear", null);
	String COLUMNNAME_MatchIfTermEndsWithCalendarYear = "MatchIfTermEndsWithCalendarYear";

	/**
	 * Set Product Category.
	 * Category of a Product
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_Product_Category_ID (int M_Product_Category_ID);

	/**
	 * Get Product Category.
	 * Category of a Product
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_Product_Category_ID();

	String COLUMNNAME_M_Product_Category_ID = "M_Product_Category_ID";

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
	 * Set SeqNo.
	 * Method of ordering records;
 lowest number comes first
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setSeqNo (int SeqNo);

	/**
	 * Get SeqNo.
	 * Method of ordering records;
 lowest number comes first
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getSeqNo();

	ModelColumn<I_C_SubscrDiscount_Line, Object> COLUMN_SeqNo = new ModelColumn<>(I_C_SubscrDiscount_Line.class, "SeqNo", null);
	String COLUMNNAME_SeqNo = "SeqNo";

	/**
	 * Set Start day from.
	 * Day of the month from which onwards a new contract needs to begin for this record to match.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setStartDay (int StartDay);

	/**
	 * Get Start day from.
	 * Day of the month from which onwards a new contract needs to begin for this record to match.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getStartDay();

	ModelColumn<I_C_SubscrDiscount_Line, Object> COLUMN_StartDay = new ModelColumn<>(I_C_SubscrDiscount_Line.class, "StartDay", null);
	String COLUMNNAME_StartDay = "StartDay";

	/**
	 * Set Start month from.
	 * Month (January=1), from which onwards a new contract needs to begin for this record to match.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setStartMonth (int StartMonth);

	/**
	 * Get Start month from.
	 * Month (January=1), from which onwards a new contract needs to begin for this record to match.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getStartMonth();

	ModelColumn<I_C_SubscrDiscount_Line, Object> COLUMN_StartMonth = new ModelColumn<>(I_C_SubscrDiscount_Line.class, "StartMonth", null);
	String COLUMNNAME_StartMonth = "StartMonth";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_C_SubscrDiscount_Line, Object> COLUMN_Updated = new ModelColumn<>(I_C_SubscrDiscount_Line.class, "Updated", null);
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
