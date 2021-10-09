package de.metas.ui.web.base.model;

import org.adempiere.model.ModelColumn;

/** Generated Interface for WEBUI_Board_CardField
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_WEBUI_Board_CardField 
{

	String Table_Name = "WEBUI_Board_CardField";

//	/** AD_Table_ID=540827 */
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

	ModelColumn<I_WEBUI_Board_CardField, org.compiere.model.I_AD_Column> COLUMN_AD_Column_ID = new ModelColumn<>(I_WEBUI_Board_CardField.class, "AD_Column_ID", org.compiere.model.I_AD_Column.class);
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

	ModelColumn<I_WEBUI_Board_CardField, Object> COLUMN_Created = new ModelColumn<>(I_WEBUI_Board_CardField.class, "Created", null);
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

	ModelColumn<I_WEBUI_Board_CardField, Object> COLUMN_IsActive = new ModelColumn<>(I_WEBUI_Board_CardField.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

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

	ModelColumn<I_WEBUI_Board_CardField, Object> COLUMN_SeqNo = new ModelColumn<>(I_WEBUI_Board_CardField.class, "SeqNo", null);
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

	ModelColumn<I_WEBUI_Board_CardField, Object> COLUMN_Updated = new ModelColumn<>(I_WEBUI_Board_CardField.class, "Updated", null);
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
	 * Set Board card field.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setWEBUI_Board_CardField_ID (int WEBUI_Board_CardField_ID);

	/**
	 * Get Board card field.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getWEBUI_Board_CardField_ID();

	ModelColumn<I_WEBUI_Board_CardField, Object> COLUMN_WEBUI_Board_CardField_ID = new ModelColumn<>(I_WEBUI_Board_CardField.class, "WEBUI_Board_CardField_ID", null);
	String COLUMNNAME_WEBUI_Board_CardField_ID = "WEBUI_Board_CardField_ID";

	/**
	 * Set Board.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setWEBUI_Board_ID (int WEBUI_Board_ID);

	/**
	 * Get Board.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getWEBUI_Board_ID();

	de.metas.ui.web.base.model.I_WEBUI_Board getWEBUI_Board();

	void setWEBUI_Board(de.metas.ui.web.base.model.I_WEBUI_Board WEBUI_Board);

	ModelColumn<I_WEBUI_Board_CardField, de.metas.ui.web.base.model.I_WEBUI_Board> COLUMN_WEBUI_Board_ID = new ModelColumn<>(I_WEBUI_Board_CardField.class, "WEBUI_Board_ID", de.metas.ui.web.base.model.I_WEBUI_Board.class);
	String COLUMNNAME_WEBUI_Board_ID = "WEBUI_Board_ID";
}
