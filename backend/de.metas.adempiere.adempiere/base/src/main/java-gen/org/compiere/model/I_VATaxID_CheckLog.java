package org.compiere.model;

import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for VATaxID_CheckLog
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_VATaxID_CheckLog 
{

	String Table_Name = "VATaxID_CheckLog";

//	/** AD_Table_ID=542639 */
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
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_PInstance_ID (int AD_PInstance_ID);

	/**
	 * Get Process Instance.
	 * Instance of a Process
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_PInstance_ID();

	ModelColumn<I_VATaxID_CheckLog, org.compiere.model.I_AD_PInstance> COLUMN_AD_PInstance_ID = new ModelColumn<>(I_VATaxID_CheckLog.class, "AD_PInstance_ID", org.compiere.model.I_AD_PInstance.class);
	String COLUMNNAME_AD_PInstance_ID = "AD_PInstance_ID";

	/**
	 * Set User Session.
	 * User Session Online or Web
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_Session_ID (int AD_Session_ID);

	/**
	 * Get User Session.
	 * User Session Online or Web
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_Session_ID();

	ModelColumn<I_VATaxID_CheckLog, org.compiere.model.I_AD_Session> COLUMN_AD_Session_ID = new ModelColumn<>(I_VATaxID_CheckLog.class, "AD_Session_ID", org.compiere.model.I_AD_Session.class);
	String COLUMNNAME_AD_Session_ID = "AD_Session_ID";

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
	 * Set Location.
	 * Identifies the address of the business partner
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_BPartner_Location_ID (int C_BPartner_Location_ID);

	/**
	 * Get Location.
	 * Identifies the address of the business partner
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_BPartner_Location_ID();

	String COLUMNNAME_C_BPartner_Location_ID = "C_BPartner_Location_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_VATaxID_CheckLog, Object> COLUMN_Created = new ModelColumn<>(I_VATaxID_CheckLog.class, "Created", null);
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

	ModelColumn<I_VATaxID_CheckLog, Object> COLUMN_CreatedBy = new ModelColumn<>(I_VATaxID_CheckLog.class, "CreatedBy", null);
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

	ModelColumn<I_VATaxID_CheckLog, Object> COLUMN_IsActive = new ModelColumn<>(I_VATaxID_CheckLog.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Raw VIES Response.
	 * Unprocessed VIES service response, kept so a dispute can be reconstructed.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setRawResponse (@Nullable java.lang.String RawResponse);

	/**
	 * Get Raw VIES Response.
	 * Unprocessed VIES service response, kept so a dispute can be reconstructed.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getRawResponse();

	ModelColumn<I_VATaxID_CheckLog, Object> COLUMN_RawResponse = new ModelColumn<>(I_VATaxID_CheckLog.class, "RawResponse", null);
	String COLUMNNAME_RawResponse = "RawResponse";

	/**
	 * Set Request Sent On.
	 * Point in time the VAT-ID check request was sent to VIES.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setRequestDate (java.sql.Timestamp RequestDate);

	/**
	 * Get Request Sent On.
	 * Point in time the VAT-ID check request was sent to VIES.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getRequestDate();

	ModelColumn<I_VATaxID_CheckLog, Object> COLUMN_RequestDate = new ModelColumn<>(I_VATaxID_CheckLog.class, "RequestDate", null);
	String COLUMNNAME_RequestDate = "RequestDate";

	/**
	 * Set VIES Consultation Number.
	 * Consultation number returned by VIES;
 only present when the requester VAT-ID is configured.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setRequestIdentifier (@Nullable java.lang.String RequestIdentifier);

	/**
	 * Get VIES Consultation Number.
	 * Consultation number returned by VIES;
 only present when the requester VAT-ID is configured.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getRequestIdentifier();

	ModelColumn<I_VATaxID_CheckLog, Object> COLUMN_RequestIdentifier = new ModelColumn<>(I_VATaxID_CheckLog.class, "RequestIdentifier", null);
	String COLUMNNAME_RequestIdentifier = "RequestIdentifier";

	/**
	 * Set Response Received On.
	 * Point in time the VIES response arrived;
 empty while the check is still pending.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setResponseDate (@Nullable java.sql.Timestamp ResponseDate);

	/**
	 * Get Response Received On.
	 * Point in time the VIES response arrived;
 empty while the check is still pending.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getResponseDate();

	ModelColumn<I_VATaxID_CheckLog, Object> COLUMN_ResponseDate = new ModelColumn<>(I_VATaxID_CheckLog.class, "ResponseDate", null);
	String COLUMNNAME_ResponseDate = "ResponseDate";

	/**
	 * Set Address Returned by VIES.
	 * Address VIES returned for the checked VAT-ID (qualified check, not yet used).
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setReturnedAddress (@Nullable java.lang.String ReturnedAddress);

	/**
	 * Get Address Returned by VIES.
	 * Address VIES returned for the checked VAT-ID (qualified check, not yet used).
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getReturnedAddress();

	ModelColumn<I_VATaxID_CheckLog, Object> COLUMN_ReturnedAddress = new ModelColumn<>(I_VATaxID_CheckLog.class, "ReturnedAddress", null);
	String COLUMNNAME_ReturnedAddress = "ReturnedAddress";

	/**
	 * Set Name Returned by VIES.
	 * Company name VIES returned for the checked VAT-ID (qualified check, not yet used).
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setReturnedName (@Nullable java.lang.String ReturnedName);

	/**
	 * Get Name Returned by VIES.
	 * Company name VIES returned for the checked VAT-ID (qualified check, not yet used).
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getReturnedName();

	ModelColumn<I_VATaxID_CheckLog, Object> COLUMN_ReturnedName = new ModelColumn<>(I_VATaxID_CheckLog.class, "ReturnedName", null);
	String COLUMNNAME_ReturnedName = "ReturnedName";

	/**
	 * Set Address Match.
	 * Result of the qualified VIES check's address match (not yet used).
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setTraderAddressMatch (@Nullable java.lang.String TraderAddressMatch);

	/**
	 * Get Address Match.
	 * Result of the qualified VIES check's address match (not yet used).
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getTraderAddressMatch();

	ModelColumn<I_VATaxID_CheckLog, Object> COLUMN_TraderAddressMatch = new ModelColumn<>(I_VATaxID_CheckLog.class, "TraderAddressMatch", null);
	String COLUMNNAME_TraderAddressMatch = "TraderAddressMatch";

	/**
	 * Set Name Match.
	 * Result of the qualified VIES check's name match (not yet used).
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setTraderNameMatch (@Nullable java.lang.String TraderNameMatch);

	/**
	 * Get Name Match.
	 * Result of the qualified VIES check's name match (not yet used).
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getTraderNameMatch();

	ModelColumn<I_VATaxID_CheckLog, Object> COLUMN_TraderNameMatch = new ModelColumn<>(I_VATaxID_CheckLog.class, "TraderNameMatch", null);
	String COLUMNNAME_TraderNameMatch = "TraderNameMatch";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_VATaxID_CheckLog, Object> COLUMN_Updated = new ModelColumn<>(I_VATaxID_CheckLog.class, "Updated", null);
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

	ModelColumn<I_VATaxID_CheckLog, Object> COLUMN_UpdatedBy = new ModelColumn<>(I_VATaxID_CheckLog.class, "UpdatedBy", null);
	String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/**
	 * Set VAT ID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setVATaxID (java.lang.String VATaxID);

	/**
	 * Get VAT ID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getVATaxID();

	ModelColumn<I_VATaxID_CheckLog, Object> COLUMN_VATaxID = new ModelColumn<>(I_VATaxID_CheckLog.class, "VATaxID", null);
	String COLUMNNAME_VATaxID = "VATaxID";

	/**
	 * Set VAT-ID Check Log.
	 * Log of individual VAT-ID online check attempts against VIES.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setVATaxID_CheckLog_ID (int VATaxID_CheckLog_ID);

	/**
	 * Get VAT-ID Check Log.
	 * Log of individual VAT-ID online check attempts against VIES.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getVATaxID_CheckLog_ID();

	ModelColumn<I_VATaxID_CheckLog, Object> COLUMN_VATaxID_CheckLog_ID = new ModelColumn<>(I_VATaxID_CheckLog.class, "VATaxID_CheckLog_ID", null);
	String COLUMNNAME_VATaxID_CheckLog_ID = "VATaxID_CheckLog_ID";

	/**
	 * Set VAT-ID Check Status.
	 * Result of the VAT-ID check.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setVATaxIDStatus (java.lang.String VATaxIDStatus);

	/**
	 * Get VAT-ID Check Status.
	 * Result of the VAT-ID check.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getVATaxIDStatus();

	ModelColumn<I_VATaxID_CheckLog, Object> COLUMN_VATaxIDStatus = new ModelColumn<>(I_VATaxID_CheckLog.class, "VATaxIDStatus", null);
	String COLUMNNAME_VATaxIDStatus = "VATaxIDStatus";
}
