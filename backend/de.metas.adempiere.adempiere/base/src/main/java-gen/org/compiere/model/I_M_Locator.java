package org.compiere.model;

import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for M_Locator
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_M_Locator 
{

	String Table_Name = "M_Locator";

//	/** AD_Table_ID=207 */
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

	ModelColumn<I_M_Locator, Object> COLUMN_Created = new ModelColumn<>(I_M_Locator.class, "Created", null);
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
	 * Set Date last inventory count.
	 * Date of Last Inventory Count
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDateLastInventory (@Nullable java.sql.Timestamp DateLastInventory);

	/**
	 * Get Date last inventory count.
	 * Date of Last Inventory Count
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getDateLastInventory();

	ModelColumn<I_M_Locator, Object> COLUMN_DateLastInventory = new ModelColumn<>(I_M_Locator.class, "DateLastInventory", null);
	String COLUMNNAME_DateLastInventory = "DateLastInventory";

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

	ModelColumn<I_M_Locator, Object> COLUMN_IsActive = new ModelColumn<>(I_M_Locator.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

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

	ModelColumn<I_M_Locator, Object> COLUMN_IsDefault = new ModelColumn<>(I_M_Locator.class, "IsDefault", null);
	String COLUMNNAME_IsDefault = "IsDefault";

	/**
	 * Set Ground Floor Locator.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsGroundLocator (boolean IsGroundLocator);

	/**
	 * Get Ground Floor Locator.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isGroundLocator();

	ModelColumn<I_M_Locator, Object> COLUMN_IsGroundLocator = new ModelColumn<>(I_M_Locator.class, "IsGroundLocator", null);
	String COLUMNNAME_IsGroundLocator = "IsGroundLocator";

	/**
	 * Set Locator.
	 * Warehouse Locator
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_Locator_ID (int M_Locator_ID);

	/**
	 * Get Locator.
	 * Warehouse Locator
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_Locator_ID();

	ModelColumn<I_M_Locator, Object> COLUMN_M_Locator_ID = new ModelColumn<>(I_M_Locator.class, "M_Locator_ID", null);
	String COLUMNNAME_M_Locator_ID = "M_Locator_ID";

	/**
	 * Set Warehouse.
	 * Storage Warehouse and Service Point
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_Warehouse_ID (int M_Warehouse_ID);

	/**
	 * Get Warehouse.
	 * Storage Warehouse and Service Point
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_Warehouse_ID();

	String COLUMNNAME_M_Warehouse_ID = "M_Warehouse_ID";

	/**
	 * Set Relative Priority.
	 * Where inventory should be picked from first
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPriorityNo (int PriorityNo);

	/**
	 * Get Relative Priority.
	 * Where inventory should be picked from first
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getPriorityNo();

	ModelColumn<I_M_Locator, Object> COLUMN_PriorityNo = new ModelColumn<>(I_M_Locator.class, "PriorityNo", null);
	String COLUMNNAME_PriorityNo = "PriorityNo";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_M_Locator, Object> COLUMN_Updated = new ModelColumn<>(I_M_Locator.class, "Updated", null);
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

	ModelColumn<I_M_Locator, Object> COLUMN_Value = new ModelColumn<>(I_M_Locator.class, "Value", null);
	String COLUMNNAME_Value = "Value";

	/**
	 * Set Aisle.
	 * X dimension, e.g., Aisle
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setX (java.lang.String X);

	/**
	 * Get Aisle.
	 * X dimension, e.g., Aisle
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getX();

	ModelColumn<I_M_Locator, Object> COLUMN_X = new ModelColumn<>(I_M_Locator.class, "X", null);
	String COLUMNNAME_X = "X";

	/**
	 * Set Rack.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setX1 (java.lang.String X1);

	/**
	 * Get Rack.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getX1();

	ModelColumn<I_M_Locator, Object> COLUMN_X1 = new ModelColumn<>(I_M_Locator.class, "X1", null);
	String COLUMNNAME_X1 = "X1";

	/**
	 * Set Tray.
	 * Y dimension, e.g., Bin
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setY (java.lang.String Y);

	/**
	 * Get Tray.
	 * Y dimension, e.g., Bin
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getY();

	ModelColumn<I_M_Locator, Object> COLUMN_Y = new ModelColumn<>(I_M_Locator.class, "Y", null);
	String COLUMNNAME_Y = "Y";

	/**
	 * Set Level.
	 * Z dimension, e.g., Level
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setZ (java.lang.String Z);

	/**
	 * Get Level.
	 * Z dimension, e.g., Level
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getZ();

	ModelColumn<I_M_Locator, Object> COLUMN_Z = new ModelColumn<>(I_M_Locator.class, "Z", null);
	String COLUMNNAME_Z = "Z";
}
