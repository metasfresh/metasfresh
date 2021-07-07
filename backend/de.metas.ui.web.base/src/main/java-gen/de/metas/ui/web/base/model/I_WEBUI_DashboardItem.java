package de.metas.ui.web.base.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;

/** Generated Interface for WEBUI_DashboardItem
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_WEBUI_DashboardItem 
{

	String Table_Name = "WEBUI_DashboardItem";

//	/** AD_Table_ID=540796 */
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
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_WEBUI_DashboardItem, Object> COLUMN_Created = new ModelColumn<>(I_WEBUI_DashboardItem.class, "Created", null);
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

	ModelColumn<I_WEBUI_DashboardItem, Object> COLUMN_ES_TimeRange = new ModelColumn<>(I_WEBUI_DashboardItem.class, "ES_TimeRange", null);
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

	ModelColumn<I_WEBUI_DashboardItem, Object> COLUMN_ES_TimeRange_End = new ModelColumn<>(I_WEBUI_DashboardItem.class, "ES_TimeRange_End", null);
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

	ModelColumn<I_WEBUI_DashboardItem, Object> COLUMN_IsActive = new ModelColumn<>(I_WEBUI_DashboardItem.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

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

	ModelColumn<I_WEBUI_DashboardItem, Object> COLUMN_Name = new ModelColumn<>(I_WEBUI_DashboardItem.class, "Name", null);
	String COLUMNNAME_Name = "Name";

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

	ModelColumn<I_WEBUI_DashboardItem, Object> COLUMN_SeqNo = new ModelColumn<>(I_WEBUI_DashboardItem.class, "SeqNo", null);
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

	ModelColumn<I_WEBUI_DashboardItem, Object> COLUMN_Updated = new ModelColumn<>(I_WEBUI_DashboardItem.class, "Updated", null);
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
	 * Set URL.
	 * Full URL address - e.g. https://www.metasfresh.com
	 *
	 * <br>Type: URL
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setURL (@Nullable java.lang.String URL);

	/**
	 * Get URL.
	 * Full URL address - e.g. https://www.metasfresh.com
	 *
	 * <br>Type: URL
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getURL();

	ModelColumn<I_WEBUI_DashboardItem, Object> COLUMN_URL = new ModelColumn<>(I_WEBUI_DashboardItem.class, "URL", null);
	String COLUMNNAME_URL = "URL";

	/**
	 * Set Dashboard.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setWEBUI_Dashboard_ID (int WEBUI_Dashboard_ID);

	/**
	 * Get Dashboard.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getWEBUI_Dashboard_ID();

	de.metas.ui.web.base.model.I_WEBUI_Dashboard getWEBUI_Dashboard();

	void setWEBUI_Dashboard(de.metas.ui.web.base.model.I_WEBUI_Dashboard WEBUI_Dashboard);

	ModelColumn<I_WEBUI_DashboardItem, de.metas.ui.web.base.model.I_WEBUI_Dashboard> COLUMN_WEBUI_Dashboard_ID = new ModelColumn<>(I_WEBUI_DashboardItem.class, "WEBUI_Dashboard_ID", de.metas.ui.web.base.model.I_WEBUI_Dashboard.class);
	String COLUMNNAME_WEBUI_Dashboard_ID = "WEBUI_Dashboard_ID";

	/**
	 * Set Dashboard item.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setWEBUI_DashboardItem_ID (int WEBUI_DashboardItem_ID);

	/**
	 * Get Dashboard item.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getWEBUI_DashboardItem_ID();

	ModelColumn<I_WEBUI_DashboardItem, Object> COLUMN_WEBUI_DashboardItem_ID = new ModelColumn<>(I_WEBUI_DashboardItem.class, "WEBUI_DashboardItem_ID", null);
	String COLUMNNAME_WEBUI_DashboardItem_ID = "WEBUI_DashboardItem_ID";

	/**
	 * Set Widget type.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setWEBUI_DashboardWidgetType (java.lang.String WEBUI_DashboardWidgetType);

	/**
	 * Get Widget type.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getWEBUI_DashboardWidgetType();

	ModelColumn<I_WEBUI_DashboardItem, Object> COLUMN_WEBUI_DashboardWidgetType = new ModelColumn<>(I_WEBUI_DashboardItem.class, "WEBUI_DashboardWidgetType", null);
	String COLUMNNAME_WEBUI_DashboardWidgetType = "WEBUI_DashboardWidgetType";

	/**
	 * Set KPI.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setWEBUI_KPI_ID (int WEBUI_KPI_ID);

	/**
	 * Get KPI.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getWEBUI_KPI_ID();

	@Nullable de.metas.ui.web.base.model.I_WEBUI_KPI getWEBUI_KPI();

	void setWEBUI_KPI(@Nullable de.metas.ui.web.base.model.I_WEBUI_KPI WEBUI_KPI);

	ModelColumn<I_WEBUI_DashboardItem, de.metas.ui.web.base.model.I_WEBUI_KPI> COLUMN_WEBUI_KPI_ID = new ModelColumn<>(I_WEBUI_DashboardItem.class, "WEBUI_KPI_ID", de.metas.ui.web.base.model.I_WEBUI_KPI.class);
	String COLUMNNAME_WEBUI_KPI_ID = "WEBUI_KPI_ID";
}
