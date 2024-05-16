package de.metas.contracts.model;

import org.adempiere.model.ModelColumn;

import java.math.BigDecimal;

/** Generated Interface for ModCntr_Settings
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_ModCntr_Settings 
{

	String Table_Name = "ModCntr_Settings";

//	/** AD_Table_ID=542339 */
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
	 * Set Additional interest days.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAddInterestDays (int AddInterestDays);

	/**
	 * Get Additional interest days.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAddInterestDays();

	ModelColumn<I_ModCntr_Settings, Object> COLUMN_AddInterestDays = new ModelColumn<>(I_ModCntr_Settings.class, "AddInterestDays", null);
	String COLUMNNAME_AddInterestDays = "AddInterestDays";

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
	 * Set Calendar.
	 * Accounting Calendar Name
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Calendar_ID (int C_Calendar_ID);

	/**
	 * Get Calendar.
	 * Accounting Calendar Name
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Calendar_ID();

	org.compiere.model.I_C_Calendar getC_Calendar();

	void setC_Calendar(org.compiere.model.I_C_Calendar C_Calendar);

	ModelColumn<I_ModCntr_Settings, org.compiere.model.I_C_Calendar> COLUMN_C_Calendar_ID = new ModelColumn<>(I_ModCntr_Settings.class, "C_Calendar_ID", org.compiere.model.I_C_Calendar.class);
	String COLUMNNAME_C_Calendar_ID = "C_Calendar_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_ModCntr_Settings, Object> COLUMN_Created = new ModelColumn<>(I_ModCntr_Settings.class, "Created", null);
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
	 * Set Year.
	 * Calendar Year
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Year_ID (int C_Year_ID);

	/**
	 * Get Year.
	 * Calendar Year
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Year_ID();

	org.compiere.model.I_C_Year getC_Year();

	void setC_Year(org.compiere.model.I_C_Year C_Year);

	ModelColumn<I_ModCntr_Settings, org.compiere.model.I_C_Year> COLUMN_C_Year_ID = new ModelColumn<>(I_ModCntr_Settings.class, "C_Year_ID", org.compiere.model.I_C_Year.class);
	String COLUMNNAME_C_Year_ID = "C_Year_ID";

	/**
	 * Set Interest rate.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setInterestRate (BigDecimal InterestRate);

	/**
	 * Get Interest rate.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getInterestRate();

	ModelColumn<I_ModCntr_Settings, Object> COLUMN_InterestRate = new ModelColumn<>(I_ModCntr_Settings.class, "InterestRate", null);
	String COLUMNNAME_InterestRate = "InterestRate";

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

	ModelColumn<I_ModCntr_Settings, Object> COLUMN_IsActive = new ModelColumn<>(I_ModCntr_Settings.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Sales Transaction.
	 * This is a Sales Transaction
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsSOTrx (boolean IsSOTrx);

	/**
	 * Get Sales Transaction.
	 * This is a Sales Transaction
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isSOTrx();

	ModelColumn<I_ModCntr_Settings, Object> COLUMN_IsSOTrx = new ModelColumn<>(I_ModCntr_Settings.class, "IsSOTrx", null);
	String COLUMNNAME_IsSOTrx = "IsSOTrx";

	/**
	 * Set Co-Product.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_Co_Product_ID (int M_Co_Product_ID);

	/**
	 * Get Co-Product.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_Co_Product_ID();

	String COLUMNNAME_M_Co_Product_ID = "M_Co_Product_ID";

	/**
	 * Set Modular Contract Settings.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setModCntr_Settings_ID (int ModCntr_Settings_ID);

	/**
	 * Get Modular Contract Settings.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getModCntr_Settings_ID();

	ModelColumn<I_ModCntr_Settings, Object> COLUMN_ModCntr_Settings_ID = new ModelColumn<>(I_ModCntr_Settings.class, "ModCntr_Settings_ID", null);
	String COLUMNNAME_ModCntr_Settings_ID = "ModCntr_Settings_ID";

	/**
	 * Set Pricing System.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_PricingSystem_ID (int M_PricingSystem_ID);

	/**
	 * Get Pricing System.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_PricingSystem_ID();

	String COLUMNNAME_M_PricingSystem_ID = "M_PricingSystem_ID";

	/**
	 * Set Processed Product.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_Processed_Product_ID (int M_Processed_Product_ID);

	/**
	 * Get Processed Product.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_Processed_Product_ID();

	String COLUMNNAME_M_Processed_Product_ID = "M_Processed_Product_ID";

	/**
	 * Set Raw Product.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_Raw_Product_ID (int M_Raw_Product_ID);

	/**
	 * Get Raw Product.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_Raw_Product_ID();

	String COLUMNNAME_M_Raw_Product_ID = "M_Raw_Product_ID";

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

	ModelColumn<I_ModCntr_Settings, Object> COLUMN_Name = new ModelColumn<>(I_ModCntr_Settings.class, "Name", null);
	String COLUMNNAME_Name = "Name";

	/**
	 * Set Storage Cost Start Date.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setStorageCostStartDate (java.sql.Timestamp StorageCostStartDate);

	/**
	 * Get Storage Cost Start Date.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getStorageCostStartDate();

	ModelColumn<I_ModCntr_Settings, Object> COLUMN_StorageCostStartDate = new ModelColumn<>(I_ModCntr_Settings.class, "StorageCostStartDate", null);
	String COLUMNNAME_StorageCostStartDate = "StorageCostStartDate";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_ModCntr_Settings, Object> COLUMN_Updated = new ModelColumn<>(I_ModCntr_Settings.class, "Updated", null);
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
