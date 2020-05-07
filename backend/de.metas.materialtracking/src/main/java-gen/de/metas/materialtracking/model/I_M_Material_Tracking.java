package de.metas.materialtracking.model;


/** Generated Interface for M_Material_Tracking
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_M_Material_Tracking 
{

    /** TableName=M_Material_Tracking */
    public static final String Table_Name = "M_Material_Tracking";

    /** AD_Table_ID=540610 */
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

    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Parzelle.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Allotment_ID (int C_Allotment_ID);

	/**
	 * Get Parzelle.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Allotment_ID();

    /** Column definition for C_Allotment_ID */
    public static final org.adempiere.model.ModelColumn<I_M_Material_Tracking, Object> COLUMN_C_Allotment_ID = new org.adempiere.model.ModelColumn<I_M_Material_Tracking, Object>(I_M_Material_Tracking.class, "C_Allotment_ID", null);
    /** Column name C_Allotment_ID */
    public static final String COLUMNNAME_C_Allotment_ID = "C_Allotment_ID";

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

    /** Column name C_BPartner_ID */
    public static final String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/**
	 * Set Pauschale - Vertragsperiode.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Flatrate_Term_ID (int C_Flatrate_Term_ID);

	/**
	 * Get Pauschale - Vertragsperiode.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Flatrate_Term_ID();

    /** Column definition for C_Flatrate_Term_ID */
    public static final org.adempiere.model.ModelColumn<I_M_Material_Tracking, Object> COLUMN_C_Flatrate_Term_ID = new org.adempiere.model.ModelColumn<I_M_Material_Tracking, Object>(I_M_Material_Tracking.class, "C_Flatrate_Term_ID", null);
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
    public static final org.adempiere.model.ModelColumn<I_M_Material_Tracking, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_M_Material_Tracking, Object>(I_M_Material_Tracking.class, "Created", null);
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

    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Beschreibung.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDescription (java.lang.String Description);

	/**
	 * Get Beschreibung.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDescription();

    /** Column definition for Description */
    public static final org.adempiere.model.ModelColumn<I_M_Material_Tracking, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<I_M_Material_Tracking, Object>(I_M_Material_Tracking.class, "Description", null);
    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

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
    public static final org.adempiere.model.ModelColumn<I_M_Material_Tracking, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_M_Material_Tracking, Object>(I_M_Material_Tracking.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Los-Nr..
	 * Los-Nummer (alphanumerisch)
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setLot (java.lang.String Lot);

	/**
	 * Get Los-Nr..
	 * Los-Nummer (alphanumerisch)
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getLot();

    /** Column definition for Lot */
    public static final org.adempiere.model.ModelColumn<I_M_Material_Tracking, Object> COLUMN_Lot = new org.adempiere.model.ModelColumn<I_M_Material_Tracking, Object>(I_M_Material_Tracking.class, "Lot", null);
    /** Column name Lot */
    public static final String COLUMNNAME_Lot = "Lot";

	/**
	 * Set Merkmals-Wert.
	 * Product Attribute Value
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_AttributeValue_ID (int M_AttributeValue_ID);

	/**
	 * Get Merkmals-Wert.
	 * Product Attribute Value
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_AttributeValue_ID();

    /** Column name M_AttributeValue_ID */
    public static final String COLUMNNAME_M_AttributeValue_ID = "M_AttributeValue_ID";

	/**
	 * Set Material-Vorgang-ID.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_Material_Tracking_ID (int M_Material_Tracking_ID);

	/**
	 * Get Material-Vorgang-ID.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_Material_Tracking_ID();

    /** Column definition for M_Material_Tracking_ID */
    public static final org.adempiere.model.ModelColumn<I_M_Material_Tracking, Object> COLUMN_M_Material_Tracking_ID = new org.adempiere.model.ModelColumn<I_M_Material_Tracking, Object>(I_M_Material_Tracking.class, "M_Material_Tracking_ID", null);
    /** Column name M_Material_Tracking_ID */
    public static final String COLUMNNAME_M_Material_Tracking_ID = "M_Material_Tracking_ID";

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

    /** Column name M_Product_ID */
    public static final String COLUMNNAME_M_Product_ID = "M_Product_ID";

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
    public static final org.adempiere.model.ModelColumn<I_M_Material_Tracking, Object> COLUMN_Processed = new org.adempiere.model.ModelColumn<I_M_Material_Tracking, Object>(I_M_Material_Tracking.class, "Processed", null);
    /** Column name Processed */
    public static final String COLUMNNAME_Processed = "Processed";

	/**
	 * Set Ausgelagerte Menge.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setQtyIssued (java.math.BigDecimal QtyIssued);

	/**
	 * Get Ausgelagerte Menge.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyIssued();

    /** Column definition for QtyIssued */
    public static final org.adempiere.model.ModelColumn<I_M_Material_Tracking, Object> COLUMN_QtyIssued = new org.adempiere.model.ModelColumn<I_M_Material_Tracking, Object>(I_M_Material_Tracking.class, "QtyIssued", null);
    /** Column name QtyIssued */
    public static final String COLUMNNAME_QtyIssued = "QtyIssued";

	/**
	 * Set Empfangene Menge.
	 * Empfangene Menge
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setQtyReceived (java.math.BigDecimal QtyReceived);

	/**
	 * Get Empfangene Menge.
	 * Empfangene Menge
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyReceived();

    /** Column definition for QtyReceived */
    public static final org.adempiere.model.ModelColumn<I_M_Material_Tracking, Object> COLUMN_QtyReceived = new org.adempiere.model.ModelColumn<I_M_Material_Tracking, Object>(I_M_Material_Tracking.class, "QtyReceived", null);
    /** Column name QtyReceived */
    public static final String COLUMNNAME_QtyReceived = "QtyReceived";

	/**
	 * Set Aussendienst.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setSalesRep_ID (int SalesRep_ID);

	/**
	 * Get Aussendienst.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getSalesRep_ID();

    /** Column name SalesRep_ID */
    public static final String COLUMNNAME_SalesRep_ID = "SalesRep_ID";

	/**
	 * Set Jahreszeit.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setSeason (java.lang.String Season);

	/**
	 * Get Jahreszeit.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getSeason();

    /** Column definition for Season */
    public static final org.adempiere.model.ModelColumn<I_M_Material_Tracking, Object> COLUMN_Season = new org.adempiere.model.ModelColumn<I_M_Material_Tracking, Object>(I_M_Material_Tracking.class, "Season", null);
    /** Column name Season */
    public static final String COLUMNNAME_Season = "Season";

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
    public static final org.adempiere.model.ModelColumn<I_M_Material_Tracking, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_M_Material_Tracking, Object>(I_M_Material_Tracking.class, "Updated", null);
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

    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/**
	 * Set Gültig ab.
	 * Gültig ab inklusiv (erster Tag)
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setValidFrom (java.sql.Timestamp ValidFrom);

	/**
	 * Get Gültig ab.
	 * Gültig ab inklusiv (erster Tag)
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getValidFrom();

    /** Column definition for ValidFrom */
    public static final org.adempiere.model.ModelColumn<I_M_Material_Tracking, Object> COLUMN_ValidFrom = new org.adempiere.model.ModelColumn<I_M_Material_Tracking, Object>(I_M_Material_Tracking.class, "ValidFrom", null);
    /** Column name ValidFrom */
    public static final String COLUMNNAME_ValidFrom = "ValidFrom";

	/**
	 * Set Gültig bis.
	 * Gültig bis inklusiv (letzter Tag)
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setValidTo (java.sql.Timestamp ValidTo);

	/**
	 * Get Gültig bis.
	 * Gültig bis inklusiv (letzter Tag)
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getValidTo();

    /** Column definition for ValidTo */
    public static final org.adempiere.model.ModelColumn<I_M_Material_Tracking, Object> COLUMN_ValidTo = new org.adempiere.model.ModelColumn<I_M_Material_Tracking, Object>(I_M_Material_Tracking.class, "ValidTo", null);
    /** Column name ValidTo */
    public static final String COLUMNNAME_ValidTo = "ValidTo";
}
