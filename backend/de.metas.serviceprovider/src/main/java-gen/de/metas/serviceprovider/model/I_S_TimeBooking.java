package de.metas.serviceprovider.model;

import java.math.BigDecimal;
import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for S_TimeBooking
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_S_TimeBooking 
{

	String Table_Name = "S_TimeBooking";

//	/** AD_Table_ID=541443 */
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
	 * Set Performing person.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_User_Performing_ID (int AD_User_Performing_ID);

	/**
	 * Get Performing person.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_User_Performing_ID();

	String COLUMNNAME_AD_User_Performing_ID = "AD_User_Performing_ID";

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

	ModelColumn<I_S_TimeBooking, Object> COLUMN_BookedDate = new ModelColumn<>(I_S_TimeBooking.class, "BookedDate", null);
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

	ModelColumn<I_S_TimeBooking, Object> COLUMN_BookedSeconds = new ModelColumn<>(I_S_TimeBooking.class, "BookedSeconds", null);
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

	ModelColumn<I_S_TimeBooking, Object> COLUMN_Comments = new ModelColumn<>(I_S_TimeBooking.class, "Comments", null);
	String COLUMNNAME_Comments = "Comments";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_S_TimeBooking, Object> COLUMN_Created = new ModelColumn<>(I_S_TimeBooking.class, "Created", null);
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

	ModelColumn<I_S_TimeBooking, Object> COLUMN_HoursAndMinutes = new ModelColumn<>(I_S_TimeBooking.class, "HoursAndMinutes", null);
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

	ModelColumn<I_S_TimeBooking, Object> COLUMN_IsActive = new ModelColumn<>(I_S_TimeBooking.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Issue.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setS_Issue_ID (int S_Issue_ID);

	/**
	 * Get Issue.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getS_Issue_ID();

	de.metas.serviceprovider.model.I_S_Issue getS_Issue();

	void setS_Issue(de.metas.serviceprovider.model.I_S_Issue S_Issue);

	ModelColumn<I_S_TimeBooking, de.metas.serviceprovider.model.I_S_Issue> COLUMN_S_Issue_ID = new ModelColumn<>(I_S_TimeBooking.class, "S_Issue_ID", de.metas.serviceprovider.model.I_S_Issue.class);
	String COLUMNNAME_S_Issue_ID = "S_Issue_ID";

	/**
	 * Set S_TimeBooking.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setS_TimeBooking_ID (int S_TimeBooking_ID);

	/**
	 * Get S_TimeBooking.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getS_TimeBooking_ID();

	ModelColumn<I_S_TimeBooking, Object> COLUMN_S_TimeBooking_ID = new ModelColumn<>(I_S_TimeBooking.class, "S_TimeBooking_ID", null);
	String COLUMNNAME_S_TimeBooking_ID = "S_TimeBooking_ID";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_S_TimeBooking, Object> COLUMN_Updated = new ModelColumn<>(I_S_TimeBooking.class, "Updated", null);
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
