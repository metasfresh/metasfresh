package org.compiere.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;

/** Generated Interface for C_BPartner_Location
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_C_BPartner_Location 
{

	String Table_Name = "C_BPartner_Location";

//	/** AD_Table_ID=293 */
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
	 * Set Org Mapping.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_Org_Mapping_ID (int AD_Org_Mapping_ID);

	/**
	 * Get Org Mapping.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_Org_Mapping_ID();

	@Nullable org.compiere.model.I_AD_Org_Mapping getAD_Org_Mapping();

	void setAD_Org_Mapping(@Nullable org.compiere.model.I_AD_Org_Mapping AD_Org_Mapping);

	ModelColumn<I_C_BPartner_Location, org.compiere.model.I_AD_Org_Mapping> COLUMN_AD_Org_Mapping_ID = new ModelColumn<>(I_C_BPartner_Location.class, "AD_Org_Mapping_ID", org.compiere.model.I_AD_Org_Mapping.class);
	String COLUMNNAME_AD_Org_Mapping_ID = "AD_Org_Mapping_ID";

	/**
	 * Set Address.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAddress (@Nullable java.lang.String Address);

	/**
	 * Get Address.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getAddress();

	ModelColumn<I_C_BPartner_Location, Object> COLUMN_Address = new ModelColumn<>(I_C_BPartner_Location.class, "Address", null);
	String COLUMNNAME_Address = "Address";

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

	ModelColumn<I_C_BPartner_Location, Object> COLUMN_BPartnerName = new ModelColumn<>(I_C_BPartner_Location.class, "BPartnerName", null);
	String COLUMNNAME_BPartnerName = "BPartnerName";

	/**
	 * Set Business Partner.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_BPartner_ID (int C_BPartner_ID);

	/**
	 * Get Business Partner.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_BPartner_ID();

	String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/**
	 * Set Location.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_BPartner_Location_ID (int C_BPartner_Location_ID);

	/**
	 * Get Location.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_BPartner_Location_ID();

	ModelColumn<I_C_BPartner_Location, Object> COLUMN_C_BPartner_Location_ID = new ModelColumn<>(I_C_BPartner_Location.class, "C_BPartner_Location_ID", null);
	String COLUMNNAME_C_BPartner_Location_ID = "C_BPartner_Location_ID";

	/**
	 * Set Location.
	 * Location or Address
	 *
	 * <br>Type: Location
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Location_ID (int C_Location_ID);

	/**
	 * Get Location.
	 * Location or Address
	 *
	 * <br>Type: Location
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Location_ID();

	org.compiere.model.I_C_Location getC_Location();

	void setC_Location(org.compiere.model.I_C_Location C_Location);

	ModelColumn<I_C_BPartner_Location, org.compiere.model.I_C_Location> COLUMN_C_Location_ID = new ModelColumn<>(I_C_BPartner_Location.class, "C_Location_ID", org.compiere.model.I_C_Location.class);
	String COLUMNNAME_C_Location_ID = "C_Location_ID";

	/**
	 * Set Sales Region.
	 * Sales coverage region
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_SalesRegion_ID (int C_SalesRegion_ID);

	/**
	 * Get Sales Region.
	 * Sales coverage region
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_SalesRegion_ID();

	@Nullable org.compiere.model.I_C_SalesRegion getC_SalesRegion();

	void setC_SalesRegion(@Nullable org.compiere.model.I_C_SalesRegion C_SalesRegion);

	ModelColumn<I_C_BPartner_Location, org.compiere.model.I_C_SalesRegion> COLUMN_C_SalesRegion_ID = new ModelColumn<>(I_C_BPartner_Location.class, "C_SalesRegion_ID", org.compiere.model.I_C_SalesRegion.class);
	String COLUMNNAME_C_SalesRegion_ID = "C_SalesRegion_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_C_BPartner_Location, Object> COLUMN_Created = new ModelColumn<>(I_C_BPartner_Location.class, "Created", null);
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

	ModelColumn<I_C_BPartner_Location, Object> COLUMN_EMail = new ModelColumn<>(I_C_BPartner_Location.class, "EMail", null);
	String COLUMNNAME_EMail = "EMail";

	/**
	 * Set Alternative eMail.
	 * EMail-Adresse
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setEMail2 (@Nullable java.lang.String EMail2);

	/**
	 * Get Alternative eMail.
	 * EMail-Adresse
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getEMail2();

	ModelColumn<I_C_BPartner_Location, Object> COLUMN_EMail2 = new ModelColumn<>(I_C_BPartner_Location.class, "EMail2", null);
	String COLUMNNAME_EMail2 = "EMail2";

	/**
	 * Set External ID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setExternalId (@Nullable java.lang.String ExternalId);

	/**
	 * Get External ID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getExternalId();

	ModelColumn<I_C_BPartner_Location, Object> COLUMN_ExternalId = new ModelColumn<>(I_C_BPartner_Location.class, "ExternalId", null);
	String COLUMNNAME_ExternalId = "ExternalId";

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

	ModelColumn<I_C_BPartner_Location, Object> COLUMN_Fax = new ModelColumn<>(I_C_BPartner_Location.class, "Fax", null);
	String COLUMNNAME_Fax = "Fax";

	/**
	 * Set Fax2.
	 * Faxnummer
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setFax2 (@Nullable java.lang.String Fax2);

	/**
	 * Get Fax2.
	 * Faxnummer
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getFax2();

	ModelColumn<I_C_BPartner_Location, Object> COLUMN_Fax2 = new ModelColumn<>(I_C_BPartner_Location.class, "Fax2", null);
	String COLUMNNAME_Fax2 = "Fax2";

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

	ModelColumn<I_C_BPartner_Location, Object> COLUMN_GLN = new ModelColumn<>(I_C_BPartner_Location.class, "GLN", null);
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

	ModelColumn<I_C_BPartner_Location, Object> COLUMN_IsActive = new ModelColumn<>(I_C_BPartner_Location.class, "IsActive", null);
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

	ModelColumn<I_C_BPartner_Location, Object> COLUMN_IsBillTo = new ModelColumn<>(I_C_BPartner_Location.class, "IsBillTo", null);
	String COLUMNNAME_IsBillTo = "IsBillTo";

	/**
	 * Set Is Invoice Default.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIsBillToDefault (boolean IsBillToDefault);

	/**
	 * Get Is Invoice Default.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isBillToDefault();

	ModelColumn<I_C_BPartner_Location, Object> COLUMN_IsBillToDefault = new ModelColumn<>(I_C_BPartner_Location.class, "IsBillToDefault", null);
	String COLUMNNAME_IsBillToDefault = "IsBillToDefault";

	/**
	 * Set Commission address.
	 * Provisionsabrechnungen werden hierhin geschickt
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIsCommissionTo (boolean IsCommissionTo);

	/**
	 * Get Commission address.
	 * Provisionsabrechnungen werden hierhin geschickt
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isCommissionTo();

	ModelColumn<I_C_BPartner_Location, Object> COLUMN_IsCommissionTo = new ModelColumn<>(I_C_BPartner_Location.class, "IsCommissionTo", null);
	String COLUMNNAME_IsCommissionTo = "IsCommissionTo";

	/**
	 * Set Provision Standard Adresse.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIsCommissionToDefault (boolean IsCommissionToDefault);

	/**
	 * Get Provision Standard Adresse.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isCommissionToDefault();

	ModelColumn<I_C_BPartner_Location, Object> COLUMN_IsCommissionToDefault = new ModelColumn<>(I_C_BPartner_Location.class, "IsCommissionToDefault", null);
	String COLUMNNAME_IsCommissionToDefault = "IsCommissionToDefault";

	/**
	 * Set ISDN.
	 * ISDN or modem line
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setISDN (@Nullable java.lang.String ISDN);

	/**
	 * Get ISDN.
	 * ISDN or modem line
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getISDN();

	ModelColumn<I_C_BPartner_Location, Object> COLUMN_ISDN = new ModelColumn<>(I_C_BPartner_Location.class, "ISDN", null);
	String COLUMNNAME_ISDN = "ISDN";

	/**
	 * Set One-time-address.
	 * One-time addresses are ephemeral business partner addresses created via the REST API. If an address is marked as a one-time address, it will be used when importing documents into the metafresh system. However, it is not available for selection when creating new documents in the metasfresh user interface.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsEphemeral (boolean IsEphemeral);

	/**
	 * Get One-time-address.
	 * One-time addresses are ephemeral business partner addresses created via the REST API. If an address is marked as a one-time address, it will be used when importing documents into the metafresh system. However, it is not available for selection when creating new documents in the metasfresh user interface.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isEphemeral();

	ModelColumn<I_C_BPartner_Location, Object> COLUMN_IsEphemeral = new ModelColumn<>(I_C_BPartner_Location.class, "IsEphemeral", null);
	String COLUMNNAME_IsEphemeral = "IsEphemeral";

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

	ModelColumn<I_C_BPartner_Location, Object> COLUMN_IsHandOverLocation = new ModelColumn<>(I_C_BPartner_Location.class, "IsHandOverLocation", null);
	String COLUMNNAME_IsHandOverLocation = "IsHandOverLocation";

	/**
	 * Set Name editable.
	 * If unticked, then the address name is maintained by metasfresh
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsNameReadWrite (boolean IsNameReadWrite);

	/**
	 * Get Name editable.
	 * If unticked, then the address name is maintained by metasfresh
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isNameReadWrite();

	ModelColumn<I_C_BPartner_Location, Object> COLUMN_IsNameReadWrite = new ModelColumn<>(I_C_BPartner_Location.class, "IsNameReadWrite", null);
	String COLUMNNAME_IsNameReadWrite = "IsNameReadWrite";

	/**
	 * Set Pay-From Address.
	 * Business Partner pays from that address and we'll send dunning letters there
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsPayFrom (boolean IsPayFrom);

	/**
	 * Get Pay-From Address.
	 * Business Partner pays from that address and we'll send dunning letters there
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isPayFrom();

	ModelColumn<I_C_BPartner_Location, Object> COLUMN_IsPayFrom = new ModelColumn<>(I_C_BPartner_Location.class, "IsPayFrom", null);
	String COLUMNNAME_IsPayFrom = "IsPayFrom";

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

	ModelColumn<I_C_BPartner_Location, Object> COLUMN_IsRemitTo = new ModelColumn<>(I_C_BPartner_Location.class, "IsRemitTo", null);
	String COLUMNNAME_IsRemitTo = "IsRemitTo";

	/**
	 * Set Replication Lookup Default.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIsReplicationLookupDefault (boolean IsReplicationLookupDefault);

	/**
	 * Get Replication Lookup Default.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isReplicationLookupDefault();

	ModelColumn<I_C_BPartner_Location, Object> COLUMN_IsReplicationLookupDefault = new ModelColumn<>(I_C_BPartner_Location.class, "IsReplicationLookupDefault", null);
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

	ModelColumn<I_C_BPartner_Location, Object> COLUMN_IsShipTo = new ModelColumn<>(I_C_BPartner_Location.class, "IsShipTo", null);
	String COLUMNNAME_IsShipTo = "IsShipTo";

	/**
	 * Set Is Ship To Default.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIsShipToDefault (boolean IsShipToDefault);

	/**
	 * Get Is Ship To Default.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isShipToDefault();

	ModelColumn<I_C_BPartner_Location, Object> COLUMN_IsShipToDefault = new ModelColumn<>(I_C_BPartner_Location.class, "IsShipToDefault", null);
	String COLUMNNAME_IsShipToDefault = "IsShipToDefault";

	/**
	 * Set isSubscriptionTo.
	 * An diese Adresse werden Abos geschickt
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIsSubscriptionTo (boolean IsSubscriptionTo);

	/**
	 * Get isSubscriptionTo.
	 * An diese Adresse werden Abos geschickt
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isSubscriptionTo();

	ModelColumn<I_C_BPartner_Location, Object> COLUMN_IsSubscriptionTo = new ModelColumn<>(I_C_BPartner_Location.class, "IsSubscriptionTo", null);
	String COLUMNNAME_IsSubscriptionTo = "IsSubscriptionTo";

	/**
	 * Set Abo Standard Adresse.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIsSubscriptionToDefault (boolean IsSubscriptionToDefault);

	/**
	 * Get Abo Standard Adresse.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isSubscriptionToDefault();

	ModelColumn<I_C_BPartner_Location, Object> COLUMN_IsSubscriptionToDefault = new ModelColumn<>(I_C_BPartner_Location.class, "IsSubscriptionToDefault", null);
	String COLUMNNAME_IsSubscriptionToDefault = "IsSubscriptionToDefault";

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

	ModelColumn<I_C_BPartner_Location, Object> COLUMN_Name = new ModelColumn<>(I_C_BPartner_Location.class, "Name", null);
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

	ModelColumn<I_C_BPartner_Location, Object> COLUMN_Phone = new ModelColumn<>(I_C_BPartner_Location.class, "Phone", null);
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

	ModelColumn<I_C_BPartner_Location, Object> COLUMN_Phone2 = new ModelColumn<>(I_C_BPartner_Location.class, "Phone2", null);
	String COLUMNNAME_Phone2 = "Phone2";

	/**
	 * Set Previous Address.
	 * The address that will be replaced by the current one in open sales candidates, billing candidates and flatrate terms.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPrevious_ID (int Previous_ID);

	/**
	 * Get Previous Address.
	 * The address that will be replaced by the current one in open sales candidates, billing candidates and flatrate terms.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getPrevious_ID();

	String COLUMNNAME_Previous_ID = "Previous_ID";

	/**
	 * Set SAP BPartner Id.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setSAP_BPartnerCode (@Nullable java.lang.String SAP_BPartnerCode);

	/**
	 * Get SAP BPartner Id.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getSAP_BPartnerCode();

	ModelColumn<I_C_BPartner_Location, Object> COLUMN_SAP_BPartnerCode = new ModelColumn<>(I_C_BPartner_Location.class, "SAP_BPartnerCode", null);
	String COLUMNNAME_SAP_BPartnerCode = "SAP_BPartnerCode";

	/**
	 * Set SAP Payment method.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setSAP_PaymentMethod (@Nullable java.lang.String SAP_PaymentMethod);

	/**
	 * Get SAP Payment method.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getSAP_PaymentMethod();

	ModelColumn<I_C_BPartner_Location, Object> COLUMN_SAP_PaymentMethod = new ModelColumn<>(I_C_BPartner_Location.class, "SAP_PaymentMethod", null);
	String COLUMNNAME_SAP_PaymentMethod = "SAP_PaymentMethod";

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

	ModelColumn<I_C_BPartner_Location, Object> COLUMN_Setup_Place_No = new ModelColumn<>(I_C_BPartner_Location.class, "Setup_Place_No", null);
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

	ModelColumn<I_C_BPartner_Location, Object> COLUMN_Updated = new ModelColumn<>(I_C_BPartner_Location.class, "Updated", null);
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
	 * Set Valid From.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setValidFrom (@Nullable java.sql.Timestamp ValidFrom);

	/**
	 * Get Valid From.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getValidFrom();

	ModelColumn<I_C_BPartner_Location, Object> COLUMN_ValidFrom = new ModelColumn<>(I_C_BPartner_Location.class, "ValidFrom", null);
	String COLUMNNAME_ValidFrom = "ValidFrom";

	/**
	 * Set VAT ID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setVATaxID (@Nullable java.lang.String VATaxID);

	/**
	 * Get VAT ID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getVATaxID();

	ModelColumn<I_C_BPartner_Location, Object> COLUMN_VATaxID = new ModelColumn<>(I_C_BPartner_Location.class, "VATaxID", null);
	String COLUMNNAME_VATaxID = "VATaxID";

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

	ModelColumn<I_C_BPartner_Location, Object> COLUMN_VisitorsAddress = new ModelColumn<>(I_C_BPartner_Location.class, "VisitorsAddress", null);
	String COLUMNNAME_VisitorsAddress = "VisitorsAddress";
}
