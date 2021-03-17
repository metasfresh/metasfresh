package org.compiere.model;

import org.adempiere.model.ModelColumn;

/** Generated Interface for AD_Zebra_Config
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_AD_Zebra_Config 
{

	String Table_Name = "AD_Zebra_Config";

//	/** AD_Table_ID=541579 */
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
	 * Set Zebra Config.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_Zebra_Config_ID (int AD_Zebra_Config_ID);

	/**
	 * Get Zebra Config.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_Zebra_Config_ID();

	ModelColumn<I_AD_Zebra_Config, Object> COLUMN_AD_Zebra_Config_ID = new ModelColumn<>(I_AD_Zebra_Config.class, "AD_Zebra_Config_ID", null);
	String COLUMNNAME_AD_Zebra_Config_ID = "AD_Zebra_Config_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_AD_Zebra_Config, Object> COLUMN_Created = new ModelColumn<>(I_AD_Zebra_Config.class, "Created", null);
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
	 * Set Encoding.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setEncoding (java.lang.String Encoding);

	/**
	 * Get Encoding.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getEncoding();

	ModelColumn<I_AD_Zebra_Config, Object> COLUMN_Encoding = new ModelColumn<>(I_AD_Zebra_Config.class, "Encoding", null);
	String COLUMNNAME_Encoding = "Encoding";

	/**
	 * Set Header Line 1.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setHeader_Line1 (java.lang.String Header_Line1);

	/**
	 * Get Header Line 1.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getHeader_Line1();

	ModelColumn<I_AD_Zebra_Config, Object> COLUMN_Header_Line1 = new ModelColumn<>(I_AD_Zebra_Config.class, "Header_Line1", null);
	String COLUMNNAME_Header_Line1 = "Header_Line1";

	/**
	 * Set Header Line 2.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setHeader_Line2 (java.lang.String Header_Line2);

	/**
	 * Get Header Line 2.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getHeader_Line2();

	ModelColumn<I_AD_Zebra_Config, Object> COLUMN_Header_Line2 = new ModelColumn<>(I_AD_Zebra_Config.class, "Header_Line2", null);
	String COLUMNNAME_Header_Line2 = "Header_Line2";

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

	ModelColumn<I_AD_Zebra_Config, Object> COLUMN_IsActive = new ModelColumn<>(I_AD_Zebra_Config.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Default.
	 * Default value
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsDefault (boolean IsDefault);

	/**
	 * Get Default.
	 * Default value
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isDefault();

	ModelColumn<I_AD_Zebra_Config, Object> COLUMN_IsDefault = new ModelColumn<>(I_AD_Zebra_Config.class, "IsDefault", null);
	String COLUMNNAME_IsDefault = "IsDefault";

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

	ModelColumn<I_AD_Zebra_Config, Object> COLUMN_Name = new ModelColumn<>(I_AD_Zebra_Config.class, "Name", null);
	String COLUMNNAME_Name = "Name";

	/**
	 * Set SQL Select .
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setSQL_Select (java.lang.String SQL_Select);

	/**
	 * Get SQL Select .
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getSQL_Select();

	ModelColumn<I_AD_Zebra_Config, Object> COLUMN_SQL_Select = new ModelColumn<>(I_AD_Zebra_Config.class, "SQL_Select", null);
	String COLUMNNAME_SQL_Select = "SQL_Select";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_AD_Zebra_Config, Object> COLUMN_Updated = new ModelColumn<>(I_AD_Zebra_Config.class, "Updated", null);
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
