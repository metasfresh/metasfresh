package org.compiere.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;
import java.math.BigDecimal;

/** Generated Interface for C_Project_TimeBooking
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_C_Project_TimeBooking 
{

	String Table_Name = "C_Project_TimeBooking";

//	/** AD_Table_ID=542407 */
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
	 * Set Booked date.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setBookedDate (java.sql.Timestamp BookedDate);

	/**
	 * Get Booked date.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getBookedDate();

	ModelColumn<I_C_Project_TimeBooking, Object> COLUMN_BookedDate = new ModelColumn<>(I_C_Project_TimeBooking.class, "BookedDate", null);
	String COLUMNNAME_BookedDate = "BookedDate";

	/**
	 * Set Booked seconds.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setBookedSeconds (@Nullable BigDecimal BookedSeconds);

	/**
	 * Get Booked seconds.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getBookedSeconds();

	ModelColumn<I_C_Project_TimeBooking, Object> COLUMN_BookedSeconds = new ModelColumn<>(I_C_Project_TimeBooking.class, "BookedSeconds", null);
	String COLUMNNAME_BookedSeconds = "BookedSeconds";

	/**
	 * Set Comments.
	 * Comments or additional information
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setComments (@Nullable java.lang.String Comments);

	/**
	 * Get Comments.
	 * Comments or additional information
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getComments();

	ModelColumn<I_C_Project_TimeBooking, Object> COLUMN_Comments = new ModelColumn<>(I_C_Project_TimeBooking.class, "Comments", null);
	String COLUMNNAME_Comments = "Comments";

	/**
	 * Set Project.
	 * Financial Project
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Project_ID (int C_Project_ID);

	/**
	 * Get Project.
	 * Financial Project
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Project_ID();

	String COLUMNNAME_C_Project_ID = "C_Project_ID";

	/**
	 * Set Project Time.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Project_TimeBooking_ID (int C_Project_TimeBooking_ID);

	/**
	 * Get Project Time.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Project_TimeBooking_ID();

	ModelColumn<I_C_Project_TimeBooking, Object> COLUMN_C_Project_TimeBooking_ID = new ModelColumn<>(I_C_Project_TimeBooking.class, "C_Project_TimeBooking_ID", null);
	String COLUMNNAME_C_Project_TimeBooking_ID = "C_Project_TimeBooking_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_C_Project_TimeBooking, Object> COLUMN_Created = new ModelColumn<>(I_C_Project_TimeBooking.class, "Created", null);
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
	 * Set Time (H:mm).
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setHoursAndMinutes (java.lang.String HoursAndMinutes);

	/**
	 * Get Time (H:mm).
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getHoursAndMinutes();

	ModelColumn<I_C_Project_TimeBooking, Object> COLUMN_HoursAndMinutes = new ModelColumn<>(I_C_Project_TimeBooking.class, "HoursAndMinutes", null);
	String COLUMNNAME_HoursAndMinutes = "HoursAndMinutes";

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

	ModelColumn<I_C_Project_TimeBooking, Object> COLUMN_IsActive = new ModelColumn<>(I_C_Project_TimeBooking.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Invoiced.
	 * Is this invoiced?
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsInvoiced (boolean IsInvoiced);

	/**
	 * Get Invoiced.
	 * Is this invoiced?
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isInvoiced();

	ModelColumn<I_C_Project_TimeBooking, Object> COLUMN_IsInvoiced = new ModelColumn<>(I_C_Project_TimeBooking.class, "IsInvoiced", null);
	String COLUMNNAME_IsInvoiced = "IsInvoiced";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_C_Project_TimeBooking, Object> COLUMN_Updated = new ModelColumn<>(I_C_Project_TimeBooking.class, "Updated", null);
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
