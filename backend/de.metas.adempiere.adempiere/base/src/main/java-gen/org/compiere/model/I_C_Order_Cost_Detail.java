package org.compiere.model;

import java.math.BigDecimal;
import org.adempiere.model.ModelColumn;

/** Generated Interface for C_Order_Cost_Detail
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_C_Order_Cost_Detail 
{

	String Table_Name = "C_Order_Cost_Detail";

//	/** AD_Table_ID=542297 */
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
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Currency_ID (int C_Currency_ID);

	/**
	 * Get Currency.
	 * The Currency for this record
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Currency_ID();

	String COLUMNNAME_C_Currency_ID = "C_Currency_ID";

	/**
	 * Set Order Cost Detail.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Order_Cost_Detail_ID (int C_Order_Cost_Detail_ID);

	/**
	 * Get Order Cost Detail.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Order_Cost_Detail_ID();

	ModelColumn<I_C_Order_Cost_Detail, Object> COLUMN_C_Order_Cost_Detail_ID = new ModelColumn<>(I_C_Order_Cost_Detail.class, "C_Order_Cost_Detail_ID", null);
	String COLUMNNAME_C_Order_Cost_Detail_ID = "C_Order_Cost_Detail_ID";

	/**
	 * Set Order Cost.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Order_Cost_ID (int C_Order_Cost_ID);

	/**
	 * Get Order Cost.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Order_Cost_ID();

	org.compiere.model.I_C_Order_Cost getC_Order_Cost();

	void setC_Order_Cost(org.compiere.model.I_C_Order_Cost C_Order_Cost);

	ModelColumn<I_C_Order_Cost_Detail, org.compiere.model.I_C_Order_Cost> COLUMN_C_Order_Cost_ID = new ModelColumn<>(I_C_Order_Cost_Detail.class, "C_Order_Cost_ID", org.compiere.model.I_C_Order_Cost.class);
	String COLUMNNAME_C_Order_Cost_ID = "C_Order_Cost_ID";

	/**
	 * Set Orderline.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_OrderLine_ID (int C_OrderLine_ID);

	/**
	 * Get Orderline.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_OrderLine_ID();

	org.compiere.model.I_C_OrderLine getC_OrderLine();

	void setC_OrderLine(org.compiere.model.I_C_OrderLine C_OrderLine);

	ModelColumn<I_C_Order_Cost_Detail, org.compiere.model.I_C_OrderLine> COLUMN_C_OrderLine_ID = new ModelColumn<>(I_C_Order_Cost_Detail.class, "C_OrderLine_ID", org.compiere.model.I_C_OrderLine.class);
	String COLUMNNAME_C_OrderLine_ID = "C_OrderLine_ID";

	/**
	 * Set Cost Amount.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setCostAmount (BigDecimal CostAmount);

	/**
	 * Get Cost Amount.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getCostAmount();

	ModelColumn<I_C_Order_Cost_Detail, Object> COLUMN_CostAmount = new ModelColumn<>(I_C_Order_Cost_Detail.class, "CostAmount", null);
	String COLUMNNAME_CostAmount = "CostAmount";

	/**
	 * Set Cost Amount Received.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setCostAmountReceived (BigDecimal CostAmountReceived);

	/**
	 * Get Cost Amount Received.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getCostAmountReceived();

	ModelColumn<I_C_Order_Cost_Detail, Object> COLUMN_CostAmountReceived = new ModelColumn<>(I_C_Order_Cost_Detail.class, "CostAmountReceived", null);
	String COLUMNNAME_CostAmountReceived = "CostAmountReceived";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_C_Order_Cost_Detail, Object> COLUMN_Created = new ModelColumn<>(I_C_Order_Cost_Detail.class, "Created", null);
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

	ModelColumn<I_C_Order_Cost_Detail, Object> COLUMN_IsActive = new ModelColumn<>(I_C_Order_Cost_Detail.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Line Net Amount.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setLineNetAmt (BigDecimal LineNetAmt);

	/**
	 * Get Line Net Amount.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getLineNetAmt();

	ModelColumn<I_C_Order_Cost_Detail, Object> COLUMN_LineNetAmt = new ModelColumn<>(I_C_Order_Cost_Detail.class, "LineNetAmt", null);
	String COLUMNNAME_LineNetAmt = "LineNetAmt";

	/**
	 * Set Product.
	 * Product, Service, Item
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_Product_ID (int M_Product_ID);

	/**
	 * Get Product.
	 * Product, Service, Item
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_Product_ID();

	String COLUMNNAME_M_Product_ID = "M_Product_ID";

	/**
	 * Set Qty Ordered.
	 * Qty Ordered
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setQtyOrdered (BigDecimal QtyOrdered);

	/**
	 * Get Qty Ordered.
	 * Qty Ordered
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyOrdered();

	ModelColumn<I_C_Order_Cost_Detail, Object> COLUMN_QtyOrdered = new ModelColumn<>(I_C_Order_Cost_Detail.class, "QtyOrdered", null);
	String COLUMNNAME_QtyOrdered = "QtyOrdered";

	/**
	 * Set Empfangene Menge.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setQtyReceived (BigDecimal QtyReceived);

	/**
	 * Get Empfangene Menge.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyReceived();

	ModelColumn<I_C_Order_Cost_Detail, Object> COLUMN_QtyReceived = new ModelColumn<>(I_C_Order_Cost_Detail.class, "QtyReceived", null);
	String COLUMNNAME_QtyReceived = "QtyReceived";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_C_Order_Cost_Detail, Object> COLUMN_Updated = new ModelColumn<>(I_C_Order_Cost_Detail.class, "Updated", null);
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
