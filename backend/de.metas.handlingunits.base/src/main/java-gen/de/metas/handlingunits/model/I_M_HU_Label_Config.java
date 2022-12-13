package de.metas.handlingunits.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;

/** Generated Interface for M_HU_Label_Config
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_M_HU_Label_Config 
{

	String Table_Name = "M_HU_Label_Config";

//	/** AD_Table_ID=542272 */
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
	 * Set Copies to Print Directly.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAutoPrintCopies (int AutoPrintCopies);

	/**
	 * Get Copies to Print Directly.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAutoPrintCopies();

	ModelColumn<I_M_HU_Label_Config, Object> COLUMN_AutoPrintCopies = new ModelColumn<>(I_M_HU_Label_Config.class, "AutoPrintCopies", null);
	String COLUMNNAME_AutoPrintCopies = "AutoPrintCopies";

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

	ModelColumn<I_M_HU_Label_Config, Object> COLUMN_Created = new ModelColumn<>(I_M_HU_Label_Config.class, "Created", null);
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
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDescription (@Nullable java.lang.String Description);

	/**
	 * Get Description.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDescription();

	ModelColumn<I_M_HU_Label_Config, Object> COLUMN_Description = new ModelColumn<>(I_M_HU_Label_Config.class, "Description", null);
	String COLUMNNAME_Description = "Description";

	/**
	 * Set Source Document Type.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setHU_SourceDocType (@Nullable java.lang.String HU_SourceDocType);

	/**
	 * Get Source Document Type.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getHU_SourceDocType();

	ModelColumn<I_M_HU_Label_Config, Object> COLUMN_HU_SourceDocType = new ModelColumn<>(I_M_HU_Label_Config.class, "HU_SourceDocType", null);
	String COLUMNNAME_HU_SourceDocType = "HU_SourceDocType";

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

	ModelColumn<I_M_HU_Label_Config, Object> COLUMN_IsActive = new ModelColumn<>(I_M_HU_Label_Config.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Apply to CUs.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsApplyToCUs (boolean IsApplyToCUs);

	/**
	 * Get Apply to CUs.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isApplyToCUs();

	ModelColumn<I_M_HU_Label_Config, Object> COLUMN_IsApplyToCUs = new ModelColumn<>(I_M_HU_Label_Config.class, "IsApplyToCUs", null);
	String COLUMNNAME_IsApplyToCUs = "IsApplyToCUs";

	/**
	 * Set Apply to LUs.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsApplyToLUs (boolean IsApplyToLUs);

	/**
	 * Get Apply to LUs.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isApplyToLUs();

	ModelColumn<I_M_HU_Label_Config, Object> COLUMN_IsApplyToLUs = new ModelColumn<>(I_M_HU_Label_Config.class, "IsApplyToLUs", null);
	String COLUMNNAME_IsApplyToLUs = "IsApplyToLUs";

	/**
	 * Set Apply to TUs.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsApplyToTUs (boolean IsApplyToTUs);

	/**
	 * Get Apply to TUs.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isApplyToTUs();

	ModelColumn<I_M_HU_Label_Config, Object> COLUMN_IsApplyToTUs = new ModelColumn<>(I_M_HU_Label_Config.class, "IsApplyToTUs", null);
	String COLUMNNAME_IsApplyToTUs = "IsApplyToTUs";

	/**
	 * Set Print directly.
	 * Print directly when the HU becomes Active
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsAutoPrint (boolean IsAutoPrint);

	/**
	 * Get Print directly.
	 * Print directly when the HU becomes Active
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isAutoPrint();

	ModelColumn<I_M_HU_Label_Config, Object> COLUMN_IsAutoPrint = new ModelColumn<>(I_M_HU_Label_Config.class, "IsAutoPrint", null);
	String COLUMNNAME_IsAutoPrint = "IsAutoPrint";

	/**
	 * Set Label Print Format.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setLabelReport_Process_ID (int LabelReport_Process_ID);

	/**
	 * Get Label Print Format.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getLabelReport_Process_ID();

	org.compiere.model.I_AD_Process getLabelReport_Process();

	void setLabelReport_Process(org.compiere.model.I_AD_Process LabelReport_Process);

	ModelColumn<I_M_HU_Label_Config, org.compiere.model.I_AD_Process> COLUMN_LabelReport_Process_ID = new ModelColumn<>(I_M_HU_Label_Config.class, "LabelReport_Process_ID", org.compiere.model.I_AD_Process.class);
	String COLUMNNAME_LabelReport_Process_ID = "LabelReport_Process_ID";

	/**
	 * Set HU Labels Configuration.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_HU_Label_Config_ID (int M_HU_Label_Config_ID);

	/**
	 * Get HU Labels Configuration.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_HU_Label_Config_ID();

	ModelColumn<I_M_HU_Label_Config, Object> COLUMN_M_HU_Label_Config_ID = new ModelColumn<>(I_M_HU_Label_Config.class, "M_HU_Label_Config_ID", null);
	String COLUMNNAME_M_HU_Label_Config_ID = "M_HU_Label_Config_ID";

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

	ModelColumn<I_M_HU_Label_Config, Object> COLUMN_SeqNo = new ModelColumn<>(I_M_HU_Label_Config.class, "SeqNo", null);
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

	ModelColumn<I_M_HU_Label_Config, Object> COLUMN_Updated = new ModelColumn<>(I_M_HU_Label_Config.class, "Updated", null);
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
