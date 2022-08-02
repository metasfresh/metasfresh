package de.metas.contracts.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;
import java.math.BigDecimal;

/** Generated Interface for C_CallOrderSummary
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_C_CallOrderSummary 
{

	String Table_Name = "C_CallOrderSummary";

//	/** AD_Table_ID=541984 */
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
	 * Set Call Order Summary.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_CallOrderSummary_ID (int C_CallOrderSummary_ID);

	/**
	 * Get Call Order Summary.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_CallOrderSummary_ID();

	ModelColumn<I_C_CallOrderSummary, Object> COLUMN_C_CallOrderSummary_ID = new ModelColumn<>(I_C_CallOrderSummary.class, "C_CallOrderSummary_ID", null);
	String COLUMNNAME_C_CallOrderSummary_ID = "C_CallOrderSummary_ID";

	/**
	 * Set Flatrate Term.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Flatrate_Term_ID (int C_Flatrate_Term_ID);

	/**
	 * Get Flatrate Term.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Flatrate_Term_ID();

	de.metas.contracts.model.I_C_Flatrate_Term getC_Flatrate_Term();

	void setC_Flatrate_Term(de.metas.contracts.model.I_C_Flatrate_Term C_Flatrate_Term);

	ModelColumn<I_C_CallOrderSummary, de.metas.contracts.model.I_C_Flatrate_Term> COLUMN_C_Flatrate_Term_ID = new ModelColumn<>(I_C_CallOrderSummary.class, "C_Flatrate_Term_ID", de.metas.contracts.model.I_C_Flatrate_Term.class);
	String COLUMNNAME_C_Flatrate_Term_ID = "C_Flatrate_Term_ID";

	/**
	 * Set Sales order.
	 * Order
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Order_ID (int C_Order_ID);

	/**
	 * Get Sales order.
	 * Order
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Order_ID();

	org.compiere.model.I_C_Order getC_Order();

	void setC_Order(org.compiere.model.I_C_Order C_Order);

	ModelColumn<I_C_CallOrderSummary, org.compiere.model.I_C_Order> COLUMN_C_Order_ID = new ModelColumn<>(I_C_CallOrderSummary.class, "C_Order_ID", org.compiere.model.I_C_Order.class);
	String COLUMNNAME_C_Order_ID = "C_Order_ID";

	/**
	 * Set Orderline.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_OrderLine_ID (int C_OrderLine_ID);

	/**
	 * Get Orderline.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_OrderLine_ID();

	org.compiere.model.I_C_OrderLine getC_OrderLine();

	void setC_OrderLine(org.compiere.model.I_C_OrderLine C_OrderLine);

	ModelColumn<I_C_CallOrderSummary, org.compiere.model.I_C_OrderLine> COLUMN_C_OrderLine_ID = new ModelColumn<>(I_C_CallOrderSummary.class, "C_OrderLine_ID", org.compiere.model.I_C_OrderLine.class);
	String COLUMNNAME_C_OrderLine_ID = "C_OrderLine_ID";

	/**
	 * Set UOM.
	 * Unit of Measure
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_UOM_ID (int C_UOM_ID);

	/**
	 * Get UOM.
	 * Unit of Measure
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_UOM_ID();

	String COLUMNNAME_C_UOM_ID = "C_UOM_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_C_CallOrderSummary, Object> COLUMN_Created = new ModelColumn<>(I_C_CallOrderSummary.class, "Created", null);
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

	ModelColumn<I_C_CallOrderSummary, Object> COLUMN_IsActive = new ModelColumn<>(I_C_CallOrderSummary.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

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

	ModelColumn<I_C_CallOrderSummary, Object> COLUMN_IsSOTrx = new ModelColumn<>(I_C_CallOrderSummary.class, "IsSOTrx", null);
	String COLUMNNAME_IsSOTrx = "IsSOTrx";

	/**
	 * Set Attributes.
	 * Attribute Instances for Products
	 *
	 * <br>Type: PAttribute
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_AttributeSetInstance_ID (int M_AttributeSetInstance_ID);

	/**
	 * Get Attributes.
	 * Attribute Instances for Products
	 *
	 * <br>Type: PAttribute
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_AttributeSetInstance_ID();

	@Nullable org.compiere.model.I_M_AttributeSetInstance getM_AttributeSetInstance();

	void setM_AttributeSetInstance(@Nullable org.compiere.model.I_M_AttributeSetInstance M_AttributeSetInstance);

	ModelColumn<I_C_CallOrderSummary, org.compiere.model.I_M_AttributeSetInstance> COLUMN_M_AttributeSetInstance_ID = new ModelColumn<>(I_C_CallOrderSummary.class, "M_AttributeSetInstance_ID", org.compiere.model.I_M_AttributeSetInstance.class);
	String COLUMNNAME_M_AttributeSetInstance_ID = "M_AttributeSetInstance_ID";

	/**
	 * Set Product.
	 * Product, Service, Item
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_Product_ID (int M_Product_ID);

	/**
	 * Get Product.
	 * Product, Service, Item
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_Product_ID();

	String COLUMNNAME_M_Product_ID = "M_Product_ID";

	/**
	 * Set Order Reference.
	 * Transaction Reference Number (Sales Order, Purchase Order) of your Business Partner
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPOReference (@Nullable java.lang.String POReference);

	/**
	 * Get Order Reference.
	 * Transaction Reference Number (Sales Order, Purchase Order) of your Business Partner
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getPOReference();

	ModelColumn<I_C_CallOrderSummary, Object> COLUMN_POReference = new ModelColumn<>(I_C_CallOrderSummary.class, "POReference", null);
	String COLUMNNAME_POReference = "POReference";

	/**
	 * Set Delivered quantity.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQtyDeliveredInUOM (@Nullable BigDecimal QtyDeliveredInUOM);

	/**
	 * Get Delivered quantity.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyDeliveredInUOM();

	ModelColumn<I_C_CallOrderSummary, Object> COLUMN_QtyDeliveredInUOM = new ModelColumn<>(I_C_CallOrderSummary.class, "QtyDeliveredInUOM", null);
	String COLUMNNAME_QtyDeliveredInUOM = "QtyDeliveredInUOM";

	/**
	 * Set Qty.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setQtyEntered (BigDecimal QtyEntered);

	/**
	 * Get Qty.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyEntered();

	ModelColumn<I_C_CallOrderSummary, Object> COLUMN_QtyEntered = new ModelColumn<>(I_C_CallOrderSummary.class, "QtyEntered", null);
	String COLUMNNAME_QtyEntered = "QtyEntered";

	/**
	 * Set Invoiced.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQtyInvoicedInUOM (@Nullable BigDecimal QtyInvoicedInUOM);

	/**
	 * Get Invoiced.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyInvoicedInUOM();

	ModelColumn<I_C_CallOrderSummary, Object> COLUMN_QtyInvoicedInUOM = new ModelColumn<>(I_C_CallOrderSummary.class, "QtyInvoicedInUOM", null);
	String COLUMNNAME_QtyInvoicedInUOM = "QtyInvoicedInUOM";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_C_CallOrderSummary, Object> COLUMN_Updated = new ModelColumn<>(I_C_CallOrderSummary.class, "Updated", null);
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
