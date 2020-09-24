package de.metas.payment.esr.model;


/** Generated Interface for ESR_ImportLine
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_ESR_ImportLine 
{

    /** TableName=ESR_ImportLine */
    public static final String Table_Name = "ESR_ImportLine";

    /** AD_Table_ID=540410 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);


	/**
	 * Set Buchungsdatum.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAccountingDate (java.sql.Timestamp AccountingDate);

	/**
	 * Get Buchungsdatum.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getAccountingDate();

    /** Column definition for AccountingDate */
    public static final org.adempiere.model.ModelColumn<I_ESR_ImportLine, Object> COLUMN_AccountingDate = new org.adempiere.model.ModelColumn<I_ESR_ImportLine, Object>(I_ESR_ImportLine.class, "AccountingDate", null);
    /** Column name AccountingDate */
    public static final String COLUMNNAME_AccountingDate = "AccountingDate";

	/**
	 * Set Konto-Nr..
	 * Kontonummer
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAccountNo (java.lang.String AccountNo);

	/**
	 * Get Konto-Nr..
	 * Kontonummer
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getAccountNo();

    /** Column definition for AccountNo */
    public static final org.adempiere.model.ModelColumn<I_ESR_ImportLine, Object> COLUMN_AccountNo = new org.adempiere.model.ModelColumn<I_ESR_ImportLine, Object>(I_ESR_ImportLine.class, "AccountNo", null);
    /** Column name AccountNo */
    public static final String COLUMNNAME_AccountNo = "AccountNo";

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
	 * Set Amount.
	 * Amount in a defined currency
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAmount (java.math.BigDecimal Amount);

	/**
	 * Get Amount.
	 * Amount in a defined currency
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getAmount();

    /** Column definition for Amount */
    public static final org.adempiere.model.ModelColumn<I_ESR_ImportLine, Object> COLUMN_Amount = new org.adempiere.model.ModelColumn<I_ESR_ImportLine, Object>(I_ESR_ImportLine.class, "Amount", null);
    /** Column name Amount */
    public static final String COLUMNNAME_Amount = "Amount";

	/**
	 * Set Geschäftspartner-Schlüssel.
	 * Suchschlüssel für den Geschäftspartner
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setBPartner_Value (java.lang.String BPartner_Value);

	/**
	 * Get Geschäftspartner-Schlüssel.
	 * Suchschlüssel für den Geschäftspartner
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getBPartner_Value();

    /** Column definition for BPartner_Value */
    public static final org.adempiere.model.ModelColumn<I_ESR_ImportLine, Object> COLUMN_BPartner_Value = new org.adempiere.model.ModelColumn<I_ESR_ImportLine, Object>(I_ESR_ImportLine.class, "BPartner_Value", null);
    /** Column name BPartner_Value */
    public static final String COLUMNNAME_BPartner_Value = "BPartner_Value";

	/**
	 * Set Bank Statement.
	 * Bank Statement of account
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_BankStatement_ID (int C_BankStatement_ID);

	/**
	 * Get Bank Statement.
	 * Bank Statement of account
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_BankStatement_ID();

    /** Column name C_BankStatement_ID */
    public static final String COLUMNNAME_C_BankStatement_ID = "C_BankStatement_ID";

	/**
	 * Set Statement Line.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_BankStatementLine_ID (int C_BankStatementLine_ID);

	/**
	 * Get Statement Line.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_BankStatementLine_ID();

    /** Column name C_BankStatementLine_ID */
    public static final String COLUMNNAME_C_BankStatementLine_ID = "C_BankStatementLine_ID";

	/**
	 * Set Bank Statement Line Reference.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_BankStatementLine_Ref_ID (int C_BankStatementLine_Ref_ID);

	/**
	 * Get Bank Statement Line Reference.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_BankStatementLine_Ref_ID();

    /** Column definition for C_BankStatementLine_Ref_ID */
    public static final org.adempiere.model.ModelColumn<I_ESR_ImportLine, Object> COLUMN_C_BankStatementLine_Ref_ID = new org.adempiere.model.ModelColumn<I_ESR_ImportLine, Object>(I_ESR_ImportLine.class, "C_BankStatementLine_Ref_ID", null);
    /** Column name C_BankStatementLine_Ref_ID */
    public static final String COLUMNNAME_C_BankStatementLine_Ref_ID = "C_BankStatementLine_Ref_ID";

	/**
	 * Set Partner Bank Account.
	 * Bank Account of the Business Partner
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_BP_BankAccount_ID (int C_BP_BankAccount_ID);

	/**
	 * Get Partner Bank Account.
	 * Bank Account of the Business Partner
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_BP_BankAccount_ID();

    /** Column name C_BP_BankAccount_ID */
    public static final String COLUMNNAME_C_BP_BankAccount_ID = "C_BP_BankAccount_ID";

	/**
	 * Set Business Partner.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_BPartner_ID (int C_BPartner_ID);

	/**
	 * Get Business Partner.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_BPartner_ID();

    /** Column name C_BPartner_ID */
    public static final String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/**
	 * Set Invoice.
	 * Invoice Identifier
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Invoice_ID (int C_Invoice_ID);

	/**
	 * Get Invoice.
	 * Invoice Identifier
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Invoice_ID();

	public org.compiere.model.I_C_Invoice getC_Invoice();

	public void setC_Invoice(org.compiere.model.I_C_Invoice C_Invoice);

    /** Column definition for C_Invoice_ID */
    public static final org.adempiere.model.ModelColumn<I_ESR_ImportLine, org.compiere.model.I_C_Invoice> COLUMN_C_Invoice_ID = new org.adempiere.model.ModelColumn<I_ESR_ImportLine, org.compiere.model.I_C_Invoice>(I_ESR_ImportLine.class, "C_Invoice_ID", org.compiere.model.I_C_Invoice.class);
    /** Column name C_Invoice_ID */
    public static final String COLUMNNAME_C_Invoice_ID = "C_Invoice_ID";

	/**
	 * Set Payment.
	 * Payment identifier
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Payment_ID (int C_Payment_ID);

	/**
	 * Get Payment.
	 * Payment identifier
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Payment_ID();

    /** Column name C_Payment_ID */
    public static final String COLUMNNAME_C_Payment_ID = "C_Payment_ID";

	/**
	 * Set Reference No.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_ReferenceNo_ID (int C_ReferenceNo_ID);

	/**
	 * Get Reference No.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_ReferenceNo_ID();

    /** Column definition for C_ReferenceNo_ID */
    public static final org.adempiere.model.ModelColumn<I_ESR_ImportLine, Object> COLUMN_C_ReferenceNo_ID = new org.adempiere.model.ModelColumn<I_ESR_ImportLine, Object>(I_ESR_ImportLine.class, "C_ReferenceNo_ID", null);
    /** Column name C_ReferenceNo_ID */
    public static final String COLUMNNAME_C_ReferenceNo_ID = "C_ReferenceNo_ID";

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
    public static final org.adempiere.model.ModelColumn<I_ESR_ImportLine, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_ESR_ImportLine, Object>(I_ESR_ImportLine.class, "Created", null);
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
	 * Set Description.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDescription (java.lang.String Description);

	/**
	 * Get Description.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDescription();

    /** Column definition for Description */
    public static final org.adempiere.model.ModelColumn<I_ESR_ImportLine, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<I_ESR_ImportLine, Object>(I_ESR_ImportLine.class, "Description", null);
    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/**
	 * Set Bereits zugeordnet.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	public void setESR_Amount_Balance (java.math.BigDecimal ESR_Amount_Balance);

	/**
	 * Get Bereits zugeordnet.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	public java.math.BigDecimal getESR_Amount_Balance();

    /** Column definition for ESR_Amount_Balance */
    public static final org.adempiere.model.ModelColumn<I_ESR_ImportLine, Object> COLUMN_ESR_Amount_Balance = new org.adempiere.model.ModelColumn<I_ESR_ImportLine, Object>(I_ESR_ImportLine.class, "ESR_Amount_Balance", null);
    /** Column name ESR_Amount_Balance */
    public static final String COLUMNNAME_ESR_Amount_Balance = "ESR_Amount_Balance";

	/**
	 * Set Importstatus.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setESR_Document_Status (java.lang.String ESR_Document_Status);

	/**
	 * Get Importstatus.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getESR_Document_Status();

    /** Column definition for ESR_Document_Status */
    public static final org.adempiere.model.ModelColumn<I_ESR_ImportLine, Object> COLUMN_ESR_Document_Status = new org.adempiere.model.ModelColumn<I_ESR_ImportLine, Object>(I_ESR_ImportLine.class, "ESR_Document_Status", null);
    /** Column name ESR_Document_Status */
    public static final String COLUMNNAME_ESR_Document_Status = "ESR_Document_Status";

	/**
	 * Set ESR Rechnungsnummer.
	 * Belegnummer der zugeordneten Rechnung
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setESR_DocumentNo (java.lang.String ESR_DocumentNo);

	/**
	 * Get ESR Rechnungsnummer.
	 * Belegnummer der zugeordneten Rechnung
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getESR_DocumentNo();

    /** Column definition for ESR_DocumentNo */
    public static final org.adempiere.model.ModelColumn<I_ESR_ImportLine, Object> COLUMN_ESR_DocumentNo = new org.adempiere.model.ModelColumn<I_ESR_ImportLine, Object>(I_ESR_ImportLine.class, "ESR_DocumentNo", null);
    /** Column name ESR_DocumentNo */
    public static final String COLUMNNAME_ESR_DocumentNo = "ESR_DocumentNo";

	/**
	 * Set ESR Zahlungsimport.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setESR_Import_ID (int ESR_Import_ID);

	/**
	 * Get ESR Zahlungsimport.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getESR_Import_ID();

	public de.metas.payment.esr.model.I_ESR_Import getESR_Import();

	public void setESR_Import(de.metas.payment.esr.model.I_ESR_Import ESR_Import);

    /** Column definition for ESR_Import_ID */
    public static final org.adempiere.model.ModelColumn<I_ESR_ImportLine, de.metas.payment.esr.model.I_ESR_Import> COLUMN_ESR_Import_ID = new org.adempiere.model.ModelColumn<I_ESR_ImportLine, de.metas.payment.esr.model.I_ESR_Import>(I_ESR_ImportLine.class, "ESR_Import_ID", de.metas.payment.esr.model.I_ESR_Import.class);
    /** Column name ESR_Import_ID */
    public static final String COLUMNNAME_ESR_Import_ID = "ESR_Import_ID";

	/**
	 * Set ESR_ImportLine.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setESR_ImportLine_ID (int ESR_ImportLine_ID);

	/**
	 * Get ESR_ImportLine.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getESR_ImportLine_ID();

    /** Column definition for ESR_ImportLine_ID */
    public static final org.adempiere.model.ModelColumn<I_ESR_ImportLine, Object> COLUMN_ESR_ImportLine_ID = new org.adempiere.model.ModelColumn<I_ESR_ImportLine, Object>(I_ESR_ImportLine.class, "ESR_ImportLine_ID", null);
    /** Column name ESR_ImportLine_ID */
    public static final String COLUMNNAME_ESR_ImportLine_ID = "ESR_ImportLine_ID";

	/**
	 * Set Rechnungsbetrag.
	 * Gesamtbetrag der zugeordneten Rechnung
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setESR_Invoice_Grandtotal (java.math.BigDecimal ESR_Invoice_Grandtotal);

	/**
	 * Get Rechnungsbetrag.
	 * Gesamtbetrag der zugeordneten Rechnung
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getESR_Invoice_Grandtotal();

    /** Column definition for ESR_Invoice_Grandtotal */
    public static final org.adempiere.model.ModelColumn<I_ESR_ImportLine, Object> COLUMN_ESR_Invoice_Grandtotal = new org.adempiere.model.ModelColumn<I_ESR_ImportLine, Object>(I_ESR_ImportLine.class, "ESR_Invoice_Grandtotal", null);
    /** Column name ESR_Invoice_Grandtotal */
    public static final String COLUMNNAME_ESR_Invoice_Grandtotal = "ESR_Invoice_Grandtotal";

	/**
	 * Set Offener Rechnungsbetrag.
	 * Noch offener Betrag der zugeordneten Rechnung, Abzüglich der ESR-Zahlbeträge aus der/den Zeile(n) der vorliegenden Import-Datei.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setESR_Invoice_Openamt (java.math.BigDecimal ESR_Invoice_Openamt);

	/**
	 * Get Offener Rechnungsbetrag.
	 * Noch offener Betrag der zugeordneten Rechnung, Abzüglich der ESR-Zahlbeträge aus der/den Zeile(n) der vorliegenden Import-Datei.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getESR_Invoice_Openamt();

    /** Column definition for ESR_Invoice_Openamt */
    public static final org.adempiere.model.ModelColumn<I_ESR_ImportLine, Object> COLUMN_ESR_Invoice_Openamt = new org.adempiere.model.ModelColumn<I_ESR_ImportLine, Object>(I_ESR_ImportLine.class, "ESR_Invoice_Openamt", null);
    /** Column name ESR_Invoice_Openamt */
    public static final String COLUMNNAME_ESR_Invoice_Openamt = "ESR_Invoice_Openamt";

	/**
	 * Set Extern erstellte ESR-Referenz.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setESR_IsManual_ReferenceNo (boolean ESR_IsManual_ReferenceNo);

	/**
	 * Get Extern erstellte ESR-Referenz.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isESR_IsManual_ReferenceNo();

    /** Column definition for ESR_IsManual_ReferenceNo */
    public static final org.adempiere.model.ModelColumn<I_ESR_ImportLine, Object> COLUMN_ESR_IsManual_ReferenceNo = new org.adempiere.model.ModelColumn<I_ESR_ImportLine, Object>(I_ESR_ImportLine.class, "ESR_IsManual_ReferenceNo", null);
    /** Column name ESR_IsManual_ReferenceNo */
    public static final String COLUMNNAME_ESR_IsManual_ReferenceNo = "ESR_IsManual_ReferenceNo";

	/**
	 * Set ESR_Payment_Action.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setESR_Payment_Action (java.lang.String ESR_Payment_Action);

	/**
	 * Get ESR_Payment_Action.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getESR_Payment_Action();

    /** Column definition for ESR_Payment_Action */
    public static final org.adempiere.model.ModelColumn<I_ESR_ImportLine, Object> COLUMN_ESR_Payment_Action = new org.adempiere.model.ModelColumn<I_ESR_ImportLine, Object>(I_ESR_ImportLine.class, "ESR_Payment_Action", null);
    /** Column name ESR_Payment_Action */
    public static final String COLUMNNAME_ESR_Payment_Action = "ESR_Payment_Action";

	/**
	 * Set ESR Referenznummer (komplett).
	 * Referenznummer inkl. bankinterner Teilnehmernummer
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setESRFullReferenceNumber (java.lang.String ESRFullReferenceNumber);

	/**
	 * Get ESR Referenznummer (komplett).
	 * Referenznummer inkl. bankinterner Teilnehmernummer
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getESRFullReferenceNumber();

    /** Column definition for ESRFullReferenceNumber */
    public static final org.adempiere.model.ModelColumn<I_ESR_ImportLine, Object> COLUMN_ESRFullReferenceNumber = new org.adempiere.model.ModelColumn<I_ESR_ImportLine, Object>(I_ESR_ImportLine.class, "ESRFullReferenceNumber", null);
    /** Column name ESRFullReferenceNumber */
    public static final String COLUMNNAME_ESRFullReferenceNumber = "ESRFullReferenceNumber";

	/**
	 * Set Importierte ESR-Zeile.
	 * ESR complete line text
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setESRLineText (java.lang.String ESRLineText);

	/**
	 * Get Importierte ESR-Zeile.
	 * ESR complete line text
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getESRLineText();

    /** Column definition for ESRLineText */
    public static final org.adempiere.model.ModelColumn<I_ESR_ImportLine, Object> COLUMN_ESRLineText = new org.adempiere.model.ModelColumn<I_ESR_ImportLine, Object>(I_ESR_ImportLine.class, "ESRLineText", null);
    /** Column name ESRLineText */
    public static final String COLUMNNAME_ESRLineText = "ESRLineText";

	/**
	 * Set Post-Teilnehmernummer.
	 * Post-Teilnehmernummer
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setESRPostParticipantNumber (java.lang.String ESRPostParticipantNumber);

	/**
	 * Get Post-Teilnehmernummer.
	 * Post-Teilnehmernummer
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getESRPostParticipantNumber();

    /** Column definition for ESRPostParticipantNumber */
    public static final org.adempiere.model.ModelColumn<I_ESR_ImportLine, Object> COLUMN_ESRPostParticipantNumber = new org.adempiere.model.ModelColumn<I_ESR_ImportLine, Object>(I_ESR_ImportLine.class, "ESRPostParticipantNumber", null);
    /** Column name ESRPostParticipantNumber */
    public static final String COLUMNNAME_ESRPostParticipantNumber = "ESRPostParticipantNumber";

	/**
	 * Set ESR Referenznummer.
	 * Referenznummer der jeweiligen Rechnung
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setESRReferenceNumber (java.lang.String ESRReferenceNumber);

	/**
	 * Get ESR Referenznummer.
	 * Referenznummer der jeweiligen Rechnung
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getESRReferenceNumber();

    /** Column definition for ESRReferenceNumber */
    public static final org.adempiere.model.ModelColumn<I_ESR_ImportLine, Object> COLUMN_ESRReferenceNumber = new org.adempiere.model.ModelColumn<I_ESR_ImportLine, Object>(I_ESR_ImportLine.class, "ESRReferenceNumber", null);
    /** Column name ESRReferenceNumber */
    public static final String COLUMNNAME_ESRReferenceNumber = "ESRReferenceNumber";

	/**
	 * Set Transaktionsart.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setESRTrxType (java.lang.String ESRTrxType);

	/**
	 * Get Transaktionsart.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getESRTrxType();

    /** Column definition for ESRTrxType */
    public static final org.adempiere.model.ModelColumn<I_ESR_ImportLine, Object> COLUMN_ESRTrxType = new org.adempiere.model.ModelColumn<I_ESR_ImportLine, Object>(I_ESR_ImportLine.class, "ESRTrxType", null);
    /** Column name ESRTrxType */
    public static final String COLUMNNAME_ESRTrxType = "ESRTrxType";

	/**
	 * Set Error Message.
	 * Fehler beim Einlesen der Datei, z.B. Fehler im Format eines Datums
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setImportErrorMsg (java.lang.String ImportErrorMsg);

	/**
	 * Get Error Message.
	 * Fehler beim Einlesen der Datei, z.B. Fehler im Format eines Datums
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getImportErrorMsg();

    /** Column definition for ImportErrorMsg */
    public static final org.adempiere.model.ModelColumn<I_ESR_ImportLine, Object> COLUMN_ImportErrorMsg = new org.adempiere.model.ModelColumn<I_ESR_ImportLine, Object>(I_ESR_ImportLine.class, "ImportErrorMsg", null);
    /** Column name ImportErrorMsg */
    public static final String COLUMNNAME_ImportErrorMsg = "ImportErrorMsg";

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
    public static final org.adempiere.model.ModelColumn<I_ESR_ImportLine, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_ESR_ImportLine, Object>(I_ESR_ImportLine.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Manuell.
	 * Dies ist ein manueller Vorgang
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setIsManual (boolean IsManual);

	/**
	 * Get Manuell.
	 * Dies ist ein manueller Vorgang
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isManual();

    /** Column definition for IsManual */
    public static final org.adempiere.model.ModelColumn<I_ESR_ImportLine, Object> COLUMN_IsManual = new org.adempiere.model.ModelColumn<I_ESR_ImportLine, Object>(I_ESR_ImportLine.class, "IsManual", null);
    /** Column name IsManual */
    public static final String COLUMNNAME_IsManual = "IsManual";

	/**
	 * Set Is Valid.
	 * The element is valid
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsValid (boolean IsValid);

	/**
	 * Get Is Valid.
	 * The element is valid
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isValid();

    /** Column definition for IsValid */
    public static final org.adempiere.model.ModelColumn<I_ESR_ImportLine, Object> COLUMN_IsValid = new org.adempiere.model.ModelColumn<I_ESR_ImportLine, Object>(I_ESR_ImportLine.class, "IsValid", null);
    /** Column name IsValid */
    public static final String COLUMNNAME_IsValid = "IsValid";

	/**
	 * Set Line.
	 * Line No
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setLineNo (int LineNo);

	/**
	 * Get Line.
	 * Line No
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getLineNo();

    /** Column definition for LineNo */
    public static final org.adempiere.model.ModelColumn<I_ESR_ImportLine, Object> COLUMN_LineNo = new org.adempiere.model.ModelColumn<I_ESR_ImportLine, Object>(I_ESR_ImportLine.class, "LineNo", null);
    /** Column name LineNo */
    public static final String COLUMNNAME_LineNo = "LineNo";

	/**
	 * Set Zuordnungsfehler.
	 * Fehler beim Zuordnen der Daten, z.B. fehlende Rechnung für eine eingelesene Referenznummer
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setMatchErrorMsg (java.lang.String MatchErrorMsg);

	/**
	 * Get Zuordnungsfehler.
	 * Fehler beim Zuordnen der Daten, z.B. fehlende Rechnung für eine eingelesene Referenznummer
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getMatchErrorMsg();

    /** Column definition for MatchErrorMsg */
    public static final org.adempiere.model.ModelColumn<I_ESR_ImportLine, Object> COLUMN_MatchErrorMsg = new org.adempiere.model.ModelColumn<I_ESR_ImportLine, Object>(I_ESR_ImportLine.class, "MatchErrorMsg", null);
    /** Column name MatchErrorMsg */
    public static final String COLUMNNAME_MatchErrorMsg = "MatchErrorMsg";

	/**
	 * Set Organisation.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setOrg_ID (int Org_ID);

	/**
	 * Get Organisation.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getOrg_ID();

    /** Column name Org_ID */
    public static final String COLUMNNAME_Org_ID = "Org_ID";

	/**
	 * Set Zahldatum.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPaymentDate (java.sql.Timestamp PaymentDate);

	/**
	 * Get Zahldatum.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getPaymentDate();

    /** Column definition for PaymentDate */
    public static final org.adempiere.model.ModelColumn<I_ESR_ImportLine, Object> COLUMN_PaymentDate = new org.adempiere.model.ModelColumn<I_ESR_ImportLine, Object>(I_ESR_ImportLine.class, "PaymentDate", null);
    /** Column name PaymentDate */
    public static final String COLUMNNAME_PaymentDate = "PaymentDate";

	/**
	 * Set Processed.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setProcessed (boolean Processed);

	/**
	 * Get Processed.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isProcessed();

    /** Column definition for Processed */
    public static final org.adempiere.model.ModelColumn<I_ESR_ImportLine, Object> COLUMN_Processed = new org.adempiere.model.ModelColumn<I_ESR_ImportLine, Object>(I_ESR_ImportLine.class, "Processed", null);
    /** Column name Processed */
    public static final String COLUMNNAME_Processed = "Processed";

	/**
	 * Set ESR Sektion.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setSektionNo (java.lang.String SektionNo);

	/**
	 * Get ESR Sektion.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getSektionNo();

    /** Column definition for SektionNo */
    public static final org.adempiere.model.ModelColumn<I_ESR_ImportLine, Object> COLUMN_SektionNo = new org.adempiere.model.ModelColumn<I_ESR_ImportLine, Object>(I_ESR_ImportLine.class, "SektionNo", null);
    /** Column name SektionNo */
    public static final String COLUMNNAME_SektionNo = "SektionNo";

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
    public static final org.adempiere.model.ModelColumn<I_ESR_ImportLine, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_ESR_ImportLine, Object>(I_ESR_ImportLine.class, "Updated", null);
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
