package org.compiere.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;

/** Generated Interface for S_ResourceType
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_S_ResourceType 
{

	String Table_Name = "S_ResourceType";

//	/** AD_Table_ID=480 */
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
	 * Set Bruchteil der Mengeneinheit zulassen.
	 * Allow Unit of Measure Fractions
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAllowUoMFractions (boolean AllowUoMFractions);

	/**
	 * Get Bruchteil der Mengeneinheit zulassen.
	 * Allow Unit of Measure Fractions
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isAllowUoMFractions();

	ModelColumn<I_S_ResourceType, Object> COLUMN_AllowUoMFractions = new ModelColumn<>(I_S_ResourceType.class, "AllowUoMFractions", null);
	String COLUMNNAME_AllowUoMFractions = "AllowUoMFractions";

	/**
	 * Set UOM.
	 * Unit of Measure
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_UOM_ID (int C_UOM_ID);

	/**
	 * Get UOM.
	 * Unit of Measure
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_UOM_ID();

	String COLUMNNAME_C_UOM_ID = "C_UOM_ID";

	/**
	 * Set Abrechenbare Menge.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setChargeableQty (int ChargeableQty);

	/**
	 * Get Abrechenbare Menge.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getChargeableQty();

	ModelColumn<I_S_ResourceType, Object> COLUMN_ChargeableQty = new ModelColumn<>(I_S_ResourceType.class, "ChargeableQty", null);
	String COLUMNNAME_ChargeableQty = "ChargeableQty";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_S_ResourceType, Object> COLUMN_Created = new ModelColumn<>(I_S_ResourceType.class, "Created", null);
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

	ModelColumn<I_S_ResourceType, Object> COLUMN_Description = new ModelColumn<>(I_S_ResourceType.class, "Description", null);
	String COLUMNNAME_Description = "Description";

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

	ModelColumn<I_S_ResourceType, Object> COLUMN_IsActive = new ModelColumn<>(I_S_ResourceType.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Tag.
	 * Resource has day slot availability
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsDateSlot (boolean IsDateSlot);

	/**
	 * Get Tag.
	 * Resource has day slot availability
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isDateSlot();

	ModelColumn<I_S_ResourceType, Object> COLUMN_IsDateSlot = new ModelColumn<>(I_S_ResourceType.class, "IsDateSlot", null);
	String COLUMNNAME_IsDateSlot = "IsDateSlot";

	/**
	 * Set Nur einmalige Zuordnung.
	 * Only one assignment at a time (no double-booking or overlapping)
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsSingleAssignment (boolean IsSingleAssignment);

	/**
	 * Get Nur einmalige Zuordnung.
	 * Only one assignment at a time (no double-booking or overlapping)
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isSingleAssignment();

	ModelColumn<I_S_ResourceType, Object> COLUMN_IsSingleAssignment = new ModelColumn<>(I_S_ResourceType.class, "IsSingleAssignment", null);
	String COLUMNNAME_IsSingleAssignment = "IsSingleAssignment";

	/**
	 * Set Zeitabschnitt.
	 * Resource has time slot availability
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsTimeSlot (boolean IsTimeSlot);

	/**
	 * Get Zeitabschnitt.
	 * Resource has time slot availability
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isTimeSlot();

	ModelColumn<I_S_ResourceType, Object> COLUMN_IsTimeSlot = new ModelColumn<>(I_S_ResourceType.class, "IsTimeSlot", null);
	String COLUMNNAME_IsTimeSlot = "IsTimeSlot";

	/**
	 * Set Product Category.
	 * Category of a Product
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_Product_Category_ID (int M_Product_Category_ID);

	/**
	 * Get Product Category.
	 * Category of a Product
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_Product_Category_ID();

	String COLUMNNAME_M_Product_Category_ID = "M_Product_Category_ID";

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

	ModelColumn<I_S_ResourceType, Object> COLUMN_Name = new ModelColumn<>(I_S_ResourceType.class, "Name", null);
	String COLUMNNAME_Name = "Name";

	/**
	 * Set Freitag.
	 * Available on Fridays
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setOnFriday (boolean OnFriday);

	/**
	 * Get Freitag.
	 * Available on Fridays
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isOnFriday();

	ModelColumn<I_S_ResourceType, Object> COLUMN_OnFriday = new ModelColumn<>(I_S_ResourceType.class, "OnFriday", null);
	String COLUMNNAME_OnFriday = "OnFriday";

	/**
	 * Set Montag.
	 * Available on Mondays
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setOnMonday (boolean OnMonday);

	/**
	 * Get Montag.
	 * Available on Mondays
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isOnMonday();

	ModelColumn<I_S_ResourceType, Object> COLUMN_OnMonday = new ModelColumn<>(I_S_ResourceType.class, "OnMonday", null);
	String COLUMNNAME_OnMonday = "OnMonday";

	/**
	 * Set Samstag.
	 * Available on Saturday
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setOnSaturday (boolean OnSaturday);

	/**
	 * Get Samstag.
	 * Available on Saturday
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isOnSaturday();

	ModelColumn<I_S_ResourceType, Object> COLUMN_OnSaturday = new ModelColumn<>(I_S_ResourceType.class, "OnSaturday", null);
	String COLUMNNAME_OnSaturday = "OnSaturday";

	/**
	 * Set Sonntag.
	 * Available on Sundays
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setOnSunday (boolean OnSunday);

	/**
	 * Get Sonntag.
	 * Available on Sundays
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isOnSunday();

	ModelColumn<I_S_ResourceType, Object> COLUMN_OnSunday = new ModelColumn<>(I_S_ResourceType.class, "OnSunday", null);
	String COLUMNNAME_OnSunday = "OnSunday";

	/**
	 * Set Donnerstag.
	 * Available on Thursdays
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setOnThursday (boolean OnThursday);

	/**
	 * Get Donnerstag.
	 * Available on Thursdays
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isOnThursday();

	ModelColumn<I_S_ResourceType, Object> COLUMN_OnThursday = new ModelColumn<>(I_S_ResourceType.class, "OnThursday", null);
	String COLUMNNAME_OnThursday = "OnThursday";

	/**
	 * Set Dienstag.
	 * Available on Tuesdays
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setOnTuesday (boolean OnTuesday);

	/**
	 * Get Dienstag.
	 * Available on Tuesdays
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isOnTuesday();

	ModelColumn<I_S_ResourceType, Object> COLUMN_OnTuesday = new ModelColumn<>(I_S_ResourceType.class, "OnTuesday", null);
	String COLUMNNAME_OnTuesday = "OnTuesday";

	/**
	 * Set Mittwoch.
	 * Available on Wednesdays
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setOnWednesday (boolean OnWednesday);

	/**
	 * Get Mittwoch.
	 * Available on Wednesdays
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isOnWednesday();

	ModelColumn<I_S_ResourceType, Object> COLUMN_OnWednesday = new ModelColumn<>(I_S_ResourceType.class, "OnWednesday", null);
	String COLUMNNAME_OnWednesday = "OnWednesday";

	/**
	 * Set Ressourcenart.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setS_ResourceType_ID (int S_ResourceType_ID);

	/**
	 * Get Ressourcenart.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getS_ResourceType_ID();

	ModelColumn<I_S_ResourceType, Object> COLUMN_S_ResourceType_ID = new ModelColumn<>(I_S_ResourceType.class, "S_ResourceType_ID", null);
	String COLUMNNAME_S_ResourceType_ID = "S_ResourceType_ID";

	/**
	 * Set Endzeitpunkt.
	 * Time when timeslot ends
	 *
	 * <br>Type: Time
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setTimeSlotEnd (@Nullable java.sql.Timestamp TimeSlotEnd);

	/**
	 * Get Endzeitpunkt.
	 * Time when timeslot ends
	 *
	 * <br>Type: Time
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getTimeSlotEnd();

	ModelColumn<I_S_ResourceType, Object> COLUMN_TimeSlotEnd = new ModelColumn<>(I_S_ResourceType.class, "TimeSlotEnd", null);
	String COLUMNNAME_TimeSlotEnd = "TimeSlotEnd";

	/**
	 * Set Startzeitpunkt.
	 * Time when timeslot starts
	 *
	 * <br>Type: Time
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setTimeSlotStart (@Nullable java.sql.Timestamp TimeSlotStart);

	/**
	 * Get Startzeitpunkt.
	 * Time when timeslot starts
	 *
	 * <br>Type: Time
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getTimeSlotStart();

	ModelColumn<I_S_ResourceType, Object> COLUMN_TimeSlotStart = new ModelColumn<>(I_S_ResourceType.class, "TimeSlotStart", null);
	String COLUMNNAME_TimeSlotStart = "TimeSlotStart";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_S_ResourceType, Object> COLUMN_Updated = new ModelColumn<>(I_S_ResourceType.class, "Updated", null);
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
	 * Set Search Key.
	 * Search key for the record in the format required - must be unique
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setValue (java.lang.String Value);

	/**
	 * Get Search Key.
	 * Search key for the record in the format required - must be unique
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getValue();

	ModelColumn<I_S_ResourceType, Object> COLUMN_Value = new ModelColumn<>(I_S_ResourceType.class, "Value", null);
	String COLUMNNAME_Value = "Value";
}
