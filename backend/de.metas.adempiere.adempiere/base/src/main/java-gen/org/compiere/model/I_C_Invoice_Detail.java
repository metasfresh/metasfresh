package org.compiere.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;
import java.math.BigDecimal;

/** Generated Interface for C_Invoice_Detail
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_C_Invoice_Detail 
{

	String Table_Name = "C_Invoice_Detail";

//	/** AD_Table_ID=540614 */
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
	 * Set Invoice candidate.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Invoice_Candidate_ID (int C_Invoice_Candidate_ID);

	/**
	 * Get Invoice candidate.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Invoice_Candidate_ID();

	ModelColumn<I_C_Invoice_Detail, Object> COLUMN_C_Invoice_Candidate_ID = new ModelColumn<>(I_C_Invoice_Detail.class, "C_Invoice_Candidate_ID", null);
	String COLUMNNAME_C_Invoice_Candidate_ID = "C_Invoice_Candidate_ID";

	/**
	 * Set Invoice detailed information.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Invoice_Detail_ID (int C_Invoice_Detail_ID);

	/**
	 * Get Invoice detailed information.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Invoice_Detail_ID();

	ModelColumn<I_C_Invoice_Detail, Object> COLUMN_C_Invoice_Detail_ID = new ModelColumn<>(I_C_Invoice_Detail.class, "C_Invoice_Detail_ID", null);
	String COLUMNNAME_C_Invoice_Detail_ID = "C_Invoice_Detail_ID";

	/**
	 * Set Invoice.
	 * Invoice Identifier
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Invoice_ID (int C_Invoice_ID);

	/**
	 * Get Invoice.
	 * Invoice Identifier
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Invoice_ID();

	@Nullable org.compiere.model.I_C_Invoice getC_Invoice();

	void setC_Invoice(@Nullable org.compiere.model.I_C_Invoice C_Invoice);

	ModelColumn<I_C_Invoice_Detail, org.compiere.model.I_C_Invoice> COLUMN_C_Invoice_ID = new ModelColumn<>(I_C_Invoice_Detail.class, "C_Invoice_ID", org.compiere.model.I_C_Invoice.class);
	String COLUMNNAME_C_Invoice_ID = "C_Invoice_ID";

	/**
	 * Set Invoice Line.
	 * Rechnungszeile
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_InvoiceLine_ID (int C_InvoiceLine_ID);

	/**
	 * Get Invoice Line.
	 * Rechnungszeile
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_InvoiceLine_ID();

	@Nullable org.compiere.model.I_C_InvoiceLine getC_InvoiceLine();

	void setC_InvoiceLine(@Nullable org.compiere.model.I_C_InvoiceLine C_InvoiceLine);

	ModelColumn<I_C_Invoice_Detail, org.compiere.model.I_C_InvoiceLine> COLUMN_C_InvoiceLine_ID = new ModelColumn<>(I_C_Invoice_Detail.class, "C_InvoiceLine_ID", org.compiere.model.I_C_InvoiceLine.class);
	String COLUMNNAME_C_InvoiceLine_ID = "C_InvoiceLine_ID";

	/**
	 * Set Period.
	 * Period of the Calendar
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Period_ID (int C_Period_ID);

	/**
	 * Get Period.
	 * Period of the Calendar
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Period_ID();

	@Nullable org.compiere.model.I_C_Period getC_Period();

	void setC_Period(@Nullable org.compiere.model.I_C_Period C_Period);

	ModelColumn<I_C_Invoice_Detail, org.compiere.model.I_C_Period> COLUMN_C_Period_ID = new ModelColumn<>(I_C_Invoice_Detail.class, "C_Period_ID", org.compiere.model.I_C_Period.class);
	String COLUMNNAME_C_Period_ID = "C_Period_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_C_Invoice_Detail, Object> COLUMN_Created = new ModelColumn<>(I_C_Invoice_Detail.class, "Created", null);
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
	 * Set Date.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDate (@Nullable java.sql.Timestamp Date);

	/**
	 * Get Date.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getDate();

	ModelColumn<I_C_Invoice_Detail, Object> COLUMN_Date = new ModelColumn<>(I_C_Invoice_Detail.class, "Date", null);
	String COLUMNNAME_Date = "Date";

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

	ModelColumn<I_C_Invoice_Detail, Object> COLUMN_Description = new ModelColumn<>(I_C_Invoice_Detail.class, "Description", null);
	String COLUMNNAME_Description = "Description";

	/**
	 * Set Discount %.
	 * Discount in percent
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDiscount (@Nullable BigDecimal Discount);

	/**
	 * Get Discount %.
	 * Discount in percent
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getDiscount();

	ModelColumn<I_C_Invoice_Detail, Object> COLUMN_Discount = new ModelColumn<>(I_C_Invoice_Detail.class, "Discount", null);
	String COLUMNNAME_Discount = "Discount";

	/**
	 * Set Ship Business Partner.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDropShip_BPartner_ID (int DropShip_BPartner_ID);

	/**
	 * Get Ship Business Partner.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getDropShip_BPartner_ID();

	String COLUMNNAME_DropShip_BPartner_ID = "DropShip_BPartner_ID";

	/**
	 * Set Ship Location.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDropShip_Location_ID (int DropShip_Location_ID);

	/**
	 * Get Ship Location.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getDropShip_Location_ID();

	String COLUMNNAME_DropShip_Location_ID = "DropShip_Location_ID";

	/**
	 * Set Ship Contact.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDropShip_User_ID (int DropShip_User_ID);

	/**
	 * Get Ship Contact.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getDropShip_User_ID();

	String COLUMNNAME_DropShip_User_ID = "DropShip_User_ID";

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

	ModelColumn<I_C_Invoice_Detail, Object> COLUMN_IsActive = new ModelColumn<>(I_C_Invoice_Detail.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Print Details override.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsDetailOverridesLine (boolean IsDetailOverridesLine);

	/**
	 * Get Print Details override.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isDetailOverridesLine();

	ModelColumn<I_C_Invoice_Detail, Object> COLUMN_IsDetailOverridesLine = new ModelColumn<>(I_C_Invoice_Detail.class, "IsDetailOverridesLine", null);
	String COLUMNNAME_IsDetailOverridesLine = "IsDetailOverridesLine";

	/**
	 * Set Print before.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsPrintBefore (boolean IsPrintBefore);

	/**
	 * Get Print before.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isPrintBefore();

	ModelColumn<I_C_Invoice_Detail, Object> COLUMN_IsPrintBefore = new ModelColumn<>(I_C_Invoice_Detail.class, "IsPrintBefore", null);
	String COLUMNNAME_IsPrintBefore = "IsPrintBefore";

	/**
	 * Set Printed.
	 * Indicates if this document / line is printed
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsPrinted (boolean IsPrinted);

	/**
	 * Get Printed.
	 * Indicates if this document / line is printed
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isPrinted();

	ModelColumn<I_C_Invoice_Detail, Object> COLUMN_IsPrinted = new ModelColumn<>(I_C_Invoice_Detail.class, "IsPrinted", null);
	String COLUMNNAME_IsPrinted = "IsPrinted";

	/**
	 * Set Label.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setLabel (@Nullable java.lang.String Label);

	/**
	 * Get Label.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getLabel();

	ModelColumn<I_C_Invoice_Detail, Object> COLUMN_Label = new ModelColumn<>(I_C_Invoice_Detail.class, "Label", null);
	String COLUMNNAME_Label = "Label";

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

	ModelColumn<I_C_Invoice_Detail, org.compiere.model.I_M_AttributeSetInstance> COLUMN_M_AttributeSetInstance_ID = new ModelColumn<>(I_C_Invoice_Detail.class, "M_AttributeSetInstance_ID", org.compiere.model.I_M_AttributeSetInstance.class);
	String COLUMNNAME_M_AttributeSetInstance_ID = "M_AttributeSetInstance_ID";

	/**
	 * Set Product.
	 * Product, Service, Item
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_Product_ID (int M_Product_ID);

	/**
	 * Get Product.
	 * Product, Service, Item
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_Product_ID();

	String COLUMNNAME_M_Product_ID = "M_Product_ID";

	/**
	 * Set Note.
	 * Optional additional user defined information
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setNote (@Nullable java.lang.String Note);

	/**
	 * Get Note.
	 * Optional additional user defined information
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getNote();

	ModelColumn<I_C_Invoice_Detail, Object> COLUMN_Note = new ModelColumn<>(I_C_Invoice_Detail.class, "Note", null);
	String COLUMNNAME_Note = "Note";

	/**
	 * Set Percentage.
	 * Percent of the entire amount
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPercentage (@Nullable BigDecimal Percentage);

	/**
	 * Get Percentage.
	 * Percent of the entire amount
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getPercentage();

	ModelColumn<I_C_Invoice_Detail, Object> COLUMN_Percentage = new ModelColumn<>(I_C_Invoice_Detail.class, "Percentage", null);
	String COLUMNNAME_Percentage = "Percentage";

	/**
	 * Set Price Actual.
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPriceActual (@Nullable BigDecimal PriceActual);

	/**
	 * Get Price Actual.
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getPriceActual();

	ModelColumn<I_C_Invoice_Detail, Object> COLUMN_PriceActual = new ModelColumn<>(I_C_Invoice_Detail.class, "PriceActual", null);
	String COLUMNNAME_PriceActual = "PriceActual";

	/**
	 * Set Price imp..
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPriceEntered (@Nullable BigDecimal PriceEntered);

	/**
	 * Get Price imp..
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getPriceEntered();

	ModelColumn<I_C_Invoice_Detail, Object> COLUMN_PriceEntered = new ModelColumn<>(I_C_Invoice_Detail.class, "PriceEntered", null);
	String COLUMNNAME_PriceEntered = "PriceEntered";

	/**
	 * Set Price Unit.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPrice_UOM_ID (int Price_UOM_ID);

	/**
	 * Get Price Unit.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getPrice_UOM_ID();

	String COLUMNNAME_Price_UOM_ID = "Price_UOM_ID";

	/**
	 * Set Quantity.
	 * Quantity
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQty (@Nullable BigDecimal Qty);

	/**
	 * Get Quantity.
	 * Quantity
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQty();

	ModelColumn<I_C_Invoice_Detail, Object> COLUMN_Qty = new ModelColumn<>(I_C_Invoice_Detail.class, "Qty", null);
	String COLUMNNAME_Qty = "Qty";

	/**
	 * Set Quantity in price unit.
	 * Bestellte Menge in Preiseinheit
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQtyEnteredInPriceUOM (@Nullable BigDecimal QtyEnteredInPriceUOM);

	/**
	 * Get Quantity in price unit.
	 * Bestellte Menge in Preiseinheit
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyEnteredInPriceUOM();

	ModelColumn<I_C_Invoice_Detail, Object> COLUMN_QtyEnteredInPriceUOM = new ModelColumn<>(I_C_Invoice_Detail.class, "QtyEnteredInPriceUOM", null);
	String COLUMNNAME_QtyEnteredInPriceUOM = "QtyEnteredInPriceUOM";

	/**
	 * Set SeqNo.
	 * Method of ordering records;
 lowest number comes first
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setSeqNo (int SeqNo);

	/**
	 * Get SeqNo.
	 * Method of ordering records;
 lowest number comes first
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getSeqNo();

	ModelColumn<I_C_Invoice_Detail, Object> COLUMN_SeqNo = new ModelColumn<>(I_C_Invoice_Detail.class, "SeqNo", null);
	String COLUMNNAME_SeqNo = "SeqNo";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_C_Invoice_Detail, Object> COLUMN_Updated = new ModelColumn<>(I_C_Invoice_Detail.class, "Updated", null);
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
