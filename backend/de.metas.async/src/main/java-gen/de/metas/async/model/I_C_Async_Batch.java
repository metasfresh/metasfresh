package de.metas.async.model;

import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for C_Async_Batch
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_C_Async_Batch 
{

	String Table_Name = "C_Async_Batch";

//	/** AD_Table_ID=540620 */
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

	ModelColumn<I_C_Async_Batch, org.compiere.model.I_AD_PInstance> COLUMN_AD_PInstance_ID = new ModelColumn<>(I_C_Async_Batch.class, "AD_PInstance_ID", org.compiere.model.I_AD_PInstance.class);
	String COLUMNNAME_AD_PInstance_ID = "AD_PInstance_ID";

	/**
	 * Set Async Batch.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Async_Batch_ID (int C_Async_Batch_ID);

	/**
	 * Get Async Batch.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Async_Batch_ID();

	ModelColumn<I_C_Async_Batch, Object> COLUMN_C_Async_Batch_ID = new ModelColumn<>(I_C_Async_Batch.class, "C_Async_Batch_ID", null);
	String COLUMNNAME_C_Async_Batch_ID = "C_Async_Batch_ID";

	/**
	 * Set Async Batch Type.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Async_Batch_Type_ID (int C_Async_Batch_Type_ID);

	/**
	 * Get Async Batch Type.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Async_Batch_Type_ID();

	@Nullable de.metas.async.model.I_C_Async_Batch_Type getC_Async_Batch_Type();

	void setC_Async_Batch_Type(@Nullable de.metas.async.model.I_C_Async_Batch_Type C_Async_Batch_Type);

	ModelColumn<I_C_Async_Batch, de.metas.async.model.I_C_Async_Batch_Type> COLUMN_C_Async_Batch_Type_ID = new ModelColumn<>(I_C_Async_Batch.class, "C_Async_Batch_Type_ID", de.metas.async.model.I_C_Async_Batch_Type.class);
	String COLUMNNAME_C_Async_Batch_Type_ID = "C_Async_Batch_Type_ID";

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

	ModelColumn<I_C_Async_Batch, Object> COLUMN_CountEnqueued = new ModelColumn<>(I_C_Async_Batch.class, "CountEnqueued", null);
	String COLUMNNAME_CountEnqueued = "CountEnqueued";

	/**
	 * Set Expected.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCountExpected (int CountExpected);

	/**
	 * Get Expected.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getCountExpected();

	ModelColumn<I_C_Async_Batch, Object> COLUMN_CountExpected = new ModelColumn<>(I_C_Async_Batch.class, "CountExpected", null);
	String COLUMNNAME_CountExpected = "CountExpected";

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

	ModelColumn<I_C_Async_Batch, Object> COLUMN_CountProcessed = new ModelColumn<>(I_C_Async_Batch.class, "CountProcessed", null);
	String COLUMNNAME_CountProcessed = "CountProcessed";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_C_Async_Batch, Object> COLUMN_Created = new ModelColumn<>(I_C_Async_Batch.class, "Created", null);
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

	ModelColumn<I_C_Async_Batch, Object> COLUMN_Description = new ModelColumn<>(I_C_Async_Batch.class, "Description", null);
	String COLUMNNAME_Description = "Description";

	/**
	 * Set First Enqueued.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setFirstEnqueued (@Nullable java.sql.Timestamp FirstEnqueued);

	/**
	 * Get First Enqueued.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getFirstEnqueued();

	ModelColumn<I_C_Async_Batch, Object> COLUMN_FirstEnqueued = new ModelColumn<>(I_C_Async_Batch.class, "FirstEnqueued", null);
	String COLUMNNAME_FirstEnqueued = "FirstEnqueued";

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

	ModelColumn<I_C_Async_Batch, Object> COLUMN_IsActive = new ModelColumn<>(I_C_Async_Batch.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Processing.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsProcessing (boolean IsProcessing);

	/**
	 * Get Processing.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isProcessing();

	ModelColumn<I_C_Async_Batch, Object> COLUMN_IsProcessing = new ModelColumn<>(I_C_Async_Batch.class, "IsProcessing", null);
	String COLUMNNAME_IsProcessing = "IsProcessing";

	/**
	 * Set Last Enqueued.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setLastEnqueued (@Nullable java.sql.Timestamp LastEnqueued);

	/**
	 * Get Last Enqueued.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getLastEnqueued();

	ModelColumn<I_C_Async_Batch, Object> COLUMN_LastEnqueued = new ModelColumn<>(I_C_Async_Batch.class, "LastEnqueued", null);
	String COLUMNNAME_LastEnqueued = "LastEnqueued";

	/**
	 * Set Last Processed.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setLastProcessed (@Nullable java.sql.Timestamp LastProcessed);

	/**
	 * Get Last Processed.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getLastProcessed();

	ModelColumn<I_C_Async_Batch, Object> COLUMN_LastProcessed = new ModelColumn<>(I_C_Async_Batch.class, "LastProcessed", null);
	String COLUMNNAME_LastProcessed = "LastProcessed";

	/**
	 * Set LastProcessed WorkPackage.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setLastProcessed_WorkPackage_ID (int LastProcessed_WorkPackage_ID);

	/**
	 * Get LastProcessed WorkPackage.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getLastProcessed_WorkPackage_ID();

	@Nullable de.metas.async.model.I_C_Queue_WorkPackage getLastProcessed_WorkPackage();

	void setLastProcessed_WorkPackage(@Nullable de.metas.async.model.I_C_Queue_WorkPackage LastProcessed_WorkPackage);

	ModelColumn<I_C_Async_Batch, de.metas.async.model.I_C_Queue_WorkPackage> COLUMN_LastProcessed_WorkPackage_ID = new ModelColumn<>(I_C_Async_Batch.class, "LastProcessed_WorkPackage_ID", de.metas.async.model.I_C_Queue_WorkPackage.class);
	String COLUMNNAME_LastProcessed_WorkPackage_ID = "LastProcessed_WorkPackage_ID";

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

	ModelColumn<I_C_Async_Batch, Object> COLUMN_Name = new ModelColumn<>(I_C_Async_Batch.class, "Name", null);
	String COLUMNNAME_Name = "Name";

	/**
	 * Set Parent_Async_Batch_ID.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setParent_Async_Batch_ID (int Parent_Async_Batch_ID);

	/**
	 * Get Parent_Async_Batch_ID.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getParent_Async_Batch_ID();

	@Nullable de.metas.async.model.I_C_Async_Batch getParent_Async_Batch();

	void setParent_Async_Batch(@Nullable de.metas.async.model.I_C_Async_Batch Parent_Async_Batch);

	ModelColumn<I_C_Async_Batch, de.metas.async.model.I_C_Async_Batch> COLUMN_Parent_Async_Batch_ID = new ModelColumn<>(I_C_Async_Batch.class, "Parent_Async_Batch_ID", de.metas.async.model.I_C_Async_Batch.class);
	String COLUMNNAME_Parent_Async_Batch_ID = "Parent_Async_Batch_ID";

	/**
	 * Set Processed.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setProcessed (boolean Processed);

	/**
	 * Get Processed.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isProcessed();

	ModelColumn<I_C_Async_Batch, Object> COLUMN_Processed = new ModelColumn<>(I_C_Async_Batch.class, "Processed", null);
	String COLUMNNAME_Processed = "Processed";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_C_Async_Batch, Object> COLUMN_Updated = new ModelColumn<>(I_C_Async_Batch.class, "Updated", null);
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
