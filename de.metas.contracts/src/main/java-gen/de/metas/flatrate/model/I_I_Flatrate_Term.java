package de.metas.flatrate.model;


/** Generated Interface for I_Flatrate_Term
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_I_Flatrate_Term 
{

    /** TableName=I_Flatrate_Term */
    public static final String Table_Name = "I_Flatrate_Term";

    /** AD_Table_ID=540836 */
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
    public static final org.adempiere.model.ModelColumn<I_I_Flatrate_Term, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_I_Flatrate_Term, org.compiere.model.I_AD_Client>(I_I_Flatrate_Term.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_I_Flatrate_Term, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_I_Flatrate_Term, org.compiere.model.I_AD_Org>(I_I_Flatrate_Term.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Geschäftspartner-Schlüssel.
	 * Key of the Business Partner
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setBPartnerValue (java.lang.String BPartnerValue);

	/**
	 * Get Geschäftspartner-Schlüssel.
	 * Key of the Business Partner
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getBPartnerValue();

    /** Column definition for BPartnerValue */
    public static final org.adempiere.model.ModelColumn<I_I_Flatrate_Term, Object> COLUMN_BPartnerValue = new org.adempiere.model.ModelColumn<I_I_Flatrate_Term, Object>(I_I_Flatrate_Term.class, "BPartnerValue", null);
    /** Column name BPartnerValue */
    public static final String COLUMNNAME_BPartnerValue = "BPartnerValue";

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
    public static final org.adempiere.model.ModelColumn<I_I_Flatrate_Term, org.compiere.model.I_C_BPartner> COLUMN_C_BPartner_ID = new org.adempiere.model.ModelColumn<I_I_Flatrate_Term, org.compiere.model.I_C_BPartner>(I_I_Flatrate_Term.class, "C_BPartner_ID", org.compiere.model.I_C_BPartner.class);
    /** Column name C_BPartner_ID */
    public static final String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/**
	 * Set Vertragsbedingungen.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Flatrate_Conditions_ID (int C_Flatrate_Conditions_ID);

	/**
	 * Get Vertragsbedingungen.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Flatrate_Conditions_ID();

	public de.metas.flatrate.model.I_C_Flatrate_Conditions getC_Flatrate_Conditions();

	public void setC_Flatrate_Conditions(de.metas.flatrate.model.I_C_Flatrate_Conditions C_Flatrate_Conditions);

    /** Column definition for C_Flatrate_Conditions_ID */
    public static final org.adempiere.model.ModelColumn<I_I_Flatrate_Term, de.metas.flatrate.model.I_C_Flatrate_Conditions> COLUMN_C_Flatrate_Conditions_ID = new org.adempiere.model.ModelColumn<I_I_Flatrate_Term, de.metas.flatrate.model.I_C_Flatrate_Conditions>(I_I_Flatrate_Term.class, "C_Flatrate_Conditions_ID", de.metas.flatrate.model.I_C_Flatrate_Conditions.class);
    /** Column name C_Flatrate_Conditions_ID */
    public static final String COLUMNNAME_C_Flatrate_Conditions_ID = "C_Flatrate_Conditions_ID";

	/**
	 * Set Vertragsbedingungen Key.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Flatrate_Conditions_Value (java.lang.String C_Flatrate_Conditions_Value);

	/**
	 * Get Vertragsbedingungen Key.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getC_Flatrate_Conditions_Value();

    /** Column definition for C_Flatrate_Conditions_Value */
    public static final org.adempiere.model.ModelColumn<I_I_Flatrate_Term, Object> COLUMN_C_Flatrate_Conditions_Value = new org.adempiere.model.ModelColumn<I_I_Flatrate_Term, Object>(I_I_Flatrate_Term.class, "C_Flatrate_Conditions_Value", null);
    /** Column name C_Flatrate_Conditions_Value */
    public static final String COLUMNNAME_C_Flatrate_Conditions_Value = "C_Flatrate_Conditions_Value";

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

	public de.metas.flatrate.model.I_C_Flatrate_Term getC_Flatrate_Term();

	public void setC_Flatrate_Term(de.metas.flatrate.model.I_C_Flatrate_Term C_Flatrate_Term);

    /** Column definition for C_Flatrate_Term_ID */
    public static final org.adempiere.model.ModelColumn<I_I_Flatrate_Term, de.metas.flatrate.model.I_C_Flatrate_Term> COLUMN_C_Flatrate_Term_ID = new org.adempiere.model.ModelColumn<I_I_Flatrate_Term, de.metas.flatrate.model.I_C_Flatrate_Term>(I_I_Flatrate_Term.class, "C_Flatrate_Term_ID", de.metas.flatrate.model.I_C_Flatrate_Term.class);
    /** Column name C_Flatrate_Term_ID */
    public static final String COLUMNNAME_C_Flatrate_Term_ID = "C_Flatrate_Term_ID";

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
    public static final org.adempiere.model.ModelColumn<I_I_Flatrate_Term, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_I_Flatrate_Term, Object>(I_I_Flatrate_Term.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_I_Flatrate_Term, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_I_Flatrate_Term, org.compiere.model.I_AD_User>(I_I_Flatrate_Term.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Lieferempfänger.
	 * Business Partner to ship to
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDropShip_BPartner_ID (int DropShip_BPartner_ID);

	/**
	 * Get Lieferempfänger.
	 * Business Partner to ship to
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getDropShip_BPartner_ID();

	public org.compiere.model.I_C_BPartner getDropShip_BPartner();

	public void setDropShip_BPartner(org.compiere.model.I_C_BPartner DropShip_BPartner);

    /** Column definition for DropShip_BPartner_ID */
    public static final org.adempiere.model.ModelColumn<I_I_Flatrate_Term, org.compiere.model.I_C_BPartner> COLUMN_DropShip_BPartner_ID = new org.adempiere.model.ModelColumn<I_I_Flatrate_Term, org.compiere.model.I_C_BPartner>(I_I_Flatrate_Term.class, "DropShip_BPartner_ID", org.compiere.model.I_C_BPartner.class);
    /** Column name DropShip_BPartner_ID */
    public static final String COLUMNNAME_DropShip_BPartner_ID = "DropShip_BPartner_ID";

	/**
	 * Set Lieferempfänger Key.
	 * Business Partner to ship to
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDropShip_BPartner_Value (java.lang.String DropShip_BPartner_Value);

	/**
	 * Get Lieferempfänger Key.
	 * Business Partner to ship to
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDropShip_BPartner_Value();

    /** Column definition for DropShip_BPartner_Value */
    public static final org.adempiere.model.ModelColumn<I_I_Flatrate_Term, Object> COLUMN_DropShip_BPartner_Value = new org.adempiere.model.ModelColumn<I_I_Flatrate_Term, Object>(I_I_Flatrate_Term.class, "DropShip_BPartner_Value", null);
    /** Column name DropShip_BPartner_Value */
    public static final String COLUMNNAME_DropShip_BPartner_Value = "DropShip_BPartner_Value";

	/**
	 * Set Enddatum.
	 * Last effective date (inclusive)
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setEndDate (java.sql.Timestamp EndDate);

	/**
	 * Get Enddatum.
	 * Last effective date (inclusive)
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getEndDate();

    /** Column definition for EndDate */
    public static final org.adempiere.model.ModelColumn<I_I_Flatrate_Term, Object> COLUMN_EndDate = new org.adempiere.model.ModelColumn<I_I_Flatrate_Term, Object>(I_I_Flatrate_Term.class, "EndDate", null);
    /** Column name EndDate */
    public static final String COLUMNNAME_EndDate = "EndDate";

	/**
	 * Set Import-Fehlermeldung.
	 * Meldungen, die durch den Importprozess generiert wurden
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setI_ErrorMsg (java.lang.String I_ErrorMsg);

	/**
	 * Get Import-Fehlermeldung.
	 * Meldungen, die durch den Importprozess generiert wurden
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getI_ErrorMsg();

    /** Column definition for I_ErrorMsg */
    public static final org.adempiere.model.ModelColumn<I_I_Flatrate_Term, Object> COLUMN_I_ErrorMsg = new org.adempiere.model.ModelColumn<I_I_Flatrate_Term, Object>(I_I_Flatrate_Term.class, "I_ErrorMsg", null);
    /** Column name I_ErrorMsg */
    public static final String COLUMNNAME_I_ErrorMsg = "I_ErrorMsg";

	/**
	 * Set Contract import.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setI_Flatrate_Term_ID (int I_Flatrate_Term_ID);

	/**
	 * Get Contract import.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getI_Flatrate_Term_ID();

    /** Column definition for I_Flatrate_Term_ID */
    public static final org.adempiere.model.ModelColumn<I_I_Flatrate_Term, Object> COLUMN_I_Flatrate_Term_ID = new org.adempiere.model.ModelColumn<I_I_Flatrate_Term, Object>(I_I_Flatrate_Term.class, "I_Flatrate_Term_ID", null);
    /** Column name I_Flatrate_Term_ID */
    public static final String COLUMNNAME_I_Flatrate_Term_ID = "I_Flatrate_Term_ID";

	/**
	 * Set Importiert.
	 * Ist dieser Import verarbeitet worden?
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setI_IsImported (java.lang.String I_IsImported);

	/**
	 * Get Importiert.
	 * Ist dieser Import verarbeitet worden?
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getI_IsImported();

    /** Column definition for I_IsImported */
    public static final org.adempiere.model.ModelColumn<I_I_Flatrate_Term, Object> COLUMN_I_IsImported = new org.adempiere.model.ModelColumn<I_I_Flatrate_Term, Object>(I_I_Flatrate_Term.class, "I_IsImported", null);
    /** Column name I_IsImported */
    public static final String COLUMNNAME_I_IsImported = "I_IsImported";

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
    public static final org.adempiere.model.ModelColumn<I_I_Flatrate_Term, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_I_Flatrate_Term, Object>(I_I_Flatrate_Term.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Produkt.
	 * Produkt, Leistung, Artikel
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_Product_ID (int M_Product_ID);

	/**
	 * Get Produkt.
	 * Produkt, Leistung, Artikel
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_Product_ID();

	public org.compiere.model.I_M_Product getM_Product();

	public void setM_Product(org.compiere.model.I_M_Product M_Product);

    /** Column definition for M_Product_ID */
    public static final org.adempiere.model.ModelColumn<I_I_Flatrate_Term, org.compiere.model.I_M_Product> COLUMN_M_Product_ID = new org.adempiere.model.ModelColumn<I_I_Flatrate_Term, org.compiere.model.I_M_Product>(I_I_Flatrate_Term.class, "M_Product_ID", org.compiere.model.I_M_Product.class);
    /** Column name M_Product_ID */
    public static final String COLUMNNAME_M_Product_ID = "M_Product_ID";

	/**
	 * Set Preis.
	 * Preis
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPrice (java.math.BigDecimal Price);

	/**
	 * Get Preis.
	 * Preis
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getPrice();

    /** Column definition for Price */
    public static final org.adempiere.model.ModelColumn<I_I_Flatrate_Term, Object> COLUMN_Price = new org.adempiere.model.ModelColumn<I_I_Flatrate_Term, Object>(I_I_Flatrate_Term.class, "Price", null);
    /** Column name Price */
    public static final String COLUMNNAME_Price = "Price";

	/**
	 * Set Verarbeitet.
	 * Checkbox sagt aus, ob der Beleg verarbeitet wurde.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setProcessed (boolean Processed);

	/**
	 * Get Verarbeitet.
	 * Checkbox sagt aus, ob der Beleg verarbeitet wurde.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isProcessed();

    /** Column definition for Processed */
    public static final org.adempiere.model.ModelColumn<I_I_Flatrate_Term, Object> COLUMN_Processed = new org.adempiere.model.ModelColumn<I_I_Flatrate_Term, Object>(I_I_Flatrate_Term.class, "Processed", null);
    /** Column name Processed */
    public static final String COLUMNNAME_Processed = "Processed";

	/**
	 * Set Produktschlüssel.
	 * Schlüssel des Produktes
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setProductValue (java.lang.String ProductValue);

	/**
	 * Get Produktschlüssel.
	 * Schlüssel des Produktes
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getProductValue();

    /** Column definition for ProductValue */
    public static final org.adempiere.model.ModelColumn<I_I_Flatrate_Term, Object> COLUMN_ProductValue = new org.adempiere.model.ModelColumn<I_I_Flatrate_Term, Object>(I_I_Flatrate_Term.class, "ProductValue", null);
    /** Column name ProductValue */
    public static final String COLUMNNAME_ProductValue = "ProductValue";

	/**
	 * Set Anfangsdatum.
	 * First effective day (inclusive)
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setStartDate (java.sql.Timestamp StartDate);

	/**
	 * Get Anfangsdatum.
	 * First effective day (inclusive)
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getStartDate();

    /** Column definition for StartDate */
    public static final org.adempiere.model.ModelColumn<I_I_Flatrate_Term, Object> COLUMN_StartDate = new org.adempiere.model.ModelColumn<I_I_Flatrate_Term, Object>(I_I_Flatrate_Term.class, "StartDate", null);
    /** Column name StartDate */
    public static final String COLUMNNAME_StartDate = "StartDate";

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
    public static final org.adempiere.model.ModelColumn<I_I_Flatrate_Term, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_I_Flatrate_Term, Object>(I_I_Flatrate_Term.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_I_Flatrate_Term, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_I_Flatrate_Term, org.compiere.model.I_AD_User>(I_I_Flatrate_Term.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
