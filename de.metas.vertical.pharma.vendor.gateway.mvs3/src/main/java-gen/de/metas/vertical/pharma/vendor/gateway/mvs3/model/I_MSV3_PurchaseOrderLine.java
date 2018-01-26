package de.metas.vertical.pharma.vendor.gateway.mvs3.model;


/** Generated Interface for MSV3_PurchaseOrderLine
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_MSV3_PurchaseOrderLine 
{

    /** TableName=MSV3_PurchaseOrderLine */
    public static final String Table_Name = "MSV3_PurchaseOrderLine";

    /** AD_Table_ID=540905 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 1 - Org
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(1);

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
    public static final org.adempiere.model.ModelColumn<I_MSV3_PurchaseOrderLine, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_MSV3_PurchaseOrderLine, org.compiere.model.I_AD_Client>(I_MSV3_PurchaseOrderLine.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_MSV3_PurchaseOrderLine, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_MSV3_PurchaseOrderLine, org.compiere.model.I_AD_Org>(I_MSV3_PurchaseOrderLine.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Auftragsposition.
	 * Auftragsposition
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_OrderLine_ID (int C_OrderLine_ID);

	/**
	 * Get Auftragsposition.
	 * Auftragsposition
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_OrderLine_ID();

	public org.compiere.model.I_C_OrderLine getC_OrderLine();

	public void setC_OrderLine(org.compiere.model.I_C_OrderLine C_OrderLine);

    /** Column definition for C_OrderLine_ID */
    public static final org.adempiere.model.ModelColumn<I_MSV3_PurchaseOrderLine, org.compiere.model.I_C_OrderLine> COLUMN_C_OrderLine_ID = new org.adempiere.model.ModelColumn<I_MSV3_PurchaseOrderLine, org.compiere.model.I_C_OrderLine>(I_MSV3_PurchaseOrderLine.class, "C_OrderLine_ID", org.compiere.model.I_C_OrderLine.class);
    /** Column name C_OrderLine_ID */
    public static final String COLUMNNAME_C_OrderLine_ID = "C_OrderLine_ID";

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
    public static final org.adempiere.model.ModelColumn<I_MSV3_PurchaseOrderLine, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_MSV3_PurchaseOrderLine, Object>(I_MSV3_PurchaseOrderLine.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_MSV3_PurchaseOrderLine, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_MSV3_PurchaseOrderLine, org.compiere.model.I_AD_User>(I_MSV3_PurchaseOrderLine.class, "CreatedBy", org.compiere.model.I_AD_User.class);
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
    public static final org.adempiere.model.ModelColumn<I_MSV3_PurchaseOrderLine, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_MSV3_PurchaseOrderLine, Object>(I_MSV3_PurchaseOrderLine.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set MSV3_PurchaseOrder.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setMSV3_PurchaseOrder_ID (int MSV3_PurchaseOrder_ID);

	/**
	 * Get MSV3_PurchaseOrder.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getMSV3_PurchaseOrder_ID();

	public de.metas.vertical.pharma.vendor.gateway.mvs3.model.I_MSV3_PurchaseOrder getMSV3_PurchaseOrder();

	public void setMSV3_PurchaseOrder(de.metas.vertical.pharma.vendor.gateway.mvs3.model.I_MSV3_PurchaseOrder MSV3_PurchaseOrder);

    /** Column definition for MSV3_PurchaseOrder_ID */
    public static final org.adempiere.model.ModelColumn<I_MSV3_PurchaseOrderLine, de.metas.vertical.pharma.vendor.gateway.mvs3.model.I_MSV3_PurchaseOrder> COLUMN_MSV3_PurchaseOrder_ID = new org.adempiere.model.ModelColumn<I_MSV3_PurchaseOrderLine, de.metas.vertical.pharma.vendor.gateway.mvs3.model.I_MSV3_PurchaseOrder>(I_MSV3_PurchaseOrderLine.class, "MSV3_PurchaseOrder_ID", de.metas.vertical.pharma.vendor.gateway.mvs3.model.I_MSV3_PurchaseOrder.class);
    /** Column name MSV3_PurchaseOrder_ID */
    public static final String COLUMNNAME_MSV3_PurchaseOrder_ID = "MSV3_PurchaseOrder_ID";

	/**
	 * Set MSV3_PurchaseOrderLine.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setMSV3_PurchaseOrderLine_ID (int MSV3_PurchaseOrderLine_ID);

	/**
	 * Get MSV3_PurchaseOrderLine.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getMSV3_PurchaseOrderLine_ID();

    /** Column definition for MSV3_PurchaseOrderLine_ID */
    public static final org.adempiere.model.ModelColumn<I_MSV3_PurchaseOrderLine, Object> COLUMN_MSV3_PurchaseOrderLine_ID = new org.adempiere.model.ModelColumn<I_MSV3_PurchaseOrderLine, Object>(I_MSV3_PurchaseOrderLine.class, "MSV3_PurchaseOrderLine_ID", null);
    /** Column name MSV3_PurchaseOrderLine_ID */
    public static final String COLUMNNAME_MSV3_PurchaseOrderLine_ID = "MSV3_PurchaseOrderLine_ID";

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
    public static final org.adempiere.model.ModelColumn<I_MSV3_PurchaseOrderLine, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_MSV3_PurchaseOrderLine, Object>(I_MSV3_PurchaseOrderLine.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_MSV3_PurchaseOrderLine, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_MSV3_PurchaseOrderLine, org.compiere.model.I_AD_User>(I_MSV3_PurchaseOrderLine.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
