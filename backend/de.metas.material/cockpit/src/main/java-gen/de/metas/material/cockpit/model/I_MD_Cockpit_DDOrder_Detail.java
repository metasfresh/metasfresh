package de.metas.material.cockpit.model;

import org.adempiere.model.ModelColumn;

import java.math.BigDecimal;

/** Generated Interface for MD_Cockpit_DDOrder_Detail
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_MD_Cockpit_DDOrder_Detail 
{

	String Table_Name = "MD_Cockpit_DDOrder_Detail";

//	/** AD_Table_ID=541887 */
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

	ModelColumn<I_MD_Cockpit_DDOrder_Detail, Object> COLUMN_Created = new ModelColumn<>(I_MD_Cockpit_DDOrder_Detail.class, "Created", null);
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
	 * Set DDOrderDetailType.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDDOrderDetailType (java.lang.String DDOrderDetailType);

	/**
	 * Get DDOrderDetailType.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getDDOrderDetailType();

	ModelColumn<I_MD_Cockpit_DDOrder_Detail, Object> COLUMN_DDOrderDetailType = new ModelColumn<>(I_MD_Cockpit_DDOrder_Detail.class, "DDOrderDetailType", null);
	String COLUMNNAME_DDOrderDetailType = "DDOrderDetailType";

	/**
	 * Set Distribution Order Line.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDD_OrderLine_ID (int DD_OrderLine_ID);

	/**
	 * Get Distribution Order Line.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getDD_OrderLine_ID();

	org.eevolution.model.I_DD_OrderLine getDD_OrderLine();

	void setDD_OrderLine(org.eevolution.model.I_DD_OrderLine DD_OrderLine);

	ModelColumn<I_MD_Cockpit_DDOrder_Detail, org.eevolution.model.I_DD_OrderLine> COLUMN_DD_OrderLine_ID = new ModelColumn<>(I_MD_Cockpit_DDOrder_Detail.class, "DD_OrderLine_ID", org.eevolution.model.I_DD_OrderLine.class);
	String COLUMNNAME_DD_OrderLine_ID = "DD_OrderLine_ID";

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

	ModelColumn<I_MD_Cockpit_DDOrder_Detail, Object> COLUMN_IsActive = new ModelColumn<>(I_MD_Cockpit_DDOrder_Detail.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Cockpit DDOrder detail.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setMD_Cockpit_DDOrder_Detail_ID (int MD_Cockpit_DDOrder_Detail_ID);

	/**
	 * Get Cockpit DDOrder detail.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getMD_Cockpit_DDOrder_Detail_ID();

	ModelColumn<I_MD_Cockpit_DDOrder_Detail, Object> COLUMN_MD_Cockpit_DDOrder_Detail_ID = new ModelColumn<>(I_MD_Cockpit_DDOrder_Detail.class, "MD_Cockpit_DDOrder_Detail_ID", null);
	String COLUMNNAME_MD_Cockpit_DDOrder_Detail_ID = "MD_Cockpit_DDOrder_Detail_ID";

	/**
	 * Set Material Cockpit.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setMD_Cockpit_ID (int MD_Cockpit_ID);

	/**
	 * Get Material Cockpit.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getMD_Cockpit_ID();

	de.metas.material.cockpit.model.I_MD_Cockpit getMD_Cockpit();

	void setMD_Cockpit(de.metas.material.cockpit.model.I_MD_Cockpit MD_Cockpit);

	ModelColumn<I_MD_Cockpit_DDOrder_Detail, de.metas.material.cockpit.model.I_MD_Cockpit> COLUMN_MD_Cockpit_ID = new ModelColumn<>(I_MD_Cockpit_DDOrder_Detail.class, "MD_Cockpit_ID", de.metas.material.cockpit.model.I_MD_Cockpit.class);
	String COLUMNNAME_MD_Cockpit_ID = "MD_Cockpit_ID";

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
	 * Set Pending quantity.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setQtyPending (BigDecimal QtyPending);

	/**
	 * Get Pending quantity.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyPending();

	ModelColumn<I_MD_Cockpit_DDOrder_Detail, Object> COLUMN_QtyPending = new ModelColumn<>(I_MD_Cockpit_DDOrder_Detail.class, "QtyPending", null);
	String COLUMNNAME_QtyPending = "QtyPending";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_MD_Cockpit_DDOrder_Detail, Object> COLUMN_Updated = new ModelColumn<>(I_MD_Cockpit_DDOrder_Detail.class, "Updated", null);
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
