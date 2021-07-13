package de.metas.contracts.commission.model;

import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for C_HierarchyCommissionSettings
 *  @author metasfresh (generated) 
 */
public interface I_C_HierarchyCommissionSettings 
{

	String Table_Name = "C_HierarchyCommissionSettings";

//	/** AD_Table_ID=541425 */
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
	 * Set Hierarchy commission settings.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_HierarchyCommissionSettings_ID (int C_HierarchyCommissionSettings_ID);

	/**
	 * Get Hierarchy commission settings.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_HierarchyCommissionSettings_ID();

	ModelColumn<I_C_HierarchyCommissionSettings, Object> COLUMN_C_HierarchyCommissionSettings_ID = new ModelColumn<>(I_C_HierarchyCommissionSettings.class, "C_HierarchyCommissionSettings_ID", null);
	String COLUMNNAME_C_HierarchyCommissionSettings_ID = "C_HierarchyCommissionSettings_ID";

	/**
	 * Set Commission product.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setCommission_Product_ID (int Commission_Product_ID);

	/**
	 * Get Commission product.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getCommission_Product_ID();

	String COLUMNNAME_Commission_Product_ID = "Commission_Product_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_C_HierarchyCommissionSettings, Object> COLUMN_Created = new ModelColumn<>(I_C_HierarchyCommissionSettings.class, "Created", null);
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
	 * Set Description.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDescription (@Nullable java.lang.String Description);

	/**
	 * Get Description.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDescription();

	ModelColumn<I_C_HierarchyCommissionSettings, Object> COLUMN_Description = new ModelColumn<>(I_C_HierarchyCommissionSettings.class, "Description", null);
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

	ModelColumn<I_C_HierarchyCommissionSettings, Object> COLUMN_IsActive = new ModelColumn<>(I_C_HierarchyCommissionSettings.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Commission deed for own revenue.
	 * If set and the sales-rep makes a purchase of their own, then a 0% commission deed record is create for this.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsCreateShareForOwnRevenue (boolean IsCreateShareForOwnRevenue);

	/**
	 * Get Commission deed for own revenue.
	 * If set and the sales-rep makes a purchase of their own, then a 0% commission deed record is create for this.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isCreateShareForOwnRevenue();

	ModelColumn<I_C_HierarchyCommissionSettings, Object> COLUMN_IsCreateShareForOwnRevenue = new ModelColumn<>(I_C_HierarchyCommissionSettings.class, "IsCreateShareForOwnRevenue", null);
	String COLUMNNAME_IsCreateShareForOwnRevenue = "IsCreateShareForOwnRevenue";

	/**
	 * Set Subtract commission from base.
	 * Specifies if the commission points from lower levels shall be subtracted from the base points for the current level.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsSubtractLowerLevelCommissionFromBase (boolean IsSubtractLowerLevelCommissionFromBase);

	/**
	 * Get Subtract commission from base.
	 * Specifies if the commission points from lower levels shall be subtracted from the base points for the current level.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isSubtractLowerLevelCommissionFromBase();

	ModelColumn<I_C_HierarchyCommissionSettings, Object> COLUMN_IsSubtractLowerLevelCommissionFromBase = new ModelColumn<>(I_C_HierarchyCommissionSettings.class, "IsSubtractLowerLevelCommissionFromBase", null);
	String COLUMNNAME_IsSubtractLowerLevelCommissionFromBase = "IsSubtractLowerLevelCommissionFromBase";

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

	ModelColumn<I_C_HierarchyCommissionSettings, Object> COLUMN_Name = new ModelColumn<>(I_C_HierarchyCommissionSettings.class, "Name", null);
	String COLUMNNAME_Name = "Name";

	/**
	 * Set Points precision.
	 * Number of digits after the decimal point to which the system rounds when computing commission points.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPointsPrecision (int PointsPrecision);

	/**
	 * Get Points precision.
	 * Number of digits after the decimal point to which the system rounds when computing commission points.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getPointsPrecision();

	ModelColumn<I_C_HierarchyCommissionSettings, Object> COLUMN_PointsPrecision = new ModelColumn<>(I_C_HierarchyCommissionSettings.class, "PointsPrecision", null);
	String COLUMNNAME_PointsPrecision = "PointsPrecision";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_C_HierarchyCommissionSettings, Object> COLUMN_Updated = new ModelColumn<>(I_C_HierarchyCommissionSettings.class, "Updated", null);
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
