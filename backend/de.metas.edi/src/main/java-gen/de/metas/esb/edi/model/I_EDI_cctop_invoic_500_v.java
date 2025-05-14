package de.metas.esb.edi.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;
import java.math.BigDecimal;

/** Generated Interface for EDI_cctop_invoic_500_v
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_EDI_cctop_invoic_500_v 
{

	String Table_Name = "EDI_cctop_invoic_500_v";

//	/** AD_Table_ID=540463 */
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
	 * Set Buyer_EAN_CU.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setBuyer_EAN_CU (@Nullable java.lang.String Buyer_EAN_CU);

	/**
	 * Get Buyer_EAN_CU.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getBuyer_EAN_CU();

	ModelColumn<I_EDI_cctop_invoic_500_v, Object> COLUMN_Buyer_EAN_CU = new ModelColumn<>(I_EDI_cctop_invoic_500_v.class, "Buyer_EAN_CU", null);
	String COLUMNNAME_Buyer_EAN_CU = "Buyer_EAN_CU";

	/**
	 * Set Buyer_GTIN_CU.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setBuyer_GTIN_CU (@Nullable java.lang.String Buyer_GTIN_CU);

	/**
	 * Get Buyer_GTIN_CU.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getBuyer_GTIN_CU();

	ModelColumn<I_EDI_cctop_invoic_500_v, Object> COLUMN_Buyer_GTIN_CU = new ModelColumn<>(I_EDI_cctop_invoic_500_v.class, "Buyer_GTIN_CU", null);
	String COLUMNNAME_Buyer_GTIN_CU = "Buyer_GTIN_CU";

	/**
	 * Set Buyer_GTIN_TU.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setBuyer_GTIN_TU (@Nullable java.lang.String Buyer_GTIN_TU);

	/**
	 * Get Buyer_GTIN_TU.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getBuyer_GTIN_TU();

	ModelColumn<I_EDI_cctop_invoic_500_v, Object> COLUMN_Buyer_GTIN_TU = new ModelColumn<>(I_EDI_cctop_invoic_500_v.class, "Buyer_GTIN_TU", null);
	String COLUMNNAME_Buyer_GTIN_TU = "Buyer_GTIN_TU";

	/**
	 * Set Invoice.
	 * Invoice Identifier
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Invoice_ID (int C_Invoice_ID);

	/**
	 * Get Invoice.
	 * Invoice Identifier
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Invoice_ID();

	@Nullable org.compiere.model.I_C_Invoice getC_Invoice();

	void setC_Invoice(@Nullable org.compiere.model.I_C_Invoice C_Invoice);

	ModelColumn<I_EDI_cctop_invoic_500_v, org.compiere.model.I_C_Invoice> COLUMN_C_Invoice_ID = new ModelColumn<>(I_EDI_cctop_invoic_500_v.class, "C_Invoice_ID", org.compiere.model.I_C_Invoice.class);
	String COLUMNNAME_C_Invoice_ID = "C_Invoice_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_EDI_cctop_invoic_500_v, Object> COLUMN_Created = new ModelColumn<>(I_EDI_cctop_invoic_500_v.class, "Created", null);
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
	 * Set BPartner UOM.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_UOM_BPartner_ID (int C_UOM_BPartner_ID);

	/**
	 * Get BPartner UOM.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_UOM_BPartner_ID();

	String COLUMNNAME_C_UOM_BPartner_ID = "C_UOM_BPartner_ID";

	/**
	 * Set Customer Product No.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCustomerProductNo (@Nullable java.lang.String CustomerProductNo);

	/**
	 * Get Customer Product No.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getCustomerProductNo();

	ModelColumn<I_EDI_cctop_invoic_500_v, Object> COLUMN_CustomerProductNo = new ModelColumn<>(I_EDI_cctop_invoic_500_v.class, "CustomerProductNo", null);
	String COLUMNNAME_CustomerProductNo = "CustomerProductNo";

	/**
	 * Set Discount %.
	 * Discount in percent
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDiscount (@Nullable BigDecimal Discount);

	/**
	 * Get Discount %.
	 * Discount in percent
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getDiscount();

	ModelColumn<I_EDI_cctop_invoic_500_v, Object> COLUMN_Discount = new ModelColumn<>(I_EDI_cctop_invoic_500_v.class, "Discount", null);
	String COLUMNNAME_Discount = "Discount";

	/**
	 * Set Ordered UOM.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setEanCom_Ordered_UOM (@Nullable java.lang.String EanCom_Ordered_UOM);

	/**
	 * Get Ordered UOM.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getEanCom_Ordered_UOM();

	ModelColumn<I_EDI_cctop_invoic_500_v, Object> COLUMN_EanCom_Ordered_UOM = new ModelColumn<>(I_EDI_cctop_invoic_500_v.class, "EanCom_Ordered_UOM", null);
	String COLUMNNAME_EanCom_Ordered_UOM = "EanCom_Ordered_UOM";

	/**
	 * Set EanCom_Price_UOM.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setEanCom_Price_UOM (@Nullable java.lang.String EanCom_Price_UOM);

	/**
	 * Get EanCom_Price_UOM.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getEanCom_Price_UOM();

	ModelColumn<I_EDI_cctop_invoic_500_v, Object> COLUMN_EanCom_Price_UOM = new ModelColumn<>(I_EDI_cctop_invoic_500_v.class, "EanCom_Price_UOM", null);
	String COLUMNNAME_EanCom_Price_UOM = "EanCom_Price_UOM";

	/**
	 * Set eancom_uom.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setEanCom_UOM (@Nullable java.lang.String EanCom_UOM);

	/**
	 * Get eancom_uom.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getEanCom_UOM();

	ModelColumn<I_EDI_cctop_invoic_500_v, Object> COLUMN_EanCom_UOM = new ModelColumn<>(I_EDI_cctop_invoic_500_v.class, "EanCom_UOM", null);
	String COLUMNNAME_EanCom_UOM = "EanCom_UOM";

	/**
	 * Set CU-EAN.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setEAN_CU (@Nullable java.lang.String EAN_CU);

	/**
	 * Get CU-EAN.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getEAN_CU();

	ModelColumn<I_EDI_cctop_invoic_500_v, Object> COLUMN_EAN_CU = new ModelColumn<>(I_EDI_cctop_invoic_500_v.class, "EAN_CU", null);
	String COLUMNNAME_EAN_CU = "EAN_CU";

	/**
	 * Set TU-EAN.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setEAN_TU (@Nullable java.lang.String EAN_TU);

	/**
	 * Get TU-EAN.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getEAN_TU();

	ModelColumn<I_EDI_cctop_invoic_500_v, Object> COLUMN_EAN_TU = new ModelColumn<>(I_EDI_cctop_invoic_500_v.class, "EAN_TU", null);
	String COLUMNNAME_EAN_TU = "EAN_TU";

	/**
	 * Set EDI_cctop_invoic_500_v.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setEDI_cctop_invoic_500_v_ID (int EDI_cctop_invoic_500_v_ID);

	/**
	 * Get EDI_cctop_invoic_500_v.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getEDI_cctop_invoic_500_v_ID();

	ModelColumn<I_EDI_cctop_invoic_500_v, Object> COLUMN_EDI_cctop_invoic_500_v_ID = new ModelColumn<>(I_EDI_cctop_invoic_500_v.class, "EDI_cctop_invoic_500_v_ID", null);
	String COLUMNNAME_EDI_cctop_invoic_500_v_ID = "EDI_cctop_invoic_500_v_ID";

	/**
	 * Set EDI_cctop_invoic_v.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setEDI_cctop_invoic_v_ID (int EDI_cctop_invoic_v_ID);

	/**
	 * Get EDI_cctop_invoic_v.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getEDI_cctop_invoic_v_ID();

	@Nullable de.metas.esb.edi.model.I_EDI_cctop_invoic_v getEDI_cctop_invoic_v();

	void setEDI_cctop_invoic_v(@Nullable de.metas.esb.edi.model.I_EDI_cctop_invoic_v EDI_cctop_invoic_v);

	ModelColumn<I_EDI_cctop_invoic_500_v, de.metas.esb.edi.model.I_EDI_cctop_invoic_v> COLUMN_EDI_cctop_invoic_v_ID = new ModelColumn<>(I_EDI_cctop_invoic_500_v.class, "EDI_cctop_invoic_v_ID", de.metas.esb.edi.model.I_EDI_cctop_invoic_v.class);
	String COLUMNNAME_EDI_cctop_invoic_v_ID = "EDI_cctop_invoic_v_ID";

	/**
	 * Set ExternalSeqNo.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setExternalSeqNo (int ExternalSeqNo);

	/**
	 * Get ExternalSeqNo.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getExternalSeqNo();

	ModelColumn<I_EDI_cctop_invoic_500_v, Object> COLUMN_ExternalSeqNo = new ModelColumn<>(I_EDI_cctop_invoic_500_v.class, "ExternalSeqNo", null);
	String COLUMNNAME_ExternalSeqNo = "ExternalSeqNo";

	/**
	 * Set GTIN.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setGTIN (@Nullable java.lang.String GTIN);

	/**
	 * Get GTIN.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getGTIN();

	ModelColumn<I_EDI_cctop_invoic_500_v, Object> COLUMN_GTIN = new ModelColumn<>(I_EDI_cctop_invoic_500_v.class, "GTIN", null);
	String COLUMNNAME_GTIN = "GTIN";

	/**
	 * Set Invoicable Quantity per.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setInvoicableQtyBasedOn (@Nullable java.lang.String InvoicableQtyBasedOn);

	/**
	 * Get Invoicable Quantity per.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getInvoicableQtyBasedOn();

	ModelColumn<I_EDI_cctop_invoic_500_v, Object> COLUMN_InvoicableQtyBasedOn = new ModelColumn<>(I_EDI_cctop_invoic_500_v.class, "InvoicableQtyBasedOn", null);
	String COLUMNNAME_InvoicableQtyBasedOn = "InvoicableQtyBasedOn";

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

	ModelColumn<I_EDI_cctop_invoic_500_v, Object> COLUMN_IsActive = new ModelColumn<>(I_EDI_cctop_invoic_500_v.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set ISO Currency Code.
	 * Three letter ISO 4217 Code of the Currency
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setISO_Code (@Nullable java.lang.String ISO_Code);

	/**
	 * Get ISO Currency Code.
	 * Three letter ISO 4217 Code of the Currency
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getISO_Code();

	ModelColumn<I_EDI_cctop_invoic_500_v, Object> COLUMN_ISO_Code = new ModelColumn<>(I_EDI_cctop_invoic_500_v.class, "ISO_Code", null);
	String COLUMNNAME_ISO_Code = "ISO_Code";

	/**
	 * Set SO Tax exempt.
	 * Business partner is exempt from tax on sales
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsTaxExempt (boolean IsTaxExempt);

	/**
	 * Get SO Tax exempt.
	 * Business partner is exempt from tax on sales
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isTaxExempt();

	ModelColumn<I_EDI_cctop_invoic_500_v, Object> COLUMN_IsTaxExempt = new ModelColumn<>(I_EDI_cctop_invoic_500_v.class, "IsTaxExempt", null);
	String COLUMNNAME_IsTaxExempt = "IsTaxExempt";

	/**
	 * Set Leergut.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setLeergut (@Nullable java.lang.String Leergut);

	/**
	 * Get Leergut.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getLeergut();

	ModelColumn<I_EDI_cctop_invoic_500_v, Object> COLUMN_Leergut = new ModelColumn<>(I_EDI_cctop_invoic_500_v.class, "Leergut", null);
	String COLUMNNAME_Leergut = "Leergut";

	/**
	 * Set SeqNo..
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setLine (int Line);

	/**
	 * Get SeqNo..
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getLine();

	ModelColumn<I_EDI_cctop_invoic_500_v, Object> COLUMN_Line = new ModelColumn<>(I_EDI_cctop_invoic_500_v.class, "Line", null);
	String COLUMNNAME_Line = "Line";

	/**
	 * Set Line Net Amount.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setLineNetAmt (@Nullable BigDecimal LineNetAmt);

	/**
	 * Get Line Net Amount.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getLineNetAmt();

	ModelColumn<I_EDI_cctop_invoic_500_v, Object> COLUMN_LineNetAmt = new ModelColumn<>(I_EDI_cctop_invoic_500_v.class, "LineNetAmt", null);
	String COLUMNNAME_LineNetAmt = "LineNetAmt";

	/**
	 * Set Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setName (@Nullable java.lang.String Name);

	/**
	 * Get Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getName();

	ModelColumn<I_EDI_cctop_invoic_500_v, Object> COLUMN_Name = new ModelColumn<>(I_EDI_cctop_invoic_500_v.class, "Name", null);
	String COLUMNNAME_Name = "Name";

	/**
	 * Set Name 2.
	 * Additional Name
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setName2 (@Nullable java.lang.String Name2);

	/**
	 * Get Name 2.
	 * Additional Name
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getName2();

	ModelColumn<I_EDI_cctop_invoic_500_v, Object> COLUMN_Name2 = new ModelColumn<>(I_EDI_cctop_invoic_500_v.class, "Name2", null);
	String COLUMNNAME_Name2 = "Name2";

	/**
	 * Set Order Line.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setOrderLine (int OrderLine);

	/**
	 * Get Order Line.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getOrderLine();

	ModelColumn<I_EDI_cctop_invoic_500_v, Object> COLUMN_OrderLine = new ModelColumn<>(I_EDI_cctop_invoic_500_v.class, "OrderLine", null);
	String COLUMNNAME_OrderLine = "OrderLine";

	/**
	 * Set Auftragsreferenz.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setOrderPOReference (@Nullable java.lang.String OrderPOReference);

	/**
	 * Get Auftragsreferenz.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getOrderPOReference();

	ModelColumn<I_EDI_cctop_invoic_500_v, Object> COLUMN_OrderPOReference = new ModelColumn<>(I_EDI_cctop_invoic_500_v.class, "OrderPOReference", null);
	String COLUMNNAME_OrderPOReference = "OrderPOReference";

	/**
	 * Set Price Actual.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPriceActual (@Nullable BigDecimal PriceActual);

	/**
	 * Get Price Actual.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getPriceActual();

	ModelColumn<I_EDI_cctop_invoic_500_v, Object> COLUMN_PriceActual = new ModelColumn<>(I_EDI_cctop_invoic_500_v.class, "PriceActual", null);
	String COLUMNNAME_PriceActual = "PriceActual";

	/**
	 * Set List Price.
	 * List Price
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPriceList (@Nullable BigDecimal PriceList);

	/**
	 * Get List Price.
	 * List Price
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getPriceList();

	ModelColumn<I_EDI_cctop_invoic_500_v, Object> COLUMN_PriceList = new ModelColumn<>(I_EDI_cctop_invoic_500_v.class, "PriceList", null);
	String COLUMNNAME_PriceList = "PriceList";

	/**
	 * Set Product Description.
	 * Product Description
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setProductDescription (@Nullable java.lang.String ProductDescription);

	/**
	 * Get Product Description.
	 * Product Description
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getProductDescription();

	ModelColumn<I_EDI_cctop_invoic_500_v, Object> COLUMN_ProductDescription = new ModelColumn<>(I_EDI_cctop_invoic_500_v.class, "ProductDescription", null);
	String COLUMNNAME_ProductDescription = "ProductDescription";

	/**
	 * Set Qty Entered In BPartner UOM.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQtyEnteredInBPartnerUOM (@Nullable BigDecimal QtyEnteredInBPartnerUOM);

	/**
	 * Get Qty Entered In BPartner UOM.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyEnteredInBPartnerUOM();

	ModelColumn<I_EDI_cctop_invoic_500_v, Object> COLUMN_QtyEnteredInBPartnerUOM = new ModelColumn<>(I_EDI_cctop_invoic_500_v.class, "QtyEnteredInBPartnerUOM", null);
	String COLUMNNAME_QtyEnteredInBPartnerUOM = "QtyEnteredInBPartnerUOM";

	/**
	 * Set Quantity Invoiced.
	 * Invoiced quantity in the product's UOM.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQtyInvoiced (@Nullable BigDecimal QtyInvoiced);

	/**
	 * Get Quantity Invoiced.
	 * Invoiced quantity in the product's UOM.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyInvoiced();

	ModelColumn<I_EDI_cctop_invoic_500_v, Object> COLUMN_QtyInvoiced = new ModelColumn<>(I_EDI_cctop_invoic_500_v.class, "QtyInvoiced", null);
	String COLUMNNAME_QtyInvoiced = "QtyInvoiced";

	/**
	 * Set Invoiced quantity in ordered UOM.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQtyInvoicedInOrderedUOM (@Nullable BigDecimal QtyInvoicedInOrderedUOM);

	/**
	 * Get Invoiced quantity in ordered UOM.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyInvoicedInOrderedUOM();

	ModelColumn<I_EDI_cctop_invoic_500_v, Object> COLUMN_QtyInvoicedInOrderedUOM = new ModelColumn<>(I_EDI_cctop_invoic_500_v.class, "QtyInvoicedInOrderedUOM", null);
	String COLUMNNAME_QtyInvoicedInOrderedUOM = "QtyInvoicedInOrderedUOM";

	/**
	 * Set Rate.
	 * Rate or Tax or Exchange
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setRate (@Nullable BigDecimal Rate);

	/**
	 * Get Rate.
	 * Rate or Tax or Exchange
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getRate();

	ModelColumn<I_EDI_cctop_invoic_500_v, Object> COLUMN_Rate = new ModelColumn<>(I_EDI_cctop_invoic_500_v.class, "Rate", null);
	String COLUMNNAME_Rate = "Rate";

	/**
	 * Set Supplier_GTIN_CU.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setSupplier_GTIN_CU (@Nullable java.lang.String Supplier_GTIN_CU);

	/**
	 * Get Supplier_GTIN_CU.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getSupplier_GTIN_CU();

	ModelColumn<I_EDI_cctop_invoic_500_v, Object> COLUMN_Supplier_GTIN_CU = new ModelColumn<>(I_EDI_cctop_invoic_500_v.class, "Supplier_GTIN_CU", null);
	String COLUMNNAME_Supplier_GTIN_CU = "Supplier_GTIN_CU";

	/**
	 * Set Positions-Steuer.
	 * Betrag der enthaltenen oder zuzgl. Steuer in einer Rechungs- oder Auftragsposition
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setTaxAmtInfo (@Nullable BigDecimal TaxAmtInfo);

	/**
	 * Get Positions-Steuer.
	 * Betrag der enthaltenen oder zuzgl. Steuer in einer Rechungs- oder Auftragsposition
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getTaxAmtInfo();

	ModelColumn<I_EDI_cctop_invoic_500_v, Object> COLUMN_TaxAmtInfo = new ModelColumn<>(I_EDI_cctop_invoic_500_v.class, "TaxAmtInfo", null);
	String COLUMNNAME_TaxAmtInfo = "TaxAmtInfo";

	/**
	 * Set taxfree.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void settaxfree (boolean taxfree);

	/**
	 * Get taxfree.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean istaxfree();

	ModelColumn<I_EDI_cctop_invoic_500_v, Object> COLUMN_taxfree = new ModelColumn<>(I_EDI_cctop_invoic_500_v.class, "taxfree", null);
	String COLUMNNAME_taxfree = "taxfree";

	/**
	 * Set CU-UPC.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setUPC_CU (@Nullable java.lang.String UPC_CU);

	/**
	 * Get CU-UPC.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getUPC_CU();

	ModelColumn<I_EDI_cctop_invoic_500_v, Object> COLUMN_UPC_CU = new ModelColumn<>(I_EDI_cctop_invoic_500_v.class, "UPC_CU", null);
	String COLUMNNAME_UPC_CU = "UPC_CU";

	/**
	 * Set TU-UPC.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setUPC_TU (@Nullable java.lang.String UPC_TU);

	/**
	 * Get TU-UPC.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getUPC_TU();

	ModelColumn<I_EDI_cctop_invoic_500_v, Object> COLUMN_UPC_TU = new ModelColumn<>(I_EDI_cctop_invoic_500_v.class, "UPC_TU", null);
	String COLUMNNAME_UPC_TU = "UPC_TU";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_EDI_cctop_invoic_500_v, Object> COLUMN_Updated = new ModelColumn<>(I_EDI_cctop_invoic_500_v.class, "Updated", null);
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
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setValue (@Nullable java.lang.String Value);

	/**
	 * Get Search Key.
	 * Search key for the record in the format required - must be unique
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getValue();

	ModelColumn<I_EDI_cctop_invoic_500_v, Object> COLUMN_Value = new ModelColumn<>(I_EDI_cctop_invoic_500_v.class, "Value", null);
	String COLUMNNAME_Value = "Value";
}
