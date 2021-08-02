package de.metas.elasticsearch.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;

/** Generated Interface for ES_FTS_IndexInclude
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_ES_FTS_IndexInclude 
{

	String Table_Name = "ES_FTS_IndexInclude";

//	/** AD_Table_ID=540991 */
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
	 * Set Attribute Name.
	 * Name of the Attribute
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAttributeName (java.lang.String AttributeName);

	/**
	 * Get Attribute Name.
	 * Name of the Attribute
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getAttributeName();

	ModelColumn<I_ES_FTS_IndexInclude, Object> COLUMN_AttributeName = new ModelColumn<>(I_ES_FTS_IndexInclude.class, "AttributeName", null);
	String COLUMNNAME_AttributeName = "AttributeName";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_ES_FTS_IndexInclude, Object> COLUMN_Created = new ModelColumn<>(I_ES_FTS_IndexInclude.class, "Created", null);
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

	ModelColumn<I_ES_FTS_IndexInclude, Object> COLUMN_Description = new ModelColumn<>(I_ES_FTS_IndexInclude.class, "Description", null);
	String COLUMNNAME_Description = "Description";

	/**
	 * Set FTS Index.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setES_FTS_Index_ID (int ES_FTS_Index_ID);

	/**
	 * Get FTS Index.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getES_FTS_Index_ID();

	de.metas.elasticsearch.model.I_ES_FTS_Index getES_FTS_Index();

	void setES_FTS_Index(de.metas.elasticsearch.model.I_ES_FTS_Index ES_FTS_Index);

	ModelColumn<I_ES_FTS_IndexInclude, de.metas.elasticsearch.model.I_ES_FTS_Index> COLUMN_ES_FTS_Index_ID = new ModelColumn<>(I_ES_FTS_IndexInclude.class, "ES_FTS_Index_ID", de.metas.elasticsearch.model.I_ES_FTS_Index.class);
	String COLUMNNAME_ES_FTS_Index_ID = "ES_FTS_Index_ID";

	/**
	 * Set FTS Index Include.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setES_FTS_IndexInclude_ID (int ES_FTS_IndexInclude_ID);

	/**
	 * Get FTS Index Include.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getES_FTS_IndexInclude_ID();

	ModelColumn<I_ES_FTS_IndexInclude, Object> COLUMN_ES_FTS_IndexInclude_ID = new ModelColumn<>(I_ES_FTS_IndexInclude.class, "ES_FTS_IndexInclude_ID", null);
	String COLUMNNAME_ES_FTS_IndexInclude_ID = "ES_FTS_IndexInclude_ID";

	/**
	 * Set Link column.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setInclude_LinkColumn_ID (int Include_LinkColumn_ID);

	/**
	 * Get Link column.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getInclude_LinkColumn_ID();

	org.compiere.model.I_AD_Column getInclude_LinkColumn();

	void setInclude_LinkColumn(org.compiere.model.I_AD_Column Include_LinkColumn);

	ModelColumn<I_ES_FTS_IndexInclude, org.compiere.model.I_AD_Column> COLUMN_Include_LinkColumn_ID = new ModelColumn<>(I_ES_FTS_IndexInclude.class, "Include_LinkColumn_ID", org.compiere.model.I_AD_Column.class);
	String COLUMNNAME_Include_LinkColumn_ID = "Include_LinkColumn_ID";

	/**
	 * Set Spalte.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setInclude_Table_ID (int Include_Table_ID);

	/**
	 * Get Spalte.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getInclude_Table_ID();

	String COLUMNNAME_Include_Table_ID = "Include_Table_ID";

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

	ModelColumn<I_ES_FTS_IndexInclude, Object> COLUMN_IsActive = new ModelColumn<>(I_ES_FTS_IndexInclude.class, "IsActive", null);
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

	ModelColumn<I_ES_FTS_IndexInclude, Object> COLUMN_Updated = new ModelColumn<>(I_ES_FTS_IndexInclude.class, "Updated", null);
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
