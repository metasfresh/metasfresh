package de.metas.dunning.model;

import org.adempiere.model.ModelColumn;
import org.compiere.model.I_M_SectionCode;

import javax.annotation.Nullable;

/** Generated Interface for C_Dunning_Candidate
 *  @author Adempiere (generated)
 */
@SuppressWarnings("javadoc")
public interface I_C_Dunning_Candidate
{

	/** TableName=C_Dunning_Candidate */
	public static final String Table_Name = "C_Dunning_Candidate";

	/** AD_Table_ID=540396 */
	//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

	//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

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
	public int getAD_Client_ID();

	public org.compiere.model.I_AD_Client getAD_Client();

	/** Column definition for AD_Client_ID */
	public static final org.adempiere.model.ModelColumn<I_C_Dunning_Candidate, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_C_Dunning_Candidate, org.compiere.model.I_AD_Client>(I_C_Dunning_Candidate.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
	/** Column name AD_Client_ID */
	public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Sektion.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Sektion.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Org_ID();

	public org.compiere.model.I_AD_Org getAD_Org();

	public void setAD_Org(org.compiere.model.I_AD_Org AD_Org);

	/** Column definition for AD_Org_ID */
	public static final org.adempiere.model.ModelColumn<I_C_Dunning_Candidate, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_C_Dunning_Candidate, org.compiere.model.I_AD_Org>(I_C_Dunning_Candidate.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
	/** Column name AD_Org_ID */
	public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set DB-Tabelle.
	 * Database Table information
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_Table_ID (int AD_Table_ID);

	/**
	 * Get DB-Tabelle.
	 * Database Table information
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Table_ID();

	public org.compiere.model.I_AD_Table getAD_Table();

	public void setAD_Table(org.compiere.model.I_AD_Table AD_Table);

	/** Column definition for AD_Table_ID */
	public static final org.adempiere.model.ModelColumn<I_C_Dunning_Candidate, org.compiere.model.I_AD_Table> COLUMN_AD_Table_ID = new org.adempiere.model.ModelColumn<I_C_Dunning_Candidate, org.compiere.model.I_AD_Table>(I_C_Dunning_Candidate.class, "AD_Table_ID", org.compiere.model.I_AD_Table.class);
	/** Column name AD_Table_ID */
	public static final String COLUMNNAME_AD_Table_ID = "AD_Table_ID";

	/**
	 * Set Geschäftspartner.
	 * Bezeichnet einen Geschäftspartner
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_BPartner_ID (int C_BPartner_ID);

	/**
	 * Get Geschäftspartner.
	 * Bezeichnet einen Geschäftspartner
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_BPartner_ID();

	public org.compiere.model.I_C_BPartner getC_BPartner();

	public void setC_BPartner(org.compiere.model.I_C_BPartner C_BPartner);

	/** Column definition for C_BPartner_ID */
	public static final org.adempiere.model.ModelColumn<I_C_Dunning_Candidate, org.compiere.model.I_C_BPartner> COLUMN_C_BPartner_ID = new org.adempiere.model.ModelColumn<I_C_Dunning_Candidate, org.compiere.model.I_C_BPartner>(I_C_Dunning_Candidate.class, "C_BPartner_ID", org.compiere.model.I_C_BPartner.class);
	/** Column name C_BPartner_ID */
	public static final String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/**
	 * Set Standort.
	 * Identifiziert die (Liefer-) Adresse des Geschäftspartners
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_BPartner_Location_ID (int C_BPartner_Location_ID);

	/**
	 * Get Standort.
	 * Identifiziert die (Liefer-) Adresse des Geschäftspartners
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_BPartner_Location_ID();

	public org.compiere.model.I_C_BPartner_Location getC_BPartner_Location();

	public void setC_BPartner_Location(org.compiere.model.I_C_BPartner_Location C_BPartner_Location);

	/** Column definition for C_BPartner_Location_ID */
	public static final org.adempiere.model.ModelColumn<I_C_Dunning_Candidate, org.compiere.model.I_C_BPartner_Location> COLUMN_C_BPartner_Location_ID = new org.adempiere.model.ModelColumn<I_C_Dunning_Candidate, org.compiere.model.I_C_BPartner_Location>(I_C_Dunning_Candidate.class, "C_BPartner_Location_ID", org.compiere.model.I_C_BPartner_Location.class);
	/** Column name C_BPartner_Location_ID */
	public static final String COLUMNNAME_C_BPartner_Location_ID = "C_BPartner_Location_ID";

	/**
	 * Set Währung.
	 * Die Währung für diesen Eintrag
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_Currency_ID (int C_Currency_ID);

	/**
	 * Get Währung.
	 * Die Währung für diesen Eintrag
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_Currency_ID();

	public org.compiere.model.I_C_Currency getC_Currency();

	public void setC_Currency(org.compiere.model.I_C_Currency C_Currency);

	/** Column definition for C_Currency_ID */
	public static final org.adempiere.model.ModelColumn<I_C_Dunning_Candidate, org.compiere.model.I_C_Currency> COLUMN_C_Currency_ID = new org.adempiere.model.ModelColumn<I_C_Dunning_Candidate, org.compiere.model.I_C_Currency>(I_C_Dunning_Candidate.class, "C_Currency_ID", org.compiere.model.I_C_Currency.class);
	/** Column name C_Currency_ID */
	public static final String COLUMNNAME_C_Currency_ID = "C_Currency_ID";

	/**
	 * Set Mahnungsdisposition.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_Dunning_Candidate_ID (int C_Dunning_Candidate_ID);

	/**
	 * Get Mahnungsdisposition.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_Dunning_Candidate_ID();

	/** Column definition for C_Dunning_Candidate_ID */
	public static final org.adempiere.model.ModelColumn<I_C_Dunning_Candidate, Object> COLUMN_C_Dunning_Candidate_ID = new org.adempiere.model.ModelColumn<I_C_Dunning_Candidate, Object>(I_C_Dunning_Candidate.class, "C_Dunning_Candidate_ID", null);
	/** Column name C_Dunning_Candidate_ID */
	public static final String COLUMNNAME_C_Dunning_Candidate_ID = "C_Dunning_Candidate_ID";

	/**
	 * Set Mahnkontakt.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Dunning_Contact_ID (int C_Dunning_Contact_ID);

	/**
	 * Get Mahnkontakt.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Dunning_Contact_ID();

	public org.compiere.model.I_AD_User getC_Dunning_Contact();

	public void setC_Dunning_Contact(org.compiere.model.I_AD_User C_Dunning_Contact);

	/** Column definition for C_Dunning_Contact_ID */
	public static final org.adempiere.model.ModelColumn<I_C_Dunning_Candidate, org.compiere.model.I_AD_User> COLUMN_C_Dunning_Contact_ID = new org.adempiere.model.ModelColumn<I_C_Dunning_Candidate, org.compiere.model.I_AD_User>(I_C_Dunning_Candidate.class, "C_Dunning_Contact_ID", org.compiere.model.I_AD_User.class);
	/** Column name C_Dunning_Contact_ID */
	public static final String COLUMNNAME_C_Dunning_Contact_ID = "C_Dunning_Contact_ID";

	/**
	 * Set Mahnstufe.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_DunningLevel_ID (int C_DunningLevel_ID);

	/**
	 * Get Mahnstufe.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_DunningLevel_ID();

	public org.compiere.model.I_C_DunningLevel getC_DunningLevel();

	public void setC_DunningLevel(org.compiere.model.I_C_DunningLevel C_DunningLevel);

	/** Column definition for C_DunningLevel_ID */
	public static final org.adempiere.model.ModelColumn<I_C_Dunning_Candidate, org.compiere.model.I_C_DunningLevel> COLUMN_C_DunningLevel_ID = new org.adempiere.model.ModelColumn<I_C_Dunning_Candidate, org.compiere.model.I_C_DunningLevel>(I_C_Dunning_Candidate.class, "C_DunningLevel_ID", org.compiere.model.I_C_DunningLevel.class);
	/** Column name C_DunningLevel_ID */
	public static final String COLUMNNAME_C_DunningLevel_ID = "C_DunningLevel_ID";

	/**
	 * Get Erstellt.
	 * Datum, an dem dieser Eintrag erstellt wurde
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getCreated();

	/** Column definition for Created */
	public static final org.adempiere.model.ModelColumn<I_C_Dunning_Candidate, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_C_Dunning_Candidate, Object>(I_C_Dunning_Candidate.class, "Created", null);
	/** Column name Created */
	public static final String COLUMNNAME_Created = "Created";

	/**
	 * Get Erstellt durch.
	 * Nutzer, der diesen Eintrag erstellt hat
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getCreatedBy();

	/** Column definition for CreatedBy */
	public static final org.adempiere.model.ModelColumn<I_C_Dunning_Candidate, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_C_Dunning_Candidate, org.compiere.model.I_AD_User>(I_C_Dunning_Candidate.class, "CreatedBy", org.compiere.model.I_AD_User.class);
	/** Column name CreatedBy */
	public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Tage fällig.
	 * Anzahl der Tage der Fälligkeit (negativ: Fällig in Anzahl Tagen)
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDaysDue (int DaysDue);

	/**
	 * Get Tage fällig.
	 * Anzahl der Tage der Fälligkeit (negativ: Fällig in Anzahl Tagen)
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getDaysDue();

	/** Column definition for DaysDue */
	public static final org.adempiere.model.ModelColumn<I_C_Dunning_Candidate, Object> COLUMN_DaysDue = new org.adempiere.model.ModelColumn<I_C_Dunning_Candidate, Object>(I_C_Dunning_Candidate.class, "DaysDue", null);
	/** Column name DaysDue */
	public static final String COLUMNNAME_DaysDue = "DaysDue";

	/**
	 * Set Nr..
	 * Document sequence number of the document
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDocumentNo (java.lang.String DocumentNo);

	/**
	 * Get Nr..
	 * Document sequence number of the document
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDocumentNo();

	/** Column definition for DocumentNo */
	public static final org.adempiere.model.ModelColumn<I_C_Dunning_Candidate, Object> COLUMN_DocumentNo = new org.adempiere.model.ModelColumn<I_C_Dunning_Candidate, Object>(I_C_Dunning_Candidate.class, "DocumentNo", null);
	/** Column name DocumentNo */
	public static final String COLUMNNAME_DocumentNo = "DocumentNo";

	/**
	 * Set Datum Fälligkeit.
	 * Datum, zu dem Zahlung fällig wird
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDueDate (java.sql.Timestamp DueDate);

	/**
	 * Get Datum Fälligkeit.
	 * Datum, zu dem Zahlung fällig wird
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getDueDate();

	/** Column definition for DueDate */
	public static final org.adempiere.model.ModelColumn<I_C_Dunning_Candidate, Object> COLUMN_DueDate = new org.adempiere.model.ModelColumn<I_C_Dunning_Candidate, Object>(I_C_Dunning_Candidate.class, "DueDate", null);
	/** Column name DueDate */
	public static final String COLUMNNAME_DueDate = "DueDate";

	/**
	 * Set Dunning Date.
	 * Date of Dunning
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDunningDate (java.sql.Timestamp DunningDate);

	/**
	 * Get Dunning Date.
	 * Date of Dunning
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getDunningDate();

	/** Column definition for DunningDate */
	public static final org.adempiere.model.ModelColumn<I_C_Dunning_Candidate, Object> COLUMN_DunningDate = new org.adempiere.model.ModelColumn<I_C_Dunning_Candidate, Object>(I_C_Dunning_Candidate.class, "DunningDate", null);
	/** Column name DunningDate */
	public static final String COLUMNNAME_DunningDate = "DunningDate";

	/**
	 * Set Dunning Date Effective.
	 * Effective Date of Dunning
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDunningDateEffective (java.sql.Timestamp DunningDateEffective);

	/**
	 * Get Dunning Date Effective.
	 * Effective Date of Dunning
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getDunningDateEffective();

	/** Column definition for DunningDateEffective */
	public static final org.adempiere.model.ModelColumn<I_C_Dunning_Candidate, Object> COLUMN_DunningDateEffective = new org.adempiere.model.ModelColumn<I_C_Dunning_Candidate, Object>(I_C_Dunning_Candidate.class, "DunningDateEffective", null);
	/** Column name DunningDateEffective */
	public static final String COLUMNNAME_DunningDateEffective = "DunningDateEffective";

	/**
	 * Set Dunning Grace Date.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDunningGrace (java.sql.Timestamp DunningGrace);

	/**
	 * Get Dunning Grace Date.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getDunningGrace();

	/** Column definition for DunningGrace */
	public static final org.adempiere.model.ModelColumn<I_C_Dunning_Candidate, Object> COLUMN_DunningGrace = new org.adempiere.model.ModelColumn<I_C_Dunning_Candidate, Object>(I_C_Dunning_Candidate.class, "DunningGrace", null);
	/** Column name DunningGrace */
	public static final String COLUMNNAME_DunningGrace = "DunningGrace";

	/**
	 * Set Mahnzins.
	 * Prozentualer Anteil der offenen Summe, der als zusätzliche Mahngebühr ausgewiesen wird.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDunningInterestAmt (java.math.BigDecimal DunningInterestAmt);

	/**
	 * Get Mahnzins.
	 * Prozentualer Anteil der offenen Summe, der als zusätzliche Mahngebühr ausgewiesen wird.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getDunningInterestAmt();

	/** Column definition for DunningInterestAmt */
	public static final org.adempiere.model.ModelColumn<I_C_Dunning_Candidate, Object> COLUMN_DunningInterestAmt = new org.adempiere.model.ModelColumn<I_C_Dunning_Candidate, Object>(I_C_Dunning_Candidate.class, "DunningInterestAmt", null);
	/** Column name DunningInterestAmt */
	public static final String COLUMNNAME_DunningInterestAmt = "DunningInterestAmt";

	/**
	 * Set Mahnpauschale.
	 * Pauschale Mahngebühr
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setFeeAmt (java.math.BigDecimal FeeAmt);

	/**
	 * Get Mahnpauschale.
	 * Pauschale Mahngebühr
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getFeeAmt();

	/** Column definition for FeeAmt */
	public static final org.adempiere.model.ModelColumn<I_C_Dunning_Candidate, Object> COLUMN_FeeAmt = new org.adempiere.model.ModelColumn<I_C_Dunning_Candidate, Object>(I_C_Dunning_Candidate.class, "FeeAmt", null);
	/** Column name FeeAmt */
	public static final String COLUMNNAME_FeeAmt = "FeeAmt";

	/**
	 * Set Aktiv.
	 * Der Eintrag ist im System aktiv
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsActive (boolean IsActive);

	/**
	 * Get Aktiv.
	 * Der Eintrag ist im System aktiv
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isActive();

	/** Column definition for IsActive */
	public static final org.adempiere.model.ModelColumn<I_C_Dunning_Candidate, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_C_Dunning_Candidate, Object>(I_C_Dunning_Candidate.class, "IsActive", null);
	/** Column name IsActive */
	public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set IsDunningDocProcessed.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setIsDunningDocProcessed (boolean IsDunningDocProcessed);

	/**
	 * Get IsDunningDocProcessed.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isDunningDocProcessed();

	/** Column definition for IsDunningDocProcessed */
	public static final org.adempiere.model.ModelColumn<I_C_Dunning_Candidate, Object> COLUMN_IsDunningDocProcessed = new org.adempiere.model.ModelColumn<I_C_Dunning_Candidate, Object>(I_C_Dunning_Candidate.class, "IsDunningDocProcessed", null);
	/** Column name IsDunningDocProcessed */
	public static final String COLUMNNAME_IsDunningDocProcessed = "IsDunningDocProcessed";

	/**
	 * Set Massenaustritt.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsWriteOff (boolean IsWriteOff);

	/**
	 * Get Massenaustritt.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isWriteOff();

	/** Column definition for IsWriteOff */
	public static final org.adempiere.model.ModelColumn<I_C_Dunning_Candidate, Object> COLUMN_IsWriteOff = new org.adempiere.model.ModelColumn<I_C_Dunning_Candidate, Object>(I_C_Dunning_Candidate.class, "IsWriteOff", null);
	/** Column name IsWriteOff */
	public static final String COLUMNNAME_IsWriteOff = "IsWriteOff";

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

	@Nullable
	org.compiere.model.I_M_SectionCode getM_SectionCode();

	void setM_SectionCode(@Nullable org.compiere.model.I_M_SectionCode M_SectionCode);

	ModelColumn<I_C_Dunning_Candidate, I_M_SectionCode> COLUMN_M_SectionCode_ID = new ModelColumn<>(I_C_Dunning_Candidate.class, "M_SectionCode_ID", org.compiere.model.I_M_SectionCode.class);
	String COLUMNNAME_M_SectionCode_ID = "M_SectionCode_ID";

	/**
	 * Set Offener Betrag.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setOpenAmt (java.math.BigDecimal OpenAmt);

	/**
	 * Get Offener Betrag.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getOpenAmt();

	/** Column definition for OpenAmt */
	public static final org.adempiere.model.ModelColumn<I_C_Dunning_Candidate, Object> COLUMN_OpenAmt = new org.adempiere.model.ModelColumn<I_C_Dunning_Candidate, Object>(I_C_Dunning_Candidate.class, "OpenAmt", null);
	/** Column name OpenAmt */
	public static final String COLUMNNAME_OpenAmt = "OpenAmt";

	/**
	 * Set Verarbeitet.
	 * Checkbox sagt aus, ob der Beleg verarbeitet wurde.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setProcessed (boolean Processed);

	/**
	 * Get Verarbeitet.
	 * Checkbox sagt aus, ob der Beleg verarbeitet wurde.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isProcessed();

	/** Column definition for Processed */
	public static final org.adempiere.model.ModelColumn<I_C_Dunning_Candidate, Object> COLUMN_Processed = new org.adempiere.model.ModelColumn<I_C_Dunning_Candidate, Object>(I_C_Dunning_Candidate.class, "Processed", null);
	/** Column name Processed */
	public static final String COLUMNNAME_Processed = "Processed";

	/**
	 * Set Datensatz-ID.
	 * Direct internal record ID
	 *
	 * <br>Type: Button
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setRecord_ID (int Record_ID);

	/**
	 * Get Datensatz-ID.
	 * Direct internal record ID
	 *
	 * <br>Type: Button
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getRecord_ID();

	/** Column definition for Record_ID */
	public static final org.adempiere.model.ModelColumn<I_C_Dunning_Candidate, Object> COLUMN_Record_ID = new org.adempiere.model.ModelColumn<I_C_Dunning_Candidate, Object>(I_C_Dunning_Candidate.class, "Record_ID", null);
	/** Column name Record_ID */
	public static final String COLUMNNAME_Record_ID = "Record_ID";

	/**
	 * Set Gesamtbetrag.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setTotalAmt (java.math.BigDecimal TotalAmt);

	/**
	 * Get Gesamtbetrag.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getTotalAmt();

	/** Column definition for TotalAmt */
	public static final org.adempiere.model.ModelColumn<I_C_Dunning_Candidate, Object> COLUMN_TotalAmt = new org.adempiere.model.ModelColumn<I_C_Dunning_Candidate, Object>(I_C_Dunning_Candidate.class, "TotalAmt", null);
	/** Column name TotalAmt */
	public static final String COLUMNNAME_TotalAmt = "TotalAmt";

	/**
	 * Get Aktualisiert.
	 * Datum, an dem dieser Eintrag aktualisiert wurde
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getUpdated();

	/** Column definition for Updated */
	public static final org.adempiere.model.ModelColumn<I_C_Dunning_Candidate, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_C_Dunning_Candidate, Object>(I_C_Dunning_Candidate.class, "Updated", null);
	/** Column name Updated */
	public static final String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Aktualisiert durch.
	 * Nutzer, der diesen Eintrag aktualisiert hat
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getUpdatedBy();

	/** Column definition for UpdatedBy */
	public static final org.adempiere.model.ModelColumn<I_C_Dunning_Candidate, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_C_Dunning_Candidate, org.compiere.model.I_AD_User>(I_C_Dunning_Candidate.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
	/** Column name UpdatedBy */
	public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
