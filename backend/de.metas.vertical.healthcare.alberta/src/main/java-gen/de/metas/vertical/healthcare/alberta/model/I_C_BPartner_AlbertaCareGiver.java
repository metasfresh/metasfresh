package de.metas.vertical.healthcare.alberta.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;

/** Generated Interface for C_BPartner_AlbertaCareGiver
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_C_BPartner_AlbertaCareGiver 
{

	String Table_Name = "C_BPartner_AlbertaCareGiver";

//	/** AD_Table_ID=541645 */
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
	 * Set Caregiver.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_BPartner_AlbertaCareGiver_ID (int C_BPartner_AlbertaCareGiver_ID);

	/**
	 * Get Caregiver.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_BPartner_AlbertaCareGiver_ID();

	ModelColumn<I_C_BPartner_AlbertaCareGiver, Object> COLUMN_C_BPartner_AlbertaCareGiver_ID = new ModelColumn<>(I_C_BPartner_AlbertaCareGiver.class, "C_BPartner_AlbertaCareGiver_ID", null);
	String COLUMNNAME_C_BPartner_AlbertaCareGiver_ID = "C_BPartner_AlbertaCareGiver_ID";

	/**
	 * Set Caregiver.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_BPartner_Caregiver_ID (int C_BPartner_Caregiver_ID);

	/**
	 * Get Caregiver.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_BPartner_Caregiver_ID();

	String COLUMNNAME_C_BPartner_Caregiver_ID = "C_BPartner_Caregiver_ID";

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
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_C_BPartner_AlbertaCareGiver, Object> COLUMN_Created = new ModelColumn<>(I_C_BPartner_AlbertaCareGiver.class, "Created", null);
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

	ModelColumn<I_C_BPartner_AlbertaCareGiver, Object> COLUMN_IsActive = new ModelColumn<>(I_C_BPartner_AlbertaCareGiver.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Legal carer.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIsLegalCarer (boolean IsLegalCarer);

	/**
	 * Get Legal carer.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isLegalCarer();

	ModelColumn<I_C_BPartner_AlbertaCareGiver, Object> COLUMN_IsLegalCarer = new ModelColumn<>(I_C_BPartner_AlbertaCareGiver.class, "IsLegalCarer", null);
	String COLUMNNAME_IsLegalCarer = "IsLegalCarer";

	/**
	 * Set Type of contact.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setType_Contact (@Nullable String Type_Contact);

	/**
	 * Get Type of contact.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable String getType_Contact();

	ModelColumn<I_C_BPartner_AlbertaCareGiver, Object> COLUMN_Type_Contact = new ModelColumn<>(I_C_BPartner_AlbertaCareGiver.class, "Type_Contact", null);
	String COLUMNNAME_Type_Contact = "Type_Contact";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_C_BPartner_AlbertaCareGiver, Object> COLUMN_Updated = new ModelColumn<>(I_C_BPartner_AlbertaCareGiver.class, "Updated", null);
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
