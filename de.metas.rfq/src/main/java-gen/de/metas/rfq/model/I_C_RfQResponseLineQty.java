package de.metas.rfq.model;


/** Generated Interface for C_RfQResponseLineQty
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_C_RfQResponseLineQty 
{

    /** TableName=C_RfQResponseLineQty */
    public static final String Table_Name = "C_RfQResponseLineQty";

    /** AD_Table_ID=672 */
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
    public static final org.adempiere.model.ModelColumn<I_C_RfQResponseLineQty, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_C_RfQResponseLineQty, org.compiere.model.I_AD_Client>(I_C_RfQResponseLineQty.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_C_RfQResponseLineQty, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_C_RfQResponseLineQty, org.compiere.model.I_AD_Org>(I_C_RfQResponseLineQty.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

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
    public static final org.adempiere.model.ModelColumn<I_C_RfQResponseLineQty, de.metas.rfq.model.I_C_RfQLine> COLUMN_C_RfQLine_ID = new org.adempiere.model.ModelColumn<I_C_RfQResponseLineQty, de.metas.rfq.model.I_C_RfQLine>(I_C_RfQResponseLineQty.class, "C_RfQLine_ID", de.metas.rfq.model.I_C_RfQLine.class);
    /** Column name C_RfQLine_ID */
    public static final String COLUMNNAME_C_RfQLine_ID = "C_RfQLine_ID";

	/**
	 * Set RfQ Line Quantity.
	 * Request for Quotation Line Quantity
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_RfQLineQty_ID (int C_RfQLineQty_ID);

	/**
	 * Get RfQ Line Quantity.
	 * Request for Quotation Line Quantity
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_RfQLineQty_ID();

	public de.metas.rfq.model.I_C_RfQLineQty getC_RfQLineQty();

	public void setC_RfQLineQty(de.metas.rfq.model.I_C_RfQLineQty C_RfQLineQty);

    /** Column definition for C_RfQLineQty_ID */
    public static final org.adempiere.model.ModelColumn<I_C_RfQResponseLineQty, de.metas.rfq.model.I_C_RfQLineQty> COLUMN_C_RfQLineQty_ID = new org.adempiere.model.ModelColumn<I_C_RfQResponseLineQty, de.metas.rfq.model.I_C_RfQLineQty>(I_C_RfQResponseLineQty.class, "C_RfQLineQty_ID", de.metas.rfq.model.I_C_RfQLineQty.class);
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
    public static final org.adempiere.model.ModelColumn<I_C_RfQResponseLineQty, de.metas.rfq.model.I_C_RfQResponseLine> COLUMN_C_RfQResponseLine_ID = new org.adempiere.model.ModelColumn<I_C_RfQResponseLineQty, de.metas.rfq.model.I_C_RfQResponseLine>(I_C_RfQResponseLineQty.class, "C_RfQResponseLine_ID", de.metas.rfq.model.I_C_RfQResponseLine.class);
    /** Column name C_RfQResponseLine_ID */
    public static final String COLUMNNAME_C_RfQResponseLine_ID = "C_RfQResponseLine_ID";

	/**
	 * Set RfQ Response Line Qty.
	 * Request for Quotation Response Line Quantity
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_RfQResponseLineQty_ID (int C_RfQResponseLineQty_ID);

	/**
	 * Get RfQ Response Line Qty.
	 * Request for Quotation Response Line Quantity
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_RfQResponseLineQty_ID();

    /** Column definition for C_RfQResponseLineQty_ID */
    public static final org.adempiere.model.ModelColumn<I_C_RfQResponseLineQty, Object> COLUMN_C_RfQResponseLineQty_ID = new org.adempiere.model.ModelColumn<I_C_RfQResponseLineQty, Object>(I_C_RfQResponseLineQty.class, "C_RfQResponseLineQty_ID", null);
    /** Column name C_RfQResponseLineQty_ID */
    public static final String COLUMNNAME_C_RfQResponseLineQty_ID = "C_RfQResponseLineQty_ID";

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
    public static final org.adempiere.model.ModelColumn<I_C_RfQResponseLineQty, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_C_RfQResponseLineQty, Object>(I_C_RfQResponseLineQty.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_RfQResponseLineQty, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_C_RfQResponseLineQty, org.compiere.model.I_AD_User>(I_C_RfQResponseLineQty.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Zugesagter Termin.
	 * Zugesagter Termin für diesen Auftrag
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDatePromised (java.sql.Timestamp DatePromised);

	/**
	 * Get Zugesagter Termin.
	 * Zugesagter Termin für diesen Auftrag
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getDatePromised();

    /** Column definition for DatePromised */
    public static final org.adempiere.model.ModelColumn<I_C_RfQResponseLineQty, Object> COLUMN_DatePromised = new org.adempiere.model.ModelColumn<I_C_RfQResponseLineQty, Object>(I_C_RfQResponseLineQty.class, "DatePromised", null);
    /** Column name DatePromised */
    public static final String COLUMNNAME_DatePromised = "DatePromised";

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
    public static final org.adempiere.model.ModelColumn<I_C_RfQResponseLineQty, Object> COLUMN_Discount = new org.adempiere.model.ModelColumn<I_C_RfQResponseLineQty, Object>(I_C_RfQResponseLineQty.class, "Discount", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_RfQResponseLineQty, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_C_RfQResponseLineQty, Object>(I_C_RfQResponseLineQty.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

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
    public static final org.adempiere.model.ModelColumn<I_C_RfQResponseLineQty, Object> COLUMN_Price = new org.adempiere.model.ModelColumn<I_C_RfQResponseLineQty, Object>(I_C_RfQResponseLineQty.class, "Price", null);
    /** Column name Price */
    public static final String COLUMNNAME_Price = "Price";

	/**
	 * Set Zusagbar.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setQtyPromised (java.math.BigDecimal QtyPromised);

	/**
	 * Get Zusagbar.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyPromised();

    /** Column definition for QtyPromised */
    public static final org.adempiere.model.ModelColumn<I_C_RfQResponseLineQty, Object> COLUMN_QtyPromised = new org.adempiere.model.ModelColumn<I_C_RfQResponseLineQty, Object>(I_C_RfQResponseLineQty.class, "QtyPromised", null);
    /** Column name QtyPromised */
    public static final String COLUMNNAME_QtyPromised = "QtyPromised";

	/**
	 * Set Ranking.
	 * Relative Rank Number
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setRanking (int Ranking);

	/**
	 * Get Ranking.
	 * Relative Rank Number
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getRanking();

    /** Column definition for Ranking */
    public static final org.adempiere.model.ModelColumn<I_C_RfQResponseLineQty, Object> COLUMN_Ranking = new org.adempiere.model.ModelColumn<I_C_RfQResponseLineQty, Object>(I_C_RfQResponseLineQty.class, "Ranking", null);
    /** Column name Ranking */
    public static final String COLUMNNAME_Ranking = "Ranking";

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
    public static final org.adempiere.model.ModelColumn<I_C_RfQResponseLineQty, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_C_RfQResponseLineQty, Object>(I_C_RfQResponseLineQty.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_RfQResponseLineQty, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_C_RfQResponseLineQty, org.compiere.model.I_AD_User>(I_C_RfQResponseLineQty.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
