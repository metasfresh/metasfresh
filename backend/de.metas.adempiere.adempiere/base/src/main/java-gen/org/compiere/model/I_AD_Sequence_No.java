package org.compiere.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;

/** Generated Interface for AD_Sequence_No
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_AD_Sequence_No 
{

	String Table_Name = "AD_Sequence_No";

//	/** AD_Table_ID=122 */
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
	 * Set Sequence.
	 * Document Sequence
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_Sequence_ID (int AD_Sequence_ID);

	/**
	 * Get Sequence.
	 * Document Sequence
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_Sequence_ID();

	org.compiere.model.I_AD_Sequence getAD_Sequence();

	void setAD_Sequence(org.compiere.model.I_AD_Sequence AD_Sequence);

	ModelColumn<I_AD_Sequence_No, org.compiere.model.I_AD_Sequence> COLUMN_AD_Sequence_ID = new ModelColumn<>(I_AD_Sequence_No.class, "AD_Sequence_ID", org.compiere.model.I_AD_Sequence.class);
	String COLUMNNAME_AD_Sequence_ID = "AD_Sequence_ID";

	/**
	 * Set Month.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCalendarMonth (@Nullable java.lang.String CalendarMonth);

	/**
	 * Get Month.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getCalendarMonth();

	ModelColumn<I_AD_Sequence_No, Object> COLUMN_CalendarMonth = new ModelColumn<>(I_AD_Sequence_No.class, "CalendarMonth", null);
	String COLUMNNAME_CalendarMonth = "CalendarMonth";

	/**
	 * Set Year.
	 * Calendar Year
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setCalendarYear (java.lang.String CalendarYear);

	/**
	 * Get Year.
	 * Calendar Year
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getCalendarYear();

	ModelColumn<I_AD_Sequence_No, Object> COLUMN_CalendarYear = new ModelColumn<>(I_AD_Sequence_No.class, "CalendarYear", null);
	String COLUMNNAME_CalendarYear = "CalendarYear";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_AD_Sequence_No, Object> COLUMN_Created = new ModelColumn<>(I_AD_Sequence_No.class, "Created", null);
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
	 * Set Current Next.
	 * The next number to be used
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setCurrentNext (int CurrentNext);

	/**
	 * Get Current Next.
	 * The next number to be used
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getCurrentNext();

	ModelColumn<I_AD_Sequence_No, Object> COLUMN_CurrentNext = new ModelColumn<>(I_AD_Sequence_No.class, "CurrentNext", null);
	String COLUMNNAME_CurrentNext = "CurrentNext";

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

	ModelColumn<I_AD_Sequence_No, Object> COLUMN_IsActive = new ModelColumn<>(I_AD_Sequence_No.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_AD_Sequence_No, Object> COLUMN_Updated = new ModelColumn<>(I_AD_Sequence_No.class, "Updated", null);
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
