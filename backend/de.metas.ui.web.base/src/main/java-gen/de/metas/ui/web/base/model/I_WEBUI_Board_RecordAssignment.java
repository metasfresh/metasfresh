package de.metas.ui.web.base.model;

import org.adempiere.model.ModelColumn;

/** Generated Interface for WEBUI_Board_RecordAssignment
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_WEBUI_Board_RecordAssignment 
{

	String Table_Name = "WEBUI_Board_RecordAssignment";

//	/** AD_Table_ID=540826 */
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

	ModelColumn<I_WEBUI_Board_RecordAssignment, Object> COLUMN_Created = new ModelColumn<>(I_WEBUI_Board_RecordAssignment.class, "Created", null);
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

	ModelColumn<I_WEBUI_Board_RecordAssignment, Object> COLUMN_IsActive = new ModelColumn<>(I_WEBUI_Board_RecordAssignment.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Record ID.
	 * Direct internal record ID
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setRecord_ID (int Record_ID);

	/**
	 * Get Record ID.
	 * Direct internal record ID
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getRecord_ID();

	ModelColumn<I_WEBUI_Board_RecordAssignment, Object> COLUMN_Record_ID = new ModelColumn<>(I_WEBUI_Board_RecordAssignment.class, "Record_ID", null);
	String COLUMNNAME_Record_ID = "Record_ID";

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

	ModelColumn<I_WEBUI_Board_RecordAssignment, Object> COLUMN_SeqNo = new ModelColumn<>(I_WEBUI_Board_RecordAssignment.class, "SeqNo", null);
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

	ModelColumn<I_WEBUI_Board_RecordAssignment, Object> COLUMN_Updated = new ModelColumn<>(I_WEBUI_Board_RecordAssignment.class, "Updated", null);
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

	ModelColumn<I_WEBUI_Board_RecordAssignment, de.metas.ui.web.base.model.I_WEBUI_Board> COLUMN_WEBUI_Board_ID = new ModelColumn<>(I_WEBUI_Board_RecordAssignment.class, "WEBUI_Board_ID", de.metas.ui.web.base.model.I_WEBUI_Board.class);
	String COLUMNNAME_WEBUI_Board_ID = "WEBUI_Board_ID";

	/**
	 * Set Board lane.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setWEBUI_Board_Lane_ID (int WEBUI_Board_Lane_ID);

	/**
	 * Get Board lane.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getWEBUI_Board_Lane_ID();

	de.metas.ui.web.base.model.I_WEBUI_Board_Lane getWEBUI_Board_Lane();

	void setWEBUI_Board_Lane(de.metas.ui.web.base.model.I_WEBUI_Board_Lane WEBUI_Board_Lane);

	ModelColumn<I_WEBUI_Board_RecordAssignment, de.metas.ui.web.base.model.I_WEBUI_Board_Lane> COLUMN_WEBUI_Board_Lane_ID = new ModelColumn<>(I_WEBUI_Board_RecordAssignment.class, "WEBUI_Board_Lane_ID", de.metas.ui.web.base.model.I_WEBUI_Board_Lane.class);
	String COLUMNNAME_WEBUI_Board_Lane_ID = "WEBUI_Board_Lane_ID";

	/**
	 * Set Board record assignment.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setWEBUI_Board_RecordAssignment_ID (int WEBUI_Board_RecordAssignment_ID);

	/**
	 * Get Board record assignment.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getWEBUI_Board_RecordAssignment_ID();

	ModelColumn<I_WEBUI_Board_RecordAssignment, Object> COLUMN_WEBUI_Board_RecordAssignment_ID = new ModelColumn<>(I_WEBUI_Board_RecordAssignment.class, "WEBUI_Board_RecordAssignment_ID", null);
	String COLUMNNAME_WEBUI_Board_RecordAssignment_ID = "WEBUI_Board_RecordAssignment_ID";
}
