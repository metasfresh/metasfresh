package de.metas.handlingunits.model;


/** Generated Interface for M_HU_Reservation
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_M_HU_Reservation 
{

    /** TableName=M_HU_Reservation */
    public static final String Table_Name = "M_HU_Reservation";

    /** AD_Table_ID=540988 */
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

    /** Column definition for AD_Client_ID */
    public static final org.adempiere.model.ModelColumn<I_M_HU_Reservation, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_M_HU_Reservation, org.compiere.model.I_AD_Client>(I_M_HU_Reservation.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_M_HU_Reservation, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_M_HU_Reservation, org.compiere.model.I_AD_Org>(I_M_HU_Reservation.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Kunde.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_BPartner_Customer_ID (int C_BPartner_Customer_ID);

	/**
	 * Get Kunde.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_BPartner_Customer_ID();

    /** Column definition for C_BPartner_Customer_ID */
    public static final org.adempiere.model.ModelColumn<I_M_HU_Reservation, org.compiere.model.I_C_BPartner> COLUMN_C_BPartner_Customer_ID = new org.adempiere.model.ModelColumn<I_M_HU_Reservation, org.compiere.model.I_C_BPartner>(I_M_HU_Reservation.class, "C_BPartner_Customer_ID", org.compiere.model.I_C_BPartner.class);
    /** Column name C_BPartner_Customer_ID */
    public static final String COLUMNNAME_C_BPartner_Customer_ID = "C_BPartner_Customer_ID";

	/**
	 * Set Auftragsposition.
	 * Auftragsposition
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_OrderLineSO_ID (int C_OrderLineSO_ID);

	/**
	 * Get Auftragsposition.
	 * Auftragsposition
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_OrderLineSO_ID();

	public org.compiere.model.I_C_OrderLine getC_OrderLineSO();

	public void setC_OrderLineSO(org.compiere.model.I_C_OrderLine C_OrderLineSO);

    /** Column definition for C_OrderLineSO_ID */
    public static final org.adempiere.model.ModelColumn<I_M_HU_Reservation, org.compiere.model.I_C_OrderLine> COLUMN_C_OrderLineSO_ID = new org.adempiere.model.ModelColumn<I_M_HU_Reservation, org.compiere.model.I_C_OrderLine>(I_M_HU_Reservation.class, "C_OrderLineSO_ID", org.compiere.model.I_C_OrderLine.class);
    /** Column name C_OrderLineSO_ID */
    public static final String COLUMNNAME_C_OrderLineSO_ID = "C_OrderLineSO_ID";

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

    /** Column definition for C_UOM_ID */
    public static final org.adempiere.model.ModelColumn<I_M_HU_Reservation, org.compiere.model.I_C_UOM> COLUMN_C_UOM_ID = new org.adempiere.model.ModelColumn<I_M_HU_Reservation, org.compiere.model.I_C_UOM>(I_M_HU_Reservation.class, "C_UOM_ID", org.compiere.model.I_C_UOM.class);
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
    public static final org.adempiere.model.ModelColumn<I_M_HU_Reservation, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_M_HU_Reservation, Object>(I_M_HU_Reservation.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_M_HU_Reservation, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_M_HU_Reservation, org.compiere.model.I_AD_User>(I_M_HU_Reservation.class, "CreatedBy", org.compiere.model.I_AD_User.class);
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
    public static final org.adempiere.model.ModelColumn<I_M_HU_Reservation, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_M_HU_Reservation, Object>(I_M_HU_Reservation.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set M_HU_Reservation.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_HU_Reservation_ID (int M_HU_Reservation_ID);

	/**
	 * Get M_HU_Reservation.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_HU_Reservation_ID();

    /** Column definition for M_HU_Reservation_ID */
    public static final org.adempiere.model.ModelColumn<I_M_HU_Reservation, Object> COLUMN_M_HU_Reservation_ID = new org.adempiere.model.ModelColumn<I_M_HU_Reservation, Object>(I_M_HU_Reservation.class, "M_HU_Reservation_ID", null);
    /** Column name M_HU_Reservation_ID */
    public static final String COLUMNNAME_M_HU_Reservation_ID = "M_HU_Reservation_ID";

	/**
	 * Set Offen.
	 * Offene Menge
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setQtyReserved (java.math.BigDecimal QtyReserved);

	/**
	 * Get Offen.
	 * Offene Menge
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyReserved();

    /** Column definition for QtyReserved */
    public static final org.adempiere.model.ModelColumn<I_M_HU_Reservation, Object> COLUMN_QtyReserved = new org.adempiere.model.ModelColumn<I_M_HU_Reservation, Object>(I_M_HU_Reservation.class, "QtyReserved", null);
    /** Column name QtyReserved */
    public static final String COLUMNNAME_QtyReserved = "QtyReserved";

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
    public static final org.adempiere.model.ModelColumn<I_M_HU_Reservation, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_M_HU_Reservation, Object>(I_M_HU_Reservation.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_M_HU_Reservation, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_M_HU_Reservation, org.compiere.model.I_AD_User>(I_M_HU_Reservation.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/**
	 * Set CU Handling Unit (VHU).
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setVHU_ID (int VHU_ID);

	/**
	 * Get CU Handling Unit (VHU).
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getVHU_ID();

	public de.metas.handlingunits.model.I_M_HU getVHU();

	public void setVHU(de.metas.handlingunits.model.I_M_HU VHU);

    /** Column definition for VHU_ID */
    public static final org.adempiere.model.ModelColumn<I_M_HU_Reservation, de.metas.handlingunits.model.I_M_HU> COLUMN_VHU_ID = new org.adempiere.model.ModelColumn<I_M_HU_Reservation, de.metas.handlingunits.model.I_M_HU>(I_M_HU_Reservation.class, "VHU_ID", de.metas.handlingunits.model.I_M_HU.class);
    /** Column name VHU_ID */
    public static final String COLUMNNAME_VHU_ID = "VHU_ID";
}
