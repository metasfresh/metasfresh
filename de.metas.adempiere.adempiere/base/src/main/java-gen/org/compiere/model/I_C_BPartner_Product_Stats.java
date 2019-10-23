package org.compiere.model;


/** Generated Interface for C_BPartner_Product_Stats
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_C_BPartner_Product_Stats 
{

    /** TableName=C_BPartner_Product_Stats */
    public static final String Table_Name = "C_BPartner_Product_Stats";

    /** AD_Table_ID=541171 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 2 - Client
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(2);

    /** Load Meta Data */

	/**
	 * Get Mandant.
	 * Mandant für diese Installation.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Client_ID();

	public org.compiere.model.I_AD_Client getAD_Client();

    /** Column definition for AD_Client_ID */
    public static final org.adempiere.model.ModelColumn<I_C_BPartner_Product_Stats, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_C_BPartner_Product_Stats, org.compiere.model.I_AD_Client>(I_C_BPartner_Product_Stats.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Sektion.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Sektion.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Org_ID();

	public org.compiere.model.I_AD_Org getAD_Org();

	public void setAD_Org(org.compiere.model.I_AD_Org AD_Org);

    /** Column definition for AD_Org_ID */
    public static final org.adempiere.model.ModelColumn<I_C_BPartner_Product_Stats, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_C_BPartner_Product_Stats, org.compiere.model.I_AD_Org>(I_C_BPartner_Product_Stats.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Geschäftspartner.
	 * Bezeichnet einen Geschäftspartner
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_BPartner_ID (int C_BPartner_ID);

	/**
	 * Get Geschäftspartner.
	 * Bezeichnet einen Geschäftspartner
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_BPartner_ID();

	public org.compiere.model.I_C_BPartner getC_BPartner();

	public void setC_BPartner(org.compiere.model.I_C_BPartner C_BPartner);

    /** Column definition for C_BPartner_ID */
    public static final org.adempiere.model.ModelColumn<I_C_BPartner_Product_Stats, org.compiere.model.I_C_BPartner> COLUMN_C_BPartner_ID = new org.adempiere.model.ModelColumn<I_C_BPartner_Product_Stats, org.compiere.model.I_C_BPartner>(I_C_BPartner_Product_Stats.class, "C_BPartner_ID", org.compiere.model.I_C_BPartner.class);
    /** Column name C_BPartner_ID */
    public static final String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/**
	 * Set Geschäftspartner Produkt Statistik.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_BPartner_Product_Stats_ID (int C_BPartner_Product_Stats_ID);

	/**
	 * Get Geschäftspartner Produkt Statistik.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_BPartner_Product_Stats_ID();

    /** Column definition for C_BPartner_Product_Stats_ID */
    public static final org.adempiere.model.ModelColumn<I_C_BPartner_Product_Stats, Object> COLUMN_C_BPartner_Product_Stats_ID = new org.adempiere.model.ModelColumn<I_C_BPartner_Product_Stats, Object>(I_C_BPartner_Product_Stats.class, "C_BPartner_Product_Stats_ID", null);
    /** Column name C_BPartner_Product_Stats_ID */
    public static final String COLUMNNAME_C_BPartner_Product_Stats_ID = "C_BPartner_Product_Stats_ID";

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
    public static final org.adempiere.model.ModelColumn<I_C_BPartner_Product_Stats, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_C_BPartner_Product_Stats, Object>(I_C_BPartner_Product_Stats.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_BPartner_Product_Stats, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_C_BPartner_Product_Stats, org.compiere.model.I_AD_User>(I_C_BPartner_Product_Stats.class, "CreatedBy", org.compiere.model.I_AD_User.class);
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
    public static final org.adempiere.model.ModelColumn<I_C_BPartner_Product_Stats, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_C_BPartner_Product_Stats, Object>(I_C_BPartner_Product_Stats.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Letzter Wareneingang.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setLastReceiptDate (java.sql.Timestamp LastReceiptDate);

	/**
	 * Get Letzter Wareneingang.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getLastReceiptDate();

    /** Column definition for LastReceiptDate */
    public static final org.adempiere.model.ModelColumn<I_C_BPartner_Product_Stats, Object> COLUMN_LastReceiptDate = new org.adempiere.model.ModelColumn<I_C_BPartner_Product_Stats, Object>(I_C_BPartner_Product_Stats.class, "LastReceiptDate", null);
    /** Column name LastReceiptDate */
    public static final String COLUMNNAME_LastReceiptDate = "LastReceiptDate";

	/**
	 * Set Letzte Debitoren Rechnung.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setLastSales_Invoice_ID (int LastSales_Invoice_ID);

	/**
	 * Get Letzte Debitoren Rechnung.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getLastSales_Invoice_ID();

	public org.compiere.model.I_C_Invoice getLastSales_Invoice();

	public void setLastSales_Invoice(org.compiere.model.I_C_Invoice LastSales_Invoice);

    /** Column definition for LastSales_Invoice_ID */
    public static final org.adempiere.model.ModelColumn<I_C_BPartner_Product_Stats, org.compiere.model.I_C_Invoice> COLUMN_LastSales_Invoice_ID = new org.adempiere.model.ModelColumn<I_C_BPartner_Product_Stats, org.compiere.model.I_C_Invoice>(I_C_BPartner_Product_Stats.class, "LastSales_Invoice_ID", org.compiere.model.I_C_Invoice.class);
    /** Column name LastSales_Invoice_ID */
    public static final String COLUMNNAME_LastSales_Invoice_ID = "LastSales_Invoice_ID";

	/**
	 * Set Last Sales Invoice Date.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setLastSalesInvoiceDate (java.sql.Timestamp LastSalesInvoiceDate);

	/**
	 * Get Last Sales Invoice Date.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getLastSalesInvoiceDate();

    /** Column definition for LastSalesInvoiceDate */
    public static final org.adempiere.model.ModelColumn<I_C_BPartner_Product_Stats, Object> COLUMN_LastSalesInvoiceDate = new org.adempiere.model.ModelColumn<I_C_BPartner_Product_Stats, Object>(I_C_BPartner_Product_Stats.class, "LastSalesInvoiceDate", null);
    /** Column name LastSalesInvoiceDate */
    public static final String COLUMNNAME_LastSalesInvoiceDate = "LastSalesInvoiceDate";

	/**
	 * Set Letzter VK.
	 * letzter Verkaufspreis
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setLastSalesPrice (java.math.BigDecimal LastSalesPrice);

	/**
	 * Get Letzter VK.
	 * letzter Verkaufspreis
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getLastSalesPrice();

    /** Column definition for LastSalesPrice */
    public static final org.adempiere.model.ModelColumn<I_C_BPartner_Product_Stats, Object> COLUMN_LastSalesPrice = new org.adempiere.model.ModelColumn<I_C_BPartner_Product_Stats, Object>(I_C_BPartner_Product_Stats.class, "LastSalesPrice", null);
    /** Column name LastSalesPrice */
    public static final String COLUMNNAME_LastSalesPrice = "LastSalesPrice";

	/**
	 * Set Letzter VK Währung.
	 * Letzter Verkaufspreis Währung
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setLastSalesPrice_Currency_ID (int LastSalesPrice_Currency_ID);

	/**
	 * Get Letzter VK Währung.
	 * Letzter Verkaufspreis Währung
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getLastSalesPrice_Currency_ID();

	public org.compiere.model.I_C_Currency getLastSalesPrice_Currency();

	public void setLastSalesPrice_Currency(org.compiere.model.I_C_Currency LastSalesPrice_Currency);

    /** Column definition for LastSalesPrice_Currency_ID */
    public static final org.adempiere.model.ModelColumn<I_C_BPartner_Product_Stats, org.compiere.model.I_C_Currency> COLUMN_LastSalesPrice_Currency_ID = new org.adempiere.model.ModelColumn<I_C_BPartner_Product_Stats, org.compiere.model.I_C_Currency>(I_C_BPartner_Product_Stats.class, "LastSalesPrice_Currency_ID", org.compiere.model.I_C_Currency.class);
    /** Column name LastSalesPrice_Currency_ID */
    public static final String COLUMNNAME_LastSalesPrice_Currency_ID = "LastSalesPrice_Currency_ID";

	/**
	 * Set Letzte Lieferung.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setLastShipDate (java.sql.Timestamp LastShipDate);

	/**
	 * Get Letzte Lieferung.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getLastShipDate();

    /** Column definition for LastShipDate */
    public static final org.adempiere.model.ModelColumn<I_C_BPartner_Product_Stats, Object> COLUMN_LastShipDate = new org.adempiere.model.ModelColumn<I_C_BPartner_Product_Stats, Object>(I_C_BPartner_Product_Stats.class, "LastShipDate", null);
    /** Column name LastShipDate */
    public static final String COLUMNNAME_LastShipDate = "LastShipDate";

	/**
	 * Set Produkt.
	 * Produkt, Leistung, Artikel
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_Product_ID (int M_Product_ID);

	/**
	 * Get Produkt.
	 * Produkt, Leistung, Artikel
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_Product_ID();

	public org.compiere.model.I_M_Product getM_Product();

	public void setM_Product(org.compiere.model.I_M_Product M_Product);

    /** Column definition for M_Product_ID */
    public static final org.adempiere.model.ModelColumn<I_C_BPartner_Product_Stats, org.compiere.model.I_M_Product> COLUMN_M_Product_ID = new org.adempiere.model.ModelColumn<I_C_BPartner_Product_Stats, org.compiere.model.I_M_Product>(I_C_BPartner_Product_Stats.class, "M_Product_ID", org.compiere.model.I_M_Product.class);
    /** Column name M_Product_ID */
    public static final String COLUMNNAME_M_Product_ID = "M_Product_ID";

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
    public static final org.adempiere.model.ModelColumn<I_C_BPartner_Product_Stats, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_C_BPartner_Product_Stats, Object>(I_C_BPartner_Product_Stats.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_BPartner_Product_Stats, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_C_BPartner_Product_Stats, org.compiere.model.I_AD_User>(I_C_BPartner_Product_Stats.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
