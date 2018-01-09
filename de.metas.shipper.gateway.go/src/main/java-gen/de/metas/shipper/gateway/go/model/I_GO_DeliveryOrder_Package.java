package de.metas.shipper.gateway.go.model;


/** Generated Interface for GO_DeliveryOrder_Package
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_GO_DeliveryOrder_Package 
{

    /** TableName=GO_DeliveryOrder_Package */
    public static final String Table_Name = "GO_DeliveryOrder_Package";

    /** AD_Table_ID=540894 */
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
    public static final org.adempiere.model.ModelColumn<I_GO_DeliveryOrder_Package, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_GO_DeliveryOrder_Package, org.compiere.model.I_AD_Client>(I_GO_DeliveryOrder_Package.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_GO_DeliveryOrder_Package, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_GO_DeliveryOrder_Package, org.compiere.model.I_AD_Org>(I_GO_DeliveryOrder_Package.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
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
    public static final org.adempiere.model.ModelColumn<I_GO_DeliveryOrder_Package, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_GO_DeliveryOrder_Package, Object>(I_GO_DeliveryOrder_Package.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_GO_DeliveryOrder_Package, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_GO_DeliveryOrder_Package, org.compiere.model.I_AD_User>(I_GO_DeliveryOrder_Package.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set GO Delivery Order.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setGO_DeliveryOrder_ID (int GO_DeliveryOrder_ID);

	/**
	 * Get GO Delivery Order.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getGO_DeliveryOrder_ID();

	public de.metas.shipper.gateway.go.model.I_GO_DeliveryOrder getGO_DeliveryOrder();

	public void setGO_DeliveryOrder(de.metas.shipper.gateway.go.model.I_GO_DeliveryOrder GO_DeliveryOrder);

    /** Column definition for GO_DeliveryOrder_ID */
    public static final org.adempiere.model.ModelColumn<I_GO_DeliveryOrder_Package, de.metas.shipper.gateway.go.model.I_GO_DeliveryOrder> COLUMN_GO_DeliveryOrder_ID = new org.adempiere.model.ModelColumn<I_GO_DeliveryOrder_Package, de.metas.shipper.gateway.go.model.I_GO_DeliveryOrder>(I_GO_DeliveryOrder_Package.class, "GO_DeliveryOrder_ID", de.metas.shipper.gateway.go.model.I_GO_DeliveryOrder.class);
    /** Column name GO_DeliveryOrder_ID */
    public static final String COLUMNNAME_GO_DeliveryOrder_ID = "GO_DeliveryOrder_ID";

	/**
	 * Set GO Delivery Order Package.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setGO_DeliveryOrder_Package_ID (int GO_DeliveryOrder_Package_ID);

	/**
	 * Get GO Delivery Order Package.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getGO_DeliveryOrder_Package_ID();

    /** Column definition for GO_DeliveryOrder_Package_ID */
    public static final org.adempiere.model.ModelColumn<I_GO_DeliveryOrder_Package, Object> COLUMN_GO_DeliveryOrder_Package_ID = new org.adempiere.model.ModelColumn<I_GO_DeliveryOrder_Package, Object>(I_GO_DeliveryOrder_Package.class, "GO_DeliveryOrder_Package_ID", null);
    /** Column name GO_DeliveryOrder_Package_ID */
    public static final String COLUMNNAME_GO_DeliveryOrder_Package_ID = "GO_DeliveryOrder_Package_ID";

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
    public static final org.adempiere.model.ModelColumn<I_GO_DeliveryOrder_Package, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_GO_DeliveryOrder_Package, Object>(I_GO_DeliveryOrder_Package.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Packstück.
	 * Shipment Package
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_Package_ID (int M_Package_ID);

	/**
	 * Get Packstück.
	 * Shipment Package
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_Package_ID();

	public org.compiere.model.I_M_Package getM_Package();

	public void setM_Package(org.compiere.model.I_M_Package M_Package);

    /** Column definition for M_Package_ID */
    public static final org.adempiere.model.ModelColumn<I_GO_DeliveryOrder_Package, org.compiere.model.I_M_Package> COLUMN_M_Package_ID = new org.adempiere.model.ModelColumn<I_GO_DeliveryOrder_Package, org.compiere.model.I_M_Package>(I_GO_DeliveryOrder_Package.class, "M_Package_ID", org.compiere.model.I_M_Package.class);
    /** Column name M_Package_ID */
    public static final String COLUMNNAME_M_Package_ID = "M_Package_ID";

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
    public static final org.adempiere.model.ModelColumn<I_GO_DeliveryOrder_Package, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_GO_DeliveryOrder_Package, Object>(I_GO_DeliveryOrder_Package.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_GO_DeliveryOrder_Package, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_GO_DeliveryOrder_Package, org.compiere.model.I_AD_User>(I_GO_DeliveryOrder_Package.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
