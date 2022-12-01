package org.compiere.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;

/** Generated Interface for M_Warehouse
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_M_Warehouse 
{

	String Table_Name = "M_Warehouse";

//	/** AD_Table_ID=190 */
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
	 * Set Activity.
	 * Business Activity
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Activity_ID (int C_Activity_ID);

	/**
	 * Get Activity.
	 * Business Activity
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Activity_ID();

	String COLUMNNAME_C_Activity_ID = "C_Activity_ID";

	/**
	 * Set Business Partner.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_BPartner_ID (int C_BPartner_ID);

	/**
	 * Get Business Partner.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_BPartner_ID();

	String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/**
	 * Set Location.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_BPartner_Location_ID (int C_BPartner_Location_ID);

	/**
	 * Get Location.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_BPartner_Location_ID();

	String COLUMNNAME_C_BPartner_Location_ID = "C_BPartner_Location_ID";

	/**
	 * Set Location.
	 * Location or Address
	 *
	 * <br>Type: Location
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Location_ID (int C_Location_ID);

	/**
	 * Get Location.
	 * Location or Address
	 *
	 * <br>Type: Location
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Location_ID();

	@Nullable org.compiere.model.I_C_Location getC_Location();

	void setC_Location(@Nullable org.compiere.model.I_C_Location C_Location);

	ModelColumn<I_M_Warehouse, org.compiere.model.I_C_Location> COLUMN_C_Location_ID = new ModelColumn<>(I_M_Warehouse.class, "C_Location_ID", org.compiere.model.I_C_Location.class);
	String COLUMNNAME_C_Location_ID = "C_Location_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_M_Warehouse, Object> COLUMN_Created = new ModelColumn<>(I_M_Warehouse.class, "Created", null);
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

	ModelColumn<I_M_Warehouse, Object> COLUMN_Description = new ModelColumn<>(I_M_Warehouse.class, "Description", null);
	String COLUMNNAME_Description = "Description";

	/**
	 * Set External ID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setExternalId (@Nullable java.lang.String ExternalId);

	/**
	 * Get External ID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getExternalId();

	ModelColumn<I_M_Warehouse, Object> COLUMN_ExternalId = new ModelColumn<>(I_M_Warehouse.class, "ExternalId", null);
	String COLUMNNAME_ExternalId = "ExternalId";

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

	ModelColumn<I_M_Warehouse, Object> COLUMN_IsActive = new ModelColumn<>(I_M_Warehouse.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set In Transit.
	 * Movement is in transit
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIsInTransit (boolean IsInTransit);

	/**
	 * Get In Transit.
	 * Movement is in transit
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isInTransit();

	ModelColumn<I_M_Warehouse, Object> COLUMN_IsInTransit = new ModelColumn<>(I_M_Warehouse.class, "IsInTransit", null);
	String COLUMNNAME_IsInTransit = "IsInTransit";

	/**
	 * Set Beanstandungslager.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsIssueWarehouse (boolean IsIssueWarehouse);

	/**
	 * Get Beanstandungslager.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isIssueWarehouse();

	ModelColumn<I_M_Warehouse, Object> COLUMN_IsIssueWarehouse = new ModelColumn<>(I_M_Warehouse.class, "IsIssueWarehouse", null);
	String COLUMNNAME_IsIssueWarehouse = "IsIssueWarehouse";

	/**
	 * Set Kommissionierlager.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsPickingWarehouse (boolean IsPickingWarehouse);

	/**
	 * Get Kommissionierlager.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isPickingWarehouse();

	ModelColumn<I_M_Warehouse, Object> COLUMN_IsPickingWarehouse = new ModelColumn<>(I_M_Warehouse.class, "IsPickingWarehouse", null);
	String COLUMNNAME_IsPickingWarehouse = "IsPickingWarehouse";

	/**
	 * Set IsQualityReturnWarehouse.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsQualityReturnWarehouse (boolean IsQualityReturnWarehouse);

	/**
	 * Get IsQualityReturnWarehouse.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isQualityReturnWarehouse();

	ModelColumn<I_M_Warehouse, Object> COLUMN_IsQualityReturnWarehouse = new ModelColumn<>(I_M_Warehouse.class, "IsQualityReturnWarehouse", null);
	String COLUMNNAME_IsQualityReturnWarehouse = "IsQualityReturnWarehouse";

	/**
	 * Set IsQuarantineWarehouse.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsQuarantineWarehouse (boolean IsQuarantineWarehouse);

	/**
	 * Get IsQuarantineWarehouse.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isQuarantineWarehouse();

	ModelColumn<I_M_Warehouse, Object> COLUMN_IsQuarantineWarehouse = new ModelColumn<>(I_M_Warehouse.class, "IsQuarantineWarehouse", null);
	String COLUMNNAME_IsQuarantineWarehouse = "IsQuarantineWarehouse";

	/**
	 * Set Receive as Source HU.
	 * Automatically marks all received HUs carrying BOM components as 'Source HUs'.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsReceiveAsSourceHU (boolean IsReceiveAsSourceHU);

	/**
	 * Get Receive as Source HU.
	 * Automatically marks all received HUs carrying BOM components as 'Source HUs'.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isReceiveAsSourceHU();

	ModelColumn<I_M_Warehouse, Object> COLUMN_IsReceiveAsSourceHU = new ModelColumn<>(I_M_Warehouse.class, "IsReceiveAsSourceHU", null);
	String COLUMNNAME_IsReceiveAsSourceHU = "IsReceiveAsSourceHU";

	/**
	 * Set Warehouse.
	 * Storage Warehouse and Service Point
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_Warehouse_ID (int M_Warehouse_ID);

	/**
	 * Get Warehouse.
	 * Storage Warehouse and Service Point
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_Warehouse_ID();

	ModelColumn<I_M_Warehouse, Object> COLUMN_M_Warehouse_ID = new ModelColumn<>(I_M_Warehouse.class, "M_Warehouse_ID", null);
	String COLUMNNAME_M_Warehouse_ID = "M_Warehouse_ID";

	/**
	 * Set Warehouse Picking Group.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_Warehouse_PickingGroup_ID (int M_Warehouse_PickingGroup_ID);

	/**
	 * Get Warehouse Picking Group.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_Warehouse_PickingGroup_ID();

	@Nullable org.compiere.model.I_M_Warehouse_PickingGroup getM_Warehouse_PickingGroup();

	void setM_Warehouse_PickingGroup(@Nullable org.compiere.model.I_M_Warehouse_PickingGroup M_Warehouse_PickingGroup);

	ModelColumn<I_M_Warehouse, org.compiere.model.I_M_Warehouse_PickingGroup> COLUMN_M_Warehouse_PickingGroup_ID = new ModelColumn<>(I_M_Warehouse.class, "M_Warehouse_PickingGroup_ID", org.compiere.model.I_M_Warehouse_PickingGroup.class);
	String COLUMNNAME_M_Warehouse_PickingGroup_ID = "M_Warehouse_PickingGroup_ID";

	/**
	 * Set Warehouse Type.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_Warehouse_Type_ID (int M_Warehouse_Type_ID);

	/**
	 * Get Warehouse Type.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_Warehouse_Type_ID();

	@Nullable org.compiere.model.I_M_Warehouse_Type getM_Warehouse_Type();

	void setM_Warehouse_Type(@Nullable org.compiere.model.I_M_Warehouse_Type M_Warehouse_Type);

	ModelColumn<I_M_Warehouse, org.compiere.model.I_M_Warehouse_Type> COLUMN_M_Warehouse_Type_ID = new ModelColumn<>(I_M_Warehouse.class, "M_Warehouse_Type_ID", org.compiere.model.I_M_Warehouse_Type.class);
	String COLUMNNAME_M_Warehouse_Type_ID = "M_Warehouse_Type_ID";

	/**
	 * Set Source Warehouse.
	 * Optional Warehouse to replenish from
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_WarehouseSource_ID (int M_WarehouseSource_ID);

	/**
	 * Get Source Warehouse.
	 * Optional Warehouse to replenish from
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_WarehouseSource_ID();

	String COLUMNNAME_M_WarehouseSource_ID = "M_WarehouseSource_ID";

	/**
	 * Set Manufacturing Warehouse Group.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setManufacturing_Warehouse_Group_ID (int Manufacturing_Warehouse_Group_ID);

	/**
	 * Get Manufacturing Warehouse Group.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getManufacturing_Warehouse_Group_ID();

	@Nullable org.compiere.model.I_M_Warehouse_Group getManufacturing_Warehouse_Group();

	void setManufacturing_Warehouse_Group(@Nullable org.compiere.model.I_M_Warehouse_Group Manufacturing_Warehouse_Group);

	ModelColumn<I_M_Warehouse, org.compiere.model.I_M_Warehouse_Group> COLUMN_Manufacturing_Warehouse_Group_ID = new ModelColumn<>(I_M_Warehouse.class, "Manufacturing_Warehouse_Group_ID", org.compiere.model.I_M_Warehouse_Group.class);
	String COLUMNNAME_Manufacturing_Warehouse_Group_ID = "Manufacturing_Warehouse_Group_ID";

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

	ModelColumn<I_M_Warehouse, Object> COLUMN_MRP_Exclude = new ModelColumn<>(I_M_Warehouse.class, "MRP_Exclude", null);
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

	ModelColumn<I_M_Warehouse, Object> COLUMN_Name = new ModelColumn<>(I_M_Warehouse.class, "Name", null);
	String COLUMNNAME_Name = "Name";

	/**
	 * Set Produktionsstätte.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPP_Plant_ID (int PP_Plant_ID);

	/**
	 * Get Produktionsstätte.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getPP_Plant_ID();

	@Nullable org.compiere.model.I_S_Resource getPP_Plant();

	void setPP_Plant(@Nullable org.compiere.model.I_S_Resource PP_Plant);

	ModelColumn<I_M_Warehouse, org.compiere.model.I_S_Resource> COLUMN_PP_Plant_ID = new ModelColumn<>(I_M_Warehouse.class, "PP_Plant_ID", org.compiere.model.I_S_Resource.class);
	String COLUMNNAME_PP_Plant_ID = "PP_Plant_ID";

	/**
	 * Set Replenishment Class.
	 * Custom class to calculate Quantity to Order
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setReplenishmentClass (@Nullable java.lang.String ReplenishmentClass);

	/**
	 * Get Replenishment Class.
	 * Custom class to calculate Quantity to Order
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getReplenishmentClass();

	ModelColumn<I_M_Warehouse, Object> COLUMN_ReplenishmentClass = new ModelColumn<>(I_M_Warehouse.class, "ReplenishmentClass", null);
	String COLUMNNAME_ReplenishmentClass = "ReplenishmentClass";

	/**
	 * Set Element-Trenner.
	 * Element Separator
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setSeparator (java.lang.String Separator);

	/**
	 * Get Element-Trenner.
	 * Element Separator
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getSeparator();

	ModelColumn<I_M_Warehouse, Object> COLUMN_Separator = new ModelColumn<>(I_M_Warehouse.class, "Separator", null);
	String COLUMNNAME_Separator = "Separator";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_M_Warehouse, Object> COLUMN_Updated = new ModelColumn<>(I_M_Warehouse.class, "Updated", null);
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

	ModelColumn<I_M_Warehouse, Object> COLUMN_Value = new ModelColumn<>(I_M_Warehouse.class, "Value", null);
	String COLUMNNAME_Value = "Value";
}
