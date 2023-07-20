package org.compiere.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;

/** Generated Interface for C_BPartner_Location_QuickInput
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_C_BPartner_Location_QuickInput 
{

	String Table_Name = "C_BPartner_Location_QuickInput";

//	/** AD_Table_ID=541968 */
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
	 * Set Partner Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setBPartnerName (@Nullable java.lang.String BPartnerName);

	/**
	 * Get Partner Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getBPartnerName();

	ModelColumn<I_C_BPartner_Location_QuickInput, Object> COLUMN_BPartnerName = new ModelColumn<>(I_C_BPartner_Location_QuickInput.class, "BPartnerName", null);
	String COLUMNNAME_BPartnerName = "BPartnerName";

	/**
	 * Set Partner Location Quick Input.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_BPartner_Location_QuickInput_ID (int C_BPartner_Location_QuickInput_ID);

	/**
	 * Get Partner Location Quick Input.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_BPartner_Location_QuickInput_ID();

	ModelColumn<I_C_BPartner_Location_QuickInput, Object> COLUMN_C_BPartner_Location_QuickInput_ID = new ModelColumn<>(I_C_BPartner_Location_QuickInput.class, "C_BPartner_Location_QuickInput_ID", null);
	String COLUMNNAME_C_BPartner_Location_QuickInput_ID = "C_BPartner_Location_QuickInput_ID";

	/**
	 * Set New BPartner quick input.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_BPartner_QuickInput_ID (int C_BPartner_QuickInput_ID);

	/**
	 * Get New BPartner quick input.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_BPartner_QuickInput_ID();

	org.compiere.model.I_C_BPartner_QuickInput getC_BPartner_QuickInput();

	void setC_BPartner_QuickInput(org.compiere.model.I_C_BPartner_QuickInput C_BPartner_QuickInput);

	ModelColumn<I_C_BPartner_Location_QuickInput, org.compiere.model.I_C_BPartner_QuickInput> COLUMN_C_BPartner_QuickInput_ID = new ModelColumn<>(I_C_BPartner_Location_QuickInput.class, "C_BPartner_QuickInput_ID", org.compiere.model.I_C_BPartner_QuickInput.class);
	String COLUMNNAME_C_BPartner_QuickInput_ID = "C_BPartner_QuickInput_ID";

	/**
	 * Set Location.
	 * Location or Address
	 *
	 * <br>Type: Location
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Location_ID (int C_Location_ID);

	/**
	 * Get Location.
	 * Location or Address
	 *
	 * <br>Type: Location
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Location_ID();

	@Nullable org.compiere.model.I_C_Location getC_Location();

	void setC_Location(@Nullable org.compiere.model.I_C_Location C_Location);

	ModelColumn<I_C_BPartner_Location_QuickInput, org.compiere.model.I_C_Location> COLUMN_C_Location_ID = new ModelColumn<>(I_C_BPartner_Location_QuickInput.class, "C_Location_ID", org.compiere.model.I_C_Location.class);
	String COLUMNNAME_C_Location_ID = "C_Location_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_C_BPartner_Location_QuickInput, Object> COLUMN_Created = new ModelColumn<>(I_C_BPartner_Location_QuickInput.class, "Created", null);
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
	 * Set eMail.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setEMail (@Nullable java.lang.String EMail);

	/**
	 * Get eMail.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getEMail();

	ModelColumn<I_C_BPartner_Location_QuickInput, Object> COLUMN_EMail = new ModelColumn<>(I_C_BPartner_Location_QuickInput.class, "EMail", null);
	String COLUMNNAME_EMail = "EMail";

	/**
	 * Set Fax.
	 * Facsimile number
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setFax (@Nullable java.lang.String Fax);

	/**
	 * Get Fax.
	 * Facsimile number
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getFax();

	ModelColumn<I_C_BPartner_Location_QuickInput, Object> COLUMN_Fax = new ModelColumn<>(I_C_BPartner_Location_QuickInput.class, "Fax", null);
	String COLUMNNAME_Fax = "Fax";

	/**
	 * Set GLN.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setGLN (@Nullable java.lang.String GLN);

	/**
	 * Get GLN.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getGLN();

	ModelColumn<I_C_BPartner_Location_QuickInput, Object> COLUMN_GLN = new ModelColumn<>(I_C_BPartner_Location_QuickInput.class, "GLN", null);
	String COLUMNNAME_GLN = "GLN";

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

	ModelColumn<I_C_BPartner_Location_QuickInput, Object> COLUMN_IsActive = new ModelColumn<>(I_C_BPartner_Location_QuickInput.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Invoice Address.
	 * Business Partner Invoice/Bill Address
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsBillTo (boolean IsBillTo);

	/**
	 * Get Invoice Address.
	 * Business Partner Invoice/Bill Address
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isBillTo();

	ModelColumn<I_C_BPartner_Location_QuickInput, Object> COLUMN_IsBillTo = new ModelColumn<>(I_C_BPartner_Location_QuickInput.class, "IsBillTo", null);
	String COLUMNNAME_IsBillTo = "IsBillTo";

	/**
	 * Set Is Invoice Default.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsBillToDefault (boolean IsBillToDefault);

	/**
	 * Get Is Invoice Default.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isBillToDefault();

	ModelColumn<I_C_BPartner_Location_QuickInput, Object> COLUMN_IsBillToDefault = new ModelColumn<>(I_C_BPartner_Location_QuickInput.class, "IsBillToDefault", null);
	String COLUMNNAME_IsBillToDefault = "IsBillToDefault";

	/**
	 * Set Handover Location.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsHandOverLocation (boolean IsHandOverLocation);

	/**
	 * Get Handover Location.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isHandOverLocation();

	ModelColumn<I_C_BPartner_Location_QuickInput, Object> COLUMN_IsHandOverLocation = new ModelColumn<>(I_C_BPartner_Location_QuickInput.class, "IsHandOverLocation", null);
	String COLUMNNAME_IsHandOverLocation = "IsHandOverLocation";

	/**
	 * Set Remit-To Address.
	 * Business Partner payment address
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsRemitTo (boolean IsRemitTo);

	/**
	 * Get Remit-To Address.
	 * Business Partner payment address
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isRemitTo();

	ModelColumn<I_C_BPartner_Location_QuickInput, Object> COLUMN_IsRemitTo = new ModelColumn<>(I_C_BPartner_Location_QuickInput.class, "IsRemitTo", null);
	String COLUMNNAME_IsRemitTo = "IsRemitTo";

	/**
	 * Set Replication Lookup Default.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsReplicationLookupDefault (boolean IsReplicationLookupDefault);

	/**
	 * Get Replication Lookup Default.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isReplicationLookupDefault();

	ModelColumn<I_C_BPartner_Location_QuickInput, Object> COLUMN_IsReplicationLookupDefault = new ModelColumn<>(I_C_BPartner_Location_QuickInput.class, "IsReplicationLookupDefault", null);
	String COLUMNNAME_IsReplicationLookupDefault = "IsReplicationLookupDefault";

	/**
	 * Set Ship Address.
	 * Business Partner Shipment Address
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsShipTo (boolean IsShipTo);

	/**
	 * Get Ship Address.
	 * Business Partner Shipment Address
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isShipTo();

	ModelColumn<I_C_BPartner_Location_QuickInput, Object> COLUMN_IsShipTo = new ModelColumn<>(I_C_BPartner_Location_QuickInput.class, "IsShipTo", null);
	String COLUMNNAME_IsShipTo = "IsShipTo";

	/**
	 * Set Is Ship To Default.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsShipToDefault (boolean IsShipToDefault);

	/**
	 * Get Is Ship To Default.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isShipToDefault();

	ModelColumn<I_C_BPartner_Location_QuickInput, Object> COLUMN_IsShipToDefault = new ModelColumn<>(I_C_BPartner_Location_QuickInput.class, "IsShipToDefault", null);
	String COLUMNNAME_IsShipToDefault = "IsShipToDefault";

	/**
	 * Set Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setName (@Nullable java.lang.String Name);

	/**
	 * Get Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getName();

	ModelColumn<I_C_BPartner_Location_QuickInput, Object> COLUMN_Name = new ModelColumn<>(I_C_BPartner_Location_QuickInput.class, "Name", null);
	String COLUMNNAME_Name = "Name";

	/**
	 * Set Phone.
	 * Identifies a telephone number
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPhone (@Nullable java.lang.String Phone);

	/**
	 * Get Phone.
	 * Identifies a telephone number
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getPhone();

	ModelColumn<I_C_BPartner_Location_QuickInput, Object> COLUMN_Phone = new ModelColumn<>(I_C_BPartner_Location_QuickInput.class, "Phone", null);
	String COLUMNNAME_Phone = "Phone";

	/**
	 * Set Phone (alternative).
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPhone2 (@Nullable java.lang.String Phone2);

	/**
	 * Get Phone (alternative).
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getPhone2();

	ModelColumn<I_C_BPartner_Location_QuickInput, Object> COLUMN_Phone2 = new ModelColumn<>(I_C_BPartner_Location_QuickInput.class, "Phone2", null);
	String COLUMNNAME_Phone2 = "Phone2";

	/**
	 * Set Setup Place No..
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setSetup_Place_No (@Nullable java.lang.String Setup_Place_No);

	/**
	 * Get Setup Place No..
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getSetup_Place_No();

	ModelColumn<I_C_BPartner_Location_QuickInput, Object> COLUMN_Setup_Place_No = new ModelColumn<>(I_C_BPartner_Location_QuickInput.class, "Setup_Place_No", null);
	String COLUMNNAME_Setup_Place_No = "Setup_Place_No";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_C_BPartner_Location_QuickInput, Object> COLUMN_Updated = new ModelColumn<>(I_C_BPartner_Location_QuickInput.class, "Updated", null);
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

	/**
	 * Set Visitors Address.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setVisitorsAddress (boolean VisitorsAddress);

	/**
	 * Get Visitors Address.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isVisitorsAddress();

	ModelColumn<I_C_BPartner_Location_QuickInput, Object> COLUMN_VisitorsAddress = new ModelColumn<>(I_C_BPartner_Location_QuickInput.class, "VisitorsAddress", null);
	String COLUMNNAME_VisitorsAddress = "VisitorsAddress";
}
