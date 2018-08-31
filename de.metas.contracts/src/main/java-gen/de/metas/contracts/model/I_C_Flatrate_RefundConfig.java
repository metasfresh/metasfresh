package de.metas.contracts.model;


/** Generated Interface for C_Flatrate_RefundConfig
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_C_Flatrate_RefundConfig 
{

    /** TableName=C_Flatrate_RefundConfig */
    public static final String Table_Name = "C_Flatrate_RefundConfig";

    /** AD_Table_ID=540980 */
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
    public static final org.adempiere.model.ModelColumn<I_C_Flatrate_RefundConfig, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_C_Flatrate_RefundConfig, org.compiere.model.I_AD_Client>(I_C_Flatrate_RefundConfig.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_C_Flatrate_RefundConfig, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_C_Flatrate_RefundConfig, org.compiere.model.I_AD_Org>(I_C_Flatrate_RefundConfig.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Währung.
	 * Die Währung für diesen Eintrag
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Currency_ID (int C_Currency_ID);

	/**
	 * Get Währung.
	 * Die Währung für diesen Eintrag
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Currency_ID();

	public org.compiere.model.I_C_Currency getC_Currency();

	public void setC_Currency(org.compiere.model.I_C_Currency C_Currency);

    /** Column definition for C_Currency_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Flatrate_RefundConfig, org.compiere.model.I_C_Currency> COLUMN_C_Currency_ID = new org.adempiere.model.ModelColumn<I_C_Flatrate_RefundConfig, org.compiere.model.I_C_Currency>(I_C_Flatrate_RefundConfig.class, "C_Currency_ID", org.compiere.model.I_C_Currency.class);
    /** Column name C_Currency_ID */
    public static final String COLUMNNAME_C_Currency_ID = "C_Currency_ID";

	/**
	 * Set Vertragsbedingungen.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_Flatrate_Conditions_ID (int C_Flatrate_Conditions_ID);

	/**
	 * Get Vertragsbedingungen.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_Flatrate_Conditions_ID();

	public de.metas.contracts.model.I_C_Flatrate_Conditions getC_Flatrate_Conditions();

	public void setC_Flatrate_Conditions(de.metas.contracts.model.I_C_Flatrate_Conditions C_Flatrate_Conditions);

    /** Column definition for C_Flatrate_Conditions_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Flatrate_RefundConfig, de.metas.contracts.model.I_C_Flatrate_Conditions> COLUMN_C_Flatrate_Conditions_ID = new org.adempiere.model.ModelColumn<I_C_Flatrate_RefundConfig, de.metas.contracts.model.I_C_Flatrate_Conditions>(I_C_Flatrate_RefundConfig.class, "C_Flatrate_Conditions_ID", de.metas.contracts.model.I_C_Flatrate_Conditions.class);
    /** Column name C_Flatrate_Conditions_ID */
    public static final String COLUMNNAME_C_Flatrate_Conditions_ID = "C_Flatrate_Conditions_ID";

	/**
	 * Set C_Flatrate_RefundConfig.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_Flatrate_RefundConfig_ID (int C_Flatrate_RefundConfig_ID);

	/**
	 * Get C_Flatrate_RefundConfig.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_Flatrate_RefundConfig_ID();

    /** Column definition for C_Flatrate_RefundConfig_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Flatrate_RefundConfig, Object> COLUMN_C_Flatrate_RefundConfig_ID = new org.adempiere.model.ModelColumn<I_C_Flatrate_RefundConfig, Object>(I_C_Flatrate_RefundConfig.class, "C_Flatrate_RefundConfig_ID", null);
    /** Column name C_Flatrate_RefundConfig_ID */
    public static final String COLUMNNAME_C_Flatrate_RefundConfig_ID = "C_Flatrate_RefundConfig_ID";

	/**
	 * Set Terminplan Rechnung.
	 * Plan für die Rechnungsstellung
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_InvoiceSchedule_ID (int C_InvoiceSchedule_ID);

	/**
	 * Get Terminplan Rechnung.
	 * Plan für die Rechnungsstellung
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_InvoiceSchedule_ID();

	public org.compiere.model.I_C_InvoiceSchedule getC_InvoiceSchedule();

	public void setC_InvoiceSchedule(org.compiere.model.I_C_InvoiceSchedule C_InvoiceSchedule);

    /** Column definition for C_InvoiceSchedule_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Flatrate_RefundConfig, org.compiere.model.I_C_InvoiceSchedule> COLUMN_C_InvoiceSchedule_ID = new org.adempiere.model.ModelColumn<I_C_Flatrate_RefundConfig, org.compiere.model.I_C_InvoiceSchedule>(I_C_Flatrate_RefundConfig.class, "C_InvoiceSchedule_ID", org.compiere.model.I_C_InvoiceSchedule.class);
    /** Column name C_InvoiceSchedule_ID */
    public static final String COLUMNNAME_C_InvoiceSchedule_ID = "C_InvoiceSchedule_ID";

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
    public static final org.adempiere.model.ModelColumn<I_C_Flatrate_RefundConfig, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_C_Flatrate_RefundConfig, Object>(I_C_Flatrate_RefundConfig.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_Flatrate_RefundConfig, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_C_Flatrate_RefundConfig, org.compiere.model.I_AD_User>(I_C_Flatrate_RefundConfig.class, "CreatedBy", org.compiere.model.I_AD_User.class);
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
    public static final org.adempiere.model.ModelColumn<I_C_Flatrate_RefundConfig, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_C_Flatrate_RefundConfig, Object>(I_C_Flatrate_RefundConfig.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set In Roherlösberechnung.
	 * Legt fest, ob die Rückvergütungsparameter in die Berechnung des erwarteten Roherlöses (d.h. Ertrag/Marge) einfließen soll.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsUseInProfitCalculation (boolean IsUseInProfitCalculation);

	/**
	 * Get In Roherlösberechnung.
	 * Legt fest, ob die Rückvergütungsparameter in die Berechnung des erwarteten Roherlöses (d.h. Ertrag/Marge) einfließen soll.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isUseInProfitCalculation();

    /** Column definition for IsUseInProfitCalculation */
    public static final org.adempiere.model.ModelColumn<I_C_Flatrate_RefundConfig, Object> COLUMN_IsUseInProfitCalculation = new org.adempiere.model.ModelColumn<I_C_Flatrate_RefundConfig, Object>(I_C_Flatrate_RefundConfig.class, "IsUseInProfitCalculation", null);
    /** Column name IsUseInProfitCalculation */
    public static final String COLUMNNAME_IsUseInProfitCalculation = "IsUseInProfitCalculation";

	/**
	 * Set Mindestmenge.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setMinQty (java.math.BigDecimal MinQty);

	/**
	 * Get Mindestmenge.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getMinQty();

    /** Column definition for MinQty */
    public static final org.adempiere.model.ModelColumn<I_C_Flatrate_RefundConfig, Object> COLUMN_MinQty = new org.adempiere.model.ModelColumn<I_C_Flatrate_RefundConfig, Object>(I_C_Flatrate_RefundConfig.class, "MinQty", null);
    /** Column name MinQty */
    public static final String COLUMNNAME_MinQty = "MinQty";

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
    public static final org.adempiere.model.ModelColumn<I_C_Flatrate_RefundConfig, org.compiere.model.I_M_Product> COLUMN_M_Product_ID = new org.adempiere.model.ModelColumn<I_C_Flatrate_RefundConfig, org.compiere.model.I_M_Product>(I_C_Flatrate_RefundConfig.class, "M_Product_ID", org.compiere.model.I_M_Product.class);
    /** Column name M_Product_ID */
    public static final String COLUMNNAME_M_Product_ID = "M_Product_ID";

	/**
	 * Set Rückvergütungsbetrag.
	 * Rückvergütungsbetrag pro Produkt-Einheit
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setRefundAmt (java.math.BigDecimal RefundAmt);

	/**
	 * Get Rückvergütungsbetrag.
	 * Rückvergütungsbetrag pro Produkt-Einheit
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getRefundAmt();

    /** Column definition for RefundAmt */
    public static final org.adempiere.model.ModelColumn<I_C_Flatrate_RefundConfig, Object> COLUMN_RefundAmt = new org.adempiere.model.ModelColumn<I_C_Flatrate_RefundConfig, Object>(I_C_Flatrate_RefundConfig.class, "RefundAmt", null);
    /** Column name RefundAmt */
    public static final String COLUMNNAME_RefundAmt = "RefundAmt";

	/**
	 * Set Vergütung basiert auf.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setRefundBase (java.lang.String RefundBase);

	/**
	 * Get Vergütung basiert auf.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getRefundBase();

    /** Column definition for RefundBase */
    public static final org.adempiere.model.ModelColumn<I_C_Flatrate_RefundConfig, Object> COLUMN_RefundBase = new org.adempiere.model.ModelColumn<I_C_Flatrate_RefundConfig, Object>(I_C_Flatrate_RefundConfig.class, "RefundBase", null);
    /** Column name RefundBase */
    public static final String COLUMNNAME_RefundBase = "RefundBase";

	/**
	 * Set Rückvergütung per.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setRefundInvoiceType (java.lang.String RefundInvoiceType);

	/**
	 * Get Rückvergütung per.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getRefundInvoiceType();

    /** Column definition for RefundInvoiceType */
    public static final org.adempiere.model.ModelColumn<I_C_Flatrate_RefundConfig, Object> COLUMN_RefundInvoiceType = new org.adempiere.model.ModelColumn<I_C_Flatrate_RefundConfig, Object>(I_C_Flatrate_RefundConfig.class, "RefundInvoiceType", null);
    /** Column name RefundInvoiceType */
    public static final String COLUMNNAME_RefundInvoiceType = "RefundInvoiceType";

	/**
	 * Set Staffel-Modus.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setRefundMode (java.lang.String RefundMode);

	/**
	 * Get Staffel-Modus.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getRefundMode();

    /** Column definition for RefundMode */
    public static final org.adempiere.model.ModelColumn<I_C_Flatrate_RefundConfig, Object> COLUMN_RefundMode = new org.adempiere.model.ModelColumn<I_C_Flatrate_RefundConfig, Object>(I_C_Flatrate_RefundConfig.class, "RefundMode", null);
    /** Column name RefundMode */
    public static final String COLUMNNAME_RefundMode = "RefundMode";

	/**
	 * Set Rückvergütung %.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setRefundPercent (java.math.BigDecimal RefundPercent);

	/**
	 * Get Rückvergütung %.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getRefundPercent();

    /** Column definition for RefundPercent */
    public static final org.adempiere.model.ModelColumn<I_C_Flatrate_RefundConfig, Object> COLUMN_RefundPercent = new org.adempiere.model.ModelColumn<I_C_Flatrate_RefundConfig, Object>(I_C_Flatrate_RefundConfig.class, "RefundPercent", null);
    /** Column name RefundPercent */
    public static final String COLUMNNAME_RefundPercent = "RefundPercent";

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
    public static final org.adempiere.model.ModelColumn<I_C_Flatrate_RefundConfig, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_C_Flatrate_RefundConfig, Object>(I_C_Flatrate_RefundConfig.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_Flatrate_RefundConfig, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_C_Flatrate_RefundConfig, org.compiere.model.I_AD_User>(I_C_Flatrate_RefundConfig.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
