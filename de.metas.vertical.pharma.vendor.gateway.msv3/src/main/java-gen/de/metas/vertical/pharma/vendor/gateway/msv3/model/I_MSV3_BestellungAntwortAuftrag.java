package de.metas.vertical.pharma.vendor.gateway.msv3.model;


/** Generated Interface for MSV3_BestellungAntwortAuftrag
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_MSV3_BestellungAntwortAuftrag 
{

    /** TableName=MSV3_BestellungAntwortAuftrag */
    public static final String Table_Name = "MSV3_BestellungAntwortAuftrag";

    /** AD_Table_ID=540912 */
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
    public static final org.adempiere.model.ModelColumn<I_MSV3_BestellungAntwortAuftrag, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_MSV3_BestellungAntwortAuftrag, org.compiere.model.I_AD_Client>(I_MSV3_BestellungAntwortAuftrag.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_MSV3_BestellungAntwortAuftrag, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_MSV3_BestellungAntwortAuftrag, org.compiere.model.I_AD_Org>(I_MSV3_BestellungAntwortAuftrag.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

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
    public static final org.adempiere.model.ModelColumn<I_MSV3_BestellungAntwortAuftrag, org.compiere.model.I_C_Order> COLUMN_C_OrderPO_ID = new org.adempiere.model.ModelColumn<I_MSV3_BestellungAntwortAuftrag, org.compiere.model.I_C_Order>(I_MSV3_BestellungAntwortAuftrag.class, "C_OrderPO_ID", org.compiere.model.I_C_Order.class);
    /** Column name C_OrderPO_ID */
    public static final String COLUMNNAME_C_OrderPO_ID = "C_OrderPO_ID";

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
    public static final org.adempiere.model.ModelColumn<I_MSV3_BestellungAntwortAuftrag, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_MSV3_BestellungAntwortAuftrag, Object>(I_MSV3_BestellungAntwortAuftrag.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_MSV3_BestellungAntwortAuftrag, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_MSV3_BestellungAntwortAuftrag, org.compiere.model.I_AD_User>(I_MSV3_BestellungAntwortAuftrag.class, "CreatedBy", org.compiere.model.I_AD_User.class);
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
    public static final org.adempiere.model.ModelColumn<I_MSV3_BestellungAntwortAuftrag, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_MSV3_BestellungAntwortAuftrag, Object>(I_MSV3_BestellungAntwortAuftrag.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Auftragsart.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setMSV3_Auftragsart (java.lang.String MSV3_Auftragsart);

	/**
	 * Get Auftragsart.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getMSV3_Auftragsart();

    /** Column definition for MSV3_Auftragsart */
    public static final org.adempiere.model.ModelColumn<I_MSV3_BestellungAntwortAuftrag, Object> COLUMN_MSV3_Auftragsart = new org.adempiere.model.ModelColumn<I_MSV3_BestellungAntwortAuftrag, Object>(I_MSV3_BestellungAntwortAuftrag.class, "MSV3_Auftragsart", null);
    /** Column name MSV3_Auftragsart */
    public static final String COLUMNNAME_MSV3_Auftragsart = "MSV3_Auftragsart";

	/**
	 * Set Auftragsfehler.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setMSV3_Auftragsfehler_ID (int MSV3_Auftragsfehler_ID);

	/**
	 * Get Auftragsfehler.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getMSV3_Auftragsfehler_ID();

	public de.metas.vertical.pharma.vendor.gateway.msv3.model.I_MSV3_FaultInfo getMSV3_Auftragsfehler();

	public void setMSV3_Auftragsfehler(de.metas.vertical.pharma.vendor.gateway.msv3.model.I_MSV3_FaultInfo MSV3_Auftragsfehler);

    /** Column definition for MSV3_Auftragsfehler_ID */
    public static final org.adempiere.model.ModelColumn<I_MSV3_BestellungAntwortAuftrag, de.metas.vertical.pharma.vendor.gateway.msv3.model.I_MSV3_FaultInfo> COLUMN_MSV3_Auftragsfehler_ID = new org.adempiere.model.ModelColumn<I_MSV3_BestellungAntwortAuftrag, de.metas.vertical.pharma.vendor.gateway.msv3.model.I_MSV3_FaultInfo>(I_MSV3_BestellungAntwortAuftrag.class, "MSV3_Auftragsfehler_ID", de.metas.vertical.pharma.vendor.gateway.msv3.model.I_MSV3_FaultInfo.class);
    /** Column name MSV3_Auftragsfehler_ID */
    public static final String COLUMNNAME_MSV3_Auftragsfehler_ID = "MSV3_Auftragsfehler_ID";

	/**
	 * Set Auftragskennung.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setMSV3_Auftragskennung (java.lang.String MSV3_Auftragskennung);

	/**
	 * Get Auftragskennung.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getMSV3_Auftragskennung();

    /** Column definition for MSV3_Auftragskennung */
    public static final org.adempiere.model.ModelColumn<I_MSV3_BestellungAntwortAuftrag, Object> COLUMN_MSV3_Auftragskennung = new org.adempiere.model.ModelColumn<I_MSV3_BestellungAntwortAuftrag, Object>(I_MSV3_BestellungAntwortAuftrag.class, "MSV3_Auftragskennung", null);
    /** Column name MSV3_Auftragskennung */
    public static final String COLUMNNAME_MSV3_Auftragskennung = "MSV3_Auftragskennung";

	/**
	 * Set AuftragsSupportID.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setMSV3_AuftragsSupportID (int MSV3_AuftragsSupportID);

	/**
	 * Get AuftragsSupportID.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getMSV3_AuftragsSupportID();

    /** Column definition for MSV3_AuftragsSupportID */
    public static final org.adempiere.model.ModelColumn<I_MSV3_BestellungAntwortAuftrag, Object> COLUMN_MSV3_AuftragsSupportID = new org.adempiere.model.ModelColumn<I_MSV3_BestellungAntwortAuftrag, Object>(I_MSV3_BestellungAntwortAuftrag.class, "MSV3_AuftragsSupportID", null);
    /** Column name MSV3_AuftragsSupportID */
    public static final String COLUMNNAME_MSV3_AuftragsSupportID = "MSV3_AuftragsSupportID";

	/**
	 * Set MSV3_BestellungAntwortAuftrag.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setMSV3_BestellungAntwortAuftrag_ID (int MSV3_BestellungAntwortAuftrag_ID);

	/**
	 * Get MSV3_BestellungAntwortAuftrag.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getMSV3_BestellungAntwortAuftrag_ID();

    /** Column definition for MSV3_BestellungAntwortAuftrag_ID */
    public static final org.adempiere.model.ModelColumn<I_MSV3_BestellungAntwortAuftrag, Object> COLUMN_MSV3_BestellungAntwortAuftrag_ID = new org.adempiere.model.ModelColumn<I_MSV3_BestellungAntwortAuftrag, Object>(I_MSV3_BestellungAntwortAuftrag.class, "MSV3_BestellungAntwortAuftrag_ID", null);
    /** Column name MSV3_BestellungAntwortAuftrag_ID */
    public static final String COLUMNNAME_MSV3_BestellungAntwortAuftrag_ID = "MSV3_BestellungAntwortAuftrag_ID";

	/**
	 * Set MSV3_BestellungAntwort.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setMSV3_BestellungAntwort_ID (int MSV3_BestellungAntwort_ID);

	/**
	 * Get MSV3_BestellungAntwort.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getMSV3_BestellungAntwort_ID();

	public de.metas.vertical.pharma.vendor.gateway.msv3.model.I_MSV3_BestellungAntwort getMSV3_BestellungAntwort();

	public void setMSV3_BestellungAntwort(de.metas.vertical.pharma.vendor.gateway.msv3.model.I_MSV3_BestellungAntwort MSV3_BestellungAntwort);

    /** Column definition for MSV3_BestellungAntwort_ID */
    public static final org.adempiere.model.ModelColumn<I_MSV3_BestellungAntwortAuftrag, de.metas.vertical.pharma.vendor.gateway.msv3.model.I_MSV3_BestellungAntwort> COLUMN_MSV3_BestellungAntwort_ID = new org.adempiere.model.ModelColumn<I_MSV3_BestellungAntwortAuftrag, de.metas.vertical.pharma.vendor.gateway.msv3.model.I_MSV3_BestellungAntwort>(I_MSV3_BestellungAntwortAuftrag.class, "MSV3_BestellungAntwort_ID", de.metas.vertical.pharma.vendor.gateway.msv3.model.I_MSV3_BestellungAntwort.class);
    /** Column name MSV3_BestellungAntwort_ID */
    public static final String COLUMNNAME_MSV3_BestellungAntwort_ID = "MSV3_BestellungAntwort_ID";

	/**
	 * Set GebindeId.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setMSV3_GebindeId (java.lang.String MSV3_GebindeId);

	/**
	 * Get GebindeId.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getMSV3_GebindeId();

    /** Column definition for MSV3_GebindeId */
    public static final org.adempiere.model.ModelColumn<I_MSV3_BestellungAntwortAuftrag, Object> COLUMN_MSV3_GebindeId = new org.adempiere.model.ModelColumn<I_MSV3_BestellungAntwortAuftrag, Object>(I_MSV3_BestellungAntwortAuftrag.class, "MSV3_GebindeId", null);
    /** Column name MSV3_GebindeId */
    public static final String COLUMNNAME_MSV3_GebindeId = "MSV3_GebindeId";

	/**
	 * Set Id.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setMSV3_Id (java.lang.String MSV3_Id);

	/**
	 * Get Id.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getMSV3_Id();

    /** Column definition for MSV3_Id */
    public static final org.adempiere.model.ModelColumn<I_MSV3_BestellungAntwortAuftrag, Object> COLUMN_MSV3_Id = new org.adempiere.model.ModelColumn<I_MSV3_BestellungAntwortAuftrag, Object>(I_MSV3_BestellungAntwortAuftrag.class, "MSV3_Id", null);
    /** Column name MSV3_Id */
    public static final String COLUMNNAME_MSV3_Id = "MSV3_Id";

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
    public static final org.adempiere.model.ModelColumn<I_MSV3_BestellungAntwortAuftrag, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_MSV3_BestellungAntwortAuftrag, Object>(I_MSV3_BestellungAntwortAuftrag.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_MSV3_BestellungAntwortAuftrag, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_MSV3_BestellungAntwortAuftrag, org.compiere.model.I_AD_User>(I_MSV3_BestellungAntwortAuftrag.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
