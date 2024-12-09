package de.metas.esb.edi.model;

<<<<<<< HEAD
=======
import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

/** Generated Interface for EDI_cctop_119_v
 *  @author metasfresh (generated) 
 */
<<<<<<< HEAD
@SuppressWarnings("javadoc")
public interface I_EDI_cctop_119_v 
{

    /** TableName=EDI_cctop_119_v */
    public static final String Table_Name = "EDI_cctop_119_v";

    /** AD_Table_ID=540466 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);
=======
@SuppressWarnings("unused")
public interface I_EDI_cctop_119_v 
{

	String Table_Name = "EDI_cctop_119_v";

//	/** AD_Table_ID=540466 */
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

	/**
	 * Set Adresszeile 1.
	 * Adresszeile 1 für diesen Standort
=======
	int getAD_Client_ID();

	String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Street & House No..
	 * Address line 1 for this location
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setAddress1 (java.lang.String Address1);

	/**
	 * Get Adresszeile 1.
	 * Adresszeile 1 für diesen Standort
=======
	void setAddress1 (@Nullable java.lang.String Address1);

	/**
	 * Get Street & House No..
	 * Address line 1 for this location
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public java.lang.String getAddress1();

    /** Column definition for Address1 */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_119_v, Object> COLUMN_Address1 = new org.adempiere.model.ModelColumn<I_EDI_cctop_119_v, Object>(I_EDI_cctop_119_v.class, "Address1", null);
    /** Column name Address1 */
    public static final String COLUMNNAME_Address1 = "Address1";

	/**
	 * Set Adresszeile 2.
	 * Adresszeile 2 für diesen Standort
=======
	@Nullable java.lang.String getAddress1();

	ModelColumn<I_EDI_cctop_119_v, Object> COLUMN_Address1 = new ModelColumn<>(I_EDI_cctop_119_v.class, "Address1", null);
	String COLUMNNAME_Address1 = "Address1";

	/**
	 * Set Address 2.
	 * Address line 2 for this location
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setAddress2 (java.lang.String Address2);

	/**
	 * Get Adresszeile 2.
	 * Adresszeile 2 für diesen Standort
=======
	void setAddress2 (@Nullable java.lang.String Address2);

	/**
	 * Get Address 2.
	 * Address line 2 for this location
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public java.lang.String getAddress2();

    /** Column definition for Address2 */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_119_v, Object> COLUMN_Address2 = new org.adempiere.model.ModelColumn<I_EDI_cctop_119_v, Object>(I_EDI_cctop_119_v.class, "Address2", null);
    /** Column name Address2 */
    public static final String COLUMNNAME_Address2 = "Address2";
=======
	@Nullable java.lang.String getAddress2();

	ModelColumn<I_EDI_cctop_119_v, Object> COLUMN_Address2 = new ModelColumn<>(I_EDI_cctop_119_v.class, "Address2", null);
	String COLUMNNAME_Address2 = "Address2";
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
	 * Set Location.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setC_BPartner_Location_ID (int C_BPartner_Location_ID);
=======
	void setC_BPartner_Location_ID (int C_BPartner_Location_ID);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Get Location.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public int getC_BPartner_Location_ID();

    /** Column name C_BPartner_Location_ID */
    public static final String COLUMNNAME_C_BPartner_Location_ID = "C_BPartner_Location_ID";
=======
	int getC_BPartner_Location_ID();

	String COLUMNNAME_C_BPartner_Location_ID = "C_BPartner_Location_ID";
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
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_119_v, org.compiere.model.I_C_Invoice> COLUMN_C_Invoice_ID = new org.adempiere.model.ModelColumn<I_EDI_cctop_119_v, org.compiere.model.I_C_Invoice>(I_EDI_cctop_119_v.class, "C_Invoice_ID", org.compiere.model.I_C_Invoice.class);
    /** Column name C_Invoice_ID */
    public static final String COLUMNNAME_C_Invoice_ID = "C_Invoice_ID";
=======
	int getC_Invoice_ID();

	@Nullable org.compiere.model.I_C_Invoice getC_Invoice();

	void setC_Invoice(@Nullable org.compiere.model.I_C_Invoice C_Invoice);

	ModelColumn<I_EDI_cctop_119_v, org.compiere.model.I_C_Invoice> COLUMN_C_Invoice_ID = new ModelColumn<>(I_EDI_cctop_119_v.class, "C_Invoice_ID", org.compiere.model.I_C_Invoice.class);
	String COLUMNNAME_C_Invoice_ID = "C_Invoice_ID";
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Set City Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setCity (java.lang.String City);
=======
	void setCity (@Nullable java.lang.String City);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Get City Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public java.lang.String getCity();

    /** Column definition for City */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_119_v, Object> COLUMN_City = new org.adempiere.model.ModelColumn<I_EDI_cctop_119_v, Object>(I_EDI_cctop_119_v.class, "City", null);
    /** Column name City */
    public static final String COLUMNNAME_City = "City";
=======
	@Nullable java.lang.String getCity();

	ModelColumn<I_EDI_cctop_119_v, Object> COLUMN_City = new ModelColumn<>(I_EDI_cctop_119_v.class, "City", null);
	String COLUMNNAME_City = "City";

	/**
	 * Set Contact.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setContact (@Nullable java.lang.String Contact);

	/**
	 * Get Contact.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getContact();

	ModelColumn<I_EDI_cctop_119_v, Object> COLUMN_Contact = new ModelColumn<>(I_EDI_cctop_119_v.class, "Contact", null);
	String COLUMNNAME_Contact = "Contact";
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Set ISO Country Code.
	 * Upper-case two-letter alphanumeric ISO Country code according to ISO 3166-1 - http://www.chemie.fu-berlin.de/diverse/doc/ISO_3166.html
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setCountryCode (java.lang.String CountryCode);
=======
	void setCountryCode (@Nullable java.lang.String CountryCode);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Get ISO Country Code.
	 * Upper-case two-letter alphanumeric ISO Country code according to ISO 3166-1 - http://www.chemie.fu-berlin.de/diverse/doc/ISO_3166.html
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public java.lang.String getCountryCode();

    /** Column definition for CountryCode */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_119_v, Object> COLUMN_CountryCode = new org.adempiere.model.ModelColumn<I_EDI_cctop_119_v, Object>(I_EDI_cctop_119_v.class, "CountryCode", null);
    /** Column name CountryCode */
    public static final String COLUMNNAME_CountryCode = "CountryCode";
=======
	@Nullable java.lang.String getCountryCode();

	ModelColumn<I_EDI_cctop_119_v, Object> COLUMN_CountryCode = new ModelColumn<>(I_EDI_cctop_119_v.class, "CountryCode", null);
	String COLUMNNAME_CountryCode = "CountryCode";
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
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_119_v, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_EDI_cctop_119_v, Object>(I_EDI_cctop_119_v.class, "Created", null);
    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";
=======
	java.sql.Timestamp getCreated();

	ModelColumn<I_EDI_cctop_119_v, Object> COLUMN_Created = new ModelColumn<>(I_EDI_cctop_119_v.class, "Created", null);
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
	 * Set eancom_locationtype.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void seteancom_locationtype (java.lang.String eancom_locationtype);
=======
	void seteancom_locationtype (@Nullable java.lang.String eancom_locationtype);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Get eancom_locationtype.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public java.lang.String geteancom_locationtype();

    /** Column definition for eancom_locationtype */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_119_v, Object> COLUMN_eancom_locationtype = new org.adempiere.model.ModelColumn<I_EDI_cctop_119_v, Object>(I_EDI_cctop_119_v.class, "eancom_locationtype", null);
    /** Column name eancom_locationtype */
    public static final String COLUMNNAME_eancom_locationtype = "eancom_locationtype";
=======
	@Nullable java.lang.String geteancom_locationtype();

	ModelColumn<I_EDI_cctop_119_v, Object> COLUMN_eancom_locationtype = new ModelColumn<>(I_EDI_cctop_119_v.class, "eancom_locationtype", null);
	String COLUMNNAME_eancom_locationtype = "eancom_locationtype";
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Set EDI_cctop_119_v.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setEDI_cctop_119_v_ID (int EDI_cctop_119_v_ID);
=======
	void setEDI_cctop_119_v_ID (int EDI_cctop_119_v_ID);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Get EDI_cctop_119_v.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public int getEDI_cctop_119_v_ID();

    /** Column definition for EDI_cctop_119_v_ID */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_119_v, Object> COLUMN_EDI_cctop_119_v_ID = new org.adempiere.model.ModelColumn<I_EDI_cctop_119_v, Object>(I_EDI_cctop_119_v.class, "EDI_cctop_119_v_ID", null);
    /** Column name EDI_cctop_119_v_ID */
    public static final String COLUMNNAME_EDI_cctop_119_v_ID = "EDI_cctop_119_v_ID";
=======
	int getEDI_cctop_119_v_ID();

	ModelColumn<I_EDI_cctop_119_v, Object> COLUMN_EDI_cctop_119_v_ID = new ModelColumn<>(I_EDI_cctop_119_v.class, "EDI_cctop_119_v_ID", null);
	String COLUMNNAME_EDI_cctop_119_v_ID = "EDI_cctop_119_v_ID";
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
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_119_v, de.metas.esb.edi.model.I_EDI_cctop_invoic_v> COLUMN_EDI_cctop_invoic_v_ID = new org.adempiere.model.ModelColumn<I_EDI_cctop_119_v, de.metas.esb.edi.model.I_EDI_cctop_invoic_v>(I_EDI_cctop_119_v.class, "EDI_cctop_invoic_v_ID", de.metas.esb.edi.model.I_EDI_cctop_invoic_v.class);
    /** Column name EDI_cctop_invoic_v_ID */
    public static final String COLUMNNAME_EDI_cctop_invoic_v_ID = "EDI_cctop_invoic_v_ID";

	/**
	 * Set Fax.
	 * Faxnummer
=======
	int getEDI_cctop_invoic_v_ID();

	@Nullable de.metas.esb.edi.model.I_EDI_cctop_invoic_v getEDI_cctop_invoic_v();

	void setEDI_cctop_invoic_v(@Nullable de.metas.esb.edi.model.I_EDI_cctop_invoic_v EDI_cctop_invoic_v);

	ModelColumn<I_EDI_cctop_119_v, de.metas.esb.edi.model.I_EDI_cctop_invoic_v> COLUMN_EDI_cctop_invoic_v_ID = new ModelColumn<>(I_EDI_cctop_119_v.class, "EDI_cctop_invoic_v_ID", de.metas.esb.edi.model.I_EDI_cctop_invoic_v.class);
	String COLUMNNAME_EDI_cctop_invoic_v_ID = "EDI_cctop_invoic_v_ID";

	/**
	 * Set Fax.
	 * Facsimile number
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setFax (java.lang.String Fax);

	/**
	 * Get Fax.
	 * Faxnummer
=======
	void setFax (@Nullable java.lang.String Fax);

	/**
	 * Get Fax.
	 * Facsimile number
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public java.lang.String getFax();

    /** Column definition for Fax */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_119_v, Object> COLUMN_Fax = new org.adempiere.model.ModelColumn<I_EDI_cctop_119_v, Object>(I_EDI_cctop_119_v.class, "Fax", null);
    /** Column name Fax */
    public static final String COLUMNNAME_Fax = "Fax";
=======
	@Nullable java.lang.String getFax();

	ModelColumn<I_EDI_cctop_119_v, Object> COLUMN_Fax = new ModelColumn<>(I_EDI_cctop_119_v.class, "Fax", null);
	String COLUMNNAME_Fax = "Fax";
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Set GLN.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setGLN (java.lang.String GLN);
=======
	void setGLN (@Nullable java.lang.String GLN);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Get GLN.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public java.lang.String getGLN();

    /** Column definition for GLN */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_119_v, Object> COLUMN_GLN = new org.adempiere.model.ModelColumn<I_EDI_cctop_119_v, Object>(I_EDI_cctop_119_v.class, "GLN", null);
    /** Column name GLN */
    public static final String COLUMNNAME_GLN = "GLN";
=======
	@Nullable java.lang.String getGLN();

	ModelColumn<I_EDI_cctop_119_v, Object> COLUMN_GLN = new ModelColumn<>(I_EDI_cctop_119_v.class, "GLN", null);
	String COLUMNNAME_GLN = "GLN";
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
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_119_v, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_EDI_cctop_119_v, Object>(I_EDI_cctop_119_v.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";
=======
	boolean isActive();

	ModelColumn<I_EDI_cctop_119_v, Object> COLUMN_IsActive = new ModelColumn<>(I_EDI_cctop_119_v.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Set Shipment/ Receipt.
	 * Material Shipment Document
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setM_InOut_ID (int M_InOut_ID);
=======
	void setM_InOut_ID (int M_InOut_ID);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Get Shipment/ Receipt.
	 * Material Shipment Document
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public int getM_InOut_ID();

	public org.compiere.model.I_M_InOut getM_InOut();

	public void setM_InOut(org.compiere.model.I_M_InOut M_InOut);

    /** Column definition for M_InOut_ID */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_119_v, org.compiere.model.I_M_InOut> COLUMN_M_InOut_ID = new org.adempiere.model.ModelColumn<I_EDI_cctop_119_v, org.compiere.model.I_M_InOut>(I_EDI_cctop_119_v.class, "M_InOut_ID", org.compiere.model.I_M_InOut.class);
    /** Column name M_InOut_ID */
    public static final String COLUMNNAME_M_InOut_ID = "M_InOut_ID";
=======
	int getM_InOut_ID();

	@Nullable org.compiere.model.I_M_InOut getM_InOut();

	void setM_InOut(@Nullable org.compiere.model.I_M_InOut M_InOut);

	ModelColumn<I_EDI_cctop_119_v, org.compiere.model.I_M_InOut> COLUMN_M_InOut_ID = new ModelColumn<>(I_EDI_cctop_119_v.class, "M_InOut_ID", org.compiere.model.I_M_InOut.class);
	String COLUMNNAME_M_InOut_ID = "M_InOut_ID";
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Set Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setName (java.lang.String Name);
=======
	void setName (@Nullable java.lang.String Name);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Get Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public java.lang.String getName();

    /** Column definition for Name */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_119_v, Object> COLUMN_Name = new org.adempiere.model.ModelColumn<I_EDI_cctop_119_v, Object>(I_EDI_cctop_119_v.class, "Name", null);
    /** Column name Name */
    public static final String COLUMNNAME_Name = "Name";

	/**
	 * Set Name 2.
	 * Zusätzliche Bezeichnung
=======
	@Nullable java.lang.String getName();

	ModelColumn<I_EDI_cctop_119_v, Object> COLUMN_Name = new ModelColumn<>(I_EDI_cctop_119_v.class, "Name", null);
	String COLUMNNAME_Name = "Name";

	/**
	 * Set Name 2.
	 * Additional Name
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setName2 (java.lang.String Name2);

	/**
	 * Get Name 2.
	 * Zusätzliche Bezeichnung
=======
	void setName2 (@Nullable java.lang.String Name2);

	/**
	 * Get Name 2.
	 * Additional Name
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public java.lang.String getName2();

    /** Column definition for Name2 */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_119_v, Object> COLUMN_Name2 = new org.adempiere.model.ModelColumn<I_EDI_cctop_119_v, Object>(I_EDI_cctop_119_v.class, "Name2", null);
    /** Column name Name2 */
    public static final String COLUMNNAME_Name2 = "Name2";
=======
	@Nullable java.lang.String getName2();

	ModelColumn<I_EDI_cctop_119_v, Object> COLUMN_Name2 = new ModelColumn<>(I_EDI_cctop_119_v.class, "Name2", null);
	String COLUMNNAME_Name2 = "Name2";
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Set Phone.
	 * Identifies a telephone number
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setPhone (java.lang.String Phone);
=======
	void setPhone (@Nullable java.lang.String Phone);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Get Phone.
	 * Identifies a telephone number
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public java.lang.String getPhone();

    /** Column definition for Phone */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_119_v, Object> COLUMN_Phone = new org.adempiere.model.ModelColumn<I_EDI_cctop_119_v, Object>(I_EDI_cctop_119_v.class, "Phone", null);
    /** Column name Phone */
    public static final String COLUMNNAME_Phone = "Phone";
=======
	@Nullable java.lang.String getPhone();

	ModelColumn<I_EDI_cctop_119_v, Object> COLUMN_Phone = new ModelColumn<>(I_EDI_cctop_119_v.class, "Phone", null);
	String COLUMNNAME_Phone = "Phone";
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Set Postal.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setPostal (java.lang.String Postal);
=======
	void setPostal (@Nullable java.lang.String Postal);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Get Postal.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public java.lang.String getPostal();

    /** Column definition for Postal */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_119_v, Object> COLUMN_Postal = new org.adempiere.model.ModelColumn<I_EDI_cctop_119_v, Object>(I_EDI_cctop_119_v.class, "Postal", null);
    /** Column name Postal */
    public static final String COLUMNNAME_Postal = "Postal";

	/**
	 * Set Referenznummer.
	 * Ihre Kunden- oder Lieferantennummer beim Geschäftspartner
=======
	@Nullable java.lang.String getPostal();

	ModelColumn<I_EDI_cctop_119_v, Object> COLUMN_Postal = new ModelColumn<>(I_EDI_cctop_119_v.class, "Postal", null);
	String COLUMNNAME_Postal = "Postal";

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
	public void setReferenceNo (java.lang.String ReferenceNo);

	/**
	 * Get Referenznummer.
	 * Ihre Kunden- oder Lieferantennummer beim Geschäftspartner
=======
	void setReferenceNo (@Nullable java.lang.String ReferenceNo);

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
	public java.lang.String getReferenceNo();

    /** Column definition for ReferenceNo */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_119_v, Object> COLUMN_ReferenceNo = new org.adempiere.model.ModelColumn<I_EDI_cctop_119_v, Object>(I_EDI_cctop_119_v.class, "ReferenceNo", null);
    /** Column name ReferenceNo */
    public static final String COLUMNNAME_ReferenceNo = "ReferenceNo";
=======
	@Nullable java.lang.String getReferenceNo();

	ModelColumn<I_EDI_cctop_119_v, Object> COLUMN_ReferenceNo = new ModelColumn<>(I_EDI_cctop_119_v.class, "ReferenceNo", null);
	String COLUMNNAME_ReferenceNo = "ReferenceNo";

	/**
	 * Set Setup Place No..
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setSetup_Place_No (@Nullable java.lang.String Setup_Place_No);

	/**
	 * Get Setup Place No..
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getSetup_Place_No();

	ModelColumn<I_EDI_cctop_119_v, Object> COLUMN_Setup_Place_No = new ModelColumn<>(I_EDI_cctop_119_v.class, "Setup_Place_No", null);
	String COLUMNNAME_Setup_Place_No = "Setup_Place_No";

	/**
	 * Set Site name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setSiteName (@Nullable java.lang.String SiteName);

	/**
	 * Get Site name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getSiteName();

	ModelColumn<I_EDI_cctop_119_v, Object> COLUMN_SiteName = new ModelColumn<>(I_EDI_cctop_119_v.class, "SiteName", null);
	String COLUMNNAME_SiteName = "SiteName";
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
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_119_v, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_EDI_cctop_119_v, Object>(I_EDI_cctop_119_v.class, "Updated", null);
    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";
=======
	java.sql.Timestamp getUpdated();

	ModelColumn<I_EDI_cctop_119_v, Object> COLUMN_Updated = new ModelColumn<>(I_EDI_cctop_119_v.class, "Updated", null);
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

	/**
	 * Set Search Key.
	 * Search key for the record in the format required - must be unique
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setValue (java.lang.String Value);
=======
	void setValue (@Nullable java.lang.String Value);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Get Search Key.
	 * Search key for the record in the format required - must be unique
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public java.lang.String getValue();

    /** Column definition for Value */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_119_v, Object> COLUMN_Value = new org.adempiere.model.ModelColumn<I_EDI_cctop_119_v, Object>(I_EDI_cctop_119_v.class, "Value", null);
    /** Column name Value */
    public static final String COLUMNNAME_Value = "Value";
=======
	@Nullable java.lang.String getValue();

	ModelColumn<I_EDI_cctop_119_v, Object> COLUMN_Value = new ModelColumn<>(I_EDI_cctop_119_v.class, "Value", null);
	String COLUMNNAME_Value = "Value";
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Set VAT ID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setVATaxID (java.lang.String VATaxID);
=======
	void setVATaxID (@Nullable java.lang.String VATaxID);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Get VAT ID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public java.lang.String getVATaxID();

    /** Column definition for VATaxID */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_119_v, Object> COLUMN_VATaxID = new org.adempiere.model.ModelColumn<I_EDI_cctop_119_v, Object>(I_EDI_cctop_119_v.class, "VATaxID", null);
    /** Column name VATaxID */
    public static final String COLUMNNAME_VATaxID = "VATaxID";
=======
	@Nullable java.lang.String getVATaxID();

	ModelColumn<I_EDI_cctop_119_v, Object> COLUMN_VATaxID = new ModelColumn<>(I_EDI_cctop_119_v.class, "VATaxID", null);
	String COLUMNNAME_VATaxID = "VATaxID";
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
}
