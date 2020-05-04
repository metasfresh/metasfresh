package org.compiere.model;


/** Generated Interface for C_Customs_Invoice_Line
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_C_Customs_Invoice_Line 
{

    /** TableName=C_Customs_Invoice_Line */
    public static final String Table_Name = "C_Customs_Invoice_Line";

    /** AD_Table_ID=541361 */
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
    public static final org.adempiere.model.ModelColumn<I_C_Customs_Invoice_Line, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_C_Customs_Invoice_Line, org.compiere.model.I_AD_Client>(I_C_Customs_Invoice_Line.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_C_Customs_Invoice_Line, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_C_Customs_Invoice_Line, org.compiere.model.I_AD_Org>(I_C_Customs_Invoice_Line.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Customs Invoice.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Customs_Invoice_ID (int C_Customs_Invoice_ID);

	/**
	 * Get Customs Invoice.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Customs_Invoice_ID();

	public org.compiere.model.I_C_Customs_Invoice getC_Customs_Invoice();

	public void setC_Customs_Invoice(org.compiere.model.I_C_Customs_Invoice C_Customs_Invoice);

    /** Column definition for C_Customs_Invoice_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Customs_Invoice_Line, org.compiere.model.I_C_Customs_Invoice> COLUMN_C_Customs_Invoice_ID = new org.adempiere.model.ModelColumn<I_C_Customs_Invoice_Line, org.compiere.model.I_C_Customs_Invoice>(I_C_Customs_Invoice_Line.class, "C_Customs_Invoice_ID", org.compiere.model.I_C_Customs_Invoice.class);
    /** Column name C_Customs_Invoice_ID */
    public static final String COLUMNNAME_C_Customs_Invoice_ID = "C_Customs_Invoice_ID";

	/**
	 * Set Customs Invoice Line.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_Customs_Invoice_Line_ID (int C_Customs_Invoice_Line_ID);

	/**
	 * Get Customs Invoice Line.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_Customs_Invoice_Line_ID();

    /** Column definition for C_Customs_Invoice_Line_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Customs_Invoice_Line, Object> COLUMN_C_Customs_Invoice_Line_ID = new org.adempiere.model.ModelColumn<I_C_Customs_Invoice_Line, Object>(I_C_Customs_Invoice_Line.class, "C_Customs_Invoice_Line_ID", null);
    /** Column name C_Customs_Invoice_Line_ID */
    public static final String COLUMNNAME_C_Customs_Invoice_Line_ID = "C_Customs_Invoice_Line_ID";

	/**
	 * Set Maßeinheit.
	 * Maßeinheit
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_UOM_ID (int C_UOM_ID);

	/**
	 * Get Maßeinheit.
	 * Maßeinheit
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_UOM_ID();

	public org.compiere.model.I_C_UOM getC_UOM();

	public void setC_UOM(org.compiere.model.I_C_UOM C_UOM);

    /** Column definition for C_UOM_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Customs_Invoice_Line, org.compiere.model.I_C_UOM> COLUMN_C_UOM_ID = new org.adempiere.model.ModelColumn<I_C_Customs_Invoice_Line, org.compiere.model.I_C_UOM>(I_C_Customs_Invoice_Line.class, "C_UOM_ID", org.compiere.model.I_C_UOM.class);
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
    public static final org.adempiere.model.ModelColumn<I_C_Customs_Invoice_Line, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_C_Customs_Invoice_Line, Object>(I_C_Customs_Invoice_Line.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_Customs_Invoice_Line, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_C_Customs_Invoice_Line, org.compiere.model.I_AD_User>(I_C_Customs_Invoice_Line.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Berechnete Menge.
	 * The quantity invoiced
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setInvoicedQty (java.math.BigDecimal InvoicedQty);

	/**
	 * Get Berechnete Menge.
	 * The quantity invoiced
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getInvoicedQty();

    /** Column definition for InvoicedQty */
    public static final org.adempiere.model.ModelColumn<I_C_Customs_Invoice_Line, Object> COLUMN_InvoicedQty = new org.adempiere.model.ModelColumn<I_C_Customs_Invoice_Line, Object>(I_C_Customs_Invoice_Line.class, "InvoicedQty", null);
    /** Column name InvoicedQty */
    public static final String COLUMNNAME_InvoicedQty = "InvoicedQty";

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
    public static final org.adempiere.model.ModelColumn<I_C_Customs_Invoice_Line, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_C_Customs_Invoice_Line, Object>(I_C_Customs_Invoice_Line.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Zeilennetto.
	 * Nettowert Zeile (Menge * Einzelpreis) ohne Fracht und Gebühren
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setLineNetAmt (java.math.BigDecimal LineNetAmt);

	/**
	 * Get Zeilennetto.
	 * Nettowert Zeile (Menge * Einzelpreis) ohne Fracht und Gebühren
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getLineNetAmt();

    /** Column definition for LineNetAmt */
    public static final org.adempiere.model.ModelColumn<I_C_Customs_Invoice_Line, Object> COLUMN_LineNetAmt = new org.adempiere.model.ModelColumn<I_C_Customs_Invoice_Line, Object>(I_C_Customs_Invoice_Line.class, "LineNetAmt", null);
    /** Column name LineNetAmt */
    public static final String COLUMNNAME_LineNetAmt = "LineNetAmt";

	/**
	 * Set Position.
	 * Zeile Nr.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setLineNo (int LineNo);

	/**
	 * Get Position.
	 * Zeile Nr.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getLineNo();

    /** Column definition for LineNo */
    public static final org.adempiere.model.ModelColumn<I_C_Customs_Invoice_Line, Object> COLUMN_LineNo = new org.adempiere.model.ModelColumn<I_C_Customs_Invoice_Line, Object>(I_C_Customs_Invoice_Line.class, "LineNo", null);
    /** Column name LineNo */
    public static final String COLUMNNAME_LineNo = "LineNo";

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
    public static final org.adempiere.model.ModelColumn<I_C_Customs_Invoice_Line, org.compiere.model.I_M_Product> COLUMN_M_Product_ID = new org.adempiere.model.ModelColumn<I_C_Customs_Invoice_Line, org.compiere.model.I_M_Product>(I_C_Customs_Invoice_Line.class, "M_Product_ID", org.compiere.model.I_M_Product.class);
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
    public static final org.adempiere.model.ModelColumn<I_C_Customs_Invoice_Line, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_C_Customs_Invoice_Line, Object>(I_C_Customs_Invoice_Line.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_Customs_Invoice_Line, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_C_Customs_Invoice_Line, org.compiere.model.I_AD_User>(I_C_Customs_Invoice_Line.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
