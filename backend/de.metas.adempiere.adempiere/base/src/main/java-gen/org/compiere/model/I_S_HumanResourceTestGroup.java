package org.compiere.model;

import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for S_HumanResourceTestGroup
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_S_HumanResourceTestGroup 
{

	String Table_Name = "S_HumanResourceTestGroup";

//	/** AD_Table_ID=542326 */
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
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_S_HumanResourceTestGroup, Object> COLUMN_Created = new ModelColumn<>(I_S_HumanResourceTestGroup.class, "Created", null);
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
	 * Set Department.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDepartment (java.lang.String Department);

	/**
	 * Get Department.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getDepartment();

	ModelColumn<I_S_HumanResourceTestGroup, Object> COLUMN_Department = new ModelColumn<>(I_S_HumanResourceTestGroup.class, "Department", null);
	String COLUMNNAME_Department = "Department";

	/**
	 * Set LPG.
	 * Average weekly capacity
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setGroupIdentifier (java.lang.String GroupIdentifier);

	/**
	 * Get LPG.
	 * Average weekly capacity
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getGroupIdentifier();

	ModelColumn<I_S_HumanResourceTestGroup, Object> COLUMN_GroupIdentifier = new ModelColumn<>(I_S_HumanResourceTestGroup.class, "GroupIdentifier", null);
	String COLUMNNAME_GroupIdentifier = "GroupIdentifier";

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

	ModelColumn<I_S_HumanResourceTestGroup, Object> COLUMN_IsActive = new ModelColumn<>(I_S_HumanResourceTestGroup.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Day Slot.
	 * Resource has day slot availability
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsDateSlot (boolean IsDateSlot);

	/**
	 * Get Day Slot.
	 * Resource has day slot availability
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isDateSlot();

	ModelColumn<I_S_HumanResourceTestGroup, Object> COLUMN_IsDateSlot = new ModelColumn<>(I_S_HumanResourceTestGroup.class, "IsDateSlot", null);
	String COLUMNNAME_IsDateSlot = "IsDateSlot";

	/**
	 * Set Time Slot.
	 * Resource has time slot availability
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsTimeSlot (boolean IsTimeSlot);

	/**
	 * Get Time Slot.
	 * Resource has time slot availability
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isTimeSlot();

	ModelColumn<I_S_HumanResourceTestGroup, Object> COLUMN_IsTimeSlot = new ModelColumn<>(I_S_HumanResourceTestGroup.class, "IsTimeSlot", null);
	String COLUMNNAME_IsTimeSlot = "IsTimeSlot";

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

	ModelColumn<I_S_HumanResourceTestGroup, Object> COLUMN_Name = new ModelColumn<>(I_S_HumanResourceTestGroup.class, "Name", null);
	String COLUMNNAME_Name = "Name";

	/**
	 * Set Friday.
	 * Friday's available
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setOnFriday (boolean OnFriday);

	/**
	 * Get Friday.
	 * Friday's available
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isOnFriday();

	ModelColumn<I_S_HumanResourceTestGroup, Object> COLUMN_OnFriday = new ModelColumn<>(I_S_HumanResourceTestGroup.class, "OnFriday", null);
	String COLUMNNAME_OnFriday = "OnFriday";

	/**
	 * Set Monday.
	 * Monday's available
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setOnMonday (boolean OnMonday);

	/**
	 * Get Monday.
	 * Monday's available
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isOnMonday();

	ModelColumn<I_S_HumanResourceTestGroup, Object> COLUMN_OnMonday = new ModelColumn<>(I_S_HumanResourceTestGroup.class, "OnMonday", null);
	String COLUMNNAME_OnMonday = "OnMonday";

	/**
	 * Set Saturday.
	 * Available on Saturday
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setOnSaturday (boolean OnSaturday);

	/**
	 * Get Saturday.
	 * Available on Saturday
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isOnSaturday();

	ModelColumn<I_S_HumanResourceTestGroup, Object> COLUMN_OnSaturday = new ModelColumn<>(I_S_HumanResourceTestGroup.class, "OnSaturday", null);
	String COLUMNNAME_OnSaturday = "OnSaturday";

	/**
	 * Set Sunday.
	 * Available on Sundays
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setOnSunday (boolean OnSunday);

	/**
	 * Get Sunday.
	 * Available on Sundays
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isOnSunday();

	ModelColumn<I_S_HumanResourceTestGroup, Object> COLUMN_OnSunday = new ModelColumn<>(I_S_HumanResourceTestGroup.class, "OnSunday", null);
	String COLUMNNAME_OnSunday = "OnSunday";

	/**
	 * Set Thursday.
	 * Thursday's available
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setOnThursday (boolean OnThursday);

	/**
	 * Get Thursday.
	 * Thursday's available
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isOnThursday();

	ModelColumn<I_S_HumanResourceTestGroup, Object> COLUMN_OnThursday = new ModelColumn<>(I_S_HumanResourceTestGroup.class, "OnThursday", null);
	String COLUMNNAME_OnThursday = "OnThursday";

	/**
	 * Set Tuesday.
	 * Tuesday's available
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setOnTuesday (boolean OnTuesday);

	/**
	 * Get Tuesday.
	 * Tuesday's available
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isOnTuesday();

	ModelColumn<I_S_HumanResourceTestGroup, Object> COLUMN_OnTuesday = new ModelColumn<>(I_S_HumanResourceTestGroup.class, "OnTuesday", null);
	String COLUMNNAME_OnTuesday = "OnTuesday";

	/**
	 * Set Wednesday.
	 * Wednesday's available
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setOnWednesday (boolean OnWednesday);

	/**
	 * Get Wednesday.
	 * Wednesday's available
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isOnWednesday();

	ModelColumn<I_S_HumanResourceTestGroup, Object> COLUMN_OnWednesday = new ModelColumn<>(I_S_HumanResourceTestGroup.class, "OnWednesday", null);
	String COLUMNNAME_OnWednesday = "OnWednesday";

	/**
	 * Set Test facility group.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setS_HumanResourceTestGroup_ID (int S_HumanResourceTestGroup_ID);

	/**
	 * Get Test facility group.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getS_HumanResourceTestGroup_ID();

	ModelColumn<I_S_HumanResourceTestGroup, Object> COLUMN_S_HumanResourceTestGroup_ID = new ModelColumn<>(I_S_HumanResourceTestGroup.class, "S_HumanResourceTestGroup_ID", null);
	String COLUMNNAME_S_HumanResourceTestGroup_ID = "S_HumanResourceTestGroup_ID";

	/**
	 * Set Slot End.
	 * Time when timeslot ends
	 *
	 * <br>Type: Time
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setTimeSlotEnd (@Nullable java.sql.Timestamp TimeSlotEnd);

	/**
	 * Get Slot End.
	 * Time when timeslot ends
	 *
	 * <br>Type: Time
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getTimeSlotEnd();

	ModelColumn<I_S_HumanResourceTestGroup, Object> COLUMN_TimeSlotEnd = new ModelColumn<>(I_S_HumanResourceTestGroup.class, "TimeSlotEnd", null);
	String COLUMNNAME_TimeSlotEnd = "TimeSlotEnd";

	/**
	 * Set Slot Start.
	 * Time when timeslot starts
	 *
	 * <br>Type: Time
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setTimeSlotStart (@Nullable java.sql.Timestamp TimeSlotStart);

	/**
	 * Get Slot Start.
	 * Time when timeslot starts
	 *
	 * <br>Type: Time
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getTimeSlotStart();

	ModelColumn<I_S_HumanResourceTestGroup, Object> COLUMN_TimeSlotStart = new ModelColumn<>(I_S_HumanResourceTestGroup.class, "TimeSlotStart", null);
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

	ModelColumn<I_S_HumanResourceTestGroup, Object> COLUMN_Updated = new ModelColumn<>(I_S_HumanResourceTestGroup.class, "Updated", null);
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
