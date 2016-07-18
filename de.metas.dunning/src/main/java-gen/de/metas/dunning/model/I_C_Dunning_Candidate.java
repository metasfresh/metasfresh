/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2007 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software, you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY, without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program, if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
package de.metas.dunning.model;


/** Generated Interface for C_Dunning_Candidate
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_C_Dunning_Candidate 
{

    /** TableName=C_Dunning_Candidate */
    public static final String Table_Name = "C_Dunning_Candidate";

    /** AD_Table_ID=540396 */
//    public static final int Table_ID = MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 1 - Org 
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(1);

    /** Load Meta Data */

    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/** Get Mandant.
	  * Mandant für diese Installation.
	  */
	public int getAD_Client_ID();

	public org.compiere.model.I_AD_Client getAD_Client() throws RuntimeException;

    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/** Set Sektion.
	  * Organisatorische Einheit des Mandanten
	  */
	public void setAD_Org_ID (int AD_Org_ID);

	/** Get Sektion.
	  * Organisatorische Einheit des Mandanten
	  */
	public int getAD_Org_ID();

	public org.compiere.model.I_AD_Org getAD_Org() throws RuntimeException;

	public void setAD_Org(org.compiere.model.I_AD_Org AD_Org);

    /** Column name AD_Table_ID */
    public static final String COLUMNNAME_AD_Table_ID = "AD_Table_ID";

	/** Set DB-Tabelle.
	  * Database Table information
	  */
	public void setAD_Table_ID (int AD_Table_ID);

	/** Get DB-Tabelle.
	  * Database Table information
	  */
	public int getAD_Table_ID();

	public org.compiere.model.I_AD_Table getAD_Table() throws RuntimeException;

	public void setAD_Table(org.compiere.model.I_AD_Table AD_Table);

    /** Column name C_BPartner_ID */
    public static final String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/** Set Geschäftspartner.
	  * Bezeichnet einen Geschäftspartner
	  */
	public void setC_BPartner_ID (int C_BPartner_ID);

	/** Get Geschäftspartner.
	  * Bezeichnet einen Geschäftspartner
	  */
	public int getC_BPartner_ID();

	public org.compiere.model.I_C_BPartner getC_BPartner() throws RuntimeException;

	public void setC_BPartner(org.compiere.model.I_C_BPartner C_BPartner);

    /** Column name C_BPartner_Location_ID */
    public static final String COLUMNNAME_C_BPartner_Location_ID = "C_BPartner_Location_ID";

	/** Set Standort.
	  * Identifiziert die (Liefer-) Adresse des Geschäftspartners
	  */
	public void setC_BPartner_Location_ID (int C_BPartner_Location_ID);

	/** Get Standort.
	  * Identifiziert die (Liefer-) Adresse des Geschäftspartners
	  */
	public int getC_BPartner_Location_ID();

	public org.compiere.model.I_C_BPartner_Location getC_BPartner_Location() throws RuntimeException;

	public void setC_BPartner_Location(org.compiere.model.I_C_BPartner_Location C_BPartner_Location);

    /** Column name C_Currency_ID */
    public static final String COLUMNNAME_C_Currency_ID = "C_Currency_ID";

	/** Set Währung.
	  * Die Währung für diesen Eintrag
	  */
	public void setC_Currency_ID (int C_Currency_ID);

	/** Get Währung.
	  * Die Währung für diesen Eintrag
	  */
	public int getC_Currency_ID();

	public org.compiere.model.I_C_Currency getC_Currency() throws RuntimeException;

	public void setC_Currency(org.compiere.model.I_C_Currency C_Currency);

    /** Column name C_Dunning_Candidate_ID */
    public static final String COLUMNNAME_C_Dunning_Candidate_ID = "C_Dunning_Candidate_ID";

	/** Set Mahnungsdisposition	  */
	public void setC_Dunning_Candidate_ID (int C_Dunning_Candidate_ID);

	/** Get Mahnungsdisposition	  */
	public int getC_Dunning_Candidate_ID();

    /** Column name C_Dunning_Contact_ID */
    public static final String COLUMNNAME_C_Dunning_Contact_ID = "C_Dunning_Contact_ID";

	/** Set Mahnkontakt	  */
	public void setC_Dunning_Contact_ID (int C_Dunning_Contact_ID);

	/** Get Mahnkontakt	  */
	public int getC_Dunning_Contact_ID();

	public org.compiere.model.I_AD_User getC_Dunning_Contact() throws RuntimeException;

	public void setC_Dunning_Contact(org.compiere.model.I_AD_User C_Dunning_Contact);

    /** Column name C_DunningLevel_ID */
    public static final String COLUMNNAME_C_DunningLevel_ID = "C_DunningLevel_ID";

	/** Set Mahnstufe	  */
	public void setC_DunningLevel_ID (int C_DunningLevel_ID);

	/** Get Mahnstufe	  */
	public int getC_DunningLevel_ID();

	public org.compiere.model.I_C_DunningLevel getC_DunningLevel() throws RuntimeException;

	public void setC_DunningLevel(org.compiere.model.I_C_DunningLevel C_DunningLevel);

    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/** Get Erstellt.
	  * Datum, an dem dieser Eintrag erstellt wurde
	  */
	public java.sql.Timestamp getCreated();

    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/** Get Erstellt durch.
	  * Nutzer, der diesen Eintrag erstellt hat
	  */
	public int getCreatedBy();

    /** Column name DaysDue */
    public static final String COLUMNNAME_DaysDue = "DaysDue";

	/** Set Tage fällig.
	  * Anzahl der Tage der Fälligkeit (negativ: Fällig in Anzahl Tagen)
	  */
	public void setDaysDue (int DaysDue);

	/** Get Tage fällig.
	  * Anzahl der Tage der Fälligkeit (negativ: Fällig in Anzahl Tagen)
	  */
	public int getDaysDue();
	
	
	
	/**
	 * Set Beleg Nr..
	 * Document sequence number of the document
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDocumentNo (java.lang.String DocumentNo);

	/**
	 * Get Beleg Nr..
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

    /** Column name DueDate */
    public static final String COLUMNNAME_DueDate = "DueDate";

	/** Set Datum Fälligkeit.
	  * Datum, zu dem Zahlung fällig wird
	  */
	public void setDueDate (java.sql.Timestamp DueDate);

	/** Get Datum Fälligkeit.
	  * Datum, zu dem Zahlung fällig wird
	  */
	public java.sql.Timestamp getDueDate();

    /** Column name DunningDate */
    public static final String COLUMNNAME_DunningDate = "DunningDate";

	/** Set Dunning Date.
	  * Date of Dunning
	  */
	public void setDunningDate (java.sql.Timestamp DunningDate);

	/** Get Dunning Date.
	  * Date of Dunning
	  */
	public java.sql.Timestamp getDunningDate();

    /** Column name DunningDateEffective */
    public static final String COLUMNNAME_DunningDateEffective = "DunningDateEffective";

	/** Set Dunning Date Effective.
	  * Effective Date of Dunning
	  */
	public void setDunningDateEffective (java.sql.Timestamp DunningDateEffective);

	/** Get Dunning Date Effective.
	  * Effective Date of Dunning
	  */
	public java.sql.Timestamp getDunningDateEffective();

    /** Column name DunningGrace */
    public static final String COLUMNNAME_DunningGrace = "DunningGrace";

	/** Set Dunning Grace Date	  */
	public void setDunningGrace (java.sql.Timestamp DunningGrace);

	/** Get Dunning Grace Date	  */
	public java.sql.Timestamp getDunningGrace();

    /** Column name DunningInterestAmt */
    public static final String COLUMNNAME_DunningInterestAmt = "DunningInterestAmt";

	/** Set Mahnzins.
	  * Prozentualer Anteil der offenen Summe, der als zusätzliche Mahngebühr ausgewiesen wird.
	  */
	public void setDunningInterestAmt (java.math.BigDecimal DunningInterestAmt);

	/** Get Mahnzins.
	  * Prozentualer Anteil der offenen Summe, der als zusätzliche Mahngebühr ausgewiesen wird.
	  */
	public java.math.BigDecimal getDunningInterestAmt();

    /** Column name FeeAmt */
    public static final String COLUMNNAME_FeeAmt = "FeeAmt";

	/** Set Mahnpauschale.
	  * Pauschale Mahngebühr
	  */
	public void setFeeAmt (java.math.BigDecimal FeeAmt);

	/** Get Mahnpauschale.
	  * Pauschale Mahngebühr
	  */
	public java.math.BigDecimal getFeeAmt();

    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/** Set Aktiv.
	  * Der Eintrag ist im System aktiv
	  */
	public void setIsActive (boolean IsActive);

	/** Get Aktiv.
	  * Der Eintrag ist im System aktiv
	  */
	public boolean isActive();

    /** Column name IsDunningDocProcessed */
    public static final String COLUMNNAME_IsDunningDocProcessed = "IsDunningDocProcessed";

	/** Set IsDunningDocProcessed	  */
	public void setIsDunningDocProcessed (boolean IsDunningDocProcessed);

	/** Get IsDunningDocProcessed	  */
	public boolean isDunningDocProcessed();

    /** Column name IsStaled */
    public static final String COLUMNNAME_IsStaled = "IsStaled";

	/** Set Zu aktualisieren	  */
	public void setIsStaled (boolean IsStaled);

	/** Get Zu aktualisieren	  */
	public boolean isStaled();

    /** Column name IsWriteOff */
    public static final String COLUMNNAME_IsWriteOff = "IsWriteOff";

	/** Set Massenaustritt	  */
	public void setIsWriteOff (boolean IsWriteOff);

	/** Get Massenaustritt	  */
	public boolean isWriteOff();

    /** Column name OpenAmt */
    public static final String COLUMNNAME_OpenAmt = "OpenAmt";

	/** Set Offener Betrag	  */
	public void setOpenAmt (java.math.BigDecimal OpenAmt);

	/** Get Offener Betrag	  */
	public java.math.BigDecimal getOpenAmt();

    /** Column name Processed */
    public static final String COLUMNNAME_Processed = "Processed";

	/** Set Verarbeitet.
	  * Checkbox sagt aus, ob der Beleg verarbeitet wurde. 
	  */
	public void setProcessed (boolean Processed);

	/** Get Verarbeitet.
	  * Checkbox sagt aus, ob der Beleg verarbeitet wurde. 
	  */
	public boolean isProcessed();

    /** Column name Record_ID */
    public static final String COLUMNNAME_Record_ID = "Record_ID";

	/** Set Datensatz-ID.
	  * Direct internal record ID
	  */
	public void setRecord_ID (int Record_ID);

	/** Get Datensatz-ID.
	  * Direct internal record ID
	  */
	public int getRecord_ID();

    /** Column name TotalAmt */
    public static final String COLUMNNAME_TotalAmt = "TotalAmt";

	/** Set Gesamtbetrag	  */
	public void setTotalAmt (java.math.BigDecimal TotalAmt);

	/** Get Gesamtbetrag	  */
	public java.math.BigDecimal getTotalAmt();

    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/** Get Aktualisiert.
	  * Datum, an dem dieser Eintrag aktualisiert wurde
	  */
	public java.sql.Timestamp getUpdated();

    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/** Get Aktualisiert durch.
	  * Nutzer, der diesen Eintrag aktualisiert hat
	  */
	public int getUpdatedBy();
}
