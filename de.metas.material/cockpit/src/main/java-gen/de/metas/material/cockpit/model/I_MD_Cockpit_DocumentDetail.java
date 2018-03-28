package de.metas.material.cockpit.model;


/** Generated Interface for MD_Cockpit_DocumentDetail
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_MD_Cockpit_DocumentDetail 
{

    /** TableName=MD_Cockpit_DocumentDetail */
    public static final String Table_Name = "MD_Cockpit_DocumentDetail";

    /** AD_Table_ID=540893 */
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
    public static final org.adempiere.model.ModelColumn<I_MD_Cockpit_DocumentDetail, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_MD_Cockpit_DocumentDetail, org.compiere.model.I_AD_Client>(I_MD_Cockpit_DocumentDetail.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_MD_Cockpit_DocumentDetail, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_MD_Cockpit_DocumentDetail, org.compiere.model.I_AD_Org>(I_MD_Cockpit_DocumentDetail.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Geschäftspartner.
	 * Bezeichnet einen Geschäftspartner
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_BPartner_ID (int C_BPartner_ID);

	/**
	 * Get Geschäftspartner.
	 * Bezeichnet einen Geschäftspartner
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_BPartner_ID();

	public org.compiere.model.I_C_BPartner getC_BPartner();

	public void setC_BPartner(org.compiere.model.I_C_BPartner C_BPartner);

    /** Column definition for C_BPartner_ID */
    public static final org.adempiere.model.ModelColumn<I_MD_Cockpit_DocumentDetail, org.compiere.model.I_C_BPartner> COLUMN_C_BPartner_ID = new org.adempiere.model.ModelColumn<I_MD_Cockpit_DocumentDetail, org.compiere.model.I_C_BPartner>(I_MD_Cockpit_DocumentDetail.class, "C_BPartner_ID", org.compiere.model.I_C_BPartner.class);
    /** Column name C_BPartner_ID */
    public static final String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/**
	 * Set Belegart.
	 * Belegart oder Verarbeitungsvorgaben
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_DocType_ID (int C_DocType_ID);

	/**
	 * Get Belegart.
	 * Belegart oder Verarbeitungsvorgaben
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_DocType_ID();

	public org.compiere.model.I_C_DocType getC_DocType();

	public void setC_DocType(org.compiere.model.I_C_DocType C_DocType);

    /** Column definition for C_DocType_ID */
    public static final org.adempiere.model.ModelColumn<I_MD_Cockpit_DocumentDetail, org.compiere.model.I_C_DocType> COLUMN_C_DocType_ID = new org.adempiere.model.ModelColumn<I_MD_Cockpit_DocumentDetail, org.compiere.model.I_C_DocType>(I_MD_Cockpit_DocumentDetail.class, "C_DocType_ID", org.compiere.model.I_C_DocType.class);
    /** Column name C_DocType_ID */
    public static final String COLUMNNAME_C_DocType_ID = "C_DocType_ID";

	/**
	 * Set Pauschale - Vertragsperiode.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Flatrate_Term_ID (int C_Flatrate_Term_ID);

	/**
	 * Get Pauschale - Vertragsperiode.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Flatrate_Term_ID();

    /** Column definition for C_Flatrate_Term_ID */
    public static final org.adempiere.model.ModelColumn<I_MD_Cockpit_DocumentDetail, Object> COLUMN_C_Flatrate_Term_ID = new org.adempiere.model.ModelColumn<I_MD_Cockpit_DocumentDetail, Object>(I_MD_Cockpit_DocumentDetail.class, "C_Flatrate_Term_ID", null);
    /** Column name C_Flatrate_Term_ID */
    public static final String COLUMNNAME_C_Flatrate_Term_ID = "C_Flatrate_Term_ID";

	/**
	 * Set Auftrag.
	 * Auftrag
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Order_ID (int C_Order_ID);

	/**
	 * Get Auftrag.
	 * Auftrag
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Order_ID();

	public org.compiere.model.I_C_Order getC_Order();

	public void setC_Order(org.compiere.model.I_C_Order C_Order);

    /** Column definition for C_Order_ID */
    public static final org.adempiere.model.ModelColumn<I_MD_Cockpit_DocumentDetail, org.compiere.model.I_C_Order> COLUMN_C_Order_ID = new org.adempiere.model.ModelColumn<I_MD_Cockpit_DocumentDetail, org.compiere.model.I_C_Order>(I_MD_Cockpit_DocumentDetail.class, "C_Order_ID", org.compiere.model.I_C_Order.class);
    /** Column name C_Order_ID */
    public static final String COLUMNNAME_C_Order_ID = "C_Order_ID";

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
    public static final org.adempiere.model.ModelColumn<I_MD_Cockpit_DocumentDetail, org.compiere.model.I_C_OrderLine> COLUMN_C_OrderLine_ID = new org.adempiere.model.ModelColumn<I_MD_Cockpit_DocumentDetail, org.compiere.model.I_C_OrderLine>(I_MD_Cockpit_DocumentDetail.class, "C_OrderLine_ID", org.compiere.model.I_C_OrderLine.class);
    /** Column name C_OrderLine_ID */
    public static final String COLUMNNAME_C_OrderLine_ID = "C_OrderLine_ID";

	/**
	 * Set Abo-Verlauf.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_SubscriptionProgress_ID (int C_SubscriptionProgress_ID);

	/**
	 * Get Abo-Verlauf.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_SubscriptionProgress_ID();

    /** Column definition for C_SubscriptionProgress_ID */
    public static final org.adempiere.model.ModelColumn<I_MD_Cockpit_DocumentDetail, Object> COLUMN_C_SubscriptionProgress_ID = new org.adempiere.model.ModelColumn<I_MD_Cockpit_DocumentDetail, Object>(I_MD_Cockpit_DocumentDetail.class, "C_SubscriptionProgress_ID", null);
    /** Column name C_SubscriptionProgress_ID */
    public static final String COLUMNNAME_C_SubscriptionProgress_ID = "C_SubscriptionProgress_ID";

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
    public static final org.adempiere.model.ModelColumn<I_MD_Cockpit_DocumentDetail, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_MD_Cockpit_DocumentDetail, Object>(I_MD_Cockpit_DocumentDetail.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_MD_Cockpit_DocumentDetail, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_MD_Cockpit_DocumentDetail, org.compiere.model.I_AD_User>(I_MD_Cockpit_DocumentDetail.class, "CreatedBy", org.compiere.model.I_AD_User.class);
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
    public static final org.adempiere.model.ModelColumn<I_MD_Cockpit_DocumentDetail, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_MD_Cockpit_DocumentDetail, Object>(I_MD_Cockpit_DocumentDetail.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Wareneingangsdisposition.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_ReceiptSchedule_ID (int M_ReceiptSchedule_ID);

	/**
	 * Get Wareneingangsdisposition.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_ReceiptSchedule_ID();

    /** Column definition for M_ReceiptSchedule_ID */
    public static final org.adempiere.model.ModelColumn<I_MD_Cockpit_DocumentDetail, Object> COLUMN_M_ReceiptSchedule_ID = new org.adempiere.model.ModelColumn<I_MD_Cockpit_DocumentDetail, Object>(I_MD_Cockpit_DocumentDetail.class, "M_ReceiptSchedule_ID", null);
    /** Column name M_ReceiptSchedule_ID */
    public static final String COLUMNNAME_M_ReceiptSchedule_ID = "M_ReceiptSchedule_ID";

	/**
	 * Set Lieferdisposition.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_ShipmentSchedule_ID (int M_ShipmentSchedule_ID);

	/**
	 * Get Lieferdisposition.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_ShipmentSchedule_ID();

    /** Column definition for M_ShipmentSchedule_ID */
    public static final org.adempiere.model.ModelColumn<I_MD_Cockpit_DocumentDetail, Object> COLUMN_M_ShipmentSchedule_ID = new org.adempiere.model.ModelColumn<I_MD_Cockpit_DocumentDetail, Object>(I_MD_Cockpit_DocumentDetail.class, "M_ShipmentSchedule_ID", null);
    /** Column name M_ShipmentSchedule_ID */
    public static final String COLUMNNAME_M_ShipmentSchedule_ID = "M_ShipmentSchedule_ID";

	/**
	 * Set DocumentDetail.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setMD_Cockpit_DocumentDetail_ID (int MD_Cockpit_DocumentDetail_ID);

	/**
	 * Get DocumentDetail.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getMD_Cockpit_DocumentDetail_ID();

    /** Column definition for MD_Cockpit_DocumentDetail_ID */
    public static final org.adempiere.model.ModelColumn<I_MD_Cockpit_DocumentDetail, Object> COLUMN_MD_Cockpit_DocumentDetail_ID = new org.adempiere.model.ModelColumn<I_MD_Cockpit_DocumentDetail, Object>(I_MD_Cockpit_DocumentDetail.class, "MD_Cockpit_DocumentDetail_ID", null);
    /** Column name MD_Cockpit_DocumentDetail_ID */
    public static final String COLUMNNAME_MD_Cockpit_DocumentDetail_ID = "MD_Cockpit_DocumentDetail_ID";

	/**
	 * Set Materialcockpit.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setMD_Cockpit_ID (int MD_Cockpit_ID);

	/**
	 * Get Materialcockpit.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getMD_Cockpit_ID();

	public de.metas.material.cockpit.model.I_MD_Cockpit getMD_Cockpit();

	public void setMD_Cockpit(de.metas.material.cockpit.model.I_MD_Cockpit MD_Cockpit);

    /** Column definition for MD_Cockpit_ID */
    public static final org.adempiere.model.ModelColumn<I_MD_Cockpit_DocumentDetail, de.metas.material.cockpit.model.I_MD_Cockpit> COLUMN_MD_Cockpit_ID = new org.adempiere.model.ModelColumn<I_MD_Cockpit_DocumentDetail, de.metas.material.cockpit.model.I_MD_Cockpit>(I_MD_Cockpit_DocumentDetail.class, "MD_Cockpit_ID", de.metas.material.cockpit.model.I_MD_Cockpit.class);
    /** Column name MD_Cockpit_ID */
    public static final String COLUMNNAME_MD_Cockpit_ID = "MD_Cockpit_ID";

	/**
	 * Set Bestellt/ Beauftragt.
	 * Bestellt/ Beauftragt
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setQtyOrdered (java.math.BigDecimal QtyOrdered);

	/**
	 * Get Bestellt/ Beauftragt.
	 * Bestellt/ Beauftragt
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyOrdered();

    /** Column definition for QtyOrdered */
    public static final org.adempiere.model.ModelColumn<I_MD_Cockpit_DocumentDetail, Object> COLUMN_QtyOrdered = new org.adempiere.model.ModelColumn<I_MD_Cockpit_DocumentDetail, Object>(I_MD_Cockpit_DocumentDetail.class, "QtyOrdered", null);
    /** Column name QtyOrdered */
    public static final String COLUMNNAME_QtyOrdered = "QtyOrdered";

	/**
	 * Set Offen.
	 * Offene Menge
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setQtyReserved (java.math.BigDecimal QtyReserved);

	/**
	 * Get Offen.
	 * Offene Menge
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyReserved();

    /** Column definition for QtyReserved */
    public static final org.adempiere.model.ModelColumn<I_MD_Cockpit_DocumentDetail, Object> COLUMN_QtyReserved = new org.adempiere.model.ModelColumn<I_MD_Cockpit_DocumentDetail, Object>(I_MD_Cockpit_DocumentDetail.class, "QtyReserved", null);
    /** Column name QtyReserved */
    public static final String COLUMNNAME_QtyReserved = "QtyReserved";

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
    public static final org.adempiere.model.ModelColumn<I_MD_Cockpit_DocumentDetail, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_MD_Cockpit_DocumentDetail, Object>(I_MD_Cockpit_DocumentDetail.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_MD_Cockpit_DocumentDetail, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_MD_Cockpit_DocumentDetail, org.compiere.model.I_AD_User>(I_MD_Cockpit_DocumentDetail.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
