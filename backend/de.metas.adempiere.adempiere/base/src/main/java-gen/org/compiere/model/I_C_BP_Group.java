package org.compiere.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;
import java.math.BigDecimal;

/** Generated Interface for C_BP_Group
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_C_BP_Group 
{

	String Table_Name = "C_BP_Group";

//	/** AD_Table_ID=394 */
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
	 * Set Druck - Farbe.
	 * Color used for printing and display
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_PrintColor_ID (int AD_PrintColor_ID);

	/**
	 * Get Druck - Farbe.
	 * Color used for printing and display
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_PrintColor_ID();

	@Nullable org.compiere.model.I_AD_PrintColor getAD_PrintColor();

	void setAD_PrintColor(@Nullable org.compiere.model.I_AD_PrintColor AD_PrintColor);

	ModelColumn<I_C_BP_Group, org.compiere.model.I_AD_PrintColor> COLUMN_AD_PrintColor_ID = new ModelColumn<>(I_C_BP_Group.class, "AD_PrintColor_ID", org.compiere.model.I_AD_PrintColor.class);
	String COLUMNNAME_AD_PrintColor_ID = "AD_PrintColor_ID";

	/**
	 * Set Individual business partner's name format.
	 * Describes how the new business partner's name is initiated, based on the added contacts' names and forms of address.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setBPNameAndGreetingStrategy (@Nullable java.lang.String BPNameAndGreetingStrategy);

	/**
	 * Get Individual business partner's name format.
	 * Describes how the new business partner's name is initiated, based on the added contacts' names and forms of address.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getBPNameAndGreetingStrategy();

	ModelColumn<I_C_BP_Group, Object> COLUMN_BPNameAndGreetingStrategy = new ModelColumn<>(I_C_BP_Group.class, "BPNameAndGreetingStrategy", null);
	String COLUMNNAME_BPNameAndGreetingStrategy = "BPNameAndGreetingStrategy";

	/**
	 * Set Business Partner Group.
	 * Business Partner Group
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_BP_Group_ID (int C_BP_Group_ID);

	/**
	 * Get Business Partner Group.
	 * Business Partner Group
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_BP_Group_ID();

	ModelColumn<I_C_BP_Group, Object> COLUMN_C_BP_Group_ID = new ModelColumn<>(I_C_BP_Group.class, "C_BP_Group_ID", null);
	String COLUMNNAME_C_BP_Group_ID = "C_BP_Group_ID";

	/**
	 * Set Mahnung.
	 * Dunning Rules for overdue invoices
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Dunning_ID (int C_Dunning_ID);

	/**
	 * Get Mahnung.
	 * Dunning Rules for overdue invoices
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Dunning_ID();

	@Nullable org.compiere.model.I_C_Dunning getC_Dunning();

	void setC_Dunning(@Nullable org.compiere.model.I_C_Dunning C_Dunning);

	ModelColumn<I_C_BP_Group, org.compiere.model.I_C_Dunning> COLUMN_C_Dunning_ID = new ModelColumn<>(I_C_BP_Group.class, "C_Dunning_ID", org.compiere.model.I_C_Dunning.class);
	String COLUMNNAME_C_Dunning_ID = "C_Dunning_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_C_BP_Group, Object> COLUMN_Created = new ModelColumn<>(I_C_BP_Group.class, "Created", null);
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
	 * Set Credit Watch %.
	 * Credit Watch - Percent of Credit Limit when OK switches to Watch
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCreditWatchPercent (@Nullable BigDecimal CreditWatchPercent);

	/**
	 * Get Credit Watch %.
	 * Credit Watch - Percent of Credit Limit when OK switches to Watch
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getCreditWatchPercent();

	ModelColumn<I_C_BP_Group, Object> COLUMN_CreditWatchPercent = new ModelColumn<>(I_C_BP_Group.class, "CreditWatchPercent", null);
	String COLUMNNAME_CreditWatchPercent = "CreditWatchPercent";

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

	ModelColumn<I_C_BP_Group, Object> COLUMN_Description = new ModelColumn<>(I_C_BP_Group.class, "Description", null);
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

	ModelColumn<I_C_BP_Group, Object> COLUMN_IsActive = new ModelColumn<>(I_C_BP_Group.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Confidential Info.
	 * Can enter confidential information
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsConfidentialInfo (boolean IsConfidentialInfo);

	/**
	 * Get Confidential Info.
	 * Can enter confidential information
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isConfidentialInfo();

	ModelColumn<I_C_BP_Group, Object> COLUMN_IsConfidentialInfo = new ModelColumn<>(I_C_BP_Group.class, "IsConfidentialInfo", null);
	String COLUMNNAME_IsConfidentialInfo = "IsConfidentialInfo";

	/**
	 * Set Kunde.
	 * Zeigt an, ob dieser Geschäftspartner ein Kunde ist
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsCustomer (boolean IsCustomer);

	/**
	 * Get Kunde.
	 * Zeigt an, ob dieser Geschäftspartner ein Kunde ist
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isCustomer();

	ModelColumn<I_C_BP_Group, Object> COLUMN_IsCustomer = new ModelColumn<>(I_C_BP_Group.class, "IsCustomer", null);
	String COLUMNNAME_IsCustomer = "IsCustomer";

	/**
	 * Set Default.
	 * Default value
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsDefault (boolean IsDefault);

	/**
	 * Get Default.
	 * Default value
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isDefault();

	ModelColumn<I_C_BP_Group, Object> COLUMN_IsDefault = new ModelColumn<>(I_C_BP_Group.class, "IsDefault", null);
	String COLUMNNAME_IsDefault = "IsDefault";

	/**
	 * Set Discount Schema.
	 * Schema to calculate the trade discount percentage
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_DiscountSchema_ID (int M_DiscountSchema_ID);

	/**
	 * Get Discount Schema.
	 * Schema to calculate the trade discount percentage
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_DiscountSchema_ID();

	@Nullable org.compiere.model.I_M_DiscountSchema getM_DiscountSchema();

	void setM_DiscountSchema(@Nullable org.compiere.model.I_M_DiscountSchema M_DiscountSchema);

	ModelColumn<I_C_BP_Group, org.compiere.model.I_M_DiscountSchema> COLUMN_M_DiscountSchema_ID = new ModelColumn<>(I_C_BP_Group.class, "M_DiscountSchema_ID", org.compiere.model.I_M_DiscountSchema.class);
	String COLUMNNAME_M_DiscountSchema_ID = "M_DiscountSchema_ID";

	/**
	 * Set Frachtkostenpauschale.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_FreightCost_ID (int M_FreightCost_ID);

	/**
	 * Get Frachtkostenpauschale.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_FreightCost_ID();

	ModelColumn<I_C_BP_Group, Object> COLUMN_M_FreightCost_ID = new ModelColumn<>(I_C_BP_Group.class, "M_FreightCost_ID", null);
	String COLUMNNAME_M_FreightCost_ID = "M_FreightCost_ID";

	/**
	 * Set Price List.
	 * Unique identifier of a Price List
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_PriceList_ID (int M_PriceList_ID);

	/**
	 * Get Price List.
	 * Unique identifier of a Price List
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_PriceList_ID();

	String COLUMNNAME_M_PriceList_ID = "M_PriceList_ID";

	/**
	 * Set Pricing System.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_PricingSystem_ID (int M_PricingSystem_ID);

	/**
	 * Get Pricing System.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_PricingSystem_ID();

	String COLUMNNAME_M_PricingSystem_ID = "M_PricingSystem_ID";

	/**
	 * Set Exclude from MRP.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setMRP_Exclude (@Nullable java.lang.String MRP_Exclude);

	/**
	 * Get Exclude from MRP.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getMRP_Exclude();

	ModelColumn<I_C_BP_Group, Object> COLUMN_MRP_Exclude = new ModelColumn<>(I_C_BP_Group.class, "MRP_Exclude", null);
	String COLUMNNAME_MRP_Exclude = "MRP_Exclude";

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

	ModelColumn<I_C_BP_Group, Object> COLUMN_Name = new ModelColumn<>(I_C_BP_Group.class, "Name", null);
	String COLUMNNAME_Name = "Name";

	/**
	 * Set Payment Rule.
	 * How you pay the invoice
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPaymentRule (@Nullable java.lang.String PaymentRule);

	/**
	 * Get Payment Rule.
	 * How you pay the invoice
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getPaymentRule();

	ModelColumn<I_C_BP_Group, Object> COLUMN_PaymentRule = new ModelColumn<>(I_C_BP_Group.class, "PaymentRule", null);
	String COLUMNNAME_PaymentRule = "PaymentRule";

	/**
	 * Set PO Discount Schema.
	 * Schema to calculate the purchase trade discount percentage
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPO_DiscountSchema_ID (int PO_DiscountSchema_ID);

	/**
	 * Get PO Discount Schema.
	 * Schema to calculate the purchase trade discount percentage
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getPO_DiscountSchema_ID();

	@Nullable org.compiere.model.I_M_DiscountSchema getPO_DiscountSchema();

	void setPO_DiscountSchema(@Nullable org.compiere.model.I_M_DiscountSchema PO_DiscountSchema);

	ModelColumn<I_C_BP_Group, org.compiere.model.I_M_DiscountSchema> COLUMN_PO_DiscountSchema_ID = new ModelColumn<>(I_C_BP_Group.class, "PO_DiscountSchema_ID", org.compiere.model.I_M_DiscountSchema.class);
	String COLUMNNAME_PO_DiscountSchema_ID = "PO_DiscountSchema_ID";

	/**
	 * Set Einkaufspreisliste.
	 * Price List used by this Business Partner
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPO_PriceList_ID (int PO_PriceList_ID);

	/**
	 * Get Einkaufspreisliste.
	 * Price List used by this Business Partner
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getPO_PriceList_ID();

	String COLUMNNAME_PO_PriceList_ID = "PO_PriceList_ID";

	/**
	 * Set Purchase Pricing System.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPO_PricingSystem_ID (int PO_PricingSystem_ID);

	/**
	 * Get Purchase Pricing System.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getPO_PricingSystem_ID();

	String COLUMNNAME_PO_PricingSystem_ID = "PO_PricingSystem_ID";

	/**
	 * Set Price Match Tolerance.
	 * Preis Abweichung Toleranz
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPriceMatchTolerance (@Nullable BigDecimal PriceMatchTolerance);

	/**
	 * Get Price Match Tolerance.
	 * Preis Abweichung Toleranz
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getPriceMatchTolerance();

	ModelColumn<I_C_BP_Group, Object> COLUMN_PriceMatchTolerance = new ModelColumn<>(I_C_BP_Group.class, "PriceMatchTolerance", null);
	String COLUMNNAME_PriceMatchTolerance = "PriceMatchTolerance";

	/**
	 * Set Priority Base.
	 * Base of Priority
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPriorityBase (@Nullable java.lang.String PriorityBase);

	/**
	 * Get Priority Base.
	 * Base of Priority
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getPriorityBase();

	ModelColumn<I_C_BP_Group, Object> COLUMN_PriorityBase = new ModelColumn<>(I_C_BP_Group.class, "PriorityBase", null);
	String COLUMNNAME_PriorityBase = "PriorityBase";

	/**
	 * Set Kreditstatus.
	 * Kreditstatus des Geschäftspartners
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setSOCreditStatus (java.lang.String SOCreditStatus);

	/**
	 * Get Kreditstatus.
	 * Kreditstatus des Geschäftspartners
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getSOCreditStatus();

	ModelColumn<I_C_BP_Group, Object> COLUMN_SOCreditStatus = new ModelColumn<>(I_C_BP_Group.class, "SOCreditStatus", null);
	String COLUMNNAME_SOCreditStatus = "SOCreditStatus";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_C_BP_Group, Object> COLUMN_Updated = new ModelColumn<>(I_C_BP_Group.class, "Updated", null);
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

	ModelColumn<I_C_BP_Group, Object> COLUMN_Value = new ModelColumn<>(I_C_BP_Group.class, "Value", null);
	String COLUMNNAME_Value = "Value";
}
