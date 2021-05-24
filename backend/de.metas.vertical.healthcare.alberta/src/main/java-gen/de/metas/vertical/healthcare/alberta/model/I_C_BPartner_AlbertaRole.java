package de.metas.vertical.healthcare.alberta.model;

import org.adempiere.model.ModelColumn;

/** Generated Interface for C_BPartner_AlbertaRole
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_C_BPartner_AlbertaRole 
{

	String Table_Name = "C_BPartner_AlbertaRole";

//	/** AD_Table_ID=541646 */
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
	 * Set Role.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAlbertaRole (String AlbertaRole);

	/**
	 * Get Role.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	String getAlbertaRole();

	ModelColumn<I_C_BPartner_AlbertaRole, Object> COLUMN_AlbertaRole = new ModelColumn<>(I_C_BPartner_AlbertaRole.class, "AlbertaRole", null);
	String COLUMNNAME_AlbertaRole = "AlbertaRole";

	/**
	 * Set BPartner Alberta Role.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_BPartner_AlbertaRole_ID (int C_BPartner_AlbertaRole_ID);

	/**
	 * Get BPartner Alberta Role.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_BPartner_AlbertaRole_ID();

	ModelColumn<I_C_BPartner_AlbertaRole, Object> COLUMN_C_BPartner_AlbertaRole_ID = new ModelColumn<>(I_C_BPartner_AlbertaRole.class, "C_BPartner_AlbertaRole_ID", null);
	String COLUMNNAME_C_BPartner_AlbertaRole_ID = "C_BPartner_AlbertaRole_ID";

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

	ModelColumn<I_C_BPartner_AlbertaRole, Object> COLUMN_Created = new ModelColumn<>(I_C_BPartner_AlbertaRole.class, "Created", null);
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

	ModelColumn<I_C_BPartner_AlbertaRole, Object> COLUMN_IsActive = new ModelColumn<>(I_C_BPartner_AlbertaRole.class, "IsActive", null);
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

	ModelColumn<I_C_BPartner_AlbertaRole, Object> COLUMN_Updated = new ModelColumn<>(I_C_BPartner_AlbertaRole.class, "Updated", null);
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
