package de.metas.serviceprovider.model;

import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for S_FailedTimeBooking
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_S_FailedTimeBooking 
{

	String Table_Name = "S_FailedTimeBooking";

//	/** AD_Table_ID=541487 */
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

	ModelColumn<I_S_FailedTimeBooking, Object> COLUMN_Created = new ModelColumn<>(I_S_FailedTimeBooking.class, "Created", null);
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

	ModelColumn<I_S_FailedTimeBooking, Object> COLUMN_ExternalId = new ModelColumn<>(I_S_FailedTimeBooking.class, "ExternalId", null);
	String COLUMNNAME_ExternalId = "ExternalId";

	/**
	 * Set External system.
	 * Name of an external system (e.g. Github )
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setExternalSystem (@Nullable java.lang.String ExternalSystem);

	/**
	 * Get External system.
	 * Name of an external system (e.g. Github )
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getExternalSystem();

	ModelColumn<I_S_FailedTimeBooking, Object> COLUMN_ExternalSystem = new ModelColumn<>(I_S_FailedTimeBooking.class, "ExternalSystem", null);
	String COLUMNNAME_ExternalSystem = "ExternalSystem";

	/**
	 * Set Error Message.
	 * Fehler beim Einlesen der Datei, z.B. Fehler im Format eines Datums
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setImportErrorMsg (@Nullable java.lang.String ImportErrorMsg);

	/**
	 * Get Error Message.
	 * Fehler beim Einlesen der Datei, z.B. Fehler im Format eines Datums
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getImportErrorMsg();

	ModelColumn<I_S_FailedTimeBooking, Object> COLUMN_ImportErrorMsg = new ModelColumn<>(I_S_FailedTimeBooking.class, "ImportErrorMsg", null);
	String COLUMNNAME_ImportErrorMsg = "ImportErrorMsg";

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

	ModelColumn<I_S_FailedTimeBooking, Object> COLUMN_IsActive = new ModelColumn<>(I_S_FailedTimeBooking.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set JSON value.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setJSONValue (@Nullable java.lang.String JSONValue);

	/**
	 * Get JSON value.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getJSONValue();

	ModelColumn<I_S_FailedTimeBooking, Object> COLUMN_JSONValue = new ModelColumn<>(I_S_FailedTimeBooking.class, "JSONValue", null);
	String COLUMNNAME_JSONValue = "JSONValue";

	/**
	 * Set Failed time booking.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setS_FailedTimeBooking_ID (int S_FailedTimeBooking_ID);

	/**
	 * Get Failed time booking.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getS_FailedTimeBooking_ID();

	ModelColumn<I_S_FailedTimeBooking, Object> COLUMN_S_FailedTimeBooking_ID = new ModelColumn<>(I_S_FailedTimeBooking.class, "S_FailedTimeBooking_ID", null);
	String COLUMNNAME_S_FailedTimeBooking_ID = "S_FailedTimeBooking_ID";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_S_FailedTimeBooking, Object> COLUMN_Updated = new ModelColumn<>(I_S_FailedTimeBooking.class, "Updated", null);
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
