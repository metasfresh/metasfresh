package de.metas.rfq.model;


/** Generated Interface for C_RfQResponseLineQty_v
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_C_RfQResponseLineQty_v 
{

    /** TableName=C_RfQResponseLineQty_v */
    public static final String Table_Name = "C_RfQResponseLineQty_v";

    /** AD_Table_ID=726 */
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
    public static final org.adempiere.model.ModelColumn<I_C_RfQResponseLineQty_v, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_C_RfQResponseLineQty_v, org.compiere.model.I_AD_Client>(I_C_RfQResponseLineQty_v.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_C_RfQResponseLineQty_v, Object> COLUMN_AD_Language = new org.adempiere.model.ModelColumn<I_C_RfQResponseLineQty_v, Object>(I_C_RfQResponseLineQty_v.class, "AD_Language", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_RfQResponseLineQty_v, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_C_RfQResponseLineQty_v, org.compiere.model.I_AD_Org>(I_C_RfQResponseLineQty_v.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

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
    public static final org.adempiere.model.ModelColumn<I_C_RfQResponseLineQty_v, de.metas.rfq.model.I_C_RfQLineQty> COLUMN_C_RfQLineQty_ID = new org.adempiere.model.ModelColumn<I_C_RfQResponseLineQty_v, de.metas.rfq.model.I_C_RfQLineQty>(I_C_RfQResponseLineQty_v.class, "C_RfQLineQty_ID", de.metas.rfq.model.I_C_RfQLineQty.class);
    /** Column name C_RfQLineQty_ID */
    public static final String COLUMNNAME_C_RfQLineQty_ID = "C_RfQLineQty_ID";

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

	public de.metas.rfq.model.I_C_RfQResponseLine getC_RfQResponseLine();

	public void setC_RfQResponseLine(de.metas.rfq.model.I_C_RfQResponseLine C_RfQResponseLine);

    /** Column definition for C_RfQResponseLine_ID */
    public static final org.adempiere.model.ModelColumn<I_C_RfQResponseLineQty_v, de.metas.rfq.model.I_C_RfQResponseLine> COLUMN_C_RfQResponseLine_ID = new org.adempiere.model.ModelColumn<I_C_RfQResponseLineQty_v, de.metas.rfq.model.I_C_RfQResponseLine>(I_C_RfQResponseLineQty_v.class, "C_RfQResponseLine_ID", de.metas.rfq.model.I_C_RfQResponseLine.class);
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

    /** Column definition for C_RfQResponseLineQty_ID */
    public static final org.adempiere.model.ModelColumn<I_C_RfQResponseLineQty_v, de.metas.rfq.model.I_C_RfQResponseLineQty> COLUMN_C_RfQResponseLineQty_ID = new org.adempiere.model.ModelColumn<I_C_RfQResponseLineQty_v, de.metas.rfq.model.I_C_RfQResponseLineQty>(I_C_RfQResponseLineQty_v.class, "C_RfQResponseLineQty_ID", de.metas.rfq.model.I_C_RfQResponseLineQty.class);
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
    public static final org.adempiere.model.ModelColumn<I_C_RfQResponseLineQty_v, org.compiere.model.I_C_UOM> COLUMN_C_UOM_ID = new org.adempiere.model.ModelColumn<I_C_RfQResponseLineQty_v, org.compiere.model.I_C_UOM>(I_C_RfQResponseLineQty_v.class, "C_UOM_ID", org.compiere.model.I_C_UOM.class);
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
    public static final org.adempiere.model.ModelColumn<I_C_RfQResponseLineQty_v, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_C_RfQResponseLineQty_v, Object>(I_C_RfQResponseLineQty_v.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_RfQResponseLineQty_v, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_C_RfQResponseLineQty_v, org.compiere.model.I_AD_User>(I_C_RfQResponseLineQty_v.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

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
    public static final org.adempiere.model.ModelColumn<I_C_RfQResponseLineQty_v, Object> COLUMN_Discount = new org.adempiere.model.ModelColumn<I_C_RfQResponseLineQty_v, Object>(I_C_RfQResponseLineQty_v.class, "Discount", null);
    /** Column name Discount */
    public static final String COLUMNNAME_Discount = "Discount";

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
    public static final org.adempiere.model.ModelColumn<I_C_RfQResponseLineQty_v, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_C_RfQResponseLineQty_v, Object>(I_C_RfQResponseLineQty_v.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Preis.
	 * Price
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setPrice (int Price);

	/**
	 * Get Preis.
	 * Price
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getPrice();

	public org.compiere.model.I_C_ValidCombination getPr();

	public void setPr(org.compiere.model.I_C_ValidCombination Pr);

    /** Column definition for Price */
    public static final org.adempiere.model.ModelColumn<I_C_RfQResponseLineQty_v, org.compiere.model.I_C_ValidCombination> COLUMN_Price = new org.adempiere.model.ModelColumn<I_C_RfQResponseLineQty_v, org.compiere.model.I_C_ValidCombination>(I_C_RfQResponseLineQty_v.class, "Price", org.compiere.model.I_C_ValidCombination.class);
    /** Column name Price */
    public static final String COLUMNNAME_Price = "Price";

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
    public static final org.adempiere.model.ModelColumn<I_C_RfQResponseLineQty_v, Object> COLUMN_Qty = new org.adempiere.model.ModelColumn<I_C_RfQResponseLineQty_v, Object>(I_C_RfQResponseLineQty_v.class, "Qty", null);
    /** Column name Qty */
    public static final String COLUMNNAME_Qty = "Qty";

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
    public static final org.adempiere.model.ModelColumn<I_C_RfQResponseLineQty_v, Object> COLUMN_UOMSymbol = new org.adempiere.model.ModelColumn<I_C_RfQResponseLineQty_v, Object>(I_C_RfQResponseLineQty_v.class, "UOMSymbol", null);
    /** Column name UOMSymbol */
    public static final String COLUMNNAME_UOMSymbol = "UOMSymbol";

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
    public static final org.adempiere.model.ModelColumn<I_C_RfQResponseLineQty_v, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_C_RfQResponseLineQty_v, Object>(I_C_RfQResponseLineQty_v.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_RfQResponseLineQty_v, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_C_RfQResponseLineQty_v, org.compiere.model.I_AD_User>(I_C_RfQResponseLineQty_v.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
