package de.metas.contracts.model;

import org.adempiere.model.ModelColumn;

import java.math.BigDecimal;

/** Generated Interface for ModCntr_InvoicingGroup
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_ModCntr_InvoicingGroup 
{

	String Table_Name = "ModCntr_InvoicingGroup";

//	/** AD_Table_ID=542359 */
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
	 * Set Currency.
	 * The Currency for this record
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Currency_ID (int C_Currency_ID);

	/**
	 * Get Currency.
	 * The Currency for this record
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Currency_ID();

	String COLUMNNAME_C_Currency_ID = "C_Currency_ID";

	/**
	 * Set Harvesting Calendar.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Harvesting_Calendar_ID (int C_Harvesting_Calendar_ID);

	/**
	 * Get Harvesting Calendar.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Harvesting_Calendar_ID();

	org.compiere.model.I_C_Calendar getC_Harvesting_Calendar();

	void setC_Harvesting_Calendar(org.compiere.model.I_C_Calendar C_Harvesting_Calendar);

	ModelColumn<I_ModCntr_InvoicingGroup, org.compiere.model.I_C_Calendar> COLUMN_C_Harvesting_Calendar_ID = new ModelColumn<>(I_ModCntr_InvoicingGroup.class, "C_Harvesting_Calendar_ID", org.compiere.model.I_C_Calendar.class);
	String COLUMNNAME_C_Harvesting_Calendar_ID = "C_Harvesting_Calendar_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_ModCntr_InvoicingGroup, Object> COLUMN_Created = new ModelColumn<>(I_ModCntr_InvoicingGroup.class, "Created", null);
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
	 * Set Grouping Product.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setGroup_Product_ID (int Group_Product_ID);

	/**
	 * Get Grouping Product.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getGroup_Product_ID();

	String COLUMNNAME_Group_Product_ID = "Group_Product_ID";

	/**
	 * Set Harvesting Year.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setHarvesting_Year_ID (int Harvesting_Year_ID);

	/**
	 * Get Harvesting Year.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getHarvesting_Year_ID();

	org.compiere.model.I_C_Year getHarvesting_Year();

	void setHarvesting_Year(org.compiere.model.I_C_Year Harvesting_Year);

	ModelColumn<I_ModCntr_InvoicingGroup, org.compiere.model.I_C_Year> COLUMN_Harvesting_Year_ID = new ModelColumn<>(I_ModCntr_InvoicingGroup.class, "Harvesting_Year_ID", org.compiere.model.I_C_Year.class);
	String COLUMNNAME_Harvesting_Year_ID = "Harvesting_Year_ID";

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

	ModelColumn<I_ModCntr_InvoicingGroup, Object> COLUMN_IsActive = new ModelColumn<>(I_ModCntr_InvoicingGroup.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Invoice Group.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setModCntr_InvoicingGroup_ID (int ModCntr_InvoicingGroup_ID);

	/**
	 * Get Invoice Group.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getModCntr_InvoicingGroup_ID();

	ModelColumn<I_ModCntr_InvoicingGroup, Object> COLUMN_ModCntr_InvoicingGroup_ID = new ModelColumn<>(I_ModCntr_InvoicingGroup.class, "ModCntr_InvoicingGroup_ID", null);
	String COLUMNNAME_ModCntr_InvoicingGroup_ID = "ModCntr_InvoicingGroup_ID";

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

	ModelColumn<I_ModCntr_InvoicingGroup, Object> COLUMN_Name = new ModelColumn<>(I_ModCntr_InvoicingGroup.class, "Name", null);
	String COLUMNNAME_Name = "Name";

	/**
	 * Set Total interest.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setTotalInterest (BigDecimal TotalInterest);

	/**
	 * Get Total interest.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getTotalInterest();

	ModelColumn<I_ModCntr_InvoicingGroup, Object> COLUMN_TotalInterest = new ModelColumn<>(I_ModCntr_InvoicingGroup.class, "TotalInterest", null);
	String COLUMNNAME_TotalInterest = "TotalInterest";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_ModCntr_InvoicingGroup, Object> COLUMN_Updated = new ModelColumn<>(I_ModCntr_InvoicingGroup.class, "Updated", null);
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
