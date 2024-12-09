package de.metas.esb.edi.model;

<<<<<<< HEAD
=======
import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;
import java.math.BigDecimal;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

/** Generated Interface for EDI_cctop_901_991_v
 *  @author metasfresh (generated) 
 */
<<<<<<< HEAD
@SuppressWarnings("javadoc")
public interface I_EDI_cctop_901_991_v 
{

    /** TableName=EDI_cctop_901_991_v */
    public static final String Table_Name = "EDI_cctop_901_991_v";

    /** AD_Table_ID=540469 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);
=======
@SuppressWarnings("unused")
public interface I_EDI_cctop_901_991_v 
{

	String Table_Name = "EDI_cctop_901_991_v";

//	/** AD_Table_ID=540469 */
//	int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))


	/**
	 * Get Client.
	 * Client/Tenant for this installation.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public int getAD_Client_ID();

    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";
=======
	int getAD_Client_ID();

	String COLUMNNAME_AD_Client_ID = "AD_Client_ID";
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Set Organisation.
	 * Organisational entity within client
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setAD_Org_ID (int AD_Org_ID);
=======
	void setAD_Org_ID (int AD_Org_ID);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Get Organisation.
	 * Organisational entity within client
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public int getAD_Org_ID();

    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";
=======
	int getAD_Org_ID();

	String COLUMNNAME_AD_Org_ID = "AD_Org_ID";
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Set Invoice.
	 * Invoice Identifier
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setC_Invoice_ID (int C_Invoice_ID);
=======
	void setC_Invoice_ID (int C_Invoice_ID);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Get Invoice.
	 * Invoice Identifier
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public int getC_Invoice_ID();

	public org.compiere.model.I_C_Invoice getC_Invoice();

	public void setC_Invoice(org.compiere.model.I_C_Invoice C_Invoice);

    /** Column definition for C_Invoice_ID */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_901_991_v, org.compiere.model.I_C_Invoice> COLUMN_C_Invoice_ID = new org.adempiere.model.ModelColumn<I_EDI_cctop_901_991_v, org.compiere.model.I_C_Invoice>(I_EDI_cctop_901_991_v.class, "C_Invoice_ID", org.compiere.model.I_C_Invoice.class);
    /** Column name C_Invoice_ID */
    public static final String COLUMNNAME_C_Invoice_ID = "C_Invoice_ID";
=======
	int getC_Invoice_ID();

	@Nullable org.compiere.model.I_C_Invoice getC_Invoice();

	void setC_Invoice(@Nullable org.compiere.model.I_C_Invoice C_Invoice);

	ModelColumn<I_EDI_cctop_901_991_v, org.compiere.model.I_C_Invoice> COLUMN_C_Invoice_ID = new ModelColumn<>(I_EDI_cctop_901_991_v.class, "C_Invoice_ID", org.compiere.model.I_C_Invoice.class);
	String COLUMNNAME_C_Invoice_ID = "C_Invoice_ID";
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public java.sql.Timestamp getCreated();

    /** Column definition for Created */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_901_991_v, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_EDI_cctop_901_991_v, Object>(I_EDI_cctop_901_991_v.class, "Created", null);
    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";
=======
	java.sql.Timestamp getCreated();

	ModelColumn<I_EDI_cctop_901_991_v, Object> COLUMN_Created = new ModelColumn<>(I_EDI_cctop_901_991_v.class, "Created", null);
	String COLUMNNAME_Created = "Created";
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Get Created By.
	 * User who created this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public int getCreatedBy();

    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";
=======
	int getCreatedBy();

	String COLUMNNAME_CreatedBy = "CreatedBy";
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Set EDI_cctop_901_991_v.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setEDI_cctop_901_991_v_ID (int EDI_cctop_901_991_v_ID);
=======
	void setEDI_cctop_901_991_v_ID (int EDI_cctop_901_991_v_ID);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Get EDI_cctop_901_991_v.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public int getEDI_cctop_901_991_v_ID();

    /** Column definition for EDI_cctop_901_991_v_ID */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_901_991_v, Object> COLUMN_EDI_cctop_901_991_v_ID = new org.adempiere.model.ModelColumn<I_EDI_cctop_901_991_v, Object>(I_EDI_cctop_901_991_v.class, "EDI_cctop_901_991_v_ID", null);
    /** Column name EDI_cctop_901_991_v_ID */
    public static final String COLUMNNAME_EDI_cctop_901_991_v_ID = "EDI_cctop_901_991_v_ID";
=======
	int getEDI_cctop_901_991_v_ID();

	ModelColumn<I_EDI_cctop_901_991_v, Object> COLUMN_EDI_cctop_901_991_v_ID = new ModelColumn<>(I_EDI_cctop_901_991_v.class, "EDI_cctop_901_991_v_ID", null);
	String COLUMNNAME_EDI_cctop_901_991_v_ID = "EDI_cctop_901_991_v_ID";
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Set EDI_cctop_invoic_v.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setEDI_cctop_invoic_v_ID (int EDI_cctop_invoic_v_ID);
=======
	void setEDI_cctop_invoic_v_ID (int EDI_cctop_invoic_v_ID);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Get EDI_cctop_invoic_v.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public int getEDI_cctop_invoic_v_ID();

	public de.metas.esb.edi.model.I_EDI_cctop_invoic_v getEDI_cctop_invoic_v();

	public void setEDI_cctop_invoic_v(de.metas.esb.edi.model.I_EDI_cctop_invoic_v EDI_cctop_invoic_v);

    /** Column definition for EDI_cctop_invoic_v_ID */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_901_991_v, de.metas.esb.edi.model.I_EDI_cctop_invoic_v> COLUMN_EDI_cctop_invoic_v_ID = new org.adempiere.model.ModelColumn<I_EDI_cctop_901_991_v, de.metas.esb.edi.model.I_EDI_cctop_invoic_v>(I_EDI_cctop_901_991_v.class, "EDI_cctop_invoic_v_ID", de.metas.esb.edi.model.I_EDI_cctop_invoic_v.class);
    /** Column name EDI_cctop_invoic_v_ID */
    public static final String COLUMNNAME_EDI_cctop_invoic_v_ID = "EDI_cctop_invoic_v_ID";

	/**
	 * Set Referenznummer.
	 * Ihre Kunden- oder Lieferantennummer beim Geschäftspartner
=======
	int getEDI_cctop_invoic_v_ID();

	@Nullable de.metas.esb.edi.model.I_EDI_cctop_invoic_v getEDI_cctop_invoic_v();

	void setEDI_cctop_invoic_v(@Nullable de.metas.esb.edi.model.I_EDI_cctop_invoic_v EDI_cctop_invoic_v);

	ModelColumn<I_EDI_cctop_901_991_v, de.metas.esb.edi.model.I_EDI_cctop_invoic_v> COLUMN_EDI_cctop_invoic_v_ID = new ModelColumn<>(I_EDI_cctop_901_991_v.class, "EDI_cctop_invoic_v_ID", de.metas.esb.edi.model.I_EDI_cctop_invoic_v.class);
	String COLUMNNAME_EDI_cctop_invoic_v_ID = "EDI_cctop_invoic_v_ID";

	/**
	 * Set Reference No.
	 * Your customer or vendor number at the Business Partner's site
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setESRReferenceNumber (java.lang.String ESRReferenceNumber);

	/**
	 * Get Referenznummer.
	 * Ihre Kunden- oder Lieferantennummer beim Geschäftspartner
=======
	void setESRReferenceNumber (@Nullable java.lang.String ESRReferenceNumber);

	/**
	 * Get Reference No.
	 * Your customer or vendor number at the Business Partner's site
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public java.lang.String getESRReferenceNumber();

    /** Column definition for ESRReferenceNumber */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_901_991_v, Object> COLUMN_ESRReferenceNumber = new org.adempiere.model.ModelColumn<I_EDI_cctop_901_991_v, Object>(I_EDI_cctop_901_991_v.class, "ESRReferenceNumber", null);
    /** Column name ESRReferenceNumber */
    public static final String COLUMNNAME_ESRReferenceNumber = "ESRReferenceNumber";
=======
	@Nullable java.lang.String getESRReferenceNumber();

	ModelColumn<I_EDI_cctop_901_991_v, Object> COLUMN_ESRReferenceNumber = new ModelColumn<>(I_EDI_cctop_901_991_v.class, "ESRReferenceNumber", null);
	String COLUMNNAME_ESRReferenceNumber = "ESRReferenceNumber";
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Set Active.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setIsActive (boolean IsActive);
=======
	void setIsActive (boolean IsActive);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Get Active.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public boolean isActive();

    /** Column definition for IsActive */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_901_991_v, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_EDI_cctop_901_991_v, Object>(I_EDI_cctop_901_991_v.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Satz.
=======
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
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 * Rate or Tax or Exchange
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setRate (java.math.BigDecimal Rate);

	/**
	 * Get Satz.
=======
	void setRate (@Nullable BigDecimal Rate);

	/**
	 * Get Rate.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 * Rate or Tax or Exchange
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public java.math.BigDecimal getRate();

    /** Column definition for Rate */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_901_991_v, Object> COLUMN_Rate = new org.adempiere.model.ModelColumn<I_EDI_cctop_901_991_v, Object>(I_EDI_cctop_901_991_v.class, "Rate", null);
    /** Column name Rate */
    public static final String COLUMNNAME_Rate = "Rate";

	/**
	 * Set Steuerbetrag.
	 * Steuerbetrag für diesen Beleg
=======
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
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setTaxAmt (java.math.BigDecimal TaxAmt);

	/**
	 * Get Steuerbetrag.
	 * Steuerbetrag für diesen Beleg
=======
	void setTaxAmt (@Nullable BigDecimal TaxAmt);

	/**
	 * Get Tax Amount.
	 * Tax Amount for Credit Card transaction
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public java.math.BigDecimal getTaxAmt();

    /** Column definition for TaxAmt */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_901_991_v, Object> COLUMN_TaxAmt = new org.adempiere.model.ModelColumn<I_EDI_cctop_901_991_v, Object>(I_EDI_cctop_901_991_v.class, "TaxAmt", null);
    /** Column name TaxAmt */
    public static final String COLUMNNAME_TaxAmt = "TaxAmt";

	/**
	 * Set Bezugswert.
	 * Bezugswert für die Berechnung der Steuer
=======
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
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setTaxBaseAmt (java.math.BigDecimal TaxBaseAmt);

	/**
	 * Get Bezugswert.
	 * Bezugswert für die Berechnung der Steuer
=======
	void setTaxBaseAmt (@Nullable BigDecimal TaxBaseAmt);

	/**
	 * Get Tax base Amount.
	 * Base for calculating the tax amount
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public java.math.BigDecimal getTaxBaseAmt();

    /** Column definition for TaxBaseAmt */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_901_991_v, Object> COLUMN_TaxBaseAmt = new org.adempiere.model.ModelColumn<I_EDI_cctop_901_991_v, Object>(I_EDI_cctop_901_991_v.class, "TaxBaseAmt", null);
    /** Column name TaxBaseAmt */
    public static final String COLUMNNAME_TaxBaseAmt = "TaxBaseAmt";
=======
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
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Set Total Amount.
	 * Total Amount
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setTotalAmt (java.math.BigDecimal TotalAmt);
=======
	void setTotalAmt (@Nullable BigDecimal TotalAmt);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Get Total Amount.
	 * Total Amount
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public java.math.BigDecimal getTotalAmt();

    /** Column definition for TotalAmt */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_901_991_v, Object> COLUMN_TotalAmt = new org.adempiere.model.ModelColumn<I_EDI_cctop_901_991_v, Object>(I_EDI_cctop_901_991_v.class, "TotalAmt", null);
    /** Column name TotalAmt */
    public static final String COLUMNNAME_TotalAmt = "TotalAmt";
=======
	BigDecimal getTotalAmt();

	ModelColumn<I_EDI_cctop_901_991_v, Object> COLUMN_TotalAmt = new ModelColumn<>(I_EDI_cctop_901_991_v.class, "TotalAmt", null);
	String COLUMNNAME_TotalAmt = "TotalAmt";
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public java.sql.Timestamp getUpdated();

    /** Column definition for Updated */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_901_991_v, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_EDI_cctop_901_991_v, Object>(I_EDI_cctop_901_991_v.class, "Updated", null);
    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";
=======
	java.sql.Timestamp getUpdated();

	ModelColumn<I_EDI_cctop_901_991_v, Object> COLUMN_Updated = new ModelColumn<>(I_EDI_cctop_901_991_v.class, "Updated", null);
	String COLUMNNAME_Updated = "Updated";
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Get Updated By.
	 * User who updated this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public int getUpdatedBy();

    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
=======
	int getUpdatedBy();

	String COLUMNNAME_UpdatedBy = "UpdatedBy";
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
}
