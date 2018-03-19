package de.metas.purchasecandidate.model;


/** Generated Interface for C_PurchaseCandidate_Alloc
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_C_PurchaseCandidate_Alloc 
{

    /** TableName=C_PurchaseCandidate_Alloc */
    public static final String Table_Name = "C_PurchaseCandidate_Alloc";

    /** AD_Table_ID=540930 */
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
    public static final org.adempiere.model.ModelColumn<I_C_PurchaseCandidate_Alloc, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_C_PurchaseCandidate_Alloc, org.compiere.model.I_AD_Client>(I_C_PurchaseCandidate_Alloc.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set System-Problem.
	 * Automatically created or manually entered System Issue
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_Issue_ID (int AD_Issue_ID);

	/**
	 * Get System-Problem.
	 * Automatically created or manually entered System Issue
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_Issue_ID();

	public org.compiere.model.I_AD_Issue getAD_Issue();

	public void setAD_Issue(org.compiere.model.I_AD_Issue AD_Issue);

    /** Column definition for AD_Issue_ID */
    public static final org.adempiere.model.ModelColumn<I_C_PurchaseCandidate_Alloc, org.compiere.model.I_AD_Issue> COLUMN_AD_Issue_ID = new org.adempiere.model.ModelColumn<I_C_PurchaseCandidate_Alloc, org.compiere.model.I_AD_Issue>(I_C_PurchaseCandidate_Alloc.class, "AD_Issue_ID", org.compiere.model.I_AD_Issue.class);
    /** Column name AD_Issue_ID */
    public static final String COLUMNNAME_AD_Issue_ID = "AD_Issue_ID";

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
    public static final org.adempiere.model.ModelColumn<I_C_PurchaseCandidate_Alloc, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_C_PurchaseCandidate_Alloc, org.compiere.model.I_AD_Org>(I_C_PurchaseCandidate_Alloc.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set DB-Tabelle.
	 * Database Table information
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_Table_ID (int AD_Table_ID);

	/**
	 * Get DB-Tabelle.
	 * Database Table information
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_Table_ID();

	public org.compiere.model.I_AD_Table getAD_Table();

	public void setAD_Table(org.compiere.model.I_AD_Table AD_Table);

    /** Column definition for AD_Table_ID */
    public static final org.adempiere.model.ModelColumn<I_C_PurchaseCandidate_Alloc, org.compiere.model.I_AD_Table> COLUMN_AD_Table_ID = new org.adempiere.model.ModelColumn<I_C_PurchaseCandidate_Alloc, org.compiere.model.I_AD_Table>(I_C_PurchaseCandidate_Alloc.class, "AD_Table_ID", org.compiere.model.I_AD_Table.class);
    /** Column name AD_Table_ID */
    public static final String COLUMNNAME_AD_Table_ID = "AD_Table_ID";

	/**
	 * Set Bestellposition.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_OrderLinePO_ID (int C_OrderLinePO_ID);

	/**
	 * Get Bestellposition.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_OrderLinePO_ID();

	public org.compiere.model.I_C_OrderLine getC_OrderLinePO();

	public void setC_OrderLinePO(org.compiere.model.I_C_OrderLine C_OrderLinePO);

    /** Column definition for C_OrderLinePO_ID */
    public static final org.adempiere.model.ModelColumn<I_C_PurchaseCandidate_Alloc, org.compiere.model.I_C_OrderLine> COLUMN_C_OrderLinePO_ID = new org.adempiere.model.ModelColumn<I_C_PurchaseCandidate_Alloc, org.compiere.model.I_C_OrderLine>(I_C_PurchaseCandidate_Alloc.class, "C_OrderLinePO_ID", org.compiere.model.I_C_OrderLine.class);
    /** Column name C_OrderLinePO_ID */
    public static final String COLUMNNAME_C_OrderLinePO_ID = "C_OrderLinePO_ID";

	/**
	 * Set Bestellung.
	 * Bestellung
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_OrderPO_ID (int C_OrderPO_ID);

	/**
	 * Get Bestellung.
	 * Bestellung
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_OrderPO_ID();

	public org.compiere.model.I_C_Order getC_OrderPO();

	public void setC_OrderPO(org.compiere.model.I_C_Order C_OrderPO);

    /** Column definition for C_OrderPO_ID */
    public static final org.adempiere.model.ModelColumn<I_C_PurchaseCandidate_Alloc, org.compiere.model.I_C_Order> COLUMN_C_OrderPO_ID = new org.adempiere.model.ModelColumn<I_C_PurchaseCandidate_Alloc, org.compiere.model.I_C_Order>(I_C_PurchaseCandidate_Alloc.class, "C_OrderPO_ID", org.compiere.model.I_C_Order.class);
    /** Column name C_OrderPO_ID */
    public static final String COLUMNNAME_C_OrderPO_ID = "C_OrderPO_ID";

	/**
	 * Set C_PurchaseCandidate_Alloc.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_PurchaseCandidate_Alloc_ID (int C_PurchaseCandidate_Alloc_ID);

	/**
	 * Get C_PurchaseCandidate_Alloc.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_PurchaseCandidate_Alloc_ID();

    /** Column definition for C_PurchaseCandidate_Alloc_ID */
    public static final org.adempiere.model.ModelColumn<I_C_PurchaseCandidate_Alloc, Object> COLUMN_C_PurchaseCandidate_Alloc_ID = new org.adempiere.model.ModelColumn<I_C_PurchaseCandidate_Alloc, Object>(I_C_PurchaseCandidate_Alloc.class, "C_PurchaseCandidate_Alloc_ID", null);
    /** Column name C_PurchaseCandidate_Alloc_ID */
    public static final String COLUMNNAME_C_PurchaseCandidate_Alloc_ID = "C_PurchaseCandidate_Alloc_ID";

	/**
	 * Set Purchase candidate.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_PurchaseCandidate_ID (int C_PurchaseCandidate_ID);

	/**
	 * Get Purchase candidate.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_PurchaseCandidate_ID();

	public de.metas.purchasecandidate.model.I_C_PurchaseCandidate getC_PurchaseCandidate();

	public void setC_PurchaseCandidate(de.metas.purchasecandidate.model.I_C_PurchaseCandidate C_PurchaseCandidate);

    /** Column definition for C_PurchaseCandidate_ID */
    public static final org.adempiere.model.ModelColumn<I_C_PurchaseCandidate_Alloc, de.metas.purchasecandidate.model.I_C_PurchaseCandidate> COLUMN_C_PurchaseCandidate_ID = new org.adempiere.model.ModelColumn<I_C_PurchaseCandidate_Alloc, de.metas.purchasecandidate.model.I_C_PurchaseCandidate>(I_C_PurchaseCandidate_Alloc.class, "C_PurchaseCandidate_ID", de.metas.purchasecandidate.model.I_C_PurchaseCandidate.class);
    /** Column name C_PurchaseCandidate_ID */
    public static final String COLUMNNAME_C_PurchaseCandidate_ID = "C_PurchaseCandidate_ID";

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
    public static final org.adempiere.model.ModelColumn<I_C_PurchaseCandidate_Alloc, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_C_PurchaseCandidate_Alloc, Object>(I_C_PurchaseCandidate_Alloc.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_PurchaseCandidate_Alloc, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_C_PurchaseCandidate_Alloc, org.compiere.model.I_AD_User>(I_C_PurchaseCandidate_Alloc.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Zugesagter Termin.
	 * Zugesagter Termin für diesen Auftrag
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDatePromised (java.sql.Timestamp DatePromised);

	/**
	 * Get Zugesagter Termin.
	 * Zugesagter Termin für diesen Auftrag
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getDatePromised();

    /** Column definition for DatePromised */
    public static final org.adempiere.model.ModelColumn<I_C_PurchaseCandidate_Alloc, Object> COLUMN_DatePromised = new org.adempiere.model.ModelColumn<I_C_PurchaseCandidate_Alloc, Object>(I_C_PurchaseCandidate_Alloc.class, "DatePromised", null);
    /** Column name DatePromised */
    public static final String COLUMNNAME_DatePromised = "DatePromised";

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
    public static final org.adempiere.model.ModelColumn<I_C_PurchaseCandidate_Alloc, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_C_PurchaseCandidate_Alloc, Object>(I_C_PurchaseCandidate_Alloc.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Datensatz-ID.
	 * Direct internal record ID
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setRecord_ID (int Record_ID);

	/**
	 * Get Datensatz-ID.
	 * Direct internal record ID
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getRecord_ID();

    /** Column definition for Record_ID */
    public static final org.adempiere.model.ModelColumn<I_C_PurchaseCandidate_Alloc, Object> COLUMN_Record_ID = new org.adempiere.model.ModelColumn<I_C_PurchaseCandidate_Alloc, Object>(I_C_PurchaseCandidate_Alloc.class, "Record_ID", null);
    /** Column name Record_ID */
    public static final String COLUMNNAME_Record_ID = "Record_ID";

	/**
	 * Set Bestell-Kennung.
	 * Kennung zur Eindeutigen Identifikation der Bestellung beim Lieferanten
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setRemotePurchaseOrderId (java.lang.String RemotePurchaseOrderId);

	/**
	 * Get Bestell-Kennung.
	 * Kennung zur Eindeutigen Identifikation der Bestellung beim Lieferanten
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getRemotePurchaseOrderId();

    /** Column definition for RemotePurchaseOrderId */
    public static final org.adempiere.model.ModelColumn<I_C_PurchaseCandidate_Alloc, Object> COLUMN_RemotePurchaseOrderId = new org.adempiere.model.ModelColumn<I_C_PurchaseCandidate_Alloc, Object>(I_C_PurchaseCandidate_Alloc.class, "RemotePurchaseOrderId", null);
    /** Column name RemotePurchaseOrderId */
    public static final String COLUMNNAME_RemotePurchaseOrderId = "RemotePurchaseOrderId";

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
    public static final org.adempiere.model.ModelColumn<I_C_PurchaseCandidate_Alloc, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_C_PurchaseCandidate_Alloc, Object>(I_C_PurchaseCandidate_Alloc.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_PurchaseCandidate_Alloc, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_C_PurchaseCandidate_Alloc, org.compiere.model.I_AD_User>(I_C_PurchaseCandidate_Alloc.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
