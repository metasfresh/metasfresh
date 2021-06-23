package de.metas.ui.web.base.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;

/** Generated Interface for WEBUI_KPI
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_WEBUI_KPI 
{

	String Table_Name = "WEBUI_KPI";

//	/** AD_Table_ID=540801 */
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
	 * Set Chart Type.
	 * Type fo chart to render
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setChartType (java.lang.String ChartType);

	/**
	 * Get Chart Type.
	 * Type fo chart to render
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getChartType();

	ModelColumn<I_WEBUI_KPI, Object> COLUMN_ChartType = new ModelColumn<>(I_WEBUI_KPI.class, "ChartType", null);
	String COLUMNNAME_ChartType = "ChartType";

	/**
	 * Set Offset.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCompareOffset (@Nullable java.lang.String CompareOffset);

	/**
	 * Get Offset.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getCompareOffset();

	ModelColumn<I_WEBUI_KPI, Object> COLUMN_CompareOffset = new ModelColumn<>(I_WEBUI_KPI.class, "CompareOffset", null);
	String COLUMNNAME_CompareOffset = "CompareOffset";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_WEBUI_KPI, Object> COLUMN_Created = new ModelColumn<>(I_WEBUI_KPI.class, "Created", null);
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
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDescription (@Nullable java.lang.String Description);

	/**
	 * Get Description.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDescription();

	ModelColumn<I_WEBUI_KPI, Object> COLUMN_Description = new ModelColumn<>(I_WEBUI_KPI.class, "Description", null);
	String COLUMNNAME_Description = "Description";

	/**
	 * Set Elasticsearch Index.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setES_Index (@Nullable java.lang.String ES_Index);

	/**
	 * Get Elasticsearch Index.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getES_Index();

	ModelColumn<I_WEBUI_KPI, Object> COLUMN_ES_Index = new ModelColumn<>(I_WEBUI_KPI.class, "ES_Index", null);
	String COLUMNNAME_ES_Index = "ES_Index";

	/**
	 * Set Elasticsearch query.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setES_Query (@Nullable java.lang.String ES_Query);

	/**
	 * Get Elasticsearch query.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getES_Query();

	ModelColumn<I_WEBUI_KPI, Object> COLUMN_ES_Query = new ModelColumn<>(I_WEBUI_KPI.class, "ES_Query", null);
	String COLUMNNAME_ES_Query = "ES_Query";

	/**
	 * Set Time range.
	 * Time range using format 'PnDTnHnMn.nS'
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setES_TimeRange (@Nullable java.lang.String ES_TimeRange);

	/**
	 * Get Time range.
	 * Time range using format 'PnDTnHnMn.nS'
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getES_TimeRange();

	ModelColumn<I_WEBUI_KPI, Object> COLUMN_ES_TimeRange = new ModelColumn<>(I_WEBUI_KPI.class, "ES_TimeRange", null);
	String COLUMNNAME_ES_TimeRange = "ES_TimeRange";

	/**
	 * Set Time range end.
	 * Time range's ending offset (relative to now)
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setES_TimeRange_End (@Nullable java.lang.String ES_TimeRange_End);

	/**
	 * Get Time range end.
	 * Time range's ending offset (relative to now)
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getES_TimeRange_End();

	ModelColumn<I_WEBUI_KPI, Object> COLUMN_ES_TimeRange_End = new ModelColumn<>(I_WEBUI_KPI.class, "ES_TimeRange_End", null);
	String COLUMNNAME_ES_TimeRange_End = "ES_TimeRange_End";

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

	ModelColumn<I_WEBUI_KPI, Object> COLUMN_IsActive = new ModelColumn<>(I_WEBUI_KPI.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Compare.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsGenerateComparation (boolean IsGenerateComparation);

	/**
	 * Get Compare.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isGenerateComparation();

	ModelColumn<I_WEBUI_KPI, Object> COLUMN_IsGenerateComparation = new ModelColumn<>(I_WEBUI_KPI.class, "IsGenerateComparation", null);
	String COLUMNNAME_IsGenerateComparation = "IsGenerateComparation";

	/**
	 * Set Datasource.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setKPI_DataSource_Type (java.lang.String KPI_DataSource_Type);

	/**
	 * Get Datasource.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getKPI_DataSource_Type();

	ModelColumn<I_WEBUI_KPI, Object> COLUMN_KPI_DataSource_Type = new ModelColumn<>(I_WEBUI_KPI.class, "KPI_DataSource_Type", null);
	String COLUMNNAME_KPI_DataSource_Type = "KPI_DataSource_Type";

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

	ModelColumn<I_WEBUI_KPI, Object> COLUMN_Name = new ModelColumn<>(I_WEBUI_KPI.class, "Name", null);
	String COLUMNNAME_Name = "Name";

	/**
	 * Set SQL From Clause.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setSQL_From (@Nullable java.lang.String SQL_From);

	/**
	 * Get SQL From Clause.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getSQL_From();

	ModelColumn<I_WEBUI_KPI, Object> COLUMN_SQL_From = new ModelColumn<>(I_WEBUI_KPI.class, "SQL_From", null);
	String COLUMNNAME_SQL_From = "SQL_From";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_WEBUI_KPI, Object> COLUMN_Updated = new ModelColumn<>(I_WEBUI_KPI.class, "Updated", null);
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
	 * Set KPI.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setWEBUI_KPI_ID (int WEBUI_KPI_ID);

	/**
	 * Get KPI.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getWEBUI_KPI_ID();

	ModelColumn<I_WEBUI_KPI, Object> COLUMN_WEBUI_KPI_ID = new ModelColumn<>(I_WEBUI_KPI.class, "WEBUI_KPI_ID", null);
	String COLUMNNAME_WEBUI_KPI_ID = "WEBUI_KPI_ID";
}
