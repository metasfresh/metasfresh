package de.metas.fresh.partnerreporttext.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;

/**
 * Generated Interface for C_BPartner_Report_Text
 *
 * @author metasfresh (generated)
 */
@SuppressWarnings("unused")
public interface I_C_BPartner_Report_Text
{

	String Table_Name = "C_BPartner_Report_Text";

	//	/** AD_Table_ID=542474 */
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
	 * Set Additional Text.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAdditionalText(@Nullable String AdditionalText);

	/**
	 * Get Additional Text.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable
	String getAdditionalText();

	ModelColumn<I_C_BPartner_Report_Text, Object> COLUMN_AdditionalText = new ModelColumn<>(I_C_BPartner_Report_Text.class, "AdditionalText", null);
	String COLUMNNAME_AdditionalText = "AdditionalText";

	/**
	 * Set Organisation.
	 * Organisational entity within client
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_Org_ID(int AD_Org_ID);

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
	 * Set Document Type.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_BPartner_DocType_ID(int C_BPartner_DocType_ID);

	/**
	 * Get Document Type.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_BPartner_DocType_ID();

	@Nullable
	I_C_BPartner_DocType getC_BPartner_DocType();

	void setC_BPartner_DocType(@Nullable I_C_BPartner_DocType C_BPartner_DocType);

	ModelColumn<I_C_BPartner_Report_Text, I_C_BPartner_DocType> COLUMN_C_BPartner_DocType_ID = new ModelColumn<>(I_C_BPartner_Report_Text.class, "C_BPartner_DocType_ID", I_C_BPartner_DocType.class);
	String COLUMNNAME_C_BPartner_DocType_ID = "C_BPartner_DocType_ID";

	/**
	 * Set Business Partner.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_BPartner_ID(int C_BPartner_ID);

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
	 * Set Report Texts.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_BPartner_Report_Text_ID(int C_BPartner_Report_Text_ID);

	/**
	 * Get Report Texts.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_BPartner_Report_Text_ID();

	ModelColumn<I_C_BPartner_Report_Text, Object> COLUMN_C_BPartner_Report_Text_ID = new ModelColumn<>(I_C_BPartner_Report_Text.class, "C_BPartner_Report_Text_ID", null);
	String COLUMNNAME_C_BPartner_Report_Text_ID = "C_BPartner_Report_Text_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_C_BPartner_Report_Text, Object> COLUMN_Created = new ModelColumn<>(I_C_BPartner_Report_Text.class, "Created", null);
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
	void setIsActive(boolean IsActive);

	/**
	 * Get Active.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isActive();

	ModelColumn<I_C_BPartner_Report_Text, Object> COLUMN_IsActive = new ModelColumn<>(I_C_BPartner_Report_Text.class, "IsActive", null);
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

	ModelColumn<I_C_BPartner_Report_Text, Object> COLUMN_Updated = new ModelColumn<>(I_C_BPartner_Report_Text.class, "Updated", null);
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
	void setValue(String Value);

	/**
	 * Get Search Key.
	 * Search key for the record in the format required - must be unique
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	String getValue();

	ModelColumn<I_C_BPartner_Report_Text, Object> COLUMN_Value = new ModelColumn<>(I_C_BPartner_Report_Text.class, "Value", null);
	String COLUMNNAME_Value = "Value";
}
