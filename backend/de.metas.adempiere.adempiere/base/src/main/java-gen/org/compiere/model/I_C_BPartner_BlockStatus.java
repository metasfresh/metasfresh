package org.compiere.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;

/** Generated Interface for C_BPartner_BlockStatus
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_C_BPartner_BlockStatus 
{

	String Table_Name = "C_BPartner_BlockStatus";

//	/** AD_Table_ID=542315 */
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
	 * Set Block status.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setBlockStatus (java.lang.String BlockStatus);

	/**
	 * Get Block status.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getBlockStatus();

	ModelColumn<I_C_BPartner_BlockStatus, Object> COLUMN_BlockStatus = new ModelColumn<>(I_C_BPartner_BlockStatus.class, "BlockStatus", null);
	String COLUMNNAME_BlockStatus = "BlockStatus";

	/**
	 * Set Block status.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_BPartner_BlockStatus_ID (int C_BPartner_BlockStatus_ID);

	/**
	 * Get Block status.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_BPartner_BlockStatus_ID();

	ModelColumn<I_C_BPartner_BlockStatus, Object> COLUMN_C_BPartner_BlockStatus_ID = new ModelColumn<>(I_C_BPartner_BlockStatus.class, "C_BPartner_BlockStatus_ID", null);
	String COLUMNNAME_C_BPartner_BlockStatus_ID = "C_BPartner_BlockStatus_ID";

	/**
	 * Set Business Partner.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_BPartner_ID (int C_BPartner_ID);

	/**
	 * Get Business Partner.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_BPartner_ID();

	String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_C_BPartner_BlockStatus, Object> COLUMN_Created = new ModelColumn<>(I_C_BPartner_BlockStatus.class, "Created", null);
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

	ModelColumn<I_C_BPartner_BlockStatus, Object> COLUMN_IsActive = new ModelColumn<>(I_C_BPartner_BlockStatus.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Current Valid Version.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsCurrent (boolean IsCurrent);

	/**
	 * Get Current Valid Version.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isCurrent();

	ModelColumn<I_C_BPartner_BlockStatus, Object> COLUMN_IsCurrent = new ModelColumn<>(I_C_BPartner_BlockStatus.class, "IsCurrent", null);
	String COLUMNNAME_IsCurrent = "IsCurrent";

	/**
	 * Set Reason.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setReason (@Nullable java.lang.String Reason);

	/**
	 * Get Reason.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getReason();

	ModelColumn<I_C_BPartner_BlockStatus, Object> COLUMN_Reason = new ModelColumn<>(I_C_BPartner_BlockStatus.class, "Reason", null);
	String COLUMNNAME_Reason = "Reason";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_C_BPartner_BlockStatus, Object> COLUMN_Updated = new ModelColumn<>(I_C_BPartner_BlockStatus.class, "Updated", null);
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
