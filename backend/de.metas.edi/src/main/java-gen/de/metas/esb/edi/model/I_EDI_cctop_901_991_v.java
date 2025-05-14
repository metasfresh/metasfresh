package de.metas.esb.edi.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;
import java.math.BigDecimal;

/** Generated Interface for EDI_cctop_901_991_v
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_EDI_cctop_901_991_v 
{

	String Table_Name = "EDI_cctop_901_991_v";

//	/** AD_Table_ID=540469 */
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

	ModelColumn<I_EDI_cctop_901_991_v, org.compiere.model.I_C_Invoice> COLUMN_C_Invoice_ID = new ModelColumn<>(I_EDI_cctop_901_991_v.class, "C_Invoice_ID", org.compiere.model.I_C_Invoice.class);
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

	ModelColumn<I_EDI_cctop_901_991_v, Object> COLUMN_Created = new ModelColumn<>(I_EDI_cctop_901_991_v.class, "Created", null);
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
	 * Set EDI_cctop_901_991_v.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setEDI_cctop_901_991_v_ID (int EDI_cctop_901_991_v_ID);

	/**
	 * Get EDI_cctop_901_991_v.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getEDI_cctop_901_991_v_ID();

	ModelColumn<I_EDI_cctop_901_991_v, Object> COLUMN_EDI_cctop_901_991_v_ID = new ModelColumn<>(I_EDI_cctop_901_991_v.class, "EDI_cctop_901_991_v_ID", null);
	String COLUMNNAME_EDI_cctop_901_991_v_ID = "EDI_cctop_901_991_v_ID";

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

	ModelColumn<I_EDI_cctop_901_991_v, de.metas.esb.edi.model.I_EDI_cctop_invoic_v> COLUMN_EDI_cctop_invoic_v_ID = new ModelColumn<>(I_EDI_cctop_901_991_v.class, "EDI_cctop_invoic_v_ID", de.metas.esb.edi.model.I_EDI_cctop_invoic_v.class);
	String COLUMNNAME_EDI_cctop_invoic_v_ID = "EDI_cctop_invoic_v_ID";

	/**
	 * Set Reference No.
	 * Your customer or vendor number at the Business Partner's site
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setESRReferenceNumber (@Nullable java.lang.String ESRReferenceNumber);

	/**
	 * Get Reference No.
	 * Your customer or vendor number at the Business Partner's site
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getESRReferenceNumber();

	ModelColumn<I_EDI_cctop_901_991_v, Object> COLUMN_ESRReferenceNumber = new ModelColumn<>(I_EDI_cctop_901_991_v.class, "ESRReferenceNumber", null);
	String COLUMNNAME_ESRReferenceNumber = "ESRReferenceNumber";

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

	ModelColumn<I_EDI_cctop_901_991_v, Object> COLUMN_IsActive = new ModelColumn<>(I_EDI_cctop_901_991_v.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Hauptmehrwertsteuersatz.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsMainVAT (boolean IsMainVAT);

	/**
	 * Get Hauptmehrwertsteuersatz.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isMainVAT();

	ModelColumn<I_EDI_cctop_901_991_v, Object> COLUMN_IsMainVAT = new ModelColumn<>(I_EDI_cctop_901_991_v.class, "IsMainVAT", null);
	String COLUMNNAME_IsMainVAT = "IsMainVAT";

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

	ModelColumn<I_EDI_cctop_901_991_v, Object> COLUMN_IsTaxExempt = new ModelColumn<>(I_EDI_cctop_901_991_v.class, "IsTaxExempt", null);
	String COLUMNNAME_IsTaxExempt = "IsTaxExempt";

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

	ModelColumn<I_EDI_cctop_901_991_v, Object> COLUMN_Rate = new ModelColumn<>(I_EDI_cctop_901_991_v.class, "Rate", null);
	String COLUMNNAME_Rate = "Rate";

	/**
	 * Set SurchargeAmt.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setSurchargeAmt (BigDecimal SurchargeAmt);

	/**
	 * Get SurchargeAmt.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getSurchargeAmt();

	ModelColumn<I_EDI_cctop_901_991_v, Object> COLUMN_SurchargeAmt = new ModelColumn<>(I_EDI_cctop_901_991_v.class, "SurchargeAmt", null);
	String COLUMNNAME_SurchargeAmt = "SurchargeAmt";

	/**
	 * Set Tax Amount.
	 * Tax Amount for Credit Card transaction
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setTaxAmt (@Nullable BigDecimal TaxAmt);

	/**
	 * Get Tax Amount.
	 * Tax Amount for Credit Card transaction
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getTaxAmt();

	ModelColumn<I_EDI_cctop_901_991_v, Object> COLUMN_TaxAmt = new ModelColumn<>(I_EDI_cctop_901_991_v.class, "TaxAmt", null);
	String COLUMNNAME_TaxAmt = "TaxAmt";

	/**
	 * Set TaxAmtWithSurchargeAmt.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setTaxAmtWithSurchargeAmt (BigDecimal TaxAmtWithSurchargeAmt);

	/**
	 * Get TaxAmtWithSurchargeAmt.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getTaxAmtWithSurchargeAmt();

	ModelColumn<I_EDI_cctop_901_991_v, Object> COLUMN_TaxAmtWithSurchargeAmt = new ModelColumn<>(I_EDI_cctop_901_991_v.class, "TaxAmtWithSurchargeAmt", null);
	String COLUMNNAME_TaxAmtWithSurchargeAmt = "TaxAmtWithSurchargeAmt";

	/**
	 * Set Tax base Amount.
	 * Base for calculating the tax amount
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setTaxBaseAmt (@Nullable BigDecimal TaxBaseAmt);

	/**
	 * Get Tax base Amount.
	 * Base for calculating the tax amount
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getTaxBaseAmt();

	ModelColumn<I_EDI_cctop_901_991_v, Object> COLUMN_TaxBaseAmt = new ModelColumn<>(I_EDI_cctop_901_991_v.class, "TaxBaseAmt", null);
	String COLUMNNAME_TaxBaseAmt = "TaxBaseAmt";

	/**
	 * Set TaxBaseAmtWithSurchargeAmt.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setTaxBaseAmtWithSurchargeAmt (BigDecimal TaxBaseAmtWithSurchargeAmt);

	/**
	 * Get TaxBaseAmtWithSurchargeAmt.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getTaxBaseAmtWithSurchargeAmt();

	ModelColumn<I_EDI_cctop_901_991_v, Object> COLUMN_TaxBaseAmtWithSurchargeAmt = new ModelColumn<>(I_EDI_cctop_901_991_v.class, "TaxBaseAmtWithSurchargeAmt", null);
	String COLUMNNAME_TaxBaseAmtWithSurchargeAmt = "TaxBaseAmtWithSurchargeAmt";

	/**
	 * Set Total Amount.
	 * Total Amount
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setTotalAmt (@Nullable BigDecimal TotalAmt);

	/**
	 * Get Total Amount.
	 * Total Amount
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getTotalAmt();

	ModelColumn<I_EDI_cctop_901_991_v, Object> COLUMN_TotalAmt = new ModelColumn<>(I_EDI_cctop_901_991_v.class, "TotalAmt", null);
	String COLUMNNAME_TotalAmt = "TotalAmt";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_EDI_cctop_901_991_v, Object> COLUMN_Updated = new ModelColumn<>(I_EDI_cctop_901_991_v.class, "Updated", null);
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
