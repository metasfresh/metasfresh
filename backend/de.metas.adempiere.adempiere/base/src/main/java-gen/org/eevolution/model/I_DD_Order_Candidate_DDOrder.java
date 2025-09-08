package org.eevolution.model;

import java.math.BigDecimal;
import org.adempiere.model.ModelColumn;

/** Generated Interface for DD_Order_Candidate_DDOrder
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_DD_Order_Candidate_DDOrder 
{

	String Table_Name = "DD_Order_Candidate_DDOrder";

//	/** AD_Table_ID=542429 */
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

	ModelColumn<I_DD_Order_Candidate_DDOrder, Object> COLUMN_Created = new ModelColumn<>(I_DD_Order_Candidate_DDOrder.class, "Created", null);
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
	 * Set UOM.
	 * Unit of Measure
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_UOM_ID (int C_UOM_ID);

	/**
	 * Get UOM.
	 * Unit of Measure
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_UOM_ID();

	String COLUMNNAME_C_UOM_ID = "C_UOM_ID";

	/**
	 * Set DD_Order_Candidate - DD_Order.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDD_Order_Candidate_DDOrder_ID (int DD_Order_Candidate_DDOrder_ID);

	/**
	 * Get DD_Order_Candidate - DD_Order.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getDD_Order_Candidate_DDOrder_ID();

	ModelColumn<I_DD_Order_Candidate_DDOrder, Object> COLUMN_DD_Order_Candidate_DDOrder_ID = new ModelColumn<>(I_DD_Order_Candidate_DDOrder.class, "DD_Order_Candidate_DDOrder_ID", null);
	String COLUMNNAME_DD_Order_Candidate_DDOrder_ID = "DD_Order_Candidate_DDOrder_ID";

	/**
	 * Set Distribution Candidate.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDD_Order_Candidate_ID (int DD_Order_Candidate_ID);

	/**
	 * Get Distribution Candidate.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getDD_Order_Candidate_ID();

	org.eevolution.model.I_DD_Order_Candidate getDD_Order_Candidate();

	void setDD_Order_Candidate(org.eevolution.model.I_DD_Order_Candidate DD_Order_Candidate);

	ModelColumn<I_DD_Order_Candidate_DDOrder, org.eevolution.model.I_DD_Order_Candidate> COLUMN_DD_Order_Candidate_ID = new ModelColumn<>(I_DD_Order_Candidate_DDOrder.class, "DD_Order_Candidate_ID", org.eevolution.model.I_DD_Order_Candidate.class);
	String COLUMNNAME_DD_Order_Candidate_ID = "DD_Order_Candidate_ID";

	/**
	 * Set Distribution Order.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDD_Order_ID (int DD_Order_ID);

	/**
	 * Get Distribution Order.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getDD_Order_ID();

	org.eevolution.model.I_DD_Order getDD_Order();

	void setDD_Order(org.eevolution.model.I_DD_Order DD_Order);

	ModelColumn<I_DD_Order_Candidate_DDOrder, org.eevolution.model.I_DD_Order> COLUMN_DD_Order_ID = new ModelColumn<>(I_DD_Order_Candidate_DDOrder.class, "DD_Order_ID", org.eevolution.model.I_DD_Order.class);
	String COLUMNNAME_DD_Order_ID = "DD_Order_ID";

	/**
	 * Set Distribution Order Line.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDD_OrderLine_ID (int DD_OrderLine_ID);

	/**
	 * Get Distribution Order Line.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getDD_OrderLine_ID();

	org.eevolution.model.I_DD_OrderLine getDD_OrderLine();

	void setDD_OrderLine(org.eevolution.model.I_DD_OrderLine DD_OrderLine);

	ModelColumn<I_DD_Order_Candidate_DDOrder, org.eevolution.model.I_DD_OrderLine> COLUMN_DD_OrderLine_ID = new ModelColumn<>(I_DD_Order_Candidate_DDOrder.class, "DD_OrderLine_ID", org.eevolution.model.I_DD_OrderLine.class);
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

	ModelColumn<I_DD_Order_Candidate_DDOrder, Object> COLUMN_IsActive = new ModelColumn<>(I_DD_Order_Candidate_DDOrder.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Quantity.
	 * Quantity
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setQty (BigDecimal Qty);

	/**
	 * Get Quantity.
	 * Quantity
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getQty();

	ModelColumn<I_DD_Order_Candidate_DDOrder, Object> COLUMN_Qty = new ModelColumn<>(I_DD_Order_Candidate_DDOrder.class, "Qty", null);
	String COLUMNNAME_Qty = "Qty";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_DD_Order_Candidate_DDOrder, Object> COLUMN_Updated = new ModelColumn<>(I_DD_Order_Candidate_DDOrder.class, "Updated", null);
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
