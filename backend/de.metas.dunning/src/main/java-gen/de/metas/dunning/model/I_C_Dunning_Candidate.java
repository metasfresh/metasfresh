package de.metas.dunning.model;

import org.adempiere.model.ModelColumn;
import org.compiere.model.I_C_DunningLevel;
import org.compiere.model.I_M_SectionCode;

import javax.annotation.Nullable;
import java.math.BigDecimal;

/** Generated Interface for C_Dunning_Candidate
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_C_Dunning_Candidate
{

	String Table_Name = "C_Dunning_Candidate";

//	/** AD_Table_ID=540396 */
//	int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

    /** AccessLevel = 1 - Org
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(1);

    /** Load Meta Data */

	/**
	 * Get Mandant.
	 * Mandant für diese Installation.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_Client_ID();

	String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Sektion.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Sektion.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_Org_ID();

	String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set DB-Tabelle.
	 * Database Table information
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_Table_ID (int AD_Table_ID);

	/**
	 * Get DB-Tabelle.
	 * Database Table information
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_Table_ID();

	String COLUMNNAME_AD_Table_ID = "AD_Table_ID";

	/**
	 * Set Geschäftspartner.
	 * Bezeichnet einen Geschäftspartner
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_BPartner_ID (int C_BPartner_ID);

	/**
	 * Get Geschäftspartner.
	 * Bezeichnet einen Geschäftspartner
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_BPartner_ID();

	String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/**
	 * Set Location.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_BPartner_Location_ID (int C_BPartner_Location_ID);

	/**
	 * Get Location.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_BPartner_Location_ID();

	String COLUMNNAME_C_BPartner_Location_ID = "C_BPartner_Location_ID";

	/**
	 * Set Currency.
	 * The Currency for this record
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Currency_ID (int C_Currency_ID);

	/**
	 * Get Währung.
	 * Die Währung für diesen Eintrag
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Currency_ID();

	String COLUMNNAME_C_Currency_ID = "C_Currency_ID";

	/**
	 * Set Mahnungsdisposition.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Dunning_Candidate_ID (int C_Dunning_Candidate_ID);

	/**
	 * Get Mahnungsdisposition.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Dunning_Candidate_ID();

	ModelColumn<I_C_Dunning_Candidate, Object> COLUMN_C_Dunning_Candidate_ID = new ModelColumn<>(I_C_Dunning_Candidate.class, "C_Dunning_Candidate_ID", null);
	String COLUMNNAME_C_Dunning_Candidate_ID = "C_Dunning_Candidate_ID";

	/**
	 * Set Mahnkontakt.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Dunning_Contact_ID (int C_Dunning_Contact_ID);

	/**
	 * Get Mahnkontakt.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Dunning_Contact_ID();

	String COLUMNNAME_C_Dunning_Contact_ID = "C_Dunning_Contact_ID";

	/**
	 * Set Mahnstufe.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_DunningLevel_ID (int C_DunningLevel_ID);

	/**
	 * Get Mahnstufe.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_DunningLevel_ID();

	org.compiere.model.I_C_DunningLevel getC_DunningLevel();

	void setC_DunningLevel(org.compiere.model.I_C_DunningLevel C_DunningLevel);

	ModelColumn<I_C_Dunning_Candidate, I_C_DunningLevel> COLUMN_C_DunningLevel_ID = new ModelColumn<>(I_C_Dunning_Candidate.class, "C_DunningLevel_ID", org.compiere.model.I_C_DunningLevel.class);
	String COLUMNNAME_C_DunningLevel_ID = "C_DunningLevel_ID";

	/**
	 * Get Erstellt.
	 * Datum, an dem dieser Eintrag erstellt wurde
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getCreated();

	ModelColumn<I_C_Dunning_Candidate, Object> COLUMN_Created = new ModelColumn<>(I_C_Dunning_Candidate.class, "Created", null);
	String COLUMNNAME_Created = "Created";

	/**
	 * Get Erstellt durch.
	 * Nutzer, der diesen Eintrag erstellt hat
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getCreatedBy();

	String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Tage fällig.
	 * Anzahl der Tage der Fälligkeit (negativ: Fällig in Anzahl Tagen)
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDaysDue (int DaysDue);

	/**
	 * Get Tage fällig.
	 * Anzahl der Tage der Fälligkeit (negativ: Fällig in Anzahl Tagen)
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getDaysDue();

	ModelColumn<I_C_Dunning_Candidate, Object> COLUMN_DaysDue = new ModelColumn<>(I_C_Dunning_Candidate.class, "DaysDue", null);
	String COLUMNNAME_DaysDue = "DaysDue";

	/**
	 * Set Nr..
	 * Document sequence number of the document
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDocumentNo (@Nullable java.lang.String DocumentNo);

	/**
	 * Get Nr..
	 * Document sequence number of the document
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDocumentNo();

	ModelColumn<I_C_Dunning_Candidate, Object> COLUMN_DocumentNo = new ModelColumn<>(I_C_Dunning_Candidate.class, "DocumentNo", null);
	String COLUMNNAME_DocumentNo = "DocumentNo";

	/**
	 * Set Datum Fälligkeit.
	 * Datum, zu dem Zahlung fällig wird
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDueDate (java.sql.Timestamp DueDate);

	/**
	 * Get Datum Fälligkeit.
	 * Datum, zu dem Zahlung fällig wird
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getDueDate();

	ModelColumn<I_C_Dunning_Candidate, Object> COLUMN_DueDate = new ModelColumn<>(I_C_Dunning_Candidate.class, "DueDate", null);
	String COLUMNNAME_DueDate = "DueDate";

	/**
	 * Set Dunning Date.
	 * Date of Dunning
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDunningDate (java.sql.Timestamp DunningDate);

	/**
	 * Get Dunning Date.
	 * Date of Dunning
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getDunningDate();

	ModelColumn<I_C_Dunning_Candidate, Object> COLUMN_DunningDate = new ModelColumn<>(I_C_Dunning_Candidate.class, "DunningDate", null);
	String COLUMNNAME_DunningDate = "DunningDate";

	/**
	 * Set Dunning Date Effective.
	 * Effective Date of Dunning
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDunningDateEffective (@Nullable java.sql.Timestamp DunningDateEffective);

	/**
	 * Get Dunning Date Effective.
	 * Effective Date of Dunning
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getDunningDateEffective();

	ModelColumn<I_C_Dunning_Candidate, Object> COLUMN_DunningDateEffective = new ModelColumn<>(I_C_Dunning_Candidate.class, "DunningDateEffective", null);
	String COLUMNNAME_DunningDateEffective = "DunningDateEffective";

	/**
	 * Set Dunning Grace Date.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDunningGrace (@Nullable java.sql.Timestamp DunningGrace);

	/**
	 * Get Dunning Grace Date.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getDunningGrace();

	ModelColumn<I_C_Dunning_Candidate, Object> COLUMN_DunningGrace = new ModelColumn<>(I_C_Dunning_Candidate.class, "DunningGrace", null);
	String COLUMNNAME_DunningGrace = "DunningGrace";

	/**
	 * Set Mahnzins.
	 * Prozentualer Anteil der offenen Summe, der als zusätzliche Mahngebühr ausgewiesen wird.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDunningInterestAmt (@Nullable BigDecimal DunningInterestAmt);

	/**
	 * Get Mahnzins.
	 * Prozentualer Anteil der offenen Summe, der als zusätzliche Mahngebühr ausgewiesen wird.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getDunningInterestAmt();

	ModelColumn<I_C_Dunning_Candidate, Object> COLUMN_DunningInterestAmt = new ModelColumn<>(I_C_Dunning_Candidate.class, "DunningInterestAmt", null);
	String COLUMNNAME_DunningInterestAmt = "DunningInterestAmt";

	/**
	 * Set Mahnpauschale.
	 * Pauschale Mahngebühr
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setFeeAmt (@Nullable BigDecimal FeeAmt);

	/**
	 * Get Mahnpauschale.
	 * Pauschale Mahngebühr
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getFeeAmt();

	ModelColumn<I_C_Dunning_Candidate, Object> COLUMN_FeeAmt = new ModelColumn<>(I_C_Dunning_Candidate.class, "FeeAmt", null);
	String COLUMNNAME_FeeAmt = "FeeAmt";

	/**
	 * Set Aktiv.
	 * Der Eintrag ist im System aktiv
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsActive (boolean IsActive);

	/**
	 * Get Aktiv.
	 * Der Eintrag ist im System aktiv
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isActive();

	ModelColumn<I_C_Dunning_Candidate, Object> COLUMN_IsActive = new ModelColumn<>(I_C_Dunning_Candidate.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set IsDunningDocProcessed.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIsDunningDocProcessed (boolean IsDunningDocProcessed);

	/**
	 * Get IsDunningDocProcessed.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isDunningDocProcessed();

	ModelColumn<I_C_Dunning_Candidate, Object> COLUMN_IsDunningDocProcessed = new ModelColumn<>(I_C_Dunning_Candidate.class, "IsDunningDocProcessed", null);
	String COLUMNNAME_IsDunningDocProcessed = "IsDunningDocProcessed";

	/**
	 * Set Massenaustritt.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsWriteOff (boolean IsWriteOff);

	/**
	 * Get Massenaustritt.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isWriteOff();

	ModelColumn<I_C_Dunning_Candidate, Object> COLUMN_IsWriteOff = new ModelColumn<>(I_C_Dunning_Candidate.class, "IsWriteOff", null);
    String COLUMNNAME_IsWriteOff = "IsWriteOff";

	/**
	 * Set Section Code.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_SectionCode_ID (int M_SectionCode_ID);

	/**
	 * Get Section Code.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_SectionCode_ID();

	ModelColumn<I_C_Dunning_Candidate, I_M_SectionCode> COLUMN_M_SectionCode_ID = new ModelColumn<>(I_C_Dunning_Candidate.class, "M_SectionCode_ID", org.compiere.model.I_M_SectionCode.class);
	String COLUMNNAME_M_SectionCode_ID = "M_SectionCode_ID";
	/**
	 * Set Offener Betrag.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setOpenAmt (@Nullable BigDecimal OpenAmt);

	/**
	 * Get Offener Betrag.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getOpenAmt();

	ModelColumn<I_C_Dunning_Candidate, Object> COLUMN_OpenAmt = new ModelColumn<>(I_C_Dunning_Candidate.class, "OpenAmt", null);
	String COLUMNNAME_OpenAmt = "OpenAmt";

	/**
	 * Set Order Reference.
	 * Transaction Reference Number (Sales Order, Purchase Order) of your Business Partner
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPOReference (@Nullable java.lang.String POReference);

	/**
	 * Get Order Reference.
	 * Transaction Reference Number (Sales Order, Purchase Order) of your Business Partner
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getPOReference();

	org.adempiere.model.ModelColumn<I_C_Dunning_Candidate, Object> COLUMN_POReference = new org.adempiere.model.ModelColumn<>(I_C_Dunning_Candidate.class, "POReference", null);
	String COLUMNNAME_POReference = "POReference";

	/**
	 * Set Verarbeitet.
	 * Checkbox sagt aus, ob der Beleg verarbeitet wurde.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setProcessed (boolean Processed);

	/**
	 * Get Verarbeitet.
	 * Checkbox sagt aus, ob der Beleg verarbeitet wurde.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isProcessed();

	ModelColumn<I_C_Dunning_Candidate, Object> COLUMN_Processed = new ModelColumn<>(I_C_Dunning_Candidate.class, "Processed", null);
	String COLUMNNAME_Processed = "Processed";

	/**
	 * Set Datensatz-ID.
	 * Direct internal record ID
	 *
	 * <br>Type: Button
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setRecord_ID (int Record_ID);

	/**
	 * Get Datensatz-ID.
	 * Direct internal record ID
	 *
	 * <br>Type: Button
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getRecord_ID();

	ModelColumn<I_C_Dunning_Candidate, Object> COLUMN_Record_ID = new ModelColumn<>(I_C_Dunning_Candidate.class, "Record_ID", null);
	String COLUMNNAME_Record_ID = "Record_ID";

	/**
	 * Set Gesamtbetrag.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setTotalAmt (@Nullable BigDecimal TotalAmt);

	/**
	 * Get Gesamtbetrag.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getTotalAmt();

	ModelColumn<I_C_Dunning_Candidate, Object> COLUMN_TotalAmt = new ModelColumn<>(I_C_Dunning_Candidate.class, "TotalAmt", null);
	String COLUMNNAME_TotalAmt = "TotalAmt";

	/**
	 * Get Aktualisiert.
	 * Datum, an dem dieser Eintrag aktualisiert wurde
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_C_Dunning_Candidate, Object> COLUMN_Updated = new ModelColumn<>(I_C_Dunning_Candidate.class, "Updated", null);
	String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Aktualisiert durch.
	 * Nutzer, der diesen Eintrag aktualisiert hat
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getUpdatedBy();

	String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
