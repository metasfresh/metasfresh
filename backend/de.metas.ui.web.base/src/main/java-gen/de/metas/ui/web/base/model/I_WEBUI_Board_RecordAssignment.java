package de.metas.ui.web.base.model;


/** Generated Interface for WEBUI_Board_RecordAssignment
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_WEBUI_Board_RecordAssignment 
{

    /** TableName=WEBUI_Board_RecordAssignment */
    public static final String Table_Name = "WEBUI_Board_RecordAssignment";

    /** AD_Table_ID=540826 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 7 - System - Client - Org
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(7);

    /** Load Meta Data */

	/**
	 * Get Mandant.
	 * Mandant für diese Installation.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Client_ID();

	public org.compiere.model.I_AD_Client getAD_Client();

    /** Column definition for AD_Client_ID */
    public static final org.adempiere.model.ModelColumn<I_WEBUI_Board_RecordAssignment, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_WEBUI_Board_RecordAssignment, org.compiere.model.I_AD_Client>(I_WEBUI_Board_RecordAssignment.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Sektion.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Sektion.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Org_ID();

	public org.compiere.model.I_AD_Org getAD_Org();

	public void setAD_Org(org.compiere.model.I_AD_Org AD_Org);

    /** Column definition for AD_Org_ID */
    public static final org.adempiere.model.ModelColumn<I_WEBUI_Board_RecordAssignment, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_WEBUI_Board_RecordAssignment, org.compiere.model.I_AD_Org>(I_WEBUI_Board_RecordAssignment.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Get Erstellt.
	 * Datum, an dem dieser Eintrag erstellt wurde
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getCreated();

    /** Column definition for Created */
    public static final org.adempiere.model.ModelColumn<I_WEBUI_Board_RecordAssignment, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_WEBUI_Board_RecordAssignment, Object>(I_WEBUI_Board_RecordAssignment.class, "Created", null);
    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/**
	 * Get Erstellt durch.
	 * Nutzer, der diesen Eintrag erstellt hat
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getCreatedBy();

    /** Column definition for CreatedBy */
    public static final org.adempiere.model.ModelColumn<I_WEBUI_Board_RecordAssignment, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_WEBUI_Board_RecordAssignment, org.compiere.model.I_AD_User>(I_WEBUI_Board_RecordAssignment.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Aktiv.
	 * Der Eintrag ist im System aktiv
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsActive (boolean IsActive);

	/**
	 * Get Aktiv.
	 * Der Eintrag ist im System aktiv
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isActive();

    /** Column definition for IsActive */
    public static final org.adempiere.model.ModelColumn<I_WEBUI_Board_RecordAssignment, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_WEBUI_Board_RecordAssignment, Object>(I_WEBUI_Board_RecordAssignment.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Datensatz-ID.
	 * Direct internal record ID
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setRecord_ID (int Record_ID);

	/**
	 * Get Datensatz-ID.
	 * Direct internal record ID
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getRecord_ID();

    /** Column definition for Record_ID */
    public static final org.adempiere.model.ModelColumn<I_WEBUI_Board_RecordAssignment, Object> COLUMN_Record_ID = new org.adempiere.model.ModelColumn<I_WEBUI_Board_RecordAssignment, Object>(I_WEBUI_Board_RecordAssignment.class, "Record_ID", null);
    /** Column name Record_ID */
    public static final String COLUMNNAME_Record_ID = "Record_ID";

	/**
	 * Set Reihenfolge.
	 * Zur Bestimmung der Reihenfolge der Einträge;
 die kleinste Zahl kommt zuerst
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setSeqNo (int SeqNo);

	/**
	 * Get Reihenfolge.
	 * Zur Bestimmung der Reihenfolge der Einträge;
 die kleinste Zahl kommt zuerst
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getSeqNo();

    /** Column definition for SeqNo */
    public static final org.adempiere.model.ModelColumn<I_WEBUI_Board_RecordAssignment, Object> COLUMN_SeqNo = new org.adempiere.model.ModelColumn<I_WEBUI_Board_RecordAssignment, Object>(I_WEBUI_Board_RecordAssignment.class, "SeqNo", null);
    /** Column name SeqNo */
    public static final String COLUMNNAME_SeqNo = "SeqNo";

	/**
	 * Get Aktualisiert.
	 * Datum, an dem dieser Eintrag aktualisiert wurde
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getUpdated();

    /** Column definition for Updated */
    public static final org.adempiere.model.ModelColumn<I_WEBUI_Board_RecordAssignment, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_WEBUI_Board_RecordAssignment, Object>(I_WEBUI_Board_RecordAssignment.class, "Updated", null);
    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Aktualisiert durch.
	 * Nutzer, der diesen Eintrag aktualisiert hat
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getUpdatedBy();

    /** Column definition for UpdatedBy */
    public static final org.adempiere.model.ModelColumn<I_WEBUI_Board_RecordAssignment, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_WEBUI_Board_RecordAssignment, org.compiere.model.I_AD_User>(I_WEBUI_Board_RecordAssignment.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/**
	 * Set Board.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setWEBUI_Board_ID (int WEBUI_Board_ID);

	/**
	 * Get Board.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getWEBUI_Board_ID();

	public de.metas.ui.web.base.model.I_WEBUI_Board getWEBUI_Board();

	public void setWEBUI_Board(de.metas.ui.web.base.model.I_WEBUI_Board WEBUI_Board);

    /** Column definition for WEBUI_Board_ID */
    public static final org.adempiere.model.ModelColumn<I_WEBUI_Board_RecordAssignment, de.metas.ui.web.base.model.I_WEBUI_Board> COLUMN_WEBUI_Board_ID = new org.adempiere.model.ModelColumn<I_WEBUI_Board_RecordAssignment, de.metas.ui.web.base.model.I_WEBUI_Board>(I_WEBUI_Board_RecordAssignment.class, "WEBUI_Board_ID", de.metas.ui.web.base.model.I_WEBUI_Board.class);
    /** Column name WEBUI_Board_ID */
    public static final String COLUMNNAME_WEBUI_Board_ID = "WEBUI_Board_ID";

	/**
	 * Set Board lane.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setWEBUI_Board_Lane_ID (int WEBUI_Board_Lane_ID);

	/**
	 * Get Board lane.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getWEBUI_Board_Lane_ID();

	public de.metas.ui.web.base.model.I_WEBUI_Board_Lane getWEBUI_Board_Lane();

	public void setWEBUI_Board_Lane(de.metas.ui.web.base.model.I_WEBUI_Board_Lane WEBUI_Board_Lane);

    /** Column definition for WEBUI_Board_Lane_ID */
    public static final org.adempiere.model.ModelColumn<I_WEBUI_Board_RecordAssignment, de.metas.ui.web.base.model.I_WEBUI_Board_Lane> COLUMN_WEBUI_Board_Lane_ID = new org.adempiere.model.ModelColumn<I_WEBUI_Board_RecordAssignment, de.metas.ui.web.base.model.I_WEBUI_Board_Lane>(I_WEBUI_Board_RecordAssignment.class, "WEBUI_Board_Lane_ID", de.metas.ui.web.base.model.I_WEBUI_Board_Lane.class);
    /** Column name WEBUI_Board_Lane_ID */
    public static final String COLUMNNAME_WEBUI_Board_Lane_ID = "WEBUI_Board_Lane_ID";

	/**
	 * Set Board record assignment.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setWEBUI_Board_RecordAssignment_ID (int WEBUI_Board_RecordAssignment_ID);

	/**
	 * Get Board record assignment.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getWEBUI_Board_RecordAssignment_ID();

    /** Column definition for WEBUI_Board_RecordAssignment_ID */
    public static final org.adempiere.model.ModelColumn<I_WEBUI_Board_RecordAssignment, Object> COLUMN_WEBUI_Board_RecordAssignment_ID = new org.adempiere.model.ModelColumn<I_WEBUI_Board_RecordAssignment, Object>(I_WEBUI_Board_RecordAssignment.class, "WEBUI_Board_RecordAssignment_ID", null);
    /** Column name WEBUI_Board_RecordAssignment_ID */
    public static final String COLUMNNAME_WEBUI_Board_RecordAssignment_ID = "WEBUI_Board_RecordAssignment_ID";
}
