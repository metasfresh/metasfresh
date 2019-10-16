package de.metas.esb.edi.model;


/** Generated Interface for EDI_cctop_invoic_500_v
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_EDI_cctop_invoic_500_v 
{

    /** TableName=EDI_cctop_invoic_500_v */
    public static final String Table_Name = "EDI_cctop_invoic_500_v";

    /** AD_Table_ID=540463 */
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

    /** Column definition for AD_Client_ID */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_500_v, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_500_v, org.compiere.model.I_AD_Client>(I_EDI_cctop_invoic_500_v.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Organisation.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Organisation.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Org_ID();

    /** Column definition for AD_Org_ID */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_500_v, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_500_v, org.compiere.model.I_AD_Org>(I_EDI_cctop_invoic_500_v.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Invoice.
	 * Invoice Identifier
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Invoice_ID (int C_Invoice_ID);

	/**
	 * Get Invoice.
	 * Invoice Identifier
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Invoice_ID();

	public org.compiere.model.I_C_Invoice getC_Invoice();

	public void setC_Invoice(org.compiere.model.I_C_Invoice C_Invoice);

    /** Column definition for C_Invoice_ID */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_500_v, org.compiere.model.I_C_Invoice> COLUMN_C_Invoice_ID = new org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_500_v, org.compiere.model.I_C_Invoice>(I_EDI_cctop_invoic_500_v.class, "C_Invoice_ID", org.compiere.model.I_C_Invoice.class);
    /** Column name C_Invoice_ID */
    public static final String COLUMNNAME_C_Invoice_ID = "C_Invoice_ID";

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
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_500_v, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_500_v, Object>(I_EDI_cctop_invoic_500_v.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_500_v, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_500_v, org.compiere.model.I_AD_User>(I_EDI_cctop_invoic_500_v.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set EanCom_Price_UOM.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setEanCom_Price_UOM (java.lang.String EanCom_Price_UOM);

	/**
	 * Get EanCom_Price_UOM.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getEanCom_Price_UOM();

    /** Column definition for EanCom_Price_UOM */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_500_v, Object> COLUMN_EanCom_Price_UOM = new org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_500_v, Object>(I_EDI_cctop_invoic_500_v.class, "EanCom_Price_UOM", null);
    /** Column name EanCom_Price_UOM */
    public static final String COLUMNNAME_EanCom_Price_UOM = "EanCom_Price_UOM";

	/**
	 * Set eancom_uom.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void seteancom_uom (java.lang.String eancom_uom);

	/**
	 * Get eancom_uom.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String geteancom_uom();

    /** Column definition for eancom_uom */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_500_v, Object> COLUMN_eancom_uom = new org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_500_v, Object>(I_EDI_cctop_invoic_500_v.class, "eancom_uom", null);
    /** Column name eancom_uom */
    public static final String COLUMNNAME_eancom_uom = "eancom_uom";

	/**
	 * Set EDI_cctop_invoic_500_v.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setEDI_cctop_invoic_500_v_ID (int EDI_cctop_invoic_500_v_ID);

	/**
	 * Get EDI_cctop_invoic_500_v.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getEDI_cctop_invoic_500_v_ID();

    /** Column definition for EDI_cctop_invoic_500_v_ID */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_500_v, Object> COLUMN_EDI_cctop_invoic_500_v_ID = new org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_500_v, Object>(I_EDI_cctop_invoic_500_v.class, "EDI_cctop_invoic_500_v_ID", null);
    /** Column name EDI_cctop_invoic_500_v_ID */
    public static final String COLUMNNAME_EDI_cctop_invoic_500_v_ID = "EDI_cctop_invoic_500_v_ID";

	/**
	 * Set EDI_cctop_invoic_v.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setEDI_cctop_invoic_v_ID (int EDI_cctop_invoic_v_ID);

	/**
	 * Get EDI_cctop_invoic_v.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getEDI_cctop_invoic_v_ID();

	public de.metas.esb.edi.model.I_EDI_cctop_invoic_v getEDI_cctop_invoic_v();

	public void setEDI_cctop_invoic_v(de.metas.esb.edi.model.I_EDI_cctop_invoic_v EDI_cctop_invoic_v);

    /** Column definition for EDI_cctop_invoic_v_ID */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_500_v, de.metas.esb.edi.model.I_EDI_cctop_invoic_v> COLUMN_EDI_cctop_invoic_v_ID = new org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_500_v, de.metas.esb.edi.model.I_EDI_cctop_invoic_v>(I_EDI_cctop_invoic_500_v.class, "EDI_cctop_invoic_v_ID", de.metas.esb.edi.model.I_EDI_cctop_invoic_v.class);
    /** Column name EDI_cctop_invoic_v_ID */
    public static final String COLUMNNAME_EDI_cctop_invoic_v_ID = "EDI_cctop_invoic_v_ID";

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
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_500_v, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_500_v, Object>(I_EDI_cctop_invoic_500_v.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set ISO Währungscode.
	 * Dreibuchstabiger ISO 4217 Code für die Währung
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setISO_Code (java.lang.String ISO_Code);

	/**
	 * Get ISO Währungscode.
	 * Dreibuchstabiger ISO 4217 Code für die Währung
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getISO_Code();

    /** Column definition for ISO_Code */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_500_v, Object> COLUMN_ISO_Code = new org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_500_v, Object>(I_EDI_cctop_invoic_500_v.class, "ISO_Code", null);
    /** Column name ISO_Code */
    public static final String COLUMNNAME_ISO_Code = "ISO_Code";

	/**
	 * Set Leergut.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setLeergut (java.lang.String Leergut);

	/**
	 * Get Leergut.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getLeergut();

    /** Column definition for Leergut */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_500_v, Object> COLUMN_Leergut = new org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_500_v, Object>(I_EDI_cctop_invoic_500_v.class, "Leergut", null);
    /** Column name Leergut */
    public static final String COLUMNNAME_Leergut = "Leergut";

	/**
	 * Set Zeile Nr..
	 * Einzelne Zeile in dem Dokument
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setLine (int Line);

	/**
	 * Get Zeile Nr..
	 * Einzelne Zeile in dem Dokument
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getLine();

    /** Column definition for Line */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_500_v, Object> COLUMN_Line = new org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_500_v, Object>(I_EDI_cctop_invoic_500_v.class, "Line", null);
    /** Column name Line */
    public static final String COLUMNNAME_Line = "Line";

	/**
	 * Set Zeilennetto.
	 * Nettowert Zeile (Menge * Einzelpreis) ohne Fracht und Gebühren
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setLineNetAmt (java.math.BigDecimal LineNetAmt);

	/**
	 * Get Zeilennetto.
	 * Nettowert Zeile (Menge * Einzelpreis) ohne Fracht und Gebühren
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getLineNetAmt();

    /** Column definition for LineNetAmt */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_500_v, Object> COLUMN_LineNetAmt = new org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_500_v, Object>(I_EDI_cctop_invoic_500_v.class, "LineNetAmt", null);
    /** Column name LineNetAmt */
    public static final String COLUMNNAME_LineNetAmt = "LineNetAmt";

	/**
	 * Set Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setName (java.lang.String Name);

	/**
	 * Get Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getName();

    /** Column definition for Name */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_500_v, Object> COLUMN_Name = new org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_500_v, Object>(I_EDI_cctop_invoic_500_v.class, "Name", null);
    /** Column name Name */
    public static final String COLUMNNAME_Name = "Name";

	/**
	 * Set Name Zusatz.
	 * Zusätzliche Bezeichnung
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setName2 (java.lang.String Name2);

	/**
	 * Get Name Zusatz.
	 * Zusätzliche Bezeichnung
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getName2();

    /** Column definition for Name2 */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_500_v, Object> COLUMN_Name2 = new org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_500_v, Object>(I_EDI_cctop_invoic_500_v.class, "Name2", null);
    /** Column name Name2 */
    public static final String COLUMNNAME_Name2 = "Name2";

	/**
	 * Set Order Line.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setOrderLine (int OrderLine);

	/**
	 * Get Order Line.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getOrderLine();

    /** Column definition for OrderLine */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_500_v, Object> COLUMN_OrderLine = new org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_500_v, Object>(I_EDI_cctop_invoic_500_v.class, "OrderLine", null);
    /** Column name OrderLine */
    public static final String COLUMNNAME_OrderLine = "OrderLine";

	/**
	 * Set Auftragsreferenz.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setOrderPOReference (java.lang.String OrderPOReference);

	/**
	 * Get Auftragsreferenz.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getOrderPOReference();

    /** Column definition for OrderPOReference */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_500_v, Object> COLUMN_OrderPOReference = new org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_500_v, Object>(I_EDI_cctop_invoic_500_v.class, "OrderPOReference", null);
    /** Column name OrderPOReference */
    public static final String COLUMNNAME_OrderPOReference = "OrderPOReference";

	/**
	 * Set Einzelpreis.
	 * Effektiver Preis
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPriceActual (java.math.BigDecimal PriceActual);

	/**
	 * Get Einzelpreis.
	 * Effektiver Preis
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getPriceActual();

    /** Column definition for PriceActual */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_500_v, Object> COLUMN_PriceActual = new org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_500_v, Object>(I_EDI_cctop_invoic_500_v.class, "PriceActual", null);
    /** Column name PriceActual */
    public static final String COLUMNNAME_PriceActual = "PriceActual";

	/**
	 * Set Auszeichnungspreis.
	 * Auszeichnungspreis
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPriceList (java.math.BigDecimal PriceList);

	/**
	 * Get Auszeichnungspreis.
	 * Auszeichnungspreis
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getPriceList();

    /** Column definition for PriceList */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_500_v, Object> COLUMN_PriceList = new org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_500_v, Object>(I_EDI_cctop_invoic_500_v.class, "PriceList", null);
    /** Column name PriceList */
    public static final String COLUMNNAME_PriceList = "PriceList";

	/**
	 * Set Produktbeschreibung.
	 * Produktbeschreibung
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setProductDescription (java.lang.String ProductDescription);

	/**
	 * Get Produktbeschreibung.
	 * Produktbeschreibung
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getProductDescription();

    /** Column definition for ProductDescription */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_500_v, Object> COLUMN_ProductDescription = new org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_500_v, Object>(I_EDI_cctop_invoic_500_v.class, "ProductDescription", null);
    /** Column name ProductDescription */
    public static final String COLUMNNAME_ProductDescription = "ProductDescription";

	/**
	 * Set Berechn. Menge.
	 * Menge in Produkt-Maßeinheit, die bereits in Rechnung gestellt wurde.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setQtyInvoiced (java.math.BigDecimal QtyInvoiced);

	/**
	 * Get Berechn. Menge.
	 * Menge in Produkt-Maßeinheit, die bereits in Rechnung gestellt wurde.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyInvoiced();

    /** Column definition for QtyInvoiced */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_500_v, Object> COLUMN_QtyInvoiced = new org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_500_v, Object>(I_EDI_cctop_invoic_500_v.class, "QtyInvoiced", null);
    /** Column name QtyInvoiced */
    public static final String COLUMNNAME_QtyInvoiced = "QtyInvoiced";

	/**
	 * Set Satz.
	 * Rate or Tax or Exchange
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setRate (java.math.BigDecimal Rate);

	/**
	 * Get Satz.
	 * Rate or Tax or Exchange
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getRate();

    /** Column definition for Rate */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_500_v, Object> COLUMN_Rate = new org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_500_v, Object>(I_EDI_cctop_invoic_500_v.class, "Rate", null);
    /** Column name Rate */
    public static final String COLUMNNAME_Rate = "Rate";

	/**
	 * Set Positions-Steuer.
	 * Betrag der enthaltenen oder zuzgl. Steuer in einer Rechungs- oder Auftragsposition
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setTaxAmtInfo (java.math.BigDecimal TaxAmtInfo);

	/**
	 * Get Positions-Steuer.
	 * Betrag der enthaltenen oder zuzgl. Steuer in einer Rechungs- oder Auftragsposition
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getTaxAmtInfo();

    /** Column definition for TaxAmtInfo */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_500_v, Object> COLUMN_TaxAmtInfo = new org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_500_v, Object>(I_EDI_cctop_invoic_500_v.class, "TaxAmtInfo", null);
    /** Column name TaxAmtInfo */
    public static final String COLUMNNAME_TaxAmtInfo = "TaxAmtInfo";

	/**
	 * Set taxfree.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void settaxfree (boolean taxfree);

	/**
	 * Get taxfree.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean istaxfree();

    /** Column definition for taxfree */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_500_v, Object> COLUMN_taxfree = new org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_500_v, Object>(I_EDI_cctop_invoic_500_v.class, "taxfree", null);
    /** Column name taxfree */
    public static final String COLUMNNAME_taxfree = "taxfree";

	/**
	 * Set UPC/EAN.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setUPC (java.lang.String UPC);

	/**
	 * Get UPC/EAN.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getUPC();

    /** Column definition for UPC */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_500_v, Object> COLUMN_UPC = new org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_500_v, Object>(I_EDI_cctop_invoic_500_v.class, "UPC", null);
    /** Column name UPC */
    public static final String COLUMNNAME_UPC = "UPC";

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
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_500_v, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_500_v, Object>(I_EDI_cctop_invoic_500_v.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_500_v, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_500_v, org.compiere.model.I_AD_User>(I_EDI_cctop_invoic_500_v.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/**
	 * Set Suchschlüssel.
	 * Suchschlüssel für den Eintrag im erforderlichen Format - muss eindeutig sein
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setValue (java.lang.String Value);

	/**
	 * Get Suchschlüssel.
	 * Suchschlüssel für den Eintrag im erforderlichen Format - muss eindeutig sein
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getValue();

    /** Column definition for Value */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_500_v, Object> COLUMN_Value = new org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_500_v, Object>(I_EDI_cctop_invoic_500_v.class, "Value", null);
    /** Column name Value */
    public static final String COLUMNNAME_Value = "Value";

	/**
	 * Set Produkt-Nr. Geschäftspartner.
	 * Produkt-Nr. beim Geschäftspartner
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setVendorProductNo (java.lang.String VendorProductNo);

	/**
	 * Get Produkt-Nr. Geschäftspartner.
	 * Produkt-Nr. beim Geschäftspartner
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getVendorProductNo();

    /** Column definition for VendorProductNo */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_500_v, Object> COLUMN_VendorProductNo = new org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_500_v, Object>(I_EDI_cctop_invoic_500_v.class, "VendorProductNo", null);
    /** Column name VendorProductNo */
    public static final String COLUMNNAME_VendorProductNo = "VendorProductNo";
}
