package org.compiere.model;


/** Generated Interface for C_BP_Group
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_C_BP_Group 
{

    /** TableName=C_BP_Group */
    public static final String Table_Name = "C_BP_Group";

    /** AD_Table_ID=394 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(3);

    /** Load Meta Data */

	/**
	 * Get Mandant.
	 * Client/Tenant for this installation.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Client_ID();

	public org.compiere.model.I_AD_Client getAD_Client();

    /** Column definition for AD_Client_ID */
    public static final org.adempiere.model.ModelColumn<I_C_BP_Group, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_C_BP_Group, org.compiere.model.I_AD_Client>(I_C_BP_Group.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Sektion.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Sektion.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Org_ID();

	public org.compiere.model.I_AD_Org getAD_Org();

	public void setAD_Org(org.compiere.model.I_AD_Org AD_Org);

    /** Column definition for AD_Org_ID */
    public static final org.adempiere.model.ModelColumn<I_C_BP_Group, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_C_BP_Group, org.compiere.model.I_AD_Org>(I_C_BP_Group.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Druck - Farbe.
	 * Color used for printing and display
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_PrintColor_ID (int AD_PrintColor_ID);

	/**
	 * Get Druck - Farbe.
	 * Color used for printing and display
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_PrintColor_ID();

	public org.compiere.model.I_AD_PrintColor getAD_PrintColor();

	public void setAD_PrintColor(org.compiere.model.I_AD_PrintColor AD_PrintColor);

    /** Column definition for AD_PrintColor_ID */
    public static final org.adempiere.model.ModelColumn<I_C_BP_Group, org.compiere.model.I_AD_PrintColor> COLUMN_AD_PrintColor_ID = new org.adempiere.model.ModelColumn<I_C_BP_Group, org.compiere.model.I_AD_PrintColor>(I_C_BP_Group.class, "AD_PrintColor_ID", org.compiere.model.I_AD_PrintColor.class);
    /** Column name AD_PrintColor_ID */
    public static final String COLUMNNAME_AD_PrintColor_ID = "AD_PrintColor_ID";

	/**
	 * Set Geschäftspartnergruppe.
	 * Business Partner Group
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_BP_Group_ID (int C_BP_Group_ID);

	/**
	 * Get Geschäftspartnergruppe.
	 * Business Partner Group
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_BP_Group_ID();

    /** Column definition for C_BP_Group_ID */
    public static final org.adempiere.model.ModelColumn<I_C_BP_Group, Object> COLUMN_C_BP_Group_ID = new org.adempiere.model.ModelColumn<I_C_BP_Group, Object>(I_C_BP_Group.class, "C_BP_Group_ID", null);
    /** Column name C_BP_Group_ID */
    public static final String COLUMNNAME_C_BP_Group_ID = "C_BP_Group_ID";

	/**
	 * Set Mahnung.
	 * Dunning Rules for overdue invoices
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Dunning_ID (int C_Dunning_ID);

	/**
	 * Get Mahnung.
	 * Dunning Rules for overdue invoices
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Dunning_ID();

	public org.compiere.model.I_C_Dunning getC_Dunning();

	public void setC_Dunning(org.compiere.model.I_C_Dunning C_Dunning);

    /** Column definition for C_Dunning_ID */
    public static final org.adempiere.model.ModelColumn<I_C_BP_Group, org.compiere.model.I_C_Dunning> COLUMN_C_Dunning_ID = new org.adempiere.model.ModelColumn<I_C_BP_Group, org.compiere.model.I_C_Dunning>(I_C_BP_Group.class, "C_Dunning_ID", org.compiere.model.I_C_Dunning.class);
    /** Column name C_Dunning_ID */
    public static final String COLUMNNAME_C_Dunning_ID = "C_Dunning_ID";

	/**
	 * Get Erstellt.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getCreated();

    /** Column definition for Created */
    public static final org.adempiere.model.ModelColumn<I_C_BP_Group, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_C_BP_Group, Object>(I_C_BP_Group.class, "Created", null);
    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/**
	 * Get Erstellt durch.
	 * User who created this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getCreatedBy();

    /** Column definition for CreatedBy */
    public static final org.adempiere.model.ModelColumn<I_C_BP_Group, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_C_BP_Group, org.compiere.model.I_AD_User>(I_C_BP_Group.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Kredit Limit %.
	 * Credit Watch - Percent of Credit Limit when OK switches to Watch
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setCreditWatchPercent (java.math.BigDecimal CreditWatchPercent);

	/**
	 * Get Kredit Limit %.
	 * Credit Watch - Percent of Credit Limit when OK switches to Watch
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getCreditWatchPercent();

    /** Column definition for CreditWatchPercent */
    public static final org.adempiere.model.ModelColumn<I_C_BP_Group, Object> COLUMN_CreditWatchPercent = new org.adempiere.model.ModelColumn<I_C_BP_Group, Object>(I_C_BP_Group.class, "CreditWatchPercent", null);
    /** Column name CreditWatchPercent */
    public static final String COLUMNNAME_CreditWatchPercent = "CreditWatchPercent";

	/**
	 * Set Beschreibung.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDescription (java.lang.String Description);

	/**
	 * Get Beschreibung.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDescription();

    /** Column definition for Description */
    public static final org.adempiere.model.ModelColumn<I_C_BP_Group, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<I_C_BP_Group, Object>(I_C_BP_Group.class, "Description", null);
    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/**
	 * Set Aktiv.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsActive (boolean IsActive);

	/**
	 * Get Aktiv.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isActive();

    /** Column definition for IsActive */
    public static final org.adempiere.model.ModelColumn<I_C_BP_Group, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_C_BP_Group, Object>(I_C_BP_Group.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Confidential Info.
	 * Can enter confidential information
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsConfidentialInfo (boolean IsConfidentialInfo);

	/**
	 * Get Confidential Info.
	 * Can enter confidential information
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isConfidentialInfo();

    /** Column definition for IsConfidentialInfo */
    public static final org.adempiere.model.ModelColumn<I_C_BP_Group, Object> COLUMN_IsConfidentialInfo = new org.adempiere.model.ModelColumn<I_C_BP_Group, Object>(I_C_BP_Group.class, "IsConfidentialInfo", null);
    /** Column name IsConfidentialInfo */
    public static final String COLUMNNAME_IsConfidentialInfo = "IsConfidentialInfo";

	/**
	 * Set Kunde.
	 * Zeigt an, ob dieser Geschäftspartner ein Kunde ist
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsCustomer (boolean IsCustomer);

	/**
	 * Get Kunde.
	 * Zeigt an, ob dieser Geschäftspartner ein Kunde ist
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isCustomer();

    /** Column definition for IsCustomer */
    public static final org.adempiere.model.ModelColumn<I_C_BP_Group, Object> COLUMN_IsCustomer = new org.adempiere.model.ModelColumn<I_C_BP_Group, Object>(I_C_BP_Group.class, "IsCustomer", null);
    /** Column name IsCustomer */
    public static final String COLUMNNAME_IsCustomer = "IsCustomer";

	/**
	 * Set Standard.
	 * Default value
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsDefault (boolean IsDefault);

	/**
	 * Get Standard.
	 * Default value
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isDefault();

    /** Column definition for IsDefault */
    public static final org.adempiere.model.ModelColumn<I_C_BP_Group, Object> COLUMN_IsDefault = new org.adempiere.model.ModelColumn<I_C_BP_Group, Object>(I_C_BP_Group.class, "IsDefault", null);
    /** Column name IsDefault */
    public static final String COLUMNNAME_IsDefault = "IsDefault";

	/**
	 * Set Rabatt Schema.
	 * Schema um den prozentualen Rabatt zu berechnen
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_DiscountSchema_ID (int M_DiscountSchema_ID);

	/**
	 * Get Rabatt Schema.
	 * Schema um den prozentualen Rabatt zu berechnen
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_DiscountSchema_ID();

	public org.compiere.model.I_M_DiscountSchema getM_DiscountSchema();

	public void setM_DiscountSchema(org.compiere.model.I_M_DiscountSchema M_DiscountSchema);

    /** Column definition for M_DiscountSchema_ID */
    public static final org.adempiere.model.ModelColumn<I_C_BP_Group, org.compiere.model.I_M_DiscountSchema> COLUMN_M_DiscountSchema_ID = new org.adempiere.model.ModelColumn<I_C_BP_Group, org.compiere.model.I_M_DiscountSchema>(I_C_BP_Group.class, "M_DiscountSchema_ID", org.compiere.model.I_M_DiscountSchema.class);
    /** Column name M_DiscountSchema_ID */
    public static final String COLUMNNAME_M_DiscountSchema_ID = "M_DiscountSchema_ID";

	/**
	 * Set Frachtkostenpauschale.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_FreightCost_ID (int M_FreightCost_ID);

	/**
	 * Get Frachtkostenpauschale.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_FreightCost_ID();

    /** Column definition for M_FreightCost_ID */
    public static final org.adempiere.model.ModelColumn<I_C_BP_Group, Object> COLUMN_M_FreightCost_ID = new org.adempiere.model.ModelColumn<I_C_BP_Group, Object>(I_C_BP_Group.class, "M_FreightCost_ID", null);
    /** Column name M_FreightCost_ID */
    public static final String COLUMNNAME_M_FreightCost_ID = "M_FreightCost_ID";

	/**
	 * Set Preisliste.
	 * Unique identifier of a Price List
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_PriceList_ID (int M_PriceList_ID);

	/**
	 * Get Preisliste.
	 * Unique identifier of a Price List
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_PriceList_ID();

	public org.compiere.model.I_M_PriceList getM_PriceList();

	public void setM_PriceList(org.compiere.model.I_M_PriceList M_PriceList);

    /** Column definition for M_PriceList_ID */
    public static final org.adempiere.model.ModelColumn<I_C_BP_Group, org.compiere.model.I_M_PriceList> COLUMN_M_PriceList_ID = new org.adempiere.model.ModelColumn<I_C_BP_Group, org.compiere.model.I_M_PriceList>(I_C_BP_Group.class, "M_PriceList_ID", org.compiere.model.I_M_PriceList.class);
    /** Column name M_PriceList_ID */
    public static final String COLUMNNAME_M_PriceList_ID = "M_PriceList_ID";

	/**
	 * Set Preissystem.
	 * Ein Preissystem enthält beliebig viele, Länder-abhängige Preislisten.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_PricingSystem_ID (int M_PricingSystem_ID);

	/**
	 * Get Preissystem.
	 * Ein Preissystem enthält beliebig viele, Länder-abhängige Preislisten.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_PricingSystem_ID();

	public org.compiere.model.I_M_PricingSystem getM_PricingSystem();

	public void setM_PricingSystem(org.compiere.model.I_M_PricingSystem M_PricingSystem);

    /** Column definition for M_PricingSystem_ID */
    public static final org.adempiere.model.ModelColumn<I_C_BP_Group, org.compiere.model.I_M_PricingSystem> COLUMN_M_PricingSystem_ID = new org.adempiere.model.ModelColumn<I_C_BP_Group, org.compiere.model.I_M_PricingSystem>(I_C_BP_Group.class, "M_PricingSystem_ID", org.compiere.model.I_M_PricingSystem.class);
    /** Column name M_PricingSystem_ID */
    public static final String COLUMNNAME_M_PricingSystem_ID = "M_PricingSystem_ID";

	/**
	 * Set MRP ausschliessen.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setMRP_Exclude (java.lang.String MRP_Exclude);

	/**
	 * Get MRP ausschliessen.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getMRP_Exclude();

    /** Column definition for MRP_Exclude */
    public static final org.adempiere.model.ModelColumn<I_C_BP_Group, Object> COLUMN_MRP_Exclude = new org.adempiere.model.ModelColumn<I_C_BP_Group, Object>(I_C_BP_Group.class, "MRP_Exclude", null);
    /** Column name MRP_Exclude */
    public static final String COLUMNNAME_MRP_Exclude = "MRP_Exclude";

	/**
	 * Set Name.
	 * Alphanumeric identifier of the entity
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setName (java.lang.String Name);

	/**
	 * Get Name.
	 * Alphanumeric identifier of the entity
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getName();

    /** Column definition for Name */
    public static final org.adempiere.model.ModelColumn<I_C_BP_Group, Object> COLUMN_Name = new org.adempiere.model.ModelColumn<I_C_BP_Group, Object>(I_C_BP_Group.class, "Name", null);
    /** Column name Name */
    public static final String COLUMNNAME_Name = "Name";

	/**
	 * Set Einkauf Rabatt Schema.
	 * Schema to calculate the purchase trade discount percentage
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPO_DiscountSchema_ID (int PO_DiscountSchema_ID);

	/**
	 * Get Einkauf Rabatt Schema.
	 * Schema to calculate the purchase trade discount percentage
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getPO_DiscountSchema_ID();

	public org.compiere.model.I_M_DiscountSchema getPO_DiscountSchema();

	public void setPO_DiscountSchema(org.compiere.model.I_M_DiscountSchema PO_DiscountSchema);

    /** Column definition for PO_DiscountSchema_ID */
    public static final org.adempiere.model.ModelColumn<I_C_BP_Group, org.compiere.model.I_M_DiscountSchema> COLUMN_PO_DiscountSchema_ID = new org.adempiere.model.ModelColumn<I_C_BP_Group, org.compiere.model.I_M_DiscountSchema>(I_C_BP_Group.class, "PO_DiscountSchema_ID", org.compiere.model.I_M_DiscountSchema.class);
    /** Column name PO_DiscountSchema_ID */
    public static final String COLUMNNAME_PO_DiscountSchema_ID = "PO_DiscountSchema_ID";

	/**
	 * Set Einkaufspreisliste.
	 * Price List used by this Business Partner
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPO_PriceList_ID (int PO_PriceList_ID);

	/**
	 * Get Einkaufspreisliste.
	 * Price List used by this Business Partner
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getPO_PriceList_ID();

	public org.compiere.model.I_M_PriceList getPO_PriceList();

	public void setPO_PriceList(org.compiere.model.I_M_PriceList PO_PriceList);

    /** Column definition for PO_PriceList_ID */
    public static final org.adempiere.model.ModelColumn<I_C_BP_Group, org.compiere.model.I_M_PriceList> COLUMN_PO_PriceList_ID = new org.adempiere.model.ModelColumn<I_C_BP_Group, org.compiere.model.I_M_PriceList>(I_C_BP_Group.class, "PO_PriceList_ID", org.compiere.model.I_M_PriceList.class);
    /** Column name PO_PriceList_ID */
    public static final String COLUMNNAME_PO_PriceList_ID = "PO_PriceList_ID";

	/**
	 * Set Einkaufspreissystem.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPO_PricingSystem_ID (int PO_PricingSystem_ID);

	/**
	 * Get Einkaufspreissystem.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getPO_PricingSystem_ID();

	public org.compiere.model.I_M_PricingSystem getPO_PricingSystem();

	public void setPO_PricingSystem(org.compiere.model.I_M_PricingSystem PO_PricingSystem);

    /** Column definition for PO_PricingSystem_ID */
    public static final org.adempiere.model.ModelColumn<I_C_BP_Group, org.compiere.model.I_M_PricingSystem> COLUMN_PO_PricingSystem_ID = new org.adempiere.model.ModelColumn<I_C_BP_Group, org.compiere.model.I_M_PricingSystem>(I_C_BP_Group.class, "PO_PricingSystem_ID", org.compiere.model.I_M_PricingSystem.class);
    /** Column name PO_PricingSystem_ID */
    public static final String COLUMNNAME_PO_PricingSystem_ID = "PO_PricingSystem_ID";

	/**
	 * Set Preis Abweichung Toleranz.
	 * Preis Abweichung Toleranz
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPriceMatchTolerance (java.math.BigDecimal PriceMatchTolerance);

	/**
	 * Get Preis Abweichung Toleranz.
	 * Preis Abweichung Toleranz
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getPriceMatchTolerance();

    /** Column definition for PriceMatchTolerance */
    public static final org.adempiere.model.ModelColumn<I_C_BP_Group, Object> COLUMN_PriceMatchTolerance = new org.adempiere.model.ModelColumn<I_C_BP_Group, Object>(I_C_BP_Group.class, "PriceMatchTolerance", null);
    /** Column name PriceMatchTolerance */
    public static final String COLUMNNAME_PriceMatchTolerance = "PriceMatchTolerance";

	/**
	 * Set Priority Base.
	 * Base of Priority
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPriorityBase (java.lang.String PriorityBase);

	/**
	 * Get Priority Base.
	 * Base of Priority
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getPriorityBase();

    /** Column definition for PriorityBase */
    public static final org.adempiere.model.ModelColumn<I_C_BP_Group, Object> COLUMN_PriorityBase = new org.adempiere.model.ModelColumn<I_C_BP_Group, Object>(I_C_BP_Group.class, "PriorityBase", null);
    /** Column name PriorityBase */
    public static final String COLUMNNAME_PriorityBase = "PriorityBase";

	/**
	 * Set Kreditstatus.
	 * Kreditstatus des Geschäftspartners
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setSOCreditStatus (java.lang.String SOCreditStatus);

	/**
	 * Get Kreditstatus.
	 * Kreditstatus des Geschäftspartners
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getSOCreditStatus();

    /** Column definition for SOCreditStatus */
    public static final org.adempiere.model.ModelColumn<I_C_BP_Group, Object> COLUMN_SOCreditStatus = new org.adempiere.model.ModelColumn<I_C_BP_Group, Object>(I_C_BP_Group.class, "SOCreditStatus", null);
    /** Column name SOCreditStatus */
    public static final String COLUMNNAME_SOCreditStatus = "SOCreditStatus";

	/**
	 * Get Aktualisiert.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getUpdated();

    /** Column definition for Updated */
    public static final org.adempiere.model.ModelColumn<I_C_BP_Group, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_C_BP_Group, Object>(I_C_BP_Group.class, "Updated", null);
    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Aktualisiert durch.
	 * User who updated this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getUpdatedBy();

    /** Column definition for UpdatedBy */
    public static final org.adempiere.model.ModelColumn<I_C_BP_Group, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_C_BP_Group, org.compiere.model.I_AD_User>(I_C_BP_Group.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/**
	 * Set Suchschlüssel.
	 * Search key for the record in the format required - must be unique
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setValue (java.lang.String Value);

	/**
	 * Get Suchschlüssel.
	 * Search key for the record in the format required - must be unique
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getValue();

    /** Column definition for Value */
    public static final org.adempiere.model.ModelColumn<I_C_BP_Group, Object> COLUMN_Value = new org.adempiere.model.ModelColumn<I_C_BP_Group, Object>(I_C_BP_Group.class, "Value", null);
    /** Column name Value */
    public static final String COLUMNNAME_Value = "Value";
}
