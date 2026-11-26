package org.compiere.model;

import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for M_MeansOfTransportation
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_M_MeansOfTransportation 
{

	String Table_Name = "M_MeansOfTransportation";

//	/** AD_Table_ID=542268 */
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
	 * Set Business Partner.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_BPartner_ID (int C_BPartner_ID);

	/**
	 * Get Business Partner.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
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

	ModelColumn<I_M_MeansOfTransportation, Object> COLUMN_Created = new ModelColumn<>(I_M_MeansOfTransportation.class, "Created", null);
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
	 * Set Description.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDescription (@Nullable java.lang.String Description);

	/**
	 * Get Description.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDescription();

	ModelColumn<I_M_MeansOfTransportation, Object> COLUMN_Description = new ModelColumn<>(I_M_MeansOfTransportation.class, "Description", null);
	String COLUMNNAME_Description = "Description";

	/**
	 * Set Description URL.
	 * URL for the description
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDescriptionURL (@Nullable java.lang.String DescriptionURL);

	/**
	 * Get Description URL.
	 * URL for the description
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDescriptionURL();

	ModelColumn<I_M_MeansOfTransportation, Object> COLUMN_DescriptionURL = new ModelColumn<>(I_M_MeansOfTransportation.class, "DescriptionURL", null);
	String COLUMNNAME_DescriptionURL = "DescriptionURL";

	/**
	 * Set IMO/MMSI Number.
	 * International Maritime Organization/Maritime Mobile Service Identity
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIMO_MMSI_Number (@Nullable java.lang.String IMO_MMSI_Number);

	/**
	 * Get IMO/MMSI Number.
	 * International Maritime Organization/Maritime Mobile Service Identity
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getIMO_MMSI_Number();

	ModelColumn<I_M_MeansOfTransportation, Object> COLUMN_IMO_MMSI_Number = new ModelColumn<>(I_M_MeansOfTransportation.class, "IMO_MMSI_Number", null);
	String COLUMNNAME_IMO_MMSI_Number = "IMO_MMSI_Number";

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

	ModelColumn<I_M_MeansOfTransportation, Object> COLUMN_IsActive = new ModelColumn<>(I_M_MeansOfTransportation.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Means of Transportation.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_MeansOfTransportation_ID (int M_MeansOfTransportation_ID);

	/**
	 * Get Means of Transportation.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_MeansOfTransportation_ID();

	ModelColumn<I_M_MeansOfTransportation, Object> COLUMN_M_MeansOfTransportation_ID = new ModelColumn<>(I_M_MeansOfTransportation.class, "M_MeansOfTransportation_ID", null);
	String COLUMNNAME_M_MeansOfTransportation_ID = "M_MeansOfTransportation_ID";

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

	ModelColumn<I_M_MeansOfTransportation, Object> COLUMN_Name = new ModelColumn<>(I_M_MeansOfTransportation.class, "Name", null);
	String COLUMNNAME_Name = "Name";

	/**
	 * Set Plane.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPlane (@Nullable java.lang.String Plane);

	/**
	 * Get Plane.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getPlane();

	ModelColumn<I_M_MeansOfTransportation, Object> COLUMN_Plane = new ModelColumn<>(I_M_MeansOfTransportation.class, "Plane", null);
	String COLUMNNAME_Plane = "Plane";

	/**
	 * Set Plate Nr.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPlate_Number (@Nullable java.lang.String Plate_Number);

	/**
	 * Get Plate Nr.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getPlate_Number();

	ModelColumn<I_M_MeansOfTransportation, Object> COLUMN_Plate_Number = new ModelColumn<>(I_M_MeansOfTransportation.class, "Plate_Number", null);
	String COLUMNNAME_Plate_Number = "Plate_Number";

	/**
	 * Set RTC.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setRTC (@Nullable java.lang.String RTC);

	/**
	 * Get RTC.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getRTC();

	ModelColumn<I_M_MeansOfTransportation, Object> COLUMN_RTC = new ModelColumn<>(I_M_MeansOfTransportation.class, "RTC", null);
	String COLUMNNAME_RTC = "RTC";

	/**
	 * Set Means Of Transportation Type.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setType_MoT (@Nullable java.lang.String Type_MoT);

	/**
	 * Get Means Of Transportation Type.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getType_MoT();

	ModelColumn<I_M_MeansOfTransportation, Object> COLUMN_Type_MoT = new ModelColumn<>(I_M_MeansOfTransportation.class, "Type_MoT", null);
	String COLUMNNAME_Type_MoT = "Type_MoT";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_M_MeansOfTransportation, Object> COLUMN_Updated = new ModelColumn<>(I_M_MeansOfTransportation.class, "Updated", null);
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
	 * Set Search Key.
	 * Search key for the record in the format required - must be unique
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setValue (java.lang.String Value);

	/**
	 * Get Search Key.
	 * Search key for the record in the format required - must be unique
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getValue();

	ModelColumn<I_M_MeansOfTransportation, Object> COLUMN_Value = new ModelColumn<>(I_M_MeansOfTransportation.class, "Value", null);
	String COLUMNNAME_Value = "Value";
}
