package de.metas.printing.model;


/** Generated Interface for RV_Prt_Bericht_Statistik_List_Per_Org
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_RV_Prt_Bericht_Statistik_List_Per_Org 
{

    /** TableName=RV_Prt_Bericht_Statistik_List_Per_Org */
    public static final String Table_Name = "RV_Prt_Bericht_Statistik_List_Per_Org";

    /** AD_Table_ID=540479 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);


	/**
	 * Get Client.
	 * Client/Tenant for this installation.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_Client_ID();

    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Straße und Nr..
	 * Adresszeile 1 für diesen Standort
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAddress1 (java.lang.String Address1);

	/**
	 * Get Straße und Nr..
	 * Adresszeile 1 für diesen Standort
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getAddress1();

    /** Column definition for Address1 */
    public static final org.adempiere.model.ModelColumn<I_RV_Prt_Bericht_Statistik_List_Per_Org, Object> COLUMN_Address1 = new org.adempiere.model.ModelColumn<I_RV_Prt_Bericht_Statistik_List_Per_Org, Object>(I_RV_Prt_Bericht_Statistik_List_Per_Org.class, "Address1", null);
    /** Column name Address1 */
    public static final String COLUMNNAME_Address1 = "Address1";

	/**
	 * Set Organisation.
	 * Organisational entity within client
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Organisation.
	 * Organisational entity within client
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_Org_ID();

    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Address.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setBPartnerAddress (java.lang.String BPartnerAddress);

	/**
	 * Get Address.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getBPartnerAddress();

    /** Column definition for BPartnerAddress */
    public static final org.adempiere.model.ModelColumn<I_RV_Prt_Bericht_Statistik_List_Per_Org, Object> COLUMN_BPartnerAddress = new org.adempiere.model.ModelColumn<I_RV_Prt_Bericht_Statistik_List_Per_Org, Object>(I_RV_Prt_Bericht_Statistik_List_Per_Org.class, "BPartnerAddress", null);
    /** Column name BPartnerAddress */
    public static final String COLUMNNAME_BPartnerAddress = "BPartnerAddress";

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
    public static final org.adempiere.model.ModelColumn<I_RV_Prt_Bericht_Statistik_List_Per_Org, org.compiere.model.I_C_Invoice> COLUMN_C_Invoice_ID = new org.adempiere.model.ModelColumn<I_RV_Prt_Bericht_Statistik_List_Per_Org, org.compiere.model.I_C_Invoice>(I_RV_Prt_Bericht_Statistik_List_Per_Org.class, "C_Invoice_ID", org.compiere.model.I_C_Invoice.class);
    /** Column name C_Invoice_ID */
    public static final String COLUMNNAME_C_Invoice_ID = "C_Invoice_ID";

	/**
	 * Set Company Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setCompanyname (java.lang.String Companyname);

	/**
	 * Get Company Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getCompanyname();

    /** Column definition for Companyname */
    public static final org.adempiere.model.ModelColumn<I_RV_Prt_Bericht_Statistik_List_Per_Org, Object> COLUMN_Companyname = new org.adempiere.model.ModelColumn<I_RV_Prt_Bericht_Statistik_List_Per_Org, Object>(I_RV_Prt_Bericht_Statistik_List_Per_Org.class, "Companyname", null);
    /** Column name Companyname */
    public static final String COLUMNNAME_Companyname = "Companyname";

	/**
	 * Set Druck-Job.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Print_Job_ID (int C_Print_Job_ID);

	/**
	 * Get Druck-Job.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Print_Job_ID();

	public de.metas.printing.model.I_C_Print_Job getC_Print_Job();

	public void setC_Print_Job(de.metas.printing.model.I_C_Print_Job C_Print_Job);

    /** Column definition for C_Print_Job_ID */
    public static final org.adempiere.model.ModelColumn<I_RV_Prt_Bericht_Statistik_List_Per_Org, de.metas.printing.model.I_C_Print_Job> COLUMN_C_Print_Job_ID = new org.adempiere.model.ModelColumn<I_RV_Prt_Bericht_Statistik_List_Per_Org, de.metas.printing.model.I_C_Print_Job>(I_RV_Prt_Bericht_Statistik_List_Per_Org.class, "C_Print_Job_ID", de.metas.printing.model.I_C_Print_Job.class);
    /** Column name C_Print_Job_ID */
    public static final String COLUMNNAME_C_Print_Job_ID = "C_Print_Job_ID";

	/**
	 * Set Druck-Job Anweisung.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Print_Job_Instructions_ID (int C_Print_Job_Instructions_ID);

	/**
	 * Get Druck-Job Anweisung.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Print_Job_Instructions_ID();

	public de.metas.printing.model.I_C_Print_Job_Instructions getC_Print_Job_Instructions();

	public void setC_Print_Job_Instructions(de.metas.printing.model.I_C_Print_Job_Instructions C_Print_Job_Instructions);

    /** Column definition for C_Print_Job_Instructions_ID */
    public static final org.adempiere.model.ModelColumn<I_RV_Prt_Bericht_Statistik_List_Per_Org, de.metas.printing.model.I_C_Print_Job_Instructions> COLUMN_C_Print_Job_Instructions_ID = new org.adempiere.model.ModelColumn<I_RV_Prt_Bericht_Statistik_List_Per_Org, de.metas.printing.model.I_C_Print_Job_Instructions>(I_RV_Prt_Bericht_Statistik_List_Per_Org.class, "C_Print_Job_Instructions_ID", de.metas.printing.model.I_C_Print_Job_Instructions.class);
    /** Column name C_Print_Job_Instructions_ID */
    public static final String COLUMNNAME_C_Print_Job_Instructions_ID = "C_Print_Job_Instructions_ID";

	/**
	 * Set c_print_job_name.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setc_print_job_name (java.lang.String c_print_job_name);

	/**
	 * Get c_print_job_name.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getc_print_job_name();

    /** Column definition for c_print_job_name */
    public static final org.adempiere.model.ModelColumn<I_RV_Prt_Bericht_Statistik_List_Per_Org, Object> COLUMN_c_print_job_name = new org.adempiere.model.ModelColumn<I_RV_Prt_Bericht_Statistik_List_Per_Org, Object>(I_RV_Prt_Bericht_Statistik_List_Per_Org.class, "c_print_job_name", null);
    /** Column name c_print_job_name */
    public static final String COLUMNNAME_c_print_job_name = "c_print_job_name";

	/**
	 * Set Druckpaket.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Print_Package_ID (int C_Print_Package_ID);

	/**
	 * Get Druckpaket.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Print_Package_ID();

	public de.metas.printing.model.I_C_Print_Package getC_Print_Package();

	public void setC_Print_Package(de.metas.printing.model.I_C_Print_Package C_Print_Package);

    /** Column definition for C_Print_Package_ID */
    public static final org.adempiere.model.ModelColumn<I_RV_Prt_Bericht_Statistik_List_Per_Org, de.metas.printing.model.I_C_Print_Package> COLUMN_C_Print_Package_ID = new org.adempiere.model.ModelColumn<I_RV_Prt_Bericht_Statistik_List_Per_Org, de.metas.printing.model.I_C_Print_Package>(I_RV_Prt_Bericht_Statistik_List_Per_Org.class, "C_Print_Package_ID", de.metas.printing.model.I_C_Print_Package.class);
    /** Column name C_Print_Package_ID */
    public static final String COLUMNNAME_C_Print_Package_ID = "C_Print_Package_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getCreated();

    /** Column definition for Created */
    public static final org.adempiere.model.ModelColumn<I_RV_Prt_Bericht_Statistik_List_Per_Org, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_RV_Prt_Bericht_Statistik_List_Per_Org, Object>(I_RV_Prt_Bericht_Statistik_List_Per_Org.class, "Created", null);
    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/**
	 * Get Created By.
	 * User who created this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getCreatedBy();

    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Date.
	 * Date printed on Invoice
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDateInvoiced (java.sql.Timestamp DateInvoiced);

	/**
	 * Get Date.
	 * Date printed on Invoice
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getDateInvoiced();

    /** Column definition for DateInvoiced */
    public static final org.adempiere.model.ModelColumn<I_RV_Prt_Bericht_Statistik_List_Per_Org, Object> COLUMN_DateInvoiced = new org.adempiere.model.ModelColumn<I_RV_Prt_Bericht_Statistik_List_Per_Org, Object>(I_RV_Prt_Bericht_Statistik_List_Per_Org.class, "DateInvoiced", null);
    /** Column name DateInvoiced */
    public static final String COLUMNNAME_DateInvoiced = "DateInvoiced";

	/**
	 * Set document.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setdocument (java.lang.String document);

	/**
	 * Get document.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getdocument();

    /** Column definition for document */
    public static final org.adempiere.model.ModelColumn<I_RV_Prt_Bericht_Statistik_List_Per_Org, Object> COLUMN_document = new org.adempiere.model.ModelColumn<I_RV_Prt_Bericht_Statistik_List_Per_Org, Object>(I_RV_Prt_Bericht_Statistik_List_Per_Org.class, "document", null);
    /** Column name document */
    public static final String COLUMNNAME_document = "document";

	/**
	 * Set Document No.
	 * Document sequence number of the document
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDocumentNo (java.lang.String DocumentNo);

	/**
	 * Get Document No.
	 * Document sequence number of the document
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDocumentNo();

    /** Column definition for DocumentNo */
    public static final org.adempiere.model.ModelColumn<I_RV_Prt_Bericht_Statistik_List_Per_Org, Object> COLUMN_DocumentNo = new org.adempiere.model.ModelColumn<I_RV_Prt_Bericht_Statistik_List_Per_Org, Object>(I_RV_Prt_Bericht_Statistik_List_Per_Org.class, "DocumentNo", null);
    /** Column name DocumentNo */
    public static final String COLUMNNAME_DocumentNo = "DocumentNo";

	/**
	 * Set Firstname.
	 * Firstname
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setFirstname (java.lang.String Firstname);

	/**
	 * Get Firstname.
	 * Firstname
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getFirstname();

    /** Column definition for Firstname */
    public static final org.adempiere.model.ModelColumn<I_RV_Prt_Bericht_Statistik_List_Per_Org, Object> COLUMN_Firstname = new org.adempiere.model.ModelColumn<I_RV_Prt_Bericht_Statistik_List_Per_Org, Object>(I_RV_Prt_Bericht_Statistik_List_Per_Org.class, "Firstname", null);
    /** Column name Firstname */
    public static final String COLUMNNAME_Firstname = "Firstname";

	/**
	 * Set Summe Gesamt.
	 * Summe über Alles zu diesem Beleg
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setGrandTotal (java.math.BigDecimal GrandTotal);

	/**
	 * Get Summe Gesamt.
	 * Summe über Alles zu diesem Beleg
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getGrandTotal();

    /** Column definition for GrandTotal */
    public static final org.adempiere.model.ModelColumn<I_RV_Prt_Bericht_Statistik_List_Per_Org, Object> COLUMN_GrandTotal = new org.adempiere.model.ModelColumn<I_RV_Prt_Bericht_Statistik_List_Per_Org, Object>(I_RV_Prt_Bericht_Statistik_List_Per_Org.class, "GrandTotal", null);
    /** Column name GrandTotal */
    public static final String COLUMNNAME_GrandTotal = "GrandTotal";

	/**
	 * Set Active.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setIsActive (boolean IsActive);

	/**
	 * Get Active.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isActive();

    /** Column definition for IsActive */
    public static final org.adempiere.model.ModelColumn<I_RV_Prt_Bericht_Statistik_List_Per_Org, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_RV_Prt_Bericht_Statistik_List_Per_Org, Object>(I_RV_Prt_Bericht_Statistik_List_Per_Org.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Lastname.
	 * Lastname
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setLastname (java.lang.String Lastname);

	/**
	 * Get Lastname.
	 * Lastname
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getLastname();

    /** Column definition for Lastname */
    public static final org.adempiere.model.ModelColumn<I_RV_Prt_Bericht_Statistik_List_Per_Org, Object> COLUMN_Lastname = new org.adempiere.model.ModelColumn<I_RV_Prt_Bericht_Statistik_List_Per_Org, Object>(I_RV_Prt_Bericht_Statistik_List_Per_Org.class, "Lastname", null);
    /** Column name Lastname */
    public static final String COLUMNNAME_Lastname = "Lastname";

	/**
	 * Set printjob.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setprintjob (java.lang.String printjob);

	/**
	 * Get printjob.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getprintjob();

    /** Column definition for printjob */
    public static final org.adempiere.model.ModelColumn<I_RV_Prt_Bericht_Statistik_List_Per_Org, Object> COLUMN_printjob = new org.adempiere.model.ModelColumn<I_RV_Prt_Bericht_Statistik_List_Per_Org, Object>(I_RV_Prt_Bericht_Statistik_List_Per_Org.class, "printjob", null);
    /** Column name printjob */
    public static final String COLUMNNAME_printjob = "printjob";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getUpdated();

    /** Column definition for Updated */
    public static final org.adempiere.model.ModelColumn<I_RV_Prt_Bericht_Statistik_List_Per_Org, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_RV_Prt_Bericht_Statistik_List_Per_Org, Object>(I_RV_Prt_Bericht_Statistik_List_Per_Org.class, "Updated", null);
    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Updated By.
	 * User who updated this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getUpdatedBy();

    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
