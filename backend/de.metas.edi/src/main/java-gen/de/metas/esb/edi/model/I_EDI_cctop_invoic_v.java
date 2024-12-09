package de.metas.esb.edi.model;

<<<<<<< HEAD
=======
import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;
import java.math.BigDecimal;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

/** Generated Interface for EDI_cctop_invoic_v
 *  @author metasfresh (generated) 
 */
<<<<<<< HEAD
@SuppressWarnings("javadoc")
public interface I_EDI_cctop_invoic_v 
{

    /** TableName=EDI_cctop_invoic_v */
    public static final String Table_Name = "EDI_cctop_invoic_v";

    /** AD_Table_ID=540462 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);
=======
@SuppressWarnings("unused")
public interface I_EDI_cctop_invoic_v 
{

	String Table_Name = "EDI_cctop_invoic_v";

//	/** AD_Table_ID=540462 */
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
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_v, org.compiere.model.I_C_Invoice> COLUMN_C_Invoice_ID = new org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_v, org.compiere.model.I_C_Invoice>(I_EDI_cctop_invoic_v.class, "C_Invoice_ID", org.compiere.model.I_C_Invoice.class);
    /** Column name C_Invoice_ID */
    public static final String COLUMNNAME_C_Invoice_ID = "C_Invoice_ID";
=======
	int getC_Invoice_ID();

	@Nullable org.compiere.model.I_C_Invoice getC_Invoice();

	void setC_Invoice(@Nullable org.compiere.model.I_C_Invoice C_Invoice);

	ModelColumn<I_EDI_cctop_invoic_v, org.compiere.model.I_C_Invoice> COLUMN_C_Invoice_ID = new ModelColumn<>(I_EDI_cctop_invoic_v.class, "C_Invoice_ID", org.compiere.model.I_C_Invoice.class);
	String COLUMNNAME_C_Invoice_ID = "C_Invoice_ID";
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Set Sales order.
	 * Order
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setC_Order_ID (int C_Order_ID);
=======
	void setC_Order_ID (int C_Order_ID);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Get Sales order.
	 * Order
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public int getC_Order_ID();

	public org.compiere.model.I_C_Order getC_Order();

	public void setC_Order(org.compiere.model.I_C_Order C_Order);

    /** Column definition for C_Order_ID */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_v, org.compiere.model.I_C_Order> COLUMN_C_Order_ID = new org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_v, org.compiere.model.I_C_Order>(I_EDI_cctop_invoic_v.class, "C_Order_ID", org.compiere.model.I_C_Order.class);
    /** Column name C_Order_ID */
    public static final String COLUMNNAME_C_Order_ID = "C_Order_ID";
=======
	int getC_Order_ID();

	@Nullable org.compiere.model.I_C_Order getC_Order();

	void setC_Order(@Nullable org.compiere.model.I_C_Order C_Order);

	ModelColumn<I_EDI_cctop_invoic_v, org.compiere.model.I_C_Order> COLUMN_C_Order_ID = new ModelColumn<>(I_EDI_cctop_invoic_v.class, "C_Order_ID", org.compiere.model.I_C_Order.class);
	String COLUMNNAME_C_Order_ID = "C_Order_ID";
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
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_v, Object> COLUMN_CountryCode = new org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_v, Object>(I_EDI_cctop_invoic_v.class, "CountryCode", null);
    /** Column name CountryCode */
    public static final String COLUMNNAME_CountryCode = "CountryCode";
=======
	@Nullable java.lang.String getCountryCode();

	ModelColumn<I_EDI_cctop_invoic_v, Object> COLUMN_CountryCode = new ModelColumn<>(I_EDI_cctop_invoic_v.class, "CountryCode", null);
	String COLUMNNAME_CountryCode = "CountryCode";
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Set ISO Ländercode 3 Stelliger.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setCountryCode_3digit (java.lang.String CountryCode_3digit);
=======
	void setCountryCode_3digit (@Nullable java.lang.String CountryCode_3digit);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Get ISO Ländercode 3 Stelliger.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public java.lang.String getCountryCode_3digit();

    /** Column definition for CountryCode_3digit */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_v, Object> COLUMN_CountryCode_3digit = new org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_v, Object>(I_EDI_cctop_invoic_v.class, "CountryCode_3digit", null);
    /** Column name CountryCode_3digit */
    public static final String COLUMNNAME_CountryCode_3digit = "CountryCode_3digit";
=======
	@Nullable java.lang.String getCountryCode_3digit();

	ModelColumn<I_EDI_cctop_invoic_v, Object> COLUMN_CountryCode_3digit = new ModelColumn<>(I_EDI_cctop_invoic_v.class, "CountryCode_3digit", null);
	String COLUMNNAME_CountryCode_3digit = "CountryCode_3digit";
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
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_v, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_v, Object>(I_EDI_cctop_invoic_v.class, "Created", null);
    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";
=======
	java.sql.Timestamp getCreated();

	ModelColumn<I_EDI_cctop_invoic_v, Object> COLUMN_Created = new ModelColumn<>(I_EDI_cctop_invoic_v.class, "Created", null);
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

	/**
	 * Set Gutschrift Grund.
=======
	int getCreatedBy();

	String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Credit Reason.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setCreditMemoReason (java.lang.String CreditMemoReason);

	/**
	 * Get Gutschrift Grund.
=======
	void setCreditMemoReason (@Nullable java.lang.String CreditMemoReason);

	/**
	 * Get Credit Reason.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public java.lang.String getCreditMemoReason();

    /** Column definition for CreditMemoReason */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_v, Object> COLUMN_CreditMemoReason = new org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_v, Object>(I_EDI_cctop_invoic_v.class, "CreditMemoReason", null);
    /** Column name CreditMemoReason */
    public static final String COLUMNNAME_CreditMemoReason = "CreditMemoReason";
=======
	@Nullable java.lang.String getCreditMemoReason();

	ModelColumn<I_EDI_cctop_invoic_v, Object> COLUMN_CreditMemoReason = new ModelColumn<>(I_EDI_cctop_invoic_v.class, "CreditMemoReason", null);
	String COLUMNNAME_CreditMemoReason = "CreditMemoReason";
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Set CreditMemoReasonText.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setCreditMemoReasonText (java.lang.String CreditMemoReasonText);
=======
	void setCreditMemoReasonText (@Nullable java.lang.String CreditMemoReasonText);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Get CreditMemoReasonText.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public java.lang.String getCreditMemoReasonText();

    /** Column definition for CreditMemoReasonText */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_v, Object> COLUMN_CreditMemoReasonText = new org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_v, Object>(I_EDI_cctop_invoic_v.class, "CreditMemoReasonText", null);
    /** Column name CreditMemoReasonText */
    public static final String COLUMNNAME_CreditMemoReasonText = "CreditMemoReasonText";
=======
	@Nullable java.lang.String getCreditMemoReasonText();

	ModelColumn<I_EDI_cctop_invoic_v, Object> COLUMN_CreditMemoReasonText = new ModelColumn<>(I_EDI_cctop_invoic_v.class, "CreditMemoReasonText", null);
	String COLUMNNAME_CreditMemoReasonText = "CreditMemoReasonText";

	/**
	 * Set Accounting Date.
	 * Accounting Date
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDateAcct (@Nullable java.sql.Timestamp DateAcct);

	/**
	 * Get Accounting Date.
	 * Accounting Date
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getDateAcct();

	ModelColumn<I_EDI_cctop_invoic_v, Object> COLUMN_DateAcct = new ModelColumn<>(I_EDI_cctop_invoic_v.class, "DateAcct", null);
	String COLUMNNAME_DateAcct = "DateAcct";
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Set Date.
	 * Date printed on Invoice
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setDateInvoiced (java.sql.Timestamp DateInvoiced);
=======
	void setDateInvoiced (@Nullable java.sql.Timestamp DateInvoiced);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Get Date.
	 * Date printed on Invoice
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public java.sql.Timestamp getDateInvoiced();

    /** Column definition for DateInvoiced */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_v, Object> COLUMN_DateInvoiced = new org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_v, Object>(I_EDI_cctop_invoic_v.class, "DateInvoiced", null);
    /** Column name DateInvoiced */
    public static final String COLUMNNAME_DateInvoiced = "DateInvoiced";
=======
	@Nullable java.sql.Timestamp getDateInvoiced();

	ModelColumn<I_EDI_cctop_invoic_v, Object> COLUMN_DateInvoiced = new ModelColumn<>(I_EDI_cctop_invoic_v.class, "DateInvoiced", null);
	String COLUMNNAME_DateInvoiced = "DateInvoiced";
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Set Date.
	 * Date of Order
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setDateOrdered (java.sql.Timestamp DateOrdered);
=======
	void setDateOrdered (@Nullable java.sql.Timestamp DateOrdered);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Get Date.
	 * Date of Order
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public java.sql.Timestamp getDateOrdered();

    /** Column definition for DateOrdered */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_v, Object> COLUMN_DateOrdered = new org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_v, Object>(I_EDI_cctop_invoic_v.class, "DateOrdered", null);
    /** Column name DateOrdered */
    public static final String COLUMNNAME_DateOrdered = "DateOrdered";
=======
	@Nullable java.sql.Timestamp getDateOrdered();

	ModelColumn<I_EDI_cctop_invoic_v, Object> COLUMN_DateOrdered = new ModelColumn<>(I_EDI_cctop_invoic_v.class, "DateOrdered", null);
	String COLUMNNAME_DateOrdered = "DateOrdered";
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Set eancom_doctype.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void seteancom_doctype (java.lang.String eancom_doctype);
=======
	void seteancom_doctype (@Nullable java.lang.String eancom_doctype);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Get eancom_doctype.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public java.lang.String geteancom_doctype();

    /** Column definition for eancom_doctype */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_v, Object> COLUMN_eancom_doctype = new org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_v, Object>(I_EDI_cctop_invoic_v.class, "eancom_doctype", null);
    /** Column name eancom_doctype */
    public static final String COLUMNNAME_eancom_doctype = "eancom_doctype";
=======
	@Nullable java.lang.String geteancom_doctype();

	ModelColumn<I_EDI_cctop_invoic_v, Object> COLUMN_eancom_doctype = new ModelColumn<>(I_EDI_cctop_invoic_v.class, "eancom_doctype", null);
	String COLUMNNAME_eancom_doctype = "eancom_doctype";
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Set EDI_cctop_invoic_v.
	 *
	 * <br>Type: ID
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
	 * <br>Type: ID
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public int getEDI_cctop_invoic_v_ID();

    /** Column definition for EDI_cctop_invoic_v_ID */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_v, Object> COLUMN_EDI_cctop_invoic_v_ID = new org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_v, Object>(I_EDI_cctop_invoic_v.class, "EDI_cctop_invoic_v_ID", null);
    /** Column name EDI_cctop_invoic_v_ID */
    public static final String COLUMNNAME_EDI_cctop_invoic_v_ID = "EDI_cctop_invoic_v_ID";
=======
	int getEDI_cctop_invoic_v_ID();

	ModelColumn<I_EDI_cctop_invoic_v, Object> COLUMN_EDI_cctop_invoic_v_ID = new ModelColumn<>(I_EDI_cctop_invoic_v.class, "EDI_cctop_invoic_v_ID", null);
	String COLUMNNAME_EDI_cctop_invoic_v_ID = "EDI_cctop_invoic_v_ID";
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Set EDI Desadv-Nr..
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setEDIDesadvDocumentNo (String EDIDesadvDocumentNo);
=======
	void setEDIDesadvDocumentNo (@Nullable java.lang.String EDIDesadvDocumentNo);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Get EDI Desadv-Nr..
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public String getEDIDesadvDocumentNo();

	/** Column definition for EDIDesadvDocumentNo */
	public static final org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_v, Object> COLUMN_EDIDesadvDocumentNo = new org.adempiere.model.ModelColumn<>(I_EDI_cctop_invoic_v.class, "EDIDesadvDocumentNo", null);
	/** Column name EDIDesadvDocumentNo */
	public static final String COLUMNNAME_EDIDesadvDocumentNo = "EDIDesadvDocumentNo";

	/**
	 * Set Summe Gesamt.
	 * Summe über Alles zu diesem Beleg
=======
	@Nullable java.lang.String getEDIDesadvDocumentNo();

	ModelColumn<I_EDI_cctop_invoic_v, Object> COLUMN_EDIDesadvDocumentNo = new ModelColumn<>(I_EDI_cctop_invoic_v.class, "EDIDesadvDocumentNo", null);
	String COLUMNNAME_EDIDesadvDocumentNo = "EDIDesadvDocumentNo";

	/**
	 * Set Grand Total.
	 * Total amount of document
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setGrandTotal (java.math.BigDecimal GrandTotal);

	/**
	 * Get Summe Gesamt.
	 * Summe über Alles zu diesem Beleg
=======
	void setGrandTotal (@Nullable BigDecimal GrandTotal);

	/**
	 * Get Grand Total.
	 * Total amount of document
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public java.math.BigDecimal getGrandTotal();

    /** Column definition for GrandTotal */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_v, Object> COLUMN_GrandTotal = new org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_v, Object>(I_EDI_cctop_invoic_v.class, "GrandTotal", null);
    /** Column name GrandTotal */
    public static final String COLUMNNAME_GrandTotal = "GrandTotal";
=======
	BigDecimal getGrandTotal();

	ModelColumn<I_EDI_cctop_invoic_v, Object> COLUMN_GrandTotal = new ModelColumn<>(I_EDI_cctop_invoic_v.class, "GrandTotal", null);
	String COLUMNNAME_GrandTotal = "GrandTotal";

	/**
	 * Set GrandTotalWithSurchargeAmt.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setGrandTotalWithSurchargeAmt (BigDecimal GrandTotalWithSurchargeAmt);

	/**
	 * Get GrandTotalWithSurchargeAmt.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getGrandTotalWithSurchargeAmt();

	ModelColumn<I_EDI_cctop_invoic_v, Object> COLUMN_GrandTotalWithSurchargeAmt = new ModelColumn<>(I_EDI_cctop_invoic_v.class, "GrandTotalWithSurchargeAmt", null);
	String COLUMNNAME_GrandTotalWithSurchargeAmt = "GrandTotalWithSurchargeAmt";

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

	ModelColumn<I_EDI_cctop_invoic_v, Object> COLUMN_InvoicableQtyBasedOn = new ModelColumn<>(I_EDI_cctop_invoic_v.class, "InvoicableQtyBasedOn", null);
	String COLUMNNAME_InvoicableQtyBasedOn = "InvoicableQtyBasedOn";
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Set invoice_documentno.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setinvoice_documentno (java.lang.String invoice_documentno);
=======
	void setinvoice_documentno (@Nullable java.lang.String invoice_documentno);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Get invoice_documentno.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public java.lang.String getinvoice_documentno();

    /** Column definition for invoice_documentno */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_v, Object> COLUMN_invoice_documentno = new org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_v, Object>(I_EDI_cctop_invoic_v.class, "invoice_documentno", null);
    /** Column name invoice_documentno */
    public static final String COLUMNNAME_invoice_documentno = "invoice_documentno";
=======
	@Nullable java.lang.String getinvoice_documentno();

	ModelColumn<I_EDI_cctop_invoic_v, Object> COLUMN_invoice_documentno = new ModelColumn<>(I_EDI_cctop_invoic_v.class, "invoice_documentno", null);
	String COLUMNNAME_invoice_documentno = "invoice_documentno";
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
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_v, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_v, Object>(I_EDI_cctop_invoic_v.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set ISO Währungscode.
	 * Dreibuchstabiger ISO 4217 Code für die Währung
=======
	boolean isActive();

	ModelColumn<I_EDI_cctop_invoic_v, Object> COLUMN_IsActive = new ModelColumn<>(I_EDI_cctop_invoic_v.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set ISO Currency Code.
	 * Three letter ISO 4217 Code of the Currency
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setISO_Code (java.lang.String ISO_Code);

	/**
	 * Get ISO Währungscode.
	 * Dreibuchstabiger ISO 4217 Code für die Währung
=======
	void setISO_Code (@Nullable java.lang.String ISO_Code);

	/**
	 * Get ISO Currency Code.
	 * Three letter ISO 4217 Code of the Currency
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public java.lang.String getISO_Code();

    /** Column definition for ISO_Code */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_v, Object> COLUMN_ISO_Code = new org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_v, Object>(I_EDI_cctop_invoic_v.class, "ISO_Code", null);
    /** Column name ISO_Code */
    public static final String COLUMNNAME_ISO_Code = "ISO_Code";

	/**
	 * Set Bewegungs-Datum.
	 * Datum, an dem eine Produkt in oder aus dem Bestand bewegt wurde
=======
	@Nullable java.lang.String getISO_Code();

	ModelColumn<I_EDI_cctop_invoic_v, Object> COLUMN_ISO_Code = new ModelColumn<>(I_EDI_cctop_invoic_v.class, "ISO_Code", null);
	String COLUMNNAME_ISO_Code = "ISO_Code";

	/**
	 * Set Date.
	 * Date a product was moved in or out of inventory
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setMovementDate (java.sql.Timestamp MovementDate);

	/**
	 * Get Bewegungs-Datum.
	 * Datum, an dem eine Produkt in oder aus dem Bestand bewegt wurde
=======
	void setMovementDate (@Nullable java.sql.Timestamp MovementDate);

	/**
	 * Get Date.
	 * Date a product was moved in or out of inventory
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public java.sql.Timestamp getMovementDate();

    /** Column definition for MovementDate */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_v, Object> COLUMN_MovementDate = new org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_v, Object>(I_EDI_cctop_invoic_v.class, "MovementDate", null);
    /** Column name MovementDate */
    public static final String COLUMNNAME_MovementDate = "MovementDate";
=======
	@Nullable java.sql.Timestamp getMovementDate();

	ModelColumn<I_EDI_cctop_invoic_v, Object> COLUMN_MovementDate = new ModelColumn<>(I_EDI_cctop_invoic_v.class, "MovementDate", null);
	String COLUMNNAME_MovementDate = "MovementDate";
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Set Order Reference.
	 * Transaction Reference Number (Sales Order, Purchase Order) of your Business Partner
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setPOReference (java.lang.String POReference);
=======
	void setPOReference (@Nullable java.lang.String POReference);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Get Order Reference.
	 * Transaction Reference Number (Sales Order, Purchase Order) of your Business Partner
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public java.lang.String getPOReference();

    /** Column definition for POReference */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_v, Object> COLUMN_POReference = new org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_v, Object>(I_EDI_cctop_invoic_v.class, "POReference", null);
    /** Column name POReference */
    public static final String COLUMNNAME_POReference = "POReference";
=======
	@Nullable java.lang.String getPOReference();

	ModelColumn<I_EDI_cctop_invoic_v, Object> COLUMN_POReference = new ModelColumn<>(I_EDI_cctop_invoic_v.class, "POReference", null);
	String COLUMNNAME_POReference = "POReference";
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Set receivergln.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setreceivergln (java.lang.String receivergln);
=======
	void setReceiverGLN (@Nullable java.lang.String ReceiverGLN);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Get receivergln.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public java.lang.String getreceivergln();

    /** Column definition for receivergln */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_v, Object> COLUMN_receivergln = new org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_v, Object>(I_EDI_cctop_invoic_v.class, "receivergln", null);
    /** Column name receivergln */
    public static final String COLUMNNAME_receivergln = "receivergln";
=======
	@Nullable java.lang.String getReceiverGLN();

	ModelColumn<I_EDI_cctop_invoic_v, Object> COLUMN_ReceiverGLN = new ModelColumn<>(I_EDI_cctop_invoic_v.class, "ReceiverGLN", null);
	String COLUMNNAME_ReceiverGLN = "ReceiverGLN";
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Set sendergln.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setsendergln (java.lang.String sendergln);
=======
	void setSenderGLN (@Nullable java.lang.String SenderGLN);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Get sendergln.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public java.lang.String getsendergln();

    /** Column definition for sendergln */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_v, Object> COLUMN_sendergln = new org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_v, Object>(I_EDI_cctop_invoic_v.class, "sendergln", null);
    /** Column name sendergln */
    public static final String COLUMNNAME_sendergln = "sendergln";
=======
	@Nullable java.lang.String getSenderGLN();

	ModelColumn<I_EDI_cctop_invoic_v, Object> COLUMN_SenderGLN = new ModelColumn<>(I_EDI_cctop_invoic_v.class, "SenderGLN", null);
	String COLUMNNAME_SenderGLN = "SenderGLN";
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Set shipment_documentno.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setshipment_documentno (java.lang.String shipment_documentno);
=======
	void setShipment_DocumentNo (@Nullable java.lang.String Shipment_DocumentNo);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Get shipment_documentno.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public java.lang.String getshipment_documentno();

    /** Column definition for shipment_documentno */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_v, Object> COLUMN_shipment_documentno = new org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_v, Object>(I_EDI_cctop_invoic_v.class, "shipment_documentno", null);
    /** Column name shipment_documentno */
    public static final String COLUMNNAME_shipment_documentno = "shipment_documentno";

	/**
	 * Set Summe Zeilen.
	 * Summe aller Zeilen zu diesem Beleg
=======
	@Nullable java.lang.String getShipment_DocumentNo();

	ModelColumn<I_EDI_cctop_invoic_v, Object> COLUMN_Shipment_DocumentNo = new ModelColumn<>(I_EDI_cctop_invoic_v.class, "Shipment_DocumentNo", null);
	String COLUMNNAME_Shipment_DocumentNo = "Shipment_DocumentNo";

	/**
	 * Set SurchargeAmt.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setTotalLines (java.math.BigDecimal TotalLines);

	/**
	 * Get Summe Zeilen.
	 * Summe aller Zeilen zu diesem Beleg
=======
	void setSurchargeAmt (@Nullable BigDecimal SurchargeAmt);

	/**
	 * Get SurchargeAmt.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public java.math.BigDecimal getTotalLines();

    /** Column definition for TotalLines */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_v, Object> COLUMN_TotalLines = new org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_v, Object>(I_EDI_cctop_invoic_v.class, "TotalLines", null);
    /** Column name TotalLines */
    public static final String COLUMNNAME_TotalLines = "TotalLines";
=======
	BigDecimal getSurchargeAmt();

	ModelColumn<I_EDI_cctop_invoic_v, Object> COLUMN_SurchargeAmt = new ModelColumn<>(I_EDI_cctop_invoic_v.class, "SurchargeAmt", null);
	String COLUMNNAME_SurchargeAmt = "SurchargeAmt";

	/**
	 * Set Total Lines.
	 * Total of all document lines
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setTotalLines (@Nullable BigDecimal TotalLines);

	/**
	 * Get Total Lines.
	 * Total of all document lines
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getTotalLines();

	ModelColumn<I_EDI_cctop_invoic_v, Object> COLUMN_TotalLines = new ModelColumn<>(I_EDI_cctop_invoic_v.class, "TotalLines", null);
	String COLUMNNAME_TotalLines = "TotalLines";

	/**
	 * Set TotalLinesWithSurchargeAmt.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setTotalLinesWithSurchargeAmt (@Nullable BigDecimal TotalLinesWithSurchargeAmt);

	/**
	 * Get TotalLinesWithSurchargeAmt.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getTotalLinesWithSurchargeAmt();

	ModelColumn<I_EDI_cctop_invoic_v, Object> COLUMN_TotalLinesWithSurchargeAmt = new ModelColumn<>(I_EDI_cctop_invoic_v.class, "TotalLinesWithSurchargeAmt", null);
	String COLUMNNAME_TotalLinesWithSurchargeAmt = "TotalLinesWithSurchargeAmt";
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Set totaltaxbaseamt.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void settotaltaxbaseamt (java.math.BigDecimal totaltaxbaseamt);
=======
	void setTotalTaxBaseAmt (@Nullable BigDecimal TotalTaxBaseAmt);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Get totaltaxbaseamt.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public java.math.BigDecimal gettotaltaxbaseamt();

    /** Column definition for totaltaxbaseamt */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_v, Object> COLUMN_totaltaxbaseamt = new org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_v, Object>(I_EDI_cctop_invoic_v.class, "totaltaxbaseamt", null);
    /** Column name totaltaxbaseamt */
    public static final String COLUMNNAME_totaltaxbaseamt = "totaltaxbaseamt";
=======
	BigDecimal getTotalTaxBaseAmt();

	ModelColumn<I_EDI_cctop_invoic_v, Object> COLUMN_TotalTaxBaseAmt = new ModelColumn<>(I_EDI_cctop_invoic_v.class, "TotalTaxBaseAmt", null);
	String COLUMNNAME_TotalTaxBaseAmt = "TotalTaxBaseAmt";
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Set totalvat.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void settotalvat (java.math.BigDecimal totalvat);
=======
	void setTotalVat (@Nullable BigDecimal TotalVat);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Get totalvat.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public java.math.BigDecimal gettotalvat();

    /** Column definition for totalvat */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_v, Object> COLUMN_totalvat = new org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_v, Object>(I_EDI_cctop_invoic_v.class, "totalvat", null);
    /** Column name totalvat */
    public static final String COLUMNNAME_totalvat = "totalvat";
=======
	BigDecimal getTotalVat();

	ModelColumn<I_EDI_cctop_invoic_v, Object> COLUMN_TotalVat = new ModelColumn<>(I_EDI_cctop_invoic_v.class, "TotalVat", null);
	String COLUMNNAME_TotalVat = "TotalVat";

	/**
	 * Set TotalVatWithSurchargeAmt.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setTotalVatWithSurchargeAmt (BigDecimal TotalVatWithSurchargeAmt);

	/**
	 * Get TotalVatWithSurchargeAmt.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getTotalVatWithSurchargeAmt();

	ModelColumn<I_EDI_cctop_invoic_v, Object> COLUMN_TotalVatWithSurchargeAmt = new ModelColumn<>(I_EDI_cctop_invoic_v.class, "TotalVatWithSurchargeAmt", null);
	String COLUMNNAME_TotalVatWithSurchargeAmt = "TotalVatWithSurchargeAmt";
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
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_v, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_v, Object>(I_EDI_cctop_invoic_v.class, "Updated", null);
    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";
=======
	java.sql.Timestamp getUpdated();

	ModelColumn<I_EDI_cctop_invoic_v, Object> COLUMN_Updated = new ModelColumn<>(I_EDI_cctop_invoic_v.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_v, Object> COLUMN_VATaxID = new org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_v, Object>(I_EDI_cctop_invoic_v.class, "VATaxID", null);
    /** Column name VATaxID */
    public static final String COLUMNNAME_VATaxID = "VATaxID";
=======
	@Nullable java.lang.String getVATaxID();

	ModelColumn<I_EDI_cctop_invoic_v, Object> COLUMN_VATaxID = new ModelColumn<>(I_EDI_cctop_invoic_v.class, "VATaxID", null);
	String COLUMNNAME_VATaxID = "VATaxID";
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
}
