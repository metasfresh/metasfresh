/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2025 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

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
	 * Set Print Color.
	 * Color used for printing and display
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_PrintColor_ID (int AD_PrintColor_ID);

	/**
	 * Get Print Color.
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
	 * Set Bill Business Partner.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setBill_BPartner_ID (int Bill_BPartner_ID);

	/**
	 * Get Bill Business Partner.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getBill_BPartner_ID();

	String COLUMNNAME_Bill_BPartner_ID = "Bill_BPartner_ID";

	/**
	 * Set Bill Location.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setBill_Location_ID (int Bill_Location_ID);

	/**
	 * Get Bill Location.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getBill_Location_ID();

	String COLUMNNAME_Bill_Location_ID = "Bill_Location_ID";

	/**
	 * Set Bill Contact.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setBill_User_ID (int Bill_User_ID);

	/**
	 * Get Bill Contact.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getBill_User_ID();

	String COLUMNNAME_Bill_User_ID = "Bill_User_ID";

	/**
	 * Set Individual business partner's name format.
	 * Describes how the new business partner's name is initiated, based on the added contacts' names and forms of address.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setBPNameAndGreetingStrategy (java.lang.String BPNameAndGreetingStrategy);

	/**
	 * Get Individual business partner's name format.
	 * Describes how the new business partner's name is initiated, based on the added contacts' names and forms of address.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getBPNameAndGreetingStrategy();

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
	 * Set Dunning.
	 * Dunning Rules for overdue invoices
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Dunning_ID (int C_Dunning_ID);

	/**
	 * Get Dunning.
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
	 * Set Incoterms.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Incoterms_ID (int C_Incoterms_ID);

	/**
	 * Get Incoterms.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Incoterms_ID();

	@Nullable org.compiere.model.I_C_Incoterms getC_Incoterms();

	void setC_Incoterms(@Nullable org.compiere.model.I_C_Incoterms C_Incoterms);

	ModelColumn<I_C_BP_Group, org.compiere.model.I_C_Incoterms> COLUMN_C_Incoterms_ID = new ModelColumn<>(I_C_BP_Group.class, "C_Incoterms_ID", org.compiere.model.I_C_Incoterms.class);
	String COLUMNNAME_C_Incoterms_ID = "C_Incoterms_ID";

	/**
	 * Set Payment Term.
	 * The terms of Payment (timing, discount)
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_PaymentTerm_ID (int C_PaymentTerm_ID);

	/**
	 * Get Payment Term.
	 * The terms of Payment (timing, discount)
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
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
	 * Set Freight Cost Rule.
	 * Method for charging Freight
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setFreightCostRule (@Nullable java.lang.String FreightCostRule);

	/**
	 * Get Freight Cost Rule.
	 * Method for charging Freight
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getFreightCostRule();

	ModelColumn<I_C_BP_Group, Object> COLUMN_FreightCostRule = new ModelColumn<>(I_C_BP_Group.class, "FreightCostRule", null);
	String COLUMNNAME_FreightCostRule = "FreightCostRule";

	/**
	 * Set Incoterm Location.
	 * Anzugebender Ort für Handelsklausel
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIncotermLocation (@Nullable java.lang.String IncotermLocation);

	/**
	 * Get Incoterm Location.
	 * Anzugebender Ort für Handelsklausel
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getIncotermLocation();

	ModelColumn<I_C_BP_Group, Object> COLUMN_IncotermLocation = new ModelColumn<>(I_C_BP_Group.class, "IncotermLocation", null);
	String COLUMNNAME_IncotermLocation = "IncotermLocation";

	/**
	 * Set Invoice Rule.
	 * Frequency and method of invoicing
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setInvoiceRule (@Nullable java.lang.String InvoiceRule);

	/**
	 * Get Invoice Rule.
	 * Frequency and method of invoicing
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getInvoiceRule();

	ModelColumn<I_C_BP_Group, Object> COLUMN_InvoiceRule = new ModelColumn<>(I_C_BP_Group.class, "InvoiceRule", null);
	String COLUMNNAME_InvoiceRule = "InvoiceRule";

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
	 * Set Association.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsAssociation (boolean IsAssociation);

	/**
	 * Get Association.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isAssociation();

	ModelColumn<I_C_BP_Group, Object> COLUMN_IsAssociation = new ModelColumn<>(I_C_BP_Group.class, "IsAssociation", null);
	String COLUMNNAME_IsAssociation = "IsAssociation";

	/**
	 * Set Auto Invoice.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIsAutoInvoice (@Nullable java.lang.String IsAutoInvoice);

	/**
	 * Get Auto Invoice.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getIsAutoInvoice();

	ModelColumn<I_C_BP_Group, Object> COLUMN_IsAutoInvoice = new ModelColumn<>(I_C_BP_Group.class, "IsAutoInvoice", null);
	String COLUMNNAME_IsAutoInvoice = "IsAutoInvoice";

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
	 * Set Customer.
	 * Indicates if this Business Partner is a Customer
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsCustomer (boolean IsCustomer);

	/**
	 * Get Customer.
	 * Indicates if this Business Partner is a Customer
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
	 * Set Parent Business Partner Group.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setParent_BP_Group_ID (int Parent_BP_Group_ID);

	/**
	 * Get Parent Business Partner Group.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getParent_BP_Group_ID();

	@Nullable org.compiere.model.I_C_BP_Group getParent_BP_Group();

	void setParent_BP_Group(@Nullable org.compiere.model.I_C_BP_Group Parent_BP_Group);

	ModelColumn<I_C_BP_Group, org.compiere.model.I_C_BP_Group> COLUMN_Parent_BP_Group_ID = new ModelColumn<>(I_C_BP_Group.class, "Parent_BP_Group_ID", org.compiere.model.I_C_BP_Group.class);
	String COLUMNNAME_Parent_BP_Group_ID = "Parent_BP_Group_ID";

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
	 * Set Payment Rule.
	 * Purchase payment option
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPaymentRulePO (@Nullable java.lang.String PaymentRulePO);

	/**
	 * Get Payment Rule.
	 * Purchase payment option
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getPaymentRulePO();

	ModelColumn<I_C_BP_Group, Object> COLUMN_PaymentRulePO = new ModelColumn<>(I_C_BP_Group.class, "PaymentRulePO", null);
	String COLUMNNAME_PaymentRulePO = "PaymentRulePO";

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
	 * Set PO Incoterm Location.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPO_IncotermLocation (@Nullable java.lang.String PO_IncotermLocation);

	/**
	 * Get PO Incoterm Location.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getPO_IncotermLocation();

	ModelColumn<I_C_BP_Group, Object> COLUMN_PO_IncotermLocation = new ModelColumn<>(I_C_BP_Group.class, "PO_IncotermLocation", null);
	String COLUMNNAME_PO_IncotermLocation = "PO_IncotermLocation";

	/**
	 * Set PO Incoterms.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPO_Incoterms_ID (int PO_Incoterms_ID);

	/**
	 * Get PO Incoterms.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getPO_Incoterms_ID();

	@Nullable org.compiere.model.I_C_Incoterms getPO_Incoterms();

	void setPO_Incoterms(@Nullable org.compiere.model.I_C_Incoterms PO_Incoterms);

	ModelColumn<I_C_BP_Group, org.compiere.model.I_C_Incoterms> COLUMN_PO_Incoterms_ID = new ModelColumn<>(I_C_BP_Group.class, "PO_Incoterms_ID", org.compiere.model.I_C_Incoterms.class);
	String COLUMNNAME_PO_Incoterms_ID = "PO_Incoterms_ID";

	/**
	 * Set PO Payment Term.
	 * Payment rules for a purchase order
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPO_PaymentTerm_ID (int PO_PaymentTerm_ID);

	/**
	 * Get PO Payment Term.
	 * Payment rules for a purchase order
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getPO_PaymentTerm_ID();

	String COLUMNNAME_PO_PaymentTerm_ID = "PO_PaymentTerm_ID";

	/**
	 * Set Purchase Pricelist.
	 * Price List used by this Business Partner
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPO_PriceList_ID (int PO_PriceList_ID);

	/**
	 * Get Purchase Pricelist.
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
	 * PO-Invoice Match Price Tolerance in percent of the purchase price
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPriceMatchTolerance (@Nullable BigDecimal PriceMatchTolerance);

	/**
	 * Get Price Match Tolerance.
	 * PO-Invoice Match Price Tolerance in percent of the purchase price
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
	 * Set Purchaser.
	 * Purchasing Responsible
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPurchaser_User_ID (int Purchaser_User_ID);

	/**
	 * Get Purchaser.
	 * Purchasing Responsible
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getPurchaser_User_ID();

	String COLUMNNAME_Purchaser_User_ID = "Purchaser_User_ID";

	/**
	 * Set Credit Status.
	 * Business Partner Credit Status
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setSOCreditStatus (java.lang.String SOCreditStatus);

	/**
	 * Get Credit Status.
	 * Business Partner Credit Status
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
