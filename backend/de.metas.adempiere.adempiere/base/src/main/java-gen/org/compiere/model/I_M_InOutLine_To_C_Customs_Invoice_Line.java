package org.compiere.model;


/** Generated Interface for M_InOutLine_To_C_Customs_Invoice_Line
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_M_InOutLine_To_C_Customs_Invoice_Line 
{

    /** TableName=M_InOutLine_To_C_Customs_Invoice_Line */
    public static final String Table_Name = "M_InOutLine_To_C_Customs_Invoice_Line";

    /** AD_Table_ID=541451 */
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
	 * Set Währung.
	 * Die Währung für diesen Eintrag
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Currency_ID (int C_Currency_ID);

	/**
	 * Get Währung.
	 * Die Währung für diesen Eintrag
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Currency_ID();

    /** Column name C_Currency_ID */
    public static final String COLUMNNAME_C_Currency_ID = "C_Currency_ID";

	/**
	 * Set Zollrechnung.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Customs_Invoice_ID (int C_Customs_Invoice_ID);

	/**
	 * Get Zollrechnung.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Customs_Invoice_ID();

	public org.compiere.model.I_C_Customs_Invoice getC_Customs_Invoice();

	public void setC_Customs_Invoice(org.compiere.model.I_C_Customs_Invoice C_Customs_Invoice);

    /** Column definition for C_Customs_Invoice_ID */
    public static final org.adempiere.model.ModelColumn<I_M_InOutLine_To_C_Customs_Invoice_Line, org.compiere.model.I_C_Customs_Invoice> COLUMN_C_Customs_Invoice_ID = new org.adempiere.model.ModelColumn<I_M_InOutLine_To_C_Customs_Invoice_Line, org.compiere.model.I_C_Customs_Invoice>(I_M_InOutLine_To_C_Customs_Invoice_Line.class, "C_Customs_Invoice_ID", org.compiere.model.I_C_Customs_Invoice.class);
    /** Column name C_Customs_Invoice_ID */
    public static final String COLUMNNAME_C_Customs_Invoice_ID = "C_Customs_Invoice_ID";

	/**
	 * Set Customs Invoice Line.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Customs_Invoice_Line_ID (int C_Customs_Invoice_Line_ID);

	/**
	 * Get Customs Invoice Line.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Customs_Invoice_Line_ID();

	public org.compiere.model.I_C_Customs_Invoice_Line getC_Customs_Invoice_Line();

	public void setC_Customs_Invoice_Line(org.compiere.model.I_C_Customs_Invoice_Line C_Customs_Invoice_Line);

    /** Column definition for C_Customs_Invoice_Line_ID */
    public static final org.adempiere.model.ModelColumn<I_M_InOutLine_To_C_Customs_Invoice_Line, org.compiere.model.I_C_Customs_Invoice_Line> COLUMN_C_Customs_Invoice_Line_ID = new org.adempiere.model.ModelColumn<I_M_InOutLine_To_C_Customs_Invoice_Line, org.compiere.model.I_C_Customs_Invoice_Line>(I_M_InOutLine_To_C_Customs_Invoice_Line.class, "C_Customs_Invoice_Line_ID", org.compiere.model.I_C_Customs_Invoice_Line.class);
    /** Column name C_Customs_Invoice_Line_ID */
    public static final String COLUMNNAME_C_Customs_Invoice_Line_ID = "C_Customs_Invoice_Line_ID";

	/**
	 * Set Maßeinheit.
	 * Maßeinheit
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_UOM_ID (int C_UOM_ID);

	/**
	 * Get Maßeinheit.
	 * Maßeinheit
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_UOM_ID();

    /** Column name C_UOM_ID */
    public static final String COLUMNNAME_C_UOM_ID = "C_UOM_ID";

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
    public static final org.adempiere.model.ModelColumn<I_M_InOutLine_To_C_Customs_Invoice_Line, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_M_InOutLine_To_C_Customs_Invoice_Line, Object>(I_M_InOutLine_To_C_Customs_Invoice_Line.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_M_InOutLine_To_C_Customs_Invoice_Line, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_M_InOutLine_To_C_Customs_Invoice_Line, Object>(I_M_InOutLine_To_C_Customs_Invoice_Line.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Lieferung/Wareneingang.
	 * Material Shipment Document
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_InOut_ID (int M_InOut_ID);

	/**
	 * Get Lieferung/Wareneingang.
	 * Material Shipment Document
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_InOut_ID();

	public org.compiere.model.I_M_InOut getM_InOut();

	public void setM_InOut(org.compiere.model.I_M_InOut M_InOut);

    /** Column definition for M_InOut_ID */
    public static final org.adempiere.model.ModelColumn<I_M_InOutLine_To_C_Customs_Invoice_Line, org.compiere.model.I_M_InOut> COLUMN_M_InOut_ID = new org.adempiere.model.ModelColumn<I_M_InOutLine_To_C_Customs_Invoice_Line, org.compiere.model.I_M_InOut>(I_M_InOutLine_To_C_Customs_Invoice_Line.class, "M_InOut_ID", org.compiere.model.I_M_InOut.class);
    /** Column name M_InOut_ID */
    public static final String COLUMNNAME_M_InOut_ID = "M_InOut_ID";

	/**
	 * Set Versand-/Wareneingangsposition.
	 * Position auf Versand- oder Wareneingangsbeleg
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_InOutLine_ID (int M_InOutLine_ID);

	/**
	 * Get Versand-/Wareneingangsposition.
	 * Position auf Versand- oder Wareneingangsbeleg
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_InOutLine_ID();

	public org.compiere.model.I_M_InOutLine getM_InOutLine();

	public void setM_InOutLine(org.compiere.model.I_M_InOutLine M_InOutLine);

    /** Column definition for M_InOutLine_ID */
    public static final org.adempiere.model.ModelColumn<I_M_InOutLine_To_C_Customs_Invoice_Line, org.compiere.model.I_M_InOutLine> COLUMN_M_InOutLine_ID = new org.adempiere.model.ModelColumn<I_M_InOutLine_To_C_Customs_Invoice_Line, org.compiere.model.I_M_InOutLine>(I_M_InOutLine_To_C_Customs_Invoice_Line.class, "M_InOutLine_ID", org.compiere.model.I_M_InOutLine.class);
    /** Column name M_InOutLine_ID */
    public static final String COLUMNNAME_M_InOutLine_ID = "M_InOutLine_ID";

	/**
	 * Set M_InOutLine_To_C_Customs_Invoice_Line.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_InOutLine_To_C_Customs_Invoice_Line_ID (int M_InOutLine_To_C_Customs_Invoice_Line_ID);

	/**
	 * Get M_InOutLine_To_C_Customs_Invoice_Line.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_InOutLine_To_C_Customs_Invoice_Line_ID();

    /** Column definition for M_InOutLine_To_C_Customs_Invoice_Line_ID */
    public static final org.adempiere.model.ModelColumn<I_M_InOutLine_To_C_Customs_Invoice_Line, Object> COLUMN_M_InOutLine_To_C_Customs_Invoice_Line_ID = new org.adempiere.model.ModelColumn<I_M_InOutLine_To_C_Customs_Invoice_Line, Object>(I_M_InOutLine_To_C_Customs_Invoice_Line.class, "M_InOutLine_To_C_Customs_Invoice_Line_ID", null);
    /** Column name M_InOutLine_To_C_Customs_Invoice_Line_ID */
    public static final String COLUMNNAME_M_InOutLine_To_C_Customs_Invoice_Line_ID = "M_InOutLine_To_C_Customs_Invoice_Line_ID";

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

    /** Column name M_Product_ID */
    public static final String COLUMNNAME_M_Product_ID = "M_Product_ID";

	/**
	 * Set Bewegungs-Menge.
	 * Menge eines bewegten Produktes.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setMovementQty (java.math.BigDecimal MovementQty);

	/**
	 * Get Bewegungs-Menge.
	 * Menge eines bewegten Produktes.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getMovementQty();

    /** Column definition for MovementQty */
    public static final org.adempiere.model.ModelColumn<I_M_InOutLine_To_C_Customs_Invoice_Line, Object> COLUMN_MovementQty = new org.adempiere.model.ModelColumn<I_M_InOutLine_To_C_Customs_Invoice_Line, Object>(I_M_InOutLine_To_C_Customs_Invoice_Line.class, "MovementQty", null);
    /** Column name MovementQty */
    public static final String COLUMNNAME_MovementQty = "MovementQty";

	/**
	 * Set Einzelpreis.
	 * Effektiver Preis
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPriceActual (java.math.BigDecimal PriceActual);

	/**
	 * Get Einzelpreis.
	 * Effektiver Preis
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getPriceActual();

    /** Column definition for PriceActual */
    public static final org.adempiere.model.ModelColumn<I_M_InOutLine_To_C_Customs_Invoice_Line, Object> COLUMN_PriceActual = new org.adempiere.model.ModelColumn<I_M_InOutLine_To_C_Customs_Invoice_Line, Object>(I_M_InOutLine_To_C_Customs_Invoice_Line.class, "PriceActual", null);
    /** Column name PriceActual */
    public static final String COLUMNNAME_PriceActual = "PriceActual";

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
    public static final org.adempiere.model.ModelColumn<I_M_InOutLine_To_C_Customs_Invoice_Line, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_M_InOutLine_To_C_Customs_Invoice_Line, Object>(I_M_InOutLine_To_C_Customs_Invoice_Line.class, "Updated", null);
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
}
