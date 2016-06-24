package de.metas.rfq.model;


/** Generated Interface for C_RfQLineQty
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_C_RfQLineQty 
{

    /** TableName=C_RfQLineQty */
    public static final String Table_Name = "C_RfQLineQty";

    /** AD_Table_ID=675 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 1 - Org
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(1);

    /** Load Meta Data */

	/**
	 * Get Mandant.
	 * Client/Tenant for this installation.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Client_ID();

	public org.compiere.model.I_AD_Client getAD_Client();

    /** Column definition for AD_Client_ID */
    public static final org.adempiere.model.ModelColumn<I_C_RfQLineQty, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_C_RfQLineQty, org.compiere.model.I_AD_Client>(I_C_RfQLineQty.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_C_RfQLineQty, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_C_RfQLineQty, org.compiere.model.I_AD_Org>(I_C_RfQLineQty.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
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
    public static final org.adempiere.model.ModelColumn<I_C_RfQLineQty, Object> COLUMN_BenchmarkPrice = new org.adempiere.model.ModelColumn<I_C_RfQLineQty, Object>(I_C_RfQLineQty.class, "BenchmarkPrice", null);
    /** Column name BenchmarkPrice */
    public static final String COLUMNNAME_BenchmarkPrice = "BenchmarkPrice";

	/**
	 * Set Best Response Amount.
	 * Best Response Amount
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setBestResponseAmt (java.math.BigDecimal BestResponseAmt);

	/**
	 * Get Best Response Amount.
	 * Best Response Amount
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getBestResponseAmt();

    /** Column definition for BestResponseAmt */
    public static final org.adempiere.model.ModelColumn<I_C_RfQLineQty, Object> COLUMN_BestResponseAmt = new org.adempiere.model.ModelColumn<I_C_RfQLineQty, Object>(I_C_RfQLineQty.class, "BestResponseAmt", null);
    /** Column name BestResponseAmt */
    public static final String COLUMNNAME_BestResponseAmt = "BestResponseAmt";

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
    public static final org.adempiere.model.ModelColumn<I_C_RfQLineQty, de.metas.rfq.model.I_C_RfQLine> COLUMN_C_RfQLine_ID = new org.adempiere.model.ModelColumn<I_C_RfQLineQty, de.metas.rfq.model.I_C_RfQLine>(I_C_RfQLineQty.class, "C_RfQLine_ID", de.metas.rfq.model.I_C_RfQLine.class);
    /** Column name C_RfQLine_ID */
    public static final String COLUMNNAME_C_RfQLine_ID = "C_RfQLine_ID";

	/**
	 * Set RfQ Line Quantity.
	 * Request for Quotation Line Quantity
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_RfQLineQty_ID (int C_RfQLineQty_ID);

	/**
	 * Get RfQ Line Quantity.
	 * Request for Quotation Line Quantity
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_RfQLineQty_ID();

    /** Column definition for C_RfQLineQty_ID */
    public static final org.adempiere.model.ModelColumn<I_C_RfQLineQty, Object> COLUMN_C_RfQLineQty_ID = new org.adempiere.model.ModelColumn<I_C_RfQLineQty, Object>(I_C_RfQLineQty.class, "C_RfQLineQty_ID", null);
    /** Column name C_RfQLineQty_ID */
    public static final String COLUMNNAME_C_RfQLineQty_ID = "C_RfQLineQty_ID";

	/**
	 * Set Maßeinheit.
	 * Unit of Measure
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_UOM_ID (int C_UOM_ID);

	/**
	 * Get Maßeinheit.
	 * Unit of Measure
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_UOM_ID();

	public org.compiere.model.I_C_UOM getC_UOM();

	public void setC_UOM(org.compiere.model.I_C_UOM C_UOM);

    /** Column definition for C_UOM_ID */
    public static final org.adempiere.model.ModelColumn<I_C_RfQLineQty, org.compiere.model.I_C_UOM> COLUMN_C_UOM_ID = new org.adempiere.model.ModelColumn<I_C_RfQLineQty, org.compiere.model.I_C_UOM>(I_C_RfQLineQty.class, "C_UOM_ID", org.compiere.model.I_C_UOM.class);
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
    public static final org.adempiere.model.ModelColumn<I_C_RfQLineQty, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_C_RfQLineQty, Object>(I_C_RfQLineQty.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_RfQLineQty, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_C_RfQLineQty, org.compiere.model.I_AD_User>(I_C_RfQLineQty.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

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
    public static final org.adempiere.model.ModelColumn<I_C_RfQLineQty, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_C_RfQLineQty, Object>(I_C_RfQLineQty.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Offer Quantity.
	 * This quantity is used in the Offer to the Customer
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsOfferQty (boolean IsOfferQty);

	/**
	 * Get Offer Quantity.
	 * This quantity is used in the Offer to the Customer
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isOfferQty();

    /** Column definition for IsOfferQty */
    public static final org.adempiere.model.ModelColumn<I_C_RfQLineQty, Object> COLUMN_IsOfferQty = new org.adempiere.model.ModelColumn<I_C_RfQLineQty, Object>(I_C_RfQLineQty.class, "IsOfferQty", null);
    /** Column name IsOfferQty */
    public static final String COLUMNNAME_IsOfferQty = "IsOfferQty";

	/**
	 * Set Purchase Quantity.
	 * This quantity is used in the Purchase Order to the Supplier
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsPurchaseQty (boolean IsPurchaseQty);

	/**
	 * Get Purchase Quantity.
	 * This quantity is used in the Purchase Order to the Supplier
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isPurchaseQty();

    /** Column definition for IsPurchaseQty */
    public static final org.adempiere.model.ModelColumn<I_C_RfQLineQty, Object> COLUMN_IsPurchaseQty = new org.adempiere.model.ModelColumn<I_C_RfQLineQty, Object>(I_C_RfQLineQty.class, "IsPurchaseQty", null);
    /** Column name IsPurchaseQty */
    public static final String COLUMNNAME_IsPurchaseQty = "IsPurchaseQty";

	/**
	 * Set RfQ Quantity.
	 * The quantity is used when generating RfQ Responses
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsRfQQty (boolean IsRfQQty);

	/**
	 * Get RfQ Quantity.
	 * The quantity is used when generating RfQ Responses
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isRfQQty();

    /** Column definition for IsRfQQty */
    public static final org.adempiere.model.ModelColumn<I_C_RfQLineQty, Object> COLUMN_IsRfQQty = new org.adempiere.model.ModelColumn<I_C_RfQLineQty, Object>(I_C_RfQLineQty.class, "IsRfQQty", null);
    /** Column name IsRfQQty */
    public static final String COLUMNNAME_IsRfQQty = "IsRfQQty";

	/**
	 * Set Margin %.
	 * Margin for a product as a percentage
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setMargin (java.math.BigDecimal Margin);

	/**
	 * Get Margin %.
	 * Margin for a product as a percentage
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getMargin();

    /** Column definition for Margin */
    public static final org.adempiere.model.ModelColumn<I_C_RfQLineQty, Object> COLUMN_Margin = new org.adempiere.model.ModelColumn<I_C_RfQLineQty, Object>(I_C_RfQLineQty.class, "Margin", null);
    /** Column name Margin */
    public static final String COLUMNNAME_Margin = "Margin";

	/**
	 * Set Offer Amount.
	 * Amount of the Offer
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setOfferAmt (java.math.BigDecimal OfferAmt);

	/**
	 * Get Offer Amount.
	 * Amount of the Offer
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getOfferAmt();

    /** Column definition for OfferAmt */
    public static final org.adempiere.model.ModelColumn<I_C_RfQLineQty, Object> COLUMN_OfferAmt = new org.adempiere.model.ModelColumn<I_C_RfQLineQty, Object>(I_C_RfQLineQty.class, "OfferAmt", null);
    /** Column name OfferAmt */
    public static final String COLUMNNAME_OfferAmt = "OfferAmt";

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
    public static final org.adempiere.model.ModelColumn<I_C_RfQLineQty, Object> COLUMN_Qty = new org.adempiere.model.ModelColumn<I_C_RfQLineQty, Object>(I_C_RfQLineQty.class, "Qty", null);
    /** Column name Qty */
    public static final String COLUMNNAME_Qty = "Qty";

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
    public static final org.adempiere.model.ModelColumn<I_C_RfQLineQty, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_C_RfQLineQty, Object>(I_C_RfQLineQty.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_RfQLineQty, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_C_RfQLineQty, org.compiere.model.I_AD_User>(I_C_RfQLineQty.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
