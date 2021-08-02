package de.metas.elasticsearch.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;

/** Generated Interface for ES_FTS_Index
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_ES_FTS_Index 
{

	String Table_Name = "ES_FTS_Index";

//	/** AD_Table_ID=540990 */
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
	 * Set Table.
	 * Database Table information
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_Table_ID (int AD_Table_ID);

	/**
	 * Get Table.
	 * Database Table information
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_Table_ID();

	String COLUMNNAME_AD_Table_ID = "AD_Table_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_ES_FTS_Index, Object> COLUMN_Created = new ModelColumn<>(I_ES_FTS_Index.class, "Created", null);
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
	 * Set FTS Index.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setES_FTS_Index_ID (int ES_FTS_Index_ID);

	/**
	 * Get FTS Index.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getES_FTS_Index_ID();

	ModelColumn<I_ES_FTS_Index, Object> COLUMN_ES_FTS_Index_ID = new ModelColumn<>(I_ES_FTS_Index.class, "ES_FTS_Index_ID", null);
	String COLUMNNAME_ES_FTS_Index_ID = "ES_FTS_Index_ID";

	/**
	 * Set Full Text Search Template.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setES_FTS_Template_ID (int ES_FTS_Template_ID);

	/**
	 * Get Full Text Search Template.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getES_FTS_Template_ID();

	@Nullable de.metas.elasticsearch.model.I_ES_FTS_Template getES_FTS_Template();

	void setES_FTS_Template(@Nullable de.metas.elasticsearch.model.I_ES_FTS_Template ES_FTS_Template);

	ModelColumn<I_ES_FTS_Index, de.metas.elasticsearch.model.I_ES_FTS_Template> COLUMN_ES_FTS_Template_ID = new ModelColumn<>(I_ES_FTS_Index.class, "ES_FTS_Template_ID", de.metas.elasticsearch.model.I_ES_FTS_Template.class);
	String COLUMNNAME_ES_FTS_Template_ID = "ES_FTS_Template_ID";

	/**
	 * Set Elasticsearch Index.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setES_Index (java.lang.String ES_Index);

	/**
	 * Get Elasticsearch Index.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getES_Index();

	ModelColumn<I_ES_FTS_Index, Object> COLUMN_ES_Index = new ModelColumn<>(I_ES_FTS_Index.class, "ES_Index", null);
	String COLUMNNAME_ES_Index = "ES_Index";

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

	ModelColumn<I_ES_FTS_Index, Object> COLUMN_IsActive = new ModelColumn<>(I_ES_FTS_Index.class, "IsActive", null);
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

	ModelColumn<I_ES_FTS_Index, Object> COLUMN_Updated = new ModelColumn<>(I_ES_FTS_Index.class, "Updated", null);
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
