package de.metas.payment.esr.model;

import java.math.BigDecimal;

import javax.annotation.Nullable;

import org.adempiere.model.ModelColumn;

/** Generated Interface for ESR_ImportLine
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_ESR_ImportLine 
{

	String Table_Name = "ESR_ImportLine";

//	/** AD_Table_ID=540410 */
//	int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);


	/**
	 * Set Buchungsdatum.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAccountingDate (@Nullable java.sql.Timestamp AccountingDate);

	/**
	 * Get Buchungsdatum.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getAccountingDate();

	ModelColumn<I_ESR_ImportLine, Object> COLUMN_AccountingDate = new ModelColumn<>(I_ESR_ImportLine.class, "AccountingDate", null);
	String COLUMNNAME_AccountingDate = "AccountingDate";

	/**
	 * Set Konto-Nr..
	 * Kontonummer
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAccountNo (@Nullable java.lang.String AccountNo);

	/**
	 * Get Konto-Nr..
	 * Kontonummer
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getAccountNo();

	ModelColumn<I_ESR_ImportLine, Object> COLUMN_AccountNo = new ModelColumn<>(I_ESR_ImportLine.class, "AccountNo", null);
	String COLUMNNAME_AccountNo = "AccountNo";

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
	 * Set Amount.
	 * Amount in a defined currency
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAmount (@Nullable BigDecimal Amount);

	/**
	 * Get Amount.
	 * Amount in a defined currency
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getAmount();

	ModelColumn<I_ESR_ImportLine, Object> COLUMN_Amount = new ModelColumn<>(I_ESR_ImportLine.class, "Amount", null);
	String COLUMNNAME_Amount = "Amount";

	/**
	 * Set Geschäftspartner-Schlüssel.
	 * Suchschlüssel für den Geschäftspartner
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setBPartner_Value (@Nullable java.lang.String BPartner_Value);

	/**
	 * Get Geschäftspartner-Schlüssel.
	 * Suchschlüssel für den Geschäftspartner
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getBPartner_Value();

	ModelColumn<I_ESR_ImportLine, Object> COLUMN_BPartner_Value = new ModelColumn<>(I_ESR_ImportLine.class, "BPartner_Value", null);
	String COLUMNNAME_BPartner_Value = "BPartner_Value";

	/**
	 * Set Bank Statement.
	 * Bank Statement of account
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_BankStatement_ID (int C_BankStatement_ID);

	/**
	 * Get Bank Statement.
	 * Bank Statement of account
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_BankStatement_ID();

	String COLUMNNAME_C_BankStatement_ID = "C_BankStatement_ID";

	/**
	 * Set Statement Line.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_BankStatementLine_ID (int C_BankStatementLine_ID);

	/**
	 * Get Statement Line.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_BankStatementLine_ID();

	String COLUMNNAME_C_BankStatementLine_ID = "C_BankStatementLine_ID";

	/**
	 * Set Bank Statement Line Reference.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_BankStatementLine_Ref_ID (int C_BankStatementLine_Ref_ID);

	/**
	 * Get Bank Statement Line Reference.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_BankStatementLine_Ref_ID();

	ModelColumn<I_ESR_ImportLine, Object> COLUMN_C_BankStatementLine_Ref_ID = new ModelColumn<>(I_ESR_ImportLine.class, "C_BankStatementLine_Ref_ID", null);
	String COLUMNNAME_C_BankStatementLine_Ref_ID = "C_BankStatementLine_Ref_ID";

	/**
	 * Set Business Partner.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_BPartner_ID (int C_BPartner_ID);

	/**
	 * Get Business Partner.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_BPartner_ID();

	String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/**
	 * Set Partner Bank Account.
	 * Bank Account of the Business Partner
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_BP_BankAccount_ID (int C_BP_BankAccount_ID);

	/**
	 * Get Partner Bank Account.
	 * Bank Account of the Business Partner
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_BP_BankAccount_ID();

	String COLUMNNAME_C_BP_BankAccount_ID = "C_BP_BankAccount_ID";

	/**
	 * Set Invoice.
	 * Invoice Identifier
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Invoice_ID (int C_Invoice_ID);

	/**
	 * Get Invoice.
	 * Invoice Identifier
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Invoice_ID();

	@Nullable org.compiere.model.I_C_Invoice getC_Invoice();

	void setC_Invoice(@Nullable org.compiere.model.I_C_Invoice C_Invoice);

	ModelColumn<I_ESR_ImportLine, org.compiere.model.I_C_Invoice> COLUMN_C_Invoice_ID = new ModelColumn<>(I_ESR_ImportLine.class, "C_Invoice_ID", org.compiere.model.I_C_Invoice.class);
	String COLUMNNAME_C_Invoice_ID = "C_Invoice_ID";

	/**
	 * Set Payments.
	 * Payment identifier
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Payment_ID (int C_Payment_ID);

	/**
	 * Get Payments.
	 * Payment identifier
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Payment_ID();

	String COLUMNNAME_C_Payment_ID = "C_Payment_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_ESR_ImportLine, Object> COLUMN_Created = new ModelColumn<>(I_ESR_ImportLine.class, "Created", null);
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
	 * Set Reference No.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_ReferenceNo_ID (int C_ReferenceNo_ID);

	/**
	 * Get Reference No.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_ReferenceNo_ID();

	ModelColumn<I_ESR_ImportLine, Object> COLUMN_C_ReferenceNo_ID = new ModelColumn<>(I_ESR_ImportLine.class, "C_ReferenceNo_ID", null);
	String COLUMNNAME_C_ReferenceNo_ID = "C_ReferenceNo_ID";

	/**
	 * Set Description.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDescription (@Nullable java.lang.String Description);

	/**
	 * Get Description.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDescription();

	ModelColumn<I_ESR_ImportLine, Object> COLUMN_Description = new ModelColumn<>(I_ESR_ImportLine.class, "Description", null);
	String COLUMNNAME_Description = "Description";

	/**
	 * Set Bereits zugeordnet.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setESR_Amount_Balance (@Nullable BigDecimal ESR_Amount_Balance);

	/**
	 * Get Bereits zugeordnet.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	BigDecimal getESR_Amount_Balance();

	ModelColumn<I_ESR_ImportLine, Object> COLUMN_ESR_Amount_Balance = new ModelColumn<>(I_ESR_ImportLine.class, "ESR_Amount_Balance", null);
	String COLUMNNAME_ESR_Amount_Balance = "ESR_Amount_Balance";

	/**
	 * Set ESR Rechnungsnummer.
	 * Belegnummer der zugeordneten Rechnung
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setESR_DocumentNo (@Nullable java.lang.String ESR_DocumentNo);

	/**
	 * Get ESR Rechnungsnummer.
	 * Belegnummer der zugeordneten Rechnung
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getESR_DocumentNo();

	ModelColumn<I_ESR_ImportLine, Object> COLUMN_ESR_DocumentNo = new ModelColumn<>(I_ESR_ImportLine.class, "ESR_DocumentNo", null);
	String COLUMNNAME_ESR_DocumentNo = "ESR_DocumentNo";

	/**
	 * Set Importstatus.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setESR_Document_Status (java.lang.String ESR_Document_Status);

	/**
	 * Get Importstatus.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getESR_Document_Status();

	ModelColumn<I_ESR_ImportLine, Object> COLUMN_ESR_Document_Status = new ModelColumn<>(I_ESR_ImportLine.class, "ESR_Document_Status", null);
	String COLUMNNAME_ESR_Document_Status = "ESR_Document_Status";

	/**
	 * Set ESR Referenznummer (komplett).
	 * Referenznummer inkl. bankinterner Teilnehmernummer
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setESRFullReferenceNumber (@Nullable java.lang.String ESRFullReferenceNumber);

	/**
	 * Get ESR Referenznummer (komplett).
	 * Referenznummer inkl. bankinterner Teilnehmernummer
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getESRFullReferenceNumber();

	ModelColumn<I_ESR_ImportLine, Object> COLUMN_ESRFullReferenceNumber = new ModelColumn<>(I_ESR_ImportLine.class, "ESRFullReferenceNumber", null);
	String COLUMNNAME_ESRFullReferenceNumber = "ESRFullReferenceNumber";

	/**
	 * Set ESR Zahlungsimport.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setESR_Import_ID (int ESR_Import_ID);

	/**
	 * Get ESR Zahlungsimport.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getESR_Import_ID();

	de.metas.payment.esr.model.I_ESR_Import getESR_Import();

	void setESR_Import(de.metas.payment.esr.model.I_ESR_Import ESR_Import);

	ModelColumn<I_ESR_ImportLine, de.metas.payment.esr.model.I_ESR_Import> COLUMN_ESR_Import_ID = new ModelColumn<>(I_ESR_ImportLine.class, "ESR_Import_ID", de.metas.payment.esr.model.I_ESR_Import.class);
	String COLUMNNAME_ESR_Import_ID = "ESR_Import_ID";

	/**
	 * Set ESR_ImportLine.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setESR_ImportLine_ID (int ESR_ImportLine_ID);

	/**
	 * Get ESR_ImportLine.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getESR_ImportLine_ID();

	ModelColumn<I_ESR_ImportLine, Object> COLUMN_ESR_ImportLine_ID = new ModelColumn<>(I_ESR_ImportLine.class, "ESR_ImportLine_ID", null);
	String COLUMNNAME_ESR_ImportLine_ID = "ESR_ImportLine_ID";

	/**
	 * Set Rechnungsbetrag.
	 * Gesamtbetrag der zugeordneten Rechnung
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setESR_Invoice_Grandtotal (@Nullable BigDecimal ESR_Invoice_Grandtotal);

	/**
	 * Get Rechnungsbetrag.
	 * Gesamtbetrag der zugeordneten Rechnung
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getESR_Invoice_Grandtotal();

	ModelColumn<I_ESR_ImportLine, Object> COLUMN_ESR_Invoice_Grandtotal = new ModelColumn<>(I_ESR_ImportLine.class, "ESR_Invoice_Grandtotal", null);
	String COLUMNNAME_ESR_Invoice_Grandtotal = "ESR_Invoice_Grandtotal";

	/**
	 * Set Offener Rechnungsbetrag.
	 * Noch offener Betrag der zugeordneten Rechnung, Abzüglich der ESR-Zahlbeträge aus der/den Zeile(n) der vorliegenden Import-Datei.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setESR_Invoice_Openamt (@Nullable BigDecimal ESR_Invoice_Openamt);

	/**
	 * Get Offener Rechnungsbetrag.
	 * Noch offener Betrag der zugeordneten Rechnung, Abzüglich der ESR-Zahlbeträge aus der/den Zeile(n) der vorliegenden Import-Datei.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getESR_Invoice_Openamt();

	ModelColumn<I_ESR_ImportLine, Object> COLUMN_ESR_Invoice_Openamt = new ModelColumn<>(I_ESR_ImportLine.class, "ESR_Invoice_Openamt", null);
	String COLUMNNAME_ESR_Invoice_Openamt = "ESR_Invoice_Openamt";

	/**
	 * Set Extern erstellte ESR-Referenz.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setESR_IsManual_ReferenceNo (boolean ESR_IsManual_ReferenceNo);

	/**
	 * Get Extern erstellte ESR-Referenz.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isESR_IsManual_ReferenceNo();

	ModelColumn<I_ESR_ImportLine, Object> COLUMN_ESR_IsManual_ReferenceNo = new ModelColumn<>(I_ESR_ImportLine.class, "ESR_IsManual_ReferenceNo", null);
	String COLUMNNAME_ESR_IsManual_ReferenceNo = "ESR_IsManual_ReferenceNo";

	/**
	 * Set Importierte ESR-Zeile.
	 * ESR complete line text
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setESRLineText (java.lang.String ESRLineText);

	/**
	 * Get Importierte ESR-Zeile.
	 * ESR complete line text
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getESRLineText();

	ModelColumn<I_ESR_ImportLine, Object> COLUMN_ESRLineText = new ModelColumn<>(I_ESR_ImportLine.class, "ESRLineText", null);
	String COLUMNNAME_ESRLineText = "ESRLineText";

	/**
	 * Set ESR_Payment_Action.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setESR_Payment_Action (@Nullable java.lang.String ESR_Payment_Action);

	/**
	 * Get ESR_Payment_Action.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getESR_Payment_Action();

	ModelColumn<I_ESR_ImportLine, Object> COLUMN_ESR_Payment_Action = new ModelColumn<>(I_ESR_ImportLine.class, "ESR_Payment_Action", null);
	String COLUMNNAME_ESR_Payment_Action = "ESR_Payment_Action";

	/**
	 * Set Post-Teilnehmernummer.
	 * Post-Teilnehmernummer
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setESRPostParticipantNumber (@Nullable java.lang.String ESRPostParticipantNumber);

	/**
	 * Get Post-Teilnehmernummer.
	 * Post-Teilnehmernummer
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getESRPostParticipantNumber();

	ModelColumn<I_ESR_ImportLine, Object> COLUMN_ESRPostParticipantNumber = new ModelColumn<>(I_ESR_ImportLine.class, "ESRPostParticipantNumber", null);
	String COLUMNNAME_ESRPostParticipantNumber = "ESRPostParticipantNumber";

	/**
	 * Set ESR Referenznummer.
	 * Referenznummer der jeweiligen Rechnung
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setESRReferenceNumber (@Nullable java.lang.String ESRReferenceNumber);

	/**
	 * Get ESR Referenznummer.
	 * Referenznummer der jeweiligen Rechnung
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getESRReferenceNumber();

	ModelColumn<I_ESR_ImportLine, Object> COLUMN_ESRReferenceNumber = new ModelColumn<>(I_ESR_ImportLine.class, "ESRReferenceNumber", null);
	String COLUMNNAME_ESRReferenceNumber = "ESRReferenceNumber";

	/**
	 * Set Transaktionsart.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setESRTrxType (@Nullable java.lang.String ESRTrxType);

	/**
	 * Get Transaktionsart.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getESRTrxType();

	ModelColumn<I_ESR_ImportLine, Object> COLUMN_ESRTrxType = new ModelColumn<>(I_ESR_ImportLine.class, "ESRTrxType", null);
	String COLUMNNAME_ESRTrxType = "ESRTrxType";

	/**
	 * Set Error Message.
	 * Fehler beim Einlesen der Datei, z.B. Fehler im Format eines Datums
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setImportErrorMsg (@Nullable java.lang.String ImportErrorMsg);

	/**
	 * Get Error Message.
	 * Fehler beim Einlesen der Datei, z.B. Fehler im Format eines Datums
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getImportErrorMsg();

	ModelColumn<I_ESR_ImportLine, Object> COLUMN_ImportErrorMsg = new ModelColumn<>(I_ESR_ImportLine.class, "ImportErrorMsg", null);
	String COLUMNNAME_ImportErrorMsg = "ImportErrorMsg";

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

	ModelColumn<I_ESR_ImportLine, Object> COLUMN_IsActive = new ModelColumn<>(I_ESR_ImportLine.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Manuell.
	 * Dies ist ein manueller Vorgang
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIsManual (boolean IsManual);

	/**
	 * Get Manuell.
	 * Dies ist ein manueller Vorgang
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isManual();

	ModelColumn<I_ESR_ImportLine, Object> COLUMN_IsManual = new ModelColumn<>(I_ESR_ImportLine.class, "IsManual", null);
	String COLUMNNAME_IsManual = "IsManual";

	/**
	 * Set Is Valid.
	 * The element is valid
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsValid (boolean IsValid);

	/**
	 * Get Is Valid.
	 * The element is valid
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isValid();

	ModelColumn<I_ESR_ImportLine, Object> COLUMN_IsValid = new ModelColumn<>(I_ESR_ImportLine.class, "IsValid", null);
	String COLUMNNAME_IsValid = "IsValid";

	/**
	 * Set Line.
	 * Line No
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setLineNo (int LineNo);

	/**
	 * Get Line.
	 * Line No
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getLineNo();

	ModelColumn<I_ESR_ImportLine, Object> COLUMN_LineNo = new ModelColumn<>(I_ESR_ImportLine.class, "LineNo", null);
	String COLUMNNAME_LineNo = "LineNo";

	/**
	 * Set Zuordnungsfehler.
	 * Fehler beim Zuordnen der Daten, z.B. fehlende Rechnung für eine eingelesene Referenznummer
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setMatchErrorMsg (@Nullable java.lang.String MatchErrorMsg);

	/**
	 * Get Zuordnungsfehler.
	 * Fehler beim Zuordnen der Daten, z.B. fehlende Rechnung für eine eingelesene Referenznummer
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getMatchErrorMsg();

	ModelColumn<I_ESR_ImportLine, Object> COLUMN_MatchErrorMsg = new ModelColumn<>(I_ESR_ImportLine.class, "MatchErrorMsg", null);
	String COLUMNNAME_MatchErrorMsg = "MatchErrorMsg";

	/**
	 * Set Organisation.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setOrg_ID (int Org_ID);

	/**
	 * Get Organisation.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getOrg_ID();

	String COLUMNNAME_Org_ID = "Org_ID";

	/**
	 * Set Zahldatum.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPaymentDate (@Nullable java.sql.Timestamp PaymentDate);

	/**
	 * Get Zahldatum.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getPaymentDate();

	ModelColumn<I_ESR_ImportLine, Object> COLUMN_PaymentDate = new ModelColumn<>(I_ESR_ImportLine.class, "PaymentDate", null);
	String COLUMNNAME_PaymentDate = "PaymentDate";

	/**
	 * Set Processed.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setProcessed (boolean Processed);

	/**
	 * Get Processed.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isProcessed();

	ModelColumn<I_ESR_ImportLine, Object> COLUMN_Processed = new ModelColumn<>(I_ESR_ImportLine.class, "Processed", null);
	String COLUMNNAME_Processed = "Processed";

	/**
	 * Set ESR Sektion.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setSektionNo (@Nullable java.lang.String SektionNo);

	/**
	 * Get ESR Sektion.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getSektionNo();

	ModelColumn<I_ESR_ImportLine, Object> COLUMN_SektionNo = new ModelColumn<>(I_ESR_ImportLine.class, "SektionNo", null);
	String COLUMNNAME_SektionNo = "SektionNo";

	/**
	 * Set Type.
	 * Type of Validation (SQL, Java Script, Java Language)
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setType (java.lang.String Type);

	/**
	 * Get Type.
	 * Type of Validation (SQL, Java Script, Java Language)
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getType();

	ModelColumn<I_ESR_ImportLine, Object> COLUMN_Type = new ModelColumn<>(I_ESR_ImportLine.class, "Type", null);
	String COLUMNNAME_Type = "Type";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_ESR_ImportLine, Object> COLUMN_Updated = new ModelColumn<>(I_ESR_ImportLine.class, "Updated", null);
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
