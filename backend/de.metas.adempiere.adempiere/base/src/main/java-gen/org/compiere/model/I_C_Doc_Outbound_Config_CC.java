package org.compiere.model;

import org.adempiere.model.ModelColumn;

/** Generated Interface for C_Doc_Outbound_Config_CC
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_C_Doc_Outbound_Config_CC 
{

	String Table_Name = "C_Doc_Outbound_Config_CC";

//	/** AD_Table_ID=542430 */
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
	 * Set Print Format.
	 * The print format determines how data is rendered for print.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_PrintFormat_ID (int AD_PrintFormat_ID);

	/**
	 * Get Print Format.
	 * The print format determines how data is rendered for print.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_PrintFormat_ID();

	org.compiere.model.I_AD_PrintFormat getAD_PrintFormat();

	void setAD_PrintFormat(org.compiere.model.I_AD_PrintFormat AD_PrintFormat);

	ModelColumn<I_C_Doc_Outbound_Config_CC, org.compiere.model.I_AD_PrintFormat> COLUMN_AD_PrintFormat_ID = new ModelColumn<>(I_C_Doc_Outbound_Config_CC.class, "AD_PrintFormat_ID", org.compiere.model.I_AD_PrintFormat.class);
	String COLUMNNAME_AD_PrintFormat_ID = "AD_PrintFormat_ID";

	/**
	 * Set BPartner Column name.
	 * BPartner Column name
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setBPartner_ColumnName_ID (int BPartner_ColumnName_ID);

	/**
	 * Get BPartner Column name.
	 * BPartner Column name
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getBPartner_ColumnName_ID();

	org.compiere.model.I_AD_Column getBPartner_ColumnName();

	void setBPartner_ColumnName(org.compiere.model.I_AD_Column BPartner_ColumnName);

	ModelColumn<I_C_Doc_Outbound_Config_CC, org.compiere.model.I_AD_Column> COLUMN_BPartner_ColumnName_ID = new ModelColumn<>(I_C_Doc_Outbound_Config_CC.class, "BPartner_ColumnName_ID", org.compiere.model.I_AD_Column.class);
	String COLUMNNAME_BPartner_ColumnName_ID = "BPartner_ColumnName_ID";


	/**
	 * Set Override DocType
	 * Override_DocType_ID
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setOverride_DocType_ID (int Override_DocType_ID);

	/**
	 * Get Override DocType
	 * Override_DocType_ID
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getOverride_DocType_ID();

	I_C_DocType getOverride_DocType();

	void setOverride_DocType(I_C_DocType Override_DocType);

	ModelColumn<I_C_Doc_Outbound_Config_CC, I_C_DocType> COLUMN_Override_DocType_ID = new ModelColumn<>(I_C_Doc_Outbound_Config_CC.class, "Override_DocType_ID", I_C_DocType.class);
	String COLUMNNAME_Override_DocType_ID = "Override_DocType_ID";

	/**
	 * Set Outbound Doc Config CC.
	 * Table used to add several print formats that shall be used for the same table
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Doc_Outbound_Config_CC_ID (int C_Doc_Outbound_Config_CC_ID);

	/**
	 * Get Outbound Doc Config CC.
	 * Table used to add several print formats that shall be used for the same table
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Doc_Outbound_Config_CC_ID();

	ModelColumn<I_C_Doc_Outbound_Config_CC, Object> COLUMN_C_Doc_Outbound_Config_CC_ID = new ModelColumn<>(I_C_Doc_Outbound_Config_CC.class, "C_Doc_Outbound_Config_CC_ID", null);
	String COLUMNNAME_C_Doc_Outbound_Config_CC_ID = "C_Doc_Outbound_Config_CC_ID";

	/**
	 * Set Ausgehende Belege Konfig.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Doc_Outbound_Config_ID (int C_Doc_Outbound_Config_ID);

	/**
	 * Get Ausgehende Belege Konfig.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Doc_Outbound_Config_ID();

	org.compiere.model.I_C_Doc_Outbound_Config getC_Doc_Outbound_Config();

	void setC_Doc_Outbound_Config(org.compiere.model.I_C_Doc_Outbound_Config C_Doc_Outbound_Config);

	ModelColumn<I_C_Doc_Outbound_Config_CC, org.compiere.model.I_C_Doc_Outbound_Config> COLUMN_C_Doc_Outbound_Config_ID = new ModelColumn<>(I_C_Doc_Outbound_Config_CC.class, "C_Doc_Outbound_Config_ID", org.compiere.model.I_C_Doc_Outbound_Config.class);
	String COLUMNNAME_C_Doc_Outbound_Config_ID = "C_Doc_Outbound_Config_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_C_Doc_Outbound_Config_CC, Object> COLUMN_Created = new ModelColumn<>(I_C_Doc_Outbound_Config_CC.class, "Created", null);
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

	ModelColumn<I_C_Doc_Outbound_Config_CC, Object> COLUMN_IsActive = new ModelColumn<>(I_C_Doc_Outbound_Config_CC.class, "IsActive", null);
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

	ModelColumn<I_C_Doc_Outbound_Config_CC, Object> COLUMN_Updated = new ModelColumn<>(I_C_Doc_Outbound_Config_CC.class, "Updated", null);
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
