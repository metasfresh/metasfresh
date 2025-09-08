package de.metas.async.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;

/** Generated Interface for RV_Async_batch_statistics
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_RV_Async_batch_statistics 
{

	String Table_Name = "RV_Async_batch_statistics";

//	/** AD_Table_ID=540621 */
//	int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);


	/**
	 * Get Client.
	 * Client/Tenant for this installation.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_Client_ID();

	String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Organisation.
	 * Organisational entity within client
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Organisation.
	 * Organisational entity within client
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_Org_ID();

	String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Table.
	 * Database Table information
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_Table_ID (int AD_Table_ID);

	/**
	 * Get Table.
	 * Database Table information
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_Table_ID();

	String COLUMNNAME_AD_Table_ID = "AD_Table_ID";

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

	@Nullable de.metas.async.model.I_C_Async_Batch getC_Async_Batch();

	void setC_Async_Batch(@Nullable de.metas.async.model.I_C_Async_Batch C_Async_Batch);

	ModelColumn<I_RV_Async_batch_statistics, de.metas.async.model.I_C_Async_Batch> COLUMN_C_Async_Batch_ID = new ModelColumn<>(I_RV_Async_batch_statistics.class, "C_Async_Batch_ID", de.metas.async.model.I_C_Async_Batch.class);
	String COLUMNNAME_C_Async_Batch_ID = "C_Async_Batch_ID";

	/**
	 * Set Enqueued.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCountEnqueued (int CountEnqueued);

	/**
	 * Get Enqueued.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getCountEnqueued();

	ModelColumn<I_RV_Async_batch_statistics, Object> COLUMN_CountEnqueued = new ModelColumn<>(I_RV_Async_batch_statistics.class, "CountEnqueued", null);
	String COLUMNNAME_CountEnqueued = "CountEnqueued";

	/**
	 * Set Verarbeitet.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCountProcessed (int CountProcessed);

	/**
	 * Get Verarbeitet.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getCountProcessed();

	ModelColumn<I_RV_Async_batch_statistics, Object> COLUMN_CountProcessed = new ModelColumn<>(I_RV_Async_batch_statistics.class, "CountProcessed", null);
	String COLUMNNAME_CountProcessed = "CountProcessed";

	/**
	 * Set WorkPackage Processor.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Queue_PackageProcessor_ID (int C_Queue_PackageProcessor_ID);

	/**
	 * Get WorkPackage Processor.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Queue_PackageProcessor_ID();

	@Nullable de.metas.async.model.I_C_Queue_PackageProcessor getC_Queue_PackageProcessor();

	void setC_Queue_PackageProcessor(@Nullable de.metas.async.model.I_C_Queue_PackageProcessor C_Queue_PackageProcessor);

	ModelColumn<I_RV_Async_batch_statistics, de.metas.async.model.I_C_Queue_PackageProcessor> COLUMN_C_Queue_PackageProcessor_ID = new ModelColumn<>(I_RV_Async_batch_statistics.class, "C_Queue_PackageProcessor_ID", de.metas.async.model.I_C_Queue_PackageProcessor.class);
	String COLUMNNAME_C_Queue_PackageProcessor_ID = "C_Queue_PackageProcessor_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getCreated();

	ModelColumn<I_RV_Async_batch_statistics, Object> COLUMN_Created = new ModelColumn<>(I_RV_Async_batch_statistics.class, "Created", null);
	String COLUMNNAME_Created = "Created";

	/**
	 * Get Created By.
	 * User who created this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getCreatedBy();

	String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getUpdated();

	ModelColumn<I_RV_Async_batch_statistics, Object> COLUMN_Updated = new ModelColumn<>(I_RV_Async_batch_statistics.class, "Updated", null);
	String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Updated By.
	 * User who updated this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getUpdatedBy();

	String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
