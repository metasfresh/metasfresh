package org.eevolution.model;


/** Generated Interface for PP_Cost_Collector_CostDetailAdjust
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_PP_Cost_Collector_CostDetailAdjust 
{

    /** TableName=PP_Cost_Collector_CostDetailAdjust */
    public static final String Table_Name = "PP_Cost_Collector_CostDetailAdjust";

    /** AD_Table_ID=541158 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 1 - Org
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(1);

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
    public static final org.adempiere.model.ModelColumn<I_PP_Cost_Collector_CostDetailAdjust, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_PP_Cost_Collector_CostDetailAdjust, org.compiere.model.I_AD_Client>(I_PP_Cost_Collector_CostDetailAdjust.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_PP_Cost_Collector_CostDetailAdjust, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_PP_Cost_Collector_CostDetailAdjust, org.compiere.model.I_AD_Org>(I_PP_Cost_Collector_CostDetailAdjust.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Betrag.
	 * Betrag
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAmt (java.math.BigDecimal Amt);

	/**
	 * Get Betrag.
	 * Betrag
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getAmt();

    /** Column definition for Amt */
    public static final org.adempiere.model.ModelColumn<I_PP_Cost_Collector_CostDetailAdjust, Object> COLUMN_Amt = new org.adempiere.model.ModelColumn<I_PP_Cost_Collector_CostDetailAdjust, Object>(I_PP_Cost_Collector_CostDetailAdjust.class, "Amt", null);
    /** Column name Amt */
    public static final String COLUMNNAME_Amt = "Amt";

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
    public static final org.adempiere.model.ModelColumn<I_PP_Cost_Collector_CostDetailAdjust, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_PP_Cost_Collector_CostDetailAdjust, Object>(I_PP_Cost_Collector_CostDetailAdjust.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_PP_Cost_Collector_CostDetailAdjust, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_PP_Cost_Collector_CostDetailAdjust, org.compiere.model.I_AD_User>(I_PP_Cost_Collector_CostDetailAdjust.class, "CreatedBy", org.compiere.model.I_AD_User.class);
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
    public static final org.adempiere.model.ModelColumn<I_PP_Cost_Collector_CostDetailAdjust, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_PP_Cost_Collector_CostDetailAdjust, Object>(I_PP_Cost_Collector_CostDetailAdjust.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Kosten-Detail.
	 * Cost Detail Information
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_CostDetail_ID (int M_CostDetail_ID);

	/**
	 * Get Kosten-Detail.
	 * Cost Detail Information
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_CostDetail_ID();

	public org.compiere.model.I_M_CostDetail getM_CostDetail();

	public void setM_CostDetail(org.compiere.model.I_M_CostDetail M_CostDetail);

    /** Column definition for M_CostDetail_ID */
    public static final org.adempiere.model.ModelColumn<I_PP_Cost_Collector_CostDetailAdjust, org.compiere.model.I_M_CostDetail> COLUMN_M_CostDetail_ID = new org.adempiere.model.ModelColumn<I_PP_Cost_Collector_CostDetailAdjust, org.compiere.model.I_M_CostDetail>(I_PP_Cost_Collector_CostDetailAdjust.class, "M_CostDetail_ID", org.compiere.model.I_M_CostDetail.class);
    /** Column name M_CostDetail_ID */
    public static final String COLUMNNAME_M_CostDetail_ID = "M_CostDetail_ID";

	/**
	 * Set PP_Cost_Collector_CostDetailAdjust.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setPP_Cost_Collector_CostDetailAdjust_ID (int PP_Cost_Collector_CostDetailAdjust_ID);

	/**
	 * Get PP_Cost_Collector_CostDetailAdjust.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getPP_Cost_Collector_CostDetailAdjust_ID();

    /** Column definition for PP_Cost_Collector_CostDetailAdjust_ID */
    public static final org.adempiere.model.ModelColumn<I_PP_Cost_Collector_CostDetailAdjust, Object> COLUMN_PP_Cost_Collector_CostDetailAdjust_ID = new org.adempiere.model.ModelColumn<I_PP_Cost_Collector_CostDetailAdjust, Object>(I_PP_Cost_Collector_CostDetailAdjust.class, "PP_Cost_Collector_CostDetailAdjust_ID", null);
    /** Column name PP_Cost_Collector_CostDetailAdjust_ID */
    public static final String COLUMNNAME_PP_Cost_Collector_CostDetailAdjust_ID = "PP_Cost_Collector_CostDetailAdjust_ID";

	/**
	 * Set Previous Cumulated Amount.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setPrev_CumulatedAmt (java.math.BigDecimal Prev_CumulatedAmt);

	/**
	 * Get Previous Cumulated Amount.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getPrev_CumulatedAmt();

    /** Column definition for Prev_CumulatedAmt */
    public static final org.adempiere.model.ModelColumn<I_PP_Cost_Collector_CostDetailAdjust, Object> COLUMN_Prev_CumulatedAmt = new org.adempiere.model.ModelColumn<I_PP_Cost_Collector_CostDetailAdjust, Object>(I_PP_Cost_Collector_CostDetailAdjust.class, "Prev_CumulatedAmt", null);
    /** Column name Prev_CumulatedAmt */
    public static final String COLUMNNAME_Prev_CumulatedAmt = "Prev_CumulatedAmt";

	/**
	 * Set Previous Cumulated Quantity.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setPrev_CumulatedQty (java.math.BigDecimal Prev_CumulatedQty);

	/**
	 * Get Previous Cumulated Quantity.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getPrev_CumulatedQty();

    /** Column definition for Prev_CumulatedQty */
    public static final org.adempiere.model.ModelColumn<I_PP_Cost_Collector_CostDetailAdjust, Object> COLUMN_Prev_CumulatedQty = new org.adempiere.model.ModelColumn<I_PP_Cost_Collector_CostDetailAdjust, Object>(I_PP_Cost_Collector_CostDetailAdjust.class, "Prev_CumulatedQty", null);
    /** Column name Prev_CumulatedQty */
    public static final String COLUMNNAME_Prev_CumulatedQty = "Prev_CumulatedQty";

	/**
	 * Set Previous Current Cost Price.
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setPrev_CurrentCostPrice (java.math.BigDecimal Prev_CurrentCostPrice);

	/**
	 * Get Previous Current Cost Price.
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getPrev_CurrentCostPrice();

    /** Column definition for Prev_CurrentCostPrice */
    public static final org.adempiere.model.ModelColumn<I_PP_Cost_Collector_CostDetailAdjust, Object> COLUMN_Prev_CurrentCostPrice = new org.adempiere.model.ModelColumn<I_PP_Cost_Collector_CostDetailAdjust, Object>(I_PP_Cost_Collector_CostDetailAdjust.class, "Prev_CurrentCostPrice", null);
    /** Column name Prev_CurrentCostPrice */
    public static final String COLUMNNAME_Prev_CurrentCostPrice = "Prev_CurrentCostPrice";

	/**
	 * Set Previous Current Cost Price LL.
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setPrev_CurrentCostPriceLL (java.math.BigDecimal Prev_CurrentCostPriceLL);

	/**
	 * Get Previous Current Cost Price LL.
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getPrev_CurrentCostPriceLL();

    /** Column definition for Prev_CurrentCostPriceLL */
    public static final org.adempiere.model.ModelColumn<I_PP_Cost_Collector_CostDetailAdjust, Object> COLUMN_Prev_CurrentCostPriceLL = new org.adempiere.model.ModelColumn<I_PP_Cost_Collector_CostDetailAdjust, Object>(I_PP_Cost_Collector_CostDetailAdjust.class, "Prev_CurrentCostPriceLL", null);
    /** Column name Prev_CurrentCostPriceLL */
    public static final String COLUMNNAME_Prev_CurrentCostPriceLL = "Prev_CurrentCostPriceLL";

	/**
	 * Set Previous Current Qty.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setPrev_CurrentQty (java.math.BigDecimal Prev_CurrentQty);

	/**
	 * Get Previous Current Qty.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getPrev_CurrentQty();

    /** Column definition for Prev_CurrentQty */
    public static final org.adempiere.model.ModelColumn<I_PP_Cost_Collector_CostDetailAdjust, Object> COLUMN_Prev_CurrentQty = new org.adempiere.model.ModelColumn<I_PP_Cost_Collector_CostDetailAdjust, Object>(I_PP_Cost_Collector_CostDetailAdjust.class, "Prev_CurrentQty", null);
    /** Column name Prev_CurrentQty */
    public static final String COLUMNNAME_Prev_CurrentQty = "Prev_CurrentQty";

	/**
	 * Set Menge.
	 * Menge
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setQty (java.math.BigDecimal Qty);

	/**
	 * Get Menge.
	 * Menge
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQty();

    /** Column definition for Qty */
    public static final org.adempiere.model.ModelColumn<I_PP_Cost_Collector_CostDetailAdjust, Object> COLUMN_Qty = new org.adempiere.model.ModelColumn<I_PP_Cost_Collector_CostDetailAdjust, Object>(I_PP_Cost_Collector_CostDetailAdjust.class, "Qty", null);
    /** Column name Qty */
    public static final String COLUMNNAME_Qty = "Qty";

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
    public static final org.adempiere.model.ModelColumn<I_PP_Cost_Collector_CostDetailAdjust, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_PP_Cost_Collector_CostDetailAdjust, Object>(I_PP_Cost_Collector_CostDetailAdjust.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_PP_Cost_Collector_CostDetailAdjust, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_PP_Cost_Collector_CostDetailAdjust, org.compiere.model.I_AD_User>(I_PP_Cost_Collector_CostDetailAdjust.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
