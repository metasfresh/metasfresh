package de.metas.payment.sumup.repository.model;

import org.adempiere.model.ModelColumn;

/** Generated Interface for SUMUP_CardReader
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_SUMUP_CardReader 
{

	String Table_Name = "SUMUP_CardReader";

//	/** AD_Table_ID=542441 */
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

	ModelColumn<I_SUMUP_CardReader, Object> COLUMN_Created = new ModelColumn<>(I_SUMUP_CardReader.class, "Created", null);
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
	 * Set External ID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setExternalId (java.lang.String ExternalId);

	/**
	 * Get External ID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getExternalId();

	ModelColumn<I_SUMUP_CardReader, Object> COLUMN_ExternalId = new ModelColumn<>(I_SUMUP_CardReader.class, "ExternalId", null);
	String COLUMNNAME_ExternalId = "ExternalId";

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

	ModelColumn<I_SUMUP_CardReader, Object> COLUMN_IsActive = new ModelColumn<>(I_SUMUP_CardReader.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

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

	ModelColumn<I_SUMUP_CardReader, Object> COLUMN_Name = new ModelColumn<>(I_SUMUP_CardReader.class, "Name", null);
	String COLUMNNAME_Name = "Name";

	/**
	 * Set Card Reader.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setSUMUP_CardReader_ID (int SUMUP_CardReader_ID);

	/**
	 * Get Card Reader.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getSUMUP_CardReader_ID();

	ModelColumn<I_SUMUP_CardReader, Object> COLUMN_SUMUP_CardReader_ID = new ModelColumn<>(I_SUMUP_CardReader.class, "SUMUP_CardReader_ID", null);
	String COLUMNNAME_SUMUP_CardReader_ID = "SUMUP_CardReader_ID";

	/**
	 * Set SumUp Configuration.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setSUMUP_Config_ID (int SUMUP_Config_ID);

	/**
	 * Get SumUp Configuration.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getSUMUP_Config_ID();

	de.metas.payment.sumup.repository.model.I_SUMUP_Config getSUMUP_Config();

	void setSUMUP_Config(de.metas.payment.sumup.repository.model.I_SUMUP_Config SUMUP_Config);

	ModelColumn<I_SUMUP_CardReader, de.metas.payment.sumup.repository.model.I_SUMUP_Config> COLUMN_SUMUP_Config_ID = new ModelColumn<>(I_SUMUP_CardReader.class, "SUMUP_Config_ID", de.metas.payment.sumup.repository.model.I_SUMUP_Config.class);
	String COLUMNNAME_SUMUP_Config_ID = "SUMUP_Config_ID";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_SUMUP_CardReader, Object> COLUMN_Updated = new ModelColumn<>(I_SUMUP_CardReader.class, "Updated", null);
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
