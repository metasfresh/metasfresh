package org.compiere.model;

import lombok.NonNull;
import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for M_PriceList_Version
 *  @author metasfresh (generated)
 */
@SuppressWarnings("unused")
public interface I_M_PriceList_Version
{

	String Table_Name = "M_PriceList_Version";

//	/** AD_Table_ID=295 */
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

	ModelColumn<I_M_PriceList_Version, Object> COLUMN_Created = new ModelColumn<>(I_M_PriceList_Version.class, "Created", null);
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
	 * Set Region.
	 * Identifies a geographical Region
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Region_ID (int C_Region_ID);

	/**
	 * Get Region.
	 * Identifies a geographical Region
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Region_ID();

	@Nullable org.compiere.model.I_C_Region getC_Region();

	void setC_Region(@Nullable org.compiere.model.I_C_Region C_Region);

	ModelColumn<I_M_PriceList_Version, org.compiere.model.I_C_Region> COLUMN_C_Region_ID = new ModelColumn<>(I_M_PriceList_Version.class, "C_Region_ID", org.compiere.model.I_C_Region.class);
	String COLUMNNAME_C_Region_ID = "C_Region_ID";

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

	ModelColumn<I_M_PriceList_Version, Object> COLUMN_Description = new ModelColumn<>(I_M_PriceList_Version.class, "Description", null);
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

	ModelColumn<I_M_PriceList_Version, Object> COLUMN_IsActive = new ModelColumn<>(I_M_PriceList_Version.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

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

	ModelColumn<I_M_PriceList_Version, org.compiere.model.I_M_DiscountSchema> COLUMN_M_DiscountSchema_ID = new ModelColumn<>(I_M_PriceList_Version.class, "M_DiscountSchema_ID", org.compiere.model.I_M_DiscountSchema.class);
	String COLUMNNAME_M_DiscountSchema_ID = "M_DiscountSchema_ID";

	/**
	 * Set Price List.
	 * Unique identifier of a Price List
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_PriceList_ID (int M_PriceList_ID);

	/**
	 * Get Price List.
	 * Unique identifier of a Price List
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_PriceList_ID();

	String COLUMNNAME_M_PriceList_ID = "M_PriceList_ID";

	/**
	 * Set Basis Pricelist Version.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_Pricelist_Version_Base_ID (int M_Pricelist_Version_Base_ID);

	/**
	 * Get Basis Pricelist Version.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_Pricelist_Version_Base_ID();

	String COLUMNNAME_M_Pricelist_Version_Base_ID = "M_Pricelist_Version_Base_ID";

	/**
	 * Set Price List Version.
	 * Identifies a unique instance of a Price List
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_PriceList_Version_ID (int M_PriceList_Version_ID);

	/**
	 * Get Price List Version.
	 * Identifies a unique instance of a Price List
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_PriceList_Version_ID();

	ModelColumn<I_M_PriceList_Version, Object> COLUMN_M_PriceList_Version_ID = new ModelColumn<>(I_M_PriceList_Version.class, "M_PriceList_Version_ID", null);
	String COLUMNNAME_M_PriceList_Version_ID = "M_PriceList_Version_ID";

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

	ModelColumn<I_M_PriceList_Version, Object> COLUMN_Name = new ModelColumn<>(I_M_PriceList_Version.class, "Name", null);
	String COLUMNNAME_Name = "Name";

	/**
	 * Set Create Price List .
	 * Create Prices based on parameters of this version
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setProcCreate (@Nullable java.lang.String ProcCreate);

	/**
	 * Get Create Price List .
	 * Create Prices based on parameters of this version
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getProcCreate();

	ModelColumn<I_M_PriceList_Version, Object> COLUMN_ProcCreate = new ModelColumn<>(I_M_PriceList_Version.class, "ProcCreate", null);
	String COLUMNNAME_ProcCreate = "ProcCreate";

	/**
	 * Set Processed.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setProcessed (boolean Processed);

	/**
	 * Get Processed.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isProcessed();

	ModelColumn<I_M_PriceList_Version, Object> COLUMN_Processed = new ModelColumn<>(I_M_PriceList_Version.class, "Processed", null);
	String COLUMNNAME_Processed = "Processed";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_M_PriceList_Version, Object> COLUMN_Updated = new ModelColumn<>(I_M_PriceList_Version.class, "Updated", null);
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
    @NonNull
	java.sql.Timestamp getValidFrom();

	ModelColumn<I_M_PriceList_Version, Object> COLUMN_ValidFrom = new ModelColumn<>(I_M_PriceList_Version.class, "ValidFrom", null);
	String COLUMNNAME_ValidFrom = "ValidFrom";
}
