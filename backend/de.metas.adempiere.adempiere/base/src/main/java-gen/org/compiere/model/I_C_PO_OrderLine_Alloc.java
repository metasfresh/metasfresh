package org.compiere.model;

import org.adempiere.model.ModelColumn;

/** Generated Interface for C_PO_OrderLine_Alloc
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_C_PO_OrderLine_Alloc 
{

	String Table_Name = "C_PO_OrderLine_Alloc";

//	/** AD_Table_ID=541863 */
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
	 * Set Purchase Order.
	 * Purchase Order
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_OrderPO_ID (int C_OrderPO_ID);

	/**
	 * Get Purchase Order.
	 * Purchase Order
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_OrderPO_ID();

	org.compiere.model.I_C_Order getC_OrderPO();

	void setC_OrderPO(org.compiere.model.I_C_Order C_OrderPO);

	ModelColumn<I_C_PO_OrderLine_Alloc, org.compiere.model.I_C_Order> COLUMN_C_OrderPO_ID = new ModelColumn<>(I_C_PO_OrderLine_Alloc.class, "C_OrderPO_ID", org.compiere.model.I_C_Order.class);
	String COLUMNNAME_C_OrderPO_ID = "C_OrderPO_ID";

	/**
	 * Set Order.
	 * Order
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_OrderSO_ID (int C_OrderSO_ID);

	/**
	 * Get Order.
	 * Order
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_OrderSO_ID();

	org.compiere.model.I_C_Order getC_OrderSO();

	void setC_OrderSO(org.compiere.model.I_C_Order C_OrderSO);

	ModelColumn<I_C_PO_OrderLine_Alloc, org.compiere.model.I_C_Order> COLUMN_C_OrderSO_ID = new ModelColumn<>(I_C_PO_OrderLine_Alloc.class, "C_OrderSO_ID", org.compiere.model.I_C_Order.class);
	String COLUMNNAME_C_OrderSO_ID = "C_OrderSO_ID";

	/**
	 * Set C_PO_OrderLine_Alloc.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_PO_OrderLine_Alloc_ID (int C_PO_OrderLine_Alloc_ID);

	/**
	 * Get C_PO_OrderLine_Alloc.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_PO_OrderLine_Alloc_ID();

	ModelColumn<I_C_PO_OrderLine_Alloc, Object> COLUMN_C_PO_OrderLine_Alloc_ID = new ModelColumn<>(I_C_PO_OrderLine_Alloc.class, "C_PO_OrderLine_Alloc_ID", null);
	String COLUMNNAME_C_PO_OrderLine_Alloc_ID = "C_PO_OrderLine_Alloc_ID";

	/**
	 * Set C_PO_OrderLine_ID.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_PO_OrderLine_ID (int C_PO_OrderLine_ID);

	/**
	 * Get C_PO_OrderLine_ID.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_PO_OrderLine_ID();

	org.compiere.model.I_C_OrderLine getC_PO_OrderLine();

	void setC_PO_OrderLine(org.compiere.model.I_C_OrderLine C_PO_OrderLine);

	ModelColumn<I_C_PO_OrderLine_Alloc, org.compiere.model.I_C_OrderLine> COLUMN_C_PO_OrderLine_ID = new ModelColumn<>(I_C_PO_OrderLine_Alloc.class, "C_PO_OrderLine_ID", org.compiere.model.I_C_OrderLine.class);
	String COLUMNNAME_C_PO_OrderLine_ID = "C_PO_OrderLine_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_C_PO_OrderLine_Alloc, Object> COLUMN_Created = new ModelColumn<>(I_C_PO_OrderLine_Alloc.class, "Created", null);
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
	 * Set C_SO_OrderLine_ID.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_SO_OrderLine_ID (int C_SO_OrderLine_ID);

	/**
	 * Get C_SO_OrderLine_ID.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_SO_OrderLine_ID();

	org.compiere.model.I_C_OrderLine getC_SO_OrderLine();

	void setC_SO_OrderLine(org.compiere.model.I_C_OrderLine C_SO_OrderLine);

	ModelColumn<I_C_PO_OrderLine_Alloc, org.compiere.model.I_C_OrderLine> COLUMN_C_SO_OrderLine_ID = new ModelColumn<>(I_C_PO_OrderLine_Alloc.class, "C_SO_OrderLine_ID", org.compiere.model.I_C_OrderLine.class);
	String COLUMNNAME_C_SO_OrderLine_ID = "C_SO_OrderLine_ID";

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

	ModelColumn<I_C_PO_OrderLine_Alloc, Object> COLUMN_IsActive = new ModelColumn<>(I_C_PO_OrderLine_Alloc.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_C_PO_OrderLine_Alloc, Object> COLUMN_Updated = new ModelColumn<>(I_C_PO_OrderLine_Alloc.class, "Updated", null);
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
