package org.compiere.model;


/** Generated Interface for C_Invoice_Detail
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_C_Invoice_Detail 
{

    /** TableName=C_Invoice_Detail */
    public static final String Table_Name = "C_Invoice_Detail";

    /** AD_Table_ID=540614 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);


	/**
	 * Get Client.
	 * Client/Tenant for this installation.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Client_ID();

    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Organisation.
	 * Organisational entity within client
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Organisation.
	 * Organisational entity within client
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Org_ID();

    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Invoice candidate.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Invoice_Candidate_ID (int C_Invoice_Candidate_ID);

	/**
	 * Get Invoice candidate.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Invoice_Candidate_ID();

    /** Column definition for C_Invoice_Candidate_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Detail, Object> COLUMN_C_Invoice_Candidate_ID = new org.adempiere.model.ModelColumn<I_C_Invoice_Detail, Object>(I_C_Invoice_Detail.class, "C_Invoice_Candidate_ID", null);
    /** Column name C_Invoice_Candidate_ID */
    public static final String COLUMNNAME_C_Invoice_Candidate_ID = "C_Invoice_Candidate_ID";

	/**
	 * Set Invoice detailed informations.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_Invoice_Detail_ID (int C_Invoice_Detail_ID);

	/**
	 * Get Invoice detailed informations.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_Invoice_Detail_ID();

    /** Column definition for C_Invoice_Detail_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Detail, Object> COLUMN_C_Invoice_Detail_ID = new org.adempiere.model.ModelColumn<I_C_Invoice_Detail, Object>(I_C_Invoice_Detail.class, "C_Invoice_Detail_ID", null);
    /** Column name C_Invoice_Detail_ID */
    public static final String COLUMNNAME_C_Invoice_Detail_ID = "C_Invoice_Detail_ID";

	/**
	 * Set Invoice.
	 * Invoice Identifier
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Invoice_ID (int C_Invoice_ID);

	/**
	 * Get Invoice.
	 * Invoice Identifier
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Invoice_ID();

	public org.compiere.model.I_C_Invoice getC_Invoice();

	public void setC_Invoice(org.compiere.model.I_C_Invoice C_Invoice);

    /** Column definition for C_Invoice_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Detail, org.compiere.model.I_C_Invoice> COLUMN_C_Invoice_ID = new org.adempiere.model.ModelColumn<I_C_Invoice_Detail, org.compiere.model.I_C_Invoice>(I_C_Invoice_Detail.class, "C_Invoice_ID", org.compiere.model.I_C_Invoice.class);
    /** Column name C_Invoice_ID */
    public static final String COLUMNNAME_C_Invoice_ID = "C_Invoice_ID";

	/**
	 * Set Invoice Line.
	 * Rechnungszeile
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_InvoiceLine_ID (int C_InvoiceLine_ID);

	/**
	 * Get Invoice Line.
	 * Rechnungszeile
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_InvoiceLine_ID();

	public org.compiere.model.I_C_InvoiceLine getC_InvoiceLine();

	public void setC_InvoiceLine(org.compiere.model.I_C_InvoiceLine C_InvoiceLine);

    /** Column definition for C_InvoiceLine_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Detail, org.compiere.model.I_C_InvoiceLine> COLUMN_C_InvoiceLine_ID = new org.adempiere.model.ModelColumn<I_C_Invoice_Detail, org.compiere.model.I_C_InvoiceLine>(I_C_Invoice_Detail.class, "C_InvoiceLine_ID", org.compiere.model.I_C_InvoiceLine.class);
    /** Column name C_InvoiceLine_ID */
    public static final String COLUMNNAME_C_InvoiceLine_ID = "C_InvoiceLine_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getCreated();

    /** Column definition for Created */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Detail, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_C_Invoice_Detail, Object>(I_C_Invoice_Detail.class, "Created", null);
    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/**
	 * Get Created By.
	 * User who created this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getCreatedBy();

    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set UOM.
	 * Unit of Measure
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_UOM_ID (int C_UOM_ID);

	/**
	 * Get UOM.
	 * Unit of Measure
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_UOM_ID();

    /** Column name C_UOM_ID */
    public static final String COLUMNNAME_C_UOM_ID = "C_UOM_ID";

	/**
	 * Set Date.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDate (java.sql.Timestamp Date);

	/**
	 * Get Date.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getDate();

    /** Column definition for Date */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Detail, Object> COLUMN_Date = new org.adempiere.model.ModelColumn<I_C_Invoice_Detail, Object>(I_C_Invoice_Detail.class, "Date", null);
    /** Column name Date */
    public static final String COLUMNNAME_Date = "Date";

	/**
	 * Set Description.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDescription (java.lang.String Description);

	/**
	 * Get Description.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDescription();

    /** Column definition for Description */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Detail, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<I_C_Invoice_Detail, Object>(I_C_Invoice_Detail.class, "Description", null);
    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/**
	 * Set Rabatt %.
	 * Abschlag in Prozent
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDiscount (java.math.BigDecimal Discount);

	/**
	 * Get Rabatt %.
	 * Abschlag in Prozent
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getDiscount();

    /** Column definition for Discount */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Detail, Object> COLUMN_Discount = new org.adempiere.model.ModelColumn<I_C_Invoice_Detail, Object>(I_C_Invoice_Detail.class, "Discount", null);
    /** Column name Discount */
    public static final String COLUMNNAME_Discount = "Discount";

	/**
	 * Set Active.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsActive (boolean IsActive);

	/**
	 * Get Active.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isActive();

    /** Column definition for IsActive */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Detail, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_C_Invoice_Detail, Object>(I_C_Invoice_Detail.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Detail-Info statt Rechnungszeile andrucken.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsDetailOverridesLine (boolean IsDetailOverridesLine);

	/**
	 * Get Detail-Info statt Rechnungszeile andrucken.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isDetailOverridesLine();

    /** Column definition for IsDetailOverridesLine */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Detail, Object> COLUMN_IsDetailOverridesLine = new org.adempiere.model.ModelColumn<I_C_Invoice_Detail, Object>(I_C_Invoice_Detail.class, "IsDetailOverridesLine", null);
    /** Column name IsDetailOverridesLine */
    public static final String COLUMNNAME_IsDetailOverridesLine = "IsDetailOverridesLine";

	/**
	 * Set davor andrucken.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsPrintBefore (boolean IsPrintBefore);

	/**
	 * Get davor andrucken.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isPrintBefore();

    /** Column definition for IsPrintBefore */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Detail, Object> COLUMN_IsPrintBefore = new org.adempiere.model.ModelColumn<I_C_Invoice_Detail, Object>(I_C_Invoice_Detail.class, "IsPrintBefore", null);
    /** Column name IsPrintBefore */
    public static final String COLUMNNAME_IsPrintBefore = "IsPrintBefore";

	/**
	 * Set andrucken.
	 * Indicates if this document / line is printed
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsPrinted (boolean IsPrinted);

	/**
	 * Get andrucken.
	 * Indicates if this document / line is printed
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isPrinted();

    /** Column definition for IsPrinted */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Detail, Object> COLUMN_IsPrinted = new org.adempiere.model.ModelColumn<I_C_Invoice_Detail, Object>(I_C_Invoice_Detail.class, "IsPrinted", null);
    /** Column name IsPrinted */
    public static final String COLUMNNAME_IsPrinted = "IsPrinted";

	/**
	 * Set Label.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setLabel (java.lang.String Label);

	/**
	 * Get Label.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getLabel();

    /** Column definition for Label */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Detail, Object> COLUMN_Label = new org.adempiere.model.ModelColumn<I_C_Invoice_Detail, Object>(I_C_Invoice_Detail.class, "Label", null);
    /** Column name Label */
    public static final String COLUMNNAME_Label = "Label";

	/**
	 * Set Ausprägung Merkmals-Satz.
	 * Merkmals Ausprägungen zum Produkt
	 *
	 * <br>Type: PAttribute
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_AttributeSetInstance_ID (int M_AttributeSetInstance_ID);

	/**
	 * Get Ausprägung Merkmals-Satz.
	 * Merkmals Ausprägungen zum Produkt
	 *
	 * <br>Type: PAttribute
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_AttributeSetInstance_ID();

	public org.compiere.model.I_M_AttributeSetInstance getM_AttributeSetInstance();

	public void setM_AttributeSetInstance(org.compiere.model.I_M_AttributeSetInstance M_AttributeSetInstance);

    /** Column definition for M_AttributeSetInstance_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Detail, org.compiere.model.I_M_AttributeSetInstance> COLUMN_M_AttributeSetInstance_ID = new org.adempiere.model.ModelColumn<I_C_Invoice_Detail, org.compiere.model.I_M_AttributeSetInstance>(I_C_Invoice_Detail.class, "M_AttributeSetInstance_ID", org.compiere.model.I_M_AttributeSetInstance.class);
    /** Column name M_AttributeSetInstance_ID */
    public static final String COLUMNNAME_M_AttributeSetInstance_ID = "M_AttributeSetInstance_ID";

	/**
	 * Set Product.
	 * Product, Service, Item
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_Product_ID (int M_Product_ID);

	/**
	 * Get Product.
	 * Product, Service, Item
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_Product_ID();

    /** Column name M_Product_ID */
    public static final String COLUMNNAME_M_Product_ID = "M_Product_ID";

	/**
	 * Set Note.
	 * Optional additional user defined information
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setNote (java.lang.String Note);

	/**
	 * Get Note.
	 * Optional additional user defined information
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getNote();

    /** Column definition for Note */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Detail, Object> COLUMN_Note = new org.adempiere.model.ModelColumn<I_C_Invoice_Detail, Object>(I_C_Invoice_Detail.class, "Note", null);
    /** Column name Note */
    public static final String COLUMNNAME_Note = "Note";

	/**
	 * Set Percentage.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPercentage (java.math.BigDecimal Percentage);

	/**
	 * Get Percentage.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getPercentage();

    /** Column definition for Percentage */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Detail, Object> COLUMN_Percentage = new org.adempiere.model.ModelColumn<I_C_Invoice_Detail, Object>(I_C_Invoice_Detail.class, "Percentage", null);
    /** Column name Percentage */
    public static final String COLUMNNAME_Percentage = "Percentage";

	/**
	 * Set Price Actual.
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPriceActual (java.math.BigDecimal PriceActual);

	/**
	 * Get Price Actual.
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getPriceActual();

    /** Column definition for PriceActual */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Detail, Object> COLUMN_PriceActual = new org.adempiere.model.ModelColumn<I_C_Invoice_Detail, Object>(I_C_Invoice_Detail.class, "PriceActual", null);
    /** Column name PriceActual */
    public static final String COLUMNNAME_PriceActual = "PriceActual";

	/**
	 * Set Preis.
	 * Eingegebener Preis - der Preis basierend auf der gewählten Mengeneinheit
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPriceEntered (java.math.BigDecimal PriceEntered);

	/**
	 * Get Preis.
	 * Eingegebener Preis - der Preis basierend auf der gewählten Mengeneinheit
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getPriceEntered();

    /** Column definition for PriceEntered */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Detail, Object> COLUMN_PriceEntered = new org.adempiere.model.ModelColumn<I_C_Invoice_Detail, Object>(I_C_Invoice_Detail.class, "PriceEntered", null);
    /** Column name PriceEntered */
    public static final String COLUMNNAME_PriceEntered = "PriceEntered";

	/**
	 * Set Price Unit.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPrice_UOM_ID (int Price_UOM_ID);

	/**
	 * Get Price Unit.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getPrice_UOM_ID();

    /** Column name Price_UOM_ID */
    public static final String COLUMNNAME_Price_UOM_ID = "Price_UOM_ID";

	/**
	 * Set Quantity.
	 * Quantity
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setQty (java.math.BigDecimal Qty);

	/**
	 * Get Quantity.
	 * Quantity
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQty();

    /** Column definition for Qty */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Detail, Object> COLUMN_Qty = new org.adempiere.model.ModelColumn<I_C_Invoice_Detail, Object>(I_C_Invoice_Detail.class, "Qty", null);
    /** Column name Qty */
    public static final String COLUMNNAME_Qty = "Qty";

	/**
	 * Set Bestellte Menge in Preiseinheit.
	 * Bestellte Menge in Preiseinheit
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setQtyEnteredInPriceUOM (java.math.BigDecimal QtyEnteredInPriceUOM);

	/**
	 * Get Bestellte Menge in Preiseinheit.
	 * Bestellte Menge in Preiseinheit
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyEnteredInPriceUOM();

    /** Column definition for QtyEnteredInPriceUOM */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Detail, Object> COLUMN_QtyEnteredInPriceUOM = new org.adempiere.model.ModelColumn<I_C_Invoice_Detail, Object>(I_C_Invoice_Detail.class, "QtyEnteredInPriceUOM", null);
    /** Column name QtyEnteredInPriceUOM */
    public static final String COLUMNNAME_QtyEnteredInPriceUOM = "QtyEnteredInPriceUOM";

	/**
	 * Set SeqNo.
	 * Method of ordering records;
 lowest number comes first
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setSeqNo (int SeqNo);

	/**
	 * Get SeqNo.
	 * Method of ordering records;
 lowest number comes first
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getSeqNo();

    /** Column definition for SeqNo */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Detail, Object> COLUMN_SeqNo = new org.adempiere.model.ModelColumn<I_C_Invoice_Detail, Object>(I_C_Invoice_Detail.class, "SeqNo", null);
    /** Column name SeqNo */
    public static final String COLUMNNAME_SeqNo = "SeqNo";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getUpdated();

    /** Column definition for Updated */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Detail, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_C_Invoice_Detail, Object>(I_C_Invoice_Detail.class, "Updated", null);
    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Updated By.
	 * User who updated this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getUpdatedBy();

    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
