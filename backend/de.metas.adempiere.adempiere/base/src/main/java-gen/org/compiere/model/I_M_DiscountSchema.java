package org.compiere.model;

import java.math.BigDecimal;
import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for M_DiscountSchema
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_M_DiscountSchema 
{

	String Table_Name = "M_DiscountSchema";

//	/** AD_Table_ID=475 */
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
	 * Set Break Value Attribute.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setBreakValue_Attribute_ID (int BreakValue_Attribute_ID);

	/**
	 * Get Break Value Attribute.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getBreakValue_Attribute_ID();

	String COLUMNNAME_BreakValue_Attribute_ID = "BreakValue_Attribute_ID";

	/**
	 * Set Break Value Type.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setBreakValueType (java.lang.String BreakValueType);

	/**
	 * Get Break Value Type.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getBreakValueType();

	ModelColumn<I_M_DiscountSchema, Object> COLUMN_BreakValueType = new ModelColumn<>(I_M_DiscountSchema.class, "BreakValueType", null);
	String COLUMNNAME_BreakValueType = "BreakValueType";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_M_DiscountSchema, Object> COLUMN_Created = new ModelColumn<>(I_M_DiscountSchema.class, "Created", null);
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
	 * Set Accumulation Level.
	 * Level for accumulative calculations
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCumulativeLevel (@Nullable java.lang.String CumulativeLevel);

	/**
	 * Get Accumulation Level.
	 * Level for accumulative calculations
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getCumulativeLevel();

	ModelColumn<I_M_DiscountSchema, Object> COLUMN_CumulativeLevel = new ModelColumn<>(I_M_DiscountSchema.class, "CumulativeLevel", null);
	String COLUMNNAME_CumulativeLevel = "CumulativeLevel";

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

	ModelColumn<I_M_DiscountSchema, Object> COLUMN_Description = new ModelColumn<>(I_M_DiscountSchema.class, "Description", null);
	String COLUMNNAME_Description = "Description";

	/**
	 * Set Discount Type.
	 * Type of trade discount calculation
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDiscountType (java.lang.String DiscountType);

	/**
	 * Get Discount Type.
	 * Type of trade discount calculation
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getDiscountType();

	ModelColumn<I_M_DiscountSchema, Object> COLUMN_DiscountType = new ModelColumn<>(I_M_DiscountSchema.class, "DiscountType", null);
	String COLUMNNAME_DiscountType = "DiscountType";

	/**
	 * Set Retain zero prices.
	 * Do not change prices equal to 0 when updating (derivative) price lists.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDoNotChangeZeroPrices (boolean DoNotChangeZeroPrices);

	/**
	 * Get Retain zero prices.
	 * Do not change prices equal to 0 when updating (derivative) price lists.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isDoNotChangeZeroPrices();

	ModelColumn<I_M_DiscountSchema, Object> COLUMN_DoNotChangeZeroPrices = new ModelColumn<>(I_M_DiscountSchema.class, "DoNotChangeZeroPrices", null);
	String COLUMNNAME_DoNotChangeZeroPrices = "DoNotChangeZeroPrices";

	/**
	 * Set Flat Discount %.
	 * Flat discount percentage
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setFlatDiscount (@Nullable BigDecimal FlatDiscount);

	/**
	 * Get Flat Discount %.
	 * Flat discount percentage
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getFlatDiscount();

	ModelColumn<I_M_DiscountSchema, Object> COLUMN_FlatDiscount = new ModelColumn<>(I_M_DiscountSchema.class, "FlatDiscount", null);
	String COLUMNNAME_FlatDiscount = "FlatDiscount";

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

	ModelColumn<I_M_DiscountSchema, Object> COLUMN_IsActive = new ModelColumn<>(I_M_DiscountSchema.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Flat Discount.
	 * Use flat discount defined on Business Partner Level
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsBPartnerFlatDiscount (boolean IsBPartnerFlatDiscount);

	/**
	 * Get Flat Discount.
	 * Use flat discount defined on Business Partner Level
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isBPartnerFlatDiscount();

	ModelColumn<I_M_DiscountSchema, Object> COLUMN_IsBPartnerFlatDiscount = new ModelColumn<>(I_M_DiscountSchema.class, "IsBPartnerFlatDiscount", null);
	String COLUMNNAME_IsBPartnerFlatDiscount = "IsBPartnerFlatDiscount";

	/**
	 * Set Price Schema - Calculated Surcharge.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_DiscountSchema_Calculated_Surcharge_ID (int M_DiscountSchema_Calculated_Surcharge_ID);

	/**
	 * Get Price Schema - Calculated Surcharge.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_DiscountSchema_Calculated_Surcharge_ID();

	@Nullable org.compiere.model.I_M_DiscountSchema_Calculated_Surcharge getM_DiscountSchema_Calculated_Surcharge();

	void setM_DiscountSchema_Calculated_Surcharge(@Nullable org.compiere.model.I_M_DiscountSchema_Calculated_Surcharge M_DiscountSchema_Calculated_Surcharge);

	ModelColumn<I_M_DiscountSchema, org.compiere.model.I_M_DiscountSchema_Calculated_Surcharge> COLUMN_M_DiscountSchema_Calculated_Surcharge_ID = new ModelColumn<>(I_M_DiscountSchema.class, "M_DiscountSchema_Calculated_Surcharge_ID", org.compiere.model.I_M_DiscountSchema_Calculated_Surcharge.class);
	String COLUMNNAME_M_DiscountSchema_Calculated_Surcharge_ID = "M_DiscountSchema_Calculated_Surcharge_ID";

	/**
	 * Set Discount Schema.
	 * Schema to calculate the trade discount percentage
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_DiscountSchema_ID (int M_DiscountSchema_ID);

	/**
	 * Get Discount Schema.
	 * Schema to calculate the trade discount percentage
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_DiscountSchema_ID();

	ModelColumn<I_M_DiscountSchema, Object> COLUMN_M_DiscountSchema_ID = new ModelColumn<>(I_M_DiscountSchema.class, "M_DiscountSchema_ID", null);
	String COLUMNNAME_M_DiscountSchema_ID = "M_DiscountSchema_ID";

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

	ModelColumn<I_M_DiscountSchema, Object> COLUMN_Name = new ModelColumn<>(I_M_DiscountSchema.class, "Name", null);
	String COLUMNNAME_Name = "Name";

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

	ModelColumn<I_M_DiscountSchema, Object> COLUMN_Processing = new ModelColumn<>(I_M_DiscountSchema.class, "Processing", null);
	String COLUMNNAME_Processing = "Processing";

	/**
	 * Set Script.
	 * Dynamic Java Language Script to calculate result
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setScript (@Nullable java.lang.String Script);

	/**
	 * Get Script.
	 * Dynamic Java Language Script to calculate result
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getScript();

	ModelColumn<I_M_DiscountSchema, Object> COLUMN_Script = new ModelColumn<>(I_M_DiscountSchema.class, "Script", null);
	String COLUMNNAME_Script = "Script";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_M_DiscountSchema, Object> COLUMN_Updated = new ModelColumn<>(I_M_DiscountSchema.class, "Updated", null);
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
	 * Set Valid From.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setValidFrom (java.sql.Timestamp ValidFrom);

	/**
	 * Get Valid From.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getValidFrom();

	ModelColumn<I_M_DiscountSchema, Object> COLUMN_ValidFrom = new ModelColumn<>(I_M_DiscountSchema.class, "ValidFrom", null);
	String COLUMNNAME_ValidFrom = "ValidFrom";
}
