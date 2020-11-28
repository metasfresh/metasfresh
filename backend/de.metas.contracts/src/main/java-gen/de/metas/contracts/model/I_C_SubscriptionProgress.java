package de.metas.contracts.model;


/** Generated Interface for C_SubscriptionProgress
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_C_SubscriptionProgress 
{

    /** TableName=C_SubscriptionProgress */
    public static final String Table_Name = "C_SubscriptionProgress";

    /** AD_Table_ID=540029 */
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
    public static final org.adempiere.model.ModelColumn<I_C_SubscriptionProgress, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_C_SubscriptionProgress, org.compiere.model.I_AD_Client>(I_C_SubscriptionProgress.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_C_SubscriptionProgress, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_C_SubscriptionProgress, org.compiere.model.I_AD_Org>(I_C_SubscriptionProgress.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Pauschale - Vertragsperiode.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_Flatrate_Term_ID (int C_Flatrate_Term_ID);

	/**
	 * Get Pauschale - Vertragsperiode.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_Flatrate_Term_ID();

	public de.metas.contracts.model.I_C_Flatrate_Term getC_Flatrate_Term();

	public void setC_Flatrate_Term(de.metas.contracts.model.I_C_Flatrate_Term C_Flatrate_Term);

    /** Column definition for C_Flatrate_Term_ID */
    public static final org.adempiere.model.ModelColumn<I_C_SubscriptionProgress, de.metas.contracts.model.I_C_Flatrate_Term> COLUMN_C_Flatrate_Term_ID = new org.adempiere.model.ModelColumn<I_C_SubscriptionProgress, de.metas.contracts.model.I_C_Flatrate_Term>(I_C_SubscriptionProgress.class, "C_Flatrate_Term_ID", de.metas.contracts.model.I_C_Flatrate_Term.class);
    /** Column name C_Flatrate_Term_ID */
    public static final String COLUMNNAME_C_Flatrate_Term_ID = "C_Flatrate_Term_ID";

	/**
	 * Set Vertrags-Status.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setContractStatus (java.lang.String ContractStatus);

	/**
	 * Get Vertrags-Status.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getContractStatus();

    /** Column definition for ContractStatus */
    public static final org.adempiere.model.ModelColumn<I_C_SubscriptionProgress, Object> COLUMN_ContractStatus = new org.adempiere.model.ModelColumn<I_C_SubscriptionProgress, Object>(I_C_SubscriptionProgress.class, "ContractStatus", null);
    /** Column name ContractStatus */
    public static final String COLUMNNAME_ContractStatus = "ContractStatus";

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
    public static final org.adempiere.model.ModelColumn<I_C_SubscriptionProgress, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_C_SubscriptionProgress, Object>(I_C_SubscriptionProgress.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_SubscriptionProgress, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_C_SubscriptionProgress, org.compiere.model.I_AD_User>(I_C_SubscriptionProgress.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Abo-Verlauf.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_SubscriptionProgress_ID (int C_SubscriptionProgress_ID);

	/**
	 * Get Abo-Verlauf.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_SubscriptionProgress_ID();

    /** Column definition for C_SubscriptionProgress_ID */
    public static final org.adempiere.model.ModelColumn<I_C_SubscriptionProgress, Object> COLUMN_C_SubscriptionProgress_ID = new org.adempiere.model.ModelColumn<I_C_SubscriptionProgress, Object>(I_C_SubscriptionProgress.class, "C_SubscriptionProgress_ID", null);
    /** Column name C_SubscriptionProgress_ID */
    public static final String COLUMNNAME_C_SubscriptionProgress_ID = "C_SubscriptionProgress_ID";

	/**
	 * Set Lieferempfänger.
	 * Business Partner to ship to
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDropShip_BPartner_ID (int DropShip_BPartner_ID);

	/**
	 * Get Lieferempfänger.
	 * Business Partner to ship to
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getDropShip_BPartner_ID();

	public org.compiere.model.I_C_BPartner getDropShip_BPartner();

	public void setDropShip_BPartner(org.compiere.model.I_C_BPartner DropShip_BPartner);

    /** Column definition for DropShip_BPartner_ID */
    public static final org.adempiere.model.ModelColumn<I_C_SubscriptionProgress, org.compiere.model.I_C_BPartner> COLUMN_DropShip_BPartner_ID = new org.adempiere.model.ModelColumn<I_C_SubscriptionProgress, org.compiere.model.I_C_BPartner>(I_C_SubscriptionProgress.class, "DropShip_BPartner_ID", org.compiere.model.I_C_BPartner.class);
    /** Column name DropShip_BPartner_ID */
    public static final String COLUMNNAME_DropShip_BPartner_ID = "DropShip_BPartner_ID";

	/**
	 * Set Lieferadresse.
	 * Business Partner Location for shipping to
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDropShip_Location_ID (int DropShip_Location_ID);

	/**
	 * Get Lieferadresse.
	 * Business Partner Location for shipping to
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getDropShip_Location_ID();

	public org.compiere.model.I_C_BPartner_Location getDropShip_Location();

	public void setDropShip_Location(org.compiere.model.I_C_BPartner_Location DropShip_Location);

    /** Column definition for DropShip_Location_ID */
    public static final org.adempiere.model.ModelColumn<I_C_SubscriptionProgress, org.compiere.model.I_C_BPartner_Location> COLUMN_DropShip_Location_ID = new org.adempiere.model.ModelColumn<I_C_SubscriptionProgress, org.compiere.model.I_C_BPartner_Location>(I_C_SubscriptionProgress.class, "DropShip_Location_ID", org.compiere.model.I_C_BPartner_Location.class);
    /** Column name DropShip_Location_ID */
    public static final String COLUMNNAME_DropShip_Location_ID = "DropShip_Location_ID";

	/**
	 * Set Lieferkontakt.
	 * Business Partner Contact for drop shipment
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDropShip_User_ID (int DropShip_User_ID);

	/**
	 * Get Lieferkontakt.
	 * Business Partner Contact for drop shipment
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getDropShip_User_ID();

	public org.compiere.model.I_AD_User getDropShip_User();

	public void setDropShip_User(org.compiere.model.I_AD_User DropShip_User);

    /** Column definition for DropShip_User_ID */
    public static final org.adempiere.model.ModelColumn<I_C_SubscriptionProgress, org.compiere.model.I_AD_User> COLUMN_DropShip_User_ID = new org.adempiere.model.ModelColumn<I_C_SubscriptionProgress, org.compiere.model.I_AD_User>(I_C_SubscriptionProgress.class, "DropShip_User_ID", org.compiere.model.I_AD_User.class);
    /** Column name DropShip_User_ID */
    public static final String COLUMNNAME_DropShip_User_ID = "DropShip_User_ID";

	/**
	 * Set Datum.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setEventDate (java.sql.Timestamp EventDate);

	/**
	 * Get Datum.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getEventDate();

    /** Column definition for EventDate */
    public static final org.adempiere.model.ModelColumn<I_C_SubscriptionProgress, Object> COLUMN_EventDate = new org.adempiere.model.ModelColumn<I_C_SubscriptionProgress, Object>(I_C_SubscriptionProgress.class, "EventDate", null);
    /** Column name EventDate */
    public static final String COLUMNNAME_EventDate = "EventDate";

	/**
	 * Set Ereignisart.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setEventType (java.lang.String EventType);

	/**
	 * Get Ereignisart.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getEventType();

    /** Column definition for EventType */
    public static final org.adempiere.model.ModelColumn<I_C_SubscriptionProgress, Object> COLUMN_EventType = new org.adempiere.model.ModelColumn<I_C_SubscriptionProgress, Object>(I_C_SubscriptionProgress.class, "EventType", null);
    /** Column name EventType */
    public static final String COLUMNNAME_EventType = "EventType";

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
    public static final org.adempiere.model.ModelColumn<I_C_SubscriptionProgress, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_C_SubscriptionProgress, Object>(I_C_SubscriptionProgress.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Lieferdisposition.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_ShipmentSchedule_ID (int M_ShipmentSchedule_ID);

	/**
	 * Get Lieferdisposition.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_ShipmentSchedule_ID();

	public de.metas.inoutcandidate.model.I_M_ShipmentSchedule getM_ShipmentSchedule();

	public void setM_ShipmentSchedule(de.metas.inoutcandidate.model.I_M_ShipmentSchedule M_ShipmentSchedule);

    /** Column definition for M_ShipmentSchedule_ID */
    public static final org.adempiere.model.ModelColumn<I_C_SubscriptionProgress, de.metas.inoutcandidate.model.I_M_ShipmentSchedule> COLUMN_M_ShipmentSchedule_ID = new org.adempiere.model.ModelColumn<I_C_SubscriptionProgress, de.metas.inoutcandidate.model.I_M_ShipmentSchedule>(I_C_SubscriptionProgress.class, "M_ShipmentSchedule_ID", de.metas.inoutcandidate.model.I_M_ShipmentSchedule.class);
    /** Column name M_ShipmentSchedule_ID */
    public static final String COLUMNNAME_M_ShipmentSchedule_ID = "M_ShipmentSchedule_ID";

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
    public static final org.adempiere.model.ModelColumn<I_C_SubscriptionProgress, Object> COLUMN_Processed = new org.adempiere.model.ModelColumn<I_C_SubscriptionProgress, Object>(I_C_SubscriptionProgress.class, "Processed", null);
    /** Column name Processed */
    public static final String COLUMNNAME_Processed = "Processed";

	/**
	 * Set Menge.
	 * Menge
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setQty (java.math.BigDecimal Qty);

	/**
	 * Get Menge.
	 * Menge
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQty();

    /** Column definition for Qty */
    public static final org.adempiere.model.ModelColumn<I_C_SubscriptionProgress, Object> COLUMN_Qty = new org.adempiere.model.ModelColumn<I_C_SubscriptionProgress, Object>(I_C_SubscriptionProgress.class, "Qty", null);
    /** Column name Qty */
    public static final String COLUMNNAME_Qty = "Qty";

	/**
	 * Set Reihenfolge.
	 * Zur Bestimmung der Reihenfolge der Einträge;
 die kleinste Zahl kommt zuerst
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setSeqNo (int SeqNo);

	/**
	 * Get Reihenfolge.
	 * Zur Bestimmung der Reihenfolge der Einträge;
 die kleinste Zahl kommt zuerst
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getSeqNo();

    /** Column definition for SeqNo */
    public static final org.adempiere.model.ModelColumn<I_C_SubscriptionProgress, Object> COLUMN_SeqNo = new org.adempiere.model.ModelColumn<I_C_SubscriptionProgress, Object>(I_C_SubscriptionProgress.class, "SeqNo", null);
    /** Column name SeqNo */
    public static final String COLUMNNAME_SeqNo = "SeqNo";

	/**
	 * Set Status.
	 * Status of the currently running check
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setStatus (java.lang.String Status);

	/**
	 * Get Status.
	 * Status of the currently running check
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getStatus();

    /** Column definition for Status */
    public static final org.adempiere.model.ModelColumn<I_C_SubscriptionProgress, Object> COLUMN_Status = new org.adempiere.model.ModelColumn<I_C_SubscriptionProgress, Object>(I_C_SubscriptionProgress.class, "Status", null);
    /** Column name Status */
    public static final String COLUMNNAME_Status = "Status";

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
    public static final org.adempiere.model.ModelColumn<I_C_SubscriptionProgress, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_C_SubscriptionProgress, Object>(I_C_SubscriptionProgress.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_SubscriptionProgress, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_C_SubscriptionProgress, org.compiere.model.I_AD_User>(I_C_SubscriptionProgress.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
