package de.metas.contracts.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;

/** Generated Interface for C_Flatrate_Transition
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_C_Flatrate_Transition 
{

	String Table_Name = "C_Flatrate_Transition";

//	/** AD_Table_ID=540331 */
//	int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);


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
	 * Set Abrechnungs-/Lieferkalender.
	 * Bezeichnung des Kalenders, der die Abrechnungs- bzw. bei Abonnements die Lieferperioden (z.B. Monate) definiert
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Calendar_Contract_ID (int C_Calendar_Contract_ID);

	/**
	 * Get Abrechnungs-/Lieferkalender.
	 * Bezeichnung des Kalenders, der die Abrechnungs- bzw. bei Abonnements die Lieferperioden (z.B. Monate) definiert
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Calendar_Contract_ID();

	org.compiere.model.I_C_Calendar getC_Calendar_Contract();

	void setC_Calendar_Contract(org.compiere.model.I_C_Calendar C_Calendar_Contract);

	ModelColumn<I_C_Flatrate_Transition, org.compiere.model.I_C_Calendar> COLUMN_C_Calendar_Contract_ID = new ModelColumn<>(I_C_Flatrate_Transition.class, "C_Calendar_Contract_ID", org.compiere.model.I_C_Calendar.class);
	String COLUMNNAME_C_Calendar_Contract_ID = "C_Calendar_Contract_ID";

	/**
	 * Set Nächste Vertragsbedingungen.
	 * Auswahl der Vertragsbedingungen, die bei einer Vertragsverlängerung in der folgenden Vertragsperide anzuwenden sind
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Flatrate_Conditions_Next_ID (int C_Flatrate_Conditions_Next_ID);

	/**
	 * Get Nächste Vertragsbedingungen.
	 * Auswahl der Vertragsbedingungen, die bei einer Vertragsverlängerung in der folgenden Vertragsperide anzuwenden sind
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Flatrate_Conditions_Next_ID();

	@Nullable de.metas.contracts.model.I_C_Flatrate_Conditions getC_Flatrate_Conditions_Next();

	void setC_Flatrate_Conditions_Next(@Nullable de.metas.contracts.model.I_C_Flatrate_Conditions C_Flatrate_Conditions_Next);

	ModelColumn<I_C_Flatrate_Transition, de.metas.contracts.model.I_C_Flatrate_Conditions> COLUMN_C_Flatrate_Conditions_Next_ID = new ModelColumn<>(I_C_Flatrate_Transition.class, "C_Flatrate_Conditions_Next_ID", de.metas.contracts.model.I_C_Flatrate_Conditions.class);
	String COLUMNNAME_C_Flatrate_Conditions_Next_ID = "C_Flatrate_Conditions_Next_ID";

	/**
	 * Set Vertragsverlängerung/-übergang.
	 * Regelt z.B. die Vertragslaufzeit, Kündigungsfristen, autmatische Verlängerung usw.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Flatrate_Transition_ID (int C_Flatrate_Transition_ID);

	/**
	 * Get Vertragsverlängerung/-übergang.
	 * Regelt z.B. die Vertragslaufzeit, Kündigungsfristen, autmatische Verlängerung usw.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Flatrate_Transition_ID();

	ModelColumn<I_C_Flatrate_Transition, Object> COLUMN_C_Flatrate_Transition_ID = new ModelColumn<>(I_C_Flatrate_Transition.class, "C_Flatrate_Transition_ID", null);
	String COLUMNNAME_C_Flatrate_Transition_ID = "C_Flatrate_Transition_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_C_Flatrate_Transition, Object> COLUMN_Created = new ModelColumn<>(I_C_Flatrate_Transition.class, "Created", null);
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
	 * Set Lieferintervall.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDeliveryInterval (int DeliveryInterval);

	/**
	 * Get Lieferintervall.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getDeliveryInterval();

	ModelColumn<I_C_Flatrate_Transition, Object> COLUMN_DeliveryInterval = new ModelColumn<>(I_C_Flatrate_Transition.class, "DeliveryInterval", null);
	String COLUMNNAME_DeliveryInterval = "DeliveryInterval";

	/**
	 * Set Einheit des Lieferintervalls.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDeliveryIntervalUnit (@Nullable java.lang.String DeliveryIntervalUnit);

	/**
	 * Get Einheit des Lieferintervalls.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDeliveryIntervalUnit();

	ModelColumn<I_C_Flatrate_Transition, Object> COLUMN_DeliveryIntervalUnit = new ModelColumn<>(I_C_Flatrate_Transition.class, "DeliveryIntervalUnit", null);
	String COLUMNNAME_DeliveryIntervalUnit = "DeliveryIntervalUnit";

	/**
	 * Set Description.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDescription (@Nullable java.lang.String Description);

	/**
	 * Get Description.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDescription();

	ModelColumn<I_C_Flatrate_Transition, Object> COLUMN_Description = new ModelColumn<>(I_C_Flatrate_Transition.class, "Description", null);
	String COLUMNNAME_Description = "Description";

	/**
	 * Set Process Batch.
	 * Der zukünftige Status des Belegs
	 *
	 * <br>Type: Button
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDocAction (java.lang.String DocAction);

	/**
	 * Get Process Batch.
	 * Der zukünftige Status des Belegs
	 *
	 * <br>Type: Button
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getDocAction();

	ModelColumn<I_C_Flatrate_Transition, Object> COLUMN_DocAction = new ModelColumn<>(I_C_Flatrate_Transition.class, "DocAction", null);
	String COLUMNNAME_DocAction = "DocAction";

	/**
	 * Set Status.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDocStatus (java.lang.String DocStatus);

	/**
	 * Get Status.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getDocStatus();

	ModelColumn<I_C_Flatrate_Transition, Object> COLUMN_DocStatus = new ModelColumn<>(I_C_Flatrate_Transition.class, "DocStatus", null);
	String COLUMNNAME_DocStatus = "DocStatus";

	/**
	 * Set Endet mit Kalenderjahr.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setEndsWithCalendarYear (boolean EndsWithCalendarYear);

	/**
	 * Get Endet mit Kalenderjahr.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isEndsWithCalendarYear();

	ModelColumn<I_C_Flatrate_Transition, Object> COLUMN_EndsWithCalendarYear = new ModelColumn<>(I_C_Flatrate_Transition.class, "EndsWithCalendarYear", null);
	String COLUMNNAME_EndsWithCalendarYear = "EndsWithCalendarYear";

	/**
	 * Set Extension Type.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setExtensionType (@Nullable java.lang.String ExtensionType);

	/**
	 * Get Extension Type.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getExtensionType();

	ModelColumn<I_C_Flatrate_Transition, Object> COLUMN_ExtensionType = new ModelColumn<>(I_C_Flatrate_Transition.class, "ExtensionType", null);
	String COLUMNNAME_ExtensionType = "ExtensionType";

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

	ModelColumn<I_C_Flatrate_Transition, Object> COLUMN_IsActive = new ModelColumn<>(I_C_Flatrate_Transition.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Neue Vertragslaufzeit autom. Fertigstellen.
	 * Legt fest, ob das System die automatisch neu erzeugte Vertragsperiode sofort fertigstellen soll.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsAutoCompleteNewTerm (boolean IsAutoCompleteNewTerm);

	/**
	 * Get Neue Vertragslaufzeit autom. Fertigstellen.
	 * Legt fest, ob das System die automatisch neu erzeugte Vertragsperiode sofort fertigstellen soll.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isAutoCompleteNewTerm();

	ModelColumn<I_C_Flatrate_Transition, Object> COLUMN_IsAutoCompleteNewTerm = new ModelColumn<>(I_C_Flatrate_Transition.class, "IsAutoCompleteNewTerm", null);
	String COLUMNNAME_IsAutoCompleteNewTerm = "IsAutoCompleteNewTerm";

	/**
	 * Set Betreuer Informieren.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsNotifyUserInCharge (boolean IsNotifyUserInCharge);

	/**
	 * Get Betreuer Informieren.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isNotifyUserInCharge();

	ModelColumn<I_C_Flatrate_Transition, Object> COLUMN_IsNotifyUserInCharge = new ModelColumn<>(I_C_Flatrate_Transition.class, "IsNotifyUserInCharge", null);
	String COLUMNNAME_IsNotifyUserInCharge = "IsNotifyUserInCharge";

	/**
	 * Set Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setName (java.lang.String Name);

	/**
	 * Get Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getName();

	ModelColumn<I_C_Flatrate_Transition, Object> COLUMN_Name = new ModelColumn<>(I_C_Flatrate_Transition.class, "Name", null);
	String COLUMNNAME_Name = "Name";

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

	ModelColumn<I_C_Flatrate_Transition, Object> COLUMN_Processed = new ModelColumn<>(I_C_Flatrate_Transition.class, "Processed", null);
	String COLUMNNAME_Processed = "Processed";

	/**
	 * Set Process Now.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setProcessing (boolean Processing);

	/**
	 * Get Process Now.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isProcessing();

	ModelColumn<I_C_Flatrate_Transition, Object> COLUMN_Processing = new ModelColumn<>(I_C_Flatrate_Transition.class, "Processing", null);
	String COLUMNNAME_Processing = "Processing";

	/**
	 * Set Vertragslaufzeit.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setTermDuration (int TermDuration);

	/**
	 * Get Vertragslaufzeit.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getTermDuration();

	ModelColumn<I_C_Flatrate_Transition, Object> COLUMN_TermDuration = new ModelColumn<>(I_C_Flatrate_Transition.class, "TermDuration", null);
	String COLUMNNAME_TermDuration = "TermDuration";

	/**
	 * Set Einheit der Vertragslaufzeit.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setTermDurationUnit (java.lang.String TermDurationUnit);

	/**
	 * Get Einheit der Vertragslaufzeit.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getTermDurationUnit();

	ModelColumn<I_C_Flatrate_Transition, Object> COLUMN_TermDurationUnit = new ModelColumn<>(I_C_Flatrate_Transition.class, "TermDurationUnit", null);
	String COLUMNNAME_TermDurationUnit = "TermDurationUnit";

	/**
	 * Set Ablauffrist.
	 * Zeit vor Vertragsablauf, zu der bestimmte Aktionen durchgeführt werden sollen.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setTermOfNotice (int TermOfNotice);

	/**
	 * Get Ablauffrist.
	 * Zeit vor Vertragsablauf, zu der bestimmte Aktionen durchgeführt werden sollen.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getTermOfNotice();

	ModelColumn<I_C_Flatrate_Transition, Object> COLUMN_TermOfNotice = new ModelColumn<>(I_C_Flatrate_Transition.class, "TermOfNotice", null);
	String COLUMNNAME_TermOfNotice = "TermOfNotice";

	/**
	 * Set Einheit der Kündigungsfrist.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setTermOfNoticeUnit (java.lang.String TermOfNoticeUnit);

	/**
	 * Get Einheit der Kündigungsfrist.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getTermOfNoticeUnit();

	ModelColumn<I_C_Flatrate_Transition, Object> COLUMN_TermOfNoticeUnit = new ModelColumn<>(I_C_Flatrate_Transition.class, "TermOfNoticeUnit", null);
	String COLUMNNAME_TermOfNoticeUnit = "TermOfNoticeUnit";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_C_Flatrate_Transition, Object> COLUMN_Updated = new ModelColumn<>(I_C_Flatrate_Transition.class, "Updated", null);
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
