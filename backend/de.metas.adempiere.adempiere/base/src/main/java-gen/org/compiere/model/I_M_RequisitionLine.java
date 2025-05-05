package org.compiere.model;

import java.math.BigDecimal;
import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for M_RequisitionLine
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_M_RequisitionLine 
{

	String Table_Name = "M_RequisitionLine";

//	/** AD_Table_ID=703 */
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
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_BPartner_Location_ID (int C_BPartner_Location_ID);

	/**
	 * Get Location.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_BPartner_Location_ID();

	String COLUMNNAME_C_BPartner_Location_ID = "C_BPartner_Location_ID";

	/**
	 * Set Costs.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Charge_ID (int C_Charge_ID);

	/**
	 * Get Costs.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Charge_ID();

	String COLUMNNAME_C_Charge_ID = "C_Charge_ID";

	/**
	 * Set Orderline.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_OrderLine_ID (int C_OrderLine_ID);

	/**
	 * Get Orderline.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_OrderLine_ID();

	@Nullable org.compiere.model.I_C_OrderLine getC_OrderLine();

	void setC_OrderLine(@Nullable org.compiere.model.I_C_OrderLine C_OrderLine);

	ModelColumn<I_M_RequisitionLine, org.compiere.model.I_C_OrderLine> COLUMN_C_OrderLine_ID = new ModelColumn<>(I_M_RequisitionLine.class, "C_OrderLine_ID", org.compiere.model.I_C_OrderLine.class);
	String COLUMNNAME_C_OrderLine_ID = "C_OrderLine_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_M_RequisitionLine, Object> COLUMN_Created = new ModelColumn<>(I_M_RequisitionLine.class, "Created", null);
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
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_UOM_ID (int C_UOM_ID);

	/**
	 * Get UOM.
	 * Unit of Measure
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_UOM_ID();

	String COLUMNNAME_C_UOM_ID = "C_UOM_ID";

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

	ModelColumn<I_M_RequisitionLine, Object> COLUMN_Description = new ModelColumn<>(I_M_RequisitionLine.class, "Description", null);
	String COLUMNNAME_Description = "Description";

	/**
	 * Set eMail.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setEMail (@Nullable java.lang.String EMail);

	/**
	 * Get eMail.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getEMail();

	ModelColumn<I_M_RequisitionLine, Object> COLUMN_EMail = new ModelColumn<>(I_M_RequisitionLine.class, "EMail", null);
	String COLUMNNAME_EMail = "EMail";

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

	ModelColumn<I_M_RequisitionLine, Object> COLUMN_IsActive = new ModelColumn<>(I_M_RequisitionLine.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Neuer Lieferant.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsNewSupplier (boolean IsNewSupplier);

	/**
	 * Get Neuer Lieferant.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isNewSupplier();

	ModelColumn<I_M_RequisitionLine, Object> COLUMN_IsNewSupplier = new ModelColumn<>(I_M_RequisitionLine.class, "IsNewSupplier", null);
	String COLUMNNAME_IsNewSupplier = "IsNewSupplier";

	/**
	 * Set New Supplier address.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsNewSupplierAddress (boolean IsNewSupplierAddress);

	/**
	 * Get New Supplier address.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isNewSupplierAddress();

	ModelColumn<I_M_RequisitionLine, Object> COLUMN_IsNewSupplierAddress = new ModelColumn<>(I_M_RequisitionLine.class, "IsNewSupplierAddress", null);
	String COLUMNNAME_IsNewSupplierAddress = "IsNewSupplierAddress";

	/**
	 * Set Vendor.
	 * Indicates if this Business Partner is a Vendor
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIsVendor (boolean IsVendor);

	/**
	 * Get Vendor.
	 * Indicates if this Business Partner is a Vendor
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isVendor();

	ModelColumn<I_M_RequisitionLine, Object> COLUMN_IsVendor = new ModelColumn<>(I_M_RequisitionLine.class, "IsVendor", null);
	String COLUMNNAME_IsVendor = "IsVendor";

	/**
	 * Set SeqNo..
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setLine (int Line);

	/**
	 * Get SeqNo..
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getLine();

	ModelColumn<I_M_RequisitionLine, Object> COLUMN_Line = new ModelColumn<>(I_M_RequisitionLine.class, "Line", null);
	String COLUMNNAME_Line = "Line";

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

	ModelColumn<I_M_RequisitionLine, Object> COLUMN_LineNetAmt = new ModelColumn<>(I_M_RequisitionLine.class, "LineNetAmt", null);
	String COLUMNNAME_LineNetAmt = "LineNetAmt";

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

	ModelColumn<I_M_RequisitionLine, org.compiere.model.I_M_AttributeSetInstance> COLUMN_M_AttributeSetInstance_ID = new ModelColumn<>(I_M_RequisitionLine.class, "M_AttributeSetInstance_ID", org.compiere.model.I_M_AttributeSetInstance.class);
	String COLUMNNAME_M_AttributeSetInstance_ID = "M_AttributeSetInstance_ID";

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
	 * Set Requisition.
	 * Material Requisition
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_Requisition_ID (int M_Requisition_ID);

	/**
	 * Get Requisition.
	 * Material Requisition
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_Requisition_ID();

	org.compiere.model.I_M_Requisition getM_Requisition();

	void setM_Requisition(org.compiere.model.I_M_Requisition M_Requisition);

	ModelColumn<I_M_RequisitionLine, org.compiere.model.I_M_Requisition> COLUMN_M_Requisition_ID = new ModelColumn<>(I_M_RequisitionLine.class, "M_Requisition_ID", org.compiere.model.I_M_Requisition.class);
	String COLUMNNAME_M_Requisition_ID = "M_Requisition_ID";

	/**
	 * Set Requisition Line.
	 * Material Requisition Line
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_RequisitionLine_ID (int M_RequisitionLine_ID);

	/**
	 * Get Requisition Line.
	 * Material Requisition Line
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_RequisitionLine_ID();

	ModelColumn<I_M_RequisitionLine, Object> COLUMN_M_RequisitionLine_ID = new ModelColumn<>(I_M_RequisitionLine.class, "M_RequisitionLine_ID", null);
	String COLUMNNAME_M_RequisitionLine_ID = "M_RequisitionLine_ID";

	/**
	 * Set New Supplier Address Details.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setNewSupplierAddress (@Nullable java.lang.String NewSupplierAddress);

	/**
	 * Get New Supplier Address Details.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getNewSupplierAddress();

	ModelColumn<I_M_RequisitionLine, Object> COLUMN_NewSupplierAddress = new ModelColumn<>(I_M_RequisitionLine.class, "NewSupplierAddress", null);
	String COLUMNNAME_NewSupplierAddress = "NewSupplierAddress";

	/**
	 * Set New Supplier Name.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setNewSupplierName (@Nullable java.lang.String NewSupplierName);

	/**
	 * Get New Supplier Name.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getNewSupplierName();

	ModelColumn<I_M_RequisitionLine, Object> COLUMN_NewSupplierName = new ModelColumn<>(I_M_RequisitionLine.class, "NewSupplierName", null);
	String COLUMNNAME_NewSupplierName = "NewSupplierName";

	/**
	 * Set Kanal der Bestellung.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setOrderChannel (@Nullable java.lang.String OrderChannel);

	/**
	 * Get Kanal der Bestellung.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getOrderChannel();

	ModelColumn<I_M_RequisitionLine, Object> COLUMN_OrderChannel = new ModelColumn<>(I_M_RequisitionLine.class, "OrderChannel", null);
	String COLUMNNAME_OrderChannel = "OrderChannel";

	/**
	 * Set Payment Rule.
	 * How you pay the invoice
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPaymentRule (@Nullable java.lang.String PaymentRule);

	/**
	 * Get Payment Rule.
	 * How you pay the invoice
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getPaymentRule();

	ModelColumn<I_M_RequisitionLine, Object> COLUMN_PaymentRule = new ModelColumn<>(I_M_RequisitionLine.class, "PaymentRule", null);
	String COLUMNNAME_PaymentRule = "PaymentRule";

	/**
	 * Set Price Actual.
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPriceActual (BigDecimal PriceActual);

	/**
	 * Get Price Actual.
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getPriceActual();

	ModelColumn<I_M_RequisitionLine, Object> COLUMN_PriceActual = new ModelColumn<>(I_M_RequisitionLine.class, "PriceActual", null);
	String COLUMNNAME_PriceActual = "PriceActual";

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

	ModelColumn<I_M_RequisitionLine, Object> COLUMN_Qty = new ModelColumn<>(I_M_RequisitionLine.class, "Qty", null);
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

	ModelColumn<I_M_RequisitionLine, Object> COLUMN_Updated = new ModelColumn<>(I_M_RequisitionLine.class, "Updated", null);
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
