package de.metas.async.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;

/** Generated Interface for C_Async_Batch_Type
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_C_Async_Batch_Type 
{

	String Table_Name = "C_Async_Batch_Type";

//	/** AD_Table_ID=540625 */
//	int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);


	/**
	 * Set Boiler Plate.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_BoilerPlate_ID (int AD_BoilerPlate_ID);

	/**
	 * Get Boiler Plate.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_BoilerPlate_ID();

	ModelColumn<I_C_Async_Batch_Type, Object> COLUMN_AD_BoilerPlate_ID = new ModelColumn<>(I_C_Async_Batch_Type.class, "AD_BoilerPlate_ID", null);
	String COLUMNNAME_AD_BoilerPlate_ID = "AD_BoilerPlate_ID";

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
	 * Set Async Batch Type.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Async_Batch_Type_ID (int C_Async_Batch_Type_ID);

	/**
	 * Get Async Batch Type.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Async_Batch_Type_ID();

	ModelColumn<I_C_Async_Batch_Type, Object> COLUMN_C_Async_Batch_Type_ID = new ModelColumn<>(I_C_Async_Batch_Type.class, "C_Async_Batch_Type_ID", null);
	String COLUMNNAME_C_Async_Batch_Type_ID = "C_Async_Batch_Type_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_C_Async_Batch_Type, Object> COLUMN_Created = new ModelColumn<>(I_C_Async_Batch_Type.class, "Created", null);
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
	 * Set Internal Name.
	 * Generally used to give records a name that can be safely referenced from code.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setInternalName (String InternalName);

	/**
	 * Get Internal Name.
	 * Generally used to give records a name that can be safely referenced from code.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	String getInternalName();

	ModelColumn<I_C_Async_Batch_Type, Object> COLUMN_InternalName = new ModelColumn<>(I_C_Async_Batch_Type.class, "InternalName", null);
	String COLUMNNAME_InternalName = "InternalName";

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

	ModelColumn<I_C_Async_Batch_Type, Object> COLUMN_IsActive = new ModelColumn<>(I_C_Async_Batch_Type.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Keep Alive Time (Hours).
	 * If set greater than zero, the batch has to be processed within the given number of hours, or it is flagged with Error=Yes.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setKeepAliveTimeHours (@Nullable String KeepAliveTimeHours);

	/**
	 * Get Keep Alive Time (Hours).
	 * If set greater than zero, the batch has to be processed within the given number of hours, or it is flagged with Error=Yes.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable String getKeepAliveTimeHours();

	ModelColumn<I_C_Async_Batch_Type, Object> COLUMN_KeepAliveTimeHours = new ModelColumn<>(I_C_Async_Batch_Type.class, "KeepAliveTimeHours", null);
	String COLUMNNAME_KeepAliveTimeHours = "KeepAliveTimeHours";

	/**
	 * Set Benachrichtigungs-Art.
	 * Art der Benachrichtigung
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setNotificationType (@Nullable String NotificationType);

	/**
	 * Get Benachrichtigungs-Art.
	 * Art der Benachrichtigung
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable String getNotificationType();

	ModelColumn<I_C_Async_Batch_Type, Object> COLUMN_NotificationType = new ModelColumn<>(I_C_Async_Batch_Type.class, "NotificationType", null);
	String COLUMNNAME_NotificationType = "NotificationType";

	/**
	 * Set Skip Timeout (millis).
	 * Interval in which metasfresh checkes whether the batch was meanwhile processed
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setSkipTimeoutMillis (int SkipTimeoutMillis);

	/**
	 * Get Skip Timeout (millis).
	 * Interval in which metasfresh checkes whether the batch was meanwhile processed
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getSkipTimeoutMillis();

	ModelColumn<I_C_Async_Batch_Type, Object> COLUMN_SkipTimeoutMillis = new ModelColumn<>(I_C_Async_Batch_Type.class, "SkipTimeoutMillis", null);
	String COLUMNNAME_SkipTimeoutMillis = "SkipTimeoutMillis";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_C_Async_Batch_Type, Object> COLUMN_Updated = new ModelColumn<>(I_C_Async_Batch_Type.class, "Updated", null);
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
