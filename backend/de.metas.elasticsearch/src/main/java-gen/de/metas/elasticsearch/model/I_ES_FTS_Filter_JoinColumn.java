package de.metas.elasticsearch.model;

import org.adempiere.model.ModelColumn;

/** Generated Interface for ES_FTS_Filter_JoinColumn
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_ES_FTS_Filter_JoinColumn 
{

	String Table_Name = "ES_FTS_Filter_JoinColumn";

//	/** AD_Table_ID=541760 */
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
	 * Set Link Column.
	 * Link Column for Multi-Parent tables
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_Column_ID (int AD_Column_ID);

	/**
	 * Get Link Column.
	 * Link Column for Multi-Parent tables
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_Column_ID();

	org.compiere.model.I_AD_Column getAD_Column();

	void setAD_Column(org.compiere.model.I_AD_Column AD_Column);

	ModelColumn<I_ES_FTS_Filter_JoinColumn, org.compiere.model.I_AD_Column> COLUMN_AD_Column_ID = new ModelColumn<>(I_ES_FTS_Filter_JoinColumn.class, "AD_Column_ID", org.compiere.model.I_AD_Column.class);
	String COLUMNNAME_AD_Column_ID = "AD_Column_ID";

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
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_ES_FTS_Filter_JoinColumn, Object> COLUMN_Created = new ModelColumn<>(I_ES_FTS_Filter_JoinColumn.class, "Created", null);
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
	 * Set Elasticsearch Field.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setES_FTS_Config_Field_ID (int ES_FTS_Config_Field_ID);

	/**
	 * Get Elasticsearch Field.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getES_FTS_Config_Field_ID();

	de.metas.elasticsearch.model.I_ES_FTS_Config_Field getES_FTS_Config_Field();

	void setES_FTS_Config_Field(de.metas.elasticsearch.model.I_ES_FTS_Config_Field ES_FTS_Config_Field);

	ModelColumn<I_ES_FTS_Filter_JoinColumn, de.metas.elasticsearch.model.I_ES_FTS_Config_Field> COLUMN_ES_FTS_Config_Field_ID = new ModelColumn<>(I_ES_FTS_Filter_JoinColumn.class, "ES_FTS_Config_Field_ID", de.metas.elasticsearch.model.I_ES_FTS_Config_Field.class);
	String COLUMNNAME_ES_FTS_Config_Field_ID = "ES_FTS_Config_Field_ID";

	/**
	 * Set Full Text Search Filter.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setES_FTS_Filter_ID (int ES_FTS_Filter_ID);

	/**
	 * Get Full Text Search Filter.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getES_FTS_Filter_ID();

	de.metas.elasticsearch.model.I_ES_FTS_Filter getES_FTS_Filter();

	void setES_FTS_Filter(de.metas.elasticsearch.model.I_ES_FTS_Filter ES_FTS_Filter);

	ModelColumn<I_ES_FTS_Filter_JoinColumn, de.metas.elasticsearch.model.I_ES_FTS_Filter> COLUMN_ES_FTS_Filter_ID = new ModelColumn<>(I_ES_FTS_Filter_JoinColumn.class, "ES_FTS_Filter_ID", de.metas.elasticsearch.model.I_ES_FTS_Filter.class);
	String COLUMNNAME_ES_FTS_Filter_ID = "ES_FTS_Filter_ID";

	/**
	 * Set Full Text Search Filter Join Column.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setES_FTS_Filter_JoinColumn_ID (int ES_FTS_Filter_JoinColumn_ID);

	/**
	 * Get Full Text Search Filter Join Column.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getES_FTS_Filter_JoinColumn_ID();

	ModelColumn<I_ES_FTS_Filter_JoinColumn, Object> COLUMN_ES_FTS_Filter_JoinColumn_ID = new ModelColumn<>(I_ES_FTS_Filter_JoinColumn.class, "ES_FTS_Filter_JoinColumn_ID", null);
	String COLUMNNAME_ES_FTS_Filter_JoinColumn_ID = "ES_FTS_Filter_JoinColumn_ID";

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

	ModelColumn<I_ES_FTS_Filter_JoinColumn, Object> COLUMN_IsActive = new ModelColumn<>(I_ES_FTS_Filter_JoinColumn.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Nullable.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsNullable (boolean IsNullable);

	/**
	 * Get Nullable.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isNullable();

	ModelColumn<I_ES_FTS_Filter_JoinColumn, Object> COLUMN_IsNullable = new ModelColumn<>(I_ES_FTS_Filter_JoinColumn.class, "IsNullable", null);
	String COLUMNNAME_IsNullable = "IsNullable";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_ES_FTS_Filter_JoinColumn, Object> COLUMN_Updated = new ModelColumn<>(I_ES_FTS_Filter_JoinColumn.class, "Updated", null);
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
