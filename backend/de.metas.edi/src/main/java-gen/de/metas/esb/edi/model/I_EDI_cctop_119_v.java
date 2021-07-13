package de.metas.esb.edi.model;


/** Generated Interface for EDI_cctop_119_v
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_EDI_cctop_119_v 
{

    /** TableName=EDI_cctop_119_v */
    public static final String Table_Name = "EDI_cctop_119_v";

    /** AD_Table_ID=540466 */
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
	 * Set Adresszeile 1.
	 * Adresszeile 1 für diesen Standort
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAddress1 (java.lang.String Address1);

	/**
	 * Get Adresszeile 1.
	 * Adresszeile 1 für diesen Standort
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getAddress1();

    /** Column definition for Address1 */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_119_v, Object> COLUMN_Address1 = new org.adempiere.model.ModelColumn<I_EDI_cctop_119_v, Object>(I_EDI_cctop_119_v.class, "Address1", null);
    /** Column name Address1 */
    public static final String COLUMNNAME_Address1 = "Address1";

	/**
	 * Set Adresszeile 2.
	 * Adresszeile 2 für diesen Standort
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAddress2 (java.lang.String Address2);

	/**
	 * Get Adresszeile 2.
	 * Adresszeile 2 für diesen Standort
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getAddress2();

    /** Column definition for Address2 */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_119_v, Object> COLUMN_Address2 = new org.adempiere.model.ModelColumn<I_EDI_cctop_119_v, Object>(I_EDI_cctop_119_v.class, "Address2", null);
    /** Column name Address2 */
    public static final String COLUMNNAME_Address2 = "Address2";

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
	 * Set Location.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_BPartner_Location_ID (int C_BPartner_Location_ID);

	/**
	 * Get Location.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_BPartner_Location_ID();

    /** Column name C_BPartner_Location_ID */
    public static final String COLUMNNAME_C_BPartner_Location_ID = "C_BPartner_Location_ID";

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
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_119_v, org.compiere.model.I_C_Invoice> COLUMN_C_Invoice_ID = new org.adempiere.model.ModelColumn<I_EDI_cctop_119_v, org.compiere.model.I_C_Invoice>(I_EDI_cctop_119_v.class, "C_Invoice_ID", org.compiere.model.I_C_Invoice.class);
    /** Column name C_Invoice_ID */
    public static final String COLUMNNAME_C_Invoice_ID = "C_Invoice_ID";

	/**
	 * Set City Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setCity (java.lang.String City);

	/**
	 * Get City Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getCity();

    /** Column definition for City */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_119_v, Object> COLUMN_City = new org.adempiere.model.ModelColumn<I_EDI_cctop_119_v, Object>(I_EDI_cctop_119_v.class, "City", null);
    /** Column name City */
    public static final String COLUMNNAME_City = "City";

	/**
	 * Set ISO Country Code.
	 * Upper-case two-letter alphanumeric ISO Country code according to ISO 3166-1 - http://www.chemie.fu-berlin.de/diverse/doc/ISO_3166.html
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setCountryCode (java.lang.String CountryCode);

	/**
	 * Get ISO Country Code.
	 * Upper-case two-letter alphanumeric ISO Country code according to ISO 3166-1 - http://www.chemie.fu-berlin.de/diverse/doc/ISO_3166.html
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getCountryCode();

    /** Column definition for CountryCode */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_119_v, Object> COLUMN_CountryCode = new org.adempiere.model.ModelColumn<I_EDI_cctop_119_v, Object>(I_EDI_cctop_119_v.class, "CountryCode", null);
    /** Column name CountryCode */
    public static final String COLUMNNAME_CountryCode = "CountryCode";

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
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_119_v, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_EDI_cctop_119_v, Object>(I_EDI_cctop_119_v.class, "Created", null);
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
	 * Set eancom_locationtype.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void seteancom_locationtype (java.lang.String eancom_locationtype);

	/**
	 * Get eancom_locationtype.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String geteancom_locationtype();

    /** Column definition for eancom_locationtype */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_119_v, Object> COLUMN_eancom_locationtype = new org.adempiere.model.ModelColumn<I_EDI_cctop_119_v, Object>(I_EDI_cctop_119_v.class, "eancom_locationtype", null);
    /** Column name eancom_locationtype */
    public static final String COLUMNNAME_eancom_locationtype = "eancom_locationtype";

	/**
	 * Set EDI_cctop_119_v.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setEDI_cctop_119_v_ID (int EDI_cctop_119_v_ID);

	/**
	 * Get EDI_cctop_119_v.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getEDI_cctop_119_v_ID();

    /** Column definition for EDI_cctop_119_v_ID */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_119_v, Object> COLUMN_EDI_cctop_119_v_ID = new org.adempiere.model.ModelColumn<I_EDI_cctop_119_v, Object>(I_EDI_cctop_119_v.class, "EDI_cctop_119_v_ID", null);
    /** Column name EDI_cctop_119_v_ID */
    public static final String COLUMNNAME_EDI_cctop_119_v_ID = "EDI_cctop_119_v_ID";

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
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_119_v, de.metas.esb.edi.model.I_EDI_cctop_invoic_v> COLUMN_EDI_cctop_invoic_v_ID = new org.adempiere.model.ModelColumn<I_EDI_cctop_119_v, de.metas.esb.edi.model.I_EDI_cctop_invoic_v>(I_EDI_cctop_119_v.class, "EDI_cctop_invoic_v_ID", de.metas.esb.edi.model.I_EDI_cctop_invoic_v.class);
    /** Column name EDI_cctop_invoic_v_ID */
    public static final String COLUMNNAME_EDI_cctop_invoic_v_ID = "EDI_cctop_invoic_v_ID";

	/**
	 * Set Fax.
	 * Faxnummer
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setFax (java.lang.String Fax);

	/**
	 * Get Fax.
	 * Faxnummer
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getFax();

    /** Column definition for Fax */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_119_v, Object> COLUMN_Fax = new org.adempiere.model.ModelColumn<I_EDI_cctop_119_v, Object>(I_EDI_cctop_119_v.class, "Fax", null);
    /** Column name Fax */
    public static final String COLUMNNAME_Fax = "Fax";

	/**
	 * Set GLN.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setGLN (java.lang.String GLN);

	/**
	 * Get GLN.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getGLN();

    /** Column definition for GLN */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_119_v, Object> COLUMN_GLN = new org.adempiere.model.ModelColumn<I_EDI_cctop_119_v, Object>(I_EDI_cctop_119_v.class, "GLN", null);
    /** Column name GLN */
    public static final String COLUMNNAME_GLN = "GLN";

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
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_119_v, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_EDI_cctop_119_v, Object>(I_EDI_cctop_119_v.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Shipment/ Receipt.
	 * Material Shipment Document
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_InOut_ID (int M_InOut_ID);

	/**
	 * Get Shipment/ Receipt.
	 * Material Shipment Document
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_InOut_ID();

	public org.compiere.model.I_M_InOut getM_InOut();

	public void setM_InOut(org.compiere.model.I_M_InOut M_InOut);

    /** Column definition for M_InOut_ID */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_119_v, org.compiere.model.I_M_InOut> COLUMN_M_InOut_ID = new org.adempiere.model.ModelColumn<I_EDI_cctop_119_v, org.compiere.model.I_M_InOut>(I_EDI_cctop_119_v.class, "M_InOut_ID", org.compiere.model.I_M_InOut.class);
    /** Column name M_InOut_ID */
    public static final String COLUMNNAME_M_InOut_ID = "M_InOut_ID";

	/**
	 * Set Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setName (java.lang.String Name);

	/**
	 * Get Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getName();

    /** Column definition for Name */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_119_v, Object> COLUMN_Name = new org.adempiere.model.ModelColumn<I_EDI_cctop_119_v, Object>(I_EDI_cctop_119_v.class, "Name", null);
    /** Column name Name */
    public static final String COLUMNNAME_Name = "Name";

	/**
	 * Set Name 2.
	 * Zusätzliche Bezeichnung
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setName2 (java.lang.String Name2);

	/**
	 * Get Name 2.
	 * Zusätzliche Bezeichnung
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getName2();

    /** Column definition for Name2 */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_119_v, Object> COLUMN_Name2 = new org.adempiere.model.ModelColumn<I_EDI_cctop_119_v, Object>(I_EDI_cctop_119_v.class, "Name2", null);
    /** Column name Name2 */
    public static final String COLUMNNAME_Name2 = "Name2";

	/**
	 * Set Phone.
	 * Identifies a telephone number
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPhone (java.lang.String Phone);

	/**
	 * Get Phone.
	 * Identifies a telephone number
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getPhone();

    /** Column definition for Phone */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_119_v, Object> COLUMN_Phone = new org.adempiere.model.ModelColumn<I_EDI_cctop_119_v, Object>(I_EDI_cctop_119_v.class, "Phone", null);
    /** Column name Phone */
    public static final String COLUMNNAME_Phone = "Phone";

	/**
	 * Set Postal.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPostal (java.lang.String Postal);

	/**
	 * Get Postal.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getPostal();

    /** Column definition for Postal */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_119_v, Object> COLUMN_Postal = new org.adempiere.model.ModelColumn<I_EDI_cctop_119_v, Object>(I_EDI_cctop_119_v.class, "Postal", null);
    /** Column name Postal */
    public static final String COLUMNNAME_Postal = "Postal";

	/**
	 * Set Referenznummer.
	 * Ihre Kunden- oder Lieferantennummer beim Geschäftspartner
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setReferenceNo (java.lang.String ReferenceNo);

	/**
	 * Get Referenznummer.
	 * Ihre Kunden- oder Lieferantennummer beim Geschäftspartner
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getReferenceNo();

    /** Column definition for ReferenceNo */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_119_v, Object> COLUMN_ReferenceNo = new org.adempiere.model.ModelColumn<I_EDI_cctop_119_v, Object>(I_EDI_cctop_119_v.class, "ReferenceNo", null);
    /** Column name ReferenceNo */
    public static final String COLUMNNAME_ReferenceNo = "ReferenceNo";

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
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_119_v, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_EDI_cctop_119_v, Object>(I_EDI_cctop_119_v.class, "Updated", null);
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

	/**
	 * Set Search Key.
	 * Search key for the record in the format required - must be unique
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setValue (java.lang.String Value);

	/**
	 * Get Search Key.
	 * Search key for the record in the format required - must be unique
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getValue();

    /** Column definition for Value */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_119_v, Object> COLUMN_Value = new org.adempiere.model.ModelColumn<I_EDI_cctop_119_v, Object>(I_EDI_cctop_119_v.class, "Value", null);
    /** Column name Value */
    public static final String COLUMNNAME_Value = "Value";

	/**
	 * Set VAT ID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setVATaxID (java.lang.String VATaxID);

	/**
	 * Get VAT ID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getVATaxID();

    /** Column definition for VATaxID */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_119_v, Object> COLUMN_VATaxID = new org.adempiere.model.ModelColumn<I_EDI_cctop_119_v, Object>(I_EDI_cctop_119_v.class, "VATaxID", null);
    /** Column name VATaxID */
    public static final String COLUMNNAME_VATaxID = "VATaxID";
}
