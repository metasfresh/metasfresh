package de.metas.esb.edi.model;


/** Generated Interface for EDI_cctop_120_v
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_EDI_cctop_120_v 
{

    /** TableName=EDI_cctop_120_v */
    public static final String Table_Name = "EDI_cctop_120_v";

    /** AD_Table_ID=540467 */
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
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_120_v, org.compiere.model.I_C_Invoice> COLUMN_C_Invoice_ID = new org.adempiere.model.ModelColumn<I_EDI_cctop_120_v, org.compiere.model.I_C_Invoice>(I_EDI_cctop_120_v.class, "C_Invoice_ID", org.compiere.model.I_C_Invoice.class);
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
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_120_v, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_EDI_cctop_120_v, Object>(I_EDI_cctop_120_v.class, "Created", null);
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
	 * Set EDI_cctop_120_v.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setEDI_cctop_120_v_ID (int EDI_cctop_120_v_ID);

	/**
	 * Get EDI_cctop_120_v.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getEDI_cctop_120_v_ID();

    /** Column definition for EDI_cctop_120_v_ID */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_120_v, Object> COLUMN_EDI_cctop_120_v_ID = new org.adempiere.model.ModelColumn<I_EDI_cctop_120_v, Object>(I_EDI_cctop_120_v.class, "EDI_cctop_120_v_ID", null);
    /** Column name EDI_cctop_120_v_ID */
    public static final String COLUMNNAME_EDI_cctop_120_v_ID = "EDI_cctop_120_v_ID";

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
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_120_v, de.metas.esb.edi.model.I_EDI_cctop_invoic_v> COLUMN_EDI_cctop_invoic_v_ID = new org.adempiere.model.ModelColumn<I_EDI_cctop_120_v, de.metas.esb.edi.model.I_EDI_cctop_invoic_v>(I_EDI_cctop_120_v.class, "EDI_cctop_invoic_v_ID", de.metas.esb.edi.model.I_EDI_cctop_invoic_v.class);
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
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_120_v, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_EDI_cctop_120_v, Object>(I_EDI_cctop_120_v.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set ISO Währungscode.
	 * Dreibuchstabiger ISO 4217 Code für die Währung
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setISO_Code (java.lang.String ISO_Code);

	/**
	 * Get ISO Währungscode.
	 * Dreibuchstabiger ISO 4217 Code für die Währung
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getISO_Code();

    /** Column definition for ISO_Code */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_120_v, Object> COLUMN_ISO_Code = new org.adempiere.model.ModelColumn<I_EDI_cctop_120_v, Object>(I_EDI_cctop_120_v.class, "ISO_Code", null);
    /** Column name ISO_Code */
    public static final String COLUMNNAME_ISO_Code = "ISO_Code";

	/**
	 * Set netdate.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setnetdate (java.sql.Timestamp netdate);

	/**
	 * Get netdate.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getnetdate();

    /** Column definition for netdate */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_120_v, Object> COLUMN_netdate = new org.adempiere.model.ModelColumn<I_EDI_cctop_120_v, Object>(I_EDI_cctop_120_v.class, "netdate", null);
    /** Column name netdate */
    public static final String COLUMNNAME_netdate = "netdate";

	/**
	 * Set Tage Netto.
	 * Anzahl Tage, bis Zahlung fällig wird.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setNetDays (int NetDays);

	/**
	 * Get Tage Netto.
	 * Anzahl Tage, bis Zahlung fällig wird.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getNetDays();

    /** Column definition for NetDays */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_120_v, Object> COLUMN_NetDays = new org.adempiere.model.ModelColumn<I_EDI_cctop_120_v, Object>(I_EDI_cctop_120_v.class, "NetDays", null);
    /** Column name NetDays */
    public static final String COLUMNNAME_NetDays = "NetDays";

	/**
	 * Set singlevat.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setsinglevat (java.math.BigDecimal singlevat);

	/**
	 * Get singlevat.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getsinglevat();

    /** Column definition for singlevat */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_120_v, Object> COLUMN_singlevat = new org.adempiere.model.ModelColumn<I_EDI_cctop_120_v, Object>(I_EDI_cctop_120_v.class, "singlevat", null);
    /** Column name singlevat */
    public static final String COLUMNNAME_singlevat = "singlevat";

	/**
	 * Set taxfree.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void settaxfree (boolean taxfree);

	/**
	 * Get taxfree.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean istaxfree();

    /** Column definition for taxfree */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_120_v, Object> COLUMN_taxfree = new org.adempiere.model.ModelColumn<I_EDI_cctop_120_v, Object>(I_EDI_cctop_120_v.class, "taxfree", null);
    /** Column name taxfree */
    public static final String COLUMNNAME_taxfree = "taxfree";

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
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_120_v, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_EDI_cctop_120_v, Object>(I_EDI_cctop_120_v.class, "Updated", null);
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
