package de.metas.esb.edi.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;
import java.math.BigDecimal;

/** Generated Interface for EDI_cctop_140_v
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_EDI_cctop_140_v 
{

	String Table_Name = "EDI_cctop_140_v";

//	/** AD_Table_ID=540468 */
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

	ModelColumn<I_EDI_cctop_140_v, org.compiere.model.I_C_Invoice> COLUMN_C_Invoice_ID = new ModelColumn<>(I_EDI_cctop_140_v.class, "C_Invoice_ID", org.compiere.model.I_C_Invoice.class);
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

	ModelColumn<I_EDI_cctop_140_v, Object> COLUMN_Created = new ModelColumn<>(I_EDI_cctop_140_v.class, "Created", null);
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
	 * Set Discount %.
	 * Discount in percent
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDiscount (int Discount);

	/**
	 * Get Discount %.
	 * Discount in percent
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getDiscount();

	ModelColumn<I_EDI_cctop_140_v, Object> COLUMN_Discount = new ModelColumn<>(I_EDI_cctop_140_v.class, "Discount", null);
	String COLUMNNAME_Discount = "Discount";

	/**
	 * Set Discount.
	 * Calculated amount of discount
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDiscountAmt (@Nullable BigDecimal DiscountAmt);

	/**
	 * Get Discount.
	 * Calculated amount of discount
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getDiscountAmt();

	ModelColumn<I_EDI_cctop_140_v, Object> COLUMN_DiscountAmt = new ModelColumn<>(I_EDI_cctop_140_v.class, "DiscountAmt", null);
	String COLUMNNAME_DiscountAmt = "DiscountAmt";

	/**
	 * Set DiscountBaseAmt.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDiscountBaseAmt (@Nullable BigDecimal DiscountBaseAmt);

	/**
	 * Get DiscountBaseAmt.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getDiscountBaseAmt();

	ModelColumn<I_EDI_cctop_140_v, Object> COLUMN_DiscountBaseAmt = new ModelColumn<>(I_EDI_cctop_140_v.class, "DiscountBaseAmt", null);
	String COLUMNNAME_DiscountBaseAmt = "DiscountBaseAmt";

	/**
	 * Set Discount Date.
	 * Last Date for payments with discount
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDiscountDate (@Nullable java.sql.Timestamp DiscountDate);

	/**
	 * Get Discount Date.
	 * Last Date for payments with discount
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getDiscountDate();

	ModelColumn<I_EDI_cctop_140_v, Object> COLUMN_DiscountDate = new ModelColumn<>(I_EDI_cctop_140_v.class, "DiscountDate", null);
	String COLUMNNAME_DiscountDate = "DiscountDate";

	/**
	 * Set Discount Days.
	 * Number of days from invoice date to be eligible for discount
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDiscountDays (int DiscountDays);

	/**
	 * Get Discount Days.
	 * Number of days from invoice date to be eligible for discount
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getDiscountDays();

	ModelColumn<I_EDI_cctop_140_v, Object> COLUMN_DiscountDays = new ModelColumn<>(I_EDI_cctop_140_v.class, "DiscountDays", null);
	String COLUMNNAME_DiscountDays = "DiscountDays";

	/**
	 * Set EDI_cctop_140_v.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setEDI_cctop_140_v_ID (int EDI_cctop_140_v_ID);

	/**
	 * Get EDI_cctop_140_v.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getEDI_cctop_140_v_ID();

	ModelColumn<I_EDI_cctop_140_v, Object> COLUMN_EDI_cctop_140_v_ID = new ModelColumn<>(I_EDI_cctop_140_v.class, "EDI_cctop_140_v_ID", null);
	String COLUMNNAME_EDI_cctop_140_v_ID = "EDI_cctop_140_v_ID";

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

	ModelColumn<I_EDI_cctop_140_v, de.metas.esb.edi.model.I_EDI_cctop_invoic_v> COLUMN_EDI_cctop_invoic_v_ID = new ModelColumn<>(I_EDI_cctop_140_v.class, "EDI_cctop_invoic_v_ID", de.metas.esb.edi.model.I_EDI_cctop_invoic_v.class);
	String COLUMNNAME_EDI_cctop_invoic_v_ID = "EDI_cctop_invoic_v_ID";

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

	ModelColumn<I_EDI_cctop_140_v, Object> COLUMN_IsActive = new ModelColumn<>(I_EDI_cctop_140_v.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

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

	ModelColumn<I_EDI_cctop_140_v, Object> COLUMN_Name = new ModelColumn<>(I_EDI_cctop_140_v.class, "Name", null);
	String COLUMNNAME_Name = "Name";

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

	ModelColumn<I_EDI_cctop_140_v, Object> COLUMN_Rate = new ModelColumn<>(I_EDI_cctop_140_v.class, "Rate", null);
	String COLUMNNAME_Rate = "Rate";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_EDI_cctop_140_v, Object> COLUMN_Updated = new ModelColumn<>(I_EDI_cctop_140_v.class, "Updated", null);
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
