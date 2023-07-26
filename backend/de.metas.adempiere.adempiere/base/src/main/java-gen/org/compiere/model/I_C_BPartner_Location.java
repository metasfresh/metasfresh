package org.compiere.model;

import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

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
	 * Set Address.
	 * Anschrift
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAddress (@Nullable java.lang.String Address);

	/**
	 * Get Address.
	 * Anschrift
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getAddress();

	ModelColumn<I_C_BPartner_Location, Object> COLUMN_Address = new ModelColumn<>(I_C_BPartner_Location.class, "Address", null);
	String COLUMNNAME_Address = "Address";

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
	 * Set Vorbelegung Rechnung.
	 * Rechnungs-Adresse für diesen Geschäftspartner
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsBillTo (boolean IsBillTo);

	/**
	 * Get Vorbelegung Rechnung.
	 * Rechnungs-Adresse für diesen Geschäftspartner
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
	 * Set Zahlungs-Adresse.
	 * Business Partner pays from that address and we'll send dunning letters there
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsPayFrom (boolean IsPayFrom);

	/**
	 * Get Zahlungs-Adresse.
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
	 * Set Erstattungs-Adresse.
	 * Business Partner payment address
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsRemitTo (boolean IsRemitTo);

	/**
	 * Get Erstattungs-Adresse.
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
	 * Set Lieferstandard.
	 * Liefer-Adresse für den Geschäftspartner
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsShipTo (boolean IsShipTo);

	/**
	 * Get Lieferstandard.
	 * Liefer-Adresse für den Geschäftspartner
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
	 * Set Abo Adresse.
	 * An diese Adresse werden Abos geschickt
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIsSubscriptionTo (boolean IsSubscriptionTo);

	/**
	 * Get Abo Adresse.
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
