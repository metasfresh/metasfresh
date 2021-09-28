package org.compiere.model;

import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for AD_User_Occupation_AdditionalSpecialization
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_AD_User_Occupation_AdditionalSpecialization 
{

	String Table_Name = "AD_User_Occupation_AdditionalSpecialization";

//	/** AD_Table_ID=541775 */
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
	 * Set Contact.
	 * User within the system - Internal or Business Partner Contact
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_User_ID (int AD_User_ID);

	/**
	 * Get Contact.
	 * User within the system - Internal or Business Partner Contact
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_User_ID();

	String COLUMNNAME_AD_User_ID = "AD_User_ID";

	/**
	 * Set User Additional Specialization.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_User_Occupation_AdditionalSpecialization_ID (int AD_User_Occupation_AdditionalSpecialization_ID);

	/**
	 * Get User Additional Specialization.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_User_Occupation_AdditionalSpecialization_ID();

	ModelColumn<I_AD_User_Occupation_AdditionalSpecialization, Object> COLUMN_AD_User_Occupation_AdditionalSpecialization_ID = new ModelColumn<>(I_AD_User_Occupation_AdditionalSpecialization.class, "AD_User_Occupation_AdditionalSpecialization_ID", null);
	String COLUMNNAME_AD_User_Occupation_AdditionalSpecialization_ID = "AD_User_Occupation_AdditionalSpecialization_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_AD_User_Occupation_AdditionalSpecialization, Object> COLUMN_Created = new ModelColumn<>(I_AD_User_Occupation_AdditionalSpecialization.class, "Created", null);
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
	 * Set Speciality.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCRM_Occupation_ID (int CRM_Occupation_ID);

	/**
	 * Get Speciality.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getCRM_Occupation_ID();

	@Nullable org.compiere.model.I_CRM_Occupation getCRM_Occupation();

	void setCRM_Occupation(@Nullable org.compiere.model.I_CRM_Occupation CRM_Occupation);

	ModelColumn<I_AD_User_Occupation_AdditionalSpecialization, org.compiere.model.I_CRM_Occupation> COLUMN_CRM_Occupation_ID = new ModelColumn<>(I_AD_User_Occupation_AdditionalSpecialization.class, "CRM_Occupation_ID", org.compiere.model.I_CRM_Occupation.class);
	String COLUMNNAME_CRM_Occupation_ID = "CRM_Occupation_ID";

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

	ModelColumn<I_AD_User_Occupation_AdditionalSpecialization, Object> COLUMN_IsActive = new ModelColumn<>(I_AD_User_Occupation_AdditionalSpecialization.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_AD_User_Occupation_AdditionalSpecialization, Object> COLUMN_Updated = new ModelColumn<>(I_AD_User_Occupation_AdditionalSpecialization.class, "Updated", null);
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
