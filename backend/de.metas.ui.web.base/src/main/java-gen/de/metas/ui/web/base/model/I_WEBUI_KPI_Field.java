package de.metas.ui.web.base.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;

/** Generated Interface for WEBUI_KPI_Field
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_WEBUI_KPI_Field 
{

	String Table_Name = "WEBUI_KPI_Field";

//	/** AD_Table_ID=540802 */
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
	 * Set System-Element.
	 * Das "System-Element" ermöglicht die zentrale  Verwaltung von Spaltenbeschreibungen und Hilfetexten.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_Element_ID (int AD_Element_ID);

	/**
	 * Get System-Element.
	 * Das "System-Element" ermöglicht die zentrale  Verwaltung von Spaltenbeschreibungen und Hilfetexten.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_Element_ID();

	org.compiere.model.I_AD_Element getAD_Element();

	void setAD_Element(org.compiere.model.I_AD_Element AD_Element);

	ModelColumn<I_WEBUI_KPI_Field, org.compiere.model.I_AD_Element> COLUMN_AD_Element_ID = new ModelColumn<>(I_WEBUI_KPI_Field.class, "AD_Element_ID", org.compiere.model.I_AD_Element.class);
	String COLUMNNAME_AD_Element_ID = "AD_Element_ID";

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
	 * Set Referenz.
	 * Systemreferenz und Validierung
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_Reference_ID (int AD_Reference_ID);

	/**
	 * Get Referenz.
	 * Systemreferenz und Validierung
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_Reference_ID();

	org.compiere.model.I_AD_Reference getAD_Reference();

	void setAD_Reference(org.compiere.model.I_AD_Reference AD_Reference);

	ModelColumn<I_WEBUI_KPI_Field, org.compiere.model.I_AD_Reference> COLUMN_AD_Reference_ID = new ModelColumn<>(I_WEBUI_KPI_Field.class, "AD_Reference_ID", org.compiere.model.I_AD_Reference.class);
	String COLUMNNAME_AD_Reference_ID = "AD_Reference_ID";

	/**
	 * Set Color.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setColor (@Nullable java.lang.String Color);

	/**
	 * Get Color.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getColor();

	ModelColumn<I_WEBUI_KPI_Field, Object> COLUMN_Color = new ModelColumn<>(I_WEBUI_KPI_Field.class, "Color", null);
	String COLUMNNAME_Color = "Color";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_WEBUI_KPI_Field, Object> COLUMN_Created = new ModelColumn<>(I_WEBUI_KPI_Field.class, "Created", null);
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
	 * Set Elasticsearch field path.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setES_FieldPath (@Nullable java.lang.String ES_FieldPath);

	/**
	 * Get Elasticsearch field path.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getES_FieldPath();

	ModelColumn<I_WEBUI_KPI_Field, Object> COLUMN_ES_FieldPath = new ModelColumn<>(I_WEBUI_KPI_Field.class, "ES_FieldPath", null);
	String COLUMNNAME_ES_FieldPath = "ES_FieldPath";

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

	ModelColumn<I_WEBUI_KPI_Field, Object> COLUMN_IsActive = new ModelColumn<>(I_WEBUI_KPI_Field.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Gruppieren.
	 * After a group change, totals, etc. are printed
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsGroupBy (boolean IsGroupBy);

	/**
	 * Get Gruppieren.
	 * After a group change, totals, etc. are printed
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isGroupBy();

	ModelColumn<I_WEBUI_KPI_Field, Object> COLUMN_IsGroupBy = new ModelColumn<>(I_WEBUI_KPI_Field.class, "IsGroupBy", null);
	String COLUMNNAME_IsGroupBy = "IsGroupBy";

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

	ModelColumn<I_WEBUI_KPI_Field, Object> COLUMN_Name = new ModelColumn<>(I_WEBUI_KPI_Field.class, "Name", null);
	String COLUMNNAME_Name = "Name";

	/**
	 * Set Offset name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setOffsetName (@Nullable java.lang.String OffsetName);

	/**
	 * Get Offset name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getOffsetName();

	ModelColumn<I_WEBUI_KPI_Field, Object> COLUMN_OffsetName = new ModelColumn<>(I_WEBUI_KPI_Field.class, "OffsetName", null);
	String COLUMNNAME_OffsetName = "OffsetName";

	/**
	 * Set SQL Select .
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setSQL_Select (@Nullable java.lang.String SQL_Select);

	/**
	 * Get SQL Select .
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getSQL_Select();

	ModelColumn<I_WEBUI_KPI_Field, Object> COLUMN_SQL_Select = new ModelColumn<>(I_WEBUI_KPI_Field.class, "SQL_Select", null);
	String COLUMNNAME_SQL_Select = "SQL_Select";

	/**
	 * Set Symbol.
	 * Symbol für die Maßeinheit
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setUOMSymbol (@Nullable java.lang.String UOMSymbol);

	/**
	 * Get Symbol.
	 * Symbol für die Maßeinheit
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getUOMSymbol();

	ModelColumn<I_WEBUI_KPI_Field, Object> COLUMN_UOMSymbol = new ModelColumn<>(I_WEBUI_KPI_Field.class, "UOMSymbol", null);
	String COLUMNNAME_UOMSymbol = "UOMSymbol";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_WEBUI_KPI_Field, Object> COLUMN_Updated = new ModelColumn<>(I_WEBUI_KPI_Field.class, "Updated", null);
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
	 * Set WEBUI_KPI_Field.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setWEBUI_KPI_Field_ID (int WEBUI_KPI_Field_ID);

	/**
	 * Get WEBUI_KPI_Field.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getWEBUI_KPI_Field_ID();

	ModelColumn<I_WEBUI_KPI_Field, Object> COLUMN_WEBUI_KPI_Field_ID = new ModelColumn<>(I_WEBUI_KPI_Field.class, "WEBUI_KPI_Field_ID", null);
	String COLUMNNAME_WEBUI_KPI_Field_ID = "WEBUI_KPI_Field_ID";

	/**
	 * Set KPI.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setWEBUI_KPI_ID (int WEBUI_KPI_ID);

	/**
	 * Get KPI.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getWEBUI_KPI_ID();

	de.metas.ui.web.base.model.I_WEBUI_KPI getWEBUI_KPI();

	void setWEBUI_KPI(de.metas.ui.web.base.model.I_WEBUI_KPI WEBUI_KPI);

	ModelColumn<I_WEBUI_KPI_Field, de.metas.ui.web.base.model.I_WEBUI_KPI> COLUMN_WEBUI_KPI_ID = new ModelColumn<>(I_WEBUI_KPI_Field.class, "WEBUI_KPI_ID", de.metas.ui.web.base.model.I_WEBUI_KPI.class);
	String COLUMNNAME_WEBUI_KPI_ID = "WEBUI_KPI_ID";
}
