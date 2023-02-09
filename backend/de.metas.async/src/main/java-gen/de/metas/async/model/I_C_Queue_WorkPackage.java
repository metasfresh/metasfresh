package de.metas.async.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;

/** Generated Interface for C_Queue_WorkPackage
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_C_Queue_WorkPackage 
{

	String Table_Name = "C_Queue_WorkPackage";

//	/** AD_Table_ID=540425 */
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
	 * Set Issues.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_Issue_ID (int AD_Issue_ID);

	/**
	 * Get Issues.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_Issue_ID();

	org.compiere.model.I_AD_Issue getAD_Issue();

	void setAD_Issue(org.compiere.model.I_AD_Issue AD_Issue);

	/** Column definition for AD_Issue_ID */
	org.adempiere.model.ModelColumn<I_C_Queue_WorkPackage, org.compiere.model.I_AD_Issue> COLUMN_AD_Issue_ID = new org.adempiere.model.ModelColumn<I_C_Queue_WorkPackage, org.compiere.model.I_AD_Issue>(I_C_Queue_WorkPackage.class, "AD_Issue_ID", org.compiere.model.I_AD_Issue.class);
	/** Column name AD_Issue_ID */
	String COLUMNNAME_AD_Issue_ID = "AD_Issue_ID";

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
	 * Set Process Instance.
	 * Instance of a Process
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_PInstance_ID (int AD_PInstance_ID);

	/**
	 * Get Process Instance.
	 * Instance of a Process
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_PInstance_ID();

	@Nullable org.compiere.model.I_AD_PInstance getAD_PInstance();

	void setAD_PInstance(@Nullable org.compiere.model.I_AD_PInstance AD_PInstance);

	ModelColumn<I_C_Queue_WorkPackage, org.compiere.model.I_AD_PInstance> COLUMN_AD_PInstance_ID = new ModelColumn<>(I_C_Queue_WorkPackage.class, "AD_PInstance_ID", org.compiere.model.I_AD_PInstance.class);
	String COLUMNNAME_AD_PInstance_ID = "AD_PInstance_ID";

	/**
	 * Set Role.
	 * Responsibility Role
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_Role_ID (int AD_Role_ID);

	/**
	 * Get Role.
	 * Responsibility Role
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_Role_ID();

	@Nullable org.compiere.model.I_AD_Role getAD_Role();

	void setAD_Role(@Nullable org.compiere.model.I_AD_Role AD_Role);

	ModelColumn<I_C_Queue_WorkPackage, org.compiere.model.I_AD_Role> COLUMN_AD_Role_ID = new ModelColumn<>(I_C_Queue_WorkPackage.class, "AD_Role_ID", org.compiere.model.I_AD_Role.class);
	String COLUMNNAME_AD_Role_ID = "AD_Role_ID";

	/**
	 * Set Contact.
	 * User within the system - Internal or Business Partner Contact
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_User_ID (int AD_User_ID);

	/**
	 * Get Contact.
	 * User within the system - Internal or Business Partner Contact
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_User_ID();

	String COLUMNNAME_AD_User_ID = "AD_User_ID";

	/**
	 * Set Responsible.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_User_InCharge_ID (int AD_User_InCharge_ID);

	/**
	 * Get Responsible.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_User_InCharge_ID();

	String COLUMNNAME_AD_User_InCharge_ID = "AD_User_InCharge_ID";

	/**
	 * Set Batch Enqueued Count.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setBatchEnqueuedCount (int BatchEnqueuedCount);

	/**
	 * Get Batch Enqueued Count.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getBatchEnqueuedCount();

	ModelColumn<I_C_Queue_WorkPackage, Object> COLUMN_BatchEnqueuedCount = new ModelColumn<>(I_C_Queue_WorkPackage.class, "BatchEnqueuedCount", null);
	String COLUMNNAME_BatchEnqueuedCount = "BatchEnqueuedCount";

	/**
	 * Set Async Batch.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Async_Batch_ID (int C_Async_Batch_ID);

	/**
	 * Get Async Batch.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Async_Batch_ID();

	@Nullable I_C_Async_Batch getC_Async_Batch();

	void setC_Async_Batch(@Nullable I_C_Async_Batch C_Async_Batch);

	ModelColumn<I_C_Queue_WorkPackage, I_C_Async_Batch> COLUMN_C_Async_Batch_ID = new ModelColumn<>(I_C_Queue_WorkPackage.class, "C_Async_Batch_ID", I_C_Async_Batch.class);
	String COLUMNNAME_C_Async_Batch_ID = "C_Async_Batch_ID";

	/**
	 * Set WorkPackage Processor.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Queue_PackageProcessor_ID (int C_Queue_PackageProcessor_ID);

	/**
	 * Get WorkPackage Processor.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Queue_PackageProcessor_ID();

	I_C_Queue_PackageProcessor getC_Queue_PackageProcessor();

	void setC_Queue_PackageProcessor(I_C_Queue_PackageProcessor C_Queue_PackageProcessor);

	ModelColumn<I_C_Queue_WorkPackage, I_C_Queue_PackageProcessor> COLUMN_C_Queue_PackageProcessor_ID = new ModelColumn<>(I_C_Queue_WorkPackage.class, "C_Queue_PackageProcessor_ID", I_C_Queue_PackageProcessor.class);
	String COLUMNNAME_C_Queue_PackageProcessor_ID = "C_Queue_PackageProcessor_ID";

	/**
	 * Set Asynchronous WorkPackage Queue.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Queue_WorkPackage_ID (int C_Queue_WorkPackage_ID);

	/**
	 * Get Asynchronous WorkPackage Queue.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Queue_WorkPackage_ID();

	ModelColumn<I_C_Queue_WorkPackage, Object> COLUMN_C_Queue_WorkPackage_ID = new ModelColumn<>(I_C_Queue_WorkPackage.class, "C_Queue_WorkPackage_ID", null);
	String COLUMNNAME_C_Queue_WorkPackage_ID = "C_Queue_WorkPackage_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_C_Queue_WorkPackage, Object> COLUMN_Created = new ModelColumn<>(I_C_Queue_WorkPackage.class, "Created", null);
	String COLUMNNAME_Created = "Created";

	/**
	 * Get Created By.
	 * User who created this records
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getCreatedBy();

	String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Error Message.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setErrorMsg (@Nullable String ErrorMsg);

	/**
	 * Get Error Message.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable String getErrorMsg();

	ModelColumn<I_C_Queue_WorkPackage, Object> COLUMN_ErrorMsg = new ModelColumn<>(I_C_Queue_WorkPackage.class, "ErrorMsg", null);
	String COLUMNNAME_ErrorMsg = "ErrorMsg";

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

	ModelColumn<I_C_Queue_WorkPackage, Object> COLUMN_IsActive = new ModelColumn<>(I_C_Queue_WorkPackage.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Error.
	 * An Error occurred in the execution
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsError (boolean IsError);

	/**
	 * Get Error.
	 * An Error occurred in the execution
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isError();

	ModelColumn<I_C_Queue_WorkPackage, Object> COLUMN_IsError = new ModelColumn<>(I_C_Queue_WorkPackage.class, "IsError", null);
	String COLUMNNAME_IsError = "IsError";

	/**
	 * Set Error acknowledged.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsErrorAcknowledged (boolean IsErrorAcknowledged);

	/**
	 * Get Error acknowledged.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isErrorAcknowledged();

	ModelColumn<I_C_Queue_WorkPackage, Object> COLUMN_IsErrorAcknowledged = new ModelColumn<>(I_C_Queue_WorkPackage.class, "IsErrorAcknowledged", null);
	String COLUMNNAME_IsErrorAcknowledged = "IsErrorAcknowledged";

	/**
	 * Set Bereit zur Verarbeitung.
	 * Zeigt an, ob das Zusammentstellen eines Arbeitspakets abgeschlossen ist.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsReadyForProcessing (boolean IsReadyForProcessing);

	/**
	 * Get Bereit zur Verarbeitung.
	 * Zeigt an, ob das Zusammentstellen eines Arbeitspakets abgeschlossen ist.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isReadyForProcessing();

	ModelColumn<I_C_Queue_WorkPackage, Object> COLUMN_IsReadyForProcessing = new ModelColumn<>(I_C_Queue_WorkPackage.class, "IsReadyForProcessing", null);
	String COLUMNNAME_IsReadyForProcessing = "IsReadyForProcessing";

	/**
	 * Set Last Duration (ms).
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setLastDurationMillis (int LastDurationMillis);

	/**
	 * Get Last Duration (ms).
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getLastDurationMillis();

	ModelColumn<I_C_Queue_WorkPackage, Object> COLUMN_LastDurationMillis = new ModelColumn<>(I_C_Queue_WorkPackage.class, "LastDurationMillis", null);
	String COLUMNNAME_LastDurationMillis = "LastDurationMillis";

	/**
	 * Set Last End Time.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setLastEndTime (@Nullable java.sql.Timestamp LastEndTime);

	/**
	 * Get Last End Time.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getLastEndTime();

	ModelColumn<I_C_Queue_WorkPackage, Object> COLUMN_LastEndTime = new ModelColumn<>(I_C_Queue_WorkPackage.class, "LastEndTime", null);
	String COLUMNNAME_LastEndTime = "LastEndTime";

	/**
	 * Set Last StartTime.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setLastStartTime (@Nullable java.sql.Timestamp LastStartTime);

	/**
	 * Get Last StartTime.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getLastStartTime();

	ModelColumn<I_C_Queue_WorkPackage, Object> COLUMN_LastStartTime = new ModelColumn<>(I_C_Queue_WorkPackage.class, "LastStartTime", null);
	String COLUMNNAME_LastStartTime = "LastStartTime";

	/**
	 * Set Gesperrt.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setLocked (boolean Locked);

	/**
	 * Get Gesperrt.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	boolean isLocked();

	ModelColumn<I_C_Queue_WorkPackage, Object> COLUMN_Locked = new ModelColumn<>(I_C_Queue_WorkPackage.class, "Locked", null);
	String COLUMNNAME_Locked = "Locked";

	/**
	 * Set Priority.
	 * Indicates if this request is of a high, medium or low priority.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPriority (String Priority);

	/**
	 * Get Priority.
	 * Indicates if this request is of a high, medium or low priority.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	String getPriority();

	ModelColumn<I_C_Queue_WorkPackage, Object> COLUMN_Priority = new ModelColumn<>(I_C_Queue_WorkPackage.class, "Priority", null);
	String COLUMNNAME_Priority = "Priority";

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

	ModelColumn<I_C_Queue_WorkPackage, Object> COLUMN_Processed = new ModelColumn<>(I_C_Queue_WorkPackage.class, "Processed", null);
	String COLUMNNAME_Processed = "Processed";

	/**
	 * Set Skipped Count.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setSkipped_Count (int Skipped_Count);

	/**
	 * Get Skipped Count.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getSkipped_Count();

	ModelColumn<I_C_Queue_WorkPackage, Object> COLUMN_Skipped_Count = new ModelColumn<>(I_C_Queue_WorkPackage.class, "Skipped_Count", null);
	String COLUMNNAME_Skipped_Count = "Skipped_Count";

	/**
	 * Set Skipped First Time.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setSkipped_First_Time (@Nullable java.sql.Timestamp Skipped_First_Time);

	/**
	 * Get Skipped First Time.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getSkipped_First_Time();

	ModelColumn<I_C_Queue_WorkPackage, Object> COLUMN_Skipped_First_Time = new ModelColumn<>(I_C_Queue_WorkPackage.class, "Skipped_First_Time", null);
	String COLUMNNAME_Skipped_First_Time = "Skipped_First_Time";

	/**
	 * Set Skipped Last Reason.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setSkipped_Last_Reason (@Nullable String Skipped_Last_Reason);

	/**
	 * Get Skipped Last Reason.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable String getSkipped_Last_Reason();

	ModelColumn<I_C_Queue_WorkPackage, Object> COLUMN_Skipped_Last_Reason = new ModelColumn<>(I_C_Queue_WorkPackage.class, "Skipped_Last_Reason", null);
	String COLUMNNAME_Skipped_Last_Reason = "Skipped_Last_Reason";

	/**
	 * Set Zuletzt Übersprungen um.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setSkippedAt (@Nullable java.sql.Timestamp SkippedAt);

	/**
	 * Get Zuletzt Übersprungen um.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getSkippedAt();

	ModelColumn<I_C_Queue_WorkPackage, Object> COLUMN_SkippedAt = new ModelColumn<>(I_C_Queue_WorkPackage.class, "SkippedAt", null);
	String COLUMNNAME_SkippedAt = "SkippedAt";

	/**
	 * Set Skip Timeout (millis).
	 * Interval in which metasfresh checkes whether the batch was meanwhile processed
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setSkipTimeoutMillis (int SkipTimeoutMillis);

	/**
	 * Get Skip Timeout (millis).
	 * Interval in which metasfresh checkes whether the batch was meanwhile processed
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getSkipTimeoutMillis();

	ModelColumn<I_C_Queue_WorkPackage, Object> COLUMN_SkipTimeoutMillis = new ModelColumn<>(I_C_Queue_WorkPackage.class, "SkipTimeoutMillis", null);
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

	ModelColumn<I_C_Queue_WorkPackage, Object> COLUMN_Updated = new ModelColumn<>(I_C_Queue_WorkPackage.class, "Updated", null);
	String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Updated By.
	 * User who updated this records
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getUpdatedBy();

	String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
