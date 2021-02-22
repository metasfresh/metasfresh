package de.metas.purchasecandidate.model;

import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for C_BP_PurchaseSchedule
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_C_BP_PurchaseSchedule 
{

	String Table_Name = "C_BP_PurchaseSchedule";

//	/** AD_Table_ID=540975 */
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
	 * Set Business Partner.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_BPartner_ID (int C_BPartner_ID);

	/**
	 * Get Business Partner.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_BPartner_ID();

	String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/**
	 * Set BPartner's purchase schedule.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_BP_PurchaseSchedule_ID (int C_BP_PurchaseSchedule_ID);

	/**
	 * Get BPartner's purchase schedule.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_BP_PurchaseSchedule_ID();

	ModelColumn<I_C_BP_PurchaseSchedule, Object> COLUMN_C_BP_PurchaseSchedule_ID = new ModelColumn<>(I_C_BP_PurchaseSchedule.class, "C_BP_PurchaseSchedule_ID", null);
	String COLUMNNAME_C_BP_PurchaseSchedule_ID = "C_BP_PurchaseSchedule_ID";

	/**
	 * Set Calendar.
	 * Accounting Calendar Name
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Calendar_ID (int C_Calendar_ID);

	/**
	 * Get Calendar.
	 * Accounting Calendar Name
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Calendar_ID();

	@Nullable org.compiere.model.I_C_Calendar getC_Calendar();

	void setC_Calendar(@Nullable org.compiere.model.I_C_Calendar C_Calendar);

	ModelColumn<I_C_BP_PurchaseSchedule, org.compiere.model.I_C_Calendar> COLUMN_C_Calendar_ID = new ModelColumn<>(I_C_BP_PurchaseSchedule.class, "C_Calendar_ID", org.compiere.model.I_C_Calendar.class);
	String COLUMNNAME_C_Calendar_ID = "C_Calendar_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_C_BP_PurchaseSchedule, Object> COLUMN_Created = new ModelColumn<>(I_C_BP_PurchaseSchedule.class, "Created", null);
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

	ModelColumn<I_C_BP_PurchaseSchedule, Object> COLUMN_Description = new ModelColumn<>(I_C_BP_PurchaseSchedule.class, "Description", null);
	String COLUMNNAME_Description = "Description";

	/**
	 * Set Häufigkeit.
	 * Häufigkeit von Ereignissen
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setFrequency (int Frequency);

	/**
	 * Get Häufigkeit.
	 * Häufigkeit von Ereignissen
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getFrequency();

	ModelColumn<I_C_BP_PurchaseSchedule, Object> COLUMN_Frequency = new ModelColumn<>(I_C_BP_PurchaseSchedule.class, "Frequency", null);
	String COLUMNNAME_Frequency = "Frequency";

	/**
	 * Set Häufigkeitsart.
	 * Häufigkeitsart für Ereignisse
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setFrequencyType (java.lang.String FrequencyType);

	/**
	 * Get Häufigkeitsart.
	 * Häufigkeitsart für Ereignisse
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getFrequencyType();

	ModelColumn<I_C_BP_PurchaseSchedule, Object> COLUMN_FrequencyType = new ModelColumn<>(I_C_BP_PurchaseSchedule.class, "FrequencyType", null);
	String COLUMNNAME_FrequencyType = "FrequencyType";

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

	ModelColumn<I_C_BP_PurchaseSchedule, Object> COLUMN_IsActive = new ModelColumn<>(I_C_BP_PurchaseSchedule.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Lead Time Offset.
	 * Optional Lead Time offest before starting production
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setLeadTimeOffset (int LeadTimeOffset);

	/**
	 * Get Lead Time Offset.
	 * Optional Lead Time offest before starting production
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getLeadTimeOffset();

	ModelColumn<I_C_BP_PurchaseSchedule, Object> COLUMN_LeadTimeOffset = new ModelColumn<>(I_C_BP_PurchaseSchedule.class, "LeadTimeOffset", null);
	String COLUMNNAME_LeadTimeOffset = "LeadTimeOffset";

	/**
	 * Set Day of the Month.
	 * Tag des Monats 1 to 28/29/30/31
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setMonthDay (int MonthDay);

	/**
	 * Get Day of the Month.
	 * Tag des Monats 1 to 28/29/30/31
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getMonthDay();

	ModelColumn<I_C_BP_PurchaseSchedule, Object> COLUMN_MonthDay = new ModelColumn<>(I_C_BP_PurchaseSchedule.class, "MonthDay", null);
	String COLUMNNAME_MonthDay = "MonthDay";

	/**
	 * Set Freitag.
	 * Freitags verfügbar
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setOnFriday (boolean OnFriday);

	/**
	 * Get Freitag.
	 * Freitags verfügbar
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isOnFriday();

	ModelColumn<I_C_BP_PurchaseSchedule, Object> COLUMN_OnFriday = new ModelColumn<>(I_C_BP_PurchaseSchedule.class, "OnFriday", null);
	String COLUMNNAME_OnFriday = "OnFriday";

	/**
	 * Set Montag.
	 * Montags verfügbar
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setOnMonday (boolean OnMonday);

	/**
	 * Get Montag.
	 * Montags verfügbar
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isOnMonday();

	ModelColumn<I_C_BP_PurchaseSchedule, Object> COLUMN_OnMonday = new ModelColumn<>(I_C_BP_PurchaseSchedule.class, "OnMonday", null);
	String COLUMNNAME_OnMonday = "OnMonday";

	/**
	 * Set Samstag.
	 * Samstags verfügbar
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setOnSaturday (boolean OnSaturday);

	/**
	 * Get Samstag.
	 * Samstags verfügbar
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isOnSaturday();

	ModelColumn<I_C_BP_PurchaseSchedule, Object> COLUMN_OnSaturday = new ModelColumn<>(I_C_BP_PurchaseSchedule.class, "OnSaturday", null);
	String COLUMNNAME_OnSaturday = "OnSaturday";

	/**
	 * Set Sonntag.
	 * Sonntags verfügbar
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setOnSunday (boolean OnSunday);

	/**
	 * Get Sonntag.
	 * Sonntags verfügbar
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isOnSunday();

	ModelColumn<I_C_BP_PurchaseSchedule, Object> COLUMN_OnSunday = new ModelColumn<>(I_C_BP_PurchaseSchedule.class, "OnSunday", null);
	String COLUMNNAME_OnSunday = "OnSunday";

	/**
	 * Set Donnerstag.
	 * Donnerstags verfügbar
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setOnThursday (boolean OnThursday);

	/**
	 * Get Donnerstag.
	 * Donnerstags verfügbar
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isOnThursday();

	ModelColumn<I_C_BP_PurchaseSchedule, Object> COLUMN_OnThursday = new ModelColumn<>(I_C_BP_PurchaseSchedule.class, "OnThursday", null);
	String COLUMNNAME_OnThursday = "OnThursday";

	/**
	 * Set Dienstag.
	 * Dienstags verfügbar
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setOnTuesday (boolean OnTuesday);

	/**
	 * Get Dienstag.
	 * Dienstags verfügbar
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isOnTuesday();

	ModelColumn<I_C_BP_PurchaseSchedule, Object> COLUMN_OnTuesday = new ModelColumn<>(I_C_BP_PurchaseSchedule.class, "OnTuesday", null);
	String COLUMNNAME_OnTuesday = "OnTuesday";

	/**
	 * Set Mittwoch.
	 * Mittwochs verfügbar
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setOnWednesday (boolean OnWednesday);

	/**
	 * Get Mittwoch.
	 * Mittwochs verfügbar
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isOnWednesday();

	ModelColumn<I_C_BP_PurchaseSchedule, Object> COLUMN_OnWednesday = new ModelColumn<>(I_C_BP_PurchaseSchedule.class, "OnWednesday", null);
	String COLUMNNAME_OnWednesday = "OnWednesday";

	/**
	 * Set Bereitstellungszeit Mo.
	 * Preparation time for monday
	 *
	 * <br>Type: Time
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPreparationTime_1 (@Nullable java.sql.Timestamp PreparationTime_1);

	/**
	 * Get Bereitstellungszeit Mo.
	 * Preparation time for monday
	 *
	 * <br>Type: Time
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getPreparationTime_1();

	ModelColumn<I_C_BP_PurchaseSchedule, Object> COLUMN_PreparationTime_1 = new ModelColumn<>(I_C_BP_PurchaseSchedule.class, "PreparationTime_1", null);
	String COLUMNNAME_PreparationTime_1 = "PreparationTime_1";

	/**
	 * Set Bereitstellungszeit Di.
	 * Preparation time for tuesday
	 *
	 * <br>Type: Time
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPreparationTime_2 (@Nullable java.sql.Timestamp PreparationTime_2);

	/**
	 * Get Bereitstellungszeit Di.
	 * Preparation time for tuesday
	 *
	 * <br>Type: Time
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getPreparationTime_2();

	ModelColumn<I_C_BP_PurchaseSchedule, Object> COLUMN_PreparationTime_2 = new ModelColumn<>(I_C_BP_PurchaseSchedule.class, "PreparationTime_2", null);
	String COLUMNNAME_PreparationTime_2 = "PreparationTime_2";

	/**
	 * Set Bereitstellungszeit Mi.
	 * Preparation time for wednesday
	 *
	 * <br>Type: Time
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPreparationTime_3 (@Nullable java.sql.Timestamp PreparationTime_3);

	/**
	 * Get Bereitstellungszeit Mi.
	 * Preparation time for wednesday
	 *
	 * <br>Type: Time
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getPreparationTime_3();

	ModelColumn<I_C_BP_PurchaseSchedule, Object> COLUMN_PreparationTime_3 = new ModelColumn<>(I_C_BP_PurchaseSchedule.class, "PreparationTime_3", null);
	String COLUMNNAME_PreparationTime_3 = "PreparationTime_3";

	/**
	 * Set Bereitstellungszeit Do.
	 * Preparation time for thursday
	 *
	 * <br>Type: Time
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPreparationTime_4 (@Nullable java.sql.Timestamp PreparationTime_4);

	/**
	 * Get Bereitstellungszeit Do.
	 * Preparation time for thursday
	 *
	 * <br>Type: Time
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getPreparationTime_4();

	ModelColumn<I_C_BP_PurchaseSchedule, Object> COLUMN_PreparationTime_4 = new ModelColumn<>(I_C_BP_PurchaseSchedule.class, "PreparationTime_4", null);
	String COLUMNNAME_PreparationTime_4 = "PreparationTime_4";

	/**
	 * Set Bereitstellungszeit Fr.
	 * Preparation time for Friday
	 *
	 * <br>Type: Time
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPreparationTime_5 (@Nullable java.sql.Timestamp PreparationTime_5);

	/**
	 * Get Bereitstellungszeit Fr.
	 * Preparation time for Friday
	 *
	 * <br>Type: Time
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getPreparationTime_5();

	ModelColumn<I_C_BP_PurchaseSchedule, Object> COLUMN_PreparationTime_5 = new ModelColumn<>(I_C_BP_PurchaseSchedule.class, "PreparationTime_5", null);
	String COLUMNNAME_PreparationTime_5 = "PreparationTime_5";

	/**
	 * Set Bereitstellungszeit Sa.
	 * Preparation time for Saturday
	 *
	 * <br>Type: Time
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPreparationTime_6 (@Nullable java.sql.Timestamp PreparationTime_6);

	/**
	 * Get Bereitstellungszeit Sa.
	 * Preparation time for Saturday
	 *
	 * <br>Type: Time
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getPreparationTime_6();

	ModelColumn<I_C_BP_PurchaseSchedule, Object> COLUMN_PreparationTime_6 = new ModelColumn<>(I_C_BP_PurchaseSchedule.class, "PreparationTime_6", null);
	String COLUMNNAME_PreparationTime_6 = "PreparationTime_6";

	/**
	 * Set Bereitstellungszeit So.
	 * Preparation time for Sunday
	 *
	 * <br>Type: Time
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPreparationTime_7 (@Nullable java.sql.Timestamp PreparationTime_7);

	/**
	 * Get Bereitstellungszeit So.
	 * Preparation time for Sunday
	 *
	 * <br>Type: Time
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getPreparationTime_7();

	ModelColumn<I_C_BP_PurchaseSchedule, Object> COLUMN_PreparationTime_7 = new ModelColumn<>(I_C_BP_PurchaseSchedule.class, "PreparationTime_7", null);
	String COLUMNNAME_PreparationTime_7 = "PreparationTime_7";

	/**
	 * Set Wiedervorlage.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setReminderTimeInMin (int ReminderTimeInMin);

	/**
	 * Get Wiedervorlage.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getReminderTimeInMin();

	ModelColumn<I_C_BP_PurchaseSchedule, Object> COLUMN_ReminderTimeInMin = new ModelColumn<>(I_C_BP_PurchaseSchedule.class, "ReminderTimeInMin", null);
	String COLUMNNAME_ReminderTimeInMin = "ReminderTimeInMin";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_C_BP_PurchaseSchedule, Object> COLUMN_Updated = new ModelColumn<>(I_C_BP_PurchaseSchedule.class, "Updated", null);
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

	/**
	 * Set Valid From.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setValidFrom (java.sql.Timestamp ValidFrom);

	/**
	 * Get Valid From.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getValidFrom();

	ModelColumn<I_C_BP_PurchaseSchedule, Object> COLUMN_ValidFrom = new ModelColumn<>(I_C_BP_PurchaseSchedule.class, "ValidFrom", null);
	String COLUMNNAME_ValidFrom = "ValidFrom";
}
