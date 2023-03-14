package org.compiere.model;

import java.math.BigDecimal;
import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for M_InOut_Cost
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_M_InOut_Cost 
{

	String Table_Name = "M_InOut_Cost";

//	/** AD_Table_ID=542299 */
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
	 * Set Business Partner.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_BPartner_ID (int C_BPartner_ID);

	/**
	 * Get Business Partner.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_BPartner_ID();

	String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/**
	 * Set Cost Type.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Cost_Type_ID (int C_Cost_Type_ID);

	/**
	 * Get Cost Type.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Cost_Type_ID();

	org.compiere.model.I_C_Cost_Type getC_Cost_Type();

	void setC_Cost_Type(org.compiere.model.I_C_Cost_Type C_Cost_Type);

	ModelColumn<I_M_InOut_Cost, org.compiere.model.I_C_Cost_Type> COLUMN_C_Cost_Type_ID = new ModelColumn<>(I_M_InOut_Cost.class, "C_Cost_Type_ID", org.compiere.model.I_C_Cost_Type.class);
	String COLUMNNAME_C_Cost_Type_ID = "C_Cost_Type_ID";

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
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Order_Cost_Detail_ID (int C_Order_Cost_Detail_ID);

	/**
	 * Get Order Cost Detail.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Order_Cost_Detail_ID();

	org.compiere.model.I_C_Order_Cost_Detail getC_Order_Cost_Detail();

	void setC_Order_Cost_Detail(org.compiere.model.I_C_Order_Cost_Detail C_Order_Cost_Detail);

	ModelColumn<I_M_InOut_Cost, org.compiere.model.I_C_Order_Cost_Detail> COLUMN_C_Order_Cost_Detail_ID = new ModelColumn<>(I_M_InOut_Cost.class, "C_Order_Cost_Detail_ID", org.compiere.model.I_C_Order_Cost_Detail.class);
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

	ModelColumn<I_M_InOut_Cost, org.compiere.model.I_C_Order_Cost> COLUMN_C_Order_Cost_ID = new ModelColumn<>(I_M_InOut_Cost.class, "C_Order_Cost_ID", org.compiere.model.I_C_Order_Cost.class);
	String COLUMNNAME_C_Order_Cost_ID = "C_Order_Cost_ID";

	/**
	 * Set Sales order.
	 * Order
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Order_ID (int C_Order_ID);

	/**
	 * Get Sales order.
	 * Order
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Order_ID();

	org.compiere.model.I_C_Order getC_Order();

	void setC_Order(org.compiere.model.I_C_Order C_Order);

	ModelColumn<I_M_InOut_Cost, org.compiere.model.I_C_Order> COLUMN_C_Order_ID = new ModelColumn<>(I_M_InOut_Cost.class, "C_Order_ID", org.compiere.model.I_C_Order.class);
	String COLUMNNAME_C_Order_ID = "C_Order_ID";

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

	ModelColumn<I_M_InOut_Cost, org.compiere.model.I_C_OrderLine> COLUMN_C_OrderLine_ID = new ModelColumn<>(I_M_InOut_Cost.class, "C_OrderLine_ID", org.compiere.model.I_C_OrderLine.class);
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

	ModelColumn<I_M_InOut_Cost, Object> COLUMN_CostAmount = new ModelColumn<>(I_M_InOut_Cost.class, "CostAmount", null);
	String COLUMNNAME_CostAmount = "CostAmount";

	/**
	 * Set Cost Amount Invoiced.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setCostAmountInvoiced (BigDecimal CostAmountInvoiced);

	/**
	 * Get Cost Amount Invoiced.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getCostAmountInvoiced();

	ModelColumn<I_M_InOut_Cost, Object> COLUMN_CostAmountInvoiced = new ModelColumn<>(I_M_InOut_Cost.class, "CostAmountInvoiced", null);
	String COLUMNNAME_CostAmountInvoiced = "CostAmountInvoiced";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_M_InOut_Cost, Object> COLUMN_Created = new ModelColumn<>(I_M_InOut_Cost.class, "Created", null);
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

	ModelColumn<I_M_InOut_Cost, Object> COLUMN_IsActive = new ModelColumn<>(I_M_InOut_Cost.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Invoiced.
	 * Is this invoiced?
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsInvoiced (boolean IsInvoiced);

	/**
	 * Get Invoiced.
	 * Is this invoiced?
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isInvoiced();

	ModelColumn<I_M_InOut_Cost, Object> COLUMN_IsInvoiced = new ModelColumn<>(I_M_InOut_Cost.class, "IsInvoiced", null);
	String COLUMNNAME_IsInvoiced = "IsInvoiced";

	/**
	 * Set Sales Transaction.
	 * This is a Sales Transaction
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsSOTrx (boolean IsSOTrx);

	/**
	 * Get Sales Transaction.
	 * This is a Sales Transaction
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isSOTrx();

	ModelColumn<I_M_InOut_Cost, Object> COLUMN_IsSOTrx = new ModelColumn<>(I_M_InOut_Cost.class, "IsSOTrx", null);
	String COLUMNNAME_IsSOTrx = "IsSOTrx";

	/**
	 * Set Cost Element.
	 * Product Cost Element
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_CostElement_ID (int M_CostElement_ID);

	/**
	 * Get Cost Element.
	 * Product Cost Element
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_CostElement_ID();

	org.compiere.model.I_M_CostElement getM_CostElement();

	void setM_CostElement(org.compiere.model.I_M_CostElement M_CostElement);

	ModelColumn<I_M_InOut_Cost, org.compiere.model.I_M_CostElement> COLUMN_M_CostElement_ID = new ModelColumn<>(I_M_InOut_Cost.class, "M_CostElement_ID", org.compiere.model.I_M_CostElement.class);
	String COLUMNNAME_M_CostElement_ID = "M_CostElement_ID";

	/**
	 * Set Shipment/Receipt Costs.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_InOut_Cost_ID (int M_InOut_Cost_ID);

	/**
	 * Get Shipment/Receipt Costs.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_InOut_Cost_ID();

	ModelColumn<I_M_InOut_Cost, Object> COLUMN_M_InOut_Cost_ID = new ModelColumn<>(I_M_InOut_Cost.class, "M_InOut_Cost_ID", null);
	String COLUMNNAME_M_InOut_Cost_ID = "M_InOut_Cost_ID";

	/**
	 * Set Shipment/ Receipt.
	 * Material Shipment Document
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_InOut_ID (int M_InOut_ID);

	/**
	 * Get Shipment/ Receipt.
	 * Material Shipment Document
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_InOut_ID();

	org.compiere.model.I_M_InOut getM_InOut();

	void setM_InOut(org.compiere.model.I_M_InOut M_InOut);

	ModelColumn<I_M_InOut_Cost, org.compiere.model.I_M_InOut> COLUMN_M_InOut_ID = new ModelColumn<>(I_M_InOut_Cost.class, "M_InOut_ID", org.compiere.model.I_M_InOut.class);
	String COLUMNNAME_M_InOut_ID = "M_InOut_ID";

	/**
	 * Set Receipt Line.
	 * Line on Receipt document
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_InOutLine_ID (int M_InOutLine_ID);

	/**
	 * Get Receipt Line.
	 * Line on Receipt document
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_InOutLine_ID();

	org.compiere.model.I_M_InOutLine getM_InOutLine();

	void setM_InOutLine(org.compiere.model.I_M_InOutLine M_InOutLine);

	ModelColumn<I_M_InOut_Cost, org.compiere.model.I_M_InOutLine> COLUMN_M_InOutLine_ID = new ModelColumn<>(I_M_InOut_Cost.class, "M_InOutLine_ID", org.compiere.model.I_M_InOutLine.class);
	String COLUMNNAME_M_InOutLine_ID = "M_InOutLine_ID";

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

	ModelColumn<I_M_InOut_Cost, Object> COLUMN_Qty = new ModelColumn<>(I_M_InOut_Cost.class, "Qty", null);
	String COLUMNNAME_Qty = "Qty";

	/**
	 * Set Reversal ID.
	 * ID of document reversal
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setReversal_ID (int Reversal_ID);

	/**
	 * Get Reversal ID.
	 * ID of document reversal
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getReversal_ID();

	@Nullable org.compiere.model.I_M_InOut_Cost getReversal();

	void setReversal(@Nullable org.compiere.model.I_M_InOut_Cost Reversal);

	ModelColumn<I_M_InOut_Cost, org.compiere.model.I_M_InOut_Cost> COLUMN_Reversal_ID = new ModelColumn<>(I_M_InOut_Cost.class, "Reversal_ID", org.compiere.model.I_M_InOut_Cost.class);
	String COLUMNNAME_Reversal_ID = "Reversal_ID";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_M_InOut_Cost, Object> COLUMN_Updated = new ModelColumn<>(I_M_InOut_Cost.class, "Updated", null);
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
