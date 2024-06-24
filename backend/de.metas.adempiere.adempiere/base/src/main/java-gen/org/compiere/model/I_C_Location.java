package org.compiere.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;
import java.math.BigDecimal;

/** Generated Interface for C_Location
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_C_Location 
{

	String Table_Name = "C_Location";

//	/** AD_Table_ID=162 */
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
	 * Set Street & House No..
	 * Address line 1 for this location
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAddress1 (@Nullable java.lang.String Address1);

	/**
	 * Get Street & House No..
	 * Address line 1 for this location
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getAddress1();

	ModelColumn<I_C_Location, Object> COLUMN_Address1 = new ModelColumn<>(I_C_Location.class, "Address1", null);
	String COLUMNNAME_Address1 = "Address1";

	/**
	 * Set Address 2.
	 * Address line 2 for this location
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAddress2 (@Nullable java.lang.String Address2);

	/**
	 * Get Address 2.
	 * Address line 2 for this location
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getAddress2();

	ModelColumn<I_C_Location, Object> COLUMN_Address2 = new ModelColumn<>(I_C_Location.class, "Address2", null);
	String COLUMNNAME_Address2 = "Address2";

	/**
	 * Set Adresszeile 3.
	 * Address Line 3 for the location
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAddress3 (@Nullable java.lang.String Address3);

	/**
	 * Get Adresszeile 3.
	 * Address Line 3 for the location
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getAddress3();

	ModelColumn<I_C_Location, Object> COLUMN_Address3 = new ModelColumn<>(I_C_Location.class, "Address3", null);
	String COLUMNNAME_Address3 = "Address3";

	/**
	 * Set Adresszusatz.
	 * Adresszeile 4 für diesen Standort
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAddress4 (@Nullable java.lang.String Address4);

	/**
	 * Get Adresszusatz.
	 * Adresszeile 4 für diesen Standort
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getAddress4();

	ModelColumn<I_C_Location, Object> COLUMN_Address4 = new ModelColumn<>(I_C_Location.class, "Address4", null);
	String COLUMNNAME_Address4 = "Address4";

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
	 * Set C/O.
	 * In care of
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCareOf (@Nullable java.lang.String CareOf);

	/**
	 * Get C/O.
	 * In care of
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getCareOf();

	ModelColumn<I_C_Location, Object> COLUMN_CareOf = new ModelColumn<>(I_C_Location.class, "CareOf", null);
	String COLUMNNAME_CareOf = "CareOf";

	/**
	 * Set Ort.
	 * City
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_City_ID (int C_City_ID);

	/**
	 * Get Ort.
	 * City
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_City_ID();

	@Nullable org.compiere.model.I_C_City getC_City();

	void setC_City(@Nullable org.compiere.model.I_C_City C_City);

	ModelColumn<I_C_Location, org.compiere.model.I_C_City> COLUMN_C_City_ID = new ModelColumn<>(I_C_Location.class, "C_City_ID", org.compiere.model.I_C_City.class);
	String COLUMNNAME_C_City_ID = "C_City_ID";

	/**
	 * Set Country.
	 * Country
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Country_ID (int C_Country_ID);

	/**
	 * Get Country.
	 * Country
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Country_ID();

	org.compiere.model.I_C_Country getC_Country();

	void setC_Country(org.compiere.model.I_C_Country C_Country);

	ModelColumn<I_C_Location, org.compiere.model.I_C_Country> COLUMN_C_Country_ID = new ModelColumn<>(I_C_Location.class, "C_Country_ID", org.compiere.model.I_C_Country.class);
	String COLUMNNAME_C_Country_ID = "C_Country_ID";

	/**
	 * Set City Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCity (@Nullable java.lang.String City);

	/**
	 * Get City Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getCity();

	ModelColumn<I_C_Location, Object> COLUMN_City = new ModelColumn<>(I_C_Location.class, "City", null);
	String COLUMNNAME_City = "City";

	/**
	 * Set Location.
	 * Location or Address
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Location_ID (int C_Location_ID);

	/**
	 * Get Location.
	 * Location or Address
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Location_ID();

	ModelColumn<I_C_Location, Object> COLUMN_C_Location_ID = new ModelColumn<>(I_C_Location.class, "C_Location_ID", null);
	String COLUMNNAME_C_Location_ID = "C_Location_ID";

	/**
	 * Set Postal codes.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Postal_ID (int C_Postal_ID);

	/**
	 * Get Postal codes.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Postal_ID();

	@Nullable org.compiere.model.I_C_Postal getC_Postal();

	void setC_Postal(@Nullable org.compiere.model.I_C_Postal C_Postal);

	ModelColumn<I_C_Location, org.compiere.model.I_C_Postal> COLUMN_C_Postal_ID = new ModelColumn<>(I_C_Location.class, "C_Postal_ID", org.compiere.model.I_C_Postal.class);
	String COLUMNNAME_C_Postal_ID = "C_Postal_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_C_Location, Object> COLUMN_Created = new ModelColumn<>(I_C_Location.class, "Created", null);
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
	 * Set Region.
	 * Identifies a geographical Region
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Region_ID (int C_Region_ID);

	/**
	 * Get Region.
	 * Identifies a geographical Region
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Region_ID();

	@Nullable org.compiere.model.I_C_Region getC_Region();

	void setC_Region(@Nullable org.compiere.model.I_C_Region C_Region);

	ModelColumn<I_C_Location, org.compiere.model.I_C_Region> COLUMN_C_Region_ID = new ModelColumn<>(I_C_Location.class, "C_Region_ID", org.compiere.model.I_C_Region.class);
	String COLUMNNAME_C_Region_ID = "C_Region_ID";

	/**
	 * Set DHL Post ID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDHL_PostId (@Nullable java.lang.String DHL_PostId);

	/**
	 * Get DHL Post ID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDHL_PostId();

	ModelColumn<I_C_Location, Object> COLUMN_DHL_PostId = new ModelColumn<>(I_C_Location.class, "DHL_PostId", null);
	String COLUMNNAME_DHL_PostId = "DHL_PostId";

	/**
	 * Set Geocoding Error.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setGeocoding_Issue_ID (int Geocoding_Issue_ID);

	/**
	 * Get Geocoding Error.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getGeocoding_Issue_ID();

	String COLUMNNAME_Geocoding_Issue_ID = "Geocoding_Issue_ID";

	/**
	 * Set Geocoding Status.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setGeocodingStatus (java.lang.String GeocodingStatus);

	/**
	 * Get Geocoding Status.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getGeocodingStatus();

	ModelColumn<I_C_Location, Object> COLUMN_GeocodingStatus = new ModelColumn<>(I_C_Location.class, "GeocodingStatus", null);
	String COLUMNNAME_GeocodingStatus = "GeocodingStatus";

	/**
	 * Set Hausnummer.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setHouseNumber (@Nullable java.lang.String HouseNumber);

	/**
	 * Get Hausnummer.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getHouseNumber();

	ModelColumn<I_C_Location, Object> COLUMN_HouseNumber = new ModelColumn<>(I_C_Location.class, "HouseNumber", null);
	String COLUMNNAME_HouseNumber = "HouseNumber";

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

	ModelColumn<I_C_Location, Object> COLUMN_IsActive = new ModelColumn<>(I_C_Location.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Is P.O. box.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsPOBoxNum (boolean IsPOBoxNum);

	/**
	 * Get Is P.O. box.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isPOBoxNum();

	ModelColumn<I_C_Location, Object> COLUMN_IsPOBoxNum = new ModelColumn<>(I_C_Location.class, "IsPOBoxNum", null);
	String COLUMNNAME_IsPOBoxNum = "IsPOBoxNum";

	/**
	 * Set PLZ verifiziert.
	 * Sagt aus, ob Postleitzahl der Adresse verifiziert wurde.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsPostalValidated (boolean IsPostalValidated);

	/**
	 * Get PLZ verifiziert.
	 * Sagt aus, ob Postleitzahl der Adresse verifiziert wurde.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isPostalValidated();

	ModelColumn<I_C_Location, Object> COLUMN_IsPostalValidated = new ModelColumn<>(I_C_Location.class, "IsPostalValidated", null);
	String COLUMNNAME_IsPostalValidated = "IsPostalValidated";

	/**
	 * Set Latitude.
	 * Geographical latitude
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setLatitude (@Nullable BigDecimal Latitude);

	/**
	 * Get Latitude.
	 * Geographical latitude
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getLatitude();

	ModelColumn<I_C_Location, Object> COLUMN_Latitude = new ModelColumn<>(I_C_Location.class, "Latitude", null);
	String COLUMNNAME_Latitude = "Latitude";

	/**
	 * Set Längengrad.
	 * Geographical longitude
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setLongitude (@Nullable BigDecimal Longitude);

	/**
	 * Get Längengrad.
	 * Geographical longitude
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getLongitude();

	ModelColumn<I_C_Location, Object> COLUMN_Longitude = new ModelColumn<>(I_C_Location.class, "Longitude", null);
	String COLUMNNAME_Longitude = "Longitude";

	/**
	 * Set P.O. box number.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPOBox (@Nullable java.lang.String POBox);

	/**
	 * Get P.O. box number.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getPOBox();

	ModelColumn<I_C_Location, Object> COLUMN_POBox = new ModelColumn<>(I_C_Location.class, "POBox", null);
	String COLUMNNAME_POBox = "POBox";

	/**
	 * Set Postal.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPostal (@Nullable java.lang.String Postal);

	/**
	 * Get Postal.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getPostal();

	ModelColumn<I_C_Location, Object> COLUMN_Postal = new ModelColumn<>(I_C_Location.class, "Postal", null);
	String COLUMNNAME_Postal = "Postal";

	/**
	 * Set -.
	 * Additional ZIP or Postal code
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPostal_Add (@Nullable java.lang.String Postal_Add);

	/**
	 * Get -.
	 * Additional ZIP or Postal code
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getPostal_Add();

	ModelColumn<I_C_Location, Object> COLUMN_Postal_Add = new ModelColumn<>(I_C_Location.class, "Postal_Add", null);
	String COLUMNNAME_Postal_Add = "Postal_Add";

	/**
	 * Set Region Name.
	 * Name of the Region
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setRegionName (@Nullable java.lang.String RegionName);

	/**
	 * Get Region Name.
	 * Name of the Region
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getRegionName();

	ModelColumn<I_C_Location, Object> COLUMN_RegionName = new ModelColumn<>(I_C_Location.class, "RegionName", null);
	String COLUMNNAME_RegionName = "RegionName";

	/**
	 * Set Straße.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setStreet (@Nullable java.lang.String Street);

	/**
	 * Get Straße.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getStreet();

	ModelColumn<I_C_Location, Object> COLUMN_Street = new ModelColumn<>(I_C_Location.class, "Street", null);
	String COLUMNNAME_Street = "Street";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_C_Location, Object> COLUMN_Updated = new ModelColumn<>(I_C_Location.class, "Updated", null);
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
