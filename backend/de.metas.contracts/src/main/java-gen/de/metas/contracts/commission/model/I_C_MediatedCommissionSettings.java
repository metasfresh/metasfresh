package de.metas.contracts.commission.model;

import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for C_MediatedCommissionSettings
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_C_MediatedCommissionSettings 
{

	String Table_Name = "C_MediatedCommissionSettings";

//	/** AD_Table_ID=541795 */
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
	 * Set Brokerage commission settings.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_MediatedCommissionSettings_ID (int C_MediatedCommissionSettings_ID);

	/**
	 * Get Brokerage commission settings.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_MediatedCommissionSettings_ID();

	ModelColumn<I_C_MediatedCommissionSettings, Object> COLUMN_C_MediatedCommissionSettings_ID = new ModelColumn<>(I_C_MediatedCommissionSettings.class, "C_MediatedCommissionSettings_ID", null);
	String COLUMNNAME_C_MediatedCommissionSettings_ID = "C_MediatedCommissionSettings_ID";

	/**
	 * Set Commission product.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setCommission_Product_ID (int Commission_Product_ID);

	/**
	 * Get Commission product.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getCommission_Product_ID();

	String COLUMNNAME_Commission_Product_ID = "Commission_Product_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_C_MediatedCommissionSettings, Object> COLUMN_Created = new ModelColumn<>(I_C_MediatedCommissionSettings.class, "Created", null);
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

	ModelColumn<I_C_MediatedCommissionSettings, Object> COLUMN_Description = new ModelColumn<>(I_C_MediatedCommissionSettings.class, "Description", null);
	String COLUMNNAME_Description = "Description";

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

	ModelColumn<I_C_MediatedCommissionSettings, Object> COLUMN_IsActive = new ModelColumn<>(I_C_MediatedCommissionSettings.class, "IsActive", null);
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

	ModelColumn<I_C_MediatedCommissionSettings, Object> COLUMN_Name = new ModelColumn<>(I_C_MediatedCommissionSettings.class, "Name", null);
	String COLUMNNAME_Name = "Name";

	/**
	 * Set Points precision.
	 * Number of digits after the decimal point to which the system rounds when computing commission points.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPointsPrecision (int PointsPrecision);

	/**
	 * Get Points precision.
	 * Number of digits after the decimal point to which the system rounds when computing commission points.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getPointsPrecision();

	ModelColumn<I_C_MediatedCommissionSettings, Object> COLUMN_PointsPrecision = new ModelColumn<>(I_C_MediatedCommissionSettings.class, "PointsPrecision", null);
	String COLUMNNAME_PointsPrecision = "PointsPrecision";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_C_MediatedCommissionSettings, Object> COLUMN_Updated = new ModelColumn<>(I_C_MediatedCommissionSettings.class, "Updated", null);
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
