package de.metas.tourplanning.model;


/** Generated Interface for M_Tour_Instance
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_M_Tour_Instance 
{

    /** TableName=M_Tour_Instance */
    public static final String Table_Name = "M_Tour_Instance";

    /** AD_Table_ID=540609 */
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
    public static final org.adempiere.model.ModelColumn<I_M_Tour_Instance, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_M_Tour_Instance, org.compiere.model.I_AD_Client>(I_M_Tour_Instance.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_M_Tour_Instance, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_M_Tour_Instance, org.compiere.model.I_AD_Org>(I_M_Tour_Instance.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

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
    public static final org.adempiere.model.ModelColumn<I_M_Tour_Instance, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_M_Tour_Instance, Object>(I_M_Tour_Instance.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_M_Tour_Instance, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_M_Tour_Instance, org.compiere.model.I_AD_User>(I_M_Tour_Instance.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Lieferdatum.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDeliveryDate (java.sql.Timestamp DeliveryDate);

	/**
	 * Get Lieferdatum.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getDeliveryDate();

    /** Column definition for DeliveryDate */
    public static final org.adempiere.model.ModelColumn<I_M_Tour_Instance, Object> COLUMN_DeliveryDate = new org.adempiere.model.ModelColumn<I_M_Tour_Instance, Object>(I_M_Tour_Instance.class, "DeliveryDate", null);
    /** Column name DeliveryDate */
    public static final String COLUMNNAME_DeliveryDate = "DeliveryDate";

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
    public static final org.adempiere.model.ModelColumn<I_M_Tour_Instance, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_M_Tour_Instance, Object>(I_M_Tour_Instance.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Transport Auftrag.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_ShipperTransportation_ID (int M_ShipperTransportation_ID);

	/**
	 * Get Transport Auftrag.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_ShipperTransportation_ID();

    /** Column definition for M_ShipperTransportation_ID */
    public static final org.adempiere.model.ModelColumn<I_M_Tour_Instance, Object> COLUMN_M_ShipperTransportation_ID = new org.adempiere.model.ModelColumn<I_M_Tour_Instance, Object>(I_M_Tour_Instance.class, "M_ShipperTransportation_ID", null);
    /** Column name M_ShipperTransportation_ID */
    public static final String COLUMNNAME_M_ShipperTransportation_ID = "M_ShipperTransportation_ID";

	/**
	 * Set Tour.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_Tour_ID (int M_Tour_ID);

	/**
	 * Get Tour.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_Tour_ID();

	public de.metas.tourplanning.model.I_M_Tour getM_Tour();

	public void setM_Tour(de.metas.tourplanning.model.I_M_Tour M_Tour);

    /** Column definition for M_Tour_ID */
    public static final org.adempiere.model.ModelColumn<I_M_Tour_Instance, de.metas.tourplanning.model.I_M_Tour> COLUMN_M_Tour_ID = new org.adempiere.model.ModelColumn<I_M_Tour_Instance, de.metas.tourplanning.model.I_M_Tour>(I_M_Tour_Instance.class, "M_Tour_ID", de.metas.tourplanning.model.I_M_Tour.class);
    /** Column name M_Tour_ID */
    public static final String COLUMNNAME_M_Tour_ID = "M_Tour_ID";

	/**
	 * Set Tour Instance.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_Tour_Instance_ID (int M_Tour_Instance_ID);

	/**
	 * Get Tour Instance.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_Tour_Instance_ID();

    /** Column definition for M_Tour_Instance_ID */
    public static final org.adempiere.model.ModelColumn<I_M_Tour_Instance, Object> COLUMN_M_Tour_Instance_ID = new org.adempiere.model.ModelColumn<I_M_Tour_Instance, Object>(I_M_Tour_Instance.class, "M_Tour_Instance_ID", null);
    /** Column name M_Tour_Instance_ID */
    public static final String COLUMNNAME_M_Tour_Instance_ID = "M_Tour_Instance_ID";

	/**
	 * Set Verarbeitet.
	 * Checkbox sagt aus, ob der Beleg verarbeitet wurde.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setProcessed (boolean Processed);

	/**
	 * Get Verarbeitet.
	 * Checkbox sagt aus, ob der Beleg verarbeitet wurde.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isProcessed();

    /** Column definition for Processed */
    public static final org.adempiere.model.ModelColumn<I_M_Tour_Instance, Object> COLUMN_Processed = new org.adempiere.model.ModelColumn<I_M_Tour_Instance, Object>(I_M_Tour_Instance.class, "Processed", null);
    /** Column name Processed */
    public static final String COLUMNNAME_Processed = "Processed";

	/**
	 * Set Gelieferte Menge (LU).
	 * Gelieferte Menge (LU)
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setQtyDelivered_LU (java.math.BigDecimal QtyDelivered_LU);

	/**
	 * Get Gelieferte Menge (LU).
	 * Gelieferte Menge (LU)
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyDelivered_LU();

    /** Column definition for QtyDelivered_LU */
    public static final org.adempiere.model.ModelColumn<I_M_Tour_Instance, Object> COLUMN_QtyDelivered_LU = new org.adempiere.model.ModelColumn<I_M_Tour_Instance, Object>(I_M_Tour_Instance.class, "QtyDelivered_LU", null);
    /** Column name QtyDelivered_LU */
    public static final String COLUMNNAME_QtyDelivered_LU = "QtyDelivered_LU";

	/**
	 * Set Gelieferte Menge (TU).
	 * Gelieferte Menge (TU)
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setQtyDelivered_TU (java.math.BigDecimal QtyDelivered_TU);

	/**
	 * Get Gelieferte Menge (TU).
	 * Gelieferte Menge (TU)
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyDelivered_TU();

    /** Column definition for QtyDelivered_TU */
    public static final org.adempiere.model.ModelColumn<I_M_Tour_Instance, Object> COLUMN_QtyDelivered_TU = new org.adempiere.model.ModelColumn<I_M_Tour_Instance, Object>(I_M_Tour_Instance.class, "QtyDelivered_TU", null);
    /** Column name QtyDelivered_TU */
    public static final String COLUMNNAME_QtyDelivered_TU = "QtyDelivered_TU";

	/**
	 * Set Bestellte Menge (LU).
	 * Bestellte Menge (LU)
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setQtyOrdered_LU (java.math.BigDecimal QtyOrdered_LU);

	/**
	 * Get Bestellte Menge (LU).
	 * Bestellte Menge (LU)
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyOrdered_LU();

    /** Column definition for QtyOrdered_LU */
    public static final org.adempiere.model.ModelColumn<I_M_Tour_Instance, Object> COLUMN_QtyOrdered_LU = new org.adempiere.model.ModelColumn<I_M_Tour_Instance, Object>(I_M_Tour_Instance.class, "QtyOrdered_LU", null);
    /** Column name QtyOrdered_LU */
    public static final String COLUMNNAME_QtyOrdered_LU = "QtyOrdered_LU";

	/**
	 * Set Bestellte Menge (TU).
	 * Bestellte Menge (TU)
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setQtyOrdered_TU (java.math.BigDecimal QtyOrdered_TU);

	/**
	 * Get Bestellte Menge (TU).
	 * Bestellte Menge (TU)
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyOrdered_TU();

    /** Column definition for QtyOrdered_TU */
    public static final org.adempiere.model.ModelColumn<I_M_Tour_Instance, Object> COLUMN_QtyOrdered_TU = new org.adempiere.model.ModelColumn<I_M_Tour_Instance, Object>(I_M_Tour_Instance.class, "QtyOrdered_TU", null);
    /** Column name QtyOrdered_TU */
    public static final String COLUMNNAME_QtyOrdered_TU = "QtyOrdered_TU";

	/**
	 * Set Ausliefermenge (LU).
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setQtyToDeliver_LU (java.math.BigDecimal QtyToDeliver_LU);

	/**
	 * Get Ausliefermenge (LU).
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyToDeliver_LU();

    /** Column definition for QtyToDeliver_LU */
    public static final org.adempiere.model.ModelColumn<I_M_Tour_Instance, Object> COLUMN_QtyToDeliver_LU = new org.adempiere.model.ModelColumn<I_M_Tour_Instance, Object>(I_M_Tour_Instance.class, "QtyToDeliver_LU", null);
    /** Column name QtyToDeliver_LU */
    public static final String COLUMNNAME_QtyToDeliver_LU = "QtyToDeliver_LU";

	/**
	 * Set Ausliefermenge (TU).
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setQtyToDeliver_TU (java.math.BigDecimal QtyToDeliver_TU);

	/**
	 * Get Ausliefermenge (TU).
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyToDeliver_TU();

    /** Column definition for QtyToDeliver_TU */
    public static final org.adempiere.model.ModelColumn<I_M_Tour_Instance, Object> COLUMN_QtyToDeliver_TU = new org.adempiere.model.ModelColumn<I_M_Tour_Instance, Object>(I_M_Tour_Instance.class, "QtyToDeliver_TU", null);
    /** Column name QtyToDeliver_TU */
    public static final String COLUMNNAME_QtyToDeliver_TU = "QtyToDeliver_TU";

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
    public static final org.adempiere.model.ModelColumn<I_M_Tour_Instance, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_M_Tour_Instance, Object>(I_M_Tour_Instance.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_M_Tour_Instance, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_M_Tour_Instance, org.compiere.model.I_AD_User>(I_M_Tour_Instance.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
