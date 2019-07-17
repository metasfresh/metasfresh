package org.adempiere.model;


/** Generated Interface for M_FreightCostDetail
 *  @author Adempiere (generated)
 */
@SuppressWarnings("javadoc")
public interface I_M_FreightCostDetail
{

    /** TableName=M_FreightCostDetail */
    public static final String Table_Name = "M_FreightCostDetail";

    /** AD_Table_ID=540005 */
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
    public static final org.adempiere.model.ModelColumn<I_M_FreightCostDetail, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<>(I_M_FreightCostDetail.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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

    /** Column definition for AD_Org_ID */
    public static final org.adempiere.model.ModelColumn<I_M_FreightCostDetail, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<>(I_M_FreightCostDetail.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Land.
	 * Land
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Country_ID (int C_Country_ID);

	/**
	 * Get Land.
	 * Land
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Country_ID();

	public org.compiere.model.I_C_Country getC_Country();

	public void setC_Country(org.compiere.model.I_C_Country C_Country);

    /** Column definition for C_Country_ID */
    public static final org.adempiere.model.ModelColumn<I_M_FreightCostDetail, org.compiere.model.I_C_Country> COLUMN_C_Country_ID = new org.adempiere.model.ModelColumn<>(I_M_FreightCostDetail.class, "C_Country_ID", org.compiere.model.I_C_Country.class);
    /** Column name C_Country_ID */
    public static final String COLUMNNAME_C_Country_ID = "C_Country_ID";

	/**
	 * Set Währung.
	 * Die Währung für diesen Eintrag
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Currency_ID (int C_Currency_ID);

	/**
	 * Get Währung.
	 * Die Währung für diesen Eintrag
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Currency_ID();

    /** Column definition for C_Currency_ID */
    public static final org.adempiere.model.ModelColumn<I_M_FreightCostDetail, org.compiere.model.I_C_Currency> COLUMN_C_Currency_ID = new org.adempiere.model.ModelColumn<>(I_M_FreightCostDetail.class, "C_Currency_ID", org.compiere.model.I_C_Currency.class);
    /** Column name C_Currency_ID */
    public static final String COLUMNNAME_C_Currency_ID = "C_Currency_ID";

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
    public static final org.adempiere.model.ModelColumn<I_M_FreightCostDetail, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<>(I_M_FreightCostDetail.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_M_FreightCostDetail, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<>(I_M_FreightCostDetail.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Frachtbetrag.
	 * Frachtbetrag
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setFreightAmt (java.math.BigDecimal FreightAmt);

	/**
	 * Get Frachtbetrag.
	 * Frachtbetrag
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getFreightAmt();

    /** Column definition for FreightAmt */
    public static final org.adempiere.model.ModelColumn<I_M_FreightCostDetail, Object> COLUMN_FreightAmt = new org.adempiere.model.ModelColumn<>(I_M_FreightCostDetail.class, "FreightAmt", null);
    /** Column name FreightAmt */
    public static final String COLUMNNAME_FreightAmt = "FreightAmt";

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
    public static final org.adempiere.model.ModelColumn<I_M_FreightCostDetail, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<>(I_M_FreightCostDetail.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Frachtkostendetail.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_FreightCostDetail_ID (int M_FreightCostDetail_ID);

	/**
	 * Get Frachtkostendetail.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_FreightCostDetail_ID();

    /** Column definition for M_FreightCostDetail_ID */
    public static final org.adempiere.model.ModelColumn<I_M_FreightCostDetail, Object> COLUMN_M_FreightCostDetail_ID = new org.adempiere.model.ModelColumn<>(I_M_FreightCostDetail.class, "M_FreightCostDetail_ID", null);
    /** Column name M_FreightCostDetail_ID */
    public static final String COLUMNNAME_M_FreightCostDetail_ID = "M_FreightCostDetail_ID";

	/**
	 * Set Lieferweg-Versandkosten.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_FreightCostShipper_ID (int M_FreightCostShipper_ID);

	/**
	 * Get Lieferweg-Versandkosten.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_FreightCostShipper_ID();

	public org.adempiere.model.I_M_FreightCostShipper getM_FreightCostShipper();

	public void setM_FreightCostShipper(org.adempiere.model.I_M_FreightCostShipper M_FreightCostShipper);

    /** Column definition for M_FreightCostShipper_ID */
    public static final org.adempiere.model.ModelColumn<I_M_FreightCostDetail, org.adempiere.model.I_M_FreightCostShipper> COLUMN_M_FreightCostShipper_ID = new org.adempiere.model.ModelColumn<>(I_M_FreightCostDetail.class, "M_FreightCostShipper_ID", org.adempiere.model.I_M_FreightCostShipper.class);
    /** Column name M_FreightCostShipper_ID */
    public static final String COLUMNNAME_M_FreightCostShipper_ID = "M_FreightCostShipper_ID";

	/**
	 * Set Lieferwert.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setShipmentValueAmt (java.math.BigDecimal ShipmentValueAmt);

	/**
	 * Get Lieferwert.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getShipmentValueAmt();

    /** Column definition for ShipmentValueAmt */
    public static final org.adempiere.model.ModelColumn<I_M_FreightCostDetail, Object> COLUMN_ShipmentValueAmt = new org.adempiere.model.ModelColumn<>(I_M_FreightCostDetail.class, "ShipmentValueAmt", null);
    /** Column name ShipmentValueAmt */
    public static final String COLUMNNAME_ShipmentValueAmt = "ShipmentValueAmt";

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
    public static final org.adempiere.model.ModelColumn<I_M_FreightCostDetail, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<>(I_M_FreightCostDetail.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_M_FreightCostDetail, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<>(I_M_FreightCostDetail.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
