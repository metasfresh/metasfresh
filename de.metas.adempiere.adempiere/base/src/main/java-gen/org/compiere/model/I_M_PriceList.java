package org.compiere.model;


/** Generated Interface for M_PriceList
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_M_PriceList 
{

    /** TableName=M_PriceList */
    public static final String Table_Name = "M_PriceList";

    /** AD_Table_ID=255 */
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
    public static final org.adempiere.model.ModelColumn<I_M_PriceList, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_M_PriceList, org.compiere.model.I_AD_Client>(I_M_PriceList.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_M_PriceList, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_M_PriceList, org.compiere.model.I_AD_Org>(I_M_PriceList.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Base Pricelist.
	 * Pricelist to be used, if product not found on this pricelist
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setBasePriceList_ID (int BasePriceList_ID);

	/**
	 * Get Base Pricelist.
	 * Pricelist to be used, if product not found on this pricelist
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getBasePriceList_ID();

	public org.compiere.model.I_M_PriceList getBasePriceList();

	public void setBasePriceList(org.compiere.model.I_M_PriceList BasePriceList);

    /** Column definition for BasePriceList_ID */
    public static final org.adempiere.model.ModelColumn<I_M_PriceList, org.compiere.model.I_M_PriceList> COLUMN_BasePriceList_ID = new org.adempiere.model.ModelColumn<I_M_PriceList, org.compiere.model.I_M_PriceList>(I_M_PriceList.class, "BasePriceList_ID", org.compiere.model.I_M_PriceList.class);
    /** Column name BasePriceList_ID */
    public static final String COLUMNNAME_BasePriceList_ID = "BasePriceList_ID";

	/**
	 * Set Land.
	 * Land
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Country_ID (int C_Country_ID);

	/**
	 * Get Land.
	 * Land
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Country_ID();

	public org.compiere.model.I_C_Country getC_Country();

	public void setC_Country(org.compiere.model.I_C_Country C_Country);

    /** Column definition for C_Country_ID */
    public static final org.adempiere.model.ModelColumn<I_M_PriceList, org.compiere.model.I_C_Country> COLUMN_C_Country_ID = new org.adempiere.model.ModelColumn<I_M_PriceList, org.compiere.model.I_C_Country>(I_M_PriceList.class, "C_Country_ID", org.compiere.model.I_C_Country.class);
    /** Column name C_Country_ID */
    public static final String COLUMNNAME_C_Country_ID = "C_Country_ID";

	/**
	 * Set Währung.
	 * The Currency for this record
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_Currency_ID (int C_Currency_ID);

	/**
	 * Get Währung.
	 * The Currency for this record
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_Currency_ID();

	public org.compiere.model.I_C_Currency getC_Currency();

	public void setC_Currency(org.compiere.model.I_C_Currency C_Currency);

    /** Column definition for C_Currency_ID */
    public static final org.adempiere.model.ModelColumn<I_M_PriceList, org.compiere.model.I_C_Currency> COLUMN_C_Currency_ID = new org.adempiere.model.ModelColumn<I_M_PriceList, org.compiere.model.I_C_Currency>(I_M_PriceList.class, "C_Currency_ID", org.compiere.model.I_C_Currency.class);
    /** Column name C_Currency_ID */
    public static final String COLUMNNAME_C_Currency_ID = "C_Currency_ID";

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
    public static final org.adempiere.model.ModelColumn<I_M_PriceList, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_M_PriceList, Object>(I_M_PriceList.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_M_PriceList, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_M_PriceList, org.compiere.model.I_AD_User>(I_M_PriceList.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

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
    public static final org.adempiere.model.ModelColumn<I_M_PriceList, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<I_M_PriceList, Object>(I_M_PriceList.class, "Description", null);
    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/**
	 * Set Enforce price limit.
	 * Do not allow prices below the limit price
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setEnforcePriceLimit (boolean EnforcePriceLimit);

	/**
	 * Get Enforce price limit.
	 * Do not allow prices below the limit price
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isEnforcePriceLimit();

    /** Column definition for EnforcePriceLimit */
    public static final org.adempiere.model.ModelColumn<I_M_PriceList, Object> COLUMN_EnforcePriceLimit = new org.adempiere.model.ModelColumn<I_M_PriceList, Object>(I_M_PriceList.class, "EnforcePriceLimit", null);
    /** Column name EnforcePriceLimit */
    public static final String COLUMNNAME_EnforcePriceLimit = "EnforcePriceLimit";

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
    public static final org.adempiere.model.ModelColumn<I_M_PriceList, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_M_PriceList, Object>(I_M_PriceList.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

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
    public static final org.adempiere.model.ModelColumn<I_M_PriceList, Object> COLUMN_IsDefault = new org.adempiere.model.ModelColumn<I_M_PriceList, Object>(I_M_PriceList.class, "IsDefault", null);
    /** Column name IsDefault */
    public static final String COLUMNNAME_IsDefault = "IsDefault";

	/**
	 * Set Pflichtangabe.
	 * Data entry is required in this column
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setIsMandatory (boolean IsMandatory);

	/**
	 * Get Pflichtangabe.
	 * Data entry is required in this column
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isMandatory();

    /** Column definition for IsMandatory */
    public static final org.adempiere.model.ModelColumn<I_M_PriceList, Object> COLUMN_IsMandatory = new org.adempiere.model.ModelColumn<I_M_PriceList, Object>(I_M_PriceList.class, "IsMandatory", null);
    /** Column name IsMandatory */
    public static final String COLUMNNAME_IsMandatory = "IsMandatory";

	/**
	 * Set isPresentForProduct.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setIsPresentForProduct (boolean IsPresentForProduct);

	/**
	 * Get isPresentForProduct.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isPresentForProduct();

    /** Column definition for IsPresentForProduct */
    public static final org.adempiere.model.ModelColumn<I_M_PriceList, Object> COLUMN_IsPresentForProduct = new org.adempiere.model.ModelColumn<I_M_PriceList, Object>(I_M_PriceList.class, "IsPresentForProduct", null);
    /** Column name IsPresentForProduct */
    public static final String COLUMNNAME_IsPresentForProduct = "IsPresentForProduct";

	/**
	 * Set Verkaufspreisliste.
	 * This is a Sales Price List
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsSOPriceList (boolean IsSOPriceList);

	/**
	 * Get Verkaufspreisliste.
	 * This is a Sales Price List
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isSOPriceList();

    /** Column definition for IsSOPriceList */
    public static final org.adempiere.model.ModelColumn<I_M_PriceList, Object> COLUMN_IsSOPriceList = new org.adempiere.model.ModelColumn<I_M_PriceList, Object>(I_M_PriceList.class, "IsSOPriceList", null);
    /** Column name IsSOPriceList */
    public static final String COLUMNNAME_IsSOPriceList = "IsSOPriceList";

	/**
	 * Set Preis inklusive Steuern.
	 * Tax is included in the price
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsTaxIncluded (boolean IsTaxIncluded);

	/**
	 * Get Preis inklusive Steuern.
	 * Tax is included in the price
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isTaxIncluded();

    /** Column definition for IsTaxIncluded */
    public static final org.adempiere.model.ModelColumn<I_M_PriceList, Object> COLUMN_IsTaxIncluded = new org.adempiere.model.ModelColumn<I_M_PriceList, Object>(I_M_PriceList.class, "IsTaxIncluded", null);
    /** Column name IsTaxIncluded */
    public static final String COLUMNNAME_IsTaxIncluded = "IsTaxIncluded";

	/**
	 * Set Preisliste.
	 * Unique identifier of a Price List
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_PriceList_ID (int M_PriceList_ID);

	/**
	 * Get Preisliste.
	 * Unique identifier of a Price List
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_PriceList_ID();

    /** Column definition for M_PriceList_ID */
    public static final org.adempiere.model.ModelColumn<I_M_PriceList, Object> COLUMN_M_PriceList_ID = new org.adempiere.model.ModelColumn<I_M_PriceList, Object>(I_M_PriceList.class, "M_PriceList_ID", null);
    /** Column name M_PriceList_ID */
    public static final String COLUMNNAME_M_PriceList_ID = "M_PriceList_ID";

	/**
	 * Set Preissystem.
	 * Ein Preissystem enthält beliebig viele, Länder-abhängige Preislisten.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_PricingSystem_ID (int M_PricingSystem_ID);

	/**
	 * Get Preissystem.
	 * Ein Preissystem enthält beliebig viele, Länder-abhängige Preislisten.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_PricingSystem_ID();

	public org.compiere.model.I_M_PricingSystem getM_PricingSystem();

	public void setM_PricingSystem(org.compiere.model.I_M_PricingSystem M_PricingSystem);

    /** Column definition for M_PricingSystem_ID */
    public static final org.adempiere.model.ModelColumn<I_M_PriceList, org.compiere.model.I_M_PricingSystem> COLUMN_M_PricingSystem_ID = new org.adempiere.model.ModelColumn<I_M_PriceList, org.compiere.model.I_M_PricingSystem>(I_M_PriceList.class, "M_PricingSystem_ID", org.compiere.model.I_M_PricingSystem.class);
    /** Column name M_PricingSystem_ID */
    public static final String COLUMNNAME_M_PricingSystem_ID = "M_PricingSystem_ID";

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
    public static final org.adempiere.model.ModelColumn<I_M_PriceList, Object> COLUMN_Name = new org.adempiere.model.ModelColumn<I_M_PriceList, Object>(I_M_PriceList.class, "Name", null);
    /** Column name Name */
    public static final String COLUMNNAME_Name = "Name";

	/**
	 * Set Price Precision.
	 * Precision (number of decimals) for the Price
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setPricePrecision (int PricePrecision);

	/**
	 * Get Price Precision.
	 * Precision (number of decimals) for the Price
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getPricePrecision();

    /** Column definition for PricePrecision */
    public static final org.adempiere.model.ModelColumn<I_M_PriceList, Object> COLUMN_PricePrecision = new org.adempiere.model.ModelColumn<I_M_PriceList, Object>(I_M_PriceList.class, "PricePrecision", null);
    /** Column name PricePrecision */
    public static final String COLUMNNAME_PricePrecision = "PricePrecision";

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
    public static final org.adempiere.model.ModelColumn<I_M_PriceList, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_M_PriceList, Object>(I_M_PriceList.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_M_PriceList, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_M_PriceList, org.compiere.model.I_AD_User>(I_M_PriceList.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
