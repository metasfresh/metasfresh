package de.metas.esb.edi.model;


/** Generated Interface for EDI_cctop_140_v
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_EDI_cctop_140_v 
{

    /** TableName=EDI_cctop_140_v */
    public static final String Table_Name = "EDI_cctop_140_v";

    /** AD_Table_ID=540468 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);


	/**
	 * Get Client.
	 * Client/Tenant for this installation.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Client_ID();

    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Organisation.
	 * Organisational entity within client
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Organisation.
	 * Organisational entity within client
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Org_ID();

    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Invoice.
	 * Invoice Identifier
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Invoice_ID (int C_Invoice_ID);

	/**
	 * Get Invoice.
	 * Invoice Identifier
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Invoice_ID();

	public org.compiere.model.I_C_Invoice getC_Invoice();

	public void setC_Invoice(org.compiere.model.I_C_Invoice C_Invoice);

    /** Column definition for C_Invoice_ID */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_140_v, org.compiere.model.I_C_Invoice> COLUMN_C_Invoice_ID = new org.adempiere.model.ModelColumn<I_EDI_cctop_140_v, org.compiere.model.I_C_Invoice>(I_EDI_cctop_140_v.class, "C_Invoice_ID", org.compiere.model.I_C_Invoice.class);
    /** Column name C_Invoice_ID */
    public static final String COLUMNNAME_C_Invoice_ID = "C_Invoice_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getCreated();

    /** Column definition for Created */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_140_v, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_EDI_cctop_140_v, Object>(I_EDI_cctop_140_v.class, "Created", null);
    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/**
	 * Get Created By.
	 * User who created this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getCreatedBy();

    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Rabatt %.
	 * Abschlag in Prozent
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDiscount (int Discount);

	/**
	 * Get Rabatt %.
	 * Abschlag in Prozent
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getDiscount();

    /** Column definition for Discount */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_140_v, Object> COLUMN_Discount = new org.adempiere.model.ModelColumn<I_EDI_cctop_140_v, Object>(I_EDI_cctop_140_v.class, "Discount", null);
    /** Column name Discount */
    public static final String COLUMNNAME_Discount = "Discount";

	/**
	 * Set Skontodatum.
	 * Last Date for payments with discount
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDiscountDate (java.sql.Timestamp DiscountDate);

	/**
	 * Get Skontodatum.
	 * Last Date for payments with discount
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getDiscountDate();

    /** Column definition for DiscountDate */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_140_v, Object> COLUMN_DiscountDate = new org.adempiere.model.ModelColumn<I_EDI_cctop_140_v, Object>(I_EDI_cctop_140_v.class, "DiscountDate", null);
    /** Column name DiscountDate */
    public static final String COLUMNNAME_DiscountDate = "DiscountDate";

	/**
	 * Set Rabattfrist.
	 * Anzahl der Tage ab Rechnungsstellung innerhalb derer der Rabatt gewährt wird
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDiscountDays (int DiscountDays);

	/**
	 * Get Rabattfrist.
	 * Anzahl der Tage ab Rechnungsstellung innerhalb derer der Rabatt gewährt wird
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getDiscountDays();

    /** Column definition for DiscountDays */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_140_v, Object> COLUMN_DiscountDays = new org.adempiere.model.ModelColumn<I_EDI_cctop_140_v, Object>(I_EDI_cctop_140_v.class, "DiscountDays", null);
    /** Column name DiscountDays */
    public static final String COLUMNNAME_DiscountDays = "DiscountDays";

	/**
	 * Set EDI_cctop_140_v.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setEDI_cctop_140_v_ID (int EDI_cctop_140_v_ID);

	/**
	 * Get EDI_cctop_140_v.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getEDI_cctop_140_v_ID();

    /** Column definition for EDI_cctop_140_v_ID */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_140_v, Object> COLUMN_EDI_cctop_140_v_ID = new org.adempiere.model.ModelColumn<I_EDI_cctop_140_v, Object>(I_EDI_cctop_140_v.class, "EDI_cctop_140_v_ID", null);
    /** Column name EDI_cctop_140_v_ID */
    public static final String COLUMNNAME_EDI_cctop_140_v_ID = "EDI_cctop_140_v_ID";

	/**
	 * Set EDI_cctop_invoic_v.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setEDI_cctop_invoic_v_ID (int EDI_cctop_invoic_v_ID);

	/**
	 * Get EDI_cctop_invoic_v.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getEDI_cctop_invoic_v_ID();

	public de.metas.esb.edi.model.I_EDI_cctop_invoic_v getEDI_cctop_invoic_v();

	public void setEDI_cctop_invoic_v(de.metas.esb.edi.model.I_EDI_cctop_invoic_v EDI_cctop_invoic_v);

    /** Column definition for EDI_cctop_invoic_v_ID */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_140_v, de.metas.esb.edi.model.I_EDI_cctop_invoic_v> COLUMN_EDI_cctop_invoic_v_ID = new org.adempiere.model.ModelColumn<I_EDI_cctop_140_v, de.metas.esb.edi.model.I_EDI_cctop_invoic_v>(I_EDI_cctop_140_v.class, "EDI_cctop_invoic_v_ID", de.metas.esb.edi.model.I_EDI_cctop_invoic_v.class);
    /** Column name EDI_cctop_invoic_v_ID */
    public static final String COLUMNNAME_EDI_cctop_invoic_v_ID = "EDI_cctop_invoic_v_ID";

	/**
	 * Set Active.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsActive (boolean IsActive);

	/**
	 * Get Active.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isActive();

    /** Column definition for IsActive */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_140_v, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_EDI_cctop_140_v, Object>(I_EDI_cctop_140_v.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Satz.
	 * Rate or Tax or Exchange
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setRate (java.math.BigDecimal Rate);

	/**
	 * Get Satz.
	 * Rate or Tax or Exchange
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getRate();

    /** Column definition for Rate */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_140_v, Object> COLUMN_Rate = new org.adempiere.model.ModelColumn<I_EDI_cctop_140_v, Object>(I_EDI_cctop_140_v.class, "Rate", null);
    /** Column name Rate */
    public static final String COLUMNNAME_Rate = "Rate";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getUpdated();

    /** Column definition for Updated */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_140_v, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_EDI_cctop_140_v, Object>(I_EDI_cctop_140_v.class, "Updated", null);
    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Updated By.
	 * User who updated this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getUpdatedBy();

    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
