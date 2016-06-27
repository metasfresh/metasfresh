package de.metas.rfq.model;


/** Generated Interface for C_RfQResponseLine_v
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_C_RfQResponseLine_v 
{

    /** TableName=C_RfQResponseLine_v */
    public static final String Table_Name = "C_RfQResponseLine_v";

    /** AD_Table_ID=724 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(3);

    /** Load Meta Data */

	/**
	 * Get Mandant.
	 * Client/Tenant for this installation.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Client_ID();

	public org.compiere.model.I_AD_Client getAD_Client();

    /** Column definition for AD_Client_ID */
    public static final org.adempiere.model.ModelColumn<I_C_RfQResponseLine_v, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_C_RfQResponseLine_v, org.compiere.model.I_AD_Client>(I_C_RfQResponseLine_v.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Sprache.
	 * Language for this entity
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_Language (java.lang.String AD_Language);

	/**
	 * Get Sprache.
	 * Language for this entity
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getAD_Language();

    /** Column definition for AD_Language */
    public static final org.adempiere.model.ModelColumn<I_C_RfQResponseLine_v, Object> COLUMN_AD_Language = new org.adempiere.model.ModelColumn<I_C_RfQResponseLine_v, Object>(I_C_RfQResponseLine_v.class, "AD_Language", null);
    /** Column name AD_Language */
    public static final String COLUMNNAME_AD_Language = "AD_Language";

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
    public static final org.adempiere.model.ModelColumn<I_C_RfQResponseLine_v, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_C_RfQResponseLine_v, org.compiere.model.I_AD_Org>(I_C_RfQResponseLine_v.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Benchmark Price.
	 * Price to compare responses to
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setBenchmarkPrice (java.math.BigDecimal BenchmarkPrice);

	/**
	 * Get Benchmark Price.
	 * Price to compare responses to
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getBenchmarkPrice();

    /** Column definition for BenchmarkPrice */
    public static final org.adempiere.model.ModelColumn<I_C_RfQResponseLine_v, Object> COLUMN_BenchmarkPrice = new org.adempiere.model.ModelColumn<I_C_RfQResponseLine_v, Object>(I_C_RfQResponseLine_v.class, "BenchmarkPrice", null);
    /** Column name BenchmarkPrice */
    public static final String COLUMNNAME_BenchmarkPrice = "BenchmarkPrice";

	/**
	 * Set RfQ Line.
	 * Request for Quotation Line
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_RfQLine_ID (int C_RfQLine_ID);

	/**
	 * Get RfQ Line.
	 * Request for Quotation Line
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_RfQLine_ID();

	public de.metas.rfq.model.I_C_RfQLine getC_RfQLine();

	public void setC_RfQLine(de.metas.rfq.model.I_C_RfQLine C_RfQLine);

    /** Column definition for C_RfQLine_ID */
    public static final org.adempiere.model.ModelColumn<I_C_RfQResponseLine_v, de.metas.rfq.model.I_C_RfQLine> COLUMN_C_RfQLine_ID = new org.adempiere.model.ModelColumn<I_C_RfQResponseLine_v, de.metas.rfq.model.I_C_RfQLine>(I_C_RfQResponseLine_v.class, "C_RfQLine_ID", de.metas.rfq.model.I_C_RfQLine.class);
    /** Column name C_RfQLine_ID */
    public static final String COLUMNNAME_C_RfQLine_ID = "C_RfQLine_ID";

	/**
	 * Set RfQ Line Quantity.
	 * Request for Quotation Line Quantity
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_RfQLineQty_ID (int C_RfQLineQty_ID);

	/**
	 * Get RfQ Line Quantity.
	 * Request for Quotation Line Quantity
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_RfQLineQty_ID();

	public de.metas.rfq.model.I_C_RfQLineQty getC_RfQLineQty();

	public void setC_RfQLineQty(de.metas.rfq.model.I_C_RfQLineQty C_RfQLineQty);

    /** Column definition for C_RfQLineQty_ID */
    public static final org.adempiere.model.ModelColumn<I_C_RfQResponseLine_v, de.metas.rfq.model.I_C_RfQLineQty> COLUMN_C_RfQLineQty_ID = new org.adempiere.model.ModelColumn<I_C_RfQResponseLine_v, de.metas.rfq.model.I_C_RfQLineQty>(I_C_RfQResponseLine_v.class, "C_RfQLineQty_ID", de.metas.rfq.model.I_C_RfQLineQty.class);
    /** Column name C_RfQLineQty_ID */
    public static final String COLUMNNAME_C_RfQLineQty_ID = "C_RfQLineQty_ID";

	/**
	 * Set Ausschreibungs-Antwort.
	 * Request for Quotation Response from a potential Vendor
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_RfQResponse_ID (int C_RfQResponse_ID);

	/**
	 * Get Ausschreibungs-Antwort.
	 * Request for Quotation Response from a potential Vendor
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_RfQResponse_ID();

	public de.metas.rfq.model.I_C_RfQResponse getC_RfQResponse();

	public void setC_RfQResponse(de.metas.rfq.model.I_C_RfQResponse C_RfQResponse);

    /** Column definition for C_RfQResponse_ID */
    public static final org.adempiere.model.ModelColumn<I_C_RfQResponseLine_v, de.metas.rfq.model.I_C_RfQResponse> COLUMN_C_RfQResponse_ID = new org.adempiere.model.ModelColumn<I_C_RfQResponseLine_v, de.metas.rfq.model.I_C_RfQResponse>(I_C_RfQResponseLine_v.class, "C_RfQResponse_ID", de.metas.rfq.model.I_C_RfQResponse.class);
    /** Column name C_RfQResponse_ID */
    public static final String COLUMNNAME_C_RfQResponse_ID = "C_RfQResponse_ID";

	/**
	 * Set RfQ Response Line.
	 * Request for Quotation Response Line
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_RfQResponseLine_ID (int C_RfQResponseLine_ID);

	/**
	 * Get RfQ Response Line.
	 * Request for Quotation Response Line
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_RfQResponseLine_ID();

    /** Column definition for C_RfQResponseLine_ID */
    public static final org.adempiere.model.ModelColumn<I_C_RfQResponseLine_v, de.metas.rfq.model.I_C_RfQResponseLine> COLUMN_C_RfQResponseLine_ID = new org.adempiere.model.ModelColumn<I_C_RfQResponseLine_v, de.metas.rfq.model.I_C_RfQResponseLine>(I_C_RfQResponseLine_v.class, "C_RfQResponseLine_ID", de.metas.rfq.model.I_C_RfQResponseLine.class);
    /** Column name C_RfQResponseLine_ID */
    public static final String COLUMNNAME_C_RfQResponseLine_ID = "C_RfQResponseLine_ID";

	/**
	 * Set RfQ Response Line Qty.
	 * Request for Quotation Response Line Quantity
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_RfQResponseLineQty_ID (int C_RfQResponseLineQty_ID);

	/**
	 * Get RfQ Response Line Qty.
	 * Request for Quotation Response Line Quantity
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_RfQResponseLineQty_ID();

	public de.metas.rfq.model.I_C_RfQResponseLineQty getC_RfQResponseLineQty();

	public void setC_RfQResponseLineQty(de.metas.rfq.model.I_C_RfQResponseLineQty C_RfQResponseLineQty);

    /** Column definition for C_RfQResponseLineQty_ID */
    public static final org.adempiere.model.ModelColumn<I_C_RfQResponseLine_v, de.metas.rfq.model.I_C_RfQResponseLineQty> COLUMN_C_RfQResponseLineQty_ID = new org.adempiere.model.ModelColumn<I_C_RfQResponseLine_v, de.metas.rfq.model.I_C_RfQResponseLineQty>(I_C_RfQResponseLine_v.class, "C_RfQResponseLineQty_ID", de.metas.rfq.model.I_C_RfQResponseLineQty.class);
    /** Column name C_RfQResponseLineQty_ID */
    public static final String COLUMNNAME_C_RfQResponseLineQty_ID = "C_RfQResponseLineQty_ID";

	/**
	 * Set Maßeinheit.
	 * Unit of Measure
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_UOM_ID (int C_UOM_ID);

	/**
	 * Get Maßeinheit.
	 * Unit of Measure
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_UOM_ID();

	public org.compiere.model.I_C_UOM getC_UOM();

	public void setC_UOM(org.compiere.model.I_C_UOM C_UOM);

    /** Column definition for C_UOM_ID */
    public static final org.adempiere.model.ModelColumn<I_C_RfQResponseLine_v, org.compiere.model.I_C_UOM> COLUMN_C_UOM_ID = new org.adempiere.model.ModelColumn<I_C_RfQResponseLine_v, org.compiere.model.I_C_UOM>(I_C_RfQResponseLine_v.class, "C_UOM_ID", org.compiere.model.I_C_UOM.class);
    /** Column name C_UOM_ID */
    public static final String COLUMNNAME_C_UOM_ID = "C_UOM_ID";

	/**
	 * Get Erstellt.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getCreated();

    /** Column definition for Created */
    public static final org.adempiere.model.ModelColumn<I_C_RfQResponseLine_v, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_C_RfQResponseLine_v, Object>(I_C_RfQResponseLine_v.class, "Created", null);
    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/**
	 * Get Erstellt durch.
	 * User who created this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getCreatedBy();

    /** Column definition for CreatedBy */
    public static final org.adempiere.model.ModelColumn<I_C_RfQResponseLine_v, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_C_RfQResponseLine_v, org.compiere.model.I_AD_User>(I_C_RfQResponseLine_v.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Arbeitsbeginn.
	 * Date when work is (planned to be) started
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDateWorkStart (java.sql.Timestamp DateWorkStart);

	/**
	 * Get Arbeitsbeginn.
	 * Date when work is (planned to be) started
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getDateWorkStart();

    /** Column definition for DateWorkStart */
    public static final org.adempiere.model.ModelColumn<I_C_RfQResponseLine_v, Object> COLUMN_DateWorkStart = new org.adempiere.model.ModelColumn<I_C_RfQResponseLine_v, Object>(I_C_RfQResponseLine_v.class, "DateWorkStart", null);
    /** Column name DateWorkStart */
    public static final String COLUMNNAME_DateWorkStart = "DateWorkStart";

	/**
	 * Set Auslieferungstage.
	 * Number of Days (planned) until Delivery
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDeliveryDays (int DeliveryDays);

	/**
	 * Get Auslieferungstage.
	 * Number of Days (planned) until Delivery
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getDeliveryDays();

    /** Column definition for DeliveryDays */
    public static final org.adempiere.model.ModelColumn<I_C_RfQResponseLine_v, Object> COLUMN_DeliveryDays = new org.adempiere.model.ModelColumn<I_C_RfQResponseLine_v, Object>(I_C_RfQResponseLine_v.class, "DeliveryDays", null);
    /** Column name DeliveryDays */
    public static final String COLUMNNAME_DeliveryDays = "DeliveryDays";

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
    public static final org.adempiere.model.ModelColumn<I_C_RfQResponseLine_v, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<I_C_RfQResponseLine_v, Object>(I_C_RfQResponseLine_v.class, "Description", null);
    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/**
	 * Set Rabatt %.
	 * Discount in percent
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDiscount (java.math.BigDecimal Discount);

	/**
	 * Get Rabatt %.
	 * Discount in percent
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getDiscount();

    /** Column definition for Discount */
    public static final org.adempiere.model.ModelColumn<I_C_RfQResponseLine_v, Object> COLUMN_Discount = new org.adempiere.model.ModelColumn<I_C_RfQResponseLine_v, Object>(I_C_RfQResponseLine_v.class, "Discount", null);
    /** Column name Discount */
    public static final String COLUMNNAME_Discount = "Discount";

	/**
	 * Set Notiz / Zeilentext.
	 * Additional information for a Document
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDocumentNote (java.lang.String DocumentNote);

	/**
	 * Get Notiz / Zeilentext.
	 * Additional information for a Document
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDocumentNote();

    /** Column definition for DocumentNote */
    public static final org.adempiere.model.ModelColumn<I_C_RfQResponseLine_v, Object> COLUMN_DocumentNote = new org.adempiere.model.ModelColumn<I_C_RfQResponseLine_v, Object>(I_C_RfQResponseLine_v.class, "DocumentNote", null);
    /** Column name DocumentNote */
    public static final String COLUMNNAME_DocumentNote = "DocumentNote";

	/**
	 * Set Kommentar/Hilfe.
	 * Comment or Hint
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setHelp (java.lang.String Help);

	/**
	 * Get Kommentar/Hilfe.
	 * Comment or Hint
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getHelp();

    /** Column definition for Help */
    public static final org.adempiere.model.ModelColumn<I_C_RfQResponseLine_v, Object> COLUMN_Help = new org.adempiere.model.ModelColumn<I_C_RfQResponseLine_v, Object>(I_C_RfQResponseLine_v.class, "Help", null);
    /** Column name Help */
    public static final String COLUMNNAME_Help = "Help";

	/**
	 * Set Aktiv.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsActive (boolean IsActive);

	/**
	 * Get Aktiv.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isActive();

    /** Column definition for IsActive */
    public static final org.adempiere.model.ModelColumn<I_C_RfQResponseLine_v, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_C_RfQResponseLine_v, Object>(I_C_RfQResponseLine_v.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Zeile Nr..
	 * Unique line for this document
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setLine (int Line);

	/**
	 * Get Zeile Nr..
	 * Unique line for this document
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getLine();

    /** Column definition for Line */
    public static final org.adempiere.model.ModelColumn<I_C_RfQResponseLine_v, Object> COLUMN_Line = new org.adempiere.model.ModelColumn<I_C_RfQResponseLine_v, Object>(I_C_RfQResponseLine_v.class, "Line", null);
    /** Column name Line */
    public static final String COLUMNNAME_Line = "Line";

	/**
	 * Set Ausprägung Merkmals-Satz.
	 * Product Attribute Set Instance
	 *
	 * <br>Type: PAttribute
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_AttributeSetInstance_ID (int M_AttributeSetInstance_ID);

	/**
	 * Get Ausprägung Merkmals-Satz.
	 * Product Attribute Set Instance
	 *
	 * <br>Type: PAttribute
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_AttributeSetInstance_ID();

	public org.compiere.model.I_M_AttributeSetInstance getM_AttributeSetInstance();

	public void setM_AttributeSetInstance(org.compiere.model.I_M_AttributeSetInstance M_AttributeSetInstance);

    /** Column definition for M_AttributeSetInstance_ID */
    public static final org.adempiere.model.ModelColumn<I_C_RfQResponseLine_v, org.compiere.model.I_M_AttributeSetInstance> COLUMN_M_AttributeSetInstance_ID = new org.adempiere.model.ModelColumn<I_C_RfQResponseLine_v, org.compiere.model.I_M_AttributeSetInstance>(I_C_RfQResponseLine_v.class, "M_AttributeSetInstance_ID", org.compiere.model.I_M_AttributeSetInstance.class);
    /** Column name M_AttributeSetInstance_ID */
    public static final String COLUMNNAME_M_AttributeSetInstance_ID = "M_AttributeSetInstance_ID";

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
    public static final org.adempiere.model.ModelColumn<I_C_RfQResponseLine_v, org.compiere.model.I_M_Product> COLUMN_M_Product_ID = new org.adempiere.model.ModelColumn<I_C_RfQResponseLine_v, org.compiere.model.I_M_Product>(I_C_RfQResponseLine_v.class, "M_Product_ID", org.compiere.model.I_M_Product.class);
    /** Column name M_Product_ID */
    public static final String COLUMNNAME_M_Product_ID = "M_Product_ID";

	/**
	 * Set Name.
	 * Alphanumeric identifier of the entity
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setName (java.lang.String Name);

	/**
	 * Get Name.
	 * Alphanumeric identifier of the entity
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getName();

    /** Column definition for Name */
    public static final org.adempiere.model.ModelColumn<I_C_RfQResponseLine_v, Object> COLUMN_Name = new org.adempiere.model.ModelColumn<I_C_RfQResponseLine_v, Object>(I_C_RfQResponseLine_v.class, "Name", null);
    /** Column name Name */
    public static final String COLUMNNAME_Name = "Name";

	/**
	 * Set Preis.
	 * Price
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setPrice (java.math.BigDecimal Price);

	/**
	 * Get Preis.
	 * Price
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getPrice();

    /** Column definition for Price */
    public static final org.adempiere.model.ModelColumn<I_C_RfQResponseLine_v, Object> COLUMN_Price = new org.adempiere.model.ModelColumn<I_C_RfQResponseLine_v, Object>(I_C_RfQResponseLine_v.class, "Price", null);
    /** Column name Price */
    public static final String COLUMNNAME_Price = "Price";

	/**
	 * Set Produktschlüssel.
	 * Key of the Product
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setProductValue (java.lang.String ProductValue);

	/**
	 * Get Produktschlüssel.
	 * Key of the Product
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getProductValue();

    /** Column definition for ProductValue */
    public static final org.adempiere.model.ModelColumn<I_C_RfQResponseLine_v, Object> COLUMN_ProductValue = new org.adempiere.model.ModelColumn<I_C_RfQResponseLine_v, Object>(I_C_RfQResponseLine_v.class, "ProductValue", null);
    /** Column name ProductValue */
    public static final String COLUMNNAME_ProductValue = "ProductValue";

	/**
	 * Set Menge.
	 * Quantity
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setQty (java.math.BigDecimal Qty);

	/**
	 * Get Menge.
	 * Quantity
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQty();

    /** Column definition for Qty */
    public static final org.adempiere.model.ModelColumn<I_C_RfQResponseLine_v, Object> COLUMN_Qty = new org.adempiere.model.ModelColumn<I_C_RfQResponseLine_v, Object>(I_C_RfQResponseLine_v.class, "Qty", null);
    /** Column name Qty */
    public static final String COLUMNNAME_Qty = "Qty";

	/**
	 * Set SKU.
	 * Stock Keeping Unit
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setSKU (java.lang.String SKU);

	/**
	 * Get SKU.
	 * Stock Keeping Unit
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getSKU();

    /** Column definition for SKU */
    public static final org.adempiere.model.ModelColumn<I_C_RfQResponseLine_v, Object> COLUMN_SKU = new org.adempiere.model.ModelColumn<I_C_RfQResponseLine_v, Object>(I_C_RfQResponseLine_v.class, "SKU", null);
    /** Column name SKU */
    public static final String COLUMNNAME_SKU = "SKU";

	/**
	 * Set Symbol.
	 * Symbol for a Unit of Measure
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setUOMSymbol (java.lang.String UOMSymbol);

	/**
	 * Get Symbol.
	 * Symbol for a Unit of Measure
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getUOMSymbol();

    /** Column definition for UOMSymbol */
    public static final org.adempiere.model.ModelColumn<I_C_RfQResponseLine_v, Object> COLUMN_UOMSymbol = new org.adempiere.model.ModelColumn<I_C_RfQResponseLine_v, Object>(I_C_RfQResponseLine_v.class, "UOMSymbol", null);
    /** Column name UOMSymbol */
    public static final String COLUMNNAME_UOMSymbol = "UOMSymbol";

	/**
	 * Set UPC/EAN.
	 * Bar Code (Universal Product Code or its superset European Article Number)
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setUPC (java.lang.String UPC);

	/**
	 * Get UPC/EAN.
	 * Bar Code (Universal Product Code or its superset European Article Number)
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getUPC();

    /** Column definition for UPC */
    public static final org.adempiere.model.ModelColumn<I_C_RfQResponseLine_v, Object> COLUMN_UPC = new org.adempiere.model.ModelColumn<I_C_RfQResponseLine_v, Object>(I_C_RfQResponseLine_v.class, "UPC", null);
    /** Column name UPC */
    public static final String COLUMNNAME_UPC = "UPC";

	/**
	 * Get Aktualisiert.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getUpdated();

    /** Column definition for Updated */
    public static final org.adempiere.model.ModelColumn<I_C_RfQResponseLine_v, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_C_RfQResponseLine_v, Object>(I_C_RfQResponseLine_v.class, "Updated", null);
    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Aktualisiert durch.
	 * User who updated this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getUpdatedBy();

    /** Column definition for UpdatedBy */
    public static final org.adempiere.model.ModelColumn<I_C_RfQResponseLine_v, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_C_RfQResponseLine_v, org.compiere.model.I_AD_User>(I_C_RfQResponseLine_v.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
