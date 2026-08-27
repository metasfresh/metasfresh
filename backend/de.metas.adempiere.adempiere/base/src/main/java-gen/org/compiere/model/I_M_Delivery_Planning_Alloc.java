package org.compiere.model;

import org.adempiere.model.ModelColumn;

/** Generated Interface for M_Delivery_Planning_Alloc
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_M_Delivery_Planning_Alloc 
{

	String Table_Name = "M_Delivery_Planning_Alloc";

//	/** AD_Table_ID=542641 */
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

	ModelColumn<I_M_Delivery_Planning_Alloc, Object> COLUMN_Created = new ModelColumn<>(I_M_Delivery_Planning_Alloc.class, "Created", null);
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

	ModelColumn<I_M_Delivery_Planning_Alloc, Object> COLUMN_IsActive = new ModelColumn<>(I_M_Delivery_Planning_Alloc.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Line.
	 * Line No
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setLineNo (int LineNo);

	/**
	 * Get Line.
	 * Line No
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getLineNo();

	ModelColumn<I_M_Delivery_Planning_Alloc, Object> COLUMN_LineNo = new ModelColumn<>(I_M_Delivery_Planning_Alloc.class, "LineNo", null);
	String COLUMNNAME_LineNo = "LineNo";

	/**
	 * Set Delivery Planning Allocation.
	 * Allocation of a delivery planning to a delivery instruction.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_Delivery_Planning_Alloc_ID (int M_Delivery_Planning_Alloc_ID);

	/**
	 * Get Delivery Planning Allocation.
	 * Allocation of a delivery planning to a delivery instruction.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_Delivery_Planning_Alloc_ID();

	ModelColumn<I_M_Delivery_Planning_Alloc, Object> COLUMN_M_Delivery_Planning_Alloc_ID = new ModelColumn<>(I_M_Delivery_Planning_Alloc.class, "M_Delivery_Planning_Alloc_ID", null);
	String COLUMNNAME_M_Delivery_Planning_Alloc_ID = "M_Delivery_Planning_Alloc_ID";

	/**
	 * Set Delivery Planning.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_Delivery_Planning_ID (int M_Delivery_Planning_ID);

	/**
	 * Get Delivery Planning.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_Delivery_Planning_ID();

	ModelColumn<I_M_Delivery_Planning_Alloc, org.compiere.model.I_M_Delivery_Planning> COLUMN_M_Delivery_Planning_ID = new ModelColumn<>(I_M_Delivery_Planning_Alloc.class, "M_Delivery_Planning_ID", org.compiere.model.I_M_Delivery_Planning.class);
	String COLUMNNAME_M_Delivery_Planning_ID = "M_Delivery_Planning_ID";

	/**
	 * Set Transportation Order.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_ShipperTransportation_ID (int M_ShipperTransportation_ID);

	/**
	 * Get Transportation Order.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_ShipperTransportation_ID();

	ModelColumn<I_M_Delivery_Planning_Alloc, Object> COLUMN_M_ShipperTransportation_ID = new ModelColumn<>(I_M_Delivery_Planning_Alloc.class, "M_ShipperTransportation_ID", null);
	String COLUMNNAME_M_ShipperTransportation_ID = "M_ShipperTransportation_ID";

	/**
	 * Set Shipping Package.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_ShippingPackage_ID (int M_ShippingPackage_ID);

	/**
	 * Get Shipping Package.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_ShippingPackage_ID();

	ModelColumn<I_M_Delivery_Planning_Alloc, Object> COLUMN_M_ShippingPackage_ID = new ModelColumn<>(I_M_Delivery_Planning_Alloc.class, "M_ShippingPackage_ID", null);
	String COLUMNNAME_M_ShippingPackage_ID = "M_ShippingPackage_ID";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_M_Delivery_Planning_Alloc, Object> COLUMN_Updated = new ModelColumn<>(I_M_Delivery_Planning_Alloc.class, "Updated", null);
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
