package de.metas.externalsystem.model;

import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for SAP_BPartnerImportSettings
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_SAP_BPartnerImportSettings 
{

	String Table_Name = "SAP_BPartnerImportSettings";

//	/** AD_Table_ID=542325 */
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
	 * Set Business Partner Group.
	 * Business Partner Group
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_BP_Group_ID (int C_BP_Group_ID);

	/**
	 * Get Business Partner Group.
	 * Business Partner Group
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_BP_Group_ID();

	@Nullable org.compiere.model.I_C_BP_Group getC_BP_Group();

	void setC_BP_Group(@Nullable org.compiere.model.I_C_BP_Group C_BP_Group);

	ModelColumn<I_SAP_BPartnerImportSettings, org.compiere.model.I_C_BP_Group> COLUMN_C_BP_Group_ID = new ModelColumn<>(I_SAP_BPartnerImportSettings.class, "C_BP_Group_ID", org.compiere.model.I_C_BP_Group.class);
	String COLUMNNAME_C_BP_Group_ID = "C_BP_Group_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_SAP_BPartnerImportSettings, Object> COLUMN_Created = new ModelColumn<>(I_SAP_BPartnerImportSettings.class, "Created", null);
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
	 * Set ExternalSystem_Config_SAP.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setExternalSystem_Config_SAP_ID (int ExternalSystem_Config_SAP_ID);

	/**
	 * Get ExternalSystem_Config_SAP.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getExternalSystem_Config_SAP_ID();

	de.metas.externalsystem.model.I_ExternalSystem_Config_SAP getExternalSystem_Config_SAP();

	void setExternalSystem_Config_SAP(de.metas.externalsystem.model.I_ExternalSystem_Config_SAP ExternalSystem_Config_SAP);

	ModelColumn<I_SAP_BPartnerImportSettings, de.metas.externalsystem.model.I_ExternalSystem_Config_SAP> COLUMN_ExternalSystem_Config_SAP_ID = new ModelColumn<>(I_SAP_BPartnerImportSettings.class, "ExternalSystem_Config_SAP_ID", de.metas.externalsystem.model.I_ExternalSystem_Config_SAP.class);
	String COLUMNNAME_ExternalSystem_Config_SAP_ID = "ExternalSystem_Config_SAP_ID";

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

	ModelColumn<I_SAP_BPartnerImportSettings, Object> COLUMN_IsActive = new ModelColumn<>(I_SAP_BPartnerImportSettings.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Single Business Partner.
	 * If true, the business partners having the partner code falling under the regex pattern, will not be aggregated based on the code and no section group business partner is created
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setisSingleBPartner (boolean isSingleBPartner);

	/**
	 * Get Single Business Partner.
	 * If true, the business partners having the partner code falling under the regex pattern, will not be aggregated based on the code and no section group business partner is created
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isSingleBPartner();

	ModelColumn<I_SAP_BPartnerImportSettings, Object> COLUMN_isSingleBPartner = new ModelColumn<>(I_SAP_BPartnerImportSettings.class, "isSingleBPartner", null);
	String COLUMNNAME_isSingleBPartner = "isSingleBPartner";

	/**
	 * Set Partner Code Pattern.
	 * Regex pattern for the partner code
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPartnerCodePattern (java.lang.String PartnerCodePattern);

	/**
	 * Get Partner Code Pattern.
	 * Regex pattern for the partner code
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getPartnerCodePattern();

	ModelColumn<I_SAP_BPartnerImportSettings, Object> COLUMN_PartnerCodePattern = new ModelColumn<>(I_SAP_BPartnerImportSettings.class, "PartnerCodePattern", null);
	String COLUMNNAME_PartnerCodePattern = "PartnerCodePattern";

	/**
	 * Set SAP BPartner Import Settings.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setSAP_BPartnerImportSettings_ID (int SAP_BPartnerImportSettings_ID);

	/**
	 * Get SAP BPartner Import Settings.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getSAP_BPartnerImportSettings_ID();

	ModelColumn<I_SAP_BPartnerImportSettings, Object> COLUMN_SAP_BPartnerImportSettings_ID = new ModelColumn<>(I_SAP_BPartnerImportSettings.class, "SAP_BPartnerImportSettings_ID", null);
	String COLUMNNAME_SAP_BPartnerImportSettings_ID = "SAP_BPartnerImportSettings_ID";

	/**
	 * Set SeqNo.
	 * Method of ordering records;
 lowest number comes first
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setSeqNo (int SeqNo);

	/**
	 * Get SeqNo.
	 * Method of ordering records;
 lowest number comes first
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getSeqNo();

	ModelColumn<I_SAP_BPartnerImportSettings, Object> COLUMN_SeqNo = new ModelColumn<>(I_SAP_BPartnerImportSettings.class, "SeqNo", null);
	String COLUMNNAME_SeqNo = "SeqNo";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_SAP_BPartnerImportSettings, Object> COLUMN_Updated = new ModelColumn<>(I_SAP_BPartnerImportSettings.class, "Updated", null);
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
