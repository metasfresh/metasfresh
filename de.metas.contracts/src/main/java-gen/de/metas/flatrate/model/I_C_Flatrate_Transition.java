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
package de.metas.flatrate.model;


/** Generated Interface for C_Flatrate_Transition
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_C_Flatrate_Transition 
{

    /** TableName=C_Flatrate_Transition */
    public static final String Table_Name = "C_Flatrate_Transition";

    /** AD_Table_ID=540331 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org 
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(3);

    /** Load Meta Data */

	/** Get Mandant.
	  * Mandant für diese Installation.
	  */
	public int getAD_Client_ID();

	public org.compiere.model.I_AD_Client getAD_Client() throws RuntimeException;

    /** Column definition for AD_Client_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Flatrate_Transition, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_C_Flatrate_Transition, org.compiere.model.I_AD_Client>(I_C_Flatrate_Transition.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

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

    /** Column definition for AD_Org_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Flatrate_Transition, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_C_Flatrate_Transition, org.compiere.model.I_AD_Org>(I_C_Flatrate_Transition.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/** Set Abrechnungs-/Lieferkalender.
	  * Bezeichnung des Kalenders, der die Abrechnungs- bzw. bei Abonnements die Lieferperioden (z.B. Monate) definiert
	  */
	public void setC_Calendar_Contract_ID (int C_Calendar_Contract_ID);

	/** Get Abrechnungs-/Lieferkalender.
	  * Bezeichnung des Kalenders, der die Abrechnungs- bzw. bei Abonnements die Lieferperioden (z.B. Monate) definiert
	  */
	public int getC_Calendar_Contract_ID();

	public org.compiere.model.I_C_Calendar getC_Calendar_Contract() throws RuntimeException;

	public void setC_Calendar_Contract(org.compiere.model.I_C_Calendar C_Calendar_Contract);

    /** Column definition for C_Calendar_Contract_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Flatrate_Transition, org.compiere.model.I_C_Calendar> COLUMN_C_Calendar_Contract_ID = new org.adempiere.model.ModelColumn<I_C_Flatrate_Transition, org.compiere.model.I_C_Calendar>(I_C_Flatrate_Transition.class, "C_Calendar_Contract_ID", org.compiere.model.I_C_Calendar.class);
    /** Column name C_Calendar_Contract_ID */
    public static final String COLUMNNAME_C_Calendar_Contract_ID = "C_Calendar_Contract_ID";

	/** Set Nächste Vertragsbedingungen.
	  * Auswahl der Vertragsbedingungen, die bei einer Vertragsverlängerung in der folgenden Vertragsperide anzuwenden sind
	  */
	public void setC_Flatrate_Conditions_Next_ID (int C_Flatrate_Conditions_Next_ID);

	/** Get Nächste Vertragsbedingungen.
	  * Auswahl der Vertragsbedingungen, die bei einer Vertragsverlängerung in der folgenden Vertragsperide anzuwenden sind
	  */
	public int getC_Flatrate_Conditions_Next_ID();

	public de.metas.flatrate.model.I_C_Flatrate_Conditions getC_Flatrate_Conditions_Next() throws RuntimeException;

	public void setC_Flatrate_Conditions_Next(de.metas.flatrate.model.I_C_Flatrate_Conditions C_Flatrate_Conditions_Next);

    /** Column definition for C_Flatrate_Conditions_Next_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Flatrate_Transition, de.metas.flatrate.model.I_C_Flatrate_Conditions> COLUMN_C_Flatrate_Conditions_Next_ID = new org.adempiere.model.ModelColumn<I_C_Flatrate_Transition, de.metas.flatrate.model.I_C_Flatrate_Conditions>(I_C_Flatrate_Transition.class, "C_Flatrate_Conditions_Next_ID", de.metas.flatrate.model.I_C_Flatrate_Conditions.class);
    /** Column name C_Flatrate_Conditions_Next_ID */
    public static final String COLUMNNAME_C_Flatrate_Conditions_Next_ID = "C_Flatrate_Conditions_Next_ID";

	/** Set Vertragsverlängerung/-übergang.
	  * Regelt z.B. die Vertragslaufzeit, Kündigungsfristen, autmatische Verlängerung usw.
	  */
	public void setC_Flatrate_Transition_ID (int C_Flatrate_Transition_ID);

	/** Get Vertragsverlängerung/-übergang.
	  * Regelt z.B. die Vertragslaufzeit, Kündigungsfristen, autmatische Verlängerung usw.
	  */
	public int getC_Flatrate_Transition_ID();

    /** Column definition for C_Flatrate_Transition_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Flatrate_Transition, Object> COLUMN_C_Flatrate_Transition_ID = new org.adempiere.model.ModelColumn<I_C_Flatrate_Transition, Object>(I_C_Flatrate_Transition.class, "C_Flatrate_Transition_ID", null);
    /** Column name C_Flatrate_Transition_ID */
    public static final String COLUMNNAME_C_Flatrate_Transition_ID = "C_Flatrate_Transition_ID";

	/** Get Erstellt.
	  * Datum, an dem dieser Eintrag erstellt wurde
	  */
	public java.sql.Timestamp getCreated();

    /** Column definition for Created */
    public static final org.adempiere.model.ModelColumn<I_C_Flatrate_Transition, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_C_Flatrate_Transition, Object>(I_C_Flatrate_Transition.class, "Created", null);
    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/** Get Erstellt durch.
	  * Nutzer, der diesen Eintrag erstellt hat
	  */
	public int getCreatedBy();

    /** Column definition for CreatedBy */
    public static final org.adempiere.model.ModelColumn<I_C_Flatrate_Transition, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_C_Flatrate_Transition, org.compiere.model.I_AD_User>(I_C_Flatrate_Transition.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/** Set Lieferintervall	  */
	public void setDeliveryInterval (int DeliveryInterval);

	/** Get Lieferintervall	  */
	public int getDeliveryInterval();

    /** Column definition for DeliveryInterval */
    public static final org.adempiere.model.ModelColumn<I_C_Flatrate_Transition, Object> COLUMN_DeliveryInterval = new org.adempiere.model.ModelColumn<I_C_Flatrate_Transition, Object>(I_C_Flatrate_Transition.class, "DeliveryInterval", null);
    /** Column name DeliveryInterval */
    public static final String COLUMNNAME_DeliveryInterval = "DeliveryInterval";

	/** Set Einheit des Lieferintervalls	  */
	public void setDeliveryIntervalUnit (java.lang.String DeliveryIntervalUnit);

	/** Get Einheit des Lieferintervalls	  */
	public java.lang.String getDeliveryIntervalUnit();

    /** Column definition for DeliveryIntervalUnit */
    public static final org.adempiere.model.ModelColumn<I_C_Flatrate_Transition, Object> COLUMN_DeliveryIntervalUnit = new org.adempiere.model.ModelColumn<I_C_Flatrate_Transition, Object>(I_C_Flatrate_Transition.class, "DeliveryIntervalUnit", null);
    /** Column name DeliveryIntervalUnit */
    public static final String COLUMNNAME_DeliveryIntervalUnit = "DeliveryIntervalUnit";

	/** Set Beschreibung	  */
	public void setDescription (java.lang.String Description);

	/** Get Beschreibung	  */
	public java.lang.String getDescription();

    /** Column definition for Description */
    public static final org.adempiere.model.ModelColumn<I_C_Flatrate_Transition, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<I_C_Flatrate_Transition, Object>(I_C_Flatrate_Transition.class, "Description", null);
    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/** Set Belegverarbeitung.
	  * Der zukünftige Status des Belegs
	  */
	public void setDocAction (java.lang.String DocAction);

	/** Get Belegverarbeitung.
	  * Der zukünftige Status des Belegs
	  */
	public java.lang.String getDocAction();

    /** Column definition for DocAction */
    public static final org.adempiere.model.ModelColumn<I_C_Flatrate_Transition, Object> COLUMN_DocAction = new org.adempiere.model.ModelColumn<I_C_Flatrate_Transition, Object>(I_C_Flatrate_Transition.class, "DocAction", null);
    /** Column name DocAction */
    public static final String COLUMNNAME_DocAction = "DocAction";

	/** Set Belegstatus.
	  * The current status of the document
	  */
	public void setDocStatus (java.lang.String DocStatus);

	/** Get Belegstatus.
	  * The current status of the document
	  */
	public java.lang.String getDocStatus();

    /** Column definition for DocStatus */
    public static final org.adempiere.model.ModelColumn<I_C_Flatrate_Transition, Object> COLUMN_DocStatus = new org.adempiere.model.ModelColumn<I_C_Flatrate_Transition, Object>(I_C_Flatrate_Transition.class, "DocStatus", null);
    /** Column name DocStatus */
    public static final String COLUMNNAME_DocStatus = "DocStatus";

	/** Set Endet mit Kalenderjahr	  */
	public void setEndsWithCalendarYear (boolean EndsWithCalendarYear);

	/** Get Endet mit Kalenderjahr	  */
	public boolean isEndsWithCalendarYear();

    /** Column definition for EndsWithCalendarYear */
    public static final org.adempiere.model.ModelColumn<I_C_Flatrate_Transition, Object> COLUMN_EndsWithCalendarYear = new org.adempiere.model.ModelColumn<I_C_Flatrate_Transition, Object>(I_C_Flatrate_Transition.class, "EndsWithCalendarYear", null);
    /** Column name EndsWithCalendarYear */
    public static final String COLUMNNAME_EndsWithCalendarYear = "EndsWithCalendarYear";

	/** Set Aktiv.
	  * Der Eintrag ist im System aktiv
	  */
	public void setIsActive (boolean IsActive);

	/** Get Aktiv.
	  * Der Eintrag ist im System aktiv
	  */
	public boolean isActive();

    /** Column definition for IsActive */
    public static final org.adempiere.model.ModelColumn<I_C_Flatrate_Transition, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_C_Flatrate_Transition, Object>(I_C_Flatrate_Transition.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/** Set Neue Vertragslaufzeit autom. Fertigstellen.
	  * Legt fest, ob das System die automatisch neu erzeugte Vertragsperiode sofort fertigstellen soll.
	  */
	public void setIsAutoCompleteNewTerm (boolean IsAutoCompleteNewTerm);

	/** Get Neue Vertragslaufzeit autom. Fertigstellen.
	  * Legt fest, ob das System die automatisch neu erzeugte Vertragsperiode sofort fertigstellen soll.
	  */
	public boolean isAutoCompleteNewTerm();

    /** Column definition for IsAutoCompleteNewTerm */
    public static final org.adempiere.model.ModelColumn<I_C_Flatrate_Transition, Object> COLUMN_IsAutoCompleteNewTerm = new org.adempiere.model.ModelColumn<I_C_Flatrate_Transition, Object>(I_C_Flatrate_Transition.class, "IsAutoCompleteNewTerm", null);
    /** Column name IsAutoCompleteNewTerm */
    public static final String COLUMNNAME_IsAutoCompleteNewTerm = "IsAutoCompleteNewTerm";

	/** Set Vertrag autom. verlängern.
	  * Wenn dieser Haken gesetzt ist, werden laufende Verträge automatisch verlängert
	  */
	public void setIsAutoRenew (boolean IsAutoRenew);

	/** Get Vertrag autom. verlängern.
	  * Wenn dieser Haken gesetzt ist, werden laufende Verträge automatisch verlängert
	  */
	public boolean isAutoRenew();

    /** Column definition for IsAutoRenew */
    public static final org.adempiere.model.ModelColumn<I_C_Flatrate_Transition, Object> COLUMN_IsAutoRenew = new org.adempiere.model.ModelColumn<I_C_Flatrate_Transition, Object>(I_C_Flatrate_Transition.class, "IsAutoRenew", null);
    /** Column name IsAutoRenew */
    public static final String COLUMNNAME_IsAutoRenew = "IsAutoRenew";

	/** Set Betreuer Informieren	  */
	public void setIsNotifyUserInCharge (boolean IsNotifyUserInCharge);

	/** Get Betreuer Informieren	  */
	public boolean isNotifyUserInCharge();

    /** Column definition for IsNotifyUserInCharge */
    public static final org.adempiere.model.ModelColumn<I_C_Flatrate_Transition, Object> COLUMN_IsNotifyUserInCharge = new org.adempiere.model.ModelColumn<I_C_Flatrate_Transition, Object>(I_C_Flatrate_Transition.class, "IsNotifyUserInCharge", null);
    /** Column name IsNotifyUserInCharge */
    public static final String COLUMNNAME_IsNotifyUserInCharge = "IsNotifyUserInCharge";

	/** Set Name.
	  * Alphanumeric identifier of the entity
	  */
	public void setName (java.lang.String Name);

	/** Get Name.
	  * Alphanumeric identifier of the entity
	  */
	public java.lang.String getName();

    /** Column definition for Name */
    public static final org.adempiere.model.ModelColumn<I_C_Flatrate_Transition, Object> COLUMN_Name = new org.adempiere.model.ModelColumn<I_C_Flatrate_Transition, Object>(I_C_Flatrate_Transition.class, "Name", null);
    /** Column name Name */
    public static final String COLUMNNAME_Name = "Name";

	/** Set Verarbeitet.
	  * Checkbox sagt aus, ob der Beleg verarbeitet wurde. 
	  */
	public void setProcessed (boolean Processed);

	/** Get Verarbeitet.
	  * Checkbox sagt aus, ob der Beleg verarbeitet wurde. 
	  */
	public boolean isProcessed();

    /** Column definition for Processed */
    public static final org.adempiere.model.ModelColumn<I_C_Flatrate_Transition, Object> COLUMN_Processed = new org.adempiere.model.ModelColumn<I_C_Flatrate_Transition, Object>(I_C_Flatrate_Transition.class, "Processed", null);
    /** Column name Processed */
    public static final String COLUMNNAME_Processed = "Processed";

	/** Set Process Now	  */
	public void setProcessing (boolean Processing);

	/** Get Process Now	  */
	public boolean isProcessing();

    /** Column definition for Processing */
    public static final org.adempiere.model.ModelColumn<I_C_Flatrate_Transition, Object> COLUMN_Processing = new org.adempiere.model.ModelColumn<I_C_Flatrate_Transition, Object>(I_C_Flatrate_Transition.class, "Processing", null);
    /** Column name Processing */
    public static final String COLUMNNAME_Processing = "Processing";

	/** Set Vertragslaufzeit	  */
	public void setTermDuration (int TermDuration);

	/** Get Vertragslaufzeit	  */
	public int getTermDuration();

    /** Column definition for TermDuration */
    public static final org.adempiere.model.ModelColumn<I_C_Flatrate_Transition, Object> COLUMN_TermDuration = new org.adempiere.model.ModelColumn<I_C_Flatrate_Transition, Object>(I_C_Flatrate_Transition.class, "TermDuration", null);
    /** Column name TermDuration */
    public static final String COLUMNNAME_TermDuration = "TermDuration";

	/** Set Einheit der Vertragslaufzeit	  */
	public void setTermDurationUnit (java.lang.String TermDurationUnit);

	/** Get Einheit der Vertragslaufzeit	  */
	public java.lang.String getTermDurationUnit();

    /** Column definition for TermDurationUnit */
    public static final org.adempiere.model.ModelColumn<I_C_Flatrate_Transition, Object> COLUMN_TermDurationUnit = new org.adempiere.model.ModelColumn<I_C_Flatrate_Transition, Object>(I_C_Flatrate_Transition.class, "TermDurationUnit", null);
    /** Column name TermDurationUnit */
    public static final String COLUMNNAME_TermDurationUnit = "TermDurationUnit";

	/** Set Ablauffrist.
	  * Zeit vor Vertragsablauf, zu der bestimmte Aktionen durchgeführt werden sollen.
	  */
	public void setTermOfNotice (int TermOfNotice);

	/** Get Ablauffrist.
	  * Zeit vor Vertragsablauf, zu der bestimmte Aktionen durchgeführt werden sollen.
	  */
	public int getTermOfNotice();

    /** Column definition for TermOfNotice */
    public static final org.adempiere.model.ModelColumn<I_C_Flatrate_Transition, Object> COLUMN_TermOfNotice = new org.adempiere.model.ModelColumn<I_C_Flatrate_Transition, Object>(I_C_Flatrate_Transition.class, "TermOfNotice", null);
    /** Column name TermOfNotice */
    public static final String COLUMNNAME_TermOfNotice = "TermOfNotice";

	/** Set Einheit der Kündigungsfrist	  */
	public void setTermOfNoticeUnit (java.lang.String TermOfNoticeUnit);

	/** Get Einheit der Kündigungsfrist	  */
	public java.lang.String getTermOfNoticeUnit();

    /** Column definition for TermOfNoticeUnit */
    public static final org.adempiere.model.ModelColumn<I_C_Flatrate_Transition, Object> COLUMN_TermOfNoticeUnit = new org.adempiere.model.ModelColumn<I_C_Flatrate_Transition, Object>(I_C_Flatrate_Transition.class, "TermOfNoticeUnit", null);
    /** Column name TermOfNoticeUnit */
    public static final String COLUMNNAME_TermOfNoticeUnit = "TermOfNoticeUnit";

	/** Get Aktualisiert.
	  * Datum, an dem dieser Eintrag aktualisiert wurde
	  */
	public java.sql.Timestamp getUpdated();

    /** Column definition for Updated */
    public static final org.adempiere.model.ModelColumn<I_C_Flatrate_Transition, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_C_Flatrate_Transition, Object>(I_C_Flatrate_Transition.class, "Updated", null);
    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/** Get Aktualisiert durch.
	  * Nutzer, der diesen Eintrag aktualisiert hat
	  */
	public int getUpdatedBy();

    /** Column definition for UpdatedBy */
    public static final org.adempiere.model.ModelColumn<I_C_Flatrate_Transition, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_C_Flatrate_Transition, org.compiere.model.I_AD_User>(I_C_Flatrate_Transition.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
