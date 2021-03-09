package de.metas.handlingunits.model;

import org.adempiere.model.ModelColumn;

/** Generated Interface for M_HU_Trx_Hdr
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_M_HU_Trx_Hdr 
{

	String Table_Name = "M_HU_Trx_Hdr";

//	/** AD_Table_ID=540514 */
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

	ModelColumn<I_M_HU_Trx_Hdr, Object> COLUMN_Created = new ModelColumn<>(I_M_HU_Trx_Hdr.class, "Created", null);
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

	ModelColumn<I_M_HU_Trx_Hdr, Object> COLUMN_IsActive = new ModelColumn<>(I_M_HU_Trx_Hdr.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set HU-Transaktionskopf.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_HU_Trx_Hdr_ID (int M_HU_Trx_Hdr_ID);

	/**
	 * Get HU-Transaktionskopf.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_HU_Trx_Hdr_ID();

	ModelColumn<I_M_HU_Trx_Hdr, Object> COLUMN_M_HU_Trx_Hdr_ID = new ModelColumn<>(I_M_HU_Trx_Hdr.class, "M_HU_Trx_Hdr_ID", null);
	String COLUMNNAME_M_HU_Trx_Hdr_ID = "M_HU_Trx_Hdr_ID";

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

	ModelColumn<I_M_HU_Trx_Hdr, Object> COLUMN_Processed = new ModelColumn<>(I_M_HU_Trx_Hdr.class, "Processed", null);
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

	ModelColumn<I_M_HU_Trx_Hdr, Object> COLUMN_Updated = new ModelColumn<>(I_M_HU_Trx_Hdr.class, "Updated", null);
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
