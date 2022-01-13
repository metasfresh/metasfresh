package org.compiere.model;

import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for C_Postal
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_C_Postal 
{

	String Table_Name = "C_Postal";

//	/** AD_Table_ID=540317 */
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
	 * Set Organisation in charge.
	 * Organisation which is in charge of this postal number.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_Org_InCharge_ID (int AD_Org_InCharge_ID);

	/**
	 * Get Organisation in charge.
	 * Organisation which is in charge of this postal number.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_Org_InCharge_ID();

	String COLUMNNAME_AD_Org_InCharge_ID = "AD_Org_InCharge_ID";

	/**
	 * Set Ort.
	 * Ort
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_City_ID (int C_City_ID);

	/**
	 * Get Ort.
	 * Ort
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_City_ID();

	@Nullable org.compiere.model.I_C_City getC_City();

	void setC_City(@Nullable org.compiere.model.I_C_City C_City);

	ModelColumn<I_C_Postal, org.compiere.model.I_C_City> COLUMN_C_City_ID = new ModelColumn<>(I_C_Postal.class, "C_City_ID", org.compiere.model.I_C_City.class);
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

	ModelColumn<I_C_Postal, org.compiere.model.I_C_Country> COLUMN_C_Country_ID = new ModelColumn<>(I_C_Postal.class, "C_Country_ID", org.compiere.model.I_C_Country.class);
	String COLUMNNAME_C_Country_ID = "C_Country_ID";

	/**
	 * Set Postal codes.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Postal_ID (int C_Postal_ID);

	/**
	 * Get Postal codes.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Postal_ID();

	ModelColumn<I_C_Postal, Object> COLUMN_C_Postal_ID = new ModelColumn<>(I_C_Postal.class, "C_Postal_ID", null);
	String COLUMNNAME_C_Postal_ID = "C_Postal_ID";

	/**
	 * Set Region.
	 * Identifiziert eine geographische Region
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Region_ID (int C_Region_ID);

	/**
	 * Get Region.
	 * Identifiziert eine geographische Region
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Region_ID();

	@Nullable org.compiere.model.I_C_Region getC_Region();

	void setC_Region(@Nullable org.compiere.model.I_C_Region C_Region);

	ModelColumn<I_C_Postal, org.compiere.model.I_C_Region> COLUMN_C_Region_ID = new ModelColumn<>(I_C_Postal.class, "C_Region_ID", org.compiere.model.I_C_Region.class);
	String COLUMNNAME_C_Region_ID = "C_Region_ID";

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

	ModelColumn<I_C_Postal, Object> COLUMN_City = new ModelColumn<>(I_C_Postal.class, "City", null);
	String COLUMNNAME_City = "City";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_C_Postal, Object> COLUMN_Created = new ModelColumn<>(I_C_Postal.class, "Created", null);
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
	 * Set Bezirk.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDistrict (@Nullable java.lang.String District);

	/**
	 * Get Bezirk.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDistrict();

	ModelColumn<I_C_Postal, Object> COLUMN_District = new ModelColumn<>(I_C_Postal.class, "District", null);
	String COLUMNNAME_District = "District";

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

	ModelColumn<I_C_Postal, Object> COLUMN_IsActive = new ModelColumn<>(I_C_Postal.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Manuell.
	 * Dies ist ein manueller Vorgang
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsManual (boolean IsManual);

	/**
	 * Get Manuell.
	 * Dies ist ein manueller Vorgang
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isManual();

	ModelColumn<I_C_Postal, Object> COLUMN_IsManual = new ModelColumn<>(I_C_Postal.class, "IsManual", null);
	String COLUMNNAME_IsManual = "IsManual";

	/**
	 * Set DPD Validated.
	 * Record was validated on DPD database
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIsValidDPD (boolean IsValidDPD);

	/**
	 * Get DPD Validated.
	 * Record was validated on DPD database
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isValidDPD();

	ModelColumn<I_C_Postal, Object> COLUMN_IsValidDPD = new ModelColumn<>(I_C_Postal.class, "IsValidDPD", null);
	String COLUMNNAME_IsValidDPD = "IsValidDPD";

	/**
	 * Set Bereinigung notwendig.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setNonStdAddress (boolean NonStdAddress);

	/**
	 * Get Bereinigung notwendig.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isNonStdAddress();

	ModelColumn<I_C_Postal, Object> COLUMN_NonStdAddress = new ModelColumn<>(I_C_Postal.class, "NonStdAddress", null);
	String COLUMNNAME_NonStdAddress = "NonStdAddress";

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

	ModelColumn<I_C_Postal, Object> COLUMN_Postal = new ModelColumn<>(I_C_Postal.class, "Postal", null);
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

	ModelColumn<I_C_Postal, Object> COLUMN_Postal_Add = new ModelColumn<>(I_C_Postal.class, "Postal_Add", null);
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

	ModelColumn<I_C_Postal, Object> COLUMN_RegionName = new ModelColumn<>(I_C_Postal.class, "RegionName", null);
	String COLUMNNAME_RegionName = "RegionName";

	/**
	 * Set Gemeinde.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setTownship (@Nullable java.lang.String Township);

	/**
	 * Get Gemeinde.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getTownship();

	ModelColumn<I_C_Postal, Object> COLUMN_Township = new ModelColumn<>(I_C_Postal.class, "Township", null);
	String COLUMNNAME_Township = "Township";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_C_Postal, Object> COLUMN_Updated = new ModelColumn<>(I_C_Postal.class, "Updated", null);
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
