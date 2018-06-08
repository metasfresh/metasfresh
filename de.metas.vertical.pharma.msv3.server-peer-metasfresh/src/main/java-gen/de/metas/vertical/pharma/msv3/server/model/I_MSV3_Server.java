package de.metas.vertical.pharma.msv3.server.model;


/** Generated Interface for MSV3_Server
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_MSV3_Server 
{

    /** TableName=MSV3_Server */
    public static final String Table_Name = "MSV3_Server";

    /** AD_Table_ID=540956 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(3);

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
    public static final org.adempiere.model.ModelColumn<I_MSV3_Server, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_MSV3_Server, org.compiere.model.I_AD_Client>(I_MSV3_Server.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_MSV3_Server, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_MSV3_Server, org.compiere.model.I_AD_Org>(I_MSV3_Server.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
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
    public static final org.adempiere.model.ModelColumn<I_MSV3_Server, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_MSV3_Server, Object>(I_MSV3_Server.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_MSV3_Server, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_MSV3_Server, org.compiere.model.I_AD_User>(I_MSV3_Server.class, "CreatedBy", org.compiere.model.I_AD_User.class);
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
    public static final org.adempiere.model.ModelColumn<I_MSV3_Server, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_MSV3_Server, Object>(I_MSV3_Server.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Warehouse Picking Group.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_Warehouse_PickingGroup_ID (int M_Warehouse_PickingGroup_ID);

	/**
	 * Get Warehouse Picking Group.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_Warehouse_PickingGroup_ID();

	public org.compiere.model.I_M_Warehouse_PickingGroup getM_Warehouse_PickingGroup();

	public void setM_Warehouse_PickingGroup(org.compiere.model.I_M_Warehouse_PickingGroup M_Warehouse_PickingGroup);

    /** Column definition for M_Warehouse_PickingGroup_ID */
    public static final org.adempiere.model.ModelColumn<I_MSV3_Server, org.compiere.model.I_M_Warehouse_PickingGroup> COLUMN_M_Warehouse_PickingGroup_ID = new org.adempiere.model.ModelColumn<I_MSV3_Server, org.compiere.model.I_M_Warehouse_PickingGroup>(I_MSV3_Server.class, "M_Warehouse_PickingGroup_ID", org.compiere.model.I_M_Warehouse_PickingGroup.class);
    /** Column name M_Warehouse_PickingGroup_ID */
    public static final String COLUMNNAME_M_Warehouse_PickingGroup_ID = "M_Warehouse_PickingGroup_ID";

	/**
	 * Set MSV3 Server.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setMSV3_Server_ID (int MSV3_Server_ID);

	/**
	 * Get MSV3 Server.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getMSV3_Server_ID();

    /** Column definition for MSV3_Server_ID */
    public static final org.adempiere.model.ModelColumn<I_MSV3_Server, Object> COLUMN_MSV3_Server_ID = new org.adempiere.model.ModelColumn<I_MSV3_Server, Object>(I_MSV3_Server.class, "MSV3_Server_ID", null);
    /** Column name MSV3_Server_ID */
    public static final String COLUMNNAME_MSV3_Server_ID = "MSV3_Server_ID";

	/**
	 * Set Min. Zusagbar (ATP).
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setQty_AvailableToPromise_Min (java.math.BigDecimal Qty_AvailableToPromise_Min);

	/**
	 * Get Min. Zusagbar (ATP).
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQty_AvailableToPromise_Min();

    /** Column definition for Qty_AvailableToPromise_Min */
    public static final org.adempiere.model.ModelColumn<I_MSV3_Server, Object> COLUMN_Qty_AvailableToPromise_Min = new org.adempiere.model.ModelColumn<I_MSV3_Server, Object>(I_MSV3_Server.class, "Qty_AvailableToPromise_Min", null);
    /** Column name Qty_AvailableToPromise_Min */
    public static final String COLUMNNAME_Qty_AvailableToPromise_Min = "Qty_AvailableToPromise_Min";

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
    public static final org.adempiere.model.ModelColumn<I_MSV3_Server, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_MSV3_Server, Object>(I_MSV3_Server.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_MSV3_Server, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_MSV3_Server, org.compiere.model.I_AD_User>(I_MSV3_Server.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
