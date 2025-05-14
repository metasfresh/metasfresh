package org.adempiere.process.rpl.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;

/** Generated Interface for EXP_ReplicationTrx
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_EXP_ReplicationTrx 
{

	String Table_Name = "EXP_ReplicationTrx";

//	/** AD_Table_ID=540573 */
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

	ModelColumn<I_EXP_ReplicationTrx, Object> COLUMN_Created = new ModelColumn<>(I_EXP_ReplicationTrx.class, "Created", null);
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
	void setErrorMsg (@Nullable java.lang.String ErrorMsg);

	/**
	 * Get Error Message.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getErrorMsg();

	ModelColumn<I_EXP_ReplicationTrx, Object> COLUMN_ErrorMsg = new ModelColumn<>(I_EXP_ReplicationTrx.class, "ErrorMsg", null);
	String COLUMNNAME_ErrorMsg = "ErrorMsg";

	/**
	 * Set Replikationstransaktion.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setEXP_ReplicationTrx_ID (int EXP_ReplicationTrx_ID);

	/**
	 * Get Replikationstransaktion.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getEXP_ReplicationTrx_ID();

	ModelColumn<I_EXP_ReplicationTrx, Object> COLUMN_EXP_ReplicationTrx_ID = new ModelColumn<>(I_EXP_ReplicationTrx.class, "EXP_ReplicationTrx_ID", null);
	String COLUMNNAME_EXP_ReplicationTrx_ID = "EXP_ReplicationTrx_ID";

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

	ModelColumn<I_EXP_ReplicationTrx, Object> COLUMN_IsActive = new ModelColumn<>(I_EXP_ReplicationTrx.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Error.
	 * An Error occurred in the execution
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIsError (boolean IsError);

	/**
	 * Get Error.
	 * An Error occurred in the execution
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isError();

	ModelColumn<I_EXP_ReplicationTrx, Object> COLUMN_IsError = new ModelColumn<>(I_EXP_ReplicationTrx.class, "IsError", null);
	String COLUMNNAME_IsError = "IsError";

	/**
	 * Set Replication transaction finished.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIsReplicationTrxFinished (boolean IsReplicationTrxFinished);

	/**
	 * Get Replication transaction finished.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isReplicationTrxFinished();

	ModelColumn<I_EXP_ReplicationTrx, Object> COLUMN_IsReplicationTrxFinished = new ModelColumn<>(I_EXP_ReplicationTrx.class, "IsReplicationTrxFinished", null);
	String COLUMNNAME_IsReplicationTrxFinished = "IsReplicationTrxFinished";

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

	ModelColumn<I_EXP_ReplicationTrx, Object> COLUMN_Name = new ModelColumn<>(I_EXP_ReplicationTrx.class, "Name", null);
	String COLUMNNAME_Name = "Name";

	/**
	 * Set Note.
	 * Optional additional user defined information
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setNote (@Nullable java.lang.String Note);

	/**
	 * Get Note.
	 * Optional additional user defined information
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getNote();

	ModelColumn<I_EXP_ReplicationTrx, Object> COLUMN_Note = new ModelColumn<>(I_EXP_ReplicationTrx.class, "Note", null);
	String COLUMNNAME_Note = "Note";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_EXP_ReplicationTrx, Object> COLUMN_Updated = new ModelColumn<>(I_EXP_ReplicationTrx.class, "Updated", null);
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
